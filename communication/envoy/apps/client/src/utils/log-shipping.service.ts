import { Injectable } from '@nestjs/common';
import { SocketClientService } from '../socket/socket-client.service';
import { logEmitter, CommonLoggerService, LogEventDataTransfer } from '@envoy/common';
import { format } from 'date-and-time';
import * as fs from 'fs';
import * as path from 'path';

/**
 * Util class to ship the logs to the server
 */
@Injectable()
export class LogShippingService {

    constructor(private readonly socketClientService: SocketClientService, private logger: CommonLoggerService) {
        this.logger.setContext('LogShippingService');
        //Listening for the log events
        logEmitter.on('log', (zipFilePath) => {
            const time = format(new Date(), 'YYYY-MM-DD hh-mm-ss A [GMT]Z', true);
            //console.log("ZIP FILE PATH "+zipFilePath);
            fs.readFile(path.normalize(zipFilePath), (err, data) => {
                if (err) {
                    this.logger.error(`Error in reading the log file ${err.message}`, err.stack);
                    return;
                }
                const dataToSend: LogEventDataTransfer = {
                    logTime: time,
                    buf: data
                };
                socketClientService.getSocket().emit('log', dataToSend);
            });
        });
    }

}