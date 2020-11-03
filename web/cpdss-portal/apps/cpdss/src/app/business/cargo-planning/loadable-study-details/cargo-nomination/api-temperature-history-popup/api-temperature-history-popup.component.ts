import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'cpdss-portal-api-temperature-history-popup',
  templateUrl: './api-temperature-history-popup.component.html',
  styleUrls: ['./api-temperature-history-popup.component.scss']
})
export class ApiTemperatureHistoryPopupComponent implements OnInit {

  @Input()
  get visible(): boolean {
    return this._visible;
  }
  set visible(visible: boolean) {
    this._visible = visible;
  }

  @Output() visibleChange = new EventEmitter<boolean>();

  private _visible: boolean;

  constructor() { }

  ngOnInit(): void {
  }

  closePopup() {
    this.visible = false;
    this.visibleChange.emit(this.visible);
  }

}
