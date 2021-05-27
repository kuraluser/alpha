import { ConfigService } from '@nestjs/config';
import { CommonDataStore } from '../store/common-db-store';
import { CommonDBService } from '../services/common-db.service';
import { InboundEventData, InboundEventDataTransfer } from '../models/common-models';
import { TaskCompleted, AppConstants, dataEmitter } from '../utils/common-app.constants';
import * as io from 'socket.io-client';
import { Socket } from 'socket.io';
import * as fs from 'fs';
import * as path from 'path';
import * as _ from 'underscore';
import { CommonLoggerService } from '../services/common-logger.service';
import { Injectable } from '@nestjs/common';
import { CommonUtilService } from '../utils/common-util.service';
import { InBoundEventDataEntity } from '../entities/common-inbound-event.entity';
import { UpdateResult } from 'typeorm';

/**
 * Class to perform common transport task functions
 */
@Injectable()
export class CommonTransportTaskService {

    private timerSleepInMs = 20000;

    private ioClient: SocketIOClient.Socket | SocketIO.Socket;

    constructor(private commonDbStore: CommonDataStore, private commonUtilService: CommonUtilService,
        private commonDbService: CommonDBService<InBoundEventDataEntity, InboundEventData>,
        private configService: ConfigService,
        private logger: CommonLoggerService) {
        this.logger.setContext('CommonTransportTaskService');
    }

    /**
        * Method to start the transport method
        * @param event
        */
    public intiateTransport(eventData: InboundEventData, taskCompleted: TaskCompleted): void {

        this.logger.log('Initiating Transport');

        const splitFileSize: number = this.configService.get<number>('splitFileSize');
        //Copying ioclient object 
        this.ioClient = eventData.ioClient;
        //Removing ioclient object from eventdata before processing
        eventData = _.omit(eventData, 'ioClient');
        //Checking if the packets are missing
        if (eventData.missingPackets && eventData.missingPackets.length !== 0) {
            const packetArray: string[] = eventData.missingPackets.split(",");
            if (packetArray.length === 0) {
                this.saveEventSendState(eventData, taskCompleted);
                return;
            }
            eventData.partNumber = packetArray[0];
        } else if (eventData.partNumber === AppConstants.PART_NUMBER_ZERO && eventData.split === 'y' && Number(eventData.size) > splitFileSize) {
            //Checking if its the first part of the packet
            eventData.partNumber = eventData.startIndex;
        } else if (eventData.partNumber !== AppConstants.PART_NUMBER_ZERO) {
            const nextSeq: number = parseInt(eventData.partNumber, 10) + 1;
            //Checking if the eventdata is completely transported
            if (nextSeq > eventData.total) {
                this.saveEventSendState(eventData, taskCompleted);
                return;
            }
            const totalLength = eventData.total.toString().length;
            const sequence: string = this.commonUtilService.leadingZero(nextSeq, totalLength);
            eventData.partNumber = sequence;
        }

        this.transportData(eventData, taskCompleted);
    }

    /**
     * Method to recursively send data using websockets Socket.IO library
     *
     * @param eventData
     */
    private transportData(eventData: InboundEventData, taskCompleted: TaskCompleted): void {

        //Checking if the data is cancelled
        if (this.commonUtilService.checkCanceledKeys(eventData.uniqueId)) {
            this.logger.warn('Data has cancelled by the client for the id ' + eventData.uniqueId);
            taskCompleted("Error", '');
            return;
        }
        let filePath: string;
        let fileName: string;
        this.logger.log('Part number ' + eventData.partNumber);
        if (eventData.partNumber === AppConstants.PART_NUMBER_ZERO) {
            filePath = eventData.filePath;
            fileName = eventData.fileName;
        } else {
            filePath = `${eventData.filePath}.sf-part${eventData.partNumber}`;
            fileName = `${eventData.fileName}.sf-part${eventData.partNumber}`;
        }
        this.logger.log('File name ' + filePath);
        fs.readFile(path.normalize(filePath), (err, data) => {
            if (err) {
                this.logger.error(`Error in reading the file ${err.message}`, err.stack);
                taskCompleted("Error", '');
                return;
            }
            const dataToSend: InboundEventDataTransfer = {
                clientId: eventData.clientId,
                fileName: fileName,
                buf: data
            };
            //Checking if its the first part of the packet
            if (eventData.partNumber === AppConstants.PART_NUMBER_ZERO || eventData.partNumber === eventData.startIndex) {
                dataToSend.messageId = eventData.messageId;
                dataToSend.messageType = eventData.messageType;
                dataToSend.checksum = eventData.checksum;
                dataToSend.algo = eventData.algo;
                dataToSend.size = eventData.size;
                dataToSend.total = eventData.total;
                dataToSend.seq = eventData.sequence;
                dataToSend.createdtimeStamp = eventData.createdtimeStamp
            }
            //Checking if the socket is connected if not retry after delay
            if (!this.ioClient || !this.ioClient.connected) {
                setTimeout(async () => {
                    this.logger.warn('Error socket connection');
                    //Updating the transport read timestamp to show the file is still under processing
                    const updateResult: UpdateResult = await this.commonDbService.updateData(this.commonDbStore.getInboundEventStore(),
                        { clientId: eventData.clientId, messageId: eventData.messageId, uniqueId: eventData.uniqueId, shipId: eventData.shipId },
                        { transportReadTimeStamp: new Date().getTime() });
                    if (updateResult.affected === 0) {
                        this.logger.error(`Error in updating transport read timestamp for the client ${eventData.clientId} with message ${eventData.messageId}`, `Unique id ${eventData.uniqueId} for the ship ${eventData.shipId}`);
                    }
                    taskCompleted('No socket connection', '');
                }, this.timerSleepInMs);
                return;
            }
            //Calling socket.emit to send the data packets
            this.ioClient.emit('send', dataToSend, this.receiveAck(eventData, taskCompleted));
        });
    }

    /**
     * Method to receive acknowledgement after sending each packet
     * @param eventData 
     * @param taskCompleted 
     */
    private receiveAck(eventData: InboundEventData, taskCompleted: TaskCompleted): any {

        //Timing out send if the response didnt came within the limit
        const timer: NodeJS.Timeout = setTimeout(() => {
            //Calling the call back function
            this.logger.warn("Calling timeout for transport for client " + eventData.clientId + " with message id " + eventData.messageId + " from ship " + eventData.shipId);
            ack(undefined);
        }, this.timerSleepInMs);

        const ack = async (result) => {

            //console.log('result ' + result);
            // Clearing the control timeout
            clearTimeout(timer);
            // Checking if the ack is occurred from the timeout signal
            if (!result) {
                //Updating the transport read timestamp to show the file is still under processing
                const updateResult: UpdateResult = await this.commonDbService.updateData(this.commonDbStore.getInboundEventStore(),
                    { clientId: eventData.clientId, messageId: eventData.messageId, uniqueId: eventData.uniqueId, shipId: eventData.shipId },
                    { transportReadTimeStamp: new Date().getTime() });
                if (updateResult.affected === 0) {
                    this.logger.error(`Error in updating transport read timestamp for the client ${eventData.clientId} with message ${eventData.messageId}`, `Unique id ${eventData.uniqueId} for the ship ${eventData.shipId}`);
                }
                //Calling recursively to send the data
                this.transportData(eventData, taskCompleted);
                return;
            }

            try {
                const ackFileName: string = result;
                let nextSeq = 0;
                let seq = "";
                if (ackFileName.indexOf('part') !== -1) {
                    seq = ackFileName.substring(ackFileName.indexOf('part') + 4, ackFileName.length);
                    nextSeq = parseInt(seq, 10) + 1;
                } else {
                    nextSeq = eventData.total + 1;
                }

                //Checking for missing packets and removing the send packets
                if (eventData.missingPackets) {
                    let missingPacketArray = eventData.missingPackets.split(",");
                    missingPacketArray = _.filter(missingPacketArray, (item) => item !== seq);
                    if (missingPacketArray.length === 0) {
                        eventData.missingPackets = null;
                        eventData.partNumber = eventData.total.toString();
                        this.saveEventSendState(eventData, taskCompleted);
                        return;
                    }
                    eventData.missingPackets = missingPacketArray.toString();
                    nextSeq = parseInt(missingPacketArray[0], 10);
                } else if (nextSeq > eventData.total) {
                    this.saveEventSendState(eventData, taskCompleted);
                    return;
                }
                // Saving the send packets
                eventData.transportReadTimeStamp = new Date().getTime();
                //Update data updates the row without using select query to check if the row exists.
                //This improves the performance while saving the send packets to db
                await this.commonDbService.updateData(this.commonDbStore.getInboundEventStore(),
                    { clientId: eventData.clientId, uniqueId: eventData.uniqueId },
                    eventData);
                // Adding next file for transport
                const totalLength = eventData.total.toString().length;
                const sequence: string = this.commonUtilService.leadingZero(nextSeq, totalLength);
                eventData.partNumber = sequence;
                //Calling recursively to send the data
                this.transportData(eventData, taskCompleted);
            } catch (err) {
                this.logger.error(`Error response from server ${err.message}`, err.stack);
                taskCompleted(err, '');
            }
        };
        return ack;
    }

    /**
     * Saving event send success 
     * 
     * @param eventData 
     * @param taskCompleted 
     */
    public async saveEventSendState(eventData: InboundEventData, taskCompleted: TaskCompleted): Promise<string> {
        try {
            eventData.process = 'send';
            eventData.processStatus = 'SEND_SUCCESS';
            eventData.transportReadTimeStamp = new Date().getTime();
            // Saving completed send packets
            //Update data updates the row without using select query to check if the row exists.
            //This improves the performance while saving the send packets to db
            await this.commonDbService.updateData(this.commonDbStore.getInboundEventStore(), { clientId: eventData.clientId, shipId: eventData.shipId, uniqueId: eventData.uniqueId },
                eventData);
            taskCompleted(undefined, 'success');
            return "success";
        } catch (err) {
            this.logger.error(`Failed to save send packets ${err.message}`, err.stack);
            taskCompleted(err, '');
            return "failed";
        }
    }
}