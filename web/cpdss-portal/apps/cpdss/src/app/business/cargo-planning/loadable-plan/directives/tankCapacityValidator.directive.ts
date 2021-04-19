import { FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';



/**
 * Validator function for validating OBS. M3 should be less than tank capacity
 *
 * @export
 * @param {string} compareValue
 * @param {string} field
 * @returns {ValidatorFn}
 */
 export function tankCapacityValidator(field: string, compareValue: string , errorField: string): ValidatorFn {
    return (control: FormControl): ValidationErrors | null => {
      
      if (!control.root || !control.parent) {
        return null;
      }
      
      if(Number(control.value) > Number(compareValue)) {
        const formControl = <FormControl>control.parent.controls[errorField];
        formControl.markAsTouched();
        formControl.markAsDirty();
        formControl.setErrors({greaterThanTankCapacity: true})
      }
    }
  } 