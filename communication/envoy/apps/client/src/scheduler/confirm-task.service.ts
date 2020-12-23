import { Injectable } from '@nestjs/common';
import { Cron, CronExpression } from '@nestjs/schedule';
import { CommonDataStore, CommonLoggerService, CommonConfirmTaskService, InBoundEventDataEntity } from '@envoy/common';
import { CommonDBService, InboundEventData } from '@envoy/common';
import * as fastq from 'fastq';
import { SocketClientService } from '../socket/socket-client.service';

/**
 * Class to confirm file transport
 */
@Injectable()
export class ConfirmTaskService {

    private confirmTaskQueue: fastq.queue;
    private isLocked = false;

    constructor(
        private readonly commonDbStore: CommonDataStore,
        private readonly commonDbService: CommonDBService<InBoundEventDataEntity, InboundEventData>,
        private commonConfirmTaskService: CommonConfirmTaskService,
        private readonly socketClientService: SocketClientService,
        private logger: CommonLoggerService
    ) {
        this.logger.setContext('ConfirmTaskService');
        this.confirmTaskQueue = fastq(commonConfirmTaskService, commonConfirmTaskService.confirmDataTransfer, 1);
    }

    /**
     * Cron job to check for new events to finish
     */
    @Cron(CronExpression.EVERY_10_SECONDS)
    public async confirmTaskCron(): Promise<string> {
        this.logger.debug('FILE CONFIRM is under processing ' + this.isLocked);
        // Checking if the queue lenght is empty
        if (this.isLocked) {
            return Promise.resolve('success');
        }
        try {
            const params =  { process: 'finish' };
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
                this.confirmTaskQueue.push(event, (err, re) => {
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
