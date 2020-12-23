import { Injectable } from '@nestjs/common';
import { CommonLoggerService, EventData } from '@envoy/common';
import { ConfigService } from '@nestjs/config';
import * as IORedis from 'ioredis';
import * as Redlock from 'redlock';

/**
 * Util class for server
 */
@Injectable()
export class RedisUtilService {

    //30 seconds default ttl for each resources
    private ttl = 3 * 1000 * 10;

    private redLock: Redlock;

    private subScriberRedis: IORedis.Redis;

    private redis: IORedis.Redis;

    constructor(private configService: ConfigService, private logger: CommonLoggerService) {
        this.logger.setContext('RedisUtilService');
        this.logger.log('Initialising redis');

        this.subScriberRedis = new IORedis({
            sentinels: configService.get('redisSentinalServers'),
            name: configService.get('redisMasterName'),
            password: configService.get('redisMasterPassword'),
            connectTimeout: 10000,
            autoResubscribe: true
        });
        this.redis = new IORedis({
            sentinels: configService.get('redisSentinalServers'),
            name: configService.get('redisMasterName'),
            password: configService.get('redisMasterPassword'),
            connectTimeout: 10000
        });
        this.redLock = new Redlock([this.redis], { retryCount: 0 });
    }

    /**
     * Method to lock an object with default expiry time
     * 
     * @param resource 
     */
    public lockObject(resource: string): Promise<Redlock.Lock> {
        return this.redLock.lock(resource, this.ttl);
    }

    /**
     * Method to unlock
     * 
     * @param lock 
     */
    public releaseLock(lock: Redlock.Lock): void {
        lock.unlock();
    }

    /**
     * Method to store objects to redis
     * 
     * @param key 
     * @param resource 
     */
    public async storeObject(key: string, resource: any): Promise<string> {
        return await this.redis.set(key, Buffer.from(resource));
    }

    /**
     * Method to store objects to redis
     * 
     * @param key 
     * @param resource 
     */
    public async getObject(key: string): Promise<Buffer> {
        return await this.redis.getBuffer(key);
    }

    /**
    * Method to delete objects from redis
    * 
    * @param key 
    * @param resource 
    */
    public async deleteObject(key: string): Promise<number> {
        return await this.redis.del(key);
    }


    /**
     * Redis channel subscription
     * 
     * @param channelName 
     */
    public subscribeChannel(channelName: string): void {
        this.subScriberRedis.subscribe(channelName);
    }

    /**
     * Publish to channel
     * 
     * @param channelName 
     * @param eventData 
     */
    public publishToChannel(channelName: string, eventData: EventData): void {
        this.redis.publish(channelName, JSON.stringify(eventData));
    }

    /**
     * Method to get messages
     */
    public getRedisMessages(): IORedis.Redis {
        return this.subScriberRedis;
    }

}