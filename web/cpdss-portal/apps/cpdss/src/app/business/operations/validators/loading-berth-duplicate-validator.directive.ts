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
        if (control?.value && selectedBerths) {
            if(index !== -2){
                selectedBerths.splice(index,1);
            }
            const found = selectedBerths.some(berth => berth?.name?.berthId === control?.value?.berthId );
            if(found){
                return {duplicateBerth: true}
            }else{
                return null;
            }
        } else {
            return null;
        }

    }
}
