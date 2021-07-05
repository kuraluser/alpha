import { Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { ConfirmationService, MessageService } from 'primeng/api';
import { DATATABLE_SELECTIONMODE, IDataTableColumn } from '../../../../shared/components/datatable/datatable.model';
import { LoadableStudy } from '../../../cargo-planning/models/loadable-study-list.model';
import { LoadableStudyDetailsTransformationService } from '../../../cargo-planning/services/loadable-study-details-transformation.service';
import { LoadableStudyListApiService } from '../../../cargo-planning/services/loadable-study-list-api.service';
import { Voyage, VOYAGE_STATUS } from '../../../core/models/common.model';
import { IVessel } from '../../../core/models/vessel-details.model';
import { IPermission } from '../../../../shared/models/user-profile.model';
import { IPermissionContext, PERMISSION_ACTION } from '../../../../shared/models/common.model';
import { PermissionsService } from '../../../../shared/services/permissions/permissions.service';
import { AppConfigurationService } from '../../../../shared/services/app-configuration/app-configuration.service';

/**
 * Component class for side pane
 *
 * @export
 * @class SidePanelLoadableStudyListComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-side-panel-loadable-study-list',
  templateUrl: './side-panel-loadable-study-list.component.html',
  styleUrls: ['./side-panel-loadable-study-list.component.scss']
})
export class SidePanelLoadableStudyListComponent implements OnInit {

  @ViewChild('sidepaneDatatable') sidepaneDatatable: ElementRef;
  @Input()
  get loadableStudies(): LoadableStudy[] {
    return this._loadableStudies;
  }
  set loadableStudies(loadableStudies: LoadableStudy[]) {
    this._loadableStudies = loadableStudies;
    this.getGridColumns();
  }

  @Input()
  get selectedLoadableStudy(): LoadableStudy {
    return this._selectedLoadableStudy;
  }
  set selectedLoadableStudy(selectedLoadableStudy: LoadableStudy) {
    this._selectedLoadableStudy = selectedLoadableStudy;
    const selectedLoadableStudyIndex = this.loadableStudies?.findIndex(loadableStudy => loadableStudy?.id === selectedLoadableStudy?.id);
    this.scrollToSelectedRow(selectedLoadableStudyIndex);
  }

  @Input() voyage: Voyage;
  @Input() vesselInfo: IVessel;

  @Output() selectedLoadableStudyChange = new EventEmitter<LoadableStudy>();
  @Output() deleteLoadableStudy = new EventEmitter<Event>();
  @Output() newLoadableStudyAdded = new EventEmitter<LoadableStudy>();

  columns: IDataTableColumn[];
  display = false;
  isEdit = false;
  duplicateLoadableStudy: LoadableStudy;
  selectionMode = DATATABLE_SELECTIONMODE.SINGLE;
  permission: IPermission;
  addLSBtnPermissionContext: IPermissionContext;
  VOYAGE_STATUS = VOYAGE_STATUS;


  private _loadableStudies: LoadableStudy[];
  private _selectedLoadableStudy: LoadableStudy;

  constructor(
    private loadableStudyListApiService: LoadableStudyListApiService,
    private loadableStudyDetailsTransformationService: LoadableStudyDetailsTransformationService,
    private translateService: TranslateService,
    private messageService: MessageService,
    private ngxSpinnerService: NgxSpinnerService,
    private confirmationService: ConfirmationService,
    private permissionsService: PermissionsService) { }

  ngOnInit(): void {
    this.duplicateLoadableStudy = <LoadableStudy>{};
    this.permission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['LoadableStudyListComponent'], false);
    this.addLSBtnPermissionContext = { key: AppConfigurationService.settings.permissionMapping['NewLoadableStudy'], actions: [PERMISSION_ACTION.VIEW] };
    this.getGridColumns();
  }

  /**
   * Get all grid columns
   *
   * @memberof SidePanelLoadableStudyListComponent
   */
  getGridColumns() {
    this.columns = this.loadableStudyDetailsTransformationService.getLoadableStudyGridColumns(this.permission, this.voyage?.statusId);
  }

  /**
   * Handler for row duplicate event
   *
   * @param {*} event
   * @memberof SidePanelLoadableStudyListComponent
   */
  onDuplicate(event) {
    this.duplicateLoadableStudy = event?.data;
    this.isEdit = false;
    this.display = true;
  }

  /**
   * Handler for row delete event
   *
   * @param {*} event
   * @memberof SidePanelLoadableStudyListComponent
   */
  async onDelete(event) {
    const translationKeys = await this.translateService.get(['LOADABLE_STUDY_DELETE_SUCCESS', 'LOADABLE_STUDY_DELETE_SUCCESSFULLY', 'LOADABLE_STUDY_DELETE_ERROR', 'LOADABLE_STUDY_DELETE_STATUS_ERROR', 'LOADABLE_STUDY_DELETE_SUMMARY', 'LOADABLE_STUDY_DELETE_DETAILS', 'LOADABLE_STUDY_DELETE_CONFIRM_LABEL', 'LOADABLE_STUDY_DELETE_REJECT_LABEL']).toPromise();

    this.confirmationService.confirm({
      key: 'confirmation-alert',
      header: translationKeys['LOADABLE_STUDY_DELETE_SUMMARY'],
      message: translationKeys['LOADABLE_STUDY_DELETE_DETAILS'],
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: translationKeys['LOADABLE_STUDY_DELETE_CONFIRM_LABEL'],
      acceptIcon: 'pi',
      acceptButtonStyleClass: 'btn btn-main mr-5',
      rejectVisible: true,
      rejectLabel: translationKeys['LOADABLE_STUDY_DELETE_REJECT_LABEL'],
      rejectIcon: 'pi',
      rejectButtonStyleClass: 'btn btn-main',
      accept: async () => {
        this.ngxSpinnerService.show();
        try {
          const res = await this.loadableStudyListApiService.deleteLodableStudy(this.vesselInfo?.id, this.voyage?.id, event?.data?.id).toPromise();
          if (res?.responseStatus?.status === "200") {
            this.messageService.add({ severity: 'success', summary: translationKeys['LOADABLE_STUDY_DELETE_SUCCESS'], detail: translationKeys['LOADABLE_STUDY_DELETE_SUCCESSFULLY'] });
            this.deleteLoadableStudy.emit(event);
          }
        } catch (errorResponse) {
          if (errorResponse?.error?.errorCode === 'ERR-RICO-110') {
            this.messageService.add({ severity: 'error', summary: translationKeys['LOADABLE_STUDY_DELETE_ERROR'], detail: translationKeys['LOADABLE_STUDY_DELETE_STATUS_ERROR'], life: 10000 });
          }
        }
        this.ngxSpinnerService.hide();
      }
    });
  }

  /**
   * Open create new loadable study popup
   *
   * @memberof SidePanelLoadableStudyListComponent
   */
  openLoadableStudyPopup() {
    this.duplicateLoadableStudy = <LoadableStudy>{};
    this.isEdit = false;
    this.display = true;
  }

  /**
   * Set visibility of popup
   *
   * @param {*} event
   * @memberof SidePanelLoadableStudyListComponent
   */
  closeLoadableStudyPopup(event) {
    this.display = event;
  }

  /**
   * Handler for row selection
   *
   * @param {*} event
   * @memberof SidePanelLoadableStudyListComponent
   */
  onRowSelection(event) {
    this.selectedLoadableStudyChange.emit(event?.data?.id);
  }

  /**
   * Handler for added new loadable study
   *
   * @param {*} event
   * @memberof SidePanelLoadableStudyListComponent
   */
  onNewLoadableStudyAdded(event) {
    this.newLoadableStudyAdded.emit(event);
  }

  /**
  * Handler for row selection
  *
  * @param {*} event
  * @memberof SidePanelLoadableStudyListComponent
  */
  onEdit(event) {
    this.selectedLoadableStudy = event?.data;
    this.isEdit = true;
    this.display = true;
  }

  /**
   * Method to scroll to seletced row in the side pane grid
   *
   * @param {number} rowIndex
   * @memberof SidePanelLoadableStudyListComponent
   */
  scrollToSelectedRow(rowIndex: number) {
    const rows = this.sidepaneDatatable?.nativeElement?.querySelectorAll('table tbody tr');
    if (rows) {
      rows[rowIndex]?.scrollIntoView({
        behavior: 'smooth',
        block: 'center'
      });
    }
  }
}
