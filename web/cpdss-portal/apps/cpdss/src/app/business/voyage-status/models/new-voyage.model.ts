import { IResponse, IResponseStatus } from '../../../shared/models/common.model';

/**
 * Model for new voyage
 */
export class NewVoyageModel {
    public voyageNo!: string;
    public captainId!: number;
    public chiefOfficerId!: number;
    public startDate!: string;
    public endDate!: string;
}

/**
 * Model for new voyage api response
 */
export class NewVoyageResponseModel implements IResponse {
    responseStatus?: IResponseStatus;
    status: string;
    message?: string;
    errorCode?: string;
    correlationId?: string;
    voyageId: number;
}
export class Status {
    status: string;
}

