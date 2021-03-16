/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.redis;

import com.cpdss.common.generated.VesselInfoServiceGrpc;
import com.cpdss.common.redis.CommonKeyValueStore;
import com.cpdss.gateway.domain.redis.VesselInfo;
import java.util.ArrayList;
import java.util.List;
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
}
