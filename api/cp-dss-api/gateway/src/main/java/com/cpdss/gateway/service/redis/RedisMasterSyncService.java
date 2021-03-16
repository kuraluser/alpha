/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.redis;

public interface RedisMasterSyncService {

  Integer OFFSET_VAL = 0;
  Integer PAGE_COUNT = 1500;

  void fetchDomainDataFromService();
}
