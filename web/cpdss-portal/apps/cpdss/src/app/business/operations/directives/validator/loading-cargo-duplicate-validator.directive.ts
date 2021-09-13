import { FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * Validator Function for cargo duplicates
 * @param control 
 */
export function loadingCargoDuplicateValidator(): ValidatorFn {
    return (control: FormControl): ValidationErrors | null => {
        if (!control.root || !control.parent) {
            return null;
        }
        
        const dataTableArray = control.root.value?.loadingDischargingSequence?.dataTable;
        if (control?.value && dataTableArray) {
            
            const found = dataTableArray.some(data => data !== control?.parent.value && data?.cargo?.cargoNominationId === control?.value?.cargoNominationId );
            if(found){
                return {duplicateCargo: true}
            }else{
                return null;
            }
        } else {
            return null;
        }

    }
}
