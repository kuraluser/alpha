import { Component, OnInit, Input } from '@angular/core';
import { DepartureConditionTransformationService } from './departure-condition-transformation.service';
import { ITankOptions, IVoyagePortDetails, TANKTYPE, ICargo, OPERATIONS, ICargoQuantities, IShipCargoTank, IBallastQuantities, IShipBallastTank, IShipBunkerTank} from '../../core/models/common.model';
import { QUANTITY_UNIT, ICargoConditions } from '../../../shared/models/common.model';
import { QuantityPipe } from '../../../shared/pipes/quantity/quantity.pipe';
import { DecimalPipe } from '@angular/common';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { ULLAGE_STATUS } from '../models/loading-discharging.model';
import { LoadingDischargingTransformationService } from '../services/loading-discharging-transformation.service';

/**
 * Component class for departure condition block
 *
 * @export
 * @class DepartureConditionComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-departure-condition',
  templateUrl: './departure-condition.component.html',
  styleUrls: ['./departure-condition.component.scss']
})
export class DepartureConditionComponent implements OnInit {

  @Input() get loadingPlanData(): any {
    return this._loadingPlanData;
  };

  set loadingPlanData(value: any) {
    this._loadingPlanData = value ? JSON.parse(JSON.stringify(value)) : {};
    this.formatData();
    this.convertQuantityToSelectedUnit();
  }
  @Input() cargos: any;
  @Input() loadingInfoId: number;
  @Input() vesselId: number;
  @Input() portRotationId: number;
  @Input() get currentQuantitySelectedUnit(): QUANTITY_UNIT {
    return this._currentQuantitySelectedUnit;
  }

  set currentQuantitySelectedUnit(value: QUANTITY_UNIT) {
    this.prevQuantitySelectedUnit = this.currentQuantitySelectedUnit ?? AppConfigurationService.settings.baseUnit;
    this._currentQuantitySelectedUnit = value;
    this.formatData();
    this.convertQuantityToSelectedUnit();
  }
  private _currentQuantitySelectedUnit: QUANTITY_UNIT;
  private _loadingPlanData: any;

  display = false;
  departureCargoTanks: IShipCargoTank[][] = [];
  departureCargoTankQuantity: ICargoQuantities[];
  cargoTankOptions: ITankOptions;
  departureDetailsColumns: any = [];
  cargoConditions: ICargoConditions[] = [];
  cargoQuantities: ICargoQuantities[] = [];
  
  ballastTankQuantity: any = [];
  rearBallastTanks: IShipBallastTank[][];
  frontBallastTanks: IShipBallastTank[][];
  centerBallastTanks: IShipBallastTank[][];
  ballastTankOptions: ITankOptions = { showFillingPercentage: true, showTooltip: true, isSelectable: false, ullageField: 'correctedUllage', ullageUnit: AppConfigurationService.settings?.ullageUnit, densityField: 'sg', weightField: 'actualWeight', weightUnit: AppConfigurationService.settings.baseUnit };
  prevQuantitySelectedUnit: QUANTITY_UNIT;


  readonly tankType = TANKTYPE;
  selectedTab = TANKTYPE.CARGO;
  readonly OPERATIONS = OPERATIONS;
  readonly ULLAGE_STATUS = ULLAGE_STATUS;

  constructor(
    private departureConditionTransformationService: DepartureConditionTransformationService,
    private loadingDischargingTransformationService: LoadingDischargingTransformationService,
    private quantityPipe: QuantityPipe,
    private _decimalPipe: DecimalPipe
  ) { }

  ngOnInit(): void {

    this.cargoTankOptions = {
      "showFillingPercentage": true,
      "showTooltip": true,
      "isSelectable": false,
      "ullageField": "correctedUllage",
      "ullageUnit": AppConfigurationService.settings?.ullageUnit,
      "densityField": "api",
      "weightField": "actualWeight",
      "commodityNameField": "abbreviation"
    };
        
    this.departureDetailsColumns = this.departureConditionTransformationService.departureDetailsColumns();
    this.getShipLandingTanks();
  }

  /**
* Method to format loaded data
*
* @memberof DepartureConditionComponent
*/
  formatData() {
    this.prevQuantitySelectedUnit = AppConfigurationService.settings.baseUnit;
    this.cargoQuantities = [];
    this.cargoConditions = [];
    this.loadingPlanData?.loadingInformation?.cargoVesselTankDetails?.loadableQuantityCargoDetails?.map(item => {
      let actualWeight = 0, plannedWeight = 0;
      this.loadingPlanData?.planStowageDetails?.map(stowage => {
        if (stowage.conditionType === 2 && stowage.cargoNominationId === stowage.cargoNominationId) {
          if (stowage.valueType === 1) {
            actualWeight += Number(stowage.quantityMT);
          }
          if (stowage.valueType === 2) {
            plannedWeight += Number(stowage.quantityMT);
            this.cargoQuantities.push(this.departureConditionTransformationService.formatCargoQuantities(stowage, item));
          }
        }
      });
      const conditionObj = this.departureConditionTransformationService.formatCargoCondition(item);
      conditionObj.actualWeight = actualWeight;
      conditionObj.plannedWeight = plannedWeight;
      this.cargoConditions.push(conditionObj);
    });
    this.departureCargoTankQuantity = [];
  
    this.loadingPlanData?.cargoTanks?.map(item => {
      item.map(tank => {
        let actualQty = 0, planedQty = 0;
        const data: any = {};
        this.loadingPlanData?.planStowageDetails?.map(stowage => {
          if (tank.id === stowage.tankId) {
            if (stowage.conditionType === 2 && stowage.valueType === 1) {
              actualQty += Number(stowage.quantityMT);
            }
            if (stowage.conditionType === 2 && stowage.valueType === 2) {
              planedQty += Number(stowage.quantityMT);
            }
            data.cargoNominationId = stowage.cargoNominationId;
            data.api = stowage.api;
            data.temperature = stowage.temperature;
          }
        });
        data.plannedWeight = planedQty;
        data.actualWeight = actualQty;
        data.tankId = tank.id;
        this.loadingPlanData?.loadingInformation?.cargoVesselTankDetails?.loadableQuantityCargoDetails?.map(el=>{
          if(el.cargoNominationId === data.cargoNominationId){
            data.colorCode = el.colorCode;
          }
        });
        this.departureCargoTankQuantity.push(data);
      });
    });
    this.departureCargoTanks = this.departureConditionTransformationService.formatCargoTanks(this.loadingPlanData?.cargoTanks, this.departureCargoTankQuantity, this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit);
  }


  /**
* Method to find out cargo
*
* @memberof LoadingDischargingCargoDetailsComponent
*/
  findCargo(loadableQuantityCargoDetails): string {
    let cargoDetail;
    this.cargos?.map((cargo) => {
      if (cargo?.id === loadableQuantityCargoDetails?.cargoId) {
        cargoDetail = cargo;
      }
    })
    return cargoDetail?.name;
  }

  /**
* initialise tanks data
*
* @memberof DepartureConditionComponent
*/
  async getShipLandingTanks() {
    this.prevQuantitySelectedUnit = AppConfigurationService.settings.baseUnit;
    this.convertQuantityToSelectedUnit();
  }

  /**
  * Convert quantity to selected uint
  *
  * @memberof DepartureConditionComponent
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
            if (ballast.conditionType === 2 && ballast.valueType === 1) {
              actualQty += Number(ballast.quantityMT);
            }
            if (ballast.conditionType === 2 && ballast.valueType === 2) {
              planedQty += Number(ballast.quantityMT);
            }
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
            if (ballast.conditionType === 2 && ballast.valueType === 1) {
              actualQty += Number(ballast.quantityMT);
            }
            if (ballast.conditionType === 2 && ballast.valueType === 2) {
              planedQty += Number(ballast.quantityMT);
            }
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
            if (ballast.conditionType === 2 && ballast.valueType === 1) {
              actualQty += Number(ballast.quantityMT);
            }
            if (ballast.conditionType === 2 && ballast.valueType === 2) {
              planedQty += Number(ballast.quantityMT);
            }
          }
        });
        data.plannedWeight = planedQty;
        data.actualWeight = actualQty;
        data.tankId = tank.id;
        data.colorCode = colorCode;
        this.ballastTankQuantity.push(data);
      });
    });
    this.rearBallastTanks = this.departureConditionTransformationService.formatBallastTanks(this.loadingPlanData?.ballastRearTanks, this.ballastTankQuantity, this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit);
    this.centerBallastTanks = this.departureConditionTransformationService.formatBallastTanks(this.loadingPlanData?.ballastCenterTanks, this.ballastTankQuantity, this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit);
    this.frontBallastTanks = this.departureConditionTransformationService.formatBallastTanks(this.loadingPlanData?.ballastFrontTanks, this.ballastTankQuantity, this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit);
  }

  /**
   * Handler for tab click
   *
   * @param {TANKTYPE} selectedTab
   * @memberof DepartureConditionComponent
   */
  onTabClick(selectedTab: TANKTYPE) {
    this.selectedTab = selectedTab;
  }

  /**
   * Handler for pop up close
   *
   * @memberof DepartureConditionComponent
   */
  closePopUp(evnet) {
    this.display = false;
  }

  /**
   * Handler for showing error pop up
   *
   * @memberof DepartureConditionComponent
   */
  showError(){
    this.loadingDischargingTransformationService.showUllageError(true);
  }

}
