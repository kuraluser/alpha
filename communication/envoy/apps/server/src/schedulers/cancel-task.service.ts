import { Injectable } from '@nestjs/common';
import { Cron, CronExpression } from '@nestjs/schedule';
import { CommonDataStore, CommonLoggerService, InBoundEventDataEntity, CommonCancelTaskService } from '@envoy/common';
import { CommonDBService, InboundEventData } from '@envoy/common';
import * as fastq from 'fastq';
import * as Redlock from 'redlock';
import { RedisUtilService } from '../utils/redis-util.service';
import { ServerUtilService } from '../utils/server-util.service';
import { UpdateResult } from 'typeorm';

/**
 * Class to cancel file transport
 */
@Injectable()
export class CancelTaskService {

  private cancelTaskQueue: fastq.queue;
  private queueCount = 0;

  private CANCEL_TASK_DATA_LOCK = "cancel:task:data:lock";
  private RECHECK_CANCEL_TASK_DATA_LOCK = "recheck:cancel:task:data:lock";
  private CANCEL_DATA_CHANNEL = "cancel_data_channel";

  constructor(
    private readonly commonDbStore: CommonDataStore,
    private readonly commonDbService: CommonDBService<InBoundEventDataEntity, InboundEventData>,
    private serverUtilService: ServerUtilService,
    private commonCancelTaskService: CommonCancelTaskService, private redisUtilService: RedisUtilService,
    private logger: CommonLoggerService
  ) {
    this.logger.setContext('CancelTaskService');
    this.cancelTaskQueue = fastq(commonCancelTaskService, commonCancelTaskService.cancelDataTransfer, 1);
    //Subscribing to the cancel channel
    redisUtilService.subscribeChannel(this.CANCEL_DATA_CHANNEL);
    //Receiving messages 
    redisUtilService.getRedisMessages().on("message", (channel, message) => {
      const event: InboundEventData = JSON.parse(message) as InboundEventData;
      if (channel !== this.CANCEL_DATA_CHANNEL) {
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
      event.cancelRead = 'y';
      this.cancelTaskQueue.push(event, (err, re) => {
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
   * Cron job to check for new events to cancel
   */
  @Cron(CronExpression.EVERY_10_SECONDS)
  public async cancelTaskCron(): Promise<string> {
    this.logger.debug('FILE CANCEL is under processing ' + this.queueCount);
    // Checking if the queue length is empty
    if (this.queueCount !== 0) {
      return Promise.resolve('success');
    }
    let redisLock: Redlock.Lock;
    try {
      redisLock = await this.redisUtilService.lockObject(this.CANCEL_TASK_DATA_LOCK);
      const params = { process: 'init-cancel' };
      const result: InboundEventData[] = await this.commonDbService.find(this.commonDbStore.getInboundEventStore(), 'inbound', "inbound.process = :process AND inbound.cancelRead IS NULL", params);
      if (result.length === 0) {
        this.logger.debug('No data found');
        return Promise.resolve('success');
      }
      const eventsGroupedByClientAndShip: InboundEventData[] = this.serverUtilService.groupByClientAndShipSortBySeq(result) as InboundEventData[];
      eventsGroupedByClientAndShip.forEach(async (event: InboundEventData) => {
        
        //Publishing to redis channel 
        this.redisUtilService.publishToChannel(this.CANCEL_DATA_CHANNEL, event);

        //Updating read status for the cancel task
        const updateResult: UpdateResult = await this.commonDbService.updateData(this.commonDbStore.getInboundEventStore(),
          { clientId: event.clientId, messageId: event.messageId, uniqueId: event.uniqueId, shipId: event.shipId }, { cancelRead: 'y' });
        if (updateResult.affected === 0) {
          this.logger.error(`Error in updating cancel read status for the client ${event.clientId} 
                    with message ${event.messageId}`, `Unique id ${event.uniqueId} from the ship ${event.shipId}`);
        }
      });
    } catch (err) {
      if (err instanceof Redlock.LockError) {
        this.logger.warn('Cancel tasks is locked');
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
  * Cron job to recheck the cancel task, if any file stuck after 30 seconds restart the process
  */
  @Cron(CronExpression.EVERY_30_SECONDS)
  public async recheckCancelTaskCron(): Promise<string> {
    let redisLock: Redlock.Lock;
    try {
      redisLock = await this.redisUtilService.lockObject(this.RECHECK_CANCEL_TASK_DATA_LOCK);
      const params = { process: 'init-cancel', cancelRead: 'y' };
      const result: InboundEventData[] = await this.commonDbService.find(
        this.commonDbStore.getInboundEventStore(), 'inbound', "inbound.process = :process AND inbound.cancelRead = :cancelRead", params);
      if (result.length === 0) {
        this.logger.debug('No data found');
        return Promise.resolve('success');
      }
      result.forEach(async (event: InboundEventData) => {
        const diff = new Date().getTime() - event.cancelReadTimeStamp;
        //if the diff is more than 30 seconds reset the readstatus to null
        if (diff > 30) {
          //Updating read status for the cancel read task
          const updateResult: UpdateResult = await this.commonDbService.updateData(this.commonDbStore.getInboundEventStore(),
            { clientId: event.clientId, messageId: event.messageId, uniqueId: event.uniqueId, shipId: event.shipId }, { cancelRead: null });
          if (updateResult.affected === 0) {
            this.logger.error(`Error in updating cancel read status for the client ${event.clientId} with message ${event.messageId}`, `Unique id ${event.uniqueId} from the ship ${event.shipId}`);
          }
        }
      });
    } catch (err) {
      if (err instanceof Redlock.LockError) {
        this.logger.warn('Recheck Cancel tasks is locked');
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
