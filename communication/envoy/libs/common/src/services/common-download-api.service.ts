import { ConfigService } from '@nestjs/config';
import { CommonDataStore } from '../store/common-db-store';
import { OutboundEventData } from '../models/common-models';
import { CommonDBService } from './common-db.service';
import { CommonLoggerService } from './common-logger.service';
import { Injectable } from '@nestjs/common';
import * as path from 'path';
import * as _ from 'underscore';
import * as fs from 'fs';
import { OutBoundEventDataEntity } from '../entities/common-outbound-event.entity';
import { UpdateResult } from 'typeorm';
/**
 * Class to perform common download api functions
 */
@Injectable()
export class CommonDownloadApiService {

    constructor(private commonDbStore: CommonDataStore, private commonDbService: CommonDBService<OutBoundEventDataEntity, OutboundEventData>,
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
                const whereCondition = "outbound.clientId = :clientId AND outbound.shipId = :shipId AND outbound.process = :process";
                const whereParams = { clientId: params.clientId, shipId: params.shipId, process: 'verified' };
                const result: OutboundEventData[] = await this.commonDbService.findAndSortWithLimit(this.commonDbStore.getOutboundEventStore(), 'outbound', whereCondition, whereParams, "outbound.sequence", 1);
                if (result.length === 0) {
                    this.logger.log('No data found');
                    res.set('message', 'NO_DATA');
                    res.end();
                    return;
                }
                let outboundData: OutboundEventData = result[0];
                const filePath = outboundData.filePath;
                outboundData = _.omit(outboundData, ['_id', 'total', 'filePath', 'missingPackets', 'ioClient', 'fileName']);
                const file = path.normalize(filePath);
                if (!fs.existsSync(file)) {
                    res.send("File not exists");
                    throw new Error('File not found');
                }
                res.set('message', "DATA");
                // Setting message info as header
                res.set('message-info', JSON.stringify(outboundData));
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
}