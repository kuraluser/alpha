import { FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * Validator function to check date in range
 *
 * @export
 * @param {Date} fromDate
 * @param {Date} toDate
 * @return {*}  {ValidatorFn}
 */
export function dateInRangeValidator(fromDate: Date, toDate: Date): ValidatorFn {
  return (control: FormControl): ValidationErrors | null => {
    if (control.root && control.parent && fromDate && toDate) {
      if (fromDate && toDate) {
        fromDate.setHours(0, 0, 0, 0)
        toDate.setHours(23, 59, 59, 0)
        if (control.value) {
          const checkDate = control.value;
          checkDate.setSeconds(0, 0)
          if (checkDate < fromDate || checkDate > toDate) {
            return { notInRange: true };
          }
        }
      }
    }
    return null;
  }
}
