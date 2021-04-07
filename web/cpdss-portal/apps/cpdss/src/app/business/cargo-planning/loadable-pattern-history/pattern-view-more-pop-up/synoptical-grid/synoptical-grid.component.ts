import { Component, Input, OnInit } from '@angular/core';
import { IDataTableColumn } from '../../../../../shared/components/datatable/datatable.model';
import { ILoadablePlanSynopticalRecord } from '../../../models/cargo-planning.model';
import { LoadableStudyPatternTransformationService } from '../../../services/loadable-study-pattern-transformation.service';

/**
 * Component class of show synoptical grid
 *
 * @export
 * @class SynopticalGridComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-synoptical-grid',
  templateUrl: './synoptical-grid.component.html',
  styleUrls: ['./synoptical-grid.component.scss']
})
export class SynopticalGridComponent implements OnInit {
  @Input() loadablePlanSynopticRecord: ILoadablePlanSynopticalRecord;

  tableRow: IDataTableColumn[];
  public tableCol: ILoadablePlanSynopticalRecord[] = [];
  constructor(private loadableStudyPatternTransformationService: LoadableStudyPatternTransformationService) { }

  ngOnInit(): void {
    this.tableRow = this.loadableStudyPatternTransformationService.getSynopticalRecordTableColumn();
    this.tableCol.push(this.loadablePlanSynopticRecord)
  }

}
