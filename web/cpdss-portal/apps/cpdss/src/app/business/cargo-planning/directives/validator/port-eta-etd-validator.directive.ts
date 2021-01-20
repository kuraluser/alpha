import { FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

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
          firstValue = formArray[index - 1].etd;
          secondValue = control.value;
          error = { etaFailed: true };
        }
        if (key === 'etd' && index < formArray.length - 1) {
          secondValue = formArray[index + 1].eta;
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
