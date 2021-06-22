import { Component, OnInit } from '@angular/core';
import { RulesService } from './services/rules.service';
import { RULES_TABS, RULE_TYPES } from './../models/rules.model'
import { DATATABLE_FIELD_TYPE } from '../../../shared/components/datatable/datatable.model';

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
  rulesJson = {
    plan: [
      {
        header: "Vessel Stability Rules",
        rules: [
          {
            id: 1,
            inputs: [
              {
                prefix: "Trim between",
                defaultValue: -0.1,
                type: this.fieldType.NUMBER,
                max: 0.1,
                min: -0.1,
                value: 10
              },
              {
                prefix: "and",
                defaultValue: -0.1,
                type: this.fieldType.NUMBER,
                max: 0.1,
                min: -0.1,
                suffix: "meters"
              }
            ],
            ruleType: RULE_TYPES.ABSOLUTE,
            enable: true,
            disable: true
          },
          {
            id: 2,
            inputs: [
              {
                prefix: "List is",
                defaultValue: -0.1,
                type: this.fieldType.NUMBER,
                max: 0.1,
                min: -0.1,
                suffix: "degree",
                value: 5
              },
            ],
            ruleType: RULE_TYPES.PREFERRABLE,
          },
          {
            id: 2,
            inputs: [
              {
                prefix: "Ensure draft not over the load line",
              },
            ],
            ruleType: RULE_TYPES.PREFERRABLE,
          }
        ]
      },
      {
        header: "Vessel Facility Rules",
      },
      {
        header: "Ports/Berth Clearance Rules",
      },
      {
        header: "Vessel Tank Compatibility Rules",
      },
      {
        header: "Adjacent Cargo Compatibility Rules",
      },
      {
        header: "Prior Cargo List Rules",
      },
      {
        header: "Definition of Constant/System Rules"
      },
      {
        header: "Algorithm Rules"
      }

    ]
  }
  constructor(
    public rulesService: RulesService
  ) { }

  /**
  * Component Lifecycle Hook OnInit
  *
  * @returns {void}
  * @memberof RulesComponent
  */
  ngOnInit(): void {
    this.rulesService.init()
  }

  /**
  * Method to do actions while clicking on a tab
  *
  * @returns {void}
  * @memberof RulesComponent
  */
  onTabClick(tab) {
    this.selectedTab = tab
  }

  /**
  * Method to do actions while selecting a Voyage
  *
  * @returns {void}
  * @memberof RulesComponent
  */
  onVoyageSelected() {

  }

}
