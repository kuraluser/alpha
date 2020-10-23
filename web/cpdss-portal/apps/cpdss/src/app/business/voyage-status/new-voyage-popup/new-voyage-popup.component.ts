import { Output } from '@angular/core';
import { EventEmitter } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { VesselDetailsModel } from '../../model/vessel-details.model';
import { VesselsApiService } from '../../services/vessels-api.service';

import { NewVoyageModel } from '../models/new-voyage.model';
import { NewVoyageResponseModel } from '../models/new-voyage.model';
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

  constructor(private fb: FormBuilder, private router: Router,
    private voyageApiService: VoyageApiService,
    private vesselApiService: VesselsApiService) { }

  ngOnInit(): void {
    this.showPopUp = true;
    this.isLoading = false;
    this.isSubmitted = false;
    this.vesselDetails = this.vesselApiService.vesselDetails;
    this.showPopUp = true;
    this.newVoyageForm = this.fb.group({
      'captain': [this.vesselDetails[0].captainName],
      'chiefOfficer': [this.vesselDetails[0].chiefOfficerName],
      'voyageNo': [null, [Validators.required]]
    });
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
    this.isSubmitted = true;
    if (this.newVoyageForm.valid) {
      this.isLoading = true;
      this.newVoyageModel = new NewVoyageModel();
      this.newVoyageModel.captainId = this.vesselDetails[0].captainId;
      this.newVoyageModel.cheifOfficerId = this.vesselDetails[0].chiefOfficerId;
      this.newVoyageModel.voyageNumber = this.newVoyageForm.value.voyageNo;
      this.saveNewVoyage();
      this.isLoading = false;
      this.router.navigate(['business/cargo-planning/loadable-study-list']);
    }
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
  async saveNewVoyage(): Promise<NewVoyageResponseModel> {
    const vessel_id = 557;
    const company_id = 9997;
    return await this.voyageApiService.saveNewVoyageData(this.newVoyageModel, vessel_id, company_id).toPromise();
  }


}
