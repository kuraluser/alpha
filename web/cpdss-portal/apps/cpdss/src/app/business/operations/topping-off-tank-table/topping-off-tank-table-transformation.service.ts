import { Injectable } from '@angular/core';
import { DATATABLE_FIELD_TYPE, IDataTableColumn } from '../../../shared/components/datatable/datatable.model';
import { ValueObject } from '../../../shared/models/common.model';
import { IToppingOffSequence } from '../models/loading-information.model';
import { IToppingOffSequenceValueObject } from './topping-off-tank-table-transformation.model';

/**
 * Transformation Service for topping off tank table
 *
 * @export
 * @class ToppingOffTankTableTransformationService
 */
@Injectable()
export class ToppingOffTankTableTransformationService {

  constructor() { }

  /**
* Method for setting topping off tank table
*
* @returns {IDataTableColumn[]}
* @memberof ToppingOffTankTableTransformationService
*/
  getDatatableColumns(): IDataTableColumn[] {
    return [
      {
        field: 'displayOrder',
        header: 'LOADABLE_PLAN_TOPPING_OFF_TANK_SEQUENCE',
      },
      {
        field: 'shortName',
        header: 'LOADABLE_PLAN_TOPPING_OFF_TANK',
      },
      {
        field: 'cargoAbbreviation',
        header: 'LOADABLE_PLAN_TOPPING_OFF_TANK_TYPE',
        fieldType: DATATABLE_FIELD_TYPE.BADGE,
        badgeColorField: 'colourCode'
      },
      {
        field: 'ullage',
        header: 'LOADABLE_PLAN_TOPPING_OFF_TANK_ULLAGE',
        fieldType: DATATABLE_FIELD_TYPE.NUMBER,
        fieldPlaceholder: "LOADABLE_PLAN_TOPPING_OFF_TANK_ULLAGE_PLACEHOLDER"
      },
      {
        field: 'quantity',
        header: 'LOADABLE_PLAN_TOPPING_OFF_TANK_QUANTITY',
      },
      {
        field: 'fillingRatio',
        header: 'LOADABLE_PLAN_TOPPING_OFF_TANK_FILLING',
      },
      {
        field: 'remark',
        header: 'LOADABLE_PLAN_TOPPING_OFF_TANK_REMARKS',
        fieldType: DATATABLE_FIELD_TYPE.TEXT,
        fieldPlaceholder: "LOADABLE_PLAN_TOPPING_OFF_TANK_REMARKS_PLACEHOLDER"
      },
    ];
  }

  /**
* Method for converting topping sequence to value object model
*
* @param {IToppingOffSequence} toppingOffSequence
* @param {boolean} [isNewValue=true]
* @returns {IToppingOffSequenceValueObject}
* @memberof ToppingOffTankTableTransformationService
*/
  getToppingOffSequenceAsValueObject(toppingOffSequence: IToppingOffSequence, isNewValue = true, isEditable = true): IToppingOffSequenceValueObject {
    const _toppingOffSequence = <IToppingOffSequenceValueObject>{};
    _toppingOffSequence.id = toppingOffSequence.id;
    _toppingOffSequence.loadingInfoId = toppingOffSequence.loadingInfoId;
    _toppingOffSequence.orderNumber = toppingOffSequence.orderNumber;
    _toppingOffSequence.tankId = toppingOffSequence.tankId;
    _toppingOffSequence.cargoId = toppingOffSequence.cargoId;
    _toppingOffSequence.shortName = toppingOffSequence.shortName;
    _toppingOffSequence.cargoName = toppingOffSequence.cargoName;
    _toppingOffSequence.cargoAbbreviation = toppingOffSequence.cargoAbbreviation;
    _toppingOffSequence.colourCode = toppingOffSequence.colourCode;
    _toppingOffSequence.remark = new ValueObject<string>(toppingOffSequence.remark, true, isNewValue, false, true);
    _toppingOffSequence.ullage = new ValueObject<number>(toppingOffSequence.ullage, true, isNewValue, false, true);
    _toppingOffSequence.quantity = toppingOffSequence.quantity;
    _toppingOffSequence.fillingRatio = toppingOffSequence.fillingRatio;
    _toppingOffSequence.api = toppingOffSequence.api;
    _toppingOffSequence.temperature = toppingOffSequence.temperature;
    _toppingOffSequence.displayOrder = toppingOffSequence.displayOrder;
    return _toppingOffSequence;
  }

  /**
   * Method for converting from cargonomination value object model
   *
   * @param {ICargoNominationValueObject} cargoNomination
   * @returns {ICargoNomination}
   * @memberof LoadableStudyDetailsTransformationService
   */
   getToppingOffSequenceAsValue(cargoTypeList: IToppingOffSequenceValueObject[][]): IToppingOffSequence[] {
    let toppingOffSequence: IToppingOffSequence[] = [];
    cargoTypeList?.forEach((toppingOffSequenceValueObject) => {
     toppingOffSequenceValueObject?.forEach((valueObject) => {
      let _toppingOffSequence = <IToppingOffSequence>{};
      _toppingOffSequence.id = valueObject.id;
      _toppingOffSequence.loadingInfoId = valueObject.loadingInfoId;
      _toppingOffSequence.orderNumber = valueObject.orderNumber;
      _toppingOffSequence.tankId = valueObject.tankId;
      _toppingOffSequence.cargoId = valueObject.cargoId;
      _toppingOffSequence.shortName = valueObject.shortName;
      _toppingOffSequence.cargoName = valueObject.cargoName;
      _toppingOffSequence.cargoAbbreviation = valueObject.cargoAbbreviation;
      _toppingOffSequence.colourCode = valueObject.colourCode;
      _toppingOffSequence.remark = valueObject.remark.value;
      _toppingOffSequence.ullage = Number(valueObject.ullage.value);
      _toppingOffSequence.quantity = valueObject.quantity;
      _toppingOffSequence.fillingRatio = valueObject.fillingRatio;
      _toppingOffSequence.displayOrder = valueObject.displayOrder;
      toppingOffSequence.push(_toppingOffSequence);
     })
    })
    return toppingOffSequence;
  }

}
