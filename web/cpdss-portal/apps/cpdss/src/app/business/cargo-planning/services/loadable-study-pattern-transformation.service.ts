import { Injectable } from '@angular/core';
import { IDataTableColumn } from '../../../shared/components/datatable/datatable.model';
import { ICommingleDetails } from '../models/cargo-planning.model';

@Injectable({
  providedIn: 'root'
})
export class LoadableStudyPatternTransformationService {

  constructor() { }


  /**
* Method for setting commingle details grid columns
*
* @returns {IDataTableColumn[]}
* @memberof LoadableStudyPatternTransformationService
*/
  getCommingleDetailsDatatableColumns(): IDataTableColumn[] {
    return [
      {
        field: 'grade',
        header: 'LOADABLE_PATTERN_GRADE'
      },
      {
        field: 'tankShortName',
        header: 'LOADABLE_PATTERN_TANK'
      },
      {
        field: 'quantity',
        header: 'LOADABLE_PATTERN_TOTAL_QUANTITY'
      },
      {
        field: 'api',
        header: 'LOADABLE_PATTERN_API'
      },
      {
        field: 'temperature',
        header: 'LOADABLE_PATTERN_TEMP'
      },
      {
        field: '',
        header: 'LOADABLE_PATTERN_COMPOSITION_BREAKDOWN',
        fieldColumnClass: 'commingle-composition',
        columns: [
          {
            field: 'cargoPercentage',
            header: 'LOADABLE_PATTERN_PERCENTAGE',
            fieldClass: 'commingle-composition-percentage'
          },
          {
            field: 'cargoQuantity',
            header: 'LOADABLE_PATTERN_QUANTITY',
            fieldClass: 'commingle-composition-quantity'
          }
        ]
      }

    ]
  }

  /**
  * Method for transforming commingled data
  *
  * @param {ICommingleDetails} commingleDetail
  * @returns {ICommingleDetails}
  * @memberof LoadableStudyPatternTransformationService
  */
  formatCommingleDetail(commingleDetail: ICommingleDetails): ICommingleDetails {
    commingleDetail.cargoQuantity = commingleDetail.cargo1Quantity + '\n' + commingleDetail.cargo2Quantity;
    commingleDetail.cargoPercentage = commingleDetail.cargo1Abbrivation + '-' + commingleDetail.cargo1Percentage + '%\n' + commingleDetail.cargo2Abbrivation + '-' + commingleDetail.cargo2Percentage + '%';
    return commingleDetail;
  }

}
