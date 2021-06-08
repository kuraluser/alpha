import { FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

import { AppConfigurationService } from '../../../../shared/services/app-configuration/app-configuration.service';

/**
 * Validator function for validating OBS. M3 should be less than tank capacity
 *
 * @export
 * @param {string} compareValue
 * @param {string} field
 * @returns {ValidatorFn}
 */
 export function tankCapacityValidator(field: string, compareValue: string , errorField: string , fillingRatio: string): ValidatorFn {
    return (control: FormControl): ValidationErrors | null => {
      
      if (!control.root || !control.parent) {
        return null;
      }
 
      if(Number(control['parent']?.get(field)?.value) > Number(compareValue)) {
        const formControl = <FormControl>control.parent.controls[errorField];
        setTimeout(() => {
          formControl.markAsTouched();
          formControl.markAsDirty();
          formControl.setErrors({ greaterThanTankCapacity: true })
        }, 250)
      } else if (Number(control['parent']?.get(fillingRatio)?.value) > Number(AppConfigurationService.settings.maxFillingPercentage)) {
        const formControl = <FormControl>control.parent.controls[errorField];
        setTimeout(() => {
          formControl.markAsTouched();
          formControl.markAsDirty();
          formControl.setErrors({ maxLimit: true })
        }, 250)
      } else {
        const formControl = <FormControl>control.parent.controls[errorField];
        formControl.setErrors(null)
      }
    }
  } 