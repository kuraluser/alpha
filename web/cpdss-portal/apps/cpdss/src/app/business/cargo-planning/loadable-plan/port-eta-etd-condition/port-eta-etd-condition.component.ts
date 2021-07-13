import { Component, OnInit, Input } from '@angular/core';
import { DecimalPipe } from '@angular/common';

import { IDataTableColumn } from '../../../../shared/components/datatable/datatable.model';
import { ISynopticalRecordArrangeModel } from '../../models/loadable-plan.model';
import { LoadablePlanTransformationService } from '../../services/loadable-plan-transformation.service';
import { ILoadablePlanSynopticalRecord } from '../../models/cargo-planning.model';

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

  public tableRow: IDataTableColumn[];
  public value: any[];
  public tableCol: any[];
  public arrangeRecords: ISynopticalRecordArrangeModel[];

  // private
  private _synopticalRecords: ILoadablePlanSynopticalRecord[];

  @Input() set synopticalRecords(value: ILoadablePlanSynopticalRecord[]) {
    this._synopticalRecords = value;
    this.setTableHeader(this._synopticalRecords);
  }

  @Input() vesselLightWeight: number;

  // public method
  constructor(
    private loadablePlanTransformationService: LoadablePlanTransformationService,
    private _decimalPipe: DecimalPipe
  ) { }

  /**
   * Component lifecycle ngOnit
   *
   * @returns {void}
   * @memberof PortEtaEtdConditionComponent
   */
  ngOnInit(): void {
    this.tableRow = this.loadablePlanTransformationService.getEtaEtdTableColumns();
  }

  /**
  * set table header
  * @returns {void}
  * @memberof PortEtaEtdConditionComponent
  */
  private setTableHeader(synopticalRecords: ILoadablePlanSynopticalRecord[]) {
    this.tableCol = [];
    this.arrangeRecords = [];
    synopticalRecords?.map((synopticalRecord: ILoadablePlanSynopticalRecord) => {
      synopticalRecord.operationType === 'ARR' ? this.tableCol.push({ header: 'ARRIVAL' }) : this.tableCol.push({ header: 'DEPARTURE' });
      this.arrangeRecords.push(this.loadablePlanTransformationService.getFormatedEtaEtdData(this._decimalPipe , synopticalRecord, this.vesselLightWeight));
    })
  }

}
