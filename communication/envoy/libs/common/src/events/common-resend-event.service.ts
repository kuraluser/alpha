import { ResendEventDataTransfer, InboundEventData } from '../models/common-models';
import { CommonDataStore } from '../store/common-db-store';
import { CommonLoggerService } from '../services/common-logger.service';
import { Injectable } from '@nestjs/common';
import { CommonDBService } from '../services/common-db.service';
import { AppConstants } from '../utils/common-app.constants';
import { InBoundEventDataEntity } from '../entities/common-inbound-event.entity';
import { UpdateResult } from 'typeorm';

/**
 * Service to handle common resend events
 * 
 */
@Injectable()
export class CommonResendEventService {
    constructor(private commonDbStore: CommonDataStore, private commonDbService: CommonDBService<InBoundEventDataEntity, InboundEventData>,
        private logger: CommonLoggerService) {
        this.logger.setContext('CommonResendEventService');
    }

    /**
     * Method to handle resend event
     * 
     * @param resendPacket 
     */
    public handleResendEvent(resendPacket: ResendEventDataTransfer): void {
        (async () => {
            try {
                const conditions = "inbound.clientId = :clientId AND inbound.messageId = :messageId AND inbound.uniqueId = :uniqueId";
                const params = { clientId: resendPacket.clientId, messageId: resendPacket.messageId, uniqueId: resendPacket.uniqueId };
                const eventData: InboundEventData = await this.commonDbService.findOne(this.commonDbStore.getInboundEventStore(), "inbound", conditions, params) as InboundEventData;
                if (eventData.process !== 'finish' && eventData.process !== 'confirm') {
                    return;
                }
                // Saving resending request
                //if missing packet is empty send the full data
                if (!resendPacket.missingPackets || resendPacket.missingPackets.length === 0) {
                    //Resetting the partnumber to zero
                    eventData.partNumber = AppConstants.PART_NUMBER_ZERO;
                }
                eventData.missingPackets = resendPacket.missingPackets;
                eventData.process = 'split';
                const updatedResult: UpdateResult = await this.commonDbService.updateData(this.commonDbStore.getInboundEventStore(), { clientId: eventData.clientId, messageId: eventData.messageId, uniqueId: eventData.uniqueId }, eventData);
                if (updatedResult.affected !== 0) {
                    this.logger.log("Succesfully saved retrying event " + eventData.messageId);
                }
            } catch (err) {
                this.logger.error(`Error in retriving data ${err.message}`, err.stack);
            }
        })();
    }
}