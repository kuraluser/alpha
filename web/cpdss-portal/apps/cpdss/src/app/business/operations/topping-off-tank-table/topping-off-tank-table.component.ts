import { Component, Input, OnInit } from '@angular/core';
import { IDataTableColumn } from '../../../shared/components/datatable/datatable.model';
import { ICargo, IShipCargoTank } from '../../core/models/common.model';
import { IToppingOffSequence } from '../models/loading-discharging.model';
import { ToppingOffTankTableTransformationService } from './topping-off-tank-table-transformation.service';
import { QUANTITY_UNIT, ICargoConditions } from '../../../shared/models/common.model';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { QuantityPipe } from '../../../shared/pipes/quantity/quantity.pipe';

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
  @Input() loadingDischargingPLanData: any;
  @Input() get currentQuantitySelectedUnit(): QUANTITY_UNIT {
    return this._currentQuantitySelectedUnit;
  }


  set currentQuantitySelectedUnit(value: QUANTITY_UNIT) {
    this.prevQuantitySelectedUnit = this.currentQuantitySelectedUnit ?? AppConfigurationService.settings.baseUnit;
    this._currentQuantitySelectedUnit = value;
    this.initToppingOffSequence();
  }
  private _currentQuantitySelectedUnit: QUANTITY_UNIT;
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

  @Input() loadingPlanData: any;

  prevQuantitySelectedUnit: QUANTITY_UNIT;
  private _toppingOffSequence: IToppingOffSequence[];
  private _cargos: ICargo[];
  private cargoSequenceOrder;

  cargoTypeColumns: IDataTableColumn[] = [];
  cargoTypeList: any = [];


  constructor(
    private quantityPipe: QuantityPipe,
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
    this.cargoSequenceOrder = 0;

    if (this.toppingOffSequence && this.cargos) {
      this.toppingOffSequence?.map((topping) => {
        const cargo = this.findCargo(topping?.cargoId);
        const foundTank = this.findTank(topping?.tankId)
        topping.cargoName = cargo?.name;
        topping.shortName = foundTank?.shortName;
        topping.colourCode = foundTank?.commodity?.colorCode ? foundTank?.commodity?.colorCode : this.getColourCode(topping);
        return topping;
      })
      this.toppingOffSequence.sort((a, b) => (a.displayOrder > b.displayOrder) ? 1 : ((b.displayOrder > a.displayOrder) ? -1 : 0))
      this.initiToppingSequenceArray();
    }
  }

  /**
* Method to getting colour code
*
* @memberof ToppingOffTankTableComponent
*/
  getColourCode(data) {
    let colourCode = '';
    this.loadingPlanData?.currentPortCargos?.map(item => {
      if (item.cargoNominationId === data.cargoNominationId) {
        colourCode = item.colorCode;
      }
    });
    return colourCode;
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
    const cargoQuantity = [];
    this.loadingDischargingPLanData?.planStowageDetails?.map(item => {
      if (item.conditionType === 2 && item.valueType === 2) {
        cargoQuantity.push(item);
      }
    });
    const cargoTanks = this.toppingOffTankTableTransformationService.formatCargoTanks(this.cargoTanks, cargoQuantity, this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit);
    this.cargoTypeList = Object.values(this.toppingOffSequence?.reduce((acc, obj) => {
      cargoTanks?.map(item => {
        item?.map(tank => {
          if (tank.id === obj.tankId) {
            obj.fillingRatio = Number(tank.commodity?.percentageFilled);
          }
        });
      });
      this.loadingDischargingPLanData?.planStowageDetails?.map(item => {
        if (item.conditionType === 2 && item.valueType === 2 && item.tankId === obj.tankId) {
          obj.ullage = item?.ullage && !isNaN(Number(item?.ullage)) ? Number(Number(item?.ullage).toFixed(3)) : 0;
          obj.quantity = this.quantityPipe.transform(item?.quantityMT, QUANTITY_UNIT.MT , this.currentQuantitySelectedUnit, obj.api, obj.temperature, -1);
        }
      });
      const key = obj['cargoId'];
      if (!acc[key]) {
        acc[key] = [];
      }
      // Add object to list for given key's value
      acc[key].push(obj);
      return acc;
    }, {}));
    this.sortCargoTypeList();
  }

  /**
  * Method for sorting array based on sequence number
  *
  * @memberof ToppingOffTankTableComponent
  */
  sortCargoTypeList() {
    this.cargoTypeList?.map(item => {
      item.sort((a, b) => a.displayOrder - b.displayOrder);
    });
    this.cargoTypeList?.sort((a, b) => a[0].displayOrder - b[0].displayOrder);
    this.cargoTypeList?.forEach((element) => {
      element?.forEach((element,index) => {       
        element.displayOrder =  ++this.cargoSequenceOrder      
      });
    });
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
