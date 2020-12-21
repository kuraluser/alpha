import { Component, Input, OnInit } from '@angular/core';
import { LoadableStudy } from '../../cargo-planning/models/loadable-study-list.model';
import { Voyage } from '../../core/models/common.model';
import { ISynopticalRecords } from '../models/synoptical-table.model';
import { SynopticalApiService } from '../services/synoptical-api.service';

/**
 * Component class of synoptical table
 *
 * @export
 * @class SynopticalTableComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-synoptical-table',
  templateUrl: './synoptical-table.component.html',
  styleUrls: ['./synoptical-table.component.scss']
})
export class SynopticalTableComponent implements OnInit {
  synopticalRecords: ISynopticalRecords[];
  cols: any[];
  products: any[];

  @Input() selectedLoadableStudy: LoadableStudy;
  @Input() vesselId: number;
  @Input() voyage: Voyage;

  constructor(private synoticalApiService: SynopticalApiService) { }
/**
   * Component lifecycle ngOnit
   *
   * @returns {Promise<void>}
   * @memberof SynopticalTableComponent
   */
  async ngOnInit(): Promise<void> {
    this.cols = [
      { field: 'portName', header: 'Ports' },
      { field: 'operationType', header: 'ARR OR DEP' },
      { field: 'distance', header: 'Distance' },
      { field: 'speed', header: 'Speed' },
      { field: 'runningHours', header: 'Running Hours' },
      { field: 'inPortHours', header: 'In Port Hours' },
      { field: 'timeOfSunrise', header: 'Time of Sunrise' },
      { field: 'timeOfSunset', header: 'Time of Sunset' },
      { field: 'timeOfSunrise', header: 'Time of Sunrise' },

    ];
    const result = await this.synoticalApiService.getSynopticalTable(this.vesselId, this.voyage.id, this.selectedLoadableStudy.id).toPromise();
    if (result.responseStatus.status === "200") {
      this.synopticalRecords = result.synopticalRecords;
    }
   
  }

}
