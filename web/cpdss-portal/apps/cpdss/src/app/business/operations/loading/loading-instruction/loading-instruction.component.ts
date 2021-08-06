import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { LoadingInstructionApiService } from './../../services/loading-instruction-api.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { Observable } from 'rxjs';
import { UnsavedChangesGuard, ComponentCanDeactivate } from './../../../../shared/services/guards/unsaved-data-guard';
import { ILoadingInstructionGroup, ILoadingInstructionSubHeaderData } from './../../models/loading-instruction.model';
import { LoadingDischargingTransformationService } from '../../services/loading-discharging-transformation.service';
@Component({
  selector: 'cpdss-portal-loading-instruction',
  templateUrl: './loading-instruction.component.html',
  styleUrls: ['./loading-instruction.component.scss']
})

/**
 * Component class for loading instruction component
 *
 * @export
 * @class LoadingInstructionComponent
 * @implements {OnInit}
 */

export class LoadingInstructionComponent implements OnInit, ComponentCanDeactivate {

  @Input() vesselId: number;
  @Input() voyageId: number;
  @Input() portRotationId: number;
  @Input() loadingInfoId: number;

  @ViewChild('instructionCheckList') instructionCheckList;

  panelList: ILoadingInstructionGroup[] = [];
  instructionList: ILoadingInstructionSubHeaderData[] = [];
  instructionData: ILoadingInstructionSubHeaderData[] = [];
  groupId: number;

  constructor(
    private loadingInstructionApiService: LoadingInstructionApiService,
    private ngxSpinnerService: NgxSpinnerService,
    private unsavedChangesGuard: UnsavedChangesGuard,
    private loadingDischargingTransformationService: LoadingDischargingTransformationService
  ) { }

  ngOnInit(): void {
    this.getLoadingInstructionDetails();
  }

  /**
   * filter instruction list
   *
   * @param id
   * @memberof LoadingInstructionComponent
   */
  setInstructionList(id) {
    if (!id) { return; }
    this.instructionList = this.instructionData.filter(item => item.instructionHeaderId === id);
  }

  /**
   * Check for unsaved changes on side panel click
   *
   * @param id
   * @memberof LoadingInstructionComponent
   */
  canDeactivate(): Observable<boolean> | boolean {
    return !(this.instructionCheckList?.hasUnsavedChanges || this.instructionCheckList?.instructionForm?.dirty);
  }

  /**
   * Row selection of instruction side panel
   *
   * @param event
   * @memberof LoadingInstructionComponent
   */
  async sidePanelInstructionChange(event) {
    if (event && this.groupId !== event.groupId) {
      const result = await this.unsavedChangesGuard.canDeactivate(this);
      if (!result) {
        this.panelList.map(item => {
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
   * Get loading instruction data
   *
   * @memberof LoadingInstructionComponent
   */
  async getLoadingInstructionDetails() {
    try {
      this.ngxSpinnerService.show();
      const result = await this.loadingInstructionApiService.getLoadingInstructionData(this.vesselId, this.loadingInfoId, this.portRotationId).toPromise();
      this.instructionData = result?.loadingInstructionSubHeader?.length ? result?.loadingInstructionSubHeader : [];
      this.panelList = result?.loadingInstructionGroupList?.length ? this.formatPanelList(result?.loadingInstructionGroupList) : [];
      this.setInstructionSelected();
      this.ngxSpinnerService.hide();
    } catch {
      this.panelList = [];
      this.instructionData = [];
      this.ngxSpinnerService.hide();
    }
  }

  /**
   * Set instruction selection
   *
   * @memberof LoadingInstructionComponent
   */
  setInstructionSelected() {
    this.setInstructionList(this.groupId ? this.groupId : this.panelList[0]?.groupId);
    this.groupId = this.groupId ? this.groupId : this.panelList[0]?.groupId;
    if (this.groupId) {
      this.panelList.map(item => {
        if (item.groupId === this.groupId) {
          item.selected = true;
        }
      });
    } else {
      this.panelList[0].selected = true;
    }
  }

  /**
   * Update instruction tab details
   *
   * @memberof LoadingInstructionComponent
   */
  updateData() {
    this.getLoadingInstructionDetails();
  }

  /**
   * Unselect panel data
   *
   * @memberof LoadingInstructionComponent
   */
  formatPanelList(data) {
    data.map(item => {
      item.selected = false;
    });
    return data;
  }

  /**
   * set tab status - complete/incomplete
   *
   * @memberof LoadingInstructionComponent
   */
  setTabStatus(event){
    this.loadingDischargingTransformationService.setLoadingInstructionValidity(event);
  }

}
