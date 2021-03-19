import { ValidationErrors, ValidatorFn } from '@angular/forms';
import { OPERATIONS } from '../../models/cargo-planning.model';

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
          for(let i = 1; index - i >= 0; i++){
            if(typeof formArray[index-i].etd !== 'undefined'){
              firstValue = formArray[index - i].etd;
              break;
            }
          }
          secondValue = control.value;
          error = { etaFailed: true };
        }
        if (key === 'etd' && index < formArray.length - 1) {
          for(let j = 1; index + j < formArray.length; j++){
            if(typeof formArray[index+j].eta !== 'undefined'){
              secondValue = formArray[index + j].eta;
              break;
            }
          }
          firstValue = control.value;
          error = { etdFailed: true };
        }
        if (firstValue && secondValue && error) {
          firstValue.setSeconds(0,0)
          secondValue.setSeconds(0,0)
          if (secondValue <= firstValue) {
            return error;
          }
        }
      }
    }
    return null;
  }
};
