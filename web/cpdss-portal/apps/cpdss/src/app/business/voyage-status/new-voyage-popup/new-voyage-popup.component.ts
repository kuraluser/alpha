import { Input, Output } from '@angular/core';
import { EventEmitter } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { IVessel } from '../../core/models/vessel-details.model';
import { NewVoyageModel } from '../models/new-voyage.model';
import { VoyageApiService } from '../services/voyage-api.service';
import { MessageService } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { dateCompareValidator } from '../directive/validator/date-compare-validator.directive'

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
  isExisting = false;
  errorMessages: any;
  date:Date;

  constructor(private fb: FormBuilder, private router: Router,
    private voyageApiService: VoyageApiService,
    private messageService: MessageService,
    private translateService: TranslateService,
    private ngxSpinnerService: NgxSpinnerService) { }

  ngOnInit(): void {
    this.date = new Date();
    this.errorMessages = this.voyageApiService.setValidationErrorMessage();
    this.getVesselInfo();
  }

  /**
   * Submit form
   */
  onSubmit() {
    this.isLoading = true;
    this.newVoyageModel = new NewVoyageModel();
    this.newVoyageModel.captainId = this.vesselDetails?.captainId;
    this.newVoyageModel.chiefOfficerId = this.vesselDetails?.chiefOfficerId;
    this.newVoyageModel.voyageNo = this.newVoyageForm.value.voyageNo;
    this.newVoyageModel.startDate = this.formatDateTime(this.newVoyageForm.value.start_date);
    this.newVoyageModel.endDate = this.formatDateTime(this.newVoyageForm.value.end_date);
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
        this.isExisting = true;
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
      'voyageNo': [null, [Validators.required, Validators.pattern('^[a-zA-Z0-9 ][ A-Za-z0-9_.()&,-]*$'), Validators.maxLength(100)]],
      'start_date': this.fb.control(null, [Validators.required, dateCompareValidator('end_date', '<')]),
      "end_date": this.fb.control(null, [Validators.required, dateCompareValidator('start_date', '>')])
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
  }

  /**
   * Method to update form on date time select
   */
  onDateSelect(event) {
    this.newVoyageForm.controls.start_date.updateValueAndValidity();
    this.newVoyageForm.controls.end_date.updateValueAndValidity();
  }

  /**
  * Format date time(dd-mm-yyyy hh:mm)
  */
  formatDateTime(date) {
    let month = date.getMonth() + 1;
    let day = date.getDate();
    let hour = date.getHours();
    let minute = date.getMinutes();

    if (month < 10) {
      month = '0' + month;
    }

    if (day < 10) {
      day = '0' + day;
    }

    if (hour < 10) {
      hour = '0' + hour;
    }

    if (minute < 10) {
      minute = '0' + minute;
    }

    return day + '-' + month + '-' + date.getFullYear() + ' ' + hour + ':' + minute;
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
}
