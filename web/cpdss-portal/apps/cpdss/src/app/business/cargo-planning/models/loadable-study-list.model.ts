import { IResponse } from '../../../shared/models/common.model';
import { LOADABLE_STUDY_STATUS } from '../../core/models/common.model';
/**
 * Model for loadable study list
 */
export class LoadableStudy {
  id: number;
  slNo: number;
  name: string;
  status: string;
  statusId: LOADABLE_STUDY_STATUS;
  detail: string;
  createdDate: string;
  lastEdited: string;
  charterer: string;
  subCharterer: string;
  draftMark: number;
  loadLineXId: number;
  draftRestriction: number;
  maxAirTemperature: number;
  maxWaterTemperature: number;
  dischargingPortIds?: number[];
  loadableStudyStatusLastModifiedTime?: string;
  loadableStudyAttachment?: ILoadableStudyAttachment[];
  dischargingCargoId: number;
  createdFromId?: number;
  loadOnTop: boolean;
  isEditable?: boolean;
  isDeletable?: boolean;
  isDuplicate?: boolean;
  isActionsEnabled?: boolean;
  isCargoNominationComplete: boolean;
  isPortsComplete: boolean;
  isOhqComplete: boolean;
  isObqComplete: boolean;
  isDischargingPortComplete: boolean;
  ohqPorts: ILoadableOHQStatus[];
  createdFromVoyageId?: number;
}

/**
 * Interface for OHQ ports status
 *
 * @export
 * @interface ILoadableOHQStatus
 */
export interface ILoadableOHQStatus {
    id: number;
    isPortRotationOhqComplete: boolean;
}

/**
 * Interface for loadable study attachments
 *
 * @export
 * @interface ILoadableStudyAttachment
 */
export interface ILoadableStudyAttachment {
    id: number;
    fileName: string;
}

/**
 * Interface for dischrge ports ids
 *
 * @export
 * @interface IDischargingPortIds
 */
export interface IDischargingPortIds {
    portIds: number[];
    cargoNominationId: number;
    isDischargingPortComplete: boolean;
}

/**
 * Interface for loadable study save response
 *
 * @export
 * @interface ILoadableStudyResponse
 */
export interface ILoadableStudyResponse extends IResponse {
    loadableStudyId: number;
}

/**
 * Interface for loadable Studies response
 *
 * @export
 * @interface ILoadableStudiesResponse
 */
export interface ILoadableStudiesResponse {
    requestStatus: any;
    loadableStudies: LoadableStudy[];
}

/**
 * Interface for loadable pattern response
 *
 * @export
 * @interface LoadablePattern
 */
export interface LoadablePattern {
    loadablePatternId: number;
    caseNumber: number;
    loadableStudyStatusId: number;
}

/* Interface for loadable Patterns response
*
* @export
* @interface ILoadablePatternsResponse
*/
export interface ILoadablePatternsResponse {
    requestStatus: any;
    loadablePatterns: LoadablePattern[];
}
