import { Component, OnInit } from '@angular/core';
import { RulesService } from '../../core/services/rules.service';
import { RULES_TABS } from './../models/rules.model'
import { DATATABLE_FIELD_TYPE } from '../../../shared/components/datatable/datatable.model';
import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { TranslateService } from '@ngx-translate/core';
import { MessageService } from 'primeng/api';
import { NgxSpinnerService } from 'ngx-spinner';
import { Subject } from 'rxjs';

/**
 * Component Class for Rules
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

  TABS = RULES_TABS;
  tabs = Object.keys(this.TABS)
  selectedTab: string = this.TABS[this.tabs[0]];
  openSidePane = true;
  selectedIndex = 0;
  readonly fieldType = DATATABLE_FIELD_TYPE;
  rulesJson: any;
  errorArray = [];
  vesselId: number;
  ruleTypeId: number;
  tabIndex: number = 1;
  vessels: any;
  selectedVessel: any;
  rulesChange = new Subject();

  constructor(
    public rulesService: RulesService, private translateService: TranslateService, private messageService: MessageService,
    private ngxSpinner: NgxSpinnerService,
  ) { }

  /**
  * Component Lifecycle Hook OnInit
  *
  * @returns {void}
  * @memberof RulesComponent
  */
  async ngOnInit(): Promise<void> {
    this.ngxSpinner.show();
    await this.rulesService.init();
    await this.setSelectedVessel(); 
    await this.initRules();
    this.triggerGetRules();  
    this.setSelectedTab();
    this.ngxSpinner.hide();
  }

  /**
   * Method to initialise rules.
   *
   * @memberof RulesComponent
   */

  async initRules() {
    this.rulesChange.subscribe(async (res) => {
      this.ngxSpinner.show();
      await this.getRules();
      this.setIndex(this.selectedIndex);
      this.ngxSpinner.hide();
    })
  }

  /**
   * Method to set the selected vessel.
   *
   * @memberof RulesComponent
   */
  async setSelectedVessel() {
    this.vessels = await this.rulesService.vessels;
    this.vesselId = await this.rulesService.vessels[0].id;
    this.selectedVessel = this.vessels[0];
  }

  /**
   * Method to get rules.
   *
   * @memberof RulesComponent
   */
  async getRules() {
    this.ngxSpinner.show();
    this.rulesJson = await this.rulesService.getRules(this.vesselId, this.tabIndex).toPromise();
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
   * Method to set the selected tab.
   *
   * @memberof RulesComponent
   */

  setSelectedTab() {
    this.rulesService.selectedTab$.next('plan');
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
      let result = await this.rulesService.postRules(postData, this.vesselId, this.tabIndex).toPromise();
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


  /**
  * Method to do actions while clicking on a tab
  *
  * @returns {void}
  * @memberof RulesComponent
  */
  async onTabClick(tab) {
    this.ngxSpinner.show();
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
    this.triggerGetRules();
    this.ngxSpinner.hide();
  }

  /**
  * Method to do actions while selecting a Vessel.
  *
  * @returns {void}
  * @memberof RulesComponent
  */
  async onVesselSelected(event) {
    this.ngxSpinner.show();
    this.vesselId = event.value.id;
    this.triggerGetRules();
    this.ngxSpinner.hide();
  }

  /**
   * Method to trigger get rules api.
   *
   * @memberof RulesComponent
   */
  triggerGetRules() {
    this.rulesChange.next();
  }

  /**
   * Method to trigger save.
   *
   * @memberof RulesComponent
   */

  triggerSaveChanges() {
    this.rulesService.save.next();
  }

}
