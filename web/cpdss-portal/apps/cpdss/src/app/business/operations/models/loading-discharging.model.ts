import { ICargoConditions, IMode, IPercentage, IResponse, ValueObject } from "../../../shared/models/common.model";
import { ICargo, ICargoQuantities, ILoadableQuantityCargo, IProtested, IShipCargoTank, ITank, IShipBallastTank, IShipBunkerTank } from "../../core/models/common.model";

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

  isLoadingInstructionsComplete: boolean;
  isLoadingPlanGenerated: boolean;
  isLoadingSequenceGenerated: boolean;
  loadingInfoStatusId: number
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
  isLoadingInfoComplete: boolean;
  isLoadingPlanGenerated:boolean;
  isLoadingSequenceGenerated: boolean;
  isLoadingInstructionsComplete: boolean


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
  cargo: ValueObject<ILoadedCargo>;
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
  isCommingledDischarge?: boolean;
  slopQuantity?: number;
  isCommingledCargo?: boolean;
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
  convertedSlopQuantity?: string;
  convertedOrderedQuantity?: string;
  protested?: ValueObject<IProtested>;
  isCommingledDischarge?: ValueObject<boolean>;
  isCommingledCargo?: boolean;
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
  BOTTOM_LINE = 3,
  MANIFOLD = 2
}

/**
 * Pump Types
 * @export
 * @enum {number}
 */
export enum Pump_TYPES {
  Cargo_Pump = 1,
  Ballast_Pump = 2,
  GS_Pump = 3,
  IG_Pump = 4,
  Stripping_Pump = 5,
  Strip_Eductor = 6,
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
  ballastCenterTanks: IShipBallastTank[][];
  ballastFrontTanks: IShipBallastTank[][];
  ballastRearTanks: IShipBallastTank[][];
  bunkerRearTanks: IShipBunkerTank[][];
  bunkerTanks: IShipBunkerTank[][];
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

/**
 * Interface for cargo details us value object
 *
 * @export
 * @interface ICargoDetailValueObject
 */
export interface ICargoDetailValueObject {
  blRefNo: ValueObject<string>;
  bbl: ValueObject<number>;
  lt: ValueObject<number>;
  mt: ValueObject<number>;
  kl: ValueObject<number>;
  api: ValueObject<number>;
  temp: ValueObject<number>;
  cargoName: string;
  isAdd: boolean;
  cargoNominationId: number;
  portId: number;
  cargoId: number;
  isNewRow: boolean;
  id: number;
}

/**
* Interface for cargo details
*
* @export
* @interface ICargoDetail
*/
export interface ICargoDetail {
  blRefNo: string;
  quantityBbls: number;
  quantityLT: number;
  quantityMt: number;
  quantityKl: number;
  api: number;
  temperature: number;
  cargoName: string;
  portId: number;
  id?: number;
}

/**
* Interface for tank details us value object
*
* @export
* @interface ITankDetailsValueObject
*/
export interface ITankDetailsValueObject {
  tankName: ValueObject<string>;
  ullage: ValueObject<number>;
  temperature?: ValueObject<number>;
  api?: ValueObject<number>;
  quantity?: ValueObject<number | string>;
  density?: ValueObject<number>;
  id?: ValueObject<number>;
  tankId?: number;
  loadablePatternId?: ValueObject<number>;
  fillingPercentage?: ValueObject<number>;
  sounding?: ValueObject<number|string>;
  cargoNominationId?: ValueObject<number>;
  isAdd?: boolean;
  arrivalDeparture?: number;
  actualPlanned?: number;
  correctionFactor?: number;
  correctedUllage?: number;
  colorCode?: string;
  sg?: number;
}

/**
* enum for ullage status
*
* @export
* @enum ULLAGE_STATUS
*/
export enum ULLAGE_STATUS {
  ARRIVAL = 'ARRIVAL',
  DEPARTURE = 'DEPARTURE'
}

/**
* Interface for ullage save/update
*
* @export
* @interface IUllageSaveDetails
*/
export interface IUllageSaveDetails {
  billOfLandingList: IBillOfLandingList[];
  ullageUpdList: IUllageUpdList[];
  billOfLandingListRemove: IBillOfLandingList[];
  ballastUpdateList: IBallastUpdateList[];
  robUpdateList: IRobUpdateList[];
  isValidate: boolean
}

/**
* Interface for bill of ladding save
*
* @export
* @interface IBillOfLandingList
*/
export interface IBillOfLandingList {
  loadingId?: number | string;
  portId: number;
  cargoId: number;
  blRefNumber: string;
  bblAt60f: number;
  quantityLt: number;
  quantityMt: number;
  klAt15c: number;
  api: number | string;
  temperature: number;
  isUpdate?: boolean;
  isActive?: boolean | string;
  version?: string;
  id?: string | number;
}

/**
* Interface for ullage update save data
*
* @export
* @interface IUllageUpdList
*/
export interface IUllageUpdList {
  loadingInformationId: number | string;
  tankId: number;
  temperature: number;
  correctedUllage: number;
  quantity: number;
  fillingPercentage: number | string;
  cargoNominationId: number | string;
  arrival_departutre: number | string;
  actual_planned: number;
  correction_factor: number;
  api: number | string;
  isUpdate: boolean;
  port_xid?: string | number;
  port_rotation_xid?: string | number;
  grade?: string;
  isActive?: boolean;
  ullage? : string | number;
}

/**
* Interface for ballast update list
*
* @export
* @interface IBallastUpdateList
*/
export interface IBallastUpdateList {
  loadingInformationId: number | string;
  tankId: number | string;
  temperature: number | string;
  quantity: number | string;
  sounding: number | string;
  correctedUllage: number | string;
  correctionFactor: string | number;
  filling_percentage: number | string;
  arrival_departutre: number | string;
  actual_planned: number | string;
  color_code: string;
  sg: number | string;
  isUpdate: boolean;
  observedM3?: string;
  fillingRatio?: string;
  portXId?: string | number;
  portRotationXId?: string | number;
  isValidate?: string;
  isActive?: boolean;
  ullage? : string | number;
}

/**
* Interface for rob update list
*
* @export
* @interface IRobUpdateList
*/
export interface IRobUpdateList {
  loadingInformationId: number | string;
  tankId: number;
  quantity: number;
  isUpdate?: boolean;
  density: number;
  colour_code: string;
  actual_planned: number | string;
  arrival_departutre: number;
  portXId?: string | number;
  portRotationXId?: string | number;
  observedM3?: string;
  temperature?: string;
  correctedUllage?: string;
  correctionFactor?: string;
  fillingRatio?: string;
  isActive?: boolean;
  ullage?: string | number;
}

/**
* Interface for ullage update list
*
* @export
* @interface IUllageUpdateDetails
*/
export interface IUllageUpdateDetails {
  ballastCenterTanks: IShipBallastTank[][];
  ballastFrontTanks: IShipBallastTank[][];
  ballastRearTanks: IShipBallastTank[][];
  billOfLaddingList: IBillOfLandingListGetResponse[];
  bunkerRearTanks: IShipBunkerTank[][];
  bunkerTanks: IShipBunkerTank[][];
  cargoQuantityDetails: ICargoQuantityGetResponse[];
  cargoTanks: IShipCargoTank[][];
  portLoadablePlanBallastDetails: ILoadablePlanBallastDetails[];
  portLoadablePlanRobDetails: ILoadablePlanRobDetails[];
  portLoadablePlanStowageDetails: ILoadablePlanStowageDetails[];
  isPlannedValues?: boolean;
}

/**
* Interface for bill of ladding list get response
*
* @export
* @interface IBillOfLandingListGetResponse
*/
export interface IBillOfLandingListGetResponse {
  cargoNominationId: number;
  cargoName: string;
  cargoId: number;
  cargoColor: string;
  cargoAbbrevation: string;
  billOfLaddings: [];
}

/**
* Interface for cargo quantity get response
*
* @export
* @interface ICargoQuantityGetResponse
*/
export interface ICargoQuantityGetResponse {
  actualAvgApi: number;
  actualAvgTemp: number;
  actualQuantityTotal: number;
  blAvgApi: number;
  blAvgTemp: number
  blQuantityBblsTotal: number
  blQuantityKLTotal: number;
  blQuantityLTTotal: number
  blQuantityMTTotal: number
  cargoAbbrevation: string
  cargoColor: string
  cargoId: number
  cargoName: string;
  cargoNominationId: 17759
  difference: number;
  maxQuantity: number;
  maxTolerance: number;
  minQuantity: number;
  minTolerance: number
  nominationApi: number
  nominationTemp: number
  nominationTotal: number;
}

/**
* Interface for loadable plan ballast details
*
* @export
* @interface ILoadablePlanBallastDetails
*/
export interface ILoadablePlanBallastDetails {
  active: boolean;
  actualPlanned: number;
  arrivalDeparture: number;
  cargoId: number;
  colorCode: string;
  correctedUllage: number;
  correctionFactor: number;
  fillingPercentage: string;
  id: number;
  loadablePatternId: number;
  quantity: number;
  rdgUllage: number;
  sg: number | string;
  sounding: number;
  tankId: number;
  tankName: string;
  tankShortName: string;

}

/**
* Interface for loadable plan rob details
*
* @export
* @interface ILoadablePlanRobDetails
*/
export interface ILoadablePlanRobDetails {
  active: boolean;
  actualPlanned: number;
  arrivalDeparture: number;
  colorCode: string;
  density: number;
  id: number;
  loadablePatternId: number;
  quantity: number;
  tankId: number
  tankName: string;
  tankShortName: string;
}

/**
* Interface for loadable plan stowage details
*
* @export
* @interface ILoadablePlanStowageDetails
*/
export interface ILoadablePlanStowageDetails {
  abbreviation: string;
  active: boolean;
  actualPlanned: number;
  api: number;
  arrivalDeparture: number;
  cargoId: number;
  cargoNominationId: number;
  colorCode: string;
  correctedUllage: number;
  correctionFactor: number;
  fillingPercentage: string
  id: number;
  loadablePatternId: number;
  observedBarrels: number;
  observedBarrelsAt60: number;
  observedM3: number;
  quantity: number;
  rdgUllage: number;
  tankId: number;
  tankName: string;
  tankShortName: string;
  temperature: number;
  ullage: number;
  weight: number;
}

/**
* Interface for ullage quantity stowage request
*
* @export
* @interface IUllageQuantityRequest
*/
export interface IUllageQuantityRequest {
  api?: number;
  correctedUllage?: number;
  id: number;
  isBallast?: boolean;
  isCommingle?: boolean;
  sg?: number;
  tankId?: number;
  temperature?: number;
}
/**
* Interface for ullage quantity response
*
* @export
* @interface IUllageQuantityResponse
*/

export interface IUllageQuantityResponse {
  commingle: boolean;
  correctedUllage: number;
  correctionFactor: number;
  fillingRatio: number;
  isBallast: boolean;
  quantityMt: number;
  responseStatus: IResponse;
}

/**
* Enum for ullage status text
*
* @export
* @enum ULLAGE_STATUS_TEXT
*/
export enum ULLAGE_STATUS_TEXT {
  'Plan Generated'= 5,
  'Validation In Progress' = 12,
  'Validation Successfull' = 13,
  'Validation Failed' = 14,
}

/**
* Enum for ullage status
*
* @export
* @enum ULLAGE_STATUS_TEXT
*/
export enum ULLAGE_STATUS_VALUE {
  'GENERATED'= 5,
  'IN_PROGRESS' = 12,
  'SUCCESS' = 13,
  'ERROR' = 14,
}
