import { ICountry, IResponseStatus, ITimeZone, ValueObject } from '../../../shared/models/common.model';
import {IVessel} from '../../core/models/vessel-details.model';

/**
 * Interface for crew details
 *
 * @export
 * @interface CrewDetails
 */
export interface ICrewDetails {
  id: number;
  crewName: string;
  crewRank: string;
  crewRankId: number;
  vesselInformation: ICrewVesselMapping[],
}

/**
 * Interface for crew details get api response.
 *
 * @export
 * @interface ICrewDetailsResponse
 */
export interface ICrewsDetailsResponse {
  responseStatus: IResponseStatus;
  crewDetails: ICrewDetails;
}

/**
 * interface for crew master list API response
 */
export interface ICrewMasterListResponse {
  crewDetails: ICrewMasterList[];
  responseStatus: IResponseStatus;
  totalElements: number;
}

/**
 * interface for crew master list
 */
export interface ICrewMasterList {
  id: number;
  crewName: string;
  crewRank: string;
  crewRankId: number;
  vesselInformation: ICrewVesselMapping[],
  vesselName?: Array<string>;
  vesselLabel?: string;
}

/**
 * interface for crew master list state params
 */
export interface ICrewMasterListStateChange {
  pageSize: number;
  page: number;
  sortBy?: string;
  orderBy?: string;
  filter?: Object;
}

export interface ICrewVesselMapping {
  id: number;
  vessel: IVessel
}
