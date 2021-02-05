import { Component, Input, OnInit } from '@angular/core';
import { ITankOptions, TANKTYPE } from '../../core/models/common.model';
import { VoyageStatusTransformationService } from '../services/voyage-status-transformation.service';
import { IBallastQuantities, ICargoQuantities, IShipBallastTank, IShipBunkerTank, IShipCargoTank, IVoyageDetails } from '../models/voyage-status.model';
import { IVoyageStatus } from '../models/voyage-status.model';
import { OHQ_MODE } from '../../cargo-planning/models/cargo-planning.model';
import { IDataTableColumn } from '../../../shared/components/datatable/datatable.model';
import { QUANTITY_UNIT } from '../../../shared/models/common.model';

/**
 * Component class of ship landing screen
 *
 * @export
 * @class ShipLandingTanksComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-ship-landing-tanks',
  templateUrl: './ship-landing-tanks.component.html',
  styleUrls: ['./ship-landing-tanks.component.scss']
})
export class ShipLandingTanksComponent implements OnInit {

  @Input() vesselId: number;
  @Input() voyageId: number;
  @Input() loadableStudyId: number;
  @Input() shipLandingTanks: IVoyageStatus;
  @Input() selectedPortDetails: IVoyageDetails;
  @Input() get currentQuantitySelectedUnit() : QUANTITY_UNIT {
    return this._currentQuantitySelectedUnit;
  }

  set currentQuantitySelectedUnit(value: QUANTITY_UNIT) {
    this.prevQuantitySelectedUnit = this.currentQuantitySelectedUnit;
    this._currentQuantitySelectedUnit = value;
    if (this.prevQuantitySelectedUnit) {
      // this.convertQuantityToSelectedUnit();
    }
  }

  cargoTanks: IShipCargoTank[][];
  rearBallastTanks: IShipBallastTank[][];
  frontBallastTanks: IShipBallastTank[][];
  centerBallastTanks: IShipBallastTank[][];
  bunkerTanks: IShipBunkerTank[][];
  rearBunkerTanks: IShipBunkerTank[][];
  cargoColumns: IDataTableColumn[];
  ballastColumns: IDataTableColumn[];
  cargoQuantities: ICargoQuantities[];
  ballastQuantities: IBallastQuantities[];
  selectedTab = TANKTYPE.CARGO;
  viewAll = false;
  prevQuantitySelectedUnit: QUANTITY_UNIT;

  readonly tankType = TANKTYPE;

  cargoTankOptions: ITankOptions = { isBullet: true, ullageField: 'correctedUllage', ullageUnit: 'CM' };
  ballastTankOptions: ITankOptions = { showFillingPercentage: true };
  
  private _currentQuantitySelectedUnit: QUANTITY_UNIT;

  constructor(private voyageStatusTransformationService: VoyageStatusTransformationService) { }

  ngOnInit(): void {
    this.cargoColumns = this.voyageStatusTransformationService.getCargoTankDatatableColumns();
    this.ballastColumns = this.voyageStatusTransformationService.getBallastTankDatatableColumns();
    this.getShipLandingTanks();
  }

  /**
   * Handler for tab click
   *
   * @param {TANKTYPE} selectedTab
   * @memberof ShipLandingTanksComponent
   */
  onTabClick(selectedTab: TANKTYPE) {
    this.selectedTab = selectedTab;
  }

  /**
 * initialise tanks data
 *
 * @memberof ShipLandingTanksComponent
 */
  async getShipLandingTanks() {
    const mode = this.selectedPortDetails?.operationType === 'ARR' ? OHQ_MODE.ARRIVAL : OHQ_MODE.DEPARTURE;
    this.cargoQuantities = this.shipLandingTanks?.cargoQuantities ?? [];
    this.ballastQuantities = this.shipLandingTanks?.ballastQuantities ?? [];
    this.bunkerTanks = this.voyageStatusTransformationService.formatBunkerTanks(this.shipLandingTanks?.bunkerTanks, this.shipLandingTanks?.bunkerQuantities, mode);
    this.rearBunkerTanks = this.voyageStatusTransformationService.formatBunkerTanks(this.shipLandingTanks?.bunkerRearTanks, this.shipLandingTanks?.bunkerQuantities, mode);
    this.cargoTanks = this.voyageStatusTransformationService.formatCargoTanks(this.shipLandingTanks?.cargoTanks, this.shipLandingTanks?.cargoQuantities);
    this.rearBallastTanks = this.voyageStatusTransformationService.formatBallastTanks(this.shipLandingTanks?.ballastRearTanks, this.shipLandingTanks?.ballastQuantities);
    this.centerBallastTanks = this.voyageStatusTransformationService.formatBallastTanks(this.shipLandingTanks?.ballastCenterTanks, this.shipLandingTanks?.ballastQuantities);
    this.frontBallastTanks = this.voyageStatusTransformationService.formatBallastTanks(this.shipLandingTanks?.ballastFrontTanks, this.shipLandingTanks?.ballastQuantities);
  }



}
