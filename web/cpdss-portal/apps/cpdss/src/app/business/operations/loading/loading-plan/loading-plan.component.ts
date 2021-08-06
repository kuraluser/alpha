import { Component, Input, OnInit } from '@angular/core';
import { ICargo, ILoadableQuantityCargo, IShipCargoTank } from '../../../core/models/common.model';
import { ILoadingDischargingSequences, IToppingOffSequence } from '../../models/loading-discharging.model';

@Component({
  selector: 'cpdss-portal-loading-plan',
  templateUrl: './loading-plan.component.html',
  styleUrls: ['./loading-plan.component.scss']
})

/**
 * Component class for loading plan component
 *
 * @export
 * @class LoadingPlanComponent
 * @implements {OnInit}
 */
export class LoadingPlanComponent implements OnInit {
  @Input() get cargos(): ICargo[] {
    return this._cargos;
  }

  set cargos(cargos: ICargo[]) {
    this._cargos = cargos;
  }

  private _cargos: ICargo[];
  toppingOffSequence: IToppingOffSequence[];
  cargoTanks: IShipCargoTank[][];
  loadableQuantityCargoDetails: ILoadableQuantityCargo[];
  loadingSequences: ILoadingDischargingSequences;
  constructor() { }

  ngOnInit(): void {
    //TODO :  Remove static data after api integration
    this.cargoTanks = [[{ "id": 25595, "categoryId": 1, "categoryName": "Cargo Tank", "name": "SLOP TANK", "frameNumberFrom": 49, "frameNumberTo": 52, "shortName": "SLP", "fullCapacityCubm": "4117.3000", "density": 1.3000, "group": 1, "order": 1, "slopTank": false }, { "id": 25593, "categoryId": 1, "categoryName": "Cargo Tank", "name": "NO.5 WING CARGO OIL TANK", "frameNumberFrom": 52, "frameNumberTo": 61, "shortName": "5P", "fullCapacityCubm": "17277.4000", "density": 1.3000, "group": 1, "order": 2, "slopTank": false }, { "id": 25584, "categoryId": 1, "categoryName": "Cargo Tank", "name": "NO.5 CENTER CARGO OIL TANK", "frameNumberFrom": 49, "frameNumberTo": 61, "shortName": "5C", "fullCapacityCubm": "33725.1000", "density": 1.3000, "group": 1, "order": 3, "slopTank": false }, { "id": 25596, "categoryId": 1, "categoryName": "Cargo Tank", "name": "SLOP TANK", "frameNumberFrom": 49, "frameNumberTo": 52, "shortName": "SLS", "fullCapacityCubm": "4117.3000", "density": 1.3000, "group": 1, "order": 4, "slopTank": false }, { "id": 25594, "categoryId": 1, "categoryName": "Cargo Tank", "name": "NO.5 WING CARGO OIL TANK", "frameNumberFrom": 52, "frameNumberTo": 61, "shortName": "5S", "fullCapacityCubm": "17277.4000", "density": 1.3000, "group": 1, "order": 5, "slopTank": false }], [{ "id": 25591, "categoryId": 1, "categoryName": "Cargo Tank", "name": "NO.4 WING CARGO OIL TANK", "frameNumberFrom": 61, "frameNumberTo": 71, "shortName": "4P", "fullCapacityCubm": "20290.8000", "density": 1.3000, "group": 2, "order": 1, "slopTank": false }, { "id": 25583, "categoryId": 1, "categoryName": "Cargo Tank", "name": "NO.4 CENTER CARGO OIL TANK", "frameNumberFrom": 61, "frameNumberTo": 71, "shortName": "4C", "fullCapacityCubm": "28201.6000", "density": 1.3000, "group": 2, "order": 2, "slopTank": false }, { "id": 25592, "categoryId": 1, "categoryName": "Cargo Tank", "name": "NO.4 WING CARGO OIL TANK", "frameNumberFrom": 61, "frameNumberTo": 71, "shortName": "4S", "fullCapacityCubm": "20290.8000", "density": 1.3000, "group": 2, "order": 3, "slopTank": false }], [{ "id": 25589, "categoryId": 1, "categoryName": "Cargo Tank", "name": "NO.3 WING CARGO OIL TANK", "frameNumberFrom": 71, "frameNumberTo": 81, "shortName": "3P", "fullCapacityCubm": "20290.8000", "density": 1.3000, "group": 3, "order": 1, "slopTank": false }, { "id": 25582, "categoryId": 1, "categoryName": "Cargo Tank", "name": "NO.3 CENTER CARGO OIL TANK", "frameNumberFrom": 71, "frameNumberTo": 81, "shortName": "3C", "fullCapacityCubm": "28201.6000", "density": 1.3000, "group": 3, "order": 2, "slopTank": false }, { "id": 25590, "categoryId": 1, "categoryName": "Cargo Tank", "name": "NO.3 WING CARGO OIL TANK", "frameNumberFrom": 71, "frameNumberTo": 81, "shortName": "3S", "fullCapacityCubm": "20290.8000", "density": 1.3000, "group": 3, "order": 3, "slopTank": false }], [{ "id": 25587, "categoryId": 1, "categoryName": "Cargo Tank", "name": "NO.2 WING CARGO OIL TANK", "frameNumberFrom": 81, "frameNumberTo": 91, "shortName": "2P", "fullCapacityCubm": "20290.8000", "density": 1.3000, "group": 4, "order": 1, "slopTank": false }, { "id": 25581, "categoryId": 1, "categoryName": "Cargo Tank", "name": "NO.2 CENTER CARGO OIL TANK", "frameNumberFrom": 81, "frameNumberTo": 91, "shortName": "2C", "fullCapacityCubm": "28201.6000", "density": 1.3000, "group": 4, "order": 2, "slopTank": false }, { "id": 25588, "categoryId": 1, "categoryName": "Cargo Tank", "name": "NO.2 WING CARGO OIL TANK", "frameNumberFrom": 81, "frameNumberTo": 91, "shortName": "2S", "fullCapacityCubm": "20290.8000", "density": 1.3000, "group": 4, "order": 3, "slopTank": false }], [{ "id": 25585, "categoryId": 1, "categoryName": "Cargo Tank", "name": "NO.1  WING CARGO OIL TANK", "frameNumberFrom": 91, "frameNumberTo": 103, "shortName": "1P", "fullCapacityCubm": "20797.7000", "density": 1.3000, "group": 5, "order": 1, "slopTank": false }, { "id": 25580, "categoryId": 1, "categoryName": "Cargo Tank", "name": "NO.1 CENTER CARGO OIL TANK", "frameNumberFrom": 91, "frameNumberTo": 103, "shortName": "1C", "fullCapacityCubm": "30229.5000", "density": 1.3000, "group": 5, "order": 2, "slopTank": false }, { "id": 25586, "categoryId": 1, "categoryName": "Cargo Tank", "name": "NO.1  WING CARGO OIL TANK", "frameNumberFrom": 91, "frameNumberTo": 103, "shortName": "1S", "fullCapacityCubm": "20797.7000", "density": 1.3000, "group": 5, "order": 3, "slopTank": false }]]
    this.toppingOffSequence = [
      {
        "id": 621,
        "loadingInfoId": 119,
        "orderNumber": 1,
        "tankId": 25596,
        "cargoId": 252,
        "shortName": "SLS",
        "cargoName": "Murban",
        "cargoAbbreviation": "MBN",
        "colourCode": "#c90808",
        "remark": "",
        "ullage": 1.3481,
        "quantity": 3234.4,
        "fillingRatio": 98,
        "api": 40.16,
        "temperature": 109.9,
        "displayOrder": 1
      },
      {
        "id": 622,
        "loadingInfoId": 119,
        "orderNumber": 8,
        "tankId": 25581,
        "cargoId": 252,
        "shortName": "2C",
        "cargoName": "Murban",
        "cargoAbbreviation": "MBN",
        "colourCode": "#c90808",
        "remark": "",
        "ullage": 1.2438,
        "quantity": 22154.2,
        "fillingRatio": 98,
        "api": 40.16,
        "temperature": 109.9,
        "displayOrder": 2
      }
    ]
    this.loadableQuantityCargoDetails = [
      {
        "id": 9147,
        "grade": "",
        "estimatedAPI": "40.1600",
        "estimatedTemp": "109.9000",
        "orderBblsdbs": "",
        "orderBbls60f": "",
        "minTolerence": "-10.0",
        "maxTolerence": "10.0",
        "loadableBblsdbs": "",
        "loadableBbls60f": "",
        "loadableLT": "",
        "loadableMT": "275000.0",
        "loadableKL": "",
        "differencePercentage": "10.0",
        "differenceColor": "",
        "cargoId": 252,
        "cargoAbbreviation": "MBN",
        "cargoNominationId": 17163,
        "slopQuantity": 6355.200000000001,
        "timeRequiredForLoading": "",
        "orderedQuantity": "250000.1000",
        "maxLoadingRate": "20500.0000"
      }
    ]
    this.loadingSequences = {
      "reasonForDelays": [
        {
          "id": 1,
          "reason": "test"
        }
      ],
      "loadingDischargingDelays": [
        {
          "id": 94,
          "loadingInfoId": 119,
          "reasonForDelayIds": [1],
          "duration": 45.0000,
          "cargoId": 0,
          "quantity": null
        }
      ]
    }
  }

}
