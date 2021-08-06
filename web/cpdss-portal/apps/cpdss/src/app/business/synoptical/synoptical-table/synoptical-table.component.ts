import { Component, OnDestroy, OnInit, Type } from '@angular/core';
import { AbstractControl, FormArray, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { Subject, Subscription } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { DATATABLE_FIELD_TYPE } from '../../../shared/components/datatable/datatable.model';
import { IValidationErrorMessages } from '../../../shared/components/validation-error/validation-error.model';
import { numberValidator } from '../../core/directives/number-validator.directive';
import { ISynopticalRecords, SynopticalColumn, SynopticalDynamicColumn, SynopticField } from '../models/synoptical-table.model';
import { SynopticalApiService } from '../services/synoptical-api.service';
import { SynopticalService } from '../services/synoptical.service';
import * as XLSX from 'xlsx';
import { DatePipe } from '@angular/common';
import { MessageService } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';
import { VOYAGE_STATUS, LOADABLE_STUDY_STATUS, } from '../../core/models/common.model';
import { ISubTotal } from '../../../shared/models/common.model';
import { TimeZoneTransformationService } from '../../../shared/services/time-zone-conversion/time-zone-transformation.service';
import { IDateTimeFormatOptions, ITimeZone } from '../../../shared/models/common.model';
import * as moment from 'moment';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { PermissionsService } from '../../../shared/services/permissions/permissions.service';
import { PERMISSION_ACTION } from '../../../shared/models/common.model';
import { IPermission } from '../../../shared/models/user-profile.model';
import { seaWaterDensityRangeValidator } from '../../core/directives/seawater-density-range-validator.directive';

/**
 * Component class of synoptical table
 *
 * @export
 * @class SynopticalTableComponent
 * @implements {OnInit}
 */
@Component({
  selector: 'cpdss-portal-synoptical-table',
  templateUrl: './synoptical-table.component.html',
  styleUrls: ['./synoptical-table.component.scss']
})
export class SynopticalTableComponent implements OnInit, OnDestroy {
  readonly fieldType = DATATABLE_FIELD_TYPE;

  synopticalRecords: ISynopticalRecords[] = [];
  cols: SynopticalColumn[];
  maxSubRowLevel = 2;
  editDateFormat: string;
  etaEtdPermision: IPermission;
  timeOfSunrisePermission: IPermission;
  timeOfSunsetPermission: IPermission;


  listData = {};
  dynamicColumns: SynopticalDynamicColumn[];
  headerColumns: SynopticalColumn[];
  tableForm: FormArray = new FormArray([]);
  errorMessages: IValidationErrorMessages = {
    'required': 'SYNOPTICAL_REQUIRED',
    'invalid': 'SYNOPTICAL_INVALID',
    'pattern': 'SYNOPTICAL_INVALID',
    'fromMax': 'SYNOPTICAL_FROM_MAX',
    "sunRiseGreater": "SYNOPTICAL_SUNRISE_GREATER",
    "sunSetGreater": "SYNOPTICAL_SUNSET_GREATER",
    'toMin': 'SYNOPTICAL_TO_MIN',
    'timeFromMax': 'SYNOPTICAL_TIME_FROM_MAX',
    'timeToMin': 'SYNOPTICAL_TIME_TO_MIN',
    'etaMin': "SYNOPTICAL_ETA_MIN",
    'etdMin': 'SYNOPTICAL_ETD_MIN',
    'etaMax': 'SYNOPTICAL_ETA_MAX',
    'etdMax': "SYNOPTICAL_ETD_MAX",
    'invalidNumber': 'SYNOPTICAL_INVALID',
    'waterDensityError': 'PORT_WATER_DENSITY_RANGE_ERROR'
  };
  expandedRows = [];
  ngUnsubscribe: Subject<void> = new Subject();
  allColumns: SynopticalColumn[]
  datePipe: DatePipe = new DatePipe('en-US');
  synopticalRecordsCopy: ISynopticalRecords[] = [];
  loadableQuantityValue: number;
  today = new Date();
  globalTimeZones: ITimeZone[];
  vesselLightWeight: number;

  constructor(
    private synoticalApiService: SynopticalApiService,
    private route: ActivatedRoute,
    private ngxSpinner: NgxSpinnerService,
    public synopticalService: SynopticalService,
    private messageService: MessageService,
    private translateService: TranslateService,
    private timeZoneTransformationService: TimeZoneTransformationService,
    private permissionsService: PermissionsService
  ) {
  }

  /**
  * Component lifecycle ngOnit
  *
  * @returns {Promise<void>}
  * @memberof SynopticalTableComponent
  */
  async ngOnInit() {
    this.getPagePermission();
    this.today.setSeconds(0, 0);
    this.initActionSubscriptions();
    this.globalTimeZones = await this.timeZoneTransformationService.getTimeZoneList().toPromise();
    this.synopticalService.onInitCompleted$
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(completed => {
        if (completed) {
          this.route.params
            .pipe(takeUntil(this.ngUnsubscribe))
            .subscribe(async route => {
              if (Number(route.loadableStudyId) !== 0) {
                this.synopticalService.loadableStudyId = Number(route.loadableStudyId);
              }
              if (route.loadablePatternId) {
                this.synopticalService.loadablePatternId = Number(route.loadablePatternId);
              }
              if (this.synopticalService?.selectedLoadableStudy?.status === "Confirmed" && !this.synopticalService?.loadablePatternId) {
                await this.synopticalService.setSelectedLoadableStudy();
              }
              else {
                await this.synopticalService.setSelectedLoadableStudy();
                await this.initData();
              }

            })
        }
      })
    this.editDateFormat = this.timeZoneTransformationService.getMappedConfigurationDateFormat(AppConfigurationService.settings?.dateFormat)
  }


  /**
  * Get page permission
  *
  * @memberof SynopticalComponent
  */
  getPagePermission() {
    this.etaEtdPermision = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['SynopticalTableETA/ETD'], false);
    this.timeOfSunrisePermission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['SynopticalTableTimeOfSunrise'], false);
    this.timeOfSunsetPermission = this.permissionsService.getPermission(AppConfigurationService.settings.permissionMapping['SynopticalTableTimeOfSunset'], false);
  }

  /**
  * Component lifecycle ngOnDestroy
  *
  * @returns {Promise<void>}
  * @memberof SynopticalTableComponent
  */
  ngOnDestroy() {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
    this.synopticalService.showActions = false;
  }


  /**
   * Method to convert  etaEtdPlanned Time and etaActual Time into  Zone based time
   *  @returns {Promise<void>}
   *  @memberof SynopticalTableComponent
  */
  async convertIntoZoneTimeZone(synopticalRecordsTemp: any): Promise<any> {
    if (synopticalRecordsTemp?.length) {
      const dtFormatOpts: IDateTimeFormatOptions = { portLocalFormat: true };
      for (const i in synopticalRecordsTemp) {
        if (Object.prototype.hasOwnProperty.call(synopticalRecordsTemp, i)) {
          this.globalTimeZones.forEach((item) => {
            if (item.id === synopticalRecordsTemp[i].portTimezoneId) {
              dtFormatOpts.portTimeZoneOffset = item.offsetValue;
              dtFormatOpts.portTimeZoneAbbr = item.abbreviation;
              if (synopticalRecordsTemp[i].etaEtdPlanned)
                synopticalRecordsTemp[i].etaEtdPlanned = this.timeZoneTransformationService.formatDateTime(synopticalRecordsTemp[i].etaEtdPlanned, dtFormatOpts);
              if (synopticalRecordsTemp[i].etaEtdActual)
                synopticalRecordsTemp[i].etaEtdActual = this.timeZoneTransformationService.formatDateTime(synopticalRecordsTemp[i].etaEtdActual, dtFormatOpts);
            }
          })
        }
      }

      return synopticalRecordsTemp;
    }
    else {
      return [];
    }
  }


  /**
   * Function to initalize data
   *
   * @returns {Promise<void>}
   * @memberof SynopticalTableComponent
   */
  async initData(): Promise<void> {
    this.ngxSpinner.show();
    this.synopticalService.editMode = false;
    this.initColumns();
    this.initDynamicColumns();
    this.setColumnHeader();
    this.setLoadableQuantity();
    const result = await this.synoticalApiService.getSynopticalTable(this.synopticalService.vesselId, this.synopticalService.voyageId, this.synopticalService.loadableStudyId, this.synopticalService.loadablePatternId)
      .toPromise();
    if (result.responseStatus.status === "200") {
      this.synopticalService.synopticalRecords = await this.convertIntoZoneTimeZone(result.synopticalRecords);
      this.synopticalService.showActions = true;
      const index = this.cols.findIndex(item => item?.header === 'Hogging/Sagging (cm)');
      if (!this.synopticalService?.synopticalRecords[0]?.hasLoadicator && (index || index === 0)) {
        if (index || index === 0) {
          this.cols.splice(index, 1);
        }
      } else if(this.synopticalService?.synopticalRecords[0]?.hasLoadicator && (!index && index !== 0)){
          const indexBefore = this.cols.findIndex(item => item?.header === 'Final Draft (m)');
          if (indexBefore || indexBefore === 0) {
            this.cols.splice(indexBefore, 0, {
              header: "Hogging/Sagging (cm)",
              fields: [
                { key: "deflection" }
              ],
            });
          }
      }
      this.dynamicColumns.forEach(dynamicColumn => {
        this.formatData(dynamicColumn)
        this.addToColumns(dynamicColumn);
      })

      this.initForm();
    }
    this.ngxSpinner.hide();
  }

  /**
   * Method to calculate the rowspan of a particular row based on its subheaders
   * @param rowData
   * @returns {number}
   * @memberof SynopticalTableComponent
   */
  calculateRowSpan(rowData): number {
    if (rowData.subHeaders) {
      let count = 0;
      for (let i = 0; i < rowData.subHeaders.length; i++) {
        count += this.calculateRowSpan(rowData.subHeaders[i])
      }
      return count;
    } else {
      return 1;
    }
  }

  /**
   * Method to append headers
   * @param headers
   * @param rowData
   * @param index
   * @returns {Array}
   * @memberof SynopticalTableComponent
   */
  getRowHeaders(headers, rowData, index) {
    if (index > 0)
      return [];
    const newHeaders = [...headers, rowData]
    return newHeaders;
  }

  /**
   * Method to fill blank headers
   * @param subRowLevel
   * @returns {Array}
   * @memberof SynopticalTableComponent
   */
  getBlanks(subRowLevel) {
    return new Array(this.maxSubRowLevel - subRowLevel)
  }

  /**
   * Method to initialize column data
   *
   * @returns {void}
   * @memberof SynopticalTableComponent
   */
  initColumns() {
    this.cols = [
      { fields: [{ key: 'portName' }], header: 'Ports' },
      {
        fields: [{ key: 'operationType' }],
        header: 'ARR OR DEP',
      },
      {
        fields: [{
          key: 'distance',
          type: this.fieldType.NUMBER,
          validators: ['dddd.dd.+']
        }],
        header: 'Distance (NM)',
        editable: true,
        colSpan: 2,
        betweenPorts: true,
      },
      {
        fields: [{
          key: 'speed',
          type: this.fieldType.NUMBER,
          validators: ['dddd.dd.+']
        }],
        header: 'Speed (NM/Hr)',
        editable: true,
        colSpan: 2,
        betweenPorts: true,
      },
      {
        fields: [{
          key: 'runningHours',
          type: this.fieldType.NUMBER,
          validators: ['ddddddd.dd.+'],
        }],
        header: 'Running Hours',
        editable: true,
        colSpan: 2,
        betweenPorts: true,
      },
      {
        header: 'In Port Hours',
        fields: [{
          key: 'inPortHours',
          type: this.fieldType.NUMBER,
          validators: ['dddd.dd.+']
        }],
        editable: true,
        colSpan: 2
      },
      {
        header: 'ETA/ETD',
        view: this.etaEtdPermision?.view || this.etaEtdPermision?.view === undefined,
        toolTip: "PORT_TIME_ZONE_NOTIFICATION",
        subHeaders: [{
          header: '',
          subHeaders: [
            {
              header: "Plan",
              fields: [{
                key: 'etaEtdPlanned',
                type: this.fieldType.DATETIME,
                minValue: this.today,
                validators: (this.etaEtdPermision?.view || this.etaEtdPermision?.view === undefined) &&
                  (this.etaEtdPermision?.edit || this.etaEtdPermision?.edit === undefined) ? ['required'] : []
              }],
              editable: !this.checkIfConfirmed() && this.checkIfEnableEditMode() && (this.etaEtdPermision?.edit || this.etaEtdPermision?.edit === undefined),
            },
            {
              header: "Actual",
              fields: [{
                key: 'etaEtdActual',
                type: this.fieldType.DATETIME,
                maxValue: this.today,
                validators: ((this.etaEtdPermision?.view || this.etaEtdPermision?.view === undefined) &&
                  (this.etaEtdPermision?.edit || this.etaEtdPermision?.edit === undefined)) ? ['required'] : []
              }],
              editable: this.checkIfConfirmed() && (this.etaEtdPermision?.edit || this.etaEtdPermision?.edit === undefined),
            },
          ]
        }
        ]
      },
      {
        header: 'Time of Sunrise',
        view: this.timeOfSunrisePermission?.view || this.timeOfSunrisePermission?.view === undefined,
        fields: [{
          key: 'timeOfSunrise',
          type: this.fieldType.TIME
        }],
        editable: (this.timeOfSunrisePermission?.edit || this.timeOfSunrisePermission?.edit === undefined),
      },
      {
        header: 'Time of Sunset',
        view: this.timeOfSunsetPermission?.view || this.timeOfSunsetPermission?.view === undefined,
        fields: [{
          key: 'timeOfSunset',
          type: this.fieldType.TIME
        }],
        editable: (this.timeOfSunsetPermission?.edit || this.timeOfSunsetPermission?.edit === undefined),
      },
      {
        header: 'Seawater Specific Gravity (T/m3)',
        fields: [{
          key: 'specificGravity',
          type: this.fieldType.NUMBER,
          validators: ['d.dddd.+', 'seaWaterDensity'],
          numberFormat: AppConfigurationService.settings?.sgNumberFormat
        }],
        editable: true,
      },
      {
        header: 'H.W Tide & Time',
        subHeaders: [{
          header: '',
          subHeaders: [
            {
              header: "H.W Tide (m)",
              fields: [
                {
                  key: 'hwTideFrom',
                  type: this.fieldType.NUMBER,
                  validators: ['required', 'dd.dd.+']
                },
                {
                  key: 'hwTideTo',
                  type: this.fieldType.NUMBER,
                  validators: ['required', 'dd.dd.+']
                }
              ],
              editable: true,
            },
            {
              header: "Time",
              fields: [
                {
                  key: 'hwTideTimeFrom',
                  type: this.fieldType.TIME,
                  validators: ['required']
                },
                {
                  key: 'hwTideTimeTo',
                  type: this.fieldType.TIME,
                  validators: ['required']
                }
              ],
              editable: true,
            },
          ]
        }
        ]
      },
      {
        header: 'L.W Tide & Time',
        subHeaders: [{
          header: '',
          subHeaders: [
            {
              header: "L.W Tide (m)",
              fields: [
                {
                  key: 'lwTideFrom',
                  type: this.fieldType.NUMBER,
                  validators: ['required', 'dd.dd.+']
                },
                {
                  key: 'lwTideTo',
                  type: this.fieldType.NUMBER,
                  validators: ['required', 'dd.dd.+']
                }
              ],
              editable: true,
            },
            {
              header: "Time",
              fields: [
                {
                  key: 'lwTideTimeFrom',
                  type: this.fieldType.TIME,
                  validators: ['required']
                },
                {
                  key: 'lwTideTimeTo',
                  type: this.fieldType.TIME,
                  validators: ['required']
                }
              ],
              editable: true,
            },
          ]
        }
        ]
      },
      {
        header: "Hogging/Sagging (cm)",
        fields: [
          { key: "deflection" }
        ],
      },
      {
        header: "Final Draft (m)",
        expandable: true,
        subHeaders: [
          {
            header: "FWD",
            fields: [{
              key: "finalDraftFwd",
              type: this.fieldType.NUMBER
            }],
            editable: false,
          },
          {
            header: "AFT",
            fields: [{
              key: "finalDraftAft",
              type: this.fieldType.NUMBER
            }],
            editable: false,
          },
          {
            header: "MID",
            fields: [{
              key: "finalDraftMid",
              type: this.fieldType.NUMBER
            }],
            editable: false,
          },
        ],
        expandedFields: [
          {
            header: "Calculated Draft (m)",
            subHeaders: [
              {
                header: "FWD",
                subHeaders: [
                  {
                    header: "Plan",
                    fields: [{
                      key: "calculatedDraftFwdPlanned",
                      type: this.fieldType.NUMBER,
                      numberFormat: AppConfigurationService.settings.quantityNumberFormatMT,
                      validators: ['required', 'dd.dd.-']
                    }],
                    editable: false,
                  },
                  {
                    header: "Actual",
                    fields: [{
                      key: "calculatedDraftFwdActual",
                      type: this.fieldType.NUMBER,
                      numberFormat: AppConfigurationService.settings.quantityNumberFormatMT,
                      validators: ['required', 'dd.dd.-']
                    }],
                    editable: this.checkIfConfirmed(),
                  },
                ]
              },
              {
                header: "AFT",
                subHeaders: [
                  {
                    header: "Plan",
                    fields: [{
                      key: "calculatedDraftAftPlanned",
                      type: this.fieldType.NUMBER,
                      numberFormat: AppConfigurationService.settings.quantityNumberFormatMT,
                      validators: ['required', 'dd.dd.-']
                    }],
                    editable: false,
                  },
                  {
                    header: "Actual",
                    fields: [{
                      key: "calculatedDraftAftActual",
                      type: this.fieldType.NUMBER,
                      numberFormat: AppConfigurationService.settings.quantityNumberFormatMT,
                      validators: ['required', 'dd.dd.-']
                    }],
                    editable: this.checkIfConfirmed(),
                  },
                ]
              },
              {
                header: "MID",
                subHeaders: [
                  {
                    header: "Plan",
                    fields: [{
                      key: "calculatedDraftMidPlanned",
                      type: this.fieldType.NUMBER,
                      numberFormat: AppConfigurationService.settings.quantityNumberFormatMT,
                      validators: ['required', 'dd.dd.-']
                    }],
                    editable: false,
                  },
                  {
                    header: "Actual",
                    fields: [{
                      key: "calculatedDraftMidActual",
                      type: this.fieldType.NUMBER,
                      numberFormat: AppConfigurationService.settings.quantityNumberFormatMT,
                      validators: ['required', 'dd.dd.-']
                    }],
                    editable: this.checkIfConfirmed(),
                  },
                ]
              },
            ]
          },
          {
            header: "Calculated Trim (m)",
            subHeaders: [
              {
                header: "Plan",
                fields: [{
                  key: "calculatedTrimPlanned",
                  type: this.fieldType.NUMBER,
                  numberFormat: AppConfigurationService.settings.quantityNumberFormatMT,
                  validators: ['required', 'dd.dd.-']
                }],
                editable: false,
              },
              {
                header: "Actual",
                fields: [{
                  key: "calculatedTrimActual",
                  type: this.fieldType.NUMBER,
                  numberFormat: AppConfigurationService.settings.quantityNumberFormatMT,
                  validators: ['required', 'dd.dd.-']
                }],
                editable: this.checkIfConfirmed(),
              },
            ]
          }
        ]
      },
      // {  // Temporarily hiding hence the value cannot be provided from ALGO
      //   header: "Blind Sector (m)",
      //   fields: [{
      //     key: "blindSector"
      //   }],
      //   editable: false,
      // },
      {
        header: 'Cargo (MT)',
        dynamicKey: 'cargos',
        expandable: true,
        subHeaders: [
          {
            header: "",
            subHeaders: [
              {
                fields: [{ key: 'cargoPlannedTotal', numberType: 'quantity', unit: 'MT' }],
                header: 'Plan',
              },
              {
                fields: [{ key: 'cargoActualTotal', numberType: 'quantity', unit: 'MT' }],
                header: 'Actual',
              },
            ]
          }
        ],
        expandedFields: []
      },
      {
        header: 'Total F.O (MT)',
        dynamicKey: 'foList',
        expandable: true,
        subHeaders: [
          {
            header: "",
            subHeaders: [
              {
                header: 'Plan',
                fields: [{ key: 'plannedFOTotal', numberType: 'quantity', unit: 'MT' }],
              },
              {
                header: 'Actual',
                fields: [{ key: 'actualFOTotal', numberType: 'quantity', unit: 'MT' }],
              },
            ]
          }
        ],
        expandedFields: []
      },
      {
        header: 'Total D.O (MT)',
        dynamicKey: 'doList',
        expandable: true,
        subHeaders: [
          {
            header: "",
            subHeaders: [
              {
                header: 'Plan',
                fields: [{ key: 'plannedDOTotal', numberType: 'quantity', unit: 'MT' }],
              },
              {
                header: 'Actual',
                fields: [{ key: 'actualDOTotal', numberType: 'quantity', unit: 'MT' }],
              },
            ]
          }
        ],
        expandedFields: []
      },
      {
        header: 'Fresh W.T (MT)',
        dynamicKey: 'fwList',
        expandable: true,
        subHeaders: [
          {
            header: "",
            subHeaders: [
              {
                header: 'Plan',
                fields: [{ key: 'plannedFWTotal', numberType: 'quantity', unit: 'MT' }],
              },
              {
                header: 'Actual',
                fields: [{ key: 'actualFWTotal', numberType: 'quantity', unit: 'MT' }],
              },
            ]
          }
        ],
        expandedFields: []
      },
      {
        header: "Ballast (MT)",
        dynamicKey: 'ballast',
        expandable: true,
        subHeaders: [
          {
            header: "",
            subHeaders: [
              {
                header: "Plan",
                fields: [{
                  key: "ballastPlannedTotal",
                  type: this.fieldType.NUMBER,
                  numberType: 'quantity',
                  unit: 'MT'
                }],
                editable: false
              },
              {
                header: "Actual",
                fields: [{
                  key: "ballastActualTotal",
                  type: this.fieldType.NUMBER,
                  numberType: 'quantity',
                  unit: 'MT'
                }],
                editable: false,
              },
            ]
          }
        ],
        expandedFields: []
      },
      {
        header: 'Others (MT)',
        subHeaders: [{
          header: '',
          subHeaders: [
            {
              header: "Plan",
              fields: [{
                key: 'othersPlanned',
                type: this.fieldType.NUMBER,
                numberType: 'quantity',
                unit: 'MT',
                validators: ['required', 'ddddddd.dd.+']
              }],
              editable: !this.checkIfConfirmed() && this.checkIfEnableEditMode(),
            },
            {
              header: "Actual",
              fields: [{
                key: 'othersActual',
                type: this.fieldType.NUMBER,
                numberType: 'quantity',
                validators: ['required', 'ddddddd.dd.+'],
                unit: 'MT'
              }],
              editable: this.checkIfConfirmed(),
            },
          ]
        }
        ]
      },
      {
        header: 'Constant (MT)',
        subHeaders: [{
          header: '',
          subHeaders: [
            {
              header: "Plan",
              fields: [{
                key: 'constantPlanned',
                type: this.fieldType.NUMBER,
                numberType: 'quantity',
                validators: ['required', 'ddddddd.+'],
                unit: 'MT'
              }],
              editable: !this.checkIfConfirmed() && this.checkIfEnableEditMode(),
            },
            {
              header: "Actual",
              fields: [{
                key: 'constantActual',
                type: this.fieldType.NUMBER,
                numberType: 'quantity',
                validators: ['required', 'ddddddd.+'],
                unit: 'MT'
              }],
              editable: this.checkIfConfirmed(),
            },
          ]
        }
        ]
      },
      {
        header: 'Total DWT (MT)',
        subHeaders: [{
          header: '',
          subHeaders: [
            {
              header: "Plan",
              fields: [{
                key: 'totalDwtPlanned',
                type: this.fieldType.NUMBER,
                numberType: 'quantity',
                validators: ['required', 'ddddddd.+'],
                unit: 'MT'
              }],
              editable: false
            },
            {
              header: "Actual",
              fields: [{
                key: 'totalDwtActual',
                type: this.fieldType.NUMBER,
                numberType: 'quantity',
                validators: ['required', 'ddddddd.+'],
                unit: 'MT'
              }],
              editable: false,
            },
          ]
        }
        ]
      },
      {
        header: 'Displacement (MT)',
        subHeaders: [{
          header: '',
          subHeaders: [
            {
              header: "Plan",
              fields: [{
                key: 'displacementPlanned',
                type: this.fieldType.NUMBER,
                numberType: 'quantity',
                validators: ['required', 'ddddddd.+'],
                unit: 'MT'
              }],
              editable: false
            },
            {
              header: "Actual",
              fields: [{
                key: 'displacementActual',
                type: this.fieldType.NUMBER,
                numberType: 'quantity',
                validators: ['required', 'ddddddd.+'],
                unit: 'MT'
              }],
              editable: false,
            },
          ]
        }
        ]
      },
    ];

  }

  /**
   * Method to initialize dynamic column data
   *
   * @returns {void}
   * @memberof SynopticalTableComponent
   */
  initDynamicColumns() {
    this.dynamicColumns = [
      {
        fieldKey: 'cargos',
        primaryKey: 'tankId',
        headerLabel: 'tankName',
        maxKey: 'capacity',
        subHeaders: [
          {
            fields: [{
              key: 'plannedWeight',
              type: this.fieldType.NUMBER,
              validators: ['required', 'ddddddd.dd.+']
            }],
            header: 'Plan',
            editable: !this.checkIfConfirmed() && this.checkIfEnableEditMode(),
            editableByCondition: true
          },
          {
            header: 'Actual',
            fields: [{
              key: 'actualWeight',
              type: this.fieldType.NUMBER,
              validators: ['required', 'ddddddd.dd.+']
            }],
            editable: this.checkIfConfirmed(),
          },
        ],
      },
      {
        fieldKey: 'foList',
        primaryKey: 'tankId',
        headerLabel: 'tankName',
        maxKey: 'capacity',
        subHeaders: [
          {
            fields: [{
              key: 'plannedWeight',
              type: this.fieldType.NUMBER,
              validators: ['required', 'ddddddd.dd.+']
            }],
            header: 'Plan',
            editable: !this.checkIfConfirmed() && this.checkIfEnableEditMode(),

          },
          {
            fields: [{
              key: 'actualWeight',
              type: this.fieldType.NUMBER,
              validators: ['required', 'ddddddd.dd.+']
            }],
            header: 'Actual',
            editable: this.checkIfConfirmed(),

          },
        ],
      },
      {
        fieldKey: 'doList',
        primaryKey: 'tankId',
        headerLabel: 'tankName',
        maxKey: 'capacity',
        subHeaders: [
          {
            fields: [{
              key: 'plannedWeight',
              type: this.fieldType.NUMBER,
              validators: ['required', 'ddddddd.dd.+']
            }],
            header: 'Plan',
            editable: !this.checkIfConfirmed() && this.checkIfEnableEditMode()

          },
          {
            fields: [{
              key: 'actualWeight',
              type: this.fieldType.NUMBER,
              validators: ['required', 'ddddddd.dd.+']
            }],
            header: 'Actual',
            editable: this.checkIfConfirmed(),

          },
        ],
      },
      {
        fieldKey: 'fwList',
        primaryKey: 'tankId',
        headerLabel: 'tankName',
        maxKey: 'capacity',
        subHeaders: [
          {
            fields: [{
              key: 'plannedWeight',
              type: this.fieldType.NUMBER,
              validators: ['required', 'ddddddd.dd.+']
            }],
            header: 'Plan',
            editable: !this.checkIfConfirmed() && this.checkIfEnableEditMode(),

          },
          {
            fields: [{
              key: 'actualWeight',
              type: this.fieldType.NUMBER,
              validators: ['required', 'ddddddd.dd.+']
            }],
            header: 'Actual',
            editable: this.checkIfConfirmed(),

          },
        ],
      },
      {
        fieldKey: 'lubeList',
        primaryKey: 'tankId',
        headerLabel: 'tankName',
        maxKey: 'capacity',
        subHeaders: [
          {
            fields: [{
              key: 'plannedWeight',
              type: this.fieldType.NUMBER,
              validators: ['required', 'ddddddd.dd.+']
            }],
            header: 'Plan',
            editable: !this.checkIfConfirmed() && this.checkIfEnableEditMode(),

          },
          {
            fields: [{
              key: 'actualWeight',
              type: this.fieldType.NUMBER,
              validators: ['required', 'ddddddd.dd.+']
            }],
            header: 'Actual',
            editable: this.checkIfConfirmed(),
          },
        ],
      },
      {
        fieldKey: 'ballast',
        primaryKey: 'tankId',
        headerLabel: 'tankName',
        maxKey: 'capacity',
        subHeaders: [
          {
            fields: [{
              key: 'plannedWeight',
              type: this.fieldType.NUMBER,
              validators: ['required', 'ddddddd.dd.+']
            }],
            header: 'Plan',
            editable: false,

          },
          {
            fields: [{
              key: 'actualWeight',
              type: this.fieldType.NUMBER,
              validators: ['required', 'ddddddd.dd.+']
            }],
            header: 'Actual',
            editable: this.checkIfConfirmed(),
          },
        ],
      },
    ]
  }

  /**
   * Method to add columns dynamically
   * @param dynamicColumn
   * @returns {void}
   * @memberof SynopticalTableComponent
   */
  addToColumns(dynamicColumn: SynopticalDynamicColumn) {
    const keys = dynamicColumn.subHeaders;
    const fieldKey = dynamicColumn.fieldKey;
    const col = this.cols.find(column => column.dynamicKey === fieldKey);
    this.listData[fieldKey].forEach(item => {
      const subHeaders = [];
      keys.forEach(key => {
        const subHeader = JSON.parse(JSON.stringify(key))
        subHeader['fields'] = []
        key.fields.forEach(field => {
          const tempField = JSON.parse(JSON.stringify(field))
          tempField['key'] = fieldKey + item.id + field['key']
          if (field.type === 'NUMBER') {
            tempField['numberType'] = 'quantity';
            tempField['unit'] = 'MT';
          }
          if (item.max) {
            tempField['max'] = item.max;
          }
          subHeader.fields.push(tempField)
        })
        subHeaders.push(subHeader);
      });
      col.expandedFields.push(
        {
          header: "",
          subHeaders: [
            {
              header: item.header,
              subHeaders: subHeaders,
            }
          ],
        }
      );
    })
  }

  /**
   * Method to convert data from array format to keys
   * @param dynamicColumn
   * @returns {void}
   * @memberof SynopticalTableComponent
   */
  formatData(dynamicColumn: SynopticalDynamicColumn) {
    const fieldKey = dynamicColumn.fieldKey;
    const primaryKey = dynamicColumn.primaryKey;
    const headerLabel = dynamicColumn.headerLabel;
    const maxKey = dynamicColumn.maxKey;
    const keys = dynamicColumn.subHeaders;
    this.listData[fieldKey] = [];
    this.synopticalService.synopticalRecords.forEach(synopticalRecord => {
      if (synopticalRecord[fieldKey]) {
        synopticalRecord[fieldKey].forEach(record => {
          const index = this.listData[fieldKey].findIndex(item => item.id === record[primaryKey]);
          if (index < 0) {
            const fieldJson = {
              id: record[primaryKey],
              header: record[headerLabel],
            }
            if (maxKey && record[maxKey]) {
              fieldJson['max'] = record[maxKey]
            }
            this.listData[fieldKey].push(fieldJson)
          }
          keys.forEach(key => {
            key.fields.forEach(field => {
              synopticalRecord[fieldKey + record[primaryKey] + field['key']] = record[field['key']];
            })
          });
        })
      }
    })
  }

  /**
   * Method to set header column as the first column
   *
   * @returns {void}
   * @memberof SynopticalTableComponent
   */
  setColumnHeader() {
    this.headerColumns = this.cols.splice(0, 2);
  }

  /**
 * Function to initalize form
 *
 * @returns {Promise<void>}
 * @memberof SynopticalTableComponent
 */
  initForm() {
    this.allColumns = this.getAllColumns(this.cols);

    this.synopticalService.synopticalRecords.forEach((record, colIndex) => {
      this.setTotal(colIndex);
      const fg = new FormGroup({})
      this.allColumns.forEach(column => {
        if (column.editable) {
          column.fields?.forEach(field => {
            fg.addControl(field.key, new FormControl(this.getValue(record[field.key], field.type)))
          });
          column.fields?.forEach(field => {
            const validators: ValidatorFn[] = []
            field.validators?.forEach(validator => {
              validators.push(this.getValidators(validator))
            })
            const fc = fg.get(field.key);
            fc.setValidators(validators)
          })
        }
      })
      this.tableForm.push(fg)
    })
  }

  /**
 * Method to get all leaf child columns of the table
 * @param columns
 * @returns {SynopticalColumn[]}
 * @memberof SynopticalTableComponent
 */
  getAllColumns(columns: SynopticalColumn[]): SynopticalColumn[] {
    let cols: SynopticalColumn[] = [];
    columns.forEach(column => {
      if (column.expandedFields) {
        cols = [...cols, ...this.getAllColumns(column.expandedFields)]
      }
      if (column.subHeaders) {
        cols = [...cols, ...this.getAllColumns(column.subHeaders)]
      } else {
        cols.push(column)
      }
    })
    return cols;
  }

  /**
  * Method to get the control of a particular field
  * @param {number} colIndex
  * @param {string} key
  * @returns {AbstractControl}
  * @memberof SynopticalTableComponent
  */
  getControl(colIndex: number, key: string): AbstractControl {
    return this.tableForm.at(colIndex).get(key)
  }

  /**
   * Get field errors
   *
   * @param {number} formGroupIndex
   * @param {string} formControlName
   * @returns {ValidationErrors}
   * @memberof SynopticalTableComponent
  */
  fieldError(formGroupIndex: number, formControlName: string): ValidationErrors {
    const formControl = this.getControl(formGroupIndex, formControlName);
    return formControl.invalid && (formControl.dirty || formControl.touched) ? formControl.errors : null;
  }

  /**
   * Get the validator based on a key string
   *
   * @param {string} validator
   * @returns {ValidatorFn}
   * @memberof SynopticalTableComponent
  */
  getValidators(validator: string): ValidatorFn {
    if (validator === 'required')
      return Validators.required;
    else if (/^(d*(.d)?d*(.\+|.\-)?)$/.test(validator)) {
      let decimals = 0;
      let isNegativeAccepted = true;
      const arr = validator.split('.')
      const digits = arr[0].length;
      if (arr.length > 2)
        decimals = arr[1].length;
      if (/\+/.test(validator))
        isNegativeAccepted = false;
      return numberValidator(decimals, digits, isNegativeAccepted);
    } else if (validator === 'seaWaterDensity') {
      return seaWaterDensityRangeValidator();
    } else
      return null;
  }

  /**
   * Get the value after converting it based on type
   *
   * @param value
   * @param type
   * @returns {any}
   * @memberof SynopticalTableComponent
  */
  getValue(value, type) {
    if (type) {
      switch (type) {
        case this.fieldType.DATETIME:
          return this.convertIntoDate(value)
        case this.fieldType.TIME:
          return this.convertToDate(value)
        default:
          return value;
      }
    }
    return value;
  }

  /**
   * Convert value to date
   *
   * @param value
   * @returns {any}
   * @memberof SynopticalTableComponent
  */
  convertToDate(value) {
    if (value) {
      const arr = value.split(' ');
      let time, day, month, year;
      if (arr.length > 1) {
        const date = arr[0];
        time = arr[1];
        const dateArr = date.split('-');
        day = dateArr[0]
        month = Number(dateArr[1]) - 1
        year = dateArr[2]
      } else {
        const date = new Date();
        day = date.getDate();
        month = date.getMonth();
        year = date.getFullYear();
        time = arr[0];
      }
      const timeArr = time.split(':')
      const hour = timeArr[0];
      const min = timeArr[1];
      return new Date(year, month, day, hour, min);
    }
    return value;
  }


  /**
   * Convert value to date
   * @param value
   * @returns {any}
   * @memberof SynopticalTableComponent
  */
  convertIntoDate(value: any) {
    if (value) {
      if (value.length > 17) {
        value = value?.length ? moment(value.slice(0, 17), 'DD/MMM/YYYY HH:mm').toDate() : value;
        return value;
      }
      else {
        return moment(value, 'DD/MM/YYYY HH:mm').toDate();
      }
    }
    else {
      return value;
    }
  }

  /**
 * Method to update other fields on keyup of an input
 *
 * @param {SynopticField} field
 * @param {number} colIndex
 * @returns {any}
 * @memberof SynopticalTableComponent
*/
  onKeyUp(field: SynopticField, colIndex: number) {
    const fc = this.getControl(colIndex, field.key)
    const operationType = this.synopticalService.synopticalRecords[colIndex].operationType;
    const otherIndex = operationType === 'ARR' ? colIndex + 1 : colIndex - 1;
    switch (field.key) {
      case 'speed': case 'distance': case 'runningHours':
      case 'inPortHours':
        if (otherIndex >= 0 && otherIndex < this.synopticalService.synopticalRecords.length) {
          this.getControl(otherIndex, field.key)?.setValue(fc.value)
        }
        break;
      default:
        break;
    }
  }

  /**
   * function to clear Time-picker input field
   *
   * @param {*} ref
   * @param {number} colIndex
   * @param {string} key
   * @memberof SynopticalTableComponent
   */
  clearTimeInput(ref, colIndex: number, key: string): void {
    ref.value = null;
    ref.updateInputField;
    ref.hideOverlay();
    const formControl = this.getControl(colIndex, key);
    formControl.setValue(null);
  }

  /**
   * function to set current time input
   *
   * @param {*} ref
   * @param {number} colIndex
   * @param {string} key
   * @memberof SynopticalTableComponent
   */
  setTimeInput(ref, colIndex: number, key: string): void {
    if (!ref.value) {
      const time = new Date();
      time.setHours(ref.currentHour, ref.currentMinute);
      const formControl = this.getControl(colIndex, key);
      formControl.setValue(time);
    }
    ref.hideOverlay();
  }

  /**
   * Method to do validations on focusing out of an input
   *
   * @param {SynopticField} field
   * @param {number} colIndex
   * @returns {any}
   * @memberof SynopticalTableComponent
  */
  onBlur(field: SynopticField, colIndex: number, updateValues = true) {
    const fc = this.getControl(colIndex, field.key)
    const operationType = this.synopticalService.synopticalRecords[colIndex].operationType;
    const otherIndex = operationType === 'ARR' ? colIndex + 1 : colIndex - 1;
    if (!fc || fc.invalid)
      return;
    let fcMax, fcMin;
    switch (field.key) {
      case 'hwTideFrom':
        fcMax = this.getControl(colIndex, 'hwTideTo')
        if (fc.value != 0 && !fc.value) {
          fc.setErrors(null);
          fc.setErrors({ required: true })
        }
        else if (typeof fcMax.value !== 'undefined' && fc.value >= fcMax.value) {
          fc.setErrors({ fromMax: true })
        } else if (fcMax.hasError('toMin')) {
          fcMax.setValue(fcMax.value, { emitEvent: false })
        }

        break;
      case 'hwTideTo':
        fcMin = this.getControl(colIndex, 'hwTideFrom')
        if (fc.value != 0 && !fc.value) {
          fc.setErrors(null);
          fc.setErrors({ required: true })
        }
        if (typeof fcMin.value !== 'undefined' && fc.value <= fcMin.value) {
          fc.setErrors({ toMin: true })
        } else if (fcMin.hasError('fromMax')) {
          fcMin.setValue(fcMin.value, { emitEvent: false })
        }
        break;
      case 'lwTideFrom':
        if (!fc.value) {
          fc.setErrors(null);
          fc.setErrors({ required: true })
        }
        fcMax = this.getControl(colIndex, 'lwTideTo')
        if (typeof fcMax.value !== 'undefined' && fc.value >= fcMax.value) {
          fc.setErrors({ fromMax: true })
        } else if (fcMax.hasError('toMin')) {
          fcMax.setValue(fcMax.value, { emitEvent: false })
        }
        break;

      case 'timeOfSunrise':
        fcMax = this.getControl(colIndex, 'timeOfSunset')
        if (fcMax?.value) {
          fcMax.value.setSeconds(0, 0);
        }

        if (fc?.value) {
          fc?.value?.setSeconds(0, 0);
        }
        if (!fcMax?.value && !fc?.value) {
          fcMax?.setErrors(null);
          fc?.setErrors(null);
        }
        else if (typeof fcMax?.value !== 'undefined' && fc?.value >= fcMax?.value && fc?.value && fcMax?.value) {
          fc?.setErrors({ sunRiseGreater: true })
        } else if (fcMax?.hasError('sunSetGreater')) {
          fcMax?.setValue(fcMax?.value, { emitEvent: false })
        }
        break;

      case 'timeOfSunset':
        fcMax = this.getControl(colIndex, 'timeOfSunrise')
        if (fcMax?.value) {
          fcMax.value.setSeconds(0, 0);
        }
        if (fc?.value) {
          fc.value.setSeconds(0, 0);
        }
        if (!fcMax?.value && !fc?.value) {
          fcMax?.setErrors(null);
          fc?.setErrors(null);
        }
        else if (typeof fcMax?.value !== 'undefined' && fc?.value <= fcMax?.value && fc?.value && fcMax?.value) {
          fc?.setErrors({ sunSetGreater: true })
        } else if (fcMax?.hasError('sunRiseGreater')) {
          fcMax.setValue(fcMax.value, { emitEvent: false })
        }
        break;
      case 'lwTideTo':
        if (!fc.value) {
          fc.setErrors(null);
          fc.setErrors({ required: true })
        }
        fcMin = this.getControl(colIndex, 'lwTideFrom')
        if (typeof fcMin.value !== 'undefined' && fc.value <= fcMin.value) {
          fc.setErrors({ toMin: true })
        } else if (fcMin.hasError('fromMax')) {
          fcMin.setValue(fcMin.value, { emitEvent: false })
        }
        break;
      case 'hwTideTimeFrom':
        if (!fc.value) {
          fc.setErrors(null);
          fc.setErrors({ required: true })
        }
        fcMax = this.getControl(colIndex, 'hwTideTimeTo')

        if (fcMax.value) {
          fcMax.value.setSeconds(0, 0);
        }

        if (fc.value) {
          fc.value.setSeconds(0, 0);
        }

        if (typeof fcMax.value !== 'undefined' && fc.value >= fcMax.value) {
          fc.setErrors({ timeFromMax: true })
        } else if (fcMax.hasError('timeToMin')) {
          fcMax.setValue(fcMax.value, { emitEvent: false })
        }
        break;
      case 'hwTideTimeTo':
        if (!fc.value) {
          fc.setErrors(null);
          fc.setErrors({ required: true })
        }
        fcMin = this.getControl(colIndex, 'hwTideTimeFrom')
        if (fcMin.value) {
          fcMin.value.setSeconds(0, 0);
        }
        if (fc.value) {
          fc.value.setSeconds(0, 0);
        }

        if (typeof fcMin.value !== 'undefined' && fc.value <= fcMin.value) {
          fc.setErrors({ timeToMin: true })
        } else if (fcMin.hasError('timeFromMax')) {
          fcMin.setValue(fcMin.value, { emitEvent: false })
        }
        break;
      case 'lwTideTimeFrom':
        if (!fc.value) {
          fc.setErrors(null);
          fc.setErrors({ required: true })
        }
        fcMax = this.getControl(colIndex, 'lwTideTimeTo')
        if (fcMax.value) {
          fcMax.value.setSeconds(0, 0);
        }
        if (fc.value) {
          fc.value.setSeconds(0, 0);
        }
        if (typeof fcMax.value !== 'undefined' && fc.value >= fcMax.value) {
          fc.setErrors({ timeFromMax: true })
        } else if (fcMax.hasError('timeToMin')) {
          fcMax.setValue(fcMax.value, { emitEvent: false })
        }
        break;
      case 'lwTideTimeTo':
        if (!fc.value) {
          fc.setErrors(null);
          fc.setErrors({ required: true })
        }
        fcMin = this.getControl(colIndex, 'lwTideTimeFrom')
        if (fcMin.value) {
          fcMin.value.setSeconds(0, 0);
        }
        if (fc.value) {
          fc.value.setSeconds(0, 0);
        }
        if (typeof fcMin.value !== 'undefined' && fc.value <= fcMin.value) {
          fc.setErrors({ timeToMin: true })
        } else if (fcMin.hasError('timeFromMax')) {
          fcMin.setValue(fcMin.value, { emitEvent: false })
        }
        break;
      case 'etaEtdPlanned': case 'etaEtdActual':
        const value: Date = fc.value;
        value.setSeconds(0, 0)
        if (colIndex > 0) {
          fcMin = this.getControl(colIndex - 1, field.key)
          const minValue: Date = fcMin.value;
          if (minValue) {
            minValue.setSeconds(0, 0)
            if (value <= minValue) {
              if (this.synopticalService.synopticalRecords[colIndex].operationType === 'ARR')
                fc.setErrors({ etaMin: true })
              else
                fc.setErrors({ etdMin: true })
            } else if (fcMin.hasError('etaMax') || fcMin.hasError('etdMax')) {
              fcMin.setValue(fcMin.value, { emitEvent: false })
            }
          }
        }
        if (colIndex < this.synopticalService.synopticalRecords.length - 1) {
          fcMax = this.getControl(colIndex + 1, field.key)
          const maxValue: Date = fcMax.value;
          if (maxValue) {
            maxValue.setSeconds(0, 0)
            if (value >= maxValue) {
              if (this.synopticalService.synopticalRecords[colIndex].operationType === 'ARR')
                fc.setErrors({ etaMax: true })
              else
                fc.setErrors({ etdMax: true })
            } else if (fcMax.hasError('etaMin') || fcMax.hasError('etdMin')) {
              fcMax.setValue(fcMax.value, { emitEvent: false })
            }
          }
        }
        if (updateValues) {
          let inPortHours = 0;
          if (fc.valid) {
            const otherControl = this.getControl(otherIndex, field.key);
            if (otherControl.valid) {
              let arrDate, depDate;
              if (otherIndex > colIndex) {
                arrDate = fc.value;
                depDate = otherControl.value;
              } else {
                depDate = fc.value;
                arrDate = otherControl.value;
              }
              inPortHours = Math.abs(depDate - arrDate) / 36e5;
              inPortHours = Number(inPortHours.toFixed(2))
            }
          }
          this.getControl(colIndex, 'inPortHours')?.setValue(inPortHours)
          this.getControl(otherIndex, 'inPortHours')?.setValue(inPortHours)
        }
        break;
      case 'speed': case 'distance':
        if (updateValues) {
          const speedControl = this.getControl(colIndex, 'speed')
          const distanceControl = this.getControl(colIndex, 'distance')
          let speed = 0, distance = 0, runningHours = 0;
          if (speedControl.valid) {
            speed = Number(speedControl.value);
          }
          if (distanceControl.valid) {
            distance = Number(distanceControl.value);
          }
          if (speed && distance) {
            runningHours = Number(Number(distance / speed).toFixed(2));
          }
          this.getControl(colIndex, 'runningHours')?.setValue(runningHours)
          this.getControl(otherIndex, 'runningHours')?.setValue(runningHours)
        }
        break;
      case 'calculatedDraftAftPlanned': case 'calculatedDraftAftActual':
        this.synopticalService.synopticalRecords[colIndex]['finalDraftAft'] = fc.value + this.synopticalService.synopticalRecords[colIndex]['deflection'];
        break;

      case 'calculatedDraftFwdPlanned': case 'calculatedDraftFwdActual':
        this.synopticalService.synopticalRecords[colIndex]['finalDraftFwd'] = fc.value + this.synopticalService.synopticalRecords[colIndex][field.key] + this.synopticalService.synopticalRecords[colIndex]['deflection'];
        break;

      case 'calculatedDraftMidPlanned': case 'calculatedDraftMidActual':
        this.synopticalService.synopticalRecords[colIndex]['finalDraftMid'] = fc.value + this.synopticalService.synopticalRecords[colIndex][field.key] + this.synopticalService.synopticalRecords[colIndex]['deflection'];
        break;

      default:
        const planIndex = field.key.includes('plan') ? 0 : 1;
        const dynamicCols = this.cols.filter(col => col.dynamicKey);
        dynamicCols.forEach(col => {
          const dynamicKey = col.dynamicKey;
          const totalCols = this.getAllColumns(col.subHeaders)
          const totalKeys = totalCols.map(totalCol => totalCol.fields[0].key)
          if (field.key.startsWith(dynamicKey)) {
            const totalValue = this.synopticalService.synopticalRecords[colIndex][totalKeys[planIndex]]
            const currentFieldValue = this.synopticalService.synopticalRecords[colIndex][field.key] ?? 0;
            this.synopticalService.synopticalRecords[colIndex][totalKeys[planIndex]] = Number(Number(totalValue - currentFieldValue + fc.value).toFixed(2));
            this.synopticalService.synopticalRecords[colIndex][field.key] = fc.value;
          }
        });
        this.synopticalService.synopticalRecords[colIndex][field.key] = fc.value;
        this.synopticalService.synopticalRecords = [...this.synopticalService.synopticalRecords];
        this.setTotal(colIndex);
        break;
    }
  }
  /**
   * Method for setting total DWT and displacement
   *
   * @param {number} colIndex
   * @memberof SynopticalTableComponent
   */
  setTotal(colIndex: number) {
    
    this.synopticalService.synopticalRecords[colIndex].totalDwtPlanned = this.synopticalService.synopticalRecords[colIndex].plannedDOTotal
    + this.synopticalService.synopticalRecords[colIndex].plannedFOTotal
    + this.synopticalService.synopticalRecords[colIndex].plannedFWTotal
    + this.synopticalService.synopticalRecords[colIndex].ballastPlannedTotal
    + this.synopticalService.synopticalRecords[colIndex].cargoPlannedTotal
    + this.synopticalService.synopticalRecords[colIndex].constantPlanned
    + this.synopticalService.synopticalRecords[colIndex].othersPlanned;
    this.synopticalService.synopticalRecords[colIndex].displacementPlanned = this.synopticalService.synopticalRecords[colIndex].totalDwtPlanned + this.vesselLightWeight;
    
    if(this.checkIfConfirmed()){
      this.synopticalService.synopticalRecords[colIndex].totalDwtActual = this.synopticalService.synopticalRecords[colIndex].actualDOTotal
      + this.synopticalService.synopticalRecords[colIndex].actualFOTotal
      + this.synopticalService.synopticalRecords[colIndex].actualFWTotal
      + this.synopticalService.synopticalRecords[colIndex].ballastActualTotal
      + this.synopticalService.synopticalRecords[colIndex].cargoActualTotal
      + this.synopticalService.synopticalRecords[colIndex].constantActual
      + this.synopticalService.synopticalRecords[colIndex].othersActual;
      this.synopticalService.synopticalRecords[colIndex].displacementActual = this.synopticalService.synopticalRecords[colIndex].totalDwtActual + this.vesselLightWeight;
    } else {
      this.synopticalService.synopticalRecords[colIndex].totalDwtActual = 0 ;
      this.synopticalService.synopticalRecords[colIndex].displacementActual = 0;
    }
  }

  /**
   * Method to check if the loadable study is confirmed
   *
   * @returns {boolean}
   * @memberof SynopticalTableComponent
  */
  checkIfConfirmed(): boolean {
    if (this.synopticalService.selectedLoadablePattern) {
      return this.synopticalService.selectedLoadablePattern.loadableStudyStatusId === LOADABLE_STUDY_STATUS.PLAN_CONFIRMED ?? false
    }
    return this.synopticalService.selectedLoadableStudy?.status === "Confirmed" ?? false
  }

  /**
   * Method to check if the loadable study plan is generated
   *
   * @returns {boolean}
   * @memberof SynopticalTableComponent
   */
  checkIfEnableEditMode(): boolean {
    return [LOADABLE_STUDY_STATUS.PLAN_PENDING, LOADABLE_STUDY_STATUS.PLAN_NO_SOLUTION, LOADABLE_STUDY_STATUS.PLAN_ERROR].includes(this.synopticalService?.selectedLoadableStudy?.statusId) && this.synopticalService?.selectedVoyage?.statusId !== VOYAGE_STATUS.CLOSE;
  }

  /**
   * Method to check if value is null or blank
   *
   * @param {any} value
   * @returns {boolean}
   * @memberof SynopticalTableComponent
  */
  isNotBlankOrNull(value): boolean {
    return (typeof value !== 'undefined' && value !== '');
  }

  /**
   * Method to export table data
   *
   * @returns {void}
   * @memberof SynopticalTableComponent
  */
  exportExcelFromTable(): void {
    const fileName = 'synoptic.xlsx';
    const expandableRows = ['Final Draft', 'Cargo', 'Total F.O', 'Total D.O', 'Fresh W.T', 'Lube Oil Total'];
    const expanded = { ...this.expandedRows }
    expandableRows.forEach(key => {
      this.expandedRows[key] = true;
    });
    setTimeout(() => {
      /* table id is passed over here */
      const element = document.getElementById('synoptic-table');
      const ws: XLSX.WorkSheet = XLSX.utils.table_to_sheet(element);

      /* setting back all the expanded rows */
      this.expandedRows = expanded;

      /* Removing unwanted strings from the values */
      Object.keys(ws).forEach(key => {
        if (ws[key].t && ws[key].t === 's' && ws[key].v) {
          let v = String(ws[key].v)
          v = v.replace('&nbsp;', '');
          ws[key].v = v;
        }
      })


      /* generate workbook and add the worksheet */
      const wb: XLSX.WorkBook = XLSX.utils.book_new();
      XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');

      /* save to file */
      XLSX.writeFile(wb, fileName);
    }, 1000)
  }

  /**
  * Method to save changes
  *
  * @returns {void}
  * @memberof SynopticalTableComponent
 */
  async saveChanges() {
    let msgkeys, severity, valid = true;
    // Prompt all validations
    this.tableForm.markAllAsTouched();
    this.synopticalService.synopticalRecords.forEach((_, portIndex) => {
      this.allColumns.forEach(col => {
        col.fields.forEach(field => {
          this.onBlur(field, portIndex, false)
        })
      })    
    })
    //check if valid and save
    if (valid) {
      if (this.tableForm.valid) {
        this.ngxSpinner.show();
        const synopticalRecords = []
        this.synopticalService.synopticalRecords.forEach((row, index: number) => {
          const saveJson = {};
          saveJson['id'] = row.id;
          saveJson['portId'] = row.portId;
          saveJson['portRotationId'] = row.portRotationId;
          this.headerColumns.forEach(col => {
            col.fields.forEach(field => {
              saveJson[field.key] = this.synopticalService.synopticalRecords[index][field.key]
            })
          })
          this.cols.forEach(col => {
            this.setColValue(col, saveJson, index)
          })
          synopticalRecords.push(saveJson)
        })

        const postData = {
          synopticalRecords: synopticalRecords
        }
        this.synopticalService.synopticalRecords = await this.convertIntoZoneTimeZone(this.synopticalService.synopticalRecords);

        try {
          const res = await this.synoticalApiService.saveSynopticalTable(postData, this.synopticalService.vesselId, this.synopticalService.voyageId, this.synopticalService.loadableStudyId, this.synopticalService.loadablePatternId).toPromise();
          if (res?.responseStatus?.status === '200') {

            msgkeys = ['SYNOPTICAL_UPDATE_SUCCESS', 'SYNOPTICAL_UPDATE_SUCCESSFULLY']
            severity = 'success';
            this.synopticalService.editMode = false;
          } else if (res?.responseStatus?.status === '207' && Object.values(res?.failedRecords).includes('ERR-RICO-110')) {
            msgkeys = ['SYNOPTICAL_UPDATE_ERROR', 'SYNOPTICAL_UPDATE_STATUS_ERROR']
            severity = 'error';
          }
        } catch (errorResponse) {
          msgkeys = ['SYNOPTICAL_UPDATE_FAILED', 'SYNOPTICAL_UPDATE_FAILURE']
          severity = 'error';
        }
        this.ngxSpinner.hide();
      } else {
        msgkeys = ['SYNOPTICAL_UPDATE_INVALID', 'SYNOPTICAL_UPDATE_FIELDS_INVALID']
        severity = 'warn';
      }
    }
    const translationKeys = await this.translateService.get(msgkeys).toPromise();
    this.messageService.add({ severity: severity, summary: translationKeys[msgkeys[0]], detail: translationKeys[msgkeys[1]] });
  }

  /**
   * Method to set all values of a particular column
   *
   * @returns {void}
   * @memberof SynopticalTableComponent
  */
  setColValue(column: SynopticalColumn, record, index: number) {
    if (!column.dynamicKey && column.expandedFields) {
      column.expandedFields.forEach(expandedCol => {
        this.setColValue(expandedCol, record, index)
      })
    }
    if (column.dynamicKey) {
      const values = [];
      const fieldKey = column.dynamicKey
      const dynamicColumn = this.dynamicColumns.find(col => col.fieldKey === fieldKey)
      const primaryKey = dynamicColumn.primaryKey;
      const subColumns = this.getAllColumns(dynamicColumn.subHeaders)
      this.listData[fieldKey].forEach(item => {
        let json = {};
        json[primaryKey] = item.id
        json = this.setFuelTypeId(json, fieldKey, this.synopticalService.synopticalRecords[index], item, primaryKey)
        subColumns.forEach(subCol => {
          subCol.fields.forEach(field => {
            const key = field.key;
            const value = this.getValueFromTable(fieldKey + item.id + key, subCol, index, field.type)
            json[key] = value;
            this.synopticalService.synopticalRecords[index][fieldKey + item.id + key] = value;
          })
        })
        values.push(json)
      })
      record[fieldKey] = values
    } else if (column.subHeaders) {
      column.subHeaders.forEach(col => {
        this.setColValue(col, record, index)
      })
    } else if (column.fields) {
      column.fields.forEach(field => {
        const key = field.key;
        const value = this.getValueFromTable(key, column, index, field.type);
        record[key] = value;
        this.synopticalService.synopticalRecords[index][key] = value;
      })
    }
  }

  /**
  * Method to get value of a particular key based on condition
  *
  * @returns {void}
  * @memberof SynopticalTableComponent
  */
  getValueFromTable(key: string, column: SynopticalColumn, index: number, type: string) {
    if (column.editable) {
      return this.convertToString(this.getControl(index, key).value, type, this.synopticalService.synopticalRecords[index])
    }

    else if (type == this.fieldType.DATETIME && this.synopticalService.synopticalRecords[index][key]) {
      const item = this.globalTimeZones.find((item) => item.id == this.synopticalService.synopticalRecords[index].portTimezoneId)
      if (item)
        return this.timeZoneTransformationService.revertZoneTimetoUTC(this.convertIntoDate(this.synopticalService.synopticalRecords[index][key]), item.offsetValue);
      else
        return this.synopticalService.synopticalRecords[index][key].slice(0, 17);
    }
    else {
      return this.synopticalService.synopticalRecords[index][key] ?? null;
    }
  }

  /**
    * Method to convert values to string based on type
    *
    * @returns {void}
    * @memberof SynopticalTableComponent
   */
  convertToString(value, type, record) {
    switch (type) {
      case this.fieldType.DATETIME:
        const item = this.globalTimeZones.find((item) => item.id == record.portTimezoneId)
        if (item)
          return this.timeZoneTransformationService.revertZoneTimetoUTC(value, item.offsetValue);
        else
          return this.datePipe.transform(value, 'dd-MM-yyyy HH:mm')
      case this.fieldType.TIME:
        if (value === null) {
          return '';
        } else {
          return this.datePipe.transform(value, 'HH:mm')
        }
      default:
        return value;
    }
  }

  /**
  * Method to reset form values on cancelling
  *
  * @returns {void}
  * @memberof SynopticalTableComponent
  */
  resetFormValues() {
    this.allColumns.forEach(col => {
      this.synopticalService.synopticalRecords.forEach((record, index) => {
        col.fields.forEach(field => {
          const fc = this.getControl(index, field.key)
          if (fc) {
            fc.setValue(this.getValue(record[field.key], field.type))
          }
        })
      })
    })
  }

  /**
  * Method to init all action subscriptions
  *
  * @returns {void}
  * @memberof SynopticalTableComponent
  */
  initActionSubscriptions() {
    this.synopticalService.save
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(() => {
        this.saveChanges()
      })

    this.synopticalService.export
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(() => {
        this.exportExcelFromTable()
      })

    this.synopticalService.edit
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(() => {
        this.editChanges()
      })

    this.synopticalService.cancel
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(() => {
        this.cancelChanges()
      })
  }

  /**
  * Method to edit changes
  *
  * @returns {void}
  * @memberof SynopticalTableComponent
  */
  editChanges() {
    this.synopticalService.editMode = true;
    this.synopticalRecordsCopy = JSON.parse(JSON.stringify(this.synopticalService.synopticalRecords))
  }

  /**
  * Method to cancel changes
  *
  * @returns {void}
  * @memberof SynopticalTableComponent
  */
  cancelChanges() {
    this.synopticalService.synopticalRecords = JSON.parse(JSON.stringify(this.synopticalRecordsCopy))
    this.resetFormValues();
    this.synopticalService.editMode = false;
  }

  /**
  * Method to check if editable by special conditions
  *
  * @returns {boolean}
  * @memberof SynopticalTableComponent
  */
  checkEditableCondition(key: string, index: number) {
    let editable = false;
    if (key.startsWith('cargos') && index === 0) {
      editable = true
    }
    return editable;
  }

  /**
  * Method to set fuel type in ohq data
  *
  * @returns {boolean}
  * @memberof SynopticalTableComponent
  */
  setFuelTypeId(json, fieldKey, record, item, primaryKey) {
    if (['foList', 'doList', 'fwList', 'lubeList'].indexOf(fieldKey) >= 0) {
      const listItem = record[fieldKey].find(data => data[primaryKey] === item.id)
      json['fuelTypeId'] = listItem.fuelTypeId
    }
    return json
  }

  /**
  * Method to set loadable quantity
  *
  * @returns {void}
  * @memberof SynopticalTableComponent
  */
  async setLoadableQuantity() {
    const loadableQuantityResult = await this.synoticalApiService.getLoadableQuantity(this.synopticalService.vesselId, this.synopticalService.voyageId, this.synopticalService.loadableStudyId).toPromise();
    if (loadableQuantityResult.responseStatus.status === "200") {
      loadableQuantityResult.loadableQuantity.totalQuantity === '' ? this.getSubTotal(loadableQuantityResult) : this.loadableQuantityValue = Number(loadableQuantityResult.loadableQuantity.totalQuantity);
      this.vesselLightWeight = Number(loadableQuantityResult?.loadableQuantity?.vesselLightWeight);
    }
  }

  /**
  * Calculation for subtotal
  */
  getSubTotal(loadableQuantityResult: any) {
    const loadableQuantity = loadableQuantityResult.loadableQuantity;
    let subTotal = 0;
    const sgCorrection = (loadableQuantity.estSeaDensity && loadableQuantity.displacmentDraftRestriction) && (Number(loadableQuantity.estSeaDensity) - 1.025) / 1.025 * Number(loadableQuantity.displacmentDraftRestriction);
    if (loadableQuantityResult.caseNo === 1 || loadableQuantityResult.caseNo === 2) {
      const data: ISubTotal = {
        dwt: loadableQuantity.dwt,
        sagCorrection: loadableQuantity.saggingDeduction,
        sgCorrection: sgCorrection.toString(),
        foOnboard: loadableQuantity.estFOOnBoard,
        doOnboard: loadableQuantity.estDOOnBoard,
        freshWaterOnboard: loadableQuantity.estFreshWaterOnBoard,
        boilerWaterOnboard: loadableQuantity.boilerWaterOnBoard,
        ballast: loadableQuantity.ballast,
        constant: loadableQuantity.constant,
        others: loadableQuantity.otherIfAny === '' ? 0 : loadableQuantity.otherIfAny
      }
      subTotal = Number(this.synopticalService.getSubTotal(data));
      this.getTotalLoadableQuantity(subTotal, loadableQuantityResult);
    }
    else {
      const dwt = (Number(loadableQuantity.displacmentDraftRestriction) - Number(loadableQuantity.vesselLightWeight))?.toString();
      const data: ISubTotal = {
        dwt: dwt,
        sagCorrection: loadableQuantity.saggingDeduction,
        sgCorrection: sgCorrection.toString(),
        foOnboard: loadableQuantity.estFOOnBoard,
        doOnboard: loadableQuantity.estDOOnBoard,
        freshWaterOnboard: loadableQuantity.estFreshWaterOnBoard,
        boilerWaterOnboard: loadableQuantity.boilerWaterOnBoard,
        ballast: loadableQuantity.ballast,
        constant: loadableQuantity.constant,
        others: loadableQuantity.otherIfAny === '' ? 0 : loadableQuantity.otherIfAny
      }
      subTotal = Number(this.synopticalService.getSubTotal(data));
      this.getTotalLoadableQuantity(subTotal, loadableQuantityResult);
    }
  }

  /**
   * Calculation for Loadable quantity
  */
  getTotalLoadableQuantity(subTotal: number, loadableQuantityResult: any) {
    const loadableQuantity = loadableQuantityResult.loadableQuantity;
    if (loadableQuantityResult.caseNo === 1) {
      const total = Number(subTotal) + Number(loadableQuantity.foConInSZ);
      if (total < 0) {
        this.loadableQuantityValue = 0;
      }
      else {
        this.loadableQuantityValue = total;
      }

    }
    else {
      if (subTotal < 0) {
        this.loadableQuantityValue = 0;
      }
      else {
        this.loadableQuantityValue = subTotal;
      }

    }

  }
}
