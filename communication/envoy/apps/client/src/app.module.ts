import { Module, NestModule, MiddlewareConsumer } from '@nestjs/common';
import { ConfigModule, ConfigService } from '@nestjs/config';
import { UploadController } from './controllers/upload.controller';
import { SocketClientService } from './socket/socket-client.service';
import { ScheduleModule } from '@nestjs/schedule';
import { ClientUtilService } from './utils/client-util.service';
import { SplitTaskService } from './scheduler/split-task.service';
import { RequestModfierInterceptor } from './interceptors/request-modifier.interceptor';
import { CommonModule } from '@envoy/common';
import { TransportTaskService } from './scheduler/transport-task.service';
import { FinishTaskService } from './scheduler/finish-task.service';
import { SocketListenerService } from './listeners/socket-listener.service';
import { ConfirmTaskService } from './scheduler/confirm-task.service';
import config from './appconfig/config';
import { VerifyTaskService } from './scheduler/verify-task.service';
import { ResendTaskService } from './scheduler/resend-task.service';
import { DownloadController } from './controllers/download.controller';
import { LogShippingService } from './utils/log-shipping.service';
import { ClientSimulateService } from './utils/client-simulate.service';
import { ClientAuthService } from './utils/client-auth.service';
import { TypeOrmModule } from '@nestjs/typeorm';

/**
 * Root module for Envoy client
 */
@Module({
  imports: [ConfigModule.forRoot({
    load: [config],
    isGlobal: true
  }), ScheduleModule.forRoot(), TypeOrmModule.forRootAsync({
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
  controllers: [UploadController, DownloadController],
  providers: [
    SocketClientService, ClientAuthService, ClientUtilService, SplitTaskService, TransportTaskService, FinishTaskService,
    ConfirmTaskService, VerifyTaskService, ResendTaskService, SocketListenerService, LogShippingService,
    ClientSimulateService
  ]
})
export class AppModule implements NestModule {
  configure(consumer: MiddlewareConsumer) {
    consumer.apply(RequestModfierInterceptor).forRoutes(UploadController);
  }
}
