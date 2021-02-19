import { Injectable } from '@nestjs/common';
import { Cron, CronExpression } from '@nestjs/schedule';
import { CommonDataStore, OutboundEventData, CommonLoggerService, CommonUtilService, CommonVerifyTaskService, OutBoundEventDataEntity, ProcessStatus } from '@envoy/common';
import { CommonDBService } from '@envoy/common';
import * as fastq from 'fastq';
import * as Redlock from 'redlock';
import { RedisUtilService } from '../utils/redis-util.service';
import { ServerUtilService } from '../utils/server-util.service';
import { ConfigService } from '@nestjs/config';
import { UpdateResult } from 'typeorm';

/**
 * Class to verify file
 */
@Injectable()
export class VerifyTaskService {

    private verifyTaskQueue: fastq.queue;
    private queueCount = 0;

    private VERIFY_TASK_DATA_LOCK = "verify:task:data:lock";

    private RECHECK_VERIFY_TASK_DATA_LOCK = "recheck:verify:task:data:lock";

    constructor(private configService: ConfigService, private readonly commonDbStore: CommonDataStore,
        private readonly commonUtilService: CommonUtilService,
        private readonly commonDbService: CommonDBService<OutBoundEventDataEntity, OutboundEventData>,
        private commonVerifyTaskService: CommonVerifyTaskService, private redisUtilService: RedisUtilService,
        private serverUtilService: ServerUtilService, private logger: CommonLoggerService) {
        this.logger.setContext('VerifyTaskService');
        this.verifyTaskQueue = fastq(commonVerifyTaskService, commonVerifyTaskService.verifyReceivedFiles, 5);
    }

    /**
     * Cron job to check for new events to verify
     */
    @Cron(CronExpression.EVERY_10_SECONDS)
    public async verifyTaskCron(): Promise<string> {

        this.logger.debug('FILE VERIFY is under processing ' + this.queueCount);
        // Checking if the queue length is empty
        if (this.queueCount !== 0) {
            return Promise.resolve('success');
        }
        let redisLock: Redlock.Lock;
        try {
            redisLock = await this.redisUtilService.lockObject(this.VERIFY_TASK_DATA_LOCK);
            const params = { process: 'received' };
            const result: OutboundEventData[] = await this.commonDbService.find(
                this.commonDbStore.getOutboundEventStore(), 'outbound', "outbound.process = :process AND outbound.verifyRead IS NULL", params);
            if (result.length === 0) {
                this.logger.debug('No data found');
                return Promise.resolve('success');
            }
            const eventsGroupedByClientAndShip: OutboundEventData[] = this.serverUtilService.groupByClientAndShipSortBySeq(result) as OutboundEventData[];

            eventsGroupedByClientAndShip.forEach(async (event: OutboundEventData) => {
                // Acquiring lock
                this.queueCount++;
                //updating read status 
                event.verifyRead = 'y';
                this.verifyTaskQueue.push(event, (err, re) => {
                    if (err) {
                        //To-Do Quit Queue
                        this.logger.error(`Error in queue ${err.message}`, err.stack);
                    }
                    //Releasing lock
                    this.queueCount--;
                });

                //Updating verify status for the resend task
                const updateResult: UpdateResult = await this.commonDbService.updateData(this.commonDbStore.getOutboundEventStore(),
                    { clientId: event.clientId, messageId: event.messageId, uniqueId: event.uniqueId, shipId: event.shipId }, { verifyRead: 'y' });
                if (updateResult.affected === 0) {
                    this.logger.error(`Error in updating verify read status for the client ${event.clientId} with message ${event.messageId}`, `Unique id ${event.uniqueId} for the ship ${event.shipId}`);
                }
            });

        } catch (err) {
            if (err instanceof Redlock.LockError) {
                this.logger.warn('Verify tasks is locked');
            } else {
                this.logger.error(`Error in retriving data ${err.message}`, err.stack);
            }
        } finally {
            //Releasing redis lock
            if (redisLock) {
                this.redisUtilService.releaseLock(redisLock);
            }
            return Promise.resolve('success');
        }

    }

    /**
     * Cron job to recheck the verify task, if any file stuck after 30 seconds restart the process
     */
    @Cron(CronExpression.EVERY_30_SECONDS)
    public async recheckVerifyTaskCron(): Promise<string> {
        let redisLock: Redlock.Lock;
        try {
            redisLock = await this.redisUtilService.lockObject(this.RECHECK_VERIFY_TASK_DATA_LOCK);
            const params = { process: 'received', verifyRead: 'y' };
            const result: OutboundEventData[] = await this.commonDbService.find(
                this.commonDbStore.getOutboundEventStore(), 'outbound', "outbound.process = :process AND outbound.verifyRead = :verifyRead", params);
            if (result.length === 0) {
                this.logger.debug('No data found');
                return Promise.resolve('success');
            }
            result.forEach(async (event: OutboundEventData) => {
                //Checking if the file needs resending if so return without changing the read status
                if (ProcessStatus[event.processStatus] === ProcessStatus['RECEIVED_WITH_HASH_FAILED'] || ProcessStatus[event.processStatus] === ProcessStatus['RECEIVED_WITH_PACKET_MISSING']) {
                    return;
                }
                const diff = new Date().getTime() - event.verifyReadTimeStamp;
                //if the diff is more than 30 seconds reset the readstatus to null
                if (diff > 30) {
                    //Updating read status for the verify read task
                    const updateResult: UpdateResult = await this.commonDbService.updateData(this.commonDbStore.getOutboundEventStore(),
                        { clientId: event.clientId, messageId: event.messageId, uniqueId: event.uniqueId, shipId: event.shipId }, { verifyRead: null });
                    if (updateResult.affected === 0) {
                        this.logger.error(`Error in updating verify read status for the client ${event.clientId} with message ${event.messageId}`, `Unique id ${event.uniqueId} from the ship ${event.shipId}`);
                    }
                }
            });
        } catch (err) {
            if (err instanceof Redlock.LockError) {
                this.logger.warn('Recheck Verify tasks is locked');
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