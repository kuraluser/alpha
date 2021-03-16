/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.redis;

import com.cpdss.common.generated.PortInfo.PortReply;
import com.cpdss.common.generated.PortInfo.PortRequestWithPaging;
import com.cpdss.common.generated.PortInfoServiceGrpc;
import com.cpdss.common.redis.CommonKeyValueStore;
import com.cpdss.gateway.domain.redis.PortInfo;
import java.util.ArrayList;
import java.util.List;
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
                domain.setName(var1.getName());
                portInfoDomains.add(domain);
              });
    }
    this.storeData(PortInfo.class.getName(), new PortInfo(portInfoDomains));
    log.info("port list count -> {} ", reply.getPortsCount());
  }
}
