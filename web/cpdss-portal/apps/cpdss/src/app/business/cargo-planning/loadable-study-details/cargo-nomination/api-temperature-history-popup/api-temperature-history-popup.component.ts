import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { IApiTempHistory, IApiTempMonthWiseHistory, IApiTempPopupData, ICargoApiTempHistoryResponse, IMonths } from '../../../models/cargo-planning.model';
import { LoadableStudyDetailsTransformationService } from '../../../services/loadable-study-details-transformation.service';
import { IDataTableColumn } from './../../../../../shared/components/datatable/datatable.model';
import { FormBuilder, FormGroup } from '@angular/forms';
import { LoadableStudyDetailsApiService } from '../../../services/loadable-study-details-api.service';
import { DatePipe } from '@angular/common';

/**
 * To show the History of cargo Api & Temperature
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
    const cargoId = apiTempHistoryPopupData.rowDataCargo.value.id;
    const portIDs = [...apiTempHistoryPopupData.rowDataCargo.value.ports].map(port => (port.id));
    this.getApiTempHistoryData(cargoId, portIDs);
    this.ApiTempHistoryForm = this.fb.group({
      selectMonth: this.fb.control(null),
      selectPort: this.fb.control(null)
    })
  }

  @Output() visibleChange = new EventEmitter<boolean>();

  apiTempHistoryColumns: IDataTableColumn[];
  apiTempHistoryData: IApiTempHistory[] = [];
  monthwiseCargoHistory: IApiTempMonthWiseHistory[];
  apiTempHistoryMonths: IMonths[];
  ApiTempHistoryForm: FormGroup;
  monthWithPreccedingSucceedingArr: IMonths[] = [];
  selectedPortID: number;
  filteredMonthwiseHistory: IApiTempMonthWiseHistory[] = [];
  showMonthWiseGrid: boolean = false;
  uniqueYears: number[] = [];
  monthWiseGridColData: {} = {};

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
   * @memberof ApiTempHistoryPopupComponent
   */
  async getApiTempHistoryData(cargoId: number, portIDs: number[]) {
    // const cargoApiTempHistoryDetails: ICargoApiTempHistoryResponse = await this.loadableStudyDetailsApiService.getCargoApiTemperatureHistoryDetails(cargoId, portIDs).toPromise();

    /**
     * This dummy data will remove and use above commented code after integrating the actual API
     */
    const cargoApiTempHistoryDetails: ICargoApiTempHistoryResponse = {
      responseStatus: { status: "200" },
      portHistory: [{
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
      }],
      monthlyHistory: [{
        loadingPortId: 359,
        loadingYear: 2020,
        loadingMonth: 1,
        api: 33.25,
        temperature: 92.3
      }, {
        loadingPortId: 359,
        loadingYear: 2019,
        loadingMonth: 1,
        api: 32.25,
        temperature: 92.3
      }, {
        loadingPortId: 359,
        loadingYear: 2018,
        loadingMonth: 1,
        api: 31.25,
        temperature: 92.3
      }, {
        loadingPortId: 359,
        loadingYear: 2017,
        loadingMonth: 1,
        api: 30.25,
        temperature: 92.3
      }, {
        loadingPortId: 359,
        loadingYear: 2016,
        loadingMonth: 1,
        api: 29.25,
        temperature: 92.3
      }, {
        loadingPortId: 46,
        loadingYear: 2020,
        loadingMonth: 1,
        api: 33.25,
        temperature: 92.3
      }, {
        loadingPortId: 46,
        loadingYear: 2019,
        loadingMonth: 1,
        api: 32.25,
        temperature: 92.3
      }, {
        loadingPortId: 46,
        loadingYear: 2018,
        loadingMonth: 1,
        api: 31.25,
        temperature: 92.3
      }, {
        loadingPortId: 46,
        loadingYear: 2017,
        loadingMonth: 1,
        api: 30.25,
        temperature: 92.3
      }, {
        loadingPortId: 46,
        loadingYear: 2016,
        loadingMonth: 1,
        api: 29.25,
        temperature: 92.3
      }, {
        loadingPortId: 359,
        loadingYear: 2020,
        loadingMonth: 2,
        api: 33.25,
        temperature: 92.3
      }, {
        loadingPortId: 359,
        loadingYear: 2019,
        loadingMonth: 2,
        api: 32.25,
        temperature: 92.3
      }, {
        loadingPortId: 359,
        loadingYear: 2018,
        loadingMonth: 2,
        api: 31.25,
        temperature: 92.3
      }, {
        loadingPortId: 359,
        loadingYear: 2017,
        loadingMonth: 2,
        api: 30.25,
        temperature: 92.3
      }, {
        loadingPortId: 359,
        loadingYear: 2016,
        loadingMonth: 2,
        api: 29.25,
        temperature: 92.3
      }, {
        loadingPortId: 46,
        loadingYear: 2020,
        loadingMonth: 2,
        api: 33.25,
        temperature: 92.3
      }, {
        loadingPortId: 46,
        loadingYear: 2019,
        loadingMonth: 2,
        api: 32.25,
        temperature: 92.3
      }, {
        loadingPortId: 46,
        loadingYear: 2018,
        loadingMonth: 2,
        api: 31.25,
        temperature: 92.3
      }, {
        loadingPortId: 46,
        loadingYear: 2017,
        loadingMonth: 2,
        api: 30.25,
        temperature: 92.3
      }, {
        loadingPortId: 46,
        loadingYear: 2016,
        loadingMonth: 2,
        api: 29.25,
        temperature: 92.3
      }, {
        loadingPortId: 359,
        loadingYear: 2020,
        loadingMonth: 3,
        api: 33.25,
        temperature: 92.3
      }, {
        loadingPortId: 359,
        loadingYear: 2019,
        loadingMonth: 3,
        api: 32.25,
        temperature: 92.3
      }, {
        loadingPortId: 359,
        loadingYear: 2018,
        loadingMonth: 3,
        api: 31.25,
        temperature: 92.3
      }, {
        loadingPortId: 359,
        loadingYear: 2017,
        loadingMonth: 3,
        api: 30.25,
        temperature: 92.3
      }, {
        loadingPortId: 359,
        loadingYear: 2016,
        loadingMonth: 3,
        api: 29.25,
        temperature: 92.3
      }, {
        loadingPortId: 46,
        loadingYear: 2020,
        loadingMonth: 3,
        api: 33.25,
        temperature: 92.3
      }, {
        loadingPortId: 46,
        loadingYear: 2019,
        loadingMonth: 3,
        api: 32.25,
        temperature: 92.3
      }, {
        loadingPortId: 46,
        loadingYear: 2018,
        loadingMonth: 3,
        api: 31.25,
        temperature: 92.3
      }, {
        loadingPortId: 46,
        loadingYear: 2017,
        loadingMonth: 3,
        api: 30.25,
        temperature: 92.3
      }, {
        loadingPortId: 46,
        loadingYear: 2016,
        loadingMonth: 3,
        api: 29.25,
        temperature: 92.3
      }, {
        loadingPortId: 46,
        loadingYear: 2019,
        loadingMonth: 12,
        api: 23.25,
        temperature: 92.3
      }, {
        loadingPortId: 46,
        loadingYear: 2018,
        loadingMonth: 12,
        api: 22.25,
        temperature: 92.3
      }, {
        loadingPortId: 46,
        loadingYear: 2017,
        loadingMonth: 12,
        api: 21.25,
        temperature: 92.3
      }, {
        loadingPortId: 46,
        loadingYear: 2016,
        loadingMonth: 12,
        api: 20.25,
        temperature: 92.3
      }
      ]
    };
    if (cargoApiTempHistoryDetails.responseStatus.status === '200') {
      const mockResponse = cargoApiTempHistoryDetails?.portHistory;
      this.monthwiseCargoHistory = cargoApiTempHistoryDetails?.monthlyHistory;
      if (mockResponse.length) {
        const loadingPortArray = [...this.apiTempHistoryPopupData.rowDataCargo.value.ports];
        this.apiTempHistoryData = mockResponse.map(historyObj => {
          const loadingPort = loadingPortArray.find(port => port.id === historyObj.loadingPortId);
          const formattedDate = this.datePipe.transform(historyObj.loadedDate.replace(/(\d{2})-(\d{2})-(\d{4})/, "$2/$1/$3"), 'dd-MM-yyyy');
          return Object.assign(historyObj, { loadingPortName: loadingPort.name, loadedDate: formattedDate });
        });
      }
    }
  }

  /**
   * function to do on select/change month.
   * generate the preceding & succeding months with selected month.
   * @param {*} event
   * @memberof ApiTemperatureHistoryPopupComponent
   */
  onMonthChange(event): void {
    this.monthWithPreccedingSucceedingArr = [];
    const selectedMonth = this.apiTempHistoryMonths.find(month => (month.id === event?.value?.id));
    const precedingMonth = selectedMonth.id == 1 ? this.apiTempHistoryMonths.find(month => (month.id === 12)) : this.apiTempHistoryMonths.find(month => (month.id === selectedMonth.id - 1));
    const succeedingMonth = selectedMonth.id == 12 ? this.apiTempHistoryMonths.find(month => (month.id === 1)) : this.apiTempHistoryMonths.find(month => (month.id === selectedMonth.id + 1));
    this.monthWithPreccedingSucceedingArr.push(precedingMonth, selectedMonth, succeedingMonth);
    this.getMonthWiseApiTempHistoryData();
  }

  /**
   * function to do on select/change port
   * @param {*} event
   * @memberof ApiTemperatureHistoryPopupComponent
   */
  onPortChange(event): void {
    this.selectedPortID = event?.value?.id;
    this.getMonthWiseApiTempHistoryData();
  }

  /**
   * function to show month-wise Api & Temperature History
   * @memberof ApiTemperatureHistoryPopupComponent
   */
  getMonthWiseApiTempHistoryData(): void {
    this.filteredMonthwiseHistory = [];
    this.monthWiseGridColData = {};
    if (this.monthWithPreccedingSucceedingArr.length && this.selectedPortID) {
      const seletedMonths = [... this.monthWithPreccedingSucceedingArr];
      this.uniqueYears = [...new Set(this.monthwiseCargoHistory.map(data => data.loadingYear))];
      seletedMonths.forEach(month => {
        const filteredData = [...this.monthwiseCargoHistory].filter(obj => (month.id === obj.loadingMonth && obj.loadingPortId === this.selectedPortID));
        this.filteredMonthwiseHistory = this.filteredMonthwiseHistory.concat(filteredData);
      });
      if (this.filteredMonthwiseHistory.length) {
        const groupedByYear = this.groupArrayBasedKey(this.filteredMonthwiseHistory);
        this.monthWiseGridColData = groupedByYear;
      }
    }
  }

  /**
   * function to group month-wise history based on property
   * @param {IApiTempMonthWiseHistory[]} dataArray
   * @param {string} keyParam
   * @return {*}  {boolean}
   * @memberof ApiTemperatureHistoryPopupComponent
   */
  groupArrayBasedKey(dataArray: IApiTempMonthWiseHistory[]): object {
    const selectedMonths = [...this.monthWithPreccedingSucceedingArr];
    const uniqueYears = [...new Set(dataArray.map(obj => obj.loadingYear))];
    const uniqueMonths = [...new Set(selectedMonths.map(obj => obj.id))];
    let result: {} = {};
    uniqueYears.forEach(year => {
      const newArray: IApiTempMonthWiseHistory[] = dataArray.filter(data => (data.loadingYear === year));
      uniqueMonths.forEach(month => {
        if (!newArray.some(data => data.loadingMonth === month)) {
          const data = {
            loadingPortId: this.selectedPortID,
            loadingYear: year,
            loadingMonth: month,
            api: '-',
            temperature: '-'
          }
          const position = selectedMonths.map(month => (month.id)).indexOf(month);
          newArray.splice(position, 0, data);
        }
      });
      result[year] = newArray;
    });
    return result;
  }

  /**
   * function to hide month-wise Api-Temp history grid
   * @memberof ApiTemperatureHistoryPopupComponent
   */
  hideShowMonthWiseApiTempHistoryGrid(): void {
    this.showMonthWiseGrid = !this.showMonthWiseGrid;
  }

  /**
   * function to navigate to more cargo history grid
   * @memberof ApiTemperatureHistoryPopupComponent
   */
  viewMore(): void {
    this.closePopup();
  }

  /**
   * function to close the api-temp history popup
   * @memberof ApiTemperatureHistoryPopupComponent
   */
  closePopup() {
    this.selectedPortID = undefined;
    this.monthWiseGridColData = {};
    this.apiTempHistoryData = [];
    this.monthwiseCargoHistory = [];
    this.filteredMonthwiseHistory = [];
    this.monthWithPreccedingSucceedingArr = [];
    this.uniqueYears = [];
    this.visible = false;
    this.visibleChange.emit(this.visible);
  }

}
