import { Component, OnInit } from '@angular/core';
import { IDataTableColumn } from 'apps/cpdss/src/app/shared/components/datatable/datatable.model';
import { PortMasterTransformationService } from '../../services/port-master-transformation.service';
import { PortMasterApiService } from '../../services/port-master-api.service';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { IPort } from 'apps/cpdss/src/app/shared/models/common.model';


/**
 * Component class of port lising component
 * @export
 * @class PortListingComponent
 * @implements {OnInit}
 */

@Component({
  selector: 'cpdss-portal-port-listing',
  templateUrl: './port-listing.component.html',
  styleUrls: ['./port-listing.component.scss']
})
export class PortListingComponent implements OnInit {

  public columns: IDataTableColumn[];
  public portsInfo: IPort[];

  constructor(private portMasterTransformationService: PortMasterTransformationService,
    private portMasterApiService: PortMasterApiService,
    private router: Router, private ngxSpinnerService: NgxSpinnerService) { }

  /**
   * Component lifecycle ngOninit
   * @memberof PortListingComponent
   */
  async ngOnInit() {
    this.ngxSpinnerService.show();
    this.portMasterApiService.getPorts().subscribe((result) => {
      this.portsInfo = result;
      this.ngxSpinnerService.hide();
    });
    this.columns = this.portMasterTransformationService.getPortListDatatableColumns();
  }


  /**
   * Method to navigate to add port component on row selection
   * @param {selectedPort}
   * @memberof PortListingComponent
   */

  onRowSelect(selectedPort) {
    this.router.navigate([`/business/admin/port-listing/add-port/${selectedPort.data.portId}`]);
  }

}
