import { FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * Validator Function for remove space
 * @param control 
 */
export function numberValidator(decimalPlaces): ValidatorFn {
  return (control: FormControl): ValidationErrors | null => {
    if (control && control.value) {
      const decimal = control.value.toString().split('.');
      if (decimal[1] && decimal[1].length > decimalPlaces) {
        let value = control.value.toString();
        value = value.slice(0, (value.indexOf(".")) + decimalPlaces + 1);
        control.setValue(Number(value));
        if (!value) {
          return { required: true };
        }
      }
    }
    return null;
  };
};