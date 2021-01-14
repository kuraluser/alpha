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
        let firstValue, secondValue, error;
        if (key == 'eta' && index > 0) {
          firstValue = formArray[index - 1].etd;
          secondValue = control.value;
          error = { etaFailed: true };
        }
        if (key == 'etd' && index < formArray.length - 1) {
          secondValue = formArray[index + 1].eta;
          firstValue = control.value;
          error = { etdFailed: true };
        }
        if (firstValue && secondValue && error && secondValue <= firstValue) {
          return error;
        }
      }
    }
    return null;
  }
};
