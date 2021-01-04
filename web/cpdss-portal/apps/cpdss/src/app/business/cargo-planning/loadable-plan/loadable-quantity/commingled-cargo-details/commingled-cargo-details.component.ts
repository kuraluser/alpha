import { Component, OnInit, Input } from '@angular/core';
import { DecimalPipe  } from '@angular/common';

import { ITableHeaderModel , ILoadableQuantityCommingleCargo } from '../../../models/loadable-plan.model';

import { LoadablePlanTransformationService } from '../../../services/loadable-plan-transformation.service';

/**
 * Component class of commingle cargo details component in loadable plan
 *
 * @export
 * @class CommingledCargoDetailsComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-commingled-cargo-details',
  templateUrl: './commingled-cargo-details.component.html',
  styleUrls: ['./commingled-cargo-details.component.scss']
})
export class CommingledCargoDetailsComponent implements OnInit {

  @Input() set loadableQuantityCargoDetails(value: ILoadableQuantityCommingleCargo[]) {
    const loadableQuantityCargoDetails = value;
    this._loadableQuantityCargoDetails = [];
    loadableQuantityCargoDetails?.map((loadableQuantityCargoDetail) => {
      this.arrangeLoadablePlanCargoDetails(loadableQuantityCargoDetail)
    })
  }

  get loadableQuantityCargoDetails() {
    return this._loadableQuantityCargoDetails
  }

  public columns: any[];
  public _loadableQuantityCargoDetails: any[];

  constructor(
    private loadablePlanTransformationService: LoadablePlanTransformationService,
    private _decimalPipe: DecimalPipe
  ) { }

  ngOnInit(): void {
    this.columns = this.loadablePlanTransformationService.getCommingledCargoTableColumn();
  }

  /**
   * arrange Loadable Plan CargoDetails 
   */
  arrangeLoadablePlanCargoDetails(loadableQuantityCargoDetail: ILoadableQuantityCommingleCargo) {
    this._loadableQuantityCargoDetails.push(this.loadablePlanTransformationService.getFormatedLoadableCommingleCargo(this._decimalPipe , loadableQuantityCargoDetail));
  }
}
