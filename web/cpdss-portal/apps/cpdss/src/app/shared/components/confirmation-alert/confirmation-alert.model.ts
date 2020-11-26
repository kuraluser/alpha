import { Message } from 'primeng/api';

/**
 * Interface for Confirmation toast message
 *
 * @export
 * @interface IConfirmationMessage
 * @extends {Message}
 */
export interface IConfirmationMessage extends Message {
    data: IConfirmationMessageData;
}

/**
 * Interface for confirmation toast data
 *
 * @export
 * @interface IConfirmationMessageData
 */
export interface IConfirmationMessageData {
    confirmLabel: string;
    rejectLabel?: string;
}