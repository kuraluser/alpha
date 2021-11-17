import { Component, OnInit, Input, OnDestroy } from '@angular/core';
import { DepartureConditionTransformationService } from './departure-condition-transformation.service';
import { ITankOptions, IVoyagePortDetails, TANKTYPE, ICargo, OPERATIONS, ICargoQuantities, IShipCargoTank, IBallastQuantities, IShipBallastTank, IShipBunkerTank } from '../../core/models/common.model';
import { QUANTITY_UNIT, ICargoConditions } from '../../../shared/models/common.model';
import { QuantityPipe } from '../../../shared/pipes/quantity/quantity.pipe';
import { DecimalPipe } from '@angular/common';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { ULLAGE_STATUS, ULLAGE_STATUS_TEXT, ULLAGE_STATUS_VALUE } from '../models/loading-discharging.model';
import { LoadingDischargingTransformationService } from '../services/loading-discharging-transformation.service';
import { IPermission } from '../../../shared/models/user-profile.model';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

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
export class DepartureConditionComponent implements OnInit, OnDestroy {

  @Input() get loadingDischargingPlanData(): any {
    return this._loadingDischargingPlanData;
  };

  set loadingDischargingPlanData(value: any) {
    this._loadingDischargingPlanData = value ? JSON.parse(JSON.stringify(value)) : {};
    this.formatData();
    this.convertQuantityToSelectedUnit();
  }
  @Input() cargos: any;
  @Input() loadingDischargingInfoId: number;
  @Input() vesselId: number;
  @Input() portRotationId: number;
  @Input() operation: OPERATIONS;
  @Input() permission: IPermission;
  @Input() get currentQuantitySelectedUnit(): QUANTITY_UNIT {
    return this._currentQuantitySelectedUnit;
  }

  set currentQuantitySelectedUnit(value: QUANTITY_UNIT) {
    this.prevQuantitySelectedUnit = this.currentQuantitySelectedUnit ?? AppConfigurationService.settings.baseUnit;
    this._currentQuantitySelectedUnit = value;
    this.cargoTankOptions.weightUnit = value;
    this.formatData();
    this.convertQuantityToSelectedUnit();
  }
  private _currentQuantitySelectedUnit: QUANTITY_UNIT;
  private _loadingDischargingPlanData: any;

  display = false;
  departureCargoTanks: IShipCargoTank[][] = [];
  departureCargoTankQuantity: ICargoQuantities[];
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
  };
  departureDetailsColumns: any = [];
  cargoConditions: ICargoConditions[] = [];
  cargoQuantities: ICargoQuantities[] = [];

  ballastTankQuantity: any = [];
  rearBallastTanks: IShipBallastTank[][];
  frontBallastTanks: IShipBallastTank[][];
  centerBallastTanks: IShipBallastTank[][];
  ballastTankOptions: ITankOptions = { showFillingPercentage: true, showTooltip: true, isSelectable: false, ullageField: 'sounding', ullageUnit: AppConfigurationService.settings?.ullageUnit, densityField: 'sg', weightField: 'plannedWeight', weightUnit: AppConfigurationService.settings.baseUnit, showWeight: true };
  prevQuantitySelectedUnit: QUANTITY_UNIT;
  loadingDischargingPlanInfo: any;
  private ngUnsubscribe: Subject<any> = new Subject();


  readonly tankType = TANKTYPE;
  selectedTab = TANKTYPE.CARGO;
  readonly OPERATIONS = OPERATIONS;
  readonly ULLAGE_STATUS = ULLAGE_STATUS;
  readonly ULLAGE_STATUS_TEXT = ULLAGE_STATUS_TEXT;
  readonly ULLAGE_STATUS_VALUE = ULLAGE_STATUS_VALUE;

  constructor(
    private departureConditionTransformationService: DepartureConditionTransformationService,
    private loadingDischargingTransformationService: LoadingDischargingTransformationService,
    private quantityPipe: QuantityPipe,
    private _decimalPipe: DecimalPipe
  ) { }

  ngOnInit(): void {
    this.initSubscriptions();
    this.departureDetailsColumns = this.departureConditionTransformationService.departureDetailsColumns();
    this.getShipLandingTanks();
  }

  ngOnDestroy() {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  initSubscriptions() {
    this.loadingDischargingTransformationService.setUllageDepartureBtnStatus$.pipe(takeUntil(this.ngUnsubscribe)).subscribe((value) => {
      if (value && value.portRotationId === this.portRotationId) {
        this.loadingDischargingPlanData.loadingInformation.loadingPlanDepStatusId = value.status;
      }
    });
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
    const commingleArray = [];
    this.loadingDischargingPlanData?.planCommingleDetails?.map(com => {
      if (com.conditionType === 2) {
        commingleArray.push(
          {
            abbreviation: com.abbreviation,
            actualWeight: 0,
            plannedWeight: 0,
            colorCode: com.colorCode,
            tankId: com.tankId,
            cargoId: 0
          });
      }
    });
    this.loadingDischargingPlanInfo = this.loadingDischargingPlanData?.loadingInformation ? this.loadingDischargingPlanData?.loadingInformation : this.loadingDischargingPlanData?.dischargingInformation
    this.loadingDischargingPlanData?.currentPortCargos.map(item => {
      let actualWeight = 0, plannedWeight = 0;
      this.loadingDischargingPlanData?.planStowageDetails?.map(stowage => {
        const currentPorCargoNominationId = this.loadingDischargingPlanData?.loadingInformation ? item.cargoNominationId : item.dischargeCargoNominationId;
        if (stowage.conditionType === 2 && currentPorCargoNominationId === stowage.cargoNominationId) {
          if (stowage.valueType === 1) {
            if (stowage.isCommingleCargo) {
              commingleArray?.map(com => {
                if (com.tankId === stowage.tankId) {
                  com.actualWeight += Number(stowage.quantityMT);
                }
              });
            } else {
              actualWeight += Number(stowage.quantityMT);
            }
          }
          if (stowage.valueType === 2) {
            if (stowage.isCommingleCargo) {
              commingleArray?.map(com => {
                if (com.tankId === stowage.tankId) {
                  com.plannedWeight += Number(stowage.quantityMT);
                }
              });
              const commingleData = this.loadingDischargingPlanData?.planCommingleDetails?.filter(commingle => commingle.tankId === stowage.tankId);
              if (commingleData?.length) {
                this.cargoQuantities.push(this.departureConditionTransformationService.formatCargoQuantities(stowage, item, true, commingleData[0]));
              }
            } else {
              plannedWeight += Number(stowage.quantityMT);
              this.cargoQuantities.push(this.departureConditionTransformationService.formatCargoQuantities(stowage, item));
            }
          }
        }
      });
      const conditionObj = this.departureConditionTransformationService.formatCargoCondition(item);
      conditionObj.actualWeight = actualWeight;
      conditionObj.plannedWeight = plannedWeight;
      this.cargoConditions.push(conditionObj);
    });
    commingleArray?.map(com => {
      this.loadingDischargingPlanData?.planStowageDetails?.map(stowage => {
        if (stowage.isCommingleCargo && stowage.conditionType === 2) {
          if (com.tankId === stowage.tankId) {
            if (stowage.valueType === 1) {
              com.actualWeight += Number(stowage.quantityMT);
            }
            if (stowage.valueType === 2) {
              com.plannedWeight += Number(stowage.quantityMT);
              const commingleData = this.loadingDischargingPlanData?.planCommingleDetails?.filter(commingle => commingle.tankId === stowage.tankId);
              if (commingleData?.length) {
                this.cargoQuantities.push(this.departureConditionTransformationService.formatCargoQuantities(stowage, null, true, commingleData[0]));
              }
            }
          }
        }
      });
    });
    this.cargoConditions = [...commingleArray, ...this.cargoConditions];
    this.departureCargoTankQuantity = [];

    this.loadingDischargingPlanData?.cargoTanks?.map(item => {
      item.map(tank => {
        let actualQty = 0, planedQty = 0;
        const data: any = {};
        this.loadingDischargingPlanData?.planStowageDetails?.map(stowage => {
          if (tank.id === stowage.tankId) {
            if (stowage.conditionType === 2 && stowage.valueType === 1) {
              actualQty += Number(stowage.quantityMT);
            }
            if (stowage.conditionType === 2 && stowage.valueType === 2) {
              planedQty += Number(stowage.quantityMT);
              data.api = stowage.api;
              data.temperature = stowage.temperature;
              data.ullage = stowage.ullage;
              data.colorCode = stowage.colorCode;
              if (stowage.isCommingleCargo) {
                const commingleData = this.loadingDischargingPlanData?.planCommingleDetails?.filter(commingle => commingle.tankId === stowage.tankId);
                if (commingleData?.length) {
                  data.abbreviation = commingleData[0].abbreviation;
                }
              } else {
                data.abbreviation = stowage.abbreviation;
              }
              data.isCommingleCargo = stowage.isCommingleCargo;
            }
            data.cargoNominationId = stowage.cargoNominationId;
          }
        });
        data.plannedWeight = planedQty;
        data.actualWeight = actualQty;
        data.tankId = tank.id;
        this.departureCargoTankQuantity.push(data);
      });
    });
    this.departureCargoTanks = this.departureConditionTransformationService.formatCargoTanks(this.loadingDischargingPlanData?.cargoTanks, this.departureCargoTankQuantity, this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit);
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
    this.loadingDischargingPlanData?.ballastRearTanks?.map(item => {
      item.map(tank => {
        let actualQty = 0, planedQty = 0;
        const data: any = {};
        let colorCode = null;
        this.loadingDischargingPlanData?.planBallastDetails?.map(ballast => {
          if (tank.id === ballast.tankId) {
            if (ballast.conditionType === 2 && ballast.valueType === 1) {
              actualQty += Number(ballast.quantityMT);
            }
            if (ballast.conditionType === 2 && ballast.valueType === 2) {
              planedQty += Number(ballast.quantityMT);
              data.sg = ballast.sg;
              data.sounding = ballast.sounding;
              colorCode = ballast.colorCode;
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
    this.loadingDischargingPlanData?.ballastCenterTanks?.map(item => {
      item.map(tank => {
        let actualQty = 0, planedQty = 0;
        const data: any = {};
        let colorCode = null;
        this.loadingDischargingPlanData?.planBallastDetails?.map(ballast => {
          if (tank.id === ballast.tankId) {
            if (ballast.conditionType === 2 && ballast.valueType === 1) {
              actualQty += Number(ballast.quantityMT);
            }
            if (ballast.conditionType === 2 && ballast.valueType === 2) {
              planedQty += Number(ballast.quantityMT);
              data.sg = ballast.sg;
              data.sounding = ballast.sounding;
              colorCode = ballast.colorCode;
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
    this.loadingDischargingPlanData?.ballastFrontTanks?.map(item => {
      item.map(tank => {
        let actualQty = 0, planedQty = 0;
        const data: any = {};
        let colorCode = null;
        this.loadingDischargingPlanData?.planBallastDetails?.map(ballast => {
          if (tank.id === ballast.tankId) {
            if (ballast.conditionType === 2 && ballast.valueType === 1) {
              actualQty += Number(ballast.quantityMT);
            }
            if (ballast.conditionType === 2 && ballast.valueType === 2) {
              planedQty += Number(ballast.quantityMT);
              colorCode = ballast.colorCode;
              data.sg = ballast.sg;
              data.sounding = ballast.sounding;
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
    this.rearBallastTanks = this.departureConditionTransformationService.formatBallastTanks(this.loadingDischargingPlanData?.ballastRearTanks, this.ballastTankQuantity, this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit);
    this.centerBallastTanks = this.departureConditionTransformationService.formatBallastTanks(this.loadingDischargingPlanData?.ballastCenterTanks, this.ballastTankQuantity, this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit);
    this.frontBallastTanks = this.departureConditionTransformationService.formatBallastTanks(this.loadingDischargingPlanData?.ballastFrontTanks, this.ballastTankQuantity, this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit);
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
  showError() {
    this.loadingDischargingTransformationService.showUllageError({ value: true, status: 2 });
  }

}
