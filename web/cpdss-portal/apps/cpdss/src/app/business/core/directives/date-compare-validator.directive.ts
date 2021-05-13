import { FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * Validator Function for compare dates
 * @param control 
 */
export function dateCompareValidator(field, compareOperation): ValidatorFn {
    return (control: FormControl): ValidationErrors | null => {
        if (!control.root || !control.parent) {
            return null;
        }
        if (control.parent?.value[field] && control.value) {
            const compareDate = new Date(control.parent?.value[field]);
            const checkDate = new Date(control.value)
            if (compareOperation === '<') {
                if (checkDate < compareDate) {
                    return null;
                } else {
                    return { failedCompare: true };
                }
            } else {
                if (checkDate > compareDate) {
                    return null;
                } else {
                    return { failedCompare: true };
                }
            }
        } else {
            return null;
        }
    };
}

