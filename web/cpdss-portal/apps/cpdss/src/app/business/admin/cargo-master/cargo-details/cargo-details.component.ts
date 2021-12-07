import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormArray, FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AppConfigurationService } from '../../../../shared/services/app-configuration/app-configuration.service';
import { PermissionsService } from '../../../../shared/services/permissions/permissions.service';
import { IDataTableColumn } from '../../../../shared/components/datatable/datatable.model';
import { IAPITempPopupData, ICargoDetails, ICargoLoadingInformation } from '../../models/cargo.model';
import { CargoMasterApiService } from '../../services/cargo-master-api.service';
import { IPermission } from '../../../../shared/models/user-profile.model';
import { TimeZoneTransformationService } from '../../../../shared/services/time-zone-conversion/time-zone-transformation.service';
import { ICountry, IPermissionContext, PERMISSION_ACTION } from '../../../../shared/models/common.model';
import { IPort } from '../../../core/models/common.model';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

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

  private ngUnsubscribe: Subject<any> = new Subject();


  constructor(private activatedRoute: ActivatedRoute,
    private permissionsService: PermissionsService,
    private cargoMasterApiService: CargoMasterApiService,
    private timeZoneTransformationService: TimeZoneTransformationService,
    private router: Router,
    private fb: FormBuilder) { }

  async ngOnInit(): Promise<void> {
    this.activatedRoute.paramMap.pipe(takeUntil(this.ngUnsubscribe)).subscribe(params => {
      this.cargoId = Number(params.get('cargoId'));
      this.saveBtnPermissionContext = { key: AppConfigurationService.settings.permissionMapping['CargoMasterComponent'], actions: this.cargoId ? [PERMISSION_ACTION.ADD, PERMISSION_ACTION.EDIT]: [PERMISSION_ACTION.ADD] };
    });
    this.permission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['CargoMasterComponent']);
    this.countries = await this.timeZoneTransformationService.getCountries().toPromise();
    this.ports = await this.cargoMasterApiService.getPorts().toPromise();
    const result = await this.cargoMasterApiService.getCargo(this.cargoId).toPromise();
    this.cargo = this.cargoId ? result?.cargo : null;
    this.initForm(this.cargo);
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
      type: this.fb.control(cargo?.type),
      abbreviation: this.fb.control(cargo?.abbreviation),
      assayDate: this.fb.control(cargo?.assayDate),
      api: this.fb.control(cargo?.api),
      reidVapourPressure: this.fb.control(cargo?.reidVapourPressure),
      gas: this.fb.control(cargo?.gas),
      totalWax: this.fb.control(cargo?.totalWax),
      pourPoint: this.fb.control(cargo?.pourPoint),
      cloudPoint: this.fb.control(cargo?.cloudPoint),
      viscosity: this.fb.control(cargo?.viscosity),
      temp: this.fb.control(cargo?.temp),
      cowCodes: this.fb.control(cargo?.cowCodes),
      hydrogenSulfideOil: this.fb.control(cargo?.hydrogenSulfideOil),
      hydrogenSulfideVapour: this.fb.control(cargo?.hydrogenSulfideVapour),
      benzene: this.fb.control(cargo?.benzene),
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
    return this.fb.group({
      country: this.fb.control(info.country),
      port: this.fb.control(info.port)
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
   * Hander for opening api and temerature history popup
   *
   * @memberof CargoDetailsComponent
   */
  openAPITemperaturePopup() {
    this.apiTempPopupData = {
      portData: [
        {
          "id": 359,
          "name": "5 WEST",
          "quantity": 1000
        }
      ],
      cargoId: 1,
      cargoName: 'Cargo 1'
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

}
