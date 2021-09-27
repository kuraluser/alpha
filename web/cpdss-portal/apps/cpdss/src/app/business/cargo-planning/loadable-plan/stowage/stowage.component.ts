import { Component, ElementRef, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { AppConfigurationService } from '../../../../shared/services/app-configuration/app-configuration.service';
import { DATATABLE_EDITMODE } from '../../../../shared/components/datatable/datatable.model';
import { IBallastStowageDetails, IBallastTank, ICargoTank, ITankOptions, LOADABLE_STUDY_STATUS, TANKTYPE, Voyage, VOYAGE_STATUS } from '../../../core/models/common.model';
import { numberValidator } from '../../../core/directives/number-validator.directive';
import { ICargoTankDetailValueObject, IPortsEvent, IUpdatedBallastUllageResponse, IloadableQuantityCargoDetails , IUpdateUllageModel, IUpdatedUllageResponse, IUpdatedRdgLevelResponse, IValidateAndSaveStowage, IBallastTankDetailValueObject, IUpdateBallastUllagegModel, VALIDATION_AND_SAVE_STATUS } from '../../models/loadable-plan.model';
import { LoadablePlanTransformationService } from '../../services/loadable-plan-transformation.service';
import { PermissionsService } from '../../../../shared/services/permissions/permissions.service';
import { LoadableStudy } from '../../models/loadable-study-list.model';
import { NgxSpinnerService } from 'ngx-spinner';
import { LoadablePlanApiService } from '../../services/loadable-plan-api.service';
import { tankCapacityValidator } from '../../../core/directives/tankCapacityValidator.directive';
import { QuantityPipe } from '../../../../shared/pipes/quantity/quantity.pipe';
import { QUANTITY_UNIT, IValidateAndSaveResponse } from '../../../../shared/models/common.model';
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

  @Input() validationPending: boolean;

  @Input()
  get loadableStudy(): LoadableStudy {
    return this._loadableStudy;
  }
  set loadableStudy(value: LoadableStudy) {
    this._loadableStudy = value;
    this.isEditable = ![VOYAGE_STATUS.CLOSE].includes(this.voyage?.statusId);
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
    [VALIDATION_AND_SAVE_STATUS.LOADABLE_PLAN_STARTED].includes(value) ? (this.isStowageEditable = true, this.editMode = null, this.buttonStatus = 0) : this.isStowageEditable = false;
  }

  get loadablePatternValidationStatus(): number {
    return this._loadablePatternValidationStatus;
  }

  @Input()
  set ballastDetails(ballastStowageDetails: IBallastStowageDetails[]) {
    this.ballastTankDetails = ballastStowageDetails?.map((ballast: IBallastStowageDetails) => this.loadablePlanTransformationService.getBallastTankDetailAsValueObject(ballast));
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

  @Input()
  get loadableQuantityCargo(): IloadableQuantityCargoDetails[] {
    return this._loadableQuantityCargo;
  }
  set loadableQuantityCargo(loadableQuantityCargo: IloadableQuantityCargoDetails[]) {
    this._loadableQuantityCargo = loadableQuantityCargo;
  }

  @Input()
  get loadableQuantity(): number {
    return this._loadableQuantity;
  }
  set loadableQuantity(loadableQuantity: number) {
    this._loadableQuantity = loadableQuantity;
  }

  @Output() errorMessageDisplayChange = new EventEmitter<boolean>();
  @Output() ullageUpdate = new EventEmitter<boolean>();
  @Output() registerEvents = new EventEmitter<boolean>();

  readonly tankType = TANKTYPE;
  readonly validateAndSaveStatus = VALIDATION_AND_SAVE_STATUS;
  loadablePlanForm: FormGroup;
  editMode: DATATABLE_EDITMODE = null;
  selectedTab = TANKTYPE.CARGO;
  showGrid = false;
  cargoGridColumns: any[];
  cargoTankOptions: ITankOptions = { isFullyFilled: false, showCommodityName: true, showVolume: true, showFillingPercentage: true, showWeight: true, showUllage: true, class: 'loadable-plan-stowage', fillingPercentageField: 'fillingRatioOrginal', volumeField: 'observedBarrelsAt60Original', volumeUnit: 'BBLS', weightField: 'weightOrginal', weightUnit: AppConfigurationService.settings.baseUnit, ullageField: 'correctedUllageOrginal', ullageUnit: AppConfigurationService.settings?.ullageUnit, showTooltip: true, commodityNameField: 'cargoAbbreviation', showDensity: true, densityField: 'api' };
  ballastTankOptions: ITankOptions = { isFullyFilled: false, showUllage: true, showFillingPercentage: true, class: 'loadable-plan-stowage', fillingPercentageField: 'percentageOrginal', ullageField: 'correctedLevelOrginal', ullageUnit: AppConfigurationService.settings?.ullageUnit, showTooltip: true, weightField: 'metricTonOrginal', weightUnit: AppConfigurationService.settings.baseUnit, showDensity: true, densityField: 'sg' };
  isPermissionAvaliable: boolean;
  isEditable: boolean;
  buttonStatus: number;
  initialCargoTankDetails: ICargoTankDetailValueObject[];
  validateAndSaveProcessing: boolean;
  ballastTankDetails: IBallastTankDetailValueObject[];
  initBallastTankDetails: IBallastTankDetailValueObject[];
  isStowageEditable: boolean;
  errorPopup: boolean;
  stowageDataEditStatus: boolean;


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
  private _loadableQuantityCargo: IloadableQuantityCargoDetails[];
  private _loadableQuantity: number;


  constructor(
    private loadablePlanTransformationService: LoadablePlanTransformationService,
    private fb: FormBuilder,
    private router: Router,
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
    this.loadablePlanTransformationService.savedComments$.subscribe((value: string) => {
      if(value === 'savedCommentsPopup') {
        this.validateAndSave();
      }
    })
    this.loadablePlanTransformationService.editBallastStatus$.subscribe((value: any) => {
      this.validateAndSaveProcessing = value.validateAndSaveProcessing !== undefined ? value.validateAndSaveProcessing : this.validateAndSaveProcessing
    })
  }

  /**
   * Handler for tab click
   *
   * @param {TANKTYPE} selectedTab
   * @memberof StowageComponent
   */
  async onTabClick(selectedTab: TANKTYPE) {
    if(this.selectedTab === selectedTab) {
      return;
    }
    const status = await this.isGridToggle();
    if(status){
      this.selectedTab = selectedTab;
      this.buttonStatus = 0;
      this.editMode = null;
    }
  }

  /**
   * Method to toggle visibility of stoage details table
   *
   * @memberof StowageComponent
   */
  async toggleGridView() {
    const status = await this.isGridToggle();
    if(status){
      this.showGrid = !this.showGrid;
      this.buttonStatus = 0;
      this.editMode = null;
    } else {
      this.showGrid = true
    }
  }

  /**
   * Method to check form valid or not
   * @returns {boolean}
   * @memberof StowageComponent
  */
  async isGridToggle() {
    let status;
    const translationKeys = await this.translateService.get(['LOADABLE_PLAN_ULLAGE_INVALID_DATA_ERROR', 'LOADABLE_PLAN_ULLAGE_INVALID_DATA_BALLAST', 'LOADABLE_PLAN_ULLAGE_INVALID_DATA_CARGO']).toPromise();
    if (this.selectedTab === this.tankType.CARGO && !this.isFormValid('cargoTanks')) {
      status = true;
      if(this.showGrid) {
        this.messageService.add({ severity: 'error', summary: translationKeys['LOADABLE_PLAN_ULLAGE_INVALID_DATA_ERROR'], detail: translationKeys['LOADABLE_PLAN_ULLAGE_INVALID_DATA_CARGO'] });
      }
    } else if (this.selectedTab === this.tankType.BALLAST && !this.isFormValid('ballastTanks')) {
      status = true;
      if(this.showGrid) {
        this.messageService.add({ severity: 'error', summary: translationKeys['LOADABLE_PLAN_ULLAGE_INVALID_DATA_ERROR'], detail: translationKeys['LOADABLE_PLAN_ULLAGE_INVALID_DATA_BALLAST'] });
      }
    }
    return !status;
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
      fillingRatio: this.fb.control(cargo?.fillingRatio?.value , tankCapacityValidator('observedM3', cargo.fullCapacityCubm, 'rdgUllage' , 'fillingRatio')),
      tankName: this.fb.control(cargo?.tankName),
      rdgUllage: this.fb.control(cargo?.rdgUllage?.value, [Validators.required, numberValidator(6, 3, false), tankCapacityValidator('observedM3', cargo.fullCapacityCubm, 'rdgUllage', 'fillingRatio')]),
      correctionFactor: this.fb.control(cargo?.correctionFactor?.value),
      observedM3: this.fb.control(cargo?.observedM3?.value, [tankCapacityValidator('observedM3', cargo.fullCapacityCubm, 'rdgUllage' , 'fillingRatio')]),
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
    this.stowageDataEditStatus = true;
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
    const formControl = this.field(event.index, event.field, 'cargoTanks');
    if (formControl?.errors?.required) {
      this.stowageDataEditStatus = false;
      return;
    }
    if (this.editMode && !this.openSaveStowagePopup) {
      if (event.field === 'rdgUllage') {
        this.ngxSpinnerService.show();
        const data: IUpdateUllageModel = {
          id: event.data.id,
          tankId: event.data.tankId,
          correctedUllage: event.data.rdgUllage['_value'],
          isBallast: false,
          api: event.data.api['_value'],
          temperature: event.data.temperature['_value'],
          sg: '',
          isCommingle: event?.data?.isCommingle
        }
        const translationKeys = await this.translateService.get(['LOADABLE_PLAN_RDG_ULLAGE_ERROR','LOADABLE_PLAN_RDG_ULLAGE','LOADABLE_PLAN_VALIDATION_SAVE_IN_PROGESS_DETAILS', 'LOADABLE_PLAN_VALIDATION_SAVE_IN_PROGESS', 'LOADABLE_PLAN_ULLAGE_UPDATED', 'LOADABLE_PLAN_ULLAGE_UPDATED_DETAILS']).toPromise();
        try {
        const result: IUpdatedUllageResponse = await this.loadablePlanApiService.updateUllage(this.vesselId, this.voyageId, this.loadableStudyId, this.loadablePatternId, data).toPromise();
          if (result.responseStatus.status === '200') {
            if(result.fillingRatio?.toString()?.trim() === '') {
              this.cargoTankDetails[event.index].rdgUllage['_value'] = this.initialCargoTankDetails[event.index].rdgUllage['_value'];
              this.updateField(event.index, 'rdgUllage', Number(this.cargoTankDetails[event.index].rdgUllage['_value']), 'cargoTanks');
              this.messageService.add({ severity: 'error', summary: translationKeys['LOADABLE_PLAN_RDG_ULLAGE_ERROR'], detail: translationKeys['LOADABLE_PLAN_RDG_ULLAGE'] });
              this.ngxSpinnerService.hide();
              return;
            }
            this.stowageDataEditStatus = false;
            this.ullageUpdate.emit(true);
            result.quantityMt =  result.quantityMt ?? ''; 
            const unitConvertedTankDetails = {
              observedM3: this.quantityPipe.transform(result.quantityMt, QUANTITY_UNIT.MT, QUANTITY_UNIT.OBSKL, event.data.api['_value'], event.data.temperature['_value']),
              observedBarrelsAt60: this.quantityPipe.transform(result.quantityMt, QUANTITY_UNIT.MT, QUANTITY_UNIT.BBLS, event.data.api['_value'], event.data.temperature['_value']),
              observedBarrels: this.quantityPipe.transform(result.quantityMt, QUANTITY_UNIT.MT, QUANTITY_UNIT.OBSBBLS, event.data.api['_value'], event.data.temperature['_value'])
            }
            this.cargoTankDetails[event.index]['fillingRatio'].value = Number(result.fillingRatio);
            this.updateField(event.index, 'fillingRatio', this.cargoTankDetails[event.index]['fillingRatio'].value, 'cargoTanks');
            this.updateField(event.index, 'correctionFactor', Number(result.correctionFactor), 'cargoTanks');
            this.updateField(event.index, 'correctedUllage', Number(result.correctedUllage), 'cargoTanks');
            this.updateField(event.index, 'weight', result.quantityMt ? Number(result.quantityMt) : null, 'cargoTanks');
            this.updateField(event.index, 'observedM3', unitConvertedTankDetails.observedM3, 'cargoTanks');
            this.updateField(event.index, 'observedBarrelsAt60', unitConvertedTankDetails.observedBarrelsAt60, 'cargoTanks');
            this.updateField(event.index, 'observedBarrels', unitConvertedTankDetails.observedBarrels, 'cargoTanks');
            this.updateField(event.index, 'rdgUllage', Number(event.data.rdgUllage['_value']), 'cargoTanks');


            this.cargoTankDetails[event.index]['correctionFactor'].value = Number(result.correctionFactor);
            this.cargoTankDetails[event.index]['correctedUllage'].value = Number(result.correctedUllage);
            this.cargoTankDetails[event.index]['weight'].value = result.quantityMt ? Number(result.quantityMt) : null;
            this.cargoTankDetails[event.index]['observedM3'].value = unitConvertedTankDetails.observedM3;
            this.cargoTankDetails[event.index]['observedBarrelsAt60'].value = unitConvertedTankDetails.observedBarrelsAt60;
            this.cargoTankDetails[event.index]['observedBarrels'].value = unitConvertedTankDetails.observedBarrels;

            this.loadablePlanForm.updateValueAndValidity();

            this.initialCargoTankDetails = JSON.parse(JSON.stringify(this.cargoTankDetails));
            this.messageService.add({ severity: 'success', summary: translationKeys['LOADABLE_PLAN_ULLAGE_UPDATED'], detail: translationKeys['LOADABLE_PLAN_ULLAGE_UPDATED_DETAILS'] });
            this.ngxSpinnerService.hide();
          }
        } catch (error) {
          if (error.error.errorCode === 'ERR-RICO-115') {
            this.stowageDataEditStatus = false;
            this.updateField(event.index, event.field, this.initialCargoTankDetails[event.index][event.field]['_value'], 'cargoTanks');
            this.cargoTankDetails[event.index][event.field].value = this.initialCargoTankDetails[event.index][event.field]['_value'];
            this.messageService.add({ severity: 'warn', summary: translationKeys['LOADABLE_PLAN_VALIDATION_SAVE_IN_PROGESS'], detail: translationKeys['LOADABLE_PLAN_VALIDATION_SAVE_IN_PROGESS_DETAILS'] });
            this.ngxSpinnerService.hide();
          }
        }
        if (this.field(event.index, 'weight', 'cargoTanks').value === null) {
          this.messageService.add({ severity: 'error', summary: translationKeys['LOADABLE_PLAN_ULLAGE_INVALID_DATA_ERROR'], detail: translationKeys['LOADABLE_PLAN_ULLAGE_INVALID_RDG_VALUE'], life: 7000 });
        }
      }
    } else {
      this.stowageDataEditStatus = false;
      this.updateField(event.index, event.field, this.initialCargoTankDetails[event.index][event.field]['_value'], 'cargoTanks');
      this.cargoTankDetails[event.index][event.field].value = this.initialCargoTankDetails[event.index][event.field]['_value'];
      this.ngxSpinnerService.hide();
    }
  }

  /**
   * Event handler for edit complete event for ballast
   * @param {*} ref
   * @param {IPortsEvent} event
   * @memberof StowageComponent
   */
  async ballastStowageDataEdit(event: IPortsEvent, ref: any) {
    const formControl = this.field(event.index, event.field, 'ballastTanks');
    if (formControl?.errors?.required) {
      ref.stowageDataEditStatus = false;
      return;
    }
    if (event.field === 'rdgLevel') {
      this.ngxSpinnerService.show();
      const data: IUpdateUllageModel = {
        id: event.data.id,
        tankId: event.data.tankId,
        correctedUllage: event.data.rdgLevel['_value'],
        isBallast: true,
        api: "",
        temperature: "",
        sg: event.data.sg['_value']
      }
      const translationKeys = await this.translateService.get(['LOADABLE_PLAN_RDG_ULLAGE_ERROR','LOADABLE_PLAN_RDG_ULLAGE','LOADABLE_PLAN_ULLAGE_UPDATED', 'LOADABLE_PLAN_ULLAGE_UPDATED_DETAILS', 'LOADABLE_PLAN_VALIDATION_SAVE_IN_PROGESS_DETAILS', 'LOADABLE_PLAN_VALIDATION_SAVE_IN_PROGESS', 'LOADABLE_PLAN_ULLAGE_INVALID_RDG_VALUE']).toPromise();
      try {
      const result: IUpdatedUllageResponse = await this.loadablePlanApiService.updateUllage(this.vesselId, this.voyageId, this.loadableStudyId, this.loadablePatternId, data).toPromise();
        if (result.responseStatus.status === '200') {
          if(result.fillingRatio?.toString()?.trim() === '') {
            this.ballastTankDetails[event.index].rdgLevel['_value'] = this.initBallastTankDetails[event.index].rdgLevel['_value'];
            this.updateField(event.index, 'rdgLevel', Number(this.ballastTankDetails[event.index].rdgLevel['_value']), 'ballastTanks');
            this.messageService.add({ severity: 'error', summary: translationKeys['LOADABLE_PLAN_RDG_ULLAGE_ERROR'], detail: translationKeys['LOADABLE_PLAN_RDG_ULLAGE'] });
            this.ngxSpinnerService.hide();
            return;
          }
          ref.stowageDataEditStatus = false;
          this.ullageUpdate.emit(true);
          this.ballastTankDetails[event.index]['correctedLevel'].value = result.correctedUllage;
          this.ballastTankDetails[event.index]['correctionFactor'].value = result.correctionFactor;
          this.ballastTankDetails[event.index]['metricTon'].value = result.quantityMt;
          this.ballastTankDetails[event.index]['percentage'].value = result.fillingRatio + '';
          this.updateField(event.index, 'percentage', this.ballastTankDetails[event.index]['percentage'].value, 'ballastTanks');
          const unitConvertedTankDetails = {
            observedM3: (this.ballastTankDetails[event.index]['metricTon'].value && this.ballastTankDetails[event.index]['sg'].value) ?  (Number(this.ballastTankDetails[event.index]['metricTon'].value) / Number(this.ballastTankDetails[event.index]['sg'].value)).toString() : ''
          };
          this.ballastTankDetails[event.index]['cubicMeter'].value = unitConvertedTankDetails.observedM3;
          this.updateField(event.index, 'cubicMeter', this.ballastTankDetails[event.index]['cubicMeter'].value, 'ballastTanks');
          this.ballastTankDetails[event.index]['rdgLevel'].value = event.data.rdgLevel.value;
          this.updateField(event.index, 'rdgLevel', this.ballastTankDetails[event.index]['rdgLevel'].value, 'ballastTanks');
          this.loadablePlanForm.updateValueAndValidity();
          this.messageService.add({ severity: 'success', summary: translationKeys['LOADABLE_PLAN_ULLAGE_UPDATED'], detail: translationKeys['LOADABLE_PLAN_ULLAGE_UPDATED_DETAILS'] });
          this.initBallastTankDetails = JSON.parse(JSON.stringify(this.ballastTankDetails));
          this.ngxSpinnerService.hide();
        }
      } catch (error) {
        if (error.error.errorCode === 'ERR-RICO-115') {
          ref.stowageDataEditStatus = false;
          this.ballastTankDetails[event.index][event.field].value = this.initBallastTankDetails[event.index][event.field].value;
          this.updateField(event.index, event.field, this.ballastTankDetails[event.index][event.field].value, 'ballastTanks');
          this.messageService.add({ severity: 'warn', summary: translationKeys['LOADABLE_PLAN_VALIDATION_SAVE_IN_PROGESS'], detail: translationKeys['LOADABLE_PLAN_VALIDATION_SAVE_IN_PROGESS_DETAILS'] });
        }
      }
      if (this.field(event.index, 'cubicMeter', 'ballastTanks').value === '') {
        this.messageService.add({ severity: 'error', summary: translationKeys['LOADABLE_PLAN_ULLAGE_INVALID_DATA_ERROR'], detail: translationKeys['LOADABLE_PLAN_ULLAGE_INVALID_RDG_VALUE'], life: 7000 });
      }
    }
  }

  /**
   * enable or disable commnets popup
   *
   * @param {boolean} status
   * @memberof StowageComponent
  */
  async commentsPopup(status: boolean) {
    const translationKeys = await this.translateService.get(['LOADABLE_PLAN_EXCEED_TOLERANCE_LIMIT','LOADABLE_PLAN_EXCEED_LOADABLE_QUANTITY','LOADABLE_PLAN_ULLAGE_INVALID_DATA_ERROR', 'LOADABLE_PLAN_ULLAGE_INVALID_DATA_BALLAST', 'LOADABLE_PLAN_ULLAGE_INVALID_DATA_CARGO']).toPromise();
    let cargoQuantity = 0;
    let exceedToleranceLimit;

    this.loadableQuantityCargo.map(loadableQuantityCargo => {
      let total = 0;
      total += Number(loadableQuantityCargo.commingleTotalQuantity);
      this.cargoTankDetails.map((cargoDetail) => {
        if (loadableQuantityCargo?.cargoNominationId === cargoDetail?.cargoNominationId) {
          total += Number(cargoDetail.weight?.value);
          cargoQuantity += Number(cargoDetail?.weight?.value);
        }
      });
      if (total > loadableQuantityCargo?.maxTolerence) {
        exceedToleranceLimit = loadableQuantityCargo;
      } else if (total < loadableQuantityCargo?.minTolerence) {
        exceedToleranceLimit = loadableQuantityCargo;
      }
    });

    if(this.loadableQuantity < cargoQuantity) {
      this.messageService.add({ severity: 'error', summary: translationKeys['LOADABLE_PLAN_ULLAGE_INVALID_DATA_ERROR'], detail: translationKeys['LOADABLE_PLAN_EXCEED_LOADABLE_QUANTITY'] });
      return;
    } else if(exceedToleranceLimit){
      this.messageService.add({ severity: 'error', summary: translationKeys['LOADABLE_PLAN_ULLAGE_INVALID_DATA_ERROR'], detail: translationKeys['LOADABLE_PLAN_EXCEED_TOLERANCE_LIMIT'] });
      return;
    }
    if (this.loadablePlanForm.valid) {
      this.openSaveStowagePopup = status;
    } else {
      if (this.loadablePlanForm.controls['cargoTanks'].invalid) {
        this.messageService.add({ severity: 'error', summary: translationKeys['LOADABLE_PLAN_ULLAGE_INVALID_DATA_ERROR'], detail: translationKeys['LOADABLE_PLAN_ULLAGE_INVALID_DATA_CARGO'] });
      } else {
        this.messageService.add({ severity: 'error', summary: translationKeys['LOADABLE_PLAN_ULLAGE_INVALID_DATA_ERROR'], detail: translationKeys['LOADABLE_PLAN_ULLAGE_INVALID_DATA_BALLAST'] });
      }
    }
  }

  /**
   * check form valid or not
   *
   * @param {string} formGroup
   * @memberof StowageComponent
  */
  isFormValid(formGroup: string) {
    return this.loadablePlanForm.controls[formGroup].valid;
  }

  /**
   * validate and save
   *
   * @param {IPortsEvent} event
   * @memberof StowageComponent
  */
  async validateAndSave() {
    if (this.loadablePlanForm.valid) {
      this.buttonStatus = 0;
      this.editMode = null;
      this.validateAndSaveProcessing = true;
      this.loadablePlanTransformationService.ballastEditStatus({
        buttonStatus: this.buttonStatus,
        editMode: this.editMode,
        validateAndSaveProcessing: this.validateAndSaveProcessing
      })
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
        this.registerEvents.emit(true);
        navigator.serviceWorker.controller.postMessage({ type: 'validate-and-save', data });
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
  async cancelStowageEdit() {
    const translationKeys = await this.translateService.get(['LOADABLE_PLAN_ULLAGE_INVALID_DATA_ERROR', 'LOADABLE_PLAN_ULLAGE_INVALID_DATA_BALLAST', 'LOADABLE_PLAN_ULLAGE_INVALID_DATA_CARGO']).toPromise();
    if (this.isFormValid('cargoTanks')) {
      this.buttonStatus = 0;
      this.editMode = null;
    } else {
      this.messageService.add({ severity: 'error', summary: translationKeys['LOADABLE_PLAN_ULLAGE_INVALID_DATA_ERROR'], detail: translationKeys['LOADABLE_PLAN_ULLAGE_INVALID_DATA_CARGO'] });
    }

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
    control.updateValueAndValidity();
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
      cubicMeter: this.fb.control(ballast.cubicMeter.value),
      rdgLevel: this.fb.control(ballast?.rdgLevel?.value, [Validators.required, numberValidator(6, 3, false), tankCapacityValidator('cubicMeter', ballast.fullCapacityCubm, 'rdgLevel','percentage', 100)]),
      percentage: this.fb.control(ballast?.percentage)
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

  /**
  * navigation to patterns
  * @memberof StowageComponent
  */
  public navigateToLoadablePattern() {
    this.router.navigate([`/business/cargo-planning/loadable-pattern-history/1/${this.vesselId}/${this.voyageId}/${this.loadableStudyId}`]);
  }
}
