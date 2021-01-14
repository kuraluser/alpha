import { FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * Validator Function for date range 
 * @param control 
 */
export const portDateRangeValidator: ValidatorFn = (control: FormControl): ValidationErrors | null => {
    if (!control.root || !control.parent) {
        return null;
    }
    if (control.parent?.value.layCan) {
        const fromDate = control.parent.value.layCanFrom;
        const toDate = control.parent.value.layCanTo;
        if (control.value) {
            const checkDate = control.value;
            if (checkDate >= fromDate && checkDate <= toDate) {
                return null;
            } else {
                return { notInRange: true };
            }
        } else {
            return null;
        }
    } else {
        return null;
    }
};
