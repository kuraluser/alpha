import { ApplicationRef, Injectable } from '@angular/core';
import { SwUpdate } from '@angular/service-worker';
import { TranslateService } from '@ngx-translate/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { concat, interval } from 'rxjs';
import { first } from 'rxjs/operators';
import { ConfirmationAlertService } from '../../components/confirmation-alert/confirmation-alert.service';
import { CPDSSDB } from '../../models/common.model';

/**
 * Service for handling service worker events
 *
 * @export
 * @class ServiceWorkerService
 */
@Injectable({
  providedIn: 'root'
})
export class ServiceWorkerService {
  cpdssDb: CPDSSDB;
  constructor(private appRef: ApplicationRef,
    private updates: SwUpdate,
    private confirmationAlertService: ConfirmationAlertService,
    private ngxSpinnerService: NgxSpinnerService,
    private messageService: MessageService,
    private translateService: TranslateService) {
    console.log("Service worker Enabled", this.updates.isEnabled);
    this.initSubscriptions();
    this.promptUpdate();
    this.cpdssDb = new CPDSSDB();
  }

  /**
   * Initialise all subscriptions
   *
   * @memberof ServiceWorkerService
   */
  async initSubscriptions() {
    const translationKeys = await this.translateService.get(['SERVICE_WORKER_ACTIVATION', 'SERVICE_WORKER_ACTIVATED_SUCCESSFULLY']).toPromise();
    this.updates.activated.subscribe(event => {
      this.messageService.add({ severity: 'success', summary: translationKeys['SERVICE_WORKER_ACTIVATION'], detail: translationKeys['SERVICE_WORKER_ACTIVATED_SUCCESSFULLY'] });
    });
  }

  /**
   * Method for cheking for service worker updates
   *
   * @memberof ServiceWorkerService
   */
  checkForUpdate() {
    // Allow the app to stabilize first, before starting polling for updates.
    const appIsStable$ = this.appRef.isStable.pipe(first(isStable => isStable === true));
    const everySixHours$ = interval(30000);
    const everySixHoursOnceAppIsStable$ = concat(appIsStable$, everySixHours$);
    everySixHoursOnceAppIsStable$.subscribe((isStable) => {
      if (isStable) {
        this.updates.checkForUpdate().then(() => {
        });
      }
    });
  }

  /**
   * Method for prompting user if there are any updates available and actiavte that update
   *
   * @memberof ServiceWorkerService
   */
  promptUpdate() {
    this.updates.available.subscribe(event => {
      this.confirmationAlertService.add({ key: 'confirmation-alert', sticky: true, severity: 'warn', summary: 'SERVICE_WORKER_UPDATE', detail: 'SERVICE_WORKER_UPDATE_DETAILS', data: { confirmLabel: 'SERVICE_WORKER_CONFIRM_LABEL' } });
      this.confirmationAlertService.confirmAlert$.pipe(first()).subscribe(async (response) => {
        if (response) {
          this.ngxSpinnerService.show();
          this.updates.activateUpdate().then(() => {
            // TODO: delete existing db logic
            this.cpdssDb?.delete().then(() => {
              console.log("Database successfully deleted");
              this.cpdssDb.open();
            }).catch((err) => {
              console.error("Could not delete database");
            }).finally(() => {
              this.ngxSpinnerService.hide();
              document.location.reload();
            });
          });
        }
      });

    });
  }
}
