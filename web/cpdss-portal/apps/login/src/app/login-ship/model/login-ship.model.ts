/**
 * Interface for  ship login
 *
 * @export
 * @interface ILoginShip
 */
export interface ILoginShip{
    username: string;
    password: string;
}

/**
 * Interface for  ship login response
 *
 * @export
 * @interface ILoginShipResponse
 */
export interface ILoginShipResponse{
    expiryReminder: any;
    responseStatus: IStatus;
    token: string;
    status?: string;
    errorCode?: string;
    correlationId?: string;
}

/**
 * Interface for  Status
 *
 * @export
 * @interface IStatus
 */
export interface IStatus{
    status: string;
}