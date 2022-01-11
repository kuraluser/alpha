import { Component, EventEmitter, Input, OnInit, Output, ViewChild, ElementRef, OnDestroy } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { TranslateService } from '@ngx-translate/core';
import { MessageService } from 'primeng/api';
import { saveAs } from 'file-saver';
import { NgxSpinnerService } from 'ngx-spinner';

import { loadingDetailsValidator } from '../directives/validator/loading-details-time-validator.directive';
import { numberValidator } from '../../core/directives/number-validator.directive';
import { dateInRangeValidator } from '../directives/validator/date-in-range-validator.directive';

import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { PermissionsService } from '../../../shared/services/permissions/permissions.service';
import { TimeZoneTransformationService } from '../../../shared/services/time-zone-conversion/time-zone-transformation.service';
import { LoadingDischargingTransformationService } from '../services/loading-discharging-transformation.service';
import { LoadingDischargingInformationApiService } from '../services/loading-discharging-information-api.service';

import { ILoadingDischargingDetails } from '../models/loading-discharging.model';
import { IPermission } from '../../../shared/models/user-profile.model';
import { OPERATIONS } from '../../core/models/common.model';

@Component({
  selector: 'cpdss-portal-loading-discharging-details',
  templateUrl: './loading-discharging-details.component.html',
  styleUrls: ['./loading-discharging-details.component.scss']
})

/**
 * Component class for loading discharging details component
 *
 * @export
 * @class LoadingDischargingDetailsComponent
 * @implements {OnInit}
 */
export class LoadingDischargingDetailsComponent implements OnInit, OnDestroy {

  @ViewChild('fileUpload') fileUploadVariable: ElementRef;

  @Input() operation: OPERATIONS;
  @Input() loadingInfoId: number;
  @Input() dischargeInfoId: number;

  @Input()
  get loadingDischargingDetails(): ILoadingDischargingDetails {
    return this._loadingDischargingDetails;
  }
  set loadingDischargingDetails(loadingDischargingDetails: ILoadingDischargingDetails) {
    this._loadingDischargingDetails = loadingDischargingDetails;
    this.getPagePermission();
    this.initLoadingDischargingDetailsForm();
  }

  @Output() updateLoadingDischargingDetails: EventEmitter<ILoadingDischargingDetails> = new EventEmitter();

  private _loadingDischargingDetails: ILoadingDischargingDetails;

  readonly OPERATIONS = OPERATIONS;
  loadingDischargingDetailsForm: FormGroup;
  errorMessages: any;
  loadingDischargingDetailsResponse: ILoadingDischargingDetails;
  timeOfSunrisePermission: IPermission;
  timeOfSunsetPermission: IPermission;
  selectedTime: any;
  isDischargeStarted: boolean;
  datePickerFormat: string;
  private ngUnsubscribe: Subject<any> = new Subject();

  constructor(
    private fb: FormBuilder,
    private translateService: TranslateService,
    private timeZoneTransformationService: TimeZoneTransformationService,
    private permissionsService: PermissionsService,
    private messageService: MessageService,
    private ngxSpinnerService: NgxSpinnerService,
    private loadingDischargingInformationApiService: LoadingDischargingInformationApiService,
    private loadingDischargingTransformationService: LoadingDischargingTransformationService
  ) { }

  ngOnInit(): void {
    this.getPagePermission();
    this.datePickerFormat = this.timeZoneTransformationService.getMappedConfigurationDateFormat(AppConfigurationService.settings?.dateFormat);
    this.errorMessages = this.loadingDischargingTransformationService.setValidationMessageForLoadingDetails();
    this.initSubscriptions();
  }

  /**
  * unsubscribing loading info observable
  * @memberof LoadingDischargingDetailsComponent
  */
  ngOnDestroy() {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }


  /**
   * Initialization for all subscriptions
   *
   * @private
   * @memberof LoadingDischargingDetailsComponent
  */
  initSubscriptions() {
    this.loadingDischargingTransformationService.isDischargeStarted$.pipe(takeUntil(this.ngUnsubscribe)).subscribe((value) => {
      this.isDischargeStarted = value;
    })
  }

  /**
  * Method for initialise loading discharging details form
  *
  * @memberof LoadingDischargingDetailsComponent
  */
  initLoadingDischargingDetailsForm() {
    let timeOfSunriseValidation = [];
    let timeOfSunsetValidation = [];
    if (this.timeOfSunrisePermission.view || this.timeOfSunrisePermission.view === undefined) {
      timeOfSunriseValidation = [Validators.required];
    }
    if (this.timeOfSunsetPermission.view || this.timeOfSunsetPermission.view === undefined) {
      timeOfSunsetValidation = [Validators.required];
    }
    if (this.timeOfSunrisePermission.view && this.timeOfSunsetPermission.view) {
      timeOfSunsetValidation = [...timeOfSunsetValidation, loadingDetailsValidator('timeOfSunrise', '>')];
      timeOfSunriseValidation = [...timeOfSunriseValidation, loadingDetailsValidator('timeOfSunset', '<')];
    }
    this.loadingDischargingDetailsResponse = this.loadingDischargingDetails;

    const commonDateObj = this.dateStringToDate(this.loadingDischargingDetails?.commonDate);
    const etaObj = this.dateStringToDate(this.loadingDischargingDetails?.eta);
    const etdObj = this.dateStringToDate(this.loadingDischargingDetails?.etd);
    this.loadingDischargingDetailsForm = this.fb.group({
      commonDate: this.fb.control(commonDateObj, [Validators.required, dateInRangeValidator(etaObj, etdObj)]),
      timeOfSunrise: this.fb.control(this.getDateByDate(this.loadingDischargingDetails?.timeOfSunrise), timeOfSunriseValidation),
      timeOfSunset: this.fb.control(this.getDateByDate(this.loadingDischargingDetails?.timeOfSunset), timeOfSunsetValidation),
      startTime: this.fb.control(this.getDateByDate(this.loadingDischargingDetails?.startTime)),
      initialTrim: this.fb.control(this.loadingDischargingDetails.trimAllowed?.initialTrim, [Validators.required, numberValidator(2, 1), Validators.min(0), Validators.max(4)]),
      maximumTrim: this.fb.control(this.loadingDischargingDetails.trimAllowed?.maximumTrim, [Validators.required, numberValidator(2, 1), Validators.min(1), Validators.max(3)]),
    });

    if (this.operation === OPERATIONS.DISCHARGING) {
      this.loadingDischargingDetailsForm.addControl('strippingTrim', this.fb.control(this.loadingDischargingDetails.trimAllowed?.strippingTrim, [Validators.required, numberValidator(2, 1), Validators.min(0), Validators.max(2)]));
    } else {
      this.loadingDischargingDetailsForm.addControl('finalTrim', this.fb.control(this.loadingDischargingDetails.trimAllowed?.finalTrim, [Validators.required, numberValidator(2, 1), Validators.min(0), Validators.max(2)]));
    }
  }

  /**
   * Method for converting time string to date
   *
   * @param {string} field
   * @return {*}  {Date}
   * @memberof LoadingDischargingDetailsComponent
   */
  getDateByDate(field: string): Date {
    if (field) {
      const splittedField = field.split(":");
      const date = new Date();
      date.setHours(Number(splittedField[0]), Number(splittedField[1]))
      return date;
    } else {
      return null;
    }
  }

  /**
  * Method to check for field errors
  *
  * @param {string} formControlName
  * @return {ValidationErrors}
  * @memberof LoadingDischargingDetailsComponent
  */
  fieldError(formControlName: string): ValidationErrors {
    const formControl = this.field(formControlName);
    return formControl?.invalid && (formControl.dirty || formControl.touched) ? formControl.errors : null;
  }

  /**
   * Method to get formcontrolName
   *
   * @param {string} formControlName
   * @return {*}  {FormControl}
   * @memberof LoadingDischargingDetailsComponent
   */
  field(formControlName: string): FormControl {
    const formControl = <FormControl>this.loadingDischargingDetailsForm?.get(formControlName);
    return formControl;
  }

  onSubmit() { }

  /**
* Method for when time values change
*
* @memberof LoadingDischargingDetailsComponent
*/
  onTimeChange(fieldReferenceName, field) {
    this.selectedTime = null;
    if (this.loadingDischargingDetailsForm.value[field]) {
      this.selectedTime = new Date(this.loadingDischargingDetailsForm.value[field]);

    }
    else {
      this.selectedTime = new Date();
      this.selectedTime.setHours(fieldReferenceName.currentHour, fieldReferenceName.currentMinute);
      this.loadingDischargingDetailsForm.controls[field].setValue(this.selectedTime);
    }
    this.loadingDischargingDetailsResponse[field] = ((this.selectedTime.getHours() < 10 ? ('0' + this.selectedTime.getHours()) : this.selectedTime.getHours())) + ":" + ((this.selectedTime.getMinutes() < 10 ? ('0' + this.selectedTime.getMinutes()) : this.selectedTime.getMinutes()));

    if (!this.fieldError(field)) {
      this.updateLoadingDischargingDetails.emit(this.loadingDischargingDetailsResponse);
    }
    fieldReferenceName.hideOverlay();
    this.loadingDischargingDetailsForm.controls.timeOfSunrise.updateValueAndValidity();
    this.loadingDischargingDetailsForm.controls.timeOfSunset.updateValueAndValidity();
  }

  /**
   * Method to clear time input.
   *
   * @param {string} field
   * @param {*} fieldReferenceName
   * @memberof LoadingDischargingDetailsComponent
   */
  clearTimeInput(fieldReferenceName: any, field: string) {
    fieldReferenceName.value = null;
    fieldReferenceName.hideOverlay();
    this.loadingDischargingDetailsForm.controls[field].setValue(null);
    this.loadingDischargingDetailsForm.controls.timeOfSunrise.updateValueAndValidity();
    this.loadingDischargingDetailsForm.controls.timeOfSunset.updateValueAndValidity();
  }

  /**
    * Get page permission
    *
    * @memberof LoadingDischargingDetailsComponent
    */
  getPagePermission() {
    this.timeOfSunrisePermission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping[this.operation === OPERATIONS.LOADING ? 'LoadingInfoSunRise' : 'DischargingInfoSunRise'], false);
    this.timeOfSunsetPermission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping[this.operation === OPERATIONS.LOADING ? 'LoadingInfoSunSet' : 'DischargingInfoSunSet'], false);
  }

  /**
  * Method for when trim value update
  *
  * @memberof LoadingDischargingDetailsComponent
  */
  trimValueChange(field) {
    if (!this.fieldError(field)) {
      this.loadingDischargingDetailsResponse.trimAllowed[field] = this.loadingDischargingDetailsForm.value[field];
      this.updateLoadingDischargingDetails.emit(this.loadingDischargingDetailsResponse);
    }
  }

  /**
  * Method for download file
  *
  * @memberof LoadingDischargingDetailsComponent
  */
  downloadTemplate() {
    let id;
    if (this.operation === OPERATIONS.DISCHARGING) {
      id = this.dischargeInfoId;
    } else {
      id = this.loadingInfoId;
    }
    this.loadingDischargingInformationApiService.downloadTemplate(id, this.operation).subscribe((data) => {
      const blob = new Blob([data], { type: data.type })
      const fileurl = window.URL.createObjectURL(blob);
      if (this.operation === OPERATIONS.DISCHARGING) {
        saveAs(fileurl, 'Discharging_port_tide_details.xlsx');
      } else {
        saveAs(fileurl, 'Loading_port_tide_details.xlsx');
      }

    });
  }

  /**
  * Method for uploading file
  *
  * @memberof LoadingDischargingDetailsComponent
  */
  async selectFilesToUpload() {
    this.ngxSpinnerService.show();
    const translationKeys = await this.translateService.get(
      ['LOADING_DICHARGING_EXCEL_EXPORT_SUCCESS_DETAILS', 'LOADING_DICHARGING_EXCEL_EXPORT_SUCCESS', 'LOADING_DICHARGING_EXCEL_EXPORT_ERROR', 'LOADING_DISCHARGING_EXCEL_ERROR', 'LOADING_DISCHARGING_FILE_FORMAT_ERROR', 'LOADING_DISCHARGING_FILE_SIZE_ERROR',
        'LOADING_DICHARGING_EXCEL_PORT_NAME_INVALID', 'LOADING_DICHARGING_EXCEL_DATE_INVALID', 'LOADING_DICHARGING_EXCEL_TIME_INVALID',
        'LOADING_DICHARGING_EXCEL_HEIGHT_INVALID', 'LOADING_DICHARGING_EXCEL_INVALID_EXCEL_FILE', 'LOADING_DICHARGING_EXCEL_EMPTY_FILE', 'LOADING_DICHARGING_EXCEL_INVALID_FILE']).toPromise();
    this.fileUploadVariable.nativeElement.disabled = true;
    let id;
    if (this.operation === OPERATIONS.DISCHARGING) {
      id = this.dischargeInfoId;
    } else {
      id = this.loadingInfoId;
    }
    try {
      const uploadedFileVar = this.fileUploadVariable.nativeElement.files;
      const extensions = ["xlsx"];
      let uploadError;
      for (let i = 0; i < uploadedFileVar.length; i++) {
        const fileExtension = uploadedFileVar[i].name.substr((uploadedFileVar[i].name.lastIndexOf('.') + 1));
        if (extensions.includes(fileExtension.toLowerCase())) {
          if (uploadedFileVar[i].size / 1024 / 1024 >= 1) {
            uploadError = 'LOADING_DISCHARGING_FILE_SIZE_ERROR';
          }
        } else {
          uploadError = 'LOADING_DISCHARGING_FILE_FORMAT_ERROR';
        }
      }
      if (uploadError) {
        this.messageService.add({ severity: 'error', summary: translationKeys['LOADING_DISCHARGING_EXCEL_ERROR'], detail: translationKeys[uploadError] });
      } else {
        const result = await this.loadingDischargingInformationApiService.uploadTemplate(id, uploadedFileVar[0], this.operation).toPromise();
        if (result.responseStatus.status === '200') {
          this.messageService.add({ severity: 'success', summary: translationKeys['LOADING_DICHARGING_EXCEL_EXPORT_SUCCESS'], detail: translationKeys['LOADING_DICHARGING_EXCEL_EXPORT_SUCCESS_DETAILS'] });
        }
      }
    } catch (err) {
      if (err.error.errorCode === "ERR-RICO-310") {
        this.messageService.add({ severity: 'error', summary: translationKeys['LOADING_DISCHARGING_EXCEL_ERROR'], detail: translationKeys['LOADING_DICHARGING_EXCEL_PORT_NAME_INVALID'] });
      } else if (err.error.errorCode === "ERR-RICO-311") {
        this.messageService.add({ severity: 'error', summary: translationKeys['LOADING_DISCHARGING_EXCEL_ERROR'], detail: translationKeys['LOADING_DICHARGING_EXCEL_DATE_INVALID'] });
      } else if (err.error.errorCode === "ERR-RICO-312") {
        this.messageService.add({ severity: 'error', summary: translationKeys['LOADING_DISCHARGING_EXCEL_ERROR'], detail: translationKeys['LOADING_DICHARGING_EXCEL_TIME_INVALID'] });
      } else if (err.error.errorCode === "ERR-RICO-313") {
        this.messageService.add({ severity: 'error', summary: translationKeys['LOADING_DISCHARGING_EXCEL_ERROR'], detail: translationKeys['LOADING_DICHARGING_EXCEL_HEIGHT_INVALID'] });
      } else if (err.error.errorCode === "ERR-RICO-314") {
        this.messageService.add({ severity: 'error', summary: translationKeys['LOADING_DISCHARGING_EXCEL_ERROR'], detail: translationKeys['LOADING_DICHARGING_EXCEL_INVALID_EXCEL_FILE'] });
      } else if (err.error.errorCode === "ERR-RICO-315") {
        this.messageService.add({ severity: 'error', summary: translationKeys['LOADING_DISCHARGING_EXCEL_ERROR'], detail: translationKeys['LOADING_DICHARGING_EXCEL_EMPTY_FILE'] });
      } else if (err.error.errorCode === "ERR-RICO-316") {
        this.messageService.add({ severity: 'error', summary: translationKeys['LOADING_DISCHARGING_EXCEL_ERROR'], detail: translationKeys['LOADING_DICHARGING_EXCEL_INVALID_FILE'] });
      } else if (err.error.errorCode === "ERR-RICO-400") {
        this.messageService.add({ severity: 'error', summary: translationKeys['LOADING_DISCHARGING_EXCEL_ERROR'], detail: translationKeys['LOADING_DICHARGING_EXCEL_EXPORT_ERROR'] });
      }
    }
    this.fileUploadVariable.nativeElement.disabled = false;
    this.fileUploadVariable.nativeElement.value = "";
    this.ngxSpinnerService.hide();
  }

  /**
  * Method to close time picker
  * @param {string} field
  * @param {*} fieldReferenceName
  * @memberof LoadingDischargingDetailsComponent
  */
  onBlur(fieldReferenceName: any, field: string) {
    this.loadingDischargingDetailsForm.controls.timeOfSunrise.updateValueAndValidity();
    this.loadingDischargingDetailsForm.controls.timeOfSunset.updateValueAndValidity();
    fieldReferenceName.hideOverlay();
    if (!this.fieldError(field)) {
      if (field === 'commonDate') {
        const commonDT = this.loadingDischargingDetailsForm.value[field] ? this.timeZoneTransformationService.formatDateTime(this.loadingDischargingDetailsForm.value[field]) : null;
        this.loadingDischargingDetailsResponse[field] = commonDT;
      } else {
        const selectedTime = new Date(this.loadingDischargingDetailsForm.value[field]);
        this.loadingDischargingDetailsResponse[field] = ((selectedTime.getHours() < 10 ? ('0' + selectedTime.getHours()) : selectedTime.getHours())) + ":" + ((selectedTime.getMinutes() < 10 ? ('0' + selectedTime.getMinutes()) : selectedTime.getMinutes()));
      }
      this.updateLoadingDischargingDetails.emit(this.loadingDischargingDetailsResponse);
    }
  }

  /**
   * function to convert string to Date object
   *
   * @param {string} date
   * @return {*}  {Date}
   * @memberof LoadingDischargingDetailsComponent
   */
  dateStringToDate(date: string): Date {
    if (date) {
      const dtFormatOptions = { stringToDate: true };
      return this.timeZoneTransformationService.formatDateTime(date, dtFormatOptions);
    }
  }

}
