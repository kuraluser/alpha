import { Injectable } from '@nestjs/common';
import { Cron, CronExpression } from '@nestjs/schedule';
import { CommonDBService, CommonDataStore, InboundEventData, CommonLoggerService, CommonTransportTaskService, InBoundEventDataEntity } from '@envoy/common';
import * as fastq from 'fastq';
import * as Redlock from 'redlock';
import { RedisUtilService } from '../utils/redis-util.service';
import { ServerUtilService } from '../utils/server-util.service';
import { UpdateResult, AdvancedConsoleLogger } from 'typeorm';
import { JsonWebTokenError } from 'jsonwebtoken';

/**
 * Class to start/cordinate file split service
 */
@Injectable()
export class TransportTaskService {

  private transportTaskQueue: fastq.queue;
  private queueCount = 0;

  private TRANSPORT_TASK_DATA_LOCK = "transport:task:data:lock";

  private RECHECK_TRANSPORT_TASK_DATA_LOCK = "recheck:transport:task:data:lock";

  private TRANSPORT_DATA_CHANNEL = "transport_data_channel";

  constructor(private readonly commonDbStore: CommonDataStore, private readonly commonTransportTaskService: CommonTransportTaskService,
    private readonly commonDbService: CommonDBService<InBoundEventDataEntity, InboundEventData>, private serverUtilService: ServerUtilService,
    private logger: CommonLoggerService, private redisUtilService: RedisUtilService) {
    this.logger.setContext('TransportTaskService');
    this.transportTaskQueue = fastq(commonTransportTaskService, commonTransportTaskService.intiateTransport, 10);
    //Subscribing to the transport channel
    redisUtilService.subscribeChannel(this.TRANSPORT_DATA_CHANNEL);
    
    //Receiving messages 
    redisUtilService.getRedisMessages().on("message", (channel, message) => {
      const event: InboundEventData = JSON.parse(message) as InboundEventData;
      if (channel !== this.TRANSPORT_DATA_CHANNEL) {
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

      event.transportRead = 'y';

      this.transportTaskQueue.push(event, (err, re) => {
        if (err) {
          //To-Do Quit Queue
          this.logger.error(`Error in queue ${err.message}`, err.stack);
        }
        //Releasing lock after transporting
        this.queueCount--;
      });
    });
  }

  /**
   * Cron job to check for new flies to transpor
   */
  @Cron(CronExpression.EVERY_10_SECONDS)
  public async transportTaskCron(): Promise<string> {
    this.logger.debug('FILE TRANSPORT is under processing ' + this.queueCount);
    // Checking if the queue length is max size
    if (this.queueCount >= 10) {
      return Promise.resolve('success');
    }
    let redisLock: Redlock.Lock;
    try {
      redisLock = await this.redisUtilService.lockObject(this.TRANSPORT_TASK_DATA_LOCK);
      const params = { process: 'split' };
      const result: InboundEventData[] = await this.commonDbService.find(this.commonDbStore.getInboundEventStore(), 'inbound', "inbound.process = :process AND inbound.transportRead IS NULL", params);
      if (result.length === 0) {
        this.logger.debug('No data found');
        return Promise.resolve('success');
      }
      const eventsGroupedByClientAndShip: InboundEventData[] = this.serverUtilService.groupByClientAndShipSortBySeq(result) as InboundEventData[];

      eventsGroupedByClientAndShip.forEach(async (event: InboundEventData) => {
        //Publishing to redis channel 
        this.redisUtilService.publishToChannel(this.TRANSPORT_DATA_CHANNEL, event);

        //Updating read status for the transport task
        const updateResult: UpdateResult = await this.commonDbService.updateData(this.commonDbStore.getInboundEventStore(),
          { clientId: event.clientId, messageId: event.messageId, uniqueId: event.uniqueId, shipId: event.shipId }, { transportRead: 'y' });
        if (updateResult.affected === 0) {
          this.logger.error(`Error in updating transport read status for the client ${event.clientId} with message ${event.messageId}`, `Unique id ${event.uniqueId} for the ship ${event.shipId}`);
        }
      });
    } catch (err) {
      if (err instanceof Redlock.LockError) {
        this.logger.warn('Transport tasks is locked');
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
  * Cron job to recheck the file transport task, if any file stuck after 30 seconds restart the process
  */
  @Cron(CronExpression.EVERY_30_SECONDS)
  public async recheckTransportTaskCron(): Promise<string> {
    let redisLock: Redlock.Lock;
    try {
      redisLock = await this.redisUtilService.lockObject(this.RECHECK_TRANSPORT_TASK_DATA_LOCK);
      const params = { process: 'split', transportRead: 'y' };
      const result: InboundEventData[] = await this.commonDbService.find(
        this.commonDbStore.getInboundEventStore(), 'inbound', "inbound.process = :process AND inbound.transportRead = :transportRead", params);
      if (result.length === 0) {
        this.logger.debug('No data found');
        return Promise.resolve('success');
      }
      result.forEach(async (event: InboundEventData) => {
        const diff = new Date().getTime() - event.transportReadTimeStamp;
        //if the diff is more than 30 seconds reset the readstatus to null
        if (diff > 30) {
          //Updating read status for the transport read task
          const updateResult: UpdateResult = await this.commonDbService.updateData(this.commonDbStore.getInboundEventStore(),
            { clientId: event.clientId, messageId: event.messageId, uniqueId: event.uniqueId, shipId: event.shipId }, { transportRead: null });
          if (updateResult.affected === 0) {
            this.logger.error(`Error in updating transport read status for the client ${event.clientId} with message ${event.messageId}`, `Unique id ${event.uniqueId} from the ship ${event.shipId}`);
          }
        }
      });
    } catch (err) {
      if (err instanceof Redlock.LockError) {
        this.logger.warn('Recheck Transport tasks is locked');
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
