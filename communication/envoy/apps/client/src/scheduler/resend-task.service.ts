import { Injectable } from '@nestjs/common';
import { Cron, CronExpression } from '@nestjs/schedule';
import { CommonDataStore, OutboundEventData, OutBoundEventDataEntity } from '@envoy/common';
import { CommonDBService, CommonResendTaskService } from '@envoy/common';
import * as fastq from 'fastq';
import * as _ from 'underscore';
import { CommonLoggerService } from '@envoy/common';
import { SocketClientService } from '../socket/socket-client.service';

/**
 * Class to resend the failed file
 */
@Injectable()
export class ResendTaskService {

    private resendTaskQueue: fastq.queue;
    private isLocked = false;

    constructor(private readonly commonDbStore: CommonDataStore, private readonly commonDbService: CommonDBService<OutBoundEventDataEntity, OutboundEventData>,
        private commonResendTaskService: CommonResendTaskService,
        private readonly socketClientService: SocketClientService, private logger: CommonLoggerService) {
        this.logger.setContext('ResendTaskService');
        this.resendTaskQueue = fastq(commonResendTaskService, commonResendTaskService.resendFiles, 1);
    }

    /**
     * Cron job to check for new events to verify
     */
    @Cron(CronExpression.EVERY_30_SECONDS)
    public async resendTaskCron(): Promise<string> {

        this.logger.debug('FILE RESEND is under processing ' + this.isLocked);
        // Checking if the queue length is empty
        if (this.isLocked) {
            return Promise.resolve('success');
        }
        try {
            const params =  { process: 'received' };
            const orCondition = "(outbound.processStatus = :hashFailedProcessStatus OR outbound.processStatus = :packetMissingProcessStatus)";
            const orParams = { hashFailedProcessStatus: 'RECEIVED_WITH_HASH_FAILED', packetMissingProcessStatus: 'RECEIVED_WITH_PACKET_MISSING' }; 
            const result: OutboundEventData[] = await this.commonDbService.findAndSortWithLimit(
                this.commonDbStore.getOutboundEventStore(), 'outbound', "outbound.process = :process", params, "outbound.sequence", 1, orCondition, orParams);
            if (result.length === 0) {
                this.logger.debug('No data found');
                return Promise.resolve('success');
            }

            result.forEach((event: OutboundEventData) => {
                // Acquiring lock
                this.isLocked = true;
                // Setting socket.io object to the event data
                event.ioClient = this.socketClientService.getSocket();

                this.resendTaskQueue.push(event, (err, re) => {
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