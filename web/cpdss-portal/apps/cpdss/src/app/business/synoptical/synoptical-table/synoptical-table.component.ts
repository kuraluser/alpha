import { Component, OnDestroy, OnInit } from '@angular/core';
import { AbstractControl, FormArray, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { Subject, Subscription } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { DATATABLE_FIELD_TYPE } from '../../../shared/components/datatable/datatable.model';
import { IValidationErrorMessages } from '../../../shared/components/validation-error/validation-error.model';
import { numberValidator } from '../../cargo-planning/directives/validator/number-validator.directive';
import { ISynopticalRecords, SynopticalColumn, SynopticalDynamicColumn, SynopticField } from '../models/synoptical-table.model';
import { SynopticalApiService } from '../services/synoptical-api.service';
import { SynopticalService } from '../services/synoptical.service';
import * as XLSX from 'xlsx';

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


  listData = {};
  dynamicColumns: SynopticalDynamicColumn[];
  headerColumn: SynopticalColumn;
  tableForm: FormArray = new FormArray([]);
  errorMessages: IValidationErrorMessages = {
    'required': 'SYNOPTICAL_REQUIRED',
    'invalid': 'SYNOPTICAL_INVALID',
    'fromMax': 'SYNOPTICAL_FROM_MAX',
    'toMin': 'SYNOPTICAL_TO_MIN',
    'timeFromMax': 'SYNOPTICAL_TIME_FROM_MAX',
    'timeToMin': 'SYNOPTICAL_TIME_TO_MIN',
    'etaMin': "SYNOPTICAL_ETA_MIN",
    'etdMin': 'SYNOPTICAL_ETD_MIN',
    'etaMax': 'SYNOPTICAL_ETA_MAX',
    'etdMax': "SYNOPTICAL_ETD_MAX",
  };
  editMode = false;
  expandedRows = [];
  ngUnsubscribe: Subject<void> = new Subject();

  constructor(
    private synoticalApiService: SynopticalApiService,
    private route: ActivatedRoute,
    private ngxSpinner: NgxSpinnerService,
    private synopticalService: SynopticalService
  ) {
  }

  /**
  * Component lifecycle ngOnit
  *
  * @returns {Promise<void>}
  * @memberof SynopticalTableComponent
  */
  ngOnInit() {
    this.synopticalService.onInitCompleted$
      .pipe(takeUntil(this.ngUnsubscribe))
      .subscribe(completed => {
        if (completed) {
          this.route.params
            .pipe(takeUntil(this.ngUnsubscribe))
            .subscribe(async route => {
              this.synopticalService.loadableStudyId = Number(route.loadableStudyId);
              this.synopticalService.vesselId = Number(route.vesselId);
              this.synopticalService.voyageId = Number(route.voyageId);
              if (route.loadablePatternId) {
                this.synopticalService.loadablePatternId = Number(route.loadablePatternId);
              }
              await this.synopticalService.setSelectedVoyage();
              this.initData();
            });
        }
      })
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
  }

  /**
   * Function to initalize data
   *
   * @returns {Promise<void>}
   * @memberof SynopticalTableComponent
   */
  async initData(): Promise<void> {
    this.ngxSpinner.show()
    this.initColumns();
    this.initDynamicColumns();
    this.setColumnHeader();
    const result = await this.synoticalApiService.getSynopticalTable(this.synopticalService.vesselId, this.synopticalService.voyageId, this.synopticalService.loadableStudyId, this.synopticalService.loadablePatternId).toPromise();
    if (result.responseStatus.status === "200") {
      this.synopticalRecords = result.synopticalRecords ?? [];
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
        header: 'Distance',
        editable: true,
      },
      {
        fields: [{
          key: 'speed',
          type: this.fieldType.NUMBER,
          validators: ['dddd.dd.+']
        }],
        header: 'Speed',
        editable: true,
      },
      {
        fields: [{
          key: 'runningHours',
          type: this.fieldType.NUMBER,
        }],
        header: 'Running Hours',
        editable: true,
      },
      {
        header: 'ETA/ETD',
        subHeaders: [{
          header: '',
          subHeaders: [
            {
              header: "Plan",
              fields: [{
                key: 'etaEtdPlanned',
                type: this.fieldType.DATETIME,
                validators: ['required']
              }],
              editable: !this.checkIfConfirmed(),
            },
            {
              header: "Actual",
              fields: [{
                key: 'etaEtdActual',
                type: this.fieldType.DATETIME,
                validators: ['required']
              }],
              editable: this.checkIfConfirmed(),
            },
          ]
        }
        ]
      },
      {
        header: 'In Port Hours',
        fields: [{
          key: 'inPortHours',
          type: this.fieldType.NUMBER,
          validators: ['dddd.dd.+']
        }],
        editable: true,
      },
      {
        header: 'Time of Sunrise',
        fields: [{ key: 'timeOfSunrise' }],
        editable: true,
      },
      {
        header: 'Time of Sunset',
        fields: [{ key: 'timeOfSunset' }],
        editable: true,
      },
      {
        header: 'Seawater Specific Gravity',
        fields: [{
          key: 'specificGravity',
          type: this.fieldType.NUMBER,
          validators: ['dddd.dd.+']
        }],
        editable: this.checkIfConfirmed(),
      },
      {
        header: 'H.W Tide & Time',
        subHeaders: [{
          header: '',
          subHeaders: [
            {
              header: "H.W Tide",
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
                  type: this.fieldType.TIME
                },
                {
                  key: 'hwTideTimeTo',
                  type: this.fieldType.TIME
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
              header: "L.W Tide",
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
                  type: this.fieldType.TIME
                },
                {
                  key: 'lwTideTimeTo',
                  type: this.fieldType.TIME
                }
              ],
              editable: true,
            },
          ]
        }
        ]
      },
      {
        header: "Final Draft",
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
            header: "Calculated Draft",
            subHeaders: [
              {
                header: "FWD",
                subHeaders: [
                  {
                    header: "Plan",
                    fields: [{
                      key: "calculatedDraftFwdPlanned",
                      type: this.fieldType.NUMBER,
                      validators: ['required']
                    }],
                    editable: !this.checkIfConfirmed(),
                    editableIfValue: true
                  },
                  {
                    header: "Actual",
                    fields: [{
                      key: "calculatedDraftFwdActual",
                      type: this.fieldType.NUMBER,
                      validators: ['required']
                    }],
                    editable: this.checkIfConfirmed(),
                    editableIfValue: true
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
                      validators: ['required']
                    }],
                    editable: !this.checkIfConfirmed(),
                    editableIfValue: true
                  },
                  {
                    header: "Actual",
                    fields: [{
                      key: "calculatedDraftAftActual",
                      type: this.fieldType.NUMBER,
                      validators: ['required']
                    }],
                    editable: this.checkIfConfirmed(),
                    editableIfValue: true
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
                      validators: ['required']
                    }],
                    editable: !this.checkIfConfirmed(),
                    editableIfValue: true
                  },
                  {
                    header: "Actual",
                    fields: [{
                      key: "calculatedDraftMidActual",
                      type: this.fieldType.NUMBER,
                      validators: ['required']
                    }],
                    editable: this.checkIfConfirmed(),
                    editableIfValue: true
                  },
                ]
              },
            ]
          },
          {
            header: "Calculated Trim",
            subHeaders: [
              {
                header: "Plan",
                fields: [{
                  key: "calculatedTrimPlanned",
                  type: this.fieldType.NUMBER,
                  validators: ['required']
                }],
                editable: !this.checkIfConfirmed(),
                editableIfValue: true
              },
              {
                header: "Actual",
                fields: [{
                  key: "calculatedTrimActual",
                  type: this.fieldType.NUMBER,
                  validators: ['required']
                }],
                editable: this.checkIfConfirmed(),
                editableIfValue: true
              },
            ]
          }
        ]
      },
      {
        header: "Blind Sector",
        fields: [{
          key: "blindSector"
        }],
        editable: true,
      },
      {
        header: 'Cargo',
        dynamicKey: 'cargos',
        expandable: true,
        subHeaders: [
          {
            header: "",
            subHeaders: [
              {
                fields: [{ key: 'cargoPlannedTotal' }],
                header: 'Plan'
              },
              {
                fields: [{ key: 'cargoActualTotal' }],
                header: 'Actual',
              },
            ]
          }
        ],
        expandedFields: []
      },
      {
        header: 'Total F.O',
        dynamicKey: 'foList',
        expandable: true,
        subHeaders: [
          {
            header: "",
            subHeaders: [
              {
                header: 'Plan',
                fields: [{ key: 'plannedFOTotal' }],
              },
              {
                header: 'Actual',
                fields: [{ key: 'actualFOTotal' }],
              },
            ]
          }
        ],
        expandedFields: []
      },
      {
        header: 'Total D.O',
        dynamicKey: 'doList',
        expandable: true,
        subHeaders: [
          {
            header: "",
            subHeaders: [
              {
                header: 'Plan',
                fields: [{ key: 'plannedDOTotal', }],
              },
              {
                header: 'Actual',
                fields: [{ key: 'actualDOTotal', }],
              },
            ]
          }
        ],
        expandedFields: []
      },
      {
        header: 'Fresh W.T',
        dynamicKey: 'fwList',
        expandable: true,
        subHeaders: [
          {
            header: "",
            subHeaders: [
              {
                header: 'Plan',
                fields: [{ key: 'plannedFWTotal' }],
              },
              {
                header: 'Actual',
                fields: [{ key: 'actualFWTotal' }],
              },
            ]
          }
        ],
        expandedFields: []
      },
      {
        header: 'Lube Oil Total',
        dynamicKey: 'lubeList',
        expandable: true,
        subHeaders: [
          {
            header: "",
            subHeaders: [
              {
                header: 'Plan',
                fields: [{ key: 'plannedLubeTotal' }],
              },
              {
                header: 'Actual',
                fields: [{ key: 'actualLubeTotal' }],
              },
            ]
          }
        ],
        expandedFields: []
      },
      {
        header: "Ballast",
        subHeaders: [
          {
            header: "",
            subHeaders: [
              {
                header: "Plan",
                fields: [{
                  key: "ballastPlanned",
                  type: this.fieldType.NUMBER
                }],
                editable: false
              },
              {
                header: "Actual",
                fields: [{
                  key: "ballastActual",
                  type: this.fieldType.NUMBER
                }],
                editable: this.checkIfConfirmed(),
              },
            ]
          }
        ]
      },
      {
        header: 'Others',
        subHeaders: [{
          header: '',
          subHeaders: [
            {
              header: "Plan",
              fields: [{
                key: 'othersPlanned',
                type: this.fieldType.NUMBER,
                validators: ['required', 'ddddddd.+']
              }],
              editable: !this.checkIfConfirmed(),
            },
            {
              header: "Actual",
              fields: [{
                key: 'othersActual',
                type: this.fieldType.NUMBER,
                validators: ['required', 'ddddddd.+']
              }],
              editable: this.checkIfConfirmed(),
            },
          ]
        }
        ]
      },
      {
        header: 'Constant',
        subHeaders: [{
          header: '',
          subHeaders: [
            {
              header: "Plan",
              fields: [{
                key: 'constantPlanned',
                type: this.fieldType.NUMBER,
                validators: ['required']
              }],
              editable: true,
            },
            {
              header: "Actual",
              fields: [{
                key: 'constantActual',
                type: this.fieldType.NUMBER,
              }],
              editable: this.checkIfConfirmed(),
            },
          ]
        }
        ]
      },
      {
        header: 'Total DWT',
        subHeaders: [{
          header: '',
          subHeaders: [
            {
              header: "Plan",
              fields: [{
                key: 'totalDwtPlanned',
                type: this.fieldType.NUMBER,
                validators: ['required']
              }],
              editable: true,
            },
            {
              header: "Actual",
              fields: [{
                key: 'totalDwtActual',
                type: this.fieldType.NUMBER,
              }],
              editable: this.checkIfConfirmed(),
            },
          ]
        }
        ]
      },
      {
        header: 'Displacement',
        subHeaders: [{
          header: '',
          subHeaders: [
            {
              header: "Plan",
              fields: [{
                key: 'displacementPlanned',
                type: this.fieldType.NUMBER,
                validators: ['required']
              }],
              editable: !this.checkIfConfirmed(),
            },
            {
              header: "Actual",
              fields: [{
                key: 'displacementActual',
                type: this.fieldType.NUMBER,
              }],
              editable: this.checkIfConfirmed(),
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
        subHeaders: [
          {
            fields: [{
              key: 'plannedWeight',
              type: this.fieldType.NUMBER,
              validators: ['required', 'ddddddd.+']
            }],
            header: 'Plan',
            editable: !this.checkIfConfirmed(),
          },
          {
            header: 'Actual',
            fields: [{
              key: 'actualWeight',
              type: this.fieldType.NUMBER,
              validators: ['required', 'ddddddd.+']
            }],
            editable: this.checkIfConfirmed(),
          },
        ],
      },
      {
        fieldKey: 'foList',
        primaryKey: 'tankId',
        headerLabel: 'tankName',
        subHeaders: [
          {
            fields: [{
              key: 'plannedWeight',
              type: this.fieldType.NUMBER,
              validators: ['required', 'ddddddd.+']
            }],
            header: 'Plan',
            editable: !this.checkIfConfirmed(),

          },
          {
            fields: [{
              key: 'actualWeight',
              type: this.fieldType.NUMBER,
              validators: ['required', 'ddddddd.+']
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
        subHeaders: [
          {
            fields: [{
              key: 'plannedWeight',
              type: this.fieldType.NUMBER,
              validators: ['required', 'ddddddd.+']
            }],
            header: 'Plan',
            editable: !this.checkIfConfirmed()

          },
          {
            fields: [{
              key: 'actualWeight',
              type: this.fieldType.NUMBER,
              validators: ['required', 'ddddddd.+']
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
        subHeaders: [
          {
            fields: [{
              key: 'plannedWeight',
              type: this.fieldType.NUMBER,
              validators: ['required', 'ddddddd.+']
            }],
            header: 'Plan',
            editable: !this.checkIfConfirmed(),

          },
          {
            fields: [{
              key: 'actualWeight',
              type: this.fieldType.NUMBER,
              validators: ['required', 'ddddddd.+']
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
        subHeaders: [
          {
            fields: [{
              key: 'plannedWeight',
              type: this.fieldType.NUMBER,
              validators: ['required', 'ddddddd.+']
            }],
            header: 'Plan',
            editable: !this.checkIfConfirmed(),

          },
          {
            fields: [{
              key: 'actualWeight',
              type: this.fieldType.NUMBER,
              validators: ['required', 'ddddddd.+']
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
              subHeaders: subHeaders
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
    const keys = dynamicColumn.subHeaders;
    this.listData[fieldKey] = [];
    this.synopticalRecords.forEach(synopticalRecord => {
      if (synopticalRecord[fieldKey]) {
        synopticalRecord[fieldKey].forEach(record => {
          const index = this.listData[fieldKey].findIndex(item => item.id === record[primaryKey]);
          if (index < 0) {
            this.listData[fieldKey].push({
              id: record[primaryKey],
              header: record[headerLabel]
            })
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
    this.headerColumn = this.cols.splice(0, 1)[0];
  }

  /**
 * Function to initalize form
 *
 * @returns {Promise<void>}
 * @memberof SynopticalTableComponent
 */
  initForm() {
    const columns = this.getAllColumns(this.cols);

    this.synopticalRecords.forEach((record, colIndex) => {
      const fg = new FormGroup({})
      columns.forEach(column => {
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
    else if (/^(d*(.d)?d*(.\+)?)$/.test(validator)) {
      let decimals = 0;
      let isNegativeAccepted = true;
      const arr = validator.split('.')
      const digits = arr[0].length;
      if (arr.length > 2)
        decimals = arr[1].length;
      if (/\+/.test(validator))
        isNegativeAccepted = false;
      return numberValidator(decimals, digits, isNegativeAccepted);
    }
    else
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
          return this.convertToDate(value)
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
 * Method to update other fields on keyup of an input
 *
 * @param {SynopticField} field
 * @param {number} colIndex
 * @returns {any}
 * @memberof SynopticalTableComponent
*/
  onKeyUp(field: SynopticField, colIndex: number) {
    const fc = this.getControl(colIndex, field.key)
    const operationType = this.synopticalRecords[colIndex].operationType;
    const otherIndex = operationType === 'ARR' ? colIndex + 1 : colIndex - 1;
    switch (field.key) {
      case 'speed': case 'distance': case 'runningHours':
      case 'inPortHours':
        if (otherIndex >= 0 && otherIndex < this.synopticalRecords.length) {
          this.getControl(otherIndex, field.key)?.setValue(fc.value)
        }
        break;
      default:
        break;
    }
  }
  /**
   * Method to do validations on focusing out of an input
   *
   * @param {SynopticField} field
   * @param {number} colIndex
   * @returns {any}
   * @memberof SynopticalTableComponent
  */
  onBlur(field: SynopticField, colIndex: number) {
    const fc = this.getControl(colIndex, field.key)
    const operationType = this.synopticalRecords[colIndex].operationType;
    const otherIndex = operationType === 'ARR' ? colIndex + 1 : colIndex - 1;
    if (fc.invalid)
      return;
    let fcMax, fcMin;
    switch (field.key) {
      case 'hwTideFrom':
        fcMax = this.getControl(colIndex, 'hwTideTo')
        if (fc.value > fcMax.value) {
          fc.setErrors({ fromMax: true })
        } else if (fcMax.hasError('toMin')) {
          fcMax.setValue(fcMax.value, { emitEvent: false })
        }
        break;
      case 'hwTideTo':
        fcMin = this.getControl(colIndex, 'hwTideFrom')
        if (fc.value < fcMin.value) {
          fc.setErrors({ toMin: true })
        } else if (fcMin.hasError('fromMax')) {
          fcMin.setValue(fcMin.value, { emitEvent: false })
        }
        break;
      case 'lwTideFrom':
        fcMax = this.getControl(colIndex, 'lwTideTo')
        if (fc.value > fcMax.value) {
          fc.setErrors({ fromMax: true })
        } else if (fcMax.hasError('toMin')) {
          fcMax.setValue(fcMax.value, { emitEvent: false })
        }
        break;
      case 'lwTideTo':
        fcMin = this.getControl(colIndex, 'lwTideFrom')
        if (fc.value < fcMin.value) {
          fc.setErrors({ toMin: true })
        } else if (fcMin.hasError('fromMax')) {
          fcMin.setValue(fcMin.value, { emitEvent: false })
        }
        break;
      case 'hwTideTimeFrom':
        fcMax = this.getControl(colIndex, 'hwTideTimeTo')
        if (fc.value > fcMax.value) {
          fc.setErrors({ timeFromMax: true })
        } else if (fcMax.hasError('timeToMin')) {
          fcMax.setValue(fcMax.value, { emitEvent: false })
        }
        break;
      case 'hwTideTimeTo':
        fcMin = this.getControl(colIndex, 'hwTideTimeFrom')
        if (fc.value < fcMin.value) {
          fc.setErrors({ timeToMin: true })
        } else if (fcMin.hasError('timeFromMax')) {
          fcMin.setValue(fcMin.value, { emitEvent: false })
        }
        break;
      case 'lwTideTimeFrom':
        fcMax = this.getControl(colIndex, 'lwTideTimeTo')
        if (fc.value > fcMax.value) {
          fc.setErrors({ timeFromMax: true })
        } else if (fcMax.hasError('timeToMin')) {
          fcMax.setValue(fcMax.value, { emitEvent: false })
        }
        break;
      case 'lwTideTimeTo':
        fcMin = this.getControl(colIndex, 'lwTideTimeFrom')
        if (fc.value < fcMin.value) {
          fc.setErrors({ timeToMin: true })
        } else if (fcMin.hasError('timeFromMax')) {
          fcMin.setValue(fcMin.value, { emitEvent: false })
        }
        break;
      case 'etaEtdPlanned': case 'etaEtdActual':
        if (colIndex > 0) {
          fcMin = this.getControl(colIndex - 1, field.key)
          if (fc.value < fcMin.value) {
            if (this.synopticalRecords[colIndex].operationType === 'ARR')
              fc.setErrors({ etaMin: true })
            else
              fc.setErrors({ etdMin: true })
          } else if (fcMin.hasError('etaMax') || fcMin.hasError('etdMax')) {
            fcMin.setValue(fcMin.value, { emitEvent: false })
          }
        }
        if (colIndex < this.synopticalRecords.length - 1) {
          fcMax = this.getControl(colIndex + 1, field.key)
          if (fc.value > fcMax.value) {
            if (this.synopticalRecords[colIndex].operationType === 'ARR')
              fc.setErrors({ etaMax: true })
            else
              fc.setErrors({ etdMax: true })
          } else if (fcMax.hasError('etaMin') || fcMax.hasError('etdMin')) {
            fcMax.setValue(fcMax.value, { emitEvent: false })
          }
        }
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
        break;
      case 'speed': case 'distance':
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
        break;
      default:
        break;
    }
  }

  /**
   * Method to check if the loadable study is confirmed
   *
   * @returns {boolean}
   * @memberof SynopticalTableComponent
  */
  checkIfConfirmed(): boolean {
    return this.synopticalService.selectedLoadableStudy?.status === "Confirmed" ?? false
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

  exportExcelFromTable(): void {
    const fileName= 'synoptic.xlsx';
    const expandableRows = ['Final Draft', 'Cargo', 'Total F.O', 'Total D.O', 'Fresh W.T', 'Lube Oil Total'];
    const expanded = {...this.expandedRows}
    expandableRows.forEach(key => {
      this.expandedRows[key] = true;
    });
    setTimeout(()=>{
      /* table id is passed over here */
      const element = document.getElementById('synoptic-table');
      const ws: XLSX.WorkSheet = XLSX.utils.table_to_sheet(element);

      /* setting back all the expanded rows */
      this.expandedRows = expanded;

      /* Removing unwanted strings from the values */
      Object.keys(ws).forEach(key => {
        if(ws[key].t && ws[key].t === 's' && ws[key].v){
          let v = String(ws[key].v)
          v = v.replace('&nbsp;','');
          ws[key].v = v;
        }
      })


      /* generate workbook and add the worksheet */
      const wb: XLSX.WorkBook = XLSX.utils.book_new();
      XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');
      
      /* save to file */
      XLSX.writeFile(wb, fileName);
    },1000)
  }
}
