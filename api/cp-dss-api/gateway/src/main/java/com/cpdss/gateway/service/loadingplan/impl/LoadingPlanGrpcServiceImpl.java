/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.loadingplan.impl;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.PortRotation;
import com.cpdss.gateway.domain.voyage.VoyageResponse;
import com.cpdss.gateway.service.loadingplan.LoadingPlanGrpcService;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/** Calls to Grpc Service Parser here and give back to caller. */
@Slf4j
@Service
public class LoadingPlanGrpcServiceImpl implements LoadingPlanGrpcService {

  @GrpcClient("loadableStudyService")
  private LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub
      loadableStudyServiceBlockingStub;

  @GrpcClient("cargoInfoService")
  private CargoInfoServiceGrpc.CargoInfoServiceBlockingStub cargoInfoServiceBlockingStub;

  @GrpcClient("portInfoService")
  private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoServiceBlockingStub;

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @Override
  public VoyageResponse getActiveVoyageDetails(Long vesselId) throws GenericServiceException {
    LoadableStudy.ActiveVoyage activeVoyage =
        loadableStudyServiceBlockingStub.getActiveVoyagesByVessel(
            this.buildVoyageRequest(vesselId));
    if (!activeVoyage.getResponseStatus().getStatus().equalsIgnoreCase("SUCCESS")) {
      log.error("Failed to collect Active Voyage Data, Vessel Id {}", vesselId);
      throw new GenericServiceException(
          "Failed to get Active Voyage",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    if (activeVoyage.getId() <= 0) {
      log.error("Failed to collect Active Voyage Data, Vessel Id {}", vesselId);
      throw new GenericServiceException(
          "No Active Voyage Exist",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    VoyageResponse voyageResponse = new VoyageResponse();
    BeanUtils.copyProperties(activeVoyage, voyageResponse);
    List<PortRotation> rotationDomain = new ArrayList<>();
    if (activeVoyage.getPortRotationCount() > 0) {
      for (LoadableStudy.PortRotationDetail pr : activeVoyage.getPortRotationList()) {
        PortRotation prObj = new PortRotation();
        BeanUtils.copyProperties(pr, prObj);
        rotationDomain.add(prObj);
      }
    }
    voyageResponse.setPortRotations(rotationDomain);
    if (activeVoyage.getConfirmedLoadableStudy() != null
        && activeVoyage.getConfirmedLoadableStudy().getId() > 0) {
      com.cpdss.gateway.domain.LoadableStudy loadableStudy =
          new com.cpdss.gateway.domain.LoadableStudy();
      BeanUtils.copyProperties(activeVoyage.getConfirmedLoadableStudy(), loadableStudy);
      voyageResponse.setActiveLs(loadableStudy);
    }

    return voyageResponse;
  }

  @Override
  public Object getPortRotationDetailsForActiveVoyage(Long vesselId) {
    return null;
  }

  public LoadableStudy.VoyageRequest buildVoyageRequest(Long vesselId) {
    LoadableStudy.VoyageRequest.Builder builder = LoadableStudy.VoyageRequest.newBuilder();
    builder.setVesselId(vesselId);
    return builder.build();
  }
}
