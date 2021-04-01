import { Component, Input, OnInit } from '@angular/core';
import { ILoadablePatternCargoDetail } from '../../../models/loadable-pattern.model';

/**
 * Component for loading grade
 *
 * @export
 * @class GradeLoadingOrderComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-grade-loading-order',
  templateUrl: './grade-loading-order.component.html',
  styleUrls: ['./grade-loading-order.component.scss']
})
export class GradeLoadingOrderComponent implements OnInit {
  @Input() loadablePatternCargoDetails: ILoadablePatternCargoDetail[];
  @Input() index: number;
  @Input() showCaseLabel = true;
  constructor() { }

  ngOnInit(): void {
  }

}
