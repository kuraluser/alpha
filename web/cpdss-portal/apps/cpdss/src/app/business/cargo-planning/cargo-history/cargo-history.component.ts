import { Component, OnInit } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { IVessel } from '../../core/models/vessel-details.model';
import { VesselsApiService } from '../../core/services/vessels-api.service';

/**
 * Component to show Cargo history
 *
 * @export
 * @class CargoHistoryComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-cargo-history',
  templateUrl: './cargo-history.component.html',
  styleUrls: ['./cargo-history.component.scss']
})
export class CargoHistoryComponent implements OnInit {

  vesselInfo: IVessel;

  constructor(
    private vesselsApiService: VesselsApiService,
    private ngxSpinnerService: NgxSpinnerService
  ) { }

  ngOnInit(): void {
    this.initMethod();
  }

  /**
   * Temporary function to show vessel info
   * @memberof CargoHistoryComponent
   */
  async initMethod() {
    this.ngxSpinnerService.show();
    const res = await this.vesselsApiService.getVesselsInfo().toPromise();
    this.vesselInfo = res[0] ?? <IVessel>{};
    this.ngxSpinnerService.hide();
  }

}
