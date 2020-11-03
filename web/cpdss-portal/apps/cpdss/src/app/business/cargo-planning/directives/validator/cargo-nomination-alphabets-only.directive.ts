import { FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * Validator Function for remove space
 * @param control 
 */
export const alphabetsOnlyValidator: ValidatorFn = (control: FormControl): ValidationErrors | null => {
  if (control && control.value && !/^[a-zA-Z]+$/.test(control.value)) {
    const value = control.value.replace(/[^A-Za-z]/g, '')
    control.setValue(value);
    if (!value) {
      return { required: true };
    }
  }

  return null;
};
