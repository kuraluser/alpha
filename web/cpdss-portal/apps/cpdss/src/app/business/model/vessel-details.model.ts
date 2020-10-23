/**
 * Vessel details Model
 */
export class VesselDetailsModel{
    public id : number;
    public name : string;
    public captainId: number;
    public captainName: string;
    public chiefOfficerId: number;
    public chiefOfficerName: string;
    public loadlines: LoadLine[];
   
}

/**
 * Loadline Model
 */
export class LoadLine{
    public id: number;
    public name: string;
    public draftMarks: string[];
}