import { Component, OnInit, Input } from '@angular/core';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { ITankOptions, IShipCargoTank, IVoyagePortDetails, TANKTYPE, ICargoQuantities, IShipBallastTank } from '../../core/models/common.model';
import { ArrivalConditionTransformationService } from './arrival-condition-transformation.service';
import { QUANTITY_UNIT, ICargoConditions } from '../../../shared/models/common.model';
import { ULLAGE_STATUS, ULLAGE_STATUS_TEXT, ULLAGE_STATUS_VALUE } from '../models/loading-discharging.model';
import { LoadingDischargingTransformationService } from '../services/loading-discharging-transformation.service';

/**
 * Component class for arrival condition block
 *
 * @export
 * @class ArrivalConditionComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-arrival-condition',
  templateUrl: './arrival-condition.component.html',
  styleUrls: ['./arrival-condition.component.scss']
})


export class ArrivalConditionComponent implements OnInit {

  @Input() get currentQuantitySelectedUnit(): QUANTITY_UNIT {
    return this._currentQuantitySelectedUnit;
  }

  @Input() cargos: any;
  @Input() loadingInfoId: number;
  @Input() vesselId: number;
  @Input() portRotationId: number;

  @Input() get loadingPlanData(): any {
    return this._loadingPlanData;
  };

  set loadingPlanData(value: any) {
    this._loadingPlanData = value ? JSON.parse(JSON.stringify(value)) : {};
    this.formatData();
    this.convertQuantityToSelectedUnit();
  }
  set currentQuantitySelectedUnit(value: QUANTITY_UNIT) {
    this.prevQuantitySelectedUnit = this.currentQuantitySelectedUnit ?? AppConfigurationService.settings.baseUnit;
    this._currentQuantitySelectedUnit = value;
    this.cargoTankOptions.weightUnit = value;
    this.ballastTankOptions.weightUnit = value;
    this.formatData();
    this.convertQuantityToSelectedUnit();
  }
  private _currentQuantitySelectedUnit: QUANTITY_UNIT;
  private _loadingPlanData: any;
  display = false;
  arrivalCargoTanks: IShipCargoTank[][] = [];
  cargoTankQuantity: ICargoQuantities[];
  cargoTankOptions: ITankOptions = {
    showFillingPercentage: true,
    showTooltip: true,
    isSelectable: false,
    ullageField: "ullage",
    ullageUnit: AppConfigurationService.settings?.ullageUnit,
    densityField: "api",
    weightField: "plannedWeight",
    commodityNameField: "abbreviation",
    fillingPercentageField: 'percentageFilled',
    showWeight: true
  }
  cargoConditions: ICargoConditions[];
  cargoQuantities: ICargoQuantities[];

  ballastTankQuantity: any = [];
  rearBallastTanks: IShipBallastTank[][];
  frontBallastTanks: IShipBallastTank[][];
  centerBallastTanks: IShipBallastTank[][];
  ballastTankOptions: ITankOptions = { showFillingPercentage: true, showTooltip: true, isSelectable: false, ullageField: 'sounding', ullageUnit: AppConfigurationService.settings?.ullageUnit, densityField: 'sg', weightField: 'plannedWeight', weightUnit: AppConfigurationService.settings.baseUnit, showWeight: true };
  prevQuantitySelectedUnit: QUANTITY_UNIT;

  readonly tankType = TANKTYPE;
  selectedTab = TANKTYPE.CARGO;

  readonly ULLAGE_STATUS = ULLAGE_STATUS;
  readonly ULLAGE_STATUS_TEXT = ULLAGE_STATUS_TEXT;
  readonly ULLAGE_STATUS_VALUE = ULLAGE_STATUS_VALUE;

  constructor(
    private arrivalConditionTransformationService: ArrivalConditionTransformationService,
    private loadingDischargingTransformationService: LoadingDischargingTransformationService
  ) { }

  ngOnInit(): void {
    this.initSubscriptions();
    this.getShipLandingTanks();
  }

  initSubscriptions(){
    this.loadingDischargingTransformationService.setUllageArrivalBtnStatus$.subscribe((value)=>{
      if(value){
        this.loadingPlanData.loadingInformation.loadingPlanArrStatusId = value;
      }
    });
  }

  /**
  * Format loading plan data
  *
  * @memberof ArrivalConditionComponent
  */
  formatData() {
    this.prevQuantitySelectedUnit = AppConfigurationService.settings.baseUnit;
    this.cargoQuantities = [];
    this.cargoConditions = [];
    this.loadingPlanData?.loadingInformation?.cargoVesselTankDetails?.loadableQuantityCargoDetails?.map(item => {
      let actualWeight = 0, plannedWeight = 0;
      this.loadingPlanData?.planStowageDetails?.map(stowage => {
        if (stowage.conditionType === 1 && item.cargoNominationId === stowage.cargoNominationId) {
          if (stowage.valueType === 1) {
            actualWeight += Number(stowage.quantityMT);
          }
          if (stowage.valueType === 2) {
            plannedWeight += Number(stowage.quantityMT);
            this.cargoQuantities.push(this.arrivalConditionTransformationService.formatCargoQuantities(stowage, item));
          }
        }
      });
      const conditionObj = this.arrivalConditionTransformationService.formatCargoCondition(item);
      conditionObj.actualWeight = actualWeight;
      conditionObj.plannedWeight = plannedWeight;
      this.cargoConditions.push(conditionObj);
    });
    this.cargoTankQuantity = [];

    this.loadingPlanData?.cargoTanks?.map(item => {
      item.map(tank => {
        let actualQty = 0, planedQty = 0;
        const data: any = {};
        this.loadingPlanData?.planStowageDetails?.map(stowage => {
          if (tank.id === stowage.tankId) {
            if (stowage.conditionType === 1 && stowage.valueType === 1) {
              actualQty += Number(stowage.quantityMT);
            }
            if (stowage.conditionType === 1 && stowage.valueType === 2) {
              planedQty += Number(stowage.quantityMT);
            }
            data.cargoNominationId = stowage.cargoNominationId;
            data.api = stowage.api;
            data.temperature = stowage.temperature;
            data.ullage = stowage.ullage;
          }
        });
        data.plannedWeight = planedQty;
        data.actualWeight = actualQty;
        data.tankId = tank.id;
        this.loadingPlanData?.loadingInformation?.cargoVesselTankDetails?.loadableQuantityCargoDetails?.map(el => {
          if (el.cargoNominationId === data.cargoNominationId) {
            data.colorCode = el.colorCode;
            data.abbreviation = el.cargoAbbreviation;
          }
        });
        this.cargoTankQuantity.push(data);
      });
    });
    this.arrivalCargoTanks = this.arrivalConditionTransformationService.formatCargoTanks(this.loadingPlanData?.cargoTanks, this.cargoTankQuantity, this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit);
  }

  /**
 * Close ullage update popup
 *
 * @memberof ArrivalConditionComponent
 */
  closePopUp(evnet) {
    this.display = false;
  }

  /**
* initialise tanks data
*
* @memberof ArrivalConditionComponent
*/
  async getShipLandingTanks() {
    this.prevQuantitySelectedUnit = AppConfigurationService.settings.baseUnit;
    this.convertQuantityToSelectedUnit();
  }

  /**
  * Convert quantity to selected uint
  *
  * @memberof ArrivalConditionComponent
  */
  convertQuantityToSelectedUnit() {
    this.ballastTankQuantity = [];
    this.loadingPlanData?.ballastRearTanks?.map(item => {
      item.map(tank => {
        let actualQty = 0, planedQty = 0;
        const data: any = {};
        let colorCode = null;
        this.loadingPlanData?.planBallastDetails?.map(ballast => {
          if (tank.id === ballast.tankId) {
            colorCode = ballast.colorCode;
            if (ballast.conditionType === 1 && ballast.valueType === 1) {
              actualQty += Number(ballast.quantityMT);
            }
            if (ballast.conditionType === 1 && ballast.valueType === 2) {
              planedQty += Number(ballast.quantityMT);
            }
            data.sg = ballast.sg;
            data.sounding = ballast.sounding;
          }
        });
        data.plannedWeight = planedQty;
        data.actualWeight = actualQty;
        data.tankId = tank.id;
        data.colorCode = colorCode;
        this.ballastTankQuantity.push(data);
      });
    });
    this.loadingPlanData?.ballastCenterTanks?.map(item => {
      item.map(tank => {
        let actualQty = 0, planedQty = 0;
        const data: any = {};
        let colorCode = null;
        this.loadingPlanData?.planBallastDetails?.map(ballast => {
          if (tank.id === ballast.tankId) {
            colorCode = ballast.colorCode;
            if (ballast.conditionType === 1 && ballast.valueType === 1) {
              actualQty += Number(ballast.quantityMT);
            }
            if (ballast.conditionType === 1 && ballast.valueType === 2) {
              planedQty += Number(ballast.quantityMT);
            }
            data.sg = ballast.sg;
            data.sounding = ballast.sounding;
          }
        });
        data.plannedWeight = planedQty;
        data.actualWeight = actualQty;
        data.tankId = tank.id;
        data.colorCode = colorCode;
        this.ballastTankQuantity.push(data);
      });
    });
    this.loadingPlanData?.ballastFrontTanks?.map(item => {
      item.map(tank => {
        let actualQty = 0, planedQty = 0;
        const data: any = {};
        let colorCode = null;
        this.loadingPlanData?.planBallastDetails?.map(ballast => {
          if (tank.id === ballast.tankId) {
            colorCode = ballast.colorCode;
            if (ballast.conditionType === 1 && ballast.valueType === 1) {
              actualQty += Number(ballast.quantityMT);
            }
            if (ballast.conditionType === 1 && ballast.valueType === 2) {
              planedQty += Number(ballast.quantityMT);
            }
            data.sg = ballast.sg;
            data.sounding = ballast.sounding;
          }
        });
        data.plannedWeight = planedQty;
        data.actualWeight = actualQty;
        data.tankId = tank.id;
        data.colorCode = colorCode;
        this.ballastTankQuantity.push(data);
      });
    });
    this.rearBallastTanks = this.arrivalConditionTransformationService.formatBallastTanks(this.loadingPlanData?.ballastRearTanks, this.ballastTankQuantity, this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit);
    this.centerBallastTanks = this.arrivalConditionTransformationService.formatBallastTanks(this.loadingPlanData?.ballastCenterTanks, this.ballastTankQuantity, this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit);
    this.frontBallastTanks = this.arrivalConditionTransformationService.formatBallastTanks(this.loadingPlanData?.ballastFrontTanks, this.ballastTankQuantity, this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit);
  }

  /**
   * Handler for tab click
   *
   * @param {TANKTYPE} selectedTab
   * @memberof ArrivalConditionComponent
   */
  onTabClick(selectedTab: TANKTYPE) {
    this.selectedTab = selectedTab;
  }

  /**
   * Handler for showing error pop up
   *
   * @memberof ArrivalConditionComponent
   */
  showError() {
    this.loadingDischargingTransformationService.showUllageError({ value: true, status: 1});
  }

}
