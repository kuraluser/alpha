import { FormControl } from "@angular/forms";

/**
 * Validator directive to check special characters
 * @export
 * @param {FormControl} control
 * @return {*}  {{ specialCharacter: boolean }}
 */
export function specialCharacterValidator(control: FormControl): { specialCharacter: boolean } {
  const specialCharRegx: RegExp = /^[a-zA-Z0-9 ]*$/;
  if (control.value) {
    const isContainSpecialChar = specialCharRegx.test(control.value);
    const isValid = isContainSpecialChar;
    return isValid ? null : { 'specialCharacter' : true };
  }
}