import { ICountry, IResponseStatus, ValueObject } from '../../../shared/models/common.model';
import { ICargo, IPort } from '../../core/models/common.model';

/**
 * Interface for cargo in cargo master
 *
 * @export
 * @interface ICargoDetails
 * @extends {ICargo}
 */
export interface ICargoDetails extends ICargo {
  port?: Array<string>;
  portsLabel?: string;
  loadingInformation?: ICargoLoadingInformation[];
  countries?: ICountry[];
  countriesNameArray?: Array<string>;
  countriesLabel?: string;
  type?: string;
  reidVapourPressure: string;
  gas: string;
  totalWax: string;
  pourPoint: string;
  cloudPoint: string;
  viscosity: string;
  cowCodes: string;
  hydrogenSulfideOil: string;
  hydrogenSulfideVapour: string;
  benzene: string;
  specialInstrictionsRemark: string;
  assayDate?: string;
}

/**
 * Interface for cargo laoding information
 *
 * @export
 * @interface ICargoLoadingInformation
 */
export interface ICargoLoadingInformation {
  id: number;
  country: ICountry;
  port: IPort;
  ports?: IPort[];
}

/**
 * Interface for cargo loading infromation value object
 *
 * @export
 * @interface ICargoLoadingInformationValueObject
 */
export interface ICargoLoadingInformationValueObject {
  id: number;
  country: ValueObject<ICountry>;
  port: ValueObject<IPort>;
}

/**
 * Interface for API and Temp popup data
 *
 * @export
 * @interface IAPITempPopupData
 */
export interface IAPITempPopupData {
  portData: IAPITempLoadingPorts[];
  cargoId: number;
  cargoName: string;
}

/**
 * Interface for port
 *
 * @export
 * @interface IAPITempLoadingPorts
 */
export interface IAPITempLoadingPorts {
  id: number;
  isAdd?: boolean;
  name: string;
  quantity?: number;
}

/**
 * Interface for cargo port history data
 *
 * @export
 * @interface IAPITempPortHistory
 */
export interface IAPITempPortHistory {
  cargoId?: number;
  loadingPortId: number;
  loadedDate: string;
  api: number;
  temperature: number;
}

/**
 * Interface for cargo port monthwise data
 *
 * @export
 * @interface IAPITempMonthWiseHistory
 */
export interface IAPITempMonthWiseHistory {
  loadingPortId: number;
  loadedYear: number;
  loadedMonth: number;
  api: number | string;
  temperature: number | string;
}

/**
 * Interface for temp and api history api response data
 */
export interface ICargoAPITempHistoryResponse {
  responseStatus: IResponseStatus;
  portHistory: IAPITempPortHistory[];
  monthlyHistory: IAPITempMonthWiseHistory[];
}

/**
 * Interface for tem and api history request data
 *
 * @export
 * @interface IAPITempHistoryRequest
 */
export interface IAPITempHistoryRequest {
  cargoId: number;
  loadingPortIds: number[];
}


/**
 * Interface for cargos api response
 *
 * @export
 * @interface ICargosResponse
 */
export interface ICargosResponse {
  responseStatus: IResponseStatus;
  cargos: ICargoDetails[];
  totalElements: number;
}

/**
 * Interface for cargo details api response
 *
 * @export
 * @interface ICargoResponse
 */
export interface ICargoResponse {
  responseStatus: IResponseStatus;
  cargo: ICargoDetails;
}

/**
 * Interface for save cargo response
 *
 * @export
 * @interface ISaveCargoResponse
 */
export interface ISaveCargoResponse {
  responseStatus: IResponseStatus;
  id: number;
  cargo: ICargoDetails;
}

/**
 * Interface for cargo delete api response
 *
 * @export
 * @interface IDeleteCargoResponse
 */
export interface IDeleteCargoResponse {
  responseStatus: IResponseStatus;
}

