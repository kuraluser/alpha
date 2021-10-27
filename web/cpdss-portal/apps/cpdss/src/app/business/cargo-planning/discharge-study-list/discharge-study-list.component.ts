import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { ConfirmationService, MessageService } from 'primeng/api';
import { DATATABLE_ACTION, IDataTableColumn } from '../../../shared/components/datatable/datatable.model';
import { IDateTimeFormatOptions, IPermissionContext, PERMISSION_ACTION} from '../../../shared/models/common.model';
import { IPermission } from '../../../shared/models/user-profile.model';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { PermissionsService } from '../../../shared/services/permissions/permissions.service';
import { TimeZoneTransformationService } from '../../../shared/services/time-zone-conversion/time-zone-transformation.service';
import { Voyage, VOYAGE_STATUS , DISCHARGE_STUDY_STATUS  } from '../../core/models/common.model';
import { IVessel } from '../../core/models/vessel-details.model';
import { VesselsApiService } from '../../core/services/vessels-api.service';
import { VoyageService } from '../../core/services/voyage.service';
import { DischargeStudyListApiService } from '../services/discharge-study-list-api.service';
import { DischargeStudyListTransformationApiService } from '../services/discharge-study-list-transformation-api.service';
import { IDischargeStudy } from './../models/discharge-study-list.model';

/**
 *
 * Component class for DischargeStudyListComponent
 * @export
 * @class DischargeStudyListComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-discharge-study-list',
  templateUrl: './discharge-study-list.component.html',
  styleUrls: ['./discharge-study-list.component.scss']
})
export class DischargeStudyListComponent implements OnInit {

  
  voyages: Voyage[];
  _selectedVoyage: Voyage;
  loading = true;
  display = false;
  edit = false;
  vesselDetails: IVessel;
  voyageId: number;
  columns: IDataTableColumn[];
  readonly editMode = null;
  isVoyageIdSelected = true;
  permission: IPermission;
  dischargeStudyList: IDischargeStudy[]
  VOYAGE_STATUS = VOYAGE_STATUS;
  selectedDischargeStudy: any //ToDo - change the type to any to model type once actual api is availble.
  addDSBtnPermissionContext: IPermissionContext;
  confirmedPlan: IDischargeStudy;


 /**
  * get property for selected voyage.
  *
  * @type {Voyage}
  * @memberof DischargeStudyListComponent
  */
 get selectedVoyage(): Voyage {
    return this._selectedVoyage;
  }

  /**
   * set property for selected voyage.
   *
   * @memberof DischargeStudyListComponent
   */
  set selectedVoyage(voyage: Voyage) {
    this._selectedVoyage = voyage;
  }

  constructor(private vesselsApiService: VesselsApiService, private router: Router,
    private translateService: TranslateService, private activatedRoute: ActivatedRoute,
    private voyageService: VoyageService,
    private timeZoneTransformationService: TimeZoneTransformationService,
    private ngxSpinnerService: NgxSpinnerService,
    private dischargeStudyListTransformationApiService: DischargeStudyListTransformationApiService,
    private dischargeStudyListApiService: DischargeStudyListApiService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private permissionsService: PermissionsService,

  ) { }

  /**
   * Component lifecycle ngoninit
   *
   * @return {*}  {Promise<void>}
   * @memberof DischargeStudyListComponent
   */
  async ngOnInit(): Promise<void> {
    this.activatedRoute.params.subscribe(async params => {
      this.voyageId = params.id ? Number(params.id) : 0;
      this.ngxSpinnerService.show();
      const res = await this.vesselsApiService.getVesselsInfo().toPromise();
      this.vesselDetails = res[0] ?? <IVessel>{};
      localStorage.setItem("vesselId", this.vesselDetails?.id.toString())
      const result = await this.voyageService.getVoyagesByVesselId(this.vesselDetails?.id).toPromise();
      this.voyages = result.filter(voy => voy.statusId === VOYAGE_STATUS.ACTIVE || voy.statusId === VOYAGE_STATUS.CLOSE);
      this.selectedVoyage = this.voyages.find(voy => voy.status === "Active");
      if(!this.selectedVoyage) {
        this.selectedVoyage = this.voyages[0];
      }
      this.getPagePermission();
      this.showDischargeStudyList();    
      this.ngxSpinnerService.hide();

    });
    this.loading = false;
  }

  /**
   * Method to get page permission
   *
   * @memberof DischargeStudyListComponent
   */

  getPagePermission() {
    this.permission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['DischargeStudyListing']);
    this.addDSBtnPermissionContext = { key: AppConfigurationService.settings.permissionMapping['DischargeStudyListing'], actions: [PERMISSION_ACTION.ADD] };
  }


  /**
   * Method to get the selected voyages.
   *
   * @param {Voyage[]} voyages
   * @return {*}  {Voyage[]}
   * @memberof DischargeStudyListComponent
   */
  getSelectedVoyages(voyages: Voyage[]): Voyage[] {
    this.selectedVoyage = voyages?.find(voyage => voyage?.statusId === VOYAGE_STATUS.ACTIVE);
    const latestClosedVoyages = [...voyages?.filter(voyage => voyage?.statusId === VOYAGE_STATUS.CLOSE)]?.sort((a, b) => this.dischargeStudyListTransformationApiService.convertToDate(b?.actualStartDate)?.getTime() - this.dischargeStudyListTransformationApiService.convertToDate(a?.actualStartDate)?.getTime())?.slice(0, this.selectedVoyage ? 9 : 10);
    const voyageInfo = this.selectedVoyage ? [this.selectedVoyage, ...latestClosedVoyages] : [...latestClosedVoyages]
    return voyageInfo;
  }

  /**
   * Method to show discharge study list
   *
   * @memberof DischargeStudyListComponent
   */
  showDischargeStudyList() {
    this.isVoyageIdSelected = true;
    this.columns = this.dischargeStudyListTransformationApiService.getDischargeStudyTableColumns();
    if (this.permission.edit) {
      this.columns[this.columns.length - 1]['actions'].push(DATATABLE_ACTION.EDIT);
    }
    if (this.permission.delete) {
      this.columns[this.columns.length - 1]['actions'].push(DATATABLE_ACTION.DELETE);
    }
    this.getDischargeStudyInfo(this.vesselDetails?.id, this.selectedVoyage.id);
  }

 /**
  * Method to show discharge study pop up.
  *
  * @memberof DischargeStudyListComponent
  */
 callNewDischargeStudyPopup(event,selectedDischargeStudy) {
    this.display = true;
    this.edit = event;
    if(this.edit)
    {
      this.selectedDischargeStudy = selectedDischargeStudy;
    }
    else{
      this.selectedDischargeStudy = null;
    }
  }

  /**
   * Method to show  pop up on edit
   *
   * @param {*} event
   * @memberof DischargeStudyListComponent
   */

  onRowSelect(event: any) {
    if (event?.field !== "actions") {
      this.selectedDischargeStudy = null;
      this.router.navigate([`/business/cargo-planning/discharge-study-details/${this.vesselDetails?.id}/${this.selectedVoyage.id}/${event.data.id}`]);
    }
  }

  /**
   * Method to call onedit
   *
   * @memberof DischargeStudyListComponent
   */
  onEditRow(event) {
    this.callNewDischargeStudyPopup(true, event.data);
  }

  /**
   * Method on new discharge study added
   *
   * @memberof DischargeStudyListComponent
   */
  onNewDischargeStudyAdded(dischargeStudyLId)
  {
    this.router.navigate([`/business/cargo-planning/discharge-study-details/${this.vesselDetails?.id}/${this.selectedVoyage.id}/${dischargeStudyLId}`]);
  }
 
  /**
   * Method on delete row
   *
   * @param {*} event
   * @memberof DischargeStudyListComponent
   */

  async onDeletRow(event)
  {
   const translationKeys = await this.translateService.get(['DISCHARGE_STUDY_DELETE_SUCCESS', 'DISCHARGE_STUDY_DELETE_SUCCESSFULLY', 'DISCHARGE_STUDY_DELETE_ERROR', 'DISCHARGE_STUDY_DELETE_STATUS_ERROR', 'DISCHARGE_STUDY_DELETE_SUMMARY', 'DISCHARGE_STUDY_DELETE_DETAILS', 'LOADABLE_STUDY_DELETE_CONFIRM_LABEL', 'DISCHARGE_STUDY_DELETE_REJECT_LABEL']).toPromise();

    this.confirmationService.confirm({
      key: 'confirmation-alert',
      header: translationKeys['DISCHARGE_STUDY_DELETE_SUMMARY'],
      message: translationKeys['DISCHARGE_STUDY_DELETE_DETAILS'],
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: translationKeys['DISCHARGE_STUDY_DELETE_CONFIRM_LABEL'],
      acceptIcon: 'pi',
      acceptButtonStyleClass: 'btn btn-main mr-5',
      rejectVisible: true,
      rejectLabel: translationKeys['DISCHARGE_STUDY_DELETE_REJECT_LABEL'],
      rejectIcon: 'pi',
      rejectButtonStyleClass: 'btn btn-main',
      accept: async () => {
        this.ngxSpinnerService.show();
        try {
          const res = await this.dischargeStudyListApiService.deleteDischargeStudy (event.data.id).toPromise();
          if (res?.responseStatus?.status === "200") {
            this.messageService.add({ severity: 'success', summary: translationKeys['DISCHARGE_STUDY_DELETE_SUCCESS'], detail: translationKeys['DISCHARGE_STUDY_DELETE_SUCCESSFULLY'] });
            this.dischargeStudyList = this.dischargeStudyList.filter((item)=>item.id!=event.data.id);
          }
        } catch (errorResponse) {
          if (errorResponse?.error?.errorCode === 'ERR-RICO-110') {
            this.messageService.add({ severity: 'error', summary: translationKeys['DISCHARGE_STUDY_DELETE_ERROR'], detail: translationKeys['DISCHARGE_STUDY_DELETE_STATUS_ERROR'], life: 10000 });
          }
        }
        this.ngxSpinnerService.hide();
      }
    });
 
  }

  /**
   * Method to get discharge study info
   *
   * @param {number} vesselId
   * @param {number} voyageId
   * @memberof DischargeStudyListComponent
   */
  async getDischargeStudyInfo(vesselId: number, voyageId: number) {
    this.ngxSpinnerService.show();
    if (voyageId !== 0) {
      this.dischargeStudyList = null;
      const result = await this.dischargeStudyListApiService.getDischargeStudies(vesselId, voyageId).toPromise();

      const dateFormatOptions: IDateTimeFormatOptions = { utcFormat: true };
      const dischargeStudyList = result?.dischargeStudies?.map(dischargeStudy => {
        dischargeStudy.createdDate = this.timeZoneTransformationService.formatDateTime(dischargeStudy.createdDate, dateFormatOptions);
        dischargeStudy.lastEdited = this.timeZoneTransformationService.formatDateTime(dischargeStudy.lastEdited, dateFormatOptions);
        dischargeStudy.isActionsEnabled = [DISCHARGE_STUDY_STATUS.PLAN_PENDING, DISCHARGE_STUDY_STATUS.PLAN_NO_SOLUTION, DISCHARGE_STUDY_STATUS.PLAN_ERROR].includes(dischargeStudy?.statusId) && [VOYAGE_STATUS.ACTIVE].includes(this.selectedVoyage?.statusId) ? true : false;
        return dischargeStudy;
      });
      dischargeStudyList?.length ? this.dischargeStudyList = [...dischargeStudyList] : this.dischargeStudyList = [];
      this.confirmedPlan = this.dischargeStudyList.find(dischargeStudy => DISCHARGE_STUDY_STATUS.PLAN_CONFIRMED === dischargeStudy.statusId);
      if(this.confirmedPlan) {
        this.columns.pop();
      }
    }
    this.ngxSpinnerService.hide();
  }
}

