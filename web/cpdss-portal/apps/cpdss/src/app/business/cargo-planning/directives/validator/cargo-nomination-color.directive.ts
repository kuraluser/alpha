import { FormArray, FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * Validator Function for color
 * @param control 
 */
export const cargoNominationColorValidator: ValidatorFn = (control: FormControl): ValidationErrors | null => {
  if (!control.root || !control.parent) {
    return null;
  }
  const cargoNominations = control.root.get('dataTable') as FormArray;
  const cargoColors = cargoNominations.value.filter(item => item !== control.parent.value && !item?.isDelete && item.color).map(item => item.color);
  const cargoColor = control.value;

  return cargoColors.includes(cargoColor) ? { duplicateColor: true } : null;
};
