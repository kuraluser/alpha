/**
 * Model for new voyage
 */
export class NewVoyageModel {
    public voyageNumber!: string;
    public captainId!: number;
    public cheifOfficerId!: number;   
}

/**
 * Model for new voyage api response
 */
export class NewVoyageResponseModel {
    public status!: string; 
}