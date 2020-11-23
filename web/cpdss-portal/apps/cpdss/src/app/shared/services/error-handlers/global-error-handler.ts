import { HttpErrorResponse } from '@angular/common/http';
import { ErrorHandler, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';

/**
 * Service for globally hadling errors
 *
 * @export
 * @class GlobalErrorHandler
 * @implements {ErrorHandler}
 */
@Injectable({
  providedIn: 'root'
})
export class GlobalErrorHandler implements ErrorHandler {

  constructor(private ngxSpinnerService: NgxSpinnerService,
    private messageService: MessageService,
    private router: Router,
    private translateService: TranslateService) { }

  /**
   * Method for handling error
   *
   * @private
   * @param {HttpErrorResponse} error
   * @memberof GlobalErrorHandler
   */
  handleError(error: HttpErrorResponse): void {
    switch (error.status) {
      case 400: this.handelBadRequests(error);
        break;
      case 401: this.handleUnAuthorized(error);
        break;
      case 403: this.handleAccessPrivilege();
        break;
      case 404: this.handlePageNotFound();
        break;
      case 500: this.handleInternalServer(error);
        break;
      case 504: this.handleConnectionTimeout();
        break;
    }
  }

  /**
   * Handler for 400
   *
   * @private
   * @param {*} error
   * @memberof GlobalErrorHandler
   */
  private handelBadRequests(error) {
    this.ngxSpinnerService.hide();
  }

  /**
   * Handler for 401
   *
   * @private
   * @param {*} error
   * @memberof GlobalErrorHandler
   */
  private handleUnAuthorized(error) {
    this.ngxSpinnerService.hide();
    this.router.navigate(['access-denied']);
  }

  /**
   * Handler for 403
   *
   * @private
   * @memberof GlobalErrorHandler
   */
  private handleAccessPrivilege() {
    this.ngxSpinnerService.hide();
    this.router.navigate(['access-denied']);
  }

  /**
   * Handler for 404
   *
   * @private
   * @memberof GlobalErrorHandler
   */
  private handlePageNotFound() {
    this.ngxSpinnerService.hide();
    this.router.navigate(['page-not-found']);
  }

  /**
   * Handler for 500
   *
   * @private
   * @param {*} error
   * @memberof GlobalErrorHandler
   */
  private async handleInternalServer(error) {
    this.ngxSpinnerService.hide();
    const translationKeys = await this.translateService.get(['SOMETHING_WENT_WRONG_ERROR', 'SOMETHING_WENT_WRONG']).toPromise();
    this.messageService.add({severity: 'error', summary: translationKeys['SOMETHING_WENT_WRONG_ERROR'], detail: translationKeys['SOMETHING_WENT_WRONG'], sticky: true});
  }

  /**
   * Handler for 504
   *
   * @private
   * @memberof GlobalErrorHandler
   */
  private async handleConnectionTimeout() {
    this.ngxSpinnerService.hide();
    const translationKeys = await this.translateService.get(['CONNECTION_TIMEOUT_ERROR', 'CONNECTION_TIMEOUT']).toPromise();
    this.messageService.add({severity: 'error', summary: translationKeys['CONNECTION_TIMEOUT_ERROR'], detail: translationKeys['CONNECTION_TIMEOUT'], sticky: true});
  }

}
