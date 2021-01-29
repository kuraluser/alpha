import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { ISynopticalResponse } from '../models/synoptical-table.model';
/**
 * 
 * Api Service for Synoptical Table
 */

@Injectable()
export class SynopticalApiService {

    constructor(private commonApiService: CommonApiService) { }

    /**
     * Get synoptical table records
     * @param vesselId 
     * @param voyageId 
     * @param loadableStudyId 
     * @param loadablePatternId 
     */
    getSynopticalTable(vesselId: number, voyageId: number, loadableStudyId: number, loadablePatternId: number): Observable<any> {
        if (!loadablePatternId) {
            loadablePatternId = 0;
        }
        return this.commonApiService.get<ISynopticalResponse>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}/loadable-pattern/${loadablePatternId}/synoptical-table`);
    }

    /**
     * Post synoptical table records
     * @param postData 
     * @param vesselId 
     * @param voyageId 
     * @param loadableStudyId 
     * @param loadablePatternId 
     */
    saveSynopticalTable(postData, vesselId: number, voyageId: number, loadableStudyId: number, loadablePatternId: number): Observable<any> {
        if (!loadablePatternId) {
            loadablePatternId = 0;
        }
        return this.commonApiService.post(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}/loadable-pattern/${loadablePatternId}/synoptical-table`, postData);
    }

}
