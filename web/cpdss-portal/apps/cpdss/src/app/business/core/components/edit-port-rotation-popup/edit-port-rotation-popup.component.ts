import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { IVessel } from '../../models/vessel-details.model';
import { PortRotationService } from '../../services/port-rotation.service';
import { IEditPortRotationModel, Voyage } from '../../models/common.model';
import { MessageService } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';
import { IPortList, IPortsDetailsResponse } from '../../models/common.model';

/**
 * Component class of EditPortRotation
 *
 * @export
 * @class EditPortRotationPopupComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-edit-port-rotation-popup',
  templateUrl: './edit-port-rotation-popup.component.html',
  styleUrls: ['./edit-port-rotation-popup.component.scss']
})
export class EditPortRotationPopupComponent implements OnInit {
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
  isPortVisited = false;

  constructor(private ngxSpinnerService: NgxSpinnerService,
    private editPortRotationApiService: PortRotationService,
    private messageService: MessageService,
    private translateService: TranslateService) { }

  /**
   * Component lifecycle ngOnit
   *
   * @returns {Promise<void>}
   * @memberof EditPortRotationPopupComponent
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
    const translationKeys = await this.translateService.get(['EDIT_PORT_ROTATION_POPUP_SUCCESS', 'EDIT_PORT_ROTATION_POPUP_SAVED_SUCCESSFULLY', 'EDIT_PORT_ROTATION_POPUP_ERROR', 'EDIT_PORT_ROTATION_POPUP_STATUS_ERROR']).toPromise();
    const portSave: IEditPortRotationModel = { portList: [] };
    portSave.portList = JSON.parse(JSON.stringify(this.portList));
    try {
      const res = await this.editPortRotationApiService.saveEditPortRotation(this.vesselDetails.id, this.voyageId, this.loadableStudyId, portSave).toPromise();
      this.ngxSpinnerService.hide();
      if (res?.responseStatus?.status === '200') {
        this.editPortRotationApiService.portOrderChange.next(true);
        this.messageService.add({ severity: 'success', summary: translationKeys['EDIT_PORT_ROTATION_POPUP_SUCCESS'], detail: translationKeys['EDIT_PORT_ROTATION_POPUP_SAVED_SUCCESSFULLY'] });
        this.cancel();
      }
    }
    catch (errorResponse) {
      if (errorResponse?.error?.errorCode === 'ERR-RICO-110') {
        this.messageService.add({ severity: 'error', summary: translationKeys['EDIT_PORT_ROTATION_POPUP_ERROR'], detail: translationKeys['EDIT_PORT_ROTATION_POPUP_STATUS_ERROR'], life: 10000 });
      }
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
    const current = new Date();
    this.portList.map(async (port, i) => {
      if (port?.portOrder !== i + 1) {
        if (port?.etaActual) {
          this.isFutureDate = false;
          this.isPortVisited = true;
          this.portList = JSON.parse(JSON.stringify(this.portListOriginal));
          return;
        }
        else if (port?.portOrder !== i + 1) {
          port.portOrder = i + 1;
          this.isPortVisited = false;
          this.updateEtaAndEtd(i);
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
      this.portList[j].etaActual = '';
      this.portList[j].etdActual = '';
      this.portList[j].eta = '';
      this.portList[j].etd = '';
      this.portList[j].distanceBetweenPorts = 0;
      this.portList[j].portOrder = i + 1;
    }
  }
}
