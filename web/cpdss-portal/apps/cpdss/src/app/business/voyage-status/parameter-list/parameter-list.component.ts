import { Component, Input, OnInit } from '@angular/core';
import { DecimalPipe } from '@angular/common';
import { ICargoConditions, QUANTITY_UNIT } from '../../../shared/models/common.model';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { IDataTableColumn, DATATABLE_FIELD_TYPE } from '../../../shared/components/datatable/datatable.model';
import { IBunkerConditionParameterList, IBunkerConditions } from '../models/voyage-status.model';
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
    this.prevQuantitySelectedUnit = this.currentQuantitySelectedUnit ?? AppConfigurationService.settings.baseUnit;
    this._currentQuantitySelectedUnit = value;
  }

  @Input() showPlannedValues = false;

  readonly fieldType = DATATABLE_FIELD_TYPE;
  cols: IDataTableColumn[];
  parameterList: IBunkerConditionParameterList[] = [];
  totalActual = 0;
  totalPlanned = 0;
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
      { field: 'value', header: 'VOYAGE_STATUS_CARGO_VALUE', fieldType: DATATABLE_FIELD_TYPE.NUMBER, numberFormat: 'numberFormat' }
    ];
  }

  /**
   * Get parameter details
   */
  getParameterList() {
    this.parameterList = [];
    this.totalActual = 0;
    this.totalPlanned = 0;
    this.cargoConditions?.map(cargoList => {
      this.totalActual = cargoList.actualWeight + this.totalActual;
      this.totalPlanned += Number(cargoList.plannedWeight);
    });
    this.parameterList.push({ parameters: 'VOYAGE_STATUS_PARAMETER_LIST_TOTAL_CARGO_MT', value: this.showPlannedValues ? this.totalPlanned : this.totalActual, numberFormat: AppConfigurationService.settings.quantityNumberFormatMT })
    let oilWeight = 0;
    oilWeight = (this.showPlannedValues ? this.bunkerConditions['fuelOilPlannedWeight'] : this.bunkerConditions['fuelOilActualWeight']) + (this.showPlannedValues ? this.bunkerConditions['dieselOilPlannedWeight'] : this.bunkerConditions['dieselOilActualWeight']);
    this.parameterList.push({ parameters: 'VOYAGE_STATUS_PARAMETER_LIST_FUEL_AND_DIESEL', value: oilWeight, numberFormat: AppConfigurationService.settings.quantityNumberFormatMT })

    for (let [key, value] of Object.entries(this.bunkerConditions)) {
      let newKey = null;
      let numberFormat = '';
      switch (key) {
        case "ballastActualWeight":
          newKey = "VOYAGE_STATUS_PARAMETER_LIST_BALLAST_WEIGHT";
          numberFormat = AppConfigurationService.settings.quantityNumberFormatMT;
          value = this.showPlannedValues ? this.bunkerConditions['ballastPlannedWeight'] : value;
          break;
        case "displacementActual":
          newKey = "VOYAGE_STATUS_PARAMETER_LIST_DISPLACEMENT";
          numberFormat = AppConfigurationService.settings.quantityNumberFormatMT;
          value = this.showPlannedValues ? this.bunkerConditions['displacementPlanned'] : value;
          break;
        case "freshWaterActualWeight":
          newKey = "VOYAGE_STATUS_PARAMETER_LIST_FW_DW_BW";
          numberFormat = AppConfigurationService.settings.quantityNumberFormatMT;
          value = this.showPlannedValues ? this.bunkerConditions['freshWaterPlannedWeight'] : value;
          break;
        case "othersActualWeight":
          newKey = "VOYAGE_STATUS_PARAMETER_LIST_OTHERS_WEIGHT";
          numberFormat = AppConfigurationService.settings.quantityNumberFormatMT;
          value = this.showPlannedValues ? this.bunkerConditions['othersPlannedWeight'] : value;
          break;
        case "specificGravity":
          numberFormat = AppConfigurationService.settings?.sgNumberFormat;
          newKey = "VOYAGE_STATUS_PARAMETER_LIST_SPECIFIC_GRAVITY";
          break;
        case "constantActual":
          newKey = "VOYAGE_STATUS_PARAMETER_LIST_CONSTANT";
          numberFormat = AppConfigurationService.settings.quantityNumberFormatMT
          value = this.showPlannedValues ? this.bunkerConditions['constantPlanned'] : value;
          break;
        case "totalDwtWeightActual":
          newKey = "VOYAGE_STATUS_PARAMETER_LIST_TOTAL_DWT_WEIGHT";
          numberFormat = AppConfigurationService.settings.quantityNumberFormatMT
          value = this.showPlannedValues ? this.bunkerConditions['totalDwtWeightPlanned'] : value;
          break;
        default:
          break;
      }
      if (newKey) {
        this.parameterList.push({ parameters: newKey, value: value, numberFormat: numberFormat })
      }
      const totDwtInitIndex = this.parameterList.findIndex(item => item.parameters === 'VOYAGE_STATUS_PARAMETER_LIST_TOTAL_DWT_WEIGHT');
      const totDwtTemp = this.parameterList.splice(totDwtInitIndex, 1);
      this.parameterList.splice(this.parameterList.length - 1, 0, totDwtTemp[0]);

      const displacementIndex = this.parameterList.findIndex(item => item.parameters === 'VOYAGE_STATUS_PARAMETER_LIST_DISPLACEMENT');
      const displacemenTemp = this.parameterList.splice(displacementIndex, 1);
      this.parameterList.splice(this.parameterList.length, 0, displacemenTemp[0]);
    }
  }

}
