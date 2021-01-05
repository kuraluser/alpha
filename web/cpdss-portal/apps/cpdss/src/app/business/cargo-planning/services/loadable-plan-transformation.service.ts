import { Injectable } from '@angular/core';
import { ICargoTank } from '../../core/models/common.model';
import { CargoPlanningModule } from '../cargo-planning.module';
import { ITableHeaderModel, ILoadableQuantityCargo, ICargoTankDetail } from '../models/loadable-plan.model';

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
  formatCargoTanks(cargoTank: ICargoTank, cargoTankDetails: ICargoTankDetail[]): ICargoTank {
    cargoTank.commodity = cargoTankDetails?.find(cargo => cargoTank?.id === cargo?.tankId);

    return cargoTank;
  }

}
