import { AbstractControl, FormArray, FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * Validator Function for invalid sequence number
 *
 * @export
 *
 * @returns {ValidatorFn}
 */
export const sequenceNumberValidator: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
  if (!control.root || !control.parent || control?.value === '' || control?.value === null) {
    return null;
  }

  const sequences: number[] = [...new Set(<Array<number>>((control.parent.parent as FormArray)?.value?.map(value => Number(value?.sequenceNo))))];
  const count = Math.max(...sequences);
  const missingSequence = [];

  for (let i = 1; i <= count; i++) {
    if (sequences.indexOf(i) === -1) {
      missingSequence.push(i);
    }
  }

  return missingSequence?.length ? { invalidSequenceNumber: true } : null;
};

