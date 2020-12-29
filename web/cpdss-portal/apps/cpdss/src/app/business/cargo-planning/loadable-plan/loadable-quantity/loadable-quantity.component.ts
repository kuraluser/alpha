import { Component, OnInit , Input } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';

import { LoadableQuantityApiService } from '../../services/loadable-quantity-api.service';
import { ILoadableQuantityColumn } from '../../models/loadable-quantity.model';

/**
 * Component class of loadable quantity component in loadable plan
 *
 * @export
 * @class LoadableQuantityComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-loadable-quantity',
  templateUrl: './loadable-quantity.component.html',
  styleUrls: ['./loadable-quantity.component.scss']
})
export class LoadableQuantityComponent implements OnInit {
  @Input() voyageId: number;
  @Input() loadableStudyId: number;
  @Input() vesselId: number;

  columns: ILoadableQuantityColumn[];
  value: any[];

  constructor(
    private ngxSpinnerService: NgxSpinnerService,
    private loadableQuantityApiService: LoadableQuantityApiService
  ) { }

  ngOnInit() {
    this.columns = this.loadableQuantityApiService.getLoadableQuantitytableColumns();
    this.value = [
      {
        grade: 'KUWAIT EXPORT C.O',
        estimate: { api: '30.10', temp: 99.0 },
        order: { bblsTemp: '1000,000', bblsF: 950000 },
        tlrnic: { min: '-5%', max: '10%' },
        loadable: { bblsTemp: '1000,000', bblsF: 950000, lt: 50000, mt: 56000, kl: 59000 },
        diff: 2.66
      }
    ]
  }

    
  /**
 * Get all details for loadable quantity
 *
 * @returns {Promise<IPortsDetailsResponse>}
 * @memberof PortsComponent
 */
async getLoadableQuantity() {
  this.ngxSpinnerService.show();
  await this.loadableQuantityApiService.getLoadableQuantityData(this.vesselId, this.voyageId, this.loadableStudyId)
  this.ngxSpinnerService.hide();
}

}
