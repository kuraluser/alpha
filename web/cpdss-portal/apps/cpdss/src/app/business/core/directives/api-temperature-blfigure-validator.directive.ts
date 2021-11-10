import { FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * Validator Function for min value check on temperature/api field
 *
 * @export
 * @returns {ValidatorFn}
 */
export function apiTemperatureBlfigValidator(type: string): ValidatorFn {
    return (control: FormControl): ValidationErrors | null => {
        if (!control?.parent) {
            return null;
        }

        const row = control?.parent?.value;
        if (Number(control.value) === 0 && (!row.blRefNo || row?.blRefNo?.toString().trim() === '')
            && (!row.bbl || Number(row.bbl) === 0) && (!row.lt || Number(row.lt) === 0) && (!row.mt || Number(row.lt) === 0)
            && (!row.mt || Number(row.mt) === 0) && (!row.kl || Number(row.kl) === 0) && (!row.temp || Number(row.temp) === 0)) {
            return null;
        }
        if (type === 'temperature' && (!control?.value && control?.value !== 0)) {
            return { required: true };
        }
        if (type === 'api' && (!control?.value && control?.value !== 0)) {
            return { required: true };
        }
        if (type === 'temperature' && control.value < 40) {
            return { min: true };
        }

        if (type === 'api' && control.value < 8) {
            return { min: true };
        }

    }
}

