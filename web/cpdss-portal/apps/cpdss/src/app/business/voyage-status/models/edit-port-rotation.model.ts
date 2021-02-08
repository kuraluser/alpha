import { IPortList } from '../../core/models/common.model';
import { IResponseStatus } from '../../../shared/models/common.model';

/**
 * Interface for Edit port rotation response 
 */
export interface IPortResponseModel {
    responseStatus: IResponseStatus;
}

/**
 *  Interface for edit port rotation model
 */
export interface IEditPortRotationModel {
    portList: IPortList[];
}

/**
 *  Interface for edit port rotation 
 */
export interface IEditPortRotation{
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
    name: string;
    index?: number;
    isFutureDate?: boolean;
    type?: string;
    isDateEditable?: boolean;
    isTimeEditable?: boolean;
    isDistanceEditable?: boolean;
    currentPort?: boolean;
    isEditable?: boolean;
    isSelected?: boolean;
    isFocused?: boolean;
}