import { FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * Validator Function for min value check on temperature/api field
 *
 * @export
 * @returns {ValidatorFn}
 */
export function apiTemperatureMinValidator(type: string): ValidatorFn {
    return (control: FormControl): ValidationErrors | null => {
        if ((!control?.value && control?.value !== 0) || !control?.parent) {
            return;
        }

        const row = control?.parent?.value;
        if (Number(control.value) === 0 && Number(row.quantity) === 0) {
            return null;
        }
        if (type === 'temperature' && control.value < 40) {
            return { min: true };
        }

        if (type === 'api' && control.value < 8) {
            return { min: true };
        }

    }
}

