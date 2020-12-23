import { Module } from '@nestjs/common';
import { CommonDBService } from './services/common-db.service';
import { CommonUploadApiService } from './services/common-upload-api.service';
import { CommonLoggerService } from './services/common-logger.service';
import { CommonUtilService } from './utils/common-util.service';
import { CommonSplitTaskService } from './tasks/common-split-task.service';
import { CommonTransportTaskService } from './tasks/common-transport-task.service';
import { CommonTransportEventService } from './events/common-transport-event.service';
import { CommonDataStore } from './store/common-db-store';
import { CommonFinishTaskService } from './tasks/common-finish-task.service';
import { CommonFinishEventService } from './events/common-finish-event.service';
import { CommonVerifyTaskService } from './tasks/common-verify-task.service';
import { CommonResendTaskService } from './tasks/common-resend-task.service';
import { CommonResendEventService } from './events/common-resend-event.service';
import { CommonConfirmTaskService } from './tasks/common-confirm-task.service';
import { CommonConfirmEventService } from './events/common-confirm-event.service';
import { CommonDownloadApiService } from './services/common-download-api.service';
import { InBoundEventDataEntity } from './entities/common-inbound-event.entity';
import { OutBoundEventDataEntity } from './entities/common-outbound-event.entity';
import { TypeOrmModule } from '@nestjs/typeorm';

/**
 * Common modules
 */
@Module({
  imports: [TypeOrmModule.forFeature([InBoundEventDataEntity, OutBoundEventDataEntity])],
  controllers: [],
  providers: [CommonDBService, CommonUploadApiService, CommonLoggerService,
    CommonUtilService, CommonDataStore, CommonSplitTaskService, CommonTransportTaskService,
    CommonTransportEventService, CommonFinishTaskService, CommonFinishEventService, CommonVerifyTaskService,
    CommonResendTaskService, CommonResendEventService, CommonConfirmTaskService, CommonConfirmEventService,
    CommonDownloadApiService],
  exports: [CommonDBService, CommonUploadApiService, CommonLoggerService,
    CommonUtilService, CommonDataStore, CommonSplitTaskService, CommonTransportTaskService,
    CommonTransportEventService, CommonFinishTaskService, CommonFinishEventService, CommonVerifyTaskService,
    CommonResendTaskService, CommonResendEventService, CommonConfirmTaskService, CommonConfirmEventService,
    CommonDownloadApiService]
})
export class CommonModule { }
