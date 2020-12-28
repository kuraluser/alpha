import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ICargoTank, Voyage } from '../../core/models/common.model';
import { VesselsApiService } from '../../core/services/vessels-api.service';
import { VoyageService } from '../../core/services/voyage.service';
import { VesselDetailsModel } from '../../model/vessel-details.model';
import { LoadableStudy } from '../models/loadable-study-list.model';
import { LoadableStudyListApiService } from '../services/loadable-study-list-api.service';
import { LoadablePatternHistoryApiService } from '../services/loadable-pattern-history-api.service';
import { ILoadablePattern, ILoadablePatternCargoDetail, ILoadablePatternResponse } from '../models/loadable-pattern.model';

/**
 * Component class of pattern history screen
 *
 * @export
 * @class LoadablePatternHistoryComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-loadable-pattern-history',
  templateUrl: './loadable-pattern-history.component.html',
  styleUrls: ['./loadable-pattern-history.component.scss']
})
export class LoadablePatternHistoryComponent implements OnInit {

  get selectedLoadableStudy(): LoadableStudy {
    return this._selectedLoadableStudy;
  }
  set selectedLoadableStudy(selectedLoadableStudy: LoadableStudy) {
    this._selectedLoadableStudy = selectedLoadableStudy;
    this.loadableStudyId = selectedLoadableStudy?.id;
    if (selectedLoadableStudy) {
    }
  }

  private _selectedLoadableStudy: LoadableStudy;
  voyageId: number;
  loadableStudyId: number;
  vesselId: number;
  openSidePane = true;
  selectedVoyage: Voyage;
  vesselInfo: VesselDetailsModel;
  voyages: Voyage[];
  loadableStudies: LoadableStudy[];
  loadablePatternResponse: ILoadablePatternResponse;
  loadablePatterns: ILoadablePattern[];
  tankLists: ICargoTank[][];
  units = [{ name: '2020-20-22', statusId: 1 }];
  loadablePatternCreatedDate: string;
  loadableStudyName: string;
  display = false;
  selectedLoadablePatterns: ILoadablePatternCargoDetail;

  constructor(private vesselsApiService: VesselsApiService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private voyageService: VoyageService,
    private ngxSpinnerService: NgxSpinnerService,
    private loadableStudyListApiService: LoadableStudyListApiService,
    private loadablePatternApiService: LoadablePatternHistoryApiService) { }

  /**
   * Component lifecycle ngOnit
   *
   * @returns {Promise<void>}
   * @memberof LoadablePatternHistoryComponent
   */
  async ngOnInit(): Promise<void> {
    this.activatedRoute.paramMap.subscribe(params => {
      this.vesselId = Number(params.get('vesselId'));
      this.voyageId = Number(params.get('voyageId'));
      this.loadableStudyId = Number(params.get('loadableStudyId'));
      this.getLoadableStudies(this.vesselId, this.voyageId, this.loadableStudyId);
      this.getLoadablePatterns(this.vesselId, this.voyageId, this.loadableStudyId);
    });
  }

  /**
   * Method to fetch all voyages in the vessel
   *
   * @memberof LoadablePatternHistoryComponent
   */
  async backToLoadableStudy() {
    this.router.navigate([`/business/cargo-planning/loadable-study-details/${this.vesselId}/${this.voyageId}/${this.loadableStudyId}`]);
  }

  /**
     * Method to fetch all loadableStudies
     *
     * @param {number} vesselId
     * @param {number} voyageId
     * @param {number} loadableStudyId
     * @memberof LoadablePatternHistoryComponent
     */
  async getLoadableStudies(vesselId: number, voyageId: number, loadableStudyId: number) {
    this.ngxSpinnerService.show();
    const res = await this.vesselsApiService.getVesselsInfo().toPromise();
    this.vesselInfo = res[0] ?? <VesselDetailsModel>{};
    this.voyages = await this.getVoyages(this.vesselId, this.voyageId);
    const result = await this.loadableStudyListApiService.getLoadableStudies(vesselId, voyageId).toPromise();
    this.loadableStudies = result?.loadableStudies ?? [];
    this.selectedLoadableStudy = loadableStudyId ? this.loadableStudies.find(loadableStudy => loadableStudy.id === loadableStudyId) : this.loadableStudies[0];
    this.ngxSpinnerService.hide();
  }

  /**
   * Method to fetch all voyages in the vessel
   *
   * @param {number} vesselId
   * @param {number} voyageId
   * @memberof LoadablePatternHistoryComponent
   */
  async getVoyages(vesselId: number, voyageId: number) {
    const result = await this.voyageService.getVoyagesByVesselId(vesselId).toPromise();
    const voyages = result ?? [];
    this.selectedVoyage = voyages.find(voyage => voyage.id === voyageId);
    return voyages;
  }

  /**
 * Handler for on delete event from side panel
 *
 * @param {*} event
 * @memberof LoadablePatternHistoryComponent
 */
  onDeleteLoadableStudy(event) {
    //If deleted loadable study is equal to currently selected loadable study then we need reset the selection
    if (event?.data?.id === this.loadableStudyId) {
      const loadableStudies = this.loadableStudies?.filter(loadableStudy => event?.data?.id !== loadableStudy?.id);
      if (loadableStudies && loadableStudies.length) {
        this.selectedLoadableStudy = loadableStudies[0];
      } else {
        this.loadableStudyId = 0;
      }
    }
    this.loadableStudies.splice(event?.index, 1);
  }

  /**
   * Fetch loadable study details
   *
   * @param {number} vesselId
   * @param {number} voyageId
   * @param {number} loadableStudyId
   * @memberof LoadablePatternHistoryComponent
   */
  async getLoadablePatterns(vesselId: number, voyageId: number, loadableStudyId: number) {
    this.ngxSpinnerService.show();
    this.loadablePatternResponse = await this.loadablePatternApiService.getLoadablePatterns(vesselId, voyageId, loadableStudyId).toPromise();
    if (this.loadablePatternResponse.responseStatus.status === '200') {
      this.loadablePatterns = this.loadablePatternResponse.loadablePatterns;
      this.tankLists = this.loadablePatternResponse.tankLists;
      this.loadablePatternCreatedDate = this.loadablePatternResponse.loadablePatternCreatedDate;
      this.loadableStudyName = this.loadablePatternResponse.loadableStudyName;
    }
    this.ngxSpinnerService.hide();
  }

  /**
   * set visibility of popup (show/hide)
   *
   * @param {*} event
   * @memberof LoadablePatternHistoryComponent
   */
  setPopupVisibility(emittedValue) {
    this.selectedLoadablePatterns = null;
    this.display = emittedValue;
  }

  /**
   * set visibility of popup (show/hide)
   *
   * @param {*} event
   * @memberof LoadablePatternHistoryComponent
   */
  displayCommingleDetailPopup(emittedValue) {
    this.selectedLoadablePatterns = emittedValue.isCommingle ? emittedValue : null;
    this.display = emittedValue.isCommingle;
  }

  /**
   * Handler for loadable study chnage change
   *
   * @param {*} event
   * @memberof LoadablePatternHistoryComponent
   */
  onLoadableStudyChange(event) {
    this.loadableStudyId = event?.id;
    this.getLoadableStudies(this.vesselId, this.voyageId, this.loadableStudyId);
    this.getLoadablePatterns(this.vesselId, this.voyageId, this.loadableStudyId);
  }

  /**
   * for navigating stowage plan
   *
   * @param {*} event
   * @memberof LoadablePatternHistoryComponent
   */
  viewPlan() {
    this.router.navigate([`/business/cargo-planning/loadable-plan/${this.vesselId}/${this.voyageId}/${this.loadableStudyId}`]);
  }
  
}
