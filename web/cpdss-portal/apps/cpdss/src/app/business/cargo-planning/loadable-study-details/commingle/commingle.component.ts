import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { DATATABLE_EDITMODE, IDataTableColumn } from '../../../../shared/components/datatable/datatable.model';
import { NgxSpinnerService } from 'ngx-spinner';
import { ConfirmationService, MessageService } from 'primeng/api';
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
import { LoadableStudyDetailsApiService } from '../../services/loadable-study-details-api.service';
import { LoadableStudy } from '../../models/loadable-study-list.model';
import { LOADABLE_STUDY_STATUS, Voyage, VOYAGE_STATUS } from '../../../core/models/common.model';
import { QuantityDecimalService } from '../../../../shared/services/quantity-decimal/quantity-decimal.service'
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
  @Input() voyage: Voyage;
  @Input() loadableStudyId: number;
  @Input() vesselId: number;

  @Input()
  get loadableStudy() {
    return this._loadableStudy;
  }
  set loadableStudy(value: LoadableStudy) {
    this._loadableStudy = value;
    this.editMode = (this.permission?.edit === undefined || this.permission?.edit || this.permission?.add === undefined || this.permission?.add) && [LOADABLE_STUDY_STATUS.PLAN_PENDING, LOADABLE_STUDY_STATUS.PLAN_NO_SOLUTION, LOADABLE_STUDY_STATUS.PLAN_ERROR].includes(this.loadableStudy?.statusId) && ![VOYAGE_STATUS.CLOSE].includes(this.voyage?.statusId) ? DATATABLE_EDITMODE.CELL : null;
  }

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
  editMode: DATATABLE_EDITMODE;
  preferredTanks: number[];
  commingleList: ICargoGroup[];
  errorMesages: any;
  selectedTanks: IVesselCargoTank[];
  cargoNominationPermissionContext: IPermissionContext;
  permission: IPermission;
  isMaxCargo = false;
  isMaxPreferredTank = false;
  extraPreferred = 0;
  disableAddNewBtn = false;

  private _loadableStudy: LoadableStudy;

  constructor(private commingleApiService: CommingleApiService,
    private ngxSpinnerService: NgxSpinnerService,
    private fb: FormBuilder,
    private messageService: MessageService,
    private translateService: TranslateService,
    private loadableStudyDetailsTransformationService: LoadableStudyDetailsTransformationService,
    private permissionsService: PermissionsService,
    private confirmationService: ConfirmationService,
    private loadableStudyDetailsApiService: LoadableStudyDetailsApiService,
    private quantityDecimalService: QuantityDecimalService
  ) { }


  /**
 * Component lifecycle ngOnit
 *
 * @returns {Promise<void>}
 * @memberof CommingleComponent
 */
  ngOnInit() {
    this.cargoNominationPermissionContext = { key: AppConfigurationService.settings.permissionMapping['CargoCommingle'], actions: [PERMISSION_ACTION.VIEW, PERMISSION_ACTION.ADD, PERMISSION_ACTION.EDIT] };
    this.permission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['CargoCommingle'], true);
    this.percentage = [{ id: 10, name: "10%" }, { id: 20, name: "20%" }, { id: 30, name: "30%" }, { id: 40, name: "40%" }, { id: 50, name: "50%" }, { id: 60, name: "60%" }, { id: 70, name: "70%" }, { id: 80, name: "80%" }, { id: 90, name: "90%" }, { id: 100, name: "100%" }];
    this.columns = this.loadableStudyDetailsTransformationService.getManualCommingleDatatableColumns(this.permission, this.loadableStudy?.statusId, this.voyage?.statusId);
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
      this.cargoNominationsCargo = this.commingleData.cargoNominations?.map((itm, index) => {
        const cargoDetails = this.loadableStudyDetailsApiService.cargoNominations.find(cargo => cargo.id === itm.id)
        itm.loadingPorts = cargoDetails?.loadingPorts.value
        return {
          ...this.cargos.find((item) => (item.id === itm.cargoId) && item),
          ...itm
        }
      });


      let cargoGroupsTemp = this.commingleCargo?.cargoGroups?.filter((item) => {
        let cargoIds = this.cargoNominationsCargo?.map(cargoNominationCargo => cargoNominationCargo.cargoId);
        if (cargoIds.includes(item.cargo1Id) && cargoIds?.includes(item.cargo2Id)) {
          return item;
        }
      })

      if (this.commingleCargo) {
        this.commingleCargo.cargoGroups = cargoGroupsTemp;
      }
      this.cargoNominationsCargo1 = this.cargoNominationsCargo;
      this.cargoNominationsCargo2 = this.cargoNominationsCargo;
      this.listData.cargoNominationsCargo1 = this.cargoNominationsCargo;
      this.listData.cargoNominationsCargo2 = this.cargoNominationsCargo;
      this.listData.percentage = this.percentage;
      if (this.commingleCargo && this.commingleCargo.cargoGroups.length) {
        this.isVolumeMaximum = this.commingleCargo.purposeId === 1 ? true : false;
        this.selectedCargo1 = this.cargoNominationsCargo.find(cargo => cargo.id === this.commingleCargo.cargoGroups[0].cargoNomination1Id);
        this.selectedCargo2 = this.cargoNominationsCargo.find(cargo => cargo.id === this.commingleCargo.cargoGroups[0].cargoNomination2Id);
        if (this.isVolumeMaximum) {
          if (this.selectedCargo1) {
            this.cargoNominationsCargo2 = this.cargoNominationsCargo.filter(cargos => cargos.id !== this.selectedCargo1.id);
          }
          if (this.selectedCargo2) {
            this.cargoNominationsCargo1 = this.cargoNominationsCargo.filter(cargos => cargos.id !== this.selectedCargo2.id);
          }
        } else {
          this.listData.cargoNominationsCargo1 = this.cargoNominationsCargo.filter(cargos => (this.commingleCargo.cargoGroups.some(group => group.cargoNomination2Id === cargos.id)) !== true);
          this.listData.cargoNominationsCargo2 = this.cargoNominationsCargo.filter(cargos => (this.commingleCargo.cargoGroups.some(group => group.cargoNomination1Id === cargos.id)) !== true);
        }
        this.selectPurpose(null);
      }
      const cargoGroups = this.commingleCargo?.purposeId === 2 ? this.commingleData?.commingleCargo?.cargoGroups ?? [] : [];
      this.initCommingleManualArray(cargoGroups);
      this.preferredTankList = this.commingleData.vesselTanks;
      this.disableAddNewBtn = (this.cargoNominationsCargo.length <= 2 && this.manualCommingleList && this.manualCommingleList?.length >= 1) ? true : false;
      if (this.manualCommingleList && this.manualCommingleList?.length) {
        this.commingleForm.controls['preferredTanks'].setValidators([Validators.required]),
          this.commingleForm.controls['preferredTanks'].updateValueAndValidity()
      }
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
      slopOnly: this.fb.control({ value: false, disabled: !this.editMode }, [Validators.required]),
      cargo1: this.fb.control(null, []),
      cargo2: this.fb.control(null, []),
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
      this.cargoNominationsCargo2 = this.cargoNominationsCargo.filter(cargos => cargos.id !== event.value.id);
      this.selectedCargo1 = event.value;
      this.selectedCargo2 = this.selectedCargo1?.id === this.selectedCargo2?.id ? null : this.selectedCargo2;
    } else {
      this.cargoNominationsCargo1 = this.cargoNominationsCargo.filter(cargos => cargos.id !== event.value.id);
      this.selectedCargo2 = event.value;
      this.selectedCargo1 = this.selectedCargo1?.id === this.selectedCargo2?.id ? null : this.selectedCargo1;
    }
    this.commingleForm.patchValue({
      cargo1: this.selectedCargo1,
      cargo2: this.selectedCargo2
    });
    this.commingleForm.controls['cargo1'].setValidators([Validators.required]);
    this.commingleForm.controls['cargo2'].setValidators([Validators.required]);
    this.commingleForm.controls['cargo1'].updateValueAndValidity();
    this.commingleForm.controls['cargo2'].updateValueAndValidity();
  }

  /**
 * Save volume maxmisation
 */
  async saveVolumeMaximisation() {
    if (this.commingleForm.valid) {
      this.ngxSpinnerService.show();
      const translationKeys = await this.translateService.get(['COMMINGLE_SAVE_ERROR', 'COMMINGLE_SAVE_STATUS_ERROR', 'COMMINGLE_MANUAL_SAVE_WARNING', 'NO_COMMINGLE_DATA_SAVED', 'COMMINGLE_VOL_MAX_SAVE_SUCCESS', 'COMMINGLE_COMPLETED_SUCCESSFULLY']).toPromise();
      const data = {
        purposeId: this.commingleForm.value.purpose.id,
        slopOnly: this.commingleForm.value.slopOnly,
        cargoGroups: [{
          id: 0,
          cargo1Id: this.commingleForm.value.cargo1 ? this.commingleForm.value.cargo1.cargoId : '',
          cargo2Id: this.commingleForm.value.cargo2 ? this.commingleForm.value.cargo2.cargoId : '',
          cargoNomination1Id: this.commingleForm.value.cargo1 ? this.commingleForm.value.cargo1.id : '',
          cargoNomination2Id: this.commingleForm.value.cargo2 ? this.commingleForm.value.cargo2.id : ''
        }]
      }
      try {

          const result = await this.commingleApiService.saveVolMaxCommingle(this.vesselId, this.voyageId, this.loadableStudyId, data).toPromise();
          if (result.responseStatus.status === '200') {
            if (this.commingleForm.value.cargo1 && this.commingleForm.value.cargo2) {
              this.messageService.add({ severity: 'success', summary: translationKeys['COMMINGLE_VOL_MAX_SAVE_SUCCESS'], detail: translationKeys['COMMINGLE_COMPLETED_SUCCESSFULLY'] });
            } else {
              this.messageService.add({ severity: 'warn', summary: translationKeys['COMMINGLE_MANUAL_SAVE_WARNING'], detail: translationKeys['NO_COMMINGLE_DATA_SAVED'] });
            }
            this.close();
          }


      } catch (errorResponse) {
        if (errorResponse?.error?.errorCode === 'ERR-RICO-110') {
          this.messageService.add({ severity: 'error', summary: translationKeys['COMMINGLE_SAVE_ERROR'], detail: translationKeys['COMMINGLE_SAVE_STATUS_ERROR'], life: 10000 });
        }
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
      this.listData.cargoNominationsCargo2 = this.listData.cargoNominationsCargo2.filter(cargos => cargos.id !== event.data.cargo1.value.id);
      this.manualCommingleList[event.index]['cargo1Color'].value = event?.data?.cargo1?.value?.color;
      // this.updateField(event.index, 'cargo1Color', event?.data?.cargo1?.value?.color);
      form.controls.quantity.updateValueAndValidity();
    }
    if (event.field === 'cargo2') {
      this.listData.cargoNominationsCargo1 = this.listData.cargoNominationsCargo1.filter(cargos => cargos.id !== event.data.cargo2.value.id);
      this.manualCommingleList[event.index]['cargo2Color'].value = event?.data?.cargo2?.value?.color;
      // this.updateField(event.index, 'cargo2Color', event?.data?.cargo2?.value?.color);
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
    const quantityDecimal = this.quantityDecimalService.quantityDecimal();
    const min = quantityDecimal ? (1/Math.pow(10, quantityDecimal)) : 1;
    return this.fb.group({
      cargo1: this.fb.control(commingle?.cargo1?.value, [Validators.required, CargoDuplicateValidator('cargo1', 'cargo2')]),
      cargo2: this.fb.control(commingle?.cargo2?.value, [Validators.required, CargoDuplicateValidator('cargo2', 'cargo1')]),
      cargo1pct: this.fb.control(commingle?.cargo1IdPct?.value?.id, [Validators.required]),
      cargo2pct: this.fb.control(commingle?.cargo2IdPct?.value?.id, [Validators.required]),
      cargo1IdPct: this.fb.control(commingle?.cargo1IdPct?.value, [Validators.required, PercentageValidator('cargo2IdPct')]),
      cargo2IdPct: this.fb.control(commingle?.cargo2IdPct?.value, [Validators.required, PercentageValidator('cargo1IdPct')]),
      quantity: this.fb.control(commingle?.quantity?.value, [Validators.required, numberValidator(quantityDecimal, 7), Validators.min(min), commingleQuantityValidator()]),

    });
  }

  /**
   * Method for updating manual commingle form
   *
   */
  private async initCommingleManualArray(commingleData: ICargoGroup[]) {
    this.ngxSpinnerService.show();
    this.listData.cargoNominationsCargo1 = this.cargoNominationsCargo;
    this.listData.cargoNominationsCargo2 = this.cargoNominationsCargo;
    const _commingleLists = commingleData?.map((item) => {
      let manualData = this.loadableStudyDetailsTransformationService.getCommingleValueObject(item, false, this.editMode ? true : false, this.listData);
      manualData = this.convertUnit(manualData)
      return manualData;
    });
    let cargo1Total = 0;
    let cargo2Total = 0;
    const commingleListArray = _commingleLists.map((commingle, index) => {
      commingle?.cargo1?.value?.loadingPorts?.forEach((loadingPort) => {
        cargo1Total += loadingPort.quantity;
      })
      commingle?.cargo2?.value?.loadingPorts?.forEach((loadingPort) => {
        cargo2Total += loadingPort.quantity;
      })
      const loadingPortsTotal = cargo1Total + cargo2Total;
      if (loadingPortsTotal < commingle.quantity.value) {
        commingle.quantity.isEditMode = true;
      }
      return this.initCommingleManualFormGroup(commingle)
    });

    this.commingleManualForm = this.fb.group({
      dataTable: this.fb.array([...commingleListArray])
    });
    this.manualCommingleList = _commingleLists;
    this.ngxSpinnerService.hide();
  }


  /**
 * Method for adding new row for manual commingle
 *
 * @memberof CommingleComponent
 */
  addNew(commingle: ICargoGroup = null) {
    this.disableAddNewBtn = this.cargoNominationsCargo.length <= 2 ? true : false;
    if (this.manualCommingleList?.length <= 2) {
      this.listData.cargoNominationsCargo1 = this.cargoNominationsCargo;
      this.listData.cargoNominationsCargo2 = this.cargoNominationsCargo;
      commingle = commingle ?? <ICargoGroup>{
        id: 0,
        cargo1Id: null,
        cargo2Id: null,
        quantity: null,
        cargo1Pct: null,
        cargo2Pct: null
      };
      const _commingle = this.loadableStudyDetailsTransformationService.getCommingleValueObject(commingle, true, this.editMode ? true : false, this.listData);
      this.manualCommingleList = [_commingle, ...this.manualCommingleList];
      const dataTableControl = <FormArray>this.commingleManualForm.get('dataTable');
      dataTableControl.insert(0, this.initCommingleManualFormGroup(_commingle));
      this.commingleForm.controls['preferredTanks'].setValidators([Validators.required]);
      this.commingleForm.controls['preferredTanks'].updateValueAndValidity();
    }
    else if (this.manualCommingleList?.length >= 3) {
      this.isMaxCargo = true;
    }

  }

  /**
   * Delete row
   *
   */
  async onDeleteRow(event: ICommingleManualEvent) {
    const translationKeys = await this.translateService.get(['COMMINGLE_CARGO_DELETE_SUMMARY', 'COMMINGLE_CARGO_DELETE_DETAILS', 'COMMINGLE_CARGO_DELETE_CONFIRM_LABEL', 'COMMINGLE_CARGO_DELETE_REJECT_LABEL']).toPromise();

    this.confirmationService.confirm({
      key: 'confirmation-alert',
      header: translationKeys['COMMINGLE_CARGO_DELETE_SUMMARY'],
      message: translationKeys['COMMINGLE_CARGO_DELETE_DETAILS'],
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: translationKeys['COMMINGLE_CARGO_DELETE_CONFIRM_LABEL'],
      acceptIcon: 'pi',
      acceptButtonStyleClass: 'btn btn-main mr-5',
      rejectVisible: true,
      rejectLabel: translationKeys['COMMINGLE_CARGO_DELETE_REJECT_LABEL'],
      rejectIcon: 'pi',
      rejectButtonStyleClass: 'btn btn-main',
      accept: () => {
        if (event?.data?.isDelete) {
          this.manualCommingleList.splice(event.index, 1);
          this.manualCommingleList = [...this.manualCommingleList];
          (<FormArray>this.commingleManualForm.get('dataTable')).removeAt(event.index);
          if (!this.manualCommingleList?.length) {
            this.commingleForm.controls['preferredTanks'].setValidators([]);
            this.commingleForm.controls['preferredTanks'].updateValueAndValidity();
          }
          this.disableAddNewBtn = false;
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
    if (this.commingleForm.valid && this.commingleManualForm.valid) {
      this.ngxSpinnerService.show();
      const translationKeys = await this.translateService.get(['COMMINGLE_MANUAL_SAVE_WARNING', 'COMMINGLE_MANUAL_SAVE_SUCCESS', 'COMMINGLE_COMPLETED_SUCCESSFULLY', 'NO_COMMINGLE_DATA_SAVED', 'COMMINGLE_SAVE_ERROR', 'COMMINGLE_SAVE_STATUS_ERROR']).toPromise();
      const _commingleList = Array<ICargoGroup>();
      if (this.commingleManualForm.value.dataTable.length > 0) {
        for (let i = 0; i < this.manualCommingleList.length; i++) {
          this.manualCommingleList[i] = this.convertUnit(this.manualCommingleList[i], true)
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
      try {
          const result = await this.commingleApiService.saveVolMaxCommingle(this.vesselId, this.voyageId, this.loadableStudyId, data).toPromise();
          if (result.responseStatus.status === '200') {
            if (this.manualCommingleList?.length) {
              this.messageService.add({ severity: 'success', summary: translationKeys['COMMINGLE_MANUAL_SAVE_SUCCESS'], detail: translationKeys['COMMINGLE_COMPLETED_SUCCESSFULLY'] });
            } else {
              this.messageService.add({ severity: 'warn', summary: translationKeys['COMMINGLE_MANUAL_SAVE_WARNING'], detail: translationKeys['NO_COMMINGLE_DATA_SAVED'] });
            }
          }
          this.close();
      } catch (errorResponse) {
        if (errorResponse?.error?.errorCode === 'ERR-RICO-110') {
          this.messageService.add({ severity: 'error', summary: translationKeys['COMMINGLE_SAVE_ERROR'], detail: translationKeys['COMMINGLE_SAVE_STATUS_ERROR'], life: 10000 });
        }
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
      if (this.commingleCargo?.purposeId === 1) {
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
    }
    else {
      if (this.manualCommingleList && this.manualCommingleList?.length) {
        this.commingleForm.controls['preferredTanks'].setValidators([Validators.required]),
        this.commingleForm.controls['preferredTanks'].updateValueAndValidity();
      }
      this.commingleForm.controls['cargo1'].clearValidators();
      this.commingleForm.controls['cargo2'].clearValidators();
      this.commingleForm.controls['cargo1'].updateValueAndValidity();
      this.commingleForm.controls['cargo2'].updateValueAndValidity();
      this.selectedTanks = this.commingleCargo?.preferredTanks.map(preferredTank => ({
        ...this.preferredTankList.find((item) => (item.id === Number(preferredTank)) && item),
        ...preferredTank
      }));
      this.commingleForm.get('preferredTanks').setValue(this.commingleCargo?.preferredTanks);
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
  async clearCargo() {
    const translationKeys = await this.translateService.get(['COMMINGLE_CARGO_DELETE_SUMMARY', 'COMMINGLE_CARGO_DELETE_DETAILS', 'COMMINGLE_CARGO_DELETE_CONFIRM_LABEL', 'COMMINGLE_CARGO_DELETE_REJECT_LABEL']).toPromise();

    this.confirmationService.confirm({
      key: 'confirmation-alert',
      header: translationKeys['COMMINGLE_CARGO_DELETE_SUMMARY'],
      message: translationKeys['COMMINGLE_CARGO_DELETE_DETAILS'],
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: translationKeys['COMMINGLE_CARGO_DELETE_CONFIRM_LABEL'],
      acceptIcon: 'pi',
      acceptButtonStyleClass: 'btn btn-main mr-5',
      rejectVisible: true,
      rejectLabel: translationKeys['COMMINGLE_CARGO_DELETE_REJECT_LABEL'],
      rejectIcon: 'pi',
      rejectButtonStyleClass: 'btn btn-main',
      accept: () => {
        this.commingleForm.controls['cargo1'].setValue(null);
        this.commingleForm.controls['cargo2'].setValue(null);
        this.selectedCargo1 = null;
        this.selectedCargo2 = null;
        this.cargoNominationsCargo1 = this.cargoNominationsCargo;
        this.cargoNominationsCargo2 = this.cargoNominationsCargo;
        this.commingleForm.controls['cargo1'].setValidators([]);
        this.commingleForm.controls['cargo2'].setValidators([]);
        this.commingleForm.controls['cargo1'].updateValueAndValidity();
        this.commingleForm.controls['cargo2'].updateValueAndValidity();
      }
    });
  }

  /**
   * Method to show preferred tank notification
   */
  onPreferredTankSelect() {
    if (this.commingleForm.value?.preferredTanks?.length === 5) {
      this.extraPreferred = this.extraPreferred + 1;
      if (this.extraPreferred > 1) {
        this.isMaxPreferredTank = true;
      }

    }
    else {
      this.isMaxPreferredTank = false;
    }
  }

  /**
   * Method to convert the quantities by unit
   */
  convertUnit(row: ICommingleValueObject, toBaseUnit = false) {
    let unitTo, unitFrom;
    if (toBaseUnit) {
      unitFrom = localStorage.getItem('unit')
      unitTo = this.loadableStudyDetailsApiService.baseUnit;
    } else {
      unitFrom = this.loadableStudyDetailsApiService.baseUnit;
      unitTo = localStorage.getItem('unit')
    }
    const api1 = Number(row.cargo1.value.api)
    const api2 = Number(row.cargo2.value.api)
    const rd1 = 141.5 / (131.5 + api1)
    const rd2 = 141.5 / (131.5 + api2)
    const per1 = Number(row.cargo1pct.value)
    const per2 = Number(row.cargo2pct.value)
    const netRd = (rd1 * per1 + rd2 * per2) / 100
    const netApi = (141.5 / netRd) - 131.5
    row.cargo1.value.loadingPorts.forEach(port => {
      port.quantity = this.loadableStudyDetailsApiService.updateQuantityByUnit(port.quantity, unitFrom, unitTo, netApi)
    });
    row.cargo2.value.loadingPorts.forEach(port => {
      port.quantity = this.loadableStudyDetailsApiService.updateQuantityByUnit(port.quantity, unitFrom, unitTo, netApi)
    });
    row.quantity.value = Number(this.loadableStudyDetailsApiService.convertToNumber(this.loadableStudyDetailsApiService.decimalQuantiy(row.quantity.value, unitFrom, unitTo, netApi)));
    return row;
  }


}
