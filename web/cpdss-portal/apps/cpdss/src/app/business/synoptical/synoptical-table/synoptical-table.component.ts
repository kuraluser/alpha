import { Component, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ISynopticalRecords, SynopticalColumn, SynopticalDynamicColumn } from '../models/synoptical-table.model';
import { SynopticalApiService } from '../services/synoptical-api.service';

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
export class SynopticalTableComponent {
  synopticalRecords: ISynopticalRecords[] = [];
  cols: SynopticalColumn[];
  maxSubRowLevel = 2;

  @Input() loadableStudyId: number;
  @Input() vesselId: number;
  @Input() voyageId: number;

  listData = {};
  dynamicColumns: SynopticalDynamicColumn[];
  headerColumn: SynopticalColumn;

  constructor(
    private synoticalApiService: SynopticalApiService,
    private route: ActivatedRoute,
    private ngxSpinner: NgxSpinnerService
  ) {
    this.route.params
      .subscribe(route => {
        this.loadableStudyId = route.loadableStudyId;
        this.vesselId = route.vesselId;
        this.voyageId = route.voyageId;
        this.initData();
      });
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
    const result = await this.synoticalApiService.getSynopticalTable(this.vesselId, this.voyageId, this.loadableStudyId).toPromise();
    if (result.responseStatus.status === "200") {
      this.synopticalRecords = result.synopticalRecords;
      this.dynamicColumns.forEach(dynamicColumn => {
        this.listData[dynamicColumn.listKey] = result[dynamicColumn.listKey];
        this.formatData(dynamicColumn)
        this.addToColumns(dynamicColumn);
      })
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
      { field: 'portName', header: 'Ports' },
      { field: 'operationType', header: 'ARR OR DEP' },
      { field: 'distance', header: 'Distance' },
      { field: 'speed', header: 'Speed' },
      { field: 'runningHours', header: 'Running Hours' },
      // {
      //   header: 'ETA/ETD',
      //   subHeaders: [{
      //     header: '',
      //     subHeaders: [
      //       { header: "Estimated", field: 'eta' },
      //       { header: "Actual", field: 'etd' },
      //     ]
      //   }
      //   ]
      // },
      { field: 'inPortHours', header: 'In Port Hours' },
      { field: 'timeOfSunrise', header: 'Time of Sunrise' },
      { field: 'timeOfSunset', header: 'Time of Sunset' },
      { field: 'timeOfSunrise', header: 'Time of Sunrise' },
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
        listKey: 'cargoTanks',
        fieldKey: 'cargos',
        primaryKey: 'tankId',
        subHeaders: [
          { field: 'plannedWeight', header: 'Plan' },
          { field: 'actualWeight', header: 'Actual' },
        ],
        column: {
          header: 'Cargo',
          expandable: true,
          subHeaders: [
            {
              header: "",
              subHeaders: [
                { field: 'cargoPlannedTotal', header: 'Plan' },
                { field: 'cargoActualTotal', header: 'Actual' },
              ]
            }
          ],
          expandedFields: []
        }
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
    const listKey = dynamicColumn.listKey;
    let col = dynamicColumn.column;
    this.listData[listKey].forEach(item => {
      let subHeaders = [];
      keys.forEach(key => {
        subHeaders.push({
          header: key.header,
          field: fieldKey + item.id + key.field
        })
      });
      col.expandedFields.push(
        {
          header: "",
          subHeaders: [
            {
              header: item.shortName,
              subHeaders: subHeaders
            }
          ],
        }
      );
    })
    this.cols.push(col)
  }

  /**
   * Method to convert data from array format to keys
   * @param dynamicColumn
   * @returns {void}
   * @memberof SynopticalTableComponent
   */
  formatData(dynamicColumn: SynopticalDynamicColumn) {
    const listKey = dynamicColumn.listKey;
    const fieldKey = dynamicColumn.fieldKey;
    const primaryKey = dynamicColumn.primaryKey;
    const keys = dynamicColumn.subHeaders;
    this.synopticalRecords.forEach(synopticalRecord => {
      if (synopticalRecord[fieldKey]) {
        synopticalRecord[fieldKey].forEach(record => {
          keys.forEach(key => {
            synopticalRecord[fieldKey + record[primaryKey] + key.field] = record[key.field];
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
}
