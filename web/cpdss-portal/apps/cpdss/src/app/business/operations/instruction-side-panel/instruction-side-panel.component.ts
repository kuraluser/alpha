import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'cpdss-portal-instruction-side-panel',
  templateUrl: './instruction-side-panel.component.html',
  styleUrls: ['./instruction-side-panel.component.scss']
})

/**
 * Component class for instruction side panel component
 *
 * @export
 * @class InstructionSidePanelComponent
 * @implements {OnInit}
 */
export class InstructionSidePanelComponent implements OnInit {

  @Input() panelList: any;

  @Output() selectedInstructionChange = new EventEmitter();

  constructor() { }

  ngOnInit(): void {

  }

  /**
   * Row selection 
   *
   * @param row
   * @memberof InstructionSidePanelComponent
   */
  selectRow(row) {
    this.panelList.map(item => { item.selected = false; });
    this.selectedInstructionChange.emit(row);
    row.selected = true;
  }

}
