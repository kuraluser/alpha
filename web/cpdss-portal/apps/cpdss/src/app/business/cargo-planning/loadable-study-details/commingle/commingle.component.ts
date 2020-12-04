import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgxSpinnerService } from 'ngx-spinner';
import { ICargo, ICargoNomination } from '../../models/cargo-planning.model';
import { ICommingleCargo, ICommingleResponseModel, IPurpose } from '../../models/commingle.model';
import { CommingleApiService } from '../../services/commingle-api.service';


/**
 * Component class of commingle pop up
 *
 * @export
 * @class CommingleComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-commingle',
  templateUrl: './commingle.component.html',
  styleUrls: ['./commingle.component.scss']
})
export class CommingleComponent implements OnInit {

  @Input() voyageId: number;
  @Input() loadableStudyId: number;
  @Input() vesselId: number;

  @Output() displayPopUp = new EventEmitter<boolean>();

  display = true;
  checked = false;
  preferredTank: any;
  isVolumeMaximum = true;
  selectedTank: any;
  percentage: any;
  cargos: ICargo;
  commingleData: ICommingleResponseModel;
  commingleForm: FormGroup;
  cargoNominationsCargo: ICargoNomination[];
  cargoNominationsCargo1: ICargoNomination[];
  cargoNominationsCargo2: ICargoNomination[];
  selectedCargo1: ICargoNomination;
  selectedCargo2: ICargoNomination;
  commingleCargo: ICommingleCargo;
  purposeOfCommingle: IPurpose[];

  constructor(private commingleApiService: CommingleApiService,
    private ngxSpinnerService: NgxSpinnerService,
    private fb: FormBuilder) { }


  /**
 * Component lifecycle ngOnit
 *
 * @returns {Promise<void>}
 * @memberof CommingleComponent
 */
  ngOnInit() {
    this.percentage = [{ id: 1, name: "10%" }, { id: 2, name: "20%" }, { id: 2, name: "30%" }];
    this.createVolumeMaximisationFormGroup();
    this.getCommingle();
  }

  /**
* Method for get commingle data
*
* @private
* @memberof CommingleComponent
*/
  async getCommingle() {
    this.ngxSpinnerService.show();
    const cargoResult = await this.commingleApiService.getCargos().toPromise();
    if (cargoResult.responseStatus.status === '200') {
      this.cargos = cargoResult.cargos;
    }
    this.commingleData = await this.commingleApiService.getCommingle(this.vesselId, this.voyageId, this.loadableStudyId).toPromise();
    if (cargoResult.responseStatus.status === '200' && this.commingleData.responseStatus.status === '200') {
      this.purposeOfCommingle = this.commingleData.purposes;
      this.commingleCargo = this.commingleData.commingleCargo;
      this.commingleForm.patchValue({
        purpose: this.purposeOfCommingle[0]
      });
      if (this.commingleCargo) {
        this.selectedCargo1 = this.cargoNominationsCargo.find(cargo => cargo.id === this.commingleCargo.cargoGroups[0].cargo1Id);
        this.selectedCargo2 = this.cargoNominationsCargo.find(cargo => cargo.id === this.commingleCargo.cargoGroups[0].cargo2Id);
        this.commingleForm.patchValue({
          purpose: this.purposeOfCommingle.find(purpose => purpose.id === this.commingleCargo.purposeId),
          slopOnly: this.commingleCargo.slopOnly,
          cargo1: this.selectedCargo1,
          cargo2: this.selectedCargo2
        });
      }
      this.cargoNominationsCargo = this.commingleData.cargoNominations.map((item, i) => Object.assign({}, item, this.cargos[i]));
      this.cargoNominationsCargo1 = this.cargoNominationsCargo;
      this.cargoNominationsCargo2 = this.cargoNominationsCargo;
      this.preferredTank = this.commingleData.vesselCargoTanks;
    }
    this.ngxSpinnerService.hide();
  }

  /**
  * Method for creating form-group for volume maximisation
  *
  * @returns
  * @memberof CommingleComponent
  */
  async createVolumeMaximisationFormGroup() {
    this.commingleForm = this.fb.group({
      purpose: this.fb.control(null, Validators.required),
      slopOnly: this.fb.control(false, [Validators.required]),
      cargo1: this.fb.control(null, [Validators.required]),
      cargo2: this.fb.control(null, [Validators.required]),
    });
  }

  /**
   * Close commingle popup
   */
  cancel() {
    this.displayPopUp.emit(false);
  }

  /**
   * select purpose of commingle
   */
  selectPurpose() {
    this.isVolumeMaximum = !this.isVolumeMaximum;
  }

  /**
   * Triggering add new cargo
   */
  addNew() {

  }

  /**
   * Triggering cargo option change
   */
  onChange(event, cargo) {
    if (cargo === 1) {
      this.cargoNominationsCargo2 = this.cargoNominationsCargo.filter(cargos => cargos.id !== event.value.id);
      this.selectedCargo1 = event.value;
    } else {
      this.cargoNominationsCargo1 = this.cargoNominationsCargo.filter(cargos => cargos.id !== event.value.id);
      this.selectedCargo2 = event.value;
    }
  }

  /**
 * Save volume maxmisation 
 */
  async saveVolumeMaximisation() {
    if (this.commingleForm.valid) {
      this.ngxSpinnerService.show();
      const data = {
        purposeId: this.commingleForm.value.purpose.id,
        slopOnly: this.commingleForm.value.slopOnly,
        cargoGroups: [{
          id: 0,
          cargo1Id: this.commingleForm.value.cargo1.id,
          cargo2Id: this.commingleForm.value.cargo2.id
        }]
      }
      const result = await this.commingleApiService.saveVolMaxCommingle(this.vesselId, this.voyageId, this.loadableStudyId, data).toPromise();
      if (result) {
        this.ngxSpinnerService.hide();
      }
    }
  }
}
