import {
    WebSocketGateway, SubscribeMessage, MessageBody, ConnectedSocket,
    OnGatewayConnection, OnGatewayDisconnect
} from '@nestjs/websockets';
import { Socket } from 'socket.io';
import { InboundEventDataTransfer, CommonCancelEventService, CommonLoggerService, CommonTransportEventService, CommonFinishEventService, ResendEventDataTransfer, CommonResendEventService, CommonConfirmEventService, LogEventDataTransfer, CommonUtilService } from "@envoy/common";
import { ServerUtilService } from '../utils/server-util.service';
import { ConfigService } from '@nestjs/config';
import { verify, Secret } from 'jsonwebtoken';
import * as path from 'path';
/**
 * Socket server service
 */
@WebSocketGateway({
    path: '/ws', namespace: /^\/id-\w+$/, transports: ['polling', 'websocket'], pingInterval: 20000,
    pingTimeout: 10000
})
export class SocketServerService implements OnGatewayConnection, OnGatewayDisconnect {

    constructor(private configService: ConfigService, 
        private commonTransportEventService: CommonTransportEventService,
        private commonFinishEventService: CommonFinishEventService,
        private commonResendEventService: CommonResendEventService,
        private commonConfirmEventService: CommonConfirmEventService,
        private commonCancelEventService: CommonCancelEventService,
        private serverUtilService: ServerUtilService,
        private commonUtilService: CommonUtilService,
        private logger: CommonLoggerService) {
        this.logger.setContext('SocketServerService');
    }

    /**
     * Overriden method for handling websocket connection
     * 
     * @param socket 
     */
    async handleConnection(socket: Socket) {
        try {
            console.log(socket.handshake.url);
            const shipId: string = socket.handshake.headers['x-shipid'];
            const authToken = socket.handshake.headers['x-token'];
            const clientPublicKey: Secret = this.serverUtilService.getClientPublicKey(shipId);
            const jwtPayload: any = verify(authToken, clientPublicKey, { algorithms: ['RS256'] });
            if (jwtPayload.shipid.toString() !== shipId) {
                this.logger.warn(`Auth failed ${jwtPayload.shipid.toString()}`);
                throw new Error("Auth failed");
            }
        } catch (err) {
            this.logger.error(`Error in authentication ${err.message}`, err.stack);
            socket.disconnect(true);
            return false;
        }
        const listener = socket.handshake.headers['x-listener'];
        if (listener === "yes") {
            this.logger.log('Listener Connected for the ship ' + socket.nsp.name);
            this.serverUtilService.storeClientSocket(socket.nsp.name, socket);
        } else {
            this.logger.log('Connected ' + socket.nsp.name);
        }
    }

    /**
     * Overriden method for handling disconnect events from websocket
     * 
     * @param socket 
     */
    async handleDisconnect(socket: Socket) {
        const listener = socket.handshake.headers['x-listener'];
        if (listener === "yes") {
            this.logger.log('Listener Disconnected for the ship ' + socket.nsp.name);
        } else {
            this.logger.log('Disconnected ' + socket.nsp.name);
        }
        // this.serverUtilService.removeClientSocket(socket.nsp.name);
    }

    /**
     * All client data transfer events are handled here
     * 
     * @param eventData 
     * @param socket 
     */
    @SubscribeMessage('send')
    public handleEvent(@MessageBody() eventData: InboundEventDataTransfer, @ConnectedSocket() socket: Socket): string {
        const shipId: string = socket.nsp.name.substring(socket.nsp.name.indexOf('-') + 1, socket.nsp.name.length);
        this.commonTransportEventService.handleSendEvent(eventData, shipId);
        return eventData.fileName;
    }

    /**
     * All client finished data transfer handled 
     * 
     * @param eventData 
     * @param socket 
     */
    @SubscribeMessage('finish')
    public handleFinishedEvent(@MessageBody() eventData: InboundEventDataTransfer,
        @ConnectedSocket() socket: Socket): void {
        const shipId: string = socket.nsp.name.substring(socket.nsp.name.indexOf('-') + 1, socket.nsp.name.length);
        this.logger.log("Received finish event from client " + eventData.clientId + " message " + eventData.messageId + " from the ship " + shipId);
        this.commonFinishEventService.handleFinishEvent(eventData, shipId).then(result => {
            eventData.buf = result;
            this.logger.log("Sending finish ack event received for client" + eventData.clientId + " with messageId " + eventData.messageId);
            const ioClient = this.serverUtilService.getClientSocket(`/id-${shipId}`);
            if (!ioClient || !ioClient.connected) {
                this.logger.warn('Error socket connection');
                return;
            }
            //Calling socket.emit to send the data packets. Receiving ack async
            ioClient.emit('finishack', eventData);
        });
    }

     /**
     * Handle finish ack event
     * 
     * @param eventData 
     * @param socket 
     */
    @SubscribeMessage('finishack')
    public handleFinishAckEvent(@MessageBody() eventData: InboundEventDataTransfer,
        @ConnectedSocket() socket: Socket): void {
        const shipId: string = socket.nsp.name.substring(socket.nsp.name.indexOf('-') + 1, socket.nsp.name.length);
        this.logger.log("Received finish ack event from client" + eventData.clientId + " with messageId " + eventData.messageId);
        this.commonFinishEventService.handleFinishAckEvent(eventData, shipId);
    }



    /**
     * All client resend data transfer handled 
     * 
     * @param eventData 
     * @param socket 
     */
    @SubscribeMessage('resend')
    public handleResendEvent(@MessageBody() eventData: ResendEventDataTransfer,
        @ConnectedSocket() socket: Socket): void {
        const shipId: string = socket.nsp.name.substring(socket.nsp.name.indexOf('-') + 1, socket.nsp.name.length);
        this.logger.log("Received resend event from client " + eventData.clientId + " message " + eventData.messageId + " from the ship " + shipId);
        this.commonResendEventService.handleResendEvent(eventData);
    }


    /**
     * Handle confirm event
     * 
     * @param eventData 
     * @param socket 
     */
    @SubscribeMessage('confirm')
    public handleConfirmEvent(@MessageBody() eventData: InboundEventDataTransfer,
        @ConnectedSocket() socket: Socket): void {
        const shipId: string = socket.nsp.name.substring(socket.nsp.name.indexOf('-') + 1, socket.nsp.name.length);
        this.logger.log("Recieved confirm event from client " + eventData.clientId + " message " + eventData.messageId + " from the ship " + shipId);
        this.commonConfirmEventService.handleConfirmEvent(eventData, shipId).then(result => {
            eventData.buf = result;
            this.logger.log("Sending confirm ack event received for client" + eventData.clientId + " with messageId " + eventData.messageId);
            const ioClient = this.serverUtilService.getClientSocket(`/id-${shipId}`);
            if (!ioClient || !ioClient.connected) {
                this.logger.warn('Error socket connection');
                return;
            }
            //Calling socket.emit to send the data packets. Receiving ack async
            ioClient.emit('confirmack', eventData);
        });
    }

    /**
     * Handle confirmack event
     * 
     * @param eventData 
     * @param socket 
     */
    @SubscribeMessage('confirmack')
    public handleConfirmAckEvent(@MessageBody() eventData: InboundEventDataTransfer,
        @ConnectedSocket() socket: Socket): void {
        const shipId: string = socket.nsp.name.substring(socket.nsp.name.indexOf('-') + 1, socket.nsp.name.length);
        this.logger.log("Confirm ack event received for client" + eventData.clientId + " with messageId " + eventData.messageId);
        this.commonConfirmEventService.handleConfirmAckEvent(eventData, shipId);
    }


    /**
     * Handle cancel event
     * 
     * @param eventData 
     * @param socket 
     */
    @SubscribeMessage('cancel')
    public handleCancelEvent(@MessageBody() eventData: InboundEventDataTransfer,
        @ConnectedSocket() socket: Socket): void {
        const shipId: string = socket.nsp.name.substring(socket.nsp.name.indexOf('-') + 1, socket.nsp.name.length);
        this.logger.log("Recieved cancel event from client " + eventData.clientId + " message " + eventData.messageId + " from the ship " + shipId);
        this.commonCancelEventService.handleCancelEvent(eventData, shipId).then(result => {
            eventData.buf = result;
            this.logger.log("Sending cancel ack event received for client" + eventData.clientId + " with messageId " + eventData.messageId);
            const ioClient = this.serverUtilService.getClientSocket(`/id-${shipId}`);
            if (!ioClient || !ioClient.connected) {
                this.logger.warn('Error socket connection');
                return;
            }
            //Calling socket.emit to send the data packets. Receiving ack async
            ioClient.emit('cancelack', eventData);
        });
    }

    /**
     * Handle cancelack event
     * 
     * @param eventData 
     * @param socket 
     */
    @SubscribeMessage('cancelack')
    public handleCancelAckEvent(@MessageBody() eventData: InboundEventDataTransfer,
        @ConnectedSocket() socket: Socket): void {
        const shipId: string = socket.nsp.name.substring(socket.nsp.name.indexOf('-') + 1, socket.nsp.name.length);
        this.logger.log("Cancel ack event received for client" + eventData.clientId + " with messageId " + eventData.messageId);
        this.commonCancelEventService.handleCancelAckEvent(eventData, shipId);
    }

    /**
     * Confirm log event
     * 
     * @param eventData 
     * @param socket 
     */
    @SubscribeMessage('log')
    public handleLogvent(@MessageBody() eventData: LogEventDataTransfer,
        @ConnectedSocket() socket: Socket): void {
        const shipId: string = socket.nsp.name.substring(socket.nsp.name.indexOf('-') + 1, socket.nsp.name.length);
        const directory = path.normalize(`${this.configService.get<string>('appPath')}/shiplogs/${shipId}`);
        const filePath = `${directory}/${eventData.logTime}.zip`;
        this.commonUtilService.writeToFile(directory, filePath, eventData.buf).catch(err => {
            this.logger.error("Ship log write error from the ship " + shipId, err.stack);
        })
    }

}