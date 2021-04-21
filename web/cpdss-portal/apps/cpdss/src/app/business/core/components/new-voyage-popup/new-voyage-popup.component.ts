import { Input, Output } from '@angular/core';
import { EventEmitter } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { IVessel } from '../../models/vessel-details.model';
import { NewVoyageModel } from '../../models/common.model';
import { VoyageService } from '../../services/voyage.service';
import { TimeZoneTransformationService } from '../../../../shared/services/time-zone-conversion/time-zone-transformation.service';
import { MessageService } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { dateCompareValidator } from '../../directives/date-compare-validator.directive';
import { specialCharacterValidator } from '../../directives/special-character-validator.directive';
import { IDateTimeFormatOptions, ITimeZone } from '../../../../shared/models/common.model';

/**
 * Component for new voyage popup
 */
@Component({
  selector: 'cpdss-portal-new-voyage-popup',
  templateUrl: './new-voyage-popup.component.html',
  styleUrls: ['./new-voyage-popup.component.scss']
})
export class NewVoyagePopupComponent implements OnInit {
  @Input() vesselDetails: IVessel;
  @Output() displayPopUp = new EventEmitter<boolean>();

  showPopUp: boolean;
  newVoyageForm!: FormGroup;
  newVoyageModel!: NewVoyageModel;
  isLoading: boolean;
  isSubmitted: boolean;
  errorMessages: any;
  date: Date;
  globalTimeZones: ITimeZone[];
  startDateTimeZone: ITimeZone;
  endDateTimeZone: ITimeZone;

  constructor(private fb: FormBuilder, private router: Router,
    private voyageApiService: VoyageService,
    private timeZoneTransformationService: TimeZoneTransformationService,
    private messageService: MessageService,
    private translateService: TranslateService,
    private ngxSpinnerService: NgxSpinnerService) { }

  ngOnInit(): void {
    this.date = new Date();
    this.errorMessages = this.voyageApiService.setValidationErrorMessage();
    this.getVesselInfo();
    this.getTimeZoneList();
  }

  /**
   * function to get time zone list
   *
   * @memberof NewVoyagePopupComponent
   */
  async getTimeZoneList() {
    this.globalTimeZones = await this.timeZoneTransformationService.getTimeZoneList().toPromise();
  }

  /**
   * Submit form
   */
  onSubmit() {
    this.isLoading = true;
    const dtFormatOpts: IDateTimeFormatOptions = { customFormat: 'DD-MM-YYYY HH:mm' };
    this.newVoyageModel = new NewVoyageModel();
    this.newVoyageModel.captainId = this.vesselDetails?.captainId;
    this.newVoyageModel.chiefOfficerId = this.vesselDetails?.chiefOfficerId;
    this.newVoyageModel.voyageNo = this.newVoyageForm.value.voyageNo;
    this.newVoyageModel.startDate = this.newVoyageForm.value.start_date ? this.convertToTimeZoneDT(this.newVoyageForm.value.start_date, this.startDateTimeZone.id) : '';
    this.newVoyageModel.endDate = this.newVoyageForm.value.end_date ? this.convertToTimeZoneDT(this.newVoyageForm.value.end_date, this.endDateTimeZone.id) : '';
    this.newVoyageModel.startTimezoneId = this.newVoyageForm.value.start_date ? this.startDateTimeZone.id : null;
    this.newVoyageModel.endTimezoneId = this.newVoyageForm.value.end_date ? this.endDateTimeZone.id : null;
    this.saveNewVoyage();
    this.isLoading = false;
  }

  /**
   * Cancel popup
   */
  cancel() {
    this.displayPopUp.emit(false);
  }

  /**
   * Save new-voyage
   */
  async saveNewVoyage() {
    this.ngxSpinnerService.show();
    const vesselId = this.vesselDetails?.id;
    const translationKeys = await this.translateService.get(['VOYAGE_CREATE_SUCCESS', 'VOYAGE_CREATED_SUCCESSFULLY', 'VOYAGE_CREATE_ERROR', 'VOYAGE_ALREADY_EXIST']).toPromise();
    try {
      const res = await this.voyageApiService.saveNewVoyageData(this.newVoyageModel, vesselId).toPromise();
      if (res.responseStatus.status === "200") {
        this.messageService.add({ severity: 'success', summary: translationKeys['VOYAGE_CREATE_SUCCESS'], detail: translationKeys['VOYAGE_CREATED_SUCCESSFULLY'] });
        this.router.navigate(['business/cargo-planning/loadable-study-list', res.voyageId]);
        this.ngxSpinnerService.hide();
      }
    }
    catch (error) {
      if (error.error.errorCode === 'ERR-RICO-100') {
        this.newVoyageForm.controls['voyageNo'].setErrors({'duplicate': true});
        this.messageService.add({ severity: 'error', summary: translationKeys['VOYAGE_CREATE_ERROR'], detail: translationKeys['VOYAGE_ALREADY_EXIST'] });
      }
      this.ngxSpinnerService.hide();
    }
  }

  /**
   * Assign value and validators
   */
  async getVesselInfo() {
    this.newVoyageForm = this.fb.group({
      'captain': [this.vesselDetails?.captainName],
      'chiefOfficer': [this.vesselDetails?.chiefOfficerName],
      'voyageNo': this.fb.control(null, [Validators.required, specialCharacterValidator, Validators.maxLength(10)]),
      'start_date': this.fb.control(null, [dateCompareValidator('end_date', '<')]),
      'end_date': this.fb.control(null, [dateCompareValidator('start_date', '>')]),
      'selectStartDateTimeZone': this.fb.control(null),
      'selectEndDateTimeZone': this.fb.control(null)
    });
    this.showPopUp = true;
    this.isLoading = false;
    this.isSubmitted = false;
  }

  /**
   * Save button click 
   */
  saveNewVoyagePopup() {
    this.isSubmitted = true;
    if (this.newVoyageForm.valid) {
      this.onSubmit();
    } else {
      this.newVoyageForm.controls.voyageNo.markAsTouched({ onlySelf: true });
      this.newVoyageForm.markAllAsTouched();
    }
  }

  /**
   * Trim blank space entered as the first character in voyage no. field 
   */
  trimVoyageNo() {
    this.newVoyageForm.controls['voyageNo'].setValue((this.newVoyageForm.get('voyageNo').value).trim());
    this.newVoyageForm.controls['voyageNo'].setErrors(null);
  }

  /**
   * Method to update form on date time select,
   * To set conditional validators for start & end date time zones
   */
  onDateSelect(elemRef: any) {
    this.newVoyageForm.controls.start_date.updateValueAndValidity();
    this.newVoyageForm.controls.end_date.updateValueAndValidity();
    this.newVoyageForm.get('selectStartDateTimeZone').setValidators(this.newVoyageForm.value.start_date ? [Validators.required] : null);
    this.newVoyageForm.controls.selectStartDateTimeZone.updateValueAndValidity();
    this.newVoyageForm.get('selectEndDateTimeZone').setValidators(this.newVoyageForm.value.end_date ? [Validators.required] : null);
    this.newVoyageForm.controls.selectEndDateTimeZone.updateValueAndValidity();
    elemRef.hideOverlay();
  }

  /**
   * Get field errors
   *
   *
   * @param {string} formControlName
   * @returns {ValidationErrors}
   * @memberof NewVoyagePopupComponent
   */
  fieldError(formControlName: string): ValidationErrors {
    const formControl = this.field(formControlName);
    return formControl?.invalid && (formControl.dirty || formControl.touched) ? formControl.errors : null;
  }

  /**
  * Get form control of newVoyageForm 
  *
  *
  * @param {string} formControlName
  * @returns {FormControl}
  * @memberof NewVoyagePopupComponent
  */
  field(formControlName: string): FormControl {
    const formControl = <FormControl>this.newVoyageForm.get(formControlName);
    return formControl;
  }

  /**
   * function to convert date-time to time-zone based
   *
   * @param {Date} dateTime
   * @param {number} portTimezoneId
   * @return {*}  {string}
   * @memberof PortRotationRibbonComponent
   */
  convertToTimeZoneDT(dateTime: Date | string, portTimezoneId: number): string {
    if (dateTime && portTimezoneId) {
      const selectedTimeZone: ITimeZone = this.globalTimeZones.find(tz => (tz.id === portTimezoneId));
      const formatOptions: IDateTimeFormatOptions = {
        portLocalFormat: true,
        portTimeZoneOffset: selectedTimeZone?.offsetValue,
        portTimeZoneAbbr: selectedTimeZone?.abbreviation
      };
      return this.timeZoneTransformationService.revertZoneTimetoUTC(dateTime, selectedTimeZone.offsetValue);
    }
  }
}
