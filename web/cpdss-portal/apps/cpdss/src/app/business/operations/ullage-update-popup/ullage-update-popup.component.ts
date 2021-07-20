import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { AbstractControl, FormArray, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators, FormBuilder  } from '@angular/forms';
import { DATATABLE_EDITMODE, DATATABLE_FIELD_TYPE, IDataTableColumn, DATATABLE_FILTER_TYPE, DATATABLE_FILTER_MATCHMODE } from '../../../shared/components/datatable/datatable.model';
import { ICargoQuantities, IShipCargoTank, ITankOptions, IVoyagePortDetails, TANKTYPE } from '../../core/models/common.model';
import { UllageUpdatePopupTransformationService } from './ullage-update-popup-transformation.service';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { IBallastQuantities, IShipBallastTank, IShipBunkerTank } from '../../voyage-status/models/voyage-status.model';
import { IFuelType, QUANTITY_UNIT } from '../../../shared/models/common.model';
import { OHQ_MODE } from '../../cargo-planning/models/cargo-planning.model';
import { ICargoDetail , ICargoDetailValueObject } from '../models/ullage-update-popup.model';
import { numberValidator } from '../../core/directives/number-validator.directive';
import { IBlFigureTotal } from '../models/operations.model';

/**
 * Component class for ullage update
 *
 * @export
 * @class UllageUpdatePopupComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-ullage-update-popup',
  templateUrl: './ullage-update-popup.component.html',
  styleUrls: ['./ullage-update-popup.component.scss']
})
export class UllageUpdatePopupComponent implements OnInit {

  vesselInfo: any = {};
  selectedVoyage: any = {};
  currentQuantitySelectedUnit: any = null;
  selectedPortDetails: any = {};
  voyageStatusResponse: any = {};
  blFigure: any = [];
  cargoQuantityList: any = [];
  bunkerTanksList: any = [];

  readonly tankType = TANKTYPE;

  cargoTankOptions: ITankOptions = { showFillingPercentage: true, showTooltip: true, isSelectable: false, ullageField: 'correctedUllage', ullageUnit: AppConfigurationService.settings?.ullageUnit, densityField: 'api', weightField: 'actualWeight', commodityNameField: 'abbreviation' };
  ballastTankOptions: ITankOptions = { showFillingPercentage: true, showTooltip: true, isSelectable: false, ullageField: 'correctedUllage', ullageUnit: AppConfigurationService.settings?.ullageUnit, densityField: 'sg', weightField: 'actualWeight', weightUnit: AppConfigurationService.settings.baseUnit };
  ohqTankOptions: ITankOptions = { showFillingPercentage: true, showTooltip: true, densityField: 'density', weightField: 'quantity', weightUnit: AppConfigurationService.settings.baseUnit };

  cargoTanks: IShipCargoTank[][];
  rearBallastTanks: IShipBallastTank[][];
  frontBallastTanks: IShipBallastTank[][];
  centerBallastTanks: IShipBallastTank[][];
  bunkerTanks: IShipBunkerTank[][];
  rearBunkerTanks: IShipBunkerTank[][];
  cargoColumns: IDataTableColumn[];
  ballastColumns: IDataTableColumn[];
  bunkerColumns: IDataTableColumn[];
  blFigureColumns: IDataTableColumn[];
  cargoQuantities: ICargoQuantities[];
  ballastQuantities: IBallastQuantities[];
  fuelTypes:any=[];
  prevQuantitySelectedUnit: QUANTITY_UNIT;
  loadablePatternCargoDetails: any[];
  selectedCargo: any;

  selectedTab = TANKTYPE.CARGO;
  readonly fieldType = DATATABLE_FIELD_TYPE;
  tableForm: FormGroup;
  public editMode = true;
  public blFigureTotal: IBlFigureTotal;

statu: boolean;

  display:boolean;
  @Output() closePopup = new EventEmitter();
  constructor(
    private ullageUpdatePopupTransformationService: UllageUpdatePopupTransformationService,
    private fb: FormBuilder
  ) { }

  ngOnInit(): void {
    this.display = true;
    this.vesselInfo = {
      "id": 1,
      "name": "KAZUSA",
      "imoNumber": "9513402",
      "flagPath": "assets/images/flags/japan.png",
      "chiefOfficerId": 10,
      "chiefOfficerName": "null null",
      "captainId": 1,
      "captainName": "Thomas Alex",
      "charterer": "Reliance",
      "loadlines": [
        {
          "id": 1,
          "name": "Summer",
          "draftMarks": [
            20.943,
            19.888
          ]
        },
        {
          "id": 2,
          "name": "Tropical",
          "draftMarks": [
            21.376
          ]
        },
        {
          "id": 5,
          "name": "Winter and WNA",
          "draftMarks": [
            20.808
          ]
        },
        {
          "id": 7,
          "name": "Tropical to Summer",
          "draftMarks": [
            20.943,
            19.888
          ]
        },
        {
          "id": 8,
          "name": "Tropical to Winter",
          "draftMarks": [
            20.808
          ]
        },
        {
          "id": 9,
          "name": "Summer to Winter",
          "draftMarks": [
            20.808
          ]
        },
        {
          "id": 10,
          "name": "Winter to Summer",
          "draftMarks": [
            20.943,
            19.888
          ]
        },
        {
          "id": 11,
          "name": "Winter to Tropical",
          "draftMarks": [
            21.376
          ]
        },
        {
          "id": 12,
          "name": "Summer to Tropical",
          "draftMarks": [
            21.376
          ]
        }
      ]
    };

    this.selectedVoyage = {
      "voyageNo": "RisVoyage",
      "id": 1901,
      "startDate": "10-04-2021 11:19",
      "endDate": "30-04-2021 11:19",
      "status": "Active",
      "confirmedLoadableStudyId": 5829,
      "actualStartDate": "10-04-2021",
      "actualEndDate": "30-04-2021",
      "statusId": 3,
      "noOfDays": -7412
    };
    this.currentQuantitySelectedUnit = "MT";
    this.selectedPortDetails = { "portOrder": 1, "operationType": "ARR", "portId": 359, "portRotationId": 107678 };
    this.voyageStatusResponse = {
      "responseStatus": {
        "status": "200"
      },
      "cargoQuantities": [
        {
          "tankId": 25580,
          "tankName": "1C",
          "actualWeight": 0,
          "plannedWeight": 0,
          "capacity": 30229.5,
          "abbreviation": "",
          "cargoId": 0,
          "colorCode": "",
          "correctedUllage": 0,
          "api": 0,
          "sg": null,
          "isCommingleCargo": false,
          "volume": null,
          "percentageFilled": "0.00"
        },
        {
          "tankId": 25581,
          "tankName": "2C",
          "actualWeight": 0,
          "plannedWeight": 0,
          "capacity": 28201.6,
          "abbreviation": "",
          "cargoId": 0,
          "colorCode": "",
          "correctedUllage": 0,
          "api": 0,
          "sg": null,
          "isCommingleCargo": false,
          "volume": null,
          "percentageFilled": "0.00"
        },
        {
          "tankId": 25582,
          "tankName": "3C",
          "actualWeight": 0,
          "plannedWeight": 0,
          "capacity": 28201.6,
          "abbreviation": "",
          "cargoId": 0,
          "colorCode": "",
          "correctedUllage": 0,
          "api": 0,
          "sg": null,
          "isCommingleCargo": false,
          "volume": null,
          "percentageFilled": "0.00"
        },
        {
          "tankId": 25583,
          "tankName": "4C",
          "actualWeight": 0,
          "plannedWeight": 0,
          "capacity": 28201.6,
          "abbreviation": "",
          "cargoId": 0,
          "colorCode": "",
          "correctedUllage": 0,
          "api": 0,
          "sg": null,
          "isCommingleCargo": false,
          "volume": null,
          "percentageFilled": "0.00"
        },
        {
          "tankId": 25584,
          "tankName": "5C",
          "actualWeight": 0,
          "plannedWeight": 0,
          "capacity": 33725.1,
          "abbreviation": "",
          "cargoId": 0,
          "colorCode": "",
          "correctedUllage": 0,
          "api": 0,
          "sg": null,
          "isCommingleCargo": false,
          "volume": null,
          "percentageFilled": "0.00"
        },
        {
          "tankId": 25585,
          "tankName": "1P",
          "actualWeight": 0,
          "plannedWeight": 0,
          "capacity": 20797.7,
          "abbreviation": "",
          "cargoId": 0,
          "colorCode": "",
          "correctedUllage": 0,
          "api": 0,
          "sg": null,
          "isCommingleCargo": false,
          "volume": null,
          "percentageFilled": "0.00"
        },
        {
          "tankId": 25586,
          "tankName": "1S",
          "actualWeight": 0,
          "plannedWeight": 0,
          "capacity": 20797.7,
          "abbreviation": "",
          "cargoId": 0,
          "colorCode": "",
          "correctedUllage": 0,
          "api": 0,
          "sg": null,
          "isCommingleCargo": false,
          "volume": null,
          "percentageFilled": "0.00"
        },
        {
          "tankId": 25587,
          "tankName": "2P",
          "actualWeight": 0,
          "plannedWeight": 0,
          "capacity": 20290.8,
          "abbreviation": "",
          "cargoId": 0,
          "colorCode": "",
          "correctedUllage": 0,
          "api": 0,
          "sg": null,
          "isCommingleCargo": false,
          "volume": null,
          "percentageFilled": "0.00"
        },
        {
          "tankId": 25588,
          "tankName": "2S",
          "actualWeight": 0,
          "plannedWeight": 0,
          "capacity": 20290.8,
          "abbreviation": "",
          "cargoId": 0,
          "colorCode": "",
          "correctedUllage": 0,
          "api": 0,
          "sg": null,
          "isCommingleCargo": false,
          "volume": null,
          "percentageFilled": "0.00"
        },
        {
          "tankId": 25589,
          "tankName": "3P",
          "actualWeight": 0,
          "plannedWeight": 0,
          "capacity": 20290.8,
          "abbreviation": "",
          "cargoId": 0,
          "colorCode": "",
          "correctedUllage": 0,
          "api": 0,
          "sg": null,
          "isCommingleCargo": false,
          "volume": null,
          "percentageFilled": "0.00"
        },
        {
          "tankId": 25590,
          "tankName": "3S",
          "actualWeight": 0,
          "plannedWeight": 0,
          "capacity": 20290.8,
          "abbreviation": "",
          "cargoId": 0,
          "colorCode": "",
          "correctedUllage": 0,
          "api": 0,
          "sg": null,
          "isCommingleCargo": false,
          "volume": null,
          "percentageFilled": "0.00"
        },
        {
          "tankId": 25591,
          "tankName": "4P",
          "actualWeight": 0,
          "plannedWeight": 0,
          "capacity": 20290.8,
          "abbreviation": "",
          "cargoId": 0,
          "colorCode": "",
          "correctedUllage": 0,
          "api": 0,
          "sg": null,
          "isCommingleCargo": false,
          "volume": null,
          "percentageFilled": "0.00"
        },
        {
          "tankId": 25592,
          "tankName": "4S",
          "actualWeight": 0,
          "plannedWeight": 0,
          "capacity": 20290.8,
          "abbreviation": "",
          "cargoId": 0,
          "colorCode": "",
          "correctedUllage": 0,
          "api": 0,
          "sg": null,
          "isCommingleCargo": false,
          "volume": null,
          "percentageFilled": "0.00"
        },
        {
          "tankId": 25593,
          "tankName": "5P",
          "actualWeight": 0,
          "plannedWeight": 0,
          "capacity": 17277.4,
          "abbreviation": "",
          "cargoId": 0,
          "colorCode": "",
          "correctedUllage": 0,
          "api": 0,
          "sg": null,
          "isCommingleCargo": false,
          "volume": null,
          "percentageFilled": "0.00"
        },
        {
          "tankId": 25594,
          "tankName": "5S",
          "actualWeight": 0,
          "plannedWeight": 0,
          "capacity": 17277.4,
          "abbreviation": "",
          "cargoId": 0,
          "colorCode": "",
          "correctedUllage": 0,
          "api": 0,
          "sg": null,
          "isCommingleCargo": false,
          "volume": null,
          "percentageFilled": "0.00"
        },
        {
          "tankId": 25595,
          "tankName": "SLP",
          "actualWeight": 0,
          "plannedWeight": 0,
          "capacity": 4117.3,
          "abbreviation": "",
          "cargoId": 0,
          "colorCode": "",
          "correctedUllage": 0,
          "api": 0,
          "sg": null,
          "isCommingleCargo": false,
          "volume": null,
          "percentageFilled": "0.00"
        },
        {
          "tankId": 25596,
          "tankName": "SLS",
          "actualWeight": 0,
          "plannedWeight": 0,
          "capacity": 4117.3,
          "abbreviation": "",
          "cargoId": 0,
          "colorCode": "",
          "correctedUllage": 0,
          "api": 0,
          "sg": null,
          "isCommingleCargo": false,
          "volume": null,
          "percentageFilled": "0.00"
        }
      ],
      "ballastQuantities": [
        {
          "tankId": 25606,
          "tankName": "WB5P",
          "actualWeight": 0,
          "plannedWeight": 0,
          "capacity": 8561.9,
          "abbreviation": null,
          "cargoId": null,
          "colorCode": "",
          "correctedUllage": 0,
          "api": null,
          "sg": "",
          "isCommingleCargo": null,
          "volume": 0,
          "percentageFilled": "0.00"
        },
        {
          "tankId": 25607,
          "tankName": "WB5S",
          "actualWeight": 0,
          "plannedWeight": 0,
          "capacity": 8560.4,
          "abbreviation": null,
          "cargoId": null,
          "colorCode": "",
          "correctedUllage": 0,
          "api": null,
          "sg": "",
          "isCommingleCargo": null,
          "volume": 0,
          "percentageFilled": "0.00"
        },
        {
          "tankId": 25608,
          "tankName": "AWBP",
          "actualWeight": 0,
          "plannedWeight": 0,
          "capacity": 1024.9,
          "abbreviation": null,
          "cargoId": null,
          "colorCode": "",
          "correctedUllage": 0,
          "api": null,
          "sg": "",
          "isCommingleCargo": null,
          "volume": 0,
          "percentageFilled": "0.00"
        },
        {
          "tankId": 25609,
          "tankName": "AWBS",
          "actualWeight": 0,
          "plannedWeight": 0,
          "capacity": 1024.9,
          "abbreviation": null,
          "cargoId": null,
          "colorCode": "",
          "correctedUllage": 0,
          "api": null,
          "sg": "",
          "isCommingleCargo": null,
          "volume": 0,
          "percentageFilled": "0.00"
        },
        {
          "tankId": 25610,
          "tankName": "APT",
          "actualWeight": 0,
          "plannedWeight": 0,
          "capacity": 2574.4,
          "abbreviation": null,
          "cargoId": null,
          "colorCode": "",
          "correctedUllage": 0,
          "api": null,
          "sg": "",
          "isCommingleCargo": null,
          "volume": 0,
          "percentageFilled": "0.00"
        },
        {
          "tankId": 25611,
          "tankName": "FPTU",
          "actualWeight": 0,
          "plannedWeight": 0,
          "capacity": 5156.5,
          "abbreviation": null,
          "cargoId": null,
          "colorCode": "",
          "correctedUllage": 0,
          "api": null,
          "sg": "",
          "isCommingleCargo": null,
          "volume": 0,
          "percentageFilled": "0.00"
        },
        {
          "tankId": 25599,
          "tankName": "WB1S",
          "actualWeight": 0,
          "plannedWeight": 0,
          "capacity": 8904.4,
          "abbreviation": null,
          "cargoId": null,
          "colorCode": "",
          "correctedUllage": 0,
          "api": null,
          "sg": "",
          "isCommingleCargo": null,
          "volume": 0,
          "percentageFilled": "0.00"
        },
        {
          "tankId": 25600,
          "tankName": "WB2P",
          "actualWeight": 0,
          "plannedWeight": 0,
          "capacity": 8873.6,
          "abbreviation": null,
          "cargoId": null,
          "colorCode": "",
          "correctedUllage": 0,
          "api": null,
          "sg": "",
          "isCommingleCargo": null,
          "volume": 0,
          "percentageFilled": "0.00"
        },
        {
          "tankId": 25601,
          "tankName": "WB2S",
          "actualWeight": 0,
          "plannedWeight": 0,
          "capacity": 8871.5,
          "abbreviation": null,
          "cargoId": null,
          "colorCode": "",
          "correctedUllage": 0,
          "api": null,
          "sg": "",
          "isCommingleCargo": null,
          "volume": 0,
          "percentageFilled": "0.00"
        },
        {
          "tankId": 25602,
          "tankName": "WB3P",
          "actualWeight": 0,
          "plannedWeight": 0,
          "capacity": 8875.8,
          "abbreviation": null,
          "cargoId": null,
          "colorCode": "",
          "correctedUllage": 0,
          "api": null,
          "sg": "",
          "isCommingleCargo": null,
          "volume": 0,
          "percentageFilled": "0.00"
        },
        {
          "tankId": 25603,
          "tankName": "WB3S",
          "actualWeight": 0,
          "plannedWeight": 0,
          "capacity": 8873.8,
          "abbreviation": null,
          "cargoId": null,
          "colorCode": "",
          "correctedUllage": 0,
          "api": null,
          "sg": "",
          "isCommingleCargo": null,
          "volume": 0,
          "percentageFilled": "0.00"
        },
        {
          "tankId": 25604,
          "tankName": "WB4P",
          "actualWeight": 0,
          "plannedWeight": 0,
          "capacity": 8743.7,
          "abbreviation": null,
          "cargoId": null,
          "colorCode": "",
          "correctedUllage": 0,
          "api": null,
          "sg": "",
          "isCommingleCargo": null,
          "volume": 0,
          "percentageFilled": "0.00"
        },
        {
          "tankId": 25605,
          "tankName": "WB4S",
          "actualWeight": 0,
          "plannedWeight": 0,
          "capacity": 8741.7,
          "abbreviation": null,
          "cargoId": null,
          "colorCode": "",
          "correctedUllage": 0,
          "api": null,
          "sg": "",
          "isCommingleCargo": null,
          "volume": 0,
          "percentageFilled": "0.00"
        },
        {
          "tankId": 25597,
          "tankName": "FPTL",
          "actualWeight": 0,
          "plannedWeight": 0,
          "capacity": 5444.2,
          "abbreviation": null,
          "cargoId": null,
          "colorCode": "",
          "correctedUllage": 0,
          "api": null,
          "sg": "",
          "isCommingleCargo": null,
          "volume": 0,
          "percentageFilled": "0.00"
        },
        {
          "tankId": 25598,
          "tankName": "WB1P",
          "actualWeight": 0,
          "plannedWeight": 0,
          "capacity": 8906.5,
          "abbreviation": null,
          "cargoId": null,
          "colorCode": "",
          "correctedUllage": 0,
          "api": null,
          "sg": "",
          "isCommingleCargo": null,
          "volume": 0,
          "percentageFilled": "0.00"
        }
      ],
      "bunkerQuantities": [
        {
          "id": 116911,
          "portId": 359,
          "fuelTypeId": 5,
          "fuelTypeName": "Fuel Oil Tank",
          "fuelTypeShortName": "FOT",
          "tankId": 25619,
          "tankName": "BFOSRV",
          "arrivalVolume": 25,
          "arrivalQuantity": 20,
          "actualArrivalQuantity": 0,
          "departureVolume": 25,
          "departureQuantity": 20,
          "actualDepartureQuantity": 0,
          "colorCode": "#c60a09",
          "density": 0.8,
          "portRotationId": 107678,
          "quantity": 0,
          "volume": 0
        },
        {
          "id": 117184,
          "portId": 359,
          "fuelTypeId": 5,
          "fuelTypeName": "Fuel Oil Tank",
          "fuelTypeShortName": "FOT",
          "tankId": 25614,
          "tankName": "FO2P",
          "arrivalVolume": 0,
          "arrivalQuantity": 0,
          "actualArrivalQuantity": 0,
          "departureVolume": 0,
          "departureQuantity": 0,
          "actualDepartureQuantity": 0,
          "colorCode": "#c60a09",
          "density": 0,
          "portRotationId": 107678,
          "quantity": 0,
          "volume": 0
        },
        {
          "id": 117185,
          "portId": 359,
          "fuelTypeId": 5,
          "fuelTypeName": "Fuel Oil Tank",
          "fuelTypeShortName": "FOT",
          "tankId": 25615,
          "tankName": "FO2S",
          "arrivalVolume": 0,
          "arrivalQuantity": 0,
          "actualArrivalQuantity": 0,
          "departureVolume": 0,
          "departureQuantity": 0,
          "actualDepartureQuantity": 0,
          "colorCode": "#c60a09",
          "density": 0,
          "portRotationId": 107678,
          "quantity": 0,
          "volume": 0
        },
        {
          "id": 116909,
          "portId": 359,
          "fuelTypeId": 6,
          "fuelTypeName": "Diesel Oil Tank",
          "fuelTypeShortName": "DOT",
          "tankId": 25624,
          "tankName": "DOSRV1",
          "arrivalVolume": 25,
          "arrivalQuantity": 20,
          "actualArrivalQuantity": 0,
          "departureVolume": 25,
          "departureQuantity": 20,
          "actualDepartureQuantity": 0,
          "colorCode": "#fa8424",
          "density": 0.8,
          "portRotationId": 107678,
          "quantity": 0,
          "volume": 0
        },
        {
          "id": 117191,
          "portId": 359,
          "fuelTypeId": 6,
          "fuelTypeName": "Diesel Oil Tank",
          "fuelTypeShortName": "DOT",
          "tankId": 25625,
          "tankName": "DOSRV2",
          "arrivalVolume": 0,
          "arrivalQuantity": 0,
          "actualArrivalQuantity": 0,
          "departureVolume": 0,
          "departureQuantity": 0,
          "actualDepartureQuantity": 0,
          "colorCode": "#fa8424",
          "density": 0,
          "portRotationId": 107678,
          "quantity": 0,
          "volume": 0
        },
        {
          "id": 116910,
          "portId": 359,
          "fuelTypeId": 3,
          "fuelTypeName": "Fresh Water Tank",
          "fuelTypeShortName": "FWT",
          "tankId": 25636,
          "tankName": "DRWT",
          "arrivalVolume": 200,
          "arrivalQuantity": 200,
          "actualArrivalQuantity": 0,
          "departureVolume": 200,
          "departureQuantity": 200,
          "actualDepartureQuantity": 0,
          "colorCode": "#5aa1a3",
          "density": 1,
          "portRotationId": 107678,
          "quantity": 0,
          "volume": 0
        },
        {
          "id": 117196,
          "portId": 359,
          "fuelTypeId": 3,
          "fuelTypeName": "Fresh Water Tank",
          "fuelTypeShortName": "FWT",
          "tankId": 25637,
          "tankName": "FRWT",
          "arrivalVolume": 0,
          "arrivalQuantity": 0,
          "actualArrivalQuantity": 0,
          "departureVolume": 0,
          "departureQuantity": 0,
          "actualDepartureQuantity": 0,
          "colorCode": "#5aa1a3",
          "density": 0,
          "portRotationId": 107678,
          "quantity": 0,
          "volume": 0
        },
        {
          "id": 117198,
          "portId": 359,
          "fuelTypeId": 3,
          "fuelTypeName": "Fresh Water Tank",
          "fuelTypeShortName": "FWT",
          "tankId": 25638,
          "tankName": "DSWTP",
          "arrivalVolume": 0,
          "arrivalQuantity": 0,
          "actualArrivalQuantity": 0,
          "departureVolume": 0,
          "departureQuantity": 0,
          "actualDepartureQuantity": 0,
          "colorCode": "#5aa1a3",
          "density": 0,
          "portRotationId": 107678,
          "quantity": 0,
          "volume": 0
        },
        {
          "id": 117199,
          "portId": 359,
          "fuelTypeId": 3,
          "fuelTypeName": "Fresh Water Tank",
          "fuelTypeShortName": "FWT",
          "tankId": 25639,
          "tankName": "DSWTS",
          "arrivalVolume": 0,
          "arrivalQuantity": 0,
          "actualArrivalQuantity": 0,
          "departureVolume": 0,
          "departureQuantity": 0,
          "actualDepartureQuantity": 0,
          "colorCode": "#5aa1a3",
          "density": 0,
          "portRotationId": 107678,
          "quantity": 0,
          "volume": 0
        },
        {
          "id": 117192,
          "portId": 359,
          "fuelTypeId": 6,
          "fuelTypeName": "Diesel Oil Tank",
          "fuelTypeShortName": "DOT",
          "tankId": 25622,
          "tankName": "DO1S",
          "arrivalVolume": 0,
          "arrivalQuantity": 0,
          "actualArrivalQuantity": 0,
          "departureVolume": 0,
          "departureQuantity": 0,
          "actualDepartureQuantity": 0,
          "colorCode": "#fa8424",
          "density": 0,
          "portRotationId": 107678,
          "quantity": 0,
          "volume": 0
        },
        {
          "id": 117186,
          "portId": 359,
          "fuelTypeId": 5,
          "fuelTypeName": "Fuel Oil Tank",
          "fuelTypeShortName": "FOT",
          "tankId": 25612,
          "tankName": "FO1P",
          "arrivalVolume": 0,
          "arrivalQuantity": 0,
          "actualArrivalQuantity": 0,
          "departureVolume": 0,
          "departureQuantity": 0,
          "actualDepartureQuantity": 0,
          "colorCode": "#c60a09",
          "density": 0,
          "portRotationId": 107678,
          "quantity": 0,
          "volume": 0
        },
        {
          "id": 117187,
          "portId": 359,
          "fuelTypeId": 5,
          "fuelTypeName": "Fuel Oil Tank",
          "fuelTypeShortName": "FOT",
          "tankId": 25613,
          "tankName": "FO1S",
          "arrivalVolume": 0,
          "arrivalQuantity": 0,
          "actualArrivalQuantity": 0,
          "departureVolume": 0,
          "departureQuantity": 0,
          "actualDepartureQuantity": 0,
          "colorCode": "#c60a09",
          "density": 0,
          "portRotationId": 107678,
          "quantity": 0,
          "volume": 0
        },
        {
          "id": 117188,
          "portId": 359,
          "fuelTypeId": 5,
          "fuelTypeName": "Fuel Oil Tank",
          "fuelTypeShortName": "FOT",
          "tankId": 25616,
          "tankName": "HFOSRV",
          "arrivalVolume": 0,
          "arrivalQuantity": 0,
          "actualArrivalQuantity": 0,
          "departureVolume": 0,
          "departureQuantity": 0,
          "actualDepartureQuantity": 0,
          "colorCode": "#c60a09",
          "density": 0,
          "portRotationId": 107678,
          "quantity": 0,
          "volume": 0
        },
        {
          "id": 117189,
          "portId": 359,
          "fuelTypeId": 5,
          "fuelTypeName": "Fuel Oil Tank",
          "fuelTypeShortName": "FOT",
          "tankId": 25617,
          "tankName": "HFOSET",
          "arrivalVolume": 0,
          "arrivalQuantity": 0,
          "actualArrivalQuantity": 0,
          "departureVolume": 0,
          "departureQuantity": 0,
          "actualDepartureQuantity": 0,
          "colorCode": "#c60a09",
          "density": 0,
          "portRotationId": 107678,
          "quantity": 0,
          "volume": 0
        },
        {
          "id": 117194,
          "portId": 359,
          "fuelTypeId": 6,
          "fuelTypeName": "Diesel Oil Tank",
          "fuelTypeShortName": "DOT",
          "tankId": 25623,
          "tankName": "DO2S",
          "arrivalVolume": 0,
          "arrivalQuantity": 0,
          "actualArrivalQuantity": 0,
          "departureVolume": 0,
          "departureQuantity": 0,
          "actualDepartureQuantity": 0,
          "colorCode": "#fa8424",
          "density": 0,
          "portRotationId": 107678,
          "quantity": 0,
          "volume": 0
        }
      ],
      "cargoTanks": [
        [
          {
            "id": 25595,
            "categoryId": 1,
            "categoryName": "Cargo Tank",
            "name": "SLOP TANK",
            "frameNumberFrom": "49",
            "frameNumberTo": "52",
            "shortName": "SLP",
            "fullCapacityCubm": "4117.3000",
            "density": 1.3,
            "group": 1,
            "order": 1,
            "slopTank": false,
            "commodity": {
              "tankId": 25595,
              "tankName": "SLP",
              "actualWeight": 0,
              "plannedWeight": 0,
              "capacity": 4117.3,
              "abbreviation": "",
              "cargoId": 0,
              "colorCode": "",
              "correctedUllage": 0,
              "api": 0,
              "sg": null,
              "isCommingleCargo": false,
              "volume": null,
              "percentageFilled": "0.00"
            },
            "gridColumn": "1 / 4",
            "percentageFilled": "0.00"
          },
          {
            "id": 25593,
            "categoryId": 1,
            "categoryName": "Cargo Tank",
            "name": "NO.5 WING CARGO OIL TANK",
            "frameNumberFrom": "52",
            "frameNumberTo": "61",
            "shortName": "5P",
            "fullCapacityCubm": "17277.4000",
            "density": 1.3,
            "group": 1,
            "order": 2,
            "slopTank": false,
            "commodity": {
              "tankId": 25593,
              "tankName": "5P",
              "actualWeight": 0,
              "plannedWeight": 0,
              "capacity": 17277.4,
              "abbreviation": "",
              "cargoId": 0,
              "colorCode": "",
              "correctedUllage": 0,
              "api": 0,
              "sg": null,
              "isCommingleCargo": false,
              "volume": null,
              "percentageFilled": "0.00"
            },
            "gridColumn": "4 / 13",
            "percentageFilled": "0.00"
          },
          {
            "id": 25584,
            "categoryId": 1,
            "categoryName": "Cargo Tank",
            "name": "NO.5 CENTER CARGO OIL TANK",
            "frameNumberFrom": "49",
            "frameNumberTo": "61",
            "shortName": "5C",
            "fullCapacityCubm": "33725.1000",
            "density": 1.3,
            "group": 1,
            "order": 3,
            "slopTank": false,
            "commodity": {
              "tankId": 25584,
              "tankName": "5C",
              "actualWeight": 0,
              "plannedWeight": 0,
              "capacity": 33725.1,
              "abbreviation": "",
              "cargoId": 0,
              "colorCode": "",
              "correctedUllage": 0,
              "api": 0,
              "sg": null,
              "isCommingleCargo": false,
              "volume": null,
              "percentageFilled": "0.00"
            },
            "gridColumn": "1 / 13",
            "percentageFilled": "0.00"
          },
          {
            "id": 25596,
            "categoryId": 1,
            "categoryName": "Cargo Tank",
            "name": "SLOP TANK",
            "frameNumberFrom": "49",
            "frameNumberTo": "52",
            "shortName": "SLS",
            "fullCapacityCubm": "4117.3000",
            "density": 1.3,
            "group": 1,
            "order": 4,
            "slopTank": false,
            "commodity": {
              "tankId": 25596,
              "tankName": "SLS",
              "actualWeight": 0,
              "plannedWeight": 0,
              "capacity": 4117.3,
              "abbreviation": "",
              "cargoId": 0,
              "colorCode": "",
              "correctedUllage": 0,
              "api": 0,
              "sg": null,
              "isCommingleCargo": false,
              "volume": null,
              "percentageFilled": "0.00"
            },
            "gridColumn": "1 / 4",
            "percentageFilled": "0.00"
          },
          {
            "id": 25594,
            "categoryId": 1,
            "categoryName": "Cargo Tank",
            "name": "NO.5 WING CARGO OIL TANK",
            "frameNumberFrom": "52",
            "frameNumberTo": "61",
            "shortName": "5S",
            "fullCapacityCubm": "17277.4000",
            "density": 1.3,
            "group": 1,
            "order": 5,
            "slopTank": false,
            "commodity": {
              "tankId": 25594,
              "tankName": "5S",
              "actualWeight": 0,
              "plannedWeight": 0,
              "capacity": 17277.4,
              "abbreviation": "",
              "cargoId": 0,
              "colorCode": "",
              "correctedUllage": 0,
              "api": 0,
              "sg": null,
              "isCommingleCargo": false,
              "volume": null,
              "percentageFilled": "0.00"
            },
            "gridColumn": "4 / 13",
            "percentageFilled": "0.00"
          }
        ],
        [
          {
            "id": 25591,
            "categoryId": 1,
            "categoryName": "Cargo Tank",
            "name": "NO.4 WING CARGO OIL TANK",
            "frameNumberFrom": "61",
            "frameNumberTo": "71",
            "shortName": "4P",
            "fullCapacityCubm": "20290.8000",
            "density": 1.3,
            "group": 2,
            "order": 1,
            "slopTank": false,
            "commodity": {
              "tankId": 25591,
              "tankName": "4P",
              "actualWeight": 0,
              "plannedWeight": 0,
              "capacity": 20290.8,
              "abbreviation": "",
              "cargoId": 0,
              "colorCode": "",
              "correctedUllage": 0,
              "api": 0,
              "sg": null,
              "isCommingleCargo": false,
              "volume": null,
              "percentageFilled": "0.00"
            },
            "gridColumn": "1 / 11",
            "percentageFilled": "0.00"
          },
          {
            "id": 25583,
            "categoryId": 1,
            "categoryName": "Cargo Tank",
            "name": "NO.4 CENTER CARGO OIL TANK",
            "frameNumberFrom": "61",
            "frameNumberTo": "71",
            "shortName": "4C",
            "fullCapacityCubm": "28201.6000",
            "density": 1.3,
            "group": 2,
            "order": 2,
            "slopTank": false,
            "commodity": {
              "tankId": 25583,
              "tankName": "4C",
              "actualWeight": 0,
              "plannedWeight": 0,
              "capacity": 28201.6,
              "abbreviation": "",
              "cargoId": 0,
              "colorCode": "",
              "correctedUllage": 0,
              "api": 0,
              "sg": null,
              "isCommingleCargo": false,
              "volume": null,
              "percentageFilled": "0.00"
            },
            "gridColumn": "1 / 11",
            "percentageFilled": "0.00"
          },
          {
            "id": 25592,
            "categoryId": 1,
            "categoryName": "Cargo Tank",
            "name": "NO.4 WING CARGO OIL TANK",
            "frameNumberFrom": "61",
            "frameNumberTo": "71",
            "shortName": "4S",
            "fullCapacityCubm": "20290.8000",
            "density": 1.3,
            "group": 2,
            "order": 3,
            "slopTank": false,
            "commodity": {
              "tankId": 25592,
              "tankName": "4S",
              "actualWeight": 0,
              "plannedWeight": 0,
              "capacity": 20290.8,
              "abbreviation": "",
              "cargoId": 0,
              "colorCode": "",
              "correctedUllage": 0,
              "api": 0,
              "sg": null,
              "isCommingleCargo": false,
              "volume": null,
              "percentageFilled": "0.00"
            },
            "gridColumn": "1 / 11",
            "percentageFilled": "0.00"
          }
        ],
        [
          {
            "id": 25589,
            "categoryId": 1,
            "categoryName": "Cargo Tank",
            "name": "NO.3 WING CARGO OIL TANK",
            "frameNumberFrom": "71",
            "frameNumberTo": "81",
            "shortName": "3P",
            "fullCapacityCubm": "20290.8000",
            "density": 1.3,
            "group": 3,
            "order": 1,
            "slopTank": false,
            "commodity": {
              "tankId": 25589,
              "tankName": "3P",
              "actualWeight": 0,
              "plannedWeight": 0,
              "capacity": 20290.8,
              "abbreviation": "",
              "cargoId": 0,
              "colorCode": "",
              "correctedUllage": 0,
              "api": 0,
              "sg": null,
              "isCommingleCargo": false,
              "volume": null,
              "percentageFilled": "0.00"
            },
            "gridColumn": "1 / 11",
            "percentageFilled": "0.00"
          },
          {
            "id": 25582,
            "categoryId": 1,
            "categoryName": "Cargo Tank",
            "name": "NO.3 CENTER CARGO OIL TANK",
            "frameNumberFrom": "71",
            "frameNumberTo": "81",
            "shortName": "3C",
            "fullCapacityCubm": "28201.6000",
            "density": 1.3,
            "group": 3,
            "order": 2,
            "slopTank": false,
            "commodity": {
              "tankId": 25582,
              "tankName": "3C",
              "actualWeight": 0,
              "plannedWeight": 0,
              "capacity": 28201.6,
              "abbreviation": "",
              "cargoId": 0,
              "colorCode": "",
              "correctedUllage": 0,
              "api": 0,
              "sg": null,
              "isCommingleCargo": false,
              "volume": null,
              "percentageFilled": "0.00"
            },
            "gridColumn": "1 / 11",
            "percentageFilled": "0.00"
          },
          {
            "id": 25590,
            "categoryId": 1,
            "categoryName": "Cargo Tank",
            "name": "NO.3 WING CARGO OIL TANK",
            "frameNumberFrom": "71",
            "frameNumberTo": "81",
            "shortName": "3S",
            "fullCapacityCubm": "20290.8000",
            "density": 1.3,
            "group": 3,
            "order": 3,
            "slopTank": false,
            "commodity": {
              "tankId": 25590,
              "tankName": "3S",
              "actualWeight": 0,
              "plannedWeight": 0,
              "capacity": 20290.8,
              "abbreviation": "",
              "cargoId": 0,
              "colorCode": "",
              "correctedUllage": 0,
              "api": 0,
              "sg": null,
              "isCommingleCargo": false,
              "volume": null,
              "percentageFilled": "0.00"
            },
            "gridColumn": "1 / 11",
            "percentageFilled": "0.00"
          }
        ],
        [
          {
            "id": 25587,
            "categoryId": 1,
            "categoryName": "Cargo Tank",
            "name": "NO.2 WING CARGO OIL TANK",
            "frameNumberFrom": "81",
            "frameNumberTo": "91",
            "shortName": "2P",
            "fullCapacityCubm": "20290.8000",
            "density": 1.3,
            "group": 4,
            "order": 1,
            "slopTank": false,
            "commodity": {
              "tankId": 25587,
              "tankName": "2P",
              "actualWeight": 0,
              "plannedWeight": 0,
              "capacity": 20290.8,
              "abbreviation": "",
              "cargoId": 0,
              "colorCode": "",
              "correctedUllage": 0,
              "api": 0,
              "sg": null,
              "isCommingleCargo": false,
              "volume": null,
              "percentageFilled": "0.00"
            },
            "gridColumn": "1 / 11",
            "percentageFilled": "0.00"
          },
          {
            "id": 25581,
            "categoryId": 1,
            "categoryName": "Cargo Tank",
            "name": "NO.2 CENTER CARGO OIL TANK",
            "frameNumberFrom": "81",
            "frameNumberTo": "91",
            "shortName": "2C",
            "fullCapacityCubm": "28201.6000",
            "density": 1.3,
            "group": 4,
            "order": 2,
            "slopTank": false,
            "commodity": {
              "tankId": 25581,
              "tankName": "2C",
              "actualWeight": 0,
              "plannedWeight": 0,
              "capacity": 28201.6,
              "abbreviation": "",
              "cargoId": 0,
              "colorCode": "",
              "correctedUllage": 0,
              "api": 0,
              "sg": null,
              "isCommingleCargo": false,
              "volume": null,
              "percentageFilled": "0.00"
            },
            "gridColumn": "1 / 11",
            "percentageFilled": "0.00"
          },
          {
            "id": 25588,
            "categoryId": 1,
            "categoryName": "Cargo Tank",
            "name": "NO.2 WING CARGO OIL TANK",
            "frameNumberFrom": "81",
            "frameNumberTo": "91",
            "shortName": "2S",
            "fullCapacityCubm": "20290.8000",
            "density": 1.3,
            "group": 4,
            "order": 3,
            "slopTank": false,
            "commodity": {
              "tankId": 25588,
              "tankName": "2S",
              "actualWeight": 0,
              "plannedWeight": 0,
              "capacity": 20290.8,
              "abbreviation": "",
              "cargoId": 0,
              "colorCode": "",
              "correctedUllage": 0,
              "api": 0,
              "sg": null,
              "isCommingleCargo": false,
              "volume": null,
              "percentageFilled": "0.00"
            },
            "gridColumn": "1 / 11",
            "percentageFilled": "0.00"
          }
        ],
        [
          {
            "id": 25585,
            "categoryId": 1,
            "categoryName": "Cargo Tank",
            "name": "NO.1  WING CARGO OIL TANK",
            "frameNumberFrom": "91",
            "frameNumberTo": "103",
            "shortName": "1P",
            "fullCapacityCubm": "20797.7000",
            "density": 1.3,
            "group": 5,
            "order": 1,
            "slopTank": false,
            "commodity": {
              "tankId": 25585,
              "tankName": "1P",
              "actualWeight": 0,
              "plannedWeight": 0,
              "capacity": 20797.7,
              "abbreviation": "",
              "cargoId": 0,
              "colorCode": "",
              "correctedUllage": 0,
              "api": 0,
              "sg": null,
              "isCommingleCargo": false,
              "volume": null,
              "percentageFilled": "0.00"
            },
            "gridColumn": "1 / 13",
            "percentageFilled": "0.00"
          },
          {
            "id": 25580,
            "categoryId": 1,
            "categoryName": "Cargo Tank",
            "name": "NO.1 CENTER CARGO OIL TANK",
            "frameNumberFrom": "91",
            "frameNumberTo": "103",
            "shortName": "1C",
            "fullCapacityCubm": "30229.5000",
            "density": 1.3,
            "group": 5,
            "order": 2,
            "slopTank": false,
            "commodity": {
              "tankId": 25580,
              "tankName": "1C",
              "actualWeight": 0,
              "plannedWeight": 0,
              "capacity": 30229.5,
              "abbreviation": "",
              "cargoId": 0,
              "colorCode": "",
              "correctedUllage": 0,
              "api": 0,
              "sg": null,
              "isCommingleCargo": false,
              "volume": null,
              "percentageFilled": "0.00"
            },
            "gridColumn": "1 / 13",
            "percentageFilled": "0.00"
          },
          {
            "id": 25586,
            "categoryId": 1,
            "categoryName": "Cargo Tank",
            "name": "NO.1  WING CARGO OIL TANK",
            "frameNumberFrom": "91",
            "frameNumberTo": "103",
            "shortName": "1S",
            "fullCapacityCubm": "20797.7000",
            "density": 1.3,
            "group": 5,
            "order": 3,
            "slopTank": false,
            "commodity": {
              "tankId": 25586,
              "tankName": "1S",
              "actualWeight": 0,
              "plannedWeight": 0,
              "capacity": 20797.7,
              "abbreviation": "",
              "cargoId": 0,
              "colorCode": "",
              "correctedUllage": 0,
              "api": 0,
              "sg": null,
              "isCommingleCargo": false,
              "volume": null,
              "percentageFilled": "0.00"
            },
            "gridColumn": "1 / 13",
            "percentageFilled": "0.00"
          }
        ]
      ],
      "bunkerTanks": [
        [
          {
            "id": 25614,
            "categoryId": 5,
            "categoryName": "Fuel Oil Tank",
            "name": "NO.2  FUEL OIL TANK",
            "frameNumberFrom": "15",
            "frameNumberTo": "39",
            "shortName": "FO2P",
            "fullCapacityCubm": "1852.0000",
            "density": 0.98,
            "group": 2,
            "order": 1,
            "slopTank": false,
            "commodity": {
              "id": 117184,
              "portId": 359,
              "fuelTypeId": 5,
              "fuelTypeName": "Fuel Oil Tank",
              "fuelTypeShortName": "FOT",
              "tankId": 25614,
              "tankName": "FO2P",
              "arrivalVolume": 0,
              "arrivalQuantity": 0,
              "actualArrivalQuantity": 0,
              "departureVolume": 0,
              "departureQuantity": 0,
              "actualDepartureQuantity": 0,
              "colorCode": "#c60a09",
              "density": 0,
              "portRotationId": 107678,
              "quantity": 0,
              "volume": 0
            }
          },
          {
            "id": 25612,
            "categoryId": 5,
            "categoryName": "Fuel Oil Tank",
            "name": "NO.1  FUEL OIL TANK",
            "frameNumberFrom": "39",
            "frameNumberTo": "49",
            "shortName": "FO1P",
            "fullCapacityCubm": "2490.5000",
            "density": 0.98,
            "group": 2,
            "order": 2,
            "slopTank": false,
            "commodity": {
              "id": 117186,
              "portId": 359,
              "fuelTypeId": 5,
              "fuelTypeName": "Fuel Oil Tank",
              "fuelTypeShortName": "FOT",
              "tankId": 25612,
              "tankName": "FO1P",
              "arrivalVolume": 0,
              "arrivalQuantity": 0,
              "actualArrivalQuantity": 0,
              "departureVolume": 0,
              "departureQuantity": 0,
              "actualDepartureQuantity": 0,
              "colorCode": "#c60a09",
              "density": 0,
              "portRotationId": 107678,
              "quantity": 0,
              "volume": 0
            }
          },
          {
            "id": 26004,
            "categoryId": 22,
            "categoryName": "Fuel Void",
            "name": "FUEL VOID 1",
            "frameNumberFrom": "15",
            "frameNumberTo": "49",
            "shortName": "VOID",
            "group": 2,
            "order": 3,
            "slopTank": false
          },
          {
            "id": 26005,
            "categoryId": 22,
            "categoryName": "Fuel Void",
            "name": "FUEL VOID 2",
            "frameNumberFrom": "15",
            "frameNumberTo": "43",
            "shortName": "VOID",
            "group": 2,
            "order": 4,
            "slopTank": false
          },
          {
            "id": 25616,
            "categoryId": 5,
            "categoryName": "Fuel Oil Tank",
            "name": "H.F.O.SERV. TANK",
            "frameNumberFrom": "43",
            "frameNumberTo": "47",
            "shortName": "HFOSRV",
            "fullCapacityCubm": "66.8000",
            "density": 0.98,
            "group": 2,
            "order": 5,
            "slopTank": false,
            "commodity": {
              "id": 117188,
              "portId": 359,
              "fuelTypeId": 5,
              "fuelTypeName": "Fuel Oil Tank",
              "fuelTypeShortName": "FOT",
              "tankId": 25616,
              "tankName": "HFOSRV",
              "arrivalVolume": 0,
              "arrivalQuantity": 0,
              "actualArrivalQuantity": 0,
              "departureVolume": 0,
              "departureQuantity": 0,
              "actualDepartureQuantity": 0,
              "colorCode": "#c60a09",
              "density": 0,
              "portRotationId": 107678,
              "quantity": 0,
              "volume": 0
            }
          },
          {
            "id": 25625,
            "categoryId": 6,
            "categoryName": "Diesel Oil Tank",
            "name": "NO.2 D.O.SERV.TANK",
            "frameNumberFrom": "22",
            "frameNumberTo": "24",
            "shortName": "DOSRV2",
            "fullCapacityCubm": "27.4000",
            "density": 0.85,
            "group": 2,
            "order": 6,
            "slopTank": false,
            "commodity": {
              "id": 117191,
              "portId": 359,
              "fuelTypeId": 6,
              "fuelTypeName": "Diesel Oil Tank",
              "fuelTypeShortName": "DOT",
              "tankId": 25625,
              "tankName": "DOSRV2",
              "arrivalVolume": 0,
              "arrivalQuantity": 0,
              "actualArrivalQuantity": 0,
              "departureVolume": 0,
              "departureQuantity": 0,
              "actualDepartureQuantity": 0,
              "colorCode": "#fa8424",
              "density": 0,
              "portRotationId": 107678,
              "quantity": 0,
              "volume": 0
            }
          },
          {
            "id": 25624,
            "categoryId": 6,
            "categoryName": "Diesel Oil Tank",
            "name": "NO.1 D.O. SERV.TANK",
            "frameNumberFrom": "24",
            "frameNumberTo": "26",
            "shortName": "DOSRV1",
            "fullCapacityCubm": "32.4000",
            "density": 0.85,
            "group": 2,
            "order": 7,
            "slopTank": false,
            "commodity": {
              "id": 116909,
              "portId": 359,
              "fuelTypeId": 6,
              "fuelTypeName": "Diesel Oil Tank",
              "fuelTypeShortName": "DOT",
              "tankId": 25624,
              "tankName": "DOSRV1",
              "arrivalVolume": 25,
              "arrivalQuantity": 20,
              "actualArrivalQuantity": 0,
              "departureVolume": 25,
              "departureQuantity": 20,
              "actualDepartureQuantity": 0,
              "colorCode": "#fa8424",
              "density": 0.8,
              "portRotationId": 107678,
              "quantity": 0,
              "volume": 0
            }
          },
          {
            "id": 25617,
            "categoryId": 5,
            "categoryName": "Fuel Oil Tank",
            "name": "H.F.O.SETT. TANK",
            "frameNumberFrom": "39",
            "frameNumberTo": "43",
            "shortName": "HFOSET",
            "fullCapacityCubm": "64.1000",
            "density": 0.98,
            "group": 2,
            "order": 8,
            "slopTank": false,
            "commodity": {
              "id": 117189,
              "portId": 359,
              "fuelTypeId": 5,
              "fuelTypeName": "Fuel Oil Tank",
              "fuelTypeShortName": "FOT",
              "tankId": 25617,
              "tankName": "HFOSET",
              "arrivalVolume": 0,
              "arrivalQuantity": 0,
              "actualArrivalQuantity": 0,
              "departureVolume": 0,
              "departureQuantity": 0,
              "actualDepartureQuantity": 0,
              "colorCode": "#c60a09",
              "density": 0,
              "portRotationId": 107678,
              "quantity": 0,
              "volume": 0
            }
          },
          {
            "id": 25623,
            "categoryId": 6,
            "categoryName": "Diesel Oil Tank",
            "name": "NO.2 DIESEL OIL TANK",
            "frameNumberFrom": "15",
            "frameNumberTo": "22",
            "shortName": "DO2S",
            "fullCapacityCubm": "278.1000",
            "density": 0.85,
            "group": 2,
            "order": 9,
            "slopTank": false,
            "commodity": {
              "id": 117194,
              "portId": 359,
              "fuelTypeId": 6,
              "fuelTypeName": "Diesel Oil Tank",
              "fuelTypeShortName": "DOT",
              "tankId": 25623,
              "tankName": "DO2S",
              "arrivalVolume": 0,
              "arrivalQuantity": 0,
              "actualArrivalQuantity": 0,
              "departureVolume": 0,
              "departureQuantity": 0,
              "actualDepartureQuantity": 0,
              "colorCode": "#fa8424",
              "density": 0,
              "portRotationId": 107678,
              "quantity": 0,
              "volume": 0
            }
          },
          {
            "id": 25622,
            "categoryId": 6,
            "categoryName": "Diesel Oil Tank",
            "name": "NO.1 DIESEL OIL TANK",
            "frameNumberFrom": "22",
            "frameNumberTo": "26",
            "shortName": "DO1S",
            "fullCapacityCubm": "175.2000",
            "density": 0.85,
            "group": 2,
            "order": 10,
            "slopTank": false,
            "commodity": {
              "id": 117192,
              "portId": 359,
              "fuelTypeId": 6,
              "fuelTypeName": "Diesel Oil Tank",
              "fuelTypeShortName": "DOT",
              "tankId": 25622,
              "tankName": "DO1S",
              "arrivalVolume": 0,
              "arrivalQuantity": 0,
              "actualArrivalQuantity": 0,
              "departureVolume": 0,
              "departureQuantity": 0,
              "actualDepartureQuantity": 0,
              "colorCode": "#fa8424",
              "density": 0,
              "portRotationId": 107678,
              "quantity": 0,
              "volume": 0
            }
          },
          {
            "id": 25619,
            "categoryId": 5,
            "categoryName": "Fuel Oil Tank",
            "name": "BOILER F.O.SERV.TANK",
            "frameNumberFrom": "34",
            "frameNumberTo": "39",
            "shortName": "BFOSRV",
            "fullCapacityCubm": "67.2000",
            "density": 0.98,
            "group": 2,
            "order": 11,
            "slopTank": false,
            "commodity": {
              "id": 116911,
              "portId": 359,
              "fuelTypeId": 5,
              "fuelTypeName": "Fuel Oil Tank",
              "fuelTypeShortName": "FOT",
              "tankId": 25619,
              "tankName": "BFOSRV",
              "arrivalVolume": 25,
              "arrivalQuantity": 20,
              "actualArrivalQuantity": 0,
              "departureVolume": 25,
              "departureQuantity": 20,
              "actualDepartureQuantity": 0,
              "colorCode": "#c60a09",
              "density": 0.8,
              "portRotationId": 107678,
              "quantity": 0,
              "volume": 0
            }
          },
          {
            "id": 25615,
            "categoryId": 5,
            "categoryName": "Fuel Oil Tank",
            "name": "NO.2  FUEL OIL TANK",
            "frameNumberFrom": "26",
            "frameNumberTo": "39",
            "shortName": "FO2S",
            "fullCapacityCubm": "1259.2000",
            "density": 0.98,
            "group": 2,
            "order": 12,
            "slopTank": false,
            "commodity": {
              "id": 117185,
              "portId": 359,
              "fuelTypeId": 5,
              "fuelTypeName": "Fuel Oil Tank",
              "fuelTypeShortName": "FOT",
              "tankId": 25615,
              "tankName": "FO2S",
              "arrivalVolume": 0,
              "arrivalQuantity": 0,
              "actualArrivalQuantity": 0,
              "departureVolume": 0,
              "departureQuantity": 0,
              "actualDepartureQuantity": 0,
              "colorCode": "#c60a09",
              "density": 0,
              "portRotationId": 107678,
              "quantity": 0,
              "volume": 0
            }
          },
          {
            "id": 25613,
            "categoryId": 5,
            "categoryName": "Fuel Oil Tank",
            "name": "NO.1  FUEL OIL TANK",
            "frameNumberFrom": "39",
            "frameNumberTo": "49",
            "shortName": "FO1S",
            "fullCapacityCubm": "2154.3000",
            "density": 0.98,
            "group": 2,
            "order": 13,
            "slopTank": false,
            "commodity": {
              "id": 117187,
              "portId": 359,
              "fuelTypeId": 5,
              "fuelTypeName": "Fuel Oil Tank",
              "fuelTypeShortName": "FOT",
              "tankId": 25613,
              "tankName": "FO1S",
              "arrivalVolume": 0,
              "arrivalQuantity": 0,
              "actualArrivalQuantity": 0,
              "departureVolume": 0,
              "departureQuantity": 0,
              "actualDepartureQuantity": 0,
              "colorCode": "#c60a09",
              "density": 0,
              "portRotationId": 107678,
              "quantity": 0,
              "volume": 0
            }
          }
        ]
      ],
      "bunkerRearTanks": [
        [
          {
            "id": 25636,
            "categoryId": 3,
            "categoryName": "Fresh Water Tank",
            "name": "DRINKING WATER TANK",
            "frameNumberFrom": "5",
            "frameNumberTo": "13",
            "shortName": "DRWT",
            "fullCapacityCubm": "369.4000",
            "density": 1,
            "group": 1,
            "order": 1,
            "slopTank": false,
            "commodity": {
              "id": 116910,
              "portId": 359,
              "fuelTypeId": 3,
              "fuelTypeName": "Fresh Water Tank",
              "fuelTypeShortName": "FWT",
              "tankId": 25636,
              "tankName": "DRWT",
              "arrivalVolume": 200,
              "arrivalQuantity": 200,
              "actualArrivalQuantity": 0,
              "departureVolume": 200,
              "departureQuantity": 200,
              "actualDepartureQuantity": 0,
              "colorCode": "#5aa1a3",
              "density": 1,
              "portRotationId": 107678,
              "quantity": 0,
              "volume": 0
            }
          },
          {
            "id": 25638,
            "categoryId": 3,
            "categoryName": "Fresh Water Tank",
            "name": "DIST.WATER TANK",
            "frameNumberFrom": "13",
            "frameNumberTo": "15",
            "shortName": "DSWTP",
            "fullCapacityCubm": "117.0000",
            "density": 1,
            "group": 1,
            "order": 2,
            "slopTank": false,
            "commodity": {
              "id": 117198,
              "portId": 359,
              "fuelTypeId": 3,
              "fuelTypeName": "Fresh Water Tank",
              "fuelTypeShortName": "FWT",
              "tankId": 25638,
              "tankName": "DSWTP",
              "arrivalVolume": 0,
              "arrivalQuantity": 0,
              "actualArrivalQuantity": 0,
              "departureVolume": 0,
              "departureQuantity": 0,
              "actualDepartureQuantity": 0,
              "colorCode": "#5aa1a3",
              "density": 0,
              "portRotationId": 107678,
              "quantity": 0,
              "volume": 0
            }
          },
          {
            "id": 26003,
            "categoryId": 23,
            "categoryName": "Fresh Tank Void",
            "name": "APT",
            "frameNumberFrom": "0",
            "frameNumberTo": "15",
            "shortName": "APT",
            "group": 1,
            "order": 3,
            "slopTank": false
          },
          {
            "id": 25639,
            "categoryId": 3,
            "categoryName": "Fresh Water Tank",
            "name": "DIST.WATER TANK",
            "frameNumberFrom": "13",
            "frameNumberTo": "15",
            "shortName": "DSWTS",
            "fullCapacityCubm": "117.0000",
            "density": 1,
            "group": 1,
            "order": 4,
            "slopTank": false,
            "commodity": {
              "id": 117199,
              "portId": 359,
              "fuelTypeId": 3,
              "fuelTypeName": "Fresh Water Tank",
              "fuelTypeShortName": "FWT",
              "tankId": 25639,
              "tankName": "DSWTS",
              "arrivalVolume": 0,
              "arrivalQuantity": 0,
              "actualArrivalQuantity": 0,
              "departureVolume": 0,
              "departureQuantity": 0,
              "actualDepartureQuantity": 0,
              "colorCode": "#5aa1a3",
              "density": 0,
              "portRotationId": 107678,
              "quantity": 0,
              "volume": 0
            }
          },
          {
            "id": 25637,
            "categoryId": 3,
            "categoryName": "Fresh Water Tank",
            "name": "FRESH WATER TANK",
            "frameNumberFrom": "5",
            "frameNumberTo": "13",
            "shortName": "FRWT",
            "fullCapacityCubm": "369.4000",
            "density": 1,
            "group": 1,
            "order": 5,
            "slopTank": false,
            "commodity": {
              "id": 117196,
              "portId": 359,
              "fuelTypeId": 3,
              "fuelTypeName": "Fresh Water Tank",
              "fuelTypeShortName": "FWT",
              "tankId": 25637,
              "tankName": "FRWT",
              "arrivalVolume": 0,
              "arrivalQuantity": 0,
              "actualArrivalQuantity": 0,
              "departureVolume": 0,
              "departureQuantity": 0,
              "actualDepartureQuantity": 0,
              "colorCode": "#5aa1a3",
              "density": 0,
              "portRotationId": 107678,
              "quantity": 0,
              "volume": 0
            }
          }
        ]
      ],
      "ballastFrontTanks": [
        [
          {
            "id": 25611,
            "categoryId": 2,
            "categoryName": "Water Ballast Tank",
            "name": "FORE PEAK TANK(PORT USE)",
            "frameNumberFrom": "103",
            "frameNumberTo": "FE",
            "shortName": "FPTU",
            "fullCapacityCubm": "5156.5000",
            "density": 1.025,
            "group": 0,
            "order": 1,
            "slopTank": false,
            "commodity": {
              "tankId": 25611,
              "tankName": "FPTU",
              "actualWeight": 0,
              "plannedWeight": 0,
              "capacity": 5156.5,
              "abbreviation": null,
              "cargoId": null,
              "colorCode": "",
              "correctedUllage": 0,
              "api": null,
              "sg": "",
              "isCommingleCargo": null,
              "volume": 0,
              "percentageFilled": "0.00"
            }
          },
          {
            "id": 25597,
            "categoryId": 2,
            "categoryName": "Water Ballast Tank",
            "name": "FORE PEAK TANK",
            "frameNumberFrom": "103",
            "frameNumberTo": "FE",
            "shortName": "FPTL",
            "fullCapacityCubm": "5444.2000",
            "density": 1.025,
            "group": 0,
            "order": 2,
            "slopTank": false,
            "commodity": {
              "tankId": 25597,
              "tankName": "FPTL",
              "actualWeight": 0,
              "plannedWeight": 0,
              "capacity": 5444.2,
              "abbreviation": null,
              "cargoId": null,
              "colorCode": "",
              "correctedUllage": 0,
              "api": null,
              "sg": "",
              "isCommingleCargo": null,
              "volume": 0,
              "percentageFilled": "0.00"
            }
          }
        ]
      ],
      "ballastCenterTanks": [
        [
          {
            "id": 25608,
            "categoryId": 2,
            "categoryName": "Water Ballast Tank",
            "name": "AFT WATER BALLAST TANK",
            "frameNumberFrom": "15",
            "frameNumberTo": "39",
            "shortName": "AWBP",
            "fullCapacityCubm": "1024.9000",
            "density": 1.025,
            "group": 0,
            "order": 1,
            "slopTank": false,
            "commodity": {
              "tankId": 25608,
              "tankName": "AWBP",
              "actualWeight": 0,
              "plannedWeight": 0,
              "capacity": 1024.9,
              "abbreviation": null,
              "cargoId": null,
              "colorCode": "",
              "correctedUllage": 0,
              "api": null,
              "sg": "",
              "isCommingleCargo": null,
              "volume": 0,
              "percentageFilled": "0.00"
            }
          },
          {
            "id": 25606,
            "categoryId": 2,
            "categoryName": "Water Ballast Tank",
            "name": "NO.5 WATER BALLAST TANK",
            "frameNumberFrom": "49",
            "frameNumberTo": "61",
            "shortName": "WB5P",
            "fullCapacityCubm": "8561.9000",
            "density": 1.025,
            "group": 0,
            "order": 3,
            "slopTank": false,
            "commodity": {
              "tankId": 25606,
              "tankName": "WB5P",
              "actualWeight": 0,
              "plannedWeight": 0,
              "capacity": 8561.9,
              "abbreviation": null,
              "cargoId": null,
              "colorCode": "",
              "correctedUllage": 0,
              "api": null,
              "sg": "",
              "isCommingleCargo": null,
              "volume": 0,
              "percentageFilled": "0.00"
            }
          },
          {
            "id": 25604,
            "categoryId": 2,
            "categoryName": "Water Ballast Tank",
            "name": "NO.4 WATER BALLAST TANK",
            "frameNumberFrom": "61",
            "frameNumberTo": "71",
            "shortName": "WB4P",
            "fullCapacityCubm": "8743.7000",
            "density": 1.025,
            "group": 0,
            "order": 4,
            "slopTank": false,
            "commodity": {
              "tankId": 25604,
              "tankName": "WB4P",
              "actualWeight": 0,
              "plannedWeight": 0,
              "capacity": 8743.7,
              "abbreviation": null,
              "cargoId": null,
              "colorCode": "",
              "correctedUllage": 0,
              "api": null,
              "sg": "",
              "isCommingleCargo": null,
              "volume": 0,
              "percentageFilled": "0.00"
            }
          },
          {
            "id": 25602,
            "categoryId": 2,
            "categoryName": "Water Ballast Tank",
            "name": "NO.3 WATER BALLAST TANK",
            "frameNumberFrom": "71",
            "frameNumberTo": "81",
            "shortName": "WB3P",
            "fullCapacityCubm": "8875.8000",
            "density": 1.025,
            "group": 0,
            "order": 5,
            "slopTank": false,
            "commodity": {
              "tankId": 25602,
              "tankName": "WB3P",
              "actualWeight": 0,
              "plannedWeight": 0,
              "capacity": 8875.8,
              "abbreviation": null,
              "cargoId": null,
              "colorCode": "",
              "correctedUllage": 0,
              "api": null,
              "sg": "",
              "isCommingleCargo": null,
              "volume": 0,
              "percentageFilled": "0.00"
            }
          },
          {
            "id": 25600,
            "categoryId": 2,
            "categoryName": "Water Ballast Tank",
            "name": "NO.2 WATER BALLAST TANK",
            "frameNumberFrom": "81",
            "frameNumberTo": "91",
            "shortName": "WB2P",
            "fullCapacityCubm": "8873.6000",
            "density": 1.025,
            "group": 0,
            "order": 6,
            "slopTank": false,
            "commodity": {
              "tankId": 25600,
              "tankName": "WB2P",
              "actualWeight": 0,
              "plannedWeight": 0,
              "capacity": 8873.6,
              "abbreviation": null,
              "cargoId": null,
              "colorCode": "",
              "correctedUllage": 0,
              "api": null,
              "sg": "",
              "isCommingleCargo": null,
              "volume": 0,
              "percentageFilled": "0.00"
            }
          },
          {
            "id": 25598,
            "categoryId": 2,
            "categoryName": "Water Ballast Tank",
            "name": "NO.1 WATER BALLAST TANK",
            "frameNumberFrom": "91",
            "frameNumberTo": "103",
            "shortName": "WB1P",
            "fullCapacityCubm": "8906.5000",
            "density": 1.025,
            "group": 0,
            "order": 7,
            "slopTank": false,
            "commodity": {
              "tankId": 25598,
              "tankName": "WB1P",
              "actualWeight": 0,
              "plannedWeight": 0,
              "capacity": 8906.5,
              "abbreviation": null,
              "cargoId": null,
              "colorCode": "",
              "correctedUllage": 0,
              "api": null,
              "sg": "",
              "isCommingleCargo": null,
              "volume": 0,
              "percentageFilled": "0.00"
            }
          },
          {
            "id": 25609,
            "categoryId": 2,
            "categoryName": "Water Ballast Tank",
            "name": "AFT WATER BALLAST TANK",
            "frameNumberFrom": "15",
            "frameNumberTo": "39",
            "shortName": "AWBS",
            "fullCapacityCubm": "1024.9000",
            "density": 1.025,
            "group": 0,
            "order": 8,
            "slopTank": false,
            "commodity": {
              "tankId": 25609,
              "tankName": "AWBS",
              "actualWeight": 0,
              "plannedWeight": 0,
              "capacity": 1024.9,
              "abbreviation": null,
              "cargoId": null,
              "colorCode": "",
              "correctedUllage": 0,
              "api": null,
              "sg": "",
              "isCommingleCargo": null,
              "volume": 0,
              "percentageFilled": "0.00"
            }
          },
          {
            "id": 25607,
            "categoryId": 2,
            "categoryName": "Water Ballast Tank",
            "name": "NO.5 WATER BALLAST TANK",
            "frameNumberFrom": "49",
            "frameNumberTo": "61",
            "shortName": "WB5S",
            "fullCapacityCubm": "8560.4000",
            "density": 1.025,
            "group": 0,
            "order": 10,
            "slopTank": false,
            "commodity": {
              "tankId": 25607,
              "tankName": "WB5S",
              "actualWeight": 0,
              "plannedWeight": 0,
              "capacity": 8560.4,
              "abbreviation": null,
              "cargoId": null,
              "colorCode": "",
              "correctedUllage": 0,
              "api": null,
              "sg": "",
              "isCommingleCargo": null,
              "volume": 0,
              "percentageFilled": "0.00"
            }
          },
          {
            "id": 25605,
            "categoryId": 2,
            "categoryName": "Water Ballast Tank",
            "name": "NO.4 WATER BALLAST TANK",
            "frameNumberFrom": "61",
            "frameNumberTo": "71",
            "shortName": "WB4S",
            "fullCapacityCubm": "8741.7000",
            "density": 1.025,
            "group": 0,
            "order": 11,
            "slopTank": false,
            "commodity": {
              "tankId": 25605,
              "tankName": "WB4S",
              "actualWeight": 0,
              "plannedWeight": 0,
              "capacity": 8741.7,
              "abbreviation": null,
              "cargoId": null,
              "colorCode": "",
              "correctedUllage": 0,
              "api": null,
              "sg": "",
              "isCommingleCargo": null,
              "volume": 0,
              "percentageFilled": "0.00"
            }
          },
          {
            "id": 25603,
            "categoryId": 2,
            "categoryName": "Water Ballast Tank",
            "name": "NO.3 WATER BALLAST TANK",
            "frameNumberFrom": "71",
            "frameNumberTo": "81",
            "shortName": "WB3S",
            "fullCapacityCubm": "8873.8000",
            "density": 1.025,
            "group": 0,
            "order": 12,
            "slopTank": false,
            "commodity": {
              "tankId": 25603,
              "tankName": "WB3S",
              "actualWeight": 0,
              "plannedWeight": 0,
              "capacity": 8873.8,
              "abbreviation": null,
              "cargoId": null,
              "colorCode": "",
              "correctedUllage": 0,
              "api": null,
              "sg": "",
              "isCommingleCargo": null,
              "volume": 0,
              "percentageFilled": "0.00"
            }
          },
          {
            "id": 25601,
            "categoryId": 2,
            "categoryName": "Water Ballast Tank",
            "name": "NO.2 WATER BALLAST TANK",
            "frameNumberFrom": "81",
            "frameNumberTo": "91",
            "shortName": "WB2S",
            "fullCapacityCubm": "8871.5000",
            "density": 1.025,
            "group": 0,
            "order": 13,
            "slopTank": false,
            "commodity": {
              "tankId": 25601,
              "tankName": "WB2S",
              "actualWeight": 0,
              "plannedWeight": 0,
              "capacity": 8871.5,
              "abbreviation": null,
              "cargoId": null,
              "colorCode": "",
              "correctedUllage": 0,
              "api": null,
              "sg": "",
              "isCommingleCargo": null,
              "volume": 0,
              "percentageFilled": "0.00"
            }
          },
          {
            "id": 25599,
            "categoryId": 2,
            "categoryName": "Water Ballast Tank",
            "name": "NO.1 WATER BALLAST TANK",
            "frameNumberFrom": "91",
            "frameNumberTo": "103",
            "shortName": "WB1S",
            "fullCapacityCubm": "8904.4000",
            "density": 1.025,
            "group": 0,
            "order": 14,
            "slopTank": false,
            "commodity": {
              "tankId": 25599,
              "tankName": "WB1S",
              "actualWeight": 0,
              "plannedWeight": 0,
              "capacity": 8904.4,
              "abbreviation": null,
              "cargoId": null,
              "colorCode": "",
              "correctedUllage": 0,
              "api": null,
              "sg": "",
              "isCommingleCargo": null,
              "volume": 0,
              "percentageFilled": "0.00"
            }
          }
        ]
      ],
      "ballastRearTanks": [
        [
          {
            "id": 25610,
            "categoryId": 2,
            "categoryName": "Water Ballast Tank",
            "name": "AFT PEAK TANK",
            "frameNumberFrom": "AE",
            "frameNumberTo": "15",
            "shortName": "APT",
            "fullCapacityCubm": "2574.4000",
            "density": 1.025,
            "group": 0,
            "order": 1,
            "slopTank": false,
            "commodity": {
              "tankId": 25610,
              "tankName": "APT",
              "actualWeight": 0,
              "plannedWeight": 0,
              "capacity": 2574.4,
              "abbreviation": null,
              "cargoId": null,
              "colorCode": "",
              "correctedUllage": 0,
              "api": null,
              "sg": "",
              "isCommingleCargo": null,
              "volume": 0,
              "percentageFilled": "0.00"
            }
          }
        ]
      ],
      "cargoConditions": [
        {
          "id": 0,
          "plannedWeight": 0,
          "actualWeight": 0
        }
      ],
      "stabilityConditions": {
        "list": 0,
        "deflection": 0,
        "finalDraftFwd": 0,
        "finalDraftAft": 0,
        "finalDraftMid": 0,
        "calculatedDraftFwdActual": 0,
        "calculatedDraftAftActual": 0,
        "calculatedDraftMidActual": 0,
        "calculatedTrimActual": 0
      },
      "bunkerConditions": {
        "dieselOilWeight": 0,
        "fuelOilWeight": 0,
        "ballastWeight": 0,
        "freshWaterWeight": 0,
        "othersWeight": 0,
        "totalDwtWeight": 0,
        "displacement": 0,
        "specificGravity": 1.025
      }
    };
    this.loadablePatternCargoDetails = [
      {"priority":1,"cargoAbbreviation":"AEL","cargoColor":"#b82121","quantity":"105000.0","isCommingle":false,"loadablePatternCommingleDetailsId":0,"orderedQuantity":"100000.0000","loadingOrder":1,"api":"40.0000","tankName":"","tankId":null,"fillingRatio":null,"temperature":"102.0000",
      "cargo": [
        {
          cargoName: 'KU',
          cargoDetails: [
            {
              blRefNo: null,
              cargoName: 'KU',
              bbl: 5000,
              lt: 100000,
              mt: 100000,
              kl: 100000,
              api: 30.00,
              temp: 31.9
            },
            {
              blRefNo: null,
              cargoName: 'KU',
              bbl: 5000,
              lt: 29000,
              mt: 31954,
              kl: 58988,
              api: 8.60,
              temp: 56.0
            }
          ]
        },
      ]
      },
      {"priority":1,"cargoAbbreviation":"KWE","cargoColor":"#2158b8","quantity":"105000.0","isCommingle":false,"loadablePatternCommingleDetailsId":0,"orderedQuantity":"100000.0000","loadingOrder":2,"api":"30.0000","tankName":"","tankId":null,"fillingRatio":null,"temperature":"102.0000",
      "cargo": [
        {
          cargoName: 'KU',
          cargoDetails: [
            {
              blRefNo: null,
              bbl: 5000,
              lt: 100000,
              mt: 100000,
              kl: 100000,
              api: 30.00,
              temp: 31.9,
              cargoName: 'KU',
            },
            {
              blRefNo: null,
              bbl: 5000,
              lt: 29000,
              mt: 31954,
              kl: 58988,
              api: 8.60,
              temp: 56.0,
              cargoName: 'KU',
            }
          ]
        },
      ]}
    ]
    this.cargoColumns = this.ullageUpdatePopupTransformationService.getCargoTableColumn();
    this.ballastColumns = this.ullageUpdatePopupTransformationService.getBallastTankColumns();
    this.bunkerColumns = this.ullageUpdatePopupTransformationService.getBunkerTankColumns();
    this.blFigureColumns = this.ullageUpdatePopupTransformationService.getBLFigureColumns();
    this.selectedCargo = this.loadablePatternCargoDetails[0];
    this.tableForm = this.fb.group({
      items: this.fb.array([])
    })
    this.blFigGrid();

    this.cargoQuantityList = [
      {
        cargoName: 'KU',
        nominationFig: 1000000,
        tolerance: {
          min: -10,
          max: 10
        },
        quantity: {
          max: 1100000,
          min: 900000
        },
        plan: {
          bbl: 1000000,
          lt: 129000,
          mt: 131954,
          kl: 158988,
          api: 38.60,
          temp: 114
        },
        actual: {
          bbl: 1000000,
          lt: 129000,
          mt: 131954,
          kl: 158988,
          api: 38.60,
          temp: 114
        },
        blFigure: {
          bbl: 1000000,
          lt: 129000,
          mt: 131954,
          kl: 158988,
          api: 38.60,
          temp: 114
        },
        diff: {
          bbl: -2000,
          lt: -2000,
          mt: -263.91,
          kl: 317,
        },
        diffPercentage: {
          bbl: -0.20,
          lt: -0.20,
          mt: -0.20,
          kl: -0.20,
        }
      },
      {
        cargoName: 'TU',
        nominationFig: 1000000,
        tolerance: {
          min: -10,
          max: 10
        },
        quantity: {
          max: 1100000,
          min: 900000
        },
        plan: {
          bbl: 1000000,
          lt: 129000,
          mt: 131954,
          kl: 158988,
          api: 38.60,
          temp: 114
        },
        actual: {
          bbl: 1000000,
          lt: 129000,
          mt: 131954,
          kl: 158988,
          api: 38.60,
          temp: 114
        },
        blFigure: {
          bbl: 1000000,
          lt: 129000,
          mt: 131954,
          kl: 158988,
          api: 38.60,
          temp: 114
        },
        diff: {
          bbl: -2000,
          lt: -2000,
          mt: -263.91,
          kl: 317,
        },
        diffPercentage: {
          bbl: -0.20,
          lt: -0.20,
          mt: -0.20,
          kl: -0.20,
        }
      }
    ];
    this.getShipLandingTanks();
  }

  closeDialog() {
    this.closePopup.emit(true);
  }

  /**
   * Handler for tab click
   *
   * @param {TANKTYPE} selectedTab
   * @memberof UllageUpdatePopupComponent
   */
  onTabClick(selectedTab: TANKTYPE) {
    this.selectedTab = selectedTab;
  }


  /**
* initialise tanks data
*
* @memberof UllageUpdatePopupComponent
*/
  async getShipLandingTanks() {
    this.prevQuantitySelectedUnit = AppConfigurationService.settings.baseUnit;
    this.convertQuantityToSelectedUnit();
  }

  /**
  * Convert quantity to selected uint
  *
  * @memberof UllageUpdatePopupComponent
  */
  convertQuantityToSelectedUnit() {
    const mode = this.selectedPortDetails?.operationType === 'ARR' ? OHQ_MODE.ARRIVAL : OHQ_MODE.DEPARTURE;
    this.cargoQuantities = this.voyageStatusResponse?.cargoQuantities ?? [];
    this.ballastQuantities = this.voyageStatusResponse?.ballastQuantities ?? [];
    this.bunkerTanks = this.ullageUpdatePopupTransformationService.formatBunkerTanks(this.voyageStatusResponse?.bunkerTanks, this.voyageStatusResponse?.bunkerQuantities, mode);
    this.rearBunkerTanks = this.ullageUpdatePopupTransformationService.formatBunkerTanks(this.voyageStatusResponse?.bunkerRearTanks, this.voyageStatusResponse?.bunkerQuantities, mode);
    this.cargoTanks = this.ullageUpdatePopupTransformationService.formatCargoTanks(this.voyageStatusResponse?.cargoTanks, this.voyageStatusResponse?.cargoQuantities, this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit);
    this.rearBallastTanks = this.ullageUpdatePopupTransformationService.formatBallastTanks(this.voyageStatusResponse?.ballastRearTanks, this.voyageStatusResponse?.ballastQuantities, this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit);
    this.centerBallastTanks = this.ullageUpdatePopupTransformationService.formatBallastTanks(this.voyageStatusResponse?.ballastCenterTanks, this.voyageStatusResponse?.ballastQuantities, this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit);
    this.frontBallastTanks = this.ullageUpdatePopupTransformationService.formatBallastTanks(this.voyageStatusResponse?.ballastFrontTanks, this.voyageStatusResponse?.ballastQuantities, this.prevQuantitySelectedUnit, this.currentQuantitySelectedUnit);
    this.fuelTypes = [...new Map(this.voyageStatusResponse.bunkerQuantities.map(item => [item['fuelTypeId'], { id: item?.fuelTypeId, name: item?.fuelTypeName, colorCode: item?.colorCode, shortName: item?.fuelTypeShortName }])).values()];
    this.bunkerTanksList = this.bunkerTanks[0];
  }

  /**
   * Is cell visble
   * @param rowData
   * @param {number} index
   * @memberof UllageUpdatePopupComponent
   */
  isCellVisable(index: number, col: any, rowData) {
    if(index !== 0 && col.field === 'cargoName') {
      return false;
    }
    return true
  }

  /**
   * Method to calculate the rowspan of a particular row based on its subheaders
   * @param {number} index
   * @param {number} colIndex
   * @param {number} rowIndex
   * @memberof UllageUpdatePopupComponent
   */
  calculateRowSpan(index: number,colIndex: number, rowIndex: number) {
    if(index === 0 && colIndex === 0) {
      return this.blFigure.items[rowIndex].length;
    }
  }

  /**
  * Method to get the control of a particular field
  * @param {number} colIndex
  * @param {number} rowIndex
  * @param {string} key
  * @returns {AbstractControl}
  * @memberof UllageUpdatePopupComponent
  */
  getControl(rowIndex: number,colIndex: number, key: string): AbstractControl {
    return this.getCargoItems(rowIndex).at(colIndex).get(key);
  }

  /**
   * Get field errors
   *
   * @param {number} formGroupIndex
   * @param {string} formControlName
   * @returns {ValidationErrors}
   * @memberof UllageUpdatePopupComponent
  */
  fieldError(rowIndex: number,colIndex: number, key: string): ValidationErrors {
    const formControl = this.getControl(rowIndex,colIndex,key);
    return formControl?.invalid && (formControl?.dirty || formControl?.touched) ? formControl.errors : null;
  }

  /**
   * Method to change selected Cargo
   * @param {*} data
   * @memberof UllageUpdatePopupComponent
  */
  changeCargo(data: any) {
    this.selectedCargo = data.value;
    this.blFigGrid();
  }

  /**
   * Method for creating form array for B/l grid
   *
   * @memberof UllageUpdatePopupComponent
  */
  blFigGrid() {
    this.blFigure['items'] = [];
   this.loadablePatternCargoDetails.forEach((cargoDetails) => {
    const cargos = cargoDetails.cargo;
    let cargoItem = [];
    cargos.map((cargo) => {
      const cargoInfo = cargo.cargoDetails;
      cargoItem = cargoInfo.map((cargoDetail) => {
        return {'cargo': this.ullageUpdatePopupTransformationService.getFormatedCargoDetails(cargoDetail, false)};
      })
    })
    this.blFigure['items'].push(cargoItem)
    })
    this.blFigure.items.forEach((blItems, blRowIndex) => {
      const  items = this.tableForm.get('items') as FormArray;
      items.push(this.initItems())
      blItems.forEach((cargoDetails, index) => {
        this.addCargoItems(blRowIndex, cargoDetails.cargo);
      });
    })
    this.calculateTotal();
  }

  /**
   * Method to get Items
   * @memberof UllageUpdatePopupComponent
  */
  getItems(): FormArray {
    return this.tableForm.get('items') as FormArray;
  }

  /**
   * Add new Cargo
   * @param {number} rowIndex
   * @param {number} colIndex
   * @memberof UllageUpdatePopupComponent
  */
  newCargo(rowIndex: number,colIndex: number) {
    this.blFigure.items[rowIndex].push({ cargo : this.ullageUpdatePopupTransformationService.getFormatedCargoDetails(<ICargoDetail>{}, true , false)});
    this.getCargoItems(rowIndex).push(this.initCargoDetails(this.blFigure.items[rowIndex][this.blFigure.items[rowIndex].length-1].cargo));
  }

  /**
   * Delete Cargo
   * @param {number} rowIndex
   * @param {number} colIndex
   * @memberof UllageUpdatePopupComponent
  */
  deleteCargo(rowIndex: number, colIndex: number) {
    this.blFigure.items[rowIndex].splice(colIndex,1)
    this.getCargoItems(rowIndex).removeAt(colIndex);
    this.calculateTotal();
  }

  /**
   * Method to get cargo from form array
   * @param {number} cargoIndex
   * @memberof UllageUpdatePopupComponent
  */
  getCargoItems(cargoIndex: number): FormArray {
    return this.getItems()
      .at(cargoIndex)
      .get('cargos') as FormArray;
  }

  /**
   * Method to add cargo
   * @param {number} cargoIndex
   * @memberof UllageUpdatePopupComponent
  */
  addCargoItems(cargoIndex: number,cargo) {
    this.getCargoItems(cargoIndex).push(this.initCargoDetails(cargo));
  }

  /**
   *  Initialize cargo  form group
   * @memberof UllageUpdatePopupComponent
  */
  initItems(): FormGroup {
    return this.fb.group({
      cargos: this.fb.array([])
    });
  }

  /**
   * Initialize cargo details group
   * @param {*} cargo
   * @memberof UllageUpdatePopupComponent
  */
  initCargoDetails(cargo: any): FormGroup {
    return this.fb.group({
      blRefNo: this.fb.control(cargo.blRefNo.value, [Validators.required]),
      bbl: this.fb.control(cargo.bbl.value, [Validators.required]),
      lt: this.fb.control(cargo.lt.value, [Validators.required]),
      mt: this.fb.control(cargo.mt.value, [Validators.required]),
      kl: this.fb.control(cargo.kl.value, [Validators.required]),
      api: this.fb.control(cargo.api.value, [Validators.required, Validators.min(0) , numberValidator(2, 3)]),
      temp: this.fb.control(cargo.temp.value, [Validators.required , numberValidator(2, 3)])
    });
  }

  /**
   * Calculate  Total for B/L figure
   * @memberof UllageUpdatePopupComponent
  */
  calculateTotal() {
    this.blFigureTotal = <IBlFigureTotal>{ lt: 0, mt: 0,api: 0, kl: 0, bbl: 0};
    this.blFigure.items.forEach((blItems, blRowIndex) => {
      blItems.forEach((cargoDetails, index) => {
        this.blFigureTotal.lt += cargoDetails.cargo.lt.value ? Number(cargoDetails.cargo.lt.value) : 0;
        this.blFigureTotal.mt += cargoDetails.cargo.mt.value ? Number(cargoDetails.cargo.mt.value) : 0;
        this.blFigureTotal.kl += cargoDetails.cargo.kl.value ? Number(cargoDetails.cargo.kl.value) : 0;
        this.blFigureTotal.bbl += cargoDetails.cargo.bbl.value ? Number(cargoDetails.cargo.bbl.value): 0;
        this.blFigureTotal.api += cargoDetails.cargo.api.value ? Number(cargoDetails.cargo.api.value) : 0;
      });
    })
  }

    /**
  * Handler for cell on click event
  * @param event
  * @param rowData
  * @param rowIndex
  * @param col
  * @param colIndex
  */
  onClick(event, rowData, rowIndex, col: IDataTableColumn) {
    if (rowData[col.field]?.isEditable &&  this.editMode && (col.editable === undefined || col.editable)) {
      rowData[col.field].isEditMode = true;
    }
  }

  /**
  * Handler for blur event
  * @param event
  * @param rowData
  * @param rowIndex
  * @param col
  * @param colIndex
  */
  onEditComplete(event:any, rowData: any, key: string, rowIndex: number,index: number) {
    rowData[key]['value'] = event.target.value;
    const control = this.getControl(rowIndex,index,key);
    if(control.valid && rowData['isAdd']) {
      rowData[key].isEditMode = false;
    }
    this.calculateTotal();
  }
}
