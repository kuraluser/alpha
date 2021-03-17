import { Injectable } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { LoadablePattern, LoadableStudy } from '../../cargo-planning/models/loadable-study-list.model';
import { LoadableStudyListApiService } from '../../cargo-planning/services/loadable-study-list-api.service';
import { Voyage, VOYAGE_STATUS, VOYAGE_STATUS_LABEL } from '../../core/models/common.model';
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
  loadablePatternsList: LoadablePattern[];
  selectedLoadablePattern: LoadablePattern;
  vesselId: number;
  voyageId: number;
  isVoyageIdSelected = false;
  onInitCompleted = new BehaviorSubject(false);
  onInitCompleted$: Observable<boolean> = this.onInitCompleted.asObservable()
  save = new Subject();
  export = new Subject();
  edit = new Subject();
  cancel = new Subject();
  loadablePatternId: number;
  editMode = false;
  showActions = false;
  synopticalRecords: any;

  constructor(
    private loadableStudyListApiService: LoadableStudyListApiService,
    private vesselsApiService: VesselsApiService,
    private voyageService: VoyageService,
    private route: ActivatedRoute
  ) {
  }

  // Init function to intialize data
  async init() {
    const res = await this.vesselsApiService.getVesselsInfo().toPromise();
    if (!this.vesselInfo) {
      this.vesselInfo = res[0] ?? <IVessel>{};
      this.vesselId = this.vesselInfo.id;
    }
    if (!this.voyages) {
      this.voyages = await this.voyageService.getVoyagesByVesselId(this.vesselInfo?.id).toPromise();
    }
    await this.setSelectedVoyage();
    this.onInitCompleted.next(true)
  }

  // Destroy function to clear data
  async destroy() {
    this.selectedLoadableStudy = null;
    this.loadableStudyId = null;
    this.selectedLoadablePattern = null;
    this.loadablePatternId = null;
    this.selectedVoyage = null;
    this.setSelectedVoyage();
  }

  // Method to set selected voyages
  async setSelectedVoyage() {
    this.voyageId = Number(this.route.snapshot.params?.voyageId)
    if (this.selectedVoyage) {
      this.voyageId = this.selectedVoyage.id;
    } else if (this.voyages && this.voyageId) {
      this.selectedVoyage = this.voyages.find(voyage => voyage.id === this.voyageId);
      await this.getLoadableStudyInfo(this.vesselInfo.id, this.selectedVoyage.id)
    }
  }

  // Method to set selected loadable study
  setSelectedLoadableStudy() {
    this.selectedLoadableStudy = this.loadableStudyList.find(loadableStudy => loadableStudy.id === this.loadableStudyId);
    this.getLoadablePatterns();
  }

  /**
 * Get loadable study list for selected voyage
 */
  async getLoadableStudyInfo(vesselId: number, voyageId: number) {
    if (this.selectedVoyage.id !== 0) {
      this.isVoyageIdSelected = true;
      const result = await this.loadableStudyListApiService.getLoadableStudies(vesselId, voyageId).toPromise();
      this.loadableStudyList = result.loadableStudies;
      this.loadableStudyId = Number(this.route.snapshot.params?.loadableStudyId)
      if (this.selectedLoadableStudy) {
        this.loadableStudyId = this.selectedLoadableStudy.id;
      } else if (this.loadableStudyId) {
        this.setSelectedLoadableStudy();
      }
    }
    else {
      this.isVoyageIdSelected = false;
    }
  }

  /**
  * Get loadable pattern list for selected loadable study
  */
  async getLoadablePatterns() {
    const result = await this.loadableStudyListApiService.getLoadablePatterns(this.vesselId, this.voyageId, this.selectedLoadableStudy.id).toPromise();
    this.loadablePatternsList = result.loadablePatterns;
    if (this.selectedLoadablePattern) {
      this.loadablePatternId = this.selectedLoadablePattern.loadablePatternId;
    } else if (this.loadablePatternId) {
      this.selectedLoadablePattern = this.loadablePatternsList.find(pattern => pattern.loadablePatternId === this.loadablePatternId)
    }
  }

  /**
  * Method to save changes
  */
  saveChanges() {
    this.save.next()
  }

  /**
  * Method to export table data to excel
  */
  exportExcelFromTable() {
    this.export.next()
  }

  /**
  * Method to edit and cancel 
  */
  onEditOrCancel() {
    if (this.editMode) {
      this.cancel.next()
    } else {
      this.edit.next()
    }
  }
}
