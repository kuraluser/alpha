import { Component, OnInit, Input } from '@angular/core';
import { DatePipe } from '@angular/common';
import { FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';

import { NgxSpinnerService } from 'ngx-spinner';

import { ILoadablePlanCommentsDetails, ISaveComment } from '../../models/loadable-plan.model';
import { IResponse } from '../../../../shared/models/common.model';

import { LoadablePlanApiService } from '../../services/loadable-plan-api.service';
import { SecurityService } from "../../../../shared/services/security/security.service";


/**
 * Component class of comments component in loadable plan
 *
 * @export
 * @class CommentsComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.scss']
})
export class CommentsComponent implements OnInit {
  @Input() vesselId: number;
  @Input() loadableStudyId: number;
  @Input() voyageId: number;
  @Input() loadablePatternId: number;

  @Input() set commentsDetails(value: ILoadablePlanCommentsDetails[]) {
    this._commentsDetails = value;
  }

  get commentsDetails(): ILoadablePlanCommentsDetails[] {
    return this._commentsDetails;
  }

  private _commentsDetails: ILoadablePlanCommentsDetails[];
  public commentForm: FormGroup;
  public formError: boolean;

  constructor(
    private loadablePlanApiService: LoadablePlanApiService,
    private datePipe: DatePipe,
    private fb: FormBuilder,
    private ngxSpinnerService: NgxSpinnerService,
  ) { }

  /**
   * Component lifecycle ngOnit
   *
   * @returns {void}
   * @memberof CommentsComponent
  */
  ngOnInit(): void {
    this.commentForm = this.fb.group({
      comment: [null, [Validators.required]]
    })
  }

  /**
   * add new comments
  */
  async submitComments() {
    if (this.commentForm.valid) {
      const comments: ISaveComment = {
        comment: this.commentForm.controls['comment'].value
      }
      const userProfile = SecurityService.getUserProfile();
      this.ngxSpinnerService.show();
      const response:IResponse = await this.loadablePlanApiService.saveComments(this.vesselId, this.voyageId, this.loadableStudyId, this.loadablePatternId , comments).toPromise();
      this.ngxSpinnerService.hide();
      if(response.responseStatus.status === '200') {
        let newCommentsData:ILoadablePlanCommentsDetails = {
          comment: this.commentForm.controls['comment'].value,
          userName: userProfile?.firstName + " " + userProfile?.lastName,
          dataAndTime: this.datePipe.transform(new Date(), "dd-MM-yyyy hh:mm"),
          id: 1
        }
        this._commentsDetails = [newCommentsData , ...this._commentsDetails];
        this.commentForm.reset();
        this.formError = false;
      }
    } else {
      this.formError = true;
    }
  }

  /**
  * Get field errors
  *
  * @param {string} formControlName
  * @returns {ValidationErrors}
  * @memberof CommentsComponent
  */
  fieldError(formControlName: string): ValidationErrors {
    const formControl = this.field(formControlName);
    return formControl.invalid && (formControl.dirty || formControl.touched) || this.formError ? formControl.errors : null;
  }

  /**
  * Get form control of comments form 
  *
  * @param {string} formControlName
  * @returns {FormControl}
  * @memberof CommentsComponent
  */
  field(formControlName: string): FormControl {
    const formControl = <FormControl>this.commentForm.get(formControlName);
    return formControl;
  }

}