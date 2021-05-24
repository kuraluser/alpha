import { ConfigService } from '@nestjs/config';
import { CommonDataStore } from '../store/common-db-store';
import { OutboundEventData, InboundEventData, EventStatusResponse } from '../models/common-models';
import { CommonDBService } from './common-db.service';
import { CommonLoggerService } from './common-logger.service';
import { Injectable } from '@nestjs/common';
import * as path from 'path';
import * as _ from 'underscore';
import * as fs from 'fs';
import { OutBoundEventDataEntity } from '../entities/common-outbound-event.entity';
import { InBoundEventDataEntity } from '../entities/common-inbound-event.entity';
import { UpdateResult } from 'typeorm';
/**
 * Class to perform common download api functions
 */
@Injectable()
export class CommonDownloadApiService {

    constructor(private commonDbStore: CommonDataStore, private commonDbService: CommonDBService<OutBoundEventDataEntity, OutboundEventData>,
        private commonInboundDbService: CommonDBService<InBoundEventDataEntity, InboundEventData>,
        private logger: CommonLoggerService) {
        this.logger.setContext('CommonDownloadApiService');
    }

    /**
    *  Common method to download a single file for client and server
    * 
    * @param params 
    * @param req 
    * @param res 
    */
    public dowloadFile(params: any, res: any): void {
        res.set('Content-Type', 'application/zip');
        (async () => {
            try {
                const whereCondition = "outbound.clientId = :clientId AND outbound.shipId = :shipId AND outbound.messageType = :messageType AND outbound.process = :process";
                const whereParams = { clientId: params.clientId, shipId: params.shipId, messageType: params.messageType, process: 'verified' };
                const result: OutboundEventData[] = await this.commonDbService.findAndSortWithLimit(this.commonDbStore.getOutboundEventStore(), 'outbound', whereCondition, whereParams, "outbound.sequence", 1);
                if (result.length === 0) {
                    this.logger.log('No data found');
                    res.set('message', 'NO_DATA');
                    res.end();
                    return;
                }
                let outboundData: OutboundEventData = result[0];
                const filePath = outboundData.filePath;
                let responseOutboundData = _.omit(outboundData, ['_id', 'total', 'filePath', 'missingPackets', 'ioClient', 'fileName']);
                const file = path.normalize(filePath);
                if (!fs.existsSync(file)) {
                    res.send("File not exists");
                    throw new Error('File not found');
                }
                res.set('message', "DATA");
                // Setting message info as header
                res.set('message-info', JSON.stringify(responseOutboundData));
                const stream = fs.createReadStream(file, { highWaterMark: 128000 });
                stream.on('end', async () => {
                    const updateResult: UpdateResult = await this.commonDbService.updateData(this.commonDbStore.getOutboundEventStore(), { clientId: outboundData.clientId, messageId: outboundData.messageId, uniqueId: outboundData.uniqueId, shipId: outboundData.shipId }, { process: 'shared' });
                    if (updateResult.affected === 0) {
                        this.logger.error(`Error in updating shared status for the client ${outboundData.clientId} with message ${outboundData.messageId}`, `Unique id ${outboundData.uniqueId} from the ship ${outboundData.shipId}`);
                    }
                });
                stream.pipe(res);
            } catch (err) {
                this.logger.error(`Error in file read during download ${err.message}`, err.stack);
            }
        })();
    }


    /**
    *  Common method get the staus based on messageId for client and server
    * 
    * @param params 
    * @param req 
    * @param res 
    */
    public async getFileStatus(params: any): Promise<EventStatusResponse> {
        try {
            let whereCondition = "inbound.clientId = :clientId AND inbound.messageId = :messageId AND inbound.shipId = :shipId";
            let whereParams: any = { clientId: params.clientId, messageId: params.messageId, shipId: params.shipId };
            let existingInboundEventData: InboundEventData = await this.commonInboundDbService.findOne(this.commonDbStore.getInboundEventStore(), 'inbound', whereCondition, whereParams) as InboundEventData;
            if (!existingInboundEventData) {
                return { statusCode: "400", message: "Message Id doesnot exists", messageId: params.messageId };
            }
            let response: EventStatusResponse = { statusCode: "200", message: "Message Id found", messageId: params.messageId}
            response.eventUploadStatus = existingInboundEventData.processStatus;
            response.eventUploadPacketStatus = existingInboundEventData.partNumber+"/"+existingInboundEventData.total;
            whereCondition = "outbound.clientId = :clientId AND outbound.messageId = :messageId AND outbound.shipId = :shipId";
            whereParams = { clientId: params.clientId, messageId: params.messageId, shipId: params.shipId };
            let existingOutboundEventData: OutboundEventData = await this.commonDbService.findOne(this.commonDbStore.getOutboundEventStore(), 'outbound', whereCondition, whereParams) as OutboundEventData;
            if (existingOutboundEventData) {
                response.eventDownloadStatus = existingOutboundEventData.processStatus;
                response.eventDownloadPacketStatus = existingOutboundEventData.partNumber+"/"+existingOutboundEventData.total;
            }
            return response;
        } catch (err) {
            this.logger.error(`Error in reading file status ${err.message}`, err.stack);
            return { statusCode: "500", message: "Error in retrieving status", messageId: params.messageId };
        }
    }
}