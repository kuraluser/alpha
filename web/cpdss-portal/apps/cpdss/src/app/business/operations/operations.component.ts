import { Component, OnInit } from '@angular/core';
import { Voyage, VOYAGE_STATUS } from '../core/models/common.model';
import { IVessel } from '../core/models/vessel-details.model';
import { VesselsApiService } from '../core/services/vessels-api.service';
import { VoyageService } from '../core/services/voyage.service';

@Component({
  selector: 'cpdss-portal-operations',
  templateUrl: './operations.component.html',
  styleUrls: ['./operations.component.scss']
})
export class OperationsComponent implements OnInit {
  vesselInfo: IVessel;
  voyages: Voyage[];
  selectedVoyage: Voyage;
  displayNewVoyagePopup: boolean;
  displayEditPortRotationPopup: boolean;

  constructor(private vesselsApiService: VesselsApiService,
    private voyageService: VoyageService) { }

  ngOnInit(): void {
    this.getVesselInfo();
  }

  /**
   * Get vessel details
   *
   * @memberof OperationsComponent
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
   * Get voyage info
   *
   * @param {number} vesselId
   * @memberof OperationsComponent
   */
  async getVoyageInfo(vesselId: number) {
    const voyages = await this.voyageService.getVoyagesByVesselId(vesselId).toPromise();
    this.voyages = this.getSelectedVoyages(voyages);
  }

  /**
   * Get list of active and closed voyages
   *
   * @param {Voyage[]} voyages
   * @returns {Voyage[]}
   * @memberof OperationsComponent
   */
   getSelectedVoyages(voyages: Voyage[]): Voyage[] {
     const defaultVoyage = localStorage.getItem("voyageId") ? voyages?.find(voyage => voyage?.id === Number(localStorage.getItem("voyageId"))) : null;
    this.selectedVoyage = localStorage.getItem("voyageId") && defaultVoyage ? defaultVoyage : voyages?.find(voyage => voyage?.statusId === VOYAGE_STATUS.ACTIVE);
    const latestClosedVoyages = [...voyages?.filter(voyage => voyage?.statusId === VOYAGE_STATUS.CLOSE)]?.sort((a,b) => this.convertToDate(b?.actualStartDate)?.getTime() - this.convertToDate(a?.actualStartDate)?.getTime())?.slice(0, this.selectedVoyage ? 9 : 10);
    return [this.selectedVoyage, ...latestClosedVoyages];
  }

  /**
  * Convert to date time(dd-mm-yyyy hh:mm)
  *
  * @memberof OperationsComponent
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
   * Open new voyage popu
   *
   * @memberof OperationsComponent
   */
   showNewVoyageDialog() {
    this.displayNewVoyagePopup = true;
  }

  /**
   * Toggle display value from new voyage popup
   *
   * @param {boolean} display
   * @memberof OperationsComponent
   */
  displayPopUpTab(display: boolean) {
    this.displayNewVoyagePopup = display;
  }

  /**
   * Show edit port rotation popup
   *
   * @memberof OperationsComponent
   */
  showEditPortRotation() {
    this.displayEditPortRotationPopup = true;
  }

  /**
   * Toggled display value from edit port rotation
   *
   * @param {boolean} display
   * @memberof OperationsComponent
   */
  displayEditPortRotationPopUpTab(display: boolean) {
    this.displayEditPortRotationPopup = display;
  }

}
