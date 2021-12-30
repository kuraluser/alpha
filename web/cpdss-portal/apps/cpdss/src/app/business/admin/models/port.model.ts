import { ICountry, IResponseStatus, ITimeZone, ValueObject } from '../../../shared/models/common.model';


/**
 * Interface for berthInformation.
 *
 * @export
 * @interface IBerthInfo
 */
export interface IBerthInfo {
  berthId: number;
  portId: number;
  berthName: string;
  maxDraft: number;
  depthInDatum: number;
  maxLoa: number;
  maxDwt: number;
  maxShipDepth: number;
  maxManifoldHeight: number;
  minUKC?: string;
  regulationAndRestriction: string;
  isAdd: boolean;
  isDelete?: boolean;
}

/**
 * Interface for berth details as ValueObject
 *
 * @export
 * @interface IBerthValueObject
 */
export interface IBerthValueObject {
  berthId: number;
  portId: number;
  berthName: ValueObject<string>;
  maxDraft: ValueObject<number>;
  depthInDatum: ValueObject<number>;
  maxLoa: ValueObject<number>;
  maxDwt: ValueObject<number>;
  maxShipDepth: ValueObject<number>;
  maxManifoldHeight: ValueObject<number>;
  minUKC?: ValueObject<string>;
  regulationAndRestriction: ValueObject<string>;
  isAdd?: boolean;
  isDelete?: boolean;
}

/**
 * Interface for port details
 *
 * @export
 * @interface PortDetails
 */
export interface IPortDetails {
  portId: number;
  portName: string;
  portCode: string;
  maxPermissibleDraft: number;
  timezone: string;
  timezoneObj?: ITimeZone;
  timezoneOffsetVal: string;
  timezoneAbbreviation: string;
  tideHeightHigh?: number;
  tideHeightLow?: number;
  densityOfWater: number;
  countryName: string;
  country?: ICountry;
  ambientTemperature?: number;
  latitude: any;
  longitude: any;
  position?: number[];
  berthInfo: IBerthInfo[];
}

/**
 * Interface for port details get api response.
 *
 * @export
 * @interface IPortsDetailsResponse
 */
export interface IPortsDetailsResponse {
  responseStatus: IResponseStatus;
  portDetails: IPortDetails;
}

/**
 * interface for port master list API response
 */
export interface IPortMasterListResponse {
  ports: IPortMasterList[];
  responseStatus: IResponseStatus;
  totalElements: number;
}

/**
 * interface for port master list
 */
export interface IPortMasterList {
  code: string;
  id: number;
  name: string;
  countryId: number;
  countryName: string;
  timezone: string;
  timezoneAbbreviation: string;
  timezoneOffsetVal: string;
  waterDensity: number;
}

/**
 * interface for port master list state params
 */
export interface IPortMasterListStateChange {
  pageSize: number;
  page: number;
  sortBy?: string;
  orderBy?: string;
  filter?: Object;
}
