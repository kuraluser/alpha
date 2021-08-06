import { ICargoConditions, IResponse, ValueObject } from "../../../shared/models/common.model";
import { ICargoQuantities, ILoadableQuantityCargo, IShipCargoTank } from "../../core/models/common.model";

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
  machineryInUses: IMachineryInUses;
  loadingStages: ILoadingDischargingStages;
  loadingSequences: ILoadingDischargingSequences;
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
  machineryInUses: IMachineryInUses;
  dischargingStages: ILoadingDischargingStages;
  dischargingSequences: ILoadingDischargingSequences;
  toppingOffSequence: IToppingOffSequence[];
  cargoVesselTankDetails: ICargoVesselTankDetails;
  dischargingInfoId: number;
  synopticTableId: number;
  isDischargingInfoComplete: boolean;
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
  finalTrim: number;
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
  loadingMachinesInUses?: ILoadingMachinesInUses[];
  dischargingMachinesInUses?: IDischargingMachinesInUses[];
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
 * @interface ILoadingMachinesInUses
 */
export interface ILoadingMachinesInUses {
  id: number;
  loadingInfoId: number;
  machineId: number;
  machineTypeId: number;
  capacity: number;
  isUsing?: boolean;
}

/**
 * Interface for discharging machines used
 *
 * @export
 * @interface IDischargingMachinesInUses
 */
export interface IDischargingMachinesInUses {
  id: number;
  dischargingInfoId: number;
  machineId: number;
  machineTypeId: number;
  capacity: number;
  isUsing?: boolean;
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
  initialDischargingRate?: number;
  minDischargingRate: number;
  maxDischargingRate: number;
  minDeBallastingRate: number;
  maxDeBallastingRate: number;
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
}

/**
 * Interface for loading discharging stages
 *
 * @export
 * @interface ILoadingDischargingStages
 */
export interface ILoadingDischargingStages {
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
* Interface for loading sequence
*
* @export
* @interface ILoadingDischargingSequences
*/
export interface ILoadingDischargingSequences {
  reasonForDelays: IReasonForDelays[];
  loadingDischargingDelays?: ILoadingDischargingDelays[];
  dischargingDelays?: ILoadingDischargingDelays[];//TODO: need to be removed
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
  loadableQuantityCargoDetails: ILoadableQuantityCargo[];
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
  loadingMachineries: ILoadingMachinesInUses[];
  loadingStages: ILoadingDischargingStagesDetails;
  loadingDelays: ILoadingDischargingDelays[];
  toppingOffSequence: IToppingOffSequence[];
  vesselId?: number;
  voyageId?: number;
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
  dischargingMachineries: IDischargingMachinesInUses[];
  dischargingStages: ILoadingDischargingStagesDetails;
  dischargingDelays: ILoadingDischargingDelays[];
  toppingOffSequence: IToppingOffSequence[];
  vesselId?: number;
  voyageId?: number;
}

/**
 * Interface for loading stages for save
 *
 * @export
 * @interface ILoadingDischargingStagesDetails
 */
export interface ILoadingDischargingStagesDetails {
  trackStartEndStage: boolean;
  trackGradeSwitch: boolean;
  stageOffset: IStageOffset;
  stageDuration: IStageDuration;
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
 * @interface ILoadingSequenceValueObject
 */
 export interface ILoadingSequenceValueObject {
    id: number;
    reasonForDelay: ValueObject<IReasonForDelays[]>;
    duration: ValueObject<string>;
    cargo: ValueObject<ILoadableQuantityCargo>;
    quantity: number;
    isAdd: boolean;
    colorCode: string;
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
