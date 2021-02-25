import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { ConfirmationAlertService } from './confirmation-alert.service';

/**
 * Component class for Confirmation modal
 *
 * @export
 * @class ConfirmationAlertComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-confirmation-alert',
  templateUrl: './confirmation-alert.component.html',
  styleUrls: ['./confirmation-alert.component.scss']
})
export class ConfirmationAlertComponent implements OnInit, OnDestroy {

  private subscriptions: Subscription = new Subscription();
  isActive: boolean;

  constructor(private confirmationAlertService: ConfirmationAlertService) { }

  ngOnInit(): void {
    this.subscriptions.add(this.confirmationAlertService.showToast$.subscribe((response) => {
      this.isActive = response;
    }));
  }

  /**
   * Handler for confirm button click
   *
   * @memberof ConfirmationAlertComponent
   */
  onConfirm() {
    this.confirmationAlertService.onConfirm(true);
    this.confirmationAlertService.clear('confirmation-alert');
  }

  /**
   * Handler for reject button click
   *
   * @memberof ConfirmationAlertComponent
   */
  onReject() {
    this.confirmationAlertService.onConfirm(false);
    this.confirmationAlertService.clear('confirmation-alert');
  }

  ngOnDestroy() {
    this.subscriptions.unsubscribe();
  }

}
