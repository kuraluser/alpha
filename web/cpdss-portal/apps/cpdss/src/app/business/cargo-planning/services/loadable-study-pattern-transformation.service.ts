import { Injectable } from '@angular/core';
import { DATATABLE_FIELD_TYPE, IDataTableColumn } from '../../../shared/components/datatable/datatable.model';
import { ValueObject } from '../../../shared/models/common.model';
import { ICommingleDetailValueObject, ICommingleDetails } from '../models/cargo-planning.model';

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
        header: 'GRADE',
        fieldType: DATATABLE_FIELD_TYPE.TEXT
      },
      {
        field: 'tankShortName',
        header: 'TANK',
        fieldType: DATATABLE_FIELD_TYPE.TEXT
      },
      {
        field: 'cargoTotalQuantity',
        header: 'QUANTITY (BLS)',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER
      },
      {
        field: 'api',
        header: 'API',
        fieldType: DATATABLE_FIELD_TYPE.TEXT
      },
      {
        field: 'temperature',
        header: 'TEMP (F)',
        fieldType: DATATABLE_FIELD_TYPE.TEXT
      },
      {
        field: 'cargoPercentage',
        header: 'COMPOSITION PERCENTAGE',
        fieldType: DATATABLE_FIELD_TYPE.TEXT
      },
      {
        field: 'cargoQuantity',
        header: 'BREAKDOWN QUANTITY (BLS)',
        fieldType: DATATABLE_FIELD_TYPE.TEXT
      }

    ]
  }

   /**
   * Method for converting ports data to value object model
   *
   * @param {ICommingleDetails} port
   * @param {boolean} [isNewValue=true]
   * @returns {ICommingleDetailValueObject}
   * @memberof LoadableStudyPatternTransformationService
   */
  getCommingleDetailAsValueObject(commingleDetail: ICommingleDetails, isNewValue = true, isEditable = true): ICommingleDetailValueObject {
    const _commingleDetail = <ICommingleDetailValueObject>{};
    _commingleDetail.id = commingleDetail.id;
    _commingleDetail.tankShortName = new ValueObject<string>(commingleDetail.tankShortName, true, isNewValue, false, isEditable);
    _commingleDetail.cargo1Abbrivation = new ValueObject<string>(commingleDetail.cargo1Abbrivation, true, isNewValue, false, isEditable);
    _commingleDetail.cargo2Abbrivation = new ValueObject<string>(commingleDetail.cargo2Abbrivation, true, isNewValue, false, isEditable);
    _commingleDetail.grade = new ValueObject<string>(commingleDetail.grade, true, isNewValue, false, isEditable);
    _commingleDetail.quantity = new ValueObject<string>(commingleDetail.quantity, true, isNewValue, false, isEditable);
    _commingleDetail.api = new ValueObject<string>(commingleDetail.api, true, isNewValue, false, isEditable);
    _commingleDetail.temperature = new ValueObject<string>(commingleDetail.temperature, true, isNewValue, false, isEditable);
    _commingleDetail.cargo1Quantity = new ValueObject<string>(commingleDetail.temperature, true, isNewValue, false, isEditable);
    _commingleDetail.cargo2Quantity = new ValueObject<string>(commingleDetail.temperature, true, isNewValue, false, isEditable);
    _commingleDetail.cargo1Percentage = new ValueObject<string>(commingleDetail.temperature, true, isNewValue, false, isEditable);
    _commingleDetail.cargo2Percentage = new ValueObject<string>(commingleDetail.temperature, true, isNewValue, false, isEditable);
    _commingleDetail.cargoTotalQuantity = new ValueObject<number>(Number(commingleDetail.cargo1Quantity) + Number(commingleDetail.cargo2Quantity), true, isNewValue, false, isEditable);
    _commingleDetail.cargoQuantity = new ValueObject<string>(commingleDetail.cargo1Quantity + ' ' + commingleDetail.cargo2Quantity, true, isNewValue, false, isEditable);
    _commingleDetail.cargoPercentage = new ValueObject<string>(commingleDetail.cargo1Percentage + '% ' + commingleDetail.cargo2Percentage +'%', true, isNewValue, false, isEditable);
    return _commingleDetail;
  }

}
