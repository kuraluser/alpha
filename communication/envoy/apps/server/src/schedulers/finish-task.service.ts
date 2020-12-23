import { Injectable } from '@nestjs/common';
import { Cron, CronExpression } from '@nestjs/schedule';
import { CommonDBService, CommonDataStore, InboundEventData, CommonLoggerService, CommonFinishTaskService, InBoundEventDataEntity } from '@envoy/common';
import * as fastq from 'fastq';
import * as Redlock from 'redlock';
import { RedisUtilService } from '../utils/redis-util.service';
import { ServerUtilService } from '../utils/server-util.service';
import { UpdateResult } from 'typeorm';

/**
 * Class to start/cordinate file split service
 */
@Injectable()
export class FinishTaskService {

  private finishTaskQueue: fastq.queue;
  private queueCount = 0;

  private FINISH_TASK_DATA_LOCK = "finish:task:data:lock";
  private RECHECK_FINISH_TASK_DATA_LOCK = "recheck:finish:task:data:lock";
  private FINISH_DATA_CHANNEL = "finish_data_channel";

  constructor(private readonly commonDbStore: CommonDataStore,
    private readonly commonFinishTaskService: CommonFinishTaskService,
    private readonly commonDbService: CommonDBService<InBoundEventDataEntity, InboundEventData>,
    private serverUtilService: ServerUtilService, private redisUtilService: RedisUtilService,
    private logger: CommonLoggerService) {
    this.logger.setContext('FinishTaskService');
    this.finishTaskQueue = fastq(commonFinishTaskService, commonFinishTaskService.finishDataTransfer, 1);
    //Subscribing to the finish channel
    redisUtilService.subscribeChannel(this.FINISH_DATA_CHANNEL);
    //Receiving messages 
    redisUtilService.getRedisMessages().on("message", (channel, message) => {
      const event: InboundEventData = JSON.parse(message) as InboundEventData;
      if (channel !== this.FINISH_DATA_CHANNEL) {
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
      event.finishRead = 'y';

      this.finishTaskQueue.push(event, (err, re) => {
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
   * Cron job to check for new flies to finish its transport
   */
  @Cron(CronExpression.EVERY_10_SECONDS)
  public async finishTaskCron(): Promise<string> {
    this.logger.debug('FILE FINISH is under processing ' + this.queueCount);
    // Checking if the queue lenght is empty
    if (this.queueCount !== 0) {
      return Promise.resolve('success');
    }
    let redisLock: Redlock.Lock;
    try {
      redisLock = await this.redisUtilService.lockObject(this.FINISH_TASK_DATA_LOCK);
      const params = { process: 'send' };
      const result: InboundEventData[] = await this.commonDbService.find(
        this.commonDbStore.getInboundEventStore(), 'inbound', "inbound.process = :process AND inbound.finishRead IS NULL", params);
      if (result.length === 0) {
        this.logger.debug('No data found');
        return Promise.resolve('success');
      }
      const eventsGroupedByClientAndShip: InboundEventData[] = this.serverUtilService.groupByClientAndShipSortBySeq(result) as InboundEventData[];

      eventsGroupedByClientAndShip.forEach(async (event: InboundEventData) => {
        //Publishing to redis channel 
        this.redisUtilService.publishToChannel(this.FINISH_DATA_CHANNEL, event);

        //Updating read status for the finish task
        const updateResult: UpdateResult = await this.commonDbService.updateData(this.commonDbStore.getInboundEventStore(),
          { clientId: event.clientId, messageId: event.messageId, uniqueId: event.uniqueId, shipId: event.shipId }, { finishRead: 'y' });
        if (updateResult.affected === 0) {
          this.logger.error(`Error in updating finish read status for the client ${event.clientId} 
           with message ${event.messageId}`, `Unique id ${event.uniqueId} from the ship ${event.shipId}`);
        }
      });
    } catch (err) {
      if (err instanceof Redlock.LockError) {
        this.logger.warn('Finish tasks is locked');
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
  * Cron job to recheck the finish task, if any file stuck after 30 seconds restart the process
  */
  @Cron(CronExpression.EVERY_30_SECONDS)
  public async recheckFinishTaskCron(): Promise<string> {
    let redisLock: Redlock.Lock;
    try {
      redisLock = await this.redisUtilService.lockObject(this.RECHECK_FINISH_TASK_DATA_LOCK);
      const params = { process: 'send', finishRead: 'y' };
      const result: InboundEventData[] = await this.commonDbService.find(
        this.commonDbStore.getInboundEventStore(), 'inbound', "inbound.process = :process AND inbound.finishRead = :finishRead", params);
      if (result.length === 0) {
        this.logger.debug('No data found');
        return Promise.resolve('success');
      }
      result.forEach(async (event: InboundEventData) => {
        const diff = new Date().getTime() - event.finishReadTimeStamp;
        //if the diff is more than 30 seconds reset the readstatus to null
        if (diff > 30) {
          //Updating read status for the finish read task
          const updateResult: UpdateResult = await this.commonDbService.updateData(this.commonDbStore.getInboundEventStore(),
            { clientId: event.clientId, messageId: event.messageId, uniqueId: event.uniqueId, shipId: event.shipId }, { finishRead: null });
          if (updateResult.affected === 0) {
            this.logger.error(`Error in updating finish read status for the client ${event.clientId} with message ${event.messageId}`, `Unique id ${event.uniqueId} from the ship ${event.shipId}`);
          }
        }
      });
    } catch (err) {
      if (err instanceof Redlock.LockError) {
        this.logger.warn('Recheck Finish tasks is locked');
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
