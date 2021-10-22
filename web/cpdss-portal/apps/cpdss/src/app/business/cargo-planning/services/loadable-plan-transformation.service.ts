import { Injectable } from '@angular/core';
import { BehaviorSubject, Subject } from 'rxjs';

import { IBallastStowageDetails, IBallastTank, ICargoTank, ILoadableQuantityCargo } from '../../core/models/common.model';
import { CargoPlanningModule } from '../cargo-planning.module';
import { ICargoTankDetail, ILoadableQuantityCommingleCargo, ICommingleCargoDispaly,  ICargoTankDetailValueObject, ISynopticalRecordArrangeModel , IBallastTankDetailValueObject , IValidateSaveStatus } from '../models/loadable-plan.model';
import { DATATABLE_FIELD_TYPE, IDataTableColumn } from '../../../shared/components/datatable/datatable.model';
import { QUANTITY_UNIT, ValueObject , ISubTotal } from '../../../shared/models/common.model';
import { QuantityPipe } from '../../../shared/pipes/quantity/quantity.pipe';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { ILoadablePlanSynopticalRecord } from '../models/cargo-planning.model';
import { QuantityDecimalFormatPipe } from '../../../shared/pipes/quantity-decimal-format/quantity-decimal-format.pipe'; 

/**
 * Transformation Service for Lodable Plan details module
 *
 * @export
 * @class LoadablePlanTransformationService
*/

@Injectable({
  providedIn: CargoPlanningModule
})

export class LoadablePlanTransformationService {

  private quantityPipe: QuantityPipe = new QuantityPipe();
  public baseUnit = AppConfigurationService.settings.baseUnit;
  private savedComments = new Subject();
  private editBallastStatus = new BehaviorSubject<IValidateSaveStatus>({validateAndSaveProcessing: false});

  public savedComments$ = this.savedComments.asObservable();
  public editBallastStatus$ = this.editBallastStatus.asObservable();
  constructor(private quantityDecimalFormatPipe: QuantityDecimalFormatPipe) { }

  /**
  *
  * Get loadable quantity table header
  * @returns {IDataTableColumn[]}
  */
  public getEtaEtdTableColumns(): IDataTableColumn[] {
    return [
      { field: 'portName', header: 'ETA_ETD_PORT' },
      { field: 'etaEtdPlanned', header: 'ETA_ETD_DATE' },
      {
        field: 'finalDraftFwd', header: 'ETA_ETD_DRAFT', rowspan: 3, subHeader: 'ETA_ETD_DRAFT_FORE'
      },
      { field: 'finalDraftAft', header: "", subHeader: 'ETA_ETD_DRAFT_AFT' },
      { field: 'finalDraftMid', header: "", subHeader: 'ETA_ETD_DRAFT_MID' },
      { field: 'calculatedTrimPlanned', header: 'ETA_ETD_TRIM' },
      { field: 'bm', header: 'ETA_ETD_BM' },
      { field: 'sf', header: 'ETA_ETD_SF' },
      { field: 'list', header: 'ETA_ETD_LIST'},
      { field: 'cargoPlannedTotal', header: 'ETA_ETD_CARGO' },
      { field: 'plannedFOTotal', header: 'ETA_ETD_FO' },
      { field: 'plannedDOTotal', header: 'ETA_ETD_DO' },
      { field: 'ballastPlanned', header: 'ETA_ETD_BALLAST' },
      { field: 'plannedFWTotal', header: 'ETA_ETD_FRESH_WATER' },
      { field: 'othersPlanned', header: 'ETA_ETD_OTHERS' },
      { field: 'totalDwtPlanned', header: 'ETA_ETD_TOTAL_DWT' },
      { field: 'displacementPlanned', header: 'ETA_ETD_DISPLACEMENT' },
      { field: 'specificGravity', header: 'ETA_ETD_DENSITY' },
    ]
  }


  /**
*
* GetCommingled Cargo Table Column
* @returns {IDataTableColumn[]}
*/
  public getCommingledCargoTableColumn(): IDataTableColumn[] {
    return [
      { field: 'grade', header: 'LOADABLE_PLAN_COMMINGLED_CARGO_GRADE', rowspan: 2 },
      { field: 'tankShortName', header: 'LOADABLE_PLAN_COMMINGLED_CARGO_TANK', rowspan: 2 },
      {
        header: 'LOADABLE_PLAN_COMMINGLED_CARGO_QUANTITY', colspan: 2, fieldColumnClass: "th-border", subColumns: [
          { field: 'quantity', header: 'LOADABLE_PLAN_COMMINGLED_CARGO_COMPOSITION_MT', rowspan: 2 },
          { field: 'quantityBLS', header: 'LOADABLE_PLAN_CARGO_GRID_OBSERVED_BBL_AT_60F', rowspan: 2 }
        ]
      },
      { field: 'api', header: 'LOADABLE_PLAN_COMMINGLED_CARGO_API', rowspan: 2 },
      { field: 'temp', header: 'LOADABLE_PLAN_COMMINGLED_CARGO_TEMP', rowspan: 2 },
      {
        header: 'LOADABLE_PLAN_COMMINGLED_CARGO_COMPOSITION_BREAKDOWN', colspan: 6, fieldColumnClass: "th-border", subColumns: [
          { field: 'cargoPercentage', header: 'LOADABLE_PLAN_COMMINGLED_CARGO_COMPOSITION_BREAKDOWN_PERCENTAGE' },
          { field: 'cargoBblsdbs', header: 'LOADABLE_PLAN_COMMINGLED_CARGO_COMPOSITION_BREAKDOWN_BBLS@DBS.TEMP' },
          { field: 'cargoBbls60f', header: 'LOADABLE_PLAN_COMMINGLED_CARGO_COMPOSITION_BREAKDOWN_BBL@60F' },
          { field: 'cargoLT', header: 'LOADABLE_PLAN_COMMINGLED_CARGO_COMPOSITION_LT' },
          { field: 'cargoMT', header: 'LOADABLE_PLAN_COMMINGLED_CARGO_COMPOSITION_MT' },
          { field: 'cargoKL', header: 'LOADABLE_PLAN_COMMINGLED_CARGO_COMPOSITION_KL' }
        ]
      }
    ];
  }

  /**
  *
  * Get loadable quantity table header
  * @returns {IDataTableColumn[]}
  */
  getLoadableQuantityTableColumns(): IDataTableColumn[] {
    return [
      { field: 'grade', header: 'LOADABLE_PLAN_GRADE', rowspan: 2 },
      {
        header: 'LOADABLE_PLAN_ESTIMATED', colspan: 2, fieldColumnClass: "th-border", subColumns: [
          { field: 'estimatedAPI', header: 'LOADABLE_PLAN_ESTIMATED_API', fieldClass: 'text-right' },
          { field: 'estimatedTemp', header: 'LOADABLE_PLAN_ESTIMATED_TEMP', fieldClass: 'text-right' }
        ]
      },
      {
        header: 'LOADABLE_PLAN_ORDER', colspan: 2, fieldColumnClass: "th-border", subColumns: [
          { field: 'orderedQuantity', header: 'LOADABLE_PLAN_ORDER_BBLS@MT' , unit: QUANTITY_UNIT.MT , numberFormat: 'quantity', fieldClass: 'text-right'},
          { field: 'orderBbls60f', header: 'LOADABLE_PLAN_ORDER_BBLS@60F' , unit: QUANTITY_UNIT.BBLS, numberFormat: 'quantity', fieldClass: 'text-right'}
        ]
      },
      {
        header: 'LOADABLE_PLAN_TLRNC', colspan: 2, fieldColumnClass: "th-border", subColumns: [
          { field: 'minTolerence', header: 'LOADABLE_PLAN_TLRNC_MIN', fieldClass: 'text-right' },
          { field: 'maxTolerence', header: 'LOADABLE_PLAN_TLRNC_MAX', fieldClass: 'text-right' }
        ]
      },
      {
        header: 'LOADABLE_PLAN_LOADABLE', colspan: 5, fieldColumnClass: "th-border", subColumns: [
          { field: 'loadableBblsdbs', header: 'LOADABLE_PLAN_LOADABLE_BBLS@DBS_TEMP' , unit: QUANTITY_UNIT.OBSBBLS , numberFormat: 'quantity', fieldClass: 'text-right'},
          { field: 'loadableBbls60f', header: 'LOADABLE_PLAN_LOADABLE_BBLS@60F' , unit: QUANTITY_UNIT.BBLS , numberFormat: 'quantity', fieldClass: 'text-right'},
          { field: 'loadableLT', header: 'LOADABLE_PLAN_LOADABLE_LT' , unit: QUANTITY_UNIT.LT , numberFormat: 'quantity', fieldClass: 'text-right'},
          { field: 'loadableMT', header: 'LOADABLE_PLAN_LOADABLE_MT', unit: QUANTITY_UNIT.MT , numberFormat: 'quantity', fieldClass: 'text-right' },
          { field: 'loadableKL', header: 'LOADABLE_PLAN_LOADABLE_KL', unit: QUANTITY_UNIT.KL , numberFormat: 'quantity', fieldClass: 'text-right' }
        ]
      },
      {
        field: 'differencePercentage', header: 'LOADABLE_PLAN_LOADABLE_DIFF', rowspan: 2, fieldClass: 'text-right'
      }
    ]
  }

  /**
  *
  * Get Formated Loadable Quantity Data
  * @returns {ILoadableQuantityCargo}
  * @param loadableQuantityObject
  */
  public getFormatedLoadableQuantityData(_decimalPipe: any, loadableQuantity: ILoadableQuantityCargo): ILoadableQuantityCargo {
    const _loadableQuantityDetails = loadableQuantity;
    _loadableQuantityDetails.estimatedAPI = this.decimalConvertion(_decimalPipe, _loadableQuantityDetails.estimatedAPI, '1.2-2');
    _loadableQuantityDetails.estimatedTemp = this.decimalConvertion(_decimalPipe, _loadableQuantityDetails.estimatedTemp, '1.2-2');
    _loadableQuantityDetails.orderBbls60f = this.convertQuantityLoadable(_loadableQuantityDetails, QUANTITY_UNIT.BBLS, 'orderedQuantity',-1)?.toString();
    _loadableQuantityDetails.orderBblsdbs = this.convertQuantityLoadable(_loadableQuantityDetails, QUANTITY_UNIT.OBSBBLS, 'orderedQuantity',-1)?.toString();
    _loadableQuantityDetails.orderedQuantity = _loadableQuantityDetails.orderedQuantity;
    _loadableQuantityDetails.minTolerence = _loadableQuantityDetails.minTolerence + '%';
    _loadableQuantityDetails.maxTolerence = _loadableQuantityDetails.maxTolerence + '%';
    _loadableQuantityDetails.loadableBbls60f = this.convertQuantityLoadable(_loadableQuantityDetails, QUANTITY_UNIT.BBLS, 'loadableMT',-1)?.toString();
    _loadableQuantityDetails.loadableBblsdbs = this.convertQuantityLoadable(_loadableQuantityDetails, QUANTITY_UNIT.OBSBBLS, 'loadableMT',-1)?.toString();
    _loadableQuantityDetails.loadableKL = this.convertQuantityLoadable(_loadableQuantityDetails, QUANTITY_UNIT.KL, 'loadableMT',-1)?.toString();
    _loadableQuantityDetails.loadableLT = this.convertQuantityLoadable(_loadableQuantityDetails, QUANTITY_UNIT.LT, 'loadableMT',-1)?.toString();
    _loadableQuantityDetails.loadableMT = _loadableQuantityDetails.loadableMT;
    _loadableQuantityDetails.differencePercentageValue = Number(_loadableQuantityDetails.differencePercentage.replace('%', ''))
    _loadableQuantityDetails.differencePercentage = this.decimalConvertion(_decimalPipe, _loadableQuantityDetails.differencePercentageValue, '1.2-2');
    return loadableQuantity;
  }

  /**
  *
  * Get Formated Loadable Quantity Data
  * @returns {decimal converted value us number}
  */
  decimalConvertion(_decimalPipe: any, value: string | number, decimalType: string) {
    return _decimalPipe.transform(value, decimalType);
  }

  /**
  *
  * Get Formated Loadable Quantity Data
  * @returns { ICommingleCargoDispaly }
  * @param loadablePlanCommingleCargoDetails
  */
  public getFormatedLoadableCommingleCargo(_decimalPipe: any, loadablePlanCommingleCargoDetails: ILoadableQuantityCommingleCargo): ICommingleCargoDispaly {
    const _loadablePlanCommingleCargoDetails = <ICommingleCargoDispaly>{};
    _loadablePlanCommingleCargoDetails.api = this.decimalConvertion(_decimalPipe, loadablePlanCommingleCargoDetails.api, '1.2-2');
    _loadablePlanCommingleCargoDetails.tankName = loadablePlanCommingleCargoDetails.tankName;
    _loadablePlanCommingleCargoDetails.grade = loadablePlanCommingleCargoDetails.grade;
    _loadablePlanCommingleCargoDetails.quantityBLS = this.decimalConvertion(_decimalPipe, this.convertQuantityCommingle(loadablePlanCommingleCargoDetails, QUANTITY_UNIT.BBLS, 'quantity'), '1.0-0');
    _loadablePlanCommingleCargoDetails.quantity = this.quantityDecimalFormatPipe.transform(loadablePlanCommingleCargoDetails.quantity, QUANTITY_UNIT.MT);
    _loadablePlanCommingleCargoDetails.temp = this.decimalConvertion(_decimalPipe, loadablePlanCommingleCargoDetails.temp, '1.2-2');
    loadablePlanCommingleCargoDetails.cargo1Bbls60f = this.convertQuantityCommingle(loadablePlanCommingleCargoDetails, QUANTITY_UNIT.BBLS, 'cargo1MT')?.toString();
    loadablePlanCommingleCargoDetails.cargo2Bbls60f = this.convertQuantityCommingle(loadablePlanCommingleCargoDetails, QUANTITY_UNIT.BBLS, 'cargo2MT')?.toString();
    loadablePlanCommingleCargoDetails.cargo1Bblsdbs = this.convertQuantityCommingle(loadablePlanCommingleCargoDetails, QUANTITY_UNIT.OBSBBLS, 'cargo1MT')?.toString();
    loadablePlanCommingleCargoDetails.cargo2Bblsdbs = this.convertQuantityCommingle(loadablePlanCommingleCargoDetails, QUANTITY_UNIT.OBSBBLS, 'cargo2MT')?.toString();
    _loadablePlanCommingleCargoDetails.cargoPercentage = loadablePlanCommingleCargoDetails.cargo1Abbreviation + ' - ' + loadablePlanCommingleCargoDetails.cargo1Percentage + '%' + '<br>' + loadablePlanCommingleCargoDetails.cargo2Abbreviation + ' - ' + loadablePlanCommingleCargoDetails.cargo2Percentage + '%';
    _loadablePlanCommingleCargoDetails.cargoBblsdbs =  this.quantityDecimalFormatPipe.transform(loadablePlanCommingleCargoDetails.cargo1Bblsdbs, QUANTITY_UNIT.OBSBBLS) + '<br>' +  this.quantityDecimalFormatPipe.transform(loadablePlanCommingleCargoDetails.cargo2Bblsdbs, QUANTITY_UNIT.OBSBBLS);
    _loadablePlanCommingleCargoDetails.cargoBbls60f =  this.quantityDecimalFormatPipe.transform(loadablePlanCommingleCargoDetails.cargo1Bbls60f, QUANTITY_UNIT.BBLS) + '<br>' +  this.quantityDecimalFormatPipe.transform(loadablePlanCommingleCargoDetails.cargo2Bbls60f, QUANTITY_UNIT.BBLS);
    _loadablePlanCommingleCargoDetails.cargo1LT = this.convertQuantityCommingle(loadablePlanCommingleCargoDetails, QUANTITY_UNIT.LT, 'cargo1MT');
    _loadablePlanCommingleCargoDetails.cargo2LT = this.convertQuantityCommingle(loadablePlanCommingleCargoDetails, QUANTITY_UNIT.LT, 'cargo2MT');
    _loadablePlanCommingleCargoDetails.cargo1KL = this.convertQuantityCommingle(loadablePlanCommingleCargoDetails, QUANTITY_UNIT.KL, 'cargo1MT');
    _loadablePlanCommingleCargoDetails.cargo2KL = this.convertQuantityCommingle(loadablePlanCommingleCargoDetails, QUANTITY_UNIT.KL, 'cargo2MT');
    _loadablePlanCommingleCargoDetails.cargoMT = this.quantityDecimalFormatPipe.transform(loadablePlanCommingleCargoDetails.cargo1MT, QUANTITY_UNIT.MT) + '<br>' + this.quantityDecimalFormatPipe.transform(loadablePlanCommingleCargoDetails.cargo2MT, QUANTITY_UNIT.MT);
    _loadablePlanCommingleCargoDetails.cargoLT = this.quantityDecimalFormatPipe.transform(_loadablePlanCommingleCargoDetails.cargo1LT, QUANTITY_UNIT.LT) + '<br>' + this.quantityDecimalFormatPipe.transform( _loadablePlanCommingleCargoDetails.cargo2LT, QUANTITY_UNIT.LT);
    _loadablePlanCommingleCargoDetails.cargoKL = this.quantityDecimalFormatPipe.transform(_loadablePlanCommingleCargoDetails.cargo1KL, QUANTITY_UNIT.KL) + '<br>' + this.quantityDecimalFormatPipe.transform( _loadablePlanCommingleCargoDetails.cargo2KL, QUANTITY_UNIT.KL);
    _loadablePlanCommingleCargoDetails.tankShortName = loadablePlanCommingleCargoDetails?.tankShortName;
    return _loadablePlanCommingleCargoDetails;
  }

  /**
  *
   * Method for formatting cargo tanks data
   *
   * @param {ICargoTank[][]} cargoTank
   * @param {ICargoTankDetail[]} cargoTankDetails
   * @returns {ICargoTank[][]}
   * @memberof LoadablePlanTransformationService
   */
  formatCargoTanks(cargoTank: ICargoTank, cargoTankDetails: ICargoTankDetailValueObject[]): ICargoTank {
    const commodity = cargoTankDetails?.find(cargo => cargoTank?.id === cargo?.tankId);
    cargoTank.commodity = commodity ? this.getCargoTankDetailAsValue(commodity) : null;

    return cargoTank;
  }

  /**
   * Method for formatting ballast tanks data
   *
   * @param {IBallastTank} ballastTank
   * @param {IBallastStowageDetails[]} ballastTankDetails
   * @returns {IBallastTank}
   * @memberof LoadablePlanTransformationService
   */
  formatBallastTanks(ballastTank: IBallastTank, ballastTankDetails: IBallastStowageDetails[]): IBallastTank {
    ballastTank.commodity = ballastTankDetails?.find(ballast => ballastTank?.id === ballast?.tankId);

    return ballastTank;
  }

  /**
   * Method for formatting ballast stowage details
   *
   * @param  _decimalPipe
   * @param {IBallastStowageDetails} ballast
   * @returns {IBallastStowageDetails}
   * @memberof LoadablePlanTransformationService
   */
  getFormattedBallastDetails(_decimalPipe, ballast: IBallastStowageDetails): IBallastStowageDetails {
    const newBallast = <IBallastStowageDetails>JSON.parse(JSON.stringify(ballast))
    newBallast.cubicMeter = (Number(newBallast.metricTon) / Number(newBallast.sg)).toFixed(2);
    newBallast.percentage = ballast.percentage;
    newBallast.cubicMeter = this.decimalConvertion(_decimalPipe, newBallast.cubicMeter, "1.2-2");
    return newBallast
  }

  /**
  * Method to convert loadable plan tank details to value object
  *
  * @param {ICargoTankDetail} cargoTankDetail
  * @param {boolean} [isNewValue=true]
  * @returns {ICargoTankDetailValueObject}
  * @memberof LoadablePlanTransformationService
  */
  getFormatedCargoDetails(cargoTankDetail: ICargoTankDetail) {
    const _cargoTankDetail = <ICargoTankDetail>{};
    _cargoTankDetail.id = cargoTankDetail.id;
    _cargoTankDetail.tankId = cargoTankDetail.tankId;
    _cargoTankDetail.cargoAbbreviation = cargoTankDetail.cargoAbbreviation;
    _cargoTankDetail.cargoNominationId = cargoTankDetail.cargoNominationId;
    _cargoTankDetail.weight = cargoTankDetail.weight;
    _cargoTankDetail.weightOrginal = cargoTankDetail.weightOrginal;
    _cargoTankDetail.correctedUllage = cargoTankDetail.correctedUllage;
    _cargoTankDetail.correctedUllageOrginal = cargoTankDetail.correctedUllageOrginal;
    _cargoTankDetail.tankName = cargoTankDetail.tankName;
    _cargoTankDetail.rdgUllage = cargoTankDetail.rdgUllage;
    _cargoTankDetail.rdgUllageOrginal = cargoTankDetail.rdgUllageOrginal;
    _cargoTankDetail.tankShortName = cargoTankDetail.tankShortName;
    _cargoTankDetail.correctionFactor = cargoTankDetail.correctionFactor;
    _cargoTankDetail.correctionFactorOrginal = cargoTankDetail.correctionFactorOrginal;
    _cargoTankDetail.api = cargoTankDetail.api;
    _cargoTankDetail.temperature = cargoTankDetail.temperature;
    _cargoTankDetail.colorCode = cargoTankDetail.isCommingle ? AppConfigurationService.settings.commingleColor : cargoTankDetail.colorCode;
    _cargoTankDetail.observedM3 = this.convertQuantityCargo(cargoTankDetail, QUANTITY_UNIT.OBSKL, 'weight');
    _cargoTankDetail.observedBarrels = this.convertQuantityCargo(cargoTankDetail, QUANTITY_UNIT.OBSBBLS, 'weight');
    _cargoTankDetail.observedBarrelsAt60 = this.convertQuantityCargo(cargoTankDetail, QUANTITY_UNIT.BBLS, 'weight');
    _cargoTankDetail.observedBarrelsAt60Original = this.convertQuantityCargo(cargoTankDetail, QUANTITY_UNIT.BBLS, 'weightOrginal');
    _cargoTankDetail.fillingRatio = cargoTankDetail.fillingRatio;
    _cargoTankDetail.fillingRatioOrginal = cargoTankDetail.fillingRatioOrginal;
    _cargoTankDetail.fullCapacityCubm = cargoTankDetail.fullCapacityCubm;
    _cargoTankDetail.isCommingle = cargoTankDetail?.isCommingle;
    return _cargoTankDetail;
  }

  /**
   * Method to convert loadable plan tank details to value object
   *
   * @param {ICargoTankDetail} cargoTankDetail
   * @param {boolean} [isNewValue=true]
   * @returns {ICargoTankDetailValueObject}
   * @memberof LoadablePlanTransformationService
   */
  getCargoTankDetailAsValueObject(cargoTankDetail: ICargoTankDetail, isNewValue = true): ICargoTankDetailValueObject {
    const _cargoTankDetail = <ICargoTankDetailValueObject>{};
    const unitConvertedTankDetails = {
      observedM3: this.quantityPipe.transform(cargoTankDetail?.weight, QUANTITY_UNIT.MT, QUANTITY_UNIT.OBSKL, cargoTankDetail?.api, cargoTankDetail?.temperature,-1),
      observedBarrelsAt60: this.quantityPipe.transform(cargoTankDetail?.weight, QUANTITY_UNIT.MT, QUANTITY_UNIT.BBLS, cargoTankDetail?.api, cargoTankDetail?.temperature,-1),
      observedBarrelsAt60Original: this.quantityPipe.transform(cargoTankDetail?.weightOrginal, QUANTITY_UNIT.MT, QUANTITY_UNIT.BBLS, cargoTankDetail?.api, cargoTankDetail?.temperature, -1),
      observedBarrels: this.quantityPipe.transform(cargoTankDetail?.weight, QUANTITY_UNIT.MT, QUANTITY_UNIT.OBSBBLS, cargoTankDetail?.api, cargoTankDetail?.temperature,-1)
    }
    _cargoTankDetail.id = cargoTankDetail?.id;
    _cargoTankDetail.tankId = cargoTankDetail?.tankId;
    _cargoTankDetail.cargoAbbreviation = cargoTankDetail?.cargoAbbreviation;
    _cargoTankDetail.cargoNominationId = cargoTankDetail?.cargoNominationId;
    _cargoTankDetail.weight = new ValueObject<number>(Number(cargoTankDetail?.weight), true, false);
    _cargoTankDetail.weightOrginal = Number(cargoTankDetail?.weightOrginal);
    _cargoTankDetail.correctedUllage = new ValueObject<number>(cargoTankDetail?.correctedUllage, true, false);
    _cargoTankDetail.correctedUllageOrginal = cargoTankDetail?.correctedUllageOrginal;
    _cargoTankDetail.fillingRatio = new ValueObject<number>(Number(cargoTankDetail?.fillingRatio), true, false);
    _cargoTankDetail.fillingRatioOrginal = Number(cargoTankDetail?.fillingRatioOrginal);
    _cargoTankDetail.tankName = cargoTankDetail?.tankName;
    _cargoTankDetail.tankShortName = cargoTankDetail?.tankShortName;
    _cargoTankDetail.rdgUllage = new ValueObject<number>(cargoTankDetail?.rdgUllage, true, !cargoTankDetail?.isCommingle, false, !cargoTankDetail?.isCommingle);
    _cargoTankDetail.rdgUllageOrginal = cargoTankDetail?.rdgUllageOrginal;
    _cargoTankDetail.correctionFactor = new ValueObject<number>(cargoTankDetail?.correctionFactor, true, false);
    _cargoTankDetail.correctionFactorOrginal = cargoTankDetail?.correctionFactorOrginal;
    _cargoTankDetail.observedM3 = new ValueObject<number>(unitConvertedTankDetails.observedM3, true, false);
    _cargoTankDetail.observedBarrels = new ValueObject<number>(unitConvertedTankDetails?.observedBarrels, true, false);
    _cargoTankDetail.observedBarrelsAt60 = new ValueObject<number>(unitConvertedTankDetails?.observedBarrelsAt60, true, false);
    _cargoTankDetail.observedBarrelsAt60Original = unitConvertedTankDetails?.observedBarrelsAt60Original;
    _cargoTankDetail.api = new ValueObject<number>(cargoTankDetail?.api, true, false);
    _cargoTankDetail.temperature = new ValueObject<number>(cargoTankDetail?.temperature, true, false);
    _cargoTankDetail.colorCode = cargoTankDetail?.colorCode;
    _cargoTankDetail.isAdd = isNewValue;
    _cargoTankDetail.fullCapacityCubm = cargoTankDetail.fullCapacityCubm;
    _cargoTankDetail.isCommingle = cargoTankDetail?.isCommingle;
    return _cargoTankDetail;
  }

  /**
   * Method for converting cargo tank data as value
   *
   * @param {ICargoTankDetailValueObject} cargoTankDetail
   * @returns {ICargoTankDetail}
   * @memberof LoadablePlanTransformationService
   */
  getCargoTankDetailAsValue(cargoTankDetail: ICargoTankDetailValueObject): ICargoTankDetail {
    const _cargoTankDetail: ICargoTankDetail = <ICargoTankDetail>{};
    for (const key in cargoTankDetail) {
      if (Object.prototype.hasOwnProperty.call(cargoTankDetail, key)) {
        _cargoTankDetail[key] = cargoTankDetail[key]?.value ?? cargoTankDetail[key];
      }
    }

    return _cargoTankDetail;
  }

  /**
   * Method to get cargo grid colums
   *
   * @returns {IDataTableColumn[]}
   * @memberof LoadablePlanTransformationService
   */
  getCargoDatatableColumns(): IDataTableColumn[] {
    return [
      {
        field: 'tankShortName',
        header: 'LOADABLE_PLAN_CARGO_GRID_TANK',
        editable: false,
        fieldHeaderClass: 'column-tank-name'
      },
      {
        field: 'cargoAbbreviation',
        header: 'LOADABLE_PLAN_CARGO_GRID_ABBREVIATION',
        editable: false,
      },
      {
        field: 'rdgUllage',
        fieldClass: 'ullage-column',
        header: 'LOADABLE_PLAN_CARGO_GRID_RDG_ULG',
        fieldPlaceholder: 'LOADABLE_PLAN_CARGO_GRID_RDG_ULG_PLACEHOLDER',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        fieldHeaderClass: 'column-rdg-ullage',
        numberFormat: '1.0-6',
        errorMessages: {
          'required': 'LOADABLE_PLAN_CARGO_GRID_RDG_ULG_REQUIRED',
          'greaterThanTankCapacity': 'LOADABLE_PLAN_STOWAGE_EDIT_TANK_CAPACITY_ERROR',
          'maxLimit': 'LOADABLE_PLAN_MAX_LIMIT',
          'invalidNumber': 'LOADABLE_PLAN_CARGO_GRID_RDG_ULG_INVALID'
        }
      },
      {
        field: 'correctionFactor',
        header: 'LOADABLE_PLAN_CARGO_GRID_CORRECTION_FACTOR',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        editable: false,
      },
      {
        field: 'correctedUllage',
        header: 'LOADABLE_PLAN_CARGO_GRID_CORRECTED_ULLAGE',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        numberFormat: '1.2-2',
        editable: false,
      },
      {
        field: 'observedM3',
        header: 'LOADABLE_PLAN_CARGO_GRID_OBSERVED_M3',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        editable: false,
        unit: QUANTITY_UNIT.KL,
        showTotal: true,
        numberType: 'quantity'
      },
      {
        field: 'observedBarrels',
        header: 'LOADABLE_PLAN_CARGO_GRID_OBSERVED_BBLS',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        numberType: 'quantity',
        unit: QUANTITY_UNIT.OBSBBLS,
        editable: false,
        showTotal: true
      },
      {
        field: 'observedBarrelsAt60',
        header: 'LOADABLE_PLAN_CARGO_GRID_OBSERVED_BBL_AT_60F',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        numberType: 'quantity',
        unit: QUANTITY_UNIT.BBLS,
        editable: false,
        showTotal: true
      },
      {
        field: 'weight',
        header: 'LOADABLE_PLAN_CARGO_GRID_WEIGHT',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        numberFormat: AppConfigurationService.settings.quantityNumberFormatMT,
        editable: false,
        showTotal: true,
        numberType: 'quantity',
        unit: QUANTITY_UNIT.MT,
      },
      {
        field: 'fillingRatio',
        header: 'LOADABLE_PLAN_CARGO_GRID_FILLING_RATIO',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        numberFormat: '1.2-2',
        editable: false,
      },
      {
        field: 'api',
        header: 'LOADABLE_PLAN_CARGO_GRID_API',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        numberFormat: '1.2-2',
        editable: false,
      },
      {
        field: 'temperature',
        header: 'LOADABLE_PLAN_CARGO_GRID_TEMPERATURE',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        numberFormat: '1.2-2',
        editable: false,
      },
    ]
  }

  /**
   * Method to get ballast columns
   *
   * @returns {IDataTableColumn[]}
   * @memberof LoadablePlanTransformationService
   */
   getBallastDatatableColumns(): IDataTableColumn[] {
    return [
      { field: 'tankShortName', header: 'STOWAGE_BALLAST_TANK'},
      {
        field: 'rdgLevel',
        header: 'STOWAGE_BALLAST_RDG_LEVEL',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        fieldPlaceholder: 'LOADABLE_PLAN_BALLAST_GRID_RDG_ULG_PLACEHOLDER',
        numberFormat: '1.0-6',
        errorMessages: {
          'required': 'LOADABLE_PLAN_BALLAST_CARGO_GRID_RDG_ULG_REQUIRED',
          'greaterThanTankCapacity': 'LOADABLE_PLAN_BALLAST_EDIT_TANK_CAPACITY_ERROR',
          'maxLimit': 'LOADABLE_PLAN_MAX_LIMIT',
          'invalidNumber': 'LOADABLE_PLAN_BALLAST_GRID_RDG_ULG_INVALID'
        }
      },
      { field: 'correctionFactor', header: 'STOWAGE_BALLAST_CORR', editable: false , fieldType: DATATABLE_FIELD_TYPE.NUMBER },
      { field: 'correctedLevel', header: 'STOWAGE_BALLAST_CORR_LEVEL', editable: false , fieldType: DATATABLE_FIELD_TYPE.NUMBER , numberFormat: '1.2-2' },
      { field: 'metricTon', header: 'STOWAGE_BALLAST_METRIC_TON', editable: false , fieldType: DATATABLE_FIELD_TYPE.NUMBER, numberFormat: AppConfigurationService.settings.quantityNumberFormatMT },
      { field: 'cubicMeter', header: 'STOWAGE_BALLAST_CUB_METER', editable: false , fieldType: DATATABLE_FIELD_TYPE.NUMBER, numberFormat: AppConfigurationService.settings.quantityNumberFormatKL },
      { field: 'percentage', header: 'STOWAGE_BALLAST_PERCENTAGE', editable: false , fieldType: DATATABLE_FIELD_TYPE.NUMBER , numberFormat: '1.2-2' },
      { field: 'sg', header: 'STOWAGE_BALLAST_SG', editable: false , fieldType: DATATABLE_FIELD_TYPE.NUMBER , numberFormat:  AppConfigurationService.settings?.sgNumberFormat }
    ]
  }

    /**
   * Method to convert loadable plan tank details to value object
   *
   * @param {IBallastStowageDetails} ballastTankDetail
   * @param {boolean} [isNewValue=true]
   * @returns {IBallastTankDetailValueObject}
   * @memberof LoadablePlanTransformationService
   */
    getBallastTankDetailAsValueObject(ballastTankDetail: IBallastStowageDetails, isNewValue = true): IBallastTankDetailValueObject {
      const _ballastTankDetail = <IBallastTankDetailValueObject>{};
      _ballastTankDetail.id = ballastTankDetail.id;
      _ballastTankDetail.tankName = new ValueObject<string>(ballastTankDetail?.tankName, true, false);
      _ballastTankDetail.tankId = ballastTankDetail.tankId;
      _ballastTankDetail.rdgLevel = new ValueObject<string>(ballastTankDetail?.rdgLevel, true, isNewValue);
      _ballastTankDetail.rdgLevelOrginal = ballastTankDetail?.rdgLevelOrginal;
      _ballastTankDetail.correctionFactor = new ValueObject<string>(ballastTankDetail?.correctionFactor, true, false);
      _ballastTankDetail.correctionFactorOrginal = ballastTankDetail?.correctionFactorOrginal;
      _ballastTankDetail.correctedLevel = new ValueObject<string>(ballastTankDetail?.correctedLevel, true, false);
      _ballastTankDetail.correctedLevelOrginal = ballastTankDetail?.correctedLevelOrginal;
      _ballastTankDetail.metricTon = new ValueObject<string>(ballastTankDetail?.metricTon, true, false);
      _ballastTankDetail.metricTonOrginal = ballastTankDetail?.metricTonOrginal;
      _ballastTankDetail.cubicMeter = new ValueObject<string>(this.convertToNumber(ballastTankDetail?.cubicMeter), true, false);
      _ballastTankDetail.percentage = new ValueObject<string>(ballastTankDetail?.percentage, true, false);
      _ballastTankDetail.percentageOrginal = ballastTankDetail?.percentageOrginal;
      _ballastTankDetail.api = new ValueObject<number>(ballastTankDetail?.api, true, false);
      _ballastTankDetail.temperature = new ValueObject<number>(ballastTankDetail?.temperature, true, false);
      _ballastTankDetail.sg = new ValueObject<string>(ballastTankDetail?.sg, true, false);
      _ballastTankDetail.fullCapacityCubm = ballastTankDetail.fullCapacityCubm;
      _ballastTankDetail.isAdd = isNewValue;
      _ballastTankDetail.tankShortName = ballastTankDetail.tankShortName;
      return _ballastTankDetail;
    }

    /**
   * parse number from formatted string
   * @returns {number}
   */
    convertToNumber(value: string) {
      value = value?.replace(',', '');
      return value
    }

  /**
  *
  * calculate percentage
  * @returns {number}
  * @param { number } fullCapacityCubm
  * @param { number } cubicMeter
  */
  calculatePercentage(cubicMeter: number, fullCapacityCubm: number) {
    return Number((cubicMeter / fullCapacityCubm * 100));
  }

  /**
  *
  * Get Formated ETA/ETD Data
  * @returns {ISynopticalRecordArrangeModel}
  * @param { ILoadablePlanSynopticalRecord } synopticalRecord
  */
  public getFormatedEtaEtdData(_decimalPipe: any, synopticalRecord: ILoadablePlanSynopticalRecord, vesselLightWeight: number): ISynopticalRecordArrangeModel {
    const _synopticalRecord = <ISynopticalRecordArrangeModel>{};
    const totalDwtPlanned = synopticalRecord?.plannedFOTotal + synopticalRecord?.plannedDOTotal
      + synopticalRecord?.plannedFWTotal + synopticalRecord?.cargoPlannedTotal
      + synopticalRecord?.ballastPlanned + synopticalRecord?.othersPlanned
      + synopticalRecord?.constantPlanned;
    const displacementPlanned = totalDwtPlanned + vesselLightWeight;
    _synopticalRecord.id = synopticalRecord.id;
    _synopticalRecord.operationType = synopticalRecord.operationType;
    _synopticalRecord.portId = synopticalRecord.portId;
    _synopticalRecord.portName = synopticalRecord.portName;
    _synopticalRecord.etaEtdPlanned = synopticalRecord.etaEtdPlanned;
    _synopticalRecord.plannedFOTotal = this.quantityDecimalFormatPipe.transform(synopticalRecord?.plannedFOTotal,QUANTITY_UNIT.MT);
    _synopticalRecord.plannedDOTotal = this.quantityDecimalFormatPipe.transform(synopticalRecord?.plannedDOTotal,QUANTITY_UNIT.MT);
    _synopticalRecord.plannedFWTotal = this.quantityDecimalFormatPipe.transform(synopticalRecord?.plannedFWTotal,QUANTITY_UNIT.MT);
    _synopticalRecord.othersPlanned = this.quantityDecimalFormatPipe.transform(synopticalRecord?.othersPlanned,QUANTITY_UNIT.MT);
    _synopticalRecord.totalDwtPlanned = this.quantityDecimalFormatPipe.transform(totalDwtPlanned,QUANTITY_UNIT.MT);
    _synopticalRecord.displacementPlanned = this.quantityDecimalFormatPipe.transform(displacementPlanned,QUANTITY_UNIT.MT);
    _synopticalRecord.specificGravity = this.decimalConvertion(_decimalPipe, synopticalRecord.specificGravity, AppConfigurationService.settings?.sgNumberFormat);
    _synopticalRecord.cargoPlannedTotal = this.quantityDecimalFormatPipe.transform(synopticalRecord?.cargoPlannedTotal,QUANTITY_UNIT.MT);
    _synopticalRecord.ballastPlanned = this.quantityDecimalFormatPipe.transform(synopticalRecord?.ballastPlanned,QUANTITY_UNIT.MT);
    _synopticalRecord.sf = synopticalRecord.sf ? this.decimalConvertion(_decimalPipe, synopticalRecord.sf , '1.2-2') : '0.00';
    _synopticalRecord.bm = synopticalRecord.bm ? this.decimalConvertion(_decimalPipe, synopticalRecord.bm , '1.2-2') : '0.00';
    _synopticalRecord.list = synopticalRecord.list ? this.decimalConvertion(_decimalPipe, synopticalRecord.list , '1.1-1') : '0.0';

    _synopticalRecord.finalDraftFwd = this.decimalConvertion(_decimalPipe, synopticalRecord?.finalDraftFwd, '1.2-2') + 'm';
    _synopticalRecord.finalDraftAft = this.decimalConvertion(_decimalPipe, synopticalRecord?.finalDraftAft, '1.2-2') + 'm';
    _synopticalRecord.finalDraftMid = this.decimalConvertion(_decimalPipe, synopticalRecord?.finalDraftMid, '1.2-2') + 'm';
    _synopticalRecord.calculatedTrimPlanned = this.decimalConvertion(_decimalPipe, synopticalRecord?.calculatedTrimPlanned, '1.0-2') + 'm';
    return _synopticalRecord;
  }

  /**
  *
  * Convert Loadable Quantity to other units
  * @returns {number}
  * @param { ILoadableQuantityCargo } _loadableQuantityDetails
  * @param { QUANTITY_UNIT } unitTo
  * @param { string } key
  */
  convertQuantityLoadable(_loadableQuantityDetails: ILoadableQuantityCargo, unitTo: QUANTITY_UNIT, key: string, decimalPoint?: number) {
    return this.quantityPipe.transform(_loadableQuantityDetails[key], this.baseUnit, unitTo, _loadableQuantityDetails.estimatedAPI, _loadableQuantityDetails.estimatedTemp, decimalPoint)
  }

  /**
  *
  * Convert Commingle Quantity to other units
  * @returns {number}
  * @param { ILoadableQuantityCargo } _loadableQuantityDetails
  * @param { QUANTITY_UNIT } unitTo
  * @param { string } key
  */
  convertQuantityCommingle(loadablePlanCommingleCargoDetails: ILoadableQuantityCommingleCargo, unitTo: QUANTITY_UNIT, key: string) {
    return this.quantityPipe.transform(loadablePlanCommingleCargoDetails[key], this.baseUnit, unitTo, loadablePlanCommingleCargoDetails.api, loadablePlanCommingleCargoDetails.temp)
  }

  /**
  *
  * Convert Cargo Quantity to other units
  * @returns {number}
  * @param { ICargoTankDetail } cargoDetails
  * @param { QUANTITY_UNIT } unitTo
  * @param { string } key
  */
  convertQuantityCargo(cargoDetails: ICargoTankDetail, unitTo: QUANTITY_UNIT, key: string) {
    return this.quantityPipe.transform(cargoDetails[key], this.baseUnit, unitTo, cargoDetails.api, cargoDetails.temperature)
  }

  /**
  *
  * Convert Ballast Quantity to other units
  * @returns {number}
  * @param { IBallastStowageDetails } ballast
  * @param { QUANTITY_UNIT } unitTo
  */
  convertQuantityBallast(ballast: IBallastStowageDetails, unitTo: QUANTITY_UNIT) {
    return this.quantityPipe.transform(ballast.metricTon, this.baseUnit, unitTo, ballast.sg)
  }

  /**
   * comments saved
  */
  commentsSaved(comments: string) {
    this.savedComments.next(comments);
  }

  /**
   * edit stowage bellast status
   * @param { any } value
  */
  ballastEditStatus(value: any) {
    this.editBallastStatus.next(value);
  }

    /**
 * Method for calculating  subtotal
 *
 * @param {ISubTotal} data
 * @returns {number}
 * @memberof LoadablePlanTransformationService
 */
  getSubTotal(data: ISubTotal): Number {
    const subTotal = Number(data.dwt) - Number(data.sagCorrection) + Number(data.sgCorrection ? data.sgCorrection : 0) - Number(data.foOnboard)
      - Number(data.doOnboard) - Number(data.freshWaterOnboard) - Number(data.ballast)
      - Number(data.constant) - Number(data.others);
    return Number(subTotal);
  }

}
