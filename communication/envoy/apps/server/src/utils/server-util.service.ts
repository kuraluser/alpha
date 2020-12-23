import { Socket } from 'socket.io';
import * as _ from 'underscore';
import * as fs from 'fs';
import { Injectable } from '@nestjs/common';
import * as path from 'path';
import { EventData, CommonLoggerService } from '@envoy/common';
import { RedisUtilService } from './redis-util.service';
import { ConfigService } from '@nestjs/config';

/**
 * Util class for server
 */
@Injectable()
export class ServerUtilService {

    private clientSocketMap: Map<string, Socket> = new Map();
    private clientPublicKeys: Map<string, string> = new Map();

    constructor(private logger: CommonLoggerService, private configService: ConfigService, private redisUtilService: RedisUtilService) {
        this.logger.setContext('ServerUtilService');
        this.logger.log('Loading public keys');
        const directory = path.normalize(configService.get<string>('jwtPublicKeyPath'));
        const filesArray: string[] = fs.readdirSync(directory);
        //Reading the public key files(pem) for each client and storing it in the map
        _.chain(filesArray).filter(f => (f.indexOf('.pem') !== -1))
            .each(f => this.clientPublicKeys.set(f.substring(0, f.lastIndexOf('.pem')),
                fs.readFileSync(path.normalize(`${directory}/${f}`), 'utf8')));
    }

    public getClientPublicKey(shipid: string): string {
        return this.clientPublicKeys.get(shipid);
    }

    public getClientSocket(shipid: string): Socket {
        return this.clientSocketMap.get(shipid);
    }

    public storeClientSocket(shipid: string, socket: Socket): void {
        this.clientSocketMap.set(shipid, socket);
    }

    public removeClientSocket(shipid: string): void {
        this.clientSocketMap.delete(shipid);
    }


    /**
     * Method to groupby client and ship and sort by seq
     * 
     * @param result 
     */
    public groupByClientAndShipSortBySeq(result: EventData[]): EventData[] {
        // Creating events array grouped by the clients
        const groupByClient = _.groupBy(result, (eventData) => {
            return eventData.clientId;
        });
        // Creating events array grouped by the shipId and sorted by sequence
        const eventsGroupedByClientAndShip: EventData[] = [];
        _.values(groupByClient).forEach((eventArray: EventData[]) => {
            const groupByShip = _.groupBy(eventArray, (eventData) => {
                return eventData.shipId;
            });
            _.values(groupByShip).forEach((eventArray1: EventData[]) => {
                const sortedEvents = _.sortBy(eventArray1, (data: EventData) => data.sequence);
                eventsGroupedByClientAndShip.push(_.first(sortedEvents));
            });
        });
        return eventsGroupedByClientAndShip;
    }
}