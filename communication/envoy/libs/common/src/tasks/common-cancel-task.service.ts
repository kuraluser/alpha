import { Injectable } from '@nestjs/common';
import { CommonDataStore } from '../store/common-db-store';
import { InboundEventData, InboundEventDataTransfer } from '../models/common-models';
import { CommonDBService } from '../services/common-db.service';
import { CommonLoggerService } from '../services/common-logger.service';
import * as _ from 'underscore';
import { TaskCompleted } from '../utils/common-app.constants';
import { ConfigService } from '@nestjs/config';
import { UpdateResult } from 'typeorm';
import { InBoundEventDataEntity } from '../entities/common-inbound-event.entity';

/**
 * Class to perform common cancel task functions
 */
@Injectable()
export class CommonCancelTaskService {

    private timerSleepInMs = 20000;

    private ioClient: SocketIOClient.Socket | SocketIO.Socket;

    constructor(
        private configService: ConfigService,
        private readonly commonDbStore: CommonDataStore,
        private readonly commonDbService: CommonDBService<InBoundEventDataEntity, InboundEventData>,
        private logger: CommonLoggerService
    ) {
        this.logger.setContext('CommonCancelTaskService');
    }

    /**
     * Method to recursively cancel data sending using websockets Socket.IO library
     *
     * @param eventData
     */
    public cancelDataTransfer(eventData: InboundEventData, taskCompleted: TaskCompleted): void {
        try {
            //Copying ioclient object 
            this.ioClient = eventData.ioClient;
            //Removing ioclient object from eventdata before processing
            eventData = _.omit(eventData, 'ioClient');

            const dataToSend: InboundEventDataTransfer = {
                clientId: eventData.clientId,
                fileName: eventData.fileName,
                messageId: eventData.messageId,
                buf: "cancel"
            };
            //Checking if the socket is connected if not retry after delay
            if (this.ioClient && this.ioClient.connected) {
                //Calling socket.emit to send the data packets. Receiving ack async
                this.ioClient.emit('cancel', dataToSend);
            } else {
                this.logger.warn('Error socket connection');
            }

        } catch (err) {
            this.logger.error(`Error in cancel request ${err.message}`, err.stack);
        } finally {
            (async () => {
                //Updating the confirm read timestamp to show the file is still under processing
                const updateResult: UpdateResult = await this.commonDbService.updateData(this.commonDbStore.getInboundEventStore(),
                    { clientId: eventData.clientId, messageId: eventData.messageId, uniqueId: eventData.uniqueId, shipId: eventData.shipId },
                    { modifiedtimeStamp: new Date().getTime() });
                if (updateResult.affected === 0) {
                    this.logger.error(`Error in updating cancelled modified timestamp for the client ${eventData.clientId} 
                    with message ${eventData.messageId}`, `Unique id ${eventData.uniqueId} for the ship ${eventData.shipId}`);
                }
            })();

            taskCompleted(undefined, 'success');
        }
    }
}