import { Component, Input, OnInit } from '@angular/core';
import { ICargoTank, ITankOptions, TANKTYPE } from '../../../core/models/common.model';
import { ICargoTankDetail } from '../../models/loadable-plan.model';

/**
 * Component class of stowage section
 *
 * @export
 * @class StowageComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-stowage',
  templateUrl: './stowage.component.html',
  styleUrls: ['./stowage.component.scss']
})
export class StowageComponent implements OnInit {

  @Input()
  get cargoTanks(): ICargoTank[][] {
    return this._cargoTanks;
  }
  set cargoTanks(tanks: ICargoTank[][]) {
    this._cargoTanks = tanks;
  }

  @Input()
  get cargoTankDetails(): ICargoTankDetail[] {
    return this._cargoTankDetails;
  }
  set cargoTankDetails(value: ICargoTankDetail[]) {
    this._cargoTankDetails = value;
  }

  readonly tankType = TANKTYPE;
  selectedTab = TANKTYPE.CARGO;
  showGrid = false;
  columns: any[];
  value: any[];
  cargoTankOptions: ITankOptions = { isFullyFilled: false, showCommodityName: true, showVolume: true, showWeight: true, showUllage: true, showFillingPercentage: true, class: 'loadable-plan-stowage', fillingPercentageField: 'fillingRatio', volumeField: 'observedBarrels', volumeUnit: 'BBLS', weightField: 'weight', weightUnit: 'MT', ullageField: 'correctedUllage', ullageUnit: 'M'};

  private _cargoTanks: ICargoTank[][];
  private _cargoTankDetails: ICargoTankDetail[];

  constructor() { }

  ngOnInit(): void {
    this.columns = [
      { field: 'year', header: 'GRADE' },
      { field: 'year', header: 'ABBREV' },
      { field: 'year', header: 'RDG ULG' },
      { field: 'year', header: 'OBS.M3' },
      { field: 'year', header: 'OBS.BBLS' },
      { field: 'year', header: 'M/T' },
      { field: 'year', header: '%' },
      { field: 'year', header: 'API' },
      { field: 'year', header: 'DEG.F' }
    ];
  }

  /**
   * Handler for tab click
   *
   * @param {TANKTYPE} selectedTab
   * @memberof StowageComponent
   */
  onTabClick(selectedTab: TANKTYPE) {
    this.selectedTab = selectedTab;
  }

  /**
   * Method to toggle visibility of stoage details table
   *
   * @memberof StowageComponent
   */
  toggleGridView() {
    this.showGrid = !this.showGrid;
  }
}
