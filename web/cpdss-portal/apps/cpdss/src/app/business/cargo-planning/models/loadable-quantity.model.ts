/**
 * Model for loadable quantity model
 */
export class LoadableQuantityModel {
    public responseStatus: Status;
    public loadableQuantity: LodadableQuantity;
    public isSummerZone?: boolean;
    public caseNo: number;
    public selectedZone: string;
}
/**
 * Model for loadable quantity
 */
export class LodadableQuantity {
    public portId?: number;
    public draftRestriction: string;
    public displacmentDraftRestriction?: string;
    public vesselLightWeight?: string;
    public dwt: string;
    public tpc: string;
    public estSeaDensity?: string;
    public sgCorrection?: string;
    public estSagging: string;
    public saggingDeduction?: string;
    public boilerWaterOnBoard: string;
    public ballast: string;
    public constant: string;
    public estFOOnBoard: string;
    public estDOOnBoard: string;
    public estFreshWaterOnBoard?: string;
    public otherIfAny: string;
    public distanceFromLastPort?: string;
    public vesselAverageSpeed?: string;
    public runningHours?: string;
    public runningDays?: string;
    public foConsumptionPerDay?: string;
    public estTotalFOConsumption?: string;
    public updateDateAndTime?: string;
    public foConInSZ?: string;
    public subTotal?: string;
    public totalQuantity?: string;
}

/**
 * Response model for loadable quantity
 */
export class LoadableQuantityResponseModel {
    public responseStatus: Status;
    public loadableQuantityId: number;
}
/**
 *  Model for loadable quantity response status
 */
export class Status {
    status: string;
}

/**
 * Interface for loadable quality plan table 
 *
 * @export
 * @interface ITableHeaderModel
 */
export interface ITableHeaderModel {
    field?: string;
    header: string;
    colspan: number,
    subColumns?:ColumHeader[];  
}

/**
 * Interface for table header
*/
interface ColumHeader {
    field: string;
    header: string;
}

/**
 * Interface for loadable quality plan table binding  
 *
 * @export
 * @interface LodadableQuantityPlan
 */
export  interface LodadableQuantityPlan {
    id: number,
    grade: string,
    estimatedAPI: string,
    estimatedTemp: string,
    orderBblsdbs: string,
    orderBbls60f: string,
    minTolerence: string,
    maxTolerence: string,
    loadableBblsdbs: string,
    loadableBbls60f: string,
    loadableLT: string,
    loadableMT: string,
    loadableKL: string,
    differencePercentage: string,
    differenceColor: string
}





