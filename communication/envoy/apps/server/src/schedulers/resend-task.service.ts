import { Injectable } from '@nestjs/common';
import { Cron, CronExpression } from '@nestjs/schedule';
import { CommonDataStore, OutboundEventData, OutBoundEventDataEntity } from '@envoy/common';
import { CommonDBService, CommonResendTaskService } from '@envoy/common';
import * as fastq from 'fastq';
import * as Redlock from 'redlock';
import * as _ from 'underscore';
import { RedisUtilService } from '../utils/redis-util.service';
import { ServerUtilService } from '../utils/server-util.service';
import { CommonLoggerService } from '@envoy/common';
import { UpdateResult } from 'typeorm';

/**
 * Class to resend the failed file
 */
@Injectable()
export class ResendTaskService {

    private resendTaskQueue: fastq.queue;
    private queueCount = 0;

    private RESEND_TASK_DATA_LOCK = "resend:task:data:lock";
    private RECHECK_RESEND_TASK_DATA_LOCK = "recheck:resend:task:data:lock";
    private RESEND_DATA_CHANNEL = "resend_data_channel";

    constructor(private readonly commonDbStore: CommonDataStore, private readonly commonDbService: CommonDBService<OutBoundEventDataEntity, OutboundEventData>,
        private commonResendTaskService: CommonResendTaskService, private redisUtilService: RedisUtilService,
        private serverUtilService: ServerUtilService, private logger: CommonLoggerService) {
        this.logger.setContext('ResendTaskService');
        this.resendTaskQueue = fastq(commonResendTaskService, commonResendTaskService.resendFiles, 5);

        //Subscribing to the resend channel
        redisUtilService.subscribeChannel(this.RESEND_DATA_CHANNEL);
        //Receiving messages 
        redisUtilService.getRedisMessages().on("message", (channel, message) => {
            const event: OutboundEventData = JSON.parse(message) as OutboundEventData;
            if (channel !== this.RESEND_DATA_CHANNEL) {
                return;
            }
            //Setting client object
            event.ioClient = this.serverUtilService.getClientSocket(`/id-${event.shipId}`);
            //If client object not found return and retry
            if (!event.ioClient || !event.ioClient.connected) {
                this.logger.warn('Ship IO client not connected or not found for the ship id ' + event.shipId);
                this.serverUtilService.removeClientSocket(`/id-${event.shipId}`);
                return;
            }
            // Acquiring lock
            this.queueCount++;
            //updating read status 
            event.resendRead = 'y';
            this.resendTaskQueue.push(event, (err, re) => {
                if (err) {
                    //To-Do Quit Queue
                    this.logger.error(`Error in queue ${err.message}`, err.stack);
                }
                //Releasing lock
                this.queueCount--;
            });
        });
    }

    /**
     * Cron job to check for new events to verify
     */
    @Cron(CronExpression.EVERY_30_SECONDS)
    public async resendTaskCron(): Promise<string> {

        this.logger.debug('FILE RESEND is under processing ' + this.queueCount);
        // Checking if the queue length is empty
        if (this.queueCount !== 0) {
            return Promise.resolve('success');
        }
        let redisLock: Redlock.Lock;
        try {
            redisLock = await this.redisUtilService.lockObject(this.RESEND_TASK_DATA_LOCK);
            const params = { process: 'received' };
            const orCondition = "(outbound.processStatus = :hashFailedProcessStatus OR outbound.processStatus = :packetMissingProcessStatus)";
            const orParams = { hashFailedProcessStatus: 'RECEIVED_WITH_HASH_FAILED', packetMissingProcessStatus: 'RECEIVED_WITH_PACKET_MISSING' };
            const result: OutboundEventData[] = await this.commonDbService.find(
                this.commonDbStore.getOutboundEventStore(), 'outbound', "outbound.process = :process AND outbound.resendRead IS NULL", params, orCondition, orParams);
            if (result.length === 0) {
                this.logger.debug('No data found');
                return Promise.resolve('success');
            }
            const eventsGroupedByClientAndShip: OutboundEventData[] = this.serverUtilService.groupByClientAndShipSortBySeq(result) as OutboundEventData[];

            eventsGroupedByClientAndShip.forEach(async (event: OutboundEventData) => {

                //Publishing to redis channel 
                this.redisUtilService.publishToChannel(this.RESEND_DATA_CHANNEL, event);

                //Updating read status for the resend task
                const updateResult: UpdateResult = await this.commonDbService.updateData(this.commonDbStore.getOutboundEventStore(),
                    { clientId: event.clientId, messageId: event.messageId, uniqueId: event.uniqueId, shipId: event.shipId }, { resendRead: 'y' });
                if (updateResult.affected === 0) {
                    this.logger.error(`Error in updating resend read status for the client ${event.clientId} with message ${event.messageId}`, `Unique id ${event.uniqueId} for the ship ${event.shipId}`);
                }
            });
        } catch (err) {
            if (err instanceof Redlock.LockError) {
                this.logger.warn('Resend tasks is locked');
            } else {
                this.logger.error(`Error in retriving data ${err.message}`, err.stack);
            }
        } finally {
            //Releasing redis lock
            if (redisLock) {
                this.redisUtilService.releaseLock(redisLock);
            }
        }
        return Promise.resolve('success');
    }

    /**
    * Cron job to recheck the resend task, if any file stuck after 30 seconds restart the process
    */
    @Cron(CronExpression.EVERY_30_SECONDS)
    public async recheckResendTaskCron(): Promise<string> {
        let redisLock: Redlock.Lock;
        try {
            redisLock = await this.redisUtilService.lockObject(this.RECHECK_RESEND_TASK_DATA_LOCK);
            const params = { process: 'received', resendRead: 'y' };
            const orCondition = "(outbound.processStatus = :hashFailedProcessStatus OR outbound.processStatus = :packetMissingProcessStatus)";
            const orParams = { hashFailedProcessStatus: 'RECEIVED_WITH_HASH_FAILED', packetMissingProcessStatus: 'RECEIVED_WITH_PACKET_MISSING' };
            const result: OutboundEventData[] = await this.commonDbService.find(
                this.commonDbStore.getOutboundEventStore(), 'outbound', "outbound.process = :process AND outbound.resendRead = :resendRead", params, orCondition, orParams);
            if (result.length === 0) {
                this.logger.debug('No data found');
                return Promise.resolve('success');
            }
            result.forEach(async (event: OutboundEventData) => {

                const diff = new Date().getTime() - event.verifyReadTimeStamp;
                //if the diff is more than 30 seconds reset the readstatus to null
                if (diff > 30) {
                    //Updating read status for the verify read task
                    const updateResult: UpdateResult = await this.commonDbService.updateData(this.commonDbStore.getOutboundEventStore(),
                        { clientId: event.clientId, messageId: event.messageId, uniqueId: event.uniqueId, shipId: event.shipId }, { resendRead: null });
                    if (updateResult.affected === 0) {
                        this.logger.error(`Error in updating verify read status for the client ${event.clientId} with message ${event.messageId}`, `Unique id ${event.uniqueId} from the ship ${event.shipId}`);
                    }
                }
            });
        } catch (err) {
            if (err instanceof Redlock.LockError) {
                this.logger.warn('Recheck Resend task is locked');
            } else {
                this.logger.error(`Error in retriving data ${err.message}`, err.stack);
            }
        } finally {
            //Releasing redis lock
            if (redisLock) {
                this.redisUtilService.releaseLock(redisLock);
            }
        }
        return Promise.resolve('success');
    }
}