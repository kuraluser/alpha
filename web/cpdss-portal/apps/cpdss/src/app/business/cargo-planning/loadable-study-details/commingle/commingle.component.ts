import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
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
  cargos: ICargo[];
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
    private fb: FormBuilder,
    private messageService: MessageService,
    private translateService: TranslateService) { }


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
      this.cargoNominationsCargo = this.commingleData.cargoNominations.map(itm => ({
        ...this.cargos.find((item) => (item.id === itm.cargoId) && item),
        ...itm
      }));
      this.cargoNominationsCargo1 = this.cargoNominationsCargo;
      this.cargoNominationsCargo2 = this.cargoNominationsCargo;
      if (this.commingleCargo) {
        this.selectedCargo1 = this.cargoNominationsCargo.find(cargo => cargo.cargoId === this.commingleCargo.cargoGroups[0].cargo1Id);
        this.selectedCargo2 = this.cargoNominationsCargo.find(cargo => cargo.cargoId === this.commingleCargo.cargoGroups[0].cargo2Id);
        if (this.selectedCargo1) {
          this.cargoNominationsCargo2 = this.cargoNominationsCargo.filter(cargos => cargos.cargoId !== this.selectedCargo1.cargoId);
        }
        if (this.selectedCargo2) {
          this.cargoNominationsCargo1 = this.cargoNominationsCargo.filter(cargos => cargos.cargoId !== this.selectedCargo2.cargoId);
        }
        this.commingleForm.patchValue({
          purpose: this.purposeOfCommingle.find(purpose => purpose.id === this.commingleCargo.purposeId),
          slopOnly: this.commingleCargo.slopOnly,
          cargo1: this.selectedCargo1,
          cargo2: this.selectedCargo2
        });
      }
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
  close() {
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
      this.cargoNominationsCargo2 = this.cargoNominationsCargo.filter(cargos => cargos.cargoId !== event.value.cargoId);
      this.selectedCargo1 = event.value;
    } else {
      this.cargoNominationsCargo1 = this.cargoNominationsCargo.filter(cargos => cargos.cargoId !== event.value.cargoId);
      this.selectedCargo2 = event.value;
    }
  }

  /**
 * Save volume maxmisation 
 */
  async saveVolumeMaximisation() {
    if (this.commingleForm.valid) {
      this.ngxSpinnerService.show();
      const translationKeys = await this.translateService.get(['COMMINGLE_VOL_MAX_SAVE_SUCCESS', 'COMMINGLE_VOL_MAX_SAVE_SUCCESSFULLY']).toPromise();
      const data = {
        purposeId: this.commingleForm.value.purpose.id,
        slopOnly: this.commingleForm.value.slopOnly,
        cargoGroups: [{
          id: 0,
          cargo1Id: this.commingleForm.value.cargo1.cargoId,
          cargo2Id: this.commingleForm.value.cargo2.cargoId
        }]
      }
      const result = await this.commingleApiService.saveVolMaxCommingle(this.vesselId, this.voyageId, this.loadableStudyId, data).toPromise();
      if (result.responseStatus.status === '200') {
        this.messageService.add({ severity: 'success', summary: translationKeys['COMMINGLE_VOL_MAX_SAVE_SUCCESS'], detail: translationKeys['COMMINGLE_VOL_MAX_SAVE_SUCCESSFULLY'] });
        this.close();
      }
      this.ngxSpinnerService.hide();
    } else {
      this.commingleForm.markAllAsTouched();
    }
  }

   // returns form-controls of commingleForm
   get form() { return this.commingleForm.controls; }
}
