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
  getEtaEtdTableColumns(): ITableHeaderModel[]{
    return [
      { field: 'year', header: '' },
      { field: 'year', header: 'PORT' },
      { field: 'year', header: 'ETA/ETD DATE' },
      { field: 'year', header: 'ETA/ETD TIME' },
      { field: 'year', header: 'DRAFT',rowspan:3, subColumns: [
        { field: 'year', header: 'FORE' },
        { field: 'year', header: 'AFT' },
        { field: 'year', header: 'MSHIP' }
      ]},
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
}
