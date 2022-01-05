import { FormArray, FormControl, ValidationErrors, ValidatorFn } from '@angular/forms';
import { IDISCHARGE_STUDY_MODE } from '../../models/discharge-study-list.model';


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
  let quantitySum = 0;
  let maxQuantity;
  let isAutoModeAvailable;

  let isEntireRemainingStatus;
  let noCargoFoundtStatus;
  let isQuantityAutoCorrect;

  const isQuantityWithInTheRange = (quantitySum, maxQuantity) => {
    const constantValue = (0.01 / 100) * maxQuantity;
    if (quantitySum >= (maxQuantity - constantValue) && quantitySum <= (maxQuantity + constantValue)) {
      return true;
    } else {
      return false;
    }
  }

  portDetails['controls'].map((item, itemIndex) => {

    if (totalParentQuantityIndex === undefined) {
      const dischargeCargoFormControls = item.get('cargoDetail').get('dataTable') as FormArray;
      dischargeCargoFormControls['controls'].map((backLoadingItems) => {
        if (control.parent?.value?.color && backLoadingItems.get('storedKey').value === control.parent?.value?.storedKey) {
          totalParentQuantityIndex = itemIndex;
          if (backLoadingItems.get('mode').value.id === 2) {
            quantitySum += Number(backLoadingItems.get('kl').value);
          } else if (backLoadingItems.get('mode').value.name === IDISCHARGE_STUDY_MODE.BALANCE) {
            isAutoModeAvailable = true;
          }
          else if (backLoadingItems.get('mode').value.name === IDISCHARGE_STUDY_MODE.ENTIRE) {
            isEntireRemainingStatus = true;
          }
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
      if (quantitySum === maxQuantity) {
        isQuantityAutoCorrect = true;
      }
    } else {
      const cargoDataTable = item.get('cargoDetail').get('dataTable') as FormArray;
      const findCargo = cargoDataTable['controls'].find((cargoDetailItems, cargoDetailIndex) => {
        if (control.parent.value.color && cargoDetailItems.get('storedKey').value === control.parent.value.storedKey) {
          if (cargoDetailItems.get('mode').value?.name === IDISCHARGE_STUDY_MODE.BALANCE) {
            isAutoModeAvailable = true;
          }

          else if (cargoDetailItems.get('mode').value?.name === IDISCHARGE_STUDY_MODE.ENTIRE) {
            isEntireRemainingStatus = true;
          }
          return cargoDetailItems;
        }
      })

      if (findCargo && (findCargo.get('mode').value?.name === IDISCHARGE_STUDY_MODE.MANUAL || (!isAutoModeAvailable && findCargo.get('mode').value?.name === IDISCHARGE_STUDY_MODE.ENTIRE))) {
        quantitySum = Number((quantitySum + Number(findCargo.get('kl').value)).toFixed(3));
      } else if (!findCargo) {
        noCargoFoundtStatus = true;
      }

      if (isQuantityWithInTheRange(quantitySum, maxQuantity)) {
        isQuantityAutoCorrect = true;
      }
    }
  })
  const constantValue = (0.01 / 100) * maxQuantity;
  if (!isQuantityAutoCorrect && quantitySum > (maxQuantity + constantValue)) {
    return { greaterThanTotalQuantity: true }
  } else if (!noCargoFoundtStatus && !isEntireRemainingStatus && !isAutoModeAvailable && !isQuantityWithInTheRange(quantitySum, maxQuantity) && !isQuantityAutoCorrect) {
    return { quantityNotEqual: true }
  } else {
    return null;
  }
};
