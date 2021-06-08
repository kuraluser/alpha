import { ConfigService } from '@nestjs/config';
import { CommonDataStore } from '../store/common-db-store';
import { CancelledCacheData, InboundEventData, InboundEventUploadResponse } from '../models/common-models';
import { CommonDBService } from './common-db.service';
import * as Busboy from 'busboy';
import * as crypto from 'crypto';
import * as mkdirp from 'mkdirp';
import { v1 as uuidv1 } from 'uuid';
import * as fs from 'fs';
import { CommonLoggerService } from './common-logger.service';
import { Injectable } from '@nestjs/common';
import { InBoundEventDataEntity } from '../entities/common-inbound-event.entity';
import { CommonUtilService } from '../utils/common-util.service';
/**
 * Class to perform common upload api functions
 */
@Injectable()
export class CommonUploadApiService {

    constructor(private configService: ConfigService, private commonDbStore: CommonDataStore,
        private commonDbService: CommonDBService<InBoundEventDataEntity, InboundEventData>,
        private logger: CommonLoggerService, private commonUtilService: CommonUtilService) {
        this.logger.setContext('CommonUploadApiService');
    }

    /**
     * Common method to upload a single file for client and server
     * 
     * @param params 
     * @param req 
     * @param checksum 
     * @param algo 
     * @param split 
     */
    public async uploadFile(params: any, req: any, checksum: string, algo: string, split: 'y' | 'n'): Promise<InboundEventUploadResponse> {
        try {
            //Checking the message id already exists
            let conditions = "inbound.clientId = :clientId AND inbound.messageId = :messageId AND inbound.shipId = :shipId";
            let whereParams: any = { clientId: params.clientId, messageId: params.messageId, shipId: params.shipId };
            let doc = await this.commonDbService.findOne(this.commonDbStore.getInboundEventStore(), 'inbound', conditions, whereParams);
            if (doc) {
                return { statusCode: "400", message: "Message Id already exists", messageId: params.messageId, shipId: params.shipId };
            }

            conditions = "inbound.clientId = :clientId AND inbound.shipId = :shipId AND inbound.sequence = :sequence";
            whereParams = { clientId: params.clientId, shipId: params.shipId, sequence: params.seq };
            //Checking if the sequence already exists for a particular client
            doc = await this.commonDbService.findOne(this.commonDbStore.getInboundEventStore(), 'inbound', conditions, whereParams);
            if (doc) {
                return { statusCode: "400", message: "Sequence already exists", messageId: params.messageId, shipId: params.shipId };
            }
            //Creating event data
            const uniqueId: string = uuidv1();
            const fileName = `${uniqueId}.zip`;
            let eventData: InboundEventData = {
                clientId: params.clientId, messageId: params.messageId, messageType: params.messageType, sequence: params.seq, size: 0, shipId: params.shipId,
                uniqueId: uniqueId, fileName: fileName, checksum: checksum, algo: algo, split: split, process: 'upload'
            }
            //Verifying event data and returns with metadata
            eventData = await this.verifyAndUploadFile(req, eventData);
            await this.commonDbService.insert(this.commonDbStore.getInboundEventStore(), eventData);
            return { statusCode: "200", message: "success", messageId: params.messageId, shipId: params.shipId };
        } catch (err) {
            this.logger.error(`Error in file upload ${err.message}`, err.stack);
            return { statusCode: "500", message: err, messageId: params.messageId, shipId: params.shipId };
        }
    }


    /**
     * Common method to update a single file for client and server
     * 
     * @param params 
     * @param req 
     * @param checksum 
     * @param algo 
     * @param split 
     */
    public async updateFile(params: any, req: any, checksum: string, algo: string, split: 'y' | 'n'): Promise<InboundEventUploadResponse> {
        try {
            const conditions = "inbound.clientId = :clientId AND inbound.messageId = :messageId AND inbound.shipId = :shipId";
            const whereParams: any = { clientId: params.clientId, messageId: params.messageId, shipId: params.shipId };
            let existingEventData: InboundEventData = await this.commonDbService.findOne(this.commonDbStore.getInboundEventStore(), 'inbound', conditions, whereParams) as InboundEventData;
            if (!existingEventData) {
                return { statusCode: "400", message: "Message Id doesnot exists", messageId: params.messageId, shipId: params.shipId };
            }
            //Creating event data
            existingEventData.size = 0;
            existingEventData.checksum = checksum;
            existingEventData.algo = algo;
            existingEventData.split = split;
            existingEventData.process = 'upload';
            //Verifying event data and returns with metadata
            existingEventData = await this.verifyAndUploadFile(req, existingEventData);
            await this.commonDbService.updateObject(this.commonDbStore.getInboundEventStore(), existingEventData);
            return { statusCode: "200", message: "success", messageId: params.messageId, shipId: params.shipId };
        } catch (err) {
            this.logger.error(`Error in update file ${err.message}`, err.stack);
            return { statusCode: "500", message: err, messageId: params.messageId, shipId: params.shipId };
        }
    }


    /**
     * Common method to cancel a single file for client and server
     * 
     * @param params 
     * @param req 
     * @param checksum 
     * @param algo 
     * @param split 
     */
    public async cancelFile(params: any): Promise<InboundEventUploadResponse> {
        try {
            const conditions = "inbound.clientId = :clientId AND inbound.messageId = :messageId AND inbound.shipId = :shipId";
            const whereParams: any = { clientId: params.clientId, messageId: params.messageId, shipId: params.shipId };
            let existingEventData: InboundEventData = await this.commonDbService.findOne(this.commonDbStore.getInboundEventStore(), 'inbound', conditions, whereParams) as InboundEventData;
            if (!existingEventData) {
                return { statusCode: "400", message: "Message Id doesnot exists", messageId: params.messageId, shipId: params.shipId };
            }
            //Creating event data
            existingEventData.process = 'init-cancel';
            //Verifying event data and returns with metadata
            await this.commonDbService.updateObject(this.commonDbStore.getInboundEventStore(), existingEventData);
            const cancelledCacheData: CancelledCacheData = { clientId: existingEventData.clientId, messageId: existingEventData.messageId, shipId: existingEventData.shipId, uniqueId: existingEventData.uniqueId };
            this.commonUtilService.storeCanceledKeys(existingEventData.uniqueId, cancelledCacheData);
            return { statusCode: "200", message: "success", messageId: params.messageId, shipId: params.shipId };
        } catch (err) {
            this.logger.error(`Error in update file ${err.message}`, err.stack);
            return { statusCode: "500", message: err, messageId: params.messageId, shipId: params.shipId };
        }
    }

   /**
   * Method to move and verify the input file to the output directory
   *
   * @param eventData
   */
    private async verifyAndUploadFile(req: any, eventData: InboundEventData): Promise<InboundEventData> {
        const busboy = new Busboy({ headers: req.headers });
        let writerStream: fs.WriteStream;
        const outputDirectory = `${this.configService.get<string>('appPath')}/data/${eventData.clientId}/${eventData.shipId}/${eventData.uniqueId}`;
        const outputFile: string = outputDirectory + '/' + eventData.fileName;
        let isFinished: boolean;
        try {
            let fileSize = 0;
            const shasum = crypto.createHash(eventData.algo);
            await mkdirp(outputDirectory);
            await new Promise((resolve, reject) => {
                // Reading the file and writing to the new location
                busboy.on('file', (fieldname, file, filename, encoding, mimetype) => {
                    // Only zip files are allowed
                    if (!filename.match(/\.(zip)$/)) {
                        reject('Only zip files are allowed');
                        return;
                    }
                    writerStream = fs.createWriteStream(outputFile);

                    file.on('data', chunk => {
                        writerStream.write(chunk);
                        shasum.update(chunk);
                        fileSize += chunk.length;
                    });
                    file.on('end', () => writerStream.end());

                    // Marking the end of the file after writing
                    writerStream.on('finish', () => {
                        const check: string = shasum.digest('hex');
                        console.log('checksum ' + check.toLowerCase());
                        if (check.toLowerCase() === eventData.checksum.toLowerCase()) {
                            resolve('success');
                        } else {
                            reject('Checksum verification failed');
                        }
                    });

                    // Rejecting with errors
                    writerStream.on('error', error => reject(error));
                    req.on('error', error => reject(error));
                });
                req.pipe(busboy);
            });

            eventData.filePath = outputFile;
            eventData.size = fileSize;
            eventData.createdtimeStamp = new Date().getTime();
            eventData.processStatus = 'UPLOAD_WITH_HASH_VERIFIED';
            isFinished = true;
            return Promise.resolve(eventData);
        } catch (err) {
            this.logger.error(`Error in file upload and verify ${err.message}`, err.stack);
            return Promise.reject(err);
        } finally {
            // Cleanup activities
            if (writerStream) {
                writerStream.destroy();
            }
            if (!isFinished) {
                this.removeDirSync(outputDirectory);
            }
        }
    }


    /**
     * Removing a directory with files synchronously
     *
     * @param dirPath
     */
    private removeDirSync(dirPath: string): void {
        let files: any;
        try {
            files = fs.readdirSync(dirPath);
        } catch (err) {
            this.logger.error(`Not able to remove output directory ${err.message}`, err.stack);
            return;
        }
        if (files.length > 0) {
            for (let i = 0; i < files.length; i++) {
                const filePath = dirPath + '/' + files[i];
                if (fs.statSync(filePath).isFile()) fs.unlinkSync(filePath);
                else this.removeDirSync(filePath);
            }
        }
        fs.rmdirSync(dirPath);
    }

}