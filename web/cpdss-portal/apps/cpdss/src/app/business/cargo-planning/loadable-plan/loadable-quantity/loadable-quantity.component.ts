import { Component, OnInit, Input } from '@angular/core';
import { DecimalPipe } from '@angular/common';
import { NgxSpinnerService } from 'ngx-spinner';

import { LoadablePlanTransformationService } from '../../services/loadable-plan-transformation.service';

import { ITableHeaderModel, ILoadableQuantityCargo, ILoadableQuantityCommingleCargo, ITotalLoadableQuality } from '../../models/loadable-plan.model';

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
    return [
      {
        "id": 1,
        "grade": "GET",
        "tankName": "4C",
        "quantity": "1000",
        "api": "22",
        "temp": "20",
        "cargo1Abbreviation": "AER",
        "cargo2Abbreviation": "APP",
        "cargo1Percentage": "40",
        "cargo2Percentage": "60",
        "cargo1Bblsdbs": "20",
        "cargo2Bblsdbs": "300",
        "cargo1Bbls60f": "400",
        "cargo2Bbls60f": "500",
        "cargo1LT": "60",
        "cargo2LT": "40",
        "cargo1MT": "300",
        "cargo2MT": "200",
        "cargo1KL": "500",
        "cargo2KL": "800"
      }
    ];
  }

  public columns: ITableHeaderModel[];
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
    this.total = <ITotalLoadableQuality>{ orderBblsdbs: 0, orderBbls60f: 0, loadableBblsdbs: 0, loadableBbls60f: 0, loadableLT: 0, loadableMT: 0, loadableKL: 0, differencePercentage: 0 };
  }

  /**
   * calculate total for orderBblsdbs, orderBbls60f ,
   * loadableBblsdbs , loadableLT , loadableKL
  */
  private calculateTotal(loadableQuantityData: ILoadableQuantityCargo[]) {
    loadableQuantityData?.map((value: ILoadableQuantityCargo) => {
      this.total.orderBblsdbs += Number(value.orderBblsdbs);
      this.total.orderBbls60f += Number(value.orderBbls60f);
      this.total.loadableBblsdbs += Number(value.loadableBblsdbs);
      this.total.loadableBbls60f += Number(value.loadableBbls60f);
      this.total.loadableLT += Number(value.loadableLT);
      this.total.loadableMT += Number(value.loadableMT);
      this.total.loadableKL += Number(value.loadableKL);
      this.total.differencePercentage += Number(value.differencePercentage);
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
