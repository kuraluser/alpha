import { FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * Validator Function for berth duplicates
 * @param control 
 */
export function LoadingBerthDuplicateValidator(index): ValidatorFn {
    return (control: FormControl): ValidationErrors | null => {
        if (!control.root || !control.parent) {
            return null;
        }
        const selectedBerths = control.root.value.berth;
        let selectedBerthIds = [];
        let hasDuplicate = false;
        if (selectedBerths) {
            selectedBerthIds = selectedBerths.map((element, ind) => {
                if (element?.name?.berthId === control.value.berthId && ind !== index) {
                    hasDuplicate = true;
                }
            });

            if (hasDuplicate) {
                return { duplicateBerth: true }
            }
            else {
                return null;
            }

        } else {
            return null;
        }

    }
}
