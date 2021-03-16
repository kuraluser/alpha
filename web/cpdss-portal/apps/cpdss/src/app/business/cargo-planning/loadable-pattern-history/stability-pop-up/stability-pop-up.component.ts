import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { LoadableStudyPatternTransformationService } from '../../services/loadable-study-pattern-transformation.service';
import { IDataTableColumn } from '../../../../shared/components/datatable/datatable.model';
import { IStabilityParameter } from '../../models/loadable-pattern.model';

/**
 * Component for stability parameter
 *
 * @export
 * @class StabilityPopUpComponent
 * @implements {OnInit}
 */

@Component({
  selector: 'cpdss-portal-stability-pop-up',
  templateUrl: './stability-pop-up.component.html',
  styleUrls: ['./stability-pop-up.component.scss']
})
export class StabilityPopUpComponent implements OnInit {
  @Output() displayPopup = new EventEmitter();
  
  @Input() display;
  @Input() stabilityParameters: IStabilityParameter[] = [];
  columns: IDataTableColumn[];

  constructor(private loadableStudyPatternTransformationService: LoadableStudyPatternTransformationService) { }

  ngOnInit(): void {
    this.columns = this.loadableStudyPatternTransformationService.getStabilityDatatableColumns();
  }

   // for closing active modal commingle popup
   closeDialog() {
    this.displayPopup.emit(false);
  }
}
