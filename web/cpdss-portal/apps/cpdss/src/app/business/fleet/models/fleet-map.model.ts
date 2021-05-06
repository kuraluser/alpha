/**
 * interface for Voyage-ports details
 *
 * @export
 * @interface IFleetVoyagePorts
 */
export interface IFleetVoyagePorts {
    portname: string;
    portType: string;
    anchorage: boolean;
    prevPort?: string;
    nextPort?: string;
    vesselName?: string;
    atd?: string;
    iconUrl: string;
    lat: number;
    lon: number;
}

/**
 * interface for Vessels and voyage
 *
 * @export
 * @interface IFleetVessel
 */
export interface IFleetVessel {
    id: number;
    voyageName: string;
    vesselName: string;
    flagImage: string;
    atd: string;
    eta: string;
    imoNo: number;
    voyagePorts?: IFleetVoyagePorts[];
}