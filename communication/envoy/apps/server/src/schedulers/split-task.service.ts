import { Injectable } from '@nestjs/common';
import { Cron, CronExpression } from '@nestjs/schedule';
import { CommonDBService, CommonDataStore, InboundEventData, CommonLoggerService, CommonSplitTaskService, InBoundEventDataEntity } from '@envoy/common';
import * as fastq from 'fastq';
import { ServerUtilService } from '../utils/server-util.service';
import { RedisUtilService } from '../utils/redis-util.service';
import * as Redlock from 'redlock';
import { UpdateResult } from 'typeorm';
import * as _ from 'underscore';

/**
 * Class to start/cordinate file split service
 */
@Injectable()
export class SplitTaskService {

  private splitTaskQueue: fastq.queue;
  private queueCount = 0;

  private SPLIT_TASK_DATA_LOCK = "split:task:data:lock";

  private RECHECK_SPLIT_TASK_DATA_LOCK = "recheck:split:task:data:lock";

  constructor(private readonly commonDbStore: CommonDataStore, private readonly commonSplitTaskService: CommonSplitTaskService,
    private readonly commonDbService: CommonDBService<InBoundEventDataEntity, InboundEventData>, private serverUtilService: ServerUtilService,
    private logger: CommonLoggerService, private redisUtilService: RedisUtilService) {
    this.logger.setContext('SplitTaskService');
    this.splitTaskQueue = fastq(commonSplitTaskService, this.commonSplitTaskService.splitTask, 1);
  }

  /**
   * Cron job to check for new flies to split
   */
  @Cron(CronExpression.EVERY_10_SECONDS)
  public async splitTaskCron(): Promise<string> {
    this.logger.debug('SPLIT QUEUE is under processing ' + this.queueCount);
    // Checking if the queue lenght is empty
    if (this.queueCount !== 0) {
      return Promise.resolve('success');
    }
    let redisLock: Redlock.Lock;
    try {
      redisLock = await this.redisUtilService.lockObject(this.SPLIT_TASK_DATA_LOCK);
      const params = { process: 'upload' };
      const result: InboundEventData[] = await this.commonDbService.find(
        this.commonDbStore.getInboundEventStore(), 'inbound', "inbound.process = :process AND inbound.splitRead IS NULL", params);
      if (result.length === 0) {
        this.logger.debug('No data found');
        return Promise.resolve('success');
      }
      const eventsGroupedByClientAndShip: InboundEventData[] = this.serverUtilService.groupByClientAndShipSortBySeq(result) as InboundEventData[];

      eventsGroupedByClientAndShip.forEach(async (event: InboundEventData) => {
        // Acquiring lock
        this.queueCount++;
        event.splitRead = 'y';
        this.splitTaskQueue.push(event, (err, re) => {
          if (err) {
            //To-Do Quit Queue
            this.logger.error(`Error in queue ${err.message}`, err.stack);
          }
          //Releasing lock after splitting
          this.queueCount--;
        });

        //Updating read status for the split task
        const updateResult: UpdateResult = await this.commonDbService.updateData(this.commonDbStore.getInboundEventStore(),
          { clientId: event.clientId, messageId: event.messageId, uniqueId: event.uniqueId, shipId: event.shipId }, { splitRead: 'y' });
        if (updateResult.affected === 0) {
          this.logger.error(`Error in updating split read status for the client ${event.clientId} with message ${event.messageId}`, `Unique id ${event.uniqueId} from the ship ${event.shipId}`);
        }
      });
    } catch (err) {
      if (err instanceof Redlock.LockError) {
        this.logger.warn('Split tasks is locked');
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
   * Cron job to recheck the file split task, if any file stuck after 30 seconds restart the process
   */
  @Cron(CronExpression.EVERY_30_SECONDS)
  public async recheckSplitTaskCron(): Promise<string> {
    let redisLock: Redlock.Lock;
    try {
      redisLock = await this.redisUtilService.lockObject(this.RECHECK_SPLIT_TASK_DATA_LOCK);
      const params = { process: 'upload', splitRead: 'y' };
      const result: InboundEventData[] = await this.commonDbService.find(
        this.commonDbStore.getInboundEventStore(), 'inbound', "inbound.process = :process AND inbound.splitRead = :splitRead", params);
      if (result.length === 0) {
        this.logger.debug('No data found');
        return Promise.resolve('success');
      }
      result.forEach(async (event: InboundEventData) => {
        const diff = new Date().getTime() - event.splitReadTimeStamp;
        //if the diff is more than 30 seconds reset the readstatus to null
        if (diff > 30) {
          //Updating read status for the split task
          const updateResult: UpdateResult = await this.commonDbService.updateData(this.commonDbStore.getInboundEventStore(),
            { clientId: event.clientId, messageId: event.messageId, uniqueId: event.uniqueId, shipId: event.shipId }, { splitRead: null });
          if (updateResult.affected === 0) {
            this.logger.error(`Error in updating split read status for the client ${event.clientId} with message ${event.messageId}`, `Unique id ${event.uniqueId} from the ship ${event.shipId}`);
          }
        }
      });
    } catch (err) {
      if (err instanceof Redlock.LockError) {
        this.logger.warn('Recheck Split tasks is locked');
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
