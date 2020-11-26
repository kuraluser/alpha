import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DATATABLE_EDITMODE, IDataTableColumn, IDataTableEvent } from '../../../../../shared/components/datatable/datatable.model';
import { ILoadingPort, ILoadingPortValueObject, ILoadingPopupData, IPort } from '../../../models/cargo-planning.model';
import { LoadableStudyDetailsTransformationService } from '../../../services/loadable-study-details-transformation.service';

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
    this.updatePorts(popupData);
    this.loadingPort = this.popupData.rowData.loadingPorts.value ? this.popupData.rowData.loadingPorts.value.map(port => this.loadableStudyDetailsTransformationService.getCargoNominationLoadingPortAsValueObject(port, false)) : [];
    const loadingPortArray = this.loadingPort.map(port => this.initLoadingPortFormGroup(port));
    this.loadingPortsFrom = this.fb.group({
      dataTable: this.fb.array([...loadingPortArray]),
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
    private loadableStudyDetailsTransformationService: LoadableStudyDetailsTransformationService) { }

  ngOnInit(): void {
    this.loadingPort = this.popupData.rowData.loadingPorts.value ? this.popupData.rowData.loadingPorts.value.map(port => this.loadableStudyDetailsTransformationService.getCargoNominationLoadingPortAsValueObject(port, false)) : [];
    const loadingPortArray = this.loadingPort.map(port => this.initLoadingPortFormGroup(port));
    this.loadingPortsFrom = this.fb.group({
      dataTable: this.fb.array([...loadingPortArray]),
      addPort: this.fb.control(null)
    });
    this.columns = this.loadableStudyDetailsTransformationService.getCargoNominationLoadingPortDatatableColumns();
  }

  /**
   * Method for closing loading ports popup
   *
   * @memberof LoadingPortsPopupComponent
   */
  closePopup() {
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
    if (this.popupData.rowData.loadingPorts.value) {
      this.popupData.rowData.loadingPorts.value.unshift(port)
    } else {
      this.popupData.rowData.loadingPorts.value = [port];
    }
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
    popupData.ports = this.ports.filter(port => !popupData.rowData.loadingPorts.value?.some(lport => lport.id === port.id));
  }

  /**
   * Event handler for filed edit complete evnt
   *
   * @param {IDataTableEvent} event
   * @memberof LoadingPortsPopupComponent
   */
  onEditComplete(event: IDataTableEvent) {
    this.popupData.rowData.loadingPorts.value[event.index][event.field] = event.data[event.field].value;
    this.updatePorts(this.popupData);
  }

  /**
   *
   *
   * @param {IDataTableEvent} event
   * @memberof LoadingPortsPopupComponent
   */
  onDeleteRow(event: IDataTableEvent) {
    this.popupData.rowData.loadingPorts.value.splice(event.index, 1);
    this.popupData = {...this.popupData};
  }

  /**
   * Method for save popup
   *
   * @param {IDataTableEvent} event
   * @memberof LoadingPortsPopupComponent
   */
  savePopup() {
    if (this.loadingPortsFrom.valid) {
      this.popupData.isUpdate = true;
      this.popupDataChange.emit(this.popupData);
      this.closePopup();
    } else {
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
      quantity: this.fb.control(loadingPort.quantity.value, [Validators.required])
    });
  }

}
