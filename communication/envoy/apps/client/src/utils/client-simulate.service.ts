import { Injectable } from '@nestjs/common';
import { dataEmitter, CommonDataStore, CommonDBService, OutboundEventData, CommonLoggerService, OutBoundEventDataEntity } from '@envoy/common';
import fetch from 'node-fetch';
import * as FormData from 'form-data';
import { ConfigService } from '@nestjs/config';
import * as fs from 'fs';
import * as path from 'path';

/**
 * Class to simulate the data upload
 */
@Injectable()
export class ClientSimulateService {

    constructor(private configService: ConfigService, private logger: CommonLoggerService,
        private commonDbStore: CommonDataStore, private commonDbService: CommonDBService<OutBoundEventDataEntity, OutboundEventData>) {
        this.logger.setContext('ClientSimulateService');
        //Listening for the data events
        dataEmitter.on('data', (uniqueId) => {
            this.callUploadAPI(uniqueId).catch(err => {
                //No-Action
            });
        });
    }

    private async callUploadAPI(uniqueId: string): Promise<string> {
        try {
            const conditions = "outbound.uniqueId = :uniqueId";
            const params = { uniqueId: uniqueId }
            const eventData: OutboundEventData = await this.commonDbService.findOne(this.commonDbStore.getOutboundEventStore(), 'outbound', conditions, params);
            const port = this.configService.get<string>('port');
            const url = `http://localhost:${port}/push/${eventData.clientId}/${eventData.messageId}/${eventData.sequence}`;
            const file = path.normalize(eventData.filePath);
            if (!fs.existsSync(file)) {
                throw new Error('File not found');
            }
            const headerData = {
                'checksum': eventData.checksum, 'algo': eventData.algo,
                'split': eventData.total !== 1 ? 'y' : 'n'
            };
            const stream = fs.createReadStream(file, { highWaterMark: 128000 });
            const formData = new FormData()
            formData.append('zipfile', stream);

            fetch(url, {
                method: 'POST',
                headers: headerData,
                body: formData
            }).then(response => response.json())
                .then(data => {
                    this.logger.log(`File uplaod api response ${data}`);
                }).catch(err => {
                    this.logger.error(`Error response from the file uplaod api ${err.message}`, err.stack);
                })

        } catch (err) {
            this.logger.error(`Error in calling file uplaod api ${err.message}`, err.stack);
            return Promise.reject('error');
        }
        return Promise.reject('success');
    }

}