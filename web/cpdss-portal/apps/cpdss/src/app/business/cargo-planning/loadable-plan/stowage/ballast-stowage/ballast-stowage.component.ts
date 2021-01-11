import { Component, OnInit, Input } from '@angular/core';

import { LoadablePlanTransformationService } from '../../../services/loadable-plan-transformation.service';

import { IBallastStowageDetails } from '../../../models/loadable-plan.model';

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
    this._ballastDetails = [
      {
        "id": 1,
        "tankId": 25599,
        "rdgLevel": "10",
        "correctionFactor": "12",
        "correctedLevel": "14",
        "metricTon": "1000",
        "cubicMeter": "50",
        "percentage": "88",
        "sg": "10",
        "lcg": "12",
        "vcg": "18",
        "tcg": "16",
        "inertia": "20"
      },
      {
        "id": 2,
        "tankId": 25600,
        "rdgLevel": "12",
        "correctionFactor": "14",
        "correctedLevel": "16",
        "metricTon": "1500",
        "cubicMeter": "60",
        "percentage": "90",
        "sg": "12",
        "lcg": "14",
        "vcg": "20",
        "tcg": "16",
        "inertia": "18"
      }
    ];
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
