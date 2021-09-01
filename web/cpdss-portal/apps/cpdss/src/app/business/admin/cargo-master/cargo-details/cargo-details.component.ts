import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AppConfigurationService } from '../../../../shared/services/app-configuration/app-configuration.service';
import { PermissionsService } from '../../../../shared/services/permissions/permissions.service';
import { IDataTableColumn } from '../../../../shared/components/datatable/datatable.model';
import { IAPITempPopupData, ICargoDetails, ICargoLoadingInformation } from '../../models/cargo.model';
import { CargoMasterApiService } from '../../services/cargo-master-api.service';
import { IPermission } from '../../../../shared/models/user-profile.model';
import { TimeZoneTransformationService } from '../../../../shared/services/time-zone-conversion/time-zone-transformation.service';
import { ICountry } from '../../../../shared/models/common.model';
import { IPort } from '../../../core/models/common.model';

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
export class CargoDetailsComponent implements OnInit {

  cargoDetailsForm: FormGroup;
  cargoId: number;
  columns: IDataTableColumn[];
  cargo: ICargoDetails;
  permission: IPermission;
  countries: ICountry[];
  ports: IPort[];
  apiTempPopupData: IAPITempPopupData;
  openAPITemperatureHistoryPopup = false;


  constructor(private activatedRoute: ActivatedRoute,
    private permissionsService: PermissionsService,
    private cargoMasterApiService: CargoMasterApiService,
    private timeZoneTransformationService: TimeZoneTransformationService,
    private router: Router,
    private fb: FormBuilder) { }

  async ngOnInit(): Promise<void> {
    this.activatedRoute.paramMap.subscribe(params => {
      this.cargoId = Number(params.get('cargoId'));
    });

    this.permission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['CargoMasterComponent']);
    this.countries = await this.timeZoneTransformationService.getCountries().toPromise();
    this.ports = await this.cargoMasterApiService.getPorts().toPromise();
    this.cargo = this.cargoId ? {
      id: 1,
      name: 'Cargo 1',
      abbreviation: 'c1',
      api: 21,
      ports: [
        {
          "id": 2065,
          "name": "5 WEST",
          "code": "FEW",
          "waterDensity": 1.025,
          "maxAirDraft": 48,
          "maxDraft": 20
        },
        {
          "id": 2001,
          "name": "AABENRAA",
          "code": "AAB",
          "waterDensity": 1.025,
          "maxAirDraft": 48,
          "maxDraft": 20
        },
        {
          "id": 1,
          "name": "AALBORG",
          "code": "AAL",
          "waterDensity": 1.025,
          "maxAirDraft": 48,
          "maxDraft": 20
        }
      ],
      type: 'c',
      assay_date: '21-03-2021',
      temp: '107',
      reid_vapour_pressure: null,
      gas: null,
      total_wax: null,
      pour_point: null,
      cloud_point: null,
      viscosity: null,
      cow_codes: null,
      hydrogen_sulfide_oil: null,
      hydrogen_sulfide_vapour: null,
      benzene: null,
      special_instrictions_remark: null,
      loadingInformation: [
        {
          id: 1,
          country: {
            id: 1,
            name: "India",
            code: "IND"
          },
          port: {
            "id": 2065,
            "name": "5 WEST",
            "code": "FEW",
            "waterDensity": 1.025,
            "maxAirDraft": 48,
            "maxDraft": 20
          }
        },
        {
          id: 2,
          country: {
            id: 2,
            name: "Australia",
            code: "AUS"
          },
          port: {
            "id": 2001,
            "name": "AABENRAA",
            "code": "AAB",
            "waterDensity": 1.025,
            "maxAirDraft": 48,
            "maxDraft": 20
          }
        }
      ]
    } : null;
    this.initForm(this.cargo);
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
      assay_date: this.fb.control(cargo?.assay_date),
      api: this.fb.control(cargo?.api),
      reid_vapour_pressure: this.fb.control(cargo?.reid_vapour_pressure),
      gas: this.fb.control(cargo?.gas),
      total_wax: this.fb.control(cargo?.total_wax),
      pour_point: this.fb.control(cargo?.pour_point),
      cloud_point: this.fb.control(cargo?.cloud_point),
      viscosity: this.fb.control(cargo?.viscosity),
      temp: this.fb.control(cargo?.temp),
      cow_codes: this.fb.control(cargo?.cow_codes),
      hydrogen_sulfide_oil: this.fb.control(cargo?.hydrogen_sulfide_oil),
      hydrogen_sulfide_vapour: this.fb.control(cargo?.hydrogen_sulfide_vapour),
      benzene: this.fb.control(cargo?.benzene),
      special_instrictions_remark: this.fb.control(cargo?.special_instrictions_remark),
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
    (<FormArray> this.cargoDetailsForm.get('loadingInformation')).push(this.initLoadingInformationGroup(info));
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
