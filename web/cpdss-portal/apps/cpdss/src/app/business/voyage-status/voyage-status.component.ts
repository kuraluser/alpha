import { Component, OnInit } from '@angular/core';
import { VesselsApiService } from '../core/services/vessels-api.service';
import { IVessel } from '../core/models/vessel-details.model';
import { VoyageService } from '../core/services/voyage.service';
import { Voyage, VOYAGE_STATUS } from '../core/models/common.model';
import { EditPortRotationApiService } from './services/edit-port-rotation-api.service';
import { IPortsDetailsResponse } from '../core/models/common.model';
import { VoyageApiService } from './services/voyage-api.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { IBunkerConditions, ICargoConditions, ICargoQuantities, IVoyageDetails, IVoyageStatus } from './models/voyage-status.model';
import { VoyageStatusTransformationService } from './services/voyage-status-transformation.service';
import { QUANTITY_UNIT } from '../../shared/models/common.model';
import { AppConfigurationService } from '../../shared/services/app-configuration/app-configuration.service';
import { MessageService } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';
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
  selectedPortDetails: IVoyageDetails;
  voyageStatusResponse: IVoyageStatus;
  currentQuantitySelectedUnit = <QUANTITY_UNIT>localStorage.getItem('unit') ?? AppConfigurationService.settings.baseUnit;
  VOYAGE_STATUS = VOYAGE_STATUS;

  get selectedVoyage(): Voyage{
    return this._selectedVoyage;
  }

  set selectedVoyage(voyage: Voyage){
    this._selectedVoyage = voyage;
    localStorage.setItem("voyageId",voyage.id.toString())
    localStorage.removeItem("loadableStudyId")
    localStorage.removeItem("loadablePatternId")
  }

  constructor(private vesselsApiService: VesselsApiService,
    private voyageService: VoyageService,
    private voyageApiService: VoyageApiService,
    private ngxSpinnerService: NgxSpinnerService,
    public voyageStatusTransformationService: VoyageStatusTransformationService,
    private messageService: MessageService,
    private translateService: TranslateService) { }

  ngOnInit() {
    this.ngxSpinnerService.show();
    this.display = false;
    this.showData = false;
    this.getVesselInfo();
    this.ngxSpinnerService.hide();
  }

  /**
   * Get vessel details
   *
   * @memberof VoyageStatusComponent
   */
  async getVesselInfo() {
    const res = await this.vesselsApiService.getVesselsInfo().toPromise();
    this.vesselInfo = res[0] ?? <IVessel>{};
    if (this.vesselInfo?.id) {
      localStorage.setItem("vesselId",this.vesselInfo?.id.toString())
      this.getVoyageInfo(this.vesselInfo?.id);
    }
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
    const alertForVoyageEnd = localStorage.getItem('alertForVoyageEnd');
    if(alertForVoyageEnd !== 'true'){
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
    const latestClosedVoyages = [...voyages?.filter(voyage => voyage?.statusId === VOYAGE_STATUS.CLOSE)]?.sort((a,b) => this.convertToDate(b.actualStartDate).getTime() - this.convertToDate(a.actualStartDate).getTime())?.slice(0, this.selectedVoyage ? 9 : 10);
    return [this.selectedVoyage, ...latestClosedVoyages];
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
  async getVoyageStatus(vesselId: number, voyageId: number, loadableStudyId: number) {
    this.ngxSpinnerService.show();
    this.voyageStatusResponse = await this.voyageApiService.getVoyageDetails(vesselId, voyageId, loadableStudyId, this.selectedPortDetails).toPromise();
    if (this.voyageStatusResponse?.responseStatus?.status === '200') {
      this.bunkerConditions = this.voyageStatusResponse?.bunkerConditions;
      this.cargoConditions = this.voyageStatusResponse?.cargoConditions;
      this.cargoQuantities = this.voyageStatusResponse?.cargoQuantities;
    }
    this.ngxSpinnerService.hide();
  }

  /**
   * Emit port data from port rotation ribbon
   * @param portData 
   */
  getPortDetails(portData: IVoyageDetails) {
    this.selectedPortDetails = portData;
    this.getVoyageStatus(this.vesselInfo?.id, this.selectedVoyage?.id, this.selectedVoyage?.confirmedLoadableStudyId);
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
    this.voyageInfo.forEach(async (voyage) => {
      const translationKeys = await this.translateService.get(['VOYAGE_STATUS_ACTIVE_END_WARNING', 'VOYAGE_STATUS_ACTIVE_END_WARNING_MESSAGE_FIRST', 'VOYAGE_STATUS_ACTIVE_END_WARNING_MESSAGE_SECOND']).toPromise();
      if (voyage?.noOfDays >= 0) {
        this.messageService.add({ severity: 'warn', summary: translationKeys['VOYAGE_STATUS_ACTIVE_END_WARNING'], detail: translationKeys['VOYAGE_STATUS_ACTIVE_END_WARNING_MESSAGE_FIRST'] + " " + voyage?.endDate?.split(' ')[0] + ". " +  translationKeys['VOYAGE_STATUS_ACTIVE_END_WARNING_MESSAGE_SECOND'], sticky: true});
      }
    })

  }

}
