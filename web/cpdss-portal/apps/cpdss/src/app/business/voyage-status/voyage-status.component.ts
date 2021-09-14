import { Component, OnInit } from '@angular/core';
import { VesselsApiService } from '../core/services/vessels-api.service';
import { IVessel } from '../core/models/vessel-details.model';
import { VoyageService } from '../core/services/voyage.service';
import { ICargoQuantities, IVoyagePortDetails, Voyage, VOYAGE_STATUS } from '../core/models/common.model';
import { PortRotationService } from '../core/services/port-rotation.service';
import { VoyageApiService } from './services/voyage-api.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { IBunkerConditions, IVoyageStatus } from './models/voyage-status.model';
import { VoyageStatusTransformationService } from './services/voyage-status-transformation.service';

import { AppConfigurationService } from '../../shared/services/app-configuration/app-configuration.service';
import { PermissionsService } from '../../shared/services/permissions/permissions.service';
import { MessageService } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';
import { IPermission } from '../../shared/models/user-profile.model';
import { ICargoConditions, IPermissionContext, PERMISSION_ACTION, QUANTITY_UNIT } from '../../shared/models/common.model';
import { takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';

/**
 * Component for voyage status
 */
@Component({
  selector: 'cpdss-portal-voyage-status',
  templateUrl: './voyage-status.component.html',
  styleUrls: ['./voyage-status.component.scss']
})
export class VoyageStatusComponent implements OnInit {
  display: boolean;
  showData: boolean;
  vesselInfo: IVessel;
  displayEditPortPopup: boolean;
  voyageInfo: Voyage[];
  _selectedVoyage: Voyage;
  voyageDistance: number;
  bunkerConditions: IBunkerConditions;
  cargoConditions: ICargoConditions[];
  cargoQuantities: ICargoQuantities[];
  selectedPortDetails: IVoyagePortDetails;
  voyageStatusResponse: IVoyageStatus;
  currentQuantitySelectedUnit = <QUANTITY_UNIT>localStorage.getItem('unit') ?? AppConfigurationService.settings.baseUnit;
  VOYAGE_STATUS = VOYAGE_STATUS;
  newVoyagePermissionContext: IPermissionContext;
  editPortRotationPermissionContext: IPermissionContext;

  get selectedVoyage(): Voyage {
    return this._selectedVoyage;
  }

  set selectedVoyage(voyage: Voyage) {
    this._selectedVoyage = voyage;
  }

  private _ngUnsubscribe: Subject<any> = new Subject();

  constructor(private vesselsApiService: VesselsApiService,
    private voyageService: VoyageService,
    private voyageApiService: VoyageApiService,
    private ngxSpinnerService: NgxSpinnerService,
    public voyageStatusTransformationService: VoyageStatusTransformationService,
    private messageService: MessageService,
    private translateService: TranslateService,
    private portRotationService: PortRotationService,
    public permissionsService: PermissionsService) { }

  ngOnInit() {
    this.getPagePermission();
    this.display = false;
    this.showData = false;
    this.getVesselInfo();
    this.portRotationService.voyageDistance$.pipe(takeUntil(this._ngUnsubscribe)).subscribe((voyageDistance) => {
      this.voyageDistance = voyageDistance;
    });
  }

  ngOnDestroy() {
    this._ngUnsubscribe.next();
    this._ngUnsubscribe.complete();
  }

  /**
   * Get page permission
   *
   * @memberof VoyageStatusComponent
   */
  getPagePermission() {
    this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['VoyageStatusComponent']);
    this.newVoyagePermissionContext = { key: AppConfigurationService.settings.permissionMapping['NewVoyage'], actions: [PERMISSION_ACTION.VIEW] };
    this.editPortRotationPermissionContext = { key: AppConfigurationService.settings.permissionMapping['StatusEditPortRotation'], actions: [PERMISSION_ACTION.VIEW] };
  }

  /**
   * Get vessel details
   *
   * @memberof VoyageStatusComponent
   */
  async getVesselInfo() {
    this.ngxSpinnerService.show();
    const res = await this.vesselsApiService.getVesselsInfo().toPromise();
    this.vesselInfo = res[0] ?? <IVessel>{};
    if (this.vesselInfo?.id) {
      localStorage.setItem("vesselId", this.vesselInfo?.id.toString())
      this.getVoyageInfo(this.vesselInfo?.id);
    }
    this.ngxSpinnerService.hide();
  }

  /**
   * Show new-voyage popup
   */
  showDialog() {
    this.display = true;
  }

  /**
   * Value from new-voyage
   */
  displayPopUpTab(displayNew_: boolean) {
    this.display = displayNew_;
  }

  /**
   * Show edit port rotation popup
   */
  showEditPortRotation() {
    this.displayEditPortPopup = true;
  }

  /**
   * Value from edit port rotation
   */
  displayEditPortRotationPopUpTab(displayNew_: boolean) {
    this.displayEditPortPopup = displayNew_;
  }

  /**
   * Get voyage info
   */
  async getVoyageInfo(vesselId: number) {
    const voyages = await this.voyageService.getVoyagesByVesselId(vesselId).toPromise();
    this.voyageInfo = this.getSelectedVoyages(voyages);
    const voyageId = localStorage.getItem("voyageId");
    if (voyageId) {
      const checkVoyage = this.voyageInfo.find(voyage => voyage.id === Number(voyageId));
      if (checkVoyage) {
        this.selectedVoyage = checkVoyage;
      } else {
        this.selectedVoyage = this.voyageInfo[0];
      }
    } else {
      this.selectedVoyage = this.voyageInfo[0];
    }
    localStorage.setItem("voyageId", this.selectedVoyage?.id.toString());
    const alertForVoyageEnd = localStorage.getItem('alertForVoyageEnd');
    if (alertForVoyageEnd !== 'true') {
      this.alertForEnd()
    }
  }

  /**
   * Get list of active and closed voyages
   *
   * @param {Voyage[]} voyages
   * @returns {Voyage[]}
   * @memberof VoyageStatusComponent
   */
  getSelectedVoyages(voyages: Voyage[]): Voyage[] {
    this.selectedVoyage = voyages?.find(voyage => voyage?.statusId === VOYAGE_STATUS.ACTIVE);
    const latestClosedVoyages = [...voyages?.filter(voyage => voyage?.statusId === VOYAGE_STATUS.CLOSE)]?.sort((a, b) => this.convertToDate(b?.actualStartDate)?.getTime() - this.convertToDate(a?.actualStartDate)?.getTime())?.slice(0, this.selectedVoyage ? 9 : 10);
    const voyageInfo = this.selectedVoyage ? [this.selectedVoyage, ...latestClosedVoyages] : [...latestClosedVoyages]
    return voyageInfo;
  }

  /**
  * Convert to date time(dd-mm-yyyy hh:mm)
  *
  * @memberof VoyageStatusComponent
  */
  convertToDate(value): Date {
    if (value) {
      const arr = value.toString().split(' ')
      const dateArr = arr[0]?.split('-');
      if (arr[1]) {
        const timeArr = arr[1].split(':')
        if (dateArr.length > 2 && timeArr.length > 1) {
          return new Date(Number(dateArr[2]), Number(dateArr[1]) - 1, Number(dateArr[0]), Number(timeArr[0]), Number(timeArr[1]));
        }
      } else {
        return new Date(Number(dateArr[2]), Number(dateArr[1]) - 1, Number(dateArr[0]))
      }
    }
    return null
  }

  /**
   * Get voyage status details
   */
  async getVoyageStatus(vesselId: number, voyageId: number,portData: IVoyagePortDetails) {
    this.ngxSpinnerService.show();
    const id = this.selectedVoyage?.confirmedDischargeStudyId && portData.portType === 'DISCHARGE'? this.selectedVoyage?.confirmedDischargeStudyId : this.selectedVoyage?.confirmedLoadableStudyId;
    this.voyageStatusResponse = await this.voyageApiService.getVoyageDetails(vesselId, voyageId, id, this.selectedPortDetails).toPromise();
    if (this.voyageStatusResponse?.responseStatus?.status === '200') {
      this.bunkerConditions = this.voyageStatusResponse?.bunkerConditions;
      this.cargoConditions = this.voyageStatusResponse?.cargoConditions?.length > 0 ? this.voyageStatusResponse?.cargoConditions : [];
      this.cargoQuantities = this.voyageStatusResponse?.cargoQuantities?.length > 0 ? this.voyageStatusResponse?.cargoQuantities : [];
    }
    this.ngxSpinnerService.hide();
  }

  /**
   * Emit port data from port rotation ribbon
   * @param portData
   */
  getPortDetails(portData: IVoyagePortDetails) {
    this.selectedPortDetails = portData;
    this.getVoyageStatus(this.vesselInfo?.id, this.selectedVoyage?.id, portData);
  }

  /**
   * Handler for unit change event
   *
   * @param {*} event
   * @memberof VoyageStatusComponent
   */
  onUnitChange(event) {
    this.currentQuantitySelectedUnit = <QUANTITY_UNIT>localStorage.getItem('unit');
  }

  /**
  * Handler for show alert for end voyage
  *
  * @memberof VoyageStatusComponent
  */
  async alertForEnd() {
    localStorage.setItem('alertForVoyageEnd', 'true');
    const activeVoyage = this.voyageInfo?.find(voyage => voyage?.statusId === VOYAGE_STATUS.ACTIVE);
    if (activeVoyage) {
      const translationKeys = await this.translateService.get(['VOYAGE_STATUS_ACTIVE_END_WARNING', 'VOYAGE_STATUS_ACTIVE_END_WARNING_MESSAGE_FIRST', 'VOYAGE_STATUS_ACTIVE_END_WARNING_MESSAGE_SECOND']).toPromise();
      if (activeVoyage?.noOfDays >= 0) {
        this.messageService.add({ severity: 'warn', summary: translationKeys['VOYAGE_STATUS_ACTIVE_END_WARNING'], detail: translationKeys['VOYAGE_STATUS_ACTIVE_END_WARNING_MESSAGE_FIRST'] + " " + activeVoyage?.endDate?.split(' ')[0] + ". " + translationKeys['VOYAGE_STATUS_ACTIVE_END_WARNING_MESSAGE_SECOND'], sticky: true });
      }
    }
  }


  /**
  * Show loadable study list based on selected voyage id
  */
   onVoyageSelected() {
    localStorage.setItem("voyageId", this.selectedVoyage?.id.toString())
    localStorage.removeItem("loadableStudyId")
    localStorage.removeItem("loadablePatternId")
    localStorage.removeItem("dischargeStudyId")
  }

}
