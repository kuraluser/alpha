import { Component, OnDestroy, OnInit } from '@angular/core';

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


  constructor() { }

  ngOnInit(): void {

  }

  ngOnDestroy() {
  }

}
