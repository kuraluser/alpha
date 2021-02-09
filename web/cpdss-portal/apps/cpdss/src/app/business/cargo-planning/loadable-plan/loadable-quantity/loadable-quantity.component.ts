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
    this.loadableQuantityData = [];
    value?.map((loadableQuantityData: ILoadableQuantityCargo) => {
      this.loadableQuantityData.push(this.loadablePlanTransformationService.getFormatedLoadableQuantityData(this._decimalPipe, loadableQuantityData))
    });
    this.calculateTotal(this.loadableQuantityData);
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
    this.total = <ITotalLoadableQuality>{ orderBblsdbs: 0, orderBbls60f: 0, orderedQuantity: 0, loadableBblsdbs: 0, loadableBbls60f: 0, loadableLT: 0, loadableMT: 0, loadableKL: 0, differencePercentage: 0 };
    loadableQuantityData?.map((value: ILoadableQuantityCargo) => {
      this.total.orderBblsdbs += this.convertToNumber(value?.orderBblsdbs);
      this.total.orderBbls60f += this.convertToNumber(value?.orderBbls60f);
      this.total.orderedQuantity += this.convertToNumber(value?.orderedQuantity);
      this.total.loadableBblsdbs += this.convertToNumber(value?.loadableBblsdbs);
      this.total.loadableBbls60f += this.convertToNumber(value?.loadableBbls60f);
      this.total.loadableLT += this.convertToNumber(value?.loadableLT);
      this.total.loadableMT += this.convertToNumber(value?.loadableMT);
      this.total.loadableKL += this.convertToNumber(value?.loadableKL);
      this.total.differencePercentage += (value?.differencePercentageValue);
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

  /**
   * parse number from formatted string
   * @returns {number}
   */
  convertToNumber(value: string) {
    value = value?.replace(',', '');
    return Number(value)
  }

   /**
   * parse number from formatted percentage string
   * @returns {number}
   */
  convertPercentageToNumber(value: string) {
    value = value?.replace('%', '');
    return Number(value)
  }

}
