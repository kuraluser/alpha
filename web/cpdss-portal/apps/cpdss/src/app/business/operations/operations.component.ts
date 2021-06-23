import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { IVoyagePortDetails, Voyage, VOYAGE_STATUS } from '../core/models/common.model';
import { IVessel } from '../core/models/vessel-details.model';
import { VesselsApiService } from '../core/services/vessels-api.service';
import { VoyageService } from '../core/services/voyage.service';

@Component({
  selector: 'cpdss-portal-operations',
  templateUrl: './operations.component.html',
  styleUrls: ['./operations.component.scss']
})
export class OperationsComponent implements OnInit {
  vessel: IVessel;
  voyages: Voyage[];

  get selectedVoyage(): Voyage {
    return this._selectedVoyage;
  }

  set selectedVoyage(voyage: Voyage) {
    this._selectedVoyage = voyage;
    localStorage.setItem("voyageId", voyage?.id.toString())
    localStorage.removeItem("loadableStudyId")
    localStorage.removeItem("loadablePatternId")
  }

  private _selectedVoyage: Voyage;

  constructor(private vesselsApiService: VesselsApiService,
    private voyageService: VoyageService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private messageService: MessageService,
    private translateService: TranslateService,
    private ngxSpinnerService: NgxSpinnerService) { }

  ngOnInit(): void {
    this.getVesselInfo();
  }

  /**
   * Get vessel details
   *
   * @memberof OperationsComponent
   */
   async getVesselInfo() {
     this.ngxSpinnerService.show();
    const res = await this.vesselsApiService.getVesselsInfo().toPromise();
    this.vessel = res[0] ?? <IVessel>{};
    if (this.vessel?.id) {
      localStorage.setItem("vesselId",this.vessel?.id.toString())
      await this.getVoyageInfo(this.vessel?.id);
      this.ngxSpinnerService.hide();
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
    this.selectedVoyage = this.voyages[0];
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
   * Handler for port selection
   *
   * @param {IVoyagePortDetails} port
   * @memberof OperationsComponent
   */
  onPortSelection(port: IVoyagePortDetails) {
    if(port?.operationId === 1) {
      this.router.navigate(['loading', this.vessel?.id, this.selectedVoyage?.id, port?.portRotationId], { relativeTo: this.activatedRoute});
    } else if (port?.operationId === 2) {
      this.router.navigate(['discharging', this.vessel?.id, this.selectedVoyage?.id], { relativeTo: this.activatedRoute });
    } else {
      const translationKeys = this.translateService.instant(['OPERATION_PLAN_NOT_AVAILABLE_INFO_SUMMARY', 'OPERATION_PLAN_NOT_AVAILABLE_INFO_DETAILS']);
      this.messageService.add({ severity: 'info', summary: translationKeys['OPERATION_PLAN_NOT_AVAILABLE_INFO_SUMMARY'], detail: translationKeys['OPERATION_PLAN_NOT_AVAILABLE_INFO_DETAILS'] });
    }
  }

}
