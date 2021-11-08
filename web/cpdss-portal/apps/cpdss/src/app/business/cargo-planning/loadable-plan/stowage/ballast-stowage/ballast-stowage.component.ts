import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

import { NgxSpinnerService } from 'ngx-spinner';
import { TranslateService } from '@ngx-translate/core';
import { MessageService } from 'primeng/api';

import { IBallastStowageDetails } from '../../../../core/models/common.model';
import { LoadablePlanTransformationService } from '../../../services/loadable-plan-transformation.service';
import { LoadablePlanApiService } from '../../../services/loadable-plan-api.service';
import { IPortsEvent, IBallastTankDetailValueObject , VALIDATION_AND_SAVE_STATUS } from '../../../models/loadable-plan.model';
import { tankCapacityValidator } from '../../../../core/directives/tankCapacityValidator.directive';
import { DATATABLE_EDITMODE } from '../../../../../shared/components/datatable/datatable.model';

/**
 * Component class of ballast section
 *
 * @export
 * @class BallastStowageComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-ballast-stowage',
  templateUrl: './ballast-stowage.component.html',
  styleUrls: ['./ballast-stowage.component.scss']
})
export class BallastStowageComponent implements OnInit {

  //public fields
  public columns: any[];
  public editMode: DATATABLE_EDITMODE = null;
  public ballastTankDetails: IBallastTankDetailValueObject[];
  public buttonStatus: number;
  private _ballastForm: FormGroup;
  private _isEditable: boolean;
  private _loadableStudyStatus: boolean;
  private _initBallastTankDetails: IBallastTankDetailValueObject[];
  private _isStowageEditable: boolean;
  validateAndSaveProcessing: boolean;

  @Input() stowageDataEditStatus: boolean;
  @Input() validationPending: boolean;

  @Input() set ballastDetails(ballastStowageDetails: IBallastTankDetailValueObject[]) {
    this.ballastTankDetails = ballastStowageDetails;
  }

  @Input() set ballastForm(ballastForm: FormGroup) {
    this._ballastForm = ballastForm;
  }

  get ballastForm(): FormGroup {
    return this._ballastForm;
  }

  @Input() set initBallastTankDetails(ballastDetails: IBallastTankDetailValueObject[]) {
    this._initBallastTankDetails = ballastDetails;
  }

  get initBallastTankDetails(): IBallastTankDetailValueObject[] {
    return this._initBallastTankDetails;
  }

  @Input() set isEditable(isEditable: boolean) {
    this._isEditable = isEditable;
  };

  get isEditable(): boolean {
    return this._isEditable;
  }

  @Input()
  get loadableStudyStatus(): boolean {
    return this._loadableStudyStatus;
  }

  set loadableStudyStatus(value: boolean) {
    this._loadableStudyStatus = value;
  }

  @Input()
  get isStowageEditable(): boolean {
    return this._isStowageEditable;
  }

  set isStowageEditable(value: boolean) {
    this._isStowageEditable = value;
  }

  @Input() set loadablePatternValidationStatus(value: number) {
    this._loadablePatternValidationStatus = value;
  }

  get loadablePatternValidationStatus(): number {
    return this._loadablePatternValidationStatus;
  }

  readonly validateAndSaveStatus = VALIDATION_AND_SAVE_STATUS;
  private _loadablePatternValidationStatus: number;

  @Input() isPermissionAvaliable: boolean;
  @Output() ballastStowageDataEdit = new EventEmitter<IPortsEvent>();
  @Output() validateAndSave = new EventEmitter<boolean>();
  @Output() viewErrorMessage = new EventEmitter<boolean>();

  // public methods
  constructor(
    private loadablePlanTransformationService: LoadablePlanTransformationService,
    private fb: FormBuilder,
    private ngxSpinnerService: NgxSpinnerService,
    private loadablePlanApiService: LoadablePlanApiService,
    private translateService: TranslateService,
    private messageService: MessageService) { }

  /**
   * Component lifecycle ngOnit
   * Method called while intialization the component
   *
   * @returns {void}
   * @memberof BallastStowageComponent
   */
  ngOnInit(): void {
    this.buttonStatus = 0;
    this.columns = this.loadablePlanTransformationService.getBallastDatatableColumns();
    this.loadablePlanTransformationService.editBallastStatus$.subscribe((value: any) => {
      this.buttonStatus = value.buttonStatus !== undefined ? value.buttonStatus : this.buttonStatus;
      this.editMode = value.editMode !== undefined ? value.editMode : this.editMode;
      this.validateAndSaveProcessing = value?.validateAndSaveProcessing !== undefined ? value.validateAndSaveProcessing : this.validateAndSaveProcessing
    })
  }

  /**
 * Event handler for edit complete event
 *
 * @param {IPortsEvent} event
 * @memberof BallastStowageComponent
 */
  async onEditComplete(event: IPortsEvent) {
    this.stowageDataEditStatus = true;
    setTimeout(() => {
      if (this.editMode) {
        this.ballastStowageDataEdit.emit(event);
      } else {
        this.ballastTankDetails[event.index]['rdgLevel'].value = this.initBallastTankDetails[event.index]['rdgLevel']['_value'];
        this.updateField(event.index, event.field, this.initBallastTankDetails[event.index]['rdgLevel']['_value']);
      }
    }, 250)
  }

  /**
   * Method for toggling edit mode of ballast tank details grid
   *
   * @memberof BallastStowageComponent
  */
  changeGridToEditMode() {
    this.editMode = DATATABLE_EDITMODE.CELL;
    this.columns = this.loadablePlanTransformationService.getBallastDatatableColumns(true);
    this.buttonStatus = 1;
  }

  /**
   * cancel stowage edit
   *
   * @param {IPortsEvent} event
   * @memberof BallastStowageComponent
  */
  async cancelStowageEdit() {
    if(this.ballastForm.valid) {
      this.buttonStatus = 0;
      this.editMode = null;
      this.columns = this.loadablePlanTransformationService.getBallastDatatableColumns();
    } else {
      const translationKeys = await this.translateService.get(['LOADABLE_PLAN_ULLAGE_INVALID_DATA_ERROR', 'LOADABLE_PLAN_ULLAGE_INVALID_DATA_BALLAST','LOADABLE_PLAN_ULLAGE_INVALID_DATA_CARGO']).toPromise();
      this.messageService.add({ severity: 'error', summary: translationKeys['LOADABLE_PLAN_ULLAGE_INVALID_DATA_ERROR'], detail: translationKeys['LOADABLE_PLAN_ULLAGE_INVALID_DATA_BALLAST'] });
    }

  }

  /**
  * Method for fetching form fields
  *
  * @private
  * @param {number} formGroupIndex
  * @param {string} formControlName
  * @returns {FormControl}
  * @memberof BallastStowageComponent
  */
  private field(formGroupIndex: number, formControlName: string): FormControl {
    const formControl = <FormControl>(<FormArray>this.ballastForm.get('dataTable')).at(formGroupIndex).get(formControlName);
    return formControl;
  }

  /**
   * Method for updating form field
   *
   * @private
   * @param {number} index
   * @param {string} field
   * @param {*} value
   * @memberof BallastStowageComponent
   */
  private updateField(index: number, field: string, value: any) {
    const control = this.field(index, field);
    control.setValue(value);
    control.markAsDirty();
    control.markAsTouched();
  }

  /**
  * enable or disable commnets popup
  *
  * @param {boolean} status
  * @memberof BallastStowageComponent
  */
  commentsPopup(status: boolean) {
    this.validateAndSave.emit(true);
  }


  /**
  * view algo error message
  *
  * @memberof BallastStowageComponent
  */
   public viewError() {
    this.viewErrorMessage.emit(true);
  }

}
