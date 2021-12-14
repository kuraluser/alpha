import { Component, Input, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';
import '../../../../../assets/simulator-js/load';
declare var load: any;

import { VesselsApiService } from '../../services/vessels-api.service';
import { SimulatorApiService } from './services/simulator-api.service';

import { IVessel } from '../../models/vessel-details.model';
import { ISimulatorLoadingSequenceResponse, ISimulatorLoadParams, ISimulatorStowageResponse, SIMULATOR_REQUEST_TYPE } from './simulator.model';

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

  @Input() requestType: SIMULATOR_REQUEST_TYPE;
  @Input() vesselId: number;
  @Input() loadableStudyId: number;
  @Input() caseNumber: string;
  @Input() buttonLabel: string;
  @Input() loadingInfoId: number;
  @Input() dischargeInfoId: number;

  public simulatorMenu: any;
  readonly SIMULATOR_REQUEST_TYPE = SIMULATOR_REQUEST_TYPE;

  constructor(
    private vesselsApiService: VesselsApiService,
    private simulatorApiService: SimulatorApiService,
    private messageService: MessageService,
    private translateService: TranslateService
  ) { }

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
   * Function to get JSON data for Simulator
   *
   * @param {*} selectdeUserRole
   * @param {IVessel} currentVessel
   * @memberof SimulatorComponent
   */
  async onLoadSimulator(selectdeUserRole: string, currentVessel: IVessel) {
    const translationKeys = await this.translateService.get(['SOMETHING_WENT_WRONG_ERROR', 'SIMULATOR_LOADICATOR_DATA_NULL']).toPromise();
    const userDetails = JSON.parse(localStorage.getItem('userDetails'));
    const simulatorInputURL = decodeURIComponent(localStorage.getItem('simulatorSiteUrl'));

    switch (this.requestType) {
      case SIMULATOR_REQUEST_TYPE.STOWAGE_PLAN:
        const stowageJSON: ISimulatorStowageResponse = await this.simulatorApiService.getStowagePlanJsonData(this.vesselId, this.loadableStudyId, this.caseNumber).toPromise();
        if (stowageJSON.responseStatus.status === "200") {
          const data: ISimulatorLoadParams = {
            shipName: currentVessel.name.toUpperCase(),
            stowageData: JSON.parse(JSON.stringify(stowageJSON)),
            loadicatorData: null,
            path: '$.departureCondition',
            userName: userDetails.rolePermissions.role,
            userRole: selectdeUserRole,
            requestType: this.requestType,
            url: simulatorInputURL
          };
          this.launchSimulator(data);
        }
        break;
      case SIMULATOR_REQUEST_TYPE.LOADING_SEQUENCE:
        const loadingSeqJSON: ISimulatorLoadingSequenceResponse = await this.simulatorApiService.getLoadingSequencePlanJson(this.vesselId, this.loadingInfoId).toPromise();
        if (loadingSeqJSON.responseStatus.status === "200") {
          const data: ISimulatorLoadParams = {
            shipName: currentVessel.name.toUpperCase(),
            stowageData: JSON.parse(JSON.stringify(loadingSeqJSON.loadingJson)),
            loadicatorData: JSON.parse(JSON.stringify(loadingSeqJSON.loadicatorJson)),
            path: '$',
            userName: userDetails.rolePermissions.role,
            userRole: selectdeUserRole,
            requestType: this.requestType,
            url: simulatorInputURL
          };
          if (loadingSeqJSON.loadicatorJson) {
            this.launchSimulator(data);
          } else {
            this.messageService.add({ severity: 'error', summary: translationKeys['SOMETHING_WENT_WRONG_ERROR'], detail: translationKeys['SIMULATOR_LOADICATOR_DATA_NULL'] });
          }
        }
        break;
    }
  }

  /**
   * Function to launch
   *
   * @param {ISimulatorLoadParams} data
   * @memberof SimulatorComponent
   */
  launchSimulator(data: ISimulatorLoadParams): void {
    load(data.shipName, data.stowageData, data.loadicatorData, data.path, data.userName, data.userRole, data.requestType, data.url);
  }

}
