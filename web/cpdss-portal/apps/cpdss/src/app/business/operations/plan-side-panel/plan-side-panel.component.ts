import { Component, OnInit } from '@angular/core';
import { IDataTableColumn, IDataTableEvent } from '../../../shared/components/datatable/datatable.model';
import { IBerth } from '../models/loading-information.model';
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
  arrivalData = [];
  selectedBerths: IBerth[] = [];
  constructor(
    private planSidePanelTransformationService: PlanSidePanelTransformationService
  ) { }

  ngOnInit(): void {
    
    this.arrivalData = [
      {
        label: 'KU', value: '0 MT'
      },
      {
        label: 'AEL', value: '0 MT'
      },
    ]

    this.selectedBerths = [
      {
        "id": 15035,
        "portId": null,
        "loadingInfoId": 43,
        "maxShpChannel": null,
        "berthName": "North",
        "loadingBerthId": 1,
        "maxShipDepth": 2.0000,
        "hoseConnections": null,
        "seaDraftLimitation": 2.0000,
        "airDraftLimitation": 2.0000,
        "maxManifoldHeight": 2.0000,
        "regulationAndRestriction": "2.0000",
        "maxLoa": null,
        "maxDraft": null
      }
    ]
  }

}
