import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';

import { IVessel } from '../../core/models/vessel-details.model';
import { VesselsApiService } from '../../core/services/vessels-api.service';
import { IDataTableColumn } from '../../../shared/components/datatable/datatable.model';
import { CargoHistoryTransformationService } from './../services/cargo-history-transformation.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { ICargoHistoryDetails } from '../models/cargo-planning.model';

/**
 * This temporary data will remove once the actual API integreted.
 */
const tempData: ICargoHistoryDetails[] = [
  {
    vesselName: 'YUFUSAN',
    loadingPort: 'Mina Al Ahmadi',
    grade: 'Kuwait Export',
    year: 2020,
    month: 1,
    date: 12,
    api: 30.50,
    temperature: 88.3
  },
  {
    vesselName: 'SHIZUKISAN',
    loadingPort: 'RAS TANURA',
    grade: 'ARABIAN EXTRA LIGHT',
    year: 2020,
    month: 1,
    date: 4,
    api: 39.60,
    temperature: 97.8
  }
];

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
  cargoHistoryGridColumns: IDataTableColumn[];
  cargoHistoryGridData: ICargoHistoryDetails[];

  constructor(
    private vesselsApiService: VesselsApiService,
    private cargoHistoryTransformationService: CargoHistoryTransformationService,
    private ngxSpinnerService: NgxSpinnerService
  ) { }

  async ngOnInit(): Promise<void> {
    this.ngxSpinnerService.show();
    const res = await this.vesselsApiService.getVesselsInfo().toPromise();
    this.vesselInfo = res[0] ?? <IVessel>{};
    this.cargoHistoryGridColumns = this.cargoHistoryTransformationService.getCargoHistoryGridColumns();
    this.cargoHistoryGridData = tempData;
    this.ngxSpinnerService.hide();
  }

}
