import { Injectable } from '@nestjs/common';
import { Cron, CronExpression } from '@nestjs/schedule';
import { CommonDataStore, OutboundEventData, CommonLoggerService, CommonVerifyTaskService, ProcessStatus, OutBoundEventDataEntity } from '@envoy/common';
import { CommonDBService, dataEmitter } from '@envoy/common';
import * as fastq from 'fastq';

/**
 * Class to verify recieved file
 */
@Injectable()
export class VerifyTaskService {

    private verifyTaskQueue: fastq.queue;
    private isLocked = false;

    constructor(private readonly commonDbStore: CommonDataStore,
        private readonly commonDbService: CommonDBService<OutBoundEventDataEntity, OutboundEventData>,
        private commonVerifyTaskService: CommonVerifyTaskService, private logger: CommonLoggerService) {
        this.logger.setContext('VerifyTaskService');
        this.verifyTaskQueue = fastq(commonVerifyTaskService, commonVerifyTaskService.verifyReceivedFiles, 5);
    }

    /**
     * Cron job to check for new events to verify
     */
    @Cron(CronExpression.EVERY_10_SECONDS)
    public async verifyTaskCron(): Promise<string> {

        this.logger.debug('FILE VERIFY is under processing ' + this.isLocked);
        // Checking if the queue is locked
        if (this.isLocked) {
            return Promise.resolve('success');
        }
        try {
            const params =  { process: 'received' };
            const result: OutboundEventData[] = await this.commonDbService.findAndSortWithLimit(
                this.commonDbStore.getOutboundEventStore(), 'outbound', "outbound.process = :process", params, "outbound.sequence", 1);
            if (result.length === 0) {
                this.logger.debug('No data found');
                return Promise.resolve('success');
            }
            result.forEach((event: OutboundEventData) => {
                // Acquiring lock
                this.isLocked = true;
                this.verifyTaskQueue.push(event, (err, re) => {
                    if (err) {
                        //To-Do Quit Queue
                        this.logger.error(`Error in queue ${err.message}`, err.stack);
                    }
                    //Releasing lock after sending
                    this.isLocked = false;
                    //Remove the below line after POC TESTING
                    // if (ProcessStatus[event.processStatus] === ProcessStatus['RECEIVED_WITH_HASH_VERIFIED']) {
                    //     dataEmitter.emit('data', event.uniqueId);
                    // }

                });
            });
        } catch (err) {
            this.logger.error(`Error in retriving data ${err.message}`, err.stack);
        }
        return Promise.resolve('success');
    }


}