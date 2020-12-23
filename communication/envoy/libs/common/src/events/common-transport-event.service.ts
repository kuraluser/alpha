import { InboundEventDataTransfer, OutboundEventData } from '../models/common-models';
import * as path from 'path';
import { ConfigService } from '@nestjs/config';
import { CommonDataStore } from '../store/common-db-store';
import { CommonLoggerService } from '../services/common-logger.service';
import { Injectable } from '@nestjs/common';
import { CommonDBService } from '../services/common-db.service';
import { CommonUtilService } from '../utils/common-util.service';
import { OutBoundEventDataEntity } from '../entities/common-outbound-event.entity';

/**
 * Service to handle common transport events
 * 
 */
@Injectable()
export class CommonTransportEventService {
    constructor(private configService: ConfigService, private commonUtilService: CommonUtilService, private commonDbStore: CommonDataStore, private commonDbService: CommonDBService<OutBoundEventDataEntity, OutboundEventData>,
        private logger: CommonLoggerService) {
        this.logger.setContext('CommonTransportEventService');
    }
    /**
    * Method to handle send event for transport task
    * @param eventData 
    * @param shipId 
    */
    public handleSendEvent(eventData: InboundEventDataTransfer, shipId: string): void {
        try {
            const clientID: string = eventData.clientId;
            const fileNameWithSeq: string = eventData.fileName;
            const uniqueId: string = fileNameWithSeq.substring(0, fileNameWithSeq.indexOf('.'));
            const directory = path.normalize(`${this.configService.get<string>('appPath')}/data/${clientID}/${shipId}/${uniqueId}`);
            const filePath = `${directory}/${fileNameWithSeq}`;
            this.commonUtilService.writeToFile(directory, filePath, eventData.buf).then(str => {
                // Checking if its the first packet arrived and store it to the db
                if (eventData.messageId) {
                    const dataReceived: OutboundEventData = {
                        clientId: clientID,
                        uniqueId: uniqueId,
                        fileName: `${uniqueId}.zip`,
                        filePath: `${directory}/${uniqueId}.zip`,
                        shipId: shipId,
                        messageId: eventData.messageId,
                        checksum: eventData.checksum,
                        algo: eventData.algo,
                        sequence: eventData.seq,
                        size: eventData.size,
                        total: eventData.total,
                        createdtimeStamp: eventData.createdtimeStamp
                    }
                    const conditions = "outbound.clientId = :clientId AND outbound.messageId = :messageId AND outbound.uniqueId = :uniqueId AND outbound.shipId = :shipId";
                    const params = { clientId: dataReceived.clientId, messageId: dataReceived.messageId, uniqueId: dataReceived.uniqueId, shipId: dataReceived.shipId };
                    //Checking if this is a resending packet if so update the data otherwise store it
                    this.commonDbService.findOne(this.commonDbStore.getOutboundEventStore(), "outbound", conditions, params).then(result => {
                        if (result) {
                            result.checksum = dataReceived.checksum;
                            result.algo = dataReceived.algo;
                            result.sequence = dataReceived.sequence;
                            result.size = dataReceived.size;
                            result.total = dataReceived.total;
                            result.createdtimeStamp = dataReceived.createdtimeStamp;
                            this.commonDbService.updateObject(this.commonDbStore.getOutboundEventStore(), result).then(res => {
                            }).catch(err => {
                                this.logger.error(`Error in updating the outbound data ${err.message}`, err.stack);
                            })
                            return;
                        }
                        this.commonDbService.insert(this.commonDbStore.getOutboundEventStore(), dataReceived).then(res => {
                        }).catch(err => {
                            this.logger.error(`Error in saving the outbound data ${err.message}`, err.stack);
                        })
                    });
                }
                //console.log(str);
            }).catch(err => {
                this.logger.error(`Error in writing received packet ${eventData.fileName} for the ship ${shipId} ${err.message}`, err.stack);
            });
        } catch (err) {
            this.logger.error(`Error in received packet ${eventData.fileName} for the ship ${shipId} ${err.message}`, err.stack);
        }
    }
}