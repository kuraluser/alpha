import { Component, OnInit, Input } from '@angular/core';
import { DecimalPipe } from '@angular/common';
import { NgxSpinnerService } from 'ngx-spinner';

import { LoadablePlanTransformationService } from '../../services/loadable-plan-transformation.service';

import { ILoadableQuantityCargo, ILoadableQuantityCommingleCargo, ITotalLoadableQuality } from '../../models/loadable-plan.model';
import { IDataTableColumn } from '../../../../shared/components/datatable/datatable.model';

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

  @Input() set loadableQuantityCargoDetails(value: ILoadableQuantityCargo[]) {
    this.calculateTotal(value);
    this.loadableQuantityData = [];
    value?.map((loadableQuantityData: ILoadableQuantityCargo) => {
      this.loadableQuantityData.push(this.loadablePlanTransformationService.getFormatedLoadableQuantityData(this._decimalPipe, loadableQuantityData))
    });
  }

  @Input() set loadableQuantityCommingleCargoDetails(value: ILoadableQuantityCommingleCargo[]) {
    this._loadableQuantityCommingleCargoDetails = value;
  }

  get loadableQuantityCommingleCargoDetails(): ILoadableQuantityCommingleCargo[] {
    return this._loadableQuantityCommingleCargoDetails;
  }

  public columns: IDataTableColumn[];
  public loadableQuantityData: ILoadableQuantityCargo[];
  public _loadableQuantityCommingleCargoDetails: ILoadableQuantityCommingleCargo[];
  public total: ITotalLoadableQuality;

  constructor(
    private ngxSpinnerService: NgxSpinnerService,
    private loadablePlanTransformationService: LoadablePlanTransformationService,
    private _decimalPipe: DecimalPipe
  ) { }

  ngOnInit() {
    this.columns = this.loadablePlanTransformationService.getLoadableQuantityTableColumns();
  }

  /**
   * calculate total for orderBblsdbs, orderBbls60f ,
   * loadableBblsdbs , loadableLT , loadableKL
  */
  private calculateTotal(loadableQuantityData: ILoadableQuantityCargo[]) {
    this.total = <ITotalLoadableQuality>{ orderBblsdbs: 0, orderBbls60f: 0, loadableBblsdbs: 0, loadableBbls60f: 0, loadableLT: 0, loadableMT: 0, loadableKL: 0, differencePercentage: 0 };
    loadableQuantityData?.map((value: ILoadableQuantityCargo) => {
      this.total.orderBblsdbs += Number(value?.orderBblsdbs);
      this.total.orderBbls60f += Number(value?.orderBbls60f);
      this.total.loadableBblsdbs += Number(value?.loadableBblsdbs);
      this.total.loadableBbls60f += Number(value?.loadableBbls60f);
      this.total.loadableLT += Number(value?.loadableLT);
      this.total.loadableMT += Number(value?.loadableMT);
      this.total.loadableKL += Number(value?.loadableKL);
      this.total.differencePercentage += Number(value?.differencePercentage);
    })
  }

  /**
   * parse nested string object
   * @returns nested object value
   */
  public getPropByString(obj: any, propString: string) {
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
