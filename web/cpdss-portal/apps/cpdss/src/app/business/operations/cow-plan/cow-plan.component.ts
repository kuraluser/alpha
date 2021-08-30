import { Component, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import * as moment from 'moment';
import { ICargo, ITank } from '../../core/models/common.model';
import { ICOWDetails, IDischargeOperationListData } from '../models/loading-discharging.model';
import { LoadingDischargingTransformationService } from '../services/loading-discharging-transformation.service';

/**
 * Component class for COW plan setion
 *
 * @export
 * @class CowPlanComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-cow-plan',
  templateUrl: './cow-plan.component.html',
  styleUrls: ['./cow-plan.component.scss']
})
export class CowPlanComponent implements OnInit {
  @Input() listData: IDischargeOperationListData;
  @Input() cargoTanks: ITank[];
  @Input() loadedCargos: ICargo[];
  @Input() form: FormGroup;

  @Input()
  get cowDetails(): ICOWDetails {
    return this._cowDetails;
  }

  set cowDetails(value: ICOWDetails) {
    this._cowDetails = value;
    this.enableDisableFieldsOnCowOption(value?.cowOption?.id);
  }

  get cowDetailsForm() {
    return <FormGroup>this.form.get('cowDetails');
  }

  private _cowDetails: ICOWDetails;

  constructor(private loadingDischargingTransformationService: LoadingDischargingTransformationService) { }

  ngOnInit(): void {
  }

  /**
   * Handler for cow option change
   *
   * @param {*} event
   * @memberof CowPlanComponent
   */
  onCowOptionChange(event) {
    this.enableDisableFieldsOnCowOption(event?.value?.id);
  }

  /**
   * Enable Disable field based on cow option selected
   *
   * @param {number} mode
   * @memberof CowPlanComponent
   */
  enableDisableFieldsOnCowOption(mode: number) {
    if (mode === 1) {
      this.cowDetailsForm.controls.cowPercentage.enable();
      this.cowDetailsForm.controls.allCOWTanks.disable();
      this.cowDetailsForm.controls.bottomCOWTanks.disable();
      this.cowDetailsForm.controls.topCOWTanks.disable();
      this.cowDetailsForm.controls.washTanksWithDifferentCargo.disable();
      this.cowDetailsForm.controls.tanksWashingWithDifferentCargo.disable();
    } else {
      this.cowDetailsForm.controls.cowPercentage.disable();
      this.cowDetailsForm.controls.allCOWTanks.enable();
      this.cowDetailsForm.controls.bottomCOWTanks.enable();
      this.cowDetailsForm.controls.topCOWTanks.enable();
      this.cowDetailsForm.controls.washTanksWithDifferentCargo.enable();
      this.enableDisableTanksWashWithDifferentCargoFields(this.cowDetailsForm.controls.washTanksWithDifferentCargo?.value);
    }
  }

  /**
   * Handler for changing wash with different cargo option
   *
   * @param {*} event
   * @memberof CowPlanComponent
   */
  onWashWithDifferentCargoChange(event) {
    this.enableDisableTanksWashWithDifferentCargoFields(event?.value);
  }

  /**
   * Enable disable tanks washing with different cargo 
   *
   * @param {boolean} enable
   * @memberof CowPlanComponent
   */
  enableDisableTanksWashWithDifferentCargoFields(enable: boolean) {
    if (enable) {
      this.cowDetailsForm.controls.tanksWashingWithDifferentCargo.enable();
    } else {
      this.cowDetailsForm.controls.tanksWashingWithDifferentCargo.disable();
    }
  }

  /**
   * Handler for cow start and end time change
   *
   * @memberof CowPlanComponent
   */
  onCowStartEndChange() {
    const totalDurationInMinutes = this.loadingDischargingTransformationService.convertTimeStringToMinutes(this.cowDetails?.totalDuration);
    const startTimeInMinutes = this.loadingDischargingTransformationService.convertTimeStringToMinutes(this.cowDetailsForm.controls?.cowStart.value);
    const endTimeInMinutes = this.loadingDischargingTransformationService.convertTimeStringToMinutes(this.cowDetailsForm.controls?.cowEnd.value);
    const duration = totalDurationInMinutes - startTimeInMinutes - endTimeInMinutes;
    this.cowDetailsForm.controls.cowDuration.setValue(moment.utc(duration * 60 * 1000).format("HH:mm"));
  }

}
