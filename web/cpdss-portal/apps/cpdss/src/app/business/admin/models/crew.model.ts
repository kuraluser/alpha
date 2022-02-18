import {  IResponseStatus } from '../../../shared/models/common.model';

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

/**
 * enum for crew pop operation
 */
export enum CREW_POPUP_SELECTIONMODE {
  ADD = 'ADD',
  EDIT = 'EDIT',
  VIEW = 'VIEW'
}

/**
 * Interface for crew details
 *
 * @export
 * @interface CrewRank
 */
 export interface ICrewRank {
  id: number;
  rankName: string;
  rankShortName: string;
}

/**
 * interface for crew rank API response
 */
 export interface ICrewrankListResponse {
  crewRankList: ICrewRank[];
  responseStatus: IResponseStatus;
}

/**
 * interface for crew save post body data
 */
export interface IPostCrewDetails {
  id: number;
  crewName: string;
  crewRank: string;
  crewRankId: number;
  vesselInformation: ICrewVesselMapping[],
}

/**
 * vessel interface
 * useing for crew details
 */
export interface IVessel {
  id: number,
  name: string
}
