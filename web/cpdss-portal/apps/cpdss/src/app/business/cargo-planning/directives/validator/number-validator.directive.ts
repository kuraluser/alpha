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
    if (!control || typeof control.value == 'undefined' || control.value == null || control.value == '') {
      return;
    }
    let oldValue = control.value.toString();;
    let value = control.value.toString();;
    let negative = false;
    let dotEntered = false;
    value = value.replace('+', '');
    if (isNegativeAccept && value[0] == '-') {
      negative = true;
    }
    value = value.replace('-', '');
    let number: string[] = value.split('.');
    if (number[0] && number[0].length) {
      dotEntered = number[0].length !== value.length
      number[0] = number[0].replace('.', '')
    }
    if (number[1] && number[1].length) {
      dotEntered = true;
      number[1] = number[1].replace('.', '')
    }
    if (decimalPlaces > 0 && number[1] && number[1].length > decimalPlaces)
      number[1] = number[1].slice(0, decimalPlaces);
    if (digitLength && digitLength > 0 && number[0] && number[0].length > digitLength)
      number[0] = number[0].slice(0, digitLength);
    value = (negative ? '-' : '') + number[0] + (decimalPlaces && dotEntered ? ('.' + (number[1] ? number[1] : '')) : '');
    if (oldValue !== value)
      control.setValue(Number(value));
  }
}

