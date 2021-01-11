import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { LoadableStudy } from '../../cargo-planning/models/loadable-study-list.model';
import { LoadableStudyListApiService } from '../../cargo-planning/services/loadable-study-list-api.service';
import { Voyage } from '../../core/models/common.model';
import { IVessel } from '../../core/models/vessel-details.model';
import { VesselsApiService } from '../../core/services/vessels-api.service';
import { VoyageService } from '../../core/services/voyage.service';

/*
 * Service class for synoptical table
 *
 * @export
 * @class SynopticalService
*/
@Injectable()
export class SynopticalService {

  vesselInfo: IVessel;
  voyages: Voyage[];
  selectedVoyage: Voyage;
  loadableStudyList: LoadableStudy[];
  selectedLoadableStudy: LoadableStudy;
  loadableStudyId: number;
  vesselId: number;
  voyageId: number;
  isVoyageIdSelected = false;
  onInitCompleted = new BehaviorSubject(false);
  onInitCompleted$: Observable<boolean> = this.onInitCompleted.asObservable()

  constructor(
    private loadableStudyListApiService: LoadableStudyListApiService,
    private vesselsApiService: VesselsApiService,
    private voyageService: VoyageService,
  ) { }

  // Init function to intialize data
  async init() {
    const res = await this.vesselsApiService.getVesselsInfo().toPromise();
    this.vesselInfo = res[0] ?? <IVessel>{};
    this.voyages = await this.voyageService.getVoyagesByVesselId(this.vesselInfo?.id).toPromise();
    await this.setSelectedVoyage();
    this.onInitCompleted.next(true)
  }

  // Method to set selected voyages
  async setSelectedVoyage() {
    if (!this.selectedVoyage && this.voyages && this.voyageId) {
      this.selectedVoyage = this.voyages.find(voyage => voyage.id == this.voyageId)
      await this.getLoadableStudyInfo(this.vesselInfo.id, this.selectedVoyage.id)
    } else if (this.selectedVoyage) {
      this.voyageId = this.selectedVoyage.id;
    }
  }

  // Method to set selected loadable study
  setSelectedLoadableStudy() {
    this.selectedLoadableStudy = this.loadableStudyList.find(loadableStudy => loadableStudy.id == this.loadableStudyId)
  }

  /**
 * Get loadable study list for selected voyage
 */
  async getLoadableStudyInfo(vesselId: number, voyageId: number) {
    if (this.selectedVoyage.id !== 0) {
      this.isVoyageIdSelected = true;
      const result = await this.loadableStudyListApiService.getLoadableStudies(vesselId, voyageId).toPromise();
      this.loadableStudyList = result.loadableStudies;
      if (!this.selectedLoadableStudy && this.loadableStudyId) {
        this.setSelectedLoadableStudy();
      } else if (this.selectedLoadableStudy) {
        this.loadableStudyId = this.selectedLoadableStudy.id;
      }
    }
    else {
      this.isVoyageIdSelected = false;
    }
  }

}
