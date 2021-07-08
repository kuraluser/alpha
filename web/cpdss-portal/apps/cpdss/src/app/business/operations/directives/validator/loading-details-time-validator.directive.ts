import { FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * Validator Function for date range 
 * @param control 
 */
export function loadingDetailsValidator(field, compareOperation): ValidatorFn {
    return (control: FormControl): ValidationErrors | null => {
        if (control.root && control.parent && control.parent?.value[field]) {
            const compareDate: Date = control.parent.value[field];
            if (control.value) {
                const checkDate: Date = control.value;
                if (compareOperation === '<' && compareDate.getTime() < checkDate.getTime()) {
                    return { failedCompare: true };
                } else if (compareOperation === '>' && compareDate.getTime() > checkDate.getTime()) {
                    return { failedCompare: true };
                }
            }
        }
        return null;
    }
}