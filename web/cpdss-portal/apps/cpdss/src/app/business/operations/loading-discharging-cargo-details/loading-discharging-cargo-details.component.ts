import { Component, Input, OnInit, OnDestroy } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { QUANTITY_UNIT } from '../../../shared/models/common.model';
import { QuantityPipe } from '../../../shared/pipes/quantity/quantity.pipe';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { IShipCargoTank, ITankOptions, ICargoQuantities, ICargo, OPERATIONS } from '../../core/models/common.model';
import { ICargoVesselTankDetails } from '../models/loading-discharging.model';
import { LoadingDischargingTransformationService } from '../services/loading-discharging-transformation.service';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { DecimalPipe } from '@angular/common';

/**
 * Component class for loading discharging berth component
 *
 * @export
 * @class LoadingDischargingCargoDetailsComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-loading-discharging-cargo-details',
  templateUrl: './loading-discharging-cargo-details.component.html',
  styleUrls: ['./loading-discharging-cargo-details.component.scss']
})

export class LoadingDischargingCargoDetailsComponent implements OnInit, OnDestroy {
  @Input() cargos: ICargo[];
  @Input() prevQuantitySelectedUnit: QUANTITY_UNIT;
  @Input() operation: OPERATIONS;
  @Input() loadableStudyName: string;
  @Input() dischargeStudyName: string;

  @Input() get currentQuantitySelectedUnit(): QUANTITY_UNIT {
    return this._currentQuantitySelectedUnit;
  }

  set currentQuantitySelectedUnit(value: QUANTITY_UNIT) {
    this.prevQuantitySelectedUnit = this.currentQuantitySelectedUnit ?? AppConfigurationService.settings.baseUnit;
    this._currentQuantitySelectedUnit = value;
    this.cargoTankOptions.weightUnit = value;
  }
  @Input() get cargoVesselTankDetails(): ICargoVesselTankDetails {
    return this._cargoVesselTankDetails;
  }

  set cargoVesselTankDetails(cargoVesselTankDetails: ICargoVesselTankDetails) {
    this._cargoVesselTankDetails = cargoVesselTankDetails;
    this.init();
  }

  readonly OPERATIONS = OPERATIONS;
  cargoTanks: IShipCargoTank[][];
  cargoConditions: any = [];
  cargoQuantities: ICargoQuantities[];
  cargoTankOptions: ITankOptions = { isFullyFilled: false, showTooltip: true, isSelectable: false, showFillingPercentage: true, weightField: 'plannedWeight', showWeight: true, weightUnit: 'MT', commodityNameField: 'abbreviation', ullageField: 'correctedUllage', ullageUnit: AppConfigurationService?.settings?.ullageUnit, densityField: 'api', fillingPercentageField: 'fillingRatio' }
  showComminglePopup = false;
  commingleDetails: any[];

  private _currentQuantitySelectedUnit: QUANTITY_UNIT;
  private _cargoVesselTankDetails: ICargoVesselTankDetails;
  private ngUnsubscribe: Subject<any> = new Subject();

  constructor(
    private loadingDischargingTransformationService: LoadingDischargingTransformationService,
    private quantityPipe: QuantityPipe,
    private translateService: TranslateService,
    private _decimalPipe: DecimalPipe,
  ) { }

  async ngOnInit(): Promise<void> {
  }

  ngOnDestroy() {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  /**
  * Method to initialise
  *
  * @memberof LoadingDischargingCargoDetailsComponent
  */
  init(){
    this.prevQuantitySelectedUnit = AppConfigurationService.settings.baseUnit;
    this.cargoConditions = this.cargoVesselTankDetails?.cargoConditions;
    this.cargoQuantities = this.cargoVesselTankDetails?.cargoQuantities;

    this.loadingDischargingTransformationService.unitChange$.pipe(takeUntil(this.ngUnsubscribe)).subscribe((res) => {
      this.prevQuantitySelectedUnit = this.currentQuantitySelectedUnit ?? AppConfigurationService.settings.baseUnit
      this.currentQuantitySelectedUnit = <QUANTITY_UNIT>localStorage.getItem('unit');
      this.convertIntoSelectedUnit();
    })
    this.convertIntoSelectedUnit();
  }

  /**
   * Method to convert data into selected unit.
   *
   * @memberof LoadingDischargingCargoDetailsComponent
   */
  convertIntoSelectedUnit() {
    this.cargoTanks = this.loadingDischargingTransformationService.formatCargoTanks(this.cargoVesselTankDetails?.cargoTanks, this.cargoVesselTankDetails?.cargoQuantities, this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit);
  }

  /**
   * Handler for show commingle pop up
   * @param {Event} data
   * @memberof LoadingDischargingCargoDetailsComponent
   */
   showCommingle(data) {
    this.cargoVesselTankDetails;
    const commingleDetails = {...data};
    let tankData;
    this.cargoTanks.map(row => {
      row.map(tank => {
        if (tank.id === data.tankId) {
          tankData = tank;
        }
      })
    });
    commingleDetails.tankName = tankData ? tankData.name : '';
    commingleDetails.cargoQuantity = this._decimalPipe.transform(commingleDetails.cargo1Mt, '1.2-2') + '\n' + this._decimalPipe.transform(commingleDetails.cargo2Mt, '1.2-2');
    commingleDetails.cargoPercentage = commingleDetails.cargo1Abbreviation + '-' + ((commingleDetails.cargo1Mt / commingleDetails.plannedWeight) * 100).toFixed(2) + '%\n' + commingleDetails.cargo2Abbreviation + '-' + ((commingleDetails.cargo2Mt / commingleDetails.plannedWeight) * 100).toFixed(2) + '%';
    commingleDetails.quantity = this._decimalPipe.transform(commingleDetails.plannedWeight, '1.2-2');
    this.commingleDetails = [commingleDetails];
    this.showComminglePopup = true;
  }
}
