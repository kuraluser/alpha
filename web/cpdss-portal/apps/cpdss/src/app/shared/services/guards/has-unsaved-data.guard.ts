import { Injectable } from '@angular/core';
import { CanDeactivate } from '@angular/router';
import { ComponentCanDeactivate } from './component-can-deactivate';

@Injectable()
export class HasUnsavedDataGuard implements CanDeactivate<ComponentCanDeactivate> {
  canDeactivate(component: ComponentCanDeactivate): boolean {
    if (component?.hasUnsavedData && component?.hasUnsavedData()) {
      return confirm('You have some unsaved form data. Are you sure, you want to leave this page?');
    }
    return true;
  }
}