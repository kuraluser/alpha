import { Component, OnInit, Input, Output, EventEmitter, OnDestroy, ViewChild } from '@angular/core';
import { AbstractControl, FormArray, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators, FormBuilder } from '@angular/forms';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { DATATABLE_EDITMODE, DATATABLE_FIELD_TYPE, DATATABLE_SELECTIONMODE, IDataTableColumn, DATATABLE_FILTER_TYPE, DATATABLE_FILTER_MATCHMODE } from '../../../shared/components/datatable/datatable.model';
import { ICargoQuantities, IShipCargoTank, ITankOptions, IVoyagePortDetails, TANKTYPE, IBallastQuantities, IShipBallastTank, IShipBunkerTank, OPERATIONS } from '../../core/models/common.model';
import { UllageUpdatePopupTransformationService } from './ullage-update-popup-transformation.service';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { QUANTITY_UNIT } from '../../../shared/models/common.model';
import { ICargoDetail, ICargoDetailValueObject, ITankDetailsValueObject, ULLAGE_STATUS, IUllageSaveDetails, ULLAGE_STATUS_TEXT, ULLAGE_STATUS_VALUE } from '../models/loading-discharging.model';
import { numberValidator } from '../../core/directives/number-validator.directive';
import { IBlFigureTotal } from '../models/operations.model';
import { QuantityPipe } from '../../../shared/pipes/quantity/quantity.pipe';
import { DecimalPipe } from '@angular/common';
import { UllageUpdateApiService } from './ullage-update-api.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';
import { tankCapacityValidator } from './../../core/directives/tankCapacityValidator.directive';
import { LoadingDischargingTransformationService } from '../services/loading-discharging-transformation.service';
import { ConfirmationService } from 'primeng/api';
import { apiTemperatureMinValidator } from './../../core/directives/api-temperature-min-validator.directive';
import { apiTemperatureBlfigValidator } from './../../core/directives/api-temperature-blfigure-validator.directive';
import { blReferenceValidator } from './../../core/directives/bl-reference-validator.directive';;
import { IPermission } from '../../../shared/models/user-profile.model';

/**
 * Component class for ullage update
 *
 * @export
 * @class UllageUpdatePopupComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-ullage-update-popup',
  templateUrl: './ullage-update-popup.component.html',
  styleUrls: ['./ullage-update-popup.component.scss']
})
export class UllageUpdatePopupComponent implements OnInit, OnDestroy {

  @Input() infoId: number;
  @Input() status: ULLAGE_STATUS;
  @Input() vesselId: number;
  @Input() patternId: number;
  @Input() portRotationId: number;
  @Input() operation: OPERATIONS;
  @Input() permission: IPermission;
  @Input() ullageStatus: number;

  @ViewChild('fileUpload') file;

  private ngUnsubscribe: Subject<any> = new Subject();

  portId: number;

  allowedFiles = [
    'xlsx',
    'xls'
  ];

  get selectedTankId(): number {
    return this.selectedTank?.tankId;
  }

  set selectedTankId(tankId: number) {
    if (this.selectedTab === this.tankType.CARGO) {
      this.selectedTank = this.cargoQuantities?.find(tank => tank?.tankId === tankId);
      this.cargoTanks?.map(el => {
        el.map(tank => {
          if (tank.id === tankId) {
            this.percentageFilled = tank.percentageFilled + '%';
          }
        })
      })
    }
    if (this.selectedTab === this.tankType.BALLAST) {
      this.selectedTank = this.ballastQuantities?.find(tank => tank?.tankId === tankId);
    }
    if (this.selectedTab === this.tankType.BUNKER) {
      this.percentageFilled = null;
      this.selectedTank = this.bunkerTanksList?.find(tank => tank?.tankId === tankId);
      this.bunkerTanks?.map(el => {
        el?.map(tank => {
          if (tank.id === tankId) {
            this.percentageFilled = tank.percentageFilled;
          }
        });
      })
      if (!this.percentageFilled) {
        this.rearBunkerTanks?.map(el => {
          el?.map(tank => {
            if (tank.id === tankId) {
              this.percentageFilled = tank.percentageFilled;
            }
          });
        })
      }
    }
  }


  currentQuantitySelectedUnit: QUANTITY_UNIT = null;
  readonly QUANTITY_UNIT = QUANTITY_UNIT;
  readonly ULLAGE_STATUS_VALUE = ULLAGE_STATUS_VALUE;

  blFigure: any = [];
  cargoQuantityList: any = [];
  bunkerTanksList: any = [];
  ullageResponseData: any;
  ullageResponseDataCopy: any;
  selectedTank: any;
  percentageFilled: string;
  selectedPortName: string;

  showAs = {
    id: 1, label: 'Actual'
  };
  showAsOptions = [
    {
      id: 1, label: 'Actual'
    },
    {
      id: 2, label: 'As Per B/L'
    }
  ];

  readonly tankType = TANKTYPE;
  readonly ULLAGE_STATUS = ULLAGE_STATUS;
  readonly OPERATIONS = OPERATIONS;

  cargoTankOptions: ITankOptions = { showFillingPercentage: true, showTooltip: true, isSelectable: false, ullageField: 'ullage', ullageUnit: AppConfigurationService.settings?.ullageUnit, densityField: 'api', weightField: 'actualWeight', commodityNameField: 'abbreviation', weightUnit: AppConfigurationService.settings.baseUnit, showWeight: true };
  ballastTankOptions: ITankOptions = { showFillingPercentage: true, showTooltip: true, isSelectable: false, soundingField: 'sounding', ullageUnit: AppConfigurationService.settings?.ullageUnit, densityField: 'sg', weightField: 'actualWeight', weightUnit: AppConfigurationService.settings.baseUnit, showSounding: true, showWeight: true };
  ohqTankOptions: ITankOptions = { showFillingPercentage: true, showTooltip: true, densityField: 'density', weightField: 'quantity', weightUnit: AppConfigurationService.settings.baseUnit };

  cargoTanks: IShipCargoTank[][];
  rearBallastTanks: IShipBallastTank[][];
  frontBallastTanks: IShipBallastTank[][];
  centerBallastTanks: IShipBallastTank[][];
  bunkerTanks: IShipBunkerTank[][];
  rearBunkerTanks: IShipBunkerTank[][];
  cargoColumns: IDataTableColumn[];
  ballastColumns: IDataTableColumn[];
  bunkerColumns: IDataTableColumn[];
  blFigureColumns: IDataTableColumn[];
  cargoQuantities: ITankDetailsValueObject[];
  gradeDropdown: any[];

  ballastQuantities: ITankDetailsValueObject[];
  fuelTypes: any = [];
  prevQuantitySelectedUnit: QUANTITY_UNIT;
  selectedCargo: any;
  billOfLaddingRemovedList: any = [];

  selectedTab = TANKTYPE.CARGO;
  readonly fieldType = DATATABLE_FIELD_TYPE;
  readonly selectionMode = DATATABLE_SELECTIONMODE.SINGLE;
  tableForm: FormGroup;
  cargoTankForm: FormGroup;
  ballastTankForm: FormGroup;
  bunkerTankForm: FormGroup;
  public editMode: DATATABLE_EDITMODE = DATATABLE_EDITMODE.CELL;
  public blFigureTotal: IBlFigureTotal;
  isDischargeStarted: boolean;

  statu: boolean;

  display: boolean;
  @Output() closePopup = new EventEmitter();
  constructor(
    private ullageUpdatePopupTransformationService: UllageUpdatePopupTransformationService,
    private loadingDischargingTransformationService: LoadingDischargingTransformationService,
    private fb: FormBuilder,
    private quantityPipe: QuantityPipe,
    private decimalPipe: DecimalPipe,
    private ullageUpdateApiService: UllageUpdateApiService,
    private ngxSpinnerService: NgxSpinnerService,
    private messageService: MessageService,
    private translateService: TranslateService,
    private confirmationService: ConfirmationService
  ) { }

  async ngOnInit(): Promise<void> {
    this.portId = Number(localStorage.getItem('selectedPortId'));
    this.selectedPortName = localStorage.getItem('selectedPortName');
    this.ngxSpinnerService.show();
    try {
      const status = this.status === ULLAGE_STATUS.ARRIVAL ? 'ARR' : 'DEP';
      let data;
      if (this.operation === OPERATIONS.LOADING) {
        data = await this.ullageUpdateApiService.getUllageDetails(this.vesselId, this.patternId, this.portRotationId, status).toPromise();
      } else {
        data = await this.ullageUpdateApiService.getDischargePlanUllageDetails(this.vesselId, this.patternId, this.portRotationId, status).toPromise();
      }

      data.portPlanStowageDetails = this.operation === OPERATIONS.LOADING ? data?.portLoadablePlanStowageDetails : data?.portDischargePlanStowageDetails;
      data.portPlanBallastDetails = this.operation === OPERATIONS.LOADING ? data?.portLoadablePlanBallastDetails : data?.portDischargePlanBallastDetails;
      data.portPlanRobDetails = this.operation === OPERATIONS.LOADING ? data?.portLoadablePlanRobDetails : data?.portDischargePlanRobDetails;
      data.planCommingleDetails = this.operation === OPERATIONS.LOADING ? data?.loadablePlanCommingleDetails : data?.dischargePlanCommingleDetails;

      if (data?.isPlannedValues) {
        data.portPlanStowageDetails?.map(item => {
          item.quantity = 0;
          item.ullage = 0;
          item.temperature = 0;
          item.api = 0;
        });
        data.portPlanBallastDetails?.map(item => {
          item.quantity = 0;
          item.sounding = 0;
        });
        data.portPlanRobDetails?.map(item => {
          item.quantity = 0;
          item.density = 0;
        });
      }
      this.gradeDropdown = [];
      if (this.operation === OPERATIONS.LOADING) {
        data?.billOfLaddingList?.map(item => {
          if (item?.cargoToBeLoaded || item?.cargoLoaded) {
            this.gradeDropdown.push(item);
          }
        });
      } else {
        data?.billOfLaddingList?.map(item => {
          if (item?.cargoToBeDischarged || item?.cargoDischarged) {
            this.gradeDropdown.push(item);
          }
        });
      }

      if (data.planCommingleDetails?.length) {
        const commingleStowages = [];
        data.planCommingleDetails?.map(item => {
          data.portPlanStowageDetails?.map(stowage => {
            if (Number(item.tankId) === Number(stowage.tankId)) {
              stowage.isCommingleCargo = true;
              stowage.quantity = data?.isPlannedValues ? 0 : item.quantity1MT;
              stowage.quantityMT = data?.isPlannedValues ? 0 : item.quantity1MT;
              stowage.actualWeight = data?.isPlannedValues ? 0 : item.quantity1MT;
              stowage.api = data?.isPlannedValues ? 0 : item.api;
              stowage.grade = item.grade;
              stowage.temperature = data?.isPlannedValues ? 0 : item.temperature;
              stowage.colorCode = item.colorCode;
              stowage.abbreviation = item.cargo1Abbreviation;
              stowage.cargoNominationId = item.cargoNomination1Id;
              stowage.ullage = data?.isPlannedValues ? 0 : item.ullage1;
              commingleStowages.push(JSON.parse(JSON.stringify(stowage)));
              commingleStowages[commingleStowages?.length - 1].cargoNominationId = item.cargoNomination2Id;
              commingleStowages[commingleStowages?.length - 1].abbreviation = item.cargo2Abbreviation;
              commingleStowages[commingleStowages?.length - 1].quantityMT = data?.isPlannedValues ? 0 : item.quantity2MT;
              commingleStowages[commingleStowages?.length - 1].actualWeight = data?.isPlannedValues ? 0 : item.quantity2MT;
              commingleStowages[commingleStowages?.length - 1].quantity = data?.isPlannedValues ? 0 : item.quantity2MT;
              commingleStowages[commingleStowages?.length - 1].ullage = data?.isPlannedValues ? 0 : item.ullage2;
            }
          });
        });
        data.portPlanStowageDetails = [...data.portPlanStowageDetails, ...commingleStowages];
      }

      this.display = true;
      this.ngxSpinnerService.hide();
      this.ullageResponseData = JSON.parse(JSON.stringify(data));
      this.ullageResponseDataCopy = JSON.parse(JSON.stringify(data));
    } catch (e) {
      this.ngxSpinnerService.hide();
      this.closePopup.emit(true);
    }


    this.currentQuantitySelectedUnit = QUANTITY_UNIT.MT;

    this.cargoColumns = this.ullageUpdatePopupTransformationService.getCargoTableColumn();
    this.ballastColumns = this.ullageUpdatePopupTransformationService.getBallastTankColumns();
    this.bunkerColumns = this.ullageUpdatePopupTransformationService.getBunkerTankColumns();
    this.blFigureColumns = this.ullageUpdatePopupTransformationService.getBLFigureColumns();
    this.formatCargoQuantity(this.ullageResponseData?.cargoQuantityDetails);
    this.tableForm = this.fb.group({
      items: this.fb.array([])
    });
    this.cargoTankForm = this.fb.group({
      dataTable: this.fb.array([])
    });
    this.ballastTankForm = this.fb.group({
      dataTable: this.fb.array([])
    });
    this.bunkerTankForm = this.fb.group({
      dataTable: this.fb.array([])
    });
    this.selectedCargo = this.ullageResponseData?.billOfLaddingList[0];
    this.blFigGrid(this.ullageResponseData);
    this.getShipLandingTanks();
    this.setCargoQuantities();
    this.setBallastQuantities(this.ullageResponseData?.portPlanBallastDetails);
    this.setBunkerQuantities(this.ullageResponseData?.portPlanRobDetails);
    this.getCargoTankFormGroup();
    this.getBallastTankFormGroup();
    this.getBunkerTankFormGroup();
    this.updateCargoQuantiyData();
    this.validateBlFigTable();
    this.checkFillingError();
    this.loadingDischargingTransformationService.isDischargeStarted$.pipe(takeUntil(this.ngUnsubscribe)).subscribe((value) => {
      this.isDischargeStarted = value;
    })
  }


  /**
  * unsubscribing loading plan observable
  * @memberof UllageUpdatePopupComponent
  */
  ngOnDestroy() {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  /**
  * Method for show as dropdown change
  *
  * @memberof UllageUpdatePopupComponent
  */
  showAsChange(event) {
    this.updateCargoQuantiyData();
    if (this.showAs.id === 2) {
      this.updateCargoTanks();
    } else {
      this.cargoTanks = [...this.ullageUpdatePopupTransformationService.formatCargoTanks(this.ullageResponseData?.cargoTanks, this.combineCommingleCargoLayout(this.ullageResponseData?.portPlanStowageDetails), this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit).slice(0)];
    }
  }

  /**
  * Method for combining commingle data
  *
  * @memberof UllageUpdatePopupComponent
  */
  combineCommingleCargoLayout(data) {
    const nonCommingle = [], commingle = [];
    data?.map(item => {
      if (item.isCommingleCargo) {
        commingle.push(item);
      } else {
        nonCommingle.push(item);
      }
    });
    const combinedCommingleData = [];
    if (commingle?.length) {
      const distinctData = [];
      const map = new Map();
      commingle?.map(item => {
        if (!map.has(item.tankId)) {
          map.set(item.tankId, true);
          distinctData.push(item);
        }
      });
      distinctData?.map(item => {
        let obj = JSON.parse(JSON.stringify(item));
        obj.abbreviation = obj.grade;
        const hasQuantity = [];
        if (obj.actualWeight && obj.actualWeight > 0) {
          hasQuantity.push(obj.cargoNominationId);
        }
        commingle?.map(com => {
          if (com.tankId === item.tankId && item.cargoNominationId !== com.cargoNominationId) {
            if ((obj.ullage && com.ullage && obj.ullage > com.ullage) || (!obj.ullage && com.ullage)) {
              obj = JSON.parse(JSON.stringify(com));
              obj.abbreviation = obj.grade;
            }
            if (com.actualWeight && com.actualWeight > 0) {
              hasQuantity.push(com.cargoNominationId);
            }
          }
        });
        obj.cargoNominationId = this.selectedCargo.cargoNominationId;
        if (hasQuantity?.length === 1) {
          this.ullageResponseData.billOfLaddingList?.map(cargoItem => {
            if (cargoItem.cargoNominationId === hasQuantity[0]) {
              obj.colorCode = cargoItem.cargoColor;
            }
          });
        } else {
          const commingleColorObj = commingle?.filter(comItem => comItem.tankId === obj.tankId);
          if (commingleColorObj?.length) {
            obj.colorCode = commingleColorObj[0].colorCode;
          }
        }
        combinedCommingleData.push(obj);
      });
    }
    return [...nonCommingle, ...combinedCommingleData];
  }

  /**
   * Method for updating the cargo quantity table
   *
   * @memberof UllageUpdatePopupComponent
   */
  updateCargoQuantiyData() {
    this.ullageResponseData?.billOfLaddingList.map(bl => {
      let actualQuantity = 0;
      let actualAPi = 0;
      let actualTemp = 0;
      let actualTempCount = 0;
      let avgCount = 0;
      this.ullageResponseData?.portPlanStowageDetails.map(item => {
        if (bl.cargoNominationId === item.cargoNominationId) {
          actualQuantity += Number(item.quantity);
          actualAPi += Number(item.api);
          if (Number(item.api) > 0) {
            avgCount++;
          }
          actualTemp += (item.temperature ? Number(item.temperature) : 0);
          if (item.temperature && Number(item.temperature) > 0) {
            actualTempCount++;
          }
        }
      });

      let mtQuantity = 0;
      let bblQuantity = 0;
      let klQuantity = 0;
      let ltQuantity = 0;
      let apiSum = 0;
      let apiCount = 0;
      let temperateureSum = 0;
      let temperatureCount = 0;
      this.blFigure['items'].map(item => {
        item.map(blq => {
          if (blq.cargo.cargoNominationId === bl.cargoNominationId) {
            mtQuantity += blq.cargo.mt.value ? Number(blq.cargo.mt.value) : 0;
            bblQuantity += blq.cargo.bbl.value ? Number(blq.cargo.bbl.value) : 0;
            klQuantity += blq.cargo.kl.value ? Number(blq.cargo.kl.value) : 0;
            ltQuantity += blq.cargo.lt.value ? Number(blq.cargo.lt.value) : 0;
            apiSum += blq.cargo.api.value ? Number(blq.cargo.api.value) : 0;
            temperateureSum += blq.cargo.temp.value ? Number(blq.cargo.temp.value) : 0;
            if (Number(blq.cargo.api.value) > 0) {
              apiCount++;
            }
            if (Number(blq.cargo.temp.value) > 0) {
              temperatureCount++;
            }
          }
        })
      });

      this.cargoQuantityList?.map(cargo => {
        if (cargo.cargoNominationId === bl.cargoNominationId) {
          const avgActualApi = Number((actualAPi / avgCount).toFixed(2)) ?? 0;
          cargo.actual.api = isNaN(avgActualApi) ? 0 : avgActualApi;

          const avgActualTemp = Number((actualTemp / actualTempCount).toFixed(2)) ?? 0;
          cargo.actual.temp = isNaN(avgActualTemp) ? 0 : avgActualTemp;

          cargo.actual.bbl = this.quantityPipe.transform(actualQuantity, QUANTITY_UNIT.MT, QUANTITY_UNIT.BBLS, cargo.actual.api) ?? 0;
          cargo.actual.kl = this.quantityPipe.transform(actualQuantity, QUANTITY_UNIT.MT, QUANTITY_UNIT.KL, cargo.actual.api) ?? 0;
          cargo.actual.lt = this.quantityPipe.transform(actualQuantity, QUANTITY_UNIT.MT, QUANTITY_UNIT.LT, cargo.actual.api) ?? 0;
          cargo.actual.mt = isNaN(actualQuantity) ? 0 : Number(Number(actualQuantity).toFixed(2));

          const avgBlApi = Number((apiSum / apiCount).toFixed(2)) ?? 0;
          cargo.blFigure.api = isNaN(avgBlApi) ? 0 : avgBlApi;

          const avgTemp = Number((temperateureSum / temperatureCount).toFixed(2)) ?? 0;
          cargo.blFigure.temp = isNaN(avgTemp) ? 0 : avgTemp;

          cargo.blFigure.bbl = this.decimalPipe.transform(bblQuantity, AppConfigurationService.settings.quantityNumberFormatBBLS) ?? 0;
          cargo.blFigure.bbl = cargo.blFigure.bbl ? Number(cargo.blFigure.bbl.replaceAll(',', '')) : 0;
          cargo.blFigure.kl = this.decimalPipe.transform(klQuantity, AppConfigurationService.settings.quantityNumberFormatKL) ?? 0;
          cargo.blFigure.kl = cargo.blFigure.kl ? Number(cargo.blFigure.kl.replaceAll(',', '')) : 0;
          cargo.blFigure.lt = this.decimalPipe.transform(ltQuantity, AppConfigurationService.settings.quantityNumberFormatLT) ?? 0;
          cargo.blFigure.lt = cargo.blFigure.lt ? Number(cargo.blFigure.lt.replaceAll(',', '')) : 0;
          cargo.blFigure.mt = isNaN(mtQuantity) ? 0 : Number(Number(mtQuantity).toFixed(2));

          cargo.difference.bbl = this.decimalPipe.transform((cargo.actual.bbl - cargo.blFigure.bbl), AppConfigurationService.settings.quantityNumberFormatBBLS);
          cargo.difference.bbl = cargo.difference.bbl ? Number(cargo.difference.bbl.replaceAll(',', '')) : 0;
          cargo.difference.kl = this.decimalPipe.transform((cargo.actual.kl - cargo.blFigure.kl), AppConfigurationService.settings.quantityNumberFormatKL);
          cargo.difference.kl = cargo.difference.kl ? Number(cargo.difference.kl.replaceAll(',', '')) : 0;
          cargo.difference.lt = this.decimalPipe.transform((cargo.actual.lt - cargo.blFigure.lt), AppConfigurationService.settings.quantityNumberFormatLT);
          cargo.difference.lt = cargo.difference.lt ? Number(cargo.difference.lt.replaceAll(',', '')) : 0;
          cargo.difference.mt = this.decimalPipe.transform((cargo.actual.mt - cargo.blFigure.mt), AppConfigurationService.settings.quantityNumberFormatMT);
          cargo.difference.mt = cargo.difference.mt ? Number(cargo.difference.mt.replaceAll(',', '')) : 0;

          cargo.diffPercentage.bbl = cargo.actual.bbl !== 0 ? Number(((cargo.difference.bbl / cargo.actual.bbl) * 100).toFixed(2)) : 0;
          cargo.diffPercentage.bbl = isNaN(cargo.diffPercentage.bbl) ? 0 : cargo.diffPercentage.bbl;
          cargo.diffPercentage.kl = cargo.actual.kl !== 0 ? Number(((cargo.difference.kl / cargo.actual.kl) * 100).toFixed(2)) : 0;
          cargo.diffPercentage.kl = isNaN(cargo.diffPercentage.kl) ? 0 : cargo.diffPercentage.kl;
          cargo.diffPercentage.lt = cargo.actual.lt !== 0 ? Number(((cargo.difference.lt / cargo.actual.lt) * 100).toFixed(2)) : 0;
          cargo.diffPercentage.lt = isNaN(cargo.diffPercentage.lt) ? 0 : cargo.diffPercentage.lt;
          cargo.diffPercentage.mt = cargo.actual.mt !== 0 ? Number(((cargo.difference.mt / cargo.actual.mt) * 100).toFixed(2)) : 0;
          cargo.diffPercentage.mt = isNaN(cargo.diffPercentage.mt) ? 0 : cargo.diffPercentage.mt;
        }
      });

    });

  }

  /**
   * Method for updating the cargo tanks
   *
   * @memberof UllageUpdatePopupComponent
   */
  async updateCargoTanks() {
    let fillingPercentageError = false;
    let tankName = null;
    this.cargoTanks = [...this.ullageUpdatePopupTransformationService.formatCargoTanks(this.ullageResponseData?.cargoTanks, this.combineCommingleCargoLayout(this.ullageResponseData?.portPlanStowageDetails), this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit).slice(0)];
    const commingle = [];
    this.ullageResponseData?.portPlanStowageDetails?.map(item => {
      if (item.isCommingleCargo) {
        commingle.push(item);
      }
    });
    this.cargoQuantityList.map(item => {
      const differenecPercentage = item.diffPercentage.mt;
      this.cargoTanks.map(tank => {
        tank.map(elem => {
          if (elem?.commodity && elem?.commodity['cargoNominationId'] === item.cargoNominationId && (!item.cargoLoaded || !item.cargoDischarged) && item.blFigure.mt) {
            let quantityData = 0;
            if (elem.commodity.isCommingleCargo) {
              const getCommingleCargos = commingle?.filter(comData => comData.tankId === elem.commodity.tankId);
              if (getCommingleCargos?.length === 2) {
                const cargoIndex = getCommingleCargos.findIndex(cc => cc.cargoNominationId === item.cargoNominationId);
                if (cargoIndex > -1) {
                  const firstCargo = cargoIndex === 0 ? getCommingleCargos[0] : getCommingleCargos[1];
                  const secondCargo = cargoIndex === 0 ? getCommingleCargos[1] : getCommingleCargos[0];
                  if (firstCargo.quantity > secondCargo.quantity) {
                    quantityData = Number(firstCargo.quantity) - Number(secondCargo.quantity);
                    quantityData = Number(quantityData.toFixed(2));
                  } else {
                    quantityData = firstCargo.quantity;
                  }
                }
              }
            } else {
              quantityData = elem.commodity.quantity;
            }
            const changeInQty = (differenecPercentage / 100) * Number(quantityData);
            elem.commodity.quantity = differenecPercentage >= 0 ? (Number(elem.commodity.quantity) + changeInQty) : (Number(elem.commodity.quantity) - Math.abs(changeInQty));
            elem.commodity.actualWeight = elem.commodity.quantity;
            elem.commodity.volume = this.quantityPipe.transform(elem.commodity.actualWeight, this.currentQuantitySelectedUnit, QUANTITY_UNIT.OBSKL, elem.commodity.api, elem.commodity.temperature);
            elem.commodity.percentageFilled = this.ullageUpdatePopupTransformationService.getFillingPercentage(elem);
            if (Number(elem.commodity.percentageFilled) > 98.5 && !fillingPercentageError) {
              fillingPercentageError = true;
              tankName = elem.shortName;
            }
          }
        });
      })
    });
    this.cargoTanks = [...this.cargoTanks];
    if (fillingPercentageError) {
      const translationKeys = await this.translateService.get(['ULLAGE_UPDATE_ERROR_LABEL', 'ULLAGE_UPDATE_FILLING_PERCENTAGE_ERROR_ONE', 'ULLAGE_UPDATE_FILLING_PERCENTAGE_ERROR_TWO']).toPromise();
      this.messageService.add({ severity: 'error', summary: translationKeys['ULLAGE_UPDATE_ERROR_LABEL'], detail: translationKeys['ULLAGE_UPDATE_FILLING_PERCENTAGE_ERROR_ONE'] + " " + tankName + " " + translationKeys['ULLAGE_UPDATE_FILLING_PERCENTAGE_ERROR_TWO'] });
    }
  }

  /**
   * Method for seting bunker quantities
   *
   * @memberof UllageUpdatePopupComponent
   */
  setBunkerQuantities(data) {
    this.bunkerTanksList = [];
    data.map(item => {
      this.bunkerTanksList.push(this.ullageUpdatePopupTransformationService.getFormatedTankDetailsBunker(item, true, false, true))
    });
  }

  /**
   * Method for seting ballast quantities
   *
   * @memberof UllageUpdatePopupComponent
   */
  setBallastQuantities(data) {
    this.ballastQuantities = [];
    data.map(item => {
      this.ballastQuantities.push(this.ullageUpdatePopupTransformationService.getFormatedTankDetailsBallast(item, true, false, true))
    });
    this.setBallastTankQuantity();
  }

  /**
   * Method for seting ballast capacity
   *
   * @memberof UllageUpdatePopupComponent
   */
  setBallastTankQuantity() {
    this.ballastQuantities.map(item => {
      this.rearBallastTanks.map(el => {
        el.map(tank => {
          if (item.tankId === tank.id) {
            item.fullCapacityCubm = tank.fullCapacityCubm;
          }
        });
      })
      this.centerBallastTanks.map(el => {
        el.map(tank => {
          if (item.tankId === tank.id) {
            item.fullCapacityCubm = tank.fullCapacityCubm;
          }
        });
      });
      this.frontBallastTanks.map(el => {
        el.map(tank => {
          if (item.tankId === tank.id) {
            item.fullCapacityCubm = tank.fullCapacityCubm;
          }
        });
      });
    });
  }

  /**
   * Method for seting cargo quantities
   *
   * @memberof UllageUpdatePopupComponent
   */
  setCargoQuantities() {
    this.cargoQuantities = [];
    this.ullageResponseData?.portPlanStowageDetails?.map(item => {
      if (this.selectedCargo.cargoNominationId === item.cargoNominationId) {
        this.cargoQuantities.push(this.ullageUpdatePopupTransformationService.getFormatedTankDetailsCargo(item, true, false, true))
      }
    });
  }

  /**
   * Method for initializing cargo formGroup
   *
   * @memberof UllageUpdatePopupComponent
   */
  getCargoTankFormGroup() {
    this.cargoTankForm = this.fb.group({
      dataTable: this.fb.array([])
    });
    const items: any = this.cargoTankForm.get('dataTable') as FormArray;
    this.cargoQuantities?.map(item => {
      items.push(this.cargoTankFormGroup(item));
    });
  }

  /**
   * Method for initializing ballast formGroup
   *
   * @memberof UllageUpdatePopupComponent
   */
  getBallastTankFormGroup() {
    const items: any = this.ballastTankForm.get('dataTable') as FormArray;
    this.ballastQuantities?.map(item => {
      items.push(this.ballastTankFormGroup(item));
    });
  }

  /**
   * Method for initializing bunker formGroup
   *
   * @memberof UllageUpdatePopupComponent
   */
  getBunkerTankFormGroup() {
    const items: any = this.bunkerTankForm.get('dataTable') as FormArray;
    this.bunkerTanksList?.map(item => {
      items.push(this.bunkerTankFormGroup(item));
    });
  }

  /**
   * Method for initializing bunker formGroup
   *
   * @memberof UllageUpdatePopupComponent
   */
  bunkerTankFormGroup(tank) {
    return this.fb.group({
      tankName: this.fb.control(tank.tankName.value),
      quantity: this.fb.control(tank.quantity.value, [Validators.min(0), numberValidator(2, 7, false), Validators.required]),
    });
  }
  /**
   * Method for initializing ballast formGroup
   *
   * @memberof UllageUpdatePopupComponent
   */
  ballastTankFormGroup(tank) {
    return this.fb.group({
      tankName: this.fb.control(tank.tankName.value),
      quantity: this.fb.control(tank.quantity.value),
      sounding: this.fb.control(tank.sounding.value, [Validators.required, numberValidator(6, 3, false), tankCapacityValidator('quantity', tank.fullCapacityCubm, 'sounding', 'fillingPercentage', 100)]),
      fillingPercentage: this.fb.control(tank.fillingPercentage.value)
    });
  }

  /**
   * Method for initializing cargo formGroup
   *
   * @memberof UllageUpdatePopupComponent
   */
  cargoTankFormGroup(tank) {
    return this.fb.group({
      tankName: this.fb.control(tank.tankName.value),
      ullage: this.fb.control(tank.ullage.value, [Validators.required, numberValidator(6, 3, false), tankCapacityValidator(null, null, 'ullage', 'fillingPercentage')]),
      temperature: this.fb.control(tank.temperature.value, [Validators.required, Validators.min(0), Validators.max(160), numberValidator(2, 3, false), tankCapacityValidator(null, null, 'temperature', 'fillingPercentage'), apiTemperatureMinValidator('temperature')]),
      api: this.fb.control(tank.api.value, [Validators.required, Validators.min(0), numberValidator(2, 2, false), tankCapacityValidator(null, null, 'api', 'fillingPercentage'), apiTemperatureMinValidator('api')]),
      quantity: this.fb.control(tank.quantity.value),
      fillingPercentage: this.fb.control(tank.fillingPercentage.value),
      tankId: this.fb.control(tank.tankId)
    });
  }

  /**
   * Method for initializing cargo quantity table data
   *
   * @memberof UllageUpdatePopupComponent
   */
  formatCargoQuantity(data) {
    this.cargoQuantityList = [];
    data?.map(item => {
      if (this.setCargoTobeLoadedOrDischarged(item)) {
        this.cargoQuantityList.push(this.ullageUpdatePopupTransformationService.formatCargoQuantity(item));
      }
    });
  }

  /**
   * Method for checking cargo loaded
   *
   * @memberof UllageUpdatePopupComponent
   */
  setCargoTobeLoadedOrDischarged(data) {
    let cargoLoadedDischarged = false;
    if (this.operation === OPERATIONS.LOADING) {
      this.ullageResponseData?.billOfLaddingList?.map(item => {
        if (item?.cargoNominationId === data?.cargoNominationId) {
          data.cargoToBeLoaded = item?.cargoToBeLoaded;
          data.cargoLoaded = item?.cargoLoaded;
        }
        if (item?.cargoNominationId === data?.cargoNominationId && (item?.cargoToBeLoaded || item?.cargoLoaded)) {
          cargoLoadedDischarged = true;
        }
      });
    } else {
      this.ullageResponseData?.billOfLaddingList?.map(item => {
        if (item?.cargoNominationId === data?.cargoNominationId) {
          data.cargoToBeDischarged = item?.cargoToBeDischarged;
          data.cargoDischarged = item?.cargoDischarged;
        }
        if (item?.cargoNominationId === data?.cargoNominationId && (item?.cargoToBeDischarged || item?.cargoDischarged)) {
          cargoLoadedDischarged = true;
        }
      });
    }
    return cargoLoadedDischarged;
  }

  /**
   * Method for close ullage update pop up
   *
   * @memberof UllageUpdatePopupComponent
   */
  closeDialog() {
    this.closePopup.emit(true);
  }

  /**
   * Handler for tab click
   *
   * @param {TANKTYPE} selectedTab
   * @memberof UllageUpdatePopupComponent
   */
  onTabClick(selectedTab: TANKTYPE) {
    this.selectedTab = selectedTab;
    this.percentageFilled = null;
    if (selectedTab === TANKTYPE.BUNKER) {
      this.checkBunkerTankError();
    }
    if (selectedTab === TANKTYPE.CARGO) {
      this.validateBlFigTable();
    }
  }


  /**
* initialise tanks data
*
* @memberof UllageUpdatePopupComponent
*/
  async getShipLandingTanks() {
    this.prevQuantitySelectedUnit = AppConfigurationService.settings.baseUnit;
    this.convertQuantityToSelectedUnit();
  }

  /**
  * Convert quantity to selected uint
  *
  * @memberof UllageUpdatePopupComponent
  */
  convertQuantityToSelectedUnit() {
    this.bunkerTanks = this.ullageUpdatePopupTransformationService.formatBunkerTanks(this.ullageResponseData?.bunkerTanks, this.ullageResponseData?.portPlanRobDetails, this.status);
    this.rearBunkerTanks = this.ullageUpdatePopupTransformationService.formatBunkerTanks(this.ullageResponseData?.bunkerRearTanks, this.ullageResponseData?.portPlanRobDetails, this.status);
    this.cargoTanks = this.ullageUpdatePopupTransformationService.formatCargoTanks(this.ullageResponseData?.cargoTanks, this.combineCommingleCargoLayout(this.ullageResponseData?.portPlanStowageDetails), this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit);
    this.rearBallastTanks = this.ullageUpdatePopupTransformationService.formatBallastTanks(this.ullageResponseData?.ballastRearTanks, this.ullageResponseData?.portPlanBallastDetails, this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit);
    this.centerBallastTanks = this.ullageUpdatePopupTransformationService.formatBallastTanks(this.ullageResponseData?.ballastCenterTanks, this.ullageResponseData?.portPlanBallastDetails, this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit);
    this.frontBallastTanks = this.ullageUpdatePopupTransformationService.formatBallastTanks(this.ullageResponseData?.ballastFrontTanks, this.ullageResponseData?.portPlanBallastDetails, this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit);
    this.fuelTypes = [...new Map(this.ullageResponseData.portPlanRobDetails.map(item => [item['fuelTypeId'], { id: item?.fuelTypeId, name: item?.tankName, colorCode: item?.colorCode, shortName: item?.fuelTypeShortName }])).values()];
  }

  /**
   * Is cell visble
   * @param rowData
   * @param {number} index
   * @memberof UllageUpdatePopupComponent
   */
  isCellVisable(index: number, col: any, rowData) {
    if (index !== 0 && col.field === 'cargoName') {
      return false;
    }
    return true
  }

  /**
   * Method to calculate the rowspan of a particular row based on its subheaders
   * @param {number} index
   * @param {number} colIndex
   * @param {number} rowIndex
   * @memberof UllageUpdatePopupComponent
   */
  calculateRowSpan(index: number, colIndex: number, rowIndex: number) {
    if (index === 0 && colIndex === 0) {
      return this.blFigure.items[rowIndex].length;
    }
  }

  /**
  * Method to get the control of a particular field
  * @param {number} colIndex
  * @param {number} rowIndex
  * @param {string} key
  * @returns {AbstractControl}
  * @memberof UllageUpdatePopupComponent
  */
  getControl(rowIndex: number, colIndex: number, key: string): AbstractControl {
    return this.getCargoItems(rowIndex).at(colIndex).get(key);
  }

  /**
   * Get field errors
   *
   * @param {number} formGroupIndex
   * @param {string} formControlName
   * @returns {ValidationErrors}
   * @memberof UllageUpdatePopupComponent
  */
  fieldError(rowIndex: number, colIndex: number, key: string): ValidationErrors {
    const formControl = this.getControl(rowIndex, colIndex, key);
    return formControl.invalid && (formControl?.dirty || formControl?.touched) ? formControl.errors : null;
  }

  /**
   * Method to change selected Cargo
   * @param {*} data
   * @memberof UllageUpdatePopupComponent
  */
  changeCargo(data: any) {
    this.selectedCargo = data.value;
    this.setCargoQuantities();
    this.getCargoTankFormGroup();
    this.checkFillingError();
  }

  /**
   * Method to assign and check for filling errors while changing grade or file upload.
   * @memberof UllageUpdatePopupComponent
  */
  checkFillingError() {
    const items: any = this.cargoTankForm.get('dataTable') as FormArray;
    const cargoTanks = [...this.ullageUpdatePopupTransformationService.formatCargoTanks(this.ullageResponseData?.cargoTanks, this.combineCommingleCargoLayout(this.ullageResponseData?.portPlanStowageDetails), this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit).slice(0)];
    items?.controls?.map(item => {
      cargoTanks?.map(el => {
        el?.map(tank => {
          if (tank.id === item.controls?.tankId.value) {
            item.controls.fillingPercentage.setValue(tank?.commodity?.percentageFilled);
            item.controls.ullage.updateValueAndValidity();
          }
        });
      });
    });
  }

  /**
   * Method for creating form array for B/l grid
   *
   * @memberof UllageUpdatePopupComponent
  */
  blFigGrid(data) {
    this.blFigure['items'] = [];
    const blData = data?.billOfLaddingList ? data?.billOfLaddingList : [];
    blData.map(item => {
      if (item?.cargoToBeLoaded || item?.cargoToBeDischarged) {
        const cargoData = [];
        if (item?.billOfLaddings?.length) {
          item?.billOfLaddings?.map(bl => {
            cargoData.push({ 'cargo': this.ullageUpdatePopupTransformationService.getFormatedCargoDetails(bl, item, false, false) });
          });
        } else {
          cargoData.push({ 'cargo': this.ullageUpdatePopupTransformationService.getFormatedCargoDetails(<ICargoDetail>{ portId: this.portId }, item, true, true) });
        }
        this.blFigure['items'].push(cargoData);
      }
    });

    this.blFigure.items.map((blItems, blRowIndex) => {
      const items = this.tableForm.get('items') as FormArray;
      items.push(this.initItems())
      blItems.forEach((cargoDetails, index) => {
        this.addCargoItems(blRowIndex, cargoDetails.cargo);
      });
    });

    this.calculateTotal();
    this.enableDisableBLFigure();
    setTimeout(() => {
      this.validateBlFigTable();
    });
  }

  /**
   * Method for validate BL table
   *
   * @memberof UllageUpdatePopupComponent
  */
  validateBlFigTable() {
    this.blFigure.items.map((form, mainIndex) => {
      if (!this.blEmptyCheck(form)) {
        form?.map((rowData, childIndex) => {
          if (this.setBlError({ cargoNominationId: rowData?.cargo?.cargoNominationId }, 'bbl')) {
            const control = this.getControl(mainIndex, childIndex, 'bbl');
            setTimeout(() => {
              control.setErrors({ rangeError: true });
              control.markAsTouched();
              control.markAsDirty();
            });
            rowData.cargo.bbl.isEditMode = true;
          } else {
            const control = this.getControl(mainIndex, childIndex, 'bbl');
            control.setErrors(null);
            rowData.cargo.bbl.isEditMode = false;
          }
          if (this.setBlError({ cargoNominationId: rowData?.cargo?.cargoNominationId }, 'kl')) {
            const control = this.getControl(mainIndex, childIndex, 'kl');
            setTimeout(() => {
              control.setErrors({ rangeError: true });
              control.markAsTouched();
              control.markAsDirty();
            });
            rowData.cargo.kl.isEditMode = true;
          } else {
            rowData.cargo.kl.isEditMode = false;
            const control = this.getControl(mainIndex, childIndex, 'kl');
            control.setErrors(null);
          }
          if (this.setBlError({ cargoNominationId: rowData?.cargo?.cargoNominationId }, 'lt')) {
            const control = this.getControl(mainIndex, childIndex, 'lt');
            setTimeout(() => {
              control.setErrors({ rangeError: true });
              control.markAsTouched();
              control.markAsDirty();
            });
            rowData.cargo.lt.isEditMode = true;
          } else {
            rowData.cargo.lt.isEditMode = false;
            const control = this.getControl(mainIndex, childIndex, 'lt');
            control.setErrors(null);
          }
          if (this.setBlError({ cargoNominationId: rowData?.cargo?.cargoNominationId }, 'mt')) {
            const control = this.getControl(mainIndex, childIndex, 'mt');
            setTimeout(() => {
              control.setErrors({ rangeError: true });
              control.markAsTouched();
              control.markAsDirty();
            });
            rowData.cargo.mt.isEditMode = true;
          } else {
            rowData.cargo.mt.isEditMode = false;
            const control = this.getControl(mainIndex, childIndex, 'mt');
            control.setErrors(null);
          }
        });
      } else {
        this.resetBlErrors(mainIndex, 0);
      }
    });
    this.tableForm.markAllAsTouched();
    this.tableForm.markAsDirty();
  }

  /**
   * Method for enable and disable bl figure fields
   *
   * @memberof UllageUpdatePopupComponent
  */
  enableDisableBLFigure() {
    this.ullageResponseData?.billOfLaddingList?.map(item => {
      let totQuantity = 0;
      this.ullageResponseData?.portPlanStowageDetails.map(stowage => {
        if (item.cargoNominationId === stowage.cargoNominationId) {
          totQuantity += Number(stowage.quantity);
        }
      });

      this.tableForm.controls.items['controls'].map(form => {
        form.controls.cargos.controls.map(child => {
          if (child.controls.cargoNominationId.value === item.cargoNominationId) {
            if (totQuantity > 0) {
              child.enable();
            } else {
              child.disable();
            }
          }
        });
      });
    });
  }

  /**
   * Method to get Items
   * @memberof UllageUpdatePopupComponent
  */
  getItems(): FormArray {
    return this.tableForm.get('items') as FormArray;
  }

  /**
   * Add new Cargo
   * @param {number} rowIndex
   * @param {number} colIndex
   * @memberof UllageUpdatePopupComponent
  */
  newCargo(rowIndex: number, colIndex: number, rowData) {
    if (this.getCargoItems(rowIndex).disabled) { return; }
    const getBlFigure = this.ullageResponseData?.billOfLaddingList.filter((item) => item.cargoNominationId === rowData.cargoNominationId);
    if (getBlFigure?.length) {
      this.blFigure.items[rowIndex].push({ cargo: this.ullageUpdatePopupTransformationService.getFormatedCargoDetails(<ICargoDetail>{ portId: this.portId }, getBlFigure[0], true, true) });
      this.getCargoItems(rowIndex).push(this.initCargoDetails(this.blFigure.items[rowIndex][this.blFigure.items[rowIndex].length - 1].cargo));
      setTimeout(() => {
        this.validateBlFigTable();
      });
    }
  }

  /**
   * Delete confirmation functoin
   * @param {number} rowIndex
   * @param {number} colIndex
   * @memberof UllageUpdatePopupComponent
  */
  deleteConfirm(rowIndex: number, colIndex: number) {
    const translationKeys = this.translateService.instant(['ULLAGE_UPDATE_DELETE_SUMMARY', 'ULLAGE_UPDATE_DELETE_DELETE_DETAILS', 'ULLAGE_UPDATE_DELETE_CONFIRM_LABEL', 'ULLAGE_UPDATE_DELETE_REJECT_LABEL']);

    this.confirmationService.confirm({
      key: 'confirmation-alert',
      header: translationKeys['ULLAGE_UPDATE_DELETE_SUMMARY'],
      message: translationKeys['ULLAGE_UPDATE_DELETE_DELETE_DETAILS'],
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: translationKeys['ULLAGE_UPDATE_DELETE_CONFIRM_LABEL'],
      acceptIcon: 'pi',
      acceptButtonStyleClass: 'btn btn-main mr-5',
      rejectVisible: true,
      rejectLabel: translationKeys['ULLAGE_UPDATE_DELETE_REJECT_LABEL'],
      rejectIcon: 'pi',
      rejectButtonStyleClass: 'btn btn-main',
      accept: async () => {
        this.deleteCargo(rowIndex, colIndex);
      }
    });

  }

  /**
   * Delete Cargo
   * @param {number} rowIndex
   * @param {number} colIndex
   * @memberof UllageUpdatePopupComponent
  */
  deleteCargo(rowIndex: number, colIndex: number) {
    const removedItem = this.blFigure.items[rowIndex].splice(colIndex, 1);
    if (removedItem?.length && !removedItem[0].cargo.isNewRow) {
      this.billOfLaddingRemovedList.push(removedItem[0]);
    }
    this.blFigure.items = [...this.blFigure.items];
    this.getCargoItems(rowIndex).removeAt(colIndex);
    this.calculateTotal();
    this.updateCargoQuantiyData();
    setTimeout(() => {
      this.validateBlFigTable();
    });
  }

  /**
   * Method to get cargo from form array
   * @param {number} cargoIndex
   * @memberof UllageUpdatePopupComponent
  */
  getCargoItems(cargoIndex: number): FormArray {
    return this.getItems()
      .at(cargoIndex)
      .get('cargos') as FormArray;
  }

  /**
   * Method to add cargo
   * @param {number} cargoIndex
   * @memberof UllageUpdatePopupComponent
  */
  addCargoItems(cargoIndex: number, cargo) {
    this.getCargoItems(cargoIndex).push(this.initCargoDetails(cargo));
  }

  /**
   *  Initialize cargo  form group
   * @memberof UllageUpdatePopupComponent
  */
  initItems(): FormGroup {
    return this.fb.group({
      cargos: this.fb.array([])
    });
  }

  /**
   * Initialize cargo details group
   * @param {*} cargo
   * @memberof UllageUpdatePopupComponent
  */
  initCargoDetails(cargo: any): FormGroup {
    return this.fb.group({
      blRefNo: this.fb.control(cargo.blRefNo.value, [Validators.minLength(4), Validators.maxLength(12), blReferenceValidator()]),
      bbl: this.fb.control(cargo.bbl.value, [Validators.min(0), numberValidator(0, 7, false)]),
      lt: this.fb.control(cargo.lt.value, [Validators.min(0), numberValidator(2, 7, false)]),
      mt: this.fb.control(cargo.mt.value, [Validators.min(0), numberValidator(2, 7, false)]),
      kl: this.fb.control(cargo.kl.value, [Validators.min(0), numberValidator(3, 7, false)]),
      api: this.fb.control(cargo.api.value, [numberValidator(2, 2, false), apiTemperatureBlfigValidator('api')]),
      temp: this.fb.control(cargo.temp.value, [Validators.max(160), numberValidator(2, 3, false), apiTemperatureBlfigValidator('temperature')]),
      cargoNominationId: this.fb.control(cargo.cargoNominationId),
    });
  }

  /**
   * Calculate  Total for B/L figure
   * @memberof UllageUpdatePopupComponent
  */
  calculateTotal() {
    this.blFigureTotal = <IBlFigureTotal>{ lt: 0, mt: 0, api: 0, kl: 0, bbl: 0 };
    this.blFigure.items.forEach((blItems, blRowIndex) => {
      blItems.forEach((cargoDetails, index) => {
        this.blFigureTotal.lt += cargoDetails.cargo.lt.value ? Number(cargoDetails.cargo.lt.value) : 0;
        this.blFigureTotal.mt += cargoDetails.cargo.mt.value ? Number(cargoDetails.cargo.mt.value) : 0;
        this.blFigureTotal.kl += cargoDetails.cargo.kl.value ? Number(cargoDetails.cargo.kl.value) : 0;
        this.blFigureTotal.bbl += cargoDetails.cargo.bbl.value ? Number(cargoDetails.cargo.bbl.value) : 0;
        this.blFigureTotal.api += cargoDetails.cargo.api.value ? Number(cargoDetails.cargo.api.value) : 0;
      });
    })
  }

  /**
  * Handler for cell on click event
  * @param event
  * @param rowData
  * @param rowIndex
  * @param col
  * @param colIndex
  */
  onClick(event, rowData, rowIndex, col: IDataTableColumn) {
    if (rowData[col.field]?.isEditable && this.editMode && (col.editable === undefined || col.editable)) {
      rowData[col.field].isEditMode = true;
    }
  }

  /**
  * Handler for checking blill of ladding empty row
  * @param row
  */
  blEmptyCheck(row) {
    return (row?.length === 1 && ((!row[0].cargo?.api?.value || row[0].cargo?.api?.value?.toString() === '0')
      && (!row[0].cargo?.bbl?.value || row[0].cargo?.bbl?.value?.toString() === '0')
      && !row[0].cargo?.blRefNo?.value
      && (!row[0].cargo?.kl?.value || row[0].cargo?.kl?.value?.toString() === '0')
      && (!row[0].cargo?.lt?.value || row[0].cargo?.lt?.value?.toString() === '0')
      && (!row[0].cargo?.mt?.value || row[0].cargo?.mt?.value?.toString() === '0')
      && (!row[0].cargo?.temp?.value || row[0].cargo?.temp?.value?.toString() === '0')));
  }

  /**
  * Handler for resetting blill of ladding errors
  * @param row
  */
  resetBlErrors(rowIndex, index) {
    const kl = this.getControl(rowIndex, index, 'kl');
    kl.setErrors(null);
    const bbl = this.getControl(rowIndex, index, 'bbl');
    bbl.setErrors(null);
    const lt = this.getControl(rowIndex, index, 'lt');
    lt.setErrors(null);
    const mt = this.getControl(rowIndex, index, 'mt');
    mt.setErrors(null);
  }

  /**
  * Handler for blur event
  * @param event
  * @param rowData
  * @param rowIndex
  * @param col
  * @param colIndex
  */
  onEditComplete(event: any, rowData: any, key: string, rowIndex: number, index: number) {
    rowData[key]['value'] = event.target.value;
    this.tableForm.markAllAsTouched();
    this.tableForm.markAsDirty();
    const control = this.getControl(rowIndex, index, key);
    this.calculateTotal();
    this.updateCargoQuantiyData();
    if (this.showAs.id === 2) {
      this.updateCargoTanks();
    }
    const api = this.getControl(rowIndex, index, 'api');
    api.updateValueAndValidity();
    const temp = this.getControl(rowIndex, index, 'temp');
    temp.updateValueAndValidity();
    const row = this.blFigure.items[rowIndex];
    if (key === 'blRefNo' || key === 'api' || key === 'temp') {
      rowData[key].isEditMode = control.errors ? true : false;
      if (this.blEmptyCheck(row)) {
        this.resetBlErrors(rowIndex, index);
      }
    } else {
      if (!this.blEmptyCheck(row)) {
        const error = this.setBlError(rowData, key);
        if (error) {
          control.setErrors({ rangeError: true });
          rowData[key].isEditMode = true;
        } else {
          rowData[key].isEditMode = false;
          control.setErrors(null);
        }
      } else {
        this.resetBlErrors(rowIndex, index);
      }
    }

    setTimeout(() => {
      this.validateBlFigTable();
    });
  }

  /**
  * Method for set bl error
  *
  * @memberof UllageUpdatePopupComponent
 */
  setBlError(rowData, key) {
    let hasError = false;
    let mtQty = 0, bblQty = 0, klQty = 0, ltQty = 0;
    this.blFigure?.items?.map(item => {
      item.map(cargo => {
        if (cargo.cargo.cargoNominationId === rowData.cargoNominationId) {
          mtQty += isNaN(Number(cargo.cargo.mt.value)) ? 0 : Number(cargo.cargo.mt.value);
          bblQty += isNaN(Number(cargo.cargo.bbl.value)) ? 0 : Number(cargo.cargo.bbl.value);
          klQty += isNaN(Number(cargo.cargo.kl.value)) ? 0 : Number(cargo.cargo.kl.value);
          ltQty += isNaN(Number(cargo.cargo.lt.value)) ? 0 : Number(cargo.cargo.lt.value);
        }
      })
    });
    let cargoQuantityData = null;
    this.cargoQuantityList.map(item => {
      if (item.cargoNominationId === rowData.cargoNominationId) {
        cargoQuantityData = item;
      }
    });
    switch (key) {
      case 'mt':
        const maximumMt = (Number(cargoQuantityData?.actual?.mt) * 0.01) + Number(cargoQuantityData?.actual?.mt);
        const minimumMt = Number(cargoQuantityData?.actual?.mt) - (Number(cargoQuantityData?.actual?.mt) * 0.01);
        if ((mtQty > Number(maximumMt.toFixed(2))) || (mtQty < Number(minimumMt.toFixed(2)))) {
          hasError = true;
        }
        break;
      case 'bbl':
        const maximumBbl = (Number(cargoQuantityData?.actual?.bbl) * 0.01) + Number(cargoQuantityData?.actual?.bbl);
        const minimumBbl = Number(cargoQuantityData?.actual?.bbl) - (Number(cargoQuantityData?.actual?.bbl) * 0.01);
        if ((bblQty > Number(maximumBbl.toFixed(0))) || (bblQty < Number(minimumBbl.toFixed(2)))) {
          hasError = true;
        }
        break;
      case 'kl':
        const maximumKl = (Number(cargoQuantityData?.actual?.kl) * 0.01) + Number(cargoQuantityData?.actual?.kl);
        const minimumKl = Number(cargoQuantityData?.actual?.kl) - (Number(cargoQuantityData?.actual?.kl) * 0.01);
        if ((klQty > Number(maximumKl.toFixed(3))) || (klQty < Number(minimumKl.toFixed(3)))) {
          hasError = true;
        }
        break;
      case 'lt':
        const maximumLt = (Number(cargoQuantityData?.actual?.lt) * 0.01) + Number(cargoQuantityData?.actual?.lt);
        const minimumLt = Number(cargoQuantityData?.actual?.lt) - (Number(cargoQuantityData?.actual?.lt) * 0.01);
        if ((ltQty > Number(maximumLt.toFixed(2))) || (ltQty < Number(minimumLt.toFixed(2)))) {
          hasError = true;
        }
        break;
      default:
        hasError = false;
    }
    return hasError;
  }

  /**
   * Method for cargo quantity update api call
   * @param event
   * @memberof UllageUpdatePopupComponent
   */
  async cargoEditCompleted(event) {
    if (event.data.api.value === "" || event.data.ullage.value === "" || event.data.temperature.value === "") {
      return;
    }
    const param = {
      api: event.data.api.value,
      correctedUllage: event.data.ullage.value,
      id: event.data.id,
      isBallast: false,
      isCommingle: event.data.isCommingleCargo ? true : false,
      sg: '',
      tankId: event.data.tankId,
      temperature: event.data.temperature.value,
      vesselId: this.vesselId
    };

    this.ngxSpinnerService.show();
    const patternId = this.operation === OPERATIONS.LOADING ? event.data.loadablePatternId : event.data.dischargePatternId;
    const result = await this.ullageUpdateApiService.getUllageQuantity(param, patternId).toPromise();
    this.ngxSpinnerService.hide();
    this.cargoTankForm.markAllAsTouched();
    if (result.responseStatus.status === '200') {
      this.ullageResponseData?.portPlanStowageDetails.map(item => {
        if (item.cargoNominationId === event.data.cargoNominationId && item.tankId === event.data.tankId) {
          item.quantity = result.quantityMt;
          item.actualWeight = result.quantityMt;
          item.correctedUllage = result.correctedUllage;
          item.percentageFilled = result.fillingRatio;
          item.ullage = event.data.ullage.value;
          item.api = event.data.api.value;
          item.temperature = event.data.temperature.value;
        }
      });

      this.cargoQuantities[event.index].quantity.value = result.quantityMt;
      this.cargoQuantities[event.index].fillingPercentage.value = result.fillingRatio;
      const formControls = <FormControl>(<FormArray>this.cargoTankForm.get('dataTable')).at(event.index);
      formControls['controls'][event.field].setErrors(null);
      formControls['controls'].quantity.setValue(result.quantityMt);
      formControls['controls'].fillingPercentage.setValue(result.fillingRatio ? result.fillingRatio : 100);
      formControls['controls'].quantity.updateValueAndValidity();
      formControls['controls'].fillingPercentage.updateValueAndValidity();
      if (result.fillingRatio < 98) {
        formControls['controls'].ullage.updateValueAndValidity();
        formControls['controls'].temperature.updateValueAndValidity();
        formControls['controls'].api.updateValueAndValidity();
      } else if (result.fillingRatio?.toString()?.trim() === '') {
        formControls['controls'][event.field].setErrors({ maxLimit: true });
      } else {
        formControls['controls'][event.field].updateValueAndValidity();
        formControls['controls'].temperature.updateValueAndValidity();
        formControls['controls'].api.updateValueAndValidity();
      }

      this.updateCargoQuantiyData();
      if (this.showAs.id === 2) {
        this.updateCargoTanks();
      }

    } else if (result.responseStatus.status === '325') {
      const formControls = <FormControl>(<FormArray>this.cargoTankForm.get('dataTable')).at(event.index);
      formControls['controls'].fillingPercentage.setValue(100);
      formControls['controls'][event.field].setErrors({ invalidUllage: true });
    }
    this.enableDisableBLFigure();
    setTimeout(() => {
      this.cargoTanks = this.ullageUpdatePopupTransformationService.formatCargoTanks(this.ullageResponseData?.cargoTanks, this.combineCommingleCargoLayout(this.ullageResponseData?.portPlanStowageDetails), this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit);
      this.validateBlFigTable();
    });

  }

  /**
   * Method for ballast quantity update api call
   * @param event
   * @memberof UllageUpdatePopupComponent
   */
  async ballastEditCompleted(event) {
    if (event.data.sounding.value === "" || event.data.sounding.value === "-") {
      return;
    }
    const param = {
      api: '',
      correctedUllage: event.data.sounding.value,
      id: event.data.id,
      isBallast: true,
      sg: '1.025',
      tankId: event.data.tankId,
      temperature: event.data.temperature.value,
      vesselId: this.vesselId
    };
    this.ngxSpinnerService.show();
    const patternId = this.operation === OPERATIONS.LOADING ? event.data.loadablePatternId : event.data.dischargePatternId;
    const result = await this.ullageUpdateApiService.getUllageQuantity(param, patternId).toPromise();
    this.ngxSpinnerService.hide();
    if (result.responseStatus.status === '200') {
      this.ballastQuantities?.map(item => {
        if (item.tankId === event.data.tankId) {
          item.quantity.value = result.quantityMt;
          item.sounding.value = event.data.sounding.value;
          item.percentageFilled = result.fillingRatio;
          item.correctedUllage = result.correctedUllage;
        }
      });
      const formControl = <FormControl>(<FormArray>this.ballastTankForm.get('dataTable')).at(event.index).get('quantity');
      formControl.setValue(result.quantityMt);
      formControl.updateValueAndValidity();
      formControl.updateValueAndValidity();
      formControl.markAllAsTouched();
      formControl.markAsDirty();
      const formControlFilling = <FormControl>(<FormArray>this.ballastTankForm.get('dataTable')).at(event.index).get('fillingPercentage');
      formControlFilling.setValue(result.fillingRatio);
      formControlFilling.updateValueAndValidity();
      formControlFilling.updateValueAndValidity();
      const formControlSounding = <FormControl>(<FormArray>this.ballastTankForm.get('dataTable')).at(event.index).get(event.field);
      formControlSounding.updateValueAndValidity();
      this.ullageResponseData?.portPlanBallastDetails?.map(item => {
        if (item.tankId === event.data.tankId) {
          item.quantity = result.quantityMt;
          item.sounding = result.correctedUllage;
        }
      });

      if (result.fillingRatio?.toString()?.trim() === '') {
        formControlSounding.setErrors({ maxLimit: true });
      }
      setTimeout(() => {
        this.rearBallastTanks = [...this.ullageUpdatePopupTransformationService.formatBallastTanks(this.ullageResponseData?.ballastRearTanks, this.ullageResponseData?.portPlanBallastDetails, this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit)];
        this.centerBallastTanks = [...this.ullageUpdatePopupTransformationService.formatBallastTanks(this.ullageResponseData?.ballastCenterTanks, this.ullageResponseData?.portPlanBallastDetails, this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit)];
        this.frontBallastTanks = [...this.ullageUpdatePopupTransformationService.formatBallastTanks(this.ullageResponseData?.ballastFrontTanks, this.ullageResponseData?.portPlanBallastDetails, this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit)];
      });

    } else if (result.responseStatus.status === '325') {
      const formControlFilling = <FormControl>(<FormArray>this.ballastTankForm.get('dataTable')).at(event.index).get('fillingPercentage');
      formControlFilling.setValue(101);
      formControlFilling.updateValueAndValidity();
      formControlFilling.updateValueAndValidity();
      const formControl = <FormControl>(<FormArray>this.ballastTankForm.get('dataTable')).at(event.index).get(event.field);
      formControl.setErrors({ invalidSounding: true });

    }
  }

  /**
  * Method for bunker setErrors
  * @memberof UllageUpdatePopupComponent
  */
  setErrorBunker(value, tank, index) {
    const formControls = <FormControl>(<FormArray>this.bunkerTankForm.get('dataTable')).at(index);
    if (this.getRobFillingRatio(value, tank) > 95) {
      setTimeout(() => {
        formControls['controls'].quantity.setErrors({ fillingError: true });
        formControls['controls'].quantity.markAsDirty();
        formControls['controls'].quantity.markAsTouched();
      });
    } else {
      setTimeout(() => {
        formControls['controls'].quantity.setErrors(null);
        formControls['controls'].quantity.markAsDirty();
        formControls['controls'].quantity.markAsTouched();
      });
    }
  }

  /**
  * Method for bunker validation check
  * @memberof UllageUpdatePopupComponent
  */
  checkBunkerTankError() {
    this.bunkerTankForm.markAllAsTouched();
    this.bunkerTankForm.markAsDirty();
    this.bunkerTanksList.map((item, index) => {
      this.ullageResponseData?.bunkerRearTanks?.map(el => {
        el?.map(rear => {
          if (rear.id === item.tankId) {
            this.setErrorBunker(item.quantity.value, rear, index);
          }
        })
      });
      this.ullageResponseData?.bunkerTanks?.map(el => {
        el?.map(bunker => {
          if (bunker.id === item.tankId) {
            this.setErrorBunker(item.quantity.value, bunker, index);
          }
        })
      });
    });
  }

  /**
  * Method for bunker quantity check
  * @param event
  * @memberof UllageUpdatePopupComponent
  */
  bunkerEditCompleted(event) {
    let fillingRatioError = false;
    this.bunkerTankForm.markAllAsTouched();
    this.bunkerTankForm.markAsDirty();
    this.ullageResponseData?.bunkerRearTanks?.map(el => {
      el?.map(rear => {
        if (rear.id === event.data.tankId && !fillingRatioError) {
          fillingRatioError = this.getRobFillingRatio(event.data.quantity.value, rear) > 95 ? true : false;
        }
      })
    });
    this.ullageResponseData?.bunkerTanks?.map(el => {
      el?.map(bunker => {
        if (bunker.id === event.data.tankId && !fillingRatioError) {
          fillingRatioError = this.getRobFillingRatio(event.data.quantity.value, bunker) > 95 ? true : false;
        }
      })
    });
    const formControls = <FormControl>(<FormArray>this.bunkerTankForm.get('dataTable')).at(event.index);
    if (fillingRatioError) {
      setTimeout(() => {
        formControls['controls'].quantity.setErrors({ fillingError: true });
        formControls['controls'].quantity.markAsDirty();
        formControls['controls'].quantity.markAsTouched();
      });
    } else {
      setTimeout(() => {
        formControls['controls'].quantity.setErrors(null);
        formControls['controls'].quantity.markAsDirty();
        formControls['controls'].quantity.markAsTouched();
      });
      this.ullageResponseData?.portPlanRobDetails?.map(item => {
        if (item.tankId === event.data.tankId) {
          item.quantity = event.data.quantity.value;
        }
      });

      setTimeout(() => {
        this.bunkerTanks = [...this.ullageUpdatePopupTransformationService.formatBunkerTanks(this.ullageResponseData?.bunkerTanks, this.ullageResponseData?.portPlanRobDetails, this.status)];
        this.rearBunkerTanks = [...this.ullageUpdatePopupTransformationService.formatBunkerTanks(this.ullageResponseData?.bunkerRearTanks, this.ullageResponseData?.portPlanRobDetails, this.status)];
      });
    }
  }

  /**
  * Method for bunker quantity check
  * @param {any} quantity
  * @param {any} row
  * @memberof UllageUpdatePopupComponent
  */
  getRobFillingRatio(quantity, row) {
    const volume = row.density && quantity ? (quantity / row.density) : 0;
    let fillingratio: any = ((volume / Number(row?.fullCapacityCubm)) * 100).toFixed(2);
    if (Number(fillingratio) === 100) {
      fillingratio = 100;
    }
    if (isNaN(fillingratio) || !fillingratio) {
      fillingratio = 0;
    }
    return fillingratio;
  }
  /**
   * Method for cargo row selection for datatable
   * @param event
   * @memberof UllageUpdatePopupComponent
   */
  onRowSelection(event) {
    this.selectedTankId = event.data.tankId;
  }

  /**
   * Method for save ullage update
   * @param validate
   * @memberof UllageUpdatePopupComponent
   */
  async saveUllage(validate) {
    if ((this.status === ULLAGE_STATUS.DEPARTURE && this.tableForm.invalid) || this.cargoTankForm.invalid || this.ballastTankForm.invalid || this.bunkerTankForm.invalid) {
      return;
    }

    const data: IUllageSaveDetails = {
      isValidate: validate,
      billOfLandingList: [],
      ullageUpdList: [],
      billOfLandingListRemove: [],
      ballastUpdateList: [],
      robUpdateList: [],
      commingleUpdateList: []
    }
    if (this.status === ULLAGE_STATUS.DEPARTURE) {
      this.blFigure.items.map(item => {
        item.map(row => {
          data.billOfLandingList.push({
            portId: row.cargo.portId ? row.cargo.portId?.toString() : '',
            cargoId: row.cargo.cargoNominationId ? row.cargo.cargoNominationId?.toString() : '',
            blRefNumber: row.cargo.blRefNo.value ? row.cargo.blRefNo.value?.toString() : '',
            bblAt60f: row.cargo.bbl.value ? row.cargo.bbl.value?.toString() : '',
            quantityLt: row.cargo.lt.value ? row.cargo.lt.value?.toString() : '',
            quantityMt: row.cargo.mt.value ? row.cargo.mt.value?.toString() : '',
            klAt15c: row.cargo.kl.value ? row.cargo.kl.value?.toString() : '',
            api: row.cargo.api.value ? Number(row.cargo.api.value) : '',
            temperature: row.cargo.temp.value ? row.cargo.temp.value : '',
            isUpdate: row.cargo.isNewRow ? false : true,
            id: row.cargo.id ? row.cargo.id.toString() : '',
            isActive: '',
            version: '',
            ...(this.operation === OPERATIONS.LOADING ? { loadingId: this.infoId?.toString() } : { dischargingId: this.infoId?.toString() })
          });
        });
      });

      this.billOfLaddingRemovedList.map(item => {
        data.billOfLandingListRemove.push({
          portId: item.cargo.portId ? item.cargo.portId?.toString() : '',
          cargoId: item.cargo.cargoNominationId ? item.cargo.cargoNominationId?.toString() : '',
          blRefNumber: item.cargo.blRefNo.value ? item.cargo.blRefNo.value?.toString() : '',
          bblAt60f: item.cargo.bbl.value ? item.cargo.bbl.value?.toString() : '',
          quantityLt: item.cargo.lt.value ? item.cargo.lt.value?.toString() : '',
          quantityMt: item.cargo.mt.value ? item.cargo.mt.value?.toString() : '',
          klAt15c: item.cargo.kl.value ? item.cargo.kl.value?.toString() : '',
          api: item.cargo.api.value ? Number(item.cargo.api.value) : '',
          temperature: item.cargo.temp.value ? item.cargo.temp.value : '',
          id: item.cargo.id ? item.cargo.id.toString() : '',
          isUpdate: true,
          isActive: '',
          version: '',
          ...(this.operation === OPERATIONS.LOADING ? { loadingId: this.infoId?.toString() } : { dischargingId: this.infoId?.toString() })
        });
      });
    }
    const duplicate = [];
    this.ullageResponseData?.portPlanStowageDetails.map(item => {
      if (!item.isCommingleCargo) {
        data.ullageUpdList.push({
          tankId: item.tankId?.toString(),
          temperature: item.temperature?.toString(),
          correctedUllage: item.correctedUllage?.toString(),
          ullage: item.ullage?.toString(),
          quantity: item.quantity?.toString(),
          fillingPercentage: item.percentageFilled ? Number(item.percentageFilled) : '',
          cargoNominationId: item.cargoNominationId ? Number(item.cargoNominationId) : '',
          arrival_departutre: this.status === ULLAGE_STATUS.ARRIVAL ? 1 : 2,
          actual_planned: 1,
          correction_factor: item.correctionFactor?.toString(),
          api: item.api ? Number(item.api) : '',
          isUpdate: this.ullageResponseData.isPlannedValues ? false : true,
          port_xid: this.portId,
          port_rotation_xid: this.portRotationId,
          grade: '',
          color_code: item.colorCode,
          abbreviation: item.abbreviation,
          cargoId: item.cargoId,
          ...(this.operation === OPERATIONS.LOADING ? { loadingInformationId: this.infoId?.toString() } : { dischargingInformationId: this.infoId?.toString() })
        });
      } else {
        if (duplicate.indexOf(item.tankId) === -1) {
          duplicate.push(item.tankId);
          data.ullageUpdList.push({
            tankId: item.tankId?.toString(),
            temperature: 0,
            correctedUllage: 0,
            ullage: 0,
            quantity: 0,
            fillingPercentage: '',
            cargoNominationId: '',
            arrival_departutre: this.status === ULLAGE_STATUS.ARRIVAL ? 1 : 2,
            actual_planned: 1,
            correction_factor: 0,
            api: 0,
            isUpdate: this.ullageResponseData.isPlannedValues ? false : true,
            port_xid: this.portId,
            port_rotation_xid: this.portRotationId,
            grade: '',
            color_code: '',
            abbreviation: '',
            cargoId: 0,
            ...(this.operation === OPERATIONS.LOADING ? { loadingInformationId: this.infoId?.toString() } : { dischargingInformationId: this.infoId?.toString() })
          });
        }
      }
    });

    const cargoTanks = [...this.ullageUpdatePopupTransformationService.formatCargoTanks(this.ullageResponseData?.cargoTanks, this.combineCommingleCargoLayout(this.ullageResponseData?.portPlanStowageDetails), this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit).slice(0)];
    data.ullageUpdList?.map(item => {
      if (item.quantity) {
        cargoTanks?.map(el => {
          el?.map(tank => {
            if (Number(tank.id) === Number(item.tankId)) {
              item.fillingPercentage = Number(tank?.commodity?.percentageFilled);
            }
          });
        });
      }
    });

    this.ballastQuantities.map(item => {
      data.ballastUpdateList.push({
        tankId: item.tankId?.toString(),
        temperature: item.temperature.value?.toString(),
        quantity: item.quantity.value?.toString(),
        sounding: item.sounding.value?.toString(),
        correctedUllage: item.correctedUllage?.toString(),
        ullage: item.ullage?.toString(),
        correctionFactor: item.correctionFactor ? item.correctionFactor?.toString() : '',
        filling_percentage: item.fillingPercentage.value?.toString(),
        arrival_departutre: this.status === ULLAGE_STATUS.ARRIVAL ? 1 : 2,
        actual_planned: '1',
        color_code: item.colorCode,
        sg: item.sg ? item.sg?.toString() : '',
        observedM3: '',
        fillingRatio: '',
        portXId: this.portId,
        portRotationXId: this.portRotationId,
        isValidate: '',
        isUpdate: this.ullageResponseData.isPlannedValues ? false : true,
        ...(this.operation === OPERATIONS.LOADING ? { loadingInformationId: this.infoId?.toString() } : { dischargingInformationId: this.infoId?.toString() })
      });
    });
    data.ballastUpdateList?.map(item => {
      this.rearBallastTanks?.map(el => {
        el?.map(tank => {
          if (Number(tank.id) === Number(item.tankId)) {
            item.filling_percentage = tank?.commodity?.percentageFilled;
          }
        });
      });
      this.frontBallastTanks?.map(el => {
        el?.map(tank => {
          if (Number(tank.id) === Number(item.tankId)) {
            item.filling_percentage = tank?.commodity?.percentageFilled;
          }
        });
      });
      this.centerBallastTanks?.map(el => {
        el?.map(tank => {
          if (Number(tank.id) === Number(item.tankId)) {
            item.filling_percentage = tank?.commodity?.percentageFilled;
          }
        });
      });
    });

    this.bunkerTanksList.map(item => {
      data.robUpdateList.push({
        tankId: item.tankId?.toString(),
        quantity: item.quantity.value?.toString(),
        isUpdate: this.ullageResponseData.isPlannedValues ? false : true,
        density: item.density.value?.toString(),
        colour_code: item.colorCode,
        actual_planned: '1',
        arrival_departutre: this.status === ULLAGE_STATUS.ARRIVAL ? 1 : 2,
        portXId: this.portId,
        portRotationXId: this.portRotationId,
        observedM3: '',
        temperature: '',
        correctedUllage: '',
        ullage: '',
        correctionFactor: '',
        fillingRatio: '',
        ...(this.operation === OPERATIONS.LOADING ? { loadingInformationId: this.infoId?.toString() } : { dischargingInformationId: this.infoId?.toString() })
      });
    });

    data.robUpdateList?.map(item => {
      this.bunkerTanks?.map(el => {
        el?.map(tank => {
          if (Number(tank.id) === Number(item.tankId)) {
            item.fillingRatio = this.getRobFillingRatio(tank.commodity.quantity, tank)
          }
        });
      });
      this.rearBunkerTanks?.map(el => {
        el?.map(tank => {
          if (Number(tank.id) === Number(item.tankId)) {
            item.fillingRatio = this.getRobFillingRatio(tank.commodity.quantity, tank)
          }
        });
      });
    });
    data.commingleUpdateList = this.getCommigleSaveData();
    try {
      this.ngxSpinnerService.show();
      let result;
      if (this.operation === OPERATIONS.LOADING) {
        result = await this.ullageUpdateApiService.updateUllage(data).toPromise();
      } else {
        result = await this.ullageUpdateApiService.dischargePlanUpdateUllage(data).toPromise();
      }
      if (result.responseStatus.status === '200' || result.responseStatus.status === 'SUCCESS') {
        const translationKeys = await this.translateService.get(['ULLAGE_UPDATE_SUCCESS_LABEL', 'ULLAGE_UPDATE_SUCCESS_MESSAGE']).toPromise();
        this.messageService.add({ severity: 'success', summary: translationKeys['ULLAGE_UPDATE_SUCCESS_LABEL'], detail: translationKeys['ULLAGE_UPDATE_SUCCESS_MESSAGE'] });
        if (validate && result['processId']) {
          if (this.status === ULLAGE_STATUS.DEPARTURE) {
            this.loadingDischargingTransformationService.setUllageDepartureBtnStatus({ status: ULLAGE_STATUS_TEXT.ULLAGE_UPDATE_PLAN_INPROGRESS, portRotationId: this.portRotationId });
          }
          if (this.status === ULLAGE_STATUS.ARRIVAL) {
            this.loadingDischargingTransformationService.setUllageArrivalBtnStatus({ status: ULLAGE_STATUS_TEXT.ULLAGE_UPDATE_PLAN_INPROGRESS, portRotationId: this.portRotationId });
          }
          this.loadingDischargingTransformationService.validateUllage({ validate: true, processId: result['processId'], status: this.status === ULLAGE_STATUS.ARRIVAL ? 1 : 2, portRotationId: this.portRotationId });
        } else {
          if (this.status === ULLAGE_STATUS.DEPARTURE) {
            this.loadingDischargingTransformationService.setUllageDepartureBtnStatus({ status: ULLAGE_STATUS_TEXT.ULLAGE_UPDATE_PLAN_VERIFICATION_PENDING, portRotationId: this.portRotationId });
          }
          if (this.status === ULLAGE_STATUS.ARRIVAL) {
            this.loadingDischargingTransformationService.setUllageArrivalBtnStatus({ status: ULLAGE_STATUS_TEXT.ULLAGE_UPDATE_PLAN_VERIFICATION_PENDING, portRotationId: this.portRotationId });
          }
        }
        this.ngxSpinnerService.hide();
        this.closePopup.emit(true);
      }
    } catch (e) {
      this.ngxSpinnerService.hide();
    }
  }

  /**
   * Method for setting commingle data save
   * @param data
   * @memberof UllageUpdatePopupComponent
   */
  getCommigleSaveData() {
    const commingle: any = [];
    const commingleUpdateList = [];
    this.ullageResponseData?.portPlanStowageDetails?.map(item => {
      if (item.isCommingleCargo) {
        commingle.push(item);
      }
    });
    const cargoTanks = [...this.ullageUpdatePopupTransformationService.formatCargoTanks(this.ullageResponseData?.cargoTanks, this.combineCommingleCargoLayout(this.ullageResponseData?.portPlanStowageDetails), this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit).slice(0)];
    this.ullageResponseData?.planCommingleDetails?.map(item => {
      const firstCargo = commingle?.filter(com => com.tankId === item.tankId && com.cargoNominationId === item.cargoNomination1Id);
      const secondCargo = commingle?.filter(com => com.tankId === item.tankId && com.cargoNominationId === item.cargoNomination2Id);
      let firstSelected = false;
      let selectedCommingle = null, ignoredCommingle = null;
      if (firstCargo?.length && secondCargo?.length) {
        if (firstCargo[0].quantity > secondCargo[0].quantity) {
          firstSelected = true;
          selectedCommingle = firstCargo[0];
          ignoredCommingle = secondCargo[0];
        } else {
          firstSelected = false;
          selectedCommingle = secondCargo[0];
          ignoredCommingle = firstCargo[0];
        }
      }
      let fillingPertcentage = 0;
      cargoTanks?.map(el => {
        el?.map(tank => {
          if (Number(tank.id) === Number(item.tankId)) {
            fillingPertcentage = Number(tank?.commodity?.percentageFilled);
          }
        });
      });
      commingleUpdateList.push(
        {
          id: item.id,
          tankId: item.tankId,
          quantityMT: selectedCommingle.quantity,
          quantityM3: '',
          api: Number(selectedCommingle.api),
          temperature: Number(selectedCommingle.temperature),
          ullage: Number(selectedCommingle.ullage),
          cargoNomination1Id: item.cargoNomination1Id,
          cargoNomination2Id: item.cargoNomination2Id,
          cargo1Id: item.cargo1Id,
          cargo2Id: item.cargo2Id,
          colorCode: item.colorCode,
          abbreviation: item.grade,
          arrival_departutre: this.status === ULLAGE_STATUS.ARRIVAL ? 1 : 2,
          actual_planned: 1,
          fillingPercentage: fillingPertcentage,
          isUpdate: this.ullageResponseData.isPlannedValues ? false : true,
          quantity1MT: firstSelected ? Number((Number(firstCargo[0].quantity) - Number(secondCargo[0].quantity)).toFixed(2)) : firstCargo[0].quantity,
          quantity2MT: !firstSelected ? Number((Number(secondCargo[0].quantity) - Number(firstCargo[0].quantity)).toFixed(2)) : secondCargo[0].quantity,
          quantity1M3: '',
          quantity2M3: '',
          ullage1: Number(firstCargo[0].ullage),
          ullage2: Number(secondCargo[0].ullage),
          ...(this.operation === OPERATIONS.LOADING ? { loadingInformationId: item.loadingInformationId } : { dischargingInformationId: item.dischargingInformationId })
        }
      )
    });
    return commingleUpdateList;
  }

  /**
   * Method for file upload
   * @param event
   * @memberof UllageUpdatePopupComponent
   */
  async excelUpload(event) {
    if (!event.target?.files?.length) {
      return;
    }
    const translationKeys = await this.translateService.get(['ULLAGE_UPDATE_FILE_UPLOAD_ERROR_LABEL', 'ULLAGE_UPDATE_FILE_UPLOAD_UNSUPPORTED_FILE_ERROR', 'ULLAGE_UPDATE_FILE_UPLOAD_INVALID_CONTENT_ERROR', 'ULLAGE_UPDATE_FILE_UPLOAD_SUCCESS_LABEL', 'ULLAGE_UPDATE_FILE_UPLOAD_SUCCESS_MESSAGE',
      'ULLAGE_UPDATE_FILE_UPLOAD_INVALID_TANK_NAME_ERROR', 'ULLAGE_UPDATE_FILE_UPLOAD_INVALID_ULLAGE_ERROR', 'ULLAGE_UPDATE_FILE_UPLOAD_INVALID_API_ERROR', 'ULLAGE_UPDATE_FILE_UPLOAD_INVALID_TEMPERATURE_ERROR', 'ULLAGE_UPDATE_FILE_UPLOAD_INVALID_WEIGHT_ERROR', 'ULLAGE_UPDATE_FILE_SIZE_ERROR', 'ULLAGE_UPDATE_EXCEL_EMPTY_FILE']).toPromise();
    if (this.allowedFiles.indexOf(event.target?.files[0].name.split('.')[1]) < 0) {
      this.messageService.add({ severity: 'error', summary: translationKeys['ULLAGE_UPDATE_FILE_UPLOAD_ERROR_LABEL'], detail: translationKeys['ULLAGE_UPDATE_FILE_UPLOAD_UNSUPPORTED_FILE_ERROR'] });
      this.file.nativeElement.value = '';
      return;
    }
    if (event.target?.files[0].size > 2000000) {
      this.file.nativeElement.value = '';
      this.messageService.add({ severity: 'error', summary: translationKeys['ULLAGE_UPDATE_FILE_UPLOAD_ERROR_LABEL'], detail: translationKeys['ULLAGE_UPDATE_FILE_SIZE_ERROR'] });
      return;
    }
    const formData: FormData = new FormData();
    formData.append('file', event.target.files[0]);
    formData.append('infoId', this.infoId?.toString());
    formData.append('cargoNominationId', this.selectedCargo?.cargoNominationId);
    formData.append('isLoading', this.operation === OPERATIONS.LOADING ? 'true' : 'false');
    formData.append('vesselId', this.vesselId?.toString());
    const tanks = [];
    this.ullageResponseData?.portPlanStowageDetails?.map(item => {
      if (item.cargoNominationId === this.selectedCargo?.cargoNominationId) {
        tanks.push({ tankId: item.tankId, shortName: item.tankShortName });
      }
    });
    formData.append('tanks', JSON.stringify(tanks));
    try {
      this.ngxSpinnerService.show();
      const result = await this.ullageUpdateApiService.uploadFile(formData).toPromise();
      this.file.nativeElement.value = '';
      this.ngxSpinnerService.hide();
      if (result.responseStatus.status === '200') {
        this.messageService.add({ severity: 'success', summary: translationKeys['ULLAGE_UPDATE_FILE_UPLOAD_SUCCESS_LABEL'], detail: translationKeys['ULLAGE_UPDATE_FILE_UPLOAD_SUCCESS_MESSAGE'] });
        this.ullageResponseData?.portPlanStowageDetails?.map(item => {
          if (item.cargoNominationId === this.selectedCargo?.cargoNominationId) {
            result?.ullageReportResponse?.map(data => {
              if (data.tankId === item.tankId) {
                item.quantity = data.weight;
                item.actualWeight = data.weight;
                item.ullage = data.ullageObserved;
                item.correctedUllage = data.ullageObserved;
                item.temperature = data.temperature;
                item.api = data.api;
              }
            });
          }
        });
        this.setCargoQuantities();
        this.getCargoTankFormGroup();
        this.updateCargoQuantiyData();
        this.validateBlFigTable();
        this.enableDisableBLFigure();
        this.cargoTanks = [...this.ullageUpdatePopupTransformationService.formatCargoTanks(this.ullageResponseData?.cargoTanks, this.combineCommingleCargoLayout(this.ullageResponseData?.portPlanStowageDetails), this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit).slice(0)];
        this.checkFillingError();
      }
    } catch (err) {
      this.ngxSpinnerService.hide();
      this.file.nativeElement.value = '';
      if (err?.error?.errorCode === "ERR-RICO-314") {
        this.messageService.add({ severity: 'error', summary: translationKeys['ULLAGE_UPDATE_FILE_UPLOAD_ERROR_LABEL'], detail: translationKeys['ULLAGE_UPDATE_FILE_UPLOAD_UNSUPPORTED_FILE_ERROR'] });
      } else if (err.error.errorCode === "ERR-RICO-315") {
        this.messageService.add({ severity: 'error', summary: translationKeys['ULLAGE_UPDATE_FILE_UPLOAD_ERROR_LABEL'], detail: translationKeys['ULLAGE_UPDATE_EXCEL_EMPTY_FILE'] });
      } else if (err?.error?.errorCode === "ERR-RICO-316") {
        this.messageService.add({ severity: 'error', summary: translationKeys['ULLAGE_UPDATE_FILE_UPLOAD_ERROR_LABEL'], detail: translationKeys['ULLAGE_UPDATE_FILE_UPLOAD_INVALID_CONTENT_ERROR'] });
      } else if (err?.error?.errorCode === "ERR-RICO-320") {
        this.messageService.add({ severity: 'error', summary: translationKeys['ULLAGE_UPDATE_FILE_UPLOAD_ERROR_LABEL'], detail: translationKeys['ULLAGE_UPDATE_FILE_UPLOAD_INVALID_TANK_NAME_ERROR'] });
      } else if (err?.error?.errorCode === "ERR-RICO-321") {
        this.messageService.add({ severity: 'error', summary: translationKeys['ULLAGE_UPDATE_FILE_UPLOAD_ERROR_LABEL'], detail: translationKeys['ULLAGE_UPDATE_FILE_UPLOAD_INVALID_ULLAGE_ERROR'] });
      } else if (err?.error?.errorCode === "ERR-RICO-322") {
        this.messageService.add({ severity: 'error', summary: translationKeys['ULLAGE_UPDATE_FILE_UPLOAD_ERROR_LABEL'], detail: translationKeys['ULLAGE_UPDATE_FILE_UPLOAD_INVALID_API_ERROR'] });
      } else if (err?.error?.errorCode === "ERR-RICO-323") {
        this.messageService.add({ severity: 'error', summary: translationKeys['ULLAGE_UPDATE_FILE_UPLOAD_ERROR_LABEL'], detail: translationKeys['ULLAGE_UPDATE_FILE_UPLOAD_INVALID_TEMPERATURE_ERROR'] });
      } else if (err?.error?.errorCode === "ERR-RICO-324") {
        this.messageService.add({ severity: 'error', summary: translationKeys['ULLAGE_UPDATE_FILE_UPLOAD_ERROR_LABEL'], detail: translationKeys['ULLAGE_UPDATE_FILE_UPLOAD_INVALID_WEIGHT_ERROR'] });
      }
    }
  }
}
