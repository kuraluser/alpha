import { CrewMasterApiService } from './../../services/crew-master-api.service';
import { CrewMasterTransformationService } from './../../services/crew-master-transformation.service';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BehaviorSubject } from 'rxjs';
import { debounceTime, switchMap } from 'rxjs/operators';
import { NgxSpinnerService } from 'ngx-spinner';
import { PermissionsService } from '../../../../shared/services/permissions/permissions.service';
import { AppConfigurationService } from '../../../../shared/services/app-configuration/app-configuration.service';
import { IPermission } from '../../../../shared/models/user-profile.model';
import { IPermissionContext, PERMISSION_ACTION } from '../../../../shared/models/common.model';
import { IDataTableColumn, IDataTablePageChangeEvent } from '../../../../shared/components/datatable/datatable.model';
import { ICrewMasterList, ICrewMasterListStateChange, ICrewMasterListResponse } from './../../models/crew.model';

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

  constructor(
    private permissionsService: PermissionsService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private ngxSpinnerService: NgxSpinnerService,
    private crewMasterTransformationService: CrewMasterTransformationService,
    private crewMasterApiService: CrewMasterApiService
  ) { }


  ngOnInit(): void {
    this.ngxSpinnerService.show();
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
        this.ngxSpinnerService.hide();
      }
      this.loading = false;
    });
    this.addCrewBtnPermissionContext = { key: AppConfigurationService.settings.permissionMapping['CrewListingComponent'], actions: [PERMISSION_ACTION.VIEW, PERMISSION_ACTION.ADD] };
    this.permission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['PortListingComponent']);
    this.columns = this.crewMasterTransformationService.getCrewListDatatableColumns(this.permission);
  }

  /**
   * pop up screen for adding crew
   *
   */
  //TODO: implemet it in future
  addCrew() {

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

  ngOnDestroy() {
    this.getCrewMasterListState$.unsubscribe();
  }
}
