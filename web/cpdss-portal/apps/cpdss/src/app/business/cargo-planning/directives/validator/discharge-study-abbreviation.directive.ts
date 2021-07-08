import { FormArray, FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * Validator Function for Abbreviation
 * @param control 
 */
export const dischargeStudyAbbreviationValidator: ValidatorFn = (control: FormControl): ValidationErrors | null => {
  if (!control.root || !control.parent) {
    return null;
  }
  
  const portDetails = control.root.get('portDetails') as FormArray;
  let cargoAbbreviation = [];
  
  portDetails.value.map((item) => {

    const dischargeCargoDetails = item.cargoDetail.dataTable;
    const backLoadingCargoDetails = item.backLoadingDetails.dataTable;
    
    const dischargeCargoAbbrevation = dischargeCargoDetails.filter((cargoData) => {
        if(cargoData !== control.parent.value  && cargoData.color && cargoData.storedKey !== control.parent.value?.storedKey) {
          return cargoData;
        }
      }).map(cargoData => cargoData.abbreviation?.toLowerCase());

      const backLoadingCargoAbbrevation = backLoadingCargoDetails.filter((cargoData) => {
        if(cargoData !== control.parent.value  && cargoData.color) {
          return cargoData;
        }
      }).map(cargoData => cargoData.abbreviation?.toLowerCase());

    cargoAbbreviation = [...cargoAbbreviation , ...dischargeCargoAbbrevation , ...backLoadingCargoAbbrevation];
  })
  const abbreviation = control?.value;
  return cargoAbbreviation?.includes(abbreviation?.toLowerCase()) ? { duplicateAbbrevation: true } : null;
};