import { Injectable } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { LoadablePattern, LoadableStudy } from '../../cargo-planning/models/loadable-study-list.model';
import { LoadableStudyListApiService } from '../../cargo-planning/services/loadable-study-list-api.service';
import { LOADABLE_STUDY_STATUS, Voyage, VOYAGE_STATUS } from '../../core/models/common.model';
import { IVessel } from '../../core/models/vessel-details.model';
import { VesselsApiService } from '../../core/services/vessels-api.service';
import { VoyageService } from '../../core/services/voyage.service';
import { ISubTotal } from '../../../shared/models/common.model';
import { DischargeStudyListApiService } from '../../cargo-planning/services/discharge-study-list-api.service';
import { IDischargeStudy } from '../../cargo-planning/models/discharge-study-list.model';

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
  dischargeStudyList: IDischargeStudy[];
  selectedLoadableStudy: LoadableStudy;
  selectedDischargeStudy: IDischargeStudy;
  loadableOrDischargeStudyId: number;
  loadablePatternsList: LoadablePattern[];
  selectedLoadablePattern: LoadablePattern;
  vesselId: number;
  voyageId: number;
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
  hasDischargeStarted = false;

  constructor(
    private loadableStudyListApiService: LoadableStudyListApiService,
    private dischargeStudyListApiService: DischargeStudyListApiService,
    private vesselsApiService: VesselsApiService,
    private voyageService: VoyageService,
    private route: ActivatedRoute,
    private router: Router
  ) {
  }

  // Init function to intialize data
  async init() {
    localStorage.removeItem("loadablePatternId")
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



  // Method to set selected voyages
  async setSelectedVoyage() {
    const voyageId = Number(this.route.snapshot.params?.voyageId)
    if (this.voyages && voyageId && this.voyageId !== voyageId) {
      this.voyageId = voyageId;
      this.selectedVoyage = this.voyages.find(voyage => voyage.id === this.voyageId);
      this.hasDischargeStarted = this.selectedVoyage.isDischargeStarted ?? false;
      if (this.hasDischargeStarted) {
        await this.getDischargeStudyInfo(this.vesselInfo.id, this.selectedVoyage.id)
      } else {
        await this.getLoadableStudyInfo(this.vesselInfo.id, this.selectedVoyage.id)
      }
    }
  }

  // Method to set selected loadable study
  setSelectedLoadableStudy() {
    if (this.selectedVoyage.statusId !== VOYAGE_STATUS.ACTIVE && this.selectedVoyage.statusId !== VOYAGE_STATUS.CLOSE) {
      this.selectedLoadableStudy = this.loadableStudyList.find(loadableStudy => loadableStudy.id === this.loadableOrDischargeStudyId);
      this.getLoadablePatterns();
    }
  }

  // Method to set selected discharge study
  setSelectedDischargeStudy() {
    this.selectedDischargeStudy = this.dischargeStudyList.find(dischargeStudy => dischargeStudy.id === this.loadableOrDischargeStudyId);
    this.router.navigateByUrl('/business/synoptical/' + this.vesselInfo.id + '/' + this.selectedVoyage.id + '/' + this.selectedDischargeStudy.id);
  }

  /**
* Get discharge study list for selected voyage
*/
  async getDischargeStudyInfo(vesselId: number, voyageId: number) {
    if (this.selectedVoyage?.id !== 0) {
      const result = await this.dischargeStudyListApiService.getDischargeStudies(vesselId, voyageId).toPromise();
      const dischargeStudy = result.dischargeStudies.find(ds => ds.status === "Confirmed")
      if (dischargeStudy?.id) {
        this.dischargeStudyList = [dischargeStudy];
        this.loadableOrDischargeStudyId = dischargeStudy.id;
      } else {
        this.dischargeStudyList = result.dischargeStudies;
        if (this.route?.snapshot?.children[0]?.params?.loadableStudyId) {
          this.loadableOrDischargeStudyId = Number(this.route?.snapshot?.children[0]?.params?.loadableStudyId);
        } else {
          this.loadableOrDischargeStudyId = this.dischargeStudyList[0].id;
        }
      }
      this.setSelectedDischargeStudy();
    }
  }

  /**
 * Get loadable study list for selected voyage
 */
  async getLoadableStudyInfo(vesselId: number, voyageId: number) {
    if (this.selectedVoyage?.id !== 0) {
      const result = await this.loadableStudyListApiService.getLoadableStudies(vesselId, voyageId).toPromise();
      if (this.selectedVoyage.statusId === VOYAGE_STATUS.ACTIVE || this.selectedVoyage.statusId === VOYAGE_STATUS.CLOSE) {
        this.selectedLoadableStudy = result.loadableStudies.find(ls => ls.status === "Confirmed")
        this.loadableStudyList = [this.selectedLoadableStudy]
        this.getLoadablePatterns()
      } else {
        this.loadableStudyList = result.loadableStudies;
        this.loadableOrDischargeStudyId = this.route?.snapshot?.children[0]?.params?.loadableStudyId ? Number(this.route?.snapshot?.children[0]?.params?.loadableStudyId) : this.loadableStudyList[0]?.id;
        if (this.selectedLoadableStudy) {
          this.loadableOrDischargeStudyId = this.selectedLoadableStudy?.id;
        } else if (this.loadableOrDischargeStudyId) {
          this.setSelectedLoadableStudy();
        }
      }
    }
  }

  /**
  * Get loadable pattern list for selected loadable study
  */
  async getLoadablePatterns() {
    if (this.selectedLoadableStudy) {
      const result = await this.loadableStudyListApiService.getLoadablePatterns(this.vesselId, this.voyageId, this.selectedLoadableStudy.id).toPromise();
      if (this.selectedLoadableStudy.status === "Confirmed") {
        this.selectedLoadablePattern = result.loadablePatterns.find(pattern => pattern.loadableStudyStatusId === LOADABLE_STUDY_STATUS.PLAN_CONFIRMED)
        this.loadablePatternsList = [this.selectedLoadablePattern]

      } else {
        this.loadablePatternsList = result.loadablePatterns;
        if (this.selectedLoadablePattern) {
          this.loadablePatternId = this.selectedLoadablePattern.loadablePatternId;
        } else if (this.loadablePatternId) {
          this.selectedLoadablePattern = this.loadablePatternsList.find(pattern => pattern.loadablePatternId === this.loadablePatternId)
        } else {
          this.selectedLoadablePattern = this.loadablePatternsList[0]
        }
      }

      if (this.selectedLoadablePattern) {
        this.router.navigateByUrl('/business/synoptical/' + this.vesselInfo.id + '/' + this.selectedVoyage.id + '/' + this.selectedLoadableStudy.id + '/' + this.selectedLoadablePattern.loadablePatternId);
      } else if (this.selectedLoadableStudy?.id) {
        this.router.navigateByUrl('/business/synoptical/' + this.vesselInfo.id + '/' + this.selectedVoyage.id + '/' + this.selectedLoadableStudy.id);
      }
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

  /**
* Method for calculating  subtotal
*
* @param {ISubTotal} data
* @returns {number}
* @memberof SynopticalService
*/
  getSubTotal(data: ISubTotal): Number {
    const subTotal = Number(data.dwt) - Number(data.sagCorrection) + Number(data.sgCorrection ? data.sgCorrection : 0) - Number(data.foOnboard)
      - Number(data.doOnboard) - Number(data.freshWaterOnboard) - Number(data.ballast)
      - Number(data.constant) - Number(data.obqSlops) - Number(data.others);
    return Number(subTotal);
  }

  /**
  * Method to get the Loadable Study id
  */
  getLoadableStudyId(){
    if(this.hasDischargeStarted){
      return this.selectedVoyage.confirmedLoadableStudyId;
    } else {
      return this.loadableOrDischargeStudyId;
    }
  }
}
