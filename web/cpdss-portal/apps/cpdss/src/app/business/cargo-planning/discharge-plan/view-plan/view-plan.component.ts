import { Component, OnInit, Input , Output , EventEmitter } from '@angular/core';
import { FormArray, FormGroup, FormBuilder } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { ConfirmationService } from 'primeng/api';
import { NgxSpinnerService } from 'ngx-spinner';

import { DATATABLE_EDITMODE, IDataTableColumn, IDataTableFilterEvent, IDataTableSortEvent } from '../../../../shared/components/datatable/datatable.model';
import { IPermission } from '../../../../shared/models/user-profile.model';
import { Voyage, ITankDetails, IInstruction, ICargo, IPort } from '../../../core/models/common.model';
import { QUANTITY_UNIT, IPercentage, IMode } from '../../../../shared/models/common.model';

import { QuantityPipe } from '../../../../shared/pipes/quantity/quantity.pipe';
import { DischargeStudyViewPlanTransformationService } from '../../services/discharge-study-view-plan-transformation.service';
import { IDischargeStudyDropdownData , IDischargeStudyDetailsResponse , IDischargeStudyPortListDetails } from '../../models/discharge-study-view-plan.model';
import { DischargePlanApiService } from '../../services/discharge-plan-api.service'
import { IDischargeStudy } from '../../models/discharge-study-list.model';

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
  @Input() ports: IPort[];
  @Input() cargos: ICargo[];
  @Input()
   set dischargeStudy(dischargeStudy: IDischargeStudy) {
    this._dischargeStudy = dischargeStudy;
    this.getDischargeStudyDetails();
   }
   get dischargeStudy() {
     return this._dischargeStudy;
   }

   @Output() dischargeStudyPlan = new EventEmitter<IDischargeStudyPortListDetails[]>();
   @Output() dischargePatternIdChange = new EventEmitter<number>();

  columns: IDataTableColumn[];
  backLoadingColumns: IDataTableColumn[];
  dischargeStudyForm: FormGroup;
  portDetails: any[];
  instructions: IInstruction[];
  cowList: IMode[];
  mode: IMode[];
  percentageList: IPercentage[];
  tank: ITankDetails[];
  listData: IDischargeStudyDropdownData;
  
  private _dischargeStudy: IDischargeStudy;

  constructor(
    private fb: FormBuilder,
    private quantityPipe: QuantityPipe,
    private translateService: TranslateService,
    private confirmationService: ConfirmationService,
    private ngxSpinnerService: NgxSpinnerService,
    private dischargePlanApiService: DischargePlanApiService,
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
  }

  /**
 * Method to get discharge study details
 *
 * @private
 * @memberof ViewPlanComponent
 */
  private async getDischargeStudyDetails() {
    this.ngxSpinnerService.show();
    await this.setDropDownDetails();
    const instruction = await this.dischargePlanApiService.getInstructionDetails().toPromise();
    const tankList = await this.dischargePlanApiService.getTankDetails(this.vesselId).toPromise();
    
    this.instructions = instruction.instructions;
    this.tank = tankList.cargoVesselTanks;

    this.listData = {
      mode: this.mode,
      tank: this.tank,
      portList: this.ports,
      cargoList: this.cargos,
      instructions: this.instructions,
      percentageList: this.percentageList
    }

    //set dummy details need to remove when actual api comes
    const result = await this.dischargePlanApiService.getDischargeStudyDetails(this.vesselId, this.vesselId,this.dischargeStudyId).toPromise();
    const dischargeStudyDetails = result.portList;
    this.dischargePatternIdChange.emit(result.dischargePatternId);
    const portDetails = this.dischargeStudyForm.get('portDetails') as FormArray;
    this.portDetails = dischargeStudyDetails.map((portDetail, index) => {
      const isLastIndex = index + 1 === dischargeStudyDetails.length;
      const portDetailAsValueObject = this.dischargeStudyViewPlanTransformationService.getPortDetailAsValueObject(portDetail, this.listData, isLastIndex, false);
      portDetails.push(this.initDischargeStudyFormGroup(portDetailAsValueObject));
      return portDetailAsValueObject;
    })
    this.dischargeStudyPlan.emit(result.portList ? result.portList: []);
    this.ngxSpinnerService.hide();
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
      sequenceNo:this.fb.control(portDetail?.sequenceNo),
      isBackLoadingEnabled:this.fb.control(portDetail?.isBackLoadingEnabled),
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
      abbreviation: this.fb.control(cargo.abbreviation.value),
      cargo: this.fb.control(cargo.cargo.value),
      color: this.fb.control(cargo.color.value),
      bbls: this.fb.control(cargo.bbls.value),
      kl: this.fb.control(cargo.kl.value),
      mt: this.fb.control(cargo.mt.value),
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
      return property && portDetails.at(index).get(formGroupName).value ? portDetails.at(index).get(formGroupName).value[property] : portDetails.at(index).get(formGroupName)?.value;
    }
  }

}
