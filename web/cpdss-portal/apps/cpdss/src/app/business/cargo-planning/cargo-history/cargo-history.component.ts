import { Component, OnInit } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { IVessel } from '../../core/models/vessel-details.model';
import { VesselsApiService } from '../../core/services/vessels-api.service';

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

  async initMethod() {
    this.ngxSpinnerService.show();
    const res = await this.vesselsApiService.getVesselsInfo().toPromise();
    console.log(res)
    this.vesselInfo = res[0] ?? <IVessel>{};
    this.ngxSpinnerService.hide();
  }

}
