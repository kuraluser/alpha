import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { NgxSpinnerService } from 'ngx-spinner';

import { PermissionsService } from './../../../../../shared/services/permissions/permissions.service';
import { AppConfigurationService } from './../../../../../shared/services/app-configuration/app-configuration.service';
import { LoadableStudyDetailsApiService } from '../../../services/loadable-study-details-api.service';
import { LoadableStudyDetailsTransformationService } from '../../../services/loadable-study-details-transformation.service';

import { IPermission } from './../../../../../shared/models/user-profile.model';
import { IDataTableColumn } from './../../../../../shared/components/datatable/datatable.model';
import { IApiTempPortHistory, IApiTempMonthWiseHistory, IApiTempPopupData, ICargoApiTempHistoryResponse } from '../../../models/cargo-planning.model';
import * as moment from 'moment';
import { IMonth } from '../../../../../shared/models/common.model';

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
    const cargoId = apiTempHistoryPopupData.cargoId;
    const portIDs = [...apiTempHistoryPopupData.rowDataCargo].map(port => (port.portId));
    this.getApiTempHistoryData(cargoId, portIDs);
    this.apiTempHistoryForm = this.fb.group({
      selectMonth: this.fb.control(null),
      selectPort: this.fb.control(null)
    });
  }

  @Output() visibleChange = new EventEmitter<boolean>();

  apiTempHistoryColumns: IDataTableColumn[];
  apiTempHistoryData: IApiTempPortHistory[];
  monthwiseCargoHistory: IApiTempMonthWiseHistory[];
  apiTempHistoryMonths: IMonth[];
  apiTempHistoryForm: FormGroup;
  monthWithPreccedingSucceedingArr: IMonth[] = [];
  selectedPortID: number;
  filteredMonthwiseHistory: IApiTempMonthWiseHistory[] = [];
  hideMonthWiseGrid: boolean = true;
  uniqueYears: number[] = [];
  monthWiseGridColData: {} = {};
  userPermission: IPermission;

  private _visible: boolean;
  private _apiTempHistoryPopupData: IApiTempPopupData;

  constructor(
    private fb: FormBuilder,
    private loadableStudyDetailsApiService: LoadableStudyDetailsApiService,
    private loadableStudyDetailsTransformationService: LoadableStudyDetailsTransformationService,
    private userPermissionService: PermissionsService,
    private ngxSpinnerService: NgxSpinnerService
  ) { }

  ngOnInit(): void {
    this.apiTempHistoryColumns = this.loadableStudyDetailsTransformationService.getCargoNominationApiTempHistoryColumns();
    this.apiTempHistoryMonths = this.loadableStudyDetailsTransformationService.getMonthList();
    this.userPermission = this.userPermissionService.getPermission(AppConfigurationService.settings.permissionMapping['CargoHistoryComponent'], false);
  }

  /**
   * function to get last 5, Api & Temperature History
   * @memberof ApiTempHistoryPopupComponent
   */
  async getApiTempHistoryData(cargoId: number, portIDs: number[]) {
    this.ngxSpinnerService.show();
    const cargoApiTempHistoryDetails: ICargoApiTempHistoryResponse = await this.loadableStudyDetailsApiService.getCargoApiTemperatureHistoryDetails(this.apiTempHistoryPopupData.vesselId, this.apiTempHistoryPopupData.voyageId, this.apiTempHistoryPopupData.loadableStudyId, {cargoId: cargoId, loadingPortIds:portIDs}).toPromise();
    if (cargoApiTempHistoryDetails.responseStatus.status === '200') {
      const responsePortHistory: IApiTempPortHistory[] = cargoApiTempHistoryDetails.portHistory;
      if (responsePortHistory?.length) {
      const loadingPortArray = [...this.apiTempHistoryPopupData.rowDataCargo];
        this.apiTempHistoryData = responsePortHistory.map(historyObj => {
          const loadingPort = loadingPortArray.find(port => port.portId === historyObj.loadingPortId);
          const formattedDate = historyObj.loadedDate.split(' ')[0] ? moment(historyObj.loadedDate.split(' ')[0], 'DD-MM-YYYY').format(AppConfigurationService.settings?.dateFormat.split(' ')[0]) : '';
          return Object.assign(historyObj, { loadingPortName: loadingPort?.name, loadedDate: formattedDate });
        });
      } else {
        this.apiTempHistoryData = [];
      }
      this.monthwiseCargoHistory = cargoApiTempHistoryDetails.monthlyHistory;
    }
    this.ngxSpinnerService.hide();
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
    this.selectedPortID = event?.value?.portId;
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
      this.uniqueYears = [...new Set(this.monthwiseCargoHistory.map(data => data.loadedYear))];
      seletedMonths.forEach(month => {
        const filteredData = [...this.monthwiseCargoHistory].filter(obj => (month.id === obj.loadedMonth && obj.loadingPortId === this.selectedPortID));
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
    const uniqueYears = [...new Set(dataArray.map(obj => obj.loadedYear))];
    const uniqueMonths = [...new Set(selectedMonths.map(obj => obj.id))];
    let result: {} = {};
    uniqueYears.forEach(year => {
      const newArray: IApiTempMonthWiseHistory[] = dataArray.filter(data => (data.loadedYear === year));
      uniqueMonths.forEach(month => {
        if (!newArray.some(data => data.loadedMonth === month)) {
          const data = {
            loadingPortId: this.selectedPortID,
            loadedYear: year,
            loadedMonth: month,
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
    this.hideMonthWiseGrid = !this.hideMonthWiseGrid;
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
    this.hideMonthWiseGrid = true;
    this.visible = false;
    this.visibleChange.emit(this.visible);
  }

}
