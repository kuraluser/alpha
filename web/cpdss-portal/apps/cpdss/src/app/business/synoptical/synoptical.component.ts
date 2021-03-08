import { Component, OnInit } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { Router } from '@angular/router';
import { SynopticalService } from './services/synoptical.service';
import { AppConfigurationService } from '../../shared/services/app-configuration/app-configuration.service';
import { PermissionsService } from '../../shared/services/permissions/permissions.service';
import { IPermissionContext, PERMISSION_ACTION } from '../../shared/models/common.model';

/**
 * Component class of synoptical
 *
 * @export
 * @class SynopticalComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-synoptical',
  templateUrl: './synoptical.component.html',
  styleUrls: ['./synoptical.component.scss'],
  providers: [SynopticalService]
})
export class SynopticalComponent implements OnInit {

  public editRoleBtnPermissionContext: IPermissionContext;
  public saveRoleBtnPermissionContext: IPermissionContext;

  constructor(
    private ngxSpinnerService: NgxSpinnerService,
    public synopticalService: SynopticalService,
    private router: Router,
    private permissionsService: PermissionsService
  ) { }

  /**
   * Component lifecycle ngOnit
   *
   * @returns {Promise<void>}
   * @memberof SynopticalComponent
   */
  async ngOnInit(): Promise<void> {
    this.getPagePermission();
    this.ngxSpinnerService.show();
    await this.synopticalService.init();
    this.ngxSpinnerService.hide();
  }

    /**
   * Get page permission
   *
   * @memberof SynopticalComponent
   */
  getPagePermission() {
    this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['SynopticalComponent']);
    this.editRoleBtnPermissionContext = { key: AppConfigurationService.settings.permissionMapping['SynopticalComponent'], actions: [PERMISSION_ACTION.VIEW, PERMISSION_ACTION.EDIT] };
    this.saveRoleBtnPermissionContext = { key: AppConfigurationService.settings.permissionMapping['SynopticalComponent'], actions: [PERMISSION_ACTION.VIEW, PERMISSION_ACTION.ADD] };
  }

  /**
  * Show loadable study list based on selected voyage id
  */
  onVoyageSelected() {
    this.synopticalService.selectedLoadableStudy = null;
    this.synopticalService.loadableStudyId = null;
    this.synopticalService.selectedLoadablePattern = null;
    this.synopticalService.loadablePatternId = null; 
    this.synopticalService.getLoadableStudyInfo(this.synopticalService.vesselInfo?.id, this.synopticalService.selectedVoyage.id);
    this.router.navigateByUrl('/business/synoptical/' + this.synopticalService.vesselInfo.id + '/' + this.synopticalService.selectedVoyage.id)
  }

  /**
   * On selecting the loadable study
   *
   * @param event
   * @memberof SynopticalComponent
   */

  onSelectLoadableStudy() {
    this.synopticalService.selectedLoadablePattern = null;
    this.synopticalService.loadablePatternId = null;
    this.synopticalService.getLoadablePatterns();
    this.router.navigateByUrl('/business/synoptical/' + this.synopticalService.vesselInfo.id + '/' + this.synopticalService.selectedVoyage.id + '/' + this.synopticalService.selectedLoadableStudy.id)
  }

  /**
   * On selecting the loadable pattern
   *
   * @param event
   * @memberof SynopticalComponent
   */
  onSelectLoadablePattern() {
    this.router.navigateByUrl('/business/synoptical/' + this.synopticalService.vesselInfo.id + '/' + this.synopticalService.selectedVoyage.id + '/' + this.synopticalService.selectedLoadableStudy.id + '/' + this.synopticalService.selectedLoadablePattern.loadablePatternId)
  }

}
