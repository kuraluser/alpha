import { Component, OnInit, Input } from '@angular/core';
import { FormArray, FormGroup, FormBuilder } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { ConfirmationService } from 'primeng/api';
import { NgxSpinnerService } from 'ngx-spinner';

import { DATATABLE_EDITMODE, IDataTableColumn, IDataTableFilterEvent, IDataTableSortEvent } from '../../../../shared/components/datatable/datatable.model';
import { IPermission } from '../../../../shared/models/user-profile.model';
import { Voyage , ITankDetails , IInstruction, ICargo } from '../../../core/models/common.model';
import { QUANTITY_UNIT, IPercentage , IMode } from '../../../../shared/models/common.model';

import { QuantityPipe } from '../../../../shared/pipes/quantity/quantity.pipe';
import { DischargeStudyViewPlanTransformationService } from '../../services/discharge-study-view-plan-transformation.service';
import { IDischargeStudyDropdownData } from '../../models/discharge-study-view-plan.model';

/**
 * Component class of discharge study view plan
 *
 * @export
 * @class ViewPlanComponent
 * @implements {OnInit}
 */

@Component({
  selector: 'cpdss-portal-view-plan',
  templateUrl: './view-plan.component.html',
  styleUrls: ['./view-plan.component.scss']
})
export class ViewPlanComponent implements OnInit {

  @Input() voyageId: number;
  @Input() voyage: Voyage;
  @Input() dischargeStudyId: number;
  @Input() vesselId: number;
  @Input() permission: IPermission;

  columns: IDataTableColumn[];
  backLoadingColumns: IDataTableColumn[];
  dischargeStudyForm: FormGroup;
  portDetails: any[];
  instructions: IInstruction[];
  cowList: IMode[];
  mode: IMode[];
  percentageList: IPercentage[];
  tank: ITankDetails[];
  cargoList: ICargo[];
  listData: IDischargeStudyDropdownData;

  constructor(
    private fb: FormBuilder,
    private quantityPipe: QuantityPipe,
    private translateService: TranslateService,
    private confirmationService: ConfirmationService,
    private ngxSpinnerService: NgxSpinnerService,
    private dischargeStudyViewPlanTransformationService: DischargeStudyViewPlanTransformationService
  ) { }

  /**
   * NgOnit init function for view plan component
   *
   * @memberof ViewPlanComponent
   */
  ngOnInit(): void {
    this.columns = this.dischargeStudyViewPlanTransformationService.getDischargeStudyCargoDatatableColumns();
    this.backLoadingColumns = this.dischargeStudyViewPlanTransformationService.getDischargeStudyBackLoadingDatatableColumns();
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
    //set dummy details need to remove when actual api comes
    const dischargeStudyDetails = [
      {
        portName: 'kirre',
        instruction: null,
        draftRestriction: '20',
        dischargeRate: 3000,
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
            temp: 50,
            time: 2
          }
        ],
        cow: 1,
        percentage: { value: '25', name: '25%' },
        enableBlackLoading: true,
        backLoading: [],
        tankId: 25581,
        enableBackToLoading: true,
        backLoadingDetails: [
          {
            cargoId: 32,
            color: "#2bb13c",
            abbreviation: "ARL",
            name: "Alba",
            bbls: 2000,
            mt: 500,
            kl: 5000,
            mode: 2,
            api: 20,
            temp: 50
          }
        ]
      },
      {
        portName: 'MIzushima',
        dischargeRate: 3000,
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
            temp: 50,
            time: 2
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
        dischargeRate: 3000,
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
            temp: 50,
            time: 5
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
    this.portDetails = dischargeStudyDetails.map((portDetail, index) => {
      const isLastIndex = index + 1 === dischargeStudyDetails.length;
      const portDetailAsValueObject = this.dischargeStudyViewPlanTransformationService.getPortDetailAsValueObject(portDetail, this.listData, isLastIndex, false);
      portDetails.push(this.initDischargeStudyFormGroup(portDetailAsValueObject));
      return portDetailAsValueObject;
    })
  }


  /**
   * Method set dummy details need to remove when actual api comes
   *
   * @private
   * @memberof ViewPlanComponent
   */
  setDummyDetails() {
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
      { name: 'auto', id: 1 },
      { name: 'manual', id: 2 }
    ];
    this.percentageList = [
      { value: 25, name: '25%' },
      { value: 50, name: '50%' },
      { value: 75, name: '75%' },
      { value: 100, name: '100%' },
    ]
  }

  /**
 * Initialize ballast tank form group
 *
 * @private
 * @param {*} portDetail
 * @returns {FormGroup}
 * @memberof ViewPlanComponent
 */
  private initDischargeStudyFormGroup(portDetail: any): FormGroup {
    return this.fb.group({
      portName: this.fb.control(portDetail?.portName),
      instruction: this.fb.control(portDetail?.instruction),
      draftRestriction: this.fb.control(portDetail?.draftRestriction),
      cargoDetail: this.fb.group({ dataTable: this.fb.array(this.dischargeCargoFormGroup(portDetail)) }),
      cow: this.fb.control(portDetail?.cow),
      dischargeRate: this.fb.control(portDetail?.dischargeRate),
      backLoadingDetails: this.fb.group({ dataTable: this.fb.array(this.initBackLoadingFormGroup(portDetail)) })
    });
  }

  /**
   * Method for discharge back loading form Group
   *
   * @private
   * @param {*} portDetail
   * @memberof ViewPlanComponent
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
  * @memberof ViewPlanComponent
  */
  backLoadingFormGroup(backLoading: any) {
    return this.fb.group({
      color: this.fb.control(backLoading.color?.value ? backLoading.color?.value : null),
      abbreviation: this.fb.control(backLoading.abbreviation.value),
      cargo: this.fb.control(backLoading?.cargo?.value ? backLoading.cargo?.value : null),
      bbls: this.fb.control(backLoading.bbls?.value ? backLoading.bbls?.value : null),
      kl: this.fb.control(backLoading.kl?.value ? backLoading.kl?.value : null),
      mt: this.fb.control(backLoading.mt?.value ? backLoading.mt?.value : null),
      api: this.fb.control(backLoading.api?.value ? backLoading.api?.value : null),
      temp: this.fb.control(backLoading.temp?.value ? backLoading.temp?.value : null)
    })
  }

  /**
  * Method for discharge cargo fild
  *
  * @private
  * @param {*} portDetail
  * @memberof ViewPlanComponent
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
  * @memberof ViewPlanComponent
  */
  initCargoFormGroup(cargo) {
    return this.fb.group({
      maxKl: this.fb.control(cargo.maxKl.value),
      abbreviation: this.fb.control(cargo.abbreviation.value),
      cargo: this.fb.control(cargo.cargo.value),
      color: this.fb.control(cargo.color.value),
      bbls: this.fb.control(cargo.bbls.value),
      kl: this.fb.control(cargo.kl.value),
      mt: this.fb.control(cargo.mt.value),
      mode: this.fb.control(cargo.mode?.value),
      api: this.fb.control(cargo.api?.value),
      temp: this.fb.control(cargo.temp?.value),
    })
  }

  /**
 * Method for geting form field
 *
 * @private
 * @param {number} index
 * @param {string} field
 * @param {*} value
 * @memberof ViewPlanComponent
 */
  getFeild(index: number, formGroupName: string, property?: string) {
    const portDetails = this.dischargeStudyForm.get('portDetails') as FormArray;
    if (formGroupName === 'backLoadingDetails' || formGroupName === 'cargoDetail') {
      return portDetails.at(index).get(formGroupName);
    } else {
      return property ? portDetails.at(index).get(formGroupName).value[property] : portDetails.at(index).get(formGroupName).value;
    }
  }

}
