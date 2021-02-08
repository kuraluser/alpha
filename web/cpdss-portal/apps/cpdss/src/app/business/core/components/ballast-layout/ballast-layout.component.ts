import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ITank, ITankOptions } from '../../models/common.model';

/**
 * Component class for ballast tank layout
 *
 * @export
 * @class BallastLayoutComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-ballast-layout',
  templateUrl: './ballast-layout.component.html',
  styleUrls: ['./ballast-layout.component.scss']
})
export class BallastLayoutComponent implements OnInit {

  @Input() options: ITankOptions = { 'isFullyFilled': false, 'showTooltip': false, 'isSelectable': true };

  @Input()
  get tanks(): ITank[][] {
    return this._tanks;
  }

  set tanks(tanks: ITank[][]) {
    this._tanks = tanks.map(group => group.map(tank => {
      tank.gridColumn = this.gridColumn(group, tank);
      tank.percentageFilled = this.options?.isFullyFilled ? '100%' : this.getFillingPercentage(tank);
      return tank;
    }));
  }

  @Input()
  get rearTanks(): ITank[][] {
    return this._rearTanks;
  }

  set rearTanks(rearTanks: ITank[][]) {
    this._rearTanks = rearTanks.map(group => group.map(tank => {
      tank.percentageFilled = this.getFillingPercentage(tank);
      return tank;
    }));
  }

  @Input()
  get frontTanks(): ITank[][] {
    return this._frontTanks;
  }

  set frontTanks(frontTanks: ITank[][]) {
    this._frontTanks = frontTanks.map(group => group.map(tank => {
      tank.percentageFilled = this.getFillingPercentage(tank);
      return tank;
    }));
  }

  @Input() selectedTankId: number;

  @Output() selectedTankIdChange = new EventEmitter<number>();

  private _tanks: ITank[][];
  private _rearTanks: ITank[][];
  private _frontTanks: ITank[][];

  constructor() { }

  ngOnInit(): void {
  }

  /**
 * Method for getting tank group width
 *
 * @param {*} firstGroup
 * @param {*} currentGroup
 * @param {*} lastGroup
 * @returns
 * @memberof BallastLayoutComponent
 */
  getTankGroupWidthPercentage(firstArray: ITank[], baseArray: ITank[], lastArray: ITank[]) {
    let firstData: any = null;
    let baseData: any = null;
    let lastData: any = null;
    if (firstArray && firstArray.length > 0) {
      for (const val of firstArray) {
        firstData = val;
        break;
      }
    }
    if (baseArray && baseArray.length > 0) {
      for (const val of baseArray) {
        if (!baseData) {
          baseData = JSON.parse(JSON.stringify(val));
        } else {
          baseData.frameNumberFrom = Number(baseData.frameNumberFrom) < Number(val.frameNumberFrom) ? baseData.frameNumberFrom : val.frameNumberFrom;
          baseData.frameNumberTo = Number(baseData.frameNumberTo) > Number(val.frameNumberTo) ? baseData.frameNumberTo : val.frameNumberTo;
        }
      }
    }
    if (lastArray && lastArray.length > 0) {
      for (const value of lastArray) {
        lastData = value;
      }
    }
    if (baseData && lastData && firstData) {
      const totalWidth: any = Number(lastData.frameNumberTo) - Number(firstData.frameNumberFrom);
      const groupWidth: any = Number(baseData.frameNumberTo) - Number(baseData.frameNumberFrom);
      return Number((groupWidth / totalWidth) * 100) + '%';
    }
  }


  /**
   * Method to get percentage filled in tank
   *
   * @param {ITank} tank
   * @returns
   * @memberof BallastLayoutComponent
   */
  getFillingPercentage(tank: ITank) {
    let fillingratio: any = ((tank?.commodity?.volume / Number(tank?.fullCapacityCubm)) * 100).toFixed(3);
    if (Number(fillingratio) >= 100) {
      fillingratio = 100;
    }
    if (isNaN(fillingratio)) {
      fillingratio = 0;
    }
    return ((fillingratio).toString() + '%');
  }


  /**
   * Method to set grid columm property for tank
   *
   * @param {*} currentGroup
   * @param {*} tank
   * @returns
   * @memberof BallastLayoutComponent
   */
  gridColumn(currentGroup: ITank[], tank: ITank) {
    const columns = [];
    currentGroup.forEach(element => {
      columns.push({
        frameNumberFrom: Number(element.frameNumberFrom),
        frameNumberTo: Number(element.frameNumberTo)
      });
    });
    const min = columns.reduce((prev, current) => (prev.frameNumberFrom < current.frameNumberFrom) ? prev : current).frameNumberFrom;
    const max = columns.reduce((prev, current) => (prev.frameNumberTo > current.frameNumberTo) ? prev : current).frameNumberFrom;
    const totalSpan = max.frameNumberTo - min.frameNumberFrom + 1;
    const currentTankStart = (Number(tank.frameNumberFrom) - min) + 1;
    const currentTankEnd = Number(tank.frameNumberTo) - min ? (Number(tank.frameNumberTo) - min) + 1 : totalSpan;
    return `${currentTankStart} / ${currentTankEnd}`;
  }

  /**
   * Method for tank selection event
   *
   * @param {*} $event
   * @param {*} tankId
   * @memberof BallastLayoutComponent
   */
  onTankSelect($event, tankId) {
    this.selectedTankId = tankId;
    this.selectedTankIdChange.emit(tankId);
  }

}
