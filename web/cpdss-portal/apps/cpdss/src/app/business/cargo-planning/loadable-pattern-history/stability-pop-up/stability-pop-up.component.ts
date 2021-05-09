import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { DecimalPipe } from '@angular/common';
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

  private _stabilityParameters: IStabilityParameter[];
  columns: IDataTableColumn[];

  @Output() displayPopup = new EventEmitter();
  
  @Input() display;
  @Input() 
  set stabilityParameters (stabilityParameters: IStabilityParameter[]) {
    this._stabilityParameters = stabilityParameters.map((stabilityParameter: IStabilityParameter) => {
      stabilityParameter.forwardDraft = this.loadableStudyPatternTransformationService.decimalConvertion(this._decimalPipe, stabilityParameter.forwardDraft , '1.3-3')
      stabilityParameter.meanDraft = this.loadableStudyPatternTransformationService.decimalConvertion(this._decimalPipe, stabilityParameter.meanDraft , '1.2-2');
      stabilityParameter.afterDraft = this.loadableStudyPatternTransformationService.decimalConvertion(this._decimalPipe, stabilityParameter.afterDraft , '1.2-2');
      stabilityParameter.trim = this.loadableStudyPatternTransformationService.decimalConvertion(this._decimalPipe, stabilityParameter.trim , '1.2-2');
      stabilityParameter.bendinMoment = this.loadableStudyPatternTransformationService.decimalConvertion(this._decimalPipe, stabilityParameter.bendinMoment , '1.2-2');
      stabilityParameter.shearForce = this.loadableStudyPatternTransformationService.decimalConvertion(this._decimalPipe, stabilityParameter.shearForce , '1.2-2');
      return stabilityParameter;
    });
  }

  get stabilityParameters(): IStabilityParameter[] {
    return this._stabilityParameters;
  }
  
  

  constructor(
    private loadableStudyPatternTransformationService: LoadableStudyPatternTransformationService,
    private _decimalPipe: DecimalPipe) { }

  ngOnInit(): void {
    this.columns = this.loadableStudyPatternTransformationService.getStabilityDatatableColumns();
  }

   // for closing active modal commingle popup
   closeDialog() {
    this.displayPopup.emit(false);
  }
}
