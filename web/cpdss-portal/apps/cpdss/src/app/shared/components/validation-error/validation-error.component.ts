import { Component, Input, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { IValidationErrorMessages, IValidationErrors } from './validation-error.model';


/**
 * Component class for Validation error component
 *
 * @export
 * @class ValidationErrorComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-validation-error',
  templateUrl: './validation-error.component.html',
  styleUrls: ['./validation-error.component.scss']
})
export class ValidationErrorComponent implements OnInit {

  @Input()
  get errors(): IValidationErrors {
    return this._errors;
  }
  set errors(errors: IValidationErrors) {
    if (this.errors) {
      this.setErrors();
    }
    this._errors = errors;
  }

  @Input() errorMessages: IValidationErrorMessages;

  _errors: IValidationErrors;

  errorData: string;

  constructor(private translateService: TranslateService) { }

  ngOnInit() {
    if (this.errors) {
      this.setErrors();
    }
  }
  /**
   * Method for setting error component on respective form controll
   *
   * @memberof ValidationErrorComponent
   */
  setErrors() {
    const errorArr: string[] = [];
    for (const key in this.errors) {
      if (Object.prototype.hasOwnProperty.call(this.errors, key) && this.errorMessages) {
        this.translateService.get(this.errorMessages[key]).subscribe((response: string) => {
          errorArr.push(response);
          this.errorData = errorArr.join(`\n`);
        });
      }
    }
  }

}
