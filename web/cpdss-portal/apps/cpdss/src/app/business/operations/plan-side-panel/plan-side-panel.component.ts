import { Component, OnInit } from '@angular/core';
import { IDataTableColumn, IDataTableEvent } from '../../../shared/components/datatable/datatable.model';
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
  }

}
