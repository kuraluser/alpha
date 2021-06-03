import { FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';
import * as moment from 'moment';

/**
 * Validator Function for compare dates
 * @param control 
 */
export function portEtaEtdValidator(index): ValidatorFn {
    return (control): ValidationErrors | null => {
        if (control.root && control.parent) {
            const portsData = control.root.value?.portsData
            const date = moment(control.value).toDate();
            let minDate: Date, maxDate: Date, errors = null;
            
            if(!control.value || control.value === '') {
                return null;
            }
            if (index > 0) {
                if(control.value && control.value !== '' && (!portsData[index - 1].date || portsData[index - 1].date === '')) {
                    errors = { compareDateWithPrevious: true };
                    return errors
                } else if(portsData[index - 1].date || portsData[index - 1].date === ''){
                    minDate = moment(portsData[index - 1].date).subtract(portsData[index - 1].portTimezoneOffset, 'hours').toDate();
                }
            }
            
            if (index < portsData.length - 1 && portsData[index + 1].date) {
                maxDate = moment(portsData[index + 1].date).subtract(portsData[index + 1].portTimezoneOffset, 'hours').toDate();
            }
            if (minDate) {
                minDate.setHours(0, 0, 0, 0);
                if (minDate > date) {
                    errors = { minError: true };
                    return errors;
                }
            }
            if (maxDate) {
                maxDate.setHours(0, 0, 0, 0)
                if (maxDate < date) {
                    if(!errors){
                        errors = {}
                    }
                    errors['maxError'] = true;
                    return errors;
                }
            }
            return errors;
        }
        return null;
    };
}

