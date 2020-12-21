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
  @Input() totalDifferenceColor: string;
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
    this.totalDifference = this.loadablePatternCargoDetails.map(a => Number(a.difference)).reduce(function (a, b) {
      return Number(a) + Number(b);
    });
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
