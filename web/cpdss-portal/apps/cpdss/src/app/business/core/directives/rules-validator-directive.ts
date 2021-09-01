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

export function RulesValidator(key, elementIndex, rowIndex, inputIndex, max = null, min = null): ValidatorFn {
  return (control: FormControl): ValidationErrors | null => {
    const maxkey = elementIndex + '_' + rowIndex + '_' + inputIndex + '_' + 'max';
    const minkey = elementIndex + '_' + rowIndex + '_' + inputIndex + '_' + 'min';
    if (!control.root || !control.parent) {
      return null;
    }
    if (control.value == null || typeof control.value === 'undefined' || control.value === "" || isNaN(control.value)) {
      return null;
    }
    if (max != null) {
      if (Number(control.value) > Number(max)) {
        console.log(control.value)
        return { [maxkey]: true }
      }
    }
    if (min != null) {
      if (Number(control.value) < Number(min)) {
        console.log(control.value)
        return { [minkey]: true }
      }
    }
  }
};
