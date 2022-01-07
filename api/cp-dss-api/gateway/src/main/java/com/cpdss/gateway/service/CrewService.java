/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.common.generated.VesselInfoServiceGrpc;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.CrewRank;
import com.cpdss.gateway.domain.CrewRankResponse;
import com.google.protobuf.Empty;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class CrewService {
  private static final String SUCCESS = "SUCCESS";

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoServiceBlockingStub;

  /**
   * To get active crew ranks
   *
   * @param correlationId
   * @return
   * @throws GenericServiceException
   */
  public CrewRankResponse getCrewRanks(String correlationId) throws GenericServiceException {
    CrewRankResponse crewRankResponse = new CrewRankResponse();
    com.google.protobuf.Empty.Builder builder = Empty.newBuilder();
    VesselInfo.CrewReply crewReply =
        this.vesselInfoServiceBlockingStub.getAllCrewRank(builder.build());
    if (!SUCCESS.equals(crewReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "failed to get crew ranks ",
          crewReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(crewReply.getResponseStatus().getCode())));
    }
    List<CrewRank> responseRankList = new ArrayList<>();
    List<VesselInfo.CrewRank> crewRankList = crewReply.getCrewRanksList();
    crewRankList.forEach(
        rank -> {
          CrewRank crewRank = new CrewRank();
          crewRank.setId(rank.getId());
          crewRank.setRankName(rank.getCrewName());
          crewRank.setRankShortName(rank.getRankShortName());
          responseRankList.add(crewRank);
        });
    crewRankResponse.setCrewRankList(responseRankList);
    crewRankResponse.setResponseStatus(
        new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));

    return crewRankResponse;
  }
}
