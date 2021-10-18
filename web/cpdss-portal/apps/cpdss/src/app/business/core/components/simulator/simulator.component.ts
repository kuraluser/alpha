import { Component, Input, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import '../../../../../assets/external/simulator-js/load';
declare var load: any;

import { SimulatorApiService } from './../../services/simulator-api.service';
import { VesselsApiService } from '../../services/vessels-api.service';

import { IVessel } from '../../models/vessel-details.model';
import { ISimulatorResponse } from '../../models/common.model';

/**
 * Component for launch Simulator
 *
 * @export
 * @class SimulatorComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-simulator',
  templateUrl: './simulator.component.html',
  styleUrls: ['./simulator.component.scss']
})
export class SimulatorComponent implements OnInit {

  @Input() vesselId: number;
  @Input() loadableStudyId: number;
  @Input() caseNumber: string;
  @Input() buttonLabel: string;
  
  public simulatorMenu: any;
  
  constructor(
    private vesselsApiService: VesselsApiService,
    private simulatorApiService: SimulatorApiService,
    private translateService: TranslateService
  ) {}

  async ngOnInit(): Promise<void> {
    const vessels: IVessel[] = await this.vesselsApiService.getVesselsInfo().toPromise();
    const currentVessel: IVessel = vessels.find(vessel => (vessel.id === Number(localStorage.getItem("vesselId"))));
    const translationKeys = await this.translateService.get(['LOADABLE_PLAN_USER_ROLE_SELECT_MASTER_LABEL', 'LOADABLE_PLAN_USER_ROLE_SELECT_VIEWER_LABEL']).toPromise();
    this.simulatorMenu = [
      {
        label: translationKeys['LOADABLE_PLAN_USER_ROLE_SELECT_MASTER_LABEL'].toUpperCase(),
        command: () => { this.onLoadSimulator(translationKeys['LOADABLE_PLAN_USER_ROLE_SELECT_MASTER_LABEL'], currentVessel) }
      },
      {
        label: translationKeys['LOADABLE_PLAN_USER_ROLE_SELECT_VIEWER_LABEL'].toUpperCase(),
        command: () => { this.onLoadSimulator(translationKeys['LOADABLE_PLAN_USER_ROLE_SELECT_VIEWER_LABEL'], currentVessel) }
      }
    ];
  }

  /**
   * function to load Simulator
   *
   * @param {*} selectdeUserRole
   * @param {IVessel} currentVessel
   * @memberof SimulatorComponent
   */
  async onLoadSimulator(selectdeUserRole, currentVessel: IVessel) {
    const simulatorJSON: ISimulatorResponse = await this.simulatorApiService.getSimulatorJsonData(this.vesselId, this.loadableStudyId, this.caseNumber).toPromise();
    if (simulatorJSON.responseStatus.status === "200") {
      const userDetails = JSON.parse(localStorage.getItem('userDetails'));
      const userName = userDetails.rolePermissions.role;
      const shipName = currentVessel.name.toUpperCase();
      const requestType = 'StowagePlan';
      const stowageData = JSON.parse(JSON.stringify(simulatorJSON));
      const loadicatorData = null;
      const path = '$.departureCondition';
      const userRole = selectdeUserRole;

      // NOTE: parameter order must not be change
      new load(shipName, stowageData, loadicatorData, path, userName, userRole, requestType);
    }
  }

}
