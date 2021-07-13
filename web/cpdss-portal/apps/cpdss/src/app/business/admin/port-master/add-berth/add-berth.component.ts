import { Component, Input, OnInit, SimpleChanges, OnChanges } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
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
      this.berthDetailsFormArray.markAllAsTouched();
    }
  }

  /**
   * Component lifecycle ngoninit
   * @memberof AddBerthComponent
   */

  ngOnInit(): void {
    this.createForm();
    this.addBerth();
    this.markFormAsTouched();
    this.getErrorMessages();
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
   *Method to create form.
   *
   * @memberof AddBerthComponent
   */

  createForm() {
    this.berthDetailsForm = this.fb.group({
      'berthName': ['', [Validators.required, Validators.maxLength(100)]],
      'berthDepth': ['', [Validators.required, Validators.maxLength(100)]],
      'maxLoa': ['', [Validators.required, numberValidator(2, 2, false)]],
      'maxDwt': ['', [Validators.required, numberValidator(2, 2, false)]],
      'maxManifoldHeight': ['', [Validators.required, numberValidator(2, 2, false)]],
      'minUkc': ['', [Validators.required, numberValidator(2, 2, false)]],
      'restrictions': ['']
    });
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
    let berthDetailsFormGroup = this.fb.group({
      'berthName': ['', [Validators.required, Validators.maxLength(100)]],
      'berthDepth': ['', [Validators.required]],
      'maxLoa': ['', [Validators.required, numberValidator(2, 2, false)]],
      'maxDwt': ['', [Validators.required, numberValidator(2, 2, false)]],
      'maxManifoldHeight': ['', [Validators.required, numberValidator(2, 2, false)]],
      'minUkc': ['', [Validators.required, numberValidator(2, 2, false)]],
      'restrictions': ['', [Validators.maxLength(100)]]
    });

    this.berthDetailsFormArray.push(berthDetailsFormGroup);
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
   * Method to delete a berth
   *
   * @memberof AddBerthComponent
   */
  deleteBerth(indexOfBerth: number) {
    this.berthDetailsFormArray.removeAt(indexOfBerth)
  }
}
