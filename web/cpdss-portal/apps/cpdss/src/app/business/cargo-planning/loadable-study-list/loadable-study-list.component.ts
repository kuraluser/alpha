import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { IVessel } from '../../core/models/vessel-details.model';
import { VesselsApiService } from '../../core/services/vessels-api.service';
import { LoadableStudy } from '../models/loadable-study-list.model';
import { LoadableStudyListApiService } from '../services/loadable-study-list-api.service';
import { LOADABLE_STUDY_STATUS, Voyage, VOYAGE_STATUS } from '../../core/models/common.model';
import { VoyageService } from '../../core/services/voyage.service';
import { LoadableStudyListTransformationService } from '../services/loadable-study-list-transformation.service';
import { IDataTableColumn, IDataTableEvent } from '../../../shared/components/datatable/datatable.model';
import { NgxSpinnerService } from 'ngx-spinner';
import { Dropdown } from 'primeng/dropdown';
import { IPermission } from '../../../shared/models/user-profile.model';
import { PermissionsService } from '../../../shared/services/permissions/permissions.service';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { IPermissionContext, PERMISSION_ACTION } from '../../../shared/models/common.model';

/**
 * Loadable study list
 */
@Component({
  selector: 'cpdss-portal-loadable-study-list',
  templateUrl: './loadable-study-list.component.html',
  styleUrls: ['./loadable-study-list.component.scss']
})
export class LoadableStudyListComponent implements OnInit {
  loadableStudyList: LoadableStudy[];
  voyages: Voyage[];
  selectedVoyage: Voyage;
  selectedLoadableStudy: LoadableStudy;
  loading = true;
  cols: IDataTableColumn[];
  display = false;
  isEdit = false;
  isDuplicateExistingLoadableStudy = true;
  vesselDetails: IVessel;
  voyageId: number;
  columns: IDataTableColumn[];
  readonly editMode = null;
  isVoyageIdSelected = true;
  permission: IPermission;
  addLSBtnPermissionContext: IPermissionContext;
  VOYAGE_STATUS = VOYAGE_STATUS;

  constructor(private loadableStudyListApiService: LoadableStudyListApiService,
    private vesselsApiService: VesselsApiService, private router: Router,
    private translateService: TranslateService, private activatedRoute: ActivatedRoute,
    private voyageService: VoyageService, private loadableStudyListTransformationService: LoadableStudyListTransformationService,
    private ngxSpinnerService: NgxSpinnerService,
    private permissionsService: PermissionsService) { }

  async ngOnInit(): Promise<void> {
    this.activatedRoute.params.subscribe(async params => {
      this.voyageId = params.id ? Number(params.id) : 0;
      this.ngxSpinnerService.show();
      const res = await this.vesselsApiService.getVesselsInfo().toPromise();
      this.vesselDetails = res[0] ?? <IVessel>{};
      this.voyages = await this.voyageService.getVoyagesByVesselId(this.vesselDetails?.id).toPromise();
      this.ngxSpinnerService.hide();
      this.getLoadableStudyInfo(this.vesselDetails?.id, this.voyageId);
      this.selectedVoyage = this.voyages.find(voyage => voyage.id === this.voyageId);
    });
    this.permission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['LoadableStudyListComponent'], false);
    this.addLSBtnPermissionContext = { key: AppConfigurationService.settings.permissionMapping['LoadableStudyListComponent'], actions: [PERMISSION_ACTION.VIEW, PERMISSION_ACTION.ADD] };
    this.columns = this.loadableStudyListTransformationService.getLoadableStudyListDatatableColumns(this.permission, this.selectedVoyage?.statusId);
    this.loading = false;

  }
  /**
   * Take the user to particular loadable study
   */
  onRowSelect(event: IDataTableEvent) {
    if (event?.field !== 'actions') {
      this.router.navigate([`/business/cargo-planning/loadable-study-details/${this.vesselDetails?.id}/${this.selectedVoyage.id}/${event.data.id}`]);
    } else {
      this.callNewLoadableStudyPopup(true, event.data)
    }
  }

  /**
   * Get loadable study list
   */
  async getLoadableStudyInfo(vesselId: number, voyageId: number) {
    this.ngxSpinnerService.show();
    if (voyageId !== 0) {
      const result = await this.loadableStudyListApiService.getLoadableStudies(vesselId, voyageId).toPromise();
      this.loadableStudyList = result.loadableStudies.map(loadableStudy => {
        loadableStudy.isActionsEnabled = [LOADABLE_STUDY_STATUS.PLAN_PENDING, LOADABLE_STUDY_STATUS.PLAN_NO_SOLUTION, LOADABLE_STUDY_STATUS.PLAN_ERROR].includes(loadableStudy?.statusId) && ![VOYAGE_STATUS.CLOSE].includes(this.selectedVoyage?.statusId) ? true: false;
        return loadableStudy;
      });
    }
    this.ngxSpinnerService.hide();
  }

  // invoke popup which binds new-loadable-study-popup component
  callNewLoadableStudyPopup(isEdit, editLoadableStudy = null) {
    if (this.selectedVoyage) {
      this.selectedLoadableStudy = editLoadableStudy;
      this.isEdit = isEdit;
      this.display = true;
    }
    else
      this.isVoyageIdSelected = false;
  }

  // set visibility of popup (show/hide)
  setPopupVisibility(emittedValue) {
    this.display = emittedValue;
  }

  /**
   * Show loadable study list based on selected voyage id
   */
  showLoadableStudyList() {
    this.isVoyageIdSelected = true;
    this.getLoadableStudyInfo(this.vesselDetails?.id, this.selectedVoyage.id);
  }

  /**
   * called when name is clicked
   */
  columnClick(event: IDataTableEvent) {
    if (event?.field === 'actions') {
      this.callNewLoadableStudyPopup(true, event.data)
    } else {
      this.onRowSelect(event);
    }
  }

  /**
 * Handler for added new loadable study
 *
 * @param {*} event
 * @memberof LoadableStudyListComponent
 */
  onNewLoadableStudyAdded(event) {
    this.router.navigate(['business/cargo-planning/loadable-study-details/' + this.vesselDetails?.id + '/' + this.selectedVoyage.id + '/' + event]);
  }

  /**
   * Clear filter data
   */
  clearFilter(dropdown: Dropdown) {
    dropdown.resetFilter();
  }
}
