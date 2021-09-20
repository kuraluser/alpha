import { Component, OnInit, Input } from '@angular/core';
import { IDataTableColumn, IDataTableEvent } from '../../../shared/components/datatable/datatable.model';
import { OPERATIONS } from '../../core/models/common.model';
import { IBerth, IBerthDetails, IDischargingRates, ILoadingRates } from '../models/loading-discharging.model';
import { PlanSidePanelTransformationService } from './plan-side-panel-transformation.service';

/**
 * Component class for plan side panel
 *
 * @export
 * @class PlanSidePanelComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-plan-side-panel',
  templateUrl: './plan-side-panel.component.html',
  styleUrls: ['./plan-side-panel.component.scss']
})
export class PlanSidePanelComponent implements OnInit {

  arrivalColumns: IDataTableColumn[];
  departureColumns: IDataTableColumn[];
  berthDetails: IBerthDetails;
  loadingRates: ILoadingRates;
  dischargeRates: IDischargingRates;
  operation: OPERATIONS;
  _loadingDischargingPlanData: any;

  @Input() get loadingDischargingPlanData(): any{
    return this._loadingDischargingPlanData;
  }

  set loadingDischargingPlanData(value: any){
    this._loadingDischargingPlanData = value;
    this.setData();
  }

  constructor(
    private planSidePanelTransformationService: PlanSidePanelTransformationService
  ) { }

  ngOnInit(): void { 
  }

  setData(){
    if (this.loadingDischargingPlanData?.loadingInformation) {
      this.berthDetails = this.loadingDischargingPlanData?.loadingInformation?.berthDetails;
      this.loadingRates = this.loadingDischargingPlanData?.loadingInformation?.loadingRates;
      this.operation = OPERATIONS.LOADING;
    } else {
      this.berthDetails = this.loadingDischargingPlanData?.dischargingInformation?.berthDetails;
      this.dischargeRates = this.loadingDischargingPlanData?.dischargingInformation?.dischargeRates;
      this.operation = OPERATIONS.DISCHARGING;
    }
  }

}
