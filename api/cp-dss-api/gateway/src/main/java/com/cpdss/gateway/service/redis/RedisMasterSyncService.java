/* Licensed under Apache-2.0 */
package com.cpdss.gateway.service.redis;

public interface RedisMasterSyncService {

  Integer OFFSET_VAL = 0;
  Integer PAGE_COUNT = 1500;

  void fetchDomainDataFromService();
}
