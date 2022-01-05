import { Component, Input, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';

import { numberValidator } from '../../../core/directives/number-validator.directive';
import { AppConfigurationService } from '../../../../shared/services/app-configuration/app-configuration.service';
import { PortMasterTransformationService } from '../../services/port-master-transformation.service';

import { DATATABLE_EDITMODE, IDataTableColumn } from '../../../../shared/components/datatable/datatable.model';
import { IBerthInfo, IBerthValueObject } from '../../models/port.model';
import { IPermissionContext, PERMISSION_ACTION } from '../../../../shared/models/common.model';


/**
 * Component class for Add Berth Component
 *
 * @export
 * @class AddBerthComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-add-berth',
  templateUrl: './add-berth.component.html',
  styleUrls: ['./add-berth.component.scss'],
})
export class AddBerthComponent implements OnInit {

  @Input() portId: number;
  @Input()
  get existingBerthInfo() {
    return this._berthList;
  }
  set existingBerthInfo(value: IBerthInfo[]) {
    this._berthList = value;
    this.initForm(value);
  }

  addBerthBtnPermissionContext: IPermissionContext;
  columns: IDataTableColumn[];
  berthDetailsForm: FormGroup;
  berthList: IBerthValueObject[];
  editMode: DATATABLE_EDITMODE;

  private _berthList: IBerthInfo[];

  constructor(
    private fb: FormBuilder,
    private portMasterTransformationService: PortMasterTransformationService
  ) { }

  ngOnInit(): void {
    this.addBerthBtnPermissionContext = { key: AppConfigurationService.settings.permissionMapping['PortListingComponent'], actions: [PERMISSION_ACTION.VIEW, PERMISSION_ACTION.ADD] };
    this.columns = this.portMasterTransformationService.getBerthGridColumns();
    this.editMode = DATATABLE_EDITMODE.CELL;
  }

  /**
   * Method to initialize form
   *
   * @param {IBerthInfo[]} berthArray
   * @memberof AddBerthComponent
   */
  initForm(berthArray: IBerthInfo[]): void {
    const _berthList = berthArray?.map(berth => (this.portMasterTransformationService.getBerthListAsValueObject(berth, true, true, false, true, false)));
    const berthListArray = _berthList?.map(berth =>
      this.initBethsFormGroup(berth)
    );
    this.berthDetailsForm = this.fb.group({
      dataTable: this.fb.array([...berthListArray]),
    });
    this.berthList = _berthList;
    this.portMasterTransformationService.setBerthFormDetails(this.berthDetailsForm);
  }

  /**
   * Method to initialise berths formgroup
   *
   * @param {*} berths
   * @param {*} index
   * @return {*}
   * @memberof AddBerthComponent
   */
  initBethsFormGroup(berths: IBerthValueObject) {
    return this.fb.group({
      berthId: this.fb.control(berths?.berthId),
      portId: this.fb.control(berths?.portId),
      berthName: this.fb.control(berths?.berthName?.value, [Validators.maxLength(100)]),
      maxDraft: this.fb.control(berths?.maxDraft?.value, [Validators.required, numberValidator(2, 2, false)]),
      depthInDatum: this.fb.control(berths?.depthInDatum?.value, [numberValidator(2, 2, false)]),
      maxLoa: this.fb.control(berths?.maxLoa?.value, [Validators.required, numberValidator(2, 4, false)]),
      maxDwt: this.fb.control(berths?.maxDwt?.value, [Validators.required, numberValidator(2, 7, false)]),
      maxShipDepth: this.fb.control(berths?.maxShipDepth?.value, [Validators.required, numberValidator(2, 2, false)]),
      maxManifoldHeight: this.fb.control(berths?.maxManifoldHeight?.value, [Validators.required, numberValidator(2, 2, false)]),
      minUKC: this.fb.control(berths?.minUKC?.value, [Validators.required]),
      regulationAndRestriction: this.fb.control(berths?.regulationAndRestriction?.value, [Validators.maxLength(100)]),
    });
  }

  /**
   * Method to add new berth.
   *
   * @memberof AddBerthComponent
   */
  async addBerth() {
    const newBerth = <IBerthInfo>{
      berthId: 0,
      portId: this.portId ? this.portId : 0,
      berthName: null,
      depthInDatum: null,
      maxLoa: null,
      maxDwt: null,
      maxShipDepth: null,
      maxManifoldHeight: null,
      minUKC: null,
      regulationAndRestriction: null,
    };
    const dataTableFormArray = <FormArray>this.berthDetailsForm.get('dataTable');
    const newBerthAsValueObject = this.portMasterTransformationService.getBerthListAsValueObject(newBerth);
    dataTableFormArray.push(this.initBethsFormGroup(newBerthAsValueObject));
    this.berthList = [...this.berthList, newBerthAsValueObject];
  }

  /**
   * Method to call on edit row
   *
   * @param {*} event
   * @memberof AddBerthComponent
   */
  onEditComplete(event): void {
    this.portMasterTransformationService.setBerthFormDetails(this.berthDetailsForm);
  }

  /**
   * Method to call on delete row.
   *
   * @param {*} event
   * @memberof AddBerthComponent
   */
  onDeleteRow(event): void {
    if (event?.data?.isDelete) {
      // TODO: implement delete API to remove added berth datails
      this.berthList.splice(event.index, 1);
      this.berthList = [...this.berthList];
      const dataTableFormArray = <FormArray>this.berthDetailsForm.get('dataTable');
      dataTableFormArray.removeAt(event.index);
    }
  }
}
