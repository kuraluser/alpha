import { Injectable } from '@angular/core';
import { MessageService } from 'primeng/api';
import { Subject } from 'rxjs';
import { IConfirmationMessage } from './confirmation-alert.model';

/**
 * Service for Confirmation alert
 *
 * @export
 * @class ConfirmationAlertService
 */
@Injectable()
export class ConfirmationAlertService {

  private _confirmSource: Subject<boolean> = new Subject();
  private _showSource: Subject<boolean> = new Subject();

  confirmAlert$ = this._confirmSource.asObservable();
  showToast$ = this._showSource.asObservable();

  constructor(private messageService: MessageService) {
  }

  /**
   * Method for showing confirmation dialog
   *
   * @param {IConfirmationMessage} message
   * @memberof ConfirmationAlertService
   */
  add(message: IConfirmationMessage) {
    this._showSource.next(true);
    this.messageService.add({ ...message, closable: false });
  }

  /**
   * Method for clearing confirmation dialog
   *
   * @param {string} key
   * @memberof ConfirmationAlertService
   */
  clear(key: string) {
    this._showSource.next(false);
    this.messageService.clear(key);
  }

  /**
   * Method for setting user confirmation
   *
   * @param {boolean} isConfirmed
   * @memberof ConfirmationAlertService
   */
  onConfirm(isConfirmed: boolean) {
    this._confirmSource.next(isConfirmed);
  }


}
