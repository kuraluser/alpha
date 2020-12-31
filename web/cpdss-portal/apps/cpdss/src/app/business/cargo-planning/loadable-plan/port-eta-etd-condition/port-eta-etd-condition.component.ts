import { Component, OnInit, Input } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';

import { ITableHeaderModel } from '../../models/loadable-plan.model';

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
  @Input() voyageId: number;
  @Input() loadableStudyId: number;
  @Input() vesselId: number;
  @Input() loadablePatternId: number;

  columns: ITableHeaderModel[];
  value: any[];

  constructor(
    private ngxSpinnerService: NgxSpinnerService,
    private loadablePlanTransformationService: LoadablePlanTransformationService,
    private loadablePlanApiService: LoadablePlanApiService
  ) { }

  ngOnInit(): void {
    this.columns = this.loadablePlanTransformationService.getEtaEtdTableColumns();
    this.getEtaEtdDetails();
  }

  /**
  * Get  details for Eta & Etd
  *
  */
  async getEtaEtdDetails() {
    this.ngxSpinnerService.show();
    // let data = await this.loadablePlanApiService.getLoadablePlanEtaEtdDetails(this.vesselId, this.voyageId, this.loadableStudyId , this.loadablePatternId).toPromise();
    this.ngxSpinnerService.hide();
  }

}
