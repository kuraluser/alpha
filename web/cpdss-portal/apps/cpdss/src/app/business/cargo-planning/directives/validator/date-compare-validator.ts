import { FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';
import * as moment from 'moment';

/**
 * Validator Function for date comparison
 * @param control
 */
export function dateCompareValidator(date, index , compareOperation): ValidatorFn {
    return (control: FormControl): ValidationErrors | null => {
        if (control.root && control.parent && date) {
            const formArray = control.root.value?.dataTable;
            const compareDate = moment(date, 'DD-MM-YYYY HH:mm').toDate();
            if (control.value) {
                const checkDate: Date = moment(control.value).subtract(formArray[index].port?.timezoneOffsetVal, 'hours').toDate();;
                compareDate.setSeconds(0, 0)
                checkDate.setSeconds(0, 0)
                if (compareOperation === '<' && compareDate <= checkDate) {
                    return { dateLessThanActual: true };
                } else if (compareOperation === '>' && compareDate >= checkDate) {
                    return { dateGreaterThanActual : true };
                }
            }
        }
        return null;
    }
}

