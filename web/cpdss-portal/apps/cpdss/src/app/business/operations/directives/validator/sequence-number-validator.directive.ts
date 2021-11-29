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
  if(currentIndex === 0) {
    return currSequence === 1 && currSequence >= 0 ? null : {invalidNumber: true};
  } else if (currentIndex > 0) {
    const prevSequence = (<FormControl>sequences.at(currentIndex - 1)).value;
    const prevSequenceNo  = Number(prevSequence.sequenceNo) ?? 0;
    return [prevSequenceNo, prevSequenceNo + 1].includes(currSequence) ? null : { invalidSequenceNumber: true };
  }

  return null;
};

