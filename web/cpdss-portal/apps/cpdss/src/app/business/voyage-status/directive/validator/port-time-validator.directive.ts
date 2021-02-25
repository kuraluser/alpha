import { FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * Validator Function for compare dates
 * @param control 
 */
export function portTimeValidator(index): ValidatorFn {
    return (control): ValidationErrors | null => {
        if (control.root && control.parent) {
            const portsData = control.root.value?.portsData
            const time = new Date(control.value);
            const date = new Date(control.parent.value?.date);
            if (date && time) {
                date.setHours(0, 0, 0, 0);
                let minDate: Date, maxDate: Date, minTime: Date, maxTime: Date, errors = null;
                if (index > 0) {
                    minDate = portsData[index - 1].date
                    minTime = portsData[index - 1].time
                }
                if (index < portsData.length - 1) {
                    maxDate = portsData[index + 1].date
                    maxTime = portsData[index + 1].time
                }
                if (minDate) {
                    minDate.setHours(0, 0, 0, 0);
                    if (minTime && minDate.getTime() === date.getTime()){
                        minDate.setHours(minTime.getHours(), minTime.getMinutes())
                        date.setHours(time.getHours(), time.getMinutes())
                        if (minDate >= date) {
                            errors = { minError: true }
                        }
                    }
                }
                if (maxDate) {
                    maxDate.setHours(0, 0, 0, 0);
                    if (maxTime && maxDate.getTime() === date.getTime()){
                        maxDate.setHours(maxTime.getHours(), maxTime.getMinutes())
                        date.setHours(time.getHours(), time.getMinutes())
                        if (maxDate <= date) {
                            if(!errors){
                                errors = {}
                            }
                            errors['maxError'] = true;
                        }
                    }
                }
                return errors;
            }
        }
        return null;
    };
}

