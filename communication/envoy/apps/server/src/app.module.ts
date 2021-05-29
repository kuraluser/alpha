import { Module } from '@nestjs/common';
import { ConfigModule, ConfigService } from '@nestjs/config';
import { UploadAppController } from './controllers/upload.controller';
import { SocketServerService } from './socket/socket-server.service';
import { CommonModule } from '@envoy/common';
import { VerifyTaskService } from './schedulers/verify-task.service';
import { ScheduleModule } from '@nestjs/schedule';
import { ResendTaskService } from './schedulers/resend-task.service';
import { ServerUtilService } from './utils/server-util.service';
import config from './appconfig/config';
import { SplitTaskService } from './schedulers/split-task.service';
import { TransportTaskService } from './schedulers/transport-task.service';
import { FinishTaskService } from './schedulers/finish-task.service';
import { ConfirmTaskService } from './schedulers/confirm-task.service';
import { DownloadController } from './controllers/download.controller';
import { TypeOrmModule } from '@nestjs/typeorm';
import { RedisUtilService } from './utils/redis-util.service';
import { CancelTaskService } from './schedulers/cancel-task.service';

/**
 * Root module for Envoy server
 */
@Module({
  imports: [ScheduleModule.forRoot(), ConfigModule.forRoot({
    load: [config],
    isGlobal: true
  }), TypeOrmModule.forRootAsync({
    imports: [ConfigModule],
    inject: [ConfigService],
    useFactory: async (configService: ConfigService) => ({
      type: 'postgres' as 'postgres',
      host: configService.get('dbHost'),
      port: configService.get<number>('dbPort'),
      username: configService.get('dbUserName'),
      password: configService.get('dbPassword'),
      database: configService.get('dbName'),
      autoLoadEntities: true,
      synchronize: true,
      logging: false
    }),
  }), CommonModule],
  controllers: [UploadAppController, DownloadController],
  providers: [
    SocketServerService, VerifyTaskService, ResendTaskService, SplitTaskService,
    TransportTaskService, FinishTaskService, ConfirmTaskService, CancelTaskService, ServerUtilService, RedisUtilService
  ]
})
export class AppModule { }
