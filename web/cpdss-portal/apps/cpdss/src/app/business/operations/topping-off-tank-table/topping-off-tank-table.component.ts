import { Component, Input, OnInit } from '@angular/core';
import { IDataTableColumn } from '../../../shared/components/datatable/datatable.model';
import { ICargo, IShipCargoTank } from '../../core/models/common.model';
import { IToppingOffSequence } from '../models/loading-information.model';
import { ToppingOffTankTableTransformationService } from './topping-off-tank-table-transformation.service';

/**
 * Component class for loading topping off tank component
 *
 * @export
 * @class ToppingOffTankTableComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-topping-off-tank-table',
  templateUrl: './topping-off-tank-table.component.html',
  styleUrls: ['./topping-off-tank-table.component.scss']
})
export class ToppingOffTankTableComponent implements OnInit {
  @Input() cargoTanks: IShipCargoTank[][];
  @Input() get cargos(): ICargo[] {
    return this._cargos;
  }

  set cargos(cargos: ICargo[]) {
    this._cargos = cargos;
    if (this.cargos) {
      this.initToppingOffSequence();
    }
  }
  @Input() get toppingOffSequence(): IToppingOffSequence[] {
    return this._toppingOffSequence;
  }

  set toppingOffSequence(toppingOffSequence: IToppingOffSequence[]) {
    this._toppingOffSequence = toppingOffSequence;
    if (this.toppingOffSequence) {
      this.initToppingOffSequence();
    }
  }


  private _toppingOffSequence: IToppingOffSequence[];
  private _cargos: ICargo[];

  cargoTypeColumns: IDataTableColumn[] = [];
  cargoTypeList: any = [];


  constructor(
    private toppingOffTankTableTransformationService: ToppingOffTankTableTransformationService) { }

  ngOnInit(): void {
    this.cargoTypeColumns = this.toppingOffTankTableTransformationService.getDatatableColumns();
    this.initToppingOffSequence();
  }

  /**
* Method to init Topping off sequence
*
* @memberof ToppingOffTankTableComponent
*/
  initToppingOffSequence() {
    if (this.toppingOffSequence && this.cargos) {
      this.toppingOffSequence?.map((topping) => {
        const cargo = this.findCargo(topping?.cargoId);
        const foundTank = this.findTank(topping?.tankId)
        topping.cargoName = cargo?.name;
        topping.cargoAbbreviation = cargo?.abbreviation;
        topping.shortName = foundTank?.shortName;
        topping.colourCode = foundTank?.commodity?.colorCode;
        return topping;
      })
      this.toppingOffSequence.sort((a, b) => (a.displayOrder > b.displayOrder) ? 1 : ((b.displayOrder > a.displayOrder) ? -1 : 0))
      this.initiToppingSequenceArray();
    }
  }

  /**
* Method to find out cargo
*
* @memberof ToppingOffTankTableComponent
*/
  findCargo(cargoId): ICargo {
    let cargoDetail;
    this.cargos?.map((cargo) => {
      if (cargo?.id === cargoId) {
        cargoDetail = cargo;
      }
    })
    return cargoDetail;
  }
  /**
* Method for init loading sequence array
*
* @memberof ToppingOffTankTableComponent
*/
  initiToppingSequenceArray() {

    this.cargoTypeList = Object.values( this.toppingOffSequence?.reduce((acc, obj) => {
      const key = obj['cargoId'];
      if (!acc[key]) {
        acc[key] = [];
      }
      // Add object to list for given key's value
      acc[key].push(obj);
      return acc;
    }, {}));

  }



  /**
*Method to find out tank
*
* @memberof ToppingOffTankTableComponent
*/

  findTank(tankId) {
    const tankGroup = this.cargoTanks?.find((groupTank) => {
      let found = groupTank?.find((tank) => tank?.id === tankId)
      return found;
    })
    const tank = tankGroup?.find((tank) => tank?.id === tankId)
    return tank;
  }


}
