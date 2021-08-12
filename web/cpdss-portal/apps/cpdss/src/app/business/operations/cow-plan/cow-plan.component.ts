import { Component, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { ICargo, ITank } from '../../core/models/common.model';
import { ICOWDetails, IDischargeOperationListData } from '../models/loading-discharging.model';

/**
 * Component class for COW plan setion
 *
 * @export
 * @class CowPlanComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-cow-plan',
  templateUrl: './cow-plan.component.html',
  styleUrls: ['./cow-plan.component.scss']
})
export class CowPlanComponent implements OnInit {
  @Input() cowDetails: ICOWDetails;
  @Input() listData: IDischargeOperationListData;
  @Input() cargoTanks: ITank[];
  @Input() loadedCargos: ICargo[];
  @Input() form: FormGroup;

  get cowDetailsForm() {
    return <FormGroup>this.form.get('cowDetails');
  }

  constructor() { }

  ngOnInit(): void {
  }

}
