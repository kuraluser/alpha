/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadingplan.common.LoadingPlanConstants;
import com.cpdss.loadingplan.entity.*;
import com.cpdss.loadingplan.repository.*;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@Service
public class LoadingPlanService {

  @Autowired LoadingInformationService loadingInformationService;
  @Autowired CargoToppingOffSequenceService cargoToppingOffSequenceService;
  @Autowired LoadablePlanBallastDetailsService loadablePlanBallastDetailsService;
  @Autowired LoadablePlanCommingleDetailsService loadablePlanCommingleDetailsService;
  @Autowired LoadablePlanQuantityService loadablePlanQuantityService;
  @Autowired LoadablePlanStowageDetailsService loadablePlanStowageDetailsService;
  @Autowired PortLoadingPlanBallastDetailsRepository plpBallastDetailsRepository;
  @Autowired PortLoadingPlanStowageDetailsRepository plpStowageDetailsRepository;
  @Autowired PortLoadingPlanRobDetailsRepository plpRobDetailsRepository;
  @Autowired PortLoadingPlanStabilityParametersRepository plpStabilityParametersRepository;
  @Autowired LoadingInformationBuilderService informationBuilderService;
  @Autowired LoadingBerthDetailsRepository berthDetailsRepository;
  @Autowired CargoToppingOffSequenceRepository cargoToppingOffSequenceRepository;

  /**
   * @param request
   * @param builder
   */
  public void loadingPlanSynchronization(
      LoadingPlanSyncDetails request, LoadingPlanSyncReply.Builder builder) {
    try {
      LoadingInformation loadingInformation = new LoadingInformation();
      LoadingInformation savedLoadingInformation =
          loadingInformationService.saveLoadingInformationDetail(
              request.getLoadingInformationDetail(), loadingInformation);
      cargoToppingOffSequenceService.saveCargoToppingOffSequenceList(
          request.getCargoToppingOffSequencesList(), savedLoadingInformation);
      loadablePlanBallastDetailsService.saveLoadablePlanBallastDetailsList(
          request.getLoadablePlanDetailsReply().getLoadablePlanBallastDetailsList(),
          savedLoadingInformation);
      loadablePlanCommingleDetailsService.saveLoadablePlanCommingleDetailsList(
          request.getLoadablePlanDetailsReply().getLoadableQuantityCommingleCargoDetailsList(),
          savedLoadingInformation);
      loadablePlanQuantityService.saveLoadablePlanQuantyList(
          request.getLoadablePlanDetailsReply().getLoadableQuantityCargoDetailsList(),
          savedLoadingInformation);
      loadablePlanStowageDetailsService.saveLoadablePlanStowageDetailsList(
          request.getLoadablePlanDetailsReply().getLoadablePlanStowageDetailsList(),
          savedLoadingInformation);
      log.info(
          "Saved LoadingInformation on port "
              + request.getLoadingInformationDetail().getPortId()
              + " of Loadable pattern "
              + request.getLoadingInformationDetail().getLoadablePatternId());
      builder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setMessage("Successfully saved loading information in database")
              .setStatus(LoadingPlanConstants.SUCCESS)
              .build());
    } catch (Exception e) {
      log.info(
          "Failed to save LoadingInformation on port "
              + request.getLoadingInformationDetail().getPortId()
              + " of Loadable pattern "
              + request.getLoadingInformationDetail().getLoadablePatternId());
      builder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setMessage("Error occured while saving loading information in database")
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setStatus(LoadingPlanConstants.SUCCESS)
              .build());
      e.printStackTrace();
    }
  }

  public void getLoadingPlan(
      LoadingPlanModels.LoadingInformationRequest request,
      LoadingPlanModels.LoadingPlanReply.Builder masterBuilder)
      throws GenericServiceException {

    Optional<LoadingInformation> var1;
    if (request.getLoadingPlanId() > 0) {
      var1 = loadingInformationService.getLoadingInformation(request.getLoadingPlanId());
    } else {
      var1 =
          loadingInformationService.getLoadingInformation(
              0L,
              request.getVesselId(),
              request.getVoyageId(),
              request.getLoadingPatternId(),
              request.getPortRotationId());
    }

    if (var1.isPresent()) {

      // <---Loading Information Start-->
      LoadingPlanModels.LoadingInformation.Builder loadingInformation =
          LoadingPlanModels.LoadingInformation.newBuilder();

      // Loading Rate From Loading Info
      LoadingPlanModels.LoadingRates rates =
          this.informationBuilderService.buildLoadingRateMessage(var1.get());
      loadingInformation.setLoadingRate(rates);

      // Set Saved Berth Data
      List<LoadingBerthDetail> list1 =
          this.berthDetailsRepository.findAllByLoadingInformationAndIsActiveTrue(var1.orElse(null));
      List<LoadingPlanModels.LoadingBerths> berths =
          this.informationBuilderService.buildLoadingBerthsMessage(list1);
      loadingInformation.addAllLoadingBerths(berths);

      // Topping Off Sequence
      List<CargoToppingOffSequence> list2 =
          this.cargoToppingOffSequenceRepository.findAllByLoadingInformationAndIsActiveTrue(
              var1.orElse(null));
      List<LoadingPlanModels.LoadingToppingOff> toppingOff =
          this.informationBuilderService.buildToppingOffMessage(list2);
      loadingInformation.addAllToppingOffSequence(toppingOff);
      masterBuilder.setLoadingInformation(loadingInformation.build());

      // <---Loading Information End-->

      // <---Cargo Details Start-->
      List<PortLoadingPlanBallastDetails> plpBallastList =
          plpBallastDetailsRepository.findByLoadingInformationAndIsActive(var1.get(), true);
      List<PortLoadingPlanStowageDetails> plpStowageList =
          plpStowageDetailsRepository.findByLoadingInformationAndIsActive(var1.get(), true);
      List<PortLoadingPlanRobDetails> plpRobList =
          plpRobDetailsRepository.findByLoadingInformationAndIsActive(var1.get(), true);
      List<PortLoadingPlanStabilityParameters> plpStabilityList =
          plpStabilityParametersRepository.findByLoadingInformationAndIsActive(var1.get(), true);

      masterBuilder.addAllPortLoadingPlanBallastDetails(
          this.informationBuilderService.buildLoadingPlanTankBallastMessage(plpBallastList));
      masterBuilder.addAllPortLoadingPlanStowageDetails(
          this.informationBuilderService.buildLoadingPlanTankStowageMessage(plpStowageList));
      masterBuilder.addAllPortLoadingPlanRobDetails(
          this.informationBuilderService.buildLoadingPlanTankRobMessage(plpRobList));
      masterBuilder.addAllPortLoadingPlanStabilityParameters(
          this.informationBuilderService.buildLoadingPlanTankStabilityMessage(plpStabilityList));
      // <---Loading Information End-->
    } else {
      log.error("Failed to fetch Loading Plan, Loading info Id is 0");
      throw new GenericServiceException(
          "Loading Info Id Is 0", CommonErrorCodes.E_HTTP_BAD_REQUEST, HttpStatusCode.BAD_REQUEST);
    }
    masterBuilder.setResponseStatus(
        ResponseStatus.newBuilder().setStatus(LoadingPlanConstants.SUCCESS).build());
    masterBuilder.build();
  }
}
