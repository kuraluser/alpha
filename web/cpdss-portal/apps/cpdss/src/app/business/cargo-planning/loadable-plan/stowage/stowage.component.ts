import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AppConfigurationService } from '../../../../shared/services/app-configuration/app-configuration.service';
import { DATATABLE_EDITMODE } from '../../../../shared/components/datatable/datatable.model';
import { IBallastStowageDetails, IBallastTank, ICargoTank, ITankOptions, TANKTYPE } from '../../../core/models/common.model';
import { numberValidator } from '../../directives/validator/number-validator.directive';
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

  @Input() vesselId: number;

  @Input() voyageId: number;

  @Input() loadableStudyId: number;

  @Input() loadablePatternId: number;

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
    this.initLoadablePlanForm();
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

  @Input()
  set ballastDetails(value: IBallastStowageDetails[]) {
    this._ballastDetails = value;
  }

  get ballastDetails(): IBallastStowageDetails[] {
    return this._ballastDetails;
  }

  get openSaveStowagePopup(): boolean {
    return this._openSaveStowagePopup;
  }
  set openSaveStowagePopup(openSaveStowagePopup: boolean) {
    this._openSaveStowagePopup = openSaveStowagePopup;
  }

  readonly tankType = TANKTYPE;
  loadablePlanForm: FormGroup;
  editMode: DATATABLE_EDITMODE = null;
  selectedTab = TANKTYPE.CARGO;
  showGrid = false;
  cargoGridColumns: any[];
  cargoTankOptions: ITankOptions = { isFullyFilled: false, showCommodityName: true, showVolume: true, showWeight: true, showUllage: true, showFillingPercentage: true, class: 'loadable-plan-stowage', fillingPercentageField: 'fillingRatio', volumeField: 'observedBarrels', volumeUnit: 'BBLS', weightField: 'weight', weightUnit: AppConfigurationService.settings.baseUnit, ullageField: 'correctedUllage', ullageUnit: 'CM', showTooltip: true, commodityNameField: 'cargoAbbreviation', showDensity: true, densityField: 'api' };
  ballastTankOptions: ITankOptions = { isFullyFilled: false, showUllage: true, showFillingPercentage: true, class: 'loadable-plan-stowage', fillingPercentageField: 'percentage', ullageField: 'correctedLevel', ullageUnit: 'CM', showTooltip: true, weightField: 'metricTon', weightUnit: AppConfigurationService.settings.baseUnit, showDensity: true, densityField: 'sg' };


  private _cargoTanks: ICargoTank[][];
  private _rearBallastTanks: IBallastTank[][];
  private _centerBallastTanks: IBallastTank[][];
  private _frontBallastTanks: IBallastTank[][];
  private _cargoTankDetails: ICargoTankDetailValueObject[];
  private _ballastDetails: IBallastStowageDetails[];
  private _openSaveStowagePopup = false;

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
  changeGridToEditMode() {
    this.editMode = DATATABLE_EDITMODE.CELL;
  }

  /**
   * Method for opening save stowage popup
   *
   * @memberof StowageComponent
   */
  saveStowage() {
    this.editMode = null;
    this.openSaveStowagePopup = true;
  }

  /**
   * Initialize loadable plan form
   *
   * @private
   * @memberof StowageComponent
   */
  private initLoadablePlanForm() {
    const cargoTankDetailsArray = this.cargoTankDetails?.map(cargo => this.initCargoTankFormGroup(cargo));
    this.loadablePlanForm = this.fb.group({
      cargoTanks: this.fb.group({
        dataTable: this.fb.array([...cargoTankDetailsArray])
      }),
      comment: this.fb.control(null, [Validators.required, Validators.maxLength(100)])
    });
  }

  /**
   * Initialize cargo tank form group
   *
   * @private
   * @param {ICargoTankDetailValueObject} cargo
   * @returns {FormGroup}
   * @memberof StowageComponent
   */
  private initCargoTankFormGroup(cargo: ICargoTankDetailValueObject): FormGroup {
    return this.fb.group({
      id: this.fb.control(cargo?.id),
      tankId: this.fb.control(cargo?.tankId),
      cargoAbbreviation: this.fb.control(cargo?.cargoAbbreviation),
      weight: this.fb.control(cargo?.weight?.value),
      correctedUllage: this.fb.control(cargo?.correctedUllage?.value),
      fillingRatio: this.fb.control(cargo?.fillingRatio?.value),
      tankName: this.fb.control(cargo?.tankName),
      rdgUllage: this.fb.control(cargo?.rdgUllage?.value, [Validators.required, numberValidator(2, 3, false)]),
      correctionFactor: this.fb.control(cargo?.correctionFactor?.value),
      observedM3: this.fb.control(cargo?.observedM3?.value),
      observedBarrels: this.fb.control(cargo?.observedBarrels?.value),
      observedBarrelsAt60: this.fb.control(cargo?.observedBarrelsAt60?.value),
      api: this.fb.control(cargo?.api),
      temperature: this.fb.control(cargo?.temperature),
    });
  }
}
