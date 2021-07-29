import { FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * validator for alpha-numeric
 * @param control 
 */
export const alphaNumericOnlyValidator: ValidatorFn = (control: FormControl): ValidationErrors | null => {
  if (control && control.value && !/^[a-zA-Z0-9]+$/.test(control.value)) {
    const value = control.value.replace(/[^A-Za-z0-9]/g, '')
    control.setValue(value);
    if (!value) {
      return { required: true };
    }
  }

  return null;
};
