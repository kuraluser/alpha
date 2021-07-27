import { Component, OnInit } from '@angular/core';
import { CanDeactivate } from '@angular/router';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ConfirmationService } from 'primeng/api';
import { Observer } from 'rxjs';
import { TranslateService } from '@ngx-translate/core';

export interface ComponentCanDeactivate {
  canDeactivate: () => boolean | Observable<boolean>;
}

@Injectable({
  providedIn: 'root'
})
export class UnsavedChangesGuard implements CanDeactivate<ComponentCanDeactivate> {
  constructor(
    private confirmationService: ConfirmationService,
    private translateService: TranslateService,
  ) { };

  canDeactivate(component: ComponentCanDeactivate): Promise<boolean> | boolean {
    return component.canDeactivate() ?
      true :
      // NOTE: this warning message will only be shown when navigating elsewhere within your angular app;
      // when navigating away from your angular app, the browser will show a generic warning message
      this.confirmPopUp();
  }


  async confirmPopUp() {
    const translationKeys = await this.translateService.get(['UNSAVED_CHANGES_HEADER', 'UNSAVED_CHANGES_MESSAGE', 'UNSAVED_CHANGES_CONFIRM_LABEL', 'UNSAVED_CHANGES_CANCEL_LABEL']).toPromise();
    return new Promise<boolean>((resolve, reject) => {
      this.confirmationService.confirm({
        key: 'confirmation-alert',
        header: translationKeys['UNSAVED_CHANGES_HEADER'],
        message: translationKeys['UNSAVED_CHANGES_MESSAGE'],
        icon: 'pi pi-exclamation-triangle',
        acceptLabel: translationKeys['UNSAVED_CHANGES_CONFIRM_LABEL'],
        acceptIcon: 'pi',
        acceptButtonStyleClass: 'btn btn-main mr-5',
        rejectVisible: true,
        rejectLabel: translationKeys['UNSAVED_CHANGES_CANCEL_LABEL'],
        rejectIcon: 'pi',
        rejectButtonStyleClass: 'btn btn-main',
        accept: () => {
          resolve(true);
        },
        reject: () => {
          resolve(false);
        }
      });
    });
  }
}