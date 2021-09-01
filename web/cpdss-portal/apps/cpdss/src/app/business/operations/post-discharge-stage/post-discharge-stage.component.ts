import { Component, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { IPostDischargeStageTime } from '../models/loading-discharging.model';

/**
 * Component class for post discharge details component
 *
 * @export
 * @class PostDischargeStageComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-post-discharge-stage',
  templateUrl: './post-discharge-stage.component.html',
  styleUrls: ['./post-discharge-stage.component.scss']
})
export class PostDischargeStageComponent implements OnInit {
  @Input() form: FormGroup;
  @Input() postDischargeStageTime: IPostDischargeStageTime;
  postDischargeStageTimeData: IPostDischargeStageTime[] = [];

  get postDischargeStageTimeForm() {
    return <FormGroup>this.form.get('postDischargeStageTime');
  }

  constructor() { }

  ngOnInit(): void {
    this.postDischargeStageTimeData.push(this.postDischargeStageTime);
  }

}
