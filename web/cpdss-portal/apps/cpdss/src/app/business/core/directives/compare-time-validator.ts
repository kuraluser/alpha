import { FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * Validator Function for invalid check for duration
 *
 * @export
 * @param {string} maxTime
 * @returns {ValidatorFn}
 */
export function compareTimeValidator(maxTime: string): ValidatorFn {
    return (control: FormControl): ValidationErrors | null => {
        if (!control.root || !control.parent || !control.value) {
            return;
        }
        const convertTimeStringToMinutes = (time: string) => {
            const timeArr = time.split(':');
            return Number(timeArr[0].replace(/_/g, '')) * 60 + Number(timeArr[1].replace(/_/g, ''));
        }
        const compareMinute = convertTimeStringToMinutes(control.value);
        const maxMinutes = convertTimeStringToMinutes(maxTime);
        if (compareMinute > 0) {
            if(maxMinutes < compareMinute) {
                return { maxTimeLimitExceed: true };
            } 
            else {
                return null;
            }
        }
    }
}