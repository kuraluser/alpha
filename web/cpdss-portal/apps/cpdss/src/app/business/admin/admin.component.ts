import { Component, OnInit } from '@angular/core';
import { AppConfigurationService } from '../../shared/services/app-configuration/app-configuration.service';
import { PermissionsService } from '../../shared/services/permissions/permissions.service';

@Component({
  selector: 'cpdss-portal-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent implements OnInit {

  constructor(private permissionsService: PermissionsService) { }

  ngOnInit(): void {
    this.getPagePermission();
  }

  /**
   * Get page permission
   *
   * @memberof AdminComponent
   */
  getPagePermission() {
    this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['AdminComponent']);
  }


}
