import { Component, EventEmitter, Input, OnInit, Output, SimpleChanges } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { DischargeStudyListTransformationApiService } from '../../../cargo-planning/services/discharge-study-list-transformation-api.service';


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
  @Output() displayPopup = new EventEmitter();
  popUpHeader :any;
  @Input() isEdit;
  saveButtonLbl:string;
  public dischargeStudyForm: FormGroup;
  errors:any;
  errorMessages:any;
  @Input() selectedDischargeStudy;

  constructor(private translateService: TranslateService,private formBuilder: FormBuilder,
  private dischargeStudyListTransformationApiService : DischargeStudyListTransformationApiService) { }


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
   this.setPopUpHeader();
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
      'newDischargeStudyName':['', [Validators.required]],
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
    debugger;
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
  onClose()
  {
    this.displayPopup.emit(false);
  }
}
