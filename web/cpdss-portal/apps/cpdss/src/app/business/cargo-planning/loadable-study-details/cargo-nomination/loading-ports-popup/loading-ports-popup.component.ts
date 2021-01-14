import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { MessageService } from 'primeng/api';
import { DATATABLE_EDITMODE, IDataTableColumn, IDataTableEvent } from '../../../../../shared/components/datatable/datatable.model';
import { numberValidator } from '../../../directives/validator/number-validator.directive';
import { ILoadingPort, ILoadingPortValueObject, ILoadingPopupData } from '../../../models/cargo-planning.model';
import { LoadableStudyDetailsTransformationService } from '../../../services/loadable-study-details-transformation.service';
import { ConfirmationAlertService } from '../../../../../shared/components/confirmation-alert/confirmation-alert.service';
import { first } from 'rxjs/operators';
import { IPort } from '../../../../../shared/models/common.model';

/**
 * Component class for loading ports popup
 *
 * @export
 * @class LoadingPortsPopupComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-loading-ports-popup',
  templateUrl: './loading-ports-popup.component.html',
  styleUrls: ['./loading-ports-popup.component.scss']
})
export class LoadingPortsPopupComponent implements OnInit {

  @Input()
  get visible(): boolean {
    return this._visible;
  }
  set visible(visible: boolean) {
    this._visible = visible;
  }

  @Input()
  get popupData(): ILoadingPopupData {
    return this._popupData;
  }
  set popupData(popupData: ILoadingPopupData) {
    this._popupData = popupData;
    this.ports = this.popupData.ports;
    this.loadingPort = popupData.rowData.loadingPorts.value ? [...popupData.rowData.loadingPorts.value].reverse().map(port => this.loadableStudyDetailsTransformationService.getCargoNominationLoadingPortAsValueObject(port, false)) : [];
    this.updatePorts(popupData);
    const loadingPortArray = this.loadingPort.map(port => this.initLoadingPortFormGroup(port));
    this.loadingPortsFrom = this.fb.group({
      dataTable: this.fb.array([...loadingPortArray], Validators.required),
      addPort: this.fb.control(null)
    });
  }

  @Output() visibleChange = new EventEmitter<boolean>();
  @Output() popupDataChange = new EventEmitter<ILoadingPopupData>();

  loadingPortsFrom: FormGroup;
  columns: IDataTableColumn[];
  loadingPort: ILoadingPortValueObject[];
  ports: IPort[];
  readonly editMode = DATATABLE_EDITMODE.CELL;

  private _popupData: ILoadingPopupData;
  private _visible: boolean;

  constructor(private fb: FormBuilder,
    private loadableStudyDetailsTransformationService: LoadableStudyDetailsTransformationService,
    private messageService: MessageService,
    private translateService: TranslateService,
    private confirmationAlertService: ConfirmationAlertService) { }

  ngOnInit(): void {
    this.columns = this.loadableStudyDetailsTransformationService.getCargoNominationLoadingPortDatatableColumns();
  }

  /**
   * Method for closing loading ports popup
   *
   * @memberof LoadingPortsPopupComponent
   */
  closePopup() {
    this.loadingPort = null;
    this.visible = false;
    this.visibleChange.emit(this.visible);
  }

  /**
   * Method for adding new port
   *
   * @param {ILoadingPort} port
   * @memberof LoadingPortsPopupComponent
   */
  addPort(port: ILoadingPort) {
    port.quantity = null;
    const loadingPort: ILoadingPortValueObject = this.loadableStudyDetailsTransformationService.getCargoNominationLoadingPortAsValueObject(port);
    this.loadingPort = [loadingPort, ...this.loadingPort];
    const dataTableControl = <FormArray>this.loadingPortsFrom.get('dataTable');
    dataTableControl.insert(0, this.initLoadingPortFormGroup(loadingPort));
    this.loadingPortsFrom.get('addPort').setValue(null);
    this.updatePorts(this.popupData);
  }

  /**
   * Update ports dropdown list
   *
   * @param {ILoadingPopupData} popupData
   * @memberof LoadingPortsPopupComponent
   */
  updatePorts(popupData: ILoadingPopupData) {
    popupData.ports = this.ports.filter(port => !this.loadingPort?.some(lport => lport.id === port.id));
  }

  /**
   * Event handler for filed edit complete evnt
   *
   * @param {IDataTableEvent} event
   * @memberof LoadingPortsPopupComponent
   */
  onEditComplete(event: IDataTableEvent) {
    this.updatePorts(this.popupData);
  }

  /**
   *
   *
   * @param {IDataTableEvent} event
   * @memberof LoadingPortsPopupComponent
   */
  onDeleteRow(event: IDataTableEvent) {
    
    this.confirmationAlertService.add({ key: 'confirmation-alert', sticky: true, severity: 'warn', summary: 'LOADED_PORT_DELETE_SUMMARY', detail: 'LOADED_PORT_DELETE_DETAILS', data: { confirmLabel: 'LOADED_PORT_DELETE_CONFIRM_LABEL', rejectLabel: 'LOADED_PORT_DELETE_REJECT_LABEL' } });
    this.confirmationAlertService.confirmAlert$.pipe(first()).subscribe(async (response) => {
      if (response) {
        this.loadingPort.splice(event.index, 1);
        const dataTableControl = <FormArray>this.loadingPortsFrom.get('dataTable');
        dataTableControl.removeAt(event.index);
        this.updatePorts(this.popupData);
      }
    });
  }

  /**
   * Method for save popup
   *
   * @param {IDataTableEvent} event
   * @memberof LoadingPortsPopupComponent
   */
  async savePopup() {
    if (this.loadingPortsFrom.valid) {
      this.popupData.isUpdate = true;
      this.popupData.rowData.loadingPorts.value = this.loadingPort?.reverse().map(port => this.loadableStudyDetailsTransformationService.getCargoNominationLoadingPortAsValue(port));
      this.popupDataChange.emit(this.popupData);
      this.closePopup();
    } else {
      if (this.loadingPortsFrom.controls.dataTable?.errors?.required) {
        const detail = await this.translateService.get('CARGO_NOMINATION_LOADING_PORT_REQUIRED_ERROR').toPromise();
        this.messageService.add({ severity: 'error', detail: detail });
      }

      this.loadingPortsFrom.markAllAsTouched();
      this.loadingPortsFrom.updateValueAndValidity();
    }

  }

  /**
   *
   * Method for initialiing loading ports form
   * @private
   * @param {ILoadingPortValueObject} loadingPort
   * @returns
   * @memberof LoadingPortsPopupComponent
   */
  private initLoadingPortFormGroup(loadingPort: ILoadingPortValueObject) {
    return this.fb.group({
      name: this.fb.control(loadingPort.name.value, Validators.required),
      quantity: this.fb.control(loadingPort.quantity.value, [Validators.required, Validators.min(1), numberValidator(2, 7, false)])
    });
  }

}
