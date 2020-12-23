import { InboundEventDataTransfer, OutboundEventData, InboundEventData } from '../models/common-models';
import { ConfigService } from '@nestjs/config';
import { CommonDataStore } from '../store/common-db-store';
import { CommonLoggerService } from '../services/common-logger.service';
import { Injectable } from '@nestjs/common';
import { CommonDBService } from '../services/common-db.service';
import { CommonUtilService } from '../utils/common-util.service';
import { ProcessStatus, AppConstants } from '../utils/common-app.constants';
import { UpdateResult } from 'typeorm';

/**
 * Service to handle common finish events
 * 
 */
@Injectable()
export class CommonFinishEventService {
    constructor(private configService: ConfigService, private commonUtilService: CommonUtilService,
        private commonDbStore: CommonDataStore,
        private commonDbService: CommonDBService<any, any>,
        private logger: CommonLoggerService) {
        this.logger.setContext('CommonFinishEventService');
    }
    /**
    * Method to handle finish event task
    * 
    * @param eventData 
    * @param shipId 
    */
    public async handleFinishEvent(eventData: InboundEventDataTransfer, shipId: string): Promise<string> {
        try {
            const clientID: string = eventData.clientId;
            const fileName: string = eventData.fileName;
            const uniqueId: string = fileName.substring(0, fileName.indexOf('.'));
            const conditions = "outbound.clientId = :clientId AND outbound.uniqueId = :uniqueId AND outbound.shipId = :shipId";
            const params = { clientId: clientID, uniqueId: uniqueId, shipId: shipId }
            const outBoundData: OutboundEventData = await this.commonDbService.findOne(this.commonDbStore.getOutboundEventStore(), "outbound", conditions, params) as OutboundEventData;
            if (!outBoundData) {
                throw new Error('no data found');
            }
            // Checking the process status of the outboud data
            if (!outBoundData.process || ProcessStatus[outBoundData.processStatus] === ProcessStatus['RECEIVED_WITH_HASH_FAILED']
                || ProcessStatus[outBoundData.processStatus] === ProcessStatus['RECEIVED_WITH_PACKET_MISSING']) {
                await this.commonDbService.updateData(this.commonDbStore.getOutboundEventStore(),
                    { clientId: clientID, uniqueId: uniqueId, shipId: shipId },
                    { process: "received", processStatus: 'RECEIVED_SUCCESS', missingPackets: "" });
            }
            return Promise.resolve("success");
        } catch (err) {
            this.logger.error(`Error in received packet ${eventData.fileName} for the ship ${shipId}`, err.stack);
            return Promise.resolve("failed");
        }
    }

    /**
    * Method to handle finish ack event
    * 
    * @param eventData 
    * @param shipId 
    */
    public async handleFinishAckEvent(eventData: InboundEventDataTransfer, shipId: string): Promise<string> {
        try {
            const fileNameWithSeq: string = eventData.fileName;
            const uniqueId: string = fileNameWithSeq.substring(0, fileNameWithSeq.indexOf('.'));
            const message: string = eventData.buf;
            
            //Checking if the finish ack recieved late so that the current status remains as send 
            const conditions = "inbound.clientId = :clientId AND inbound.uniqueId = :uniqueId AND inbound.shipId = :shipId";
            const params = { clientId: eventData.clientId, uniqueId: uniqueId, shipId: shipId }
            const inBoundData: InboundEventData = await this.commonDbService.findOne(this.commonDbStore.getInboundEventStore(), "inbound", conditions, params) as InboundEventData;
            if (inBoundData.process !== 'send') {
                return Promise.resolve("success");
            }

            let processStatus = "";
            let process = "send";
            let partNumber = inBoundData.partNumber;
            let finishRead = inBoundData.finishRead;
            if (message === 'error') {
                processStatus = "FINISH_ERROR";
            } else if (message === 'failed') {
                //For resending if the finish is failed due to some error in the destination
                process = "split";
                processStatus = "FINISH_FAILED";
                partNumber = AppConstants.PART_NUMBER_ZERO;
            } else {
                process = 'finish';
                processStatus = "FINISH_SUCCESS";
                finishRead = 'y';
            }

            // Saving finished send packets
            const updatedResult: UpdateResult = await this.commonDbService.updateData(this.commonDbStore.getInboundEventStore(),
                { clientId: eventData.clientId, uniqueId: uniqueId, shipId: shipId },
                { process: process, processStatus: processStatus, partNumber: partNumber, finishRead: finishRead });
            if (updatedResult.affected <= 0) {
                throw new Error("No data updated");
            }
            return Promise.resolve("success");
        } catch (err) {
            this.logger.error(`'Error to save finished packets ${err.message}`, err.stack);
            return Promise.resolve("failed");
        }
    }
}