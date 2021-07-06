import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DATATABLE_EDITMODE, IDataTableColumn } from '../../../shared/components/datatable/datatable.model';
import { ICargo, ICargoResponseModel } from '../../../shared/models/common.model';
import { ILoadableQuantityCargo } from '../../core/models/common.model';
import { ILoadingDelays, ILoadingSequences } from '../models/loading-information.model';
import { LoadingDischargingManageSequenceTransformationService } from './services/loading-discharging-manage-sequence-transformation.service';
import { ILoadingSequenceListData, ILoadingSequenceValueObject } from './models/loading-discharging-manage-sequence.model';
import { durationValidator } from './validators/duration-validator.directive';
import { cargoQuantityValidator } from './validators/cargo-quantity-validator.directive';
import { ConfirmationService } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';

/**
 * Component class for loading discharging manage sequence component
 *
 * @export
 * @class LoadingDischargingManageSequenceComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-loading-discharging-manage-sequence',
  templateUrl: './loading-discharging-manage-sequence.component.html',
  styleUrls: ['./loading-discharging-manage-sequence.component.scss']
})
export class LoadingDischargingManageSequenceComponent implements OnInit {
  @Input() cargos: ICargo[];
  @Input() loadableQuantityCargo: ILoadableQuantityCargo[];

  @Input() loadingInfoId: number;

  @Input() get loadingSequences(): ILoadingSequences {
    return this._loadingSequences;
  }

  set loadingSequences(loadingSequences: ILoadingSequences) {
    this._loadingSequences = loadingSequences;
    if (this.loadingSequences) {
      this.initiLoadingSequenceArray();
    }
  }
  @Output() updateLoadingDelays: EventEmitter<ILoadingDelays[]> = new EventEmitter();

  private _loadingSequences: ILoadingSequences;

  loadingSequenceForm: FormGroup;
  columns: IDataTableColumn[];
  listData = <ILoadingSequenceListData>{};
  cargoTobeLoaded: ILoadableQuantityCargo[];
  loadingDelays: ILoadingSequenceValueObject[] = [];
  editMode: DATATABLE_EDITMODE = DATATABLE_EDITMODE.CELL;
  loadingDelayList: ILoadingDelays[];
  addInitialDelay = false;
  constructor(
    private confirmationService: ConfirmationService,
    private translateService: TranslateService,
    private fb: FormBuilder,
    private loadingDischargingManageSequenceTransformationService: LoadingDischargingManageSequenceTransformationService) { }

  async ngOnInit(): Promise<void> {
    this.columns = this.loadingDischargingManageSequenceTransformationService.getDatatableColumns();
    await this.initiLoadingSequenceArray();
  }

  /**
* Method for init loading sequence array
*
* @memberof LoadingDischargingManageSequenceComponent
*/
  async initiLoadingSequenceArray() {
    this.listData = await this.getDropdownData();
    this.listData.reasonForDelays = this.loadingSequences.reasonForDelays;
    const _loadingDelays = this.loadingSequences.loadingDelays?.map((loadingDelay) => {
      const loadingSequenceData = this.loadingDischargingManageSequenceTransformationService.getLoadingDelayAsValueObject(loadingDelay, false, true, this.listData);
      if (!loadingDelay.cargoId && !loadingDelay.quantity) {
        this.addInitialDelay = true;
      }
      return loadingSequenceData;
    });
    const loadingDelayArray = _loadingDelays?.map((loadingDelay, index) =>
      this.initLoadingSequenceFormGroup(loadingDelay, index, false)
    );
    this.loadingDelays = _loadingDelays;
    this.loadingSequenceForm = this.fb.group({
      dataTable: this.fb.array([...loadingDelayArray])
    });
  }

  /**
* Get all lookups for loading sequence screen
*
* @returns {Promise<ILoadingSequenceListData>}
* @memberof LoadingDischargingManageSequenceComponent
*/
  async getDropdownData(): Promise<ILoadingSequenceListData> {
    const cargoTobeLoaded = this.loadableQuantityCargo?.map(loadable => {
      if (loadable) {
        loadable.grade = this.findCargo(loadable);
      }
      return loadable;
    })
    this.listData = <ILoadingSequenceListData>{};
    this.listData.loadableQuantityCargo = cargoTobeLoaded;
    return this.listData;
  }



  /**
 * Method to find out cargo
 *
 * @memberof LoadingDischargingManageSequenceComponent
 */
  findCargo(loadableQuantityCargoDetails): string {
    let cargoDetail;
    this.cargos?.map((cargo) => {
      if (cargo.id === loadableQuantityCargoDetails?.cargoId) {
        cargoDetail = cargo;
      }
    })
    return cargoDetail?.name;
  }

  /**
  * Method for initializing loading sequence row
  *
  * @private
  * @param {ILoadingSequenceValueObject} loadingDelay
  * @returns
  * @memberof LoadingDischargingManageSequenceComponent
  */
  initLoadingSequenceFormGroup(loadingDelay: ILoadingSequenceValueObject, index: number, initialDelay: boolean) {
    return this.fb.group({
      id: loadingDelay.id,
      reasonForDelay: this.fb.control(loadingDelay.reasonForDelay.value, [Validators.required]),
      duration: this.fb.control(loadingDelay.duration.value, [Validators.required, durationValidator(24, 59)]),
      cargo: this.fb.control(loadingDelay.cargo.value, initialDelay ? [] : [Validators.required, cargoQuantityValidator('cargo')]),
      quantity: this.fb.control(loadingDelay.quantity.value, initialDelay ? [] : [Validators.required, cargoQuantityValidator('quantity')])
    })
  }


  /**
   * Method for adding new port
   *
   * @private
   * @memberof LoadingDischargingManageSequenceComponent
   */
  addLoadingSequence(loadingDelay: ILoadingDelays = null) {
    loadingDelay = loadingDelay ?? <ILoadingDelays>{ id: 0, loadingInfoId: null, reasonForDelayId: null, duration: null, cargoId: null, quantity: null };
    const _loadingDelays = this.loadingDischargingManageSequenceTransformationService.getLoadingDelayAsValueObject(loadingDelay, true, true, this.listData);
    const dataTableControl = <FormArray>this.loadingSequenceForm.get('dataTable');
    dataTableControl.push(this.initLoadingSequenceFormGroup(_loadingDelays, this.loadingDelays.length, false));
    this.loadingDelays = [...this.loadingDelays, _loadingDelays];

  }

  /**
     * Event handler for edit complete event
     *
     * @memberof LoadingDischargingManageSequenceComponent
     */
  onEditComplete(event) {
    const index = event.index;
    const form = this.row(index);
    if(event.field === 'cargo'){
      form.controls.quantity.updateValueAndValidity();
    }
    if(event.field === 'quantity'){
      form.controls.cargo.updateValueAndValidity();
    }
    if (form.valid) {
      const loadingDelaysList = this.loadingDischargingManageSequenceTransformationService.getLoadingDelayAsValue(this.loadingDelays, this.loadingInfoId)
      this.updateLoadingDelays.emit(loadingDelaysList);
      for (const key in this.loadingDelays[index]) {
        if (this.loadingDelays[index]?.hasOwnProperty(key) && this.loadingDelays[index][key]?.hasOwnProperty('_isEditMode')) {
          this.loadingDelays[index][key].isEditMode = false;
        }
      }
    }
  }

  /**
  * Method for fetching form group
  *
  * @private
  * @param {number} formGroupIndex
  * @returns {FormGroup}
  * @memberof LoadingDischargingManageSequenceComponent
  */
  private row(formGroupIndex: number): FormGroup {
    const formGroup = <FormGroup>(<FormArray>this.loadingSequenceForm.get('dataTable')).at(formGroupIndex);
    return formGroup;
  }

  /**
* Method for add initial delay
*
* @private
* @memberof LoadingDischargingManageSequenceComponent
*/
  onAddInitialDelay(loadingDelay: ILoadingDelays = null) {
    const dataTableControl = <FormArray>this.loadingSequenceForm.get('dataTable');
    if (this.addInitialDelay) {
      loadingDelay = loadingDelay ?? <ILoadingDelays>{ id: 0, loadingInfoId: null, reasonForDelayId: null, duration: null, cargoId: null, quantity: null };
      const _loadingDelays = this.loadingDischargingManageSequenceTransformationService.getLoadingDelayAsValueObject(loadingDelay, true, !this.addInitialDelay, this.listData);
      dataTableControl.insert(0, this.initLoadingSequenceFormGroup(_loadingDelays, this.loadingDelays.length, true));
      this.loadingDelays = [_loadingDelays, ...this.loadingDelays];
    } else {
      this.loadingDelays.splice(0, 1);
      dataTableControl.removeAt(0);
    }
  }

  /**
* Method for delete row
*
* @private
* @param {event} event
* @memberof LoadingDischargingManageSequenceComponent
*/
  async onDelete(event) {
    const translationKeys = await this.translateService.get(['LOADING_MANAGE_SEQUENCE_DELETE_SUMMARY', 'LOADING_MANAGE_SEQUENCE_DELETE_CONFIRM_DETAILS', 'LOADING_MANAGE_SEQUENCE_DELETE_CONFIRM_LABEL', 'LOADING_MANAGE_SEQUENCE_DELETE_REJECT_LABEL']).toPromise();
    if (!event?.data?.isAdd) {
      this.confirmationService.confirm({
        key: 'confirmation-alert',
        header: translationKeys['LOADING_MANAGE_SEQUENCE_DELETE_SUMMARY'],
        message: translationKeys['LOADING_MANAGE_SEQUENCE_DELETE_CONFIRM_DETAILS'],
        icon: 'pi pi-exclamation-triangle',
        acceptLabel: translationKeys['LOADING_MANAGE_SEQUENCE_DELETE_CONFIRM_LABEL'],
        acceptIcon: 'pi',
        acceptButtonStyleClass: 'btn btn-main mr-5',
        rejectVisible: true,
        rejectLabel: translationKeys['LOADING_MANAGE_SEQUENCE_DELETE_REJECT_LABEL'],
        rejectIcon: 'pi',
        rejectButtonStyleClass: 'btn btn-main',
        accept: async () => {
          await this.removeFromLoadingDelay(event);
        },
      });
    }
    else {
      await this.removeFromLoadingDelay(event);
    }
  }

  /**
 * Method to remove loading delay from list
 *
 * @param {*} event
 * @memberof LoadingDischargingManageSequenceComponent
 */
  async removeFromLoadingDelay(event) {
    this.loadingDelays.splice(event.index, 1);
    this.loadingDelays = [...this.loadingDelays];
    const dataTableControl = <FormArray>this.loadingSequenceForm.get('dataTable');
    dataTableControl.removeAt(event?.index);
    if (!event.data.isAdd) {
      const loadingDelaysList = this.loadingDischargingManageSequenceTransformationService.getLoadingDelayAsValue(this.loadingDelays, this.loadingInfoId);
      this.updateLoadingDelays.emit(loadingDelaysList);
    }
    if (event.index === 0) {
      this.addInitialDelay = false;
    }
  }

}
