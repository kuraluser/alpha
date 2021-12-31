/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service;

import static org.mockito.Mockito.doNothing;

import com.cpdss.gateway.service.redis.CargoRedisSyncService;
import com.cpdss.gateway.service.redis.PortRedisSyncService;
import com.cpdss.gateway.service.redis.VesselRedisSyncService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = {SyncRedisMasterService.class})
public class SyncRedisMasterServiceTest {
  @Autowired SyncRedisMasterService syncRedisMasterService;

  @MockBean CargoRedisSyncService cargoRedisSyncService;

  @MockBean PortRedisSyncService portRedisSyncService;

  @MockBean VesselRedisSyncService vesselRedisSyncService;

  @Test
  void testUpdateRedisFromMasterData() {
    doNothing().when(cargoRedisSyncService).fetchDomainDataFromService();
    doNothing().when(portRedisSyncService).fetchDomainDataFromService();
    doNothing().when(vesselRedisSyncService).fetchDomainDataFromService();
    this.syncRedisMasterService.updateRedisFromMasterData();
  }
}
