import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { QUANTITY_UNIT } from '../../../../shared/models/common.model';
import { QuantityPipe } from '../../../../shared/pipes/quantity/quantity.pipe';
import { AppConfigurationService } from '../../../../shared/services/app-configuration/app-configuration.service';
import { ICargoTank, ILoadableCargo, ITankOptions } from '../../../core/models/common.model';
import { ILoadablePattern } from '../../models/loadable-pattern.model';

/**
 * Component for pattern case
 *
 * @export
 * @class PatternCaseComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-pattern-case',
  templateUrl: './pattern-case.component.html',
  styleUrls: ['./pattern-case.component.scss']
})
export class PatternCaseComponent implements OnInit {

  @Output() displayCommingleDetailPopup = new EventEmitter();

  @Input() index: number;
  @Input() loadablePattern: ILoadablePattern;
  @Input() tankList: ICargoTank[][];

  loadablePatternDetailsId: number;
  tanks: ICargoTank[][];
  cargoTankOptions: ITankOptions = { isFullyFilled: false,showTooltip: true, isSelectable: false, fillingPercentageField: 'fillingRatio', weightField: 'quantityMT' }
  constructor(private quantityPipe: QuantityPipe) { }

  /**
   * Component lifecycle ngOnit
   *
   * @returns {Promise<void>}
   * @memberof PatternCaseComponent
   */
  ngOnInit(): void {
    this.loadablePatternDetailsId = this.loadablePattern?.loadablePatternId;
    this.updateTankLIst()
  }

  /**
   * Method to update commodity in tank list
   *
   * @memberof PatternCaseComponent
   */
  updateTankLIst() {
    const commingleData = this.loadablePattern.loadablePatternCargoDetails.filter((item) => (item.isCommingle === true) && item);
    this.tanks = this.tankList.map(group => {
      const newGroup = group.map((groupItem) => {
        const tank = Object.assign({}, groupItem);
        const commingle = commingleData.find((item) => (item.tankName === groupItem.name) && item);
        if (commingle) {
          tank.commodity = <ILoadableCargo>{};
          tank.commodity.colorCode = "#7114d9";
          tank.commodity.quantity = commingle.quantity
          tank.commodity.volume = this.quantityPipe.transform(tank.commodity.quantity, AppConfigurationService.settings.baseUnit, QUANTITY_UNIT.KL, commingle.api);
          tank.commodity.fillingRatio = ((tank?.commodity?.volume / Number(tank?.fullCapacityCubm)) * 100).toFixed(2);
        } else {
          tank.commodity = this.loadablePattern.loadablePlanStowageDetails.find((item) => (item.tankId === groupItem.id) && item);
        }
        return tank;
      });
      return newGroup;
    })
  }

  /**
 * Method to show commingle cargo details pop up
 *
 * @memberof PatternCaseComponent
 */
  showComminglePopup(event) {
    const commingleData = {
      loadablePatternDetailsId: this.loadablePatternDetailsId,
      loadablePatternCargoDetail: event
    }
    this.displayCommingleDetailPopup.emit(commingleData)
  }
}
