import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { Observable } from 'rxjs';
import { NgxSpinnerService } from 'ngx-spinner';
import { ComponentCanDeactivate, UnsavedChangesGuard } from './../../../../../shared/services/guards/unsaved-data-guard';

import { LoadingInstructionApiService } from '../../../services/loading-instruction-api.service';
import { DischargingInstructionApiService } from './../../../services/discharging-instruction-api.service';
import { LoadingDischargingTransformationService } from '../../../services/loading-discharging-transformation.service';

import { IDischargingInstructionGroup, IDischargingInstructionResponse, IDischargingInstructionSubHeaderData } from './../../../models/discharging-instruction.model';
import { ILoadingInstructionSubHeaderData } from '../../../models/loading-instruction.model';

@Component({
  selector: 'cpdss-portal-discharging-instruction',
  templateUrl: './discharging-instruction.component.html',
  styleUrls: ['./discharging-instruction.component.scss']
})
export class DischargingInstructionComponent implements OnInit, ComponentCanDeactivate {

  @Input() vesselId: number;
  @Input() voyageId: number;
  @Input() portRotationId: number;
  @Input() dischargeInfoId: number;

  @ViewChild('instructionCheckList') instructionCheckList;

  sidePanelList: IDischargingInstructionGroup[];
  // will use once actual API available
  // instructionData: IDischargingInstructionSubHeaderData[];
  // instructionList: IDischargingInstructionSubHeaderData[];
  groupId: number;

  instructionList: ILoadingInstructionSubHeaderData[] = [];
  instructionData: ILoadingInstructionSubHeaderData[] = [];

  constructor(
    private loadingInstructionApiService: LoadingInstructionApiService,
    private dischargingInstructionApiService: DischargingInstructionApiService,
    private loadingDischargingTransformationService: LoadingDischargingTransformationService,
    private unsavedChangesGuard: UnsavedChangesGuard,
    private ngxSpinnerService: NgxSpinnerService
  ) { }

  ngOnInit(): void {
    // Used it for temporary purpose
    this.dischargeInfoId = 155;
    this.portRotationId =  113696;
    this.getDischargingInstructionDetails();
  }

  async getDischargingInstructionDetails() {
    this.ngxSpinnerService.show();
    // Will use this once actual API available
    /*
    const dischargingInstructionResponse: IDischargingInstructionResponse = await this.dischargingInstructionApiService.getDischargingInstructionData(this.vesselId, this.dischargeInfoId, this.portRotationId).toPromise();
    if (dischargingInstructionResponse.responseStatus.status === "200") {
      this.instructionData = dischargingInstructionResponse?.dischargingInstructionSubHeader?.length ? dischargingInstructionResponse?.dischargingInstructionSubHeader : [];
      this.sidePanelList = dischargingInstructionResponse?.dischargingInstructionGroupList?.length ? dischargingInstructionResponse?.dischargingInstructionGroupList : [];
      this.setInstructionSelected();
    }
    */

    try {
      this.ngxSpinnerService.show();
      const result = await this.loadingInstructionApiService.getLoadingInstructionData(this.vesselId, this.dischargeInfoId, this.portRotationId).toPromise();
      this.instructionData = result?.loadingInstructionSubHeader?.length ? result?.loadingInstructionSubHeader : [];
      this.sidePanelList = result?.loadingInstructionGroupList?.length ? result?.loadingInstructionGroupList : [];
      this.setInstructionSelected();
      this.ngxSpinnerService.hide();
    } catch {
      this.sidePanelList = [];
      this.instructionData = [];
      this.ngxSpinnerService.hide();
    }
    this.ngxSpinnerService.hide();
  }

  /**
   * function to set side panel instruction group selected
   *
   * @memberof DischargingInstructionComponent
   */
  setInstructionSelected(): void {
    this.groupId = this.groupId ? this.groupId : this.sidePanelList[0]?.groupId;
    this.setInstructionList(this.groupId);
    if (this.groupId) {
      this.sidePanelList.map(item => {
        if (item.groupId === this.groupId) {
          item.selected = true;
        }
      });
    } else {
      this.sidePanelList[0].selected = true;
    }
  }

  /**
   * function to filter instruction set based on selected group
   *
   * @param {*} id
   * @return {*} 
   * @memberof DischargingInstructionComponent
   */
  setInstructionList(id: number): void {
    if (!id) { return; }
    this.instructionList = this.instructionData.filter(item => item.instructionHeaderId === id);
  }

  /**
   * function to select the side panel instruction group
   *
   * @param {*} event
   * @return {*} 
   * @memberof DischargingInstructionComponent
   */
  async sidePanelInstructionChange(event) {
    if (event && this.groupId !== event.groupId) {
      const result = await this.unsavedChangesGuard.canDeactivate(this);
      if (!result) {
        this.sidePanelList.map(item => {
          item.selected = (item.groupId === this.groupId);
        });
        return;
      }
      this.instructionCheckList.hasUnsavedChanges = false;
      this.instructionCheckList.instructionForm.reset();
      this.setInstructionList(event.groupId);
      this.groupId = event.groupId;
    }
  }

  /**
   * function to check the un-saved changes on form
   *
   * @return {*}  {(Observable<boolean> | boolean)}
   * @memberof DischargingInstructionComponent
   */
  canDeactivate(): Observable<boolean> | boolean {
    return !(this.instructionCheckList?.hasUnsavedChanges || this.instructionCheckList?.instructionForm?.dirty);
  }

  /**
   * function to change status of tab
   *
   * @param {*} event
   * @memberof DischargingInstructionComponent
   */
  setTabStatus(event) {
    this.loadingDischargingTransformationService.setLoadingInstructionValidity(event);
  }

}
