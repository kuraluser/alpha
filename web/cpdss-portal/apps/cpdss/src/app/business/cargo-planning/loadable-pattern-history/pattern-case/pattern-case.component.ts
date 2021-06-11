import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { IDataTableColumn } from '../../../../shared/components/datatable/datatable.model';
import { AppConfigurationService } from '../../../../shared/services/app-configuration/app-configuration.service';
import { ICargoTank, ITankOptions } from '../../../core/models/common.model';
import { ILoadablePattern } from '../../models/loadable-pattern.model';
import { LoadableStudyPatternTransformationService } from '../../services/loadable-study-pattern-transformation.service';
import { QUANTITY_UNIT } from '../../../../shared/models/common.model';

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

  @Input()
  set loadablePattern(loadablePattern: ILoadablePattern){
    this._loadablePattern = loadablePattern;
    this.loadablePatternDetailsId = loadablePattern?.loadablePatternId;
    this.updateTankLIst();
  }
  get loadablePattern():ILoadablePattern{
    return this._loadablePattern
  }
  @Input() tankList: ICargoTank[][];

  private _loadablePattern: ILoadablePattern;
  tableCol: IDataTableColumn[];
  loadablePatternDetailsId: number;
  tanks: ICargoTank[][];
  cargoTankOptions: ITankOptions = { isFullyFilled: false, showTooltip: true, isSelectable: false, fillingPercentageField: 'fillingRatio', weightField: 'quantity', commodityNameField: 'cargoAbbreviation', ullageField : 'rdgUllage', ullageUnit: AppConfigurationService.settings?.ullageUnit, densityField: 'api' }
  constructor(private loadableStudyPatternTransformationService: LoadableStudyPatternTransformationService) { }

  /**
   * Component lifecycle ngOnit
   *
   * @returns {Promise<void>}
   * @memberof PatternCaseComponent
   */
  ngOnInit(): void {
    this.tableCol =  this.loadableStudyPatternTransformationService.getCargoPriorityGridCaseTableColumn();
    this.updateTankLIst();
  }

  /**
   * Method to update commodity in tank list
   *
   * @memberof PatternCaseComponent
   */
  updateTankLIst() {
    this.tanks = this.tankList?.map(group => {
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
    this.cargoTankOptions.weightUnit  = <QUANTITY_UNIT>localStorage.getItem('unit');
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
