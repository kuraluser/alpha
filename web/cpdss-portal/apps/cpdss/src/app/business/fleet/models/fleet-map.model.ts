import { IResponseStatus } from "../../../shared/models/common.model";

/**
 * interface for vessel details response
 *
 * @export
 * @interface IFleetVesselResponse
 */
export interface IFleetVesselResponse {
    responseStatus: IResponseStatus;
    shoreList: IFleetVessel[];
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
    voyageId: number;
    voyageStart?: IFleetVoyagePorts;
    voyageEnd?: IFleetVoyagePorts;
    vesselName: string;
    flagName: string;
    atd: string;
    eta: string;
    ata?: string;
    imoNo: number;
    voyagePorts?: IFleetVoyagePorts[];
}

/**
 * interface for Voyage-ports details
 *
 * @export
 * @interface IFleetVoyagePorts
 */
export interface IFleetVoyagePorts {
    portName: string;
    portType: string;
    portOrder: number | string;
    vesselName?: string;
    atd?: string;
    ata?: string;
    etd?: string;
    eta?: string;
    iconUrl?: string;
    portTypeIconUrl?: string;
    lat: number | string;
    lon: number | string;
}

/**
 * interface for vessel-card event emitter
 *
 * @export
 * @interface IFleetVesselCardEvent
 */
export interface IFleetVesselCardEvent {
    vesselId: number;
    originalEvent: MouseEvent;
}

/**
 * interface for vessel-notifications
 *
 * @export
 * @interface IFleetNotifications
 */
export interface IFleetNotifications {
    vesselName: string;
    vesselId: number;
    flag: string;
    status: string;
    updatedDate: string;
    dateTime?: Date;
}

/**
 * interface for vessel-notifications api response
 *
 * @export
 * @interface IFleetNotificationResponse
 */
export interface IFleetNotificationResponse {
    current: IFleetNotifications[];
    all: IFleetNotifications[];
}