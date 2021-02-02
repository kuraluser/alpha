import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ICargoTank, ITankOptions } from '../../../core/models/common.model';
import { ILoadablePattern } from '../../models/loadable-pattern.model';

/**
 * Component for pattern case
 *
 * @export
 * @class PatternCaseComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-pattern-case',
  templateUrl: './pattern-case.component.html',
  styleUrls: ['./pattern-case.component.scss']
})
export class PatternCaseComponent implements OnInit {

  @Output() displayCommingleDetailPopup = new EventEmitter();

  @Input() index: number;
  @Input() loadablePattern: ILoadablePattern;
  @Input() tankList: ICargoTank[][];
  
  loadablePatternDetailsId: number;
  tanks: ICargoTank[][];
  cargoTankOptions: ITankOptions = { isFullyFilled: false, fillingPercentageField: 'fillingRatio' }
  constructor() { }

  /**
   * Component lifecycle ngOnit
   *
   * @returns {Promise<void>}
   * @memberof PatternCaseComponent
   */
  ngOnInit(): void {
    this.loadablePatternDetailsId = this.loadablePattern?.loadablePatternId;
    this.updateTankLIst()
  }

  /**
   * Method to update commodity in tank list
   *
   * @memberof PatternCaseComponent
   */
  updateTankLIst() {
    this.tanks = this.tankList.map(group => {
      const newGroup = group.map((groupItem) => {
        const tank = Object.assign({}, groupItem);
        tank.commodity = this.loadablePattern.loadablePlanStowageDetails.find((item) => (item.tankId === groupItem.id) && item);
        return tank;
      });
      return newGroup;
    })
  }

    /**
   * Method to show commingle cargo details pop up
   *
   * @memberof PatternCaseComponent
   */
  showComminglePopup(event) {
    const commingleData = {
      loadablePatternDetailsId: this.loadablePatternDetailsId,
      loadablePatternCargoDetail: event
    }
    this.displayCommingleDetailPopup.emit(commingleData)
  }
}
