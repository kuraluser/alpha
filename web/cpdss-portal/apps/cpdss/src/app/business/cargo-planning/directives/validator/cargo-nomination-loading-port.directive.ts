import { FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * Validator Function for loading port
 * @param control 
 */
export const cargoNominationLoadingPortValidator: ValidatorFn = (control: FormControl): ValidationErrors | null => {
  if (!control.root || !control.parent) {
    return null;
  }
  const cargo = control.parent.value?.cargo;

  return !cargo && !cargo?.ports ? { loadinPortCargoNotSelected: true } : null;
};
