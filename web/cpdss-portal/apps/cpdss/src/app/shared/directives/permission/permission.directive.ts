import { Directive, Input, Renderer2, TemplateRef, ViewContainerRef } from '@angular/core';
import { IPermissionContext, PERMISSION_ACTION } from '../../models/common.model';
import { PermissionsService } from '../../services/permissions/permissions.service';

/**
 * Directive for checking permission of elements
 *
 * @export
 * @class PermissionDirective
 */
@Directive({
  selector: '[cpdssPortalPermission]'
})
export class PermissionDirective {

  @Input() set cpdssPortalPermission(permissionContext: IPermissionContext) {
    if (permissionContext) {
      this.renderElement(permissionContext);
    }
  }

  constructor(private templateRef: TemplateRef<any>,
    private viewContainerRef: ViewContainerRef,
    private renderer: Renderer2,
    private permissionsService: PermissionsService) {
  }

  /**
   * Method for rendering element with permissions
   *
   * @param {IPermissionContext} permissionContext
   * @memberof PermissionDirective
   */
  renderElement(permissionContext: IPermissionContext) {
    const permissions = this.permissionsService.getPermission(permissionContext?.key, false);
    if (permissions) {
      if (permissions?.view !== null && !permissions?.view && permissionContext?.hideElementOnViewFalse) {
        this.viewContainerRef.clear();
      } else {
        const viewRef = this.viewContainerRef.createEmbeddedView(this.templateRef);
        const rootElem = viewRef.rootNodes[0];
        const tags = ['button', 'fieldset', 'optgroup', 'option', 'select', 'textarea', 'input'];
        permissionContext?.actions.forEach((action) => {
          if (permissionContext?.actions.includes(action)) {
            let permission;
            switch (action) {
              case PERMISSION_ACTION.VIEW:
                permission = !permissions?.view;
                break;
              case PERMISSION_ACTION.ADD:
                permission = !permissions?.add;
                break;
              case PERMISSION_ACTION.EDIT:
                permission = !permissions?.edit;
                break;
              case PERMISSION_ACTION.DELETE:
                permission = !permissions?.delete;
                break;
              default:
                permission = null;
                break;
            }
            const isDisableAtrributeElement = tags.includes(rootElem?.localName);
            if (permission) {
              if (isDisableAtrributeElement) {
                this.renderer.setProperty(rootElem, 'disabled', true);
              } else {
                this.renderer.addClass(rootElem, 'disabled');
              }
            } else {
              if (isDisableAtrributeElement) {
                this.renderer.setProperty(rootElem, 'disabled', false);
              } else {
                this.renderer.removeClass(rootElem, 'disabled');
              }
            }
          }
        });
      }
    }
  }

}
