import { Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { FileUpload } from 'primeng/fileupload';
import { IDropdownEvent, INewLoadableStudy } from "./new-loadable-study-popup.model";
import { IdraftMarks, ILoadLineList, INewLoadableStudyListNames, Voyage } from "../../models/common.models";
import { VesselDetailsModel } from '../../../model/vessel-details.model';
import { LoadableStudyListApiService } from '../../../cargo-planning/services/loadable-study-list-api.service';
import { LoadableStudy } from '../../../cargo-planning/models/loadable-study-list.model';
import { Router } from '@angular/router';

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
  get vesselInfoList(): VesselDetailsModel[] { return this._vesselInfoList; }
  set vesselInfoList(vesselInfoList: VesselDetailsModel[]) {
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

  private _vesselInfoList: VesselDetailsModel[] = [];
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
  constructor(private loadableStudyListApiService: LoadableStudyListApiService, private formBuilder: FormBuilder, private router: Router) { }

  ngOnInit(): void {
    this.getVesselInfo();
  }

  //get loadlines data and create form group
  async getVesselInfo() {
    this.loadlineLists = this.vesselInfoList[0].loadlines;
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
          draftMark :{ id: draftMarkSummer, name: draftMarkSummer }
        });
        this.onloadLineChange();
      }
  }

  // creating form-group for new-loadable-study
  async createNewLoadableStudyFormGroup() {
    this.newLoadableStudyFormGroup = this.formBuilder.group({
      duplicateExisting: '',
      newLoadableStudyName: ['', Validators.required],
      enquiryDetails: ['', Validators.maxLength(1000)],
      attachMail: null,
      charterer: this.vesselInfoList[0].charterer,
      subCharterer: '',
      loadLine: '',
      draftMark: '',
      draftRestriction: '',
      maxAirTempExpected: '',
      maxWaterTempExpected: ''
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
        const result = await this.loadableStudyListApiService.setLodableStudy(this.vesselInfoList[0].id, this.voyage.id, this.newLoadableStudyPopupModel).toPromise();
        if (result.responseStatus.status === "200") {
          this.closeDialog();
          this.router.navigate(['business/cargo-planning/loadable-study-details/' + this.vesselInfoList[0].id + '/' + this.voyage.id + '/' + result.loadableStudyId]);
        }
      }
    } else {
      this.newLoadableStudyFormGroup.markAllAsTouched();
    }
  }

  // this function triggers when choosing the files in fileUpload (primeng)
  selectFilesToUpload() {
    this.uploadedFiles = [];
    const uploadedFile = this.fileUploadVariable.nativeElement.files;
    const extensions = ["docx", "pdf", "txt", "jpg", "jpeg", "png"];
    if (uploadedFile.length <= 5) {
      for (let i = 0; i < uploadedFile.length; i++) {
        const fileExtension = uploadedFile[i].name.substr((uploadedFile[i].name.lastIndexOf('.') + 1));
        if (extensions.includes(fileExtension.toLowerCase())) {
          if (uploadedFile[i].size / 1024 / 1024 >= 1) {
            this.showError = true;
            this.uploadError = "NEW_LOADABLE_STUDY_LIST_POPUP_FILE_SIZE_ERROR";
            break;
          } else {
            this.showError = false;
            this.uploadedFiles.push(uploadedFile[i]);
          }
        } else {
          this.showError = true;
          this.uploadError = "NEW_LOADABLE_STUDY_LIST_POPUP_FILE_FORMAT_ERROR";
          break;
        }
      }
    } else {
      this.showError = true;
      this.uploadError = "NEW_LOADABLE_STUDY_LIST_POPUP_FILE_LIMIT_ERROR"
    }
  }

  // returns form-controls of newLoadableStudyFormGroup
  get form() { return this.newLoadableStudyFormGroup.controls; }

  // for closing active modal popup
  closeDialog() {
    this.newLoadableStudyFormGroup.reset();
    this.displayPopup.emit(false);
    this.getVesselInfo();
  }

  /**
   * Load draft mark List based on selected loadLine vaule
   */
  onloadLineChange() {
    const loadLine = this.newLoadableStudyFormGroup.get('loadLine').value
    this.draftMarkList = loadLine.draftMarks.map(draftMarks => ({ id: draftMarks, name: draftMarks }));
  }

  onDuplicateExisting(event: IDropdownEvent) {
    this.getLoadableStudyDetailsForDuplicating(event.value);
  }

}
