import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { IDataTableColumn } from '../../../../shared/components/datatable/datatable.model';
import { AppConfigurationService } from '../../../../shared/services/app-configuration/app-configuration.service';
import { ICargoTank, ITankOptions, LOADABLE_STUDY_STATUS, VOYAGE_STATUS } from '../../../core/models/common.model';
import { ILoadablePattern } from '../../models/loadable-pattern.model';
import { LoadableStudyPatternTransformationService } from '../../services/loadable-study-pattern-transformation.service';
import { QUANTITY_UNIT } from '../../../../shared/models/common.model';
import { VALIDATION_AND_SAVE_STATUS } from '../../models/loadable-plan.model';
import { LoadablePatternHistoryApiService } from '../../services/loadable-pattern-history-api.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { saveAs } from 'file-saver';

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
  @Output() viewPlan = new EventEmitter();
  @Output() confirmPlanClick = new EventEmitter();

  @Input() index: number;
  @Input() loadablePlanPermissionContext;
  @Input() selectedVoyage;
  @Input() vesselId;

  @Input()
  set loadablePattern(loadablePattern: ILoadablePattern) {
    this._loadablePattern = this.setCommingle(loadablePattern);
    this.loadablePatternDetailsId = loadablePattern?.loadablePatternId;
    this.updateTankLIst();
  }
  get loadablePattern(): ILoadablePattern {
    return this._loadablePattern
  }
  @Input() tankList: ICargoTank[][];

  private _loadablePattern: ILoadablePattern;
  tableCol: IDataTableColumn[];
  loadablePatternDetailsId: number;
  tanks: ICargoTank[][];
  cargoTankOptions: ITankOptions = { isFullyFilled: false, showTooltip: true, isSelectable: false, fillingPercentageField: 'fillingRatioOrginal', weightField: 'quantity', commodityNameField: 'cargoAbbreviation', ullageField: 'correctedUllageOrginal', ullageUnit: AppConfigurationService.settings?.ullageUnit, densityField: 'api' }

  readonly VALIDATION_AND_SAVE_STATUS = VALIDATION_AND_SAVE_STATUS;
  readonly LOADABLE_STUDY_STATUS = LOADABLE_STUDY_STATUS;
  readonly VOYAGE_STATUS = VOYAGE_STATUS;

  constructor(
    private loadableStudyPatternTransformationService: LoadableStudyPatternTransformationService,
    private loadablePatternApiService: LoadablePatternHistoryApiService,
    private ngxSpinnerService: NgxSpinnerService
    ) { }

  /**
   * Component lifecycle ngOnit
   *
   * @returns {Promise<void>}
   * @memberof PatternCaseComponent
   */
  ngOnInit(): void {
    this.tableCol = this.loadableStudyPatternTransformationService.getCargoPriorityGridCaseTableColumn();
    this.updateTankLIst();
  }

  /**
   * Set commingle color
   *
   * @memberof PatternCaseComponent
   */
  setCommingle(data) {
    if (data?.loadableQuantityCommingleCargoDetails?.length) {
      data?.loadablePlanStowageDetails?.map(item => {
        data?.loadableQuantityCommingleCargoDetails?.map(com => {
          if (item.cargoAbbreviation === com.grade) {
            item.colorCode = com.colorCode;
          }
        });
      });
      data?.loadablePatternCargoDetails?.map(item => {
        data?.loadableQuantityCommingleCargoDetails?.map(com => {
          if (item.cargoAbbreviation === com.grade) {
            item.cargoColor = com.colorCode;
          }
        });
      });
    }
    return data;
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
        return tank;
      });
      return newGroup;
    })
    this.cargoTankOptions.weightUnit = <QUANTITY_UNIT>localStorage.getItem('unit');
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

  /**
   * Method to view plan
   *
   * @memberof PatternCaseComponent
   */
  viewPlanInfo(pattern) {
    this.viewPlan.emit(pattern);
  }

  /**
  * Method to show confirmation pop up
  *
  * @memberof PatternCaseComponent
  */
  onConfirmPlanClick(event) {
    this.confirmPlanClick.emit(event);
  }

  downloadDatFile(): void {
    this.ngxSpinnerService.show();
    this.loadablePatternApiService.downloadDATfile(this.vesselId, this.loadablePatternDetailsId).subscribe((result) => {
      const fileName = result.headers.get('content-disposition').split('filename=')[1];
      const blob = new Blob([result.body], { type: result.type });
      const fileurl = window.URL.createObjectURL(blob);
      saveAs(fileurl, fileName);
      this.ngxSpinnerService.hide();
    });
  }
}
