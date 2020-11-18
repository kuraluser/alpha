import { FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * Validator Function for remove space
 * @param control 
 */
export function numberValidator(decimalPlaces, digitLength = null): ValidatorFn {
  return (control: FormControl): ValidationErrors | null => {
    if (control && control.value) {
      const number = control.value.toString().split('.');
      if (decimalPlaces) {
        if (number[1] && number[1].length > decimalPlaces) {
          let value = control.value.toString();
          value = value.slice(0, (value.indexOf(".")) + decimalPlaces + 1);
          control.setValue(Number(value));
          if (!value) {
            return { required: true };
          }
        }
      }
      if (digitLength) {
        const length = number[0].replace('-', '').length;
        if (number[0] && length > digitLength) {
          let value = control.value.toString();
          value = value.slice(0, (value.indexOf(".")));
          control.setValue(Number(value));
          if (!value) {
            return { required: true };
          }
        }
      }
    }
    return null;
  };
};

