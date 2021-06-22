import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { IBerth } from '../models/loading-information.model';

/**
 * Component class for loading discharging berth component
 *
 * @export
 * @class LoadingDischargingBerthComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-loading-discharging-berth',
  templateUrl: './loading-discharging-berth.component.html',
  styleUrls: ['./loading-discharging-berth.component.scss']
})
export class LoadingDischargingBerthComponent implements OnInit {
  @Input() editMode = true;
  @Input() availableBerths: IBerth[];
  @Input() selectedBerths: IBerth[];
  @Output() berthChange: EventEmitter<IBerth[]> = new EventEmitter();
  berthDetailsForm: FormGroup;
  berthForm: FormGroup;
  berthFormArray: FormArray;
  selectedIndex: number;

  get berths(): FormArray {
    return this.berthForm.get("berth") as FormArray
  }

  constructor(
    private fb: FormBuilder
  ) { }

  ngOnInit(): void {
    this.berthForm = this.fb.group({
      berth: this.fb.array([])
    });
    this.berthDetailsForm = this.fb.group({
      id: 0,
      berthName: '',
      maxShipDepth: '',
      hoseConnections: '',
      seaDraftLimitation: '',
      airDraftLimitation: '',
      maxManifoldHeight: '',
      regulationAndRestriction: '',
      itemsToBeAgreedWith: '',
      loadingInfoId: '',
      maxShpChannel: '',
      loadingBerthId: '',
      maxLoa: '',
      maxDraft: '',
    });
    this.berthDetailsForm.disable();
    this.initFormArray();
  }

  /**
 * Return the form controlls of the berth details form
 */
  get berthDetailsFormComtrol() {
    return this.berthDetailsForm.controls;
  }

  /**
* initialise berth details form
*/
  initFormArray() {
    if (this.selectedBerths.length > 0) {
      this.selectedBerths.forEach((selectedBerth) => {
        this.addBerth(selectedBerth);
      })
      this.setBerthDetails(this.selectedBerths[0])
      this.selectedIndex = 0;
    }

  }

  /**
  * Metgod for adding new berth form
  *
  * @memberof LoadingDischargingBerthComponent
  */
  createBerth(berth: IBerth): FormGroup {
    return this.fb.group({
      name: [berth, [Validators.required]],
      edit: berth ? false : true
    });
  }

  /**
  * Change value
  *
  * @memberof LoadingDischargingBerthComponent
  */
  change(field) {
    if (this.berthDetailsForm.value[field]) {
      this.selectedBerths.map((berth) => {
        if (berth.id === this.berthDetailsForm.value.id) {
          berth[field] = this.berthDetailsForm.value[field];
        }
        return berth;
      })
      this.berthChange.emit(this.selectedBerths);
    }

  }
  clearFilter(data) {

  }

  /**
   * Add new berth
   *
   * @memberof LoadingDischargingBerthComponent
   */
  addBerth(berth: IBerth) {
    this.berthFormArray = this.berthForm.get('berth') as FormArray;
    this.berthFormArray.push(this.createBerth(berth));
  }

  /**
   * Berth change 
   * @param {Event}
   * @memberof LoadingDischargingBerthComponent
   */
  onBerthChange(event, index) {
    this.selectedBerths.push(event.value)
    this.setBerthDetails(event.value);
    //this.availableBerths = this.availableBerths.filter((berth) => berth.id !== event.value.id);
    this.berthFormArray.at(index).patchValue({
      edit: false
    })
    this.berthChange.emit(this.selectedBerths);
  }

  /**
   * choose berth 
   * @param berth
   * @memberof LoadingDischargingBerthComponent
   */
  selectBerth(berth, index) {
    this.selectedIndex = index;
    this.setBerthDetails(berth.value.name);
  }

  /**
   * Assign values for berth display
   * @param berthInfo
   * @memberof LoadingDischargingBerthComponent
   */
  setBerthDetails(berthInfo: IBerth) {
    this.berthDetailsForm.enable();
    this.berthDetailsForm = this.fb.group({
      id: berthInfo.id,
      portId: berthInfo.portId,
      berthName: berthInfo.berthName,
      maxShipDepth: berthInfo.maxShipDepth,
      hoseConnections: berthInfo.hoseConnections,
      seaDraftLimitation: berthInfo.seaDraftLimitation,
      airDraftLimitation: berthInfo.airDraftLimitation,
      maxManifoldHeight: berthInfo.maxManifoldHeight,
      regulationAndRestriction: berthInfo.regulationAndRestriction,
      itemsToBeAgreedWith: berthInfo.itemsToBeAgreedWith,
      loadingInfoId: berthInfo.loadingInfoId,
      maxShpChannel: berthInfo.maxShpChannel,
      loadingBerthId: berthInfo.loadingBerthId,
      maxLoa: berthInfo.maxLoa,
      maxDraft: berthInfo.maxDraft
    });
  }

  /**
   * edit berth info
   * @param berth
   * @memberof LoadingDischargingBerthComponent
   */
  editBerth(index) {
    this.berthFormArray.at(index).patchValue({
      edit: true
    })
  }

  /**
   * Remove berth info
   * @param berth
   * @memberof LoadingDischargingBerthComponent
   */
  deleteBerth(event, index: number) {
    this.berths.removeAt(index);
    this.selectedBerths = this.selectedBerths.filter((berth) => berth.id !== event.value.name.id);
    if (this.selectedBerths.length > 0) {
      this.setBerthDetails(this.selectedBerths[0])
    } else {
      this.berthDetailsForm.reset();
      this.berthDetailsForm.disable();
    }
  }

}
