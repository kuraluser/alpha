import { ICrewsDetailsResponse, ICrewVesselMapping, IPostCrewDetails } from './../../models/crew.model';
import { Component, OnInit, Output, EventEmitter, Input, OnDestroy } from '@angular/core';
import { FormControl, FormBuilder, FormGroup, Validators, ValidationErrors } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { MessageService } from 'primeng/api';
import { NgxSpinnerService } from 'ngx-spinner';
import { CREW_POPUP_SELECTIONMODE, ICrewRank, ICrewrankListResponse } from '../../models/crew.model';
import { CrewMasterApiService } from '../../services/crew-master-api.service';
import { CrewMasterTransformationService } from './../../services/crew-master-transformation.service';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { IVessel } from '../../../core/models/vessel-details.model';
import { numberSpecialCharacterValidator } from '../../../core/directives/number-special-character-validator.directive';
import { environment } from 'apps/cpdss/src/environments/environment';

/**
 * Component class of add crew
 *
 * @export
 * @class AddCrewComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-add-crew',
  templateUrl: './add-crew.component.html',
  styleUrls: ['./add-crew.component.scss']
})
export class AddCrewComponent implements OnInit, OnDestroy {

  @Input() popupStatus: CREW_POPUP_SELECTIONMODE;
  @Input() vesselList: IVessel[];
  @Output() displayPopUp = new EventEmitter<boolean>();
  @Output() crewSaved = new EventEmitter();

  public visible: boolean = true;
  public addCrewForm: FormGroup;
  public errorMessages: any;
  public isExisting: boolean;
  public ranks: ICrewRank[];
  private onDestroy$: Subject<void> = new Subject<void>();
  private isVesselDisable: boolean = false;

  constructor(private fb: FormBuilder,
    private translateService: TranslateService,
    private messageService: MessageService,
    private crewTransformationService: CrewMasterTransformationService,
    private ngxSpinnerService: NgxSpinnerService,
    private crewMasterApiService: CrewMasterApiService) { }

  ngOnInit(): void {
    this.errorMessages = this.crewTransformationService.setValidationErrorMessage();
    this.getRanks();
    this.addCrewForm = this.fb.group({
      'crewName': ['', [Validators.required, Validators.maxLength(30), numberSpecialCharacterValidator]],
      'crewRank': ['', [Validators.required]],
      'vesselInformation': [[], [Validators.required]]
    });
    this.vesselChange();
  }

  /**
   * To save crew details
   * @returns
   */
  async saveNewCrew() {
    if (this.addCrewForm.invalid) {
      this.addCrewForm.markAllAsTouched();
      this.isExisting = false;
      return;
    }
    this.ngxSpinnerService.show();
    const translationKeys = await this.translateService.get(['NEW_CREW_CREATE_SUCCESS', 'NEW_CREW_CREATED_SUCCESSFULLY',
      'CREW_CREATE_ERROR', 'CREW_NAME_ALREADY_EXIST', 'SOMETHING_WENT_WRONG_ERROR', 'SOMETHING_WENT_WRONG']).toPromise();
    let postData: IPostCrewDetails = {
      id: 0,
      crewName: this.addCrewForm.value.crewName,
      crewRank: this.addCrewForm.value.crewRank.rankName,
      crewRankId: this.addCrewForm.value.crewRank.id,
      vesselInformation: []
    };
    let vesselInformationArr:ICrewVesselMapping[] = [];
    this.addCrewForm.value.vesselInformation.forEach(element => {
      let mapping: ICrewVesselMapping ={
        id: 0,
        vessel: element
      }
      vesselInformationArr.push(mapping);
    });
    postData["vesselInformation"] = vesselInformationArr;
    this.crewMasterApiService.saveCrew(postData, 0).pipe(takeUntil(this.onDestroy$)).subscribe((response: ICrewsDetailsResponse) => {
        if (response.responseStatus.status === '200') {
          this.messageService.add({ severity: 'success', summary: translationKeys['NEW_CREW_CREATE_SUCCESS'], detail: translationKeys['NEW_CREW_CREATED_SUCCESSFULLY'] });
          this.crewSaved.emit(postData);
          this.displayPopUp.emit(false);
        }
        this.ngxSpinnerService.hide();
    }, error =>{
      if (error.error.errorCode === 'ERR-RICO-356') {
        this.isExisting = true;
        this.messageService.add({ severity: 'error', summary: translationKeys['CREW_CREATE_ERROR'], detail: translationKeys['CREW_NAME_ALREADY_EXIST'] });
      }
      this.ngxSpinnerService.hide();
    });
  }

  /**
   * To cancel adding crew
   */
  cancel(): void {
    this.displayPopUp.emit(false);
  }

  /**
   * Trim blank space
   * @param {string} formControlName
   * @memberof AddUserComponent
   */
  trimFormControl(formControlName: string) {
    this.addCrewForm.controls[formControlName].setValue((this.addCrewForm.get(formControlName).value).trim());
  }

  /**
* Get form control of new addCrewForm
* @param {string} formControlName
* @returns {FormControl}
* @memberof AddUserComponent
*/
  formContolValue(formControlName: string, propString?: string): FormControl {
    const formControl = <FormControl>this.addCrewForm.get(formControlName);
    return propString && formControl.value ? formControl.value[propString] : formControl.value;
  }

  /**
    * Get form control of newVoyageForm
    * @param {string} formControlName
    * @returns {FormControl}
    * @memberof AddUserComponent
    */
  field(formControlName: string): FormControl {
    const formControl = <FormControl>this.addCrewForm.get(formControlName);
    return formControl;
  }

  /**
     * Get field errors
     * @param {string} formControlName
     * @returns {ValidationErrors}
     * @memberof AddCrewComponent
    */
  fieldError(formControlName: string): ValidationErrors {
    const formControl = this.field(formControlName);
    return formControl?.invalid && (formControl.dirty || formControl.touched) ? formControl.errors : null;
  }

  /**
   * to get All Ranks
   */
  async getRanks() {
    this.ngxSpinnerService.show();
    const translationKeys = await this.translateService.get(['SOMETHING_WENT_WRONG_ERROR', 'SOMETHING_WENT_WRONG']).toPromise();
    this.crewMasterApiService.getRanks().pipe(takeUntil(this.onDestroy$)).subscribe((response: ICrewrankListResponse) => {
      try {
        if (response.responseStatus.status === '200') {
          this.ranks = response.crewRankList;
        }
      } catch (error) {
        this.messageService.add({ severity: 'error', summary: translationKeys['SOMETHING_WENT_WRONG_ERROR'], detail: translationKeys['SOMETHING_WENT_WRONG'] });
      }
      this.ngxSpinnerService.hide();
    })
  }

  /**
   * To destroy the component
   */
  ngOnDestroy(): void {
    this.onDestroy$.next();
    this.onDestroy$.complete();
    this.onDestroy$.unsubscribe();
  }

// ship there will be one vessel, no need of drop down: DSS-5970
  async vesselChange() {
    if(environment.name === 'ship') {
      this.isVesselDisable = true;
      this.addCrewForm.get("vesselInformation").setValue(this.vesselList ?this.vesselList:[]);
    }
  }

}
