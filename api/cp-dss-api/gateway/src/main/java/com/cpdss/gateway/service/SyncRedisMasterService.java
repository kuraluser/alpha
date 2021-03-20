/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service;

import com.cpdss.gateway.service.redis.CargoRedisSyncService;
import com.cpdss.gateway.service.redis.PortRedisSyncService;
import com.cpdss.gateway.service.redis.VesselRedisSyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class used in @PostConstruct, to Update the Redis Cache Data will fetch from Master Table
 * and Push Into Redis Cargo, Port, Vessel data are currently included
 *
 * @author johnsoorajxavier
 * @since 15-03-2021
 */
@Slf4j
@Service
public class SyncRedisMasterService {

  @Autowired CargoRedisSyncService cargoRedisSyncService;

  @Autowired PortRedisSyncService portRedisSyncService;

  @Autowired VesselRedisSyncService vesselRedisSyncService;

  public void updateRedisFromMasterData() {
    try {
      cargoRedisSyncService.fetchDomainDataFromService();
      portRedisSyncService.fetchDomainDataFromService();
      vesselRedisSyncService.fetchDomainDataFromService();
    } catch (Exception e) {
      log.info("error while updating cache Message {}", e.getMessage());
      e.printStackTrace();
    }
  }
}
