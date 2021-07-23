import { IResponseStatus, ValueObject } from '../../../../shared/models/common.model';
import { IPort } from '../../../core/models/common.model'

/**
 * Interface for ports value object
 *
 * @export
 * @interface IPortRotationValueObject
 */
 export interface IPortRotationValueObject {
    id: number;
    portOrder: number;
    portTimezoneId?: number;
    slNo: number;
    port: ValueObject<IPort>;
    portcode: ValueObject<string>;
    operation: ValueObject<IOperations>;
    layCan: ValueObject<string>;
    layCanFrom: ValueObject<string>;
    layCanTo: ValueObject<string>;
    eta: ValueObject<any>;
    etd: ValueObject<any>;
}

/**
 * Interface for Operations
 *
 * @export
 * @interface IOperations
 */
export interface IOperations {
  id: number;
  operationName: string;
}
/**
 * Interface for Ports api response
 *
 * @export
 * @interface IPortsDetailsResponse
 */
export interface IPortsDetailsResponse {
  responseStatus: IResponseStatus;
  portList: IPortList[];
  operations: IOperations[];
  portId?: number;
}

/**
 * Interface for port
 *
 * @export
 * @interface IPortList
 */
export interface IPortList {
  id: number;
  portOrder: number;
  loadableStudyId: number;
  portId: number;
  operationId: number;
  seaWaterDensity: number;
  distanceBetweenPorts: number;
  timeOfStay: number;
  maxDraft: number;
  maxAirDraft: number;
  eta: string;
  etd: string;
  layCanFrom: string;
  layCanTo: string;
  isDelete?: boolean;
  isAdd?: boolean;
  storeKey?: number;
  vesselId?: number;
  voyageId?: number;
  etaActual?: string;
  etdActual?: string;
}
