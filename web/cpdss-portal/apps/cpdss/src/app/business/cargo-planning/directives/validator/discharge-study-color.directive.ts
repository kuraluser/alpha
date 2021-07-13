import { FormArray, FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';

/**
 * Validator Function for color
 * @param control 
 */
export const dischargeStudyColorValidator: ValidatorFn = (control: FormControl): ValidationErrors | null => {
  if (!control.root || !control.parent) {
    return null;
  }
  
  const portDetails = control.root.get('portDetails') as FormArray;
  let cargoColors = [];
  portDetails.value.map((item) => {
    const dischargeCargoDetails = item.cargoDetail.dataTable;
    const backLoadingCargoDetails = item.backLoadingDetails.dataTable;
    
    const dischargeCargoColor = dischargeCargoDetails.filter((cargoData) => {
      if(cargoData !== control.parent.value  && cargoData.color && cargoData.storedKey !== control.parent.value?.storedKey) {
        return cargoData;
      }
    }).map(cargoData => cargoData.color);

    const backLoadingCargoColor = backLoadingCargoDetails.filter((cargoData) => {
      if(cargoData !== control.parent.value  && cargoData.color) {
        return cargoData;
      }
    }).map(cargoData => cargoData.color);
    cargoColors = [...cargoColors , ...dischargeCargoColor , ...backLoadingCargoColor]
  })
  const cargoColor = control?.value;
  return cargoColors?.includes(cargoColor) ? { duplicateColor: true } : null;
};
