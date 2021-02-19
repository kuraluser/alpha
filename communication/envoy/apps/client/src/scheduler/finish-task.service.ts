import { Injectable } from '@nestjs/common';
import { Cron, CronExpression } from '@nestjs/schedule';
import { SocketClientService } from '../socket/socket-client.service';
import { CommonDataStore, CommonLoggerService, CommonFinishTaskService, InBoundEventDataEntity } from '@envoy/common';
import { CommonDBService, InboundEventData } from '@envoy/common';
import * as fastq from 'fastq';
/**
 * Class to finish file transport
 */
@Injectable()
export class FinishTaskService {

    private finishTaskQueue: fastq.queue;
    private isLocked = false;

    constructor(
        private readonly commonDbStore: CommonDataStore,
        private readonly commonFinishTaskService: CommonFinishTaskService,
        private readonly commonDbService: CommonDBService<InBoundEventDataEntity, InboundEventData>,
        private readonly socketClientService: SocketClientService,
        private logger: CommonLoggerService
    ) {
        this.logger.setContext('FinishTaskService');
        this.finishTaskQueue = fastq(commonFinishTaskService, commonFinishTaskService.finishDataTransfer, 1);
    }

    /**
     * Cron job to check for new events to finish
     */
    @Cron(CronExpression.EVERY_10_SECONDS)
    public async finishTaskCron(): Promise<string> {
        this.logger.debug('FILE FINISH is under processing ' + this.isLocked);
        // Checking if the queue length is empty
        if (this.isLocked) {
            return Promise.resolve('success');
        }
        try { 
            const params =  { process: 'send' };
            const result: InboundEventData[] = await this.commonDbService.findAndSortWithLimit(
                this.commonDbStore.getInboundEventStore(), 'inbound', "inbound.process = :process", params, "inbound.sequence", 1);
            if (result.length === 0) {
                this.logger.debug('No data found');
                return Promise.resolve('success');
            }
            result.forEach((event: InboundEventData) => {
                // Acquiring lock
                this.isLocked = true;
                // Setting socket.io object to the event data
                event.ioClient = this.socketClientService.getSocket();
                this.finishTaskQueue.push(event, (err, re) => {
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
