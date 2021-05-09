import { Component, EventEmitter, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { ITimeZone } from 'apps/cpdss/src/app/shared/models/common.model';
import { TimeZoneTransformationService } from 'apps/cpdss/src/app/shared/services/time-zone-conversion/time-zone-transformation.service';
import { PortMasterTransformationService } from '../../services/port-master-transformation.service';
import { numberValidator } from '../../../cargo-planning/directives/validator/number-validator.directive';
import { PortMasterApiService } from '../../services/port-master-api.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { TranslateService } from '@ngx-translate/core';
import { MessageService } from 'primeng/api';
import { ActivatedRoute, Router } from '@angular/router';


/**
 * Component Class for Add port
 *
 * @export
 * @class AddPortComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-add-port',
  templateUrl: './add-port.component.html',
  styleUrls: ['./add-port.component.scss']
})
export class AddPortComponent implements OnInit {

  timeZoneList: ITimeZone[];
  countryList: any;
  public addPortDetailsForm: FormGroup;
  public errorMessages: any;
  portId: any;
  selectedPortName: string;
  isSaveClicked = false;
  existingBerthInfo = null;
  isVisible = false;
  coordinates: string;
  selectedPortTimeZone: ITimeZone = { id: null, offsetValue: null, timezone: null }; //TODO - to be removed upon actual data


  constructor(private timeZoneTransformationService: TimeZoneTransformationService,
    private router: Router,
    private messageService: MessageService,
    private translateService: TranslateService,
    private fb: FormBuilder, private portMasterTransformationService: PortMasterTransformationService,
    private portMasterApiService: PortMasterApiService, private ngxSpinnerService: NgxSpinnerService,
    private activatedRoute: ActivatedRoute) { }

  /**
   * Component lifecyle ngoninit
   * @return {Promise<void>}
   * @memberof AddPortComponent
   */
  async ngOnInit(): Promise<void> {
    this.ngxSpinnerService.show();
    this.timeZoneList = await this.getTimeZoneList();
    this.countryList = await this.getCountryList();
    this.setPortId();
    if (this.portId) {
      this.setFormValues();
    }
    this.errorMessages = this.portMasterTransformationService.setValidationErrorMessage();
    this.createForm();
    this.ngxSpinnerService.hide();
  }


  /**
   * Method to create form
   *
   * @memberof AddPortComponent
   */
  createForm() {
    this.addPortDetailsForm = this.fb.group({
      'portName': ['', [Validators.required, Validators.maxLength(100)]],
      'portCode': ['', [Validators.required, Validators.maxLength(10)]],
      'portTimeZone': ['', Validators.required],
      'country': ['', Validators.required],
      'highTide': ['', [Validators.required, numberValidator(2, 2, false)]],
      'lowTide': ['', [Validators.required, numberValidator(2, 2, false)]],
      'permissibleShipsAtDraft': ['', [Validators.required, numberValidator(2, 2, false)]],
      'densityOfWater': ['', [Validators.required, numberValidator(2, 4, false)]],
      'ambientTemperature': ['', [Validators.required, numberValidator(2, 2, false)]],
      'position': ['', Validators.maxLength(8)]
    });
  }

  /**
   *Method to get TimeZoneList
   * @return {*} 
   * @memberof AddPortComponent
   */

  async getTimeZoneList() {
    return await this.timeZoneTransformationService.getTimeZoneList().toPromise();
  }


  /**
   *Method to get CountryList
   * @return {*} 
   * @memberof AddPortComponent
   */

  async getCountryList() {
    return this.portMasterApiService.getCountryList();
  }

  /**
   *Method to get formcontrolName
   * @param {string} formControlName
   * @return {FormControl}
   * @memberof AddPortComponent
   */

  field(formControlName: string): FormControl {
    const formControl = <FormControl>this.addPortDetailsForm.get(formControlName);
    return formControl;
  }

  /**
   * Method to check for field errors
   *
   * @param {string} formControlName
   * @return {ValidationErrors}
   * @memberof AddPortComponent
   */

  fieldError(formControlName: string): ValidationErrors {
    const formControl = this.field(formControlName);
    return formControl?.invalid && (formControl.dirty || formControl.touched) ? formControl.errors : null;
  }


  /**
   * Method to save port details
   *
   * @memberof AddPortComponent
   */
  async savePortDetails() {
    const berthDetailsForm = this.portMasterTransformationService.getBerthFormDetails();
    const translationKeys = await this.translateService.get(['PORT_ADDED_SUCCESSFULLY', 'PORT_MASTER_SUCCESS', 'PORT_MASTER_FAILURE', 'PORT_MASTER_ERROR']).toPromise();
    if (this.addPortDetailsForm.valid && berthDetailsForm.valid)   // TODO - has implement save api call later.
    {
      this.messageService.add({ severity: 'success', summary: translationKeys['PORT_MASTER_SUCCESS'], detail: translationKeys['PORT_ADDED_SUCCESSFULLY'] });
    }
    else {
      this.isSaveClicked = true;
      this.addPortDetailsForm.markAllAsTouched();
      this.messageService.add({ severity: 'error', summary: translationKeys['PORT_MASTER_ERROR'], detail: translationKeys['PORT_MASTER_FAILURE'] });
    }
  }

  /**
   * Method to cancel the added port details and navigate to portlisting page
   *
   * @memberof AddPortComponent
   */

  cancel() {
    this.router.navigate(['/business/admin/port-listing']);
  }

  /**
   *Method to get portId;
   *
   * @memberof AddPortComponent
   */
  setPortId() {
    this.portId = this.activatedRoute?.snapshot?.params?.portId;
  }


  /**
   * Method to set form values if it is a edit
   *
   * @memberof AddPortComponent
   */
  setFormValues() {
    this.selectedPortName = "RAS TANURA"; //Will be removed later when actual data is available
    this.selectedPortTimeZone = this.timeZoneList[0];  //ToDo ..this logic has to be implemented based on the port id later.
  }


  /**
   * Method to set coordinates to input box
   *
   * @param {*} coordinates
   * @memberof AddPortComponent
   */
  setCoordinates(coordinates: any) {
    coordinates?.lat?this.coordinates = coordinates.lat + " , " + coordinates.lon:this.coordinates = "";
    this.closeModal(true);
  }

  /**
   *Method to close the modal
   *
   * @param {*} event
   * @memberof AddPortComponent
   */

  closeModal(event) {
    this.isVisible = event;
  }
}
