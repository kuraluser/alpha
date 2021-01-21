import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { DATATABLE_EDITMODE, IDataTableColumn } from '../../../../shared/components/datatable/datatable.model';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { numberValidator } from '../../directives/validator/number-validator.directive';
import { ICargo, ICargoNomination } from '../../models/cargo-planning.model';
import { ICargoGroup, ICommingleCargo, ICommingleManual, ICommingleManualEvent, ICommingleResponseModel, ICommingleValueObject, IPercentage, IPurpose, IVesselCargoTank } from '../../models/commingle.model';
import { CommingleApiService } from '../../services/commingle-api.service';
import { LoadableStudyDetailsTransformationService } from '../../services/loadable-study-details-transformation.service';
import { IPermissionContext, PERMISSION_ACTION } from '../../../../shared/models/common.model';
import { AppConfigurationService } from '../../../../shared/services/app-configuration/app-configuration.service';
import { PermissionsService } from '../../../../shared/services/permissions/permissions.service';
import { IPermission } from '../../../../shared/models/user-profile.model';
import { CargoDuplicateValidator } from '../../directives/validator/cargo-duplicate-validator.directive';
import { commingleQuantityValidator } from '../../directives/validator/commingle-quantity-validator.directive';
import { PercentageValidator } from '../../directives/validator/percentage-validator.directive';
import { ConfirmationAlertService } from '../../../../shared/components/confirmation-alert/confirmation-alert.service';
import { first } from 'rxjs/operators';

/**
 * Component class of commingle pop up
 *
 * @export
 * @class CommingleComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-commingle',
  templateUrl: './commingle.component.html',
  styleUrls: ['./commingle.component.scss']
})
export class CommingleComponent implements OnInit {

  @Input() voyageId: number;
  @Input() loadableStudyId: number;
  @Input() vesselId: number;

  @Output() displayPopUp = new EventEmitter<boolean>();

  display = true;
  checked = false;
  preferredTankList: IVesselCargoTank[];
  isVolumeMaximum = true;
  selectedTank: any;
  percentage: IPercentage[];
  cargos: ICargo[];
  commingleData: ICommingleResponseModel;
  commingleForm: FormGroup;
  cargoNominationsCargo: ICargoNomination[];
  cargoNominationsCargo1: ICargoNomination[];
  cargoNominationsCargo2: ICargoNomination[];
  selectedCargo1: ICargoNomination;
  selectedCargo2: ICargoNomination;
  commingleCargo: ICommingleCargo;
  purposeOfCommingle: IPurpose[];
  columns: IDataTableColumn[];
  listData = <ICommingleManual>{};
  commingleManualForm: FormGroup;
  manualCommingleList: ICommingleValueObject[];
  readonly editMode = DATATABLE_EDITMODE.CELL;
  preferredTanks: number[];
  commingleList: ICargoGroup[];
  errorMesages: any;
  selectedTanks: IVesselCargoTank[];
  cargoNominationPermissionContext: IPermissionContext;
  permission: IPermission;
  isEditable = false;

  constructor(private commingleApiService: CommingleApiService,
    private ngxSpinnerService: NgxSpinnerService,
    private fb: FormBuilder,
    private messageService: MessageService,
    private translateService: TranslateService,
    private loadableStudyDetailsTransformationService: LoadableStudyDetailsTransformationService,
    private permissionsService: PermissionsService,
    private confirmationAlertService: ConfirmationAlertService) { }


  /**
 * Component lifecycle ngOnit
 *
 * @returns {Promise<void>}
 * @memberof CommingleComponent
 */
  ngOnInit() {
    this.cargoNominationPermissionContext = { key: AppConfigurationService.settings.permissionMapping['CargoNominationComponent'], actions: [PERMISSION_ACTION.EDIT, PERMISSION_ACTION.ADD] };
    this.permission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['CargoNominationComponent'], true);
    this.isEditable = this.permission ? this.permission?.edit : true;
    this.percentage = [{ id: 10, name: "10%" }, { id: 20, name: "20%" }, { id: 30, name: "30%" }, { id: 40, name: "40%" }, { id: 50, name: "50%" }, { id: 60, name: "60%" }, { id: 70, name: "70%" }, { id: 80, name: "80%" }, { id: 90, name: "90%" }, { id: 100, name: "100%" }];
    this.columns = this.loadableStudyDetailsTransformationService.getManualCommingleDatatableColumns();
    this.createVolumeMaximisationFormGroup();
    this.getCommingle();
    this.errorMesages = this.commingleApiService.setValidationErrorMessage();
  }

  /**
* Method for get commingle data
*
* @private
* @memberof CommingleComponent
*/
  async getCommingle() {
    this.ngxSpinnerService.show();
    const cargoResult = await this.commingleApiService.getCargos().toPromise();
    if (cargoResult.responseStatus.status === '200') {
      this.cargos = cargoResult.cargos;
    }
    this.commingleData = await this.commingleApiService.getCommingle(this.vesselId, this.voyageId, this.loadableStudyId).toPromise();
    if (cargoResult.responseStatus.status === '200' && this.commingleData.responseStatus.status === '200') {
      this.purposeOfCommingle = this.commingleData.purposes;
      this.commingleCargo = this.commingleData.commingleCargo;
      this.preferredTankList = this.commingleData.vesselTanks;
      this.commingleForm.patchValue({
        purpose: this.purposeOfCommingle[0]
      });
      this.cargoNominationsCargo = this.commingleData.cargoNominations.map(itm => ({
        ...this.cargos.find((item) => (item.id === itm.cargoId) && item),
        ...itm
      }));
      this.cargoNominationsCargo1 = this.cargoNominationsCargo;
      this.cargoNominationsCargo2 = this.cargoNominationsCargo;
      this.listData.cargoNominationsCargo1 = this.cargoNominationsCargo1;
      this.listData.cargoNominationsCargo2 = this.cargoNominationsCargo2;
      this.listData.percentage = this.percentage;
      if (this.commingleCargo) {
        this.isVolumeMaximum = this.commingleCargo.purposeId === 1 ? true : false;
        this.selectedCargo1 = this.cargoNominationsCargo.find(cargo => cargo.cargoId === this.commingleCargo.cargoGroups[0].cargo1Id);
        this.selectedCargo2 = this.cargoNominationsCargo.find(cargo => cargo.cargoId === this.commingleCargo.cargoGroups[0].cargo2Id);
        if (this.selectedCargo1) {
          this.cargoNominationsCargo2 = this.cargoNominationsCargo.filter(cargos => cargos.cargoId !== this.selectedCargo1.id);
        }
        if (this.selectedCargo2) {
          this.cargoNominationsCargo1 = this.cargoNominationsCargo.filter(cargos => cargos.cargoId !== this.selectedCargo2.id);
        }
        this.listData.cargoNominationsCargo1 = this.cargoNominationsCargo.filter(cargos => (this.commingleCargo.cargoGroups.some(group => group.cargo2Id === cargos.cargoId)) !== true);
        this.listData.cargoNominationsCargo2 = this.cargoNominationsCargo2.filter(cargos => (this.commingleCargo.cargoGroups.some(group => group.cargo1Id === cargos.cargoId)) !== true);
        this.selectPurpose(null);
      }
      const cargoGroups = this.commingleCargo?.purposeId === 2 ? this.commingleData?.commingleCargo?.cargoGroups ?? [] : [];
      this.initCommingleManualArray(cargoGroups);
      this.preferredTankList = this.commingleData.vesselTanks;
    }
    this.ngxSpinnerService.hide();
  }

  /**
  * Method for creating form-group for volume maximisation
  *
  * @returns
  * @memberof CommingleComponent
  */
  async createVolumeMaximisationFormGroup() {
    this.commingleForm = this.fb.group({
      purpose: this.fb.control(null, Validators.required),
      preferredTanks: this.fb.control(null),
      slopOnly: this.fb.control(false, [Validators.required]),
      cargo1: this.fb.control(null, [Validators.required]),
      cargo2: this.fb.control(null, [Validators.required]),
    });
  }

  /**
   * Close commingle popup
   */
  close() {
    this.displayPopUp.emit(false);
  }

  /**
   * select purpose of commingle
   */
  selectPurpose(event) {
    if (event) {
      event?.value?.id === 1 ? this.isVolumeMaximum = true : this.isVolumeMaximum = false;
    }
    this.updateCommingleFormValue();
  }


  /**
   * Triggering cargo option change
   */
  onChange(event, cargo) {
    if (cargo === 1) {
      this.cargoNominationsCargo2 = this.cargoNominationsCargo.filter(cargos => cargos.cargoId !== event.value.cargoId);
      this.selectedCargo1 = event.value;
    } else {
      this.cargoNominationsCargo1 = this.cargoNominationsCargo.filter(cargos => cargos.cargoId !== event.value.cargoId);
      this.selectedCargo2 = event.value;
    }
  }

  /**
 * Save volume maxmisation 
 */
  async saveVolumeMaximisation() {
    if (this.commingleForm.valid) {
      this.ngxSpinnerService.show();
      const translationKeys = await this.translateService.get(['COMMINGLE_VOL_MAX_SAVE_SUCCESS', 'COMMINGLE_COMPLETED_SUCCESSFULLY']).toPromise();
      const data = {
        purposeId: this.commingleForm.value.purpose.id,
        slopOnly: this.commingleForm.value.slopOnly,
        cargoGroups: [{
          id: 0,
          cargo1Id: this.commingleForm.value.cargo1.cargoId,
          cargo2Id: this.commingleForm.value.cargo2.cargoId
        }]
      }
      const result = await this.commingleApiService.saveVolMaxCommingle(this.vesselId, this.voyageId, this.loadableStudyId, data).toPromise();
      if (result.responseStatus.status === '200') {
        this.messageService.add({ severity: 'success', summary: translationKeys['COMMINGLE_VOL_MAX_SAVE_SUCCESS'], detail: translationKeys['COMMINGLE_COMPLETED_SUCCESSFULLY'] });
        this.close();
      }
      this.ngxSpinnerService.hide();
    } else {
      this.commingleForm.markAllAsTouched();
    }
  }

  // returns form-controls of commingleForm
  get form() { return this.commingleForm.controls; }

  /**
   * Event handler for edit complete event
   * @param event 
   */
  async onEditComplete(event: ICommingleManualEvent) {
    const form = this.row(event.index);
    if (event.field === 'cargo1') {
      this.listData.cargoNominationsCargo2 = this.listData.cargoNominationsCargo2.filter(cargos => cargos.cargoId !== event.data.cargo1.value.cargoId);
      this.manualCommingleList[event.index]['cargo1Color'].value = event?.data?.cargo1?.value?.color
      this.updateField(event.index, 'cargo1Color', event?.data?.cargo1?.value?.color);
      form.controls.quantity.updateValueAndValidity();
    }
    if (event.field === 'cargo2') {
      this.listData.cargoNominationsCargo1 = this.listData.cargoNominationsCargo1.filter(cargos => cargos.cargoId !== event.data.cargo2.value.cargoId);
      this.manualCommingleList[event.index]['cargo2Color'].value = event?.data?.cargo2?.value?.color
      this.updateField(event.index, 'cargo2Color', event?.data?.cargo2?.value?.color);
      form.controls.quantity.updateValueAndValidity();
    }
    if (event.field === 'cargo1IdPct') {
      this.manualCommingleList[event.index]['cargo1pct'].value = event.data.cargo1IdPct.value.id;
      this.updateField(event.index, 'cargo1pct', event.data.cargo1IdPct.value.id);
      form.controls.cargo1IdPct.updateValueAndValidity();
      form.controls.cargo2IdPct.updateValueAndValidity();
    }
    if (event.field === 'cargo2IdPct') {
      this.manualCommingleList[event.index]['cargo2pct'].value = event.data.cargo2IdPct.value.id;
      this.updateField(event.index, 'cargo2pct', event.data.cargo2IdPct.value.id);
      form.controls.cargo1IdPct.updateValueAndValidity();
      form.controls.cargo2IdPct.updateValueAndValidity();
    }
  }

  /**
* Method for updating form field
*
* @private
* @param {number} index
* @param {string} field
* @param {*} value
* @memberof CommingleComponent
*/
  private updateField(index: number, field: string, value: any) {
    const control = this.field(field, index);
    control.setValue(value);
    control.markAsDirty();
    control.markAsTouched();
  }

  /**
   * Method for initializing manual commingle row
   * @param commingle 
   */
  private initCommingleManualFormGroup(commingle: ICommingleValueObject) {
    return this.fb.group({
      cargo1: this.fb.control(commingle?.cargo1?.value, [Validators.required, CargoDuplicateValidator('cargo1', 'cargo2')]),
      cargo2: this.fb.control(commingle?.cargo2?.value, [Validators.required, CargoDuplicateValidator('cargo2', 'cargo1')]),
      cargo1pct: this.fb.control(commingle?.cargo1IdPct?.value?.id, [Validators.required]),
      cargo2pct: this.fb.control(commingle?.cargo2IdPct?.value?.id, [Validators.required]),
      cargo1IdPct: this.fb.control(commingle?.cargo1IdPct?.value, [Validators.required, PercentageValidator('cargo2IdPct')]),
      cargo2IdPct: this.fb.control(commingle?.cargo2IdPct?.value, [Validators.required, PercentageValidator('cargo1IdPct')]),
      quantity: this.fb.control(commingle?.quantity?.value, [Validators.required, numberValidator(2, 7), Validators.min(1), commingleQuantityValidator()]),

    });
  }

  /**
   * Method for updating manual commingle form
   * 
   */
  private async initCommingleManualArray(commingleData: ICargoGroup[]) {
    this.ngxSpinnerService.show();
    const _commingleLists = commingleData?.map((item) => {
      const manualData = this.loadableStudyDetailsTransformationService.getCommingleValueObject(item, false, this.isEditable, this.listData);
      return manualData;
    });
    const commingleListArray = _commingleLists.map(commingle => this.initCommingleManualFormGroup(commingle));
    this.commingleManualForm = this.fb.group({
      dataTable: this.fb.array([...commingleListArray])
    });
    this.manualCommingleList = _commingleLists;
    this.ngxSpinnerService.hide();
  }


  /**
 * Method for adding new row for manual commingle
 *
 * @private
 * @memberof CommingleComponent
 */
  private addNew(commingle: ICargoGroup = null) {
    if (this.manualCommingleList.length <= 2) {
      commingle = commingle ?? <ICargoGroup>{
        id: 0,
        cargo1Id: null,
        cargo2Id: null,
        quantity: null,
        cargo1Pct: null,
        cargo2Pct: null
      };
      const _commingle = this.loadableStudyDetailsTransformationService.getCommingleValueObject(commingle, true, this.isEditable, this.listData);
      this.manualCommingleList = [_commingle, ...this.manualCommingleList];
      const dataTableControl = <FormArray>this.commingleManualForm.get('dataTable');
      dataTableControl.insert(0, this.initCommingleManualFormGroup(_commingle));
    }

  }

  /**
   * Delete row
   * 
   */
  onDeleteRow(event: ICommingleManualEvent) {
    this.confirmationAlertService.add({ key: 'confirmation-alert', sticky: true, severity: 'warn', summary: 'COMMINGLE_CARGO_DELETE_SUMMARY', detail: 'COMMINGLE_CARGO_DELETE_DETAILS', data: { confirmLabel: 'COMMINGLE_CARGO_DELETE_CONFIRM_LABEL', rejectLabel: 'COMMINGLE_CARGO_DELETE_REJECT_LABEL' } });
    this.confirmationAlertService.confirmAlert$.pipe(first()).subscribe(async (response) => {
      if (response) {
        if (event?.data?.isDelete) {
          this.manualCommingleList.splice(event.index, 1);
          this.manualCommingleList = [...this.manualCommingleList];
          (<FormArray>this.commingleManualForm.get('dataTable')).removeAt(event.index);
        }
      }
    });
  }
  /**
   * Checking case for saving Commingle
   */
  saveCommingle() {
    this.isVolumeMaximum ? this.saveVolumeMaximisation() : this.saveManuals();
  }

  /**
   * Save maual commingle
   */
  async saveManuals() {
    this.commingleForm.markAllAsTouched();
    if (this.commingleForm.valid && this.commingleManualForm.valid && this.manualCommingleList?.length) {
      this.ngxSpinnerService.show();
      const translationKeys = await this.translateService.get(['COMMINGLE_MANUAL_SAVE_SUCCESS', 'COMMINGLE_COMPLETED_SUCCESSFULLY']).toPromise();
      const _commingleList = Array<ICargoGroup>();
      if (this.commingleManualForm.value.dataTable.length > 0) {
        for (let i = 0; i < this.manualCommingleList.length; i++) {
          _commingleList[i] = this.loadableStudyDetailsTransformationService.getCommingleAsValue(this.manualCommingleList[i]);
        }
      }
      const _preferred = Array<number[]>();
      if (this.commingleForm.value?.preferredTanks?.length > 0) {
        for (let i = 0; i < this.commingleForm.value.preferredTanks.length; i++) {
          _preferred[i] = this.commingleForm.value.preferredTanks[i].id;

        }
      }
      const data = {
        purposeId: this.commingleForm.value.purpose.id,
        slopOnly: false,
        preferredTanks: _preferred,
        cargoGroups: _commingleList

      }
      const result = await this.commingleApiService.saveVolMaxCommingle(this.vesselId, this.voyageId, this.loadableStudyId, data).toPromise();
      if (result.responseStatus.status === '200') {
        this.messageService.add({ severity: 'success', summary: translationKeys['COMMINGLE_MANUAL_SAVE_SUCCESS'], detail: translationKeys['COMMINGLE_COMPLETED_SUCCESSFULLY'] });
        this.close();
      }
      this.ngxSpinnerService.hide();
    }
    else {
      this.commingleForm.markAllAsTouched();
      this.commingleManualForm.markAllAsTouched();
    }

  }

  /**
* Method for fetching form group
*
* @private
* @param {number} formGroupIndex
* @returns {FormGroup}
* @memberof Commingle component
*/
  private row(formGroupIndex: number): FormGroup {
    const formGroup = <FormGroup>(<FormArray>this.commingleManualForm.get('dataTable')).at(formGroupIndex);
    return formGroup;
  }

  /**
   * Method for updating form field
   */
  updateCommingleFormValue() {
    if (this.isVolumeMaximum) {
      if (this.commingleCargo.purposeId === 1) {
        this.commingleForm.controls['preferredTanks'].clearValidators();
        this.commingleForm.controls['preferredTanks'].updateValueAndValidity();
        this.commingleForm.patchValue({
          purpose: this.purposeOfCommingle.find(purpose => purpose.id === 1),
          slopOnly: this.commingleCargo.slopOnly,
          cargo1: this.selectedCargo1,
          cargo2: this.selectedCargo2
        });
      }
      else {
        this.selectedCargo1 = null;
        this.selectedCargo2 = null;
        this.commingleForm.controls['preferredTanks'].clearValidators();
        this.commingleForm.controls['preferredTanks'].updateValueAndValidity();
        this.commingleForm.patchValue({
          purpose: this.purposeOfCommingle.find(purpose => purpose.id === 1),
          slopOnly: this.commingleCargo.slopOnly,
          cargo1: '',
          cargo2: ''
        });

      }
      this.commingleForm.controls['cargo1'].setValidators([Validators.required]);
      this.commingleForm.controls['cargo2'].setValidators([Validators.required]);
      this.commingleForm.controls['cargo1'].updateValueAndValidity();
      this.commingleForm.controls['cargo2'].updateValueAndValidity();
    }
    else {
      this.commingleForm.controls['preferredTanks'].setValidators([Validators.required]);
      this.commingleForm.controls['cargo1'].clearValidators();
      this.commingleForm.controls['cargo2'].clearValidators();
      this.commingleForm.controls['preferredTanks'].updateValueAndValidity();
      this.commingleForm.controls['cargo1'].updateValueAndValidity();
      this.commingleForm.controls['cargo2'].updateValueAndValidity();
      this.selectedTanks = this.commingleCargo.preferredTanks.map(preferredTank => ({
        ...this.preferredTankList.find((item) => (item.id === Number(preferredTank)) && item),
        ...preferredTank
      }));
      this.commingleForm.get('preferredTanks').setValue(this.commingleCargo.preferredTanks);
      this.commingleForm.patchValue({
        purpose: this.purposeOfCommingle.find(purpose => purpose.id === 2),
        preferredTanks: this.selectedTanks
      })


    }
  }


  /**
   * Get field errors
   *
   *
   * @param {string} formControlName
   * @returns {ValidationErrors}
   * @memberof CommingleComponent
   */
  fieldError(formControlName: string): ValidationErrors {
    const formControl = this.field(formControlName);
    return formControl.invalid && (formControl.dirty || formControl.touched) ? formControl.errors : null;
  }

  /**
* Get form control of loadableQuantityForm 
*
*
* @param {string} formControlName
* @returns {FormControl}
* @memberof CommingleComponent
*/
  field(formControlName: string, formGroupIndex = -1): FormControl {
    if (formGroupIndex !== -1) {
      const formControl = <FormControl>(<FormArray>this.commingleManualForm.get('dataTable')).at(formGroupIndex).get(formControlName);
      return formControl;
    } else {
      const formControl = <FormControl>this.commingleForm.get(formControlName);
      return formControl;
    }

  }

  /**
   * reset cargo
   * @memberof CommingleComponent
   */
  clearCargo() {
    this.confirmationAlertService.add({ key: 'confirmation-alert', sticky: true, severity: 'warn', summary: 'COMMINGLE_CARGO_DELETE_SUMMARY', detail: 'COMMINGLE_CARGO_DELETE_DETAILS', data: { confirmLabel: 'COMMINGLE_CARGO_DELETE_CONFIRM_LABEL', rejectLabel: 'COMMINGLE_CARGO_DELETE_REJECT_LABEL' } });
    this.confirmationAlertService.confirmAlert$.pipe(first()).subscribe(async (response) => {
      if (response) {
        this.commingleForm.controls['cargo1'].setValue(null);
        this.commingleForm.controls['cargo2'].setValue(null);
      }
    });
  }

}
