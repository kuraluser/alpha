import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { VesselsApiService } from '../../core/services/vessels-api.service';
import { IVessel } from '../../core/models/vessel-details.model';
import { IBallastTank, ICargoTank, Voyage, VOYAGE_STATUS, LOADABLE_STUDY_STATUS } from '../../core/models/common.model';
import { LoadablePlanApiService } from '../services/loadable-plan-api.service';
import { ICargoTankDetailValueObject, ILoadablePlanResponse, ILoadableQuantityCargo, ILoadableQuantityCommingleCargo, ILoadablePlanSynopticalRecord, ILoadablePlanCommentsDetails, IBallastStowageDetails } from '../models/loadable-plan.model';
import { LoadablePlanTransformationService } from '../services/loadable-plan-transformation.service';
import { DecimalPipe } from '@angular/common';
import { ICargoResponseModel, ICargo } from '../../../shared/models/common.model';
import { ConfirmationAlertService } from '../../../shared/components/confirmation-alert/confirmation-alert.service';
import { switchMap } from 'rxjs/operators';
import { VoyageService } from '../../core/services/voyage.service';
import { LoadableStudy } from '../models/loadable-study-list.model';
import { LoadableStudyListApiService } from '../services/loadable-study-list-api.service';

/**
 * Component class of loadable plan
 *
 * @export
 * @class LoadablePlanComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-loadable-plan',
  templateUrl: './loadable-plan.component.html',
  styleUrls: ['./loadable-plan.component.scss']
})
export class LoadablePlanComponent implements OnInit {

  get cargoTanks(): ICargoTank[][] {
    return this._cargoTanks;
  }
  set cargoTanks(tanks: ICargoTank[][]) {
    this._cargoTanks = tanks?.map(group => group.map(tank => this.loadablePlanTransformationService.formatCargoTanks(tank, this.cargoTankDetails)));
  }

  get cargoTankDetails(): ICargoTankDetailValueObject[] {
    return this._cargoTankDetails;
  }
  set cargoTankDetails(value: ICargoTankDetailValueObject[]) {
    this._cargoTankDetails = value;
  }

  get rearBallastTanks(): IBallastTank[][] {
    return this._rearBallastTanks;
  }
  set rearBallastTanks(tanks: IBallastTank[][]) {
    this._rearBallastTanks = tanks?.map(group => group.map(tank => this.loadablePlanTransformationService.formatBallastTanks(tank, this.loadablePlanBallastDetails)));
  }

  get centerBallastTanks(): IBallastTank[][] {
    return this._centerBallastTanks;
  }
  set centerBallastTanks(tanks: IBallastTank[][]) {
    this._centerBallastTanks = tanks?.map(group => group.map(tank => this.loadablePlanTransformationService.formatBallastTanks(tank, this.loadablePlanBallastDetails)));
  }

  get frontBallastTanks(): IBallastTank[][] {
    return this._frontBallastTanks;
  }
  set frontBallastTanks(tanks: IBallastTank[][]) {
    this._frontBallastTanks = tanks?.map(group => group.map(tank => this.loadablePlanTransformationService.formatBallastTanks(tank, this.loadablePlanBallastDetails)));
  }

  voyageId: number;
  loadableStudyId: number;
  loadableStudy: LoadableStudy;
  vesselId: number;
  loadablePatternId: number;
  vesselInfo: IVessel;
  loadableQuantityCargoDetails: ILoadableQuantityCargo[];
  loadableQuantityCommingleCargoDetails: ILoadableQuantityCommingleCargo[];
  loadablePlanBallastDetails: IBallastStowageDetails[];
  selectedVoyage: Voyage;
  LOADABLE_STUDY_STATUS = LOADABLE_STUDY_STATUS;
  VOYAGE_STATUS = VOYAGE_STATUS;
  public loadablePlanSynopticalRecords: ILoadablePlanSynopticalRecord[];
  public loadablePlanComments: ILoadablePlanCommentsDetails[];
  public voyageNumber: string;
  public date: string;
  public caseNumber: string;
  public cargos: ICargo[];
  public isConfirmed: boolean;

  private _cargoTanks: ICargoTank[][];
  private _cargoTankDetails: ICargoTankDetailValueObject[] = [];
  private _rearBallastTanks: IBallastTank[][];
  private _centerBallastTanks: IBallastTank[][];
  private _frontBallastTanks: IBallastTank[][];

  constructor(private ngxSpinnerService: NgxSpinnerService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private vesselsApiService: VesselsApiService,
    private loadablePlanApiService: LoadablePlanApiService,
    private loadablePlanTransformationService: LoadablePlanTransformationService,
    private _decimalPipe: DecimalPipe,
    private confirmationAlertService: ConfirmationAlertService,
    private voyageService: VoyageService,
    private loadableStudyListApiService: LoadableStudyListApiService
  ) { }

  /**
    * Component Lifecycle hook OnInit
    *
    * @memberof LoadablePlanComponent
  */
  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params => {
      this.vesselId = Number(params.get('vesselId'));
      this.voyageId = Number(params.get('voyageId'));
      this.loadableStudyId = Number(params.get('loadableStudyId'));
      this.loadablePatternId = Number(params.get('loadablePatternId'))
      this.getCargos()
      this.getVesselInfo();
      this.initSubsciptions();
	  this.getVoyages(this.vesselId, this.voyageId);
      this.getLoadableStudies(this.vesselId, this.voyageId, this.loadableStudyId);    });
    
  }

  /**
    * Initialise all subscription in this page
    *
    * @memberof LoadablePlanComponent
  */
  async initSubsciptions() {
    this.loadablePlanTransformationService.savedComments$.pipe(switchMap(() => {
      return this.loadablePlanApiService.getLoadablePlanDetails(this.vesselId, this.voyageId, this.loadableStudyId, this.loadablePatternId).toPromise();;
    })).subscribe((loadablePlanRes) => {
       if(loadablePlanRes.responseStatus.status === '200') {
        this.loadablePlanComments = loadablePlanRes.loadablePlanComments;
       }
    })
  }

  /**
    * Method to get cargos
    *
    * @memberof LoadablePlanComponent
  */
  async getCargos() {
    this.ngxSpinnerService.show();
    const cargos: ICargoResponseModel = await this.loadablePlanApiService.getCargos().toPromise();
    this.ngxSpinnerService.hide();
    if (cargos.responseStatus.status === '200') {
      this.cargos = cargos.cargos;
      this.getLoadablePlanDetails();
    }
  }

  /**
    * Method to fetch all vessel info
    *
    * @memberof LoadablePlanComponent
    */
  async getVesselInfo() {
    this.ngxSpinnerService.show();
    const res = await this.vesselsApiService.getVesselsInfo().toPromise();
    this.vesselInfo = res[0] ?? <IVessel>{};
    this.ngxSpinnerService.hide();
  }

  /**
   * Method to fetch all voyages in the vessel
   *
   * @param {number} vesselId
   * @param {number} voyageId
   * @memberof LoadablePlanComponent
   */
  async getVoyages(vesselId: number, voyageId: number) {
    this.ngxSpinnerService.show();
    const result = await this.voyageService.getVoyagesByVesselId(vesselId).toPromise();
    const voyages = result ?? [];
    this.selectedVoyage = voyages.find(voyage => voyage.id === voyageId);
    this.ngxSpinnerService.hide();
  }

  /**
   * Method to fetch all loadable studies
   *
   * @param {number} vesselId
   * @param {number} voyageId
   * @param {number} loadableStudyId
   * @memberof LoadablePlanComponent
   */
  async getLoadableStudies(vesselId: number, voyageId: number, loadableStudyId: number) {
    this.ngxSpinnerService.show();
    const result = await this.loadableStudyListApiService.getLoadableStudies(vesselId, voyageId).toPromise();
    this.loadableStudy = result?.loadableStudies?.find(loadableStudy => loadableStudy.id === loadableStudyId);
    this.ngxSpinnerService.hide();
  }


  /**
  * Method to back to loadable study
  *
  * @memberof LoadablePlanComponent
  */
  async backToLoadableStudy() {
    this.router.navigate([`/business/cargo-planning/loadable-study-details/${this.vesselId}/${this.voyageId}/${this.loadableStudyId}`]);
  }

  /**
  * Get details for loadable Plan
  * @returns {Promise<ILoadablePlanResponse>}
  * @memberof LoadablePlanComponent
  */
  private async getLoadablePlanDetails() {
    this.ngxSpinnerService.show();
    const loadablePlanRes: ILoadablePlanResponse = await this.loadablePlanApiService.getLoadablePlanDetails(this.vesselId, this.voyageId, this.loadableStudyId, this.loadablePatternId).toPromise();
    this.loadableQuantityCargoDetails = loadablePlanRes.loadableQuantityCargoDetails;
    this.loadableQuantityCargoDetails.map((loadableQuantityCargoDetail) => {
      loadableQuantityCargoDetail['grade'] = this.fingCargo(loadableQuantityCargoDetail)
    })
    this.loadableQuantityCommingleCargoDetails = loadablePlanRes.loadableQuantityCommingleCargoDetails;
    this.cargoTankDetails = loadablePlanRes?.loadablePlanStowageDetails ? loadablePlanRes?.loadablePlanStowageDetails?.map(cargo => {
      const tank = this.findCargoTank(cargo.tankId, loadablePlanRes?.tankLists)
      cargo.fullCapacityCubm = tank.fullCapacityCubm
      const formattedCargo = this.loadablePlanTransformationService.getFormatedCargoDetails(cargo)
      return this.loadablePlanTransformationService.getCargoTankDetailAsValueObject(formattedCargo)
    }) : [];
    this.cargoTanks = loadablePlanRes?.tankLists;
    this.loadablePlanBallastDetails = loadablePlanRes.loadablePlanBallastDetails?.map(ballast => {
      const tank = this.findBallastTank(ballast.tankId, [loadablePlanRes.frontBallastTanks, loadablePlanRes.rearBallastTanks, loadablePlanRes.centerBallastTanks])
      ballast.fullCapacityCubm = tank?.fullCapacityCubm
      const formattedCargo = this.loadablePlanTransformationService.getFormattedBallastDetails(this._decimalPipe, ballast)
      return formattedCargo
    }) ?? [];;
    this.frontBallastTanks = loadablePlanRes.frontBallastTanks;
    this.rearBallastTanks = loadablePlanRes.rearBallastTanks;
    this.centerBallastTanks = loadablePlanRes.centerBallastTanks;
    this.loadablePlanSynopticalRecords = loadablePlanRes.loadablePlanSynopticalRecords;
    this.loadablePlanComments = loadablePlanRes.loadablePlanComments;
    this.voyageNumber = loadablePlanRes.voyageNumber;
    this.date = loadablePlanRes.date;
    this.caseNumber = loadablePlanRes.caseNumber;
    this.ngxSpinnerService.hide();
  }

  /**
    * Method to get the specific cargo tank
    *
    * @memberof LoadablePlanComponent
  */
  findCargoTank(tankId, tankLists) {
    let tankDetails;
    tankLists.forEach(tankArr => {
      tankArr.forEach(tank => {
        if (tank.id === tankId) {
          tankDetails = tank;
          return;
        }
      })
    })
    return tankDetails;
  }

  /**
    * Method to get the specific ballast tank
    *
    * @memberof LoadablePlanComponent
  */
  findBallastTank(tankId, tankLists) {
    let tankDetails;
    tankLists.forEach(tankArr => {
      tankArr.forEach(arr => {
        arr.forEach(tank => {
          if (tank.id === tankId) {
            tankDetails = tank;
            return;
          }
        })
      })
    })
    return tankDetails;
  }

  /**
    * Method to find out cargo 
    *
    * @memberof LoadablePlanComponent
  */
  fingCargo(loadableQuantityCargoDetails): string {
    let cargoDetail;
    this.cargos.map((cargo) => {
      if (cargo.id === loadableQuantityCargoDetails.cargoId) {
        cargoDetail = cargo;
      }
    })
    return cargoDetail.name;
  }

  /**
   * for confirm stowage plan
   *
   * @param {*} event
   * @memberof LoadablePlanComponent
  */
  async confirmPlan() {
    this.ngxSpinnerService.show();
    const result = await this.loadablePlanApiService.getConfirmStatus(this.vesselId, this.voyageId, this.loadableStudyId, this.loadablePatternId).toPromise();
    this.ngxSpinnerService.hide();
    let detail;
    if (result.confirmed) {
      detail = "LOADABLE_PATTERN_CONFIRM_DETAILS_NOT_CONFIRM";
    } else {
      detail = "LOADABLE_PATTERN_CONFIRM_DETAILS_CONFIRM";
    }
    this.confirmationAlertService.add({ key: 'confirmation-alert', sticky: true, severity: 'warn', summary: 'LOADABLE_PATTERN_CONFIRM_SUMMARY', detail: detail, data: { confirmLabel: 'LOADABLE_PATTERN_CONFIRM_CONFIRM_LABEL', rejectLabel: 'LOADABLE_PATTERN_CONFIRM_REJECT_LABEL' } });
    this.confirmationAlertService.confirmAlert$.pipe().subscribe(async (response) => {
      if (response) {
        const confirmResult = await this.loadablePlanApiService.confirm(this.vesselId, this.voyageId, this.loadableStudyId, this.loadablePatternId).toPromise();
        if (confirmResult.responseStatus.status === '200') {
          this.isConfirmed = true;
        }
      }
    })
  }

}