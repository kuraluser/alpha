import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'cpdss-portal-loading-information',
  templateUrl: './loading-information.component.html',
  styleUrls: ['./loading-information.component.scss']
})

/**
 * Component class for loading information component
 *
 * @export
 * @class LoadingInformationComponent
 * @implements {OnInit}
 */
export class LoadingInformationComponent implements OnInit {

  delayColumns:any = [];
  delayData: any = [];
  constructor() { }

  ngOnInit(): void {
    this.delayColumns = [
      { field: 'slNo', header: 'SL NO' },
      { field: 'delayReason', header: 'REASON FOR DELAY' },
      { field: 'stageDelay', header: 'STAGE OF DELAY' },
      { field: 'duration', header: 'DURATION' },
    ];
    this.delayData = [
      { delayReason: '', stageDelay: 'after loading', duration: 10000},
      { delayReason: '', stageDelay: 'after loading', duration: 10000},
      { delayReason: '', stageDelay: 'after loading', duration: 10000},
    ];

    
  }

}
