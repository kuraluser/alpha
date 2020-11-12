import { Output } from '@angular/core';
import { EventEmitter } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { VesselDetailsModel } from '../../model/vessel-details.model';
import { VesselsApiService } from '../../core/services/vessels-api.service';
import { NewVoyageModel } from '../models/new-voyage.model';
import { VoyageApiService } from '../services/voyage-api.service';

/**
 * Component for new voyage popup
 */
@Component({
  selector: 'cpdss-portal-new-voyage-popup',
  templateUrl: './new-voyage-popup.component.html',
  styleUrls: ['./new-voyage-popup.component.scss']
})
export class NewVoyagePopupComponent implements OnInit {
  @Output() displayPopUp = new EventEmitter<boolean>();
  showPopUp: boolean;
  newVoyageForm!: FormGroup;
  newVoyageModel!: NewVoyageModel;
  isLoading: boolean;
  isSubmitted: boolean;
  vesselDetails: VesselDetailsModel[];
  isExisting = false;

  constructor(private fb: FormBuilder, private router: Router,
    private voyageApiService: VoyageApiService,
    private vesselApiService: VesselsApiService) { }

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
    this.newVoyageModel.captainId = this.vesselDetails[0].captainId;
    this.newVoyageModel.chiefOfficerId = this.vesselDetails[0].chiefOfficerId;
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
    const vesselId = this.vesselDetails[0].id;
    this.voyageApiService.saveNewVoyageData(this.newVoyageModel, vesselId).subscribe((res) => {
      if (res.responseStatus.status === "200") {
        this.router.navigate(['business/cargo-planning/loadable-study-list', res.voyageId]);
      }
    },
      (error) => {
        if (error.error.status === "400") {
          this.isExisting = true;
        }
      }
    );
  }

  /**
   * Assign value and validators
   */
  async getVesselInfo() {
    this.vesselDetails = await this.vesselApiService.getVesselsInfo().toPromise();
    this.newVoyageForm = this.fb.group({
      'captain': [this.vesselDetails[0].captainName],
      'chiefOfficer': [this.vesselDetails[0].chiefOfficerName],
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
