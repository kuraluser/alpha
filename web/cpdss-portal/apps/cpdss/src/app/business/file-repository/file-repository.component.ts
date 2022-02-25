import { Component, OnInit, OnDestroy } from '@angular/core';
import { VesselsApiService } from '../core/services/vessels-api.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { IVessel } from '../core/models/vessel-details.model';
import { FileRepositoryTransformationService } from './services/file-repository-transformation.service';
import { FileRepositoryApiService } from './services/file-repository-api.service';
import { IDataTableColumn } from './../../shared/components/datatable/datatable.model';
import { IFileRepoStateChange, IFileRepositoryData } from './models/file-repository.model';
import { VoyageService } from '../core/services/voyage.service';
import { Subject } from 'rxjs';
import { debounceTime, switchMap } from 'rxjs/operators';
import { TranslateService } from '@ngx-translate/core';
import { ConfirmationService } from 'primeng/api';
import { saveAs } from 'file-saver';
import { MessageService } from 'primeng/api';
import { PermissionsService } from '../../shared/services/permissions/permissions.service';
import { AppConfigurationService } from '../../shared/services/app-configuration/app-configuration.service';
import * as moment from 'moment';

/**
 * Component class for file repositiry
 *
 * @export
 * @class FileRepositoryComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-file-repository',
  templateUrl: './file-repository.component.html',
  styleUrls: ['./file-repository.component.scss']
})
export class FileRepositoryComponent implements OnInit, OnDestroy {

  vessel: IVessel;
  repositoryColumns: IDataTableColumn[];
  voyages: any;
  showAddEdit = false;
  editMode: boolean;
  totalRecords: number;
  pageState: IFileRepoStateChange = {
    fileName: '',
    pageNo: 0,
    voyageNumber: '',
    pageSize: 10,
    sortBy: '',
    orderBy: '',
    fileType: '',
    section: '',
    category: '',
    createdDate: '',
    createdBy: ''
  };
  fileIcons = {
    'docx': 'icon-file icon-file-word',
    'pdf': 'icon-file icon-file-pdf',
    'txt': 'icon-file icon-file-text',
    'csv': 'icon-file icon-file-csv',
    'xlsx': 'icon-file icon-file-excel',
    'doc': 'icon-file icon-file-word',
    'jpg': 'icon-file icon-file-jpg',
    'png': 'icon-file icon-file-png',
    'msg': 'icon-file icon-file-msg',
    'eml': 'icon-file icon-file-eml',
    'xls': 'icon-file icon-file-excel'
  };
  editData: any;
  permission: any;

  _fileRepoDetails: any = [];

  get fileRepoDetails() {
    return this._fileRepoDetails;
  }

  set fileRepoDetails(value: IFileRepositoryData[]) {
    if (value) {
      value?.map(item => {
        item.fileIcon = this.fileIcons[item.fileType];
        item.uploadType = item.isSystemGenerated ? 'System' : 'Manual';
      })
    }
    this._fileRepoDetails = value ? value : [];
  }
  private getFiles$ = new Subject<any>();

  constructor(
    private vesselsApiService: VesselsApiService,
    private ngxSpinnerService: NgxSpinnerService,
    private fileRepositoryTransformationService: FileRepositoryTransformationService,
    private fileRepositoryApiService: FileRepositoryApiService,
    private voyageService: VoyageService,
    private translateService: TranslateService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService,
    private permissionsService: PermissionsService
  ) { }

  async ngOnInit(): Promise<void> {
    this.permission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['FileRepositoryComponent']);
    this.repositoryColumns = this.fileRepositoryTransformationService.repositoryTableColumn(this.permission);
    await this.getVesselInfo();
    this.getFiles(false);

    this.getFiles$.pipe(
      debounceTime(1000),
      switchMap(() => {
        if(this.pageState?.createdDate){
        let createdDate = moment(this.pageState?.createdDate,"dd-MM-yyyy");
        if(!createdDate.isValid()){
          this.pageState["createdDate"] = null;
        }
        }
        this.ngxSpinnerService.show();
        return this.fileRepositoryApiService.getFiles(this.pageState, this.vessel?.id);
      })
    ).subscribe((result: any) => {
      this.ngxSpinnerService.hide();
      this.fileRepoDetails = result.fileRepos;
      this.actionSetting(result);
      this.totalRecords = result.totalElements;
    });
  }

  ngOnDestroy(): void {
    this.getFiles$.unsubscribe();
  }

  /**
   * Get vessel details
   *
   * @memberof FileRepositoryComponent
   */
  async getVesselInfo() {
    this.ngxSpinnerService.show();
    const res = await this.vesselsApiService.getVesselsInfo().toPromise();
    this.vessel = res[0] ?? <IVessel>{};
    await this.getVoyages();
    this.ngxSpinnerService.hide();
  }

  /**
  * Method for getting gile details
  *
  * @memberof FileRepositoryComponent
  */
  async getFiles(loader = true) {
    if (loader) {
      this.ngxSpinnerService.show();
    }
    const result = await this.fileRepositoryApiService.getFiles(this.pageState, this.vessel?.id).toPromise();
    if (loader) {
      this.ngxSpinnerService.hide();
    }
    if (result.responseStatus.status === '200') {
      await this.actionSetting(result);
      this.fileRepoDetails = result.fileRepos;
      this.totalRecords = result.totalElements;
    }
  }

  /**
   * Method for get voyage details
   *
   * @memberof FileRepositoryComponent
   */
  async getVoyages() {
    this.voyages = await this.voyageService.getVoyagesByVesselId(this.vessel.id).toPromise();
  }

  /**
   * Method for add/edit pop up close
   *
   * @memberof FileRepositoryComponent
   */
  closePopUp(event) {
    this.showAddEdit = false;
  }

  /**
   * Method for pagination and filter changes
   *
   * @memberof FileRepositoryComponent
   */
  onDataStateChange(event) {
    this.pageState.pageNo = event.paginator.currentPage;
    this.pageState.pageSize = event.paginator.rows;
    this.pageState = { ...this.pageState, ...event.filter };
    this.pageState.sortBy = event.sort?.sortField;
    this.pageState.orderBy =  event.sort?.sortOrder;
    this.getFiles$.next();
  }

  /**
   * Method for edit file
   *
   * @memberof FileRepositoryComponent
   */
  editFile(event) {
    const data = event.data;
    this.editData = JSON.parse(JSON.stringify(data));
    const section = this.fileRepositoryTransformationService.sectionList.filter(item => item.label === data.section);
    const category = this.fileRepositoryTransformationService.category.filter(item => item.label === data.category);
    const voyage = this.voyages.filter(item => item.voyageNo === data.voyageNumber);
    this.editData.section = section?.length ? section[0] : '';
    this.editData.category = category?.length ? category[0] : '';
    this.editData.voyageNumber = voyage ? voyage[0] : '';
    this.editMode = true;
    this.showAddEdit = true;
  }

  /**
   * Method for delete file confirmation
   *
   * @memberof FileRepositoryComponent
   */
  deleteConfirm(event) {

    const translationKeys = this.translateService.instant(['FILE_REPOSITORY_DELETE_SUMMARY', 'FILE_REPOSITORY_DELETE_DETAILS', 'FILE_REPOSITORY_DELETE_CONFIRM_LABEL', 'FILE_REPOSITORY_DELETE_REJECT_LABEL']);
    this.confirmationService.confirm({
      key: 'confirmation-alert',
      header: translationKeys['FILE_REPOSITORY_DELETE_SUMMARY'],
      message: translationKeys['FILE_REPOSITORY_DELETE_DETAILS'],
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: translationKeys['FILE_REPOSITORY_DELETE_CONFIRM_LABEL'],
      acceptIcon: 'pi',
      acceptButtonStyleClass: 'btn btn-main mr-5',
      rejectVisible: true,
      rejectLabel: translationKeys['FILE_REPOSITORY_DELETE_REJECT_LABEL'],
      rejectIcon: 'pi',
      rejectButtonStyleClass: 'btn btn-main',
      accept: async () => {
        this.deleteFile(event.data);
      }
    });
  }

  /**
   * Method for delete file
   * @memberof FileRepositoryComponent
   */
  async deleteFile(data) {
    const param = {
      section: data.section,
      category: data.category
    };
    const translationKeys = await this.translateService.get(['FILE_REPOSITORY_SUCCESS_LABEL', 'FILE_REPOSITORY_DELETE_SUCCESS_LABEL']).toPromise();
    this.ngxSpinnerService.show();
    const result = await this.fileRepositoryApiService.deleteFile(data.id, param).toPromise();
    this.ngxSpinnerService.hide();
    if (result.responseStatus.status === '200') {
      this.messageService.add({ severity: 'success', summary: translationKeys['FILE_REPOSITORY_SUCCESS_LABEL'], detail: translationKeys['FILE_REPOSITORY_DELETE_SUCCESS_LABEL'] });
      this.getFiles();
    }
  }

  /**
   * Method for download file
   * @memberof FileRepositoryComponent
   */
  async downloadFile(event) {
    const data = event.data;
    this.ngxSpinnerService.show();
    const result = await this.fileRepositoryApiService.downloadFile(data.id).toPromise();
    const blob = new Blob([result], { type: result.type })
    const fileurl = window.URL.createObjectURL(blob)
    saveAs(fileurl, data.fileName);
    this.ngxSpinnerService.hide();
  }

  /**
   * Method for add new file
   * @memberof FileRepositoryComponent
   */
  addNewFile() {
    this.showAddEdit = null;
    this.editMode = false;
    this.showAddEdit = true;
  }

  // to set actions on each row
  async actionSetting(result: any) {
    result.fileRepos?.map(item => {
      item.isDeletable = !item.isSystemGenerated && this.permission.delete ? true : false;
      item.isEditable = !item.isSystemGenerated && this.permission.edit ? true : false;
    });
  }



}
