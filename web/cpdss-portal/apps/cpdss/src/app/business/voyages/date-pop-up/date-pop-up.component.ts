import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { VoyageListApiService } from '../services/voyage-list-api.service';
import { VoyageListTransformationService } from '../services/voyage-list-transformation.service';

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
  startDate: Date;
  today = new Date();

  constructor(private voyageListApiService: VoyageListApiService,
    private voyageListTransformationService: VoyageListTransformationService,
    private translateService: TranslateService,
    private messageService: MessageService,
    private ngxSpinnerService: NgxSpinnerService) { }

  ngOnInit(): void {
  }

  /**
   * for closing active modal commingle popup
   *
   * @memberof DatePopUpComponent
  */
  closeDialog() {
    this.displayPopup.emit(false);
  }

  /**
  * for start voyage
  *
  * @memberof DatePopUpComponent
  */
  async startVoyage() {
    this.ngxSpinnerService.show();
    const translationKeys = await this.translateService.get(['VOYAGE_LIST_ACTIVE_VOYAGE_ERROR', 'VOYAGE_LIST_ACTIVE_VOYAGE_ERROR_EXIST', 'VOYAGE_LIST_ACTIVE_VOYAGE_ERROR_NO_CONFIRM_LOADABLE_STUDY', 'VOYAGE_LIST_ACTIVE_VOYAGE_SUCCESS', 'VOYAGE_LIST_ACTIVE_VOYAGE_SUCCESSFULLY_START']).toPromise();
    try {
      const result = await this.voyageListApiService.startVoyage(this.vesselId, this.voyageId, this.voyageListTransformationService.formatDateTime(this.startDate, true)).toPromise();
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
    this.closeDialog();
    this.ngxSpinnerService.hide();
  }
}
