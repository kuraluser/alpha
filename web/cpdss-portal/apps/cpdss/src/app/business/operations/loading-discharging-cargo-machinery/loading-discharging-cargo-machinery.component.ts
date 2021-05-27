import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'cpdss-portal-loading-discharging-cargo-machinery',
  templateUrl: './loading-discharging-cargo-machinery.component.html',
  styleUrls: ['./loading-discharging-cargo-machinery.component.scss']
})

/**
 * Component class for loading discharging cargo machinery component
 *
 * @export
 * @class LoadingDischargingCargoMachineryComponent
 * @implements {OnInit}
 */
export class LoadingDischargingCargoMachineryComponent implements OnInit {

  values: any = [];
  constructor() { }

  ngOnInit(): void {
    this.values = [
      {
        machine: 'Cargo Manifold',
        columns: [
          {
            selected: true,
            name : 'COP 1',
            capacity: '5000'
          },
          {
            selected: false,
            name : 'COP 2',
            capacity: '5000'
          },
          {
            selected: true,
            name : 'COP 3',
            capacity: '5000'
          }
        ]
      },
      {
        machine: 'Ballast Pump',
        columns: [
          {
            selected: true,
            name : 'BP 1',
            capacity: '5000'
          },
          {
            selected: false,
            name : 'BP 2',
            capacity: '5000'
          },
          {
            selected: true,
            name : 'BP 3',
            capacity: '5000'
          }
        ]
      }
    ];
  }

}
