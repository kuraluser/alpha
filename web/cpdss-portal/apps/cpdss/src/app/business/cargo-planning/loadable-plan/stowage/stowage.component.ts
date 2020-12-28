import { Component, OnInit } from '@angular/core';

/**
 * Component class of stowage
 *
 * @export
 * @class StowageComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-stowage',
  templateUrl: './stowage.component.html',
  styleUrls: ['./stowage.component.scss']
})
export class StowageComponent implements OnInit {
  selectedTab = 'Cargo'
  tanks = [
    [
      {
        "id": 25595,
        "categoryId": 1,
        "categoryName": "Cargo Tank",
        "name": "SLOP TANK",
        "frameNumberFrom": "49",
        "frameNumberTo": "52",
        "shortName": "SLP",
        "fillCapcityCubm": 4117,
        "fullCapacityCubm": "4117.3000",
        "density": 1.3,
        "group": 1,
        "order": 1,
        "slopTank": false,
        "commodity": {
          "priority": 2,
          "cargoAbbreviation": "KEN",
          "cargoColor": "#FFFFFF",
          "tankId": 25595,
          "quantity": "1000.0000",
          "difference": "-20.00",
          "differenceColor": "#FFFFFF",
          "loadablePatternDetailsId": 3,
          "isCommingle": true,
          "loadablePatternCommingleDetailsId": 1
        },
        "gridColumn": "1 / 4",
        "percentageFilled": "100%"
      },
      {
        "id": 25593,
        "categoryId": 1,
        "categoryName": "Cargo Tank",
        "name": "NO.5 WING CARGO OIL TANK",
        "frameNumberFrom": "52",
        "frameNumberTo": "61",
        "shortName": "5P",
        "fillCapcityCubm": 17277,
        "fullCapacityCubm": "17277.4000",
        "density": 1.3,
        "group": 1,
        "order": 2,
        "slopTank": false,
        "commodity": {
          "priority": 1,
          "cargoAbbreviation": "FNP",
          "cargoColor": "#FFFFFF",
          "tankId": 25593,
          "quantity": "1000.0000",
          "difference": "-65.00",
          "differenceColor": "#FFFFFF",
          "loadablePatternDetailsId": 4,
          "isCommingle": false,
          "loadablePatternCommingleDetailsId": 0
        },
        "gridColumn": "4 / 13",
        "percentageFilled": "100%"
      },
      {
        "id": 25584,
        "categoryId": 1,
        "categoryName": "Cargo Tank",
        "name": "NO.5 CENTER CARGO OIL TANK",
        "frameNumberFrom": "49",
        "frameNumberTo": "61",
        "shortName": "5C",
        "fillCapcityCubm": 20798,
        "fullCapacityCubm": "20797.7000",
        "density": 1.3,
        "group": 1,
        "order": 3,
        "slopTank": false,
        "gridColumn": "1 / 13",
        "percentageFilled": "100%"
      },
      {
        "id": 25596,
        "categoryId": 1,
        "categoryName": "Cargo Tank",
        "name": "SLOP TANK",
        "frameNumberFrom": "49",
        "frameNumberTo": "52",
        "shortName": "SLS",
        "fillCapcityCubm": 4117,
        "fullCapacityCubm": "4117.3000",
        "density": 1.3,
        "group": 1,
        "order": 4,
        "slopTank": false,
        "gridColumn": "1 / 4",
        "percentageFilled": "100%"
      },
      {
        "id": 25594,
        "categoryId": 1,
        "categoryName": "Cargo Tank",
        "name": "NO.5 WING CARGO OIL TANK",
        "frameNumberFrom": "52",
        "frameNumberTo": "61",
        "shortName": "5S",
        "fillCapcityCubm": 17277,
        "fullCapacityCubm": "17277.4000",
        "density": 1.3,
        "group": 1,
        "order": 5,
        "slopTank": false,
        "gridColumn": "4 / 13",
        "percentageFilled": "100%"
      }
    ],
    [
      {
        "id": 25591,
        "categoryId": 1,
        "categoryName": "Cargo Tank",
        "name": "NO.4 WING CARGO OIL TANK",
        "frameNumberFrom": "61",
        "frameNumberTo": "71",
        "shortName": "4P",
        "fillCapcityCubm": 20281,
        "fullCapacityCubm": "20280.8000",
        "density": 1.3,
        "group": 2,
        "order": 1,
        "slopTank": false,
        "gridColumn": "1 / 11",
        "percentageFilled": "100%"
      },
      {
        "id": 25583,
        "categoryId": 1,
        "categoryName": "Cargo Tank",
        "name": "NO.4 CENTER CARGO OIL TANK",
        "frameNumberFrom": "61",
        "frameNumberTo": "71",
        "shortName": "4C",
        "fillCapcityCubm": 33725,
        "fullCapacityCubm": "33725.1000",
        "density": 1.3,
        "group": 2,
        "order": 2,
        "slopTank": false,
        "gridColumn": "1 / 11",
        "percentageFilled": "100%"
      },
      {
        "id": 25592,
        "categoryId": 1,
        "categoryName": "Cargo Tank",
        "name": "NO.4 WING CARGO OIL TANK",
        "frameNumberFrom": "61",
        "frameNumberTo": "71",
        "shortName": "4S",
        "fillCapcityCubm": 20281,
        "fullCapacityCubm": "20280.8000",
        "density": 1.3,
        "group": 2,
        "order": 3,
        "slopTank": false,
        "gridColumn": "1 / 11",
        "percentageFilled": "100%"
      }
    ],
    [
      {
        "id": 25589,
        "categoryId": 1,
        "categoryName": "Cargo Tank",
        "name": "NO.3 WING CARGO OIL TANK",
        "frameNumberFrom": "71",
        "frameNumberTo": "81",
        "shortName": "3P",
        "fillCapcityCubm": 20281,
        "fullCapacityCubm": "20280.8000",
        "density": 1.3,
        "group": 3,
        "order": 1,
        "slopTank": false,
        "gridColumn": "1 / 11",
        "percentageFilled": "100%"
      },
      {
        "id": 25582,
        "categoryId": 1,
        "categoryName": "Cargo Tank",
        "name": "NO.3 CENTER CARGO OIL TANK",
        "frameNumberFrom": "71",
        "frameNumberTo": "81",
        "shortName": "3C",
        "fillCapcityCubm": 28202,
        "fullCapacityCubm": "28201.6000",
        "density": 1.3,
        "group": 3,
        "order": 2,
        "slopTank": false,
        "gridColumn": "1 / 11",
        "percentageFilled": "100%"
      },
      {
        "id": 25590,
        "categoryId": 1,
        "categoryName": "Cargo Tank",
        "name": "NO.3 WING CARGO OIL TANK",
        "frameNumberFrom": "71",
        "frameNumberTo": "81",
        "shortName": "3S",
        "fillCapcityCubm": 20281,
        "fullCapacityCubm": "20280.8000",
        "density": 1.3,
        "group": 3,
        "order": 3,
        "slopTank": false,
        "gridColumn": "1 / 11",
        "percentageFilled": "100%"
      }
    ],
    [
      {
        "id": 25587,
        "categoryId": 1,
        "categoryName": "Cargo Tank",
        "name": "NO.2 WING CARGO OIL TANK",
        "frameNumberFrom": "81",
        "frameNumberTo": "91",
        "shortName": "2P",
        "fillCapcityCubm": 20281,
        "fullCapacityCubm": "20280.8000",
        "density": 1.3,
        "group": 4,
        "order": 1,
        "slopTank": false,
        "gridColumn": "1 / 11",
        "percentageFilled": "100%"
      },
      {
        "id": 25581,
        "categoryId": 1,
        "categoryName": "Cargo Tank",
        "name": "NO.2 CENTER CARGO OIL TANK",
        "frameNumberFrom": "81",
        "frameNumberTo": "91",
        "shortName": "2C",
        "fillCapcityCubm": 28202,
        "fullCapacityCubm": "28201.6000",
        "density": 1.3,
        "group": 4,
        "order": 2,
        "slopTank": false,
        "gridColumn": "1 / 11",
        "percentageFilled": "100%"
      },
      {
        "id": 25588,
        "categoryId": 1,
        "categoryName": "Cargo Tank",
        "name": "NO.2 WING CARGO OIL TANK",
        "frameNumberFrom": "81",
        "frameNumberTo": "91",
        "shortName": "2S",
        "fillCapcityCubm": 20281,
        "fullCapacityCubm": "20280.8000",
        "density": 1.3,
        "group": 4,
        "order": 3,
        "slopTank": false,
        "gridColumn": "1 / 11",
        "percentageFilled": "100%"
      }
    ],
    [
      {
        "id": 25585,
        "categoryId": 1,
        "categoryName": "Cargo Tank",
        "name": "NO.1  WING CARGO OIL TANK",
        "frameNumberFrom": "91",
        "frameNumberTo": "103",
        "shortName": "1P",
        "fillCapcityCubm": 20798,
        "fullCapacityCubm": "20797.7000",
        "density": 1.3,
        "group": 5,
        "order": 1,
        "slopTank": false,
        "gridColumn": "1 / 13",
        "percentageFilled": "100%"
      },
      {
        "id": 25580,
        "categoryId": 1,
        "categoryName": "Cargo Tank",
        "name": "NO.1 CENTER CARGO OIL TANK",
        "frameNumberFrom": "91",
        "frameNumberTo": "103",
        "shortName": "1C",
        "fillCapcityCubm": 30230,
        "fullCapacityCubm": "30229.5000",
        "density": 1.3,
        "group": 5,
        "order": 2,
        "slopTank": false,
        "gridColumn": "1 / 13",
        "percentageFilled": "100%"
      },
      {
        "id": 25586,
        "categoryId": 1,
        "categoryName": "Cargo Tank",
        "name": "NO.1  WING CARGO OIL TANK",
        "frameNumberFrom": "91",
        "frameNumberTo": "103",
        "shortName": "1S",
        "fillCapcityCubm": 20281,
        "fullCapacityCubm": "20280.8000",
        "density": 1.3,
        "group": 5,
        "order": 3,
        "slopTank": false,
        "gridColumn": "1 / 13",
        "percentageFilled": "100%"
      }
    ]
  ];
  isViewMore = false;
  columns: any[];
  value: any[];
  MORE_HIDE_BUTTON_LABEL = "MORE";
  constructor() { }

  ngOnInit(): void {
    this.columns = [
      { field: 'year', header: 'GRADE' },
      { field: 'year', header: 'ABBREV' },
      { field: 'year', header: 'RDG ULG' },
      { field: 'year', header: 'OBS.M3' },
      { field: 'year', header: 'OBS.BBLS' },
      { field: 'year', header: 'M/T' },
      { field: 'year', header: '%' },
      { field: 'year', header: 'API' },
      { field: 'year', header: 'DEG.F' }
    ];
  }

  onTabClick(selectedTab: string) {
    this.selectedTab = selectedTab;
  }
  more() {
    this.isViewMore = !this.isViewMore;
    this.MORE_HIDE_BUTTON_LABEL = this.isViewMore ? "HIDE" : "MORE";
  }
}
