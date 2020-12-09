import { FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * Validator Function for date range 
 * @param control 
 */
export function portDateCompareValidator(field, compareOperation): ValidatorFn {
    return (control: FormControl): ValidationErrors | null => {
        if (!control.root || !control.parent) {
            return null;
        }
        if (control.parent?.value[field]) {
            const compare = control.parent.value[field].toString().split(' ')[0]?.split('-');
            const compareDate = new Date(Number(compare[2]), Number(compare[1]) - 1, Number(compare[0]));
            if (control.value) {
                const check = control.value.toString().split(' ')[0]?.split('-');
                if (check.length > 0) {
                    const checkDate = new Date(Number(check[2]), Number(check[1]) - 1, Number(check[0]));
                    if (compareOperation === '<') {
                        if (checkDate < compareDate) {
                            return null;
                        } else {
                            return { failedCompare: true };
                        }
                    } else {
                        if (checkDate > compareDate) {
                            return null;
                        } else {
                            return { failedCompare: true };
                        }
                    }
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    };
}

