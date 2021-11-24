import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { QUANTITY_UNIT } from '../../../shared/models/common.model';
import { LoadingDischargingTransformationService } from './../services/loading-discharging-transformation.service';
import { IDataTableColumn } from '../../../shared/components/datatable/datatable.model';

/**
 * Component class for commingle pop up
 *
 * @export
 * @class LoadingDischargingCommingleDetailsComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-loading-discharging-commingle-details',
  templateUrl: './loading-discharging-commingle-details.component.html',
  styleUrls: ['./loading-discharging-commingle-details.component.scss']
})
export class LoadingDischargingCommingleDetailsComponent implements OnInit {

  @Input() display;
  @Input() currentQuantitySelectedUnit: QUANTITY_UNIT;
  @Input() commingleData;

  @Output() displayPopup = new EventEmitter();

  columns: IDataTableColumn[];
  constructor(
    private loadingDischargingTransformationService: LoadingDischargingTransformationService
  ) { }

  ngOnInit(): void {
    this.columns = this.loadingDischargingTransformationService.getCommingleDetailsDatatableColumns();
  }

  /**
  * close pop up
  * @memberof LoadingDischargingCommingleDetailsComponent
  **/
  closeDialog() {
    this.displayPopup.emit(false);
  }

}
