import { FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * Validator Function for invalid check for duratuion
 *
 * @export
 * @param {number} maxHour
 * @param {number} [maxMinute=null]
 * @param {boolean} [isNegativeAccept=true]
 * @returns {ValidatorFn}
 */
export function durationValidator(maxHour: number, maxMinute: number = null): ValidatorFn {
    return (control: FormControl): ValidationErrors | null => {
        if (!control || typeof control.value === 'undefined' || control.value == null || control.value === '') {
            return;
        }
        if (control.value) {
            const duration = control.value.split(':');
            const hour = Number(duration[0].replace('_', ''))
            const minute = Number(duration[1].replace('_', ''))
            if (hour && minute >= 0) {
                if ((hour === maxHour && minute > 0) || (hour > maxHour || minute > maxMinute)) {
                    return { invalidDuration: true };
                }
                else {
                    return null;
                }
            }
        } else {
            return null;
        }

    }
}

