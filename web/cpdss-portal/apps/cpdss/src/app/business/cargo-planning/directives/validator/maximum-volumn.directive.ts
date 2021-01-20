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
export function maximumVolumeValidator(densityKey: string, fullCapacity: number): ValidatorFn {
  return (control: FormControl): ValidationErrors | null => {
    if (!control.root || !control.parent) {
      return null;
    }
    const density = Number(control.parent?.value[densityKey]) ?? 0;
    const weight = Number(control.value) ?? 0;
    fullCapacity = Number(fullCapacity) ?? 0;
    const maxWeight = fullCapacity * density;

    return weight > maxWeight ? { max: { max: maxWeight, actual: weight } } : null;
  }
} 