import { FormArray, FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * Validator function for total quantity in a group
 *
 * @export
 * @param {string} key
 * @param {string} groupKey
 * @returns {ValidatorFn}
 */
export function groupTotalValidator(key: string, groupKey: string): ValidatorFn {
  return (control: FormControl): ValidationErrors | null => {
    if (!control.root || !control.parent) {
      return null;
    }
    const gridData = control.root.get('dataTable') as FormArray;
    const groupId = control.parent.value[groupKey];
    const total = gridData.value?.reduce((a, b) => a + (b !== control.parent.value && b[groupKey] === groupId ? b[key] || 0 : 0), control.value);

    return total <= 0 ? { groupTotal: true } : null;
  }
} 