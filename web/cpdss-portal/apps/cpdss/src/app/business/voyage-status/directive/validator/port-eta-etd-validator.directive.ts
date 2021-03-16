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
            if (index > 0) {
                minDate = new Date(portsData[index - 1].date)
            }
            if (index < portsData.length - 1) {
                maxDate = new Date(portsData[index + 1].date)
            }
            if (minDate) {
                minDate.setHours(0, 0, 0, 0);
                if (minDate > date) {
                    errors = { minError: true }
                }
            }
            if (maxDate) {
                maxDate.setHours(0, 0, 0, 0)
                if (maxDate < date) {
                    if(!errors){
                        errors = {}
                    }
                    errors['maxError'] = true;
                }
            }
            return errors;
        }
        return null;
    };
}

