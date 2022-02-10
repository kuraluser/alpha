import { Component, OnInit, Input } from '@angular/core';
import { FormArray, FormGroup, ValidationErrors, Validators, FormBuilder, FormControl } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { NgxSpinnerService } from 'ngx-spinner';
import { v4 as uuid4 } from 'uuid';


import { IDISCHARGE_STUDY_MODE , IDischargeStudy, IDischargeStudyDropdownData, IDischargeStudyBackLoadingDetails, IPortCargoDetails, IPortDetailValueObject , IPortCargo } from '../../models/discharge-study-list.model';

import { DATATABLE_EDITMODE, IDataTableColumn } from '../../../../shared/components/datatable/datatable.model';
import { IPermission } from '../../../../shared/models/user-profile.model';
import { Voyage, IPort, IInstruction, ITankDetails, VOYAGE_STATUS, ICargo, DISCHARGE_STUDY_STATUS, OPERATIONS } from '../../../core/models/common.model';
import { QUANTITY_UNIT, IPercentage, IMode, IResponse } from '../../../../shared/models/common.model';

import { DischargeStudyDetailsTransformationService } from '../../services/discharge-study-details-transformation.service';
import { DischargeStudyDetailsApiService } from '../../services/discharge-study-details-api.service';

import { QuantityPipe } from '../../../../shared/pipes/quantity/quantity.pipe';
import { dischargeStudyColorValidator } from '../../directives/validator/discharge-study-color.directive';

import { dischargeStudyCargoQuantityValidator } from '../../directives/validator/discharge-study-cargo-quantity.directive';
import { dischargeStudyAbbreviationValidator } from '../../directives/validator/discharge-study-abbreviation.directive';
import { numberValidator } from '../../../core/directives/number-validator.directive';

import { QuantityDecimalService } from '../../../../shared/services/quantity-decimal/quantity-decimal.service';
import { alphaNumericOnlyValidator } from '../../../core/directives/alpha-numeric-only-validator.directive';
import { compareTimeValidator } from '../../directives/validator/compare-time-validator';
import { sequenceNumberValidator } from '../../../core/directives/sequence-number-validator.directive';

/**
 * Component class of discharge study screen
 *
 * @export
 * @class DischargeStudyComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-discharge-study',
  templateUrl: './discharge-study.component.html',
  styleUrls: ['./discharge-study.component.scss']
})
export class DischargeStudyComponent implements OnInit {

  @Input() voyageId: number;
  @Input() voyage: Voyage;
  @Input() dischargeStudyId: number;
  @Input() vesselId: number;
  @Input() permission: IPermission;
  @Input() ports: IPort[];
  @Input() cargos: ICargo[];
  @Input() isPlanConfirmed: IDischargeStudy;

  @Input()
  get dischargeStudy() {
    return this._dischargeStudy;
  }
  set dischargeStudy(value: IDischargeStudy) {
    this._dischargeStudy = value;
    this.editMode = (this.permission?.edit === undefined || this.permission?.edit) && [VOYAGE_STATUS.ACTIVE].includes(this.voyage?.statusId)
      && [DISCHARGE_STUDY_STATUS.PLAN_PENDING, DISCHARGE_STUDY_STATUS.PLAN_NO_SOLUTION, DISCHARGE_STUDY_STATUS.PLAN_ERROR].includes(this._dischargeStudy?.statusId) ? DATATABLE_EDITMODE.CELL : null;
    this.editMode ? this.dischargeStudyForm?.enable() : this.dischargeStudyForm?.disable();
    this.backLoadingColumns = this.dischargeStudyDetailsTransformationService.getDischargeStudyBackLoadingDatatableColumns(this.permission, this.dischargeStudy?.statusId, this.voyage.statusId);
  }

  loadableQuantity: number;
  editMode: DATATABLE_EDITMODE;
  portDetails: IPortDetailValueObject[];
  instructions: IInstruction[];
  cowList: IMode[];
  mode: IMode[];
  percentageList: IPercentage[];
  tank: ITankDetails[];
  dischargeStudyForm: FormGroup;
  columns: IDataTableColumn[];
  backLoadingColumns: IDataTableColumn[];
  listData: IDischargeStudyDropdownData;
  portCargoList: IPortCargoDetails[];
  errorMessages: any;
  readonly OPERATIONS = OPERATIONS;
  private _dischargeStudy: IDischargeStudy;

  constructor(
    private fb: FormBuilder,
    private quantityPipe: QuantityPipe,
    private translateService: TranslateService,
    private confirmationService: ConfirmationService,
    private ngxSpinnerService: NgxSpinnerService,
    private messageService: MessageService,
    private dischargeStudyDetailsApiService: DischargeStudyDetailsApiService,
    private quantityDecimalService: QuantityDecimalService,
    private dischargeStudyDetailsTransformationService: DischargeStudyDetailsTransformationService
  ) { }

  /**
   * NgOnit init function for discharge study component
   *
   * @memberof DischargeStudyComponent
   */
  ngOnInit(): void {
    this.dischargeStudyForm = this.fb.group({
      portDetails: this.fb.array([]),
      cow: this.fb.control(null, [Validators.required]),
      percentage: this.fb.control(null),
      tank: this.fb.control(null, []),
    })
    this.errorMessages = this.dischargeStudyDetailsTransformationService.setValidationMessageForDischargeStudy();
    this.columns = this.dischargeStudyDetailsTransformationService.getDischargeStudyCargoDatatableColumns();

    this.getDischargeStudyDetails();
  }

  /**
   * Method to get discharge study details
   *
   * @private
   * @memberof DischargeStudyComponent
   */
  private async getDischargeStudyDetails() {
    this.ngxSpinnerService.show();
    await this.setDropDownDetails();
    const instruction = await this.dischargeStudyDetailsApiService.getInstructionDetails().toPromise();
    const tankList = await this.dischargeStudyDetailsApiService.getTankDetails(this.vesselId).toPromise();
    const portCargoDetails = await this.dischargeStudyDetailsApiService.getPortCargoDetails(this.dischargeStudyId).toPromise();


    this.instructions = instruction.instructions;
    this.tank = tankList.cargoVesselTanks;
    this.portCargoList = portCargoDetails.portWiseCorges;

    this.listData = {
      modes: this.mode,
      tank: this.tank,
      portList: this.ports,
      cargoList: this.cargos,
      instructions: this.instructions,
      percentageList: this.percentageList
    }
    const result = await this.dischargeStudyDetailsApiService.getDischargeStudyDetails(this.vesselId, this.voyageId, this.dischargeStudyId).toPromise();
    try {
      if (result.responseStatus.status === '200') {
        this.loadableQuantity = result.loadableQuantity;
        const portDetails = this.dischargeStudyForm.get('portDetails') as FormArray;
        const portList = result.portList ? result.portList : [];
        const portUniqueColorAbbrList = [];
        const cowDetails = this.dischargeStudyDetailsTransformationService.getDischargeStudyCowDetails(result?.cowId, result?.percentage, result?.tank, this.listData);
        this.getFormFeildValue('cow').setValue(cowDetails.cow);
        this.getFormFeildValue('tank').setValue(cowDetails.tank);
        this.getFormFeildValue('percentage').setValue(cowDetails.percentage);
        this.portDetails = portList.map((portDetail, index) => {
          const isLastIndex = index + 1 === portList.length;
          const cargoList = this.portCargoList.find((portcargo) => {
            return portcargo.portId === portDetail.portId;
          })
          const portDetailAsValueObject = this.dischargeStudyDetailsTransformationService.getPortDetailAsValueObject(portList, portDetail, this.listData, isLastIndex, false, portUniqueColorAbbrList, cargoList);
          portDetails.push(this.initDischargeStudyFormGroup(portDetailAsValueObject, index));
          return portDetailAsValueObject;
        })
      }
    } catch (err) {
      this.portDetails = [];
    }

    this.ngxSpinnerService.hide();
  }

  /**
   * Method to get discharge form controls
   *
   * @private
   * @memberof DischargeStudyComponent
   */
  getFormFeildValue(formControlName: string) {
    return this.dischargeStudyForm.get(formControlName)
  }

  /**
   * Method to set Cow validation
   *
   * @private
   * @memberof DischargeStudyComponent
   */
  setCowValidation() {
      const cowId = this.getFormFeildValue('cow')?.value?.id;
      const tankFormControl = this.getFormFeildValue('tank');
      const percentageFormControl = this.getFormFeildValue('percentage');
      if (cowId === 1) {
        tankFormControl.setValue(null)
        tankFormControl.updateValueAndValidity();
        percentageFormControl.setValue({ value: 100, name: '100%' });
      } else {
        tankFormControl.setValue(null)
        tankFormControl.updateValueAndValidity();
        percentageFormControl.setValue(null);
      }
  }

  /**
   * Method to get get list data
   *
   * @private
   * @memberof DischargeStudyComponent
  */
  getListData(id: number) {
    const cargoList = this.portCargoList.find((portcargo) => {
      return portcargo.portId === id;
    })
    return {
      modes: this.mode,
      tank: this.tank,
      portList: this.ports,
      cargoList: cargoList ? cargoList.cargos : [],
      instructions: this.instructions,
      percentageList: this.percentageList
    }
  }

  /**
   * Method set drop down details
   *
   * @private
   * @memberof DischargeStudyComponent
   */
  private async setDropDownDetails() {
    const translationKeys = await this.translateService.get(['AUTO','REMAINING', 'ENTIRE' ,'MANUAL', 'BALANCE']).toPromise();
    this.mode = [
      { name: translationKeys['BALANCE'], id: 1 },
      { name: translationKeys['MANUAL'], id: 2 },
      { name: translationKeys['ENTIRE'], id: 3 }
    ];
    this.cowList = [
      { name: translationKeys['AUTO'], id: 1 },
      { name: translationKeys['MANUAL'], id: 2 }
    ];
    this.percentageList = [
      { value: 0, name: '0%' },
      { value: 25, name: '25%' },
      { value: 50, name: '50%' },
      { value: 75, name: '75%' },
      { value: 100, name: '100%' }
    ];
  }

  /**
 * Initialize ballast tank form group
 *
 * @private
 * @param {number} portDetail
 * @returns {FormGroup}
 * @memberof DischargeStudyComponent
 */
  private initDischargeStudyFormGroup(portDetail: any, portIndex: number): FormGroup {
    const freshCrudeValidationQty = portDetail?.freshCrudeOil ? [Validators.required,Validators.min(10), numberValidator(2, 4), Validators.max(1000)] : [];
    const freshCrudeValidationTime = portDetail?.freshCrudeOil ? [Validators.required, compareTimeValidator('10:00')] : [];
    return this.fb.group({
      freshCrudeOil: this.fb.control(portDetail?.freshCrudeOil),
      freshCrudeOilQuantity: this.fb.control(portDetail?.freshCrudeOilQuantity, freshCrudeValidationQty),
      freshCrudeOilTime: this.fb.control(portDetail?.freshCrudeOilTime, freshCrudeValidationTime),
      port: this.fb.control(portDetail?.port),
      operationId: this.fb.control(portDetail?.operationId),
      instruction: this.fb.control(portDetail?.instruction),
      maxDraft: this.fb.control(portDetail?.maxDraft, [Validators.required, Validators.min(0), numberValidator(2, 2)]),
      cargoDetail: this.fb.group({ dataTable: this.fb.array(this.dischargeCargoFormGroup(portDetail,portIndex)) }),
      cow: this.fb.control(portDetail?.cow, []),
      enableBackToLoading: this.fb.control(portDetail?.enableBackToLoading),
      backLoadingDetails: this.fb.group({ dataTable: this.fb.array(this.initBackLoadingFormGroup(portDetail)) })
    });
  }

  /**
   * Method for discharge back loading form Group
   *
   * @private
   * @param {*} portDetail
   * @memberof DischargeStudyComponent
   */
  initBackLoadingFormGroup(portDetail) {
    const backLoadingFormGroup = portDetail.backLoadingDetails.map((backLoadingDetail) => {
      return this.backLoadingFormGroup(backLoadingDetail);
    })
    return backLoadingFormGroup;
  }

  /**
  * Method for back loading
  *
  * @private
  * @param {*} backLoading
  * @memberof DischargeStudyComponent
  */
  backLoadingFormGroup(backLoading: any) {
    const klDecimal = this.quantityDecimalService.quantityDecimal(QUANTITY_UNIT.KL);
    const mtDecimal = this.quantityDecimalService.quantityDecimal(QUANTITY_UNIT.MT);
    const bblsDecimal = this.quantityDecimalService.quantityDecimal(QUANTITY_UNIT.BBLS);

    const klMin = klDecimal ? (1 / Math.pow(10, klDecimal)) : 1;
    const mtMin = mtDecimal ? (1 / Math.pow(10, klDecimal)) : 1;
    const bblsMin = bblsDecimal ? (1 / Math.pow(10, klDecimal)) : 1;
    const formGroup = this.fb.group({
      color: this.fb.control(backLoading.color?.value ? backLoading.color?.value : null, [Validators.required, dischargeStudyColorValidator]),
      abbreviation: this.fb.control(backLoading.abbreviation.value, [Validators.required, alphaNumericOnlyValidator, dischargeStudyAbbreviationValidator, Validators.maxLength(6)]),
      cargo: this.fb.control(backLoading?.cargo?.value ? backLoading.cargo?.value : null, [Validators.required]),
      bbls: this.fb.control(backLoading.bbls?.value ? backLoading.bbls?.value : null, [Validators.required, numberValidator(bblsDecimal, null, false), Validators.min(klMin)]),
      kl: this.fb.control(backLoading.kl?.value ? backLoading.kl?.value : null, [Validators.required, numberValidator(klDecimal, null, false), Validators.min(mtMin)]),
      mt: this.fb.control(backLoading.mt?.value ? backLoading.mt?.value : null, [Validators.required, numberValidator(mtDecimal, null, false), Validators.min(bblsMin)]),
      api: this.fb.control(backLoading.api?.value ? backLoading.api?.value : null, [Validators.required, Validators.min(8), Validators.max(99.99), numberValidator(2, 2)]),
      temp: this.fb.control(backLoading.temp?.value ? backLoading.temp?.value : null, [Validators.required, Validators.min(40), Validators.max(160), numberValidator(2, 3)]),
      storedKey: this.fb.control(backLoading?.storedKey?.value)
    })
    this.quantityFieldEnableDisable(formGroup,backLoading);
    return formGroup;
  }

  /**
  * Method for backloading quantity fields
  *
  * @private
  * @param {FormGroup} formGroup
  * @param {*} backLoading
  * @memberof DischargeStudyComponent
  */
  quantityFieldEnableDisable(formGroup: FormGroup, backLoading: any) {
    const quantityFields = ['kl','mt','bbls'];
    quantityFields.forEach(field => {
      const formControl = formGroup.get(field);
      if(formGroup.get('api').invalid || formGroup.get('temp').invalid) {
        formControl.disable();
        formControl.setValue(0);
        backLoading[field]['_value'] = 0;
      } else {
        formControl.enable();
      }
    })
  }

  /**
  * Method for discharge cargo fild
  *
  * @private
  * @param {number} portIndex
  * @param {*} portIndex
  * @memberof DischargeStudyComponent
  */
  dischargeCargoFormGroup(portDetail: any, portIndex: number) {
    const cargoFormGroup = portDetail.cargoDetail.map((cargo) => {
      return this.initCargoFormGroup(cargo,portIndex);
    })
    return cargoFormGroup;
  }

  /**
  * Method for discharge cargo fild
  *
  * @private
  * @param {*} cargo
  * @param {number} portIndex
  * @memberof DischargeStudyComponent
  */
  initCargoFormGroup(cargo: any, portIndex: number) {
    let klValidation;
    let mtValidation;
    let bblsValidation;
    if(cargo.bbls.value.id === 2  || ((cargo.bbls.value.id === 3) && !isNaN(cargo.bbls.value))) {
      klValidation = [Validators.required, Validators.min(0), dischargeStudyCargoQuantityValidator, numberValidator(this.quantityDecimalService.quantityDecimal(QUANTITY_UNIT.KL), null, false)];
      mtValidation = [Validators.required, Validators.min(0), dischargeStudyCargoQuantityValidator, numberValidator(this.quantityDecimalService.quantityDecimal(QUANTITY_UNIT.MT), null, false)];
      bblsValidation = [Validators.required, Validators.min(0), dischargeStudyCargoQuantityValidator, numberValidator(this.quantityDecimalService.quantityDecimal(QUANTITY_UNIT.BBLS), null, false)];
    } else {
      klValidation = [Validators.required, dischargeStudyCargoQuantityValidator];
      mtValidation = [Validators.required, dischargeStudyCargoQuantityValidator];
      bblsValidation = [Validators.required, dischargeStudyCargoQuantityValidator];
    }
    const isSequenceDisable = (cargo.mode?.value === 2 && !cargo.quantity);
    return this.fb.group({
      emptyMaxNoOfTanks: this.fb.control(cargo.emptyMaxNoOfTanks.value),
      sequenceNo : this.fb.control({value: cargo.sequenceNo.value, disabled: isSequenceDisable}, [Validators.required, Validators.min(1), numberValidator(0, 4, false),sequenceNumberValidator]),
      maxKl: this.fb.control(cargo.maxKl.value, []),
      abbreviation: this.fb.control(cargo.abbreviation.value, []),
      cargo: this.fb.control(cargo.cargo.value),
      color: this.fb.control(cargo.color.value),
      bbls: this.fb.control(cargo.bbls.value,   [...bblsValidation]),
      kl: this.fb.control(cargo.kl.value, [...klValidation]),
      mt: this.fb.control(cargo.mt.value, [...mtValidation]),
      mode: this.fb.control(cargo.mode?.value),
      api: this.fb.control(cargo.api?.value),
      temp: this.fb.control(cargo.temp?.value),
      storedKey: this.fb.control(cargo.storedKey?.value)
    })
  }

  /**
   * Method for updating form field
   *
   * @param {number} index
   * @memberof DischargeStudyComponent
  */
  async addBackLoading(index: number, formGroupName: string) {
    const storedKey = uuid4();
    const backLoadingDetails = <IDischargeStudyBackLoadingDetails>{};
    const backLoadingDetailsValueAsObject = this.dischargeStudyDetailsTransformationService.getBackLoadingDetailAsValueObject(backLoadingDetails, this.listData, storedKey, null, true);
    const portDetails = this.getFormFeildValue('portDetails') as FormArray;
    const backLoading = portDetails.at(index).get(formGroupName).get('dataTable') as FormArray;

    if (backLoading.length < 3) {
      backLoading.push(this.backLoadingFormGroup(backLoadingDetailsValueAsObject));
      this.dischargeStudyForm.updateValueAndValidity();
      this.portDetails[index]['backLoadingDetails'] = [...this.portDetails[index]['backLoadingDetails'], backLoadingDetailsValueAsObject];
      this.portDetails = [...this.portDetails];
    } else {
      const translationKeys = await this.translateService.get(['DISCHARGE_STUDY_BACK_LOADING_MAX_ERROR_SUMMERY', 'DISCHARGE_STUDY_BACK_LOADING_MAX_ERROR']).toPromise();
      this.messageService.add({ severity: 'error', summary: translationKeys['DISCHARGE_STUDY_BACK_LOADING_MAX_ERROR'], detail: translationKeys['DISCHARGE_STUDY_BACK_LOADING_MAX_ERROR_SUMMERY'] });
    }
    this.isDischargeStudyValid();
  }

  /**
* Method for deleting form field
* @param {*} event
* @param {number} index
* @param {string} formGroupName
* @memberof DischargeStudyComponent
*/
  async onDeleteRow(event: any, index: number, formGroupName: string) {
    if (event?.data?.isDelete) {
      const initPortDetails = [...this.portDetails];
      const translationKeys = await this.translateService.get(['DISCHARGE_STUDY_BACK_LOADING_DELETE_SUMMARY', 'DISCHARGE_STUDY_BACK_LOADING_DETAILS', 'DISCHARGE_STUDY_BACK_LOADING_DELETE_CONFIRM_LABEL', 'DISCHARGE_STUDY_BACK_LOADING_DELETE_REJECT_LABEL']).toPromise();

      this.confirmationService.confirm({
        key: 'confirmation-alert',
        header: translationKeys['DISCHARGE_STUDY_BACK_LOADING_DELETE_SUMMARY'],
        message: translationKeys['DISCHARGE_STUDY_BACK_LOADING_DETAILS'],
        icon: 'pi pi-exclamation-triangle',
        acceptLabel: translationKeys['DISCHARGE_STUDY_BACK_LOADING_DELETE_CONFIRM_LABEL'],
        acceptIcon: 'pi',
        acceptButtonStyleClass: 'btn btn-main mr-5',
        rejectVisible: true,
        rejectLabel: translationKeys['DISCHARGE_STUDY_BACK_LOADING_DELETE_REJECT_LABEL'],
        rejectIcon: 'pi',
        rejectButtonStyleClass: 'btn btn-main',
        accept: async () => {
          this.ngxSpinnerService.show();
          const portDetails = this.dischargeStudyForm.get('portDetails') as FormArray;
          for (let i = index + 1; i < initPortDetails.length; i++) {
            const cargoDetails = initPortDetails[i]['cargoDetail'];
            const cargoDetailFormArray = portDetails.at(i).get('cargoDetail').get('dataTable') as FormArray;
            const cargoToDeleteIndex = cargoDetails.findIndex((cargo, cargoIndex) => {
              if (event.data.storedKey.value === cargo.storedKey.value) {
                return cargo;
              }
            });
            if (cargoToDeleteIndex !== -1) {
              cargoDetailFormArray.removeAt(cargoToDeleteIndex);
              initPortDetails[i]['cargoDetail'].splice(cargoToDeleteIndex, 1);
            }
            this.dischargeStudyForm.updateValueAndValidity();
          }
          initPortDetails[index]['backLoadingDetails'].splice(event.index, 1);
          const backLoading = portDetails.at(index).get(formGroupName).get('dataTable') as FormArray;
          backLoading.removeAt(event.index);
          if (!backLoading.length) {
            const enableBackToLoadingControl = portDetails.at(index).get('enableBackToLoading');
            enableBackToLoadingControl.setValue(false);
            this.onChange(event, 'enableBackToLoading', index);
            backLoading.reset();
          }
          this.portDetails = [...initPortDetails];
          this.isDischargeStudyValid();
          this.dischargeStudyForm.markAsDirty();
          this.ngxSpinnerService.hide();
        }
      });
    }
  }

  /**
   * Method for save back loading details
   * @param {*} event
   * @param {number} portIndex
   * @memberof DischargeStudyComponent
  */
  async onSaveRow(event: any, portIndex: number) {
    const formGroup = this.getBackLoadingDetails(event.index, portIndex, 'backLoadingDetails');
    if (formGroup.valid) {
      const portDetails = [...this.portDetails];
      const selectedBackLoadingDetails = portDetails[portIndex].backLoadingDetails[event.index];
      selectedBackLoadingDetails.isAdd = false;
      for (const key in selectedBackLoadingDetails) {
        if (selectedBackLoadingDetails.hasOwnProperty(key) && selectedBackLoadingDetails[key].hasOwnProperty('_isEditMode')) {
          selectedBackLoadingDetails[key].isEditMode = false;
        }
      }
      portDetails[portIndex].backLoadingDetails[event.index] = selectedBackLoadingDetails;
      const sequenceNo = this.getSequenceNumberOfLastCargo(portIndex+1)?.toString();
      const newcargo = this.dischargeStudyDetailsTransformationService.setNewCargoInPortAsValuObject(selectedBackLoadingDetails, sequenceNo , this.mode[1]);
      let nextCargoDetails = portDetails[portIndex + 1].cargoDetail;
      const getCargoDetails = this.getFeild(portIndex + 1, 'cargoDetail').get('dataTable') as FormArray;
      getCargoDetails.push(this.initCargoFormGroup(newcargo,portIndex + 1));
      nextCargoDetails = [...nextCargoDetails, newcargo];
      portDetails[portIndex + 1].cargoDetail = nextCargoDetails;
      this.portDetails = [...portDetails];
    } else {
      formGroup.markAllAsTouched();
      formGroup.markAsDirty();
      this.dischargeStudyForm.updateValueAndValidity();
    }
    this.isDischargeStudyValid();
  }

  /**
   * Method to get sequence number
   *
   * @param {number} currentPort
   * @memberof DischargeStudyComponent
   */
  getSequenceNumberOfLastCargo(currentPort: number): number {
    const  nextCargoDetails = this.portDetails[currentPort]?.cargoDetail;
    let sequence = -1;
    for(let i = nextCargoDetails?.length -1 ;0 <= i;i--) {
      sequence = Number(nextCargoDetails[i]?.sequenceNo?.value) > 0  ? Number(nextCargoDetails[i]?.sequenceNo?.value)+1 : -1 ;
      if(sequence !== -1) { break; }
    }
    return sequence === -1 ? 1 : sequence;
  }

  /**
   * Method for geting form field
   *
   * @private
   * @param {number} portIndex
   * @param {string} field
   * @param {*} value
   * @memberof DischargeStudyComponent
   */
  getFeild(portIndex: number, formGroupName: string) {
    const portDetails = this.dischargeStudyForm.get('portDetails') as FormArray;
    return portDetails.at(portIndex).get(formGroupName);
  }

  /**
  * Method for updating form field while editing discharge cargo
  *
  * @private
  * @param {number} index
  * @param {*} event
  * @memberof DischargeStudyComponent
  */
  async onEditCompleteCargo(event: any, index: number) {
    const portDetails = [...this.portDetails];
    const selectedPortCargo = portDetails[index].cargoDetail[event.index];
    const portDetailsFormArray = this.dischargeStudyForm.get('portDetails') as FormArray;
    const cargoDetailFormArray = portDetailsFormArray.at(index).get('cargoDetail').get('dataTable') as FormArray;
    const formControlKl = this.getFormControl(index, 'cargoDetail' , event.index , 'kl') as FormControl;
    const formControlMt = this.getFormControl(index, 'cargoDetail' , event.index , 'mt') as FormControl;
    const formControlBbls = this.getFormControl(index, 'cargoDetail' , event.index , 'bbls') as FormControl;

    if (event.field === 'mode') {
      const selectedPortCargoSequneceFormControl = cargoDetailFormArray.at(event.index).get('sequenceNo');
      selectedPortCargoSequneceFormControl.enable();

      if (event.data.mode?.value.name === IDISCHARGE_STUDY_MODE.MANUAL) {
        this.setQuantityValidation(formControlKl,event.data.mode?.value.name,'kl');
        this.setQuantityValidation(formControlMt,event.data.mode?.value.name,'mt');
        this.setQuantityValidation(formControlBbls,event.data.mode?.value.name,'bbls');

        if (Number(event.data.quantity) === 0) {
          selectedPortCargo.sequenceNo.value = null;
          selectedPortCargoSequneceFormControl.setValue(null);
          selectedPortCargoSequneceFormControl.disable();
        }

        const kl = isNaN(formControlKl.value) ? 0 : formControlKl.value;
        this.setFeildValueOptionsOnMode('cargoDetail',event.index,'kl',true,kl, index, event.data.api.value, event.data.temp.value);
        selectedPortCargo['kl']['isEditMode'] = true;
        if (formControlKl.valid) {
          this.onQuantityEditComplete(event, portDetails, selectedPortCargo);
        } else {
          selectedPortCargo['kl'].isEditMode = true;
          selectedPortCargo['mt'].isEditMode = true;
          selectedPortCargo['bbls'].isEditMode = true;
        }
      } else if(event.data.mode?.value.name === IDISCHARGE_STUDY_MODE.BALANCE) {
        this.setQuantityValidation(formControlKl,event.data.mode?.value.name,'kl');
        this.setQuantityValidation(formControlMt,event.data.mode?.value.name,'mt');
        this.setQuantityValidation(formControlBbls,event.data.mode?.value.name,'bbls');

        this.setFeildValueOptionsOnMode('cargoDetail',event.index,'kl',true,'-', index, event.data.api.value, event.data.temp.value);
        this.onQuantityEditComplete(event, portDetails, selectedPortCargo);

        selectedPortCargo['kl']['isEditMode'] = false;
      } else {
        const { isAutoModeAvailable } = this.onQuantityEditComplete(event, portDetails, selectedPortCargo);
        this.setQuantityValidation(formControlKl,event.data.mode?.value.name,'kl',isAutoModeAvailable);
        this.setQuantityValidation(formControlMt,event.data.mode?.value.name,'mt',isAutoModeAvailable);
        this.setQuantityValidation(formControlBbls,event.data.mode?.value.name,'bbls',isAutoModeAvailable);
      }
    } else if (event.field === 'kl' || event.field === 'mt' || event.field === 'bbls') {
      const translationKeys = await this.translateService.get(['MANUAL']).toPromise();

      const selectedPortCargoSequneceFormControl = cargoDetailFormArray.at(event.index).get('sequenceNo');
      if (Number(event.data[event.field].value) === 0 && event.data.mode?.value.name === IDISCHARGE_STUDY_MODE.MANUAL) {
        selectedPortCargo.sequenceNo.value = null;
        selectedPortCargoSequneceFormControl.setValue(null);
        selectedPortCargoSequneceFormControl.disable();
      } else {
        selectedPortCargoSequneceFormControl.enable();
      }

      if(selectedPortCargo['mode'].value.name === IDISCHARGE_STUDY_MODE.BALANCE  && !isNaN(event.data[event.field].value)) {
        selectedPortCargo['mode'].value = { name: translationKeys['MANUAL'], id: 2 };
        cargoDetailFormArray.at(event.index).get('mode').setValue(selectedPortCargo['mode'].value);
      } else if(selectedPortCargo['mode'].value.id === 1) {
        this.setFeildValueOptionsOnMode('cargoDetail',event.index,'kl',true,'-', index, event.data.api.value, event.data.temp.value);
      }
      this.setFeildValueOptionsOnMode('cargoDetail',event.index,event.field,true,event.data[event.field].value, index, event.data.api.value, event.data.temp.value);
      this.setQuantityValidation(formControlKl,event.data.mode?.value.name,'kl');
      this.setQuantityValidation(formControlMt,event.data.mode?.value.name,'mt');
      this.setQuantityValidation(formControlBbls,event.data.mode?.value.name,'bbls');
      formControlKl.updateValueAndValidity();
      if (formControlKl.valid) {
        this.onQuantityEditComplete(event, portDetails, selectedPortCargo);
      } else {
        selectedPortCargo['kl'].isEditMode = true;
        selectedPortCargo['mt'].isEditMode = true;
        selectedPortCargo['bbls'].isEditMode = true;
      }
    }

    this.checkFormFieldValidity();
    selectedPortCargo['emptyMaxNoOfTanks'].isEditMode = true;
    this.portDetails = [...portDetails];
  }

  /**
  * Method for set validation for quantity fields
  *
  * @private
  * @param {FormControl} formControl
  * @param {number} index
  * @param {string} mode
  * @param {string} quantity
  * @param {boolean} isAutoModeAvailable
  * @memberof DischargeStudyComponent
  */
  setQuantityValidation(formControl: FormControl , mode: string, quantity: string, isAutoModeAvailable: boolean = false) {
    let quantityDecimal;
    switch(quantity) {
      case 'kl' :
        quantityDecimal = this.quantityDecimalService.quantityDecimal(QUANTITY_UNIT.KL);
        break;
      case 'mt' :
        quantityDecimal = this.quantityDecimalService.quantityDecimal(QUANTITY_UNIT.MT);
        break;
      case 'bbls' :
        quantityDecimal = this.quantityDecimalService.quantityDecimal(QUANTITY_UNIT.BBLS);
        break;
    }
    if((!isAutoModeAvailable && mode === IDISCHARGE_STUDY_MODE.ENTIRE) || mode === IDISCHARGE_STUDY_MODE.MANUAL) {
      formControl.setValidators([Validators.required, Validators.min(0), dischargeStudyCargoQuantityValidator, numberValidator(quantityDecimal, null, false)]);
    } else {
      formControl.setValidators([Validators.required, dischargeStudyCargoQuantityValidator]);
    }
    formControl.updateValueAndValidity();
  }

  /**
  * Method for set validation
  *
  * @private
  * @param {number} cargoIndex
  * @param {number} index
  * @param {string} api
  * @param {string} temp
  * @param {boolean} isEditableKl
  * @memberof DischargeStudyComponent
  */
  setFeildValueOptionsOnMode(type: string,cargoIndex: number , editedField: string ,isQuantityFieldEditable: boolean, value: string | number, portIndex: number, api: string, temp: string,isAutoModeAvailable: boolean = false) {
    const portDetails = [...this.portDetails];
    const selectedPortCargo = type === 'cargoDetail' ? portDetails[portIndex].cargoDetail[cargoIndex] : portDetails[portIndex].backLoadingDetails[cargoIndex];
    const mode = selectedPortCargo['mode']?.value?.name || IDISCHARGE_STUDY_MODE.MANUAL;
    selectedPortCargo[editedField].value = value;
    let unitFrom;
    if(mode === IDISCHARGE_STUDY_MODE.MANUAL || (!isAutoModeAvailable && mode === IDISCHARGE_STUDY_MODE.ENTIRE)) {
      switch(editedField) {
        case 'kl': {
          unitFrom = QUANTITY_UNIT.KL;
          const  unitConverted = this.unitConvertion(selectedPortCargo[editedField].value,unitFrom,api,temp);
          selectedPortCargo['mt'].value = isNaN(unitConverted.mt) || !unitConverted.mt ? '0' : unitConverted.mt + '';
          selectedPortCargo['bbls'].value = isNaN(unitConverted.bbls) || !unitConverted.bbls ? '0' : unitConverted.bbls + '';
          selectedPortCargo.quantity = isNaN(unitConverted.quantity) || !unitConverted.quantity ? 0 : unitConverted.quantity ;
          break;
        }
        case 'mt': {
          unitFrom = QUANTITY_UNIT.MT;
          const  unitConverted = this.unitConvertion(selectedPortCargo[editedField].value,unitFrom,api,temp);
          selectedPortCargo['kl'].value = isNaN(unitConverted.kl) || !unitConverted.kl ? '0' : unitConverted.kl + '';
          selectedPortCargo['bbls'].value = isNaN(unitConverted.bbls) || !unitConverted.bbls ? '0' : unitConverted.bbls + '';
          selectedPortCargo.quantity = isNaN(unitConverted.quantity) || !unitConverted.quantity ? 0 : unitConverted.quantity ;
          break;
        }

        case 'bbls': {
          unitFrom = QUANTITY_UNIT.BBLS;
          const  unitConverted = this.unitConvertion(selectedPortCargo[editedField].value,unitFrom,api,temp);
          selectedPortCargo['kl'].value = isNaN(unitConverted.kl) || !unitConverted.kl ? '0' : unitConverted.kl + '';
          selectedPortCargo['mt'].value = isNaN(unitConverted.mt) || !unitConverted.mt ? '0' : unitConverted.mt + '';
          selectedPortCargo.quantity = isNaN(unitConverted.quantity) || !unitConverted.quantity ? 0 : unitConverted.quantity ;
          break;
        }
      }
    } else {
      selectedPortCargo.kl.value = '-';
      selectedPortCargo.mt.value = '-';
      selectedPortCargo.bbls.value = '-';
      selectedPortCargo.quantity = null;
    }

    selectedPortCargo.kl.isEditable = isQuantityFieldEditable;
    selectedPortCargo.mt.isEditable = isQuantityFieldEditable;
    selectedPortCargo.bbls.isEditable = isQuantityFieldEditable;
    this.updatebackLoadingDetails(cargoIndex, portIndex, 'kl', selectedPortCargo['kl'].value, type);
    this.updatebackLoadingDetails(cargoIndex, portIndex, 'mt', selectedPortCargo['mt'].value, type);
    this.updatebackLoadingDetails(cargoIndex, portIndex, 'bbls', selectedPortCargo['bbls'].value, type);
  }

  /**
  * Method for unit conversion
  *
  * @private
  * @param {number | string } value
  * @param {QUANTITY_UNIT} unitFrom
  * @param {string} api
  * @param {string} temp
  * @memberof DischargeStudyComponent
  */
  unitConvertion(value: string | number, unitFrom:QUANTITY_UNIT, api:number | string, temp:number | string) {
    const unitConverted = {
      mt: this.quantityPipe.transform(value, unitFrom, QUANTITY_UNIT.MT, api,temp),
      bbls: this.quantityPipe.transform(value, unitFrom, QUANTITY_UNIT.BBLS, api, temp),
      kl: this.quantityPipe.transform(value, unitFrom, QUANTITY_UNIT.KL, api, temp),
      quantity: this.quantityPipe.transform(value, unitFrom, QUANTITY_UNIT.KL, api, temp, -1)
    }
    return unitConverted;
  }

  /**
  * Method for checking form feild validity
  *
  * @private
  * @param {number} index
  * @param {*} event
  * @memberof DischargeStudyComponent
  */
  checkFormFieldValidity(updateFieldValidity?: string) {
    this.portDetails.forEach((portDetail, portIndex) => {
      for (const key in portDetail) {
        if (portDetail.hasOwnProperty(key)) {
          if (key === 'cargoDetail' || key === 'backLoadingDetails') {
            portDetail[key]?.forEach((item, itemIndex) => {
              for (const innerKey in item) {
                if (item.hasOwnProperty(innerKey) && (typeof updateFieldValidity === 'undefined' || updateFieldValidity === innerKey)) {
                  const field = this.getFormControl(portIndex, key, itemIndex, innerKey);
                  if (field) {
                    field.updateValueAndValidity();
                    field.markAsTouched();
                    field.markAsDirty();
                    const mode = this.getFormControl(portIndex, key, itemIndex, 'mode');
                    if ((this.getFormControl(portIndex, key, itemIndex, innerKey)?.valid || (mode?.value?.id === 1 && (innerKey === 'kl' || innerKey === 'mt' || innerKey === 'bbls'))) && innerKey !== 'emptyMaxNoOfTanks') {
                      item[innerKey].isEditMode = false;
                    } else {
                      item[innerKey].isEditMode = true;
                    }
                  }
                }
              }
            })
          }
        }
      }
    })
  }

  /**
  * Method for updating quantity in ports in cargo
  *
  * @private
  * @param {*} cargo
  * @param {*} portDetails
  * @param {*} event
  * @memberof DischargeStudyComponent
  */
  onQuantityEditComplete(event: any, portDetails: any, cargo: any) {
    let parentIndex;
    let totalBackLoadingKlValue = 0;
    let quantitySum = 0;
    let maxQuantity = 0;
    let entireModeDetails = { isAvailable: false , value: 0 , cargoIndex: 0 , portIndex: 0};
    let isAutoModeAvailable;
    for (let i = 0; i < portDetails?.length; i++) {
      if (parentIndex === undefined) {
        for (let j = 0; j < portDetails[i].cargoDetail.length; j++) {
          const dischargeCargoDetails = portDetails[i].cargoDetail[j];
          if (dischargeCargoDetails.storedKey.value === cargo.storedKey.value) {
            parentIndex = i;
            if (dischargeCargoDetails.mode.value.name === IDISCHARGE_STUDY_MODE.MANUAL) {
              const kl = Number(dischargeCargoDetails.maxKl.value) - Number(dischargeCargoDetails.kl.value);
              maxQuantity = Number(dischargeCargoDetails.maxKl.value);
              quantitySum = Number(dischargeCargoDetails.kl.value);
              totalBackLoadingKlValue = Number(kl?.toFixed(3));
            }
            else if(dischargeCargoDetails.mode.value.name === IDISCHARGE_STUDY_MODE.ENTIRE) {
              entireModeDetails = {isAvailable: true ,  value:  Number(dischargeCargoDetails.maxKl.value) , portIndex: i , cargoIndex: j };
              totalBackLoadingKlValue = 0;
            }
            else {
              totalBackLoadingKlValue = Number(dischargeCargoDetails.maxKl.value) - Number(0);
              isAutoModeAvailable = true;
            }
            break;
          }
        }
        if (parentIndex === undefined) {
          for (let j = 0; j < portDetails[i].backLoadingDetails.length; j++) {
            const backLoadingDetails = portDetails[i].backLoadingDetails[j];
            if (backLoadingDetails.storedKey.value === cargo.storedKey.value) {
              parentIndex = i;
              totalBackLoadingKlValue = Number(backLoadingDetails.kl.value);
              maxQuantity =  Number(backLoadingDetails.kl.value);
              break;
            }
          }
        }
      } else {
        if (totalBackLoadingKlValue === 0) {
          const filterCargo = portDetails[i].cargoDetail.filter((cargoDetailDetails, cargoIndex) => {
            if (cargoDetailDetails.storedKey.value !== cargo.storedKey.value) {
              return cargo;
            } else {
              const portDetailsFormArray = this.dischargeStudyForm.get('portDetails') as FormArray;
              const backLoadingFormArray = portDetailsFormArray.at(i).get('cargoDetail').get('dataTable') as FormArray;
              backLoadingFormArray.removeAt(cargoIndex);
            }
          });
          portDetails[i].cargoDetail = filterCargo;
        } else if (totalBackLoadingKlValue > 0) {
          const findCargo = portDetails[i].cargoDetail.find((cargoDetailDetails, cargoIndex) => {
            if (cargoDetailDetails.storedKey.value === cargo.storedKey.value) {
              if (cargoDetailDetails.mode.value.id === 2) {
                (quantitySum += Number(cargoDetailDetails.kl.value)).toFixed(3);
                if(this.isQuantityWithInTheRange(quantitySum, maxQuantity)) {
                  this.setFeildValueOptionsOnMode('cargoDetail',cargoIndex,'kl',true,cargoDetailDetails.kl.value, i, event.data.api.value, event.data.temp.value);
                  totalBackLoadingKlValue = 0;
                } else if (Number(totalBackLoadingKlValue) >= Number(cargoDetailDetails.kl.value) && (i !== (this.portDetails.length - 1) || isAutoModeAvailable)) {
                  totalBackLoadingKlValue = Number((totalBackLoadingKlValue - Number(cargoDetailDetails.kl.value)).toFixed(3));
                  this.setFeildValueOptionsOnMode('cargoDetail',cargoIndex,'kl',true,cargoDetailDetails.kl.value, i, event.data.api.value, event.data.temp.value);
                } else {
                  this.setFeildValueOptionsOnMode('cargoDetail',cargoIndex,'kl',true,totalBackLoadingKlValue, i, event.data.api.value, event.data.temp.value);
                  totalBackLoadingKlValue = 0;
                }
              } else if(cargoDetailDetails.mode.value.id === 3) {
                entireModeDetails = {isAvailable: true ,  value: totalBackLoadingKlValue , portIndex: i , cargoIndex: cargoIndex };
                totalBackLoadingKlValue = 0;
              }
              else {
                isAutoModeAvailable = true;
              }
              return cargoDetailDetails;
            }
          })
          if (!findCargo) {
            const sequenceNo = this.getSequenceNumberOfLastCargo(i)?.toString();
            const newcargo = this.dischargeStudyDetailsTransformationService.setNewCargoInPortAsValuObject(cargo, sequenceNo , this.mode[1]);
            this.insertNewDischargeCargo(event, portDetails, newcargo, totalBackLoadingKlValue, i);
            totalBackLoadingKlValue = 0;
          }
        }
      }
    }
    if(entireModeDetails.isAvailable) {
      this.entireModeSet(isAutoModeAvailable,entireModeDetails);
    }
    return { isAutoModeAvailable , entireModeDetails };
  }

  /**
   * Method for to check quantity is with in the range
   *
   * @private
   * @param {number} quantitySum
   * @param {number} maxQuantity
   * @memberof DischargeStudyComponent
  */
  private isQuantityWithInTheRange(quantitySum: number, maxQuantity: number) {
    const value = (0.01 / 100) *  maxQuantity;
    if(quantitySum >= (maxQuantity - value) && quantitySum <=( maxQuantity + value)) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Method for entire mode
   *
   * @private
   * @param {boolean} isAutoModeAvailable
   * @param {*} entireModeDetails
   * @memberof DischargeStudyComponent
  */
  private entireModeSet(isAutoModeAvailable: boolean,entireModeDetails: any) {
    const cargoDetail = this.getBackLoadingDetails(entireModeDetails.cargoIndex,entireModeDetails.portIndex,'cargoDetail') as FormGroup;
    const api = cargoDetail.get('api').value;
    const temp = cargoDetail.get('temp').value;
    if(isAutoModeAvailable) {
      this.setFeildValueOptionsOnMode('cargoDetail',entireModeDetails.cargoIndex,'kl',false,'-', entireModeDetails.portIndex,api,temp,isAutoModeAvailable);
    } else {
      this.setFeildValueOptionsOnMode('cargoDetail',entireModeDetails.cargoIndex,'kl',false,entireModeDetails.value?.toString(), entireModeDetails.portIndex,api,temp);
    }
    const quantityFields = ['kl','mt','bbls'];
    quantityFields.forEach(value => {
      const formControl = cargoDetail.get(value) as FormControl;
      this.setQuantityValidation(formControl,IDISCHARGE_STUDY_MODE.ENTIRE,value,isAutoModeAvailable);
    })
  }

  /**
   * Method for insert new cargo in port
   *
   * @private
   * @param {*} cargo
   * @param {*} portDetails
   * @param {*} event
   * @param {number} klValue
   * @param {number} index
   * @memberof DischargeStudyComponent
   */
  insertNewDischargeCargo(event, portDetails, cargo, totalKlValue, index) {
    const duplicateCargoDetails = cargo;
    duplicateCargoDetails.kl.value = totalKlValue;
    const unitConverted =  this.unitConvertion(duplicateCargoDetails['kl'].value,QUANTITY_UNIT.KL,event.data.api.value, event.data.temp.value);
    duplicateCargoDetails['mt'].value = unitConverted.mt;
    duplicateCargoDetails['bbls'].value = unitConverted.bbls;
    duplicateCargoDetails.quantity = unitConverted.quantity;

    const sequenceNo = this.getSequenceNumberOfLastCargo(index)?.toString();
    const newcargo = this.dischargeStudyDetailsTransformationService.setNewCargoInPortAsValuObject(duplicateCargoDetails, sequenceNo , this.mode[1]);
    const getCargoDetails = this.getFeild(index, 'cargoDetail').get('dataTable') as FormArray;
    portDetails[index].cargoDetail.push(newcargo);
    getCargoDetails.push(this.initCargoFormGroup(newcargo,index));
  }

  /**
  * Method for updating  Back loading
  *
  * @private
  * @param {number} index
  * @param {*} event
  * @memberof DischargeStudyComponent
  */
  async onEditCompleteBackLoading(event: any, index: number) {
    const portDetails = [...this.portDetails];
    const data = event.data;
    const feildIndex = event.index;
    const selectedPortCargo = portDetails[index].backLoadingDetails[event.index];
    const formGroup = this.getBackLoadingDetails(event.index, index, 'backLoadingDetails') as FormGroup;

    if (event.field === 'cargo') {
      formGroup.get('abbreviation').disable();
      formGroup.get('api').disable();
      formGroup.get('temp').disable();
      formGroup.get('cargo').disable();

      const cargoDetails = await this.dischargeStudyDetailsApiService.getCargoHistoryDetails(this.vesselId, portDetails[index].port?.id, data.cargo.value.id,).toPromise();
      formGroup.get('abbreviation').enable();
      formGroup.get('api').enable();
      formGroup.get('temp').enable();
      formGroup.get('cargo').enable();


      selectedPortCargo['abbreviation'].value = data.cargo.value.abbreviation;
      selectedPortCargo['api'].value = cargoDetails.api;
      selectedPortCargo['temp'].value = cargoDetails.temperature;

      this.updatebackLoadingDetails(feildIndex, index, 'abbreviation', selectedPortCargo['abbreviation'].value, 'backLoadingDetails');
      this.updatebackLoadingDetails(feildIndex, index, 'api', selectedPortCargo['api'].value, 'backLoadingDetails');
      this.updatebackLoadingDetails(feildIndex, index, 'temp', selectedPortCargo['temp'].value, 'backLoadingDetails');
      this.quantityFieldEnableDisable(formGroup,selectedPortCargo);
    } else if(event.field === 'api' || event.field === 'temp') {
      for (let i = index; i < portDetails.length; i++) {
        if (portDetails[i+1]) {
          const getCargoDetails = this.getFeild(i + 1, 'cargoDetail').get('dataTable') as FormArray;
          portDetails[i+1].cargoDetail.map( (cargo, cargoIndex) => {
            if (cargo.storedKey.value === selectedPortCargo.storedKey.value) {
              getCargoDetails.at(cargoIndex).get(event.field).setValue(Number(selectedPortCargo[event.field].value))
              cargo[event.field].value = Number(selectedPortCargo[event.field].value);
              return cargo;
            }
          });
        }
      }

      this.setFeildValueOptionsOnMode('backLoadingDetails',event.index,'kl',true,'0', index, event.data.api.value, event.data.temp.value);
      this.quantityFieldEnableDisable(formGroup,selectedPortCargo);
    }

    if(event.field === 'kl' || event.field === 'bbls' || event.field === 'mt') {
      this.setFeildValueOptionsOnMode('backLoadingDetails',event.index,event.field,true,event.data[event.field].value, index, event.data.api.value, event.data.temp.value);
      if (!event.data.isAdd && formGroup.get(event.field).valid) {
        this.onQuantityEditComplete(event, portDetails, selectedPortCargo);
      }
    }

    this.checkFormFieldValidity();
    formGroup.get('kl').updateValueAndValidity();
    this.portDetails = [...portDetails];
  }


  /**
  * Method for updating form field
  *
  * @private
  * @param {number} feildIndex
  * @param {number} index
  * @param {string} formGroupName
  * @param {string} value
  * @memberof DischargeStudyComponent
  */
  updatebackLoadingDetails(feildIndex: number, index: number, formGroupName: string, value: string | number, formArrayName: string) {
    const feild = this.getBackLoadingDetails(feildIndex, index, formArrayName).get(formGroupName);
    feild.setValue(value);
    feild.markAsDirty();
    feild.markAsTouched();
  }

  /**
  * Method for geting form field
  *
  * @private
  * @param {number} cargoIndex
  * @param {number} portIndex
  * @param {string} formControlName
  * @param {string} formGroupName
  * @memberof DischargeStudyComponent
  */
  getFormControl(portIndex: number, formControlName: string, cargoIndex: number, formGroupName: string) {
    const feild = this.getBackLoadingDetails(cargoIndex, portIndex, formControlName).get(formGroupName);
    return feild;
  }

  /**
  * Method for rest of back Loading
  *
  * @private
  * @param {number} cargoIndex
  * @param {number} portIndex
  * @memberof DischargeStudyComponent
  */
  getBackLoadingDetails(cargoIndex: number, portIndex: number, formControlName: string) {
    const backLoadingDetails = this.getFeild(portIndex, formControlName).get('dataTable') as FormArray;
    return backLoadingDetails.at(cargoIndex);
  }

  /**
  * Method for rest of back Loading
  *
  * @private
  * @param {*} event
  * @param {number} index
  * @memberof DischargeStudyComponent
  */
  resetBackLoading(event: any, index: number) {
    const initPortDetails = this.portDetails;
    const portDetailsFormArray = this.dischargeStudyForm.get('portDetails') as FormArray;
    const enableBackLoading = portDetailsFormArray.at(index).get('enableBackToLoading').value;
    initPortDetails[index]['enableBackToLoading'] = enableBackLoading;
    if (!enableBackLoading) {
      if (initPortDetails[index]['backLoadingDetails']?.length) {
        const backLoadingDetails = initPortDetails[index]['backLoadingDetails'];
        for (let i = index + 1; i < initPortDetails?.length; i++) {
          const cargoDetail = initPortDetails[i]?.cargoDetail;
          backLoadingDetails.map((backLoadingCargo) => {
            if (!backLoadingCargo.isAdd) {
              const cargoIndex = cargoDetail.findIndex((item) => {
                if (backLoadingCargo.storedKey.value === item['storedKey']['value']) {
                  return cargoDetail;
                }
              })
              if (cargoIndex !== -1) {
                cargoDetail.splice(cargoIndex, 1);
                const cargoDetailsFormArray = portDetailsFormArray.at(i).get('cargoDetail').get('dataTable') as FormArray;
                cargoDetailsFormArray.removeAt(cargoIndex);
              }
            }
          })
          initPortDetails[i].cargoDetail = cargoDetail;
        }
      }
      initPortDetails[index]['backLoadingDetails'] = [];
      const backLoading = portDetailsFormArray.at(index).get('backLoadingDetails').get('dataTable') as FormArray;
      backLoading.clear();
      this.portDetails = [...initPortDetails];
      this.isDischargeStudyValid();
    } else {
      this.addBackLoading(index, 'backLoadingDetails');
      this.isDischargeStudyValid();
    }
  }

  /**
   * Method to check for field errors
   *
   * @param {string} formControlName
   * @return {ValidationErrors}
   * @memberof DischargeStudyComponent
  */
  fieldError(index: number, formControlName: string): ValidationErrors {
    const formControl = this.getFeild(index, formControlName);
    return formControl?.invalid && (formControl.dirty || formControl.touched) ? formControl.errors : null;
  }
  /**
   * Method to save discharge study
   *
   * @param {string} formControlName
   * @memberof DischargeStudyComponent
  */
  async saveDischargeStudy() {
    const isFormDirty = !this.dischargeStudyForm.dirty;
    const translationKeys = await this.translateService.get(['DISCHARGE_STUDY_SAVE_WARNING_MULTIPLE_PORT','DISCHARGE_STUDY_SAVE_WARNING_MULTIPLE_PORT_SUMMERY','PORT','DISCHARGE_STUDY_BACK_LOADING_GREATER_THAN_LOADABLE_QUANTITY','DISCHARGE_STUDY_SAVE_ERROR', 'DISCHARGE_STUDY_SAVE_NO_DATA_ERROR', 'DISCHARGE_STUDY_SAVE_WARNING_SUMMERY', 'DISCHARGE_STUDY_SAVE_WARNING', 'DISCHARGE_STUDY_SUCCESS', 'DISCHARGE_STUDY_SUCCESS_SUMMERY']).toPromise();
    this.checkFormFieldValidity();
    this.dischargeStudyForm.markAllAsTouched();
    this.dischargeStudyForm.markAsDirty();

    if (isFormDirty && this.portDetails?.length !== 1) {
      this.messageService.add({ severity: 'error', summary: translationKeys['DISCHARGE_STUDY_SAVE_ERROR'], detail: translationKeys['DISCHARGE_STUDY_SAVE_NO_DATA_ERROR'] });
      const value = this.dischargeStudyForm.value;
      this.dischargeStudyForm.reset(value);
      return;
    }
    if (this.dischargeStudyForm.valid) {
      const isLoadableQuantityValid = this.validateLoadableQuantity();
      if(isLoadableQuantityValid.multipleAutoModeAvailablePortIndex !== undefined) {
        this.messageService.add({
          severity: 'warn', summary: translationKeys['DISCHARGE_STUDY_SAVE_WARNING'],
          detail: `${translationKeys['DISCHARGE_STUDY_SAVE_WARNING_MULTIPLE_PORT'] } ${isLoadableQuantityValid.multipleAutoModeAvailablePortIndex+1} , ${translationKeys['DISCHARGE_STUDY_SAVE_WARNING_MULTIPLE_PORT_SUMMERY'] }`
        });
        return;
      }
      else if (isLoadableQuantityValid.backLoadingIndex !== undefined) {
        this.messageService.add({
          severity: 'warn', summary: translationKeys['DISCHARGE_STUDY_SAVE_WARNING'],
          detail: `${translationKeys['DISCHARGE_STUDY_SAVE_WARNING_SUMMERY'] } ${isLoadableQuantityValid.backLoadingIndex+1}`
        });
        return;
      } else if (isLoadableQuantityValid.invalidPort?.length) {
        const portId = isLoadableQuantityValid.invalidPort[0];
        this.messageService.add({
          severity: 'warn', summary: translationKeys['DISCHARGE_STUDY_SAVE_WARNING'],
          detail: `${translationKeys['PORT']} ${portId+1} ${translationKeys['DISCHARGE_STUDY_BACK_LOADING_GREATER_THAN_LOADABLE_QUANTITY']}`
        });
        return;
      }
      this.ngxSpinnerService.show();
      const dichargeStudyDetails = this.portDetails.map((portItems) => {
        return this.dischargeStudyDetailsTransformationService.getDischargeStudyAsValue(portItems);
      })
      const dischargeValue = this.dischargeStudyForm.value;
      const obj = {
        dischargeStudyId: this.dischargeStudyId,
        portList: dichargeStudyDetails ,
        cowId: dischargeValue.cow?.id ,
        percentage: dischargeValue.percentage?.value ?? null,
        tank: dischargeValue.tank?.length ? dischargeValue.tank.map(tankItem =>  tankItem.id) : []
      };
      const result: IResponse = await this.dischargeStudyDetailsApiService.saveDischargeStudy(obj).toPromise();
      if (result.responseStatus.status === '200') {
        this.isDischargeStudyValid(true);
        this.dischargeStudyDetailsTransformationService.setDischargeStudyValidity(true);
        this.messageService.add({ severity: 'success', summary: translationKeys['DISCHARGE_STUDY_SUCCESS'], detail: translationKeys['DISCHARGE_STUDY_SUCCESS_SUMMERY'] });
        const value = this.dischargeStudyForm.value;
        this.dischargeStudyForm.reset(value);
        this.ngxSpinnerService.hide();
      }
    } else {
      setTimeout(() => {
        if(document.querySelector('.error-icon')) {
          document.querySelector('.error-icon').scrollIntoView({ behavior: "smooth"});
        }
      })
    }
  }

  /**
   * Method to validate loadable quantity
   *
   * @memberof DischargeStudyComponent
  */
  validateLoadableQuantity() {
    const invalidPort = [];
    let backLoadingIndex;
    let multipleAutoModeAvailablePortIndex;
    this.portDetails.map((item, portIndex) => {
      let loadableQuantity = 0;
      const backLoadingDetailsFormControls = item.backLoadingDetails;
      backLoadingDetailsFormControls.map((backLoadingItems) => {
        if(backLoadingItems.isAdd) {
          backLoadingIndex = portIndex;
        }
        if (backLoadingItems.mt) {
          loadableQuantity += Number(backLoadingItems.mt.value);
        }
      })
      const cargoDetails = item.cargoDetail;
      let portAutoCount = 0;
      cargoDetails.map((cargoDetailsItems) => {
        if(cargoDetailsItems.mode.value.id === 1) {
          portAutoCount+= 1;
          if(portAutoCount > 1) {
            multipleAutoModeAvailablePortIndex = portIndex;
          }
        };
        const storedKey = cargoDetailsItems.storedKey.value;
        let maxQuantity;
        for (let i = 0; i < this.portDetails.length; i++) {
          if (maxQuantity === undefined) {
            this.portDetails[i].cargoDetail.find((cargoDetailDetail, cargoIndex) => {
              if (cargoDetailDetail.storedKey.value === storedKey) {
                maxQuantity = 0;
                maxQuantity = this.quantityPipe.transform(cargoDetailDetail.maxKl.value,QUANTITY_UNIT.KL,QUANTITY_UNIT.MT,cargoDetailDetail.api.value,cargoDetailDetail.temp.value,-1);
                maxQuantity -= cargoDetailDetail.mode['_value'].id === 2 ? Number(cargoDetailDetail.mt.value) : 0;
                return;
              }
            })
            if (maxQuantity !== undefined) {
              this.portDetails[i].backLoadingDetails.find((backLoadingDetail, cargoIndex) => {
                if (backLoadingDetail.storedKey.value === storedKey) {
                  maxQuantity = 0;
                  maxQuantity = backLoadingDetail.mt.value;
                  return;
                }
              })
            }
          } else {
            const value = this.portDetails[i].cargoDetail.find((cargoDetailDetail, cargoIndex) => {
              if (cargoDetailDetail.storedKey.value === storedKey) {
                return;
              }
            })
            if(value) {
              const quantity = value.mode.value.id === 2 ? value.mt.value : 0;
              maxQuantity = maxQuantity - Number(quantity);
            }
          }
          if(portIndex === i) {
            loadableQuantity += maxQuantity;
            break;
          }
          }
        })
        if(this.loadableQuantity < loadableQuantity) {
          invalidPort.push(portIndex);
        }
    })
    return {invalidPort , backLoadingIndex , multipleAutoModeAvailablePortIndex};
  }

  /**
   * Method to check discharge valid
    * @param { boolean } isValid
   * @memberof DischargeStudyComponent
  */
  isDischargeStudyValid(isValid = false) {
    let isAdd;
    this.portDetails.forEach((portDetail) => {
      for (const key in portDetail) {
        if (Object.prototype.hasOwnProperty.call(portDetail, key)) {
          if (key === 'backLoadingDetails') {
            portDetail[key]?.forEach((item) => {
              if (item.isNew && !isValid) {
                isAdd = true;
              } else if (isValid) {
                item.isNew = false;
              }
            })
          }
        }
      }
    })
    if (this.dischargeStudyForm.valid && !isAdd) {
      this.dischargeStudyDetailsTransformationService.setDischargeStudyValidity(true);
    } else {
      this.dischargeStudyDetailsTransformationService.setDischargeStudyValidity(false);
    }
  }

  /**
   * Method trigger while change in cow
   *
   * @param {*} event
   * @param {number} index
   * @memberof DischargeStudyComponent
  */
  onChange(event: any, field: string, index: number) {
    const portDetails = [...this.portDetails];
    if(field === 'freshCrudeOil') {
      const freshCrudeOil = this.getFeild(index, field);
      const freshCrudeOilQuantity = this.getFeild(index, 'freshCrudeOilQuantity');
      const freshCrudeOilTime = this.getFeild(index, 'freshCrudeOilTime');

      if(freshCrudeOil.value) {
        freshCrudeOilQuantity.setValidators([Validators.required,Validators.min(10), Validators.max(1000), numberValidator(2,null,false)]);
        freshCrudeOilQuantity.updateValueAndValidity();
        freshCrudeOilTime.setValidators([Validators.required, compareTimeValidator('10:00')]);
        freshCrudeOilTime.updateValueAndValidity();
      } else {
        freshCrudeOilQuantity.setValue(null);
        freshCrudeOilTime.setValue('00:00');
        freshCrudeOilQuantity.setValidators([]);
        freshCrudeOilQuantity.updateValueAndValidity();
        freshCrudeOilTime.setValidators([]);
        portDetails[index]['freshCrudeOilQuantity'] = null;
        portDetails[index]['freshCrudeOilTime'] = '00:00';
        freshCrudeOilTime.updateValueAndValidity();
      }
      portDetails[index][field] = freshCrudeOil.value;
    }
    else {
      const tankControl = this.getFeild(index, field);
      portDetails[index][field] = tankControl.value;
    }
    this.portDetails = [...portDetails];
    this.isDischargeStudyValid();
  }
}
