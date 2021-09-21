import { FormArray, FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * Validator function for loadableQuantity in backloading
 *
 * @export
 * @param {number} loadableQuantity
 * @returns {ValidatorFn}
 */
export function dischargeStudyLoadableQuantity(loadableQuantity: number): ValidatorFn {
    return (control: FormControl): ValidationErrors | null => {
        if (!control.root || !control.parent) {
            return null;
        }
        let maxQuantity = 0;
        const portDetails = control.root.get('portDetails') as FormArray;
        portDetails['controls'].map((item, itemIndex) => {
            const backLoadingDetailsFormControls = item.get('backLoadingDetails').get('dataTable') as FormArray;
            backLoadingDetailsFormControls['controls'].map((backLoadingItems) => {
                if (backLoadingItems.get('kl').value) {
                    maxQuantity = Number(backLoadingItems.get('kl').value);
                }
            })
        })
        if (loadableQuantity < maxQuantity) {
            return { backloadingGreaterThanLoading: true }
        } else {
            return null;
        }
    }
}