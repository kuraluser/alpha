import { Component, OnInit, Input } from '@angular/core';
import { IBallastStowageDetails } from '../../../../core/models/common.model';
import { LoadablePlanTransformationService } from '../../../services/loadable-plan-transformation.service';


/**
 * Component class of ballast section
 *
 * @export
 * @class BallastStowageComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-ballast-stowage',
  templateUrl: './ballast-stowage.component.html',
  styleUrls: ['./ballast-stowage.component.scss']
})
export class BallastStowageComponent implements OnInit {
  
  //public fields 
  public columns: any[];
  public editMode: false;

  //private fields 
  private _ballastDetails: IBallastStowageDetails[];

  @Input() set ballastDetails(value: IBallastStowageDetails[]) {
    this._ballastDetails = value;
  }

  get ballastDetails(): IBallastStowageDetails[] {
    return this._ballastDetails;
  }
  
  // public methods
  constructor(private loadablePlanTransformationService: LoadablePlanTransformationService) { }

  /**
   * Component lifecycle ngOnit
   * Method called while intialization the component
   * 
   * @returns {void}
   * @memberof BallastStowageComponent
   */
  ngOnInit(): void {
    this.columns = this.loadablePlanTransformationService.getBallastDatatableColumns();
  }

}
