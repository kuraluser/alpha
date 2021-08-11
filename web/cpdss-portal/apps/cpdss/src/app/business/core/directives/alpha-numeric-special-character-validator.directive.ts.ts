import { FormControl } from "@angular/forms";

/**
 * Validator directive to ensure  atleast one text and only the specified special characters are entered.
 * @export
 * @param {FormControl} control
 * @return {*}  {{ textError: boolean }}
 */
export function alphaNumericSpecialCharacterValidator(control: FormControl): { textError: boolean } {
  const specialCharRegx: RegExp = /^[a-zA-Z]+[0-9#&*()/=\[\]':"-]*$/;
  if (control.value) {
    const isContainSpecialChar = specialCharRegx.test(control.value);
    return isContainSpecialChar ? null : { 'textError': true };
  }
}