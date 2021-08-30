import { ICargoConditions, IMode, IPercentage, IResponse, ValueObject } from "../../../shared/models/common.model";
import { ICargo, ICargoQuantities, ILoadableQuantityCargo, IProtested, IShipCargoTank, ITank } from "../../core/models/common.model";

/**
 * Interface for Loadable information api response
 *
 * @export
 * @interface ILoadingInformationResponse
 */
export interface ILoadingInformationResponse {
  responseStatus: IResponse;
  loadingDetails: ILoadingDischargingDetails;
  loadingRates: ILoadingRates;
  berthDetails: IBerthDetails;
  machineryInUses: IMachineryInUsesResponse;
  loadingStages: ILoadingDischargingStagesResponse;
  loadingSequences: ILoadingDischargingSequencesResponse;
  toppingOffSequence: IToppingOffSequence[];
  cargoVesselTankDetails: ICargoVesselTankDetails;
  loadingInfoId: number;
  synopticTableId: number;
  isLoadingInfoComplete: boolean;
}

/**
 * Interface for discharging information response data
 *
 * @export
 * @interface IDischargingInformationResponse
 */
export interface IDischargingInformationResponse {
  responseStatus: IResponse;
  dischargingDetails: ILoadingDischargingDetails;
  dischargingRates: IDischargingRates;
  berthDetails: IBerthDetails;
  machineryInUses: IMachineryInUsesResponse;
  dischargingStages: ILoadingDischargingStagesResponse;
  dischargingSequences: ILoadingDischargingSequencesResponse;
  cargoVesselTankDetails: ICargoVesselTankDetailsResponse;
  dischargingInfoId: number;
  synopticTableId: number;
  isDischargingInfoComplete: boolean;
  cowDetails: ICOWDetailsResponse;
  postDischargeStageTime: IPostDischargeStageTime;
  loadedCargos: ICargo[];
  dischargeStudyName: string;
  dischargeSlopTanksFirst?: boolean;
  dischargeCommingledCargoSeperately?: boolean;
}




/**
* Interface for loading details
*
* @export
* @interface ILoadingDischargingDetails
*/
export interface ILoadingDischargingDetails {
  timeOfSunrise: string;
  timeOfSunset: string;
  startTime: string;
  trimAllowed: ITrimAllowed;
}

/**
 * Interface for trim details
 *
 * @export
 * @interface ITrimAllowed
 */
export interface ITrimAllowed {
  initialTrim: number;
  maximumTrim: number;
  finalTrim?: number;
  topOffTrim?: number;
}

/**
 * Interface for berth details
 *
 * @export
 * @interface IBerthDetails
 */
export interface IBerthDetails {
  availableBerths: IBerth[];
  selectedBerths: IBerth[];
}


/**
 * Interface for machinary in use
 *
 * @export
 * @interface IMachineryInUses
 */
export interface IMachineryInUses {
  machineTypes: IMachineTypes;
  tankTypes: IMachineTankTypes[];
  vesselBottomLine: IVesselManifoldBottomLine[];
  vesselManifold: IVesselManifoldBottomLine[];
  pumpTypes: IPumpTypes[];
  vesselPumps: IVesselPumps[];
  loadingDischargingMachinesInUses: Array<ILoadingMachinesInUse | IDischargingMachinesInUse>;
}

/**
 * Interface for machinary in use in api response
 *
 * @export
 * @interface IMachineryInUses
 */
export interface IMachineryInUsesResponse {
  machineTypes: IMachineTypes;
  tankTypes: IMachineTankTypes[];
  vesselBottomLine: IVesselManifoldBottomLine[];
  vesselManifold: IVesselManifoldBottomLine[];
  pumpTypes: IPumpTypes[];
  vesselPumps: IVesselPumps[];
  loadingMachinesInUses?: Array<ILoadingMachinesInUse>;
  dischargingMachinesInUses?: Array<IDischargingMachinesInUse>;
}

/**
 * Interface for machine vessel manifold and bottom line
 *
 * @export
 * @interface IVesselManifoldBottomLine
 */
export interface IVesselManifoldBottomLine {
  componentCode: string;
  componentName: string;
  componentType: number;
  id: number;
  machineTypeId: number;
  vesselId: number;
  isUsing?: boolean;
}



/**
 * Interface for machine tank types
 *
 * @export
 * @interface IMachineTankTypes
 */
export interface IMachineTankTypes {
  id: number;
  name: string;
}

/**
 * Interface for machine types
 *
 * @export
 * @interface IMachineTypes
 */
export interface IMachineTypes {
  BOTTOM_LINE: number;
  MANIFOLD: number;
  VESSEL_PUMP: number;
  EMPTY: number;
}


/**
 * Interface for pump types
 *
 * @export
 * @interface IPumpTypes
 */
export interface IPumpTypes {
  id: number;
  name: string;
}

/**
 * Interface for vessel pumps
 *
 * @export
 * @interface IVesselPumps
 */
export interface IVesselPumps {
  id: number;
  vesselId: number;
  machineType: number;
  pumpName: string;
  pumpCode: string;
  pumpCapacity: number;
  capacity?: number;
  isUsing?: boolean;
  pumpTypeId?: number;
}

/**
 * Interface for loading machine
 *
 * @export
 * @interface ILoadingMachinesInUse
 */
export interface ILoadingMachinesInUse {
  id: number;
  loadingInfoId: number;
  machineId: number;
  machineTypeId: number;
  capacity: number;
  isUsing?: boolean;
  pumpTypeId?: string;
}

/**
 * Interface for discharging machines used
 *
 * @export
 * @interface IDischargingMachinesInUse
 */
export interface IDischargingMachinesInUse {
  id: number;
  dischargingInfoId: number;
  machineId: number;
  machineTypeId: number;
  capacity: number;
  isUsing?: boolean;
  pumpTypeId?: string;
}


/**
 * Interface for loading rates
 *
 * @export
 * @interface ILoadingRates
 */
export interface ILoadingRates {
  id: number;
  initialLoadingRate?: number;
  minLoadingRate: number;
  maxLoadingRate: number;
  reducedLoadingRate?: number;
  minDeBallastingRate: number;
  maxDeBallastingRate: number;
  noticeTimeRateReduction: number;
  noticeTimeStopLoading: number;
  lineContentRemaining?: number;
  shoreLoadingRate: number;
}

/**
 * Interface for discharge rate
 *
 * @export
 * @interface IDischargingRates
 */
export interface IDischargingRates {
  id: number;
  initialDischargingRate: number;
  maxDischargingRate: number;
  minBallastingRate: number;
  maxBallastingRate: number;
}

/**
 * Interface for berth details
 *
 * @export
 * @interface IBerth
 */
export interface IBerth {
  berthId: number;
  portId: number;
  berthName: string;
  maxShipDepth: number;
  seaDraftLimitation: number;
  airDraftLimitation: number;
  maxManifoldHeight: number;
  regulationAndRestriction: string;
  itemsToBeAgreedWith?: string;
  loadingInfoId?: number;
  dischargingInfoId?: number;
  maxShpChannel: number;
  loadingBerthId?: number;
  dischargingBerthId?: number;
  hoseConnections: string;
  maxLoa: string;
  maxDraft: string;
  lineDisplacement: string;
  cargoCirculation: boolean;
  airPurge: boolean;
  maxManifoldPressure: number;
}

/**
 * Interface for loading discharging stages
 *
 * @export
 * @interface ILoadingDischargingStagesResponse
 */
export interface ILoadingDischargingStagesResponse {
  id: number;
  trackStartEndStage: boolean;
  trackGradeSwitch: boolean;
  stageOffset: number;
  stageDuration: number;
  stageOffsetList: IStageOffset[];
  stageDurationList: IStageDuration[];
}

/**
* Interface for stage offset list
*
* @export
* @interface IStageOffsetList
*/
export interface IStageOffset {
  id: number;
  stageOffsetVal: number;
}

/**
* Interface for stage duration list
*
* @export
* @interface IStageDurationList
*/
export interface IStageDuration {
  id: number;
  duration: number;
}


/**
* Interface for topping off sequence
*
* @export
* @interface IToppingOffSequence
*/
export interface IToppingOffSequence {
  id: number;
  loadingInfoId?: number;
  dischargingInfoId?: number;
  orderNumber: number;
  tankId: number;
  cargoId: number;
  shortName: string;
  cargoName: string;
  cargoAbbreviation: string;
  colourCode: string;
  remark: string;
  ullage: number;
  quantity: number;
  fillingRatio: number;
  api: number;
  displayOrder: number;
  temperature: number;
}

/**
* Interface for loading sequence response
*
* @export
* @interface ILoadingDischargingSequencesResponse
*/
export interface ILoadingDischargingSequencesResponse {
  reasonForDelays: IReasonForDelays[];
  loadingDelays?: ILoadingDischargingDelays[];
  dischargingDelays?: ILoadingDischargingDelays[];
}

/**
 *
 *
 * @export
 * @interface ILoadingDischargingSequences
 */
export interface ILoadingDischargingSequences {
  reasonForDelays: IReasonForDelays[];
  loadingDischargingDelays?: ILoadingDischargingDelays[];
}

/**
* Interface for reason for delays
*
* @export
* @interface IReasonForDelays
*/
export interface IReasonForDelays {
  id: number;
  reason: string;
}

/**
* Interface for loading delays
*
* @export
* @interface ILoadingDelays
*/
export interface ILoadingDischargingDelays {
  id: number;
  loadingInfoId?: number;
  dischargingInfoId?: number;
  reasonForDelayIds: number[];
  duration: number;
  cargoId: number;
  quantity: number;
  cargoNominationId?: number;
  sequenceNo?: number;
  isInitialDelay?: boolean;
}

/**
* Interface for cargo vessel tank details
*
* @export
* @interface ICargoVesselTankDetails
*/
export interface ICargoVesselTankDetails {
  cargoConditions: ICargoConditions[];
  cargoTanks: IShipCargoTank[][];
  cargoQuantities: ICargoQuantities[];
  loadableQuantityCargoDetails: ILoadedCargo[];
}

/**
* Interface for cargo vessel tank details response
*
* @export
* @interface ICargoVesselTankDetailsResponse
*/
export interface ICargoVesselTankDetailsResponse {
  cargoConditions: ICargoConditions[];
  cargoTanks: IShipCargoTank[][];
  cargoQuantities: ICargoQuantities[];
  loadableQuantityCargoDetails: ILoadedCargoResponse[];
}

/**
* Interface for loading information list
*
* @export
* @interface ILoadingInformation
*/
export interface ILoadingInformation {
  loadingInfoId: number;
  synopticalTableId: number;
  loadingDetails: ILoadingDischargingDetails;
  loadingRates: ILoadingRates;
  loadingBerths: IBerth[];
  loadingMachineries: ILoadingMachinesInUse[];
  loadingStages: ILoadingDischargingStages;
  loadingDelays: ILoadingDischargingDelays[];
  toppingOffSequence: IToppingOffSequence[];
  vesselId?: number;
  voyageId?: number;
  isLoadingInfoComplete:boolean;
}

/**
 * Interface for discharging info
 *
 * @export
 * @interface IDischargingInformation
 */
export interface IDischargingInformation {
  dischargingInfoId: number;
  synopticalTableId: number;
  dischargingDetails: ILoadingDischargingDetails;
  dischargingRates: IDischargingRates;
  dischargingBerths: IBerth[];
  berthDetails: IBerthDetails;
  dischargingMachineries: IDischargingMachinesInUse[];
  dischargingStages: ILoadingDischargingStages;
  dischargingDelays: ILoadingDischargingDelays[];
  dischargingSequences: ILoadingDischargingSequences;
  vesselId?: number;
  voyageId?: number;
  cowDetails: ICOWDetails;
  postDischargeStageTime: IPostDischargeStageTime;
  loadedCargos: ICargo[];
  cargoVesselTankDetails: ICargoVesselTankDetails;
  isDischargingInfoComplete: boolean;
  cargoTanks: ITank[];
  machineryInUses: IMachineryInUses;
  dischargeStudyName: string;
  dischargeSlopTanksFirst?: boolean;
  dischargeCommingledCargoSeperately?: boolean;
}

/**
 * Interface for loading stages for save
 *
 * @export
 * @interface ILoadingDischargingStages
 */
export interface ILoadingDischargingStages {
  trackStartEndStage: boolean;
  trackGradeSwitch: boolean;
  stageOffset: IStageOffset;
  stageDuration: IStageDuration;
  stageOffsetList?: IStageOffset[];
  stageDurationList?: IStageDuration[];
}


/**
* Interface for loading information save response
*
* @export
* @interface ILoadingInformationSaveResponse
*/
export interface ILoadingInformationSaveResponse {
  loadingInfoId: number;
  loadingInformation: ILoadingInformationResponse;
  responseStatus: IResponse;
  synopticalTableId: number;
  vesseld: number;
  voyageId: number;
}

/**
 * Interface for discharging information save response
 *
 * @export
 * @interface IDischargingInformationSaveResponse
 */
export interface IDischargingInformationSaveResponse {
  dischargingInfoId: number;
  dischargingInformation: IDischargingInformationResponse;
  responseStatus: IResponse;
  synopticalTableId: number;
  vesseld: number;
  voyageId: number;
}

/**
 * Interface for ports value object
 *
 * @export
 * @interface ILoadingDischargingSequenceValueObject
 */
export interface ILoadingDischargingSequenceValueObject {
  id: number;
  reasonForDelay: ValueObject<IReasonForDelays[]>;
  duration: ValueObject<string>;
  cargo: ValueObject<ILoadableQuantityCargo>;
  quantity: ValueObject<number>;
  isAdd: boolean;
  colorCode: string;
  sequenceNo?: ValueObject<number>;
  isInitialDelay?: boolean;
}

/**
* Interface for cargo vessel tank details
*
* @export
* @interface ILoadingSequenceDropdownData
*/
export interface ILoadingSequenceDropdownData {
  loadableQuantityCargo: ILoadableQuantityCargo[];
  reasonForDelays: IReasonForDelays[];
}

/**
 * Interface form port discharge time data
 *
 * @export
 * @interface IPostDischargeStageTime
 */
export interface IPostDischargeStageTime {
  dryCheckTime: string;
  slopDischargingTime: string;
  finalStrippingTime: string;
  freshOilWashingTime: string;
}

/**
 * Interface for COW details
 *
 * @export
 * @interface ICOWDetails
 */
export interface ICOWDetails {
  cowOption: IMode;
  cowPercentage: IPercentage;
  topCOWTanks: ITank[];
  bottomCOWTanks: ITank[];
  allCOWTanks: ITank[];
  tanksWashingWithDifferentCargo: ITanksWashingWithDifferentCargo[];
  cowStart: string;
  cowEnd: string;
  cowDuration: string;
  cowTrimMin: number;
  cowTrimMax: number;
  needFreshCrudeStorage: boolean;
  needFlushingOil: boolean;
  washTanksWithDifferentCargo: boolean;
  totalDuration: string;
}

/**
 * Interface for cow details response data
 *
 * @export
 * @interface ICOWDetailsResponse
 */
export interface ICOWDetailsResponse {
  cowOption: number;
  cowPercentage: number;
  topCOWTanks: ITank[];
  bottomCOWTanks: ITank[];
  allCOWTanks: ITank[];
  tanksWashingWithDifferentCargo: ITanksWashingWithDifferentCargo[];
  cowStart: string;
  cowEnd: string;
  cowDuration: string;
  cowTrimMin: number;
  cowTrimMax: number;
  needFreshCrudeStorage: boolean;
  needFlushingOil: boolean;
  washTanksWithDifferentCargo: boolean;
  totalDuration: string;
}

/**
 * Interface for selected tanks for washing with different cargo
 *
 * @export
 * @interface ITanksWashingWithDifferentCargo
 */
export interface ITanksWashingWithDifferentCargo {
  cargo: ICargo;
  washingCargo: ICargo;
  tanks: ITank[];
  selectedTanks: ITank[];
}

/**
 * Interface for list datas in discharging module
 *
 * @export
 * @interface IDischargeOperationListData
 */
export interface IDischargeOperationListData {
  protestedOptions: IProtested[],
  cowOptions: IMode[],
  cowPercentages: IPercentage[];
}

/**
 * Interface loaded cargo response in vessel
 *
 * @export
 * @interface ILoadedCargoResponse
 * @extends {ILoadableQuantityCargo}
 */
export interface ILoadedCargoResponse extends ILoadableQuantityCargo {
  shipFigure?: string;
  protested?: boolean;
  isCommingled?: boolean;
  slopQuantity?: number;
}

/**
 * Interface loaded cargo in vessel
 *
 * @export
 * @interface ILoadedCargo
 * @extends {ILoadableQuantityCargo}
 */
export interface ILoadedCargo extends ILoadableQuantityCargo {
  shipFigure?: string;
  actualQuantity?: string;
  protested?: ValueObject<IProtested>;
  isCommingled?: ValueObject<boolean>;
  isAdd?: boolean;
}

/**
 * Machine Types
 * @export
 * @enum {number}
 */
 export enum MACHINE_TYPES {
  EMPTY = 0,
  VESSEL_PUMP = 1,
  BOTTOM_LINE =  3,
  MANIFOLD =  2
}

/**
 * Pump Types
 * @export
 * @enum {number}
 */
 export enum Pump_TYPES {
  Cargo_Pump = 1,
  Ballast_Pump = 2,
  GS_Pump =  3,
  IG_Pump =  4,
  Stripping_Pump = 5,
  Strip_Eductor =  6 ,
  COW_Pump = 7
}

/**
* Interface for loading plan details
*
* @export
* @interface ILoadingPlanDetails
*/
export interface ILoadingPlanDetails {
  cargoTanks: IShipCargoTank[][];
  loadingInformation: ILoadingInformationResponse;
  planBallastDetails: IPlanBallastAndRob[];
  planRobDetails: IPlanBallastAndRob[];
  planStabilityParams: IPlanStabilityParams[];
  planStowageDetails: IPlanStowageDetails[];
}

/**
* Interface for ballast and rob details
*
* @export
* @interface IPlanBallastAndRob
*/
export interface IPlanBallastAndRob {
  conditionType: number;
  quantityM3: string;
  quantityMT: string;
  sounding: string;
  tankId: number;
  tankName: string;
  valueType: number;
}

/**
* Interface for loading plan stowage details
*
* @export
* @interface IPlanStabilityParams
*/
export interface IPlanStabilityParams {
  aftDraft: string;
  bm: string;
  conditionType: number;
  foreDraft: string;
  meanDraft: string;
  sf: string;
  trim: string;
  valueType: number;
}

export interface IPlanStowageDetails {
  api: string;
  cargoNominationId: number;
  conditionType: number;
  quantityM3: string;
  quantityMT: string;
  tankId: string;
  tankName: string;
  temperature: string;
  ullage: string;
  valueType: number;
}
