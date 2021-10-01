import { FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * Validator Function for min value check on temperature/api field
 *
 * @export
 * @returns {ValidatorFn}
 */
export function blReferenceValidator(): ValidatorFn {
    return (control: FormControl): ValidationErrors | null => {
        if (!control?.value || !control?.parent) {
            return null;
        }
        const regExpSpecialCharacter = new RegExp(/^[ A-Za-z0-9\#\&\(\)\/\"\:\-\=\+\*]*$/);
        if (!regExpSpecialCharacter.test(control?.value)) {
            return { pattern: true };
        }

        const regExpNumberCharacter = new RegExp(/[a-zA-Z0-9]/);
        if (!regExpNumberCharacter.test(control?.value)) {
            return { pattern: true };
        }



    }
}

