import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { ConfirmationService, MessageService } from 'primeng/api';
import { VoyageListApiService } from '../services/voyage-list-api.service';
import { VoyageListTransformationService } from '../services/voyage-list-transformation.service';
import { AppConfigurationService } from './../../../shared/services/app-configuration/app-configuration.service';
import { TimeZoneTransformationService } from '../../../shared/services/time-zone-conversion/time-zone-transformation.service';

/**
 * Component class for Date Popup compoent
 *
 * @export
 * @class DatePopUpComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-date-pop-up',
  templateUrl: './date-pop-up.component.html',
  styleUrls: ['./date-pop-up.component.scss']
})
export class DatePopUpComponent implements OnInit {

  @Output() displayPopup = new EventEmitter();

  @Input() display;
  @Input() voyageId: number;
  @Input() vesselId: number;
  @Input() isStart: boolean;
  @Input() loadablePlanId: number;
  @Input()
  get plannedStartDate(): Date {
    return this._plannedStartDate;
  }

  set plannedStartDate(value: Date) {
    this._plannedStartDate = value;
    const today = new Date();
    this.mindate = this.plannedStartDate > today ? today : this.plannedStartDate;
  }
  @Input()
  get defaultDate(): Date {
    return this._defaultDate;
  }

  set defaultDate(value: Date) {
    this.today = new Date();
    if(this.isStart) {
      this._defaultDate = value > this.today ? this.today : value;
    } else {
      this._defaultDate = value;
    }
  }

  header: string;
  startStopButtonLabel: string;
  today;
  mindate: Date;
  popupDateFormat: string;
  private _defaultDate: Date;
  private _plannedStartDate: Date;

  constructor(private voyageListApiService: VoyageListApiService,
    private voyageListTransformationService: VoyageListTransformationService,
    private timeZoneTransformationService: TimeZoneTransformationService,
    private translateService: TranslateService,
    private messageService: MessageService,
    private ngxSpinnerService: NgxSpinnerService,
    private confirmationService: ConfirmationService) { }

  ngOnInit(): void {
    this.popupDateFormat = this.timeZoneTransformationService.getMappedConfigurationDateFormat(AppConfigurationService.settings.dateFormat);
    this.header = this.isStart ? "VOYAGE_HISTORY_START_DATE_POPUP_HEADER" : "VOYAGE_HISTORY_END_DATE_POPUP_HEADER";
    this.startStopButtonLabel = this.isStart ? "VOYAGE_LIST_DATE_POPUP_START_BUTTON" : "VOYAGE_LIST_DATE_POPUP_STOP_BUTTON";
  }

  /**
   * for closing active modal commingle popup
   *
   * @memberof DatePopUpComponent
  */
  closeDialog(refresh = false) {
    const displayData = {
      showPopup: false,
      refresh: refresh
    }
    this.displayPopup.emit(displayData);
  }

  /**
  * for start voyage
  *
  * @memberof DatePopUpComponent
  */
  async startStopVoyage() {
    this.ngxSpinnerService.show();
    const translationKeys = await this.translateService.get(['VOYAGE_LIST_ACTIVE_VOYAGE_ERROR', 'VOYAGE_LIST_ACTIVE_VOYAGE_ERROR_EXIST', 'VOYAGE_LIST_ACTIVE_VOYAGE_ERROR_NO_CONFIRM_LOADABLE_STUDY', 'VOYAGE_LIST_ACTIVE_VOYAGE_SUCCESS', 'VOYAGE_LIST_ACTIVE_VOYAGE_SUCCESSFULLY_START', 'VOYAGE_LIST_ACTIVE_VOYAGE_SUCCESSFULLY_STOP', 'VOYAGE_LIST_ACTIVE_VOYAGE_SUCCESS', 'VOYAGE_LIST_ACTIVE_VOYAGE_STOP_SUMMARY', 'VOYAGE_LIST_ACTIVE_VOYAGE_STOP_DETAILS', 'VOYAGE_LIST_ACTIVE_VOYAGE_STOP_CONFIRM_LABEL', 'VOYAGE_LIST_ACTIVE_VOYAGE_STOP_REJECTION_LABEL']).toPromise();
    const formattedDate = this.timeZoneTransformationService.formatDateTime(this.defaultDate, { customFormat: 'DD-MM-YYYY HH:mm' });
    if (this.isStart) {
      try {
        const result = await this.voyageListApiService.startVoyage(this.vesselId, this.voyageId, formattedDate).toPromise();
        if (result.responseStatus.status === '200') {
          localStorage.setItem("vesselId", this.vesselId.toString());
          localStorage.setItem("voyageId", this.voyageId.toString());
          localStorage.setItem("loadableStudyId", this.loadablePlanId.toString());
          this.messageService.add({ severity: 'success', summary: translationKeys['VOYAGE_LIST_ACTIVE_VOYAGE_SUCCESS'], detail: translationKeys['VOYAGE_LIST_ACTIVE_VOYAGE_SUCCESSFULLY_START'] });
        }
      }
      catch (error) {
        if (error.error.errorCode === 'ERR-RICO-108') {
          this.messageService.add({ severity: 'error', summary: translationKeys['VOYAGE_LIST_ACTIVE_VOYAGE_ERROR'], detail: translationKeys['VOYAGE_LIST_ACTIVE_VOYAGE_ERROR_EXIST'] });
        }
        else if (error.error.errorCode === 'ERR-RICO-109') {
          this.messageService.add({ severity: 'error', summary: translationKeys['VOYAGE_LIST_ACTIVE_VOYAGE_ERROR'], detail: translationKeys['VOYAGE_LIST_ACTIVE_VOYAGE_ERROR_NO_CONFIRM_LOADABLE_STUDY'] });
        }
      }
      this.closeDialog(true);
    } else {
      this.confirmationService.confirm({
        key: 'confirmation-alert',
        header: translationKeys['VOYAGE_LIST_ACTIVE_VOYAGE_STOP_SUMMARY'],
        message: translationKeys['VOYAGE_LIST_ACTIVE_VOYAGE_STOP_DETAILS'],
        icon: 'pi pi-exclamation-triangle',
        acceptLabel: translationKeys['VOYAGE_LIST_ACTIVE_VOYAGE_STOP_CONFIRM_LABEL'],
        acceptIcon: 'pi',
        acceptButtonStyleClass: 'btn btn-main mr-5',
        rejectVisible: true,
        rejectLabel: translationKeys['VOYAGE_LIST_ACTIVE_VOYAGE_STOP_REJECTION_LABEL'],
        rejectIcon: 'pi',
        rejectButtonStyleClass: 'btn btn-main',
        accept: async () => {
          this.ngxSpinnerService.show();
          try {
            const result = await this.voyageListApiService.endVoyage(this.vesselId, this.voyageId, formattedDate).toPromise();
            if (result.responseStatus.status === '200') {
              this.messageService.add({ severity: 'success', summary: translationKeys['VOYAGE_LIST_ACTIVE_VOYAGE_SUCCESS'], detail: translationKeys['VOYAGE_LIST_ACTIVE_VOYAGE_SUCCESSFULLY_STOP'] });
            }
          } catch (error) {
          }
          this.closeDialog(true);
          this.ngxSpinnerService.hide();
        }
      });
    }
    this.ngxSpinnerService.hide();
  }
}
