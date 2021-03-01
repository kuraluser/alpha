import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { IApiTempHistory, IApiTempPopupData, ILoadingPopupData } from '../../../models/cargo-planning.model';
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
  get apiTempHistoryPopupData(): IApiTempPopupData {
    return this._apiTempHistoryPopupData;
  }
  set apiTempHistoryPopupData(apiTempHistoryPopupData: IApiTempPopupData) {
    this._apiTempHistoryPopupData = apiTempHistoryPopupData;
    this.getApiTempHistoryData();
  }

  @Output() visibleChange = new EventEmitter<boolean>();

  apiTempHistoryColumns: IDataTableColumn[];
  apiTempHistoryData: IApiTempHistory[];

  private _visible: boolean;
  private _apiTempHistoryPopupData: IApiTempPopupData;

  constructor(
    private loadableStudyDetailsTransformationService: LoadableStudyDetailsTransformationService,
  ) { }

  ngOnInit(): void {
    this.apiTempHistoryColumns = this.loadableStudyDetailsTransformationService.getCargoNominationApiTempHistoryColumns();
  }

  /**
   * function to get last 5, Api & Temperature History
   *
   * @memberof ApiTempHistoryPopupComponent
   */
  getApiTempHistoryData(): void {

    /**
     * This dummy data will remove after integrating the actual API
     */
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

  /**
   * function to hide month-wise Api-Temp history grid
   *
   * @memberof ApiTemperatureHistoryPopupComponent
   */
  hideShowMonthWiseApiTempHistoryGrid(): void {
  }

  /**
   * function to navigate to more cargo history grid
   *
   * @memberof ApiTemperatureHistoryPopupComponent
   */
  viewMore(): void {
    this.closePopup();
  }

  /**
   * function to close the api-temp history popup
   *
   * @memberof ApiTemperatureHistoryPopupComponent
   */
  closePopup() {
    this.visible = false;
    this.visibleChange.emit(this.visible);
  }

}
