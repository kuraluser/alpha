import { Component, OnInit, Input } from '@angular/core';
import { AbstractControl, FormArray, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators, FormBuilder } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { NgxSpinnerService } from 'ngx-spinner';
import { v4 as uuid4 } from 'uuid';


import { IDischargeStudy, IDischargeStudyDropdownData } from '../../models/discharge-study-list.model';

import { DATATABLE_EDITMODE, IDataTableColumn, IDataTableFilterEvent, IDataTableSortEvent } from '../../../../shared/components/datatable/datatable.model';
import { IPermission } from '../../../../shared/models/user-profile.model';
import { Voyage } from '../../../core/models/common.model';
import { QUANTITY_UNIT, ITankDetails , IPercentage , IInstruction , IMode , ICargo } from '../../../../shared/models/common.model';

import { DischargeStudyDetailsTransformationService } from '../../services/discharge-study-details-transformation.service';

import { QuantityPipe } from '../../../../shared/pipes/quantity/quantity.pipe';
import { dischargeStudyColorValidator } from '../../directives/validator/discharge-study-color.directive';
import { alphabetsOnlyValidator } from '../../directives/validator/cargo-nomination-alphabets-only.directive';
import { dischargeStudyCargoQuantityValidator } from '../../directives/validator/discharge-study-cargo-quantity.directive';
import { dischargeStudyAbbreviationValidator } from '../../directives/validator/discharge-study-abbreviation.directive';
import { numberValidator } from '../../../core/directives/number-validator.directive';
import { whiteSpaceValidator } from '../../../core/directives/space-validator.directive';

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

  @Input()
  get dischargeStudy() {
    return this._dischargeStudy;
  }
  set dischargeStudy(value: IDischargeStudy) {
    this._dischargeStudy = value;
  }

  editMode: DATATABLE_EDITMODE = DATATABLE_EDITMODE.CELL;
  portDetails: any[];
  instructions: IInstruction[];
  cowList: IInstruction[];
  mode: IMode[];
  percentageList: IPercentage[];
  tank: ITankDetails[];
  dischargeStudyForm: FormGroup;
  columns: IDataTableColumn[];
  backLoadingColumns: IDataTableColumn[];
  listData: IDischargeStudyDropdownData;
  cargoList: ICargo[];
  colorCodeStorageId: any = [];
  errorMessages: any;
  private _dischargeStudy: IDischargeStudy;

  constructor(
    private fb: FormBuilder,
    private quantityPipe: QuantityPipe,
    private translateService: TranslateService,
    private confirmationService: ConfirmationService,
    private ngxSpinnerService: NgxSpinnerService,
    private dischargeStudyDetailsTransformationService: DischargeStudyDetailsTransformationService
  ) { }

  /**
   * NgOnit init function for discharge study component
   *
   * @memberof DischargeStudyComponent
   */
  ngOnInit(): void {
    this.errorMessages = this.dischargeStudyDetailsTransformationService.setValidationMessageForDischargeStudy();
    this.columns = this.dischargeStudyDetailsTransformationService.getDischargeStudyCargoDatatableColumns();
    this.backLoadingColumns = this.dischargeStudyDetailsTransformationService.getDischargeStudyBackLoadingDatatableColumns();
    this.dischargeStudyForm = this.fb.group({
      portDetails: this.fb.array([])
    })
    this.setDummyDetails();
    this.listData = {
      mode: this.mode,
      tank: this.tank,
      instructions: this.instructions,
      cargoList: this.cargoList
    }
    const dischargeStudyDetails = [
      {
        portName: 'kirre',
        instruction: null,
        draftRestriction: '20',
        cargo: [
          {
            cargoId: 32,
            color: "#2bb13c",
            abbreviation: "ARL",
            name: "Alba",
            bbls: 2000,
            mt: 500,
            kl: 5000,
            maxKl: 5000,
            mode: 2,
            api: 20,
            temp: 50
          }
        ],
        cow: 1,
        percentage: { value: '25', name: '25%' },
        enableBlackLoading: true,
        backLoading: [],
        tankId: 25581,
        enableBackToLoading: true,
        backLoadingDetails: []
      },
      {
        portName: 'MIzushima',
        instruction: { value: 'Instruction 2' },
        draftRestriction: '20',
        cargo: [
          {
            cargoId: 202,
            color: "#c6adff",
            abbreviation: "ABS",
            name: "Alba",
            bbls: 2000,
            mt: 500,
            kl: 4000,
            maxKl: 4000,
            mode: 2,
            api: 20,
            temp: 50
          }
        ],
        cow: 1,
        percentage: { value: '25', name: '25%' },
        enableBlackLoading: true,
        backLoading: [],
        enableBackToLoading: true,
        backLoadingDetails: []
      },

      {
        portName: 'MIzushima 123',
        instruction: { value: 'Instruction 2' },
        draftRestriction: '20',
        cargo: [
          {
            cargoId: 252,
            color: "#c6ad98",
            abbreviation: "MBN",
            name: "Alba",
            bbls: 2000,
            mt: 500,
            kl: 3000,
            maxKl: 3000,
            mode: 2,
            api: 20,
            temp: 50
          }
        ],
        cow: 1,
        percentage: { value: '25', name: '25%' },
        enableBlackLoading: true,
        backLoading: [],
        enableBackToLoading: false,
        backLoadingDetails: []
      }
    ]
    const portDetails = this.dischargeStudyForm.get('portDetails') as FormArray;
    const portUniqueColorAbbrList = [];
    this.portDetails = dischargeStudyDetails.map((portDetail, index) => {
      const isLastIndex = index + 1 === dischargeStudyDetails.length;
      const portDetailAsValueObject = this.dischargeStudyDetailsTransformationService.getPortDetailAsValueObject(portDetail, this.listData, isLastIndex, false, portUniqueColorAbbrList);
      portDetails.push(this.initDischargeStudyFormGroup(portDetailAsValueObject));
      return portDetailAsValueObject;
    })
  }

  /**
   * Method set dummy details need to remove when actual api comes
   *
   * @private
   * @memberof DischargeStudyComponent
   */
  setDummyDetails() {
    this.instructions = [
      { value: 'Instruction 1' },
      { value: 'Instruction 2' },
    ];
    this.cargoList = [
      {
        abbreviation: "ARL",
        actualWeight: null,
        api: null,
        companyId: null,
        id: 32,
        name: "Arabian Light",
        plannedWeight: null,
        temp: "30"
      },
      {
        abbreviation: "ABS",
        actualWeight: null,
        api: 29.0000,
        companyId: null,
        id: 4,
        name: "Abu Safah",
        plannedWeight: null,
        temp: "35.0000"
      },
      {
        abbreviation: "KWE",
        actualWeight: null,
        api: null,
        companyId: null,
        id: 202,
        name: "Kuwait Export",
        plannedWeight: null,
        temp: '50'
      },
      {
        abbreviation: "MBN",
        actualWeight: null,
        api: null,
        companyId: null,
        id: 252,
        name: "Murban",
        plannedWeight: null,
        temp: "50"
      },
      {
        abbreviation: "",
        actualWeight: null,
        api: null,
        companyId: null,
        id: 106,
        name: "Costayaco",
        plannedWeight: null,
        temp: '500',
      }
    ]
    this.tank = [
      {
        displayOrder: 1,
        group: 5,
        id: 25580,
        name: "NO.1 CENTER CARGO OIL TANK",
        order: 2,
        shortName: "1C",
        slopTank: false,
      }, {
        displayOrder: 2,
        group: 4,
        id: 25581,
        name: "NO.2 CENTER CARGO OIL TANK",
        order: 2,
        shortName: "2C",
        slopTank: false,
      }
    ]
    this.mode = [
      { name: 'auto', id: 1 },
      { name: 'manual', id: 2 }
    ]
    this.cowList = [
      { value: 'Instruction 1' },
      { value: 'Instruction 2' },
    ];
    this.percentageList = [
      { value: '25', name: '25%' },
      { value: '50', name: '50%' },
      { value: '75', name: '75%' },
      { value: '100', name: '100%' },
    ]
  }

  /**
 * Initialize ballast tank form group
 *
 * @private
 * @param {*} portDetail
 * @returns {FormGroup}
 * @memberof DischargeStudyComponent
 */
  private initDischargeStudyFormGroup(portDetail: any): FormGroup {
    return this.fb.group({
      portName: this.fb.control(portDetail?.portName),
      instruction: this.fb.control(portDetail?.instruction),
      draftRestriction: this.fb.control(portDetail?.draftRestriction, [whiteSpaceValidator , numberValidator(2)]),
      cargoDetail: this.fb.group({ dataTable: this.fb.array(this.dischargeCargoFormGroup(portDetail)) }),
      cow: this.fb.control(portDetail?.cow),
      percentage: this.fb.control(portDetail?.percentage),
      tank: this.fb.control(portDetail?.tank),
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
    return this.fb.group({
      color: this.fb.control(backLoading.color?.value ? backLoading.color?.value : null, [Validators.required, dischargeStudyColorValidator]),
      abbreviation: this.fb.control(backLoading.abbreviation.value, [Validators.required, alphabetsOnlyValidator, Validators.maxLength(6), dischargeStudyAbbreviationValidator]),
      cargo: this.fb.control(backLoading?.cargo?.value ? backLoading.cargo?.value : null, [Validators.required]),
      bbls: this.fb.control(backLoading.bbls?.value ? backLoading.bbls?.value : null, []),
      kl: this.fb.control(backLoading.kl?.value ? backLoading.kl?.value : null, [Validators.required]),
      mt: this.fb.control(backLoading.mt?.value ? backLoading.mt?.value : null, []),
      api: this.fb.control(backLoading.api?.value ? backLoading.api?.value : null, [Validators.required, Validators.min(0), numberValidator(2, 3)]),
      temp: this.fb.control(backLoading.temp?.value ? backLoading.temp?.value : null, [Validators.required , numberValidator(2, 3)]),
      storedKey: this.fb.control(backLoading?.storedKey?.value)
    })
  }

  /**
  * Method for discharge cargo fild
  *
  * @private
  * @param {*} portDetail
  * @memberof DischargeStudyComponent
  */
  dischargeCargoFormGroup(portDetail: any) {
    const cargoFormGroup = portDetail.cargoDetail.map((cargo) => {
      return this.initCargoFormGroup(cargo);
    })
    return cargoFormGroup;
  }

  /**
  * Method for discharge cargo fild
  *
  * @private
  * @param {*} portDetail
  * @memberof DischargeStudyComponent
  */
  initCargoFormGroup(cargo) {
    return this.fb.group({
      maxKl: this.fb.control(cargo.maxKl.value, []),
      abbreviation: this.fb.control(cargo.abbreviation.value, []),
      cargo: this.fb.control(cargo.cargo.value),
      color: this.fb.control(cargo.color.value),
      bbls: this.fb.control(cargo.bbls.value),
      kl: this.fb.control(cargo.kl.value, [Validators.required, dischargeStudyCargoQuantityValidator]),
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
   * @param {*} portDetail
   * @memberof DischargeStudyComponent
  */
  addBackLoading(index: number, formGroupName: string) {
    const storedKey = uuid4();
    const backLoadingDetails = { color: '', bbls: '', kl: '', api: '', temp: '', mt: '', cargoId: null };
    const backLoadingDetailsValueAsObject = this.dischargeStudyDetailsTransformationService.getBackLoadingDetailAsValueObject(backLoadingDetails, this.listData, storedKey , true);
    const portDetails = this.dischargeStudyForm.get('portDetails') as FormArray;
    const backLoading = portDetails.at(index).get(formGroupName).get('dataTable') as FormArray;
    backLoading.push(this.backLoadingFormGroup(backLoadingDetailsValueAsObject));
    this.dischargeStudyForm.updateValueAndValidity();
    this.portDetails[index]['backLoadingDetails'] = [...this.portDetails[index]['backLoadingDetails'], backLoadingDetailsValueAsObject];
    this.portDetails = [...this.portDetails];
  }

  /**
* Method for deleting form field
* @param {*} event
* @param {*} portDetail
* @memberof DischargeStudyComponent
*/
  async onDeleteRow(event: any, index: number, formGroupName: string) {
    if (event?.data?.isDelete) {
      const backLoadingDetails = this.portDetails[index]['backLoadingDetails'];
      const valueIndex = backLoadingDetails.findIndex(backLoadingDetail => backLoadingDetail?.id === event?.data?.id);
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
          let res;
          const portDetails = this.dischargeStudyForm.get('portDetails') as FormArray;
          for (let i = index + 1; i < this.portDetails.length; i++) {
            const cargoDetails = this.portDetails[i]['cargoDetail'];
            const portCargo = cargoDetails.filter((cargo, cargoIndex) => {
              if (event.data.storedKey.value === cargo.storedKey.value) {
                const backLoadingFormArray = portDetails.at(i).get('cargoDetail').get('dataTable') as FormArray;
                backLoadingFormArray.removeAt(cargoIndex);
              } else {
                return cargo;
              }
            });
            this.portDetails[i]['cargoDetail'] = portCargo;
            backLoadingDetails.splice(event.index, 1);
            const backLoading = portDetails.at(index).get(formGroupName).get('dataTable') as FormArray;
            backLoading.removeAt(event.index);
            this.portDetails[index]['backLoadingDetails'] = [...this.portDetails[index]['backLoadingDetails']];
            this.dischargeStudyForm.updateValueAndValidity();
          }
          this.ngxSpinnerService.hide();
        }
      });
    }
  }

  /**
   * Method for save back loading details
   * @param {*} event
   * @param {*} portDetail
   * @memberof DischargeStudyComponent
  */
  async onSaveRow(event: any, index: number, formGroupName: string) {
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
      const newcargo = this.dischargeStudyDetailsTransformationService.setNewCargoInPortAsValuObject(selectedBackLoadingDetails, this.mode[1]);
      let nextCargoDetails = portDetails[index + 1].cargoDetail;
      const getCargoDetails = this.getFeild(index + 1, 'cargoDetail').get('dataTable') as FormArray;
      getCargoDetails.push(this.initCargoFormGroup(newcargo));
      nextCargoDetails = [...nextCargoDetails, newcargo];
      portDetails[index + 1].cargoDetail = nextCargoDetails;
      this.portDetails = [...portDetails];
    } else {
      formGroup.markAllAsTouched();
      formGroup.markAsDirty();
      this.dischargeStudyForm.updateValueAndValidity();
    }
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

    if (event.field === 'mode') {
      if (event.data.mode?.value.id === 2) {
        selectedPortCargo['kl'].value = 0;
        selectedPortCargo['mt'].value = 0;
        selectedPortCargo['bbls'].value = 0;
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
        mt: this.quantityPipe.transform(selectedPortCargo['kl'].value, QUANTITY_UNIT.KL, QUANTITY_UNIT.MT, event.data.api.value, event.data.temp.value),
        bbls: this.quantityPipe.transform(selectedPortCargo['kl'].value, QUANTITY_UNIT.KL, QUANTITY_UNIT.BBLS, event.data.api.value, event.data.temp.value),
      }
      selectedPortCargo['mt'].value = unitConverted.mt;
      selectedPortCargo['bbls'].value = unitConverted.bbls;
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
  checkFormFieldValidity(updateFieldValidity?:string) {
    this.portDetails.map((portDetail, portIndex) => {
      for (const key in portDetail) {
        if (portDetail.hasOwnProperty(key)) {
          if (key === 'cargoDetail' || key === 'backLoadingDetails') {
            portDetail[key].map((item, itemIndex) => {
              for (const innerKey in item) {
                if (item.hasOwnProperty(innerKey) && (typeof updateFieldValidity === 'undefined' || updateFieldValidity === innerKey)) {
                  const field = this.getFormControl(portIndex, key, itemIndex, innerKey);
                  if (field) {
                    field.updateValueAndValidity();
                    field.markAsTouched();
                    field.markAsDirty();
                    this.getFormControl(portIndex, key, itemIndex, innerKey)?.valid ? item[innerKey].isEditMode = false : item[innerKey].isEditMode = true;
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
            if(dischargeCargoDetails.mode.value.id === 2) {
              totalBackLoadingKlValue = Number(dischargeCargoDetails.maxKl.value) - Number(dischargeCargoDetails.kl.value);
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
                  totalBackLoadingKlValue -= Number(cargoDetailDetails.kl.value);
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
            const newcargo = this.dischargeStudyDetailsTransformationService.setNewCargoInPortAsValuObject(cargo, this.mode[1]);
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
      mt: this.quantityPipe.transform(duplicateCargoDetails['kl'].value, QUANTITY_UNIT.KL, QUANTITY_UNIT.MT, event.data.api.value, event.data.temp.value),
      bbls: this.quantityPipe.transform(duplicateCargoDetails['kl'].value, QUANTITY_UNIT.KL, QUANTITY_UNIT.BBLS, event.data.api.value, event.data.temp.value),
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
      mt: this.quantityPipe.transform(duplicateCargoDetails['kl'].value, QUANTITY_UNIT.KL, QUANTITY_UNIT.MT, event.data.api.value, event.data.temp.value),
      bbls: this.quantityPipe.transform(duplicateCargoDetails['kl'].value, QUANTITY_UNIT.KL, QUANTITY_UNIT.BBLS, event.data.api.value, event.data.temp.value),
    }
    duplicateCargoDetails['mt'].value = unitConverted.mt;
    duplicateCargoDetails['bbls'].value = unitConverted.bbls;
    const newcargo = this.dischargeStudyDetailsTransformationService.setNewCargoInPortAsValuObject(duplicateCargoDetails, this.mode[1]);
    const getCargoDetails = this.getFeild(index, 'cargoDetail').get('dataTable') as FormArray;
    portDetails[index].cargoDetail.push(newcargo);
    getCargoDetails.push(this.initCargoFormGroup(newcargo));
  }

  /**
  * Method for updating  Back loading
  *
  * @private
  * @param {number} index
  * @param {*} event
  * @memberof DischargeStudyComponent
  */
  onEditCompleteBackLoading(event: any, index: number) {
    let portDetails = [...this.portDetails];
    const data = event.data;
    const feildIndex = event.index;
    let selectedPortCargo = portDetails[index].backLoadingDetails[event.index];
    if (event.field === 'cargo') {
      selectedPortCargo['abbreviation'].value = data.cargo.value.abbreviation;
      selectedPortCargo['api'].value = data.cargo.value.api;
      selectedPortCargo['temp'].value = data.cargo.value.temp;
      this.updatebackLoadingDetails(feildIndex, index, 'abbreviation', selectedPortCargo['abbreviation'].value, 'backLoadingDetails');
      this.updatebackLoadingDetails(feildIndex, index, 'api', selectedPortCargo['api'].value, 'backLoadingDetails');
      this.updatebackLoadingDetails(feildIndex, index, 'temp', selectedPortCargo['temp'].value, 'backLoadingDetails');
    } else if (event.field === 'kl') {
      selectedPortCargo = this.unitConversion(selectedPortCargo, event, index, 'backLoadingDetails');
      if (!event.data.isAdd && event.data?.kl?.value !== '') {
        portDetails = this.onQuantityEditComplete(event, portDetails, selectedPortCargo);
      }
    };
    
    const formGroup = this.getBackLoadingDetails(event.index, index, 'backLoadingDetails');
    if (formGroup.valid) {
      selectedPortCargo = this.unitConversion(selectedPortCargo, event, index, 'backLoadingDetails');
    }

    if (!event.data.isAdd) {
      this.updateDischargeCargoDetails(event,portDetails,event.field);
      this.checkFormFieldValidity();
    } else {
      this.checkFormFieldValidity();
    }
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
  updateDischargeCargoDetails(event: any,portDetails: any,feild: string) {
    for(let i=event.index+1;i<portDetails.length;i++) {
      const findCardoIndex = portDetails[i].cargoDetail.findIndex((cargoDetails) => {
        if(cargoDetails.storedKey.value === event.data.storedKey.value) {
          return cargoDetails;
        }
      });
      if(findCardoIndex !== -1) {
        for(const key in event.data){
          if(event.data.hasOwnProperty(key) && (key === 'color' || key === 'cargo' || key === 'abbreviation' || key === 'api' || key === 'temp')) {
            portDetails[i].cargoDetail[findCardoIndex][key].value = event.data[key].value;
            this.updatebackLoadingDetails(findCardoIndex,i,key,event.data[key].value,'cargoDetail');
          }
        }
        this.unitConversion(portDetails[i].cargoDetail[findCardoIndex], event , i , 'cargoDetail');
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
    const units = ['kl','bbls','mt'];
    const unitConverted = {
      mt: this.quantityPipe.transform(selectedObject['kl'].value, QUANTITY_UNIT.KL, QUANTITY_UNIT.MT, event.data.api.value, event.data.temp.value),
      bbls: this.quantityPipe.transform(selectedObject['kl'].value, QUANTITY_UNIT.KL, QUANTITY_UNIT.BBLS, event.data.api.value, event.data.temp.value),
    }
    selectedObject['mt'].value = unitConverted.mt;
    selectedObject['bbls'].value = unitConverted.bbls;
    const portDetailsFormArray = this.dischargeStudyForm.get('portDetails') as FormArray;
    const backLoadingFormArray = portDetailsFormArray.at(index).get(formArrayName).get('dataTable') as FormArray;
    for(let i=0;i<units.length;i++) {
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
  updatebackLoadingDetails(feildIndex: number, index: number, formGroupName: string, value: string, formArrayName: string) {
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
              if(cargoIndex !== -1) {
                cargoDetail.splice(cargoIndex,1);
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
      backLoading.controls = [];
      this.portDetails = [...initPortDetails];
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
}
