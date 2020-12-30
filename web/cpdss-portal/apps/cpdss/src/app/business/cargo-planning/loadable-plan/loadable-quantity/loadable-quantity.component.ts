import { Component, OnInit, Input } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';

import { LoadableQuantityApiService } from '../../services/loadable-quantity-api.service';
import { ITableHeaderModel, LodadableQuantityPlan } from '../../models/loadable-quantity.model';
import { DecimalPipe  } from '@angular/common';

interface Total {
  orderBblsdbs: number,
  orderBbls60f: number,
  loadableBblsdbs: number,
  loadableBbls60f: number,
  loadableLT: number,
  loadableMT: number,
  loadableKL: number,
  differencePercentage: number
}

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
  @Input() loadablePatternId: number;

  columns: ITableHeaderModel[];
  loadableQuantityDatas: LodadableQuantityPlan[];
  public total:Total; 

  constructor(
    private ngxSpinnerService: NgxSpinnerService,
    private loadableQuantityApiService: LoadableQuantityApiService,
    private _decimalPipe: DecimalPipe
  ) { }

  ngOnInit() {
    this.columns = this.loadableQuantityApiService.getLoadableQuantityTableColumns();
    this.getLoadableQuantity();
  }

  /**
 * Get all details for loadable quantity
 *
 */
  async getLoadableQuantity() {
    this.ngxSpinnerService.show();
    // let data = await this.loadableQuantityApiService.getLoadableQuantityData(this.vesselId, this.voyageId, this.loadableStudyId , this.loadablePatternId).toPromise();
    const loadableQuantityDatas = [
      {
        "id": 1,
        "grade": "Arabian",
        "estimatedAPI": "12.0000",
        "estimatedTemp": "12.0000",
        "orderBblsdbs": "10000",
        "orderBbls60f": "100",
        "minTolerence": "12",
        "maxTolerence": "10",
        "loadableBblsdbs": "1000",
        "loadableBbls60f": "100",
        "loadableLT": "12",
        "loadableMT": "13",
        "loadableKL": "14",
        "differencePercentage": "1.2000",
        "differenceColor": "#ffffff"
      }
    ];
    this.loadableQuantityDatas = [];
    loadableQuantityDatas?.map((loadableQuantityData) => {
      this.loadableQuantityDatas.push(this.loadableQuantityApiService.getFormatedLoadableQuantityData(this._decimalPipe , loadableQuantityData))
    })
    this.total = <Total>{ orderBblsdbs: 0, orderBbls60f: 0, loadableBblsdbs: 0, loadableBbls60f: 0, loadableLT: 0, loadableMT: 0, loadableKL: 0, differencePercentage: 0 };
    this.calculateTotal();
    this.ngxSpinnerService.hide();
  }

  /**
   * calcuate total for orderBblsdbs, orderBbls60f ,
   * loadableBblsdbs , loadableLT , loadableKL
  */
  private calculateTotal() {
    this.loadableQuantityDatas?.map((value: LodadableQuantityPlan) => {
      this.total.orderBblsdbs += Number(value.orderBblsdbs);
      this.total.orderBbls60f += Number(value.orderBbls60f);
      this.total.loadableBblsdbs += Number(value.loadableBblsdbs);
      this.total.loadableBbls60f += Number(value.loadableBbls60f);
      this.total.loadableLT += Number(value.loadableMT);
      this.total.loadableMT += Number(value.loadableMT);
      this.total.loadableKL += Number(value.loadableKL);
      this.total.differencePercentage += Number(value.differencePercentage);
    })
  }

  /**
   * parse nested string object
   * @returns nested object value
   */
  getPropByString(obj, propString) {
    if (!propString) return obj;
    let prop,
      props = propString.split(".");
    for (var i = 0, iLen = props.length - 1; i < iLen; i++) {
      prop = props[i];
      if (obj[prop] !== undefined) {
        obj = obj[prop];
      } else {
        break;
      }
    }
    return obj[props[i]];
  }

}
