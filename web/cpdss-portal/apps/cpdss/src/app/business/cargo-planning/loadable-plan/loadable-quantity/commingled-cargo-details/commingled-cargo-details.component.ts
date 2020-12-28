import { Component, OnInit } from '@angular/core';

/**
 * Component class of commingle cargo details component in loadable plan
 *
 * @export
 * @class CommingledCargoDetailsComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-commingled-cargo-details',
  templateUrl: './commingled-cargo-details.component.html',
  styleUrls: ['./commingled-cargo-details.component.scss']
})
export class CommingledCargoDetailsComponent implements OnInit {
  columns: any[];
  value: any[];
  constructor() { }

  ngOnInit(): void {
    this.columns = [
      { field: 'year', header: 'GRADE' },
      { field: 'year', header: 'TANK' },
      { field: 'year', header: 'QUANTITY (BLS)' },
      { field: 'year', header: 'API' },
      { field: 'year', header: 'TEMP(F)' },
      {
        field: 'vin', header: 'COMPOSITION BREAKDOWN', subColumns: [
          { field: 'year', header: 'PERCENTAGE' },
          { field: 'year', header: 'BBLS@OBS.TEMP' },
          { field: 'year', header: 'LT' },
          { field: 'year', header: 'MT' },
          { field: 'year', header: 'KL' }
        ]
      }
    ];
  }

}
