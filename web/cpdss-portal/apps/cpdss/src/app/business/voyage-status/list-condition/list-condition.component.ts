import { Component, Input, OnInit } from '@angular/core';

/**
 * Component class for list condition
 *
 * @export
 * @class ListConditionComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-list-condition',
  templateUrl: './list-condition.component.html',
  styleUrls: ['./list-condition.component.scss']
})
export class ListConditionComponent implements OnInit {

  @Input() list: number;

  constructor() { }

  ngOnInit(): void {

  }

}
