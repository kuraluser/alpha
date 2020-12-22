import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ITank } from '../../models/common.model';

/**
 * Class for bunkering layout component
 *
 * @export
 * @class BunkeringLayoutComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-bunkering-layout',
  templateUrl: './bunkering-layout.component.html',
  styleUrls: ['./bunkering-layout.component.scss']
})
export class BunkeringLayoutComponent implements OnInit {

  @Input()
  get tanks(): ITank[][] {
    return this._tanks;
  }

  set tanks(tanks: ITank[][]) {
    this._tanks = tanks.map(group => group.map(tank => {
      tank.gridColumn = this.gridColumn(group, tank);
      tank.percentageFilled = this.getFillingPercentage(tank);
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

  @Input() selectedTankId: number;

  @Output() selectedTankIdChange = new EventEmitter<number>();

  private _tanks: ITank[][];
  private _rearTanks: ITank[][];

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
   * @memberof BunkeringLayoutComponent
   */
  getTankGroupWidthPercentage(firstGroup: ITank[], currentGroup: ITank[], lastGroup: ITank[]) {
    let firstTankData: any = null;
    let currentGroupData: any = null;
    let lastGroupData: any = null;
    if (firstGroup && firstGroup.length > 0) {
      for (const val of firstGroup) {
        firstTankData = val;
        break;
      }
    }
    if (currentGroup && currentGroup.length > 0) {
      for (const val of currentGroup) {
        if (!currentGroupData) {
          currentGroupData = JSON.parse(JSON.stringify(val));
        } else {
          currentGroupData.frameNumberFrom = Number(currentGroupData.frameNumberFrom) < Number(val.frameNumberFrom) ? currentGroupData.frameNumberFrom : val.frameNumberFrom;
          currentGroupData.frameNumberTo = Number(currentGroupData.frameNumberTo) > Number(val.frameNumberTo) ? currentGroupData.frameNumberTo : val.frameNumberTo;
        }
      }
    }
    if (lastGroup && lastGroup.length > 0) {
      for (const value of lastGroup) {
        lastGroupData = value;
      }
    }
    if (currentGroupData && lastGroupData && firstTankData) {
      const totalWidth: any = Number(lastGroupData.frameNumberTo) - Number(firstTankData.frameNumberFrom);
      const groupWidth: any = Number(currentGroupData.frameNumberTo) - Number(currentGroupData.frameNumberFrom);
      return Number((groupWidth / totalWidth) * 100) + '%';
    }
  }

  /**
   * Method to set grid columm property for tank
   *
   * @param {*} currentGroup
   * @param {*} tank
   * @returns
   * @memberof BunkeringLayoutComponent
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
   * Method to get percentage filled in tank
   *
   * @param {ITank} tank
   * @returns
   * @memberof BunkeringLayoutComponent
   */
  getFillingPercentage(tank: ITank) {
    let fillingratio: any = ((tank?.commodity?.volume / Number(tank?.fullCapacityCubm)) * 100).toFixed(2);
    if (Number(fillingratio) >= 100) {
      fillingratio = 100;
    }
    if (isNaN(fillingratio) || !fillingratio) {
      fillingratio = 0;
    }
    return ((fillingratio).toString() + '%');
  }

  /**
   * Method for tank selection event
   *
   * @param {*} $event
   * @param {*} tankId
   * @memberof BunkeringLayoutComponent
   */
  onTankSelect($event, tankId) {
    this.selectedTankId = tankId;
    this.selectedTankIdChange.emit(tankId);
  }

}
