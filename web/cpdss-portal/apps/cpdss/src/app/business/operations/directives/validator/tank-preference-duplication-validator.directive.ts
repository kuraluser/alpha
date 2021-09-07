import { AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * Validator Function for tank duplication in COW preference
 *
 * @export
 *
 * @returns {ValidatorFn}
 */
export function tankPreferenceDuplicationValidator(cowType: string): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    if (!control.root || !control.parent) {
      return null;
    }

    const topCOWTanks = (<FormControl>control.parent.get('topCOWTanks')).value;
    const bottomCOWTanks = (<FormControl>control.parent.get('bottomCOWTanks')).value;
    const allCOWTanks = (<FormControl>control.parent.get('allCOWTanks')).value;
    let duplicated = false;

    switch (cowType) {
      case 'top':
        for (let index = 0; index < topCOWTanks.length; index++) {
          for (let bIndex = 0; bIndex < bottomCOWTanks.length; bIndex++) {
            if (topCOWTanks[index]?.id === bottomCOWTanks[bIndex]?.id) {
              duplicated = true;
              break;
            }
          }
          for (let aIndex = 0; aIndex < allCOWTanks.length; aIndex++) {
            if (topCOWTanks[index]?.id === allCOWTanks[aIndex]?.id) {
              duplicated = true;
              break;
            }
          }
          if (duplicated) {
            break;
          }
        }
        break;
      case 'bottom':
        for (let index = 0; index < bottomCOWTanks.length; index++) {
          for (let tIndex = 0; tIndex < topCOWTanks.length; tIndex++) {
            if (bottomCOWTanks[index]?.id === topCOWTanks[tIndex]?.id) {
              duplicated = true;
              break;
            }
          }
          for (let aIndex = 0; aIndex < allCOWTanks.length; aIndex++) {
            if (bottomCOWTanks[index]?.id === allCOWTanks[aIndex]?.id) {
              duplicated = true;
              break;
            }
          }
          if (duplicated) {
            break;
          }
        }
        break;
      case 'all':
        for (let index = 0; index < allCOWTanks.length; index++) {
          for (let tIndex = 0; tIndex < topCOWTanks.length; tIndex++) {
            if (allCOWTanks[index]?.id === topCOWTanks[tIndex]?.id) {
              duplicated = true;
              break;
            }
          }
          for (let bIndex = 0; bIndex < bottomCOWTanks.length; bIndex++) {
            if (allCOWTanks[index]?.id === bottomCOWTanks[bIndex]?.id) {
              duplicated = true;
              break;
            }
          }
          if (duplicated) {
            break;
          }
        }
        break;
      default:
        break;
    }
    return duplicated ? { duplicateTanks: true } : null;
  }
};

