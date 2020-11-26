import { Component, OnInit } from '@angular/core';
import { AppConfigurationService } from '../../shared/services/app-configuration/app-configuration.service';
import { PermissionsService } from '../../shared/services/permissions/permissions.service';

/**
 * Component class for cargo planning module
 *
 * @export
 * @class CargoPlanningComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-cargo-planning',
  templateUrl: './cargo-planning.component.html',
  styleUrls: ['./cargo-planning.component.scss']
})
export class CargoPlanningComponent implements OnInit {

  constructor(private permissionsService: PermissionsService) { }

  ngOnInit(): void {
    this.getPagePermission();
  }

  /**
   * Get page permission
   *
   * @memberof CargoPlanningComponent
   */
  getPagePermission() {
    this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['CargoPlanningComponent']);
  }


}
