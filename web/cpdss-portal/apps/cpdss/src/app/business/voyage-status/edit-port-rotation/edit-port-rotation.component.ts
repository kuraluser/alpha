import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { IVessel } from '../../core/models/vessel-details.model';
import { EditPortRotationApiService } from '../services/edit-port-rotation-api.service';
import { IEditPortRotationModel } from '../models/edit-port-rotation.model';
import { Voyage } from '../../core/models/common.model';
import { MessageService } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';
import { IPortList, IPortsDetailsResponse } from '../../core/models/common.model';

/**
 * Component class of EditPortRotation
 *
 * @export
 * @class EditPortRotationComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-edit-port-rotation',
  templateUrl: './edit-port-rotation.component.html',
  styleUrls: ['./edit-port-rotation.component.scss']
})
export class EditPortRotationComponent implements OnInit {
  @Input() vesselDetails: IVessel;
  @Input() voyageDetails: Voyage;
  @Output() displayPopUp = new EventEmitter<boolean>();

  selectedPorts: IPortList;
  voyage: Voyage;
  voyageId: number;
  loadableStudyId: number;
  showPopUp: boolean;
  portList: IPortList[];
  portListOriginal: IPortList[];
  isFutureDate = true;
  voyageName: string;

  constructor(private ngxSpinnerService: NgxSpinnerService,
    private editPortRotationApiService: EditPortRotationApiService,
    private messageService: MessageService,
    private translateService: TranslateService) { }

  /**
   * Component lifecycle ngOnit
   *
   * @returns {Promise<void>}
   * @memberof EditPortRotationComponent
   */
  async ngOnInit(): Promise<void> {
    this.showPopUp = true;
    this.ngxSpinnerService.show();
    this.voyageName = this.voyageDetails?.voyageNo;
    this.voyageId = this.voyageDetails?.id;
    this.loadableStudyId = this.voyageDetails?.confirmedLoadableStudyId;
    const result = await this.editPortRotationApiService.getPorts().toPromise();
    const portsFormData: IPortsDetailsResponse = await this.editPortRotationApiService.getPortsDetails(this.vesselDetails.id, this.voyageId, this.loadableStudyId).toPromise();
    this.portList = portsFormData.portList.map(itm => ({
      ...result.find((item) => (item.id === itm.portId) && item),
      ...itm
    }));
    this.portListOriginal = JSON.parse(JSON.stringify(this.portList));
    this.ngxSpinnerService.hide();

  }
  /**
   * Save edit port rotaion
   */
  async saveEditPortRotation() {
    this.ngxSpinnerService.show();
    const translationKeys = await this.translateService.get(['EDIT_PORT_ROTATION_POPUP_SUCCESS', 'EDIT_PORT_ROTATION_POPUP_SAVED_SUCCESSFULLY']).toPromise();
    const portSave: IEditPortRotationModel = {portList:[]};
    portSave.portList = JSON.parse(JSON.stringify(this.portList));
    const res = await this.editPortRotationApiService.saveEditPortRotation(this.vesselDetails.id, this.voyageId, this.loadableStudyId, portSave).toPromise();
    this.ngxSpinnerService.hide();
    if (res?.responseStatus?.status  === '200') {
      this.messageService.add({ severity: 'success', summary: translationKeys['EDIT_PORT_ROTATION_POPUP_SUCCESS'], detail: translationKeys['EDIT_PORT_ROTATION_POPUP_SAVED_SUCCESSFULLY'] });
      this.cancel();
    }
  }

  /**
   * cancel popup
   */
  cancel() {
    this.displayPopUp.emit(false);
  }
/**
 * Method to reorder ports
 * @param event 
 */
  async portOrderChange(event) {
    const i = 0;
    const  current = new Date();
    this.portList.map(async (port, i) => {
      const dateAndTime = (port?.etaActual).split(" ");
      const date = dateAndTime[0];
      const time = dateAndTime[1];
      const newdate = date.split("-").reverse().join("-");
      const formatedDate = new Date(newdate + ' ' + time);
      const d1 = current.getTime();
      const d2 = formatedDate.getTime();

      if (port.portOrder !== i + 1) {
        if (d1 > d2) {
          this.isFutureDate = false;
          this.portList = JSON.parse(JSON.stringify(this.portListOriginal));
          return;
        }
        else {
          if (this.isFutureDate) {
            this.portList = this.portList.map((ports, i) => {
              if (ports.portOrder !== i + 1) {
                this.updateEtaAndEtd(i);
              }
              ports.portOrder = i + 1;
              i++;
              return ports
            });
            this.portListOriginal = JSON.parse(JSON.stringify(this.portList));
          }
        }
      }
    });
  }

  /**
   * update ports data
   * @param ports 
   * @param i 
   */
  updateEtaAndEtd(i: number) {
    for (let j = i; j < this.portList.length; j++) {
      this.portList[j].eta = '';
      this.portList[j].etd = '';
      this.portList[j].distanceBetweenPorts = 0;
    }
  }
}
