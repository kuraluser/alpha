import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { IErrorMessage } from './error-log-popup.model';
@Component({
  selector: 'cpdss-portal-error-log-popup',
  templateUrl: './error-log-popup.component.html',
  styleUrls: ['./error-log-popup.component.scss']
})
export class ErrorLogPopupComponent implements OnInit {

  @Input()
  get visible(): boolean {
    return this._visible;
  }
  set visible(visible: boolean) {
    this._visible = visible;
  }
  @Input() header: string;
  @Input() subHeading: string;

  @Input()
  get errorMessage(): IErrorMessage[] {
    return this._errorMessage;
  }
  set errorMessage(visible: IErrorMessage[]) {
    this._errorMessage = visible;
  }

  @Output() visibleChange = new EventEmitter<boolean>();

  private _visible: boolean;
  private _errorMessage: IErrorMessage[];

  constructor() { }

  ngOnInit(): void {

  }

  /**
 * Method for closing popup
 *
 * @memberof ErrorLogPopupComponent
 */
  closePopup() {
    this.visible = false;
    this.visibleChange.emit(this.visible);
  }

}
