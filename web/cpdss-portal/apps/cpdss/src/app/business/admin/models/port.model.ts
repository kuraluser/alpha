import { IResponseStatus } from '../../../shared/models/common.model';


/**
 * Interface for berthInformation.
 *
 * @export
 * @interface BerthInfo
 */
export interface BerthInfo {
  berthId: number;
  portId: number;
  berthName: string;
  maxShipDepth: number;
  depthInDatum: number;
  maxDwt: number;
  maxLoa: number;
  maxManifoldHeight: number;
  minUKC?: number;
  regulationAndRestriction: string;
}

/**
 * Interface for port details
 *
 * @export
 * @interface PortDetails
 */
export interface PortDetails {
  portId: number;
  portName: string;
  portCode: string;
  maxPermissibleDraft: number;
  timezone: string;
  timezoneOffsetVal: string;
  timezoneAbbreviation: string;
  tideHeightHigh?: number;
  tideHeightLow?: number;
  densityOfWater: number;
  country: string;
  ambientTemperature?: number;
  latitude: string;
  longitude: string;
  berthInfo: BerthInfo[];
}

/**
 * Interface for port details get api response.
 *
 * @export
 * @interface IPortsDetailsResponse
 */
export interface IPortsDetailsResponse {
  responseStatus: IResponseStatus;
  portDetails: PortDetails;
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
