import { Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { FileUpload } from 'primeng/fileupload';
import { IDropdownEvent, INewLoadableStudy } from "./new-loadable-study-popup.model";
import { IdraftMarks, ILoadLineList, INewLoadableStudyListNames, Voyage } from "../../models/common.model";
import { IVessel } from '../../../core/models/vessel-details.model';
import { LoadableStudyListApiService } from '../../../cargo-planning/services/loadable-study-list-api.service';
import { LoadableStudy } from '../../../cargo-planning/models/loadable-study-list.model';
import { Router } from '@angular/router';
import { numberValidator } from '../../../cargo-planning/directives/validator/number-validator.directive'
import { MessageService } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { whiteSpaceValidator } from '../../directives/space-validator.directive';


/**
 *  popup for creating / editing loadable-study
 */
@Component({
  selector: 'cpdss-portal-new-loadable-study-popup',
  templateUrl: './new-loadable-study-popup.component.html',
  styleUrls: ['./new-loadable-study-popup.component.scss']
})
export class NewLoadableStudyPopupComponent implements OnInit {

  @ViewChild('fileUpload') fileUpload: FileUpload;
  @Input() display;
  @Input()
  get vesselInfoList(): IVessel { return this._vesselInfoList; }
  set vesselInfoList(vesselInfoList: IVessel) {
    this._vesselInfoList = vesselInfoList;
  }
  @Input()
  get voyage(): Voyage { return this._voyage; }
  set voyage(voyage: Voyage) {
    this._voyage = voyage;
  }
  @Input()
  get loadableStudyList(): LoadableStudy[] { return this._loadableStudyList; }
  set loadableStudyList(loadableStudyList: LoadableStudy[]) {
    this._loadableStudyList = loadableStudyList;
  }

  @Input()
  get duplicateLoadableStudy(): LoadableStudy { return this._duplicateLoadableStudy; }
  set duplicateLoadableStudy(duplicateLoadableStudy: LoadableStudy) {
    this._duplicateLoadableStudy = duplicateLoadableStudy;
  }
  @Input() isEdit = false;
  @Input() selectedLoadableStudy: LoadableStudy;
  @Output() displayPopup = new EventEmitter();
  @Output() addedNewLoadableStudy = new EventEmitter<Object>();

  @ViewChild('fileUpload') fileUploadVariable: ElementRef;

  private _vesselInfoList: IVessel;
  private _voyage: Voyage;
  private _loadableStudyList: LoadableStudy[];
  private _duplicateLoadableStudy: LoadableStudy;

  newLoadableStudyFormGroup: FormGroup;
  newLoadableStudyPopupModel: INewLoadableStudy;
  newLoadableStudyListNames: INewLoadableStudyListNames;
  loadlineList: ILoadLineList;
  draftMarkList: IdraftMarks[] = [];
  uploadedFiles: any[] = [];
  editUploadFiles: any[] = [];
  loadlineLists: ILoadLineList[];
  showError = false;
  uploadError = "";
  newLoadableStudyNameExist = false;
  popUpHeader = "";
  saveButtonLabel = "";
  savedloadableDetails: any;
  deletedAttachments: number[];
  constructor(private loadableStudyListApiService: LoadableStudyListApiService,
    private formBuilder: FormBuilder,
    private router: Router,
    private messageService: MessageService,
    private translateService: TranslateService,
    private ngxSpinnerService: NgxSpinnerService) { }

  ngOnInit(): void {
    this.deletedAttachments = [];
    this.popUpHeader = this.isEdit ? "NEW_LOADABLE_STUDY_POPUP_EDIT_HEADER" : "NEW_LOADABLE_STUDY_POPUP_HEADER"
    this.saveButtonLabel = this.isEdit ? "NEW_LOADABLE_STUDY_POPUP_UPDATE_BUTTON" : "NEW_LOADABLE_STUDY_POPUP_SAVE_BUTTON";
    this.getVesselInfo();
  }

  //get loadlines data and create form group
  async getVesselInfo() {
    this.uploadedFiles = [];
    this.loadlineLists = this.vesselInfoList?.loadlines;
    this.createNewLoadableStudyFormGroup();
    if (this.isEdit) {
      this.updateLoadableStudyFormGroup(this.selectedLoadableStudy, true)
    } else {
      let isLoadableStudyAvailable;
      isLoadableStudyAvailable = this.duplicateLoadableStudy && Object.keys(this.duplicateLoadableStudy)?.length === 0 && this.duplicateLoadableStudy.constructor === Object
      if (!isLoadableStudyAvailable && this.duplicateLoadableStudy) {
        this.updateLoadableStudyFormGroup(this.duplicateLoadableStudy, false);
      } else {
        this.getLoadlineSummer();
      }
    }
  }

  //get summer loadline data
  getLoadlineSummer() {
    const result = this.loadlineLists.filter(e => e.name.toLowerCase() === 'summer');
    if (result.length > 0) {
      this.loadlineList = result[0];
      const draftMarkSummer = this.loadlineList.draftMarks[0];
      this.newLoadableStudyFormGroup.patchValue({
        loadLine: this.loadlineList,
        draftMark: { id: draftMarkSummer, name: draftMarkSummer }
      });
      this.onloadLineChange();
    }
  }

  // creating form-group for new-loadable-study
  async createNewLoadableStudyFormGroup() {
    this.newLoadableStudyFormGroup = this.formBuilder.group({
      duplicateExisting: '',
      newLoadableStudyName: this.formBuilder.control('', [Validators.required, Validators.maxLength(100), whiteSpaceValidator]),
      enquiryDetails: this.formBuilder.control('', [Validators.maxLength(1000)]),
      attachMail: null,
      charterer: this.vesselInfoList?.charterer,
      subCharterer: this.formBuilder.control('', [Validators.maxLength(30)]),
      loadLine: '',
      draftMark: '',
      draftRestriction: this.formBuilder.control('', [numberValidator(2, 2), Validators.min(0.01)]),
      maxAirTempExpected: this.formBuilder.control('', [numberValidator(2, 2), Validators.min(-99), Validators.max(99)]),
      maxWaterTempExpected: this.formBuilder.control('', [numberValidator(2, 3), Validators.min(-99), Validators.max(999)])
    });
  }

  // is Loadable Study Name Exist
  isNewLoadableStudyExist() {
    const nameExistence = this.loadableStudyList.some(e => e.name.toLowerCase() === this.newLoadableStudyFormGroup.controls.newLoadableStudyName.value.toLowerCase().trim());
    this.newLoadableStudyNameExist = this.isEdit ? (this.newLoadableStudyFormGroup.controls.newLoadableStudyName.value.toLowerCase().trim() === this.selectedLoadableStudy.name.toLocaleLowerCase() ? false : nameExistence) : nameExistence;
  }

  // post newLoadableStudyFormGroup for saving newly created loadable-study
  public async saveLoadableStudy() {
    if (this.newLoadableStudyFormGroup.valid) {
      const nameExistence = this.loadableStudyList.some(e => e.name.toLowerCase() === this.newLoadableStudyFormGroup.controls.newLoadableStudyName.value.toLowerCase().trim());
      this.newLoadableStudyNameExist = this.isEdit ? (this.newLoadableStudyFormGroup.controls.newLoadableStudyName.value.toLowerCase().trim() === this.selectedLoadableStudy.name.toLocaleLowerCase() ? false : nameExistence) : nameExistence;
      const translationKeys = await this.translateService.get(['LOADABLE_STUDY_CREATE_SUCCESS', 'LOADABLE_STUDY_CREATED_SUCCESSFULLY', 'LOADABLE_STUDY_CREATE_ERROR', 'LOADABLE_STUDY_ALREADY_EXIST', 'LOADABLE_STUDY_UPDATE_SUCCESS', 'LOADABLE_STUDY_UPDATED_SUCCESSFULLY']).toPromise();
      let isLoadableStudyAvailable;
      isLoadableStudyAvailable = this.duplicateLoadableStudy && Object.keys(this.duplicateLoadableStudy)?.length === 0 && this.duplicateLoadableStudy.constructor === Object;
      if (!this.newLoadableStudyNameExist) {
        this.newLoadableStudyPopupModel = {
          id: this.isEdit ? this.selectedLoadableStudy.id : 0,
          createdFromId: (!isLoadableStudyAvailable && this.duplicateLoadableStudy && !this.isEdit) ? 
            this.newLoadableStudyFormGroup.controls.duplicateExisting.value?.id : '',
          name: this.newLoadableStudyFormGroup.controls.newLoadableStudyName.value.trim(),
          detail: this.newLoadableStudyFormGroup.controls.enquiryDetails.value,
          attachMail: this.uploadedFiles,
          charterer: this.newLoadableStudyFormGroup.controls.charterer.value,
          subCharterer: this.newLoadableStudyFormGroup?.controls?.subCharterer?.value ? this.newLoadableStudyFormGroup?.controls?.subCharterer?.value : "",
          loadLineXId: this.newLoadableStudyFormGroup.controls.loadLine.value?.id,
          draftMark: this.newLoadableStudyFormGroup.controls.draftMark.value?.id,
          draftRestriction: this.newLoadableStudyFormGroup.controls.draftRestriction.value,
          maxAirTempExpected: this.newLoadableStudyFormGroup.controls.maxAirTempExpected.value !== undefined ? this.newLoadableStudyFormGroup.controls.maxAirTempExpected.value + '' : '',
          maxWaterTempExpected: this.newLoadableStudyFormGroup.controls.maxWaterTempExpected.value !== undefined ? this.newLoadableStudyFormGroup.controls.maxWaterTempExpected.value + '' : '',
          deletedAttachments: this.deletedAttachments.join(',')
        }
        this.ngxSpinnerService.show();
        try {
          const result = await this.loadableStudyListApiService.setLodableStudy(this.vesselInfoList?.id, this.voyage.id, this.newLoadableStudyPopupModel).toPromise();
          if (result.responseStatus.status === "200") {
            if (this.isEdit) {
              this.isLoadlineChanged() ? sessionStorage.setItem('loadableStudyInfo', JSON.stringify({ voyageId: this.voyage.id, vesselId: this.vesselInfoList?.id })) : null;
              this.messageService.add({ severity: 'success', summary: translationKeys['LOADABLE_STUDY_UPDATE_SUCCESS'], detail: translationKeys['LOADABLE_STUDY_UPDATED_SUCCESSFULLY'] });
            } else {
              this.messageService.add({ severity: 'success', summary: translationKeys['LOADABLE_STUDY_CREATE_SUCCESS'], detail: translationKeys['LOADABLE_STUDY_CREATED_SUCCESSFULLY'] });
            }
            this.closeDialog();
            this.addedNewLoadableStudy.emit(result.loadableStudyId)
          }
        } catch (error) {
          //TODO: The validation error for lodable study with same name exist error should be given in api response
          // this.messageService.add({ severity: 'error', summary: translationKeys['LOADABLE_STUDY_CREATE_ERROR'], detail: translationKeys['LOADABLE_STUDY_CREATION_FAILED'] });
        }
        this.ngxSpinnerService.hide();
      } else {
        //TODO: This must be moved to above catch expression
        this.messageService.add({ severity: 'error', summary: translationKeys['LOADABLE_STUDY_CREATE_ERROR'], detail: translationKeys['LOADABLE_STUDY_ALREADY_EXIST'] });
      }
    } else {
      this.newLoadableStudyFormGroup.markAllAsTouched();
    }
  }

  // this function triggers when choosing the files in fileUpload (primeng)
  selectFilesToUpload() {
    let uploadFile = [];
    const uploadedFileVar = this.fileUploadVariable.nativeElement.files;
    const extensions = ["docx", "pdf", "txt", "jpg", "jpeg", "png", "eml"];
    if (this.uploadedFiles.length < 5) {
      for (let i = 0; i < uploadedFileVar.length; i++) {
        const fileExtension = uploadedFileVar[i].name.substr((uploadedFileVar[i].name.lastIndexOf('.') + 1));
        if (extensions.includes(fileExtension.toLowerCase())) {
          if (uploadedFileVar[i].size / 1024 / 1024 >= 1) {
            this.showError = true;
            this.uploadError = "NEW_LOADABLE_STUDY_LIST_POPUP_FILE_SIZE_ERROR";
            continue;
          } else {
            if (uploadFile.length < 5) {
              const fileNameExist = this.uploadedFiles.some(file => file?.name?.toLowerCase() === uploadedFileVar[i].name.toLowerCase());
              if (fileNameExist) {
                this.showError = true;
                this.uploadError = "NEW_LOADABLE_STUDY_LIST_POPUP_FILE_NAME_EXIST_ERROR"
              } else {
                this.showError = false;
                uploadFile.push(uploadedFileVar[i]);
              }
            } else {
              this.showError = true;
              this.uploadError = "NEW_LOADABLE_STUDY_LIST_POPUP_FILE_LIMIT_ERROR"
            }
          }
        } else {
          this.showError = true;
          this.uploadError = "NEW_LOADABLE_STUDY_LIST_POPUP_FILE_FORMAT_ERROR";
          continue;
        }
      }
    } else {
      this.showError = true;
      this.uploadError = "NEW_LOADABLE_STUDY_LIST_POPUP_FILE_LIMIT_ERROR"
    }
    this.uploadedFiles.push(...uploadFile);
    uploadFile = [];
    this.fileUploadVariable.nativeElement.value = "";
  }

  //open selected file
  openFile(index, fileID = null) {
    if (fileID) {
      this.loadableStudyListApiService.downloadAttachment(this.vesselInfoList?.id, this.voyage.id, this.selectedLoadableStudy?.id, fileID);
    } else {
      const blob = new Blob([this.uploadedFiles[index]], { type: this.uploadedFiles[index].type })
      const fileurl = window.URL.createObjectURL(blob)
      window.open(fileurl)
    }
  }

  //remove selected file
  removeFile(index,  fileID = null) {
    if (fileID) {
      this.deletedAttachments.push(fileID);
    }
    this.uploadedFiles.splice(index, 1);
  }

  // returns form-controls of newLoadableStudyFormGroup
  get form() { return this.newLoadableStudyFormGroup.controls; }

  // for closing active modal popup
  closeDialog() {
    this.newLoadableStudyFormGroup.reset();
    this.displayPopup.emit(false);
    this.uploadError = "";
  }

  /**
   * Load draft mark List based on selected loadLine vaule
   */
  onloadLineChange() {
    const loadLine = this.newLoadableStudyFormGroup.get('loadLine').value;
    this.draftMarkList = loadLine.draftMarks.map(draftMarks => ({ id: draftMarks, name: draftMarks.toFixed(2) }));
    this.newLoadableStudyFormGroup.controls.draftMark.setValue(this.draftMarkList[0]);
  }

  //for set selcted loadable study value in loadable study form
  onDuplicateExisting(event: IDropdownEvent) {
    this.updateLoadableStudyFormGroup(event.value, false);
  }

  //for edit/duplicate update the values 
  updateLoadableStudyFormGroup(loadableStudyObj: LoadableStudy, isEdit: boolean) {
    if (isEdit) {
      this.savedloadableDetails = {
        draftMark: loadableStudyObj.draftMark,
        loadLineXId: loadableStudyObj.loadLineXId,
        draftRestriction: loadableStudyObj.draftRestriction ? loadableStudyObj.draftRestriction : ''
      }

      this.newLoadableStudyFormGroup.patchValue({
        duplicateExisting: loadableStudyObj.createdFromId ? loadableStudyObj : null
      })
      this.newLoadableStudyFormGroup.controls['duplicateExisting'].disable();
    } else {
      this.newLoadableStudyFormGroup.patchValue({
        duplicateExisting: loadableStudyObj
      })
    }
    this.newLoadableStudyFormGroup.patchValue({
      newLoadableStudyName: loadableStudyObj.name,
      enquiryDetails: loadableStudyObj.detail ? loadableStudyObj.detail : '',
      charterer: loadableStudyObj.charterer,
      subCharterer: loadableStudyObj?.subCharterer,
      loadLine: loadableStudyObj,
      draftMark: loadableStudyObj,
      draftRestriction: loadableStudyObj.draftRestriction ? loadableStudyObj.draftRestriction : '',
      maxAirTempExpected: loadableStudyObj.maxAirTemperature !== undefined ? loadableStudyObj.maxAirTemperature : '',
      maxWaterTempExpected: loadableStudyObj.maxWaterTemperature !== undefined ? loadableStudyObj.maxWaterTemperature : ''
    });
    const result = this.loadlineLists.filter(loadline => loadline.id === loadableStudyObj.loadLineXId);
    if (result.length > 0) {
      this.loadlineList = result[0];
      const selectedDraftMark = loadableStudyObj.draftMark;
      this.newLoadableStudyFormGroup.patchValue({
        loadLine: this.loadlineList,
        draftMark: { id: selectedDraftMark, name: selectedDraftMark }
      });
    }
    const loadLine = this.newLoadableStudyFormGroup.get('loadLine').value;
    this.draftMarkList = loadLine.draftMarks?.map(draftMarks => ({ id: draftMarks, name: draftMarks.toFixed(2) }));
    loadableStudyObj.loadableStudyAttachment ? this.uploadedFiles = [...loadableStudyObj.loadableStudyAttachment] : this.uploadedFiles = [];
  }

  /**
   * check whether new draftMark , loadLineXId , draftRestriction
   * has been selected
   * @memberof NewLoadableStudyPopupComponent
  */
  isLoadlineChanged() {
    const savedloadableDetails = this.savedloadableDetails;
    if (savedloadableDetails && (this.savedloadableDetails.draftMark !== this.newLoadableStudyFormGroup.controls['draftMark'].value?.id ||
      this.savedloadableDetails.loadLineXId !== this.newLoadableStudyFormGroup.controls['loadLine'].value?.id ||
      (this.savedloadableDetails.draftRestriction !== this.newLoadableStudyFormGroup.controls['draftRestriction']?.value))) {
      return true;
    }
  }

  /**
   *
   * @param type 
   * Get form control value to label
   */
  getControlLabel(type: string) {
    return this.newLoadableStudyFormGroup.controls[type].value;
  }

}
