import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { AppConfigurationService } from '../../../../shared/services/app-configuration/app-configuration.service';
import { DATATABLE_EDITMODE } from '../../../../shared/components/datatable/datatable.model';
import { IBallastStowageDetails, IBallastTank, ICargoTank, ITankOptions, LOADABLE_STUDY_STATUS, TANKTYPE, Voyage, VOYAGE_STATUS } from '../../../core/models/common.model';
import { numberValidator } from '../../directives/validator/number-validator.directive';
import { ICargoTankDetailValueObject, IPortsEvent, IUpdatedBallastUllageResponse, IUpdateUllageModel, IUpdatedUllageResponse ,IUpdatedRdgLevelResponse ,IValidateAndSaveStowage , IBallastTankDetailValueObject, IUpdateBallastUllagegModel , VALIDATION_AND_SAVE_STATUS } from '../../models/loadable-plan.model';
import { LoadablePlanTransformationService } from '../../services/loadable-plan-transformation.service';
import { PermissionsService } from '../../../../shared/services/permissions/permissions.service';
import { LoadableStudy } from '../../models/loadable-study-list.model';
import { NgxSpinnerService } from 'ngx-spinner';
import { LoadablePlanApiService } from '../../services/loadable-plan-api.service';
import { tankCapacityValidator } from '../directives/tankCapacityValidator.directive';
import { QuantityPipe } from '../../../../shared/pipes/quantity/quantity.pipe';
import { QUANTITY_UNIT , IValidateAndSaveResponse } from '../../../../shared/models/common.model';
import { DecimalPipe } from '@angular/common';
import { TranslateService } from '@ngx-translate/core';
import { MessageService } from 'primeng/api';
import { IErrorMessage } from '../../../core/components/error-log-popup/error-log-popup.model';

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

  @Input() voyage: Voyage;

  @Input() loadableStudyId: number;

  @Input()
  get loadableStudy(): LoadableStudy {
    return this._loadableStudy;
  }
  set loadableStudy(value: LoadableStudy) {
    this._loadableStudy = value;
    this.isEditable = ![LOADABLE_STUDY_STATUS.PLAN_CONFIRMED].includes(this.loadableStudy?.statusId) && ![VOYAGE_STATUS.CLOSE].includes(this.voyage?.statusId);
  }

  @Input()
  get loadableStudyStatus(): boolean {
    return this._loadableStudyStatus;
  }

  set loadableStudyStatus(value: boolean) {
    this._loadableStudyStatus = value;
  }

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

  @Input() set loadablePatternValidationStatus(value: number) {
    this._loadablePatternValidationStatus = value;
    [VALIDATION_AND_SAVE_STATUS.LOADABLE_PLAN_STARTED].includes(value) ? (this.isStowageEditable = true,this.editMode = null,this.buttonStatus = 0) : this.isStowageEditable = false;
  }

  get loadablePatternValidationStatus(): number {
    return this._loadablePatternValidationStatus;
  }

  @Input()
  set ballastDetails(ballastStowageDetails: IBallastStowageDetails[]) {
    this.ballastTankDetails = ballastStowageDetails.map((ballast: IBallastStowageDetails) => this.loadablePlanTransformationService.getBallastTankDetailAsValueObject(ballast));
    this.initLoadablePlanBallastForm();
  }

  get openSaveStowagePopup(): boolean {
    return this._openSaveStowagePopup;
  }
  set openSaveStowagePopup(openSaveStowagePopup: boolean) {
    this._openSaveStowagePopup = openSaveStowagePopup;
  }

  @Input()
  set errorMessageDisplay(errorPopup: boolean) {
    this.errorPopup = errorPopup;
  }

  @Input()
  get errorMessage(): IErrorMessage[] {
    return this._errorMessage;
  }
  set errorMessage(errorMessage: IErrorMessage[]) {
    this._errorMessage = errorMessage;
  }

  @Output() errorMessageDisplayChange = new EventEmitter<boolean>()

  readonly tankType = TANKTYPE;
  readonly validateAndSaveStatus = VALIDATION_AND_SAVE_STATUS;
  loadablePlanForm: FormGroup;
  editMode: DATATABLE_EDITMODE = null;
  selectedTab = TANKTYPE.CARGO;
  showGrid = false;
  cargoGridColumns: any[];
  cargoTankOptions: ITankOptions = { isFullyFilled: false, showCommodityName: true, showVolume: true, showWeight: true, showUllage: true, showFillingPercentage: true, class: 'loadable-plan-stowage', fillingPercentageField: 'fillingRatio', volumeField: 'observedBarrelsAt60', volumeUnit: 'BBLS', weightField: 'weight', weightUnit: AppConfigurationService.settings.baseUnit, ullageField: 'correctedUllage', ullageUnit: 'CM', showTooltip: true, commodityNameField: 'cargoAbbreviation', showDensity: true, densityField: 'api' };
  ballastTankOptions: ITankOptions = { isFullyFilled: false, showUllage: true, showFillingPercentage: true, class: 'loadable-plan-stowage', fillingPercentageField: 'percentage', ullageField: 'correctedLevel', ullageUnit: 'CM', showTooltip: true, weightField: 'metricTon', weightUnit: AppConfigurationService.settings.baseUnit, showDensity: true, densityField: 'sg' };
  isPermissionAvaliable: boolean;
  isEditable: boolean;
  buttonStatus: number;
  initialCargoTankDetails: ICargoTankDetailValueObject[];
  validateAndSaveProcessing: boolean;
  ballastTankDetails: IBallastTankDetailValueObject[];
  initBallastTankDetails: IBallastTankDetailValueObject[];
  isStowageEditable: boolean;
  errorPopup: boolean;
  

  private _cargoTanks: ICargoTank[][];
  private _rearBallastTanks: IBallastTank[][];
  private _centerBallastTanks: IBallastTank[][];
  private _frontBallastTanks: IBallastTank[][];
  private _cargoTankDetails: ICargoTankDetailValueObject[];
  private _ballastDetails: IBallastStowageDetails[];
  private _openSaveStowagePopup = false;
  private _loadableStudy: LoadableStudy;
  private _loadableStudyStatus: boolean;
  private _loadablePatternValidationStatus: number;
  private _errorMessage: IErrorMessage[];
  private quantityPipe: QuantityPipe = new QuantityPipe();

  constructor(
    private loadablePlanTransformationService: LoadablePlanTransformationService,
    private fb: FormBuilder,
    private permissionsService: PermissionsService,
    private ngxSpinnerService: NgxSpinnerService,
    private loadablePlanApiService: LoadablePlanApiService,
    private decimalPipe: DecimalPipe,
    private translateService: TranslateService,
    private messageService: MessageService) { }

  ngOnInit(): void {
    this.loadablePlanForm = this.fb.group({
      cargoTanks: this.fb.group({
        dataTable: this.fb.array([])
      }),
      ballastTanks: this.fb.group({
        dataTable: this.fb.array([])
      })
    })
    this.initLoadablePlanForm();
    this.initLoadablePlanBallastForm();
    this.buttonStatus = 0;
    this.isPermissionAvaliable = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['EditStowage'], false).view;
    this.cargoGridColumns = this.loadablePlanTransformationService.getCargoDatatableColumns();
    this.loadablePlanTransformationService.savedComments$.subscribe(() => {
      this.validateAndSave();
    })
  }

  /**
   * Handler for tab click
   *
   * @param {TANKTYPE} selectedTab
   * @memberof StowageComponent
   */
  onTabClick(selectedTab: TANKTYPE) {
    this.selectedTab = selectedTab;
    this.buttonStatus = 0;
    this.editMode = null;
  }

  /**
   * Method to toggle visibility of stoage details table
   *
   * @memberof StowageComponent
   */
  toggleGridView() {
    this.showGrid = !this.showGrid;
    this.buttonStatus = 0;
    this.editMode = null;
  }

  /**
   * Method for toggling edit mode of cargo tank details grid
   *
   * @memberof StowageComponent
   */
  changeGridToEditMode() {
    this.editMode = DATATABLE_EDITMODE.CELL;
    this.buttonStatus = 1;
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
    if (this.getFormGroup('cargoTanks') && this.cargoTankDetails) {
      const loadablePlanForm = this.getFormGroup('cargoTanks');
      const cargoDataTable = loadablePlanForm.get('dataTable') as FormArray;
      this.cargoTankDetails?.map((cargo) => {
        cargoDataTable.push(this.initCargoTankFormGroup(cargo))
      });
      this.initialCargoTankDetails = JSON.parse(JSON.stringify(this.cargoTankDetails));
    }
  }

  /**
   * get form group details
   *
   * @memberof StowageComponent
   */
  public getFormGroup(formGroupName: string) {
    return this.loadablePlanForm?.get(formGroupName) as FormGroup;
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
      rdgUllage: this.fb.control(cargo?.rdgUllage?.value, [Validators.required, numberValidator(3, 3, false), tankCapacityValidator('observedM3', cargo.fullCapacityCubm , 'rdgUllage')]),
      correctionFactor: this.fb.control(cargo?.correctionFactor?.value),
      observedM3: this.fb.control(cargo?.observedM3?.value, [tankCapacityValidator('observedM3', cargo.fullCapacityCubm , 'rdgUllage')]),
      observedBarrels: this.fb.control(cargo?.observedBarrels?.value),
      observedBarrelsAt60: this.fb.control(cargo?.observedBarrelsAt60?.value),
      api: this.fb.control(cargo?.api),
      temperature: this.fb.control(cargo?.temperature),
    });
  }



  /**
   * Event handler for edit complete event
   *
   * @param {IPortsEvent} event
   * @memberof StowageComponent
   */
  async onEditComplete(event: IPortsEvent) {
    setTimeout(() => {
      this.stowageDataEdit(event);
    }, 250)
  }

  /**
   * Event handler for edit complete event for stowage
   *
   * @param {IPortsEvent} event
   * @memberof StowageComponent
   */
  async stowageDataEdit(event: IPortsEvent) {
    if (this.editMode && !this.openSaveStowagePopup) {
      if (event.field === 'rdgUllage') {
        this.ngxSpinnerService.show();
        const data: IUpdateUllageModel = {
          id: event.data.id,
          tankId: event.data.tankId,
          correctedUllage: event.data.rdgUllage['_value'],
          isBallast: false
        }
        const translationKeys = await this.translateService.get(['LOADABLE_PLAN_ULLAGE_UPDATED', 'LOADABLE_PLAN_ULLAGE_UPDATED_DETAILS']).toPromise();

        const result: IUpdatedUllageResponse = await this.loadablePlanApiService.updateUllage(this.vesselId, this.voyageId, this.loadableStudyId, this.loadablePatternId, data).toPromise();
        
        if (result.responseStatus.status === '200') {
          const unitConvertedTankDetails = {
            observedM3: this.quantityPipe.transform(result.quantityMt, QUANTITY_UNIT.MT, QUANTITY_UNIT.KL, event.data.api['_value'], event.data.temperature['_value']),
            observedBarrelsAt60: this.quantityPipe.transform(result.quantityMt, QUANTITY_UNIT.MT, QUANTITY_UNIT.BBLS, event.data.api['_value'], event.data.temperature['_value']),
            observedBarrels: this.quantityPipe.transform(result.quantityMt, QUANTITY_UNIT.MT, QUANTITY_UNIT.OBSBBLS, event.data.api['_value'], event.data.temperature['_value'])
          }
          this.updateField(event.index, 'correctionFactor', Number(result.correctionFactor), 'cargoTanks');
          this.updateField(event.index, 'correctedUllage', Number(result.correctedUllage), 'cargoTanks');
          this.updateField(event.index, 'weight', Number(result.quantityMt), 'cargoTanks');
          this.updateField(event.index, 'observedM3', unitConvertedTankDetails.observedM3, 'cargoTanks');
          this.updateField(event.index, 'observedBarrelsAt60', unitConvertedTankDetails.observedBarrelsAt60, 'cargoTanks');
          this.updateField(event.index, 'observedBarrels', unitConvertedTankDetails.observedBarrels, 'cargoTanks');
          this.cargoTankDetails[event.index]['correctionFactor'].value = Number(result.correctionFactor);
          this.cargoTankDetails[event.index]['correctedUllage'].value = Number(result.correctedUllage);
          this.cargoTankDetails[event.index]['weight'].value = Number(result.quantityMt);
          this.cargoTankDetails[event.index]['observedM3'].value = unitConvertedTankDetails.observedM3;
          this.cargoTankDetails[event.index]['observedBarrelsAt60'].value = unitConvertedTankDetails.observedBarrelsAt60;
          this.cargoTankDetails[event.index]['observedBarrels'].value = unitConvertedTankDetails.observedBarrels;
          //TODO: Need to remove this if we get fillingRatio ration from api . Now integated with dummy values in api.
          const fillingRatio = this.decimalPipe.transform(this.loadablePlanTransformationService.calculatePercentage(Number(this.cargoTankDetails[event.index]['observedM3'].value), Number(this.cargoTankDetails[event.index]['fullCapacityCubm'])),'1.2-2');
          this.cargoTankDetails[event.index]['fillingRatio'].value = Number(result?.fillingRatio);
          this.initialCargoTankDetails = JSON.parse(JSON.stringify(this.cargoTankDetails));
          this.messageService.add({ severity: 'success', summary: translationKeys['LOADABLE_PLAN_ULLAGE_UPDATED'], detail: translationKeys['LOADABLE_PLAN_ULLAGE_UPDATED_DETAILS'] });
          this.ngxSpinnerService.hide();
        }
      }
    } else {
      this.updateField(event.index, event.field, this.initialCargoTankDetails[event.index][event.field]['_value'], 'cargoTanks');
      this.cargoTankDetails[event.index][event.field].value = this.initialCargoTankDetails[event.index][event.field]['_value'];
      this.ngxSpinnerService.hide();
    }
  }

  /**
   * Event handler for edit complete event for ballast
   *
   * @param {IPortsEvent} event
   * @memberof StowageComponent
   */
  async ballastStowageDataEdit(event: IPortsEvent) {
    const formControl = this.field(event.index, event.field, 'ballastTanks');
    if (formControl.valid && event.field === 'rdgLevel') {
      this.ngxSpinnerService.show();
      const data: IUpdateUllageModel = {
        id: event.data.id,
        tankId: event.data.tankId,
        correctedUllage: event.data.rdgLevel['_value'],
        isBallast: true
      }
      const translationKeys = await this.translateService.get(['LOADABLE_PLAN_ULLAGE_UPDATED', 'LOADABLE_PLAN_ULLAGE_UPDATED_DETAILS']).toPromise();
      const result: IUpdatedUllageResponse = await this.loadablePlanApiService.updateUllage(this.vesselId,this.voyageId,this.loadableStudyId,this.loadablePatternId,data).toPromise();
      if(result.responseStatus.status === '200') {
        this.ballastTankDetails[event.index]['correctedLevel'].value = result.correctedUllage;
        this.ballastTankDetails[event.index]['correctionFactor'].value =  result.correctionFactor;
        this.ballastTankDetails[event.index]['metricTon'].value =  result.quantityMt;
        //TODO: Need to remove this if we get fillingRatio ration from api . Now integated with dummy values in api.
        const fillingRatio = this.decimalPipe.transform(this.loadablePlanTransformationService.calculatePercentage(Number(this.ballastTankDetails[event.index]['cubicMeter'].value), Number(this.ballastTankDetails[event.index]['fullCapacityCubm'])),'1.2-2');
        this.ballastTankDetails[event.index]['percentage'].value = result.fillingRatio + '';
        const unitConvertedTankDetails = {
          observedM3: (Number(this.ballastTankDetails[event.index]['metricTon'].value) / Number(this.ballastTankDetails[event.index]['sg'].value)).toFixed(2)
        }
        this.ballastTankDetails[event.index]['cubicMeter'].value =  this.decimalPipe.transform(unitConvertedTankDetails.observedM3,'1.2-2');
        this.updateField(event.index, 'cubicMeter', this.ballastTankDetails[event.index]['cubicMeter'].value , 'ballastTanks');
        this.messageService.add({ severity: 'success', summary: translationKeys['LOADABLE_PLAN_ULLAGE_UPDATED'], detail: translationKeys['LOADABLE_PLAN_ULLAGE_UPDATED_DETAILS'] });
        this.ngxSpinnerService.hide();
      }
    }
  }

  /**
   * enable or disable commnets popup
   *
   * @param {boolean} status
   * @memberof StowageComponent
  */
  commentsPopup(status: boolean) {
    this.openSaveStowagePopup = status;
  }

  /**
   * validate and save
   *
   * @param {IPortsEvent} event
   * @memberof StowageComponent
  */
  async validateAndSave() {
    if(this.loadablePlanForm.valid) {
      this.validateAndSaveProcessing = true;
      this.ngxSpinnerService.show();
      const data: IValidateAndSaveStowage = {
        vesselId: this.vesselId,
        voyageId: this.voyageId,
        loadableStudyId: this.loadableStudyId,
        loadablePatternId: this.loadablePatternId,
        processId: null
      }
      const result: IValidateAndSaveResponse = await this.loadablePlanApiService.validateAndSave(this.vesselId, this.voyageId, this.loadableStudyId, this.loadablePatternId, {}).toPromise();
      if (result.responseStatus.status === '200') {
        data.processId = result.processId;
        navigator.serviceWorker.controller.postMessage({ type: 'validate-and-save', data});
      }
      this.ngxSpinnerService.hide();
    } else {
      this.loadablePlanForm.markAsDirty();
      this.loadablePlanForm.markAsTouched();
    }
  }

  
  /**
   * validate and save
   *
   * @param {IPortsEvent} event
   * @memberof StowageComponent
   */
  public cancelStowageEdit() {
    this.buttonStatus = 0;
    this.editMode = null;
  }

  /**
 * Method for fetching form fields
 *
 * @private
 * @param {number} formGroupIndex
 * @param {string} formControlName
 * @returns {FormControl}
 * @memberof StowageComponent
 */
  private field(formGroupIndex: number, formControlName: string, formGroupName: string): FormControl {
    const formControl = <FormControl>(<FormArray>this.getFormGroup(formGroupName).get('dataTable')).at(formGroupIndex).get(formControlName);
    return formControl;
  }

  /**
   * Method for updating form field
   *
   * @private
   * @param {number} index
   * @param {string} field
   * @param {*} value
   * @memberof StowageComponent
   */
  private updateField(index: number, field: string, value: any, formGroupName: string) {
    const control = this.field(index, field, formGroupName);
    control.setValue(value);
    control.markAsDirty();
    control.markAsTouched();
  }

  /**
   * Initialize ballast tank form group
   *
   * @private
   * @param {IBallastTankDetailValueObject} ballast
   * @returns {FormGroup}
   * @memberof StowageComponent
   */
  private initBallastTankFormGroup(ballast: IBallastTankDetailValueObject): FormGroup {
    return this.fb.group({
      id: this.fb.control(ballast?.id),
      cubicMeter: this.fb.control(ballast.cubicMeter.value , [tankCapacityValidator('cubicMeter', ballast.fullCapacityCubm,'rdgLevel')]),
      rdgLevel: this.fb.control(ballast?.rdgLevel?.value, [Validators.required, numberValidator(3, 3, false)]),
    });
  }

  /**
  * Initialize loadable plan form
  *
  * @private
  * @memberof StowageComponent
  */
  private initLoadablePlanBallastForm() {
    if (this.getFormGroup('ballastTanks') && this.ballastTankDetails) {
      const ballastPlanForm = this.getFormGroup('ballastTanks');
      const ballastDataTable = ballastPlanForm.get('dataTable') as FormArray;
      this.ballastTankDetails?.map((ballast) => {
        ballastDataTable.push(this.initBallastTankFormGroup(ballast))
      });
      this.initBallastTankDetails = JSON.parse(JSON.stringify(this.ballastTankDetails));
    }
  }

  /**
  * view algo error message
  * @param {boolean} status
  * @memberof StowageComponent
  */
  public viewError(status: boolean) {
    this.errorPopup = status;
    this.errorMessageDisplayChange.emit(status)
  }
}
