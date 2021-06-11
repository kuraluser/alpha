import { Injectable } from '@angular/core';
import { IDataTableColumn } from '../../../shared/components/datatable/datatable.model';
import { QUANTITY_UNIT } from '../../../shared/models/common.model';
import { AppConfigurationService } from '../../../shared/services/app-configuration/app-configuration.service';
import { IBallastStowageDetails, IBallastTank } from '../../core/models/common.model';
import { ICommingleDetails } from '../models/cargo-planning.model';

@Injectable({
  providedIn: 'root'
})
export class LoadableStudyPatternTransformationService {

  constructor() { }


  /**
* Method for setting commingle details grid columns
*
* @returns {IDataTableColumn[]}
* @memberof LoadableStudyPatternTransformationService
*/
  getCommingleDetailsDatatableColumns(): IDataTableColumn[] {
    return [
      {
        field: 'grade',
        header: 'LOADABLE_PATTERN_GRADE'
      },
      {
        field: 'tankShortName',
        header: 'LOADABLE_PATTERN_TANK'
      },
      {
        field: 'quantity',
        header: 'LOADABLE_PATTERN_TOTAL_QUANTITY'
      },
      {
        field: 'api',
        header: 'LOADABLE_PATTERN_API'
      },
      {
        field: 'temperature',
        header: 'LOADABLE_PATTERN_TEMP'
      },
      {
        field: '',
        header: 'LOADABLE_PATTERN_COMPOSITION_BREAKDOWN',
        fieldColumnClass: 'commingle-composition colspan-header',
        columns: [
          {
            field: 'cargoPercentage',
            header: 'LOADABLE_PATTERN_PERCENTAGE',
            fieldClass: 'commingle-composition-percentage'
          },
          {
            field: 'cargoQuantity',
            header: 'LOADABLE_PATTERN_QUANTITY',
            fieldClass: 'commingle-composition-quantity'
          }
        ]
      }

    ]
  }

  /**
  * Method for transforming commingled data
  *
  * @param {ICommingleDetails} commingleDetail
  * @returns {ICommingleDetails}
  * @memberof LoadableStudyPatternTransformationService
  */
  formatCommingleDetail(decimalPipe: any ,commingleDetail: ICommingleDetails): ICommingleDetails {
    commingleDetail.quantity = this.decimalConvertion(decimalPipe , commingleDetail.quantity , '1.2-2');
    commingleDetail.api = this.decimalConvertion(decimalPipe , commingleDetail.api , '1.2-2');
    commingleDetail.temperature = this.decimalConvertion(decimalPipe , commingleDetail.temperature , '1.2-2');
    commingleDetail.cargoQuantity = this.decimalConvertion(decimalPipe , commingleDetail.cargo1Quantity , '1.2-2') + '\n' + this.decimalConvertion(decimalPipe , commingleDetail.cargo2Quantity , '1.2-2');
    commingleDetail.cargoPercentage = commingleDetail.cargo1Abbreviation + '-' + commingleDetail.cargo1Percentage + '%\n' + commingleDetail.cargo2Abbreviation + '-' + commingleDetail.cargo2Percentage + '%';
    return commingleDetail;
  }

    /**
* Method for setting priority case grid columns
*
* @returns {IDataTableColumn[]}
* @memberof LoadableStudyPatternTransformationService
*/
getCargoPriorityGridCaseTableColumn(): IDataTableColumn[] {
  return [
    {
      field: 'priority',
      header: 'CARGO_PRIORITY_GRID_HEADER_PRIORITY'
    },
    {
      field: 'cargoAbbreviation',
      header: 'CARGO_PRIORITY_GRID_HEADER_GRADE'
    },
    {
      field: 'quantity',
      header: 'CARGO_PRIORITY_GRID_HEADER_QUANTITY'
    },
    {
      field: 'difference',
      header: 'CARGO_PRIORITY_GRID_HEADER_TOLERENCE'
    }
  ]
}


    /**
* Method for setting priority more grid columns
*
* @returns {IDataTableColumn[]}
* @memberof LoadableStudyPatternTransformationService
*/
getCargoPriorityGridMoreTableColumn(): IDataTableColumn[] {
  return [
    {
      field: 'cargoAbbreviation',
      header: 'CARGO_PRIORITY_GRID_HEADER_GRADE'
    },
    {
      field: 'quantity',
      header: 'CARGO_PRIORITY_GRID_HEADER_QUANTITY'
    }
  ]
}

  /**
* Method for setting commingle details grid columns
*
* @returns {IDataTableColumn[]}
* @memberof LoadableStudyPatternTransformationService
*/
  getStabilityDatatableColumns(): IDataTableColumn[] {
    return [
      {
        field: '',
        header: 'DRAFT',
        fieldColumnClass: "colspan-header",
        columns: [
          {
            field: 'forwardDraft',
            header: 'Fore'
          },
          {
            field: 'meanDraft',
            header: 'Mid'
          },
          {
            field: 'afterDraft',
            header: 'Aft'
          }
        ]
      },
      {
        field: 'trim',
        header: 'Trim'
      },
      {
        field: 'bendinMoment',
        header: 'Bending Moment'
      },
      {
        field: 'shearForce',
        header: 'Shear Force'
      }
    ]
  }

  /**
* Method for setting cargo to be loaded grid columns
*
* @returns {IDataTableColumn[]}
* @memberof LoadableStudyPatternTransformationService
*/
  getCargotobeLoadedDatatableColumns(quantityUnit: QUANTITY_UNIT): IDataTableColumn[] {
    const quantityNumberFormat = AppConfigurationService.settings['quantityNumberFormat' + quantityUnit];

    return [
      {
        field: 'grade',
        header: 'CARGO_TO_BE_LOADED_KIND_OF_CARGO'
      },
      {
        field: 'estimatedAPI',
        header: 'CARGO_TO_BE_LOADED_API',
        numberFormat: '1.2-2'
      },
      {
        field: 'estimatedTemp',
        header: 'CARGO_TO_BE_LOADED_TEMP',
        numberFormat: '1.2-2'
      },
      {
        field: 'loadingPort',
        header: 'CARGO_TO_BE_LOADED_LOADING_PORT'
      },
      {
        field: 'orderedQuantity',
        header: 'CARGO_TO_BE_LOADED_NOMINATION',
        numberFormat: quantityNumberFormat
      },
      {
        field: 'minMaxTolerance',
        header: 'CARGO_TO_BE_LOADED_MIN_MAX_TOLERANCE'
      },
      {
        field: 'loadableMT',
        header: 'CARGO_TO_BE_LOADED_SHIP_LOADABLE',
        numberFormat: quantityNumberFormat
      },
      {
        field: 'differencePercentage',
        header: 'CARGO_TO_BE_LOADED_DIFFERENCE'
      },
      {
        field: 'timeRequiredForLoading',
        header: 'CARGO_TO_BE_LOADED_TIME_REQUIRED'
      },
      {
        field: 'slopQuantity',
        header: 'CARGO_TO_BE_LOADED_SLOP_QTY',
        numberFormat: quantityNumberFormat
      }
    ]
  }

  /**
*
* Get synoptical table header
* @returns {IDataTableColumn[]}
* @memberof LoadableStudyPatternTransformationService
*/
  getSynopticalRecordTableColumn(): IDataTableColumn[] {
    return [
      {
        field: 'finalDraftFwd', header: 'PATEERN_SYNOPTICAL_HEADER_DRAFT', rowspan: 3, subHeader: 'PATEERN_SYNOPTICAL_HEADER_DRAFT_FORE'
      },
      { field: 'finalDraftAft', header: "", subHeader: 'PATEERN_SYNOPTICAL_HEADER_DRAFT_AFT' },
      { field: 'finalDraftMid', header: "", subHeader: 'PATEERN_SYNOPTICAL_HEADER_DRAFT_MSHIP' },
      { field: 'calculatedTrimPlanned', header: 'PATEERN_SYNOPTICAL_HEADER_TRIM' }
    ]
  }

  /**
   * Method for formatting ballast tanks data
   *
   * @param {IBallastTank} ballastTank
   * @param {IBallastStowageDetails[]} ballastTankDetails
   * @returns {IBallastTank}
   * @memberof LoadableStudyPatternTransformationService
   */
  formatBallastTanks(ballastTank: IBallastTank, ballastTankDetails: IBallastStowageDetails[]): IBallastTank {
    ballastTank.commodity = ballastTankDetails?.find(ballast => ballastTank?.id === ballast?.tankId);

    return ballastTank;
  }

  /**
  * Method for formatting ballast stowage details
  *
  * @param  _decimalPipe
  * @param {IBallastStowageDetails} ballast
  * @returns {IBallastStowageDetails}
  * @memberof LoadableStudyPatternTransformationService
  */
  getFormattedBallastDetails(_decimalPipe, ballast: IBallastStowageDetails): IBallastStowageDetails {
    const newBallast = <IBallastStowageDetails>JSON.parse(JSON.stringify(ballast))
    newBallast.cubicMeter = (Number(newBallast.metricTon) / Number(newBallast.sg)).toFixed(2);
    if (newBallast.fullCapacityCubm) {
      newBallast.percentage = (Number(newBallast.cubicMeter) / Number(newBallast.fullCapacityCubm) * 100).toString();
      newBallast.percentage = this.decimalConvertion(_decimalPipe, newBallast.percentage, "1.2-2");
    } else {
      newBallast.percentage = "0.00"
    }
    newBallast.cubicMeter = this.decimalConvertion(_decimalPipe, newBallast.cubicMeter, "1.2-2");
    return newBallast
  }

  /**
*
* Get Formated Loadable Quantity Data
* @returns {decimal converted value us number}
*/
  decimalConvertion(_decimalPipe: any, value: string | number, decimalType: string) {
    return _decimalPipe.transform(value, decimalType);
  }

  /**
   * parse number from formatted string
   * @returns {number}
  */
  convertToNumber(value: string) {
    value = value?.replace(/,/g, '');
    return Number(value)
  }
}


