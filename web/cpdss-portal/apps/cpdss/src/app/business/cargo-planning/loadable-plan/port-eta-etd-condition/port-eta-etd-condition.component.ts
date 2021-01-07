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
  header: any[];
  data: any[];

  constructor(
    private ngxSpinnerService: NgxSpinnerService,
    private loadablePlanTransformationService: LoadablePlanTransformationService,
    private loadablePlanApiService: LoadablePlanApiService
  ) { }

  ngOnInit(): void {
    this.header = [
      { colspan: 2},
      { header: "ARRIVAL" , field: "arrival1"},
      { header: "DEPARTURE" , field: "depature1"},
      { header: "ARRIVAL" , field: "arrival2"},
      { header: "DEPARTURE" , field: "depature2"},
      { header: "ARRIVAL" , field: "arrival3"},
      { header: "DEPARTURE" , field: "depature3"},
      { header: "ARRIVAL" , field: "arrival4"},
      { header: "DEPARTURE" , field: "depature4"},
      { header: "ARRIVAL" , field: "arrival5"},
      { header: "DEPARTURE" , field: "depature5"},
    ]
    this.data = [
      {arrival1: "port", depature1: "port", arrival2: "port", depature2: "port",arrival3: "port", depature3: "port",arrival4: "port", depature4: "port",arrival5: "port", depature5: "port"},
      {arrival1: "port1", depature1: "port", arrival2: "port", depature2: "port",arrival3: "port", depature3: "port",arrival4: "port", depature4: "port",arrival5: "port", depature5: "port"},
      {arrival1: "port2", depature1: "port", arrival2: "port", depature2: "port",arrival3: "port", depature3: "port",arrival4: "port", depature4: "port",arrival5: "port", depature5: "port"},
      {arrival1: "port3", depature1: "port", arrival2: "port", depature2: "port",arrival3: "port", depature3: "port",arrival4: "port", depature4: "port",arrival5: "port", depature5: "port"},
      {arrival1: "port4", depature1: "port", arrival2: "port", depature2: "port",arrival3: "port", depature3: "port",arrival4: "port", depature4: "port",arrival5: "port", depature5: "port"},
      {arrival1: "port5", depature1: "port", arrival2: "port", depature2: "port",arrival3: "port", depature3: "port",arrival4: "port", depature4: "port",arrival5: "port", depature5: "port"},
      {arrival1: "port6", depature1: "port", arrival2: "port", depature2: "port",arrival3: "port", depature3: "port",arrival4: "port", depature4: "port",arrival5: "port", depature5: "port"},
      {arrival1: "port7", depature1: "port", arrival2: "port", depature2: "port",arrival3: "port", depature3: "port",arrival4: "port", depature4: "port",arrival5: "port", depature5: "port"},
      {arrival1: "port8", depature1: "port", arrival2: "port", depature2: "port",arrival3: "port", depature3: "port",arrival4: "port", depature4: "port",arrival5: "port", depature5: "port"},
      {arrival1: "port9", depature1: "port", arrival2: "port", depature2: "port",arrival3: "port", depature3: "port",arrival4: "port", depature4: "port",arrival5: "port", depature5: "port"},
      {arrival1: "port10", depature1: "port", arrival2: "port", depature2: "port",arrival3: "port", depature3: "port",arrival4: "port", depature4: "port",arrival5: "port", depature5: "port"},
      {arrival1: "port11", depature1: "port", arrival2: "port", depature2: "port",arrival3: "port", depature3: "port",arrival4: "port", depature4: "port",arrival5: "port", depature5: "port"},
      {arrival1: "port12", depature1: "port", arrival2: "port", depature2: "port",arrival3: "port", depature3: "port",arrival4: "port", depature4: "port",arrival5: "port", depature5: "port"},
      {arrival1: "port13", depature1: "port", arrival2: "port", depature2: "port",arrival3: "port", depature3: "port",arrival4: "port", depature4: "port",arrival5: "port", depature5: "port"},
      {arrival1: "port14", depature1: "port", arrival2: "port", depature2: "port",arrival3: "port", depature3: "port",arrival4: "port", depature4: "port",arrival5: "port", depature5: "port"},
      {arrival1: "port15", depature1: "port", arrival2: "port", depature2: "port",arrival3: "port", depature3: "port",arrival4: "port", depature4: "port",arrival5: "port", depature5: "port"},
      {arrival1: "port16", depature1: "port", arrival2: "port", depature2: "port",arrival3: "port", depature3: "port",arrival4: "port", depature4: "port",arrival5: "port", depature5: "port"},
      {arrival1: "port17", depature1: "port", arrival2: "port", depature2: "port",arrival3: "port", depature3: "port",arrival4: "port", depature4: "port",arrival5: "port", depature5: "port"}
    ]
    this.columns = this.loadablePlanTransformationService.getEtaEtdTableColumns();
  }

}
