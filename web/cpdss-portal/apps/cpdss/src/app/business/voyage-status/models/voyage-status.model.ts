import { Status } from '../models/new-voyage.model';
/**
 * Interface for voyage status model
 */
export class IVoyageStatus {
    responseStatus?: Status;
    cargoQuantities: ICargoQuantities[];
    cargoTanks: ICargoTanks[][];
    cargoConditions: ICargoConditions[];
    bunkerConditions: IBunkerConditions;

}
/**
 * Interface for cargo quantites model
 */
export class ICargoQuantities {
    id: number;
    tankId: number;
    tankName: string;
    cargoId: number;
    sounding: number;
    weight: number;
    actualWeight: number;
    volume: number;
    colorCode: string;
    abbreviation: string;
    plannedWeight: number;
    difference: number;
    isPositive: boolean;
}

/**
 * Interface for cargo tank model
 */
export class ICargoTanks {
    id: number;
    tankId: number;
    tankName: string;
    cargoId: number;
    sounding: number;
    weight: number;
    actualWeight: number;
    volume: number;
    colorCode: string;
    abbreviation: string;
}
/**
 * Interface for cargo conditions model
 */
export class ICargoConditions {
    id: number;
    plannedWeight: number;
    actualWeight: number;
}
/**
 * Interface for bunker condition model
 */
export class IBunkerConditions {
    dieselOilWeight: number;
    fuelOilWeight: number;
    ballastWeight: number;
    freshWaterWeight: number;
    othersWeight: number;
    totalDwtWeight: number;
    displacement: number;
    specificGravity: number;
}
/**
 * Interface for voyage Details model
 */
export class IVoyageDetails {
    portOrder: number;
    operationType: string;
    portId?: number;
}

/**
 * Interface for parameter list
 */
export class IBunkerConditionParameterList {
    parameters: string;
    value: number;
}
