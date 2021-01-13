import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { VesselsApiService } from '../../core/services/vessels-api.service';
import { IVessel } from '../../core/models/vessel-details.model';
import { IBallastStowageDetails, IBallastTank, ICargoTank } from '../../core/models/common.model';
import { LoadablePlanApiService } from '../services/loadable-plan-api.service';
import { ICargoTankDetailValueObject, ILoadablePlanResponse, ILoadableQuantityCargo, ILoadableQuantityCommingleCargo, ILoadablePlanSynopticalRecord } from '../models/loadable-plan.model';
import { LoadablePlanTransformationService } from '../services/loadable-plan-transformation.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { numberValidator } from '../directives/validator/number-validator.directive';

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
  vesselId: number;
  loadablePatternId: number;
  vesselInfo: IVessel;
  loadableQuantityCargoDetails: ILoadableQuantityCargo[];
  loadableQuantityCommingleCargoDetails: ILoadableQuantityCommingleCargo[];
  loadablePlanForm: FormGroup;
  loadablePlanBallastDetails: IBallastStowageDetails[];
  public loadablePlanSynopticalRecords: ILoadablePlanSynopticalRecord[];

  private _cargoTanks: ICargoTank[][];
  private _cargoTankDetails: ICargoTankDetailValueObject[];
  private _rearBallastTanks: IBallastTank[][];
  private _centerBallastTanks: IBallastTank[][];
  private _frontBallastTanks: IBallastTank[][];

  constructor(private ngxSpinnerService: NgxSpinnerService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private vesselsApiService: VesselsApiService,
    private loadablePlanApiService: LoadablePlanApiService,
    private loadablePlanTransformationService: LoadablePlanTransformationService,
    private fb: FormBuilder) { }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params => {
      this.vesselId = Number(params.get('vesselId'));
      this.voyageId = Number(params.get('voyageId'));
      this.loadableStudyId = Number(params.get('loadableStudyId'));
      this.loadablePatternId = Number(params.get('loadablePatternId'))
      this.getVesselInfo();
      this.getLoadablePlanDetails();
    });
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
    this.loadableQuantityCommingleCargoDetails = loadablePlanRes.loadableQuantityCommingleCargoDetails;
    this.cargoTankDetails = loadablePlanRes?.loadablePlanStowageDetails?.map(cargo => this.loadablePlanTransformationService.getCargoTankDetailAsValueObject(cargo));
    this.loadablePlanBallastDetails = loadablePlanRes.loadablePlanBallastDetails;
    this.cargoTanks = loadablePlanRes?.tankLists;
    this.initLoadablePlanForm();
    this.frontBallastTanks = loadablePlanRes.frontBallastTanks;
    this.rearBallastTanks = loadablePlanRes.rearBallastTanks;
    this.centerBallastTanks = loadablePlanRes.centerBallastTanks;
    this.loadablePlanSynopticalRecords = loadablePlanRes.loadablePlanSynopticalRecords;
    this.ngxSpinnerService.hide();
  }

  /**
   * Initialize loadable plan form
   *
   * @private
   * @memberof LoadablePlanComponent
   */
  private initLoadablePlanForm() {
    const cargoTankDetailsArray = this.cargoTankDetails?.map(cargo => this.initCargoTankFormGroup(cargo));
    this.loadablePlanForm = this.fb.group({
      cargoTanks: this.fb.group({
        dataTable: this.fb.array([...cargoTankDetailsArray])
      })
    });
  }

  /**
   * Initialize cargo tank form group
   *
   * @private
   * @param {ICargoTankDetailValueObject} cargo
   * @returns {FormGroup}
   * @memberof LoadablePlanComponent
   */
  private initCargoTankFormGroup(cargo: ICargoTankDetailValueObject): FormGroup {
    return this.fb.group({
      id: this.fb.control(cargo?.id),
      tankId: this.fb.control(cargo?.tankId),
      cargoAbbreviation: this.fb.control(cargo?.cargoAbbreviation),
      weight: this.fb.control(cargo?.weight?.value),
      correctedUllage: this.fb.control(cargo?.correctedUllage?.value),
      fillingRatio: this.fb.control(cargo?.fillingRatio?.value),
      tankName: this.fb.control(cargo?.tankName),
      rdgUllage: this.fb.control(cargo?.rdgUllage?.value, [Validators.required, numberValidator(2, 2, false)]),
      correctionFactor: this.fb.control(cargo?.correctionFactor?.value),
      observedM3: this.fb.control(cargo?.observedM3?.value),
      observedBarrels: this.fb.control(cargo?.observedBarrels?.value),
      observedBarrelsAt60: this.fb.control(cargo?.observedBarrelsAt60?.value),
      api: this.fb.control(cargo?.api),
      temperature: this.fb.control(cargo?.temperature),
    });
  }

}
