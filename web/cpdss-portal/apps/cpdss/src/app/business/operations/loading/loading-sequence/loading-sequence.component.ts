import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { QUANTITY_UNIT, RATE_UNIT } from '../../../../shared/models/common.model';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { OPERATIONS } from '../../../core/models/common.model';
import { LoadingDischargingTransformationService } from '../../services/loading-discharging-transformation.service';

/**
 * Component class for loading sequence component
 *
 * @export
 * @class LoadingSequenceComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-loading-sequence',
  templateUrl: './loading-sequence.component.html',
  styleUrls: ['./loading-sequence.component.scss']
})
export class LoadingSequenceComponent implements OnInit, OnDestroy {
  @Input() vesselId: number;
  @Input() voyageId: number;
  @Input() portRotationId: number;
  @Input() loadingInfoId: number;
  @Input() operation: OPERATIONS;

  currentQuantitySelectedUnit = <QUANTITY_UNIT>localStorage.getItem('unit');
  currentRateSelectedUnit = <RATE_UNIT>localStorage.getItem('rate_unit');
  ngUnsubscribe: Subject<void> = new Subject();

  constructor(private loadingDischargingTransformationService: LoadingDischargingTransformationService) { }

  ngOnInit(): void {
    this.initSubscriptions();
  }

  ngOnDestroy() {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  /**
   * Method to initialize all subscriptions
   *
   * @memberof LoadingSequenceComponent
   */
  initSubscriptions() {
    this.loadingDischargingTransformationService.unitChange$.pipe(takeUntil(this.ngUnsubscribe)).subscribe((res) => {
      this.currentQuantitySelectedUnit = <QUANTITY_UNIT>localStorage.getItem('unit');
    });
    this.loadingDischargingTransformationService.rateUnitChange$.pipe(takeUntil(this.ngUnsubscribe)).subscribe((res) => {
      this.currentRateSelectedUnit = <RATE_UNIT>localStorage.getItem('rate_unit');
    });
  }

}
