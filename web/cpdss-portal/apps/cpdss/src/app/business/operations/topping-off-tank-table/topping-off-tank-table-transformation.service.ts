import { Injectable } from '@angular/core';
import { DATATABLE_FIELD_TYPE, IDataTableColumn } from '../../../shared/components/datatable/datatable.model';

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
        header: 'LOADABLE_PLAN_TOPPING_OFF_TANK_ULLAGE'
      },
      {
        field: 'quantity',
        header: 'LOADABLE_PLAN_TOPPING_OFF_TANK_QUANTITY',
      },
      {
        field: 'fillingRatio',
        header: 'LOADABLE_PLAN_TOPPING_OFF_TANK_FILLING',
      }
    ];
  }

}

 
