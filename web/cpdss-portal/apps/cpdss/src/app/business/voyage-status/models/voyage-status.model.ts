import { IResponseStatus } from '../../../shared/models/common.model';
import { ICargoQuantities, IShipCargoTank, ITank } from '../../core/models/common.model';


/**
 * Interface for voyage status model
 */
export class IVoyageStatus {
    responseStatus?: IResponseStatus;
    cargoQuantities: ICargoQuantities[];
    ballastQuantities: IBallastQuantities[];
    bunkerQuantities: IBunkerQuantities[];
    cargoTanks: IShipCargoTank[][];
    bunkerTanks: IShipBunkerTank[][];
    bunkerRearTanks: IShipBunkerTank[][];
    ballastFrontTanks: IShipBallastTank[][];
    ballastCenterTanks: IShipBallastTank[][];
    ballastRearTanks: IShipBallastTank[][];
    cargoConditions: ICargoConditions[];
    bunkerConditions: IBunkerConditions;
    stabilityConditions: IDraftCondition;
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
 * Interface for parameter list
 */
export class IBunkerConditionParameterList {
    parameters: string;
    value: number;
    numberFormat?: string;
}


/**
 * Interface for bunker tanks
 *
 * @export
 * @interface IShipBunkerTank
 * @extends {ITank}
 */
export interface IShipBunkerTank extends ITank {
    id: number;
    categoryId: number;
    categoryName: string;
    name: string;
    frameNumberFrom: number;
    frameNumberTo: number;
    shortName: string;
    heightFrom?: number;
    heightTo?: number;
    fullCapacityCubm?: string;
    density: number;
    group: number;
    order: number;
    slopTank: boolean;
    commodity?: IBunkerQuantities;
    gridColumn?: string;
    percentageFilled?: string;
}

/**
 * Interface for bunker quantites model
 * @export
 * @interface IBunkerQuantities
 */
export interface IBunkerQuantities {
    id: number;
    fuelTypeId: number;
    fuelTypeName: string;
    fuelTypeShortName?: string;
    tankId: number;
    tankName: string;
    arrivalVolume: number;
    arrivalQuantity: number;
    actualArrivalQuantity: number;
    departureVolume: number;
    departureQuantity: number;
    actualDepartureQuantity: number;
    colorCode?: string;
    density: number;
    quantity: number;
    volume: number;
}



/**
 * Interface for ballast Tank details
 * @export
 * @interface IShipBallastTank
 */
export interface IShipBallastTank extends ITank {
    id: number,
    categoryId: number,
    categoryName: string,
    name: string,
    frameNumberFrom: number,
    frameNumberTo: number,
    shortName: string,
    fullCapacityCubm: string,
    density: number,
    group: number,
    order: number,
    slopTank: boolean,
    commodity: IBallastQuantities;
}

/**
 * Interface for cargo quantity
 *
 * @export
 * @interface IBallastQuantities
 */
export interface IBallastQuantities {
    fillingRatio: string;
    abbreviation: string;
    actualWeight: number;
    capacity: number;
    cargoId: number;
    colorCode: string;
    correctedUllage: number;
    plannedWeight: number;
    tankId: number;
    tankName: string;
    sg?: string;
    volume?: number;
    percentageFilled?: string;
}


/**
 * Interface for draft condition of vessel
 *
 * @export
 * @interface IDraftCondition
 */
export interface IDraftCondition {
    list: number;
    hogSag: number;
    finalDraftFwd: number;
    finalDraftAft: number;
    finalDraftMid: number;
    calculatedDraftFwdActual: number;
    calculatedDraftAftActual: number;
    calculatedDraftMidActual: number;
    calculatedTrimActual: number;
}

