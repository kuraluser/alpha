import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { IDataTableColumn } from '../../../shared/components/datatable/datatable.model';
import { IDateTimeFormatOptions} from '../../../shared/models/common.model';
import { IPermission } from '../../../shared/models/user-profile.model';
import { TimeZoneTransformationService } from '../../../shared/services/time-zone-conversion/time-zone-transformation.service';
import { Voyage, VOYAGE_STATUS } from '../../core/models/common.model';
import { IVessel } from '../../core/models/vessel-details.model';
import { VesselsApiService } from '../../core/services/vessels-api.service';
import { VoyageService } from '../../core/services/voyage.service';
import { DischargeStudyListApiService } from '../services/discharge-study-list-api.service';
import { DischargeStudyListTransformationApiService } from '../services/discharge-study-list-transformation-api.service';


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
  dischargeStudyList: any[]
  VOYAGE_STATUS = VOYAGE_STATUS;
  selectedDischargeStudy: any //ToDo - change the type to any to model type once actual api is availble.


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
    private dischargeStudyListApiService: DischargeStudyListApiService

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
      this.voyages = this.getSelectedVoyages(result);
      this.ngxSpinnerService.hide();
      this.selectedVoyage = this.voyages[0];
      this.showDischargeStudyList();

    });
    this.loading = false;
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
    this.getDischargeStudyInfo(this.vesselDetails?.id, this.selectedVoyage.id);
  }

 /**
  * Method to show discharge study pop up.
  *
  * @memberof DischargeStudyListComponent
  */
 callNewDischargeStudyPopup() {
    this.display = true;
    this.edit = false;
  }

  /**
   * Method to show  pop up on edit
   *
   * @param {*} event
   * @memberof DischargeStudyListComponent
   */
  onRowSelect(event: any) {
    this.edit = true;
    this.display = true;
    this.selectedDischargeStudy = event.data;
    this.router.navigate([`/business/cargo-planning/discharge-study-details/${this.vesselDetails?.id}/${this.selectedVoyage.id}/${event.data.id}`]);
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
      const dischargeStudyList = result.loadableStudies.map(dischargeStudy => {
        dischargeStudy.createdDate = this.timeZoneTransformationService.formatDateTime(dischargeStudy.createdDate, dateFormatOptions);
        dischargeStudy.lastEdited = this.timeZoneTransformationService.formatDateTime(dischargeStudy.lastEdited, dateFormatOptions);
        return dischargeStudy;
      });
      dischargeStudyList?.length ? this.dischargeStudyList = [...dischargeStudyList] : this.dischargeStudyList = [];
    }
    this.ngxSpinnerService.hide();
  }
}

