import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { ConfirmationAlertService } from '../../../shared/components/confirmation-alert/confirmation-alert.service';
import { VoyageListApiService } from '../services/voyage-list-api.service';
import { VoyageListTransformationService } from '../services/voyage-list-transformation.service';
import { first } from 'rxjs/operators';

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
  @Input() defaultDate: Date;
  @Input() isStart: boolean;

  header: string;
  startStopButtonLabel: string;
  today = new Date();

  constructor(private voyageListApiService: VoyageListApiService,
    private voyageListTransformationService: VoyageListTransformationService,
    private translateService: TranslateService,
    private messageService: MessageService,
    private ngxSpinnerService: NgxSpinnerService,
    private confirmationAlertService: ConfirmationAlertService) { }

  ngOnInit(): void {
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
    const translationKeys = await this.translateService.get(['VOYAGE_LIST_ACTIVE_VOYAGE_ERROR', 'VOYAGE_LIST_ACTIVE_VOYAGE_ERROR_EXIST', 'VOYAGE_LIST_ACTIVE_VOYAGE_ERROR_NO_CONFIRM_LOADABLE_STUDY', 'VOYAGE_LIST_ACTIVE_VOYAGE_SUCCESS', 'VOYAGE_LIST_ACTIVE_VOYAGE_SUCCESSFULLY_START', 'VOYAGE_LIST_ACTIVE_VOYAGE_SUCCESSFULLY_STOP', 'VOYAGE_LIST_ACTIVE_VOYAGE_SUCCESS']).toPromise();
    if (this.isStart) {
      try {
        const result = await this.voyageListApiService.startVoyage(this.vesselId, this.voyageId, this.voyageListTransformationService.formatDateTime(this.defaultDate, true)).toPromise();
        if (result.responseStatus.status === '200') {
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
      this.confirmationAlertService.add({ key: 'confirmation-alert', sticky: true, severity: 'warn', summary: 'VOYAGE_LIST_ACTIVE_VOYAGE_STOP_SUMMARY', detail: 'VOYAGE_LIST_ACTIVE_VOYAGE_STOP_DETAILS', data: { confirmLabel: 'VOYAGE_LIST_ACTIVE_VOYAGE_STOP_CONFIRM_LABEL', rejectLabel: 'VOYAGE_LIST_ACTIVE_VOYAGE_STOP_REJECTION_LABEL' } });
      this.confirmationAlertService.confirmAlert$.pipe(first()).subscribe(async (response) => {
        if (response) {

          this.ngxSpinnerService.show();
          try {
            const result = await this.voyageListApiService.endVoyage(this.vesselId, this.voyageId, this.voyageListTransformationService.formatDateTime(this.defaultDate, true)).toPromise();
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
