/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.redis;

import com.cpdss.common.generated.PortInfo.PortReply;
import com.cpdss.common.generated.PortInfo.PortRequestWithPaging;
import com.cpdss.common.generated.PortInfoServiceGrpc;
import com.cpdss.common.redis.CommonKeyValueStore;
import com.cpdss.gateway.domain.redis.PortInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PortRedisSyncService extends CommonKeyValueStore<PortInfo>
    implements RedisMasterSyncService {

  @GrpcClient("portInfoService")
  private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoServiceBlockingStub;

  @Override
  public void fetchDomainDataFromService() {
    PortRequestWithPaging request =
        PortRequestWithPaging.newBuilder().setOffset(OFFSET_VAL).setLimit(PAGE_COUNT).build();
    PortReply reply = portInfoServiceBlockingStub.getPortInfoByPaging(request);
    List<PortInfo.PortInfoDomain> portInfoDomains = new ArrayList<>();
    if (!reply.getPortsList().isEmpty()) {
      reply
          .getPortsList()
          .forEach(
              var1 -> {
                PortInfo.PortInfoDomain domain = new PortInfo.PortInfoDomain();
                domain.setId(var1.getId());
                domain.setName(var1.getName());
                portInfoDomains.add(domain);
              });
    }
    this.storeData(PORT_MASTER_KEY, new PortInfo(portInfoDomains));
    log.info("Port data added to Redis Cache, size {}", portInfoDomains.size());
  }

  @Override
  public Map<Long, String> fetchAllIdAndName() {
    PortInfo redisData = this.getData(PORT_MASTER_KEY);
    if (redisData != null) {
      log.info("Port Redis data size {}", redisData.getPorts().size());
      return redisData.getPorts().stream()
          .collect(Collectors.toMap(map -> map.getId(), map -> map.getName()));
    }
    log.info("No Port data found in Redis Cache");
    return null;
  }

  @Override
  public Map<Long, String> filterByName(String val) {
    val.toLowerCase();
    PortInfo redisData = this.getData(PORT_MASTER_KEY);
    if (redisData != null) {
      Map<Long, String> matchRes =
          redisData.getPorts().stream()
              .filter(var1 -> var1.getName().toLowerCase().matches("(?i).*" + val + ".*"))
              .collect(Collectors.toMap(map -> map.getId(), map -> map.getName()));
      log.info("Port Redis data total {}, match {}", redisData.getPorts().size(), matchRes.size());
      return matchRes;
    }
    log.info("No Port data found in Redis Cache");
    return null;
  }
}
