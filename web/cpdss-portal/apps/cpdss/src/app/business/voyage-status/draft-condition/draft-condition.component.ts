import { Component, Input, OnInit } from '@angular/core';
import { IDataTableColumn } from '../../../shared/components/datatable/datatable.model';
import { IDraftCondition } from '../models/voyage-status.model';
import { VoyageStatusTransformationService } from '../services/voyage-status-transformation.service';

/**
 * Component for Draft Conditions
 *
 * @export
 * @class DraftConditionComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-draft-condition',
  templateUrl: './draft-condition.component.html',
  styleUrls: ['./draft-condition.component.scss']
})
export class DraftConditionComponent implements OnInit {

  @Input() draftCondition: IDraftCondition;

  columns: IDataTableColumn[];
  value = [];

  constructor(private voyageStatusTransformationService: VoyageStatusTransformationService) { }

  ngOnInit(): void {
    this.columns = this.voyageStatusTransformationService.getDraftConditionColumnFields();
    this.value = [
      { header: 'VOYAGE_STATUS_DRAFT_CONDITION_CALCULATED', aft: this.draftCondition?.calculatedDraftAftActual, mid: this?.draftCondition.calculatedDraftMidActual, fore: this.draftCondition?.calculatedDraftFwdActual },
      { header: 'VOYAGE_STATUS_DRAFT_CONDITION_CORRECTED', aft: this.draftCondition?.finalDraftAft, mid: this.draftCondition?.finalDraftMid, fore: this.draftCondition?.finalDraftFwd }
    ]
  }

}
