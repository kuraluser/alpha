import { Component, OnInit, Input } from '@angular/core';
import { QUANTITY_UNIT, LENGTH_UNIT } from './../../../shared/models/common.model';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';

/**
 * Component class for arrival condition panel
 *
 * @export
 * @class ArrivalConditionPanelComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-arrival-condition-panel',
  templateUrl: './arrival-condition-panel.component.html',
  styleUrls: ['./arrival-condition-panel.component.scss']
})
export class ArrivalConditionPanelComponent implements OnInit {

  @Input() get loadingDischargingPlanData(): any {
    return this._loadingDischargingPlanData;
  }

  set loadingDischargingPlanData(value: any) {
    this._loadingDischargingPlanData = value;
    this.formatData();
  }

  _loadingDischargingPlanData: any;
  arrivalConditionCargoInfo: any = [];
  arrivalConditionCargoTotalQuantity = 0;
  ballastQuantity = 0;
  draftFValue = 0;
  draftAValue = 0;
  draftMValue = 0;
  trimValue = 0;
  freeboard = 0;
  manifoldHeight: number;
  hasCommingle = false;

  readonly QUANTITY_UNIT = QUANTITY_UNIT;
  readonly LENGTH_UNIT = LENGTH_UNIT;
  constructor() { }

  ngOnInit(): void {
  }

  /**
  * Format loading plan data
  *
  * @memberof ArrivalConditionPanelComponent
  */
  formatData() {
    this.hasCommingle = false;
    this.arrivalConditionCargoInfo = [];
    const commingleArray = [];
    this.arrivalConditionCargoTotalQuantity = 0;
    this.loadingDischargingPlanData?.planCommingleDetails?.map(com => {
      if (com.conditionType === 1 && com.valueType === 2) {
        commingleArray.push({ abbreviation: com.abbreviation, colorCode: com.colorCode, quantity: 0, tankId: com.tankId });
      }
    });
    this.loadingDischargingPlanData?.currentPortCargos.map(cargo => {
      let cargoQuantity = 0;
      this.loadingDischargingPlanData?.planStowageDetails?.map(item => {
        const currentPorCargoNominationId = this.loadingDischargingPlanData?.loadingInformation ? cargo.cargoNominationId : cargo.dischargeCargoNominationId;
        if (item.conditionType === 1 && item.valueType === 2 && currentPorCargoNominationId === item.cargoNominationId ) {
          if (item.isCommingleCargo) {
            commingleArray?.map(com => {
              if (com.tankId === item.tankId) {
                com.quantity += Number(item.quantityMT);
              }
            });
          } else {
            cargoQuantity += Number(item.quantityMT);
          }
        }
      });
      this.arrivalConditionCargoTotalQuantity += cargoQuantity;
      this.arrivalConditionCargoInfo.push({ abbreviation: cargo.cargoAbbreviation, colorCode: cargo.colorCode, quantity: cargoQuantity, cargoNominationId: cargo.cargoNominationId });
    });
    commingleArray?.map(com => {
      this.loadingDischargingPlanData?.planStowageDetails?.map(item => {
        if (item.isCommingleCargo && item.conditionType === 1 && item.valueType === 2) {
          if (com.tankId === item.tankId) {
            com.quantity += Number(item.quantityMT);
            this.arrivalConditionCargoTotalQuantity += Number(item.quantityMT);
          }
        }
      });
    });
    this.loadingDischargingPlanData?.planCommingleDetails?.map(com => {
      this.arrivalConditionCargoInfo?.map(cargo => {
        if (cargo.cargoNominationId === com.cargoNomination1Id) {
          if (com.valueType === 2) {
            cargo.quantity += com.quantity1MT;
          }
        }
        if (cargo.cargoNominationId === com.cargoNomination2Id) {
          if (com.valueType === 2) {
            cargo.quantity += com.quantity2MT;
          }
        }
      });
    });
    this.hasCommingle = commingleArray?.length ? true : false;
    this.arrivalConditionCargoInfo = [...commingleArray, ...this.arrivalConditionCargoInfo];
    let ballastQuantity = 0;
    this.loadingDischargingPlanData?.planBallastDetails?.map(item => {
      if (item.conditionType === 1 && item.valueType === 2) {
        ballastQuantity += Number(item.quantityMT);
      }
    });
    this.ballastQuantity = Number(ballastQuantity.toFixed(2));
    this.loadingDischargingPlanData?.planStabilityParams?.map(item => {
      if (item.conditionType === 1 && item.valueType === 2) {
        this.draftFValue = Number(item?.foreDraft);
        this.draftAValue = Number(item?.aftDraft);
        this.draftMValue = Number(item?.meanDraft);
        this.trimValue = Number(item?.trim);
        this.manifoldHeight = item?.manifoldHeight;
        this.freeboard = item?.freeBoard;
      }
    });
  }

}
