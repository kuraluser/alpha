/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.redis;

import java.util.Map;

public interface RedisMasterSyncService {

  Integer OFFSET_VAL = 0;
  Integer PAGE_COUNT = 1500;
  String PORT_MASTER_KEY = "port_master";
  String VESSEL_MASTER_KEY = "vessel_master";
  String CARGO_MASTER_KEY = "cargo_master";

  void fetchDomainDataFromService();

  Map<Long, String> fetchAllIdAndName();

  Map<Long, String> filterByName(String var1);
}
