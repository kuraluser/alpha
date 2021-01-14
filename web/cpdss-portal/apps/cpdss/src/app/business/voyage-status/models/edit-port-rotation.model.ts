import { IPortList, IResponseStatus } from '../../../shared/models/common.model';

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
