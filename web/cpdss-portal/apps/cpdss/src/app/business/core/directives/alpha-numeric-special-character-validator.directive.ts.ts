import { FormControl } from "@angular/forms";

/**
 * Validator directive to ensure  atleast one text and only the specified special characters are entered.
 * @export
 * @param {FormControl} control
 * @return {*}  {{ textError: boolean }}
 */
export function alphaNumericSpecialCharacterValidator(control: FormControl): { textError: boolean } {
  const specialCharRegx: RegExp = /^[a-zA-Z0-9#&*()/=+\[\]':"-]*$/;
  if (control.value) {
    if(!control.value.match(/[a-zA-Z]/g)){
      return { 'textError':true }
    }
    const isContainSpecialChar = specialCharRegx.test(control.value);
    return isContainSpecialChar ? null : { 'textError': true };
  }
}