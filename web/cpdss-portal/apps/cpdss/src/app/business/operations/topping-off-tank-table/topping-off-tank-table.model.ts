import { IResponseStatus } from '../../../shared/models/common.model';

/**
 * Interface for toopinf off ullage requenst
 *
 * @export
 * @interface IToppingoffUllageRequest
 */
export interface IToppingoffUllageRequest {
   id: number;
   tankId: number;
   correctedUllage: string;
   api: string,
   temperature: string;
   sg: string;
   isBallast: boolean;
   isCommingle: boolean;
}

/**
* Interface for toopinf off ullage reponse
*
* @export
* @interface IToppingoffUllageResponse
*/
export interface IToppingoffUllageResponse {
   responseStatus: IResponseStatus
   correctedUllage: number;
   correctionFactor: number;
   quantityMt: number;
   fillingRatio: string;
}