import { IDataTableEvent } from '../../../shared/components/datatable/datatable.model';
import { IResponseStatus, ValueObject , IResponse } from '../../../shared/models/common.model';
import { IBallastStowageDetails, IBallastTank, ICargoTank, ILoadableCargo, ILoadableQuantityCargo } from '../../core/models/common.model';
import { ILoadablePlanSynopticalRecord } from './cargo-planning.model';
import { DATATABLE_EDITMODE } from '../../../shared/components/datatable/datatable.model';

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
    loadablePlanComments: ILoadablePlanCommentsDetails[],
    voyageNumber: string,
    date: string,
    caseNumber: string,
    loadableStudyStatusId: number;
    loadablePatternStatusId: number,
    voyageStatusId: number,
    validated: boolean;
    confirmPlanEligibility?: boolean;
    loadableQuantity: number;
    lastModifiedPort: number;
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
    cargo2KL: string,
    tankShortName?: string,
    colorCode?: string;
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
    quantityBLS: number;
    api: string,
    temp: string,
    cargoPercentage: string,
    cargoBblsdbs: string,
    cargoBbls60f: string,
    cargo1LT: number;
    cargo2LT: number;
    cargo1KL: number;
    cargo2KL: number;
    cargoLT: string
    cargoMT: string,
    cargoKL: string,
    tankShortName?: string
}



/**
 * Interface for loadableQuantity total calculate
*/
export interface ITotalLoadableQuality {
    orderedQuantity: number;
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
    weightOrginal: number;
    correctedUllage: number;
    correctedUllageOrginal: number;
    fillingRatio: string;
    fillingRatioOrginal: string;
    tankName: string;
    rdgUllage: number;
    rdgUllageOrginal: number;
    correctionFactor: number;
    correctionFactorOrginal: number;
    observedM3: number;
    observedBarrels: number;
    observedBarrelsAt60: number;
    observedBarrelsAt60Original: number;
    api: number;
    temperature: number;
    colorCode: string;
    fullCapacityCubm: string;
    isCommingle?: boolean;
    tankShortName?: string;
    cargoNominationId?: number;
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
    weightOrginal: number;
    correctedUllage: ValueObject<number>;
    correctedUllageOrginal: number;
    fillingRatio: ValueObject<number>;
    fillingRatioOrginal: number;
    tankName: string;
    rdgUllage: ValueObject<number>;
    rdgUllageOrginal: number;
    correctionFactor: ValueObject<number>;
    correctionFactorOrginal: number;
    observedM3: ValueObject<number>;
    observedBarrels: ValueObject<number>;
    observedBarrelsAt60: ValueObject<number>;
    observedBarrelsAt60Original: number;
    api: ValueObject<number>;
    temperature: ValueObject<number>;
    isAdd: boolean;
    fullCapacityCubm: string;
    tankShortName: string;
    isCommingle?: boolean;
    cargoNominationId?: number;
}

/**
 * Interface for ballast tank details value object
 *
 * @export
 * @interface IBallastTankDetailValueObject
 */
 export interface IBallastTankDetailValueObject {
    id: number;
    tankName?: ValueObject<string>;
    tankId: number;
    rdgLevel: ValueObject<string>;
    rdgLevelOrginal: string;
    correctionFactor: ValueObject<string>;
    correctionFactorOrginal: string;
    correctedLevel: ValueObject<string>;
    correctedLevelOrginal: string;
    metricTon: ValueObject<string>;
    metricTonOrginal: string;
    cubicMeter: ValueObject<string>;
    percentage: ValueObject<string>;
    percentageOrginal: string;
    sg: ValueObject<string>;
    fullCapacityCubm: string;
    isAdd: boolean;
    api: ValueObject<number>;
    temperature: ValueObject<number>;
    tankShortName: string;
    fillingRatio: string;
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
    plannedFOTotal: string,
    plannedDOTotal: string,
    plannedFWTotal: string,
    othersPlanned: string,
    totalDwtPlanned: string,
    displacementPlanned: string,
    specificGravity: number,
    finalDraftFwd: string,
    finalDraftAft: string,
    finalDraftMid: string,
    calculatedTrimPlanned: string,
    cargoPlannedTotal: string,
    ballastPlanned: string;
    sf: number;
    bm: number;
    list: number;
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


/**
 * Interface for edit data table
 *
 * @export
 * @interface IPortsEvent
 */
 export interface IPortsEvent {
    data: any;
    field: string;
    index: number;
    originalEvent: MouseEvent;

}

/**
 * Interface for change rdg Ulg
 *
 * @export
 * @interface IUpdateUllageModel
 */
export interface IUpdateUllageModel {
   id: number;
   tankId: number;
   correctedUllage: string;
   isBallast: boolean;
   api: string;
   temperature: string;
   sg: string;
   isCommingle?: boolean;
}

/**
 * Interface for change rdg Ulg
 *
 * @export
 * @interface IUpdateBallastUllagegModel
 */
 export interface IUpdateBallastUllagegModel {
    id: number;
    tankId: number;
    correctedUllage: string;
 }

 /**
 * Interface for change response
 *
 * @export
 * @interface IUpdatedBallastUllageResponse
 */
  export interface IUpdatedBallastUllageResponse {
    responseStatus: IResponseStatus;
    id: number;
    correctionFactor: string;
    correctedUllage: string;
    quantityMt: string;
 }

/**
 * Interface for change response
 *
 * @export
 * @interface IUpdatedUllageResponse
 */
 export interface IUpdatedUllageResponse {
    responseStatus: IResponseStatus;
    id: number;
    correctionFactor: string;
    correctedUllage: string;
    quantityMt: string;
    fillingRatio?: number;
 }

 /**
 * Interface for save and validate model
 *
 * @export
 * @interface IValidateAndSaveStowage
 */
export interface IValidateAndSaveStowage {
    vesselId: number,
    voyageId: number,
    loadableStudyId: number,
    loadablePatternId: number,
    processId: string
 }

 /**
 * Interface for change response
 *
 * @export
 * @interface IUpdatedUllageResponse
 */
  export interface IUpdatedRdgLevelResponse {
    responseStatus: IResponseStatus;
    id: number;
    correctionFactor: string;
    correctedUllage: string;
    quantityMt: string;
 }

 /**
 * ENUM for validaion and save
 *
 * @export
 * @enum {number}
 */
export enum VALIDATION_AND_SAVE_STATUS {
    LOADABLE_PLAN_SUCCESS = 12,
    LOADABLE_PLAN_FAILED = 13,
    LOADABLE_PLAN_STARTED = 14,
    LOADABLE_PLAN_COMMUNICATED_TO_SHORE = 23,
}

 /**
 * Interface for algo response
 *
 * @export
 * @interface IAlgoResponse
 */
  export interface IAlgoResponse {
    responseStatus: IResponseStatus;
    algoErrors: IAlgoError[]
 }

 /**
 * Interface for algo error
 *
 * @export
 * @interface IAlgoError
 */
 export interface IAlgoError {
    errorHeading: string,
    errorDetails: string[]
 }

 /**
 * Interface for comment response
 *
 * @export
 * @interface ICommentResponse
 */
 export interface ICommentResponse {
    responseStatus: IResponseStatus;
    comment: ILoadablePlanCommentsDetails
 }

 /**
 * Interface for loadableQuantityCargo Details
 *
 * @export
 * @interface IloadableQuantityCargoDetails
 */
 export interface IloadableQuantityCargoDetails {
     total: number;
     cargoAbbreviation: string;
     minTolerence: number;
     maxTolerence: number;
     cargoNominationId? : number;
     commingleTotalQuantity?: number;
 }

 /**
  * interface for get simulator json response
  *
  * @export
  * @interface ILoadablePlanSimulatorResponse
  */
 export interface ILoadablePlanSimulatorResponse {
    departureCondition: any;
    responseStatus: IResponseStatus;
 }

 /**
  * interface for validate save status
  *
  * @export
  * @interface IValidateSaveStatus
  */
 export interface IValidateSaveStatus {
    validateAndSaveProcessing: boolean;
    buttonStatus?: number;
    editMode?: DATATABLE_EDITMODE | null;
 }






