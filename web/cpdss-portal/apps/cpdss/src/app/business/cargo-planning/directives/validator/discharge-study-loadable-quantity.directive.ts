import { FormArray, FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';
import { QuantityPipe } from '../../../../shared/pipes/quantity/quantity.pipe';
import { QUANTITY_UNIT } from '../../../../shared/models/common.model';

/**
 * Validator function for loadableQuantity in backloading
 *
 * @export
 * @param {number} loadableQuantity
 * @returns {ValidatorFn}
 */
export function dischargeStudyLoadableQuantity(loadableQuantity: number): ValidatorFn {
    return (control: FormControl): ValidationErrors | null => {
        const quantityPipe = new QuantityPipe();
        if (!control.root || !control.parent) {
            return null;
        }
        let maxQuantity = 0;
        const portDetails = control.root.get('portDetails') as FormArray;
        portDetails['controls'].map((item, itemIndex) => {
            const backLoadingDetailsFormControls = item.get('backLoadingDetails').get('dataTable') as FormArray;
            backLoadingDetailsFormControls['controls'].map((backLoadingItems) => {
                if (backLoadingItems.get('kl').value) {
                    maxQuantity += Number(quantityPipe.transform(backLoadingItems.get('kl').value, QUANTITY_UNIT.KL, QUANTITY_UNIT.MT, backLoadingItems.get('api').value, backLoadingItems.get('temp').value , -1));
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