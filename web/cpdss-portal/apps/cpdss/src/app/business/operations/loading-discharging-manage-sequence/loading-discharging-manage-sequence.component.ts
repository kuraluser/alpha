import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DATATABLE_EDITMODE, IDataTableColumn } from '../../../shared/components/datatable/datatable.model';
import { ICargo, ICargoResponseModel } from '../../../shared/models/common.model';
import { ILoadableQuantityCargo } from '../../core/models/common.model';
import { ILoadingDelays, ILoadingSequences } from '../models/loading-information.model';
import { LoadingDischargingManageSequenceTransformationService } from './services/loading-discharging-manage-sequence-transformation.service';
import { ILoadingSequenceListData, ILoadingSequenceValueObject } from './models/loading-discharging-manage-sequence.model';

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
  @Input() loadingSequences: ILoadingSequences;
  @Input() loadingInfoId: number;

  @Output() updateLoadingDelays: EventEmitter<ILoadingDelays[]> = new EventEmitter();

  loadingSequenceForm: FormGroup;
  columns: IDataTableColumn[];
  listData = <ILoadingSequenceListData>{};
  cargoTobeLoaded: ILoadableQuantityCargo[];
  loadingDelays: ILoadingSequenceValueObject[] = [];
  editMode: DATATABLE_EDITMODE = DATATABLE_EDITMODE.CELL;
  loadingDelayList: ILoadingDelays[];
  constructor(
    private fb: FormBuilder,
    private loadingDischargingManageSequenceTransformationService: LoadingDischargingManageSequenceTransformationService) { }

  async ngOnInit(): Promise<void> {
    this.listData = await this.getDropdownData();
    this.listData.reasonForDelays = this.loadingSequences.reasonForDelays;
    this.columns = this.loadingDischargingManageSequenceTransformationService.getDatatableColumns();
    this.initiLoadingSequenceArray();
  }

  /**
* Method for init loading sequence array
*
* @memberof LoadingDischargingManageSequenceComponent
*/
  initiLoadingSequenceArray() {
    const _loadingDelays = this.loadingSequences.loadingDelays?.map((loadingDelay) => {
      const loadingSequenceData = this.loadingDischargingManageSequenceTransformationService.getLoadingDelayAsValueObject(loadingDelay, false, true, this.listData);
      return loadingSequenceData;
    });
    const loadingDelayArray = _loadingDelays.map((loadingDelay, index) =>
      this.initLoadingSequenceFormGroup(loadingDelay, index)
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
      if (cargo.id === loadableQuantityCargoDetails.cargoId) {
        cargoDetail = cargo;
      }
    })
    return cargoDetail.name;
  }

  /**
  * Method for initializing loading sequence row
  *
  * @private
  * @param {ILoadingSequenceValueObject} loadingDelay
  * @returns
  * @memberof LoadingDischargingManageSequenceComponent
  */
  initLoadingSequenceFormGroup(loadingDelay: ILoadingSequenceValueObject, index: number) {
    return this.fb.group({
      reasonForDelay: this.fb.control(loadingDelay.reasonForDelay, [Validators.required]),
      duration: this.fb.control(loadingDelay.duration.value, [Validators.required]),
      cargo: this.fb.control(loadingDelay.cargo, [Validators.required]),
      quantity: this.fb.control(loadingDelay.quantity.value, [Validators.required])
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
    dataTableControl.push(this.initLoadingSequenceFormGroup(_loadingDelays, this.loadingDelays.length));
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
    if (form.valid) {
      const loadingDelaysList = this.loadingDischargingManageSequenceTransformationService.getLoadingDelayAsValue(this.loadingDelays, this.loadingInfoId)
      this.updateLoadingDelays.emit(loadingDelaysList);
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


}
