import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoadableStudies } from '../model/loadable-study-list.model'

@Injectable()
export class LoadableStudyListApiService {
  loadableStudyList: LoadableStudies[];

  constructor(private http: HttpClient) { }

  /**
   * Get loadable study list mock json
   */
  getLoadableStudyList() {

    this.loadableStudyList = [
      {
        "id": 1,
        "name": "LS-01",
        "status": "Plan generated",
        "detail": "LS-01_details",
        "createdDate": "15-10-2020",
        "charterer": "charter-1",
        "subCharterer": "sub-charter",
        "draftMark": 100,
        "loadLineXId": 1,
        "draftRestriction": 122,
        "maxTempExpected": 100
      },
      {
        "id": 2,
        "name": "LS-02",
        "status": "Confirmed",
        "detail": "details",
        "createdDate": "15-10-2020",
        "charterer": "charter-2",
        "subCharterer": "sub-charter",
        "draftMark": 200,
        "loadLineXId": 1,
        "draftRestriction": 200,
        "maxTempExpected": 50
      }
    ];
    return this.loadableStudyList;
  }

  /**
   * Get loadable study list
   */
  getLoadableStudyData(): LoadableStudies[] {
    //  this.http.get<LoadableStudies[]>('/vessels/{vesselId}/voyages/{voyageId}/loadable-studies');
    this.loadableStudyList = this.getLoadableStudyList();
    return this.loadableStudyList;

  }
}  
