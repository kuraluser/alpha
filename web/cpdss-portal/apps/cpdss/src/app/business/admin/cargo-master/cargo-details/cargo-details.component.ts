import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { AppConfigurationService } from '../../../../shared/services/app-configuration/app-configuration.service';
import { PermissionsService } from '../../../../shared/services/permissions/permissions.service';
import { IDataTableColumn } from '../../../../shared/components/datatable/datatable.model';
import { ICargoDetails } from '../../models/cargo.model';
import { CargoMasterTransformationService } from '../../services/cargo-master-transformation.service';
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


  constructor(private activatedRoute: ActivatedRoute,
    private permissionsService: PermissionsService,
    private cargoMasterTransformationService: CargoMasterTransformationService,
    private cargoMasterApiService: CargoMasterApiService,
    private timeZoneTransformationService: TimeZoneTransformationService) { }

  async ngOnInit(): Promise<void> {
    this.activatedRoute.paramMap.subscribe(params => {
      this.cargoId = Number(params.get('cargoId'));
    });

    this.permission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['CargoMasterComponent']);
    this.countries = await this.timeZoneTransformationService.getCountries().toPromise();
    this.ports = await this.cargoMasterApiService.getPorts().toPromise();
    this.cargo = {
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
      cargoLoadingInformation: [
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
    }
  }

  /**
   * Handler for add new cargo loading information
   *
   * @memberof CargoDetailsComponent
   */
  addLoadingInfo() {
    this.cargo.cargoLoadingInformation.push({id: 0, country: null, port: null});
  }

}
