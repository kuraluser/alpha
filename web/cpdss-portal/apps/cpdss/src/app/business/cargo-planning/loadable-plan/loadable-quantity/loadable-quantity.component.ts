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
      { field: 'vin', header: 'Estimated', subColumns: [
        { field: 'year', header: 'API' },
        { field: 'year', header: 'TEMP' }
      ] },
      { field: 'year', header: 'ORDER' , subColumns: [
        { field: 'year', header: 'BBLSSQBS>TEMP' },
        { field: 'year', header: 'BBLSQLF' }
      ] },
      { field: 'brand', header: 'TLRNC' , subColumns: [
        { field: 'year', header: 'Min' },
        { field: 'year', header: 'Max' }
      ] },
      { field: 'color', header: 'LOADABLE' , subColumns: [
        { field: 'year', header: 'BBLSSQBS' },
        { field: 'year', header: 'BBLSQLF' },
        { field: 'year', header: 'LT' },
        { field: 'year', header: 'MT' },
        { field: 'year', header: 'KL' }
      ] },
      { field: 'color', header: 'DIFF.' , subColumns: [
        { field: 'year', header: '%' }
      ] }
  ];
  }

}
