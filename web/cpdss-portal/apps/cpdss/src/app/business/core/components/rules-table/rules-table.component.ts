import { Component, EventEmitter, Input, OnDestroy, OnInit, Output, SimpleChanges } from '@angular/core';
import { FormArray, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { IValidationErrorMessages } from 'apps/cpdss/src/app/shared/components/validation-error/validation-error.model';
import { MessageService } from 'primeng/api';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { RULE_TYPES } from '../../models/rules.model'
import { numberValidator } from '../../../core/directives/number-validator.directive'
import { invalid } from '@angular/compiler/src/render3/view/util';
import {RulesValidator} from '../../../core/directives/rules-validator-directive'

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
 
  selectedTab = 'plan';
  data = 'plan';
  tabIndex = 1;
  ngUnsubscribe: Subject<void> = new Subject();
  adminRules;
  ruleTypes = [
    { value: RULE_TYPES.ABSOLUTE },
    { value: RULE_TYPES.PREFERRABLE }
  ]
  columns = [   
    { header: "" },
    { header: "Rule Type" },
    { header: "Enable/Disable" }
  ];
  error = [];
  errorProperty: any;
  selectedValueForRadio = []
  rulesForm: FormArray = new FormArray([])
  errorMessages: IValidationErrorMessages = {
    'required': 'RULES_REQUIRED',
  }
 
  selectedIndex = 0;
  @Input() rulesJson;
  @Input()rulesService;
  @Input() displaySettings = true;
  @Output() isSaveClicked: EventEmitter<any> = new EventEmitter();
  @Output() formChanges: EventEmitter<any> = new EventEmitter();
  @Input()isCancelChanges = false;
  rulesFormTemp :any;
  rules: any;

  constructor(private translateService: TranslateService,
    private messageService: MessageService,) { }

  /**
  * Component lifecycle ngOnInit
  *
  * @returns {Promise<void>}
  * @memberof RulesTableComponent
  */
  async ngOnInit(): Promise<void> {
    this.rulesService.init();
    this.showHideDisplaySetttingsColumn();
    this.initActionSubscriptions();  
    this.setIndex(this.selectedIndex);  
    this.initForm();
    this.disableForm();
    this.checkForFormValueChanges();  
    if (this.rulesJson &&  this.rulesJson[this.data] && this.rulesJson[this.data][this.selectedIndex])
      this.rules = this.rulesJson[this.data][this.selectedIndex].rules;  
  }

  /**
   * Method to show/hide DisplaySettings column
   *
   * @memberof RulesTableComponent
   */
  showHideDisplaySetttingsColumn()
  {
    if (this.displaySettings) {
      this.columns.unshift({ header: "Display in Settings" });
    }
  }

  /**
   * Component lifecycle ngonchanges.
   *
   * @param {SimpleChanges} changes
   * @memberof RulesTableComponent
   */
  ngOnChanges(changes: SimpleChanges) {  
    this.resetForm(changes); 
  }
  

  /**
   * Method to reset form when cancel changes button is clicked.
   *
   * @param {*} changes
   * @memberof RulesTableComponent
   */

  resetForm(changes) {   
    if (changes?.isCancelChanges?.currentValue) {
      this.initForm();
      this.disableForm(); 
    }
    if (changes?.rulesJson)
    { 
      
      this.initForm(); 
      this.selectedIndex = 0;
      this.rulesJson[this.data] = changes?.rulesJson?.currentValue?.plan;
      this.rules = this.rulesJson[this.data][this.selectedIndex].rules;  
      this.disableForm();     
    }
  }
 
/**
 * Method to check whether values of form has changed.
 *
 * @memberof RulesTableComponent
 */

  checkForFormValueChanges() {
    if (this.rulesForm.dirty) {
      this.formChanges.emit(true);
    }
  }

   
  /**
   * Method to set index.
   *
   * @param {*} index
   * @memberof RulesTableComponent
   */
  setIndex(index: any) {
    this.selectedIndex = index;
    this.setRules();
  }

  
 /**
  * Method to set rules.
  *
  * @memberof RulesTableComponent
  */

  setRules() {
    if (!this.rulesJson[this.data]) {
      this.rules = [];
    }
    else {
      if (this.rulesJson[this.data]) {
        this.rules = this.rulesJson[this.data][this.selectedIndex].rules;
      }
    }
  }

  /**
   * Method to set tab errors.
   *
   * @param {*} index
   * @memberof RulesTableComponent
   */
  setTabError(index: any) {
    if (this.rulesForm?.controls[index]?.valid == false && this.rulesForm?.controls[index]?.touched ) {
      this.error[index] = true;
    }
    else {
      this.error[index] = false;
    }
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
  async saveChanges() {
    for (let i = 0; i < this.rulesForm.controls.length; i++) {
      if (!this.rulesForm.controls[i].disabled) {
        this.rulesForm.controls[i].markAllAsTouched();
        this.setTabError(i);
      }
    }
    let isError = this.error.find(err => err == true);
    if (!isError) {
      for (let index = 0; index < this.rulesForm.controls.length; index++) {
        for (let j = 0; j < this.rulesForm.controls[index]['controls'].length; j++) {
          let controls = this.rulesForm.controls[index]['controls'][j];
          for (let i in controls['controls']) {
            if (i == 'inputs') {
              for (let t in controls["controls"]["inputs"].controls) {
                if (this.rulesJson[this.data][index] && this.rulesJson["plan"][index]["rules"][j]) {
                  if (this.rulesJson[this.data][index]["rules"][j]["inputs"] && this.rulesJson["plan"][index]["rules"][j]["inputs"][t]) {
                    if (Array.isArray(controls["controls"]["inputs"]["controls"][t].value)) {
                      this.rulesJson[this.data][index]["rules"][j]["inputs"][t].defaultValue = this.convertArrayIntoString(controls["controls"]["inputs"]["controls"][t].value, 'id');
                    }
                    else {
                      this.rulesJson[this.data][index]["rules"][j]["inputs"][t].defaultValue = controls["controls"]["inputs"]["controls"][t].value;
                    }
                  }
                  this.rulesJson[this.data][index]["rules"][j].enable = controls["controls"]["enable"].value;
                  if (this.rulesJson[this.data][index]["rules"][j]) {
                    this.rulesJson[this.data][index]["rules"][j].ruleType = controls["controls"]["ruleType"].value.value;
                  }

                  if (this.displaySettings) {
                    this.rulesJson[this.data][index]["rules"][j].displayInSettings = controls["controls"]["displayInSettings"].value;
                  }
                }
              }
            }

          }

        }
      }
      let postData = {
        [this.data]: this.rulesJson[this.data]
      }
      this.isSaveClicked.emit(postData);
    }
    else {
      let msgkeys = ['RULES_UPDATE_INVALID_HEADER', 'RULES_UPDATE_INVALID_FIELDS']
      let severity = 'warn';
      const translationKeys = await this.translateService.get(msgkeys).toPromise();
      this.messageService.add({ severity: severity, summary: translationKeys[msgkeys[0]], detail: translationKeys[msgkeys[1]] });
    }

  }

  /**
  * Method to init form controls based on the rules JSON 
  *
  * @returns {void}
  * @memberof RulesTableComponent
  */
  initForm() {
    let errorMessageGreater, errorMessageLesser;
      this.translateService.get(['ADMIN_RULE_GREATER_THAN', 'ADMIN_RULE_LESSER_THAN']).subscribe((res) => {
        errorMessageGreater = res['ADMIN_RULE_GREATER_THAN'];
        errorMessageLesser = res['ADMIN_RULE_LESSER_THAN'];
      })
    this.rulesForm = new FormArray([]);
    this.rulesJson[this.selectedTab].forEach(element => {
      let formArrayTemp = new FormArray([])
      element.rules.forEach((rule,rowIndex) => {
        const formGroup = new FormGroup({})
        formGroup.addControl('ruleType', new FormControl({value : rule.ruleType}))
        if (this.displaySettings) {
          formGroup.addControl('displayInSettings', new FormControl(rule.displayInSettings))
        }

        formGroup.addControl('enable', new FormControl(rule.enable))
        const formArray = new FormArray([])
        if (rule?.inputs.length > 0) {
          rule.inputs.forEach((input,inputIndex) => {           
            let value = null;
            if(input.type ==='String' || input.type==='Number' || input.type === 'number' || input.type==='Boolean')
            {
              if (input?.value) {
                value = input.value;


              }
              else if (input?.defaultValue) {
                value = input.defaultValue

              }
            }
          if(input.type==='Dropdown' && input.ruleDropDownMaster!=null){
            value = [];
             let defaultValuesArray = input?.defaultValue?.split(',');
             defaultValuesArray?.forEach(element => {
               let result = input?.ruleDropDownMaster?.find(r=>r.id===Number(element))
               value?.push(result);
             });
            }
            
            let validationArray = [];
            if (input.isMandatory && !input?.isHardRule == false) {
              validationArray.push(Validators.required);
            }

            if (input?.max) {
              validationArray.push(RulesValidator('inputs', rowIndex, inputIndex, input?.max, input?.min))
              let maxkey = rowIndex + 'input' + '_' + inputIndex + '_' + input?.max;
              this.errorMessages[maxkey] = errorMessageLesser + input?.max;
            }

            if (input?.min) {
              validationArray.push(RulesValidator('inputs', rowIndex, inputIndex, input?.max, input?.min))
              let minkey = rowIndex + 'input' + '_' + inputIndex + '_' + input?.min;
              this.errorMessages[minkey] = errorMessageGreater + input?.min;
            }
              
            
            if(input.type==='Number' || input.type=='number'){
             validationArray.push(numberValidator(2, 3))
            }
            formArray.push(new FormControl(value, validationArray))
          });

          formGroup.addControl('inputs', formArray)
        }
        formArrayTemp.push(formGroup);
      }
      )
      this.rulesForm.push(formArrayTemp);
    })
  
  }



  /**
  * Method to get a control at a particular index 
  *
  * @returns {void}
  * @memberof RulesTableComponent
  */
  getControl(key: string, rowIndex: number, inputIndex: number = null): FormControl {
   
    const temp: FormArray = <FormArray>this.rulesForm.at(this.selectedIndex);
    if (inputIndex !== null) {
      return <FormControl>(<FormArray>(<FormGroup>temp?.at(rowIndex))?.get(key))?.at(inputIndex)
    }
    return <FormControl>(<FormGroup>temp?.at(rowIndex))?.get(key);
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
 
    return formControl?.invalid && (formControl.dirty || formControl.touched) ? formControl.errors : null;
  }

  /**
  * Method to disable form if specified in the rules JSON 
  *
  * @returns {void}
  * @memberof RulesTableComponent
  */
  disableForm() {
    this.rules?.forEach((rule, ruleIndex) => {
      if (rule?.isHardRule) {
        this.rulesForm.at(ruleIndex).disable()
      }
    })
  }


  /**
   * Method on ruletypeselected.
   *
   * @memberof RulesTableComponent
   */
  onRuleTypeSelected() {
    this.checkForFormValueChanges()
  }
  
  /**
   *Method to convert array into string, seperated by commas based on property.
   *
   * @memberof RulesTableComponent
   */
  convertArrayIntoString(value:any, propertyName:any)
  {
   return Array.prototype.map.call(value, s => s[propertyName]).toString();
  }
    
}


