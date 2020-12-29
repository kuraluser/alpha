import { FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * Validator Function for date range 
 * @param control 
 */
export function portDuplicationValidator(field): ValidatorFn {
    return (control: FormControl): ValidationErrors | null => {
        if (!control.root || !control.parent) {
            return null;
        }
        let found = false;
        const dataTableArray = control.root.value.dataTable.filter((dataValue) => (dataValue?.portOrder !== control.parent?.value?.portOrder));
        if(control.parent?.value.port && field === 'operation'){
            if(control?.value){
               found = dataTableArray.some(portData => portData.port.id === control.parent?.value.port?.id &&  portData.operation?.id === control.value?.id);
            }
        }
        if(control.parent?.value.operation && field === 'port'){
            if(control?.value){
                found = dataTableArray.some(portData => portData.port?.id === control.value?.id &&  portData.operation?.id === control.parent?.value?.operation?.id);
            }
        }
        if(found){
            return { duplicate: true };
        }else{
            return null;
        }
      
    }
};
