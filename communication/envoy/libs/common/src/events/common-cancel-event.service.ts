import { InboundEventDataTransfer, OutboundEventData, InboundEventData, CancelledCacheData } from '../models/common-models';
import { ConfigService } from '@nestjs/config';
import { CommonDataStore } from '../store/common-db-store';
import { CommonLoggerService } from '../services/common-logger.service';
import { Injectable } from '@nestjs/common';
import { CommonDBService } from '../services/common-db.service';
import { CommonUtilService } from '../utils/common-util.service';
import * as path from 'path';
import { promises as fspromise } from 'fs';
import * as _ from 'underscore';
import { UpdateResult } from 'typeorm';

/**
 * Service to handle common cancel events
 * 
 */
@Injectable()
export class CommonCancelEventService {

    constructor(private configService: ConfigService, private commonUtilService: CommonUtilService,
        private commonDbStore: CommonDataStore,
        private commonDbService: CommonDBService<any, any>,
        private logger: CommonLoggerService) {
        this.logger.setContext('CommonCancelEventService');
    }
    /**
    * Method to handle cancel event for init-cancel task
    * @param eventData 
    * @param shipId 
    */
    public async handleCancelEvent(eventData: InboundEventDataTransfer, shipId: string): Promise<string> {
        try {
            const fileNameWithSeq: string = eventData.fileName;
            const uniqueId: string = fileNameWithSeq.substring(0, fileNameWithSeq.indexOf('.'));
            //Checking if the data is present in the outbound for cancelling
            const conditions = "outbound.clientId = :clientId AND outbound.uniqueId = :uniqueId AND outbound.shipId = :shipId";
            const params = { clientId: eventData.clientId, uniqueId: uniqueId, shipId: shipId }
            const outBoundData: OutboundEventData = await this.commonDbService.findOne(this.commonDbStore.getOutboundEventStore(), "outbound", conditions, params) as OutboundEventData;
            if (!outBoundData) {
                return Promise.resolve("no-data");
            }
            // Saving cancelled status for the packets
            const updatedResult: UpdateResult = await this.commonDbService.updateData(this.commonDbStore.getOutboundEventStore(),
                { clientId: eventData.clientId, uniqueId: uniqueId, shipId: shipId },
                { process: "cancelled", processStatus: "CANCELLED_SUCCESS" });
            //Adding cancelled data to the cache
            const cancelledCacheData: CancelledCacheData = { clientId: outBoundData.clientId, messageId: outBoundData.messageId, shipId: outBoundData.shipId, uniqueId: outBoundData.uniqueId };
            this.commonUtilService.storeCanceledKeys(outBoundData.uniqueId, cancelledCacheData);
            if (updatedResult.affected <= 0) {
                throw new Error("No data updated");
            }
            //Removing the entire file
            const directory = path.normalize(`${this.configService.get<string>('appPath')}/data/${eventData.clientId}/${shipId}/${uniqueId}`);
            try {
                await fspromise.access(directory);
                await fspromise.rmdir(directory, { recursive: true });
            } catch (error) {
                // Not handled
            }
            return Promise.resolve("success");
        } catch (err) {
            this.logger.error(`Failed to save cancelled status for the packets ${eventData.fileName} for the ship ${shipId}`, err.stack);
            //To-Do Error Handling
            return Promise.resolve("error");
        }
    }

    /**
    * Method to receive acknowledgement after cancelling each packet
    * 
    * @param eventData 
    * @param taskCompleted 
    */
    public async handleCancelAckEvent(eventData: InboundEventDataTransfer, shipId: string): Promise<string> {
        try {
            const fileNameWithSeq: string = eventData.fileName;
            const uniqueId: string = fileNameWithSeq.substring(0, fileNameWithSeq.indexOf('.'));
            const message: string = eventData.buf;
            //Checking if the cancel ack recieved late so that the current status remains as init-cancel 
            const conditions = "inbound.clientId = :clientId AND inbound.uniqueId = :uniqueId AND inbound.shipId = :shipId";
            const params = { clientId: eventData.clientId, uniqueId: uniqueId, shipId: shipId }
            const inBoundData: InboundEventData = await this.commonDbService.findOne(this.commonDbStore.getInboundEventStore(), "inbound", conditions, params) as InboundEventData;
            if (inBoundData.process !== 'init-cancel') {
                return Promise.resolve("success");
            }
            let processStatus = "";
            let process = "init-cancel";
            let cancelRead = inBoundData.cancelRead;
            if (message === 'error') {
                processStatus = "CANCELLED_ERROR";
            } else if (message === 'failed') {
                processStatus = "CANCELLED_FAILED";
            } else {
                processStatus = "CANCELLED_SUCCESS";
                process = 'cancelled';
                cancelRead = 'y';
                //Removing the entire file
                const directory = path.normalize(`${this.configService.get<string>('appPath')}/data/${eventData.clientId}/${shipId}/${uniqueId}`);
                try {
                    await fspromise.access(directory);
                    await fspromise.rmdir(directory, { recursive: true });
                } catch (error) {
                    // Not handled
                }
            }
            // Saving cancelled send packets
            const updatedResult: UpdateResult = await this.commonDbService.updateData(this.commonDbStore.getInboundEventStore(),
                { clientId: eventData.clientId, uniqueId: uniqueId, shipId: shipId }, { process: process, processStatus: processStatus, cancelRead: cancelRead });
            if (updatedResult.affected <= 0) {
                throw new Error("No data updated");
            }
            return Promise.resolve("success");
        } catch (err) {
            this.logger.error(`Error to save cancelled packets ${err.message}`, err.stack);
            return Promise.resolve("failed");
        }
    }
}