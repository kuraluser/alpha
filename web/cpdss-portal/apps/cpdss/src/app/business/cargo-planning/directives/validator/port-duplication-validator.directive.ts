import { FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * Validator Function for date range 
 * @param control 
 */
export function portDuplicationValidator(field): ValidatorFn {
    return (control: FormControl): ValidationErrors | null => {
        if (control.root && control.parent) {
            let filtered = [];
            const dataTableArray = control.root.value.dataTable.filter(portData => portData.portOrder !== control.parent.value?.portOrder);
            if (control?.value) {
                if (control.parent.value?.port && field === 'operation') {
                    filtered = dataTableArray.filter(portData => portData.port?.id === control.parent.value.port?.id && portData.operation?.id === control.value?.id);
                }
                if (control.parent.value?.operation && field === 'port') {
                    filtered = dataTableArray.filter(portData => portData.port?.id === control.value?.id && portData.operation?.id === control.parent?.value?.operation?.id);
                }
            }
            if (filtered.length >= 1) {
                return { duplicate: true };
            }

        }
        return null;
    }
};
