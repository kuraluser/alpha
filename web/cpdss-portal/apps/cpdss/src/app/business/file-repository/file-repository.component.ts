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
  fileRepoDetails: IFileRepositoryData[];
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
  }
  editData: any;

  private getFiles$ = new Subject<any>();

  constructor(
    private vesselsApiService: VesselsApiService,
    private ngxSpinnerService: NgxSpinnerService,
    private fileRepositoryTransformationService: FileRepositoryTransformationService,
    private fileRepositoryApiService: FileRepositoryApiService,
    private voyageService: VoyageService,
    private translateService: TranslateService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService
  ) { }

  ngOnInit(): void {
    this.repositoryColumns = this.fileRepositoryTransformationService.repositoryTableColumn();

    this.getVesselInfo();
    this.getFiles(false);

    this.getFiles$.pipe(
      debounceTime(1000),
      switchMap(() => {
        return this.fileRepositoryApiService.getFiles(this.pageState);
      })
    ).subscribe((result: any) => {
      this.fileRepoDetails = result.fileRepos;
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
    const result = await this.fileRepositoryApiService.getFiles(this.pageState).toPromise();
    if (loader) {
      this.ngxSpinnerService.hide();
    }
    if (result.responseStatus.status === '200') {
      result.fileRepos?.map(item => {
        item.delete = item.isSystemGenerated ? false : true;
        item.edit = item.isSystemGenerated ? false : true;
      });
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
    if (event.action === 'paginator') {
      this.pageState.pageNo = event.paginator.currentPage;
      this.pageState.pageSize = event.paginator.rows;
      this.getFiles();
    }
    if (event.action === 'filter') {
      this.pageState = { ...this.pageState, ...event.filter };
      this.pageState.pageNo = 0;
      this.pageState.pageSize = event.paginator.rows;
      this.getFiles$.next();
    }
  }

  /**
   * Method for edit/delete/view 
   *
   * @memberof FileRepositoryComponent
   */
  action(event) {
    if (event.field === 'edit') {
      this.editFile(event.data);
    }
    if (event.field === 'delete') {
      this.deleteConfirm(event.data);
    }
    if (event.field === 'download') {
      this.downloadFile(event.data);
    }
  }

  /**
   * Method for edit file
   *
   * @memberof FileRepositoryComponent
   */
  editFile(data) {
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
  deleteConfirm(data) {

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
        this.deleteFile(data);
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
    if(result.responseStatus.status === '200'){
      this.messageService.add({ severity: 'success', summary: translationKeys['FILE_REPOSITORY_SUCCESS_LABEL'], detail: translationKeys['FILE_REPOSITORY_DELETE_SUCCESS_LABEL'] });
      this.getFiles();
    }
  }

  /**
   * Method for download file
   * @memberof FileRepositoryComponent
   */
  async downloadFile(data) {
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



}
