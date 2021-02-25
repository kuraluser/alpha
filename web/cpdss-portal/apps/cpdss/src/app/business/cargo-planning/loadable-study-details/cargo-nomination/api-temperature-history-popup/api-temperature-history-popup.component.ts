import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { IApiTemperatureHistory, ILoadingPopupData } from '../../../models/cargo-planning.model';
import { LoadableStudyDetailsTransformationService } from '../../../services/loadable-study-details-transformation.service';
import { IDataTableColumn } from './../../../../../shared/components/datatable/datatable.model';

/**
 * To show the History of cargo Api & Temperature
 *
 * @export
 * @class ApiTemperatureHistoryPopupComponent
 * @implements {OnInit}
 */
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

  @Input()
  get apiTempHistoryPopupData(): ILoadingPopupData {
    return this._apiTempHistoryPopupData;
  }
  set apiTempHistoryPopupData(apiTempHistoryPopupData: ILoadingPopupData) {
    this._apiTempHistoryPopupData = apiTempHistoryPopupData;
    this.getApiTemperatureHistoryData();
  }

  @Output() visibleChange = new EventEmitter<boolean>();

  apiTempHistoryColumns: IDataTableColumn[];
  apiTempHistoryData: IApiTemperatureHistory[];

  private _visible: boolean;
  private _apiTempHistoryPopupData: ILoadingPopupData;

  constructor(
    private loadableStudyDetailsTransformationService: LoadableStudyDetailsTransformationService,
  ) { }

  ngOnInit(): void {
    this.apiTempHistoryColumns = this.loadableStudyDetailsTransformationService.getCargoNominationApiTempHistoryColumns();
  }

  /**
   *function to get last 5, Api & Temperature History
   *
   * @memberof ApiTemperatureHistoryPopupComponent
   */
  getApiTemperatureHistoryData(): void {
    this.apiTempHistoryData = [
      {
        port: 'Ahamedi',
        date: '10-03-2020',
        api: 30.11,
        temp: 80.1
      },
      {
        port: 'Fujairah',
        date: '18-03-2020',
        api: 32.05,
        temp: 75.5
      },
      {
        port: 'Aalborg',
        date: '21-03-2020',
        api: 30.30,
        temp: 90.5
      }
    ];
  }

  closePopup() {
    this.visible = false;
    this.visibleChange.emit(this.visible);
  }

}
