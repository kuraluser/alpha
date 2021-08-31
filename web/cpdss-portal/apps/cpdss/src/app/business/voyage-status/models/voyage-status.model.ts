import { ICargoConditions, IResponseStatus } from '../../../shared/models/common.model';
import { ICargoQuantities, IShipCargoTank, ITank, IShipBallastTank, IBallastQuantities, IBunkerQuantities, IShipBunkerTank } from '../../core/models/common.model';


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
 * Interface for draft condition of vessel
 *
 * @export
 * @interface IDraftCondition
 */
export interface IDraftCondition {
    list: number;
    deflection: number;
    finalDraftFwd: number;
    finalDraftAft: number;
    finalDraftMid: number;
    calculatedDraftFwdActual: number;
    calculatedDraftAftActual: number;
    calculatedDraftMidActual: number;
    calculatedTrimActual: number;
}

