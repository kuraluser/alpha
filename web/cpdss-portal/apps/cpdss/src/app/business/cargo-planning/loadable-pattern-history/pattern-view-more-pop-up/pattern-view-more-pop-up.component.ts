import { DecimalPipe } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { ICargo, QUANTITY_UNIT } from '../../../../shared/models/common.model';
import { IDataTableColumn } from '../../../../shared/components/datatable/datatable.model';
import { AppConfigurationService } from '../../../../shared/services/app-configuration/app-configuration.service';
import { IBallastStowageDetails, IBallastTank, ICargoTank, ITankOptions, TANKTYPE } from '../../../core/models/common.model';
import { ILoadableQuantityCargo } from '../../models/cargo-planning.model';
import { ILoadablePattern } from '../../models/loadable-pattern.model';
import { LoadableStudyPatternTransformationService } from '../../services/loadable-study-pattern-transformation.service'
import { QuantityPipe } from 'apps/cpdss/src/app/shared/pipes/quantity/quantity.pipe';

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
  @Input() selectedLoadablePattern: ILoadablePattern;
  @Input() cargoTankList: ICargoTank[][];
  @Input() rearBallastTanks: IBallastTank[][];
  @Input() centerBallastTanks: IBallastTank[][];
  @Input() frontBallastTanks: IBallastTank[][];
  @Input() cargos: ICargo[];

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

  cargoTankOptions: ITankOptions = { isFullyFilled: false, showTooltip: true, isSelectable: false, fillingPercentageField: 'fillingRatio', weightField: 'quantity', showWeight: true, weightUnit: 'MT', commodityNameField: 'cargoAbbreviation', ullageField : 'rdgUllage', ullageUnit: 'CM', densityField: 'api' }
  ballastTankOptions: ITankOptions = { isFullyFilled: false, showUllage: true, showFillingPercentage: true, class: 'loadable-plan-stowage', fillingPercentageField: 'percentage', ullageField: 'correctedLevel', ullageUnit: 'CM', showTooltip: true, weightField: 'metricTon', weightUnit: AppConfigurationService.settings.baseUnit, showDensity: true, densityField: 'sg' };

  private _loadablePlanBallastDetails: IBallastStowageDetails[];

  constructor(private router: Router,
    private loadableStudyPatternTransformationService: LoadableStudyPatternTransformationService,
    private _decimalPipe: DecimalPipe,
    private quantityPipe: QuantityPipe) { }

  /**
  * Component lifecycle ngOnit
  *
  * @returns {Promise<void>}
  * @memberof PatternViewMorePopUpComponent
  */
  ngOnInit(): void {
    this.cargoTableCol = this.loadableStudyPatternTransformationService.getCargoPriorityGridMoreTableColumn();
    this.cargoTobeLoadedColumns = this.loadableStudyPatternTransformationService.getCargotobeLoadedDatatableColumns();
    this.prevQuantitySelectedUnit = AppConfigurationService.settings.baseUnit;
    this.updateCargoTobeLoadedData();
    this.updateLoadablePlanStowageData();
    this.updateBallastTankData();
    this.updateLoadablePatternCargoDetails();
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
        loadable.apiTemp = (Number(loadable.estimatedAPI).toFixed(2)).toString() + (loadable.estimatedTemp ? "/" + (Number(loadable.estimatedTemp).toFixed(2)).toString() : '');
        loadable.minMaxTolerance = loadable.minTolerence + (loadable.maxTolerence ? "/" + loadable.maxTolerence : '');
        loadable.differencePercentage = loadable.differencePercentage ? (loadable.differencePercentage.includes('%') ? loadable.differencePercentage : loadable.differencePercentage + '%') : '';
        loadable.grade = this.fingCargo(loadable)
        const orderedQuantity = this.quantityPipe.transform(loadable?.orderedQuantity, this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit, loadable?.estimatedAPI);
        loadable.orderedQuantity = orderedQuantity.toFixed(2).toString();
        const loadableMT = this.quantityPipe.transform(loadable?.loadableMT, this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit, loadable?.estimatedAPI);
        loadable.loadableMT = loadableMT.toFixed(2);
        loadable.loadingPort = loadable?.loadingPorts?.join(',');
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
      this.prevQuantitySelectedUnit = this.currentQuantitySelectedUnit;
      this.currentQuantitySelectedUnit = <QUANTITY_UNIT>localStorage.getItem('unit');
      if (this.prevQuantitySelectedUnit) {
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
        const orderedQuantity = this.quantityPipe.transform(loadable?.orderedQuantity, this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit, loadable?.estimatedAPI);
        loadable.orderedQuantity = orderedQuantity.toFixed(2).toString();
        const loadableMT = this.quantityPipe.transform(loadable?.loadableMT, this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit, loadable?.estimatedAPI);
        loadable.loadableMT = loadableMT.toFixed(2);
      } 
      return loadable;
    })
  }

  /**
   * Method to update loadable plan stowage details
   *
   * @memberof PatternViewMorePopUpComponent
   */
  updateLoadablePlanStowageData(){
    this.selectedLoadablePattern.loadablePlanStowageDetails = this.selectedLoadablePattern.loadablePlanStowageDetails?.map(loadableStowage => {
      if (loadableStowage) {
        const quantity = this.quantityPipe.transform(loadableStowage?.quantityMT, this.baseUnit, this.currentQuantitySelectedUnit, loadableStowage?.api);
        loadableStowage.quantity = Number(quantity?.toFixed(2));
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
  updateLoadablePatternCargoDetails(){
    this.selectedLoadablePattern.loadablePatternCargoDetails =  this.selectedLoadablePattern.loadablePatternCargoDetails?.map(cargoDetail => {
      if (cargoDetail) {
        const quantity = this.quantityPipe.transform(cargoDetail?.quantity, this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit, cargoDetail?.api);
        cargoDetail.quantity = Number(quantity?.toFixed(2));
      }
      return cargoDetail;
    })
  }
}
