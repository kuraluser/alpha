import { Injectable } from '@angular/core';
import { ITableHeaderModel , ILoadableQuantityCargo , ILoadableQuantityCommingleCargo , ICommingleCargoDispaly } from '../models/loadable-plan.model';

/**
 * Transformation Service for Lodable Plan details module
 *
 * @export
 * @class LoadablePlanTransformationService
*/

@Injectable({
  providedIn: 'root'
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
      { field: 'grade', header: 'GRADE'  , rowspan: 2 },
      { field: 'tankName', header: 'TANK' , rowspan: 2 },
      { field: 'quantity', header: 'QUANTITY (BLS)' , rowspan: 2 },
      { field: 'api', header: 'API' , rowspan: 2 },
      { field: 'temp', header: 'TEMP(F)' , rowspan: 2 },
      {
        header: 'COMPOSITION BREAKDOWN' , colspan: 5 , subColumns: [
          { field: 'cargoPercentage', header: 'PERCENTAGE' },
          { field: 'cargoBblsdbs', header: 'BBLS@DBS.TEMP' },
          { field: 'cargoBbls60f', header: 'BBL@60F' },
          { field: 'cargoLT', header: 'LT' },
          { field: 'cargoMT', header: 'MT' },
          { field: 'cargoKL', header: 'KL' }
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
      { field: 'grade', header: 'Grade', rowspan: 2 },
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
          { field: 'loadableBblsdbs', header: 'BBLS@DBS.TEMP' },
          { field: 'loadableBbls60f', header: 'BBLS@60F' },
          { field: 'loadableLT', header: 'LT' },
          { field: 'loadableMT', header: 'MT' },
          { field: 'loadableKL', header: 'KL' }
        ]
      },
      {
        field: 'differencePercentage', header: 'DIFF.%' , rowspan: 2
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
  * 
  * Get Formated Loadable Quantity Data
  * @returns { ICommingleCargoDispaly }
  * @param loadablePlanCommingleCargoDetails  
  */
 public getFormatedLoadableCommingleCargo(_decimalPipe: any, loadablePlanCommingleCargoDetails : ILoadableQuantityCommingleCargo):  ICommingleCargoDispaly {
  const _loadablePlanCommingleCargoDetails = <ICommingleCargoDispaly>{};
  _loadablePlanCommingleCargoDetails.api = this.decimalConvertion(_decimalPipe , loadablePlanCommingleCargoDetails.api);
  _loadablePlanCommingleCargoDetails.tankName = loadablePlanCommingleCargoDetails.tankName;
  _loadablePlanCommingleCargoDetails.grade = loadablePlanCommingleCargoDetails.grade;
  _loadablePlanCommingleCargoDetails.quantity = loadablePlanCommingleCargoDetails.quantity;
  _loadablePlanCommingleCargoDetails.temp = this.decimalConvertion(_decimalPipe , loadablePlanCommingleCargoDetails.temp);
  _loadablePlanCommingleCargoDetails.cargoPercentage = loadablePlanCommingleCargoDetails.cargo1Abbreviation + ' - ' + loadablePlanCommingleCargoDetails.cargo1Percentage + '%' + '<br>' + loadablePlanCommingleCargoDetails.cargo2Abbreviation + ' - ' + loadablePlanCommingleCargoDetails.cargo2Percentage + '%';
  _loadablePlanCommingleCargoDetails.cargoBblsdbs = loadablePlanCommingleCargoDetails.cargo1Bblsdbs + '<br>' + loadablePlanCommingleCargoDetails.cargo2Bblsdbs;
  _loadablePlanCommingleCargoDetails.cargoBbls60f = this.decimalConvertion(_decimalPipe , loadablePlanCommingleCargoDetails.cargo1Bbls60f) + '<br>' + this.decimalConvertion(_decimalPipe , loadablePlanCommingleCargoDetails.cargo2Bbls60f);
  _loadablePlanCommingleCargoDetails.cargoLT = this.decimalConvertion(_decimalPipe , loadablePlanCommingleCargoDetails.cargo1LT) + '<br>' + this.decimalConvertion(_decimalPipe , loadablePlanCommingleCargoDetails.cargo2LT);
  _loadablePlanCommingleCargoDetails.cargoMT = this.decimalConvertion(_decimalPipe , loadablePlanCommingleCargoDetails.cargo1MT) + '<br>' + this.decimalConvertion(_decimalPipe , loadablePlanCommingleCargoDetails.cargo2MT);
  _loadablePlanCommingleCargoDetails.cargoKL = this.decimalConvertion(_decimalPipe , loadablePlanCommingleCargoDetails.cargo1KL) + '<br>' + this.decimalConvertion(_decimalPipe , loadablePlanCommingleCargoDetails.cargo2KL);
  return _loadablePlanCommingleCargoDetails;
 }

}
