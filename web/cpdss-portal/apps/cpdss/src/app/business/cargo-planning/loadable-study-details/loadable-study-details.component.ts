import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { IPort } from '../models/cargo-planning.model';
import { LoadableStudyDetailsTransformationService } from '../services/loadable-study-details-transformation.service';
import { LoadableStudyDetailsApiService } from '../services/loadable-study-details-api.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Voyage } from '../../core/models/common.models';
import { VoyageService } from '../../core/services/voyage.service';
import { IDischargingPortIds, LoadableStudy } from '../models/loadable-study-list.model';
import { LoadableStudyListApiService } from '../services/loadable-study-list-api.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { VesselDetailsModel } from '../../model/vessel-details.model';
import { VesselsApiService } from '../../core/services/vessels-api.service';
import { TranslateService } from '@ngx-translate/core';
import { MessageService } from 'primeng/api';

/**
 * Component class for loadable study details component
 *
 * @export
 * @class LoadableStudyDetailsComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-loadable-study-details',
  templateUrl: './loadable-study-details.component.html',
  styleUrls: ['./loadable-study-details.component.scss']
})
export class LoadableStudyDetailsComponent implements OnInit {

  get selectedLoadableStudy(): LoadableStudy {
    return this._selectedLoadableStudy;
  }
  set selectedLoadableStudy(selectedLoadableStudy: LoadableStudy) {
    this._selectedLoadableStudy = selectedLoadableStudy;
    this.loadableStudyId = selectedLoadableStudy?.id;
    if (selectedLoadableStudy) {
      this.router.navigate([`business/cargo-planning/loadable-study-details/${this.vesselId}/${this.voyageId}/${this.loadableStudyId}`]);
    }
  }

  dischargingPorts: IPort[] = [];//TODO to be populated form loadable study details
  dischargingPortsNames: string;//TODO to be populated form loadable study details
  totalQuantity$: Observable<number>;
  ports: IPort[];
  cargoNominationComplete$: Observable<boolean>;
  voyageId: number;
  loadableStudyId: number;
  vesselId: number;
  vesselInfo: VesselDetailsModel;
  selectedVoyage: Voyage;
  voyages: Voyage[];
  loadableStudies: LoadableStudy[];
  _selectedLoadableStudy: LoadableStudy;
  openSidePane = true;
  portsComplete$: Observable<boolean>;

  constructor(private loadableStudyDetailsApiService: LoadableStudyDetailsApiService,
    private loadableStudyDetailsTransformationService: LoadableStudyDetailsTransformationService,
    private loadableStudyListApiService: LoadableStudyListApiService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private voyageService: VoyageService,
    private vesselsApiService: VesselsApiService,
    private ngxSpinnerService: NgxSpinnerService,
    private translateService: TranslateService,
    private messageService: MessageService) {
  }

  ngOnInit(): void {
    this.initSubsciptions();
    this.activatedRoute.paramMap.subscribe(params => {
      this.vesselId = Number(params.get('vesselId'));
      this.voyageId = Number(params.get('voyageId'));
      this.loadableStudyId = Number(params.get('loadableStudyId'));
      this.getLoadableStudies(this.vesselId, this.voyageId, this.loadableStudyId);
    });
  }

  /**
   * Method to fetch all voyages in the vessel
   *
   * @param {number} vesselId
   * @param {number} voyageId
   * @memberof LoadableStudyDetailsComponent
   */
  async getVoyages(vesselId: number, voyageId: number) {
    const result = await this.voyageService.getVoyagesByVesselId(vesselId).toPromise();
    const voyages = result ?? [];
    this.selectedVoyage = voyages.find(voyage => voyage.id === voyageId);
    return voyages;
  }

  /**
   * Method to fetch all loadableStudies
   *
   * @param {number} vesselId
   * @param {number} voyageId
   * @param {number} loadableStudyId
   * @memberof LoadableStudyDetailsComponent
   */
  async getLoadableStudies(vesselId: number, voyageId: number, loadableStudyId: number) {
    this.ngxSpinnerService.show();
    const res = await this.vesselsApiService.getVesselsInfo().toPromise();
    this.vesselInfo = res[0] ?? <VesselDetailsModel>{};
    this.voyages = await this.getVoyages(this.vesselId, this.voyageId);
    this.ports = await this.getPorts();
    const result = await this.loadableStudyListApiService.getLoadableStudies(vesselId, voyageId).toPromise();
    this.loadableStudies = result?.loadableStudies ?? [];
    this.selectedLoadableStudy = loadableStudyId ? this.loadableStudies.find(loadableStudy => loadableStudy.id === loadableStudyId) : this.loadableStudies[0];
    this.dischargingPorts = this.selectedLoadableStudy?.dischargingPortIds?.map(portId => this.ports.find(port => port?.id === portId));
    this.dischargingPortsNames = this.dischargingPorts?.map(port => port?.name).toString();
    this.loadableStudyId = this.selectedLoadableStudy?.id;
    this.ngxSpinnerService.hide();
    if (!loadableStudyId) {
      this.router.navigate([`business/cargo-planning/loadable-study-details/${vesselId}/${voyageId}/${this.loadableStudyId}`]);
    }
  }

  /**
   * Triggering add cargo nomination
   *
   * @memberof LoadableStudyDetailsComponent
   */
  addCargoNomination() {
    this.loadableStudyDetailsTransformationService.addCargoNomination();
  }

  /**
   * Initialise all subscription in this page
   *
   * @private
   * @memberof LoadableStudyDetailsComponent
   */
  private initSubsciptions() {
    this.totalQuantity$ = this.loadableStudyDetailsTransformationService.totalQuantityCargoNomination$;
    this.cargoNominationComplete$ = this.loadableStudyDetailsTransformationService.cargoNominationValidity$;
    this.portsComplete$ = this.loadableStudyDetailsTransformationService.portValidity$;
  }

  /**
   * Method for fetching all ports from master
   *
   * @private
   * @memberof LoadableStudyDetailsComponent
   */
  private async getPorts(): Promise<IPort[]> {
    const result = await this.loadableStudyDetailsApiService.getPorts().toPromise();
    return result;
  }

  /**
   * Handler for discharge port dropdown change
   *
   * @param {Event} event
   * @memberof LoadableStudyDetailsComponent
   */
  async onDischargePortChange(event: Event) {
    this.ngxSpinnerService.show();
    this.selectedLoadableStudy.dischargingPortIds = this.dischargingPorts?.map(port => port.id);
    const dischargingPortIds: IDischargingPortIds = { portIds: this.selectedLoadableStudy.dischargingPortIds };
    const translationKeys = await this.translateService.get(['LOADABLE_STUDY_DISCHARGING_PORT_UPDATE_SUCCESS', 'LOADABLE_STUDY_DISCHARGING_PORT_UPDATE_SUCCESSFULLY']).toPromise();
    const res = await this.loadableStudyDetailsApiService.setLoadableStudyDischargingPorts(this.vesselId, this.voyageId, this.loadableStudyId, dischargingPortIds).toPromise();
    if (res?.responseStatus?.status === "200") {
      this.messageService.add({ severity: 'success', summary: translationKeys['LOADABLE_STUDY_DISCHARGING_PORT_UPDATE_SUCCESS'], detail: translationKeys['LOADABLE_STUDY_DISCHARGING_PORT_UPDATE_SUCCESSFULLY'] });
    }
    this.ngxSpinnerService.hide();
  }

  /**
   * Handler for voyage dropdown change
   *
   * @param {*} event
   * @memberof LoadableStudyDetailsComponent
   */
  onVoyageChange(event) {
    this.voyageId = event?.value?.id;
    this.router.navigate([`business/cargo-planning/loadable-study-details/${this.vesselId}/${this.voyageId}/0`]);
  }

   /**
   * Triggering add port
   *
   * @memberof LoadableStudyDetailsComponent
   */
  addPort() {
    this.loadableStudyDetailsTransformationService.addPort();
  }

  /**
   * Handler for on delete event from side panel
   *
   * @param {*} event
   * @memberof LoadableStudyDetailsComponent
   */
  onDeleteLoadableStudy(event) {
    this.router.navigate([`business/cargo-planning/loadable-study-details/${this.vesselId}/${this.voyageId}/0`]);
  }
}
