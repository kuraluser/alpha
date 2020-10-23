import { Component, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { FileUpload } from 'primeng/fileupload';
import { CommonApiService } from "../../../../shared/services/common/common-api.service";
import { ILoadLineLists, INewLoadableStudyPopupModel } from "./new-loadable-study-popup.model";
import { IdraftMarks, ILoadLineList, INewLoadableStudyListNames } from "../../models/common.models";
import { VesselsApiService } from '../../../services/vessels-api.service';
import { VesselDetailsModel } from '../../../model/vessel-details.model';

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
  @Input() duplicateExistingLoadableStudy: boolean;
  @Input() loadableStudyIdForDuplication;
  @Input() display;
  @Output() displayPopup = new EventEmitter();

  newLoadableStudyFormGroup: FormGroup;
  newLoadableStudyPopupModel: INewLoadableStudyPopupModel;
  newLoadableStudyListNames: INewLoadableStudyListNames;
  loadlineList: ILoadLineList;
  draftMarkList: IdraftMarks;
  uploadedFiles: any[] = [];
  vesselInfoList: VesselDetailsModel[];
  loadlineLists: ILoadLineLists[];

  postApiUri = 'vessels/{vessel-id}/voyages/{voyage-id}/loadable-studies/{loadable-study-id}';
  getApiUri = 'vessels/{vessel-id}/voyages/{voyage-id}/loadable-studies';

  constructor(private commonService: CommonApiService, private formBuilder: FormBuilder, private vesselServiceApi: VesselsApiService) { }

  ngOnInit(): void {
    this.vesselInfoList = this.vesselServiceApi.vesselDetails;
    this.createNewLoadableStudyFormGroup();
    this.getLoadableStudyListNames();
    this.getLoadLineList();
    this.loadlineLists = this.vesselInfoList[0].loadlines;
  }

  // creating form-group for new-loadable-study
  createNewLoadableStudyFormGroup() {
    this.newLoadableStudyFormGroup = this.formBuilder.group({
      duplicateExisting: '',
      newLoadableStudyName: ['', Validators.required],
      enquiryDetails: ['', Validators.maxLength(1000)],
      attachMail: this.uploadedFiles.toString(),
      charterer: '',
      subCharterer: '',
      loadLine: '',
      draftMark: '',
      draftRestriction: '',
      maxTempExpected: ''
    });

    if (this.duplicateExistingLoadableStudy) {
      this.getLoadableStudyDetailsForDuplicating();
    }
  }

  // for duplicating a loadable study populating from mock API as of now.
  // the actual data will come from parent component called "loadable-study-list" and have to bind form-group with actual data.
  public async getLoadableStudyDetailsForDuplicating() {
    const response = <any>await this.commonService.get(this.getApiUri, this.loadableStudyIdForDuplication).toPromise();
    this.newLoadableStudyFormGroup.controls.duplicateExisting.setValue(response.name);
    this.newLoadableStudyFormGroup.controls.newLoadableStudyName.setValue(response.name);
    this.newLoadableStudyFormGroup.controls.enquiryDetails.setValue(response.detail);
    this.newLoadableStudyFormGroup.controls.charterer.setValue(response.charterer);
    this.newLoadableStudyFormGroup.controls.subCharterer.setValue(response.subCharterer);
    this.newLoadableStudyFormGroup.controls.loadLine.setValue(response.loadLineXId);
    this.newLoadableStudyFormGroup.controls.draftMark.setValue(response.draftMark);
    this.newLoadableStudyFormGroup.controls.draftRestriction.setValue(response.draftRestriction);
    this.newLoadableStudyFormGroup.controls.maxTempExpected.setValue(response.maxTempExpected);
  }

  // post newLoadableStudyFormGroup for saving newly created loadable-study
  public async saveLoadableStudy() {
    if (this.newLoadableStudyFormGroup.valid) {
      this.newLoadableStudyPopupModel = {
        loadableStudyId: this.duplicateExistingLoadableStudy ? this.loadableStudyIdForDuplication : '0',
        duplicateExisting: this.newLoadableStudyFormGroup.controls.duplicateExisting.value,
        newLoadableStudyName: this.newLoadableStudyFormGroup.controls.newLoadableStudyName.value,
        enquiryDetails: this.newLoadableStudyFormGroup.controls.enquiryDetails.value,
        attachMail: this.uploadedFiles,
        charterer: this.newLoadableStudyFormGroup.controls.charterer.value,
        subCharterer: this.newLoadableStudyFormGroup.controls.subCharterer.value,
        loadLine: this.newLoadableStudyFormGroup.controls.loadLine.value,
        draftMark: this.newLoadableStudyFormGroup.controls.draftMark.value,
        draftRestriction: this.newLoadableStudyFormGroup.controls.draftRestriction.value,
        maxTempExpected: this.newLoadableStudyFormGroup.controls.maxTempExpected.value
      }
      const result = await this.commonService.post(this.postApiUri, this.newLoadableStudyPopupModel).toPromise();
    }
  }

  // this function triggers when choosing the files in fileUpload (primeng)
  selectFilesToUpload() {
    this.uploadedFiles = [];
    for (let i = 0; i < this.fileUpload.files.length; i++) {
      this.uploadedFiles.push(this.fileUpload.files[i]);
    }
  }

  // returns form-controls of newLoadableStudyFormGroup
  get form() { return this.newLoadableStudyFormGroup.controls; }

  // for closing active modal popup
  closeDialog() {
    this.displayPopup.emit(false);
  }

  //#region

  /**
   *  1. loadable-study-list-names should be passed from the parent component called "loadable-study-list"
   *  2. load-line-list and draft-mark are populated from cached response of vessel-info API
   *  when the above two conditions are satisfied, below API calls should be removed
   *  NB : items populating must have distict values
   */

  // for getting loadable-study-list-names.
  public async getLoadableStudyListNames() {
    const uri = 'companies/{domain-id}/loadable-study-list';
    const loadableStudyNames = <any>await this.commonService.get(uri).toPromise();
    this.newLoadableStudyListNames = loadableStudyNames.listNames;
  }

  // for getting load-line-list and draft-mark-list
  public async getLoadLineList() {
    const tempLoadlineArray = [];
    const tempDraftMarkArray = [];
    const uri = 'vessels/vessel-info';
    const vesselInfo = <any>await this.commonService.get(uri).toPromise();
    vesselInfo.vessels.forEach(vessels => {
      vessels.loadlines.forEach(loadlines => {
        tempLoadlineArray.push(loadlines);
        loadlines.draftMarks.forEach(draftMarks => {
          const obj = { name: draftMarks, value: draftMarks }
          tempDraftMarkArray.push(obj);
        });
      });
    });
    this.loadlineList = <any>tempLoadlineArray.filter((name, i, filterarray) => filterarray.findIndex(t => t.name === name.name) === i);
    //  this.draftMarkList = <any>tempDraftMarkArray.filter((name, i, filterarray) => filterarray.findIndex(t => t.name === name.name) === i);
  }

  //#endregion
  /**
   * Load draft mark List based on selected loadLine vaule
   */
  onloadLineChane(event: Event) {
    const loadLine = this.newLoadableStudyFormGroup.get('loadLine').value
    this.draftMarkList = loadLine.draftMarks.map(draftMarks => ({ id: draftMarks, name: draftMarks }));
  }

}
