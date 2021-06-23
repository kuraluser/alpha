import { Component, Input, OnInit } from '@angular/core';
import { FormArray, FormGroup, FormControl, Validators, ValidationErrors } from '@angular/forms';
import { IValidationErrorMessages } from 'apps/cpdss/src/app/shared/components/validation-error/validation-error.model';
import { Subject } from 'rxjs';
import { RULE_TYPES } from '../../../models/cargo-planning-rules.model';


/**
 *
 * Component Class for cargo planning rules table.
 * @export
 * @class CargoPlanningRulesTableComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-cargo-planning-rules-table',
  templateUrl: './cargo-planning-rules-table.component.html',
  styleUrls: ['./cargo-planning-rules-table.component.scss']
})
export class CargoPlanningRulesTableComponent implements OnInit {


  @Input()
  get rules() {
    return this.cargoPlanningRules
  }
  set rules(rules) {
    this.cargoPlanningRules = JSON.parse(JSON.stringify(rules));
  }
  ngUnsubscribe: Subject<void> = new Subject();
  cargoPlanningRules: any;
  ruleTypes = [
    { value: RULE_TYPES.ABSOLUTE },
    { value: RULE_TYPES.PREFERRABLE }
  ]
  columns = [
    { header: "" },
    { header: "Rule Type" },
    { header: "Enable/Disable" }
  ];
  rulesForm: FormArray = new FormArray([])
  errorMessages: IValidationErrorMessages = {
    'required': 'SYNOPTICAL_REQUIRED',
    'invalid': 'SYNOPTICAL_INVALID',
  };

  /**
   * Componen lifecycle ngoninit.
   *
   * @memberof CargoPlanningRulesTableComponent
   */

  ngOnInit(): void {
    this.initForm()
    this.disableForm();
  }

  /**
 * Component lifecycle ngOnDestroy
 *
 * @returns {Promise<void>}
 * @memberof RulesTableComponent
 */

  ngOnDestroy() {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  /**
   * Method on typing the rule.
   *
   * @memberof CargoPlanningRulesTableComponent
   */
  onRuleTypeSelected() {
    //TODO.
  }

  /**
   * Method to intialise form.
   *
   * @memberof CargoPlanningRulesTableComponent
   */
  initForm() {
    this.rules.forEach(rule => {
      const formGroup = new FormGroup({})
      formGroup.addControl('ruleType', new FormControl(rule.ruleType))
      formGroup.addControl('enable', new FormControl(rule.enable))
      const formArray = new FormArray([])
      rule.inputs.forEach((input, inputIndex) => {
        formArray.push(new FormControl(input.value, Validators.required))
      });
      formGroup.addControl('inputs', formArray)
      this.rulesForm.push(formGroup)
    })
  }

  /**
   * Method to get control name.
   *
   * @param {string} key
   * @param {number} rowIndex
   * @param {number} [inputIndex=null]
   * @return {*}  {FormControl}
   * @memberof CargoPlanningRulesTableComponent
   */
  getControl(key: string, rowIndex: number, inputIndex: number = null): FormControl {
    if (inputIndex !== null) {
      return <FormControl>(<FormArray>(<FormGroup>this.rulesForm.at(rowIndex)).get(key)).at(inputIndex)
    }
    return <FormControl>(<FormGroup>this.rulesForm.at(rowIndex)).get(key)
  }

  /**
  * Method to get field errors
  *
  * @param {number} formGroupIndex
  * @param {string} formControlName
  * @returns {ValidationErrors}
  * @memberof CargoPlanningRulesTableComponent
  */
  fieldError(key: string, rowIndex: number, inputIndex: number = null): ValidationErrors {
    const formControl = this.getControl(key, rowIndex, inputIndex);
    return formControl.invalid && (formControl.dirty || formControl.touched) ? formControl.errors : null;
  }

  /**
   * Method to disable form.
   *
   * @memberof CargoPlanningRulesTableComponent
   */

  disableForm() {
    this.rules.forEach((rule, ruleIndex) => {
      if (rule.disable) {
        this.rulesForm.at(ruleIndex).disable()
      }
    })
  }

}
