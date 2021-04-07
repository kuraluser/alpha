import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { IDataTableColumn } from '../../../../shared/components/datatable/datatable.model';
import { AppConfigurationService } from '../../../../shared/services/app-configuration/app-configuration.service';
import { ICargoTank, ITankOptions } from '../../../core/models/common.model';
import { ILoadablePattern } from '../../models/loadable-pattern.model';
import { LoadableStudyPatternTransformationService } from '../../services/loadable-study-pattern-transformation.service';

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
  @Output() displayPatternViewMorePopup = new EventEmitter();

  @Input() index: number;
  @Input() loadablePattern: ILoadablePattern;
  @Input() tankList: ICargoTank[][];

  tableCol: IDataTableColumn[];
  loadablePatternDetailsId: number;
  tanks: ICargoTank[][];
  cargoTankOptions: ITankOptions = { isFullyFilled: false, showTooltip: true, isSelectable: false, fillingPercentageField: 'fillingRatio', weightField: 'quantityMT' }
  constructor(private loadableStudyPatternTransformationService: LoadableStudyPatternTransformationService) { }

  /**
   * Component lifecycle ngOnit
   *
   * @returns {Promise<void>}
   * @memberof PatternCaseComponent
   */
  ngOnInit(): void {
    this.tableCol =  this.loadableStudyPatternTransformationService.getCargoPriorityGridCaseTableColumn();
    this.loadablePatternDetailsId = this.loadablePattern?.loadablePatternId;
    this.updateTankLIst();
  }

  /**
   * Method to update commodity in tank list
   *
   * @memberof PatternCaseComponent
   */
  updateTankLIst() {
    this.tanks = this.tankList.map(group => {
      const newGroup = group.map((groupItem) => {
        const tank = Object.assign({}, groupItem);
        tank.commodity = this.loadablePattern.loadablePlanStowageDetails.find((item) => (item.tankId === groupItem.id) && item);
        if (tank.commodity?.isCommingle) {
          tank.commodity.colorCode = AppConfigurationService.settings.commingleColor;
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

   /**
  * Method to show pattern view more pop up
  *
  * @memberof PatternCaseComponent
  */
    patternViewMore(event) {
      this.displayPatternViewMorePopup.emit(this.loadablePattern)
    }
}
