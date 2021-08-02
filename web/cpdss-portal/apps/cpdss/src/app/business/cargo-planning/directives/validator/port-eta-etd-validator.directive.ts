import { ValidationErrors, ValidatorFn } from '@angular/forms';
import { OPERATIONS } from '../../../core/models/common.model';
import * as moment from 'moment';


/**
 * Validator Function for eta and etd
 * @param index
 */
export function portEtaEtdValidator(key: string, index: number): ValidatorFn {
  return (control): ValidationErrors | null => {
    if (control.parent && control.value && control.root) {
      const formArray = control.root.value?.dataTable;
      if (formArray && formArray.length) {
        let firstValue: Date, secondValue: Date, error;
        if (key === 'eta' && index > 0) {
          for (let i = index - 1; i >= 0; i--) {
            if (formArray[i].etd) {
              firstValue = moment(formArray[i].etd).subtract(formArray[i].port?.timezoneOffsetVal, 'hours').toDate();
              break;
            }
          }
          secondValue = moment(control.value).subtract(control.parent?.value?.port?.timezoneOffsetVal, 'hours').toDate();
          error = { etaFailed: true };
        }
        if (key === 'etd' && index < formArray.length) {
          for (let j = index + 1; j < formArray.length; j++) {
            if (formArray[j].eta) {
              secondValue = moment(formArray[j].eta).subtract(formArray[j].port?.timezoneOffsetVal, 'hours').toDate();
              break;
            }
          }
          firstValue = moment(control.value).subtract(control.parent?.value?.port?.timezoneOffsetVal, 'hours').toDate();
          error = { etdFailed: true };
        }
        if (firstValue && secondValue && error) {
          firstValue.setSeconds(0, 0)
          secondValue.setSeconds(0, 0)
          if (secondValue <= firstValue) {
            if(key === 'eta'){
              if(formArray[control.parent.value.portOrder - 2] && formArray[control.parent.value.portOrder - 2].port.id === control.parent.value.port.id && [OPERATIONS.LOADING, OPERATIONS.DISCHARGING, OPERATIONS.BUNKERING].includes(formArray[control.parent.value.portOrder - 2].operation?.id)){
                return null;
              } else {
                return error;
              }
            }
            if(key === 'etd'){
              if(formArray[control.parent.value.portOrder] && formArray[control.parent.value.portOrder].port.id === control.parent.value.port.id && [OPERATIONS.LOADING, OPERATIONS.DISCHARGING, OPERATIONS.BUNKERING].includes(formArray[control.parent.value.portOrder].operation?.id)){
                return null;
              } else {
                return error;
              }
            }
          }
        }
      }
    }
    return null;
  }
};
