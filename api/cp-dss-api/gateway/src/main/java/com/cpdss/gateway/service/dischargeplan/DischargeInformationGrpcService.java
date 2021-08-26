/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.dischargeplan;

import static com.cpdss.gateway.common.GatewayConstants.SUCCESS;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.discharge_plan.DischargeInformation;
import com.cpdss.common.generated.discharge_plan.DischargeInformationRequest;
import com.cpdss.common.generated.discharge_plan.DischargeInformationServiceGrpc;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DischargeInformationGrpcService {

  @GrpcClient("dischargeInformationService")
  DischargeInformationServiceGrpc.DischargeInformationServiceBlockingStub dischargeInfoServiceStub;

  public DischargeInformation getDischargeInfoRpc(Long vesselId, Long voyageId, Long portRotationId)
      throws GenericServiceException {

    DischargeInformationRequest.Builder reqBuilder = DischargeInformationRequest.newBuilder();
    DischargeInformation rpcReplay =
        this.dischargeInfoServiceStub.getDischargeInformation(reqBuilder.build());
    if (!rpcReplay.getResponseStatus().getStatus().equals(SUCCESS)) {
      log.error(
          "Failed to get discharge plan from discharge-plan Service, vessel id {}, voyage id {}, port r Id {}",
          vesselId,
          voyageId,
          portRotationId);
      throw new GenericServiceException(
          "Failed to fetch discharge plan",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    return rpcReplay;
  }
}
