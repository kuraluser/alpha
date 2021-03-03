import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { IApiTempHistory, IApiTempPopupData, IMonths } from '../../../models/cargo-planning.model';
import { LoadableStudyDetailsTransformationService } from '../../../services/loadable-study-details-transformation.service';
import { IDataTableColumn } from './../../../../../shared/components/datatable/datatable.model';
import { FormBuilder, FormGroup } from '@angular/forms';
import { LoadableStudyDetailsApiService } from '../../../services/loadable-study-details-api.service';
import { DatePipe } from '@angular/common';

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
    this.ApiTempHistoryForm = this.fb.group({
      selectMonth: this.fb.control(null),
      selectPort: this.fb.control(null)
    })
  }

  @Output() visibleChange = new EventEmitter<boolean>();

  apiTempHistoryColumns: IDataTableColumn[];
  apiTempHistoryData: IApiTempHistory[] = [];
  apiTempHistoryMonths: IMonths[];
  ApiTempHistoryForm: FormGroup;

  private _visible: boolean;
  private _apiTempHistoryPopupData: IApiTempPopupData;

  constructor(
    private fb: FormBuilder,
    private datePipe: DatePipe,
    private loadableStudyDetailsApiService: LoadableStudyDetailsApiService,
    private loadableStudyDetailsTransformationService: LoadableStudyDetailsTransformationService,
  ) { }

  ngOnInit(): void {
    this.apiTempHistoryColumns = this.loadableStudyDetailsTransformationService.getCargoNominationApiTempHistoryColumns();
    this.apiTempHistoryMonths = this.loadableStudyDetailsTransformationService.getMonthList();
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
    let mockResponse = [{
      cargoId: 10,
      loadingPortId: 46,
      loadedDate: '01-10-2020 17:25',
      api: 30.11,
      temperature: 80.1
    }, {
      cargoId: 10,
      loadingPortId: 46,
      loadedDate: '30-09-2020 17:25',
      api: 32.18,
      temperature: 75.5
    }, {
      cargoId: 10,
      loadingPortId: 46,
      loadedDate: '29-09-2020 17:25',
      api: 30.25,
      temperature: 90.5
    }, {
      cargoId: 10,
      loadingPortId: 46,
      loadedDate: '28-09-2020 17:25',
      api: 31.36,
      temperature: 88.1
    }, {
      cargoId: 10,
      loadingPortId: 46,
      loadedDate: '27-09-2020 17:25',
      api: 33.25,
      temperature: 92.3
    }];
    const loadingPortArray = [...this.apiTempHistoryPopupData.rowDataCargo.value.ports];
    this.apiTempHistoryData = mockResponse.map(historyObj => {
      const loadingPort = loadingPortArray.find(port => port.id === historyObj.loadingPortId );
      const formattedDate = this.datePipe.transform(historyObj.loadedDate.replace(/(\d{2})-(\d{2})-(\d{4})/, "$2/$1/$3"), 'dd-MM-yyyy');
      return Object.assign(historyObj, {loadingPortName: loadingPort.name, loadedDate: formattedDate});
    });
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
    this.apiTempHistoryData = [];
    this.visible = false;
    this.visibleChange.emit(this.visible);
  }

}
