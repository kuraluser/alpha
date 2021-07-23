import { Injectable } from '@angular/core';
import { DATATABLE_FIELD_TYPE, DATATABLE_FILTER_MATCHMODE, DATATABLE_FILTER_TYPE, IDataTableColumn } from 'apps/cpdss/src/app/shared/components/datatable/datatable.model';
import { ValueObject } from '../../../../../shared/models/common.model';
import { IPermission } from 'apps/cpdss/src/app/shared/models/user-profile.model';
import { AppConfigurationService } from 'apps/cpdss/src/app/shared/services/app-configuration/app-configuration.service';
import { TimeZoneTransformationService } from 'apps/cpdss/src/app/shared/services/time-zone-conversion/time-zone-transformation.service';
import { IPortAllDropdownData, OPERATIONS } from '../../../../cargo-planning/models/cargo-planning.model';
import { IPortRotationValueObject } from '../port-rotation-popup.model';
import { IOperations, IPort, IPortList } from '../../../../core/models/common.model';
import * as moment from 'moment';

/**
 * Transformation Service for Port rotation pop up module
 *
 * @export
 * @class PortRotationPopupTransformationService
 */
@Injectable()
export class PortRotationPopupTransformationService {

  constructor(private timeZoneTransformationService: TimeZoneTransformationService) { }



  /**
 * Method for setting ports grid columns
 *
 * @returns {IDataTableColumn[]}
 * @memberof PortRotationPopupTransformationService
 */
  getPortDatatableColumns(): IDataTableColumn[] {
    const minDate = new Date();
    return [
      {
        field: 'slNo',
        header: 'PORT_ORDER',
        fieldType: DATATABLE_FIELD_TYPE.SLNO,
        sortable: false,
        fieldHeaderClass: 'column-portOrder',
        fieldClass: 'sl'
      },
      {
        field: 'port',
        header: 'PORT',
        fieldType: DATATABLE_FIELD_TYPE.SELECT,
        filter: true,
        filterPlaceholder: 'SEARCH_PORT',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        listName: 'portList',
        listFilter: true,
        sortable: false,
        filterField: 'port.value.name',
        fieldOptionLabel: 'name',
        fieldPlaceholder: 'SELECT_PORT',
        virtualScroll: true,
        errorMessages: {
          'required': 'PORT_FIELD_REQUIRED_ERROR',
          'duplicate': 'PORT_FIELD_DUPLICATE_ERROR',
          'transitDuplicate': 'PORT_TRANSIT_DUPLICATE_ERROR'
        }
      },
      {
        field: 'portcode',
        header: 'PORT CODE',
        fieldType: DATATABLE_FIELD_TYPE.TEXT,
        filter: true,
        filterPlaceholder: 'SEARCH_PORT_CODE',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'portcode.value',
        fieldPlaceholder: 'PORT_CODE',
        errorMessages: {
          'required': 'PORT_CODE_REQUIRED_ERROR'
        }
      },
      {
        field: 'operation',
        header: 'OPERATIONS',
        fieldType: DATATABLE_FIELD_TYPE.SELECT,
        filter: true,
        filterPlaceholder: 'SEARCH_PORT_OPERATIONS',
        filterType: DATATABLE_FILTER_TYPE.TEXT,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        listName: 'operationList',
        filterField: 'operation.value.operationName',
        fieldOptionLabel: 'operationName',
        fieldPlaceholder: 'SELECT_OPERATION',
        errorMessages: {
          'required': 'PORT_OPERATIONS_REQUIRED_ERROR',
          'duplicate': 'PORT_FIELD_DUPLICATE_ERROR',
          'transitDuplicate': 'PORT_TRANSIT_DUPLICATE_ERROR'
        }
      },
      {
        field: 'layCan',
        header: 'LAY-CAN',
        fieldHeaderClass: 'column-laycan',
        fieldType: DATATABLE_FIELD_TYPE.DATERANGE,
        filter: false,
        minDate: minDate,
        fieldPlaceholder: 'CHOOSE_LAY_CAN',
        fieldClass: 'lay-can',
        dateFormat: this.timeZoneTransformationService.getMappedConfigurationDateFormat(AppConfigurationService.settings?.dateFormat),
        fieldHeaderTooltipIcon: 'pi-info-circle',
        fieldHeaderTooltipText: 'PORT_TIME_ZONE_NOTIFICATION',
        errorMessages: {
          'required': 'PORT_LAY_CAN_REQUIRED_ERROR',
          'toDate': 'PORT_LAY_CAN_TO_DATE_ERROR',
          'datesEqual': 'PORT_LAY_CAN_DATE_EQUAL'
        }
      },
      {
        field: 'eta',
        header: 'ETA',
        fieldHeaderClass: 'column-eta',
        fieldType: DATATABLE_FIELD_TYPE.DATETIME,
        filter: true,
        filterPlaceholder: 'SEARCH_PORT_ETA',
        filterType: DATATABLE_FILTER_TYPE.DATE,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'eta.value',
        fieldPlaceholder: 'CHOOSE_ETA',
        dateFormat: this.timeZoneTransformationService.getMappedConfigurationDateFormat(AppConfigurationService.settings?.dateFormat),
        minDate: minDate,
        fieldClass: 'eta',
        fieldHeaderTooltipIcon: 'pi-info-circle',
        fieldHeaderTooltipText: 'PORT_TIME_ZONE_NOTIFICATION',
        errorMessages: {
          'required': 'PORT_ETA_REQUIRED_ERROR',
          'notInRange': 'PORT_ETA_NOT_IN_DATE_RANGE',
          'failedCompare': 'PORT_ETA_COMPARE_ERROR',
          'etaFailed': 'PORT_ETA_COMAPRE_WITH_ETD_ERROR'
        }
      },
      {
        field: 'etd',
        header: 'ETD',
        fieldHeaderClass: 'column-etd',
        fieldType: DATATABLE_FIELD_TYPE.DATETIME,
        filter: true,
        filterPlaceholder: 'SEARCH_PORT_ETD',
        filterType: DATATABLE_FILTER_TYPE.DATE,
        filterMatchMode: DATATABLE_FILTER_MATCHMODE.CONTAINS,
        filterField: 'etd.value',
        fieldPlaceholder: 'CHOOSE_ETD',
        minDate: minDate,
        dateFormat: this.timeZoneTransformationService.getMappedConfigurationDateFormat(AppConfigurationService.settings?.dateFormat),
        fieldClass: 'etd',
        fieldHeaderTooltipIcon: 'pi-info-circle',
        fieldHeaderTooltipText: 'PORT_TIME_ZONE_NOTIFICATION',
        errorMessages: {
          'required': 'PORT_ETD_REQUIRED_ERROR',
          'notInRange': 'PORT_ETD_NOT_IN_DATE_RANGE',
          'failedCompare': 'PORT_ETD_COMPARE_ERROR',
          'etdFailed': 'PORT_ETD_COMAPRE_WITH_ETA_ERROR'
        }
      }
    ];
  }


  /**
 * Method for converting ports data to value object model
 *
 * @param {IPortList} port
 * @param {boolean} [isNewValue=true]
 * @param {IPortAllDropdownData} listData
 * @returns {IPortsValueObject}
 * @memberof PortRotationPopupTransformationService
 */
  getPortAsValueObject(port: IPortList, isNewValue = true, isEditable, listData: IPortAllDropdownData, portEtaEtdPermission: IPermission): IPortRotationValueObject {
    const _port = <IPortRotationValueObject>{};
    const isEtaEtadEdtitable = this.isEtaEtdViewable(portEtaEtdPermission);
    const portObj: IPort = listData.portList.find(portData => portData.id === port.portId);
    const operationObj: IOperations = listData.operationListComplete.find(operation => operation.id === port.operationId);
    const isEdit = operationObj ? !(operationObj.id === OPERATIONS.LOADING || operationObj.id === OPERATIONS.DISCHARGING) : true;
    const layCan = (port.layCanFrom && port.layCanTo) ? (port.layCanFrom + ' to ' + port.layCanTo) : '';
    _port.id = port.id;
    _port.portOrder = port.portOrder;
    _port.portTimezoneId = port.portTimezoneId;
    _port.portcode = new ValueObject<string>(portObj?.code, true, false, false, false);
    _port.port = new ValueObject<IPort>(portObj, true, false, false, false);
    _port.operation = new ValueObject<IOperations>(operationObj, true, false, false, false);
    _port.layCan = new ValueObject<any>(layCan, true, isNewValue, true, isEditable);
    _port.layCanFrom = new ValueObject<string>(port.layCanFrom?.trim(), true, isNewValue, false, isEditable);
    _port.layCanTo = new ValueObject<string>(port.layCanTo?.trim(), true, isNewValue, false, isEditable);
    _port.eta = new ValueObject<string>(port.eta, true, isNewValue, true, isEtaEtadEdtitable);
    _port.etd = new ValueObject<string>(port.etd, true, isNewValue, true, isEtaEtadEdtitable);
    return _port;
  }

  /**
* Method for checking eta etd permission
*
* @private
* @param {IPermission} portEtaEtdPermission
* @returns {boolean}
* @memberof PortRotationPopupTransformationService
*/
  isEtaEtdViewable(portEtaEtdPermission: IPermission) {
    return (portEtaEtdPermission?.view || portEtaEtdPermission?.view === undefined) ? true : false;
  }


    /**
  * Method for converting from port value object model
  *
  * @param {IPortList[]} portsLists
  * @param {IPortRotationValueObject[]} portValueObject
  * @returns {IPortList[]}
  * @memberof PortRotationPopupTransformationService
  */
  getPortAsValue(portsLists: IPortList[], portValueObject: IPortRotationValueObject[]): IPortList[] {
    const _ports = portsLists.map((port) => {
      const portObject = portValueObject.find(portObj => portObj.id === port.id);
      if (portObject?.layCan?.value) {
        const layCanFrom = moment(portObject?.layCan?.value.split('to')[0].trim(), AppConfigurationService.settings?.dateFormat.split(' ')[0]).endOf('d').format('DD-MM-YYYY HH:mm');
        port.layCanFrom = this.timeZoneTransformationService.revertZoneTimetoUTC(layCanFrom, portObject.port?.value?.timezoneOffsetVal)?.slice(0, 10);
        const layCanTo = moment(portObject?.layCan?.value.split('to')[1].trim(), AppConfigurationService.settings?.dateFormat.split(' ')[0]).startOf('d').format('DD-MM-YYYY HH:mm');
        port.layCanTo = this.timeZoneTransformationService.revertZoneTimetoUTC(layCanTo, portObject.port?.value?.timezoneOffsetVal)?.slice(0, 10);
      } else {
        port.layCanFrom = "";
        port.layCanTo = "";
      }
      if (portObject?.eta?.value) {
        const newEta = moment(portObject.eta?.value?.slice(0, 17)).format('DD-MM-YYYY HH:mm');
        portObject.eta?.value ? port.eta = this.timeZoneTransformationService.revertZoneTimetoUTC(newEta, portObject.port?.value?.timezoneOffsetVal) : port.eta = "";
      }
      if (portObject?.etd?.value) {
        const newEtd = moment(portObject.etd?.value?.slice(0, 17)).format('DD-MM-YYYY HH:mm');
        portObject.etd?.value ? port.etd = this.timeZoneTransformationService.revertZoneTimetoUTC(newEtd, portObject.port?.value?.timezoneOffsetVal) : port.etd = "";
      }
      return port;
    });
    return _ports;
  }
}
