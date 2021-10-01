import { Component, OnInit, Input, ViewChild, ElementRef, Output, EventEmitter , OnDestroy } from '@angular/core';
import { FormGroup, FormControl, ValidationErrors } from '@angular/forms';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { ConfirmationService, TreeNode } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';
import { InstructionCheckListApiService } from './services/instruction-check-list-api.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { IInstructionDetails, IDeleteData, ISaveStatusData } from './models/instruction-check-list.model';
import { whiteSpaceValidator } from '../../core/directives/space-validator.directive';
import { LoadingDischargingTransformationService } from '../services/loading-discharging-transformation.service';
import { OPERATIONS } from '../../core/models/common.model';
import { IPermission } from '../../../shared/models/user-profile.model';
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
export class InstructionCheckListComponent implements OnInit , OnDestroy{

  @ViewChild('editField') editElement: ElementRef;
  @Input() operation: OPERATIONS;
  @Input() set instructionList(value: TreeNode[]) {
    this.instructionListData = [];
    this.instructionListData = value && [...this.setInstructionList(value)];
    this.hasUnsavedChanges = false;
    this.setSelectedData();
  }
  disableSaveButton: boolean = false;
  isDischargeStarted: boolean;

  get instructionList(): TreeNode[] {
    return this.instructionListData;
  }

  @Output() updateData: EventEmitter<any> = new EventEmitter();
  @Output() tabStatus: EventEmitter<any> = new EventEmitter();
  @Input() vesselId: number;
  @Input() voyageId: number;
  @Input() portRotationId: number;
  @Input() groupId: number;
  @Input() loadingDischargingInfoId: number;
  @Input() permission: IPermission;

  selectAll: boolean;
  instructionListData: TreeNode[];
  selectedData: TreeNode[];
  textFieldLength: number;
  oldData: any;
  instructionForm: FormGroup;
  errorMessages: any = {
    'whitespace': 'INSTRUCTION_HEAD_REQUIRED'
  };
  hasUnsavedChanges = false;
  private ngUnsubscribe: Subject<any> = new Subject();

  constructor(
    private translateService: TranslateService,
    private confirmationService: ConfirmationService,
    private instructionCheckListApiService: InstructionCheckListApiService,
    private ngxSpinnerService: NgxSpinnerService,
    private messageService: MessageService,
    private loadingDischargingTransformationService: LoadingDischargingTransformationService
  ) { }

  ngOnInit(): void {
    this.formGroupInit();
    this.getSaveButtonStatus();
  }

  /**
 * unsubscribing Instruction CheckList  observable
 * @memberof InstructionCheckListComponent
 */
  ngOnDestroy() {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  /**
   *Method to get status of save button.
   *
   * @memberof InstructionCheckListComponent
   */

  getSaveButtonStatus() {
    this.loadingDischargingTransformationService.disableSaveButton.subscribe((status) => {
      this.disableSaveButton = status;
    })
    this.loadingDischargingTransformationService.isDischargeStarted$.pipe(takeUntil(this.ngUnsubscribe)).subscribe((value) => {
      this.isDischargeStarted = value;
    })
  }

  /**
   * Initialize formGroup
   *
   * @memberof InstructionCheckListComponent
   */
  formGroupInit() {
    this.instructionForm = new FormGroup({
      name: new FormControl('', [whiteSpaceValidator]),
    });
  }

  /**
   * Formating the selected data
   *
   * @memberof InstructionCheckListComponent
   */
  setSelectedData() {
    this.selectedData = [];
    this.instructionListData.map(item => {
      if (item.children?.length) {
        let selectedCount = 0;
        item.children.map(child => {
          if (child.data?.isChecked) {
            this.selectedData.push(child);
            selectedCount++;
          }
        });
        if (selectedCount && selectedCount === item.children?.length) {
          this.selectedData.push(item);
        } else if (selectedCount && selectedCount !== item.children?.length) {
          item.partialSelected = true;
        }
      } else {
        if (item.data?.isChecked) {
          this.selectedData.push(item);
        }
      }
    });
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
        label: item.subHeaderName,
        expanded: true,
        data: { ...item },
        children: [],
      });
      if (item?.loadingInstructionsList?.length) {
        item.loadingInstructionsList.map(subList => {
          list[list.length - 1].children.push({
            label: subList.instruction,
            data: { ...subList },
          });
        });
      }
    });
    return list;
  }

  /**
   * check box uncheck event
   *
   * @param {Event}
   * @memberof InstructionCheckListComponent
   */
  nodeUnselect(event) {
    this.hasUnsavedChanges = true;
  }

  /**
   * check box check event
   *
   * @param {Event}
   * @memberof InstructionCheckListComponent
   */
  nodeSelect(event) {
    this.hasUnsavedChanges = true;
  }

  /**
   * check box select all event
   *
   * @param {Event}
   * @memberof InstructionCheckListComponent
   */
  selectAllData(event) {
    this.hasUnsavedChanges = true;
  }
  /**
   * Save function for user created check function
   *
   * @param {Event}
   * @memberof InstructionCheckListComponent
   */
  async saveData(event) {
    if (!this.instructionForm.controls.name.value || this.instructionForm.controls.name.value.toString().trim() === '') {
      this.instructionForm.markAllAsTouched();
      this.instructionForm.markAsDirty();
      return;
    }
    let data: IInstructionDetails = {};
    const translationKeys = await this.translateService.get(['LOADING_INSTRUCTION_SUCCESS', 'LOADING_INSTRUCTION_SUCCESS_INSTRUCTION_MESSAGE', 'LOADING_INSTRUCTION_SUCCESS_SUBHEADER_MESSAGE', 'LOADING_INSTRUCTION_ERROR', 'LOADING_INSTRUCTION_ERROR_MESSAGE', 'LOADING_INSTRUCTION_SUCCESS_INSTRUCTION_UPDATE_MESSAGE', 'LOADING_INSTRUCTION_SUCCESS_SUBHEADER_UPDATE_MESSAGE']).toPromise();
    if (event.node?.data?.addFlag) {
      if (event.level === 1) {
        const childIsSelected = this.selectedData.filter(element => element.data?.instructionId === event.node?.data?.instructionId);
        data = {
          instructionHeaderId: this.groupId,
          isSingleHeader: false,
          isSubHeader: false,
          subHeaderId: event?.parent?.data?.subHeaderId,
          instruction: this.instructionForm.controls.name.value,
          isChecked: childIsSelected?.length ? true : false
        }
      } else {
        const isSelected = this.selectedData.filter(element => element.data?.subHeaderId === event.node?.data?.subHeaderId);
        data = {
          instructionHeaderId: this.groupId,
          isChecked: isSelected?.length ? true : false,
          isSingleHeader: event?.node?.data?.isSingleHeader,
          isSubHeader: true,
          instruction: this.instructionForm.controls.name.value
        }
      }
      this.ngxSpinnerService.show();
      const result = await this.instructionCheckListApiService.saveInstruction(this.vesselId, this.loadingDischargingInfoId, this.portRotationId, data).toPromise();
      this.ngxSpinnerService.hide();
      if (result?.responseStatus?.status === 'SUCCESS') {
        this.messageService.add({ severity: 'success', summary: translationKeys['LOADING_INSTRUCTION_SUCCESS'], detail: event.level === 1 || event?.node?.data?.isSingleHeader ? translationKeys['LOADING_INSTRUCTION_SUCCESS_INSTRUCTION_MESSAGE'] : translationKeys['LOADING_INSTRUCTION_SUCCESS_SUBHEADER_MESSAGE'] });
        this.updateData.emit(true);
        this.instructionForm.controls.name.setValue('');
        this.instructionForm.reset();
      } else {
        this.messageService.add({ severity: 'error', summary: translationKeys['LOADING_INSTRUCTION_ERROR'], detail: translationKeys['LOADING_INSTRUCTION_ERROR_MESSAGE'] });
      }

    } else {
      data = {
        instructionId: event.level === 0 ? event?.node?.data?.subHeaderId : event?.node?.data?.instructionId,
        instruction: this.instructionForm.controls.name.value
      };

      this.ngxSpinnerService.show();
      const result = await this.instructionCheckListApiService.updateInstruction(this.vesselId, this.loadingDischargingInfoId, this.portRotationId, data).toPromise();
      this.ngxSpinnerService.hide();
      if (result?.responseStatus?.status === 'SUCCESS') {
        this.messageService.add({ severity: 'success', summary: translationKeys['LOADING_INSTRUCTION_SUCCESS'], detail: event.level === 1 || event?.node?.data?.isSingleHeader ? translationKeys['LOADING_INSTRUCTION_SUCCESS_INSTRUCTION_UPDATE_MESSAGE'] : translationKeys['LOADING_INSTRUCTION_SUCCESS_SUBHEADER_UPDATE_MESSAGE'] });
        this.updateData.emit(true);
        this.instructionForm.controls.name.setValue('');
        this.instructionForm.reset();
      } else {
        this.messageService.add({ severity: 'error', summary: translationKeys['LOADING_INSTRUCTION_ERROR'], detail: translationKeys['LOADING_INSTRUCTION_ERROR_MESSAGE'] });
      }
    }
    this.loadingDischargingTransformationService.isLoadingInfoComplete.subscribe((status) => {
      if (status) {
        this.loadingDischargingTransformationService.inProcessing.next(false);
      }
    })
  }

  /**
   * check if any edit is currenlty active.
   *
   * @param row
   * @memberof InstructionCheckListComponent
   */
  isEditActive() {
    let editEnabled = false;
    for (let i = 0; i < this.instructionListData.length; i++) {
      if (this.instructionListData[i].data?.editable) {
        editEnabled = true;
        break;
      }
      if (this.instructionListData[i]?.children?.length) {
        this.instructionListData[i].children.map(item => {
          if (item.data.editable) {
            editEnabled = true;
          }
        });
      }
      if (editEnabled) { break; }
    }
    return editEnabled;
  }

  /**
   * add sublist
   *
   * @param row
   * @memberof InstructionCheckListComponent
   */
  addChild(row) {
    if (this.isEditActive()) { return; }
    let rowIndex = null;
    this.instructionListData.map((item, index) => {
      if (row.node.data.subHeaderId === item.data.subHeaderId) {
        rowIndex = index;
        item.children.push(
          {
            label: '',
            expanded: true,
            data: {
              label: '',
              editable: true,
              isEditable: true,
              instructionId: new Date().getTime(),
              addFlag: true
            },
          }
        );
      }
    });
    this.textFieldLength = 500;
    const itemIndex = this.selectedData.findIndex(item => item?.data?.subHeaderId === row?.node?.data?.subHeaderId);
    if (itemIndex > -1) {
      this.selectedData.splice(itemIndex, 1);
      this.instructionListData[rowIndex].partialSelected = true;
    }
    this.instructionListData = [...this.instructionListData];
    setTimeout(() => {
      this.editElement.nativeElement.scrollIntoView();
    });
  }

  /**
   * check main check list
   *
   * @memberof InstructionCheckListComponent
   */
  addParent() {
    if (this.isEditActive()) { return; }
    this.instructionListData.push(
      {
        label: '',
        expanded: true,
        children: [],
        data: {
          label: '',
          editable: true,
          isEditable: true,
          subHeaderId: new Date().getTime(),
          addFlag: true,
          isSingleHeader: false
        },
      }
    )
    this.textFieldLength = 250;
    this.instructionListData = [...this.instructionListData];
    setTimeout(() => {
      this.editElement.nativeElement.scrollIntoView();
    });
  }

  /**
   * Add new instruction
   *
   * @memberof InstructionCheckListComponent
   */
  addNewInstruction() {
    if (this.isEditActive()) { return; }
    this.instructionListData.push(
      {
        label: '',
        expanded: true,
        children: [],
        data: {
          label: '',
          editable: true,
          isEditable: true,
          subHeaderId: new Date().getTime(),
          addFlag: true,
          isSingleHeader: true
        },
      }
    )
    this.textFieldLength = 500;
    this.instructionListData = [...this.instructionListData];
    setTimeout(() => {
      this.editElement.nativeElement.scrollIntoView();
    });
  }

  /**
   * Edit function for user created check lists
   *
   * @param data
   * @memberof InstructionCheckListComponent
   */
  editData(data) {
    if (this.isEditActive()) { return; }
    this.oldData = data.node.data;
    data.node.data.editable = true;
    this.instructionForm.controls.name.setValue(data.node?.label);
    this.textFieldLength = data.level === 0 ? 250 : 500;
  }

  /**
   * Delete confirmation for user created check lists
   *
   * @param {Event}
   * @memberof InstructionCheckListComponent
   */
  deleteConfirm(data) {
    if(this.disableSaveButton) { return; }
    const translationKeys = this.translateService.instant(['LOADING_INSTRUCTION_DELETE_SUMMARY', 'LOADING_INSTRUCTION_DELETE_DETAILS', 'LOADING_INSTRUCTION_DELETE_CONFIRM_LABEL', 'LOADING_INSTRUCTION_DELETE_REJECT_LABEL']);

    this.confirmationService.confirm({
      key: 'confirmation-alert',
      header: translationKeys['LOADING_INSTRUCTION_DELETE_SUMMARY'],
      message: translationKeys['LOADING_INSTRUCTION_DELETE_DETAILS'],
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: translationKeys['LOADING_INSTRUCTION_DELETE_CONFIRM_LABEL'],
      acceptIcon: 'pi',
      acceptButtonStyleClass: 'btn btn-main mr-5',
      rejectVisible: true,
      rejectLabel: translationKeys['LOADING_INSTRUCTION_DELETE_REJECT_LABEL'],
      rejectIcon: 'pi',
      rejectButtonStyleClass: 'btn btn-main',
      accept: async () => {
        this.deleteData(data);
      }
    });
  }

  /**
   * Delete function for user created check lists
   *
   * @param {data}
   * @memberof InstructionCheckListComponent
   */
  async deleteData(data) {
    const translationKeys = await this.translateService.get(['LOADING_INSTRUCTION_SUCCESS', 'LOADING_INSTRUCTION_DELETE_MESSAGE', 'LOADING_INSTRUCTION_ERROR', 'LOADING_INSTRUCTION_ERROR_MESSAGE']).toPromise();
    const payload: IDeleteData = {
      instructionId: data.level === 1 ? data?.node?.data?.instructionId : data?.node?.data?.subHeaderId
    };
    this.ngxSpinnerService.show();
    const result = await this.instructionCheckListApiService.deleteInstruction(this.vesselId, this.loadingDischargingInfoId, this.portRotationId, payload).toPromise();
    this.ngxSpinnerService.hide();
    if (result?.responseStatus?.status === 'SUCCESS') {
      this.messageService.add({ severity: 'success', summary: translationKeys['LOADING_INSTRUCTION_SUCCESS'], detail: translationKeys['LOADING_INSTRUCTION_DELETE_MESSAGE'] });
      this.updateData.emit(true);
    } else {
      this.messageService.add({ severity: 'error', summary: translationKeys['LOADING_INSTRUCTION_ERROR'], detail: translationKeys['LOADING_INSTRUCTION_ERROR_MESSAGE'] });
    }
  }

  /**
   * select/partial select parent based on child selection
   *
   * @param {children}
   * @memberof InstructionCheckListComponent
   */
  checkParentSelection(children) {
    let childCheckCount = 0;
    children.map(child => {
      if (child.data?.isChecked) {
        childCheckCount++;
      }
    });
    return (childCheckCount && childCheckCount === children?.length);
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
    } else {
      if (data.level === 1) {
        this.instructionListData.map(item => {
          if (item.data.subHeaderId === data.parent.data.subHeaderId) {
            item.children.splice(item?.children?.length - 1, 1);
          }
          if (item?.children?.length) {
            const selectParent = this.checkParentSelection(item.children);
            if (selectParent) {
              item.partialSelected = false;
              this.selectedData.push(item);
            }
          } else {
            if (item?.data?.isChecked) {
              item.partialSelected = false;
              this.selectedData.push(item);
            }
          }
        });

      } else {
        const index = this.instructionListData.findIndex(item => item.data.subHeaderId === data.node.data.subHeaderId);
        if (index > -1) {
          this.instructionListData.splice(index, 1);
        }
      }
      this.instructionForm.controls.name.setValue('');
      this.instructionForm.reset();
      this.instructionListData = [...this.instructionListData];
    }
    this.instructionForm.reset();
  }

  /**
   * Save instruction states - checked/unchecked
   *
   * @memberof InstructionCheckListComponent
   */
  async saveAll() {
    if (this.isEditActive()) {
      this.instructionForm.markAllAsTouched();
      this.instructionForm.markAsDirty();
      return;
    }
    const data: ISaveStatusData = {
      instructionList: []
    };
    const translationKeys = await this.translateService.get(['LOADING_INSTRUCTION_SUCCESS', 'LOADING_INSTRUCTION_SAVEALL_SUCCESS', 'LOADING_INSTRUCTION_ERROR', 'LOADING_INSTRUCTION_ERROR_MESSAGE']).toPromise();
    this.instructionListData.map(item => {
      const isSelected = this.selectedData.filter(element => element.data?.subHeaderId === item.data?.subHeaderId);
      if (isSelected?.length) {
        data.instructionList.push({ instructionId: item.data?.subHeaderId, isChecked: true });
        if (item?.children?.length) {
          item?.children.map(child => {
            data.instructionList.push({ instructionId: child.data?.instructionId, isChecked: true });
          });
        }
      } else {
        data.instructionList.push({ instructionId: item.data?.subHeaderId, isChecked: false });
        if (item?.children?.length) {
          item?.children.map(child => {
            const childIsSelected = this.selectedData.filter(element => element.data?.instructionId === child.data?.instructionId);
            data.instructionList.push({ instructionId: child.data?.instructionId, isChecked: childIsSelected?.length ? true : false });
          });
        }
      }
    });
    this.ngxSpinnerService.show();
    const result = await this.instructionCheckListApiService.updateCheckListStatus(this.vesselId, this.loadingDischargingInfoId, this.portRotationId, data).toPromise();
    this.ngxSpinnerService.hide();
    if (result?.responseStatus?.status === 'SUCCESS') {
      this.tabStatus.emit(true);
      this.messageService.add({ severity: 'success', summary: translationKeys['LOADING_INSTRUCTION_SUCCESS'], detail: translationKeys['LOADING_INSTRUCTION_SAVEALL_SUCCESS'] });
      this.hasUnsavedChanges = false;
      this.updateData.emit(true);
    } else {
      this.messageService.add({ severity: 'error', summary: translationKeys['LOADING_INSTRUCTION_ERROR'], detail: translationKeys['LOADING_INSTRUCTION_ERROR_MESSAGE'] });
    }
    this.loadingDischargingTransformationService.isLoadingInfoComplete.subscribe((status) => {
      if (status) {
        this.loadingDischargingTransformationService.inProcessing.next(false);
      }
    })
  }

  /**
 * Get field errors
 * @param {string} formControlName
 * @returns {ValidationErrors}
 * @memberof InstructionCheckListComponent
*/
  fieldError(formControlName: string): ValidationErrors {
    const formControl = this.field(formControlName);
    return formControl?.invalid && (formControl.dirty || formControl.touched) ? formControl.errors : null;
  }

  /**
  * Get form control of instructionForm 
  * @param {string} formControlName
  * @returns {FormControl}
  * @memberof InstructionCheckListComponent
  */
  field(formControlName: string): FormControl {
    const formControl = <FormControl>this.instructionForm.get(formControlName);
    return formControl;
  }

}