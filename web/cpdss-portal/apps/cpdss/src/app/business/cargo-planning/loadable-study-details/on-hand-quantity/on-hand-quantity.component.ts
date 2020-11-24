import { Component, Input, OnInit } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { IPort, IPortOHQDetails } from '../../models/cargo-planning.model';
import { LoadableStudyDetailsApiService } from '../../services/loadable-study-details-api.service';

/**
 * Compoent for OHQ tab
 *
 * @export
 * @class OnHandQuantityComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-on-hand-quantity',
  templateUrl: './on-hand-quantity.component.html',
  styleUrls: ['./on-hand-quantity.component.scss']
})
export class OnHandQuantityComponent implements OnInit {

  @Input() voyageId: number;
  @Input() loadableStudyId: number;
  @Input() vesselId: number;

  ohqPorts: IPort[];
  selectedPort: IPort;
  ports: IPort[];
  selectedPortOHQDetails: IPortOHQDetails[];

  constructor(private loadableStudyDetailsApiService: LoadableStudyDetailsApiService,
    private ngxSpinnerService: NgxSpinnerService) { }

  /**
   * NgOnit init function for ohq component
   *
   * @memberof OnHandQuantityComponent
   */
  ngOnInit(): void {
    this.getPortRotation();
  }

  /**
   * Method for  fetching ohq port rotation
   *
   * @memberof OnHandQuantityComponent
   */
  async getPortRotation() {
    this.ngxSpinnerService.show();
    this.ports = await this.loadableStudyDetailsApiService.getPorts().toPromise();
    const result = await this.loadableStudyDetailsApiService.getOHQPortRotation(this.vesselId, this.voyageId, this.loadableStudyId).toPromise();
    if (result?.portList) {
      this.ohqPorts = result?.portList?.map((ohqPort) => this.ports?.find((port) => port.id === ohqPort.portId));
      this.selectedPort = this.ohqPorts[0];
      this.selectedPortOHQDetails = await this.getPortOHQDetails(this.selectedPort?.id);
    }
    this.ngxSpinnerService.hide();
  }

  /**
   * Method for fetching ohq details of selected port
   *
   * @param {number} portId
   * @memberof OnHandQuantityComponent
   */
  async getPortOHQDetails(portId: number): Promise<IPortOHQDetails[]> {
    const result = await this.loadableStudyDetailsApiService.getPortOHQDetails(this.vesselId, this.voyageId, this.loadableStudyId, portId).toPromise();
    return result?.onHandQuantities ?? [];
  }

  /**
   * Method for hadling the port selection
   *
   * @param {IPort} port
   * @memberof OnHandQuantityComponent
   */
  async onPortSelection(port: IPort) {
    this.ngxSpinnerService.show();
    this.selectedPort = port;
    this.selectedPortOHQDetails = await this.getPortOHQDetails(this.selectedPort?.id);
    this.ngxSpinnerService.hide();
  }

}
