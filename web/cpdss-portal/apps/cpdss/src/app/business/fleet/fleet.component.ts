import { Component, OnInit } from '@angular/core';

import { AppConfigurationService } from '../../shared/services/app-configuration/app-configuration.service';
import { PermissionsService } from '../../shared/services/permissions/permissions.service';

/**
 * Component class for Fleet module
 *
 * @export
 * @class FleetComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-fleet',
  templateUrl: './fleet.component.html',
  styleUrls: ['./fleet.component.scss']
})
export class FleetComponent implements OnInit {

  constructor(
    public permissionsService: PermissionsService
  ) { }

  ngOnInit(): void {
    this.getPagePermission();
  }

  /**
   * function to get page permission
   *
   * @memberof FleetComponent
   */
  getPagePermission() {
    this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['FleetComponent']);
  }

}
