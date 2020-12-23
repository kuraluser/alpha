import { InboundEventDataTransfer, OutboundEventData, InboundEventData } from '../models/common-models';
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
 * Service to handle common confirm events
 * 
 */
@Injectable()
export class CommonConfirmEventService {
    constructor(private configService: ConfigService, private commonUtilService: CommonUtilService,
        private commonDbStore: CommonDataStore,
        private commonDbService: CommonDBService<any, any>,
        private logger: CommonLoggerService) {
        this.logger.setContext('CommonConfirmEventService');
    }
    /**
    * Method to handle confirm event for finish task
    * @param eventData 
    * @param shipId 
    */
    public async handleConfirmEvent(eventData: InboundEventDataTransfer, shipId: string): Promise<string> {
        try {
            const clientID: string = eventData.clientId;
            const fileNameWithSeq: string = eventData.fileName;
            const uniqueId: string = fileNameWithSeq.substring(0, fileNameWithSeq.indexOf('.'));
            // Finding verified packets
            const conditions = "outbound.clientId = :clientId AND outbound.uniqueId = :uniqueId AND outbound.shipId = :shipId";
            const params = { clientId: clientID, uniqueId: uniqueId, shipId: shipId }
            const orCondition = "(outbound.process = :verified OR outbound.process = :shared)";
            const orParams = { verified: "verified", shared: "shared" }
            const event: OutboundEventData = await this.commonDbService.findOne(this.commonDbStore.getOutboundEventStore(), 'outbound',
                conditions, params, orCondition, orParams) as OutboundEventData;
            if (event) {
                return Promise.resolve("success");
            }
            return Promise.resolve("failed");
        } catch (err) {
            this.logger.error(`Failed to save finished packets ${eventData.fileName} for the ship ${shipId}`, err.stack);
            //To-Do Error Handling
            return Promise.resolve("error");
        }
    }

    /**
    * Method to receive acknowledgement after confirming each packet
    * @param eventData 
    * @param taskCompleted 
    */
    public async handleConfirmAckEvent(eventData: InboundEventDataTransfer, shipId: string): Promise<string> {
        try {
            const fileNameWithSeq: string = eventData.fileName;
            const uniqueId: string = fileNameWithSeq.substring(0, fileNameWithSeq.indexOf('.'));
            const message: string = eventData.buf;
           
            //Checking if the confirm ack recieved late so that the current status remains as send 
            const conditions = "inbound.clientId = :clientId AND inbound.uniqueId = :uniqueId AND inbound.shipId = :shipId";
            const params = { clientId: eventData.clientId, uniqueId: uniqueId, shipId: shipId }
            const inBoundData: InboundEventData = await this.commonDbService.findOne(this.commonDbStore.getInboundEventStore(), "inbound", conditions, params) as InboundEventData;
            if (inBoundData.process !== 'finish') {
                return Promise.resolve("success");
            }
            let processStatus = "";
            let process = "finish";
            let confirmRead = inBoundData.confirmRead;
            if (message === 'error') {
                processStatus = "CONFIRM_ERROR";
            } else if (message === 'failed') {
                processStatus = "CONFIRM_FAILED";
            } else {
                processStatus = "CONFIRM_SUCCESS";
                process = 'confirm';
                confirmRead = 'y';
                //Removing the part files
                const directory = path.normalize(`${this.configService.get<string>('appPath')}/data/${eventData.clientId}/${shipId}/${uniqueId}`);
                let filesArray: string[] = await fspromise.readdir(directory);
                filesArray = filesArray.map(file => `${directory}/${file}`)
                _.chain(filesArray).filter(f => (f.indexOf('part') !== -1)).each(async f => await fspromise.unlink(f));

            }
            // Saving confirmed send packets
            const updatedResult: UpdateResult = await this.commonDbService.updateData(this.commonDbStore.getInboundEventStore(),
                { clientId: eventData.clientId, uniqueId: uniqueId, shipId: shipId }, { process: process, processStatus: processStatus, confirmRead: confirmRead});
            if (updatedResult.affected <= 0) {
                throw new Error("No data updated");
            }
            return Promise.resolve("success");
        } catch (err) {
            this.logger.error(`Error to save confirmed packets ${err.message}`, err.stack);
            return Promise.resolve("failed");
        }
    }
}