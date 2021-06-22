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
        groupId: 1, id:1, label: 'Oil spill Equipment - 1', checked: false, subList: [
          { id: 1.1, label: 'Oil spill Equipment to be displayed and ready for use before cargo operation start-11', checked: false },
          { id: 1.2, label: 'Oil spill Equipment to be displayed and ready for use before cargo operation start- 232', checked: false },

        ]
      },
      {
        groupId: 1, id:2, label: 'Oil spill Equipment - 5', checked: false, subList: [
          { id: 1.1, label: 'Oil spill Equipment to be displayed and ready for use before cargo operation start -new', checked: false },
          { id: 1.2, label: 'Oil spill Equipment to be displayed and ready for use before cargo operation start-old', checked: false },

        ]
      },
      { groupId: 2, id:3, label: 'Oil spill Equipment - 2', checked: false, subList: [
        { id: 1.1, label: 'Oil spill Equipment to be displayed and ready for use before cargo operation start -1', checked: false },
        { id: 1.2, label: 'Oil spill Equipment to be displayed and ready for use before cargo operation start - 2', checked: false },

      ] },
      { groupId: 2, id:4, label: 'Oil spill Equipment - 3', checked: false, subList: [] },
      {
        groupId: 3, id:5, label: 'Oil spill Equipment - 3', checked: false, subList: [
          { id: 3.1, label: 'Oil spill Equipment to be displayed and ready for use before cargo operation start-3', checked: false },
          { id: 3.2, label: 'Oil spill Equipment to be displayed and ready for use before cargo operation start-4', checked: false },

        ]
      },
      {
        groupId: 4, id:6, label: 'Oil spill Equipment - 3', checked: false, subList: [
          { id: 4.1, label: 'Oil spill Equipment to be displayed and ready for use before cargo operation start - 4.1', checked: false },
          { id: 4.2, label: 'Oil spill Equipment to be displayed and ready for use before cargo operation start- 4.2', checked: false },
          { id: 4.3, label: 'Oil spill Equipment to be displayed and ready for use before cargo operation start- 4.3', checked: false },

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
    this.instructionList = this.instructionData.filter(item => item.groupId === id);
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
