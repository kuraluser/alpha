import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';

import { NgxSpinnerService } from 'ngx-spinner';

import { Voyage } from '../../core/models/common.model';
import { IVessel } from '../../core/models/vessel-details.model';

import { VoyageService } from '../../core/services/voyage.service';
import { VesselsApiService } from '../../core/services/vessels-api.service';
import { PermissionsService } from '../../../shared/services/permissions/permissions.service';

/**
 * Component class of discharge study plan
 *
 * @export
 * @class DischargeStudyComponent
 * @implements {OnInit}
 */

@Component({
  selector: 'cpdss-portal-discharge-study',
  templateUrl: './discharge-study.component.html',
  styleUrls: ['./discharge-study.component.scss']
})
export class DischargeStudyComponent implements OnInit {

  voyageId: number;
  vesselId: number;
  dischargeStudyId: number;
  vesselInfo: IVessel;
  voyages: Voyage[];
  selectedVoyage: Voyage;

  private ngUnsubscribe: Subject<any> = new Subject();

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private permissionsService: PermissionsService,
    private ngxSpinnerService: NgxSpinnerService,
    private vesselsApiService: VesselsApiService,
    private voyageService: VoyageService,
  ) { }

  /**
   * NgOnit init function for Discharge study plan component
   *
   * @memberof DischargeStudyComponent
  */
  ngOnInit(): void {
    this.activatedRoute.paramMap
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(params => {
        this.vesselId = Number(params.get('vesselId'));
        this.voyageId = Number(params.get('voyageId'));
        this.dischargeStudyId = Number(params.get('dischargeStudyId'));
        localStorage.setItem("vesselId", this.vesselId.toString())
        localStorage.setItem("voyageId", this.voyageId.toString())
        localStorage.setItem("dischargeStudyId", this.dischargeStudyId.toString())
        this.getDischargeStudies(this.vesselId, this.voyageId, this.dischargeStudyId);
      });

  }

  /**
* Method to fetch all discharge 
*
* @param {number} vesselId
* @param {number} voyageId
* @param {number} dischargeStudyId
* @memberof DischargeStudyComponent
*/
  async getDischargeStudies(vesselId: number, voyageId: number, dischargeStudyId: number) {
    this.ngxSpinnerService.show();
    const res = await this.vesselsApiService.getVesselsInfo().toPromise();
    this.vesselInfo = res[0] ?? <IVessel>{};
    this.voyages = await this.getVoyages(this.vesselId, this.voyageId);
    this.ngxSpinnerService.hide();
  }

  /**
 * Method to fetch all voyages in the vessel
 *
 * @param {number} vesselId
 * @param {number} voyageId
 * @memberof DischargeStudyComponent
 */
  async getVoyages(vesselId: number, voyageId: number) {
    const result = await this.voyageService.getVoyagesByVesselId(vesselId).toPromise();
    const voyages = result ?? [];
    this.selectedVoyage = voyages.find(voyage => voyage.id === voyageId);
    return voyages;
  }
  /**
   * Handler for voyage dropdown change
   *
   * @param {*} event
   * @memberof DischargeStudyComponent
  */
  onVoyageChange(event: any) {
    this.voyageId = event?.value?.id;
    this.router.navigate([`business/cargo-planning/discharge-study-details/${this.vesselId}/${this.voyageId}/0`]);
  }

  /**
   * Method to route back to loadbale study
   *
   * @memberof DischargeStudyComponent
  */
  backToDischargeStudy() {
    this.router.navigate([`business/cargo-planning/discharge-study-details/${this.vesselId}/${this.voyageId}/${this.dischargeStudyId}`]);
  }

}
