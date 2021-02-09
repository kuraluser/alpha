import { Component, Input, OnInit } from '@angular/core';
import { IDataTableColumn } from '../../../shared/components/datatable/datatable.model';
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
  cols: IDataTableColumn[];
  parameterList: IBunkerConditionParameterList[] = [];
  totalActual = 0;

  private _cargoConditions: ICargoConditions[];

  constructor() { }
  /**
   * Component lifecycle ngOnit
   *
   * @memberof ParameterListComponent
   */
  ngOnInit(): void {
    this.cols = [
      { field: 'parameters', header: 'VOYAGE_STATUS_CARGO_PARAMETER_LIST' },
      { field: 'value', header: 'VOYAGE_STATUS_CARGO_VALUE' }
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
    this.parameterList.push({ parameters: 'VOYAGE_STATUS_PARAMETER_LIST_TOTAL_CARGO_MT', value: this.totalActual })
    let oilWeight = 0;
    oilWeight = this.bunkerConditions?.fuelOilWeight + this.bunkerConditions?.dieselOilWeight;
    this.parameterList.push({ parameters: 'VOYAGE_STATUS_PARAMETER_LIST_FUEL_AND_DIESEL', value: oilWeight })
    for (const [key, value] of Object.entries(this.bunkerConditions)) {
      let newKey = null;
      switch (key) {
        case "ballastWeight":
          newKey = "VOYAGE_STATUS_PARAMETER_LIST_BALLAST_WEIGHT";
          break;
        case "displacement":
          newKey = "VOYAGE_STATUS_PARAMETER_LIST_DISPLACEMENT";
          break;
        case "freshWaterWeight":
          newKey = "VOYAGE_STATUS_PARAMETER_LIST_FREASH_WATER_WEIGHT";
          break;
        case "othersWeight":
          newKey = "VOYAGE_STATUS_PARAMETER_LIST_OTHERS_WEIGHT";
          break;
        case "specificGravity":
          newKey = "VOYAGE_STATUS_PARAMETER_LIST_SPECIFIC_GRAVITY";
          break;
        case "totalDwtWeight":
          newKey = "VOYAGE_STATUS_PARAMETER_LIST_TOTAL_DWT_WEIGHT";
          break;
        default:
          break;
      }
      if (newKey) {
        this.parameterList.push({ parameters: newKey, value: value })
      }
    }
  }

}
