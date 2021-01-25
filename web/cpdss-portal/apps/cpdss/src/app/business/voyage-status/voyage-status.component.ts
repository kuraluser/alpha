import { Component, OnInit } from '@angular/core';
import { VesselsApiService } from '../core/services/vessels-api.service';
import { IVessel } from '../core/models/vessel-details.model';
import { VoyageService } from '../core/services/voyage.service';
import { Voyage } from '../core/models/common.model';
import { EditPortRotationApiService } from './services/edit-port-rotation-api.service';
import { IPortsDetailsResponse } from '../core/models/common.model';
import { IEditPortRotation } from './models/edit-port-rotation.model';

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
  selectedVoyage: Voyage;
  voyageDistance: number;

  constructor(private vesselsApiService: VesselsApiService,
    private voyageService: VoyageService,
    private editPortRotationApiService: EditPortRotationApiService) { }

  ngOnInit(): void {
    this.display = false;
    this.showData = false;
    this.getVesselInfo();
    this.portDetails();
  }

  /**
   * Get vessel details
   *
   * @memberof VoyageStatusComponent
   */
  async getVesselInfo() {
    const res = await this.vesselsApiService.getVesselsInfo().toPromise();
    this.vesselInfo = res[0] ?? <IVessel>{};
    if(this.vesselInfo?.id){
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
  showEditPortRotation(){
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
  async getVoyageInfo(vesselId: number){
      const voyages = await this.voyageService.getVoyagesByVesselId(vesselId).toPromise();
      const voyage = voyages.filter(voyageActive => (voyageActive?.status === 'Active'));
      this.voyageInfo = voyage;
      this.selectedVoyage = voyage[0];
  }

  /**
   * Method to get port details
   */
  async portDetails(){
    const result = await this.editPortRotationApiService.getPorts().toPromise();
    const portsFormData: IPortsDetailsResponse = await this.editPortRotationApiService.getPortsDetails(this.vesselInfo.id, this.selectedVoyage.id, this.selectedVoyage.confirmedLoadableStudyId).toPromise();
    this.voyageDistance = 0;
    for(let i=0; i< portsFormData?.portList?.length; i++){
      this.voyageDistance = portsFormData?.portList[i].distanceBetweenPorts + this.voyageDistance;
    }
  }

}
