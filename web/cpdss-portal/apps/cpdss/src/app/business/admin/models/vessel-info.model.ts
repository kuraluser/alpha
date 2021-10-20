import { IResponseStatus } from "../../../shared/models/common.model";
import { IShipBallastTank, IShipBunkerTank, IShipCargoTank } from "../../core/models/common.model";

/**
 * Interface for Vessel information list
 */
export interface IVesselList {
    vesselId: number;
    vesselName: string;
    owner?: string;
    signalLetter?: string;
    officialNumber?: string;
    vesselType: string;
    builder: string;
    dateOfLaunch: string;
}

/**
 * Interface for Vessel information list GET API
 */
export interface IVesselListResponse {
    responseStatus: IResponseStatus;
    vesselsInfo: IVesselList[];
    totalElements: number;
}

/**
 * Interface for Vessel details
 */
export interface IVesselDetails {
    vesselId: number;
    vesselName: string;
    vesselImageUrl: string;
    countryFlagUrl: string;
    imoNumber: number;
    generalInfo: IVesselGeneral;
    vesselDimesnsions: IVesselDimensions;
    draftDisplacementDeadweight: IVesselDraftDisplacementDeadweight;
}

/**
 * Interface for vessel general info
 */
export interface IVesselGeneral {
    owner?: string;
    vesselType: string;
    builder: string;
    officialNumber: number;
    signalLetter: string;
    dateOfKeelLaid: string;
    dateOfLaunch: string;
    dateOfDelivery: string;
    navigationArea: string;
    class: string;
}

/**
 * Interface for Vessel dimensions
 */
export interface IVesselDimensions {
    registerLength: number;
    lengthOverall: number;
    draftFullLoad: number;
    draftFullLoadInFt?: number;
    breadthMoulded: number;
    lengthBetweenPerpendiculars: number;
    depthMoulded: number;
    designedLoadDraft: number;
}

/**
 * Interface for Vessel draft, displacement & deadweight
 */
export interface IVesselDraftDisplacementDeadweight {
    depthMoulded: number;
    thicknessOfUpperDeck: number;
    thicknessOfKeelPlate: number;
    totalDepth: number;
}

/**
 * Interface for Vessel details GET API
 */
export interface IVesselDetailsResponse {
    responseStatus: IResponseStatus;
    vesselDetails: IVesselDetails;
    bunkerRearTanks: IShipBunkerTank[][];
    bunkerTanks: IShipBunkerTank[][];
    ballastFrontTanks: IShipBallastTank[][];
    ballastCenterTanks: IShipBallastTank[][];
    ballastRearTanks: IShipBallastTank[][];
    cargoTanks: IShipCargoTank[][];
}

/**
 * Interface vessel list GET API subscription params
 */
export interface IVesselInfoDataStateChange {
    pageSize: number;
    pageNo: number;
    sortBy: string;
    orderBy: string;
    vesselName: string;
    vesselType: string;
    builder: string;
    dateOfLaunch: string;
}