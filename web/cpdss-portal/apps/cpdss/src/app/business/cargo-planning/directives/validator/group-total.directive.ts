import { FormArray, FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * Validator function for total quantity in a group
 *
 * @export
 * @param {string} key
 * @param {string} groupKey
 * @returns {ValidatorFn}
 */
export function groupTotalValidator(key: string, groupKey: string, gridData = null): ValidatorFn {
  return (control: FormControl): ValidationErrors | null => {
    if (!control.root || !control.parent) {
      return null;
    }
    gridData = (<FormArray>control.root.get('dataTable'))?.value ?? gridData;
    const groupId = control.parent.value[groupKey];
    const total = gridData?.reduce((a, b) => {
      const value = Number(b[key]) ? Number(b[key]) : 0;
      const sum = a + (b !== control.parent?.value && b[groupKey] === groupId ? value || 0 : 0);
      return sum;
    }, Number(control.value));

    return total <= 0 ? { groupTotal: true } : null;
  }
} 