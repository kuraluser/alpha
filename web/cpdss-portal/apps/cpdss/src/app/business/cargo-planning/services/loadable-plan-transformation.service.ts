import { Injectable } from '@angular/core';
import { DATATABLE_FIELD_TYPE, IDataTableColumn } from '../../../shared/components/datatable/datatable.model';
import { ValueObject } from '../../../shared/models/common.model';
import { ICargoTank } from '../../core/models/common.model';
import { CargoPlanningModule } from '../cargo-planning.module';
import { ITableHeaderModel, ILoadableQuantityCargo, ICargoTankDetail, ICargoTankDetailValueObject } from '../models/loadable-plan.model';

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
  * @returns {ITableHeaderModel[]}
  */
  public getEtaEtdTableColumns(): ITableHeaderModel[] {
    return [
      { field: 'year', header: '' },
      { field: 'year', header: 'PORT' },
      { field: 'year', header: 'ETA/ETD DATE' },
      { field: 'year', header: 'ETA/ETD TIME' },
      {
        field: 'year', header: 'DRAFT', rowspan: 3, subColumns: [
          { field: 'year', header: 'FORE' },
          { field: 'year', header: 'AFT' },
          { field: 'year', header: 'MSHIP' }
        ]
      },
      { field: 'year', header: 'TRIM' },
      { field: 'year', header: 'CARGO(MT)' },
      { field: 'year', header: 'F.Q.' },
      { field: 'year', header: 'D.Q.' },
      { field: 'year', header: 'BALLAST' },
      { field: 'year', header: 'FRESH WATER' },
      { field: 'year', header: 'OTHERS' },
      { field: 'year', header: 'TOTAL DWT' },
      { field: 'year', header: 'DISPLACEMENT' },
      { field: 'year', header: 'MAX FRBRO (M)' },
      { field: 'year', header: 'MAX.MNFLD(M)' },
      { field: 'year', header: 'DENSITY' },
    ]
  }


  /**
* 
* GetCommingled Cargo Table Column
* @returns {ITableHeaderModel[]}
*/
  public getCommingledCargoTableColumn(): ITableHeaderModel[] {
    return [
      { field: 'year', header: 'GRADE' },
      { field: 'year', header: 'TANK' },
      { field: 'year', header: 'QUANTITY (BLS)' },
      { field: 'year', header: 'API' },
      { field: 'year', header: 'TEMP(F)' },
      {
        header: 'COMPOSITION BREAKDOWN', subColumns: [
          { field: 'year', header: 'PERCENTAGE' },
          { field: 'year', header: 'BBLS@OBS.TEMP' },
          { field: 'year', header: 'BBL@60F' },
          { field: 'year', header: 'LT' },
          { field: 'year', header: 'MT' },
          { field: 'year', header: 'KL' }
        ]
      }
    ];
  }

  /**
  * 
  * Get loadable quantity table header
  * @returns {ITableHeaderModel[]}
  */
  getLoadableQuantityTableColumns(): ITableHeaderModel[] {
    return [
      { field: 'grade', header: 'Grade', colspan: 0, },
      {
        field: 'vin', header: 'Estimated', colspan: 2, subColumns: [
          { field: 'estimatedAPI', header: 'API' },
          { field: 'estimatedTemp', header: 'TEMP' }
        ]
      },
      {
        field: 'year', header: 'ORDER', colspan: 2, subColumns: [
          { field: 'orderBblsdbs', header: 'BBLS@OBS.TEMP' },
          { field: 'orderBbls60f', header: 'BBLS@60F' }
        ]
      },
      {
        field: 'brand', header: 'TLRNC', colspan: 2, subColumns: [
          { field: 'minTolerence', header: 'Min' },
          { field: 'maxTolerence', header: 'Max' }
        ]
      },
      {
        field: 'color', header: 'LOADABLE', colspan: 5, subColumns: [
          { field: 'loadableBblsdbs', header: 'BBLS@OBS.TEMP' },
          { field: 'loadableBbls60f', header: 'BBLS@60F' },
          { field: 'loadableLT', header: 'LT' },
          { field: 'loadableMT', header: 'MT' },
          { field: 'loadableKL', header: 'KL' }
        ]
      },
      {
        field: 'differencePercentage', header: 'DIFF.%', colspan: 2
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
    _loadableQuantityDetails.estimatedAPI = this.decimalConvertion(_decimalPipe, _loadableQuantityDetails.estimatedAPI);
    _loadableQuantityDetails.estimatedTemp = this.decimalConvertion(_decimalPipe, _loadableQuantityDetails.estimatedTemp);
    _loadableQuantityDetails.orderBbls60f = this.decimalConvertion(_decimalPipe, _loadableQuantityDetails.orderBbls60f);
    _loadableQuantityDetails.orderBblsdbs = this.decimalConvertion(_decimalPipe, _loadableQuantityDetails.orderBblsdbs);
    _loadableQuantityDetails.minTolerence = _loadableQuantityDetails.minTolerence + '%';
    _loadableQuantityDetails.maxTolerence = _loadableQuantityDetails.maxTolerence + '%';
    _loadableQuantityDetails.loadableBbls60f = this.decimalConvertion(_decimalPipe, _loadableQuantityDetails.loadableBbls60f);
    _loadableQuantityDetails.loadableBblsdbs = this.decimalConvertion(_decimalPipe, _loadableQuantityDetails.loadableBblsdbs);
    _loadableQuantityDetails.loadableKL = this.decimalConvertion(_decimalPipe, _loadableQuantityDetails.loadableKL);
    _loadableQuantityDetails.loadableMT = this.decimalConvertion(_decimalPipe, _loadableQuantityDetails.loadableMT);
    _loadableQuantityDetails.loadableLT = this.decimalConvertion(_decimalPipe, _loadableQuantityDetails.loadableLT);
    _loadableQuantityDetails.differencePercentage = _loadableQuantityDetails.differencePercentage + '%';
    return loadableQuantity;
  }

  /**
  * 
  * Get Formated Loadable Quantity Data
  * @returns {decimal converted value us number}
  */
  decimalConvertion(_decimalPipe: any, value: string | number) {
    return _decimalPipe.transform(value, '1.2-4');
  }

  /**
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

}
