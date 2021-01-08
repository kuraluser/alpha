import { FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * Validator Function for invalid check for numbers
 *
 * @export
 * @param {number} decimalPlaces
 * @param {number} [digitLength=null]
 * @param {boolean} [isNegativeAccept=true]
 * @returns {ValidatorFn}
 */
export function numberValidator(decimalPlaces: number, digitLength: number = null, isNegativeAccept = true): ValidatorFn {
  return (control: FormControl): ValidationErrors | null => {
    if (control && control.value) {

      // Check if value end with a period throw pattern error
      if (/^\-?\d+(\.)$/.test(control.value)) {
        return { pattern: true };
      }
      // Check if number is valid format
      else if (!/^\-?(\d+)?(\.\d+)?$/.test(control.value) && control.value.length > 1) {
        //remove non number characters except . and - 's except in starting position
        control.setValue(control.value.replace(/[^0-9\.\-]/g, '').replace(/(?!^)\-/g, ''));
        if (!control.value) {
          return { required: true };
        }
      }

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
      if (control?.value?.toString()?.includes('-') && !isNegativeAccept) {
        const value = Math.abs(control.value);
        control.setValue(Number(value));
        if (!value) {
          return { required: true };
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

