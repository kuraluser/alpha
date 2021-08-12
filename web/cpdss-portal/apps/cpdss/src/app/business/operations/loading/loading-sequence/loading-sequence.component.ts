import { Component, Input, OnInit } from '@angular/core';
import { OPERATIONS } from '../../../core/models/common.model';

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
export class LoadingSequenceComponent implements OnInit {
  @Input() vesselId: number;
  @Input() voyageId: number;
  @Input() portRotationId: number;
  @Input() loadingInfoId: number;
  @Input() operation: OPERATIONS;

  constructor() { }

  ngOnInit(): void {
  }

}
