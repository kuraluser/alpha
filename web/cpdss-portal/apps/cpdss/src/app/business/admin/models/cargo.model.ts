import { ICargo, ICountry, ValueObject } from '../../../shared/models/common.model';
import { IPort } from '../../core/models/common.model';

/**
 * Interface for cargo in cargo master
 *
 * @export
 * @interface ICargoDetails
 * @extends {ICargo}
 */
export interface ICargoDetails extends ICargo {
  portsNameArray?: Array<string>;
  portsLabel?: string;
  cargoLoadingInformation?: ICargoLoadingInformation[];
  countries?: ICountry[];
  countriesNameArray?: Array<string>;
  countriesLabel?: string;
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
