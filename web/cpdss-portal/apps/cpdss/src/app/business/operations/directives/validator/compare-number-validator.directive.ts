import { FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * Validator Function for date range 
 * @param control 
 */
export function compareNumberValidator(field, compareOperation): ValidatorFn {
    return (control: FormControl): ValidationErrors | null => {
        if (control.root && control.parent && control.parent?.value[field]) {
            const compareNumber = control.parent.value[field];
            if (control.value) {
                const checkNumber = control.value;
                if (compareOperation === '<' && checkNumber < compareNumber) {
                    return { failedCompare: true };
                } else if (compareOperation === '>' && checkNumber > compareNumber) {
                    return { failedCompare: true };
                }
            }
        }
        return null;
    }
}