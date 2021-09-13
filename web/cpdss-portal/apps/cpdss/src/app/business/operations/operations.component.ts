import { Component , OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { IVoyagePortDetails, Voyage, VOYAGE_STATUS, OPERATIONS } from '../core/models/common.model';
import { IVessel } from '../core/models/vessel-details.model';
import { PortRotationService } from '../core/services/port-rotation.service';
import { VesselsApiService } from '../core/services/vessels-api.service';
import { VoyageService } from '../core/services/voyage.service';
import { OPERATION_TAB } from './models/operations.model';
import { LoadingDischargingTransformationService } from './services/loading-discharging-transformation.service';

/**
 * Component class for operations component
 *
 * @export
 * @class OperationsComponent
 * @implements {OnInit}
 * @implements {OnDestroy}
 */
@Component({
  selector: 'cpdss-portal-operations',
  templateUrl: './operations.component.html',
  styleUrls: ['./operations.component.scss']
})
export class OperationsComponent implements OnInit, OnDestroy {
  vessel: IVessel;
  voyages: Voyage[];
  showAddPortPopup = false;
  isRuleModalVisible = false;
  get selectedVoyage(): Voyage {
    return this._selectedVoyage;
  }

  set selectedVoyage(voyage: Voyage) {
    this._selectedVoyage = voyage;
    localStorage.setItem("voyageId", voyage?.id.toString())
    localStorage.removeItem("loadableStudyId")
    localStorage.removeItem("loadablePatternId")
  }

  voyageDistance: number;
  operationId: number;
  readonly OPERATIONS = OPERATIONS;
  readonly OPERATION_TAB = OPERATION_TAB;
  currentTab: OPERATION_TAB = OPERATION_TAB.INFORMATION;

  private _selectedVoyage: Voyage;
  private _ngUnsubscribe: Subject<any> = new Subject();

  constructor(private vesselsApiService: VesselsApiService,
    private voyageService: VoyageService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private messageService: MessageService,
    private translateService: TranslateService,
    private ngxSpinnerService: NgxSpinnerService,
    private loadingDischargingTransformationService: LoadingDischargingTransformationService,
    private portRotationService: PortRotationService) { }


  ngOnInit(): void {
    this.getVesselInfo();
    this.initSubscriptions();
  }

  ngOnDestroy() {
    this._ngUnsubscribe.next();
    this._ngUnsubscribe.complete();
  }

  /**
   * Initialise subsciptions
   *
   * @memberof OperationsComponent
   */
  initSubscriptions() {
    this.portRotationService.voyageDistance$.pipe(takeUntil(this._ngUnsubscribe)).subscribe((voyageDistance) => {
      this.voyageDistance = voyageDistance;
    });
    this.loadingDischargingTransformationService.tabChange$.pipe(takeUntil(this._ngUnsubscribe)).subscribe((tab) => {
      this.currentTab = tab;
    });
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
      localStorage.setItem("vesselId", this.vessel?.id.toString())
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
    const translationKeys = await this.translateService.get(['LOADING_INFORMATION_NO_ACTIVE_VOYAGE', 'LOADING_INFORMATION_NO_ACTIVE_VOYAGE_MESSAGE']).toPromise();
    const voyages = await this.voyageService.getVoyagesByVesselId(vesselId).toPromise();
    this.voyages = await this.getSelectedVoyages(voyages);
    if (this.voyages.length) {
      this.selectedVoyage = this.voyages[0];
    } else {
      this.messageService.add({ severity: 'error', summary: translationKeys['LOADING_INFORMATION_NO_ACTIVE_VOYAGE'], detail: translationKeys['LOADING_INFORMATION_NO_ACTIVE_VOYAGE_MESSAGE'] });
    }

  }

  /**
   * Get list of active and closed voyages
   *
   * @param {Voyage[]} voyages
   * @returns {Voyage[]}
   * @memberof OperationsComponent
   */
  getSelectedVoyages(voyages: Voyage[]): Voyage[] {
    this.selectedVoyage = voyages?.find(voyage => voyage?.statusId === VOYAGE_STATUS.ACTIVE);
    const _voyage = this.selectedVoyage ? [this.selectedVoyage] : [];
    return [..._voyage];
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
  async onPortSelection(port: IVoyagePortDetails) {
    await localStorage.setItem('selectedPortName', port?.name);
    localStorage.setItem('selectedPortId', port?.portId?.toString());
    this.loadingDischargingTransformationService.isDischargeStarted(false);
    this.operationId = port?.operationId;
      if (port?.operationId === 1) {
        this.router.navigate(['loading', this.vessel?.id, this.selectedVoyage?.id, port?.portRotationId], { relativeTo: this.activatedRoute });
        this.loadingDischargingTransformationService.isDischargeStarted(this.selectedVoyage.isDischargeStarted);
      } else if (port?.operationId === 2) {
        this.router.navigate(['discharging', this.vessel?.id, this.selectedVoyage?.id, port?.portRotationId], { relativeTo: this.activatedRoute });
      }
  }

  /**
 * Method for add port pop up visible
 *
 * @memberof OperationsComponent
 */
  addPort() {
    this.showAddPortPopup = true;
  }

  /**
  * Value from add-port
  */
  closeAddPortPoup(displayNew_: boolean) {
    this.showAddPortPopup = displayNew_;
  }

  /**
* Handler for unit change event
*
* @param {*} event
* @memberof OperationsComponent
*/
  onUnitChange() {
    this.loadingDischargingTransformationService.setUnitChanged(true);
  }

  /**
   * Method to close the modal.
   *
   * @memberof OperationsComponent
   */
  onRulesPopUpClose(event) {
    this.isRuleModalVisible = event;
  }

  /**
   * Handler for rate unit change event
   *
   * @memberof OperationsComponent
   */
  onRateUnitChange() {
    this.loadingDischargingTransformationService.setRateUnitChanged(true);
  }

}
