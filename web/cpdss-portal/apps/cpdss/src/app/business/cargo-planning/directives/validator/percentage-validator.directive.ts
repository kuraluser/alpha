import { FormArray, FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * Validator Function for checking maximum percentage 
 * @param control 
 */
export function PercentageValidator(field): ValidatorFn {
    return (control: FormControl): ValidationErrors | null => {
        if (!control.root || !control.parent) {
            return null;
        }
        if (control?.parent?.value[field] && control?.value) {
            const total = control?.parent?.value[field]?.id + control?.value?.id;
            if (total > 100) {
                return { percentageTotal: true };
            } else {
                return null;
            }
        } else {
            return null;
        }


    }
};