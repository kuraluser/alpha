import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { DATATABLE_FIELD_TYPE } from 'apps/cpdss/src/app/shared/components/datatable/datatable.model';
import { RULES_TABS, RULE_TYPES } from '../../models/cargo-planning-rules.model';


/**
 * Component class cargo planning rules.
 *
 * @export
 * @class CargoPlanningRulesComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-cargo-planning-rules',
  templateUrl: './cargo-planning-rules.component.html',
  styleUrls: ['./cargo-planning-rules.component.scss']
})
export class CargoPlanningRulesComponent implements OnInit {

  @Input() visible;

  @Output() popUpClosed: EventEmitter<any> = new EventEmitter();;

  constructor() { }

  TABS = RULES_TABS;
  tabs = Object.keys(this.TABS)
  selectedTab: string = this.TABS[this.tabs[0]];
  openSidePane = true;
  selectedIndex = 0;
  readonly fieldType = DATATABLE_FIELD_TYPE;
  rulesJson = {                 //TODO - will be replaced with api call result later.
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


  /**
   * Component lifecycle ngoninit.
   *
   * @memberof CargoPlanningRulesComponent
   */
  ngOnInit(): void {
    //TODO 
  }

  /**
   * Method to switch between tabs.
   *
   * @param {*} tab
   * @memberof CargoPlanningRulesComponent
   */
  onTabClick(tab) {
    this.selectedTab = tab
  }

  /**
   * Method to close the modal
   *
   * @memberof CargoPlanningRulesComponent
   */

  onClose() {
    this.popUpClosed.emit(false);
  }
}
