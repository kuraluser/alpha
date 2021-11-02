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
export function cargoQuantityValidator(): ValidatorFn {
  return (control: FormControl): ValidationErrors | null => {
    if (control.root && control.parent) {
      let totalQuantity = 0;
      const dataTableArray = control.parent.parent.value;
      if (control?.parent?.value?.cargo) {
        totalQuantity = dataTableArray.reduce((quantity, value) => {
          if (control?.parent?.value?.cargo?.cargoNominationId === value?.cargo?.cargoNominationId) {
            quantity += Number(value.quantityMT);
          }
          return quantity;
        }, 0);
        if (totalQuantity > Number(control?.parent?.value?.cargo?.slopQuantityMT)) {
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

