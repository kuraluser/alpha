import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { IDataTableColumn } from 'apps/cpdss/src/app/shared/components/datatable/datatable.model';
import { ILoadablePatternCargoDetail } from '../../models/loadable-pattern.model';


/**
 * Component for priority grid
 *
 * @export
 * @class CargoPriorityGridComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-cargo-priority-grid',
  templateUrl: './cargo-priority-grid.component.html',
  styleUrls: ['./cargo-priority-grid.component.scss']
})
export class CargoPriorityGridComponent implements OnInit {

  @Output() isCommingle = new EventEmitter();
  @Output() patternViewMore = new EventEmitter();

  @Input() tableCol: IDataTableColumn[];
  @Input() totalHeader: string;
  @Input() styleClass: string;
  @Input() showTotalDifference = true;
  @Input() showMoreButton = true;

  @Input() get loadablePatternCargoDetails(): ILoadablePatternCargoDetail[] {
    return this._loadablePatternCargoDetails;
  }

  set loadablePatternCargoDetails(loadablePatternCargoDetails: ILoadablePatternCargoDetail[]) {
    if (loadablePatternCargoDetails) {
      this._loadablePatternCargoDetails = loadablePatternCargoDetails.sort((a, b) => (a.loadingOrder >= b.loadingOrder) ? 1 : -1);
      this.totalQuantity = loadablePatternCargoDetails.reduce(function (a, b) {
        return Number(a) + Number(b?.quantity);
      }, 0);
      const totalQuantity = loadablePatternCargoDetails.reduce(function (a, b) {
        return Number(a) + Number(b?.quantity);
      }, 0);
      const totalOrderedQuantity = loadablePatternCargoDetails.reduce(function (a, b) {
        return Number(a) + Number(b?.orderedQuantity);
      }, 0);
      this.totalDifference = ((totalQuantity - totalOrderedQuantity)/totalOrderedQuantity) * 100;
    }
  }

  totalQuantity = 0;
  totalDifference = 0;

  unit:string;

  private _loadablePatternCargoDetails: ILoadablePatternCargoDetail[];

  constructor() { }

  /**
  * Component lifecycle ngOnit
  *
  * @returns {Promise<void>}
  * @memberof CargoPriorityGridComponent
  */
  ngOnInit(): void {
    this.unit = localStorage.getItem('unit');
  }

  /**
  * Method to check seleted cargo is comminglr.
  *
  * @memberof CargoPriorityGridComponent
  */
  onRowSelect(priorityData) {
    this.isCommingle.emit(priorityData)
  }

  /**
  * Method to show pattern view more pop up
  *
  * @memberof CargoPriorityGridComponent
  */
  viewMore() {
    this.patternViewMore.emit()
  }


}
