import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { FormArray, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { IValidationErrorMessages } from 'apps/cpdss/src/app/shared/components/validation-error/validation-error.model';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { RULE_TYPES } from '../../models/rules.model';
import { RulesService } from '../services/rules.service';

/**
 * Component Class for Rules Table
 *
 * @export
 * @class RulesTableComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-rules-table',
  templateUrl: './rules-table.component.html',
  styleUrls: ['./rules-table.component.scss']
})
export class RulesTableComponent implements OnInit, OnDestroy {
  @Input()
  get rules() {
    return this.adminRules
  }
  set rules(rules) {
    this.adminRules = JSON.parse(JSON.stringify(rules));
  }
  ngUnsubscribe: Subject<void> = new Subject();
  adminRules;
  ruleTypes = [
    { value: RULE_TYPES.ABSOLUTE },
    { value: RULE_TYPES.PREFERRABLE }
  ]
  columns = [
    { header: "Display in Settings" },
    { header: "" },
    { header: "Rule Type" },
    { header: "Enable/Disable" }
  ];
  rulesForm: FormArray = new FormArray([])
  errorMessages: IValidationErrorMessages = {
    'required': 'SYNOPTICAL_REQUIRED',
    'invalid': 'SYNOPTICAL_INVALID',
  };
  constructor(public rulesService: RulesService) { }

 /**
 * Component lifecycle ngOnInit
 *
 * @returns {Promise<void>}
 * @memberof RulesTableComponent
 */
ngOnInit(): void {
    this.initForm()
    this.disableForm()
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
 * Method to do actions on selecting a rule type 
 *
 * @returns {void}
 * @memberof RulesTableComponent
 */
  onRuleTypeSelected() {

  }

  /**
 * Method to init all action subscriptions 
 *
 * @returns {void}
 * @memberof RulesTableComponent
 */
  initActionSubscriptions() {
    this.rulesService.save
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(() => {
        this.saveChanges()
      })
  }

  /**
  * Method to save changes 
  *
  * @returns {void}
  * @memberof RulesTableComponent
  */
  saveChanges() {
  }

  /**
  * Method to init form controls based on the rules JSON 
  *
  * @returns {void}
  * @memberof RulesTableComponent
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
  * Method to get a control at a particular index 
  *
  * @returns {void}
  * @memberof RulesTableComponent
  */
  getControl(key: string, rowIndex: number, inputIndex: number = null): FormControl {
    if (inputIndex !== null) {
      return <FormControl>(<FormArray>(<FormGroup>this.rulesForm.at(rowIndex)).get(key)).at(inputIndex)
    }
    return <FormControl>(<FormGroup>this.rulesForm.at(rowIndex)).get(key)
  }

  /**
  * Get field errors
  *
  * @param {number} formGroupIndex
  * @param {string} formControlName
  * @returns {ValidationErrors}
  * @memberof RulesTableComponent
  */
  fieldError(key: string, rowIndex: number, inputIndex: number = null): ValidationErrors {
    const formControl = this.getControl(key, rowIndex, inputIndex);
    return formControl.invalid && (formControl.dirty || formControl.touched) ? formControl.errors : null;
  }

  /**
  * Method to disable form if specified in the rules JSON 
  *
  * @returns {void}
  * @memberof RulesTableComponent
  */
  disableForm() {
    this.rules.forEach((rule, ruleIndex) => {
      if (rule.disable) {
        this.rulesForm.at(ruleIndex).disable()
      }
    })
  }

}
