import { Component, OnInit, Input } from '@angular/core';
import { IDataTableColumn, IDataTableEvent } from '../../../shared/components/datatable/datatable.model';
import { IBerth, IBerthDetails, ILoadingRates } from '../models/loading-discharging.model';
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
  _loadingPlanData: any;

  @Input() get loadingPlanData(): any{
    return this._loadingPlanData;
  }

  set loadingPlanData(value: any){
    this._loadingPlanData = value;
    this.setData();
  }

  constructor(
    private planSidePanelTransformationService: PlanSidePanelTransformationService
  ) { }

  ngOnInit(): void {
       
  }

  setData(){
    this.berthDetails = this.loadingPlanData?.loadingInformation?.berthDetails;
    this.loadingRates = this.loadingPlanData?.loadingInformation?.loadingRates;
  }

}
