import { FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * Validator Function for port eta compare with top of the etd
 * @param control 
 */
export const portEtaValidator: ValidatorFn = (control: FormControl): ValidationErrors | null => {
  if (!control.root || !control.parent) {
    return null;
  }
  if (control.value) {
    const maxPortOrder = control.root.value.dataTable.reduce((acc, port) => acc = acc > port.portOrder ? acc : port.portOrder, 0);
    const dataTableArray = control.parent?.value?.portOrder !== 0 ? control.root.value.dataTable.filter((dataValue) => (dataValue?.portOrder === (control.parent?.value?.portOrder - 1))) : control.root.value.dataTable.filter((dataValue) => (dataValue?.portOrder === maxPortOrder));
    const check = control.value.toString().split(' ')[0]?.split('-');
    if (check.length > 0) {
      const checkDate = new Date(Number(check[2]), Number(check[1]) - 1, Number(check[0]));
      const etaValidate = dataTableArray.some(portData => {
        const compare = portData?.etd?.toString()?.split(' ')[0]?.split('-');
        const compareDate = new Date(Number(compare[2]), Number(compare[1]) - 1, Number(compare[0]));
        return checkDate < compareDate

      });
      if (etaValidate) {
        return { etaFailed: true };
      } else {
        return null;
      }
    } else {
      return null;
    }
  } else {
    return null;
  }

};
