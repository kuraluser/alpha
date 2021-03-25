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
          for (let i = index - 1; i >= 0; i--) {
            if (formArray[i].etd) {
              firstValue = formArray[i].etd;
              break;
            }
          }
          secondValue = control.value;
          error = { etaFailed: true };
        }
        if (key === 'etd' && index < formArray.length) {
          for (let j = index + 1; j < formArray.length; j++) {
            if (formArray[j].eta) {
              secondValue = formArray[j].eta;
              break;
            }
          }
          firstValue = control.value;
          error = { etdFailed: true };
        }
        if (firstValue && secondValue && error) {
          firstValue.setSeconds(0, 0)
          secondValue.setSeconds(0, 0)
          if (secondValue <= firstValue) {
            return error;
          }
        }
      }
    }
    return null;
  }
};
