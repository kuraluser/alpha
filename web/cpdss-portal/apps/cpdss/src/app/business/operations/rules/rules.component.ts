import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { IVessel } from '../../core/models/vessel-details.model';
import { VesselsApiService } from '../../core/services/vessels-api.service';
import { VoyageService } from '../../core/services/voyage.service';
import { LoadingDischargingTransformationService } from '../services/loading-discharging-transformation.service';
import { RulesService } from '../services/rules.service'
import { OPERATIONS, OPERATIONS_PLAN_STATUS, Voyage, VOYAGE_STATUS } from '../../core/models/common.model';
import { IDischargingInformation, ILoadingInformationResponse } from '../models/loading-discharging.model';

/**
 * Component class for loading rules.
 *
 * @export
 * @class RulesComponent
 * @implements {OnInit}
 */

@Component({
  selector: 'cpdss-portal-rules',
  templateUrl: './rules.component.html',
  styleUrls: ['./rules.component.scss']
})

export class RulesComponent implements OnInit {

  @ViewChild('rulesTable') rulesTable: any;
  @Input() visible = true;
  @Input() operation: OPERATIONS;
  @Output() popUpClosed: EventEmitter<any> = new EventEmitter();
  rulesJson: any;
  vessels: IVessel[];
  infoId: number;
  infoStatusId: OPERATIONS_PLAN_STATUS;
  loadingDischargingInfo: IDischargingInformation | ILoadingInformationResponse;
  isCancelChanges = false;
  formChanges = false;
  vesselId: number;
  voyageId : number;
  voyage : Voyage;
  disableSaveButton = false;
  editMode = false;

  constructor(private translateService: TranslateService, public rulesService: RulesService,
    private vesselsApiService: VesselsApiService,
    private ngxSpinner: NgxSpinnerService,private messageService: MessageService,
    private voyageService: VoyageService,private loadingDischargingTransformationService:LoadingDischargingTransformationService
    ) { }

  /**
   * Component lifecycle ngoninit.
   *
   * @return {*}  {Promise<void>}
   * @memberof RulesComponent
   */
  async ngOnInit(): Promise<void> {
    this.ngxSpinner.show();
    this.vessels = await this.vesselsApiService.getVesselsInfo().toPromise();
    const vesselDetails = this.vessels[0] ?? <IVessel>{};
    const result = await this.voyageService.getVoyagesByVesselId(vesselDetails?.id).toPromise();
    this.voyage = result.find(voy=>voy.statusId=== VOYAGE_STATUS.ACTIVE);
    this.voyageId = this.voyage.id;
    await this.getRulesJson();
    this.getSaveButtonStatus()
    this.ngxSpinner.hide();
  }

  /**
   * Method to get rules json.
   *
   * @memberof RulesComponent
   */
  async getRulesJson() {
    this.vesselId = this.vessels[0].id;
    this.rulesService.loadingDischargingInfo.subscribe(async (res: IDischargingInformation | ILoadingInformationResponse) => {
      this.loadingDischargingInfo = res;
      this.infoId = this.operation === OPERATIONS.LOADING ? res['loadingInfoId'] : res['dischargeInfoId'];
      this.infoStatusId = this.operation === OPERATIONS.LOADING ? res['loadingInfoStatusId'] : res['dischargeInfoStatusId'];
      this.editMode = [OPERATIONS_PLAN_STATUS.PENDING, OPERATIONS_PLAN_STATUS.NO_PLAN_AVAILABLE, OPERATIONS_PLAN_STATUS.ERROR_OCCURED, OPERATIONS_PLAN_STATUS.PLAN_GENERATED, OPERATIONS_PLAN_STATUS.CONFIRMED].includes(this.infoStatusId) && ![VOYAGE_STATUS.CLOSE].includes(this.voyage.statusId);
      if (this.infoId != null) {
        this.rulesJson = await this.rulesService.getRules(this.vesselId,this.voyageId,this.infoId,this.operation).toPromise();
      }
    })

  }

  /**
 * Method to trigger save.
 *
 * @memberof RulesComponent
 */
  triggerSaveChanges() {
    this.rulesService.save.next();
    if(this.rulesTable.rulesForm.valid){
      this.onClose();
    }
  }


  /**
   * Method to cancel changes.
   *
   * @memberof RulesComponent
   */
  cancelChanges() {
    this.isCancelChanges = true;
    this.formChanges = false;
  }

  /**
   * Method to close the pop up.
   *
   * @memberof RulesComponent
   */

  onClose() {
    this.popUpClosed.emit(false);
  }

  /**
   * Method to show/hide cancel changes button.
   *
   * @memberof RulesComponent
   */
  updateCancelChangesButton(event) {
    this.formChanges = event
    this.isCancelChanges = !event;
  }

  /**
   * Method to get save button status.
   *
   * @memberof RulesComponent
   */
  getSaveButtonStatus() {
    this.loadingDischargingTransformationService.disableInfoInstructionRuleSave.subscribe((status) => {
      this.disableSaveButton = status;
      this.editMode = !status;
    });
  }

  /**
   * Method to save changes.
   *
   * @param {*} postData
   * @memberof RulesComponent
   */

   async saveChanges(postData) {
    this.ngxSpinner.show();
    let msgkeys, severity;
    try {
      const result = await this.rulesService.postRules(postData,this.vesselId,this.voyageId,this.infoId,this.operation).toPromise();
      if (result?.responseStatus?.status === '200') {
        msgkeys = ['RULES_UPDATE_SUCCESS', 'RULES_UPDATE_SUCCESSFULLY']
        severity = 'success';
      }
    }
    catch (error) {
      msgkeys = ['RULES_UPDATE_ERROR', 'RULES_UPDATE_FAILED'];
      severity = "error"
    }
    const translationKeys = await this.translateService.get(msgkeys).toPromise();
    this.messageService.add({ severity: severity, summary: translationKeys[msgkeys[0]], detail: translationKeys[msgkeys[1]] });
    this.ngxSpinner.hide();
  }

}
