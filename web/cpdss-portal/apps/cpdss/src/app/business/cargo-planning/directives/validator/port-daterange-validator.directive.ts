import { FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * Validator Function for date range 
 * @param control 
 */
export const portDateRangeValidator: ValidatorFn = (control: FormControl): ValidationErrors | null => {
    if (control.root && control.parent && control.parent?.value.layCan) {
        const fromDate: Date = control.parent.value.layCanFrom;
        const toDate: Date = control.parent.value.layCanTo;
        fromDate.setHours(0, 0, 0, 0)
        toDate.setHours(23,59,59,0)
        if (control.value) {
            const checkDate = control.value;
            checkDate.setSeconds(0, 0)
            if (checkDate < fromDate || checkDate > toDate) {
                return { notInRange: true };
            }
        }
    }
    return null;
};
