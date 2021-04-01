import { DecimalPipe } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { ICargo } from '../../../../shared/models/common.model';
import { IDataTableColumn } from '../../../../shared/components/datatable/datatable.model';
import { AppConfigurationService } from '../../../../shared/services/app-configuration/app-configuration.service';
import { IBallastStowageDetails, IBallastTank, ICargoTank, ITankOptions, TANKTYPE } from '../../../core/models/common.model';
import { ILoadableQuantityCargo } from '../../models/cargo-planning.model';
import { ILoadablePattern } from '../../models/loadable-pattern.model';
import { LoadableStudyPatternTransformationService } from '../../services/loadable-study-pattern-transformation.service'

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

  cargoTankOptions: ITankOptions = { isFullyFilled: false, showTooltip: true, isSelectable: false, fillingPercentageField: 'fillingRatio', weightField: 'quantityMT', showWeight: true, weightUnit: 'MT' }
  ballastTankOptions: ITankOptions = { isFullyFilled: false, showUllage: true, showFillingPercentage: true, class: 'loadable-plan-stowage', fillingPercentageField: 'percentage', ullageField: 'correctedLevel', ullageUnit: 'CM', showTooltip: true, weightField: 'metricTon', weightUnit: AppConfigurationService.settings.baseUnit, showDensity: true, densityField: 'sg' };

  private _loadablePlanBallastDetails: IBallastStowageDetails[];

  constructor(private router: Router,
    private loadableStudyPatternTransformationService: LoadableStudyPatternTransformationService,
    private _decimalPipe: DecimalPipe) { }

  /**
  * Component lifecycle ngOnit
  *
  * @returns {Promise<void>}
  * @memberof PatternViewMorePopUpComponent
  */
  ngOnInit(): void {
    this.cargoTableCol = this.loadableStudyPatternTransformationService.getCargoPriorityGridMoreTableColumn();
    this.cargoTobeLoadedColumns = this.loadableStudyPatternTransformationService.getCargotobeLoadedDatatableColumns();
    this.updateCargoTobeLoadedData();
    this.updateTankLIst();
    this.updateBallastTankData()
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
        loadable.apiTemp = loadable.estimatedAPI + (loadable.estimatedTemp ? "/" + loadable.estimatedTemp : '');
        loadable.minMaxTolerance = loadable.minTolerence + (loadable.maxTolerence ? "/" + loadable.maxTolerence : '');
        loadable.differencePercentage = loadable.differencePercentage ? loadable.differencePercentage + '%' : '';
        loadable.grade = this.fingCargo(loadable)
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

}
