import { FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * Validator Function for invalid check for numbers
 *
 * @export
 * @param {number} maxHour
 * @param {number} [maxMinute=null]
 * @param {boolean} [isNegativeAccept=true]
 * @returns {ValidatorFn}
 */
export function cargoQuantityValidator(field: string): ValidatorFn {
    return (control: FormControl): ValidationErrors | null => {
        if (control.root && control.parent) {
            let totalQuantities = {};
            const dataTableArray = control.root.value.dataTable;
            if (field === 'cargo' && control.parent.value.quantity) {
                totalQuantities = dataTableArray.reduce((res, value) => {
                    if (!res[control.value?.cargoId]) {
                        res[control.value?.cargoId] = { Id: control.value?.cargoId, quantity: 0 };
                    }
                    res[control.value?.cargoId].quantity += Number(value.quantity);
                    return res;
                }, {});
                if (totalQuantities[control.value?.cargoId]?.quantity > Number(control.value?.loadableMT)) {
                    return { quantityExceeds: true };
                } else {
                    return null;
                }
            }
            else if (field === 'quantity' && control?.parent?.value?.cargo) {
                totalQuantities = dataTableArray.reduce((res, value) => {
                    if (!res[control?.parent?.value?.cargo?.cargoId]) {
                        res[control?.parent?.value?.cargo?.cargoId] = { Id: control?.parent?.value?.cargo?.cargoId, quantity: 0 };
                    }
                    res[control?.parent?.value?.cargo?.cargoId].quantity += Number(value.quantity);
                    return res;
                }, {});
                if (totalQuantities[control?.parent?.value?.cargo?.cargoId]?.quantity > Number(control?.parent?.value?.cargo?.loadableMT)) {
                    return { quantityExceeds: true };
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}

