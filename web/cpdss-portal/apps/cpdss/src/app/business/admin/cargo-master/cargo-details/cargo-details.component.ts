import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AppConfigurationService } from '../../../../shared/services/app-configuration/app-configuration.service';
import { PermissionsService } from '../../../../shared/services/permissions/permissions.service';
import { IDataTableColumn } from '../../../../shared/components/datatable/datatable.model';
import { IAPITempPopupData, ICargoDetails, ICargoLoadingInformation } from '../../models/cargo.model';
import { CargoMasterApiService } from '../../services/cargo-master-api.service';
import { IPermission } from '../../../../shared/models/user-profile.model';
import { TimeZoneTransformationService } from '../../../../shared/services/time-zone-conversion/time-zone-transformation.service';
import { ICountry, IDateTimeFormatOptions, IPermissionContext, PERMISSION_ACTION } from '../../../../shared/models/common.model';
import { IPort } from '../../../core/models/common.model';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { NgxSpinnerService } from 'ngx-spinner';
import { IValidationErrorMessagesSet } from '../../../../shared/components/validation-error/validation-error.model';
import { TranslateService } from '@ngx-translate/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { specialCharacterValidator } from '../../../core/directives/special-character-validator.directive';
import { alphaNumericOnlyValidator } from '../../../core/directives/alpha-numeric-only-validator.directive';
import { numberValidator } from '../../../core/directives/number-validator.directive';
import { CargoMasterTransformationService } from '../../services/cargo-master-transformation.service';
import { uniqueValidator } from '../../../core/directives/unique-validator.directive';
import * as moment from 'moment';

/**
 * Coponent class for cargo details page
 *
 * @export
 * @class CargoDetailsComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-cargo-details',
  templateUrl: './cargo-details.component.html',
  styleUrls: ['./cargo-details.component.scss']
})
export class CargoDetailsComponent implements OnInit, OnDestroy {

  cargoDetailsForm: FormGroup;
  cargoId: number;
  columns: IDataTableColumn[];
  cargo: ICargoDetails;
  permission: IPermission;
  countries: ICountry[];
  ports: IPort[];
  apiTempPopupData: IAPITempPopupData;
  openAPITemperatureHistoryPopup = false;
  saveBtnPermissionContext: IPermissionContext;
  errorMessages: IValidationErrorMessagesSet;
  dateFormat: string;
  datePlaceHolder: string;

  private ngUnsubscribe: Subject<any> = new Subject();


  constructor(
    private ngxSpinnerService: NgxSpinnerService,
    private activatedRoute: ActivatedRoute,
    private permissionsService: PermissionsService,
    private cargoMasterApiService: CargoMasterApiService,
    private timeZoneTransformationService: TimeZoneTransformationService,
    private router: Router,
    private fb: FormBuilder,
    private translateService: TranslateService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService,
    private cargoMasterTransformationService: CargoMasterTransformationService) { }

  async ngOnInit(): Promise<void> {
    this.ngxSpinnerService.show();
    const date = new Date();
    const formatOptions: IDateTimeFormatOptions = { customFormat: AppConfigurationService.settings?.dateFormat.split(' ')[0] };
    const translationKeys = await this.translateService.get(['EXAMPLE']).toPromise();
    this.datePlaceHolder = translationKeys['EXAMPLE'] + this.timeZoneTransformationService.formatDateTime(date, formatOptions);
    this.dateFormat = this.timeZoneTransformationService.getMappedConfigurationDateFormat(AppConfigurationService.settings?.dateFormat)
    this.activatedRoute.paramMap.pipe(takeUntil(this.ngUnsubscribe)).subscribe(params => {
      this.cargoId = Number(params.get('cargoId'));
      this.saveBtnPermissionContext = { key: AppConfigurationService.settings.permissionMapping['CargoMasterComponent'], actions: this.cargoId ? [PERMISSION_ACTION.ADD, PERMISSION_ACTION.EDIT] : [PERMISSION_ACTION.ADD] };
    });
    this.permission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['CargoMasterComponent'], true);
    this.errorMessages = this.cargoMasterTransformationService?.setValidationErrorMessage();
    this.countries = await this.timeZoneTransformationService.getCountries().toPromise();
    this.ports = await this.cargoMasterApiService.getPorts().toPromise();
    if (this.cargoId) {
      const result = await this.cargoMasterApiService.getCargo(this.cargoId).toPromise();
      this.cargo = this.cargoId ? result?.cargo : null;
      this.cargo.assayDate = this.cargo?.assayDate ? this.timeZoneTransformationService.formatDateTime(this.cargo?.assayDate, formatOptions) : null;
    } else {
      this.cargo = <ICargoDetails>{ loadingInformation: [] };
    }
    this.initForm(this.cargo);
    this.ngxSpinnerService.hide();
  }

  ngOnDestroy() {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  /**
   * Initialize cargo details form
   *
   * @param {ICargoDetails} cargo
   * @memberof CargoDetailsComponent
   */
  initForm(cargo: ICargoDetails) {
    const loadingInformationForm = cargo?.loadingInformation ? cargo?.loadingInformation?.map(info => this.initLoadingInformationGroup(info)) : [];
    this.cargoDetailsForm = this.fb.group({
      name: this.fb.control(cargo?.name, [Validators.required, specialCharacterValidator, Validators.maxLength(100)]),
      abbreviation: this.fb.control(cargo?.abbreviation, [Validators.required, alphaNumericOnlyValidator, Validators.maxLength(10)]),
      assayDate: this.fb.control(cargo?.assayDate),
      api: this.fb.control(cargo?.api, [Validators.required, numberValidator(2)]),
      reidVapourPressure: this.fb.control(cargo?.reidVapourPressure, [numberValidator(3)]),
      gas: this.fb.control(cargo?.gas, [numberValidator(1)]),
      totalWax: this.fb.control(cargo?.totalWax, [numberValidator(0)]),
      pourPoint: this.fb.control(cargo?.pourPoint, [numberValidator(1)]),
      cloudPoint: this.fb.control(cargo?.cloudPoint, [numberValidator(1)]),
      viscosity: this.fb.control(cargo?.viscosity, [numberValidator(1)]),
      temp: this.fb.control(cargo?.temp, [numberValidator(1)]),
      cowCodes: this.fb.control(cargo?.cowCodes, [numberValidator(0)]),
      hydrogenSulfideOil: this.fb.control(cargo?.hydrogenSulfideOil, [numberValidator(0)]),
      hydrogenSulfideVapour: this.fb.control(cargo?.hydrogenSulfideVapour, [numberValidator(0)]),
      benzene: this.fb.control(cargo?.benzene, [numberValidator(0)]),
      specialInstrictionsRemark: this.fb.control(cargo?.specialInstrictionsRemark),
      loadingInformation: this.fb.array(loadingInformationForm)
    });
  }

  /**
   * Initilalize loading iformation formgroup
   *
   * @param {ICargoLoadingInformation} info
   * @return {*}
   * @memberof CargoDetailsComponent
   */
  initLoadingInformationGroup(info: ICargoLoadingInformation) {
    info.ports = this.ports?.filter(_port => _port?.countryId === info?.country?.id);
    const port = this.ports?.find(_port => _port.id === info.port?.id);
    const country = this.countries?.find(_country => _country.id === info.country?.id);
    return this.fb.group({
      country: this.fb.control(country),
      port: this.fb.control(port, [uniqueValidator])
    })
  }

  /**
   * Handler for add new cargo loading information
   *
   * @memberof CargoDetailsComponent
   */
  addLoadingInfo() {
    const info: ICargoLoadingInformation = { id: 0, country: null, port: null };
    this.cargo.loadingInformation.push(info);
    (<FormArray>this.cargoDetailsForm.get('loadingInformation')).push(this.initLoadingInformationGroup(info));
  }

  /**
   * Handler for opening api and temperature history popup
   *
   * @memberof CargoDetailsComponent
   */
  openAPITemperaturePopup(rowData: ICargoLoadingInformation) {
    this.apiTempPopupData = {
      portData: [
        rowData?.port
      ],
      cargoId: this.cargoId,
      cargoName: this.cargo?.name
    };
    this.openAPITemperatureHistoryPopup = true;
  }

  /**
   * Cancel edit/add of cargo
   *
   * @memberof CargoDetailsComponent
   */
  cancel() {
    this.router.navigate(['../'], { relativeTo: this.activatedRoute });
  }

  /**
   * Handler for save button click
   *
   * @memberof CargoDetailsComponent
   */
  async save() {
    if (this.cargoDetailsForm.valid) {
      this.ngxSpinnerService.show();
      const translationKeys = this.translateService.instant(['CARGO_SAVED_SUCCESSFULLY', 'CARGO_SAVE_SUCCESS', 'CARGO_SAVE_ERROR', 'CARGO_NAME_ALREADY_EXIST', 'CARGO_ABBREVIATION_ALREADY_EXIST']);
      const cargoDetails: ICargoDetails = this.cargoDetailsForm.value;
      cargoDetails.assayDate = cargoDetails.assayDate ? moment(cargoDetails.assayDate).format('DD-MM-YYYY') : null;
      try {
        const result = await this.cargoMasterApiService.saveCargo(cargoDetails, this.cargoId).toPromise();
        this.cargo = result?.cargo;
        this.cargoId = result?.id;
        this.ngxSpinnerService.hide();
        this.messageService.add({ severity: 'success', summary: translationKeys['CARGO_SAVE_SUCCESS'], detail: translationKeys['CARGO_SAVED_SUCCESSFULLY'] });
        this.cancel();
      } catch (error) {
        if (error?.error?.errorCode === 'ERR-RICO-328') {
          this.messageService.add({ severity: 'error', summary: translationKeys['CARGO_SAVE_ERROR'], detail: translationKeys['CARGO_NAME_ALREADY_EXIST'] });
        } else if (error?.error?.errorCode === 'ERR-RICO-329') {
          this.messageService.add({ severity: 'error', summary: translationKeys['CARGO_SAVE_ERROR'], detail: translationKeys['CARGO_ABBREVIATION_ALREADY_EXIST'] });
        }
        this.ngxSpinnerService.hide();
      }

    } else {
      this.cargoDetailsForm.markAllAsTouched();
    }
  }

  /**
   * Handler for deleting loading info
   *
   * @param {number} rowIndex
   * @memberof CargoDetailsComponent
   */
  deleteLoadingInformation(rowIndex: number) {
    const translationKeys = this.translateService.instant(['CARGO_LOADING_INFO_DELETE_SUMMARY', 'CARGO_LOADING_INFO_DELETE_CONFIRM_LABEL', 'CARGO_LOADING_INFO_DELETE_REJECT_LABEL', 'CARGO_LOADING_INFO_DELETE_DETAILS']);

    this.confirmationService.confirm({
      key: 'confirmation-alert',
      header: translationKeys['CARGO_LOADING_INFO_DELETE_SUMMARY'],
      message: translationKeys['CARGO_LOADING_INFO_DELETE_DETAILS'],
      icon: 'pi pi-exclamation-triangle',
      acceptLabel: translationKeys['CARGO_LOADING_INFO_DELETE_CONFIRM_LABEL'],
      acceptIcon: 'pi',
      acceptButtonStyleClass: 'btn btn-main mr-5',
      rejectVisible: true,
      rejectLabel: translationKeys['CARGO_LOADING_INFO_DELETE_REJECT_LABEL'],
      rejectIcon: 'pi',
      rejectButtonStyleClass: 'btn btn-main',
      accept: async () => {
        this.ngxSpinnerService.show();
        this.cargo?.loadingInformation?.splice(rowIndex, 1);
        const dataTableControl = <FormArray>this.cargoDetailsForm.get('loadingInformation');
        dataTableControl.removeAt(rowIndex);
        this.ngxSpinnerService.hide();
      }
    });
  }

  /**
  * Method to check for field errors
  *
  * @param {...string[]} formControlNames
  * @return {ValidationErrors}
  * @memberof CargoDetailsComponent
  */
  fieldError(...formControlNames: string[]): ValidationErrors {
    const formControl = this.field(...formControlNames);
    return formControl?.invalid && (formControl.dirty || formControl.touched) ? formControl.errors : null;
  }

  /**
   * Method to get form control name
   *
   * @param {...string[]} formControlNames
   * @return {*}  {FormControl}
   * @memberof CargoDetailsComponent
   */
  field(...formControlNames: string[]): FormControl {
    const formControl = <FormControl>this.cargoDetailsForm?.get([...formControlNames]);
    return formControl;
  }

  /**
   * Handler for country dropdowm change event
   *
   * @param {ICargoLoadingInformation} rowData
   * @param {number} rowIndex
   * @memberof CargoDetailsComponent
   */
  onLoadingInfoChange(rowData: ICargoLoadingInformation, rowIndex: number) {
    const loadingInfoControl = (<FormArray>this.cargoDetailsForm.get('loadingInformation')).at(rowIndex);
    const formControlPort = loadingInfoControl.get('port');
    const formControlCountry = loadingInfoControl.get('country');
    this.cargo.loadingInformation[rowIndex].country = formControlCountry?.value;
    this.cargo.loadingInformation[rowIndex].port = formControlPort?.value;

    if (formControlCountry?.value?.id) {
      this.cargo.loadingInformation[rowIndex].ports = this.ports?.filter(port => port?.countryId === formControlCountry?.value?.id);
    } else {
      this.cargo.loadingInformation[rowIndex].ports = [];
    }
  }
}
