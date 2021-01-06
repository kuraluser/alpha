import { Component, OnInit, Input } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';

import { IDataTableColumn } from '../../../../shared/components/datatable/datatable.model';

import { LoadablePlanTransformationService } from '../../services/loadable-plan-transformation.service';
import { LoadablePlanApiService } from '../../services/loadable-plan-api.service';

/**
 * Component class of ports eta etd component in loadable plan
 *
 * @export
 * @class PortEtaEtdConditionComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-port-eta-etd-condition',
  templateUrl: './port-eta-etd-condition.component.html',
  styleUrls: ['./port-eta-etd-condition.component.scss']
})
export class PortEtaEtdConditionComponent implements OnInit {

  columns: IDataTableColumn[];
  value: any[];

  constructor(
    private ngxSpinnerService: NgxSpinnerService,
    private loadablePlanTransformationService: LoadablePlanTransformationService,
    private loadablePlanApiService: LoadablePlanApiService
  ) { }

  ngOnInit(): void {
    this.columns = this.loadablePlanTransformationService.getEtaEtdTableColumns();
  }

}
