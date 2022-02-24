import { FormControl } from "@angular/forms";

/**
 * Validator directive to check number and special characters
 * @export
 * @param {FormControl} control
 * @return {*}  {{ specialCharacter: boolean }}
 */
export function numberSpecialCharacterValidator(control: FormControl): { specialCharacter: boolean } {
  const specialCharRegx: RegExp = /^[a-zA-Z ]*$/;
  if (control.value) {
    const isContainSpecialChar = specialCharRegx.test(control.value);
    return isContainSpecialChar ? null : { 'specialCharacter' : true };
  }
}
