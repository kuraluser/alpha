import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { IDataTableColumn, IDataTableEvent } from '../../../shared/components/datatable/datatable.model';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { PermissionsService } from '../../../shared/services/permissions/permissions.service';
import { ICargoDetails } from '../models/cargo.model';
import { IDataStateChange } from '../models/user-role-permission.model';
import { CargoMasterTransformationService } from '../services/cargo-master-transformation.service';

/**
 * Component class for cargo master
 *
 * @export
 * @class CargoMasterComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-cargo-master',
  templateUrl: './cargo-master.component.html',
  styleUrls: ['./cargo-master.component.scss']
})
export class CargoMasterComponent implements OnInit {
  get cargos(): ICargoDetails[] {
    return this._cargos;
  }

  set cargos(value: ICargoDetails[]) {
    this._cargos = value.map(cargo => this.cargoMasterTransformationService.formatCargo(cargo));
  }

  columns: IDataTableColumn[];
  loading: boolean;
  totalRecords: number;
  currentPage: number;
  first: number;
  pageState: IDataStateChange;

  private _cargos: ICargoDetails[];

  constructor(private permissionsService: PermissionsService,
    private cargoMasterTransformationService: CargoMasterTransformationService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) { }

  ngOnInit(): void {
    const permission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['CargoMasterComponent']);
    this.columns = this.cargoMasterTransformationService.getCargosDatatableColumns(permission);
    this.cargos = [{
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
      countries: [{
        id: 1,
        name: "India",
        code: "IND"
      }, {
        id: 2,
        name: "Australia",
        code: "Aus"
      }
      ],
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
      special_instrictions_remark: null
    },
    {
      id: 2,
      name: 'Cargo 2',
      abbreviation: 'c2',
      api: 22,
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
      countries: [{
        id: 2,
        name: "Australia",
        code: "AUS"
      }],
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
      special_instrictions_remark: null
    }];
  }

  /**
   * Handler for add cargo button click
   *
   * @memberof CargoMasterComponent
   */
  addCargo() {
    this.router.navigate(['cargo'], { relativeTo: this.activatedRoute });
  }

  /**
   * Handler for edit row event
   *
   * @param {IDataTableEvent} event
   * @memberof CargoMasterComponent
   */
  ediCargo(event: IDataTableEvent) {
    this.router.navigate(['cargo', event?.data?.id], { relativeTo: this.activatedRoute });
  }

}
