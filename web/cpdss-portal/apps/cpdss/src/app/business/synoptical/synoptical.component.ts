import { Component, OnInit } from '@angular/core';
import { Voyage } from '../core/models/common.model';
import { VesselsApiService } from '../core/services/vessels-api.service';
import { VoyageService } from '../core/services/voyage.service';
import { VesselDetailsModel } from '../model/vessel-details.model';
import { NgxSpinnerService } from 'ngx-spinner';
import { LoadableStudyListApiService } from '../cargo-planning/services/loadable-study-list-api.service';
import { LoadableStudy } from '../cargo-planning/models/loadable-study-list.model';
import { Router } from '@angular/router';

/**
 * Component class of synoptical
 *
 * @export
 * @class SynopticalComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-synoptical',
  templateUrl: './synoptical.component.html',
  styleUrls: ['./synoptical.component.scss']
})
export class SynopticalComponent implements OnInit {
  vesselInfo: VesselDetailsModel;
  voyages: Voyage[];
  selectedVoyage: Voyage;
  loadableStudyList: LoadableStudy[];
  selectedLoadableStudy: LoadableStudy;
  isVoyageIdSelected = true;

  constructor(private vesselsApiService: VesselsApiService,
    private voyageService: VoyageService, private ngxSpinnerService: NgxSpinnerService,
    private loadableStudyListApiService: LoadableStudyListApiService,
    private router: Router
  ) { }

  /**
   * Component lifecycle ngOnit
   *
   * @returns {Promise<void>}
   * @memberof SynopticalComponent
   */
  async ngOnInit(): Promise<void> {
    this.ngxSpinnerService.show();
    const res = await this.vesselsApiService.getVesselsInfo().toPromise();
    this.vesselInfo = res[0] ?? <VesselDetailsModel>{};
    this.voyages = await this.voyageService.getVoyagesByVesselId(this.vesselInfo?.id).toPromise();
    this.ngxSpinnerService.hide();

  }

  /**
  * Show loadable study list based on selected voyage id
  */
  showLoadableStudyList() {
    this.getLoadableStudyInfo(this.vesselInfo?.id, this.selectedVoyage.id);
  }

  /**
  * Get loadable study list for selected voyage
  */
  async getLoadableStudyInfo(vesselId: number, voyageId: number) {
    if (this.selectedVoyage.id !== 0) {
      this.isVoyageIdSelected = true;
      const result = await this.loadableStudyListApiService.getLoadableStudies(vesselId, voyageId).toPromise();
      this.loadableStudyList = result.loadableStudies;
    }
    else{
      this.isVoyageIdSelected = false;
    }
  }

  /**
   * On selecting the loadable study
   *
   * @param event
   * @memberof SynopticalComponent
   */

  onSelectLoadableStudy(event){
    this.router.navigateByUrl('/business/synoptical/'+ this.vesselInfo.id + '/'+ this.selectedVoyage.id + '/' + this.selectedLoadableStudy.id)
  }



}
