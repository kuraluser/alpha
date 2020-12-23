import { Injectable } from '@nestjs/common';
import * as fs from 'fs';
import * as path from 'path';
import { sign, SignOptions } from 'jsonwebtoken';
import { CommonLoggerService } from '@envoy/common';
import { ConfigService } from '@nestjs/config';

/**
 * Class to perform client auth operations
 */
@Injectable()
export class ClientAuthService {

  private authToken: string;
  private serverCA: string;
  private clientTlsCert: string;
  private clientTlsKey: string;

  constructor(private configService: ConfigService, private logger: CommonLoggerService) {

    this.logger.setContext('ClientAuthService');
    const shipId = this.configService.get<string>('shipId');
    this.logger.log("Starting envoy client for the ship " + shipId);
    this.logger.log("Loading JWT private key");

    const jwtPrivateKeyFile = path.normalize(configService.get<string>('clientJWTKeyFile'));
    const jwtPrivateKey = fs.readFileSync(jwtPrivateKeyFile, 'utf8');
    const payLoad = { "shipid": shipId };
    this.logger.log("Generating JWT auth token");
    const jwTSignOptions: SignOptions = { algorithm: 'RS256' };
    this.authToken = sign(payLoad, jwtPrivateKey, jwTSignOptions);

    if (configService.get<boolean>('verifyServerCert')) {
      this.logger.log("Loading TLS Server-CA");
      const serverKeyFile = path.normalize(configService.get<string>('serverCAFile'))
      this.serverCA = fs.readFileSync(serverKeyFile, 'utf8');
    }

    if (configService.get<boolean>('enableClientCert')) {
      this.logger.log("Loading TLS certificate");
      const tlsClientCertFile = path.normalize(configService.get<string>('clientCertFile'))
      this.clientTlsCert = fs.readFileSync(tlsClientCertFile, 'utf8');

      this.logger.log("Loading TLS privatekey");
      const tlsClientKeyFile = path.normalize(configService.get<string>('clientKeyFile'))
      this.clientTlsKey = fs.readFileSync(tlsClientKeyFile, 'utf8');
    }

  }
  /**
   * Returning auth token
   */
  public getAuthToken(): string {
    return this.authToken;
  }

  /**
   * Returning Server ca
   */
  public getServerCA(): string {
    return this.serverCA;
  }

  /**
   * Returning Client cert
   */
  public getClientTlsCert(): string {
    return this.clientTlsCert;
  }

  /**
   * Returning Client key
   */
  public getClientTlsKey(): string {
    return this.clientTlsKey;
  }

}
