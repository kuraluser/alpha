import { Injectable } from '@nestjs/common';
import { CommonDataStore } from '../store/common-db-store';
import { InboundEventData, InboundEventDataTransfer } from '../models/common-models';
import { CommonDBService } from '../services/common-db.service';
import { CommonLoggerService } from '../services/common-logger.service';
import * as fastq from 'fastq';
import * as _ from 'underscore';
import { TaskCompleted } from '../utils/common-app.constants';
import { InBoundEventDataEntity } from '../entities/common-inbound-event.entity';
import { UpdateResult } from 'typeorm';

/**
 * Class to perform common finish task functions
 */
@Injectable()
export class CommonFinishTaskService {

    private timerSleepInMs = 20000;

    private ioClient: SocketIOClient.Socket | SocketIO.Socket;

    constructor(
        private readonly commonDbStore: CommonDataStore,
        private readonly commonDbService: CommonDBService<InBoundEventDataEntity, InboundEventData>,
        private logger: CommonLoggerService
    ) {
        this.logger.setContext('CommonFinishTaskService');
    }

    /**
    * Method to recursively finish data sending using websockets Socket.IO library
    *
    * @param eventData
    */
    public finishDataTransfer(eventData: InboundEventData, taskCompleted: TaskCompleted): void {

        try {
            //Copying ioclient object 
            this.ioClient = eventData.ioClient;
            //Removing ioclient object from eventdata before processing
            eventData = _.omit(eventData, 'ioClient');

            const dataToSend: InboundEventDataTransfer = {
                clientId: eventData.clientId,
                fileName: eventData.fileName,
                messageId: eventData.messageId,
                buf: "finish"
            };
            //Checking if the client socket is available
            if (this.ioClient && this.ioClient.connected) {
                //Calling socket.emit to send the data packets. Ack will be handled async
                this.ioClient.emit('finish', dataToSend);
            } else {
                this.logger.warn('Error socket connection');
            }
        } catch (err) {

        } finally {
            (async () => {
                //Updating the finish read timestamp to show the file is still under processing
                const updateResult: UpdateResult = await this.commonDbService.updateData(this.commonDbStore.getInboundEventStore(),
                    { clientId: eventData.clientId, messageId: eventData.messageId, uniqueId: eventData.uniqueId, shipId: eventData.shipId },
                    { finishReadTimeStamp: new Date().getTime() });
                if (updateResult.affected === 0) {
                    this.logger.error(`Error in updating finish read timestamp for the client ${eventData.clientId} with message ${eventData.messageId}`, `Unique id ${eventData.uniqueId} for the ship ${eventData.shipId}`);
                }
            })();
            taskCompleted(undefined, 'success');
        }
    }
}