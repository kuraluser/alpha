import { FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * Validator Function for date range 
 * @param control 
 */
export function portDateCompareValidator(field, compareOperation): ValidatorFn {
    return (control: FormControl): ValidationErrors | null => {
        if (control.root && control.parent && control.parent?.value[field]) {
            const compareDate = control.parent.value[field];
            if (control.value) {
                const checkDate = control.value;
                if (compareOperation === '<' && compareDate <= checkDate) {
                    return { failedCompare: true };
                } else if (compareOperation === '>' && compareDate >= checkDate) {
                    return { failedCompare: true };
                }
            }
        }
        return null;
    }
}

