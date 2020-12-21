import { IResponse } from "../../../shared/models/common.model";

/**
 * Model for synoptical table records
 */
export interface ISynopticalRecords {
    portName: number
    id: number,
    operationType: string,
    distance: number,
    speed: number,
    runningHours: number,
    inPortHours: number,
    timeOfSunrise: string,
    timeOfSunset: string,
    hwTideFrom: number,
    hwTideTo: number,
    hwTideTimeFrom: string,
    hwTideTimeTo: string,
    lwTideFrom: number,
    lwTideTo: number,
    lwTideTimeFrom: string,
    lwTideTimeTo: string,
    specificGravity: number,
    portId: number,
    
}
/**
 * Model for synoptical response 
 */
 export interface ISynopticalResponse{
    responseStatus: IResponse;
    synopticalRecords : ISynopticalRecords[];
 }
