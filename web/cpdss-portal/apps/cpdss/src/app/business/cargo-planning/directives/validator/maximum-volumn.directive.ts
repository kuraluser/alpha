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
export function maximumVolumeValidator(densityKey: string, rowData = null, percentage = 100): ValidatorFn {
  return (control: FormControl): ValidationErrors | null => {
    if (!control.root || !control.parent) {
      return null;
    }
    const density = Number(control.parent?.value[densityKey]) ?? 0;
    const weight = Number(control.value) ?? 0;
    const fullCapacity = Number(rowData?.fullCapacity) ?? 0;
    const percentOfFullCapacity = (percentage / 100) * fullCapacity;

    return percentOfFullCapacity && weight > percentOfFullCapacity ? { max: { max: percentOfFullCapacity, actual: weight } } : null;
  }
}
