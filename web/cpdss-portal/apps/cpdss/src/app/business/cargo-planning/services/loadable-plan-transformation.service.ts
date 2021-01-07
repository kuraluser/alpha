import { Injectable } from '@angular/core';

import { ICargoTank } from '../../core/models/common.model';
import { CargoPlanningModule } from '../cargo-planning.module';
import { ILoadableQuantityCargo, ICargoTankDetail , ILoadableQuantityCommingleCargo , ICommingleCargoDispaly } from '../models/loadable-plan.model';
import { IDataTableColumn } from '../../../shared/components/datatable/datatable.model';

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
      { field: 'year', header: "" , subHeader: 'ETA_ETD_DRAFT_AFT' },
      { field: 'year', header: "" , subHeader: 'ETA_ETD_DRAFT_MSHIP' },
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
      { field: 'grade', header: 'LOADABLE_PLAN_COMMINGLED_CARGO_GRADE'  , rowspan: 2 },
      { field: 'tankName', header: 'LOADABLE_PLAN_COMMINGLED_CARGO_TANK' , rowspan: 2 },
      { field: 'quantity', header: 'LOADABLE_PLAN_COMMINGLED_CARGO_QUANTITY' , rowspan: 2 },
      { field: 'api', header: 'LOADABLE_PLAN_COMMINGLED_CARGO_API' , rowspan: 2 },
      { field: 'temp', header: 'LOADABLE_PLAN_COMMINGLED_CARGO_TEMP' , rowspan: 2 },
      {
         header: 'LOADABLE_PLAN_COMMINGLED_CARGO_COMPOSITION_BREAKDOWN' , colspan: 6 , subColumns: [
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
        header: 'LOADABLE_PLAN_ESTIMATED', colspan: 2, className: "header-border" ,subColumns: [
          { field: 'estimatedAPI', header: 'LOADABLE_PLAN_ESTIMATED_API' },
          { field: 'estimatedTemp', header: 'LOADABLE_PLAN_ESTIMATED_TEMP' }
        ]
      },
      {
        header: 'LOADABLE_PLAN_ORDER', colspan: 2, className: "header-border" , subColumns: [
          { field: 'orderBblsdbs', header: 'LOADABLE_PLAN_ORDER_BBLS@OBS_TEMP' },
          { field: 'orderBbls60f', header: 'LOADABLE_PLAN_ORDER_BBLS@60F' }
        ]
      },
      {
        header: 'LOADABLE_PLAN_TLRNC', colspan: 2, className: "header-border" , subColumns: [
          { field: 'minTolerence', header: 'LOADABLE_PLAN_TLRNC_MIN' },
          { field: 'maxTolerence', header: 'LOADABLE_PLAN_TLRNC_MAX' }
        ]
      },
      {
        header: 'LOADABLE_PLAN_LOADABLE', colspan: 5, className: "header-border" , subColumns: [
          { field: 'loadableBblsdbs', header: 'LOADABLE_PLAN_LOADABLE_BBLS@DBS_TEMP' },
          { field: 'loadableBbls60f', header: 'LOADABLE_PLAN_LOADABLE_BBLS@60F' },
          { field: 'loadableLT', header: 'LOADABLE_PLAN_LOADABLE_LT' },
          { field: 'loadableMT', header: 'LOADABLE_PLAN_LOADABLE_MT' },
          { field: 'loadableKL', header: 'LOADABLE_PLAN_LOADABLE_KL' }
        ]
      },
      {
        field: 'differencePercentage', header: 'LOADABLE_PLAN_LOADABLE_DIFF' , rowspan: 2
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
    _loadableQuantityDetails.estimatedAPI = this.decimalConvertion(_decimalPipe, _loadableQuantityDetails.estimatedAPI , '1.2-4');
    _loadableQuantityDetails.estimatedTemp = this.decimalConvertion(_decimalPipe, _loadableQuantityDetails.estimatedTemp , '1.2-4');
    _loadableQuantityDetails.orderBbls60f = this.decimalConvertion(_decimalPipe, _loadableQuantityDetails.orderBbls60f , '1.0-4');
    _loadableQuantityDetails.orderBblsdbs = this.decimalConvertion(_decimalPipe, _loadableQuantityDetails.orderBblsdbs , '1.0-4');
    _loadableQuantityDetails.minTolerence = _loadableQuantityDetails.minTolerence + '%';
    _loadableQuantityDetails.maxTolerence = _loadableQuantityDetails.maxTolerence + '%';
    _loadableQuantityDetails.loadableBbls60f =  this.decimalConvertion(_decimalPipe,_loadableQuantityDetails.loadableBbls60f , '1.0-4');
    _loadableQuantityDetails.loadableBblsdbs = this.decimalConvertion(_decimalPipe,_loadableQuantityDetails.loadableBblsdbs , '1.0-4');
    _loadableQuantityDetails.loadableKL = this.decimalConvertion(_decimalPipe, _loadableQuantityDetails.loadableKL , '1.2-4');
    _loadableQuantityDetails.loadableMT = this.decimalConvertion(_decimalPipe, _loadableQuantityDetails.loadableMT , '1.2-4');
    _loadableQuantityDetails.loadableLT = this.decimalConvertion(_decimalPipe, _loadableQuantityDetails.loadableLT , '1.2-4');
    _loadableQuantityDetails.differencePercentage = _loadableQuantityDetails.differencePercentage + '%';
    return loadableQuantity;
  }

  /**
  * 
  * Get Formated Loadable Quantity Data
  * @returns {decimal converted value us number}
  */
  decimalConvertion(_decimalPipe: any, value: string | number , decimalType: string) {
    return _decimalPipe.transform(value, decimalType);
  }

  /**
  * 
  * Get Formated Loadable Quantity Data
  * @returns { ICommingleCargoDispaly }
  * @param loadablePlanCommingleCargoDetails  
  */
 public getFormatedLoadableCommingleCargo(_decimalPipe: any, loadablePlanCommingleCargoDetails : ILoadableQuantityCommingleCargo):  ICommingleCargoDispaly {
  const _loadablePlanCommingleCargoDetails = <ICommingleCargoDispaly>{};
  _loadablePlanCommingleCargoDetails.api = this.decimalConvertion(_decimalPipe , loadablePlanCommingleCargoDetails.api , '1.2-4');
  _loadablePlanCommingleCargoDetails.tankName = loadablePlanCommingleCargoDetails.tankName;
  _loadablePlanCommingleCargoDetails.grade = loadablePlanCommingleCargoDetails.grade;
  _loadablePlanCommingleCargoDetails.quantity = loadablePlanCommingleCargoDetails.quantity;
  _loadablePlanCommingleCargoDetails.temp = this.decimalConvertion(_decimalPipe , loadablePlanCommingleCargoDetails.temp , '1.2-4');
  _loadablePlanCommingleCargoDetails.cargoPercentage = loadablePlanCommingleCargoDetails.cargo1Abbreviation + ' - ' + loadablePlanCommingleCargoDetails.cargo1Percentage + '%' + '<br>' + loadablePlanCommingleCargoDetails.cargo2Abbreviation + ' - ' + loadablePlanCommingleCargoDetails.cargo2Percentage + '%';
  _loadablePlanCommingleCargoDetails.cargoBblsdbs = loadablePlanCommingleCargoDetails.cargo1Bblsdbs + '<br>' + loadablePlanCommingleCargoDetails.cargo2Bblsdbs;
  _loadablePlanCommingleCargoDetails.cargoBbls60f =  loadablePlanCommingleCargoDetails.cargo1Bbls60f  + '<br>' +  loadablePlanCommingleCargoDetails.cargo2Bbls60f;
  _loadablePlanCommingleCargoDetails.cargoLT = this.decimalConvertion(_decimalPipe , loadablePlanCommingleCargoDetails.cargo1LT , '1.2-4') + '<br>' + this.decimalConvertion(_decimalPipe , loadablePlanCommingleCargoDetails.cargo2LT , '1.2-4');
  _loadablePlanCommingleCargoDetails.cargoMT = this.decimalConvertion(_decimalPipe , loadablePlanCommingleCargoDetails.cargo1MT , '1.2-4') + '<br>' + this.decimalConvertion(_decimalPipe , loadablePlanCommingleCargoDetails.cargo2MT , '1.2-4' );
  _loadablePlanCommingleCargoDetails.cargoKL = this.decimalConvertion(_decimalPipe , loadablePlanCommingleCargoDetails.cargo1KL , '1.2-4') + '<br>' + this.decimalConvertion(_decimalPipe , loadablePlanCommingleCargoDetails.cargo2KL , '1.2-4');
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
  formatCargoTanks(cargoTank: ICargoTank, cargoTankDetails: ICargoTankDetail[]): ICargoTank {
    cargoTank.commodity = cargoTankDetails?.find(cargo => cargoTank?.id === cargo?.tankId);

    return cargoTank;
  }

}
