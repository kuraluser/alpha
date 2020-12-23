import { Injectable } from '@nestjs/common';
import * as _ from 'underscore';
import * as crypto from 'crypto';
import * as path from 'path';
import * as splitFile from 'split-file';
import { promises as fspromise } from 'fs';
import { ConfigService } from '@nestjs/config';
import { CommonDataStore } from '../store/common-db-store';
import { OutboundEventData } from '../models/common-models';
import { CommonDBService } from '../services/common-db.service';
import { CommonLoggerService } from '../services/common-logger.service';
import { CommonUtilService } from '../utils/common-util.service';
import { TaskCompleted, ProcessStatus } from '../utils/common-app.constants';
import { OutBoundEventDataEntity } from '../entities/common-outbound-event.entity';

/**
 * Class to perform common verify task functions
 */
@Injectable()
export class CommonVerifyTaskService {

    constructor(private configService: ConfigService, private commonDbStore: CommonDataStore, private commonUtilService: CommonUtilService, 
        private commonDbService: CommonDBService<OutBoundEventDataEntity, OutboundEventData>, private logger: CommonLoggerService) {
        this.logger.setContext('CommonVerifyTaskService');
    }

    /**
     * Method to verify the recieved files
     *
     * @param eventData
     */
    public verifyReceivedFiles(eventData: OutboundEventData, taskCompleted: TaskCompleted): void {

        //Checking if the file needs resending if so return without verification
        if (ProcessStatus[eventData.processStatus] === ProcessStatus['RECEIVED_WITH_HASH_FAILED'] || ProcessStatus[eventData.processStatus] === ProcessStatus['RECEIVED_WITH_PACKET_MISSING']) {
            taskCompleted(undefined, 'success');
            return;
        }

        // Single file verification
        if (Number(eventData.total) === 1) {
            this.verifyChecksum(eventData, taskCompleted);
            return;
        }
        //Multi file verification
        this.verifyPackets(eventData, taskCompleted);
    }


    /**
     * Method to verify checksum for a completed file
     * 
     * @param eventData 
     */
    private async verifyChecksum(eventData: OutboundEventData, taskCompleted: TaskCompleted): Promise<boolean> {
        try {
            const shasum = crypto.createHash(eventData.algo);
            const buff = await fspromise.readFile(eventData.filePath);
            shasum.update(buff);
            const check: string = shasum.digest('hex');
            if (check.toLowerCase() === eventData.checksum.toLowerCase()) {
                eventData.processStatus = 'RECEIVED_WITH_HASH_VERIFIED';
                eventData.process = "verified";
                eventData = _.omit(eventData, 'missingPackets');
            } else {
                eventData.processStatus = 'RECEIVED_WITH_HASH_FAILED';
                this.logger.warn('Checksum verification failed for ' + eventData.uniqueId);
            }
            eventData.verifyReadTimeStamp = new Date().getTime();
            await this.commonDbService.updateObject(this.commonDbStore.getOutboundEventStore(), eventData);
            taskCompleted(undefined, 'success');
            return Promise.resolve(true);
        } catch (err) {
            this.logger.error(`Error in checksum validation for ${eventData.uniqueId} ${err.message}`, err.stack);
            taskCompleted(err, '');
            return Promise.reject(err);
        }
    }


    /**
    * Method to verify missing packets while receiving
    * 
    * @param eventData 
    */
    private async verifyPackets(eventData: OutboundEventData, taskCompleted: TaskCompleted): Promise<boolean> {
        try {
            const packetMissingArray: string[] = [];
            const directory = path.normalize(`${this.configService.get<string>('appPath')}/data/${eventData.clientId}/${eventData.shipId}/${eventData.uniqueId}`);
            let filesArray: string[] = await fspromise.readdir(directory);
            // Splitting and sorting by part number
            const packetsArray = _.chain(filesArray).filter(f => (f.indexOf('part') !== -1)).map(f => parseInt(f.substr((f.indexOf('part') + 4), f.length), 10)).sortBy(num => num).values();
            let index = 1;
            packetsArray.forEach(f => {
                if (f !== index) {
                    // Adding missing packets
                    while (f !== index) {
                        const totalLength = eventData.total.toString().length;
                        packetMissingArray.push(this.commonUtilService.leadingZero(index, totalLength));
                        index++;
                    }
                }
                index++;
            });
            index = Number(eventData.total);
            //Checking if any packets missed at the ending
            packetsArray.reverse().every(f => {
                //Exit the loop if the first item in the reversed array is same as the total
                if (f === index) {
                    return false;
                }
                 // Adding missing packets
                 while (f !== index) {
                    const totalLength = eventData.total.toString().length;
                    packetMissingArray.push(this.commonUtilService.leadingZero(index, totalLength));
                    index--;
                }
                //Exit the loop when the first item in the reversed array and the index becomes equal
                return false;
            });
            //If packets not missing then merge it to the single file and validate checksum
            if (packetMissingArray.length === 0) {
                filesArray = filesArray.map(file => `${directory}/${file}`)
                await splitFile.mergeFiles(filesArray, eventData.filePath);
                await this.verifyChecksum(eventData, taskCompleted);
                //Removing the part files
                _.chain(filesArray).filter(f => (f.indexOf('part') !== -1)).each(async f => await fspromise.unlink(f));
                return Promise.resolve(true);
            }
            // Storing missing packets
            eventData.processStatus = 'RECEIVED_WITH_PACKET_MISSING';
            eventData.missingPackets = packetMissingArray.toString();
            eventData.verifyReadTimeStamp = new Date().getTime();
            await this.commonDbService.updateObject(this.commonDbStore.getOutboundEventStore(),eventData);
            taskCompleted(undefined, 'success');
            return Promise.resolve(true);
        } catch (err) {
            this.logger.error(`Error in missing packet validation for ${eventData.uniqueId} ${err.message}`, err.stack);
            taskCompleted(err, '');
            return Promise.reject(err);
        }
    }
}