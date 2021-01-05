import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { VesselsApiService } from '../../core/services/vessels-api.service';
import { IVessel } from '../../core/models/vessel-details.model';
import { ICargoTank } from '../../core/models/common.model';
import { LoadablePlanApiService } from '../services/loadable-plan-api.service';
import { ICargoTankDetail, ILoadablePlanResponse, ILoadableQuantityCargo, ILoadableQuantityCommingleCargo } from '../models/loadable-plan.model';
import { LoadablePlanTransformationService } from '../services/loadable-plan-transformation.service';

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

  get cargoTankDetails(): ICargoTankDetail[] {
    return this._cargoTankDetails;
  }
  set cargoTankDetails(value: ICargoTankDetail[]) {
    this._cargoTankDetails = value;
  }

  voyageId: number;
  loadableStudyId: number;
  vesselId: number;
  loadablePatternId: number;
  vesselInfo: IVessel;
  loadableQuantityCargoDetails: ILoadableQuantityCargo[];
  loadableQuantityCommingleCargoDetails: ILoadableQuantityCommingleCargo[];

  private _cargoTanks: ICargoTank[][];
  private _cargoTankDetails: ICargoTankDetail[];

  constructor(private ngxSpinnerService: NgxSpinnerService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private vesselsApiService: VesselsApiService,
    private loadablePlanApiService: LoadablePlanApiService,
    private loadablePlanTransformationService: LoadablePlanTransformationService) { }

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
    this.cargoTanks = loadablePlanRes?.tankLists;
    this.cargoTankDetails = loadablePlanRes?.loadablePlanStowageDetails;
    this.cargoTanks = [
      [
        {
          "id": 25595,
          "categoryId": 1,
          "categoryName": "Cargo Tank",
          "name": "SLOP TANK",
          "frameNumberFrom": 49,
          "frameNumberTo": 52,
          "shortName": "SLP",
          "fillCapcityCubm": 4117,
          "fullCapacityCubm": "4117.3000",
          "density": 1.3,
          "group": 1,
          "order": 1,
          "slopTank": false,
          "commodity": {
            "cargoAbbreviation": "KEN",
            "cargoColor": "#FFFFFF",
            "tankId": 25595,
            "quantity": "1000.0000",
            "volume": 4000,
            "isCommingle": true
          },
        },
        {
          "id": 25593,
          "categoryId": 1,
          "categoryName": "Cargo Tank",
          "name": "NO.5 WING CARGO OIL TANK",
          "frameNumberFrom": 52,
          "frameNumberTo": 61,
          "shortName": "5P",
          "fillCapcityCubm": 17277,
          "fullCapacityCubm": "17277.4000",
          "density": 1.3,
          "group": 1,
          "order": 2,
          "slopTank": false,
          "commodity": {
            "cargoAbbreviation": "FNP",
            "cargoColor": "#FFFFFF",
            "tankId": 25593,
            "quantity": "1000.0000",
            "volume": 10000,
            "isCommingle": false
          },
        },
        {
          "id": 25584,
          "categoryId": 1,
          "categoryName": "Cargo Tank",
          "name": "NO.5 CENTER CARGO OIL TANK",
          "frameNumberFrom": 49,
          "frameNumberTo": 61,
          "shortName": "5C",
          "fillCapcityCubm": 20798,
          "fullCapacityCubm": "20797.7000",
          "density": 1.3,
          "group": 1,
          "order": 3,
          "slopTank": false
        },
        {
          "id": 25596,
          "categoryId": 1,
          "categoryName": "Cargo Tank",
          "name": "SLOP TANK",
          "frameNumberFrom": 49,
          "frameNumberTo": 52,
          "shortName": "SLS",
          "fillCapcityCubm": 4117,
          "fullCapacityCubm": "4117.3000",
          "density": 1.3,
          "group": 1,
          "order": 4,
          "slopTank": false
        },
        {
          "id": 25594,
          "categoryId": 1,
          "categoryName": "Cargo Tank",
          "name": "NO.5 WING CARGO OIL TANK",
          "frameNumberFrom": 52,
          "frameNumberTo": 61,
          "shortName": "5S",
          "fillCapcityCubm": 17277,
          "fullCapacityCubm": "17277.4000",
          "density": 1.3,
          "group": 1,
          "order": 5,
          "slopTank": false
        }
      ],
      [
        {
          "id": 25591,
          "categoryId": 1,
          "categoryName": "Cargo Tank",
          "name": "NO.4 WING CARGO OIL TANK",
          "frameNumberFrom": 61,
          "frameNumberTo": 71,
          "shortName": "4P",
          "fillCapcityCubm": 20281,
          "fullCapacityCubm": "20280.8000",
          "density": 1.3,
          "group": 2,
          "order": 1,
          "slopTank": false
        },
        {
          "id": 25583,
          "categoryId": 1,
          "categoryName": "Cargo Tank",
          "name": "NO.4 CENTER CARGO OIL TANK",
          "frameNumberFrom": 61,
          "frameNumberTo": 71,
          "shortName": "4C",
          "fillCapcityCubm": 33725,
          "fullCapacityCubm": "33725.1000",
          "density": 1.3,
          "group": 2,
          "order": 2,
          "slopTank": false
        },
        {
          "id": 25592,
          "categoryId": 1,
          "categoryName": "Cargo Tank",
          "name": "NO.4 WING CARGO OIL TANK",
          "frameNumberFrom": 61,
          "frameNumberTo": 71,
          "shortName": "4S",
          "fillCapcityCubm": 20281,
          "fullCapacityCubm": "20280.8000",
          "density": 1.3,
          "group": 2,
          "order": 3,
          "slopTank": false
        }
      ],
      [
        {
          "id": 25589,
          "categoryId": 1,
          "categoryName": "Cargo Tank",
          "name": "NO.3 WING CARGO OIL TANK",
          "frameNumberFrom": 71,
          "frameNumberTo": 81,
          "shortName": "3P",
          "fillCapcityCubm": 20281,
          "fullCapacityCubm": "20280.8000",
          "density": 1.3,
          "group": 3,
          "order": 1,
          "slopTank": false
        },
        {
          "id": 25582,
          "categoryId": 1,
          "categoryName": "Cargo Tank",
          "name": "NO.3 CENTER CARGO OIL TANK",
          "frameNumberFrom": 71,
          "frameNumberTo": 81,
          "shortName": "3C",
          "fillCapcityCubm": 28202,
          "fullCapacityCubm": "28201.6000",
          "density": 1.3,
          "group": 3,
          "order": 2,
          "slopTank": false
        },
        {
          "id": 25590,
          "categoryId": 1,
          "categoryName": "Cargo Tank",
          "name": "NO.3 WING CARGO OIL TANK",
          "frameNumberFrom": 71,
          "frameNumberTo": 81,
          "shortName": "3S",
          "fillCapcityCubm": 20281,
          "fullCapacityCubm": "20280.8000",
          "density": 1.3,
          "group": 3,
          "order": 3,
          "slopTank": false
        }
      ],
      [
        {
          "id": 25587,
          "categoryId": 1,
          "categoryName": "Cargo Tank",
          "name": "NO.2 WING CARGO OIL TANK",
          "frameNumberFrom": 81,
          "frameNumberTo": 91,
          "shortName": "2P",
          "fillCapcityCubm": 20281,
          "fullCapacityCubm": "20280.8000",
          "density": 1.3,
          "group": 4,
          "order": 1,
          "slopTank": false
        },
        {
          "id": 25581,
          "categoryId": 1,
          "categoryName": "Cargo Tank",
          "name": "NO.2 CENTER CARGO OIL TANK",
          "frameNumberFrom": 81,
          "frameNumberTo": 91,
          "shortName": "2C",
          "fillCapcityCubm": 28202,
          "fullCapacityCubm": "28201.6000",
          "density": 1.3,
          "group": 4,
          "order": 2,
          "slopTank": false
        },
        {
          "id": 25588,
          "categoryId": 1,
          "categoryName": "Cargo Tank",
          "name": "NO.2 WING CARGO OIL TANK",
          "frameNumberFrom": 81,
          "frameNumberTo": 91,
          "shortName": "2S",
          "fillCapcityCubm": 20281,
          "fullCapacityCubm": "20280.8000",
          "density": 1.3,
          "group": 4,
          "order": 3,
          "slopTank": false
        }
      ],
      [
        {
          "id": 25585,
          "categoryId": 1,
          "categoryName": "Cargo Tank",
          "name": "NO.1  WING CARGO OIL TANK",
          "frameNumberFrom": 91,
          "frameNumberTo": 103,
          "shortName": "1P",
          "fillCapcityCubm": 20798,
          "fullCapacityCubm": "20797.7000",
          "density": 1.3,
          "group": 5,
          "order": 1,
          "slopTank": false
        },
        {
          "id": 25580,
          "categoryId": 1,
          "categoryName": "Cargo Tank",
          "name": "NO.1 CENTER CARGO OIL TANK",
          "frameNumberFrom": 91,
          "frameNumberTo": 103,
          "shortName": "1C",
          "fillCapcityCubm": 30230,
          "fullCapacityCubm": "30229.5000",
          "density": 1.3,
          "group": 5,
          "order": 2,
          "slopTank": false
        },
        {
          "id": 25586,
          "categoryId": 1,
          "categoryName": "Cargo Tank",
          "name": "NO.1  WING CARGO OIL TANK",
          "frameNumberFrom": 91,
          "frameNumberTo": 103,
          "shortName": "1S",
          "fillCapcityCubm": 20281,
          "fullCapacityCubm": "20280.8000",
          "density": 1.3,
          "group": 5,
          "order": 3,
          "slopTank": false
        }
      ]
    ];
    this.ngxSpinnerService.hide();
  }

}
