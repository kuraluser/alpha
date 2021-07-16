import { FormArray, FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * Validator for duplicate cargo abbreviations
 * @param control 
 */
export const cargoNominationAbbreviationValidator: ValidatorFn = (control: FormControl): ValidationErrors | null => {
  if (!control.root || !control.parent) {
    return null;
  }
  const cargoNominations = control.root.get('dataTable') as FormArray;
  const cargoAbbreviations = cargoNominations?.value.filter(item => item !== control.parent.value && !item?.isDelete && item.abbreviation).map(item => item.abbreviation);
  const cargoAbbreviation = control?.value;

  return cargoAbbreviations?.includes(cargoAbbreviation) ? { duplicateAbbrevation: true } : null;
}