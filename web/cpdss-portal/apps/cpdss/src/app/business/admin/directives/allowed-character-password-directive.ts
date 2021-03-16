import { FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';


/**
 * allow character in password
 * @param {FormControl}control 
 */
export const isAllowedCharacterPassword: ValidatorFn = (control: FormControl): ValidationErrors | null => {
  const specialChar = /^[a-zA-Z0-9@#$%&*]+$/;
  if (control?.value && control?.value !== '' && !control.errors?.pattern && !specialChar.test(control.value)) {
    return ({ isAllowedChar: true });
  }
  return null;
};

