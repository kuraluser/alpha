/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.redis;

import com.cpdss.common.generated.CargoInfo.CargoReply;
import com.cpdss.common.generated.CargoInfo.CargoRequestWithPaging;
import com.cpdss.common.generated.CargoInfoServiceGrpc;
import com.cpdss.common.redis.CommonKeyValueStore;
import com.cpdss.gateway.domain.redis.CargoInfo;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

/**
 * Grpc Call to cargo service, to fetch cargo details
 *
 * @author johnsoorajxavier
 * @since 15-03-2021
 */
@Slf4j
@Service
public class CargoRedisSyncService extends CommonKeyValueStore<CargoInfo>
    implements RedisMasterSyncService {

  @GrpcClient("cargoInfoService")
  private CargoInfoServiceGrpc.CargoInfoServiceBlockingStub cargoInfoServiceBlockingStub;

  @Override
  public void fetchDomainDataFromService() {
    List<CargoInfo.CargoInfoDomain> domains = new ArrayList<>();
    CargoRequestWithPaging request =
        CargoRequestWithPaging.newBuilder().setOffset(OFFSET_VAL).setLimit(PAGE_COUNT).build();
    CargoReply cargoReply = cargoInfoServiceBlockingStub.getCargoInfoByPaging(request);
    log.info("Fetch Cargo from cargo-info service", cargoReply.hasResponseStatus());
    if (!cargoReply.getCargosList().isEmpty()) {
      cargoReply
          .getCargosList()
          .forEach(
              var1 -> {
                CargoInfo.CargoInfoDomain domain = new CargoInfo.CargoInfoDomain();
                domain.setName(var1.getCrudeType());
                domains.add(domain);
              });
    }
    this.storeData(CARGO_MASTER_KEY, new CargoInfo(domains));
    log.info("Cargo data added to Redis Cache, size {}", domains.size());
  }
}
