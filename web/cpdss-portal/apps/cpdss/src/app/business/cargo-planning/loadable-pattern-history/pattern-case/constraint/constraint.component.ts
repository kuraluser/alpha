import { Component, Input, OnInit } from '@angular/core';

/**
 * Component for priority grid
 *
 * @export
 * @class ConstraintComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-constraint',
  templateUrl: './constraint.component.html',
  styleUrls: ['./constraint.component.scss']
})
export class ConstraintComponent implements OnInit {
@Input() constraint: string[];

constraints: string;
  constructor() { }

  ngOnInit(): void {
    this.constraints = this.constraint.join('\n')
  }

}
