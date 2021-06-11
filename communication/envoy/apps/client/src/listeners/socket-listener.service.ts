import { CommonLoggerService, InboundEventDataTransfer, CommonTransportEventService, CommonFinishEventService, CommonResendEventService, CommonConfirmEventService, CommonCancelEventService } from '@envoy/common';
import { ResendEventDataTransfer } from '@envoy/common';
import { SocketClientService } from '../socket/socket-client.service';
import { Injectable } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';

/**
 * Class to listen for socket events from server
 */
@Injectable()
export class SocketListenerService {

    constructor(private configService: ConfigService,
        private readonly socketService: SocketClientService,
        private commonTransportEventService: CommonTransportEventService,
        private commonFinishEventService: CommonFinishEventService,
        private commonResendEventService: CommonResendEventService,
        private commonConfirmEventService: CommonConfirmEventService,
        private commonCancelEventService: CommonCancelEventService,
        private logger: CommonLoggerService) {
        this.logger.setContext('SocketListenerService');

        // Listening for transport send events
        socketService.getListenerSocket(true).on('send', (eventData: InboundEventDataTransfer, callback) => {
            const shipId = this.configService.get<string>('shipId');
            this.commonTransportEventService.handleSendEvent(eventData, shipId);
            callback(eventData.fileName);
        });

        // Listening for transport finish events
        socketService.getListenerSocket(true).on('finish', (eventData: InboundEventDataTransfer) => {
            const shipId = this.configService.get<string>('shipId');
            this.logger.log("Finish event received for client" + eventData.clientId + " with messageId " + eventData.messageId);
            this.commonFinishEventService.handleFinishEvent(eventData, shipId).then(result => {
                eventData.buf = result;
                this.logger.log("Sending finish ack event received for client" + eventData.clientId + " with messageId " + eventData.messageId);
                const ioClient = socketService.getSocket();
                if (!ioClient || !ioClient.connected) {
                    this.logger.warn('Error socket connection');
                    return;
                }
                //Calling socket.emit to send the data packets. Sending finish ack as async
                ioClient.emit('finishack', eventData);
            });
        });

        // Listening for finish ack events
        socketService.getListenerSocket(true).on('finishack', (eventData: InboundEventDataTransfer) => {
            const shipId = this.configService.get<string>('shipId');
            this.logger.log("Finish ack event received for client" + eventData.clientId + " with messageId " + eventData.messageId);
            this.commonFinishEventService.handleFinishAckEvent(eventData, shipId);
        });

        // Listening for resend events
        socketService.getListenerSocket(true).on('resend', (resendPacket: ResendEventDataTransfer) => {
            this.logger.log("Resend event received for client" + resendPacket.clientId + " with messageId " + resendPacket.messageId);
            this.commonResendEventService.handleResendEvent(resendPacket);
        });

        // Listening for confirm events
        socketService.getListenerSocket(true).on('confirm', (eventData: InboundEventDataTransfer) => {
            const shipId = this.configService.get<string>('shipId');
            this.logger.log("Confirm event received for client" + eventData.clientId + " with messageId " + eventData.messageId);
            this.commonConfirmEventService.handleConfirmEvent(eventData, shipId).then(result => {
                eventData.buf = result;
                this.logger.log("Sending confirm ack event received for client" + eventData.clientId + " with messageId " + eventData.messageId);
                const ioClient = socketService.getSocket();
                if (!ioClient || !ioClient.connected) {
                    this.logger.warn('Error socket connection');
                    return;
                }
                //Calling socket.emit to send the data packets. Sending confirm ack as async
                ioClient.emit('confirmack', eventData);
            });
        });

        // Listening for confirm ack events
        socketService.getListenerSocket(true).on('confirmack', (eventData: InboundEventDataTransfer) => {
            const shipId = this.configService.get<string>('shipId');
            this.logger.log("Confirm ack event received for client" + eventData.clientId + " with messageId " + eventData.messageId);
            this.commonConfirmEventService.handleConfirmAckEvent(eventData, shipId);
        });

        // Listening for cancel events
        socketService.getListenerSocket(true).on('cancel', (eventData: InboundEventDataTransfer) => {
            const shipId = this.configService.get<string>('shipId');
            this.logger.log("Cancel event received for client" + eventData.clientId + " with messageId " + eventData.messageId);
            this.commonCancelEventService.handleCancelEvent(eventData, shipId).then(result => {
                eventData.buf = result;
                this.logger.log("Sending cancel ack event received for client" + eventData.clientId + " with messageId " + eventData.messageId);
                const ioClient = socketService.getSocket();
                if (!ioClient || !ioClient.connected) {
                    this.logger.warn('Error socket connection');
                    return;
                }
                //Calling socket.emit to send the data packets. Sending cancel ack as async
                ioClient.emit('cancelack', eventData);
            });
        });

        // Listening for cancel ack events
        socketService.getListenerSocket(true).on('cancelack', (eventData: InboundEventDataTransfer) => {
            const shipId = this.configService.get<string>('shipId');
            this.logger.log("Cancel ack event received for client" + eventData.clientId + " with messageId " + eventData.messageId);
            this.commonCancelEventService.handleCancelAckEvent(eventData, shipId);
        });
    }
}