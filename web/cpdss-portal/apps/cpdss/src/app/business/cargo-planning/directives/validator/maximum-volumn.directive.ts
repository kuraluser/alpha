import { FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * Validator function for validating maximum volume to be allocated in tank
 *
 * @export
 * @param {string} densityKey
 * @param {string} capacityKey
 * @param {any} gridData
 * @returns {ValidatorFn}
 */
export function maximumVolumeValidator(densityKey: string, rowData = null): ValidatorFn {
  return (control: FormControl): ValidationErrors | null => {
    if (!control.root || !control.parent) {
      return null;
    }
    const density = Number(control.parent?.value[densityKey]) ?? 0;
    const weight = Number(control.value) ?? 0;
    const fullCapacity = Number(rowData?.fullCapacity) ?? 0;

    return fullCapacity && weight > fullCapacity ? { max: { max: fullCapacity, actual: weight } } : null;
  }
} 