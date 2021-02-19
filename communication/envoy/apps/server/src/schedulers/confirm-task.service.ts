import { Injectable } from '@nestjs/common';
import { Cron, CronExpression } from '@nestjs/schedule';
import { CommonDataStore, CommonLoggerService, CommonConfirmTaskService, InBoundEventDataEntity } from '@envoy/common';
import { CommonDBService, InboundEventData } from '@envoy/common';
import * as fastq from 'fastq';
import * as Redlock from 'redlock';
import { RedisUtilService } from '../utils/redis-util.service';
import { ServerUtilService } from '../utils/server-util.service';
import { UpdateResult } from 'typeorm';

/**
 * Class to confirm file transport
 */
@Injectable()
export class ConfirmTaskService {

  private confirmTaskQueue: fastq.queue;
  private queueCount = 0;

  private CONFIRM_TASK_DATA_LOCK = "confirm:task:data:lock";
  private RECHECK_CONFIRM_TASK_DATA_LOCK = "recheck:confirm:task:data:lock";
  private CONFIRM_DATA_CHANNEL = "confirm_data_channel";

  constructor(
    private readonly commonDbStore: CommonDataStore,
    private readonly commonDbService: CommonDBService<InBoundEventDataEntity, InboundEventData>,
    private serverUtilService: ServerUtilService,
    private commonConfirmTaskService: CommonConfirmTaskService, private redisUtilService: RedisUtilService,
    private logger: CommonLoggerService
  ) {
    this.logger.setContext('ConfirmTaskService');
    this.confirmTaskQueue = fastq(commonConfirmTaskService, commonConfirmTaskService.confirmDataTransfer, 1);
    //Subscribing to the finish channel
    redisUtilService.subscribeChannel(this.CONFIRM_DATA_CHANNEL);
    //Receiving messages 
    redisUtilService.getRedisMessages().on("message", (channel, message) => {
      const event: InboundEventData = JSON.parse(message) as InboundEventData;
      if (channel !== this.CONFIRM_DATA_CHANNEL) {
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
      event.confirmRead = 'y';
      this.confirmTaskQueue.push(event, (err, re) => {
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
   * Cron job to check for new events to confirm
   */
  @Cron(CronExpression.EVERY_10_SECONDS)
  public async confirmTaskCron(): Promise<string> {
    this.logger.debug('FILE CONFIRM is under processing ' + this.queueCount);
    // Checking if the queue lenght is empty
    if (this.queueCount !== 0) {
      return Promise.resolve('success');
    }
    let redisLock: Redlock.Lock;
    try {
      redisLock = await this.redisUtilService.lockObject(this.CONFIRM_TASK_DATA_LOCK);
      const params = { process: 'finish' };
      const result: InboundEventData[] = await this.commonDbService.find(this.commonDbStore.getInboundEventStore(), 'inbound', "inbound.process = :process AND inbound.confirmRead IS NULL", params);
      if (result.length === 0) {
        this.logger.debug('No data found');
        return Promise.resolve('success');
      }
      const eventsGroupedByClientAndShip: InboundEventData[] = this.serverUtilService.groupByClientAndShipSortBySeq(result) as InboundEventData[];
      eventsGroupedByClientAndShip.forEach(async (event: InboundEventData) => {
        
        //Publishing to redis channel 
        this.redisUtilService.publishToChannel(this.CONFIRM_DATA_CHANNEL, event);

        //Updating read status for the confirm task
        const updateResult: UpdateResult = await this.commonDbService.updateData(this.commonDbStore.getInboundEventStore(),
          { clientId: event.clientId, messageId: event.messageId, uniqueId: event.uniqueId, shipId: event.shipId }, { confirmRead: 'y' });
        if (updateResult.affected === 0) {
          this.logger.error(`Error in updating confirm read status for the client ${event.clientId} 
                    with message ${event.messageId}`, `Unique id ${event.uniqueId} from the ship ${event.shipId}`);
        }
      });
    } catch (err) {
      if (err instanceof Redlock.LockError) {
        this.logger.warn('Confirm tasks is locked');
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
  * Cron job to recheck the confirm task, if any file stuck after 30 seconds restart the process
  */
  @Cron(CronExpression.EVERY_30_SECONDS)
  public async recheckConfirmTaskCron(): Promise<string> {
    let redisLock: Redlock.Lock;
    try {
      redisLock = await this.redisUtilService.lockObject(this.RECHECK_CONFIRM_TASK_DATA_LOCK);
      const params = { process: 'finish', confirmRead: 'y' };
      const result: InboundEventData[] = await this.commonDbService.find(
        this.commonDbStore.getInboundEventStore(), 'inbound', "inbound.process = :process AND inbound.confirmRead = :confirmRead", params);
      if (result.length === 0) {
        this.logger.debug('No data found');
        return Promise.resolve('success');
      }
      result.forEach(async (event: InboundEventData) => {
        const diff = new Date().getTime() - event.confirmReadTimeStamp;
        //if the diff is more than 30 seconds reset the readstatus to null
        if (diff > 30) {
          //Updating read status for the confirm read task
          const updateResult: UpdateResult = await this.commonDbService.updateData(this.commonDbStore.getInboundEventStore(),
            { clientId: event.clientId, messageId: event.messageId, uniqueId: event.uniqueId, shipId: event.shipId }, { confirmRead: null });
          if (updateResult.affected === 0) {
            this.logger.error(`Error in updating confirm read status for the client ${event.clientId} with message ${event.messageId}`, `Unique id ${event.uniqueId} from the ship ${event.shipId}`);
          }
        }
      });
    } catch (err) {
      if (err instanceof Redlock.LockError) {
        this.logger.warn('Recheck Confirm tasks is locked');
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
