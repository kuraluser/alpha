import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { AbstractControl, FormArray, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators, FormBuilder } from '@angular/forms';
import { DATATABLE_EDITMODE, DATATABLE_FIELD_TYPE, DATATABLE_SELECTIONMODE, IDataTableColumn, DATATABLE_FILTER_TYPE, DATATABLE_FILTER_MATCHMODE } from '../../../shared/components/datatable/datatable.model';
import { ICargoQuantities, IShipCargoTank, ITankOptions, IVoyagePortDetails, TANKTYPE, IBallastQuantities, IShipBallastTank, IShipBunkerTank } from '../../core/models/common.model';
import { UllageUpdatePopupTransformationService } from './ullage-update-popup-transformation.service';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { QUANTITY_UNIT } from '../../../shared/models/common.model';
import { ICargoDetail, ICargoDetailValueObject, ITankDetailsValueObject, ULLAGE_STATUS, IUllageSaveDetails } from '../models/loading-discharging.model';
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
export class UllageUpdatePopupComponent implements OnInit {

  @Input() loadingInfoId: number;
  @Input() status: ULLAGE_STATUS;
  @Input() vesselId: number;
  @Input() patternId: number;
  @Input() portRotationId: number;

  get selectedTankId(): number {
    return this.selectedTank?.tankId;
  }

  set selectedTankId(tankId: number) {
    if (this.selectedTab === this.tankType.CARGO) {
      this.selectedTank = this.cargoQuantities.find(tank => tank?.tankId === tankId);
      this.cargoTanks.map(el => {
        el.map(tank => {
          if (tank.id === tankId) {
            this.percentageFilled = tank.percentageFilled + '%';
          }
        })
      })
    }
    if (this.selectedTab === this.tankType.BALLAST) {
      this.selectedTank = this.ballastQuantities.find(tank => tank?.tankId === tankId);
    }
    if (this.selectedTab === this.tankType.BUNKER) {
      this.percentageFilled = null;
      this.selectedTank = this.bunkerTanksList.find(tank => tank?.tankId === tankId);
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

  blFigure: any = [];
  cargoQuantityList: any = [];
  bunkerTanksList: any = [];
  ullageResponseData: any;
  ullageResponseDataCopy: any;
  selectedTank: any;
  percentageFilled: string;

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

  cargoTankOptions: ITankOptions = { showFillingPercentage: true, showTooltip: true, isSelectable: false, ullageField: 'correctedUllage', ullageUnit: AppConfigurationService.settings?.ullageUnit, densityField: 'api', weightField: 'actualWeight', commodityNameField: 'abbreviation' };
  ballastTankOptions: ITankOptions = { showFillingPercentage: true, showTooltip: true, isSelectable: false, ullageField: 'correctedUllage', ullageUnit: AppConfigurationService.settings?.ullageUnit, densityField: 'sg', weightField: 'actualWeight', weightUnit: AppConfigurationService.settings.baseUnit };
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
    private translateService: TranslateService
  ) { }

  async ngOnInit(): Promise<void> {
    this.ngxSpinnerService.show();
    try {
      const status = this.status === ULLAGE_STATUS.ARRIVAL ? 'ARR' : 'DEP';
      const data = await this.ullageUpdateApiService.getUllageDetails(1, 4886, 113696, status).toPromise();
      // const data = await this.ullageUpdateApiService.getUllageDetails(this.vesselId, this.patternId, this.portRotationId, status).toPromise();
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
    this.formatCargoQuantity(this.ullageResponseData.cargoQuantityDetails);
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
    this.setBallastQuantities(this.ullageResponseData?.portLoadablePlanBallastDetails);
    this.setBunkerQuantities(this.ullageResponseData?.portLoadablePlanRobDetails);
    this.getCargoTankFormGroup();
    this.getBallastTankFormGroup();
    this.getBunkerTankFormGroup();
    this.updateCargoQuantiyData();
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
      this.cargoTanks = this.ullageUpdatePopupTransformationService.formatCargoTanks(this.ullageResponseDataCopy?.cargoTanks, this.ullageResponseDataCopy?.portLoadablePlanStowageDetails, this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit).slice(0);
    }
  }

  /**
   * Method for updating the cargo quantity table
   *
   * @memberof UllageUpdatePopupComponent
   */
  updateCargoQuantiyData() {
    this.ullageResponseData?.billOfLaddingList.map(bl => {
      let actualQuantity = 0;
      this.ullageResponseData?.portLoadablePlanStowageDetails.map(item => {
        if (bl.cargoNominationId === item.cargoNominationId) {
          actualQuantity += Number(item.quantity);
        }
      });

      let mtQuantity = 0;
      let bblQuantity = 0;
      let klQuantity = 0;
      let ltQuantity = 0;
      this.blFigure['items'].map(item => {
        item.map(blq => {
          if (blq.cargo.cargoNominationId === bl.cargoNominationId) {
            mtQuantity += blq.cargo.mt.value;
            bblQuantity += blq.cargo.bbl.value;
            klQuantity += blq.cargo.kl.value;
            ltQuantity += blq.cargo.lt.value;
          }
        })
      });

      this.cargoQuantityList?.map(cargo => {
        if (cargo.cargoNominationId === bl.cargoNominationId) {
          cargo.actual.bbl = this.quantityPipe.transform(actualQuantity, QUANTITY_UNIT.MT, QUANTITY_UNIT.BBLS, cargo.api);
          cargo.actual.kl = this.quantityPipe.transform(actualQuantity, QUANTITY_UNIT.MT, QUANTITY_UNIT.KL, cargo.api);
          cargo.actual.lt = this.quantityPipe.transform(actualQuantity, QUANTITY_UNIT.MT, QUANTITY_UNIT.LT, cargo.api);
          cargo.actual.mt = isNaN(actualQuantity) ? 0 : Number(Number(actualQuantity).toFixed(2));

          cargo.blFigure.bbl = this.quantityPipe.transform(bblQuantity, QUANTITY_UNIT.MT, QUANTITY_UNIT.BBLS, cargo.api);
          cargo.blFigure.kl = this.quantityPipe.transform(klQuantity, QUANTITY_UNIT.MT, QUANTITY_UNIT.KL, cargo.api);
          cargo.blFigure.lt = this.quantityPipe.transform(ltQuantity, QUANTITY_UNIT.MT, QUANTITY_UNIT.LT, cargo.api);;
          cargo.blFigure.mt = isNaN(mtQuantity) ? 0 : Number(Number(mtQuantity).toFixed(2));

          cargo.difference.bbl = this.decimalPipe.transform((cargo.actual.bbl - cargo.blFigure.bbl), AppConfigurationService.settings.quantityNumberFormatBBLS);
          cargo.difference.bbl = cargo.difference.bbl ? Number(cargo.difference.bbl.replace(',', '')) : 0;
          cargo.difference.kl = this.decimalPipe.transform((cargo.actual.kl - cargo.blFigure.kl), AppConfigurationService.settings.quantityNumberFormatKL);
          cargo.difference.kl = cargo.difference.kl ? Number(cargo.difference.kl.replace(',', '')) : 0;
          cargo.difference.lt = this.decimalPipe.transform((cargo.actual.lt - cargo.blFigure.lt), AppConfigurationService.settings.quantityNumberFormatLT);
          cargo.difference.lt = cargo.difference.lt ? Number(cargo.difference.lt.replace(',', '')) : 0;
          cargo.difference.mt = this.decimalPipe.transform((cargo.actual.mt - cargo.blFigure.mt), AppConfigurationService.settings.quantityNumberFormatMT);
          cargo.difference.mt = cargo.difference.mt ? Number(cargo.difference.mt.replace(',', '')) : 0;

          cargo.diffPercentage.bbl = Number(((cargo.difference.bbl / cargo.actual.bbl) * 100).toFixed(2));
          cargo.diffPercentage.bbl = isNaN(cargo.diffPercentage.bbl) ? 0 : cargo.diffPercentage.bbl;
          cargo.diffPercentage.kl = Number(((cargo.difference.kl / cargo.actual.kl) * 100).toFixed(2));
          cargo.diffPercentage.kl = isNaN(cargo.diffPercentage.kl) ? 0 : cargo.diffPercentage.kl;
          cargo.diffPercentage.lt = Number(((cargo.difference.lt / cargo.actual.lt) * 100).toFixed(2));
          cargo.diffPercentage.lt = isNaN(cargo.diffPercentage.lt) ? 0 : cargo.diffPercentage.lt;
          cargo.diffPercentage.mt = Number(((cargo.difference.mt / cargo.actual.mt) * 100).toFixed(2));
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
    this.cargoQuantityList.map(item => {
      const differenecPercentage = item.diffPercentage.mt;
      this.cargoTanks.map(tank => {
        tank.map(elem => {
          if (elem?.commodity && elem?.commodity['cargoNominationId'] === item.cargoNominationId) {
            const changeInQty = (differenecPercentage / 100) * Number(elem.commodity.quantity);
            elem.commodity.quantity = differenecPercentage >= 0 ? Number(elem.commodity.quantity) + changeInQty : Number(elem.commodity.quantity) - changeInQty;
            elem.commodity.actualWeight = elem.commodity.quantity;
            elem.commodity.volume = this.quantityPipe.transform(elem.commodity.actualWeight, this.currentQuantitySelectedUnit, AppConfigurationService.settings.volumeBaseUnit, elem.commodity.api);
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
      this.bunkerTanksList.push(this.ullageUpdatePopupTransformationService.getFormatedTankDetailsBunker(item, true, true))
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
      this.ballastQuantities.push(this.ullageUpdatePopupTransformationService.getFormatedTankDetailsBallast(item, true, true))
    });
  }

  /**
   * Method for seting cargo quantities
   *
   * @memberof UllageUpdatePopupComponent
   */
  setCargoQuantities() {
    this.cargoQuantities = [];
    this.ullageResponseData?.portLoadablePlanStowageDetails?.map(item => {
      if (this.selectedCargo.cargoNominationId === item.cargoNominationId) {
        this.cargoQuantities.push(this.ullageUpdatePopupTransformationService.getFormatedTankDetailsCargo(item, true, true))
      }
    });
  }

  /**
   * Method for initializing cargo formGroup
   *
   * @memberof UllageUpdatePopupComponent
   */
  getCargoTankFormGroup() {
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
      quantity: this.fb.control(tank.quantity.value),
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
      sounding: this.fb.control(tank.sounding.value, [tankCapacityValidator(null, null, 'sounding', 'fillingPercentage')]),
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
      ullage: this.fb.control(tank.ullage.value, [tankCapacityValidator(null, null, 'ullage', 'fillingPercentage')]),
      temperature: this.fb.control(tank.temperature.value, [tankCapacityValidator(null, null, 'temperature', 'fillingPercentage')]),
      api: this.fb.control(tank.api.value, [tankCapacityValidator(null, null, 'api', 'fillingPercentage')]),
      quantity: this.fb.control(tank.quantity.value),
      fillingPercentage: this.fb.control(tank.fillingPercentage.value)
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
      this.cargoQuantityList.push(this.ullageUpdatePopupTransformationService.formatCargoQuantity(item));
    });
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

    this.bunkerTanks = this.ullageUpdatePopupTransformationService.formatBunkerTanks(this.ullageResponseData?.bunkerTanks, this.ullageResponseData?.portLoadablePlanRobDetails, this.status);
    this.rearBunkerTanks = this.ullageUpdatePopupTransformationService.formatBunkerTanks(this.ullageResponseData?.bunkerRearTanks, this.ullageResponseData?.portLoadablePlanRobDetails, this.status);
    this.cargoTanks = this.ullageUpdatePopupTransformationService.formatCargoTanks(this.ullageResponseData?.cargoTanks, this.ullageResponseData?.portLoadablePlanStowageDetails, this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit);
    this.rearBallastTanks = this.ullageUpdatePopupTransformationService.formatBallastTanks(this.ullageResponseData?.ballastRearTanks, this.ullageResponseData?.portLoadablePlanBallastDetails, this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit);
    this.centerBallastTanks = this.ullageUpdatePopupTransformationService.formatBallastTanks(this.ullageResponseData?.ballastCenterTanks, this.ullageResponseData?.portLoadablePlanBallastDetails, this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit);
    this.frontBallastTanks = this.ullageUpdatePopupTransformationService.formatBallastTanks(this.ullageResponseData?.ballastFrontTanks, this.ullageResponseData?.portLoadablePlanBallastDetails, this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit);
    this.fuelTypes = [...new Map(this.ullageResponseData.portLoadablePlanRobDetails.map(item => [item['id'], { id: item?.id, name: item?.tankName, colorCode: item?.colorCode, shortName: item?.tankShortName }])).values()];

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
    return formControl?.invalid && (formControl?.dirty || formControl?.touched) ? formControl.errors : null;
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
      const cargoData = [];
      if (item?.billOfLaddings?.length) {
        item?.billOfLaddings?.map(bl => {
          cargoData.push({ 'cargo': this.ullageUpdatePopupTransformationService.getFormatedCargoDetails(bl, item, false, false) });
        });
      } else {
        cargoData.push({ 'cargo': this.ullageUpdatePopupTransformationService.getFormatedCargoDetails(<ICargoDetail>{}, item, true, true) });
      }
      this.blFigure['items'].push(cargoData);
    });
    this.blFigure.items.map((blItems, blRowIndex) => {
      const items = this.tableForm.get('items') as FormArray;
      items.push(this.initItems())
      blItems.forEach((cargoDetails, index) => {
        this.addCargoItems(blRowIndex, cargoDetails.cargo);
      });
    })
    this.calculateTotal();
    this.enableDisableBLFigure();
  }

  /**
   * Method for enable and disable bl figure fields
   *
   * @memberof UllageUpdatePopupComponent
  */
  enableDisableBLFigure() {
    this.ullageResponseData?.billOfLaddingList?.map(item => {
      let totQuantity = 0;
      this.ullageResponseData?.portLoadablePlanStowageDetails.map(stowage => {
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
  newCargo(rowIndex: number, colIndex: number) {
    if (this.getCargoItems(rowIndex).disabled) { return; }
    this.blFigure.items[rowIndex].push({ cargo: this.ullageUpdatePopupTransformationService.getFormatedCargoDetails(<ICargoDetail>{}, this.ullageResponseData?.billOfLaddingList[rowIndex], true, true) });
    this.getCargoItems(rowIndex).push(this.initCargoDetails(this.blFigure.items[rowIndex][this.blFigure.items[rowIndex].length - 1].cargo));
  }

  /**
   * Delete Cargo
   * @param {number} rowIndex
   * @param {number} colIndex
   * @memberof UllageUpdatePopupComponent
  */
  deleteCargo(rowIndex: number, colIndex: number) {
    const removedItem = this.blFigure.items[rowIndex].splice(colIndex, 1);
    if (removedItem?.length && !removedItem[0].cargo.isAdd) {
      this.billOfLaddingRemovedList.push(removedItem[0]);
    }
    this.getCargoItems(rowIndex).removeAt(colIndex);
    this.calculateTotal();
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
      blRefNo: this.fb.control(cargo.blRefNo.value, [Validators.required, Validators.minLength(4), Validators.maxLength(12), Validators.pattern(/^[ A-Za-z0-9#&()/":-=+*]*$/)]),
      bbl: this.fb.control(cargo.bbl.value, [Validators.required]),
      lt: this.fb.control(cargo.lt.value, [Validators.required]),
      mt: this.fb.control(cargo.mt.value, [Validators.required]),
      kl: this.fb.control(cargo.kl.value, [Validators.required]),
      api: this.fb.control(cargo.api.value, [Validators.required, Validators.min(0), numberValidator(2, 3)]),
      temp: this.fb.control(cargo.temp.value, [Validators.required, numberValidator(2, 3)]),
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
  * Handler for blur event
  * @param event
  * @param rowData
  * @param rowIndex
  * @param col
  * @param colIndex
  */
  onEditComplete(event: any, rowData: any, key: string, rowIndex: number, index: number) {
    rowData[key]['value'] = event.target.value;
    const control = this.getControl(rowIndex, index, key);
    if (control.valid && rowData['isAdd']) {
      rowData[key].isEditMode = false;
    }
    this.calculateTotal();
    this.updateCargoQuantiyData();
    if (this.showAs.id === 2) {
      this.updateCargoTanks();
    }

    let mtQty = 0, bblQty = 0, klQty = 0, ltQty = 0;
    this.blFigure?.items?.map(item => {
      item.map(cargo => {
        if (cargo.cargo.cargoNominationId === rowData.cargoNominationId) {
          mtQty += Number(cargo.cargo.mt.value);
          bblQty += Number(cargo.cargo.bbl.value);
          klQty += Number(cargo.cargo.kl.value);
          ltQty += Number(cargo.cargo.lt.value);
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
        if (mtQty > (Number(cargoQuantityData?.actual?.mt) * 0.1) + Number(cargoQuantityData?.actual?.mt)
          || mtQty < (Number(cargoQuantityData?.actual?.mt) * 0.1) - Number(cargoQuantityData?.actual?.mt)) {
          control.setErrors({ rangeError: true });
        } else {
          control.setErrors(null);
        }
        break;
      case 'bbl':
        if (bblQty > (Number(cargoQuantityData?.actual?.bbl) * 0.1) + Number(cargoQuantityData?.actual?.bbl)
          || bblQty < (Number(cargoQuantityData?.actual?.bbl) * 0.1) - Number(cargoQuantityData?.actual?.bbl)) {
          control.setErrors({ rangeError: true });
        } else {
          control.setErrors(null);
        }
        break;
      case 'kl':
        if (klQty > (Number(cargoQuantityData?.actual?.kl) * 0.1) + Number(cargoQuantityData?.actual?.kl)
          || klQty < (Number(cargoQuantityData?.actual?.kl) * 0.1) - Number(cargoQuantityData?.actual?.kl)) {
          control.setErrors({ rangeError: true });
        } else {
          control.setErrors(null);
        }
        break;
      case 'lt':
        if (ltQty > (Number(cargoQuantityData?.actual?.lt) * 0.1) + Number(cargoQuantityData?.actual?.lt)
          || ltQty < (Number(cargoQuantityData?.actual?.lt) * 0.1) - Number(cargoQuantityData?.actual?.lt)) {
          control.setErrors({ rangeError: true });
        } else {
          control.setErrors(null);
        }
        break;
      default:
        control.setErrors(null);
    }

  }

  /**
   * Method for cargo quantity update api call
   * @param event
   * @memberof UllageUpdatePopupComponent
   */
  async cargoEditCompleted(event) {
    const param = {
      api: event.data.api.value,
      correctedUllage: event.data.ullage.value,
      id: event.data.id,
      isBallast: false,
      isCommingle: false,
      sg: '',
      tankId: event.data.tankId,
      temperature: event.data.temperature.value
    };
    this.ngxSpinnerService.show();
    const result = await this.ullageUpdateApiService.getUllageQuantity(param, event.data.loadablePatternId).toPromise();
    this.ngxSpinnerService.hide();
    if (result.responseStatus.status === '200') {
      this.ullageResponseData?.portLoadablePlanStowageDetails.map(item => {
        if (item.cargoNominationId === event.data.cargoNominationId) {
          item.quantity = result.quantityMt;
          item.correctedUllage = result.correctedUllage;
          item.percentageFilled = result.fillingRatio;
        }
      });
      this.cargoQuantities[event.index].quantity.value = result.quantityMt;
      this.cargoQuantities[event.index].fillingPercentage.value = result.fillingRatio;
      const formControls = <FormControl>(<FormArray>this.cargoTankForm.get('dataTable')).at(event.index);
      formControls['controls'].quantity.setValue(result.quantityMt);
      formControls['controls'].fillingPercentage.setValue(result.fillingRatio);
      formControls['controls'].quantity.updateValueAndValidity();
      formControls['controls'].fillingPercentage.updateValueAndValidity();
      formControls['controls'][event.field].updateValueAndValidity();

      this.updateCargoQuantiyData();
      if (this.showAs.id === 2) {
        this.updateCargoTanks();
      }

    }
    this.enableDisableBLFigure();
  }

  /**
   * Method for ballast quantity update api call
   * @param event
   * @memberof UllageUpdatePopupComponent
   */
  async ballastEditCompleted(event) {
    const param = {
      api: '',
      correctedUllage: event.data.sounding.value,
      id: event.data.id,
      isBallast: true,
      sg: '1.025',
      tankId: event.data.tankId,
      temperature: event.data.temperature
    };
    this.ngxSpinnerService.show();
    const result = await this.ullageUpdateApiService.getUllageQuantity(param, event.data.loadablePatternId).toPromise();
    this.ngxSpinnerService.hide();
    if (result.responseStatus.status === '200') {
      this.ballastQuantities?.map(item => {
        if (item.tankId === event.data.tankId) {
          item.quantity.value = result.quantityMt;
          item.sounding.value = result.correctedUllage;
          item.ullage.value = result.correctedUllage;
        }
      });
      const formControl = <FormControl>(<FormArray>this.ballastTankForm.get('dataTable')).at(event.index).get('quantity');
      formControl.setValue(result.quantityMt);
      formControl.updateValueAndValidity();
      formControl['controls'][event.field].updateValueAndValidity();

    }
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
    if (this.tableForm.invalid || this.cargoTankForm.invalid || this.ballastTankForm.invalid || this.bunkerTankForm.invalid) {
      return;
    }
    const data: IUllageSaveDetails = {
      isValidate: validate,
      billOfLandingList: [],
      ullageUpdList: [],
      billOfLandingListRemove: [],
      ballastUpdateList: [],
      robUpdateList: []
    }
    this.blFigure.items.map(item => {
      item.map(row => {
        data.billOfLandingList.push(
          {
            loadingId: this.loadingInfoId,
            portId: row.cargo.portId,
            cargoId: row.cargo.cargoId,
            blRefNumber: row.cargo.blRefNo.value,
            bblAt60f: row.cargo.bbl.value,
            quantityLt: row.cargo.lt.value,
            quantityMt: row.cargo.mt.value,
            klAt15c: row.cargo.kl.value,
            api: row.cargo.api.value,
            temperature: row.cargo.temp.value,
            isUpdate: row.cargo.isAdd ? false : true,
            isActive: '',
            version: ''
          }
        )
      });
    });

    this.ullageResponseData?.portLoadablePlanStowageDetails.map(item => {
      data.ullageUpdList.push({
        loadingInformationId: this.loadingInfoId,
        tankId: item.tankId,
        temperature: item.temperature,
        correctedUllage: item.correctedUllage,
        quantity: item.quantity,
        fillingPercentage: item.percentageFilled,
        cargo_nomination_xid: item.cargoNominationId,
        arrival_departutre: item.arrivalDeparture,
        actual_planned: 1,
        correction_factor: item.correctionFactor,
        api: item.api,
        isUpdate: true,
        port_xid: '',
        port_rotation_xid: '',
        grade: '',

      })
    });

    this.billOfLaddingRemovedList.map(item => {
      data.billOfLandingListRemove.push(
        {
          loadingId: this.loadingInfoId,
          portId: item.portId,
          cargoId: item.cargoId,
          blRefNumber: item.blRefNo.value,
          bblAt60f: item.bbl.value,
          quantityLt: item.lt.value,
          quantityMt: item.mt.value,
          klAt15c: item.kl.value,
          api: item.api.value,
          temperature: item.temp.value,
          isUpdate: true,
          isActive: '',
          version: ''
        }
      )
    });
    this.ballastQuantities.map(item => {
      data.ballastUpdateList.push({
        loadingInformationId: this.loadingInfoId,
        tankId: item.tankId,
        temperature: item.temperature.value,
        quantity: item.quantity.value,
        sounding: item.sounding.value,
        correctedUllage: item.correctedUllage,
        correctionFactor: item.correctionFactor,
        filling_percentage: item.fillingPercentage.value,
        arrival_departutre: item.arrivalDeparture,
        actual_planned: 1,
        color_code: item.colorCode,
        sg: item.sg,
        observedM3: '',
        fillingRatio: '',
        port_xid: '',
        port_rotation_xid: '',
        isUpdate: true
      });
    });

    this.bunkerTanksList.map(item => {
      data.robUpdateList.push({
        loadingInformationId: this.loadingInfoId,
        tankId: item.tankId,
        quantity: item.quantity.value,
        isUpdate: true,
        density: item.density.value,
        colour_code: item.colorCode,
        actual_planned: 1,
        arrival_departutre: item.arrivalDeparture,
        port_xid: '',
        port_rotation_xid: '',
        observedM3: '',
        temperature: '',
        correctedUllage: '',
        correctionFactor: '',
        fillingRatio: '',
      })
    });
    try {
      this.ngxSpinnerService.show();
      const result = await this.ullageUpdateApiService.updateUllage(data).toPromise();
      const translationKeys = await this.translateService.get(['ULLAGE_UPDATE_SUCCESS_LABEL', 'ULLAGE_UPDATE_SUCCESS_MESSAGE']).toPromise();
      this.messageService.add({ severity: 'success', summary: translationKeys['ULLAGE_UPDATE_SUCCESS_LABEL'], detail: translationKeys['ULLAGE_UPDATE_SUCCESS_MESSAGE'] });
      if (validate) {
        this.loadingDischargingTransformationService.validateUllage({ validate: true, processId: '3058eeb3-6001-4c11-ba1e-03a6899ed655', status: this.status === ULLAGE_STATUS.ARRIVAL ? 1 : 2 });
      }
      setTimeout(() => {
        this.ngxSpinnerService.hide();
        this.closePopup.emit(true);
      }, 500);
    } catch (e) {
      this.ngxSpinnerService.hide();
    }
  }
}
