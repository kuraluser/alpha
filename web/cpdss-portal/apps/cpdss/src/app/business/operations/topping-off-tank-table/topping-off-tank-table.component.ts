import { Component, OnInit } from '@angular/core';

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

  cargoTypeColumns: any = [];
  cargoTypeList: any = [];
  constructor() { }

  ngOnInit(): void {
    this.cargoTypeColumns = [
      { 
        field: 'sequence',
        header: 'SEQUENCE',
      },
      { 
        field: 'tank',
        header: 'TANK',
      },
      { 
        field: 'type',
        header: 'TYPE',
      },
      { 
        field: 'ullage',
        header: 'ULLAGE',
      },
      { 
        field: 'volume',
        header: 'VOLUME',
      },
      { 
        field: 'weight',
        header: 'WEIGHT',
      },
      { 
        field: 'fillingPer',
        header: 'FILLING %',
      },
      { 
        field: 'remarks',
        header: 'REMARKS',
      },
    ];

    this.cargoTypeList = [
      {
        sequence: '1ST',
        tank: '1P',
        type: 'KU',
        ullage: null,
        volume: null,
        weight: null,
        fillingPer: null,
        remarks: ''
      },
      {
        sequence: '2ND',
        tank: '1S',
        type: 'KU',
        ullage: null,
        volume: null,
        weight: null,
        fillingPer: null,
        remarks: ''
      },
      {
        sequence: '3RD',
        tank: '2P',
        type: 'KU',
        ullage: null,
        volume: null,
        weight: null,
        fillingPer: null,
        remarks: ''
      },
      {
        sequence: '4TH',
        tank: '2S',
        type: 'KU',
        ullage: null,
        volume: null,
        weight: null,
        fillingPer: null,
        remarks: ''
      }
    ];
  }

}
