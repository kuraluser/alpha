import { Component, Input, OnInit, SimpleChanges, OnChanges } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { DATATABLE_ACTION, DATATABLE_EDITMODE, IDataTableColumn } from  '../../../../shared/components/datatable/datatable.model';
import { ValueObject } from '../../../../shared/models/common.model';
import { numberValidator } from '../../../core/directives/number-validator.directive';
import { PortMasterTransformationService } from '../../services/port-master-transformation.service';


/**
 * Component class for Add Berth Component
 *
 * @export
 * @class AddBerthComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-add-berth',
  templateUrl: './add-berth.component.html',
  styleUrls: ['./add-berth.component.scss']
})

export class AddBerthComponent implements OnChanges, OnInit {

  @Input() existingBerthInfo;
  @Input() isSaveClicked;
  errorMessages: any;
  berthDetailsFormArray = new FormArray([]);
  public berthDetailsForm: FormGroup;
  columns :IDataTableColumn[];
  berthList;
  editMode: DATATABLE_EDITMODE;
  constructor(private fb: FormBuilder, private portMasterTransformationService: PortMasterTransformationService) { }


  
  /**
   *
   * Component lifecycle ngOnchanges
   *
   * @param {SimpleChanges} changes
   * @memberof AddBerthComponent
   */
  ngOnChanges(changes: SimpleChanges) {
    if (changes?.isSaveClicked?.currentValue) {
      this.berthDetailsForm.markAllAsTouched();
    }
  }

  /**
   * Component lifecycle ngoninit
   * @memberof AddBerthComponent
   */

  ngOnInit(): void {
    this.columns = this.portMasterTransformationService.getBerthGridColumns();
    this.columns[this.columns.length - 1]['actions'].push(DATATABLE_ACTION.DELETE); //ToDo.add permission.
    this.berthList = [] ; //ToDo
    this.editMode = DATATABLE_EDITMODE.CELL;  
    let berthList = this.initBerthListArray();
    const berthListArray = berthList.map((result, index) =>
      this.initBethsFormGroup(result, index)
    );  
    this.berthDetailsForm = this.fb.group({
      dataTable: this.fb.array([...berthListArray])
    });
  }

/**
 * Method to initialise berths formgroup
 *
 * @param {*} berths
 * @param {*} index
 * @return {*} 
 * @memberof AddBerthComponent
 */
initBethsFormGroup(berths: any, index: any) {
    return this.fb.group({
      berthName: this.fb.control(berths.berthName._value, [Validators.required]),
      berthDepth: this.fb.control(berths.berthDepth._value),
      maxLoa: this.fb.control(berths.maxLoa._value),
      maxDwt: this.fb.control(berths.maxDwt._value),
      maxManifoldHeight: this.fb.control(berths.maxManifoldHeight._value),
      minUkc: this.fb.control(berths.minUkc._value),
      restrictions: this.fb.control(berths.restrictions._value),
      id :this.fb.control(berths.id)    
    });
  }
  
  /**
   * Method to initialise berth list.
   *
   * @memberof AddBerthComponent
   */
  initBerthListArray()
   {
    const berthListArray = this.getBerthListAsValueObject(this.berthList)
    return berthListArray;
  }
 
  
  /**
   * Method to get berth list as value object.
   *
   * @param {*} element
   * @param {*} isVisible
   * @param {*} isEditMode
   * @param {*} isModified
   * @param {*} isEditable
   * @return {*} 
   * @memberof AddBerthComponent
   */
  getBerthListAsValueObject(berthList, isVisible = true, isEditMode = true, isModified = true, isEditable = true) {
    const _berthListArray = berthList.map((item) => {
      item.berthName = new ValueObject<string>(item.berthName, isVisible, isEditMode, isModified, isEditable);
      item.berthDepth = new ValueObject<string>(item.berthDepth, isVisible, isEditMode, isModified, isEditable);
      item.maxLoa = new ValueObject<string>(item.maxLoa, isVisible, isEditMode, isModified, isEditable);
      item.maxDwt = new ValueObject<string>(item.maxDwt, isVisible, isEditMode, isModified, isEditable);
      item.maxManifoldHeight = new ValueObject<string>(item.maxManifoldHeight, isVisible, isEditMode, isModified, isEditable);
      item.minUkc = new ValueObject<string>(item.minUkc, isVisible, isEditMode, isModified, isEditable);
      item.restrictions = new ValueObject<string>(item.restrictions, isVisible, isEditMode, isModified, isEditable);
      return item;
    })
    return _berthListArray;
  }


  /**
   * Method to mark all fields visited once.
   *
   * @memberof AddBerthComponent
   */

  markFormAsTouched() {
    if (this.isSaveClicked) {
      this.berthDetailsFormArray.markAllAsTouched();
    }
  }


  /**
   * Method to get validation/error messages.
   *
   * @memberof AddBerthComponent
   */
  getErrorMessages() {
    this.errorMessages = this.portMasterTransformationService.setValidationMessageForAddBerth();
  }

 
  /**
   * Method to set form value into portmaster service.
   *
   * @memberof AddBerthComponent
   */

  setFormValues() {
    this.portMasterTransformationService.setBerthFomDetails(this.berthDetailsFormArray);
  }

  /**
   *Method to add new berth.
   *
   * @memberof AddBerthComponent
   */

  addBerth() {
    const dataTableControl = <FormArray>this.berthDetailsForm.get('dataTable'); //ToDo
  }

  /**
   *Method to check for field errors
   *
   * @param {string} formControlName
   * @param {number} indexOfFormgroup
   * @return {ValidationErrors}
   * @memberof AddBerthComponent
   */
  fieldError(formControlName: string, indexOfFormgroup: number): ValidationErrors {
    const formControl = this.field(formControlName, indexOfFormgroup);
    return formControl?.invalid && (formControl.dirty || formControl.touched) ? formControl.errors : null;
  }

  /**
   *Method to get the field name
   *
   * @param {string} formControlName
   * @param {number} indexOfFormgroup
   * @return {FormControl}
   * @memberof AddBerthComponent
   */

  field(formControlName: string, indexOfFormgroup: number): FormControl {
    const formControl = <FormControl>this.berthDetailsFormArray["controls"][indexOfFormgroup].get(formControlName);
    return formControl;
  }


 /**
  * Method to call on delete row.
  *
  * @param {*} event
  * @memberof AddBerthComponent
  */
 onDeleteRow(event)
   {
     //ToDo
   }
}
