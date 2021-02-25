import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ICargoTank, ITank, ITankOptions } from '../../../core/models/common.model';



/**
 * Component for cargo tank layout
 *
 * @export
 * @class CargoTankLayoutComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-cargo-tank-layout',
  templateUrl: './cargo-tank-layout.component.html',
  styleUrls: ['./cargo-tank-layout.component.scss']
})
export class CargoTankLayoutComponent implements OnInit {

  @Input() options: ITankOptions = { 'isFullyFilled': false, 'showTooltip': false, 'isSelectable': true };

  @Input()
  get tanks(): ICargoTank[][] {
    return this._tanks;
  }

  set tanks(tanks: ICargoTank[][]) {
    this._tanks = tanks?.map(group => group.map(tank => {
      tank.gridColumn = this.gridColumn(group, tank);
      if (!this.options?.fillingPercentageField) {
        tank.percentageFilled = this.options?.isFullyFilled ? '100' : this.getFillingPercentage(tank);
      }

      return tank;
    }));
    this.selectedTankIdChange.emit(this.selectedTankId);
  }

  @Input() selectedTankId: number;

  @Output() selectedTankIdChange = new EventEmitter<number>();

  private _tanks: ICargoTank[][];
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
 * @memberof CargoTankLayoutComponent
 */
  getTankGroupWidthPercentage(firstArray: ICargoTank[], baseArray: ICargoTank[], lastArray: ICargoTank[]) {
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
   * @memberof CargoTankLayoutComponent
   */
  getFillingPercentage(tank: ITank) {
    let fillingratio: any = ((tank?.commodity?.volume / Number(tank?.fullCapacityCubm)) * 100).toFixed(3);
    if (Number(fillingratio) >= 100) {
      fillingratio = 100;
    }
    if (isNaN(fillingratio)) {
      fillingratio = 0;
    }
    return fillingratio;
  }


  /**
   * Method to set grid columm property for tank
   *
   * @param {*} currentGroup
   * @param {*} tank
   * @returns
   * @memberof CargoTankLayoutComponent
   */
  gridColumn(currentGroup: ICargoTank[], tank: ICargoTank) {
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
 * @memberof CargoTankLayoutComponent
 */
  onTankSelect($event, tankId) {
    this.selectedTankId = tankId;
    this.selectedTankIdChange.emit(tankId);
  }
}
