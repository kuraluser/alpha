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
  confirmAlert$ = this._confirmSource.asObservable();

  constructor(private messageService: MessageService) {
  }

  add(message: IConfirmationMessage) {
    this.messageService.add(message);
  }

  clear(key: string) {
    this.messageService.clear(key);
  }

  onConfirm(isConfirmed: boolean) {
    this._confirmSource.next(isConfirmed);
  }


}
