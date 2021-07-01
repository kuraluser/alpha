import { FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';


/**
 * Validator for max min values.
 *
 * @export
 * @param {*} key
 * @param {*} rowIndex
 * @param {*} inputIndex
 * @param {*} [max=null]
 * @param {*} [min=null]
 * @return {*}  {ValidatorFn}
 */

export function RulesValidator(key, rowIndex, inputIndex, max = null, min = null): ValidatorFn {
  return (control: FormControl): ValidationErrors | null => {
    let maxkey = rowIndex + 'input' + '_' + inputIndex + '_' + max;
    let minkey = rowIndex + 'input' + '_' + inputIndex + '_' + min;
    if (!control.root || !control.parent) {
      return null;
    }
   
    if (max != null) {
      if (Number(control.value) > Number(max) && Boolean(control.value)) {
        return { [maxkey]: true }
      }
    }

    else if (min != null) {
      {
        if (Number(control.value) < Number(min) && Boolean(control.value)) {
          return { [minkey]: true }
        }
      }
    }
  }
};
