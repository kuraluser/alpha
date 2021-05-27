import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'cpdss-portal-loading-instruction',
  templateUrl: './loading-instruction.component.html',
  styleUrls: ['./loading-instruction.component.scss']
})

/**
 * Component class for loading instruction component
 *
 * @export
 * @class LoadingInstructionComponent
 * @implements {OnInit}
 */

export class LoadingInstructionComponent implements OnInit {

  panelList = [];
  instructionList = [];
  instructionData = [];
  constructor() { }

  ngOnInit(): void {

    this.panelList = [
      { id: 1, label: 'Preparation for entering and cargo loading', selected: true },
      { id: 2, label: 'Deballasting Operation', selected: false },
      { id: 3, label: 'Precautions/Recording', selected: false },
      { id: 4, label: 'Others', selected: false }
    ];

    this.instructionData = [
      {
        id: 1, label: 'Oil spill Equipment - 1', checked: false, subList: [
          { id: 1.1, label: 'Oil spill Equipment to be displayed and ready for use before cargo operation start', checked: false },
          { id: 1.2, label: 'Oil spill Equipment to be displayed and ready for use before cargo operation start', checked: false },

        ]
      },
      {
        id: 1, label: 'Oil spill Equipment - 5', checked: false, subList: [
          { id: 1.1, label: 'Oil spill Equipment to be displayed and ready for use before cargo operation start', checked: false },
          { id: 1.2, label: 'Oil spill Equipment to be displayed and ready for use before cargo operation start', checked: false },

        ]
      },
      { id: 2, label: 'Oil spill Equipment - 2', checked: false, subList: [] },
      { id: 2, label: 'Oil spill Equipment - 3', checked: false, subList: [] },
      {
        id: 3, label: 'Oil spill Equipment - 3', checked: false, subList: [
          { id: 3.1, label: 'Oil spill Equipment to be displayed and ready for use before cargo operation start', checked: false },
          { id: 3.2, label: 'Oil spill Equipment to be displayed and ready for use before cargo operation start', checked: false },

        ]
      },
      {
        id: 4, label: 'Oil spill Equipment - 3', checked: false, subList: [
          { id: 4.1, label: 'Oil spill Equipment to be displayed and ready for use before cargo operation start', checked: false },
          { id: 4.2, label: 'Oil spill Equipment to be displayed and ready for use before cargo operation start', checked: false },
          { id: 4.3, label: 'Oil spill Equipment to be displayed and ready for use before cargo operation start', checked: false },

        ]
      }
    ];

    this.setInstructionList(1);
  }

  /**
   * filter instruction list
   *
   * @param id
   * @memberof LoadingComponent
   */
  setInstructionList(id) {
    this.instructionList = this.instructionData.filter(item => item.id === id);
  }

  /**
   * Row selection of instruction side panel
   *
   * @param event
   * @memberof LoadingComponent
   */
  sidePanelInstructionChange(event) {
    if (event) {
      this.setInstructionList(event.id)
    }
  }

}
