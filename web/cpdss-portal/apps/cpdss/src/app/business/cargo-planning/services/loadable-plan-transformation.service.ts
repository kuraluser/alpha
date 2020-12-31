import { Injectable } from '@angular/core';
import { ITableHeaderModel } from '../models/loadable-plan.model';

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

}
