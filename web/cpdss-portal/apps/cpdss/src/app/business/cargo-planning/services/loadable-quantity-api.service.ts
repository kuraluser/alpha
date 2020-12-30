import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CommonApiService } from '../../../shared/services/common/common-api.service';
import { LoadableQuantityModel, LoadableQuantityResponseModel, LodadableQuantity, ITableHeaderModel ,  LodadableQuantityPlan } from '../models/loadable-quantity.model';


@Injectable()
export class LoadableQuantityApiService {

  /**
   * 
   * Api Service for Loadable Quantity
   */
  constructor(private commonApiService: CommonApiService) { }

  /**
   * 
   * @param vesselId 
   * @param voyageId 
   * @param loadableStudyId 
   * Get api for loadable quantity
   */
  getLoadableQuantity(vesselId: number, voyageId: number, loadableStudyId: number): Observable<LoadableQuantityModel> {
    return this.commonApiService.get<LoadableQuantityModel>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}/loadable-quantity`);
  }

  /**
   * 
   * @param vesselId 
   * @param voyageId 
   * @param loadableStudyId 
   * @param loadableQuantity 
   * Save loadable quantity
   */
  saveLoadableQuantity(vesselId: number, voyageId: number, loadableStudyId: number, loadableQuantity: LodadableQuantity): Observable<LoadableQuantityResponseModel> {
    return this.commonApiService.post<LodadableQuantity, LoadableQuantityResponseModel>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}/loadable-quantity`, loadableQuantity);

  }
  /**
   * Set validation Error to form control
   */
  setValidationErrorMessage() {
    return {

      portName: {
        'required': 'LOADABLE_QUANTITY_PORTNAME_REQUIRED',
      },
      displacement: {
        'required': 'LOADABLE_QUANTITY_DISPLACEMENT_REQUIREDD',
      },
      arrivalMaxDraft: {
        'required': 'LOADABLE_QUANTITY_ARRIVAL_MAX_DRAFT_REQUIRED',
      },
      dwt: {
        'required': 'LOADABLE_QUANTITY_DWT_REQUIRED',
      },
      lwt: {
        'required': 'LOADABLE_QUANTITY_LWT_REQUIRED',
      },
      tpc: {
        'required': 'LOADABLE_QUANTITY_TPC_REQUIRED'
      },
      estSeaDensity: {
        'required': 'LOADABLE_QUANTITY_SEA_WATER_DENSITTY_REQUIRED',
        'min': 'LOADABLE_QUANTITY_ERROR'
      },
      sgCorrection: {
        'required': 'LOADABLE_QUANTITY_SG_CORRECTION_REQUIRED'
      },
      estimateSag: {
        'required': 'LOADABLE_QUANTITY_ESTIMATE_SAG_REQUIRED',
        'min': 'LOADABLE_QUANTITY_ERROR'
      },
      safCorrection: {
        'required': 'LOADABLE_QUANTITY_SAF_CORRECTION_REQUIRED'
      },
      foOnboard: {
        'required': 'LOADABLE_QUANTITY_FO_ONBOARD_REQUIRED',
        'pattern': 'LOADABLE_QUANTITY_ERROR'
      },
      doOnboard: {
        'required': 'LOADABLE_QUANTITY_DO_ONBOARD_REQUIRED',
        'pattern': 'LOADABLE_QUANTITY_ERROR'
      },
      freshWaterOnboard: {
        'required': 'LOADABLE_QUANTITY_FRESH_WATER_REQUIRED',
        'pattern': 'LOADABLE_QUANTITY_ERROR'
      },
      boilerWaterOnboard: {
        'required': 'LOADABLE_QUANTITY_BOILER_WATER_ONBOARD_REQUIRED',
        'pattern': 'LOADABLE_QUANTITY_ERROR'
      },
      ballast: {
        'required': 'LOADABLE_QUANTITY_BALLAST_REQUIRED',
        'min': 'LOADABLE_QUANTITY_ERROR'
      },
      constant: {
        'required': 'LOADABLE_QUANTITY_CONSTANT_REQUIRED'
      },
      others: {
        'required': 'LOADABLE_QUANTITY_OTHERS_REQUIRED',
        'min': 'LOADABLE_QUANTITY_ERROR'
      },
      distanceInSummerzone: {
        'required': 'LOADABLE_QUANTITY_DISATANCE_IN_SUMMERZONE_REQUIRED',
        'min': 'LOADABLE_QUANTITY_ERROR'
      },
      speedInSz: {
        'required': 'LOADABLE_QUANTITY_SPEED_IN_SZ_REQUIRED',
        'min': 'LOADABLE_QUANTITY_ERROR'
      },
      runningHours: {
        'required': 'LOADABLE_QUANTITY_RUNNING_HOURS_REQUIRED',
        'min': 'LOADABLE_QUANTITY_ERROR'
      },
      runningDays: {
        'required': 'LOADABLE_QUANTITY_RUNNING_DAYS_REQUIRED',
        'min': 'LOADABLE_QUANTITY_ERROR'
      },
      foConday: {
        'required': 'LOADABLE_QUANTITY_FO_CONS_PER_DAY_REQUIRED',
        'min': 'LOADABLE_QUANTITY_ERROR'
      },
      foConsInSz: {
        'required': 'LOADABLE_QUANTITY_FO_CONS_IN_SZ_REQUIRED',
        'min': 'LOADABLE_QUANTITY_ERROR'
      },
      subTotal: {
        'required': 'LOADABLE_QUANTITY_SUB_TOTAL_REQUIRED',
      },
      totalQuantity: {
        'required': 'LOADABLE_QUANTITY_TOTAL_QUANTITY_REQUIRED"'
      }
    }
  }

  /**
* 
* @param vesselId 
* @param voyageId 
* @param loadableStudyId 
* Get api for loadable quantity
*/
  getLoadableQuantityData(vesselId: number, voyageId: number, loadableStudyId: number, loadablePatternId: number): Observable<LodadableQuantityPlan> {
    return this.commonApiService.get<LodadableQuantityPlan>(`vessels/${vesselId}/voyages/${voyageId}/loadable-studies/${loadableStudyId}/loadable-pattern-details/${loadablePatternId}`);
  }

  /**
  * 
  * Get loadable quantity table header
  * @returns {ILoadableQuantityColumn[]}
  */
  getLoadableQuantityTableColumns(): ITableHeaderModel[] {
    return [
      { field: 'grade', header: 'Grade' , colspan: 0 , },
      {
        field: 'vin', header: 'Estimated',  colspan: 2,  subColumns: [
          { field: 'estimatedAPI', header: 'API' },
          { field: 'estimatedTemp', header: 'TEMP' }
        ]
      },
      {
        field: 'year', header: 'ORDER' ,  colspan: 2 ,subColumns: [
          { field: 'orderBblsdbs', header: 'BBL@OBS.TEMP' },
          { field: 'orderBbls60f', header: 'BBLS@60F' }
        ]
      },
      {
        field: 'brand', header: 'TLRNC', colspan: 2 ,  subColumns: [
          { field: 'minTolerence', header: 'Min' },
          { field: 'maxTolerence', header: 'Max' }
        ]
      },
      {
        field: 'color', header: 'LOADABLE',  colspan: 5 , subColumns: [
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
  * Get FormatedLoadable Quantity Data
  * @returns {LodadableQuantityPlan}
  * @param loadableQuantityObject 
  */
  public getFormatedLoadableQuantityData(_decimalPipe: any ,loadableQuantity: LodadableQuantityPlan): LodadableQuantityPlan {
    const _loadableQuantityDetails = loadableQuantity;
    _loadableQuantityDetails.estimatedAPI = this.decimalConvertion(_decimalPipe,_loadableQuantityDetails.estimatedAPI);
    _loadableQuantityDetails.estimatedTemp = this.decimalConvertion(_decimalPipe,_loadableQuantityDetails.estimatedAPI);
    _loadableQuantityDetails.orderBbls60f = this.decimalConvertion(_decimalPipe ,_loadableQuantityDetails.orderBbls60f);
    _loadableQuantityDetails.orderBblsdbs = this.decimalConvertion(_decimalPipe ,_loadableQuantityDetails.orderBblsdbs);
    _loadableQuantityDetails.minTolerence = _loadableQuantityDetails.minTolerence + '%';
    _loadableQuantityDetails.maxTolerence = _loadableQuantityDetails.maxTolerence + '%';
    _loadableQuantityDetails.loadableBbls60f = this.decimalConvertion(_decimalPipe , _loadableQuantityDetails.loadableBbls60f);
    _loadableQuantityDetails.loadableBblsdbs = this.decimalConvertion(_decimalPipe , _loadableQuantityDetails.loadableBblsdbs);
    _loadableQuantityDetails.loadableKL = this.decimalConvertion(_decimalPipe , _loadableQuantityDetails.loadableKL);
    _loadableQuantityDetails.loadableMT = this.decimalConvertion(_decimalPipe , _loadableQuantityDetails.loadableMT);
    _loadableQuantityDetails.loadableLT = this.decimalConvertion(_decimalPipe ,_loadableQuantityDetails.loadableLT);
    return loadableQuantity;
  }
  
  /**
  * 
  * Get FormatedLoadable Quantity Data
  * @returns {decimal converted value us number}
  */
  decimalConvertion(_decimalPipe: any , value: string | number) {
    return _decimalPipe.transform(value, '1.2-5');
  }
}
