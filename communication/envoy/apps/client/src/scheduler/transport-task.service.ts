import { Injectable } from '@nestjs/common';
import { Cron, CronExpression } from '@nestjs/schedule';
import { CommonDataStore, CommonLoggerService, CommonTransportTaskService, InBoundEventDataEntity } from '@envoy/common';
import { CommonDBService, CommonUtilService, InboundEventData } from '@envoy/common';
import * as fastq from 'fastq';
import * as _ from 'underscore';
import { SocketClientService } from '../socket/socket-client.service';

/**
 * Class to start/cordinate file transport service
 */
@Injectable()
export class TransportTaskService {

    private transportTaskQueue: fastq.queue;
    private isLocked = false;

    constructor(
        private readonly commonDbStore: CommonDataStore,
        private readonly commonTransportTaskService: CommonTransportTaskService,
        private readonly commonDbService: CommonDBService<InBoundEventDataEntity, InboundEventData>,
        private readonly socketClientService: SocketClientService,
        private logger: CommonLoggerService
    ) {
        this.logger.setContext('TransportTaskService');
        this.transportTaskQueue = fastq(commonTransportTaskService, commonTransportTaskService.intiateTransport, 1);
    }

    /**
     * Cron job to check for new events to transport
     */
    @Cron(CronExpression.EVERY_10_SECONDS)
    public async transportTaskCron(): Promise<string> {
        this.logger.log('FILE TRANSPORT is under processing ' + this.isLocked);
        // Checking if the queue is locked
        if (this.isLocked) {
            return Promise.resolve('success');
        }
        try {
            const params =  { process: 'split' };
            const result: InboundEventData[] = await this.commonDbService.findAndSortWithLimit(
                this.commonDbStore.getInboundEventStore(), 'inbound', "inbound.process = :process", params, "inbound.sequence", 1);
            if (result.length === 0) {
                this.logger.log('No data found');
                return Promise.resolve('success');
            }
            result.forEach((event: InboundEventData) => {
                // Acquiring lock
                this.isLocked = true;
                // Setting socket.io object to the event data
                event.ioClient = this.socketClientService.getSocket();
                this.transportTaskQueue.push(event, (err, re) => {
                    if (err) {
                        //To-Do Quit Queue
                        this.logger.error(`Error in queue ${err.message}`, err.stack);
                    }
                    //Releasing lock after sending
                    this.isLocked = false;
                });
            });
        } catch (err) {
            this.logger.error(`Error in retriving data ${err.message}`, err.stack);
        }
        return Promise.resolve('success');
    }
}
