import { Component, OnInit, Output, EventEmitter, Input, ViewChild } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators, FormBuilder, ValidationErrors } from '@angular/forms';
import { FileRepositoryApiService } from './../services/file-repository-api.service';
import { FileRepositoryTransformationService } from './../services/file-repository-transformation.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';
import { IVessel } from '../../core/models/vessel-details.model';

/**
 * Component class for file repositiry add/edit
 *
 * @export
 * @class AddEditFileComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-add-edit-file',
  templateUrl: './add-edit-file.component.html',
  styleUrls: ['./add-edit-file.component.scss']
})
export class AddEditFileComponent implements OnInit {

  @Output() closePopup = new EventEmitter();
  @Input() editMode: boolean;
  @Input() voyages: any;
  @ViewChild('fileUpload') fileUpload;
  @Input() data;
  @Input() vessel: IVessel;
  @Output() updateData = new EventEmitter();

  display: boolean;
  sectionList: any;
  categoryList: any;
  errorMessages: any;
  allowedFiles = [
    'docx',
    'pdf',
    'txt',
    'csv',
    'xlsx'
  ];
  fileFormGroup: FormGroup;
  constructor(
    private fb: FormBuilder,
    private ngxSpinnerService: NgxSpinnerService,
    private messageService: MessageService,
    private translateService: TranslateService,
    private fileRepositoryApiService: FileRepositoryApiService,
    private fileRepositoryTransformationService: FileRepositoryTransformationService
  ) { }

  ngOnInit(): void {
    this.display = true;
    this.sectionList = this.fileRepositoryTransformationService.sectionList;
    this.categoryList = this.fileRepositoryTransformationService.category;
    this.errorMessages = this.fileRepositoryTransformationService.setValidationErrorMessage();
    this.setFormGroup(this.editMode ? this.data : null);
  }

  /**
   * Method for close popup
   * @memberof AddEditFileComponent
   */
  closeDialog() {
    this.closePopup.emit(true);
  }

  /**
   * Method for detecting file changes
   * @memberof AddEditFileComponent
   */
  async fileChange(event) {
    if (event.target?.files?.length) {
      const translationKeys = await this.translateService.get(['FILE_REPOSITORY_ERROR_LABEL', 'FILE_REPOSITORY_INVALID_FILE', 'FILE_REPOSITORY_FILE_SIZE_ERROR']).toPromise();
      if (this.allowedFiles.indexOf(event.target?.files[0].name.split('.')[1]) < 0) {
        this.messageService.add({ severity: 'error', summary: translationKeys['FILE_REPOSITORY_ERROR_LABEL'], detail: translationKeys['FILE_REPOSITORY_INVALID_FILE'] });
        this.fileUpload.nativeElement.value = '';
        return;
      }
      if (event.target?.files[0].size > 1000000) {
        this.fileUpload.nativeElement.value = '';
        this.messageService.add({ severity: 'error', summary: translationKeys['FILE_REPOSITORY_ERROR_LABEL'], detail: translationKeys['FILE_REPOSITORY_FILE_SIZE_ERROR'] });
        return;
      }
      this.fileFormGroup.controls.fileName.setValue(event.target?.files[0].name);
      this.fileFormGroup.controls.fileType.setValue(event.target?.files[0].name.split('.')[1]);
    }
  }

  /**
   * Method for setting form group
   * @memberof AddEditFileComponent
   */
  setFormGroup(data = null) {
    this.fileFormGroup = this.fb.group({
      id: this.fb.control(data?.id ? data?.id : ''),
      voyageNo: this.fb.control(data?.voyageNumber ? data?.voyageNumber : '', [Validators.required]),
      section: this.fb.control(data?.section ? data?.section : '', [Validators.required]),
      category: this.fb.control(data?.category ? data?.category : '', [Validators.required]),
      fileName: this.fb.control(data?.fileName ? data?.fileName : ''),
      fileType: this.fb.control(data?.fileType ? data?.fileType : ''),
    });
    if (!this.editMode) {
      this.fileFormGroup.controls.fileName.setValidators([Validators.required]);
      this.fileFormGroup.controls.fileName.updateValueAndValidity();
    }
  }

  /**
   * Method for upload file
   * @memberof AddEditFileComponent
   */
  async upload() {
    this.fileFormGroup.markAllAsTouched();
    this.fileFormGroup.markAsDirty();
    if (this.fileFormGroup.invalid) { return; }
    const translationKeys = await this.translateService.get(['FILE_REPOSITORY_SUCCESS_LABEL', 'FILE_REPOSITORY_UPLOAD_SUCCESS_LABEL']).toPromise();
    const formData: FormData = new FormData();
    formData.append('file', this.fileUpload.nativeElement.files[0]);
    formData.append('vesselId', this.vessel?.id?.toString());
    formData.append('voyageNo', this.fileFormGroup.controls.voyageNo.value?.voyageNo);
    formData.append('fileName', this.fileFormGroup.controls.fileName.value);
    formData.append('fileType', this.fileFormGroup.controls.fileType.value);
    formData.append('section', this.fileFormGroup.controls.section.value?.label);
    formData.append('category', this.fileFormGroup.controls.category.value?.label);
    this.ngxSpinnerService.show();
    const result = await this.fileRepositoryApiService.saveFile(formData).toPromise();
    this.ngxSpinnerService.hide();
    if(result.responseStatus.status === '200'){
      this.messageService.add({ severity: 'success', summary: translationKeys['FILE_REPOSITORY_SUCCESS_LABEL'], detail: translationKeys['FILE_REPOSITORY_UPLOAD_SUCCESS_LABEL'] });
      this.updateData.emit(true);
      this.closeDialog();
    }
  }

  /**
   * Method for update file
   * @memberof AddEditFileComponent
   */
  async updateFile() {
    this.fileFormGroup.markAllAsTouched();
    this.fileFormGroup.markAsDirty();
    if (this.fileFormGroup.invalid) { return; }
    const translationKeys = await this.translateService.get(['FILE_REPOSITORY_SUCCESS_LABEL', 'FILE_REPOSITORY_UPDATE_SUCCESS_LABEL']).toPromise();
    const formData: FormData = new FormData();
    if (this.fileUpload.nativeElement.files?.length) {
      formData.append('file', this.fileUpload.nativeElement.files[0]);
    }
    formData.append('vesselId', this.vessel?.id?.toString());
    formData.append('section', this.fileFormGroup.controls.section.value?.label);
    formData.append('category', this.fileFormGroup.controls.category.value?.label);
    formData.append('hasFileChanged', this.fileUpload.nativeElement.files?.length ? 'true' : 'false');
    this.ngxSpinnerService.show();
    const result = await this.fileRepositoryApiService.updateFile(this.fileFormGroup.controls.id.value, formData).toPromise();
    this.ngxSpinnerService.hide();
    if(result.responseStatus.status === '200'){
      this.messageService.add({ severity: 'success', summary: translationKeys['FILE_REPOSITORY_SUCCESS_LABEL'], detail: translationKeys['FILE_REPOSITORY_UPDATE_SUCCESS_LABEL'] });
      this.updateData.emit(true);
      this.closeDialog();
    }
  }

  /**
   * Get field errors
   * @param {string} formControlName
   * @returns {ValidationErrors}
   * @memberof AddEditFileComponent
   */
  fieldError(formControlName: string): ValidationErrors {
    const formControl = this.field(formControlName);
    const data = formControl?.invalid && (formControl.dirty || formControl.touched) ? formControl.errors : null;
    return formControl?.invalid && (formControl.dirty || formControl.touched) ? formControl.errors : null;
  }
  /**
  * Get form control of newVoyageForm
  * @param {string} formControlName
  * @returns {FormControl}
  * @memberof AddEditFileComponent
  */
  field(formControlName: string): FormControl {
    const formControl = <FormControl>this.fileFormGroup.get(formControlName);
    return formControl;
  }

}
