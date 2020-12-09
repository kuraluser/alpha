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
        const from = control.parent.value.layCanFrom.toString().split('-');
        const to = control.parent.value.layCanTo.toString().split('-');
        const fromDate = new Date(Number(from[2]), Number(from[1]) - 1, Number(from[0]));
        const toDate = new Date(Number(to[2]), Number(to[1]) - 1, Number(to[0]));
        if (control.value) {
            const check = control.value.toString().split(' ')[0]?.split('-');
            const checkDate = new Date(Number(check[2]), Number(check[1]) - 1, Number(check[0]));
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
