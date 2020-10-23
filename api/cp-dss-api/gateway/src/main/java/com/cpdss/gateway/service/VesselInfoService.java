/* Licensed under Apache-2.0 */
package com.cpdss.gateway.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.VesselInfo.LoadLineDetail;
import com.cpdss.common.generated.VesselInfo.VesselDetail;
import com.cpdss.common.generated.VesselInfo.VesselReply;
import com.cpdss.common.generated.VesselInfo.VesselRequest;
import com.cpdss.common.generated.VesselInfoServiceGrpc.VesselInfoServiceBlockingStub;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.LoadLine;
import com.cpdss.gateway.domain.Vessel;
import com.cpdss.gateway.domain.VesselResponse;
import com.cpdss.gateway.entity.Users;
import com.cpdss.gateway.repository.UsersRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * Service class for vessel related operations
 *
 * @author suhail.k
 */
@Service
@Log4j2
public class VesselInfoService {

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @Autowired private UsersRepository usersRepository;

  private static final String SUCCESS = "SUCCESS";

  /**
   * Get vessels by company
   *
   * @param companyId
   * @param correlationId
   * @return
   * @throws GenericServiceException
   */
  public VesselResponse getVesselsByCompany(Long companyId, String correlationId)
      throws GenericServiceException {
    log.info("Inside getVesselsByCompany, correlationId:{}", correlationId);
    VesselReply reply =
        this.getVesselsByCompany(VesselRequest.newBuilder().setCompanyId(companyId).build());

    if (!SUCCESS.equals(reply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "failed to fetch vessels",
          reply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(reply.getResponseStatus().getCode())));
    }
    VesselResponse response = this.createVesselResponse(reply);
    response.setResponseStatus(
        new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
    return response;
  }

  /**
   * Create vessel response from grpc reply
   *
   * @param reply
   * @return
   * @throws GenericServiceException
   */
  private VesselResponse createVesselResponse(VesselReply reply) throws GenericServiceException {
    VesselResponse response = new VesselResponse();
    response.setVessels(new ArrayList<>());
    Set<Long> userIdList =
        reply.getVesselsList().stream()
            .map(VesselDetail::getCheifOfficerId)
            .collect(Collectors.toSet());
    userIdList.addAll(
        reply.getVesselsList().stream()
            .map(VesselDetail::getCaptainId)
            .collect(Collectors.toSet()));
    List<Users> userList = this.usersRepository.findByIdIn(new ArrayList<>(userIdList));
    for (VesselDetail grpcReply : reply.getVesselsList()) {
      Vessel vessel = new Vessel();
      vessel.setId(grpcReply.getId());
      vessel.setName(grpcReply.getName());
      vessel.setImoNumber(grpcReply.getImoNumber());
      vessel.setFlagPath(grpcReply.getFlag());
      vessel.setCaptainId(grpcReply.getCaptainId());
      vessel.setChiefOfficerId(grpcReply.getCheifOfficerId());
      Optional<Users> userOpt =
          userList.stream().filter(item -> item.getId().equals(vessel.getCaptainId())).findAny();
      if (!userOpt.isPresent()) {
        throw new GenericServiceException(
            "Captain info not found in database",
            CommonErrorCodes.E_GEN_INTERNAL_ERR,
            HttpStatusCode.INTERNAL_SERVER_ERROR);
      }
      vessel.setCaptainName(userOpt.get().getFirstName() + " " + userOpt.get().getLastName());
      userOpt =
          userList.stream()
              .filter(item -> item.getId().equals(vessel.getChiefOfficerId()))
              .findAny();
      if (!userOpt.isPresent()) {
        throw new GenericServiceException(
            "Chief officer info not found in database",
            CommonErrorCodes.E_GEN_INTERNAL_ERR,
            HttpStatusCode.INTERNAL_SERVER_ERROR);
      }
      vessel.setChiefOfficerName(userOpt.get().getFirstName() + " " + userOpt.get().getLastName());
      vessel.setLoadlines(new ArrayList<>());
      for (LoadLineDetail detail : grpcReply.getLoadLinesList()) {
        LoadLine loadLine = new LoadLine();
        loadLine.setId(detail.getId());
        loadLine.setName(detail.getName());
        loadLine.setDraftMarks(
            detail.getDraftMarksList().stream().map(BigDecimal::new).collect(Collectors.toList()));
        vessel.getLoadlines().add(loadLine);
      }
      response.getVessels().add(vessel);
    }
    return response;
  }

  /**
   * Call grpc service for vessel info
   *
   * @param request
   * @return
   */
  public VesselReply getVesselsByCompany(VesselRequest request) {
    return this.vesselInfoGrpcService.getAllVesselsByCompany(request);
  }
}
