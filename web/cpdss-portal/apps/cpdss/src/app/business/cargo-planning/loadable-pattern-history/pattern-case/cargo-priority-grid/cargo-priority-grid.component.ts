import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ILoadablePatternCargoDetail } from '../../../models/loadable-pattern.model';


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

  @Input() loadablePatternCargoDetails: ILoadablePatternCargoDetail[];
  totalQuantity = 0;
  totalDifference = 0;
  constructor() { }

  /**
  * Component lifecycle ngOnit
  *
  * @returns {Promise<void>}
  * @memberof CargoPriorityGridComponent
  */
  ngOnInit(): void {
    this.totalQuantity = this.loadablePatternCargoDetails.map(a => Number(a.quantity)).reduce(function (a, b) {
      return Number(a) + Number(b);
    });
    this.totalDifference = this.loadablePatternCargoDetails.map(a => Number(a.orderedQuantity) - Number(a.quantity)).reduce(function (a, b) {
      return Number(a) + Number(b);
    });
    this.loadablePatternCargoDetails = this.loadablePatternCargoDetails.sort((a, b) => (a.loadingOrder > b.loadingOrder) ? 1 : -1);
  }

  /**
  * Method to check seleted cargo is comminglr.
  *
  * @memberof CargoPriorityGridComponent
  */
  onRowSelect(priorityData) {
    this.isCommingle.emit(priorityData)
  }


}
