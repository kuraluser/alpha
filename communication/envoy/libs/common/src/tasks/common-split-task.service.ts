import { Injectable } from '@nestjs/common';
import * as splitFile from 'split-file';
import { CommonDataStore } from '../store/common-db-store';
import { CommonDBService } from '../services/common-db.service';
import { InboundEventData } from '../models/common-models';
import { CommonLoggerService } from '../services/common-logger.service';
import { TaskCompleted, AppConstants } from '../utils/common-app.constants';
import { ConfigService } from '@nestjs/config';
import { InBoundEventDataEntity } from '../entities/common-inbound-event.entity';

/**
 * Service to handle split tasks
 * 
 */
@Injectable()
export class CommonSplitTaskService {

    constructor(private commonDbStore: CommonDataStore,
        private commonDbService: CommonDBService<InBoundEventDataEntity, InboundEventData>,
        private configService: ConfigService,
        private logger: CommonLoggerService) {
        this.logger.setContext('CommonSplitTaskService');
    }


    /**
     * Method to handle file split task
     * 
     * @param eventData 
     * @param taskCompleted 
     */
    public splitTask(eventData: InboundEventData, taskCompleted: TaskCompleted): void {
        const splitFileSize: number = this.configService.get<number>('splitFileSize');
        (async () => {
            try {
                if (eventData.split === 'n' || eventData.size <= splitFileSize) {
                    eventData.total = 1;
                } else {
                    const partFiles: string[] = await splitFile.splitFileBySize(`${eventData.filePath}`, splitFileSize);
                    const startFile = partFiles[0];
                    const startIndex = startFile.substr(startFile.lastIndexOf('part') + 4, startFile.length);
                    eventData.startIndex = startIndex;
                    eventData.total = partFiles.length;
                }
                eventData.partNumber = AppConstants.PART_NUMBER_ZERO;
                eventData.process = 'split';
                eventData.processStatus = 'SPLIT_SUCCESS';
                eventData.splitReadTimeStamp = new Date().getTime();
                await this.commonDbService.updateObject(this.commonDbStore.getInboundEventStore(),
                    eventData
                );
                taskCompleted(undefined, 'success');
            } catch (err) {
                this.logger.error(`Split failed ${err.message}`, err.stack);
                eventData.splitReadTimeStamp = new Date().getTime();
                eventData.processStatus = 'SPLIT_FAILED';
                try {
                    await this.commonDbService.updateObject(this.commonDbStore.getInboundEventStore(),
                        eventData
                    );
                    taskCompleted(undefined, 'success');
                } catch (er) {
                    this.logger.error(`Failed to save split ${er.message}`, er.stack);
                    taskCompleted(er, '');
                }
            }
        })();
    }
}