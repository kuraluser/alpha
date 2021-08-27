/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.loadicator;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.CargoInfo;
import com.cpdss.common.generated.Loadicator;
import com.cpdss.common.generated.Loadicator.StowagePlan;
import com.cpdss.common.generated.PortInfo;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.UllageBillRequest;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.repository.LoadingInformationRepository;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** @author pranav.k */
@Slf4j
@Service
@Transactional
public class UllageUpdateLoadicatorService {

  @Value(value = "${algo.loadicatorUrl}")
  private String loadicatorUrl;

  @Value(value = "${loadingplan.attachment.rootFolder}")
  private String rootFolder;

  @Autowired LoadingInformationRepository loadingInformationRepository;

  @Autowired LoadicatorService loadicatorService;

  public void saveLoadicatorInfoForUllageUpdate(UllageBillRequest request)
      throws GenericServiceException {
    Loadicator.LoadicatorRequest.Builder loadicatorRequestBuilder =
        Loadicator.LoadicatorRequest.newBuilder();
    Optional<LoadingInformation> loadingInfoOpt =
        loadingInformationRepository.findByIdAndIsActiveTrue(
            request.getUpdateUllage(0).getLoadingInformationId());
    if (loadingInfoOpt.isEmpty()) {
      log.info(
          "Cannot find loading information with id {}",
          request.getUpdateUllage(0).getLoadingInformationId());
      throw new GenericServiceException(
          "Could not find loading information "
              + request.getUpdateUllage(0).getLoadingInformationId(),
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    CargoInfo.CargoReply cargoReply =
        loadicatorService.getCargoInfoForLoadicator(loadingInfoOpt.get());
    VesselInfo.VesselReply vesselReply =
        loadicatorService.getVesselDetailsForLoadicator(loadingInfoOpt.get());
    PortInfo.PortReply portReply = loadicatorService.getPortInfoForLoadicator(loadingInfoOpt.get());

    StowagePlan.Builder stowagePlanBuilder = StowagePlan.newBuilder();
    loadicatorService.buildStowagePlan(
        loadingInfoOpt.get(), 0, "", cargoReply, vesselReply, portReply, stowagePlanBuilder);
  }
}
