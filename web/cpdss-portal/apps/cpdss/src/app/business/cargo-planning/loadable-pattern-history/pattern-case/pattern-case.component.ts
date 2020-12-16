import { Component, Input, OnInit } from '@angular/core';
import { ICargoTank } from '../../../core/models/common.model';
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
  @Input() index: number;
  @Input() loadablePattern: ILoadablePattern;
  @Input() tankList: ICargoTank[][];
  tanks: ICargoTank[][];
  constructor() { }

  /**
   * Component lifecycle ngOnit
   *
   * @returns {Promise<void>}
   * @memberof PatternCaseComponent
   */
  ngOnInit(): void {
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
        tank.commodity = this.loadablePattern.loadablePatternCargoDetails.find((item) => (item.tankId === groupItem.id) && item);
        return tank;
      });
      return newGroup;
    })
  }
}
