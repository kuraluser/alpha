import { Injectable } from '@nestjs/common';
import { InBoundEventDataEntity } from '../entities/common-inbound-event.entity';
import { OutBoundEventDataEntity } from '../entities/common-outbound-event.entity';
import { Repository } from 'typeorm';
import { InjectRepository } from '@nestjs/typeorm';
/**
 * Data Store init service
 */
@Injectable()
export class CommonDataStore {

    constructor(@InjectRepository(InBoundEventDataEntity)
    private inboundEventStore: Repository<InBoundEventDataEntity>,
        @InjectRepository(OutBoundEventDataEntity)
        private outboundEventStore: Repository<OutBoundEventDataEntity>) {
    }

    /**
     * Method to return outbound event store db
     * 
     * @param isNew 
     */
    public getOutboundEventStore(): Repository<OutBoundEventDataEntity> {
        return this.outboundEventStore;
    }

    /**
    * Method to return inbound event store db
    *
    * @param isNew
    */
    public getInboundEventStore(): Repository<InBoundEventDataEntity> {
        return this.inboundEventStore;
    }
}
