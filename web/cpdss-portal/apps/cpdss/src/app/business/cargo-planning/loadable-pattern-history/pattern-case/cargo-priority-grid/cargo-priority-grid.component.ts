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

  @Input() get loadablePatternCargoDetails(): ILoadablePatternCargoDetail[] {
    return this._loadablePatternCargoDetails;
  }

  set loadablePatternCargoDetails(loadablePatternCargoDetails: ILoadablePatternCargoDetail[]) {
    if (loadablePatternCargoDetails) {
      this._loadablePatternCargoDetails = loadablePatternCargoDetails.sort((a, b) => (a.loadingOrder > b.loadingOrder) ? 1 : -1);
      this.totalQuantity = this.loadablePatternCargoDetails.reduce(function (a, b) {
        return Number(a) + Number(b?.quantity);
      }, 0);
      this.totalDifference = this.loadablePatternCargoDetails.reduce(function (a, b) {
        return Number(a) + Number(b?.difference);
      }, 0);
    }
  }

  totalQuantity = 0;
  totalDifference = 0;

  private _loadablePatternCargoDetails: ILoadablePatternCargoDetail[];

  constructor() { }

  /**
  * Component lifecycle ngOnit
  *
  * @returns {Promise<void>}
  * @memberof CargoPriorityGridComponent
  */
  ngOnInit(): void {
    
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
