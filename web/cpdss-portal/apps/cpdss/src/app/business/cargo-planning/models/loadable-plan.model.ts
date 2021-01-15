import { IDataTableEvent } from '../../../shared/components/datatable/datatable.model';
import { IResponseStatus, ValueObject } from '../../../shared/models/common.model';
import { IBallastTank, ICargoTank, ILoadableCargo } from '../../core/models/common.model';


/**
 * Interface for Loadable Quantity api response
 *
 * @export
 * @interface ILoadablePlanResponse
 */
export interface ILoadablePlanResponse {
    responseStatus: IResponseStatus;
    loadableQuantityCargoDetails: ILoadableQuantityCargo[];
    loadableQuantityCommingleCargoDetails: ILoadableQuantityCommingleCargo[];
    tankLists: ICargoTank[][];
    loadablePlanStowageDetails: ICargoTankDetail[];
    frontBallastTanks: IBallastTank[][],
    centerBallastTanks: IBallastTank[][],
    rearBallastTanks: IBallastTank[][],
    loadablePlanBallastDetails: IBallastStowageDetails[],
    loadablePlanSynopticalRecords: ILoadablePlanSynopticalRecord[],
    loadablePlanComments: ILoadablePlanCommentsDetails[]
}

/**
 * Interface for loadable quality 
 *
 * @export
 * @interface ILoadableQuantityCargo
 */
export interface ILoadableQuantityCargo {
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

/**
 * Interface for loadable commingle Cargo
 *
 * @export
 * @interface ILoadableQuantityCommingleCargo
 */
export interface ILoadableQuantityCommingleCargo {
    id: number,
    grade: string,
    tankName: string,
    quantity: string,
    api: string,
    temp: string,
    cargo1Abbreviation: string,
    cargo2Abbreviation: string,
    cargo1Percentage: string,
    cargo2Percentage: string,
    cargo1Bblsdbs: string,
    cargo2Bblsdbs: string,
    cargo1Bbls60f: string,
    cargo2Bbls60f: string,
    cargo1LT: string,
    cargo2LT: string,
    cargo1MT: string,
    cargo2MT: string,
    cargo1KL: string,
    cargo2KL: string
}

/**
 * Interface for loadable commingle Cargo
 *
 * @export
 * @interface ICommingleCargoDispaly
 */
export interface ICommingleCargoDispaly {
    id: number,
    grade: string,
    tankName: string,
    quantity: string,
    api: string,
    temp: string,
    cargoPercentage: string,
    cargoBblsdbs: string,
    cargoBbls60f: string,
    cargoLT: string
    cargoMT: string,
    cargoKL: string,
}



/**
 * Interface for loadableQuantity total calculate
*/
export interface ITotalLoadableQuality {
    orderBblsdbs: number,
    orderBbls60f: number,
    loadableBblsdbs: number,
    loadableBbls60f: number,
    loadableLT: number,
    loadableMT: number,
    loadableKL: number,
    differencePercentage: number
}

/**
 * Interface for cargo tank details
 *
 * @export
 * @interface ICargoTankDetail
 */
export interface ICargoTankDetail extends ILoadableCargo {
    id: number;
    tankId: number;
    cargoAbbreviation: string;
    weight: number;
    correctedUllage: number;
    fillingRatio: number;
    tankName: string;
    rdgUllage: number;
    correctionFactor: number;
    observedM3: number;
    observedBarrels: number;
    observedBarrelsAt60: number;
    api: number;
    temperature: number;
    colorCode: string;
}

/**
 * Interface for cargo tank details value object
 *
 * @export
 * @interface ICargoTankDetailValueObject
 */
export interface ICargoTankDetailValueObject {
    id: number;
    colorCode: string;
    tankId: number;
    cargoAbbreviation: string;
    weight: ValueObject<number>;
    correctedUllage: ValueObject<number>;
    fillingRatio: ValueObject<number>;
    tankName: string;
    rdgUllage: ValueObject<number>;
    correctionFactor: ValueObject<number>;
    observedM3: ValueObject<number>;
    observedBarrels: ValueObject<number>;
    observedBarrelsAt60: ValueObject<number>;
    api: ValueObject<number>;
    temperature: ValueObject<number>;
    isAdd: boolean;
}

/**
 * Interface for cargo tank details grid events
 *
 * @export
 * @interface ICargoTankDetailEvent
 * @extends {IDataTableEvent}
 */
export interface ICargoTankDetailEvent extends IDataTableEvent {
    data: ICargoTankDetailValueObject;
    field: string;
    index: number;
    originalEvent: MouseEvent;
}

/**
 * Interface for ballast stowage
 *
 * @export
 * @interface 
 */
export interface IBallastStowageDetails {
    id: number,
    tankName?: string,
    tankId: number,
    rdgLevel: string,
    correctionFactor: string,
    correctedLevel: string,
    metricTon: string,
    cubicMeter: string,
    percentage: string,
    sg: string,
    lcg: string,
    vcg: string,
    tcg: string,
    inertia: string
}


/**
 * Interface for ballast Tank details
 * @export
 * @interface 
 */
export interface IBallastTanksDetails {
    id: number,
    categoryId: number,
    categoryName: string,
    name: string,
    frameNumberFrom: string,
    frameNumberTo: string,
    shortName: string,
    fullCapacityCubm: string,
    density: number,
    group: number,
    order: number,
    slopTank: boolean
}

/**
 * Interface for LoadablePlan Synoptical Records
 * @export
 * @interface 
 */
export interface ILoadablePlanSynopticalRecord {
    id: number,
    operationType: string,
    portId: number,
    portName: string,
    etaEtdPlanned: string,
    plannedFOTotal: number,
    plannedDOTotal: number,
    plannedFWTotal: number,
    othersPlanned: number,
    totalDwtPlanned: number,
    displacementPlanned: number,
    specificGravity: number,
    finalDraftFwd: number,
    finalDraftAft: number,
    finalDraftMid: number,
    calculatedTrimPlanned: number,
    cargoPlannedTotal: number,
    ballastPlanned: number
}

/**
 * Interface for LoadablePlan arranged Synoptical Records
 * @export
 * @interface 
 */
export interface ISynopticalRecordArrangeModel {
    id: number,
    operationType: string,
    portId: number,
    portName: string,
    etaEtdPlanned: string,
    plannedFOTotal: number,
    plannedDOTotal: number,
    plannedFWTotal: number,
    othersPlanned: number,
    totalDwtPlanned: number,
    displacementPlanned: number,
    specificGravity: number,
    finalDraftFwd: string,
    finalDraftAft: string,
    finalDraftMid: string,
    calculatedTrimPlanned: string,
    cargoPlannedTotal: number,
    ballastPlanned: number
}

/**
 * Interface for LoadablePlan comments details
 * @export
 * @interface 
 */
export interface ILoadablePlanCommentsDetails {
    id: number,
    userName: string,
    dataAndTime: string,
    comment: string
}

/**
 * Interface for save comment
 * @export
 * @interface 
 */
export interface ISaveComment {
    comment: string;
}
