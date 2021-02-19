import { Injectable } from '@nestjs/common';
import { Cron, CronExpression } from '@nestjs/schedule';
import { CommonDBService, CommonDataStore, CommonUtilService, InboundEventData, CommonLoggerService, CommonSplitTaskService, InBoundEventDataEntity } from '@envoy/common';
import * as fastq from 'fastq';

/**
 * Class to start/cordinate file split service
 */
@Injectable()
export class SplitTaskService {

  private splitTaskQueue: fastq.queue;
  private isLocked = false;

  constructor(private readonly commonDbStore: CommonDataStore, private readonly commonSplitTaskService: CommonSplitTaskService,
    private readonly commonDbService: CommonDBService<InBoundEventDataEntity, InboundEventData>,
    private logger: CommonLoggerService) {
    this.logger.setContext('SplitTaskService');
    this.splitTaskQueue = fastq(commonSplitTaskService, this.commonSplitTaskService.splitTask, 1);
  }

  /**
   * Cron job to check for new flies to split
   */
  @Cron(CronExpression.EVERY_10_SECONDS)
  public async splitTaskCron(): Promise<string> {
    this.logger.debug('SPLIT QUEUE is under processing ' + this.isLocked);
    // Checking if the queue is locked
    if (this.isLocked) {
      return Promise.resolve('success');
    }
    try {
      const params =  { process: 'upload' };
      const result: InboundEventData[] = await this.commonDbService.findAndSortWithLimit(
        this.commonDbStore.getInboundEventStore(), 'inbound', "inbound.process = :process", params, "inbound.sequence", 1);
      if (result.length === 0) {
        this.logger.debug('No data found');
        return Promise.resolve('success');
      }
      result.forEach((event: InboundEventData) => {
        this.splitTaskQueue.push(event, (err, re) => {
          if (err) {
            //To-Do Quit Queue
            this.logger.error(`Error in queue ${err.message}`, err.stack);
          }
          //Releasing lock after splitting
          this.isLocked = false;
        });
      });
    } catch (err) {
      this.logger.error(`Error in retriving data ${err.message}`, err.stack);
    }
    return Promise.resolve('success');
  }
}
