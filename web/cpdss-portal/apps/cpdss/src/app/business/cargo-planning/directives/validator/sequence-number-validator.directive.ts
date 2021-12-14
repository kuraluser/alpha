import { AbstractControl, FormArray, FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * Validator Function for invalid sequence number
 *
 * @export
 * @param {number} index
 * @returns {ValidatorFn}
 */
export function sequenceNumberValidator(index: number): ValidatorFn {
  return (control): ValidationErrors | null => {
    if (!control.root || !control.parent || control?.value === '' || control?.value === null) {
      return null;
    }
    const currSequence = Number(control?.value);
    
    const portArray = control.root.get('portDetails') as FormArray;
    const portDetails = portArray.at(index);

    const sequences = portDetails.get('cargoDetail').get('dataTable') as FormArray;
    const currentIndex = sequences?.controls?.indexOf(control.parent);
    if (currentIndex === 0) {
      return currSequence === 1  ? null : { invalidNumber: true };
    } else if (currentIndex > 0) {
      const prevSequence = (<FormControl>sequences.at(currentIndex - 1)).value;
      const prevSequenceNo = Number(prevSequence.sequenceNo) ?? 0;
      return [prevSequenceNo, prevSequenceNo + 1].includes(currSequence) ? null : { invalidSequenceNumber: true };
    }

    return null;
  };
}