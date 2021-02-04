import { Component, Input, OnInit } from '@angular/core';
import { ITankOptions, TANKTYPE } from '../../core/models/common.model';
import { VoyageApiService } from '../services/voyage-api.service';
import { VoyageStatusTransformationService } from '../services/voyage-status-transformation.service';
import { IBallastQuantities, ICargoQuantities, ICargoQuantityValueObject, IBallastQuantityValueObject, IShipBallastTank, IShipBunkerTank, IShipCargoTank, IVoyageDetails } from '../models/voyage-status.model';
import { IVoyageStatus } from '../models/voyage-status.model';
import { OHQ_MODE } from '../../cargo-planning/models/cargo-planning.model';
import { IDataTableColumn } from '../../../shared/components/datatable/datatable.model';
import { FormBuilder, FormGroup } from '@angular/forms';

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
  cargoQuantityForm: FormGroup;
  ballastQuantityForm: FormGroup;
  selectedTab = TANKTYPE.CARGO;
  cargoQuantitiesGrid: ICargoQuantityValueObject[];
  ballastQuantitiesGrid: IBallastQuantityValueObject[];
  viewAll = false;

  readonly tankType = TANKTYPE;

  cargoTankOptions: ITankOptions = { isBullet: true, ullageField: 'correctedUllage', ullageUnit: 'CM' };
  ballastTankOptions: ITankOptions = { showFillingPercentage: true };

  constructor(private voyageStatusTransformationService: VoyageStatusTransformationService, private fb: FormBuilder) { }

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
    this.initFormgroup();
  }

  /**
* Method for initialse cargo and ballast form
*
* @private
* @memberof ShipLandingTanksComponent
*/
  initFormgroup() {
    const _cargoQuantities = this.cargoQuantities?.map((item) => {
      const cargoData = this.voyageStatusTransformationService.getCargoQuantityAsValueObject(item, false);
      return cargoData;
    });
    const cargoQuantityArray = _cargoQuantities.map((cargoQuantity) =>
      this.initCargoQuantityFormGroup(cargoQuantity)
    );
    this.cargoQuantityForm = this.fb.group({
      dataTable: this.fb.array([...cargoQuantityArray])
    });
    this.cargoQuantitiesGrid = _cargoQuantities;
console.log("this.ballastQuantities", this.ballastQuantities)
    const _ballastQuantities = this.ballastQuantities?.map((item) => {
      const ballastData = this.voyageStatusTransformationService.getBallastQuantityAsValueObject(item, false);
      return ballastData;
    });
    const ballastQuantityArray = _ballastQuantities?.map((ballastQuantity) =>
      this.initBallastQuantityFormGroup(ballastQuantity)
    );
    this.ballastQuantityForm = this.fb.group({
      dataTable: this.fb.array([...ballastQuantityArray])
    });
    this.ballastQuantitiesGrid = _ballastQuantities;
  }

  /**
 * Method for initializing cargo qauantity  row
 *
 * @private
 * @param {ICargoQuantityValueObject} cargoQuantity
 * @returns
 * @memberof ShipLandingTanksComponent
 */
  private initCargoQuantityFormGroup(cargoQuantity: ICargoQuantityValueObject) {
    return this.fb.group({
      tankName: this.fb.control(cargoQuantity.tankName),
      abbreviation: this.fb.control(cargoQuantity.abbreviation),
      correctedUllage: this.fb.control(cargoQuantity.correctedUllage),
      plannedWeight: this.fb.control(cargoQuantity.plannedWeight),
      actualWeight: this.fb.control(cargoQuantity.actualWeight)
    })
  }
  /**
 * Method for initializing ballast qauantity  row
 *
 * @private
 * @param {IBallastQuantityValueObject} cargoQuantity
 * @returns
 * @memberof ShipLandingTanksComponent
 */
  private initBallastQuantityFormGroup(cargoQuantity: IBallastQuantityValueObject) {
    return this.fb.group({
      tankName: this.fb.control(cargoQuantity.tankName),
      sg: this.fb.control(cargoQuantity.sg),
      correctedUllage: this.fb.control(cargoQuantity.correctedUllage),
      plannedWeight: this.fb.control(cargoQuantity.plannedWeight),
      actualWeight: this.fb.control(cargoQuantity.actualWeight)
    })
  }
}
