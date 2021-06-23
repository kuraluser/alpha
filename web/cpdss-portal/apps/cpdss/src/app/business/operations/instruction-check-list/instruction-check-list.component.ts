import { Component, OnInit, Input } from '@angular/core';
import { ConfirmationService, TreeNode } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';
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


  @Input() set instructionList(value: TreeNode[]) {
    this.instructionListData = [];
    this.instructionListData = value && [...this.setInstructionList(value)];
  }

  get instructionList(): TreeNode[] {
    return this.instructionListData;
  }

  selectAll: boolean;
  instructionListData: TreeNode[];
  selectedData: TreeNode[];
  textFieldLength: number;
  oldData: any;

  constructor(
    private translateService: TranslateService,
    private confirmationService: ConfirmationService
  ) { }

  ngOnInit(): void {

  }

  /**
   * Formating the instruction list
   *
   * @param value
   * @memberof InstructionCheckListComponent
   */
  setInstructionList(value) {
    const list = [];
    value.map(item => {
      list.push({
        label: item.label,
        expanded: true,
        data: item,
        children: [],
      });
      if (item.subList && item.subList.length) {
        item.subList.map(subList => {
          list[list.length - 1].children.push({
            label: subList.label,
            data: subList,
          });
        });
      }
    });
    return list;
  }

  /**
  * check box change event
  *
  * @param rowNode
  * @param rowData
  * @memberof InstructionCheckListComponent
  */
  parentNodeChange(rowNode, rowData) {

  }
  /**
   * check box uncheck event
   *
   * @param {Event}
   * @memberof InstructionCheckListComponent
   */
  nodeUnselect(event) {
  }

  /**
   * Save function for user created check function
   *
   * @param {Event}
   * @memberof InstructionCheckListComponent
   */
  saveData(event) {
    this.instructionListData.map(item => {
      if (event.level === 1) {
        if (item.data.id === event.parent.data.id) {
          item.children.map(child => {
            child.data.editable = false;
            child.data.addFlag = false;
            child.data.label = child.label;
          });
        }
      } else {
        if (item.data.id === event.node.data.id) {
          item.data.editable = false;
          item.data.addFlag = false;
          item.data.label = item.label;
        }
      }
    });
  }

  /**
   * add sublist
   *
   * @param row
   * @memberof InstructionCheckListComponent
   */
  addChild(row) {
    this.instructionListData.map(item => {
      if (row.node.data.id === item.data.id) {
        let isEditActive = false;
        item.children.map(child => {
          if (child.data.editable) {
            isEditActive = true;
          }
        });
        if (isEditActive) { return; }
        item.children.push(
          {
            label: '',
            expanded: true,
            data: {
              label: '',
              editable: true,
              userCreated: true,
              id: new Date().getTime(),
              addFlag: true
            },
          }
        )
      }
    });
    this.textFieldLength = 500;
    this.instructionListData = [...this.instructionListData];
  }

  /**
   * check main check list
   *
   * @memberof InstructionCheckListComponent
   */
  addParent() {
    let isEditActive = false;
    this.instructionListData.map(item => {
      if (item.data.editable) {
        isEditActive = true;
      }
    });
    if (isEditActive) { return; }
    this.instructionListData.push(
      {
        label: '',
        expanded: true,
        children: [],
        data: {
          label: '',
          editable: true,
          userCreated: true,
          id: new Date().getTime(),
          addFlag: true
        },
      }
    )
    this.textFieldLength = 250;
    this.instructionListData = [...this.instructionListData];
    setTimeout(() => {
      window.scrollTo(0, document.body.scrollHeight);
    });
  }

  /**
   * Edit function for user created check lists
   *
   * @param data
   * @memberof InstructionCheckListComponent
   */
  editData(data) {
    this.oldData = data.node.data;
    data.node.data.editable = true;
    this.textFieldLength = data.level === 0 ? 250 : 500;

  }

  /**
   * Delete function for user created check lists
   *
   * @param {Event}
   * @memberof InstructionCheckListComponent
   */
  deleteData(data) {
    const translationKeys = this.translateService.instant(['USER_DELETE_SUMMARY', 'USER_DELETE_DETAILS', 'USER_DELETE_CONFIRM_LABEL', 'USER_DELETE_REJECT_LABEL']);

    this.confirmationService.confirm({
      key: 'confirmation-alert',
      header: translationKeys['USER_DELETE_SUMMARY'],
      message: translationKeys['USER_DELETE_DETAILS'],
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: translationKeys['USER_DELETE_CONFIRM_LABEL'],
      acceptIcon: 'pi',
      acceptButtonStyleClass: 'btn btn-main mr-5',
      rejectVisible: true,
      rejectLabel: translationKeys['USER_DELETE_REJECT_LABEL'],
      rejectIcon: 'pi',
      rejectButtonStyleClass: 'btn btn-main',
      accept: async () => {
        if (data.level === 1) {
          this.instructionListData.map(item => {
            if (item.data.id === data.parent.data.id) {
              const index = item.children.findIndex(child => child.data.id === data.node.data.id);
              if (index > -1) {
                item.children.splice(index, 1);
              }
            }
          });
        } else {
          const index = this.instructionListData.findIndex(item => item.data.id === data.node.data.id);
          if (index > -1) {
            this.instructionListData.splice(index, 1);
          }
        }
        this.instructionListData = [...this.instructionListData];
      }
    });
  }

  /**
   * Cancel function for add/edit
   *
   * @param {Event}
   * @memberof InstructionCheckListComponent
   */
  cancel(data) {
    if (!data.node.data.addFlag) {
      data.node.data.editable = false;
      data.node.data = this.oldData;
      data.node.label = this.oldData.label;
    } else {
      if (data.level === 1) {
        this.instructionListData.map(item => {
          if (item.data.id === data.parent.data.id) {
            const index = item.children.findIndex(child => child.data.id === data.node.data.id);
            if (index > -1) {
              item.children.splice(index, 1);
            }
          }
        });
      } else {
        const index = this.instructionListData.findIndex(item => item.data.id === data.node.data.id);
        if (index > -1) {
          this.instructionListData.splice(index, 1);
        }
      }
      this.instructionListData = [...this.instructionListData];
    }
  }

}
