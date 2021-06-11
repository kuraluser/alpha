import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { MessageService } from 'primeng/api';
import { Observable, of } from 'rxjs';
import { NgxSpinnerService } from 'ngx-spinner';
import { IResponse } from '../../../../../shared/models/common.model';
import { LoadablePlanApiService } from '../../../services/loadable-plan-api.service';
import { ISaveComment } from '../../../models/loadable-plan.model';
import { LoadablePlanTransformationService } from '../../../services/loadable-plan-transformation.service';
import { whiteSpaceValidator } from '../../../../core/directives/space-validator.directive';

/**
 * Component class for Save stowage Comment Popup
 *
 * @export
 * @class SaveStowagePopupComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-save-stowage-popup',
  templateUrl: './save-stowage-popup.component.html',
  styleUrls: ['./save-stowage-popup.component.scss']
})
export class SaveStowagePopupComponent implements OnInit {

  @Input() vesselId: number;

  @Input() voyageId: number;

  @Input() loadableStudyId: number;

  @Input() loadablePatternId: number;

  @Input()
  get visible(): boolean {
    return this._visible;
  }
  set visible(visible: boolean) {
    this._visible = visible;
  }

  @Output() visibleChange = new EventEmitter<boolean>();

  saveStowageForm: FormGroup;
  errorMessages = {
    'required': 'Required',
    'maxlength': 'LOADABLE_PLAN_SAVE_STOWAGE_COMMENT_MAXLENGTH',
    'whitespace': 'COMMENTS_REQUIRED'
  };

  private _visible: boolean;

  constructor(private fb: FormBuilder,
    private messageService: MessageService,
    private translateService: TranslateService,
    private ngxSpinnerService: NgxSpinnerService,
    private loadablePlanApiService: LoadablePlanApiService,
    private loadablePlanTransformationService: LoadablePlanTransformationService) { }

  ngOnInit(): void {
    this.saveStowageForm = this.fb.group({
      comment: this.fb.control(null, [Validators.required, Validators.maxLength(100), whiteSpaceValidator])
    });
  }

  /**
   * Method for closing popup
   *
   * @memberof SaveStowagePopupComponent
   */
  closePopup() {
    this.visible = false;
    this.visibleChange.emit(this.visible);
    this.saveStowageForm.reset();
  }

    /**
   * Trim blank space 
   * @param {string} formControlName
   * @memberof SaveStowagePopupComponent
   */
    trimFormControl(formControlName: string) {
      this.saveStowageForm.controls[formControlName].setValue((this.saveStowageForm.get(formControlName).value).trim());
    }

  /**
   * Method for save popup
   *
   * @param {IDataTableEvent} event
   * @memberof SaveStowagePopupComponent
   */
  async savePopup() {
    if (this.saveStowageForm.valid) {
      this.ngxSpinnerService.show();
      const translationKeys = await this.translateService.get(['LOADABLE_PLAN_SAVE_STOWAGE_POPUP_COMMENT_SUCCESS', 'LOADABLE_PLAN_SAVE_STOWAGE_POPUP_COMMENT_SUCCESS_DETAILS']).toPromise();
      const comment = <ISaveComment>{ comment: this.field('comment')?.value };
      const response: IResponse = await this.loadablePlanApiService.saveComments(this.vesselId, this.voyageId, this.loadableStudyId, this.loadablePatternId, comment).toPromise();
      if (response?.responseStatus?.status === "200") {
        this.closePopup();
        this.loadablePlanTransformationService.commentsSaved('savedCommentsPopup');
        this.messageService.add({ severity: 'success', summary: translationKeys['LOADABLE_PLAN_SAVE_STOWAGE_POPUP_COMMENT_SUCCESS'], detail: translationKeys['LOADABLE_PLAN_SAVE_STOWAGE_POPUP_COMMENT_SUCCESS_DETAILS'] });
      }
      this.ngxSpinnerService.hide();
    } else {
      this.saveStowageForm.markAllAsTouched();
      this.saveStowageForm.updateValueAndValidity();
    }
  }

  /**
   * Method to get formcontrol error
   *
   * @param {string} formControlName
   * @returns {ValidationErrors}
   * @memberof SaveStowagePopupComponent
   */
  fieldError(formControlName: string): Observable<ValidationErrors> {
    const formControl = this.field(formControlName);
    return of(formControl.invalid && (formControl.dirty || formControl.touched) ? formControl.errors : null);
  }

  /**
   * Method to get formcontrol
   *
   * @param {string} formControlName
   * @returns {FormControl}
   * @memberof SaveStowagePopupComponent
   */
  field(formControlName: string): FormControl {
    const formControl = <FormControl>this.saveStowageForm.get(formControlName);
    return formControl;
  }

}
