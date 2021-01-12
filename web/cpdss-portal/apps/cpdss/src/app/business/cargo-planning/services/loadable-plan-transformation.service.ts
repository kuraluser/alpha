import { Injectable } from '@angular/core';

import { ICargoTank } from '../../core/models/common.model';
import { CargoPlanningModule } from '../cargo-planning.module';
import { ILoadableQuantityCargo, ICargoTankDetail, ILoadableQuantityCommingleCargo, ICommingleCargoDispaly, ICargoTankDetailValueObject } from '../models/loadable-plan.model';
import { DATATABLE_FIELD_TYPE, IDataTableColumn } from '../../../shared/components/datatable/datatable.model';
import { ValueObject } from '../../../shared/models/common.model';


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

  constructor() { }

  /**
  * 
  * Get loadable quantity table header
  * @returns {IDataTableColumn[]}
  */
  public getEtaEtdTableColumns(): IDataTableColumn[] {
    return [
      { field: 'year', header: 'ETA_ETD_PORT' },
      { field: 'year', header: 'ETA_ETD_DATE' },
      { field: 'year', header: 'ETA_ETD_TIME' },
      {
        field: 'year', header: 'ETA_ETD_DRAFT', rowspan: 3, subHeader: 'ETA_ETD_DRAFT_FORE'
      },
      { field: 'year', header: "", subHeader: 'ETA_ETD_DRAFT_AFT' },
      { field: 'year', header: "", subHeader: 'ETA_ETD_DRAFT_MSHIP' },
      { field: 'year', header: 'ETA_ETD_TRIM' },
      { field: 'year', header: 'ETA_ETD_CARGO' },
      { field: 'year', header: 'ETA_ETD_FQ' },
      { field: 'year', header: 'ETA_ETD_DQ' },
      { field: 'year', header: 'ETA_ETD_BALLAST' },
      { field: 'year', header: 'ETA_ETD_FRESH_WATER' },
      { field: 'year', header: 'ETA_ETD_OTHERS' },
      { field: 'year', header: 'ETA_ETD_TOTAL_DWT' },
      { field: 'year', header: 'ETA_ETD_DISPLACEMENT' },
      { field: 'year', header: 'ETA_ETD_MAX_FRBRO' },
      { field: 'year', header: 'ETA_ETD_MAX_MNFLD' },
      { field: 'year', header: 'ETA_ETD_DENSITY' },
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
      { field: 'tankName', header: 'LOADABLE_PLAN_COMMINGLED_CARGO_TANK', rowspan: 2 },
      { field: 'quantity', header: 'LOADABLE_PLAN_COMMINGLED_CARGO_QUANTITY', rowspan: 2 },
      { field: 'api', header: 'LOADABLE_PLAN_COMMINGLED_CARGO_API', rowspan: 2 },
      { field: 'temp', header: 'LOADABLE_PLAN_COMMINGLED_CARGO_TEMP', rowspan: 2 },
      {
        header: 'LOADABLE_PLAN_COMMINGLED_CARGO_COMPOSITION_BREAKDOWN', colspan: 6, subColumns: [
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
        header: 'LOADABLE_PLAN_ESTIMATED', colspan: 2, className: "th-border", subColumns: [
          { field: 'estimatedAPI', header: 'LOADABLE_PLAN_ESTIMATED_API' },
          { field: 'estimatedTemp', header: 'LOADABLE_PLAN_ESTIMATED_TEMP' }
        ]
      },
      {
        header: 'LOADABLE_PLAN_ORDER', colspan: 2, className: "th-border", subColumns: [
          { field: 'orderBblsdbs', header: 'LOADABLE_PLAN_ORDER_BBLS@OBS_TEMP' },
          { field: 'orderBbls60f', header: 'LOADABLE_PLAN_ORDER_BBLS@60F' }
        ]
      },
      {
        header: 'LOADABLE_PLAN_TLRNC', colspan: 2, className: "th-border", subColumns: [
          { field: 'minTolerence', header: 'LOADABLE_PLAN_TLRNC_MIN' },
          { field: 'maxTolerence', header: 'LOADABLE_PLAN_TLRNC_MAX' }
        ]
      },
      {
        header: 'LOADABLE_PLAN_LOADABLE', colspan: 5, className: "th-border", subColumns: [
          { field: 'loadableBblsdbs', header: 'LOADABLE_PLAN_LOADABLE_BBLS@DBS_TEMP' },
          { field: 'loadableBbls60f', header: 'LOADABLE_PLAN_LOADABLE_BBLS@60F' },
          { field: 'loadableLT', header: 'LOADABLE_PLAN_LOADABLE_LT' },
          { field: 'loadableMT', header: 'LOADABLE_PLAN_LOADABLE_MT' },
          { field: 'loadableKL', header: 'LOADABLE_PLAN_LOADABLE_KL' }
        ]
      },
      {
        field: 'differencePercentage', header: 'LOADABLE_PLAN_LOADABLE_DIFF', rowspan: 2
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
    _loadableQuantityDetails.estimatedAPI = this.decimalConvertion(_decimalPipe, _loadableQuantityDetails.estimatedAPI, '1.2-4');
    _loadableQuantityDetails.estimatedTemp = this.decimalConvertion(_decimalPipe, _loadableQuantityDetails.estimatedTemp, '1.2-4');
    _loadableQuantityDetails.orderBbls60f = this.decimalConvertion(_decimalPipe, _loadableQuantityDetails.orderBbls60f, '1.0-4');
    _loadableQuantityDetails.orderBblsdbs = this.decimalConvertion(_decimalPipe, _loadableQuantityDetails.orderBblsdbs, '1.0-4');
    _loadableQuantityDetails.minTolerence = _loadableQuantityDetails.minTolerence + '%';
    _loadableQuantityDetails.maxTolerence = _loadableQuantityDetails.maxTolerence + '%';
    _loadableQuantityDetails.loadableBbls60f = this.decimalConvertion(_decimalPipe, _loadableQuantityDetails.loadableBbls60f, '1.0-4');
    _loadableQuantityDetails.loadableBblsdbs = this.decimalConvertion(_decimalPipe, _loadableQuantityDetails.loadableBblsdbs, '1.0-4');
    _loadableQuantityDetails.loadableKL = this.decimalConvertion(_decimalPipe, _loadableQuantityDetails.loadableKL, '1.2-4');
    _loadableQuantityDetails.loadableMT = this.decimalConvertion(_decimalPipe, _loadableQuantityDetails.loadableMT, '1.2-4');
    _loadableQuantityDetails.loadableLT = this.decimalConvertion(_decimalPipe, _loadableQuantityDetails.loadableLT, '1.2-4');
    _loadableQuantityDetails.differencePercentage = _loadableQuantityDetails.differencePercentage + '%';
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
    _loadablePlanCommingleCargoDetails.api = this.decimalConvertion(_decimalPipe, loadablePlanCommingleCargoDetails.api, '1.2-4');
    _loadablePlanCommingleCargoDetails.tankName = loadablePlanCommingleCargoDetails.tankName;
    _loadablePlanCommingleCargoDetails.grade = loadablePlanCommingleCargoDetails.grade;
    _loadablePlanCommingleCargoDetails.quantity = loadablePlanCommingleCargoDetails.quantity;
    _loadablePlanCommingleCargoDetails.temp = this.decimalConvertion(_decimalPipe, loadablePlanCommingleCargoDetails.temp, '1.2-4');
    _loadablePlanCommingleCargoDetails.cargoPercentage = loadablePlanCommingleCargoDetails.cargo1Abbreviation + ' - ' + loadablePlanCommingleCargoDetails.cargo1Percentage + '%' + '<br>' + loadablePlanCommingleCargoDetails.cargo2Abbreviation + ' - ' + loadablePlanCommingleCargoDetails.cargo2Percentage + '%';
    _loadablePlanCommingleCargoDetails.cargoBblsdbs = loadablePlanCommingleCargoDetails.cargo1Bblsdbs + '<br>' + loadablePlanCommingleCargoDetails.cargo2Bblsdbs;
    _loadablePlanCommingleCargoDetails.cargoBbls60f = loadablePlanCommingleCargoDetails.cargo1Bbls60f + '<br>' + loadablePlanCommingleCargoDetails.cargo2Bbls60f;
    _loadablePlanCommingleCargoDetails.cargoLT = this.decimalConvertion(_decimalPipe, loadablePlanCommingleCargoDetails.cargo1LT, '1.2-4') + '<br>' + this.decimalConvertion(_decimalPipe, loadablePlanCommingleCargoDetails.cargo2LT, '1.2-4');
    _loadablePlanCommingleCargoDetails.cargoMT = this.decimalConvertion(_decimalPipe, loadablePlanCommingleCargoDetails.cargo1MT, '1.2-4') + '<br>' + this.decimalConvertion(_decimalPipe, loadablePlanCommingleCargoDetails.cargo2MT, '1.2-4');
    _loadablePlanCommingleCargoDetails.cargoKL = this.decimalConvertion(_decimalPipe, loadablePlanCommingleCargoDetails.cargo1KL, '1.2-4') + '<br>' + this.decimalConvertion(_decimalPipe, loadablePlanCommingleCargoDetails.cargo2KL, '1.2-4');
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
   * Method to convert loadable plan tank details to value object
   *
   * @param {ICargoTankDetail} cargoTankDetail
   * @param {boolean} [isNewValue=true]
   * @returns {ICargoTankDetailValueObject}
   * @memberof LoadablePlanTransformationService
   */
  getCargoTankDetailAsValueObject(cargoTankDetail: ICargoTankDetail, isNewValue = true): ICargoTankDetailValueObject {
    const _cargoTankDetail = <ICargoTankDetailValueObject>{};
    _cargoTankDetail.id = cargoTankDetail?.id;
    _cargoTankDetail.tankId = cargoTankDetail?.tankId;
    _cargoTankDetail.cargoAbbreviation = cargoTankDetail?.cargoAbbreviation;
    _cargoTankDetail.weight = new ValueObject<number>(cargoTankDetail?.weight, true, false);
    _cargoTankDetail.correctedUllage = new ValueObject<number>(cargoTankDetail?.correctedUllage, true, false);
    _cargoTankDetail.fillingRatio = new ValueObject<number>(cargoTankDetail?.fillingRatio, true, false);
    _cargoTankDetail.tankName = cargoTankDetail?.tankName;
    _cargoTankDetail.rdgUllage = new ValueObject<number>(cargoTankDetail?.rdgUllage, true, isNewValue);
    _cargoTankDetail.correctionFactor = new ValueObject<number>(cargoTankDetail?.correctionFactor, true, false);
    _cargoTankDetail.observedM3 = new ValueObject<number>(cargoTankDetail?.observedM3, true, false);
    _cargoTankDetail.observedBarrels = new ValueObject<number>(cargoTankDetail?.observedBarrels, true, false);
    _cargoTankDetail.observedBarrelsAt60 = new ValueObject<number>(cargoTankDetail?.observedBarrelsAt60, true, false);
    _cargoTankDetail.api = new ValueObject<number>(cargoTankDetail?.api, true, false);
    _cargoTankDetail.temperature = new ValueObject<number>(cargoTankDetail?.temperature, true, false);
    _cargoTankDetail.isAdd = isNewValue;

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
        field: 'tankName',
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
        header: 'LOADABLE_PLAN_CARGO_GRID_RDG_ULG',
        fieldPlaceholder: 'LOADABLE_PLAN_CARGO_GRID_RDG_ULG_PLACEHOLDER',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        fieldHeaderClass: 'column-rdg-ullage',
        numberFormat: '1.0-2',
        errorMessages: {
          'required': 'LOADABLE_PLAN_CARGO_GRID_RDG_ULG_REQUIRED'
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
        numberFormat: '1.0-2',
        editable: false,
      },
      {
        field: 'observedM3',
        header: 'LOADABLE_PLAN_CARGO_GRID_OBSERVED_M3',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        numberFormat: '1.0-2',
        editable: false,
      },
      {
        field: 'observedBarrels',
        header: 'LOADABLE_PLAN_CARGO_GRID_OBSERVED_BBLS',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        numberFormat: '1.0-2',
        editable: false,
      },
      {
        field: 'observedBarrelsAt60',
        header: 'LOADABLE_PLAN_CARGO_GRID_OBSERVED_BBL_AT_60F',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        numberFormat: '1.0-2',
        editable: false,
      },
      {
        field: 'weight',
        header: 'LOADABLE_PLAN_CARGO_GRID_WEIGHT',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        numberFormat: '1.0-2',
        editable: false,
      },
      {
        field: 'fillingRatio',
        header: 'LOADABLE_PLAN_CARGO_GRID_FILLING_RATIO',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        numberFormat: '1.0-2',
        editable: false,
      },
      {
        field: 'api',
        header: 'LOADABLE_PLAN_CARGO_GRID_API',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        numberFormat: '1.0-2',
        editable: false,
      },
      {
        field: 'temperature',
        header: 'LOADABLE_PLAN_CARGO_GRID_TEMPERATURE',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        numberFormat: '1.0-2',
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
      { field: 'tankName', header: 'STOWAGE_BALLAST_TANK' },
      { field: 'rdgLevel', header: 'STOWAGE_BALLAST_RDG_LEVEL' },
      { field: 'correctionFactor', header: 'STOWAGE_BALLAST_CORR' },
      { field: 'correctedLevel', header: 'STOWAGE_BALLAST_CORR_LEVEL' },
      { field: 'metricTon', header: 'STOWAGE_BALLAST_METRIC_TON' },
      { field: 'cubicMeter', header: 'STOWAGE_BALLAST_CUB_METER' },
      { field: 'percentage', header: 'STOWAGE_BALLAST_PERCENTAGE' },
      { field: 'sg', header: 'STOWAGE_BALLAST_SG' },
      { field: 'lcg', header: 'STOWAGE_BALLAST_LCG' },
      { field: 'vcg', header: 'STOWAGE_BALLAST_VCG' },
      { field: 'tcg', header: 'STOWAGE_BALLAST_TCG' },
      { field: 'inertia', header: 'STOWAGE_BALLAST_INERTIA' },
    ]
  }

}
