import { Injectable } from "@nestjs/common";
import { CommonLoggerService } from '../services/common-logger.service';
import { promises as fspromise } from 'fs';
import * as mkdirp from 'mkdirp';
import * as NodeCache from 'node-cache';
import { dataEmitter } from "./common-app.constants";
import { CancelledCacheData } from "../models/common-models";

/**
 * Class to perform common util functions
 */
@Injectable()
export class CommonUtilService {

    private cancelledFileCache: NodeCache;

    constructor(private logger: CommonLoggerService) {
        this.logger.setContext('CommonUtilService');
        this.cancelledFileCache = new NodeCache();
    }

    /**
     * Method to add leading zeros
     * 
     * @param num 
     * @param total 
     */
    public leadingZero(num: number, total: number): string {
        let new_num = num.toString();
        for (let i = new_num.length; i < total; i++) {
            new_num = '0' + new_num;
        }
        return new_num;
    }

    /**
   * Writing data to the file
   * 
   * @param filePath 
   * @param data 
   */
    public async writeToFile(directory: string, filePath: string, data: Buffer): Promise<string> {
        try {
            await mkdirp(directory)
            await fspromise.writeFile(filePath, data)
            return Promise.resolve('success');
        } catch (err) {
            this.logger.error(`Failed to write to file ${err.message}`, err.stack);
            return Promise.reject('error');
        }

    }

    /**
     * Store cancelled keys with expiry 1 Hour
     * @param uniqueId 
     */
    public storeCanceledKeys(uniqueId: string, cancelledData: CancelledCacheData): boolean {
        return this.cancelledFileCache.set<CancelledCacheData>(uniqueId, cancelledData, 3600);
    }

    /**
     * Check if the keys are existing in the cache
     * @param uniqueId 
     */
    public checkCanceledKeys(uniqueId: string): boolean {
        const cancelledData: CancelledCacheData = this.cancelledFileCache.get<CancelledCacheData>(uniqueId);
        if (cancelledData) {
            return true;
        }
        return false;
    }

}