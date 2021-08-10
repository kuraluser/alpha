import { FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * validator for alpha character 
 * @param control 
 */
export const isAlphaCharacterAvaiable: ValidatorFn = (control: FormControl): ValidationErrors | null => {
  if (control && control.value && control.valid &&!control.value.match(/[a-zA-Z]/g)) {
      return { patternMatch: true };
  }
  return null;
};