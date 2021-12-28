import { FormArray, FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * Validator for duplicate values
 * @param control 
 */
export const uniqueValidator: ValidatorFn = (control: FormControl): ValidationErrors | null => {
  if (!control.root || !control.parent) {
    return null;
  }
  const formArrayControl = control.parent.parent as FormArray;
  const isObject = typeof control.value === 'object';
  const controlName = (Object.keys(control.parent.controls).find(key => control.parent.controls[key] === control));
  const formControlValues = formArrayControl?.value.filter(item => item !== control.parent.value && item[controlName]).map(item => isObject ? item[controlName].id : item[controlName].toLowerCase());
  const formControlValue = isObject ? control?.value?.id : control?.value?.toLowerCase();
  
  return formControlValues?.includes(formControlValue) ? { unique: true } : null;
};
