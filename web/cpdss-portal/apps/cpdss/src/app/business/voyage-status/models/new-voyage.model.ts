/**
 * Model for new voyage
 */
export class NewVoyageModel {
    public voyageNo!: string;
    public captainId!: number;
    public chiefOfficerId!: number;
}

/**
 * Model for new voyage api response
 */
export class NewVoyageResponseModel {
    public responseStatus: Status;
    public message: number;
    public voyageId: number;
}
export class Status {
    status: string;
}