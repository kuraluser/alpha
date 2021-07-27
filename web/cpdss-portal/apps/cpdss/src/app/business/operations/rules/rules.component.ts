import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { IVessel } from '../../core/models/vessel-details.model';
import { VesselsApiService } from '../../core/services/vessels-api.service';
import { VoyageService } from '../../core/services/voyage.service';
import { RulesService } from '../services/rules/rules.service'

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

  @Input() visible: boolean = true;
  @Output() popUpClosed: EventEmitter<any> = new EventEmitter();
  rulesJson: any;
  vessels: IVessel[];
  loadingInfoId: number; 
  isCancelChanges: boolean = false;
  formChanges: boolean = false;
  vesselId: number;
  voyageId : number;
  constructor(private translateService: TranslateService, public rulesService: RulesService,
    private vesselsApiService: VesselsApiService,
    private ngxSpinner: NgxSpinnerService,private messageService: MessageService,
    private voyageService: VoyageService,) { }

  /**
   * Component lifecycle ngoninit.
   *
   * @return {*}  {Promise<void>}
   * @memberof RulesComponent
   */
  async ngOnInit(): Promise<void> {
    this.ngxSpinner.show();
    this.rulesService.init();
    this.vessels = await this.vesselsApiService.getVesselsInfo().toPromise(); 
    let vesselDetails = this.vessels[0] ?? <IVessel>{};   
    const result = await this.voyageService.getVoyagesByVesselId(vesselDetails?.id).toPromise();   
    this.voyageId = result.find(voy=>voy.status==='Active').id;    
    this.getRulesJson();
    this.ngxSpinner.hide();
  }

  /**
   * Method to get rules json.
   *
   * @memberof RulesComponent
   */
  async getRulesJson() {
    this.vesselId = this.vessels[0].id;
    this.rulesService.loadingInfoId.subscribe(async (res) => {
      this.loadingInfoId = res;
      if (this.loadingInfoId != null) {
        this.rulesJson = await this.rulesService.getRules(this.vesselId,this.voyageId,this.loadingInfoId).toPromise();
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
    this.onClose();
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
   * Method to save changes.
   *
   * @param {*} postData
   * @memberof RulesComponent
   */

   async saveChanges(postData) {
    this.ngxSpinner.show();
    let msgkeys, severity;
    try {
      let result = await this.rulesService.postRules(postData,this.vesselId,null,this.loadingInfoId).toPromise(); 
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
