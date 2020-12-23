import { Injectable } from '@nestjs/common';
import * as io from 'socket.io-client';
import { ClientAuthService } from '../utils/client-auth.service';
import { ConfigService } from '@nestjs/config';
import { CommonLoggerService, CommonTransportTaskService } from '@envoy/common';

/**
 * Web socket init service
 */
@Injectable()
export class SocketClientService {

  private ioClient: SocketIOClient.Socket;
  private listenerIoClient: SocketIOClient.Socket;

  constructor(private configService: ConfigService, private readonly clientAuthService: ClientAuthService,
    private commonTransportTaskService: CommonTransportTaskService, private logger: CommonLoggerService) {
    this.logger.setContext('SocketClientService');
    
    const wsOptions: any = {
      path: '/ws', secure: true, rejectUnauthorized: true, reconnection: false,
      transportOptions: {
        polling: {
          extraHeaders: {
            'x-shipid': configService.get<string>('shipId'),
            'x-token': clientAuthService.getAuthToken(),
            'x-listener': "no"
          }
        }
      }
    };

    //Adding server certificate verification if required
    if(configService.get<boolean>('verifyServerCert')){
      wsOptions.ca = clientAuthService.getServerCA();
    }

    //Adding client certificates if required
    if(configService.get<boolean>('enableClientCert')){
      wsOptions.cert = clientAuthService.getClientTlsCert();
      wsOptions.key = clientAuthService.getClientTlsKey();
    }

    //Connection parameters for socket client. Auto reconnection is set to false to manually fire reconnect.
    this.ioClient = io.connect(configService.get<string>('socketUrl'), wsOptions);
    //Creating deep copy of the websocket options
    const wsOptions1: any = JSON.parse(JSON.stringify(wsOptions));
    wsOptions1.forceNew = true;
    wsOptions1.transportOptions.polling.extraHeaders['x-listener'] = "yes";

    this.listenerIoClient = io.connect(configService.get<string>('socketUrl'), wsOptions1);

    [this.ioClient, this.listenerIoClient].forEach(socketClient => {
      // Tracking disconnect event
      socketClient.on('disconnect', () => {
        this.logger.warn("Disconnected " + socketClient.nsp);
        setTimeout(() => {
          socketClient.connect();
        }, 10000);
      });

      socketClient.on('connect_timeout', () => {
        this.logger.warn("Connection timeout " + socketClient.nsp);
        socketClient.disconnect();
      });

      //Tracking connect event
      socketClient.on('connect', () => {
        this.logger.log("Connected " + socketClient.nsp);
      });

      socketClient.on('connect_error', (err) => {
        this.logger.warn(`Error in socket connection ${err.message}`);
        setTimeout(() => {
          socketClient.connect();
        }, 10000);
      });
    });

  }

  /**
   * Return socket object
   */
  public getSocket(): SocketIOClient.Socket {
    return this.ioClient;
  }

  /**
  * Return listener socket object
  */
  public getListenerSocket(connect?: boolean): SocketIOClient.Socket {
    if (connect && this.listenerIoClient.disconnected) {
      this.listenerIoClient = this.listenerIoClient.connect();
    }
    return this.listenerIoClient;
  }
}
