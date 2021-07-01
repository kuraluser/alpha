import { ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * Validator Function for compare sea water density range
 * @param control 
 */
export function seaWaterDensityRangeValidator(): ValidatorFn {
  return (control): ValidationErrors | null => {
    return control.value && (control.value < 0.6 || control.value > 1.10) ? { waterDensityError: true } : null;
  }
}
