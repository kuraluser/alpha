import { Component, EventEmitter, Input, OnInit, Output, SimpleChanges } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { DischargeStudyListApiService } from '../../../cargo-planning/services/discharge-study-list-api.service';
import { DischargeStudyListTransformationApiService } from '../../../cargo-planning/services/discharge-study-list-transformation-api.service';
import { isAlphaCharacterAvaiable } from '../../directives/alpha-validator.directive';

/**
 * Component class for NewDischargeStudyPopUpComponent
 *
 * @export
 * @class NewDischargeStudyPopUpComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-new-discharge-study-pop-up',
  templateUrl: './new-discharge-study-pop-up.component.html',
  styleUrls: ['./new-discharge-study-pop-up.component.scss']
})
export class NewDischargeStudyPopUpComponent implements OnInit {


  
  @Input() display;
  @Input() vesselInfoList;
  @Input() selectedVoyage;
  @Output() displayPopup = new EventEmitter();  
  @Output() addedNewDischargeStudy = new EventEmitter<Object>();
  popUpHeader :any;
  @Input() isEdit;
  saveButtonLbl:string;
  public dischargeStudyForm: FormGroup;
  errors:any;
  errorMessages:any;
  @Input() selectedDischargeStudy;
  @Input() dischargeStudyList;

  constructor(
    private messageService: MessageService,
    private translateService: TranslateService,
    private ngxSpinnerService: NgxSpinnerService,private formBuilder: FormBuilder,
  private dischargeStudyListTransformationApiService : DischargeStudyListTransformationApiService,
  private dischargeStudyApiService : DischargeStudyListApiService) { }


  /**
   * Component lifecycle ngoninit.
   *
   * @memberof NewDischargeStudyPopUpComponent
   */
  ngOnInit(): void {
    this.setPopUpHeader();
    this.getErrorMessages();
    this.initialiseForm();
  }

  /**
   * Component lifecyle ngonchanges.
   *
   * @param {SimpleChanges} changes
   * @memberof NewDischargeStudyPopUpComponent
   */
  ngOnChanges(changes: SimpleChanges) {
    if (changes?.selectedDischargeStudy &&changes.selectedDischargeStudy.currentValue!=null ) {
      this.selectedDischargeStudy = changes.selectedDischargeStudy.currentValue
    }
    if(changes?.isEdit){
      this.isEdit = changes.isEdit.currentValue;
    }
   this.setPopUpHeader();
   this.setFormValue();
  }


  /**
   * Method to set form value
   *
   * @memberof NewDischargeStudyPopUpComponent
   */
  setFormValue() {
    if (this.selectedDischargeStudy) {
      this.dischargeStudyForm.controls.newDischargeStudyName.setValue(this.selectedDischargeStudy.name);
      this.dischargeStudyForm.controls.enquiryDetails.setValue(this.selectedDischargeStudy.detail);
    }
    else{
      this.dischargeStudyForm.reset();
    }
  }
  
  /**
   * Method to set up the header and save label in pop up.
   *
   * @memberof NewDischargeStudyPopUpComponent
   */
  setPopUpHeader()
  {
    this.popUpHeader = this.isEdit ? "DISCHARGE_STUDY_LIST_POP_HEADER_EDIT" : "DISCHARGE_STUDY_LIST_POP_HEADER_NEW"
    this.saveButtonLbl = this.isEdit ? "NEW_DISCHARGE_STUDY_POPUP_UPDATE_BUTTON" : "NEW_DISCHARGE_STUDY_POPUP_SAVE_BUTTON";
  }

  /**
   * Method to initialise form.
   *
   * @memberof NewDischargeStudyPopUpComponent
   */
  initialiseForm()
  {
    this.dischargeStudyForm = this.formBuilder.group({
      'newDischargeStudyName':['', [Validators.required , isAlphaCharacterAvaiable, Validators.maxLength(100)]],
      'enquiryDetails': ['', [Validators.maxLength(1000)]],
    });
  }


  /**
   * Method to get error messages.
   *
   * @memberof NewDischargeStudyPopUpComponent
   */
  
  getErrorMessages(){
    this.errorMessages= this.dischargeStudyListTransformationApiService.setValidationErrorMessage();
  }
  /**
   * Method to get field errors for formcontrol.
   *
   * @param {string} formControlName
   * @return {*}  {ValidationErrors}
   * @memberof NewDischargeStudyPopUpComponent
   */
  fieldError(formControlName: string): ValidationErrors { 
    const formControl = this.field(formControlName);
    return formControl.invalid && (formControl.dirty || formControl.touched) ? formControl.errors : null;
  }

  /**
   * Method to get field name.
   *
   * @param {string} formControlName
   * @return {*}  {FormControl}
   * @memberof NewDischargeStudyPopUpComponent
   */

  field(formControlName: string): FormControl {
    const formControl = <FormControl>this.dischargeStudyForm.get(formControlName);
    return formControl;
  }

  /**
   * Method to close the pop up.
   *
   * @memberof NewDischargeStudyPopUpComponent
   */
  closeDialogue()
  {
    this.dischargeStudyForm.reset();
    this.displayPopup.emit(false);
  }


  /**Method to save/update discharge study */


  public async saveOrUpdateDischargeStudy() {  
    this.dischargeStudyForm.markAllAsTouched();
    this.ngxSpinnerService.show();
    let result;
    if (this.dischargeStudyForm.valid) {
      const newModel = {
        name: this.dischargeStudyForm.value.newDischargeStudyName,
        detail: this.dischargeStudyForm.value.enquiryDetails ? this.dischargeStudyForm.value.enquiryDetails : ''
      }

      const translationKeys = await this.translateService.get(['NEW_DISCHARGE_STUDY_POPUP__NAME_EXIST', 'DISCHARGE_STUDY_CREATE_SUCCESS', 'DISCHARGE_STUDY_CREATE_SUCCESS','DISCHARGE_STUDY_CREATED_SUCCESSFULLY', 'DISCHARGE_STUDY_CREATE_ERROR', 'DISCHARGE_STUDY_ALREADY_EXIST', 'DISCHARGE_STUDY_UPDATE_SUCCESS', 'DISCHARGE_STUDY_UPDATED_SUCCESSFULLY',
      'NEW_DISCHARGE_STUDY_POPUP_BILL_OF_LADDING']).toPromise();
      try {
        if (!this.selectedDischargeStudy) {
          result = await this.dischargeStudyApiService.saveOrUpdateDischargeStudy(this.vesselInfoList.id, this.selectedVoyage.id, newModel).toPromise();
        }
        else {

          result = await this.dischargeStudyApiService.saveOrUpdateDischargeStudy(null, null, newModel, this.selectedDischargeStudy.id).toPromise();
        }
        if (result.responseStatus.status === "200") {
          if (result?.dischargeStudyId) {
            this.messageService.add({ severity: 'success', summary: translationKeys['DISCHARGE_STUDY_CREATE_SUCCESS'], detail: translationKeys['DISCHARGE_STUDY_CREATED_SUCCESSFULLY'] });
            this.addedNewDischargeStudy.emit(result?.dischargeStudyId);
          }
          else {
            this.messageService.add({ severity: 'success', summary: translationKeys['DISCHARGE_STUDY_UPDATE_SUCCESS'], detail: translationKeys['DISCHARGE_STUDY_UPDATED_SUCCESSFULLY'] });
            this.addedNewDischargeStudy.emit(result.dischargeStudy.id);
          }
        }
        else {
          this.messageService.add({ severity: 'error', summary: translationKeys['DISCHARGE_STUDY_CREATE_ERROR'], detail: translationKeys['DISCHARGE_STUDY_CREATED_SUCCESSFULLY'] });
        }
        this.ngxSpinnerService.hide();
        this.closeDialogue();
      }
      catch (error) {
        if (error.error.errorCode === 'ERR-RICO-105') {
          this.messageService.add({ severity: 'error', summary: translationKeys['DISCHARGE_STUDY_CREATE_ERROR'], detail: translationKeys['NEW_DISCHARGE_STUDY_POPUP__NAME_EXIST'] });
          this.ngxSpinnerService.hide();
        } else if(error.error.errorCode === 'ERR-RICO-151') {
          this.messageService.add({ severity: 'error', summary: translationKeys['DISCHARGE_STUDY_CREATE_ERROR'], detail: translationKeys['NEW_DISCHARGE_STUDY_POPUP_BILL_OF_LADDING'] });
          this.ngxSpinnerService.hide();
        } else if (error?.error?.errorCode === 'ERR-RICO-326') {
          this.messageService.add({ severity: 'error', summary: translationKeys['DISCHARGE_STUDY_DELETE_ERROR'], detail: translationKeys['NEW_DISCHARGE_STUDY_POPUP_ACTUAL_VALUE']});
        }
      }
    }
    this.selectedDischargeStudy = null;
    this.ngxSpinnerService.hide();
  }
}
