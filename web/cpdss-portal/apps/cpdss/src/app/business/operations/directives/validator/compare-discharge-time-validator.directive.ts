import { FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * Validator Function for invalid check for duration
 *
 * @export
 * @param {string} field
 * @param {string} maxTime
 * @returns {ValidatorFn}
 */
export function compareDischargeTimeValidation(maxTime: string, field: string): ValidatorFn {
    return (control: FormControl): ValidationErrors | null => {
        if (!control.root || !control.parent || !control.value) {
            return;
        }
        const convertTimeStringToMinutes = (time: string) => {
            const timeArr = time.split(':');
            return Number(timeArr[0].replace(/_/g, '')) * 60 + Number(timeArr[1].replace(/_/g, ''));
        }
        let compareMinute = convertTimeStringToMinutes(control.value);
        const startTime = compareMinute;
        const maxDurationHour = convertTimeStringToMinutes(maxTime);

        if (control.parent?.value[field]) {
            compareMinute += convertTimeStringToMinutes(control.parent?.value[field]);
        }
        if (startTime > 0) {
            if(maxDurationHour < startTime) {
                return { invalidDuration: true };
            } else if (maxDurationHour < compareMinute) {
                return { invalidTime: true };
            } 
            else {
                return null;
            }
        }
    }
}