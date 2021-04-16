import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'cpdss-portal-instruction-check-list',
  templateUrl: './instruction-check-list.component.html',
  styleUrls: ['./instruction-check-list.component.scss']
})

/**
 * Component class for instruction check list component
 *
 * @export
 * @class InstructionCheckListComponent
 * @implements {OnInit}
 */
export class InstructionCheckListComponent implements OnInit {

  @Input() instructionList: any;

  selectAll: boolean;
  constructor() { }

  ngOnInit(): void {


  }

}
