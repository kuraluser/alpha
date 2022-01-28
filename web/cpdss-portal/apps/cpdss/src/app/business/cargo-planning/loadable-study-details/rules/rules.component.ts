import { Component, EventEmitter, Input, OnInit, Output , ViewChild } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { DATATABLE_FIELD_TYPE } from '../../../../shared/components/datatable/datatable.model';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { RULES_TABS, RULE_TYPES } from '../../../core/models/rules.model';
import { RulesTableComponent } from '../../../core/components/rules-table/rules-table.component';
import { LoadableStudy } from '../../models/loadable-study-list.model';
import { LOADABLE_STUDY_STATUS, Voyage, VOYAGE_STATUS } from '../../../core/models/common.model';
import { RuleService } from '../../services/rule.service';
import { VesselsApiService } from '../../../core/services/vessels-api.service';

/**
 * Component class for rules.
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
  @ViewChild('rulesTable') rulesTable: RulesTableComponent;
  @Input() visible = true;
  @Input() selectedLoadableStudyId;
  @Input() voyage: Voyage;
  @Input()
  set loadableStudy(value: LoadableStudy) {
    this.editMode = [LOADABLE_STUDY_STATUS.PLAN_PENDING, LOADABLE_STUDY_STATUS.PLAN_NO_SOLUTION, LOADABLE_STUDY_STATUS.PLAN_ERROR].includes(value?.statusId) && ![VOYAGE_STATUS.CLOSE].includes(this.voyage?.statusId) && !this.voyage?.isDischargeStarted;
  }


  @Output() popUpClosed: EventEmitter<any> = new EventEmitter();
  isCancelChanges = false;

  TABS = RULES_TABS;
  tabs = Object.keys(this.TABS)
  selectedTab: string = this.TABS[this.tabs[0]];
  openSidePane = true;
  selectedIndex = 0;
  readonly fieldType = DATATABLE_FIELD_TYPE;
  rulesJson :any;
  errorArray =[];
  vesselId:number;
  ruleTypeId :number;
  tabIndex = 1;
  vessels:any;
  selectedVessel: any;
  formChanges = false;
  pageName = "LoadableStudy";
  data: any;
  editMode: boolean;


  constructor(
    public rulesService: RuleService,
    private translateService: TranslateService,private messageService:MessageService,
    private ngxSpinner: NgxSpinnerService, private vesselsApiService: VesselsApiService
  ) { }

  /**
  * Component Lifecycle Hook OnInit
  *
  * @returns {void}
  * @memberof RulesComponent
  */
 async ngOnInit(): Promise<void> {
   this.ngxSpinner.show();
   this.rulesService.selectedTab$.next('plan');
   this.data = 'plan';
   this.vessels = await this.vesselsApiService.getVesselsInfo(true).toPromise();
   this.vesselId = await this.vessels[0].id;
   this.selectedVessel = this.vessels[0];

   this.rulesJson = await this.rulesService.getRules(this.vesselId,this.tabIndex,this.selectedLoadableStudyId).toPromise();

   this.ngxSpinner.hide();
  }

  /**
   * Method to set index.
   *
   * @param {*} index
   * @memberof RulesComponent
   */
  setIndex(index) {
    this.selectedIndex = index;
  }

  /**
   * Method to save changes.
   *
   * @param {*} postData
   * @memberof RulesComponent
   */
  async saveChanges(postData){
    this.ngxSpinner.show();
    let msgkeys,severity;
    try{
    const result = await this.rulesService.postRules(postData,this.vesselId,this.tabIndex,this.selectedLoadableStudyId).toPromise();
    if (result?.responseStatus?.status === '200') {
       msgkeys = ['RULES_UPDATE_SUCCESS', 'RULES_UPDATE_SUCCESSFULLY']
       severity = 'success';
       this.cancelChanges();
      }
    }
    catch(error){
      msgkeys = ['RULES_UPDATE_ERROR', 'RULES_UPDATE_FAILED'];
      severity ="error"
     }
    const translationKeys = await this.translateService.get(msgkeys).toPromise();
    this.messageService.add({ severity: severity, summary: translationKeys[msgkeys[0]], detail: translationKeys[msgkeys[1]] });
    this.ngxSpinner.hide();
  }


  /**
  * Method to do actions while clicking on a tab
  *
  * @returns {void}
  * @memberof RulesComponent
  */
  async onTabClick(tab) {
    this.selectedTab = tab;
    switch (tab.toLowerCase()) {
      case 'plan': {
        this.tabIndex = 1;
        break;
      }
      case 'loading': {
        this.tabIndex = 2;
        break;

      }
      case 'discharging': {
        this.tabIndex = 3;
        break;
      }
    }
    this.rulesJson = await this.rulesService.getRules(this.vesselId,this.tabIndex,this.selectedLoadableStudyId).toPromise();
  }

  /**
  * Method to do actions while selecting a Voyage
  *
  * @returns {void}
  * @memberof RulesComponent
  */
   async onVesselSelected(event) {
     this.vesselId = event.value.id;
     this.rulesJson = await this.rulesService.getRules(this.vesselId,this.tabIndex,this.selectedLoadableStudyId).toPromise();
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
cancelChanges()
  {
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
}
