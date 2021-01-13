import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { DATATABLE_EDITMODE } from '../../../../shared/components/datatable/datatable.model';
import { IBallastStowageDetails, IBallastTank, ICargoTank, ITankOptions, TANKTYPE } from '../../../core/models/common.model';
import { ICargoTankDetailValueObject } from '../../models/loadable-plan.model';
import { LoadablePlanTransformationService } from '../../services/loadable-plan-transformation.service';

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
  get cargoTankDetails(): ICargoTankDetailValueObject[] {
    return this._cargoTankDetails;
  }
  set cargoTankDetails(value: ICargoTankDetailValueObject[]) {
    this._cargoTankDetails = value;
  }

  @Input()
  get rearBallastTanks(): IBallastTank[][] {
    return this._rearBallastTanks;
  }
  set rearBallastTanks(tanks: IBallastTank[][]) {
    this._rearBallastTanks = tanks;
  }

  @Input()
  get centerBallastTanks(): IBallastTank[][] {
    return this._centerBallastTanks;
  }
  set centerBallastTanks(tanks: IBallastTank[][]) {
    this._centerBallastTanks = tanks;
  }

  @Input()
  get frontBallastTanks(): IBallastTank[][] {
    return this._frontBallastTanks;
  }
  set frontBallastTanks(tanks: IBallastTank[][]) {
    this._frontBallastTanks = tanks;
  }

  @Input() loadablePlanForm: FormBuilder;

  @Input() form: FormGroup;

  @Input()
  set ballastDetails(value: IBallastStowageDetails[]) {
    this._ballastDetails = value;
  }

  get ballastDetails(): IBallastStowageDetails[] {
    return this._ballastDetails;
  }

  readonly tankType = TANKTYPE;
  editMode: DATATABLE_EDITMODE = null;
  selectedTab = TANKTYPE.CARGO;
  showGrid = false;
  cargoGridColumns: any[];
  cargoTankOptions: ITankOptions = { isFullyFilled: false, showCommodityName: true, showVolume: true, showWeight: true, showUllage: true, showFillingPercentage: true, class: 'loadable-plan-stowage', fillingPercentageField: 'fillingRatio', volumeField: 'observedBarrels', volumeUnit: 'BBLS', weightField: 'weight', weightUnit: 'MT', ullageField: 'correctedUllage', ullageUnit: 'CM' };
  ballastTankOptions: ITankOptions = { isFullyFilled: false, showUllage: true, showFillingPercentage: true, class: 'loadable-plan-stowage', fillingPercentageField: 'percentage', ullageField: 'correctedLevel', ullageUnit: 'CM' };


  private _cargoTanks: ICargoTank[][];
  private _rearBallastTanks: IBallastTank[][];
  private _centerBallastTanks: IBallastTank[][];
  private _frontBallastTanks: IBallastTank[][];
  private _cargoTankDetails: ICargoTankDetailValueObject[];
  private _ballastDetails: IBallastStowageDetails[];

  constructor(private loadablePlanTransformationService: LoadablePlanTransformationService, private fb: FormBuilder) { }

  ngOnInit(): void {
    this.cargoGridColumns = this.loadablePlanTransformationService.getCargoDatatableColumns();
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

  /**
   * Method for toggling edit mode of cargo tank details grid
   *
   * @memberof StowageComponent
   */
  toggleEditMode() {
    this.editMode = this.editMode === null ? DATATABLE_EDITMODE.CELL : null;
  }
}
