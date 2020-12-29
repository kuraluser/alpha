import { Component, OnInit } from '@angular/core';

/**
 * Component class of loadable quantity component in loadable plan
 *
 * @export
 * @class LoadableQuantityComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-loadable-quantity',
  templateUrl: './loadable-quantity.component.html',
  styleUrls: ['./loadable-quantity.component.scss']
})
export class LoadableQuantityComponent implements OnInit {
  columns: any[];
  value: any[];
  constructor() { }

  ngOnInit(): void {
    this.columns = [
      { field: 'year', header: 'Grade' },
      {
        field: 'vin', header: 'Estimated', subColumns: [
          { field: 'year', header: 'API' },
          { field: 'year', header: 'TEMP' }
        ]
      },
      {
        field: 'year', header: 'ORDER', subColumns: [
          { field: 'year', header: 'BBLSSQBS>TEMP' },
          { field: 'year', header: 'BBLSQLF' }
        ]
      },
      {
        field: 'brand', header: 'TLRNC', subColumns: [
          { field: 'year', header: 'Min' },
          { field: 'year', header: 'Max' }
        ]
      },
      {
        field: 'color', header: 'LOADABLE', subColumns: [
          { field: 'year', header: 'BBLSSQBS' },
          { field: 'year', header: 'BBLSQLF' },
          { field: 'year', header: 'LT' },
          { field: 'year', header: 'MT' },
          { field: 'year', header: 'KL' }
        ]
      },
      {
        field: 'color', header: 'DIFF.', subColumns: [
          { field: 'year', header: '%' }
        ]
      }
    ];
    this.value = [
      {
        grade: 'KUWAIT EXPORT C.O',
        estimate: { api: '30.10', temp: 99.0 },
        order: { bblsTemp: '1000,000', bblsF: 950000 },
        tlrnic: { min: '-5%', max: '10%' },
        loadable: { bblsTemp: '1000,000', bblsF: 950000, lt: 50000, mt: 56000, kl: 59000 },
        diff: 2.66
      }

    ]
  }

}
