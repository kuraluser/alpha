import { FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * Validator Function for date range 
 * @param control 
 */
export function commingleQuantityValidator(): ValidatorFn {
    return (control: FormControl): ValidationErrors | null => {
        if (!control.root || !control.parent) {
            return null;
        }
        if (control.parent?.value?.cargo1 && control.parent?.value?.cargo2 && control?.value) {
            let loadingPortsTotal = 0;
            let cargo1Total = 0
            let cargo2Total = 0
            for (let i = 0; i < control.parent?.value?.cargo1?.loadingPorts.length; i++) {
                cargo1Total = control.parent?.value?.cargo1?.loadingPorts[i].quantity + cargo1Total;
            }
            for (let j = 0; j < control.parent?.value?.cargo2?.loadingPorts.length; j++) {
                cargo2Total = control.parent?.value?.cargo2?.loadingPorts[j].quantity + cargo2Total;
            }
            loadingPortsTotal = cargo1Total + cargo2Total;
            if (loadingPortsTotal < control.value) {
                return { isMaxQuantity: true };
            }
            else {
                return null;
            }
        } else {
            return null;
        }
    }
};
