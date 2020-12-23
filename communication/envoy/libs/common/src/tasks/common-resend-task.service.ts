import { Injectable } from '@nestjs/common';
import { OutboundEventData, ResendEventDataTransfer } from '../models/common-models';
import { CommonLoggerService } from '../services/common-logger.service';
import * as _ from 'underscore';
import { TaskCompleted } from '../utils/common-app.constants';
import { UpdateResult } from 'typeorm';
import { CommonDataStore } from '../store/common-db-store';
import { CommonDBService } from '../services/common-db.service';
import { OutBoundEventDataEntity } from '../entities/common-outbound-event.entity';

/**
 * Class to perform common resend task functions
 */
@Injectable()
export class CommonResendTaskService {

    private ioClient: SocketIOClient.Socket | SocketIO.Socket;

    constructor(private logger: CommonLoggerService, private readonly commonDbStore: CommonDataStore,
        private readonly commonDbService: CommonDBService<OutBoundEventDataEntity, OutboundEventData>,
        ) {
        this.logger.setContext('CommonResendTaskService');
    }

    /**
     * Method to resend any failed files
     *
     * @param eventData
     */
    public resendFiles(eventData: OutboundEventData, taskCompleted: TaskCompleted): void {
        try {
            //Copying ioclient object 
            this.ioClient = eventData.ioClient;
            //Removing ioclient object from eventdata before processing
            eventData = _.omit(eventData, 'ioClient');
            const resendPacket: ResendEventDataTransfer = {
                clientId: eventData.clientId,
                messageId: eventData.messageId,
                uniqueId: eventData.uniqueId,
                missingPackets: eventData.missingPackets
            }
            //Checking if the client socket is available
            if (this.ioClient && this.ioClient.connected) {
                // No need for ack as the receiving side will handle
                this.ioClient.emit('resend', resendPacket);
            } else {
                this.logger.warn('Error socket connection');
            }
        } catch (err) {
            this.logger.error(`Error in resending request ${err.message}`, err.stack);
        } finally {
            (async () => {
                //Updating the resend read timestamp to show the file is still under processing
                const updateResult: UpdateResult = await this.commonDbService.updateData(this.commonDbStore.getOutboundEventStore(),
                    { clientId: eventData.clientId, messageId: eventData.messageId, uniqueId: eventData.uniqueId, shipId: eventData.shipId },
                    { resendReadTimeStamp: new Date().getTime() });
                if (updateResult.affected === 0) {
                    this.logger.error(`Error in updating resend read timestamp for the client ${eventData.clientId} 
                    with message ${eventData.messageId}`, `Unique id ${eventData.uniqueId} for the ship ${eventData.shipId}`);
                }
            })();
            taskCompleted(undefined, 'success');
        }
    }
}