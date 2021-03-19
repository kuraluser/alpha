/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.redis;

import com.cpdss.common.generated.VesselInfoServiceGrpc;
import com.cpdss.common.redis.CommonKeyValueStore;
import com.cpdss.gateway.domain.redis.VesselInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class VesselRedisSyncService extends CommonKeyValueStore<VesselInfo>
    implements RedisMasterSyncService {

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @Override
  public void fetchDomainDataFromService() {

    com.cpdss.common.generated.VesselInfo.VesselRequestWithPaging request =
        com.cpdss.common.generated.VesselInfo.VesselRequestWithPaging.newBuilder()
            .setOffset(OFFSET_VAL)
            .setLimit(PAGE_COUNT)
            .build();
    com.cpdss.common.generated.VesselInfo.VesselReply reply =
        vesselInfoGrpcService.getVesselInfoByPaging(request);
    List<VesselInfo.VesselInfoDomain> vesselDomain = new ArrayList<>();
    if (!reply.getVesselsList().isEmpty()) {
      reply
          .getVesselsList()
          .forEach(
              var1 -> {
                VesselInfo.VesselInfoDomain domain = new VesselInfo.VesselInfoDomain();
                domain.setId(var1.getId());
                domain.setName(var1.getName());
                vesselDomain.add(domain);
              });
    }
    this.storeData(VESSEL_MASTER_KEY, new VesselInfo(vesselDomain));
    log.info("Vessel data added to Redis Cache, size {}", vesselDomain.size());
  }

  @Override
  public Map<Long, String> fetchAllIdAndName() {
    VesselInfo redisData = this.getData(VESSEL_MASTER_KEY);
    if (redisData != null) {
      log.info("Vessel Redis data size {}", redisData.getVessels().size());
      return redisData.getVessels().stream()
          .collect(Collectors.toMap(map -> map.getId(), map -> map.getName()));
    }
    log.info("No Vessel data found in Redis Cache");
    return null;
  }

  @Override
  public Map<Long, String> filterByName(String val) {
    val.toLowerCase();
    VesselInfo redisData = this.getData(VESSEL_MASTER_KEY);
    if (redisData != null) {
      Map<Long, String> matchRes =
          redisData.getVessels().stream()
              .filter(var1 -> var1.getName().toLowerCase().matches("(?i).*" + val + ".*"))
              .collect(Collectors.toMap(map -> map.getId(), map -> map.getName()));
      log.info(
          "Vessel Redis data total {}, match {}", redisData.getVessels().size(), matchRes.size());
      return matchRes;
    }
    log.info("No Vessel data found in Redis Cache");
    return null;
  }
}
