import { FormArray, FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';


/**
 * Validator Function for CargoQuantity
 * @param control 
 */
export const dischargeStudyCargoQuantityValidator: ValidatorFn = (control: FormControl): ValidationErrors | null => {
  if (!control.root || !control.parent || control.value === '') {
    return null;
  }

  const portDetails = control.root.get('portDetails') as FormArray;
  let totalParentQuantityIndex;
  let calculatedTotalQuantity = 0;
  let maxQuantity;
  let isAutoModeAvailable;
  //Note: - mode 3 need to be confirmed
  // const isEntireRemainingStatus;
  let noCargoFoundtStatus;
  let isQuantityAutoCorrect;

  portDetails['controls'].map((item, itemIndex) => {

    if (totalParentQuantityIndex === undefined) {
      const dischargeCargoFormControls = item.get('cargoDetail').get('dataTable') as FormArray;
      dischargeCargoFormControls['controls'].map((backLoadingItems) => {
        if (control.parent?.value?.color && backLoadingItems.get('storedKey').value === control.parent?.value?.storedKey) {
          totalParentQuantityIndex = itemIndex;
          if(backLoadingItems.get('mode').value.id === 2) {
            calculatedTotalQuantity += Number(backLoadingItems.get('kl').value);
          } else if(backLoadingItems.get('mode').value.id === 1) {
            isAutoModeAvailable = true;
          } 
          //Note: - mode 3 need to be confirmed
          // else if(backLoadingItems.get('mode').value.id === 3) {
          //   isEntireRemainingStatus = true;
          // }
          maxQuantity = Number(backLoadingItems.get('maxKl').value);
        }
      })

      const backLoadingDetailsFormControls = item.get('backLoadingDetails').get('dataTable') as FormArray;
      backLoadingDetailsFormControls['controls'].map((backLoadingItems) => {
        if (control.parent?.value?.color && backLoadingItems.get('storedKey').value === control.parent?.value?.storedKey) {
          totalParentQuantityIndex = itemIndex;
          maxQuantity = Number(backLoadingItems.get('kl').value);
        }
      })
      if(calculatedTotalQuantity === maxQuantity) {
        isQuantityAutoCorrect = true;
      }
    } else {
      const cargoDataTable = item.get('cargoDetail').get('dataTable') as FormArray;
      const findCargo = cargoDataTable['controls'].find((cargoDetailItems, cargoDetailIndex) => {
        if (control.parent.value.color && cargoDetailItems.get('storedKey').value === control.parent.value.storedKey) {
          if(cargoDetailItems.get('mode').value?.id === 1) {
            isAutoModeAvailable = true;
          } 
          //Note: - mode 3 need to be confirmed
          // else if(cargoDetailItems.get('mode').value?.id === 3) {
          //   isEntireRemainingStatus = true;
          // }
          return cargoDetailItems;
        }
      })
      
      if (findCargo && findCargo.get('mode').value?.id === 2) {
        calculatedTotalQuantity = Number((calculatedTotalQuantity + Number(findCargo.get('kl').value)).toFixed(3));
      } else if(!findCargo) {
        noCargoFoundtStatus = true;
      }
      if(calculatedTotalQuantity === maxQuantity) {
        isQuantityAutoCorrect = true;
      }
    }
  })
  if(!isQuantityAutoCorrect && calculatedTotalQuantity > maxQuantity) {
    return { greaterThanTotalQuantity: true }
  } else if(!noCargoFoundtStatus && !isAutoModeAvailable && calculatedTotalQuantity !== maxQuantity && !isQuantityAutoCorrect) {
    return { quantityNotEqual : true }
  } else {
    return null;
  }
};
