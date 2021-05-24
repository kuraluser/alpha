/**
 * interface for Voyage-ports details
 *
 * @export
 * @interface IFleetVoyagePorts
 */
export interface IFleetVoyagePorts {
    portname: string;
    portType: string;
    portOrder: number;
    anchorage: boolean;
    vesselName?: string;
    atd?: string;
    ata?: string;
    etd?: string;
    eta?: string;
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
    voyageStart?: string;
    voyageEnd?: string;
    vesselName: string;
    flagImage: string;
    atd: string;
    eta: string;
    ata?: string;
    imoNo: number;
    voyagePorts?: IFleetVoyagePorts[];
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

export interface IFleetNotificationResponse {
    current: IFleetNotifications[];
    all: IFleetNotifications[];
}