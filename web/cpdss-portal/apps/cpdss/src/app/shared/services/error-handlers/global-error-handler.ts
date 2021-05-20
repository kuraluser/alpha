import { HttpErrorResponse } from '@angular/common/http';
import { ErrorHandler, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { AppConfigurationService } from '../app-configuration/app-configuration.service';
import { ConfirmationAlertService } from './../../../shared/components/confirmation-alert/confirmation-alert.service';
import { first } from 'rxjs/operators';
import { SecurityService } from '../security/security.service';

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
    private translateService: TranslateService,
    private confirmationAlertService: ConfirmationAlertService) { }

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
    if (error.error.errorCode === '210') {
      this.sessionOutMessage();
    } else {
      this.router.navigate(['access-denied']);
    }
  }

  /**
   * Handler session out
   *
   * @private
   * @memberof GlobalErrorHandler
   */
  public sessionOutMessage() {
    this.confirmationAlertService.clear('confirmation-alert');
    this.confirmationAlertService.add({ key: 'confirmation-alert', sticky: true, severity: 'warn', summary: 'SESSIONOUT_DIALOG_HEADER', detail: 'SESSIONOUT_DIALOG_BODY', data: { confirmLabel: 'SESSIONOUT_CONFIRM_LABEL' } });
    this.confirmationAlertService.confirmAlert$.pipe(first()).subscribe(async (response) => {
      if (response) {
        SecurityService.userLogoutAction();
        window.location.href = window.location.protocol + '//' + window.location.hostname + AppConfigurationService.settings.redirectPath;
      }
    });
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
  private async handlePageNotFound() {
    this.ngxSpinnerService.hide();
    const translationKeys = await this.translateService.get(['SOMETHING_WENT_WRONG_ERROR', 'SOMETHING_WENT_WRONG']).toPromise();
    this.messageService.add({ severity: 'error', summary: translationKeys['SOMETHING_WENT_WRONG_ERROR'], detail: translationKeys['SOMETHING_WENT_WRONG'], sticky: true });
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
    this.messageService.add({ severity: 'error', summary: translationKeys['SOMETHING_WENT_WRONG_ERROR'], detail: translationKeys['SOMETHING_WENT_WRONG'], sticky: true });
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
    this.messageService.add({ severity: 'error', summary: translationKeys['CONNECTION_TIMEOUT_ERROR'], detail: translationKeys['CONNECTION_TIMEOUT'], sticky: true });
  }

}
