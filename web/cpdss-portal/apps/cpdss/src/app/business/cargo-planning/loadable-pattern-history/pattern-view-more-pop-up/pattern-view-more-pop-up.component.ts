import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { DecimalPipe } from '@angular/common';
import { Router } from '@angular/router';
import { QUANTITY_UNIT } from '../../../../shared/models/common.model';
import { IDataTableColumn } from '../../../../shared/components/datatable/datatable.model';
import { AppConfigurationService } from '../../../../shared/services/app-configuration/app-configuration.service';
import { IBallastStowageDetails, IBallastTank, ICargo, ICargoTank, ILoadableQuantityCargo, ITankOptions, TANKTYPE } from '../../../core/models/common.model';
import { ILoadablePattern } from '../../models/loadable-pattern.model';
import { LoadableStudyPatternTransformationService } from '../../services/loadable-study-pattern-transformation.service'
import { QuantityPipe } from '../../../../shared/pipes/quantity/quantity.pipe';
import { QuantityDecimalFormatPipe } from '../../../../shared/pipes/quantity-decimal-format/quantity-decimal-format.pipe'

/**
 * Component class of pattern view more popup
 *
 * @export
 * @class PatternViewMorePopUpComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-pattern-view-more-pop-up',
  templateUrl: './pattern-view-more-pop-up.component.html',
  styleUrls: ['./pattern-view-more-pop-up.component.scss']
})
export class PatternViewMorePopUpComponent implements OnInit {
  @Output() displayPopup = new EventEmitter();

  @Input() voyageId: number;
  @Input() loadableStudyId: number;
  @Input() vesselId: number;
  @Input() display;
  @Input() cargoTankList: ICargoTank[][];
  @Input() rearBallastTanks: IBallastTank[][];
  @Input() centerBallastTanks: IBallastTank[][];
  @Input() frontBallastTanks: IBallastTank[][];
  @Input() cargos: ICargo[];
  @Input()
  get selectedLoadablePattern(): ILoadablePattern {
    return this._selectedLoadablePattern;
  }
  set selectedLoadablePattern(value: ILoadablePattern) {
    this._selectedLoadablePattern = JSON.parse(JSON.stringify(value));
  }

  get loadablePlanBallastDetails(): IBallastStowageDetails[] {
    return this._loadablePlanBallastDetails;
  }
  set loadablePlanBallastDetails(loadablePlanBallastDetails: IBallastStowageDetails[]) {
    this.rearBallastTanks = this.rearBallastTanks?.map(group => group.map(tank => this.loadableStudyPatternTransformationService.formatBallastTanks(tank, loadablePlanBallastDetails)));
    this.centerBallastTanks = this.centerBallastTanks?.map(group => group.map(tank => this.loadableStudyPatternTransformationService.formatBallastTanks(tank, loadablePlanBallastDetails)));
    this.frontBallastTanks = this.frontBallastTanks?.map(group => group.map(tank => this.loadableStudyPatternTransformationService.formatBallastTanks(tank, loadablePlanBallastDetails)));
  }


  cargoTableCol: IDataTableColumn[];
  cargoTanks: ICargoTank[][];
  cargoTobeLoadedColumns: IDataTableColumn[];
  cargoTobeLoaded: ILoadableQuantityCargo[];
  totalBallast = 0;
  readonly tankType = TANKTYPE;
  selectedTab = TANKTYPE.CARGO;
  currentQuantitySelectedUnit = <QUANTITY_UNIT>localStorage.getItem('unit');
  prevQuantitySelectedUnit: QUANTITY_UNIT;
  baseUnit = AppConfigurationService.settings.baseUnit;

  cargoTankOptions: ITankOptions = { isFullyFilled: false, showTooltip: true, isSelectable: false, showFillingPercentage: true, fillingPercentageField: 'fillingRatio', weightField: 'quantity', showWeight: true, weightUnit: 'MT', commodityNameField: 'cargoAbbreviation', ullageField: 'rdgUllage', ullageUnit: AppConfigurationService.settings?.ullageUnit, densityField: 'api' }
  ballastTankOptions: ITankOptions = { isFullyFilled: false, showUllage: true, showFillingPercentage: true, class: 'loadable-plan-stowage', fillingPercentageField: 'percentage', ullageField: 'correctedLevel', ullageUnit: AppConfigurationService.settings?.ullageUnit, showTooltip: true, weightField: 'metricTon', weightUnit: AppConfigurationService.settings.baseUnit, showDensity: true, densityField: 'sg' };

  private _loadablePlanBallastDetails: IBallastStowageDetails[];
  private _selectedLoadablePattern: ILoadablePattern;

  constructor(private router: Router,
    private loadableStudyPatternTransformationService: LoadableStudyPatternTransformationService,
    private _decimalPipe: DecimalPipe,
    private quantityPipe: QuantityPipe,
    private quantityDecimalFormatPipe: QuantityDecimalFormatPipe) { }

  /**
  * Component lifecycle ngOnit
  *
  * @returns {Promise<void>}
  * @memberof PatternViewMorePopUpComponent
  */
  ngOnInit(): void {
    this.cargoTableCol = this.loadableStudyPatternTransformationService.getCargoPriorityGridMoreTableColumn();
    this.cargoTobeLoadedColumns = this.loadableStudyPatternTransformationService.getCargotobeLoadedDatatableColumns(this.currentQuantitySelectedUnit);
    this.prevQuantitySelectedUnit = AppConfigurationService.settings.baseUnit;
    this.updateCargoTobeLoadedData();
    this.updateLoadablePlanStowageData();
    this.updateBallastTankData();
  }


  /**
   * Handler for tab click
   *
   * @param {TANKTYPE} selectedTab
   * @memberof PatternViewMorePopUpComponent
   */
  onTabClick(selectedTab: TANKTYPE) {
    this.selectedTab = selectedTab;
  }

  // for closing active modal pattern view more popup
  closeDialog() {
    this.displayPopup.emit(false);
  }

  /**
  * for navigating stowage plan
  *
  * @param {*} event
  * @memberof PatternViewMorePopUpComponent
  */
  viewPlan() {
    this.router.navigate([`/business/cargo-planning/loadable-plan/${this.vesselId}/${this.voyageId}/${this.loadableStudyId}/${this.selectedLoadablePattern.loadablePatternId}`]);
  }

  /**
   * Method to update commodity in tank list
   *
   * @memberof PatternViewMorePopUpComponent
   */
  updateTankLIst() {
    this.cargoTanks = this.cargoTankList?.map(group => {
      const newGroup = group.map((groupItem) => {
        const tank = Object.assign({}, groupItem);
        tank.commodity = this.selectedLoadablePattern.loadablePlanStowageDetails.find((item) => (item.tankId === groupItem.id) && item);
        if (tank.commodity?.isCommingle) {
          tank.commodity.colorCode = AppConfigurationService.settings.commingleColor;
        }
        return tank;
      });
      return newGroup;
    })
  }

  /**
   * Method to update ballast details
   *
   * @memberof PatternViewMorePopUpComponent
   */
  updateBallastTankData() {
    this.loadablePlanBallastDetails = this.selectedLoadablePattern.loadablePlanBallastDetails?.map(ballast => {
      this.totalBallast += Number(ballast.metricTon);
      const tank = this.findBallastTank(ballast.tankId, [this.frontBallastTanks, this.rearBallastTanks, this.centerBallastTanks])
      ballast.fullCapacityCubm = tank?.fullCapacityCubm
      const formattedCargo = this.loadableStudyPatternTransformationService.getFormattedBallastDetails(this._decimalPipe, ballast)
      return formattedCargo
    }) ?? [];;
  }

  /**
  * Method to get the specific ballast tank
  *
  * @memberof PatternViewMorePopUpComponent
  */
  findBallastTank(tankId, tankLists) {
    let tankDetails;
    tankLists.forEach(tankArr => {
      tankArr.forEach(arr => {
        arr.forEach(tank => {
          if (tank.id === tankId) {
            tankDetails = tank;
            return;
          }
        })
      })
    })
    return tankDetails;
  }

  /**
  * Method to update cargo to be loaded data
  *
  * @memberof PatternViewMorePopUpComponent
  */
  updateCargoTobeLoadedData() {
    this.cargoTobeLoaded = this.selectedLoadablePattern.loadableQuantityCargoDetails?.map(loadable => {
      if (loadable) {
        const minTolerence = this.loadableStudyPatternTransformationService.decimalConvertion(this._decimalPipe, loadable.minTolerence, '0.2-2');
        const maxTolerence = this.loadableStudyPatternTransformationService.decimalConvertion(this._decimalPipe, loadable.maxTolerence, '0.2-2');
        loadable.minMaxTolerance = maxTolerence + (minTolerence ? "/" + minTolerence : '');
        loadable.differencePercentage = loadable.differencePercentage ? (loadable.differencePercentage.includes('%') ? loadable.differencePercentage : loadable.differencePercentage + '%') : '';
        loadable.grade = this.fingCargo(loadable);

        const orderedQuantity = this.quantityPipe.transform(this.loadableStudyPatternTransformationService.convertToNumber(loadable?.orderedQuantity), this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit, loadable?.estimatedAPI, loadable?.estimatedTemp, -1);
        loadable.orderedQuantity = orderedQuantity.toString();

        const loadableMT = this.quantityPipe.transform(this.loadableStudyPatternTransformationService.convertToNumber(loadable?.loadableMT), this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit, loadable?.estimatedAPI, loadable?.estimatedTemp, -1);
        loadable.loadableMT = loadableMT.toString();

        const slopQuantity = loadable?.slopQuantity ? this.quantityPipe.transform(this.loadableStudyPatternTransformationService.convertToNumber(loadable?.slopQuantity.toString()), this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit, loadable?.estimatedAPI, loadable?.estimatedTemp, -1) : 0;
        loadable.slopQuantity = slopQuantity;

          loadable.loadingPortsLabels = loadable?.loadingPorts?.join(',');
      }
      return loadable;
    })
  }


  /**
  * Method to find out cargo
  *
  * @memberof PatternViewMorePopUpComponent
  */
  fingCargo(loadableQuantityCargoDetails): string {
    let cargoDetail;
    this.cargos.map((cargo) => {
      if (cargo.id === loadableQuantityCargoDetails.cargoId) {
        cargoDetail = cargo;
      }
    })
    return cargoDetail.name;
  }

  /**
  * Handler for unit change event
  *
  * @param {*} event
  * @memberof PatternViewMorePopUpComponent
  */
  onUnitChange() {
    this.prevQuantitySelectedUnit = this.currentQuantitySelectedUnit ?? AppConfigurationService.settings.baseUnit
    this.currentQuantitySelectedUnit = <QUANTITY_UNIT>localStorage.getItem('unit');
    if (this.prevQuantitySelectedUnit) {
      this.cargoTobeLoadedColumns = this.loadableStudyPatternTransformationService.getCargotobeLoadedDatatableColumns(this.currentQuantitySelectedUnit);
      this.convertQuantityToSelectedUnit();
    }
  }

  /**
* Method to convert to selected unit
*
* @memberof PatternViewMorePopUpComponent
*/
  convertQuantityToSelectedUnit() {
    this.updateLoadablePlanStowageData();
    this.updateLoadablePatternCargoDetails();
    this.cargoTobeLoaded = this.cargoTobeLoaded?.map(loadable => {
      if (loadable) {
        const orderedQuantity = this.quantityPipe.transform(this.loadableStudyPatternTransformationService.convertToNumber(loadable?.orderedQuantity), this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit, loadable?.estimatedAPI, loadable?.estimatedTemp, -1);
        loadable.orderedQuantity = orderedQuantity.toString();

        const loadableMT = this.quantityPipe.transform(this.loadableStudyPatternTransformationService.convertToNumber(loadable?.loadableMT), this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit, loadable?.estimatedAPI, loadable?.estimatedTemp, -1);
        loadable.loadableMT = loadableMT.toString();

        const slopQuantity = loadable?.slopQuantity ? this.quantityPipe.transform(this.loadableStudyPatternTransformationService.convertToNumber(loadable?.slopQuantity.toString()), this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit, loadable?.estimatedAPI, loadable?.estimatedTemp, -1) : 0;
        loadable.slopQuantity = slopQuantity;

      }
      return loadable;
    })
  }

  /**
   * Method to update loadable plan stowage details
   *
   * @memberof PatternViewMorePopUpComponent
   */
  updateLoadablePlanStowageData() {
    this.selectedLoadablePattern.loadablePlanStowageDetails = this.selectedLoadablePattern.loadablePlanStowageDetails?.map(loadableStowage => {
      if (loadableStowage) {
        const quantity = this.quantityPipe.transform(loadableStowage?.quantityMT, this.baseUnit, this.currentQuantitySelectedUnit, loadableStowage?.api,loadableStowage?.temperature,-1);
        loadableStowage.quantity = Number(quantity);
      }
      return loadableStowage;
    })
    this.cargoTankOptions.weightUnit = this.currentQuantitySelectedUnit;
    this.updateTankLIst();
  }

  /**
   * Method to update loadable pattern cargo detail
   *
   * @memberof PatternViewMorePopUpComponent
   */
  updateLoadablePatternCargoDetails() {
    this.selectedLoadablePattern.loadablePatternCargoDetails = this.selectedLoadablePattern.loadablePatternCargoDetails?.map(cargoDetail => {
      if (cargoDetail) {
        const quantity = this.quantityPipe.transform(cargoDetail?.quantity, this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit, cargoDetail?.api, cargoDetail.temperature,-1);
        cargoDetail.quantity = Number(quantity);
      }
      return cargoDetail;
    })
  }
}
