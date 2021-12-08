import { Component, OnInit, Input } from '@angular/core';
import { FormArray, FormGroup, ValidationErrors, Validators, FormBuilder } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { NgxSpinnerService } from 'ngx-spinner';
import { v4 as uuid4 } from 'uuid';


import { IDischargeStudy, IDischargeStudyDropdownData, IDischargeStudyBackLoadingDetails, IPortCargoDetails, IPortDetailValueObject } from '../../models/discharge-study-list.model';

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
import { sequenceNumberValidator } from '../../directives/validator/sequence-number-validator.directive';
import { compareTimeValidator } from '../../directives/validator/compare-time-validator';

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
      && [DISCHARGE_STUDY_STATUS.PLAN_PENDING, DISCHARGE_STUDY_STATUS.PLAN_NO_SOLUTION, DISCHARGE_STUDY_STATUS.PLAN_ERROR].includes(this._dischargeStudy?.statusId) && !this.isPlanConfirmed ? DATATABLE_EDITMODE.CELL : null;
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
      mode: this.mode,
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
        const cowDetails = this.dischargeStudyDetailsTransformationService.getDischargeStudyCowDetails(result.cowId,result.percentage,result.tanks, this.listData);
        this.getFormFeildValue('cow').setValue(cowDetails.cow);
        this.getFormFeildValue('tank').setValue(cowDetails.tank);
        this.getFormFeildValue('percentage').setValue(cowDetails.percentage);
        this.portDetails = portList.filter(portDetail => portDetail.operationId === OPERATIONS.DISCHARGING).map((portDetail, index) => {
          const isLastIndex = index + 1 === portList.length;
          const cargoList = this.portCargoList.find((portcargo) => {
            return portcargo.portId === portDetail.portId;
          })
          const portDetailAsValueObject = this.dischargeStudyDetailsTransformationService.getPortDetailAsValueObject(portDetail, this.listData, isLastIndex, false, portUniqueColorAbbrList,cargoList);
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
      mode: this.mode,
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
    const translationKeys = await this.translateService.get(['MANUAL', 'AUTO']).toPromise();
    this.mode = [
      { name: translationKeys['AUTO'], id: 1 },
      { name: translationKeys['MANUAL'], id: 2 }
    ]
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
    ]
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
    const freshCrudeValidationQty = portDetail?.freshCrudeOil ? [Validators.required,Validators.min(10), Validators.max(1000)] : [];
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
    const quantityDecimal = this.quantityDecimalService.quantityDecimal(QUANTITY_UNIT.KL);
    const min = quantityDecimal ? (1 / Math.pow(10, quantityDecimal)) : 1;
    return this.fb.group({
      color: this.fb.control(backLoading.color?.value ? backLoading.color?.value : null, [Validators.required, dischargeStudyColorValidator]),
      abbreviation: this.fb.control(backLoading.abbreviation.value, [Validators.required, alphaNumericOnlyValidator, dischargeStudyAbbreviationValidator, Validators.maxLength(6)]),
      cargo: this.fb.control(backLoading?.cargo?.value ? backLoading.cargo?.value : null, [Validators.required]),
      bbls: this.fb.control(backLoading.bbls?.value ? backLoading.bbls?.value : null, []),
      kl: this.fb.control(backLoading.kl?.value ? backLoading.kl?.value : null, [Validators.required, numberValidator(quantityDecimal, null, false), Validators.min(min)]),
      mt: this.fb.control(backLoading.mt?.value ? backLoading.mt?.value : null, []),
      api: this.fb.control(backLoading.api?.value ? backLoading.api?.value : null, [Validators.required, Validators.min(8), Validators.max(99.99), numberValidator(2, 2)]),
      temp: this.fb.control(backLoading.temp?.value ? backLoading.temp?.value : null, [Validators.required, Validators.min(40), Validators.max(160), numberValidator(2, 3)]),
      storedKey: this.fb.control(backLoading?.storedKey?.value)
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
    const quantityDecimal = this.quantityDecimalService.quantityDecimal(QUANTITY_UNIT.KL);
    const min = quantityDecimal ? (1 / Math.pow(10, quantityDecimal)) : 1;
    const quantityValidator = cargo.mt.value.id === 2 ? [Validators.required, Validators.min(0), dischargeStudyCargoQuantityValidator, numberValidator(quantityDecimal, null, false)] :
      [Validators.required, dischargeStudyCargoQuantityValidator];
    return this.fb.group({
      emptyMaxNoOfTanks: this.fb.control(cargo.emptyMaxNoOfTanks.value),
      sequenceNo : this.fb.control(cargo.sequenceNo.value, [Validators.required, numberValidator(0, null, false),sequenceNumberValidator(portIndex)]),
      maxKl: this.fb.control(cargo.maxKl.value, []),
      abbreviation: this.fb.control(cargo.abbreviation.value, []),
      cargo: this.fb.control(cargo.cargo.value),
      color: this.fb.control(cargo.color.value),
      bbls: this.fb.control(cargo.bbls.value),
      kl: this.fb.control(cargo.kl.value, [...quantityValidator]),
      mt: this.fb.control(cargo.mt.value),
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
    const portDetails = this.dischargeStudyForm.get('portDetails') as FormArray;
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
   * @param {number} index
   * @memberof DischargeStudyComponent
  */
  async onSaveRow(event: any, index: number) {
    const formGroup = this.getBackLoadingDetails(event.index, index, 'backLoadingDetails');
    if (formGroup.valid) {
      const portDetails = [...this.portDetails];
      const selectedBackLoadingDetails = portDetails[index].backLoadingDetails[event.index];
      selectedBackLoadingDetails.isAdd = false;
      for (const key in selectedBackLoadingDetails) {
        if (selectedBackLoadingDetails.hasOwnProperty(key) && selectedBackLoadingDetails[key].hasOwnProperty('_isEditMode')) {
          selectedBackLoadingDetails[key].isEditMode = false;
        }
      }

      portDetails[index].backLoadingDetails[event.index] = selectedBackLoadingDetails;
      const sequenceNo = this.getSequenceNumberOfLastCargo(index+1)?.toString();
      const newcargo = this.dischargeStudyDetailsTransformationService.setNewCargoInPortAsValuObject(selectedBackLoadingDetails, sequenceNo , this.mode[1]);
      let nextCargoDetails = portDetails[index + 1].cargoDetail;
      const getCargoDetails = this.getFeild(index + 1, 'cargoDetail').get('dataTable') as FormArray;
      getCargoDetails.push(this.initCargoFormGroup(newcargo,index + 1));
      nextCargoDetails = [...nextCargoDetails, newcargo];
      portDetails[index + 1].cargoDetail = nextCargoDetails;
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
   * @param {number} index
   * @param {string} field
   * @param {*} value
   * @memberof DischargeStudyComponent
   */
  getFeild(index: number, formGroupName: string) {
    const portDetails = this.dischargeStudyForm.get('portDetails') as FormArray;
    return portDetails.at(index).get(formGroupName);
  }

  /**
  * Method for updating form field while editing discharge cargo
  *
  * @private
  * @param {number} index
  * @param {*} event
  * @memberof DischargeStudyComponent
  */
  onEditCompleteCargo(event: any, index: number) {
    let portDetails = [...this.portDetails];
    const selectedPortCargo = portDetails[index].cargoDetail[event.index];
    const portDetailsFormArray = this.dischargeStudyForm.get('portDetails') as FormArray;
    const backLoadingFormArray = portDetailsFormArray.at(index).get('cargoDetail').get('dataTable') as FormArray;
    const quantityFeild = backLoadingFormArray.at(event.index).get('kl');
    const quantityDecimal = this.quantityDecimalService.quantityDecimal(QUANTITY_UNIT.KL);
    const min = quantityDecimal ? (1 / Math.pow(10, quantityDecimal)) : 1;

    if (event.field === 'mode') {
      if (event.data.mode?.value.id === 2) {

        quantityFeild.setValidators([Validators.required, Validators.min(0), dischargeStudyCargoQuantityValidator, numberValidator(quantityDecimal, null, false)]);
        quantityFeild.updateValueAndValidity();
        selectedPortCargo['kl'].value = '0';
        selectedPortCargo['mt'].value = '0';
        selectedPortCargo['bbls'].value = '0';
        selectedPortCargo['kl'].isEditable = true;
        selectedPortCargo['mt'].isEditable = true;
        selectedPortCargo['bbls'].isEditable = false;
        this.updatebackLoadingDetails(event.index, index, 'kl', selectedPortCargo['kl'].value, 'cargoDetail');
        this.updatebackLoadingDetails(event.index, index, 'mt', selectedPortCargo['mt'].value, 'cargoDetail');
        this.updatebackLoadingDetails(event.index, index, 'bbls', selectedPortCargo['bbls'].value, 'cargoDetail');
        selectedPortCargo['kl']['isEditMode'] = true;
        const formControl = backLoadingFormArray.at(event.index).get('kl');
        formControl.markAsDirty();
        formControl.markAsTouched();
        formControl.updateValueAndValidity();
        if (formControl.valid) {
          portDetails = this.onQuantityEditComplete(event, portDetails, selectedPortCargo);
        } else {
          selectedPortCargo['kl'].isEditMode = true;
        }
      } else {
        quantityFeild.setValidators([Validators.required, dischargeStudyCargoQuantityValidator]);
        quantityFeild.updateValueAndValidity();
        selectedPortCargo['kl'].isEditable = false;
        selectedPortCargo['mt'].isEditable = false;
        selectedPortCargo['bbls'].isEditable = false;
        selectedPortCargo['kl'].value = '-';
        selectedPortCargo['mt'].value = '-';
        selectedPortCargo['bbls'].value = '-';
        this.updatebackLoadingDetails(event.index, index, 'kl', selectedPortCargo['kl'].value, 'cargoDetail');
        this.updatebackLoadingDetails(event.index, index, 'mt', selectedPortCargo['mt'].value, 'cargoDetail');
        this.updatebackLoadingDetails(event.index, index, 'bbls', selectedPortCargo['bbls'].value, 'cargoDetail');
        portDetails = this.updateCargoPortDetails(event, index, event.index, portDetails, portDetails[index]['cargoDetail'][event.index]);
        selectedPortCargo['kl']['isEditMode'] = false;
      }

    } else if (event.field === 'kl') {
      const unitConverted = {
        mt: this.quantityPipe.transform(selectedPortCargo['kl'].value, QUANTITY_UNIT.KL, QUANTITY_UNIT.MT, event.data.api.value, event.data.temp.value, -1),
        bbls: this.quantityPipe.transform(selectedPortCargo['kl'].value, QUANTITY_UNIT.KL, QUANTITY_UNIT.BBLS, event.data.api.value, event.data.temp.value, -1),
      }
      selectedPortCargo['mt'].value = isNaN(unitConverted.mt) || !unitConverted.mt ? '0' : unitConverted.mt + '';
      selectedPortCargo['bbls'].value = isNaN(unitConverted.bbls) || !unitConverted.mt ? '0' : unitConverted.bbls + '';
      const formControl = backLoadingFormArray.at(event.index).get('kl');
      formControl.setValue(null);
      formControl.setValue(selectedPortCargo['kl'].value);
      formControl.markAsDirty();
      formControl.markAsTouched();
      formControl.updateValueAndValidity();
      if (formControl.valid) {
        portDetails = this.onQuantityEditComplete(event, portDetails, selectedPortCargo);
      } else {
        selectedPortCargo['kl'].isEditMode = true;
      }
    }
    this.checkFormFieldValidity();
    this.portDetails = [...portDetails];
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
                    if (this.getFormControl(portIndex, key, itemIndex, innerKey)?.valid || (mode?.value?.id === 1 && innerKey === 'kl')) {
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
  * Method for updating cargo in next port while selecting mode auto
  *
  * @private
  * @param {number} index
  * @param {feildIndex} index
  * @param {*} index
  * @param {*} event
  * @memberof DischargeStudyComponent
  */
  updateCargoPortDetails(event: any, index: number, feildIndex: number, portDetails: any, cargo: any) {
    let isCargoAvailable;
    for (let i = index + 1; i < portDetails?.length; i++) {
      isCargoAvailable = portDetails[i].cargoDetail.find((cargoDetailDetails, cargoIndex) => {
        if (cargoDetailDetails.storedKey.value === cargo.storedKey.value) {
          return cargoDetailDetails;
        }
      })
      break;
    }
    if (!isCargoAvailable) {
      portDetails = this.onQuantityEditComplete(event, portDetails, cargo);
    }
    return portDetails;
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
    let isAutoModeAvailable;
    for (let i = 0; i < portDetails?.length; i++) {
      if (parentIndex === undefined) {
        for (let j = 0; j < portDetails[i].cargoDetail.length; j++) {
          const dischargeCargoDetails = portDetails[i].cargoDetail[j];
          if (dischargeCargoDetails.storedKey.value === cargo.storedKey.value) {
            parentIndex = i;
            if (dischargeCargoDetails.mode.value.id === 2) {
              const kl = Number(dischargeCargoDetails.maxKl.value) - Number(dischargeCargoDetails.kl.value);
              totalBackLoadingKlValue = Number(kl?.toFixed(3));
            } else {
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
                if (Number(totalBackLoadingKlValue) >= Number(cargoDetailDetails.kl.value) && (i !== (this.portDetails.length - 1) || isAutoModeAvailable)) {
                  totalBackLoadingKlValue = Number((totalBackLoadingKlValue - Number(cargoDetailDetails.kl.value)).toFixed(3));
                  portDetails = this.updateCargoDetails(event, portDetails, cargoDetailDetails, cargoDetailDetails.kl.value, i, cargoIndex).portDetails;
                } else {
                  portDetails = this.updateCargoDetails(event, portDetails, cargoDetailDetails, totalBackLoadingKlValue, i, cargoIndex).portDetails;
                  totalBackLoadingKlValue = 0;
                }
              } else {
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
    return portDetails;
  }

  /**
  * Method for updating quantity in ports in cargo
  *
  * @private
  * @param {*} cargo
  * @param {*} portDetails
  * @param {*} event
  * @param {number} cargoIndex
  * @param {number} klValue
  * @param {number} index
  * @memberof DischargeStudyComponent
  */
  updateCargoDetails(event: any, portDetails: any, cargo: any, klValue: number, index: number, cargoIndex: number) {
    const duplicateCargoDetails = cargo;
    duplicateCargoDetails.kl.value = klValue;
    const unitConverted = {
      mt: this.quantityPipe.transform(duplicateCargoDetails['kl'].value, QUANTITY_UNIT.KL, QUANTITY_UNIT.MT, event.data.api.value, event.data.temp.value, -1),
      bbls: this.quantityPipe.transform(duplicateCargoDetails['kl'].value, QUANTITY_UNIT.KL, QUANTITY_UNIT.BBLS, event.data.api.value, event.data.temp.value, -1),
    }
    duplicateCargoDetails['mt'].value = unitConverted.mt;
    duplicateCargoDetails['bbls'].value = unitConverted.bbls;
    portDetails[index]['cargoDetail'][cargoIndex] = duplicateCargoDetails;
    const getCargoDetails = this.getFeild(index, 'cargoDetail').get('dataTable') as FormArray;
    for (const key in getCargoDetails.at(cargoIndex).value) {
      if (getCargoDetails.at(cargoIndex).value.hasOwnProperty(key)) {
        const object = getCargoDetails.at(cargoIndex).get(key);
        object.setValue(duplicateCargoDetails[key].value);
      }
    }
    return { portDetails: portDetails }
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
    const unitConverted = {
      mt: this.quantityPipe.transform(duplicateCargoDetails['kl'].value, QUANTITY_UNIT.KL, QUANTITY_UNIT.MT, event.data.api.value, event.data.temp.value, -1),
      bbls: this.quantityPipe.transform(duplicateCargoDetails['kl'].value, QUANTITY_UNIT.KL, QUANTITY_UNIT.BBLS, event.data.api.value, event.data.temp.value, -1),
    }
    duplicateCargoDetails['mt'].value = unitConverted.mt;
    duplicateCargoDetails['bbls'].value = unitConverted.bbls;
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
    let portDetails = [...this.portDetails];
    const data = event.data;
    const feildIndex = event.index;
    let selectedPortCargo = portDetails[index].backLoadingDetails[event.index];
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
      const unitConverted = {
        mt: this.quantityPipe.transform(selectedPortCargo['kl'].value, QUANTITY_UNIT.KL, QUANTITY_UNIT.MT, selectedPortCargo['api'].value, selectedPortCargo['temp'].value, -1),
        bbls: this.quantityPipe.transform(selectedPortCargo['kl'].value, QUANTITY_UNIT.KL, QUANTITY_UNIT.BBLS, selectedPortCargo['api'].value, selectedPortCargo['temp'].value, -1),
      }
      selectedPortCargo['mt'].value = !unitConverted.mt || isNaN(unitConverted.mt) ? 0 : unitConverted.mt;
      selectedPortCargo['bbls'].value = !unitConverted.bbls || isNaN(unitConverted.bbls) ? 0 : unitConverted.bbls;
      this.updatebackLoadingDetails(feildIndex, index, 'mt', selectedPortCargo['mt'].value, 'backLoadingDetails');
      this.updatebackLoadingDetails(feildIndex, index, 'bbls', selectedPortCargo['bbls'].value, 'backLoadingDetails');

      this.updatebackLoadingDetails(feildIndex, index, 'abbreviation', selectedPortCargo['abbreviation'].value, 'backLoadingDetails');
      this.updatebackLoadingDetails(feildIndex, index, 'api', selectedPortCargo['api'].value, 'backLoadingDetails');
      this.updatebackLoadingDetails(feildIndex, index, 'temp', selectedPortCargo['temp'].value, 'backLoadingDetails');
    }
    

    if (!event.data.isAdd && formGroup.get('kl').valid && formGroup.get('api').valid && formGroup.get('temp').valid && event.field === 'kl') {
      portDetails = this.onQuantityEditComplete(event, portDetails, selectedPortCargo);
    } else if (!event.data.isAdd) {
      const unitConverted = {
        mt: this.quantityPipe.transform(selectedPortCargo['kl'].value, QUANTITY_UNIT.KL, QUANTITY_UNIT.MT, event.data.api.value, event.data.temp.value, -1),
        bbls: this.quantityPipe.transform(selectedPortCargo['kl'].value, QUANTITY_UNIT.KL, QUANTITY_UNIT.BBLS, event.data.api.value, event.data.temp.value, -1),
      }
      selectedPortCargo['mt'].value = !unitConverted.mt || isNaN(unitConverted.mt) ? 0 : unitConverted.mt;
      selectedPortCargo['bbls'].value = !unitConverted.bbls || isNaN(unitConverted.bbls) ? 0 : unitConverted.bbls;
      this.updatebackLoadingDetails(feildIndex, index, 'mt', selectedPortCargo['mt'].value, 'backLoadingDetails');
      this.updatebackLoadingDetails(feildIndex, index, 'bbls', selectedPortCargo['bbls'].value, 'backLoadingDetails');

      this.updateDischargeCargoDetails(event, portDetails, event.field);
    }

    selectedPortCargo = this.unitConversion(selectedPortCargo, event, index, 'backLoadingDetails');
    this.checkFormFieldValidity();
    formGroup.get('kl').updateValueAndValidity();
    this.portDetails = [...portDetails];
  }

  /**
  * Method for updating  Discharge Cargo Details
  *
  * @private
  * @param {string} feild
  * @param {*} event
  * @memberof DischargeStudyComponent
  */
  updateDischargeCargoDetails(event: any, portDetails: any, feild: string) {
    for (let i = event.index + 1; i <= portDetails.length; i++) {
      const findCardoIndex = portDetails[i].cargoDetail.findIndex((cargoDetails) => {
        if (cargoDetails.storedKey.value === event.data.storedKey.value) {
          return cargoDetails;
        }
      });
      if (findCardoIndex !== -1) {
        for (const key in event.data) {
          if (event.data.hasOwnProperty(key) && (key === 'color' || key === 'cargo' || key === 'abbreviation' || key === 'api' || key === 'temp')) {
            if (key === 'api') {
              const unitConverted = {
                mt: this.quantityPipe.transform(portDetails[i].cargoDetail[findCardoIndex]['kl'].value, QUANTITY_UNIT.KL, QUANTITY_UNIT.MT, event.data.api.value, event.data.temp.value, -1),
                bbls: this.quantityPipe.transform(portDetails[i].cargoDetail[findCardoIndex]['kl'].value, QUANTITY_UNIT.KL, QUANTITY_UNIT.BBLS, event.data.api.value, event.data.temp.value, -1),
              }
              portDetails[i].cargoDetail[findCardoIndex]['mt'].value = !unitConverted.mt || isNaN(unitConverted.mt) ? 0 : unitConverted.mt;
              this.updatebackLoadingDetails(findCardoIndex, i, 'mt', portDetails[i].cargoDetail[findCardoIndex]['mt'].value , 'cargoDetail');
              portDetails[i].cargoDetail[findCardoIndex]['bbls'].value = !unitConverted.bbls || isNaN(unitConverted.bbls) ? 0 : unitConverted.bbls;
              this.updatebackLoadingDetails(findCardoIndex, i, 'bbls', portDetails[i].cargoDetail[findCardoIndex]['bbls'].value , 'cargoDetail');
            } else {
              portDetails[i].cargoDetail[findCardoIndex][key].value = event.data[key].value;
              this.updatebackLoadingDetails(findCardoIndex, i, key, event.data[key].value, 'cargoDetail');
            }
          }
        }
        this.unitConversion(portDetails[i].cargoDetail[findCardoIndex], event, i, 'cargoDetail');
      }
    }
  }
  /**
  * Method for converting unit
  *
  * @private
  * @param {number} index
  * @param {*} event
  * @param {string} formArrayName
  * @param {string} formControlName
  * @memberof DischargeStudyComponent
  */
  unitConversion(selectedObject: any, event: any, index: number, formArrayName: string) {
    const units = ['kl', 'bbls', 'mt'];
    const unitConverted = {
      mt: this.quantityPipe.transform(selectedObject['kl'].value, QUANTITY_UNIT.KL, QUANTITY_UNIT.MT, event.data.api.value, event.data.temp.value, -1),
      bbls: this.quantityPipe.transform(selectedObject['kl'].value, QUANTITY_UNIT.KL, QUANTITY_UNIT.BBLS, event.data.api.value, event.data.temp.value, -1),
    }
    selectedObject['mt'].value = !unitConverted.mt || isNaN(unitConverted.mt) ? 0 : unitConverted.mt;
    selectedObject['bbls'].value = !unitConverted.bbls || isNaN(unitConverted.bbls) ? 0 : unitConverted.bbls;
    const portDetailsFormArray = this.dischargeStudyForm.get('portDetails') as FormArray;
    const backLoadingFormArray = portDetailsFormArray.at(index).get(formArrayName).get('dataTable') as FormArray;
    for (let i = 0; i < units.length; i++) {
      const formControl = backLoadingFormArray.at(event.index).get(units[i]);
      formControl.setValue(null);
      formControl.setValue(selectedObject[units[i]].value);
    }
    return selectedObject;
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
  * @param {number} feildIndex
  * @param {number} index
  * @param {string} formControlName
  * @param {string} formGroupName
  * @memberof DischargeStudyComponent
  */
  getFormControl(index: number, formControlName: string, feildIndex: number, formGroupName: string) {
    const feild = this.getBackLoadingDetails(feildIndex, index, formControlName).get(formGroupName);
    return feild;
  }

  /**
  * Method for rest of back Loading
  *
  * @private
  * @param {number} feildIndex
  * @param {number} index
  * @memberof DischargeStudyComponent
  */
  getBackLoadingDetails(feildIndex: number, index: number, formControlName: string) {
    const backLoadingDetails = this.getFeild(index, formControlName).get('dataTable') as FormArray;
    return backLoadingDetails.at(feildIndex);
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
    const translationKeys = await this.translateService.get(['PORT','DISCHARGE_STUDY_BACK_LOADING_GREATER_THAN_LOADABLE_QUANTITY','DISCHARGE_STUDY_SAVE_ERROR', 'DISCHARGE_STUDY_SAVE_NO_DATA_ERROR', 'DISCHARGE_STUDY_SAVE_WARNING_SUMMERY', 'DISCHARGE_STUDY_SAVE_WARNING', 'DISCHARGE_STUDY_SUCCESS', 'DISCHARGE_STUDY_SUCCESS_SUMMERY']).toPromise();
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
      if (isLoadableQuantityValid.backLoadingIndex !== undefined) {
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
    this.portDetails.map((item, itemIndex) => {
      let loadableQuantity = 0;
      const backLoadingDetailsFormControls = item.backLoadingDetails;
      backLoadingDetailsFormControls.map((backLoadingItems) => {
        if(backLoadingItems.isAdd) {
          backLoadingIndex = itemIndex;
        }
        if (backLoadingItems.mt) {
          loadableQuantity += Number(backLoadingItems.mt.value);
        }
      })
      const cargoDetails = item.cargoDetail;
      cargoDetails.map((cargoDetailsItems) => {
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
          if(itemIndex === i) { 
            loadableQuantity += maxQuantity;
            break;
          }
          }
        })
        if(this.loadableQuantity < loadableQuantity) {
          invalidPort.push(itemIndex);
        }
    })
    return {invalidPort: invalidPort , backLoadingIndex: backLoadingIndex};
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
        freshCrudeOilQuantity.setValidators([Validators.required,Validators.min(10), Validators.max(1000)]);
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
