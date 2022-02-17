import { CrewMasterApiService } from './../../services/crew-master-api.service';
import { CrewMasterTransformationService } from './../../services/crew-master-transformation.service';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BehaviorSubject, Subject } from 'rxjs';
import { debounceTime, switchMap, takeUntil } from 'rxjs/operators';
import { NgxSpinnerService } from 'ngx-spinner';
import { PermissionsService } from '../../../../shared/services/permissions/permissions.service';
import { AppConfigurationService } from '../../../../shared/services/app-configuration/app-configuration.service';
import { IPermission } from '../../../../shared/models/user-profile.model';
import { IPermissionContext, PERMISSION_ACTION } from '../../../../shared/models/common.model';
import { IDataTableColumn, IDataTablePageChangeEvent } from '../../../../shared/components/datatable/datatable.model';
import { ICrewMasterList, ICrewMasterListStateChange, ICrewMasterListResponse } from './../../models/crew.model';
import { TranslateService } from '@ngx-translate/core';
import { MessageService } from 'primeng/api';
import { IVessel } from '../../../core/models/vessel-details.model';
import { IVesselsResponse } from '../../../core/models/vessel-details.model';

@Component({
  selector: 'cpdss-portal-crew-listing',
  templateUrl: './crew-listing.component.html',
  styleUrls: ['./crew-listing.component.scss']
})
export class CrewListingComponent implements OnInit, OnDestroy {

  get crews(): ICrewMasterList[] {
    return this.crewList;
  }

  set crews(value: ICrewMasterList[]) {
    this.crewList = value?.map(crew => this.crewMasterTransformationService.formatCrew(crew));
  }

  private getCrewMasterListState$ = new BehaviorSubject<ICrewMasterListStateChange>(<ICrewMasterListStateChange>{});
  public columns: IDataTableColumn[];
  public permission: IPermission;
  public addCrewBtnPermissionContext: IPermissionContext;
  public loading: boolean;
  public totalRecords: number;
  public currentPage: number;
  public first: number;
  public crewList: ICrewMasterList[];
  public crewListPageState: ICrewMasterListStateChange;
  private onDestroy$: Subject<void> = new Subject<void>();
  public vesselList: IVessel[];
  public isAddCrew: Boolean = false;
  public popupStatus: string;

  constructor(
    private permissionsService: PermissionsService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private ngxSpinnerService: NgxSpinnerService,
    private crewMasterTransformationService: CrewMasterTransformationService,
    private crewMasterApiService: CrewMasterApiService,
    private translateService: TranslateService,
    private messageService: MessageService,
  ) { }


  ngOnInit(): void {
    this.getVessels();
    this.getCrewDetails();
    this.addCrewBtnPermissionContext = { key: AppConfigurationService.settings.permissionMapping['CrewListingComponent'], actions: [PERMISSION_ACTION.VIEW, PERMISSION_ACTION.ADD] };
    this.permission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['PortListingComponent']);
    this.columns = this.crewMasterTransformationService.getCrewListDatatableColumns(this.permission);
  }

  /**
   * pop up screen for adding crew
   *
   */
  addCrew(isAddCrew:Boolean, popUpStatus: string) {
    this.isAddCrew = isAddCrew;
    this.popupStatus = popUpStatus;
  }

  /**
 * Method to bind crews to datatable
 * @param {ICrewMasterListResponse} crewMaster
 * @memberof CrewListingComponent
 */
  showCurrentPageDetails(crewMaster: ICrewMasterListResponse): void {
    this.ngxSpinnerService.hide();
    this.totalRecords = crewMaster.totalElements;
    if (this.totalRecords && !crewMaster?.crewDetails?.length) {
      this.loading = true;
      this.currentPage = this.currentPage ? this.currentPage - 1 : 0;
      this.crewListPageState['page'] = this.currentPage;
      this.getCrewMasterListState$.next(this.crewListPageState);
    }
    this.crews = this.totalRecords !== 0 ? crewMaster.crewDetails : [];
  }

  /**
 * Event handler for datatable change
 *
 * @param {IDataTablePageChangeEvent} event
 * @memberof CargoMasterComponent
 */
  onDataStateChange(event: IDataTablePageChangeEvent) {
    this.crewListPageState = {
      filter: event.filter,
      pageSize: event.paginator.rows,
      page: event.paginator.currentPage,
      sortBy: event.sort.sortField,
      orderBy: event.sort.sortOrder,
    };
    this.loading = true;
    this.getCrewMasterListState$.next(this.crewListPageState);
  }

  /**
   * to get all vessels
   */
  async getVessels() {
    this.ngxSpinnerService.show();
    const translationKeys = await this.translateService.get(['SOMETHING_WENT_WRONG_ERROR', 'SOMETHING_WENT_WRONG']).toPromise();
    this.crewMasterApiService.getAllvessels().pipe(takeUntil(this.onDestroy$)).subscribe((response: IVesselsResponse) => {
      try {
        if (response.responseStatus.status === '200') {
          this.vesselList = response.vessels;
        }
      } catch (error) {
        this.messageService.add({ severity: 'error', summary: translationKeys['SOMETHING_WENT_WRONG_ERROR'], detail: translationKeys['SOMETHING_WENT_WRONG'] });
      }
      this.ngxSpinnerService.hide();
    })
  }

  /**
   * to get all crew details, listing of crews
   */
   async getCrewDetails(){
    this.ngxSpinnerService.show();
    const translationKeys = await this.translateService.get(['SOMETHING_WENT_WRONG_ERROR', 'SOMETHING_WENT_WRONG']).toPromise();
    this.first = 0;
    this.currentPage = 0;
    this.crewListPageState = <ICrewMasterListStateChange>{};
    this.getCrewMasterListState$.pipe(
      debounceTime(1000),
      switchMap(() => {
        return this.crewMasterApiService.getCrewList(this.crewListPageState).toPromise();
      })
    ).subscribe((response: ICrewMasterListResponse) => {
      try {
        if (response.responseStatus.status === '200') {
          this.showCurrentPageDetails(response);
        }
      } catch (error) {
        this.messageService.add({ severity: 'error', summary: translationKeys['SOMETHING_WENT_WRONG_ERROR'], detail: translationKeys['SOMETHING_WENT_WRONG'] });
      }
      this.ngxSpinnerService.hide();
      this.loading = false;
    });
   }

  ngOnDestroy() {
    this.getCrewMasterListState$.unsubscribe();
    this.onDestroy$.next();
    this.onDestroy$.complete();
    this.onDestroy$.unsubscribe();
  }
}
