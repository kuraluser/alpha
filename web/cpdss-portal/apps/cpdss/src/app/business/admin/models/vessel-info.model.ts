/**
 * Interface for Vessel-master information
 */
export interface IVesselMasterInfo {
    vesselId: number;
    vesselName: string;
    owner: string;
    vesselType: string;
    builder: string;
    dateOfLaunch: string;
}