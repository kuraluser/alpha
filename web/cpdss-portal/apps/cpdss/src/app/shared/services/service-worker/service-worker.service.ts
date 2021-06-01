import { ApplicationRef, Injectable } from '@angular/core';
import { SwUpdate } from '@angular/service-worker';
import { TranslateService } from '@ngx-translate/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { ConfirmationService, MessageService } from 'primeng/api';
import { concat, interval } from 'rxjs';
import { first } from 'rxjs/operators';
import { CPDSSDB } from '../../models/common.model';
import { SecurityService } from '../security/security.service';

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
    private confirmationService: ConfirmationService,
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
    this.updates.activated.subscribe(async event => {
      //Show success alert when service worker is activate
      const translationKeys = await this.translateService.get(['SERVICE_WORKER_ACTIVATION', 'SERVICE_WORKER_ACTIVATED_SUCCESSFULLY']).toPromise();
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
    this.updates.available.subscribe(async event => {
      this.confirmationService.close();
      const translationKeys = await this.translateService.get(['SERVICE_WORKER_UPDATE', 'SERVICE_WORKER_UPDATE_DETAILS', 'SERVICE_WORKER_CONFIRM_LABEL']).toPromise();

      this.confirmationService.confirm({
        key: 'confirmation-alert',
        header: translationKeys['SERVICE_WORKER_UPDATE'],
        message: translationKeys['SERVICE_WORKER_UPDATE_DETAILS'],
        icon: 'pi pi-exclamation-triangle',
        acceptLabel: translationKeys['SERVICE_WORKER_CONFIRM_LABEL'],
        acceptIcon: 'pi',
        acceptButtonStyleClass: 'btn btn-main mr-5',
        rejectVisible: false,
        accept: () => {
          this.ngxSpinnerService.show();
          // TODO: delete existing db logic
          this.cpdssDb?.delete().then(async () => {
            console.log("Database successfully deleted");
            const isOpen = await this.cpdssDb.open();
            if (isOpen) {
              //If opened set token in indexed db
              this.updates.activateUpdate().then(() => {
                SecurityService.initPropertiesDB(localStorage.getItem('token'));
                console.error("Service worker Reload");
                this.ngxSpinnerService.hide();
                document.location.reload();
              });
            }
          }).catch((err) => {
            console.error("Could not delete database");
            console.error("Service worker Reload");
            this.ngxSpinnerService.hide();
            document.location.reload();
          });
        }
      });

    });
  }
}
