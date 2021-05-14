import { Component, Input, OnInit } from '@angular/core';
import { DecimalPipe } from'@angular/common';
import { QUANTITY_UNIT } from '../../../shared/models/common.model';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { IDataTableColumn , DATATABLE_FIELD_TYPE } from '../../../shared/components/datatable/datatable.model';
import { IBunkerConditionParameterList, IBunkerConditions, ICargoConditions, ICargoQuantities } from '../models/voyage-status.model';
/**
 * Component class of ParameterListComponent
 */
@Component({
  selector: 'cpdss-portal-parameter-list',
  templateUrl: './parameter-list.component.html',
  styleUrls: ['./parameter-list.component.scss']
})
export class ParameterListComponent implements OnInit {
  @Input() bunkerConditions: IBunkerConditions;
  @Input() get cargoConditions(): ICargoConditions[] {
    return this._cargoConditions;
  }


  set cargoConditions(cargoConditions: ICargoConditions[]) {
    this._cargoConditions = cargoConditions;
    this.getParameterList();
  }

  @Input() get currentQuantitySelectedUnit(): QUANTITY_UNIT {
    return this._currentQuantitySelectedUnit;
  }

  set currentQuantitySelectedUnit(value: QUANTITY_UNIT) {
    this.prevQuantitySelectedUnit = this.currentQuantitySelectedUnit??AppConfigurationService.settings.baseUnit;
    this._currentQuantitySelectedUnit = value;
    this.getParameterList();    
  }
  
  readonly fieldType = DATATABLE_FIELD_TYPE;
  cols: IDataTableColumn[];
  parameterList: IBunkerConditionParameterList[] = [];
  totalActual = 0;
  private _currentQuantitySelectedUnit: QUANTITY_UNIT;
  private prevQuantitySelectedUnit: QUANTITY_UNIT;

  private _cargoConditions: ICargoConditions[];

  constructor(private _decimalPipe: DecimalPipe) { }
  /**
   * Component lifecycle ngOnit
   *
   * @memberof ParameterListComponent
   */
  ngOnInit(): void {
    this.cols = [
      { field: 'parameters', header: 'VOYAGE_STATUS_CARGO_PARAMETER_LIST' },
      { field: 'value', header: 'VOYAGE_STATUS_CARGO_VALUE' , fieldType: DATATABLE_FIELD_TYPE.NUMBER , numberFormat: 'numberFormat'}
    ];
  }

  /**
   * Get parameter details
   */
  getParameterList() {
    this.parameterList = [];
    this.totalActual = 0
    this.cargoConditions?.map(cargoList => {
      this.totalActual = cargoList.actualWeight + this.totalActual;

    })
    this._currentQuantitySelectedUnit;
    this.parameterList.push({ parameters: 'VOYAGE_STATUS_PARAMETER_LIST_TOTAL_CARGO_MT', value: this.totalActual, numberFormat: this._currentQuantitySelectedUnit === 'MT' ? AppConfigurationService.settings.quantityNumberFormatMT : (this._currentQuantitySelectedUnit === 'KL' ? AppConfigurationService.settings.quantityNumberFormatKL : AppConfigurationService.settings.quantityNumberFormatBBLS)})
    let oilWeight = 0;
    oilWeight = this.bunkerConditions?.fuelOilWeight + this.bunkerConditions?.dieselOilWeight;
    this.parameterList.push({ parameters: 'VOYAGE_STATUS_PARAMETER_LIST_FUEL_AND_DIESEL', value: oilWeight, numberFormat: this._currentQuantitySelectedUnit === 'MT' ? AppConfigurationService.settings.quantityNumberFormatMT : (this._currentQuantitySelectedUnit === 'KL' ? AppConfigurationService.settings.quantityNumberFormatKL : AppConfigurationService.settings.quantityNumberFormatBBLS) })
    for (const [key, value] of Object.entries(this.bunkerConditions)) {
      let newKey = null;
      let numberFormat = '';
      switch (key) {
        case "ballastWeight":
          newKey = "VOYAGE_STATUS_PARAMETER_LIST_BALLAST_WEIGHT";
          numberFormat = this._currentQuantitySelectedUnit === 'MT' ? AppConfigurationService.settings.quantityNumberFormatMT : (this._currentQuantitySelectedUnit === 'KL' ? AppConfigurationService.settings.quantityNumberFormatKL : AppConfigurationService.settings.quantityNumberFormatBBLS);
          break;
        case "displacement":
          newKey = "VOYAGE_STATUS_PARAMETER_LIST_DISPLACEMENT";
          numberFormat = this._currentQuantitySelectedUnit === 'MT' ? AppConfigurationService.settings.quantityNumberFormatMT : (this._currentQuantitySelectedUnit === 'KL' ? AppConfigurationService.settings.quantityNumberFormatKL : AppConfigurationService.settings.quantityNumberFormatBBLS);
          break;
        case "freshWaterWeight":
          newKey = "VOYAGE_STATUS_PARAMETER_LIST_FREASH_WATER_WEIGHT";
          numberFormat = this._currentQuantitySelectedUnit === 'MT' ? AppConfigurationService.settings.quantityNumberFormatMT : (this._currentQuantitySelectedUnit === 'KL' ? AppConfigurationService.settings.quantityNumberFormatKL : AppConfigurationService.settings.quantityNumberFormatBBLS);
          break;
        case "othersWeight":
          newKey = "VOYAGE_STATUS_PARAMETER_LIST_OTHERS_WEIGHT";
          numberFormat = this._currentQuantitySelectedUnit === 'MT' ? AppConfigurationService.settings.quantityNumberFormatMT : (this._currentQuantitySelectedUnit === 'KL' ? AppConfigurationService.settings.quantityNumberFormatKL : AppConfigurationService.settings.quantityNumberFormatBBLS);
          break;
        case "specificGravity":
          numberFormat =  '1.4-4';
          newKey = "VOYAGE_STATUS_PARAMETER_LIST_SPECIFIC_GRAVITY";
          break;
        case "totalDwtWeight":
          newKey = "VOYAGE_STATUS_PARAMETER_LIST_TOTAL_DWT_WEIGHT";
          numberFormat = this._currentQuantitySelectedUnit === 'MT' ? AppConfigurationService.settings.quantityNumberFormatMT : (this._currentQuantitySelectedUnit === 'KL' ? AppConfigurationService.settings.quantityNumberFormatKL : AppConfigurationService.settings.quantityNumberFormatBBLS);
          break;
        default:
          break;
      }
      if (newKey) {
        this.parameterList.push({ parameters: newKey, value: value , numberFormat: numberFormat})
      }
    }
  }

}
