/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.CargoInfo;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.common.generated.VesselInfoServiceGrpc;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.CrewRank;
import com.cpdss.gateway.domain.CrewRankResponse;
import com.cpdss.gateway.domain.Vessel;
import com.cpdss.gateway.domain.crewmaster.CrewDetailed;
import com.cpdss.gateway.domain.crewmaster.CrewDetailedResponse;
import com.cpdss.gateway.domain.crewmaster.CrewVesselMapping;
import com.cpdss.gateway.domain.crewmaster.CrewsDetailedResponse;
import com.google.protobuf.Empty;
import io.micrometer.core.instrument.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.apache.commons.collections.CollectionUtils;
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

  public CrewsDetailedResponse getCrewDetails(
      int page,
      int pageSize,
      String sortBy,
      String orderBy,
      Map<String, String> params,
      String correlationIdHeader)
      throws GenericServiceException {
    CrewsDetailedResponse crewDetailedResponse = new CrewsDetailedResponse();
    VesselInfo.VesselsInfoRequest.Builder crewDetailsBuilder =
        VesselInfo.VesselsInfoRequest.newBuilder();
    String vesselName = "";
    crewDetailsBuilder.setPageNo(page);
    crewDetailsBuilder.setPageSize(pageSize);
    crewDetailsBuilder.setSortBy(sortBy);
    crewDetailsBuilder.setOrderBy(orderBy);
    for (Map.Entry<String, String> entry : params.entrySet()) {
      String key = entry.getKey();
      String value = entry.getValue();
      String keyMapped = "";
      if ("vesselName".equals(key)) {
        vesselName = value;
        keyMapped = key;
      } else if ("crewRank".equals(key)) {
        keyMapped = "rankName";
      } else {
        keyMapped = key;
      }
      crewDetailsBuilder.addParam(
          CargoInfo.Param.newBuilder().setKey(keyMapped).setValue(value).build());
    }

    List<VesselInfo.CrewVesselMappingDetail> crewVesselMappingDetails =
        getAllCrewVesselMappings(vesselName);
    if (!StringUtils.isEmpty(vesselName)) {
      // if No vessel is found while vessel filtering, sending empty response
      if (CollectionUtils.isEmpty(crewVesselMappingDetails)) {
        CommonSuccessResponse commonSuccessResponse = new CommonSuccessResponse();
        commonSuccessResponse.setStatus(String.valueOf(HttpStatus.OK.value()));
        commonSuccessResponse.setCorrelationId(correlationIdHeader);
        crewDetailedResponse.setResponseStatus(commonSuccessResponse);
        crewDetailedResponse.setTotalElements(Long.valueOf("0"));
        crewDetailedResponse.setCrewDetails(new ArrayList<CrewDetailed>());
        return crewDetailedResponse;
      }
      List<Long> crewXidList =
          crewVesselMappingDetails.stream()
              .map(VesselInfo.CrewVesselMappingDetail::getCrewId)
              .collect(Collectors.toList());
      crewDetailsBuilder.addAllCrewXIds(crewXidList);
    }
    VesselInfo.CrewDetailedReply crewReply =
        this.vesselInfoServiceBlockingStub.getAllCrewDetails(crewDetailsBuilder.build());

    if (crewReply == null
        || !(SUCCESS.equalsIgnoreCase(crewReply.getResponseStatus().getStatus()))) {
      log.info("Failed to get crew list");
      throw new GenericServiceException(
          "Error in calling crew list service",
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    buildCrewDetailResponse(crewDetailedResponse, crewReply, crewVesselMappingDetails);
    CommonSuccessResponse commonSuccessResponse = new CommonSuccessResponse();
    commonSuccessResponse.setStatus(String.valueOf(HttpStatus.OK.value()));
    commonSuccessResponse.setCorrelationId(correlationIdHeader);
    crewDetailedResponse.setResponseStatus(commonSuccessResponse);
    crewDetailedResponse.setTotalElements(crewReply.getTotalElements());

    return crewDetailedResponse;
  }

  private List<VesselInfo.CrewVesselMappingDetail> getAllCrewVesselMappings(String vesselName)
      throws GenericServiceException {
    VesselInfo.CrewVesselRequest.Builder crewVesselRequestBuilder =
        VesselInfo.CrewVesselRequest.newBuilder();
    if (vesselName != null) crewVesselRequestBuilder.setVesselName(vesselName);
    VesselInfo.CrewVesselReply crewVesselReply =
        this.vesselInfoServiceBlockingStub.getAllCrewVesselMapping(
            crewVesselRequestBuilder.build());
    if (!SUCCESS.equalsIgnoreCase(crewVesselReply.getResponseStatus().getStatus())) {
      log.info("Error in retrieving all crewvessel mappings");
      throw new GenericServiceException(
          "Error in retrieving all crewvessel mappings",
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    return crewVesselReply.getVesselsList();
  }

  private void buildCrewDetailResponse(
      CrewsDetailedResponse crewDetailedResponse,
      VesselInfo.CrewDetailedReply crewReply,
      List<VesselInfo.CrewVesselMappingDetail> crewVesselMappingDetails) {
    List<CrewDetailed> crewDetails = new ArrayList<>();
    crewReply
        .getCrewDetailsList()
        .forEach(
            crewDetailed -> {
              CrewDetailed eachCrew = new CrewDetailed();
              eachCrew.setId(crewDetailed.getId());
              eachCrew.setCrewName(crewDetailed.getCrewName());
              eachCrew.setCrewRank(crewDetailed.getCrewRank());
              eachCrew.setCrewRankId(crewDetailed.getCrewRankId());
              List<VesselInfo.CrewVesselMappingDetail> crewVesselMappingDetailList =
                  crewVesselMappingDetails.stream()
                      .filter(
                          crewVesselMappingDetail ->
                              crewVesselMappingDetail.getCrewId() == crewDetailed.getId())
                      .collect(Collectors.toList());
              List<CrewVesselMapping> crewVesselMappings =
                  this.buildCrewVesselMapping(crewVesselMappingDetailList);
              eachCrew.setVesselInformation(crewVesselMappings);
              crewDetails.add(eachCrew);
            });
    crewDetailedResponse.setCrewDetails(crewDetails);
    crewDetailedResponse.setTotalElements(crewReply.getTotalElements());
  }

  private List<CrewVesselMapping> buildCrewVesselMapping(
      List<VesselInfo.CrewVesselMappingDetail> crewVesselMappingDetailList) {
    List<CrewVesselMapping> crewVesselMappings = new ArrayList<>();
    crewVesselMappingDetailList.stream()
        .forEach(
            crewVesselMappingDetail -> {
              CrewVesselMapping crewVesselMapping = new CrewVesselMapping();
              crewVesselMapping.setId(crewVesselMappingDetail.getId());
              Vessel vessel = new Vessel();
              vessel.setId(crewVesselMappingDetail.getVesselId());
              vessel.setName(crewVesselMappingDetail.getVesselName());
              crewVesselMapping.setVessel(vessel);
              crewVesselMappings.add(crewVesselMapping);
            });
    return crewVesselMappings;
  }

  public CrewDetailedResponse saveCrewDetails(
      String correlationIdHeader, Long crewId, CrewDetailed crewDetailed)
      throws GenericServiceException {
    CrewDetailedResponse crewDetailedResponse = new CrewDetailedResponse();
    VesselInfo.CrewDetailed.Builder crewDetailsRequest = VesselInfo.CrewDetailed.newBuilder();
    crewDetailsRequest.setId(crewId);
    crewDetailsRequest.setCrewName(crewDetailed.getCrewName());
    crewDetailsRequest.setCrewRank(crewDetailed.getCrewRank());
    crewDetailsRequest.setCrewRankId(crewDetailed.getCrewRankId());
    VesselInfo.CrewDetailsReply crewDetailsReply =
        this.vesselInfoServiceBlockingStub.saveCrewDetails(crewDetailsRequest.build());
    if (crewDetailsReply == null
        || !(SUCCESS.equalsIgnoreCase(crewDetailsReply.getResponseStatus().getStatus()))) {
      log.error("Failed to save crew!");
      throw new GenericServiceException(
          "Failed to save crew!",
          crewDetailsReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(crewDetailsReply.getResponseStatus().getHttpStatusCode()));
    }
    VesselInfo.CrewVesselMappingRequest.Builder crewVesselMappingRequest =
        VesselInfo.CrewVesselMappingRequest.newBuilder();
    buildCrewVesselMapping(crewDetailsReply, crewDetailed, crewVesselMappingRequest);
    VesselInfo.CrewVesselReply crewVesselReply =
        this.vesselInfoServiceBlockingStub.saveCrewVesselMappings(crewVesselMappingRequest.build());
    if (crewVesselReply == null
        || !(SUCCESS.equalsIgnoreCase(crewVesselReply.getResponseStatus().getStatus()))) {
      log.error("Failed to save Crew Vessel Mapping!");
      throw new GenericServiceException(
          "Failed to save Crew Vessel Mapping!",
          crewVesselReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(crewVesselReply.getResponseStatus().getHttpStatusCode()));
    }
    CommonSuccessResponse commonSuccessResponse = new CommonSuccessResponse();
    commonSuccessResponse.setStatus(String.valueOf(HttpStatus.OK.value()));
    commonSuccessResponse.setCorrelationId(correlationIdHeader);
    crewDetailedResponse.setResponseStatus(commonSuccessResponse);
    return crewDetailedResponse;
  }

  private void buildCrewVesselMapping(
      VesselInfo.CrewDetailsReply crewDetailsReply,
      CrewDetailed crewDetailed,
      VesselInfo.CrewVesselMappingRequest.Builder crewVesselMappingRequest) {
    Optional.ofNullable(crewDetailed.getVesselInformation())
        .ifPresent(
            crewVesselMappings -> {
              crewVesselMappings.forEach(
                  crewVesselMapping -> {
                    if (crewVesselMapping.getVessel() != null) {
                      VesselInfo.CrewVesselMappingDetail.Builder crewVesselMappingDetail =
                          VesselInfo.CrewVesselMappingDetail.newBuilder();
                      crewVesselMappingDetail.setVesselId(crewVesselMapping.getVessel().getId());
                      crewVesselMappingDetail.setCrewId(crewDetailsReply.getCrewDetails().getId());
                      crewVesselMappingRequest.addCrewVesselMappings(crewVesselMappingDetail);
                    }
                  });
            });
  }

  /**
   * To get the crew details based on the vessel and rank
   *
   * @param vesselId
   * @param rankId
   * @param rankName
   * @param correlationIdHeader
   * @return
   * @throws GenericServiceException
   */
  public CrewsDetailedResponse getCrewDetailsByRank(
      Long vesselId, Long rankId, String rankName, String correlationIdHeader)
      throws GenericServiceException {
    CrewsDetailedResponse crewDetailedResponse = new CrewsDetailedResponse();
    VesselInfo.VesselsInfoRequest.Builder crewDetailsBuilder =
        VesselInfo.VesselsInfoRequest.newBuilder();
    if (vesselId != null) crewDetailsBuilder.setVesselId(vesselId);
    if (rankId != null) crewDetailsBuilder.setRankId(rankId);
    if (rankName != null) crewDetailsBuilder.setRankName(rankName);

    VesselInfo.CrewDetailedReply crewReply =
        this.vesselInfoServiceBlockingStub.getAllCrewDetailsByRank(crewDetailsBuilder.build());

    if (crewReply == null
        || !(SUCCESS.equalsIgnoreCase(crewReply.getResponseStatus().getStatus()))) {
      log.info("Failed to get crew list");
      throw new GenericServiceException(
          "Error in calling crew list service",
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    buildCrewDetailResponse(crewDetailedResponse, crewReply, new ArrayList<>());
    CommonSuccessResponse commonSuccessResponse = new CommonSuccessResponse();
    commonSuccessResponse.setStatus(String.valueOf(HttpStatus.OK.value()));
    commonSuccessResponse.setCorrelationId(correlationIdHeader);
    crewDetailedResponse.setResponseStatus(commonSuccessResponse);
    crewDetailedResponse.setTotalElements(crewReply.getTotalElements());

    return crewDetailedResponse;
  }
}
