import { FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * Validator Function for compare dates
 * @param control 
 */
export function portEtaEtdValidator(index): ValidatorFn {
    return (control): ValidationErrors | null => {
        if (control.root && control.parent) {
            const portsData = control.root.value?.portsData
            const date = new Date(control.value);
            let minDate: Date, maxDate: Date, errors = null;
            
            if(!control.value || control.value === '') {
                return null;
            }
            if (index > 0) {
                if(control.value && control.value !== '' && (!portsData[index - 1].date || portsData[index - 1].date === '')) {
                    errors = { compareDateWithPrevious: true };
                    return errors
                } else if(portsData[index - 1].date || portsData[index - 1].date === ''){
                    minDate = new Date(portsData[index - 1].date);
                }
            }
            
            if (index < portsData.length - 1 && portsData[index + 1].date) {
                maxDate = new Date(portsData[index + 1].date)
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

