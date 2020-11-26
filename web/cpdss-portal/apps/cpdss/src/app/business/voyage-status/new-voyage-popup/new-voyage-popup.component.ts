import { Input, Output } from '@angular/core';
import { EventEmitter } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { VesselDetailsModel } from '../../model/vessel-details.model';
import { NewVoyageModel } from '../models/new-voyage.model';
import { VoyageApiService } from '../services/voyage-api.service';
import { MessageService } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';
import { NgxSpinnerService } from 'ngx-spinner';

/**
 * Component for new voyage popup
 */
@Component({
  selector: 'cpdss-portal-new-voyage-popup',
  templateUrl: './new-voyage-popup.component.html',
  styleUrls: ['./new-voyage-popup.component.scss']
})
export class NewVoyagePopupComponent implements OnInit {
  @Input() vesselDetails: VesselDetailsModel;
  @Output() displayPopUp = new EventEmitter<boolean>();
  showPopUp: boolean;
  newVoyageForm!: FormGroup;
  newVoyageModel!: NewVoyageModel;
  isLoading: boolean;
  isSubmitted: boolean;
  isExisting = false;

  constructor(private fb: FormBuilder, private router: Router,
    private voyageApiService: VoyageApiService,
    private messageService: MessageService,
    private translateService: TranslateService,
    private ngxSpinnerService: NgxSpinnerService) { }

  ngOnInit(): void {
    this.getVesselInfo();
  }

  /**
   * Return the form controlls of the form
   */
  get newVoyageFormControl() {
    return this.newVoyageForm.controls;
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
      'voyageNo': [null, [Validators.required, Validators.pattern('^[a-zA-Z0-9 ][ A-Za-z0-9_.()&,-]*$'), Validators.maxLength(100)]]
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
    }

  }

  /**
   * Trim blank space entered as the first character in voyage no. field 
   */
  trimVoyageNo(){
   this.newVoyageForm.controls['voyageNo'].setValue((this.newVoyageForm.get('voyageNo').value).trim());
  }

}
