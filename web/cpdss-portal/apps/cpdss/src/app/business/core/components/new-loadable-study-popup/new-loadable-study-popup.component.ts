import { Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { FileUpload } from 'primeng/fileupload';
import { IDropdownEvent, INewLoadableStudy } from "./new-loadable-study-popup.model";
import { IdraftMarks, ILoadLineList, INewLoadableStudyListNames, Voyage } from "../../models/common.models";
import { VesselDetailsModel } from '../../../model/vessel-details.model';
import { LoadableStudyListApiService } from '../../../cargo-planning/services/loadable-study-list-api.service';
import { LoadableStudy } from '../../../cargo-planning/models/loadable-study-list.model';
import { Router } from '@angular/router';
import { numberValidator } from '../../../cargo-planning/directives/validator/number-validator.directive'
import { MessageService } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';
import { NgxSpinnerService } from 'ngx-spinner';

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
  get vesselInfoList(): VesselDetailsModel { return this._vesselInfoList; }
  set vesselInfoList(vesselInfoList: VesselDetailsModel) {
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
    if (duplicateLoadableStudy)
      this.getLoadableStudyDetailsForDuplicating(duplicateLoadableStudy);
  }

  @Output() displayPopup = new EventEmitter();

  @ViewChild('fileUpload') fileUploadVariable: ElementRef;

  private _vesselInfoList: VesselDetailsModel;
  private _voyage: Voyage;
  private _loadableStudyList: LoadableStudy[];
  private _duplicateLoadableStudy: LoadableStudy;

  newLoadableStudyFormGroup: FormGroup;
  newLoadableStudyPopupModel: INewLoadableStudy;
  newLoadableStudyListNames: INewLoadableStudyListNames;
  loadlineList: ILoadLineList;
  draftMarkList: IdraftMarks[] = [];
  uploadedFiles: any[] = [];
  loadlineLists: ILoadLineList[];
  showError = false;
  uploadError = "";
  newLoadableStudyNameExist = false;
  constructor(private loadableStudyListApiService: LoadableStudyListApiService,
    private formBuilder: FormBuilder,
    private router: Router,
    private messageService: MessageService,
    private translateService: TranslateService,
    private ngxSpinnerService: NgxSpinnerService) { }

  ngOnInit(): void {
    this.getVesselInfo();
  }

  //get loadlines data and create form group
  async getVesselInfo() {
    this.uploadedFiles = [];
    this.loadlineLists = this.vesselInfoList?.loadlines;
    this.createNewLoadableStudyFormGroup();
    this.getLoadlineSummer();
  }

  //get summer loadline data
  getLoadlineSummer() {
    const result = this.loadlineLists.filter(e => e.name.toLowerCase() === 'summer');
    if (result.length > 0) {
      this.loadlineList = result[0];
      const draftMarkSummer = Math.max(...this.loadlineList.draftMarks.map(Number));
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
      newLoadableStudyName: this.formBuilder.control('', [Validators.required, Validators.maxLength(100), Validators.pattern(/^\S+/)]),
      enquiryDetails: this.formBuilder.control('', [Validators.maxLength(1000)]),
      attachMail: null,
      charterer: this.vesselInfoList?.charterer,
      subCharterer: this.formBuilder.control('', [Validators.maxLength(30)]),
      loadLine: '',
      draftMark: '',
      draftRestriction: this.formBuilder.control('', [Validators.min(-99.99), Validators.max(99.99)]),
      maxAirTempExpected: this.formBuilder.control('', [Validators.min(-999.99), Validators.max(999.99), numberValidator(2)]),
      maxWaterTempExpected: this.formBuilder.control('', [Validators.min(-999.99), Validators.max(999.99), numberValidator(2)])
    });
  }

  // for duplicating a loadable study populating from mock API as of now.
  // the actual data will come from parent component called "loadable-study-list" and have to bind form-group with actual data.
  public async getLoadableStudyDetailsForDuplicating(loadableStudyObj: LoadableStudy) {
    this.newLoadableStudyFormGroup.controls.duplicateExisting.setValue(loadableStudyObj);
    this.newLoadableStudyFormGroup.controls.newLoadableStudyName.setValue(loadableStudyObj.name);
    this.newLoadableStudyFormGroup.controls.enquiryDetails.setValue(loadableStudyObj.detail);
    this.newLoadableStudyFormGroup.controls.charterer.setValue(loadableStudyObj.charterer);
    this.newLoadableStudyFormGroup.controls.subCharterer.setValue(loadableStudyObj.subCharterer);
    const loadLine = this.loadlineLists.find(loadline => loadableStudyObj.loadLineXId === loadline.id);
    this.newLoadableStudyFormGroup.controls.loadLine.setValue(loadLine);
    this.onloadLineChange();
    const draftMark = this.draftMarkList.find(draftmark => draftmark.id === loadableStudyObj.draftMark);
    this.newLoadableStudyFormGroup.controls.draftMark.setValue(draftMark);
    this.newLoadableStudyFormGroup.controls.draftRestriction.setValue(loadableStudyObj.draftRestriction);
    this.newLoadableStudyFormGroup.controls.maxAirTempExpected.setValue(loadableStudyObj.maxAirTemperature);
    this.newLoadableStudyFormGroup.controls.maxWaterTempExpected.setValue(loadableStudyObj.maxWaterTemperature);
  }

  // post newLoadableStudyFormGroup for saving newly created loadable-study
  public async saveLoadableStudy() {
    if (this.newLoadableStudyFormGroup.valid) {
      this.newLoadableStudyNameExist = this.loadableStudyList.some(e => e.name.toLowerCase() === this.newLoadableStudyFormGroup.controls.newLoadableStudyName.value.toLowerCase().trim());
      const translationKeys = await this.translateService.get(['LOADABLE_STUDY_CREATE_SUCCESS', 'LOADABLE_STUDY_CREATED_SUCCESSFULLY', 'LOADABLE_STUDY_CREATE_ERROR', 'LOADABLE_STUDY_ALREADY_EXIST']).toPromise();
      if (!this.newLoadableStudyNameExist) {
        this.newLoadableStudyPopupModel = {
          id: 0,
          createdFromId: this.newLoadableStudyFormGroup.controls.duplicateExisting.value?.id,
          name: this.newLoadableStudyFormGroup.controls.newLoadableStudyName.value.trim(),
          detail: this.newLoadableStudyFormGroup.controls.enquiryDetails.value,
          attachMail: this.uploadedFiles,
          charterer: this.newLoadableStudyFormGroup.controls.charterer.value,
          subCharterer: this.newLoadableStudyFormGroup.controls.subCharterer.value,
          loadLineXId: this.newLoadableStudyFormGroup.controls.loadLine.value?.id,
          draftMark: this.newLoadableStudyFormGroup.controls.draftMark.value?.id,
          draftRestriction: this.newLoadableStudyFormGroup.controls.draftRestriction.value,
          maxAirTempExpected: this.newLoadableStudyFormGroup.controls.maxAirTempExpected.value,
          maxWaterTempExpected: this.newLoadableStudyFormGroup.controls.maxWaterTempExpected.value
        }
        this.ngxSpinnerService.show();
        try {
          const result = await this.loadableStudyListApiService.setLodableStudy(this.vesselInfoList?.id, this.voyage.id, this.newLoadableStudyPopupModel).toPromise();
          if (result.responseStatus.status === "200") {
            this.messageService.add({ severity: 'success', summary: translationKeys['LOADABLE_STUDY_CREATE_SUCCESS'], detail: translationKeys['LOADABLE_STUDY_CREATED_SUCCESSFULLY'] });
            this.closeDialog();
            this.router.navigate(['business/cargo-planning/loadable-study-details/' + this.vesselInfoList?.id + '/' + this.voyage.id + '/' + result.loadableStudyId]);
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
            this.showError = false;
            if(uploadFile.length < 5){
              uploadFile.push(uploadedFileVar[i]);
            }else{
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
  openFile(index) {
    const blob = new Blob([this.uploadedFiles[index]], { type: this.uploadedFiles[index].type })
    const fileurl = window.URL.createObjectURL(blob)
    window.open(fileurl)
  }

  //remove selected file
  removeFile(index) {
    this.uploadedFiles.splice(index, 1);
  }

  // returns form-controls of newLoadableStudyFormGroup
  get form() { return this.newLoadableStudyFormGroup.controls; }

  // for closing active modal popup
  closeDialog() {
    this.newLoadableStudyFormGroup.reset();
    this.displayPopup.emit(false);
    this.uploadError = "";
    this.getVesselInfo();
  }

  /**
   * Load draft mark List based on selected loadLine vaule
   */
  onloadLineChange() {
    const loadLine = this.newLoadableStudyFormGroup.get('loadLine').value;
    this.draftMarkList = loadLine.draftMarks.map(draftMarks => ({ id: draftMarks, name: draftMarks }));
    this.newLoadableStudyFormGroup.controls.draftMark.setValue(this.draftMarkList[0]);
  }

  //for set selcted loadable study value in loadable study form
  onDuplicateExisting(event: IDropdownEvent) {
    this.getLoadableStudyDetailsForDuplicating(event.value);
  }

}
