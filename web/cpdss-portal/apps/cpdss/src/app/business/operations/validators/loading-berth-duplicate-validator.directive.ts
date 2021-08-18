import { FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * Validator Function for berth duplicates
 * @param control 
 */

export const LoadingBerthDuplicateValidator: ValidatorFn = (control: FormControl): ValidationErrors | null => {
        if (!control.root || !control.parent) {
            return null;
        }
        const selectedBerths = control.root.value.berth;
        let selectedBerthIds = [];
        let hasDuplicate = false;
        if (selectedBerths && control.value?.berthId) {
            selectedBerthIds = selectedBerths.map((element, ind) => {
                if (element?.name?.berthId === control.value?.berthId && element !== control.parent.value) {
                    hasDuplicate = true;
                }
            });

            if (hasDuplicate) {
                return { duplicateBerth: true }
            }
            else {
                return null;
            }

        } else if(!control.value?.berthId) {
            return { berthRequired: true }
        } else {
            return null;
        }

    }

