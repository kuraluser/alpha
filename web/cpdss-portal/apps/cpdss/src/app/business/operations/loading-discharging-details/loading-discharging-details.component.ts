import { Component, EventEmitter, Input, OnInit, Output , ViewChild , ElementRef } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { MessageService } from 'primeng/api';
import { saveAs } from 'file-saver';
import { NgxSpinnerService } from 'ngx-spinner';
import { ILoadingDischargingDetails } from '../models/loading-discharging.model';
import { loadingDetailsValidator } from '../directives/validator/loading-details-time-validator.directive';
import { numberValidator } from '../../core/directives/number-validator.directive';
import { LoadingDischargingTransformationService } from '../services/loading-discharging-transformation.service';
import { PermissionsService } from '../../../shared/services/permissions/permissions.service';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { IPermission } from '../../../shared/models/user-profile.model';
import { OPERATIONS } from '../../core/models/common.model';
import { LoadingDischargingInformationApiService } from '../services/loading-discharging-information-api.service';

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
export class LoadingDischargingDetailsComponent implements OnInit {

  @ViewChild('fileUpload') fileUploadVariable: ElementRef;

  @Input() operation: OPERATIONS;
  @Input() loadingInfoId: number;
  @Input() dischargingInfoId: number;

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
  selectedTime:any;
  constructor(private fb: FormBuilder,
    private translateService: TranslateService,
    private permissionsService: PermissionsService,
    private messageService: MessageService,
    private ngxSpinnerService: NgxSpinnerService,
    private loadingDischargingInformationApiService: LoadingDischargingInformationApiService,
    private loadingDischargingTransformationService: LoadingDischargingTransformationService) { }

  ngOnInit(): void {
    this.getPagePermission();
    this.errorMessages = this.loadingDischargingTransformationService.setValidationMessageForLoadingDetails();
  }

  /**
  * Method for initialise loading discharging details form
  *
  * @memberof LoadingDischargingDetailsComponent
  */
  initLoadingDischargingDetailsForm() {
    let timeOfSunriseValidation = [];
    let timeOfSunsetValidation = [];
    if(this.timeOfSunrisePermission.view || this.timeOfSunrisePermission.view === undefined) {
      timeOfSunriseValidation = [Validators.required];
    } 
    if(this.timeOfSunsetPermission.view || this.timeOfSunsetPermission.view === undefined) {
      timeOfSunsetValidation = [Validators.required];
    }
    if(this.timeOfSunrisePermission.view && this.timeOfSunsetPermission.view) {
      timeOfSunsetValidation = [...timeOfSunsetValidation , loadingDetailsValidator('timeOfSunrise', '>')];
      timeOfSunriseValidation = [...timeOfSunriseValidation , loadingDetailsValidator('timeOfSunset', '<')];
    }
    this.loadingDischargingDetailsResponse = this.loadingDischargingDetails;
    this.loadingDischargingDetailsForm = this.fb.group({
      timeOfSunrise: this.fb.control(this.getDateByDate(this.loadingDischargingDetails?.timeOfSunrise), timeOfSunriseValidation),
      timeOfSunset: this.fb.control(this.getDateByDate(this.loadingDischargingDetails?.timeOfSunset), timeOfSunsetValidation),
      startTime: this.fb.control(this.getDateByDate(this.loadingDischargingDetails?.startTime), [Validators.required]),
      initialTrim: this.fb.control(this.loadingDischargingDetails.trimAllowed?.initialTrim, [Validators.required, numberValidator(2, 1), Validators.min(0), Validators.max(4)]),
      maximumTrim: this.fb.control(this.loadingDischargingDetails.trimAllowed?.maximumTrim, [Validators.required, numberValidator(2, 1), Validators.min(1), Validators.max(3)]),
    });

    if(this.operation === OPERATIONS.DISCHARGING) {
      this.loadingDischargingDetailsForm.addControl('topOffTrim', this.fb.control(this.loadingDischargingDetails.trimAllowed?.topOffTrim, [Validators.required, numberValidator(2, 1), Validators.min(0), Validators.max(2)]));
    } else {
      this.loadingDischargingDetailsForm.addControl('finalTrim', this.fb.control(this.loadingDischargingDetails.trimAllowed?.finalTrim, [Validators.required, numberValidator(2, 1), Validators.min(0), Validators.max(2)]));
    }
   }

  /**
* Method for converting time string to date
*
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
*Method to get formcontrolName
* @param {string} formControlName
* @return {FormControl}
* @memberof LoadingDischargingDetailsComponent
*/
  field(formControlName: string): FormControl {
    const formControl = <FormControl>this.loadingDischargingDetailsForm?.get(formControlName);
    return formControl;
  }

  clearFilter(dd) {
  }

  onSubmit() { }

  /**
* Method for when time values change
*
* @memberof LoadingDischargingDetailsComponent
*/
  onTimeChange(fieldReferenceName, field) {
    fieldReferenceName.hideOverlay();
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
    this.loadingDischargingDetailsForm.controls.timeOfSunrise.updateValueAndValidity();
    this.loadingDischargingDetailsForm.controls.timeOfSunset.updateValueAndValidity();
  }

  /**
   * Method to clear time input.
   *
   * @param {*} field
   * @memberof LoadingDischargingDetailsComponent
   */
  clearTimeInput(fieldReferenceName, field) {
    fieldReferenceName.hideOverlay();
    this.loadingDischargingDetailsForm.controls[field].setValue(null);
  }

/**
  * Get page permission
  *
  * @memberof SynopticalComponent
  */
 getPagePermission() {
  this.timeOfSunrisePermission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['LoadingInfoSunRise'], false);
  this.timeOfSunsetPermission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['LoadingInfoSunSet'], false);
}



  /**
  * Method for when trim value update
  *
  * @memberof LoadingDischargingDetailsComponent
  */
  trimValueChange(field) {
    if (this.loadingDischargingDetailsForm.valid && this.loadingDischargingDetailsForm.value[field]) {
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
    if(this.operation === OPERATIONS.DISCHARGING) {
      id = this.dischargingInfoId;
    } else {
      id = this.loadingInfoId;
    }
    this.loadingDischargingInformationApiService.downloadTemplate(id,this.operation).subscribe((data) => {
      const blob = new Blob([data], { type: data.type })
      const fileurl = window.URL.createObjectURL(blob)
      saveAs(fileurl, 'Loading port tide details.xlsx');
    });
  }

  /**
  * Method for uploading file
  *
  * @memberof LoadingDischargingDetailsComponent
  */
  async selectFilesToUpload() {
    this.ngxSpinnerService.show();
    const translationKeys = await this.translateService.get(['LOADING_DICHARGING_EXCEL_EXPORT_SUCCESS_DETAILS','LOADING_DICHARGING_EXCEL_EXPORT_SUCCESS','LOADING_DICHARGING_EXCEL_EXPORT_ERROR','LOADING_DISCHARGING_EXCEL_ERROR','LOADING_DISCHARGING_FILE_FORMAT_ERROR', 'LOADING_DISCHARGING_FILE_SIZE_ERROR']).toPromise();
    this.fileUploadVariable.nativeElement.disabled = true;
    let id;
    if(this.operation === OPERATIONS.DISCHARGING) {
      id = this.dischargingInfoId;
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
          if(uploadedFileVar[i].size / 1024 / 1024 >= 1) {
            uploadError = 'LOADING_DISCHARGING_FILE_SIZE_ERROR';
          }
        } else {
          uploadError = 'LOADING_DISCHARGING_FILE_FORMAT_ERROR';
        }
      }
      if(uploadError) {
        this.messageService.add({ severity: 'error', summary: translationKeys['LOADING_DISCHARGING_EXCEL_ERROR'], detail: translationKeys[uploadError] });
      } else {
        const result = await this.loadingDischargingInformationApiService.uploadTemplate(id,uploadedFileVar[0],this.operation).toPromise();
        if(result.responseStatus.status === '200') {
          this.messageService.add({ severity: 'success', summary: translationKeys['LOADING_DICHARGING_EXCEL_EXPORT_SUCCESS'], detail: translationKeys['LOADING_DICHARGING_EXCEL_EXPORT_SUCCESS_DETAILS'] });
        }
      }
    } catch(err) {
      if(err.error.errorCode === "ERR-RICO-400") {
        this.messageService.add({ severity: 'error', summary: translationKeys['LOADING_DISCHARGING_EXCEL_ERROR'], detail: translationKeys['LOADING_DICHARGING_EXCEL_EXPORT_ERROR'] });
      }
    }
    this.fileUploadVariable.nativeElement.disabled = false;
    this.fileUploadVariable.nativeElement.value = "";
    this.ngxSpinnerService.hide();
  }

}
