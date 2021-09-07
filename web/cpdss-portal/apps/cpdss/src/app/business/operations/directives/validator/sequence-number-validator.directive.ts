import { AbstractControl, FormArray, FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * Validator Function for invalid sequence number
 *
 * @export
 *
 * @returns {ValidatorFn}
 */
export const sequenceNumberValidator: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
  if (!control.root || !control.parent) {
    return null;
  }
  const currSequence = Number(control?.value);
  const sequences = control.parent.parent as FormArray;
  const currentIndex = sequences?.controls?.indexOf(control.parent);
  if (currentIndex - 1 > 0) {
    const prevSequence = (<FormControl>sequences.at(currentIndex - 1)).value;
    return [prevSequence.sequenceNo, prevSequence.sequenceNo + 1].includes(currSequence) ? null : { invalidSequenceNumber: true };
  }

  return null;
};

