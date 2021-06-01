/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.impl;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail;
import com.cpdss.loadingplan.entity.LoadingBerthDetail;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.entity.LoadingMachineryInUse;
import com.cpdss.loadingplan.repository.*;
import com.cpdss.loadingplan.service.CargoToppingOffSequenceService;
import com.cpdss.loadingplan.service.LoadingBerthService;
import com.cpdss.loadingplan.service.LoadingDelayService;
import com.cpdss.loadingplan.service.LoadingInformationBuilderService;
import com.cpdss.loadingplan.service.LoadingInformationService;
import com.cpdss.loadingplan.service.LoadingMachineryInUseService;
import com.cpdss.loadingplan.service.LoadingStageService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
public class LoadingInformationServiceImpl implements LoadingInformationService {

  @Autowired LoadingInformationRepository loadingInformationRepository;
  @Autowired CargoToppingOffSequenceRepository cargoToppingOffSequenceRepository;
  @Autowired LoadablePlanBallastDetailsRepository loadablePlanBallastDetailsRepository;
  @Autowired LoadablePlanCommingleDetailsRepository loadablePlanCommingleDetailsRepository;
  @Autowired LoadablePlanQuantityRepository loadablePlanQuantityRepository;
  @Autowired LoadablePlanStowageDetailsRepository loadablePlanStowageDetailsRepository;
  @Autowired LoadingInformationBuilderService informationBuilderService;
  @Autowired LoadingBerthDetailsRepository berthDetailsRepository;
  @Autowired LoadingMachineryInUseRepository loadingMachineryInUserRepository;

  @Autowired LoadingBerthService loadingBerthService;
  @Autowired LoadingDelayService loadingDelayService;
  @Autowired LoadingMachineryInUseService loadingMachineryInUseService;
  @Autowired LoadingStageService loadingStageService;
  @Autowired CargoToppingOffSequenceService toppingOffSequenceService;

  @Override
  public LoadingInformation saveLoadingInformationDetail(
      LoadingInformationDetail loadingInformationDetail, LoadingInformation loadingInformation) {
    deleteLoadingInformationOfVessel(
        loadingInformationDetail.getVesselId(), loadingInformationDetail.getLoadablePatternId());

    Optional.ofNullable(loadingInformationDetail.getLoadablePatternId())
        .ifPresent(loadingInformation::setLoadablePatternXId);
    Optional.ofNullable(loadingInformationDetail.getPortId())
        .ifPresent(loadingInformation::setPortXId);
    Optional.ofNullable(loadingInformationDetail.getSynopticalTableId())
        .ifPresent(loadingInformation::setSynopticalTableXId);
    Optional.ofNullable(loadingInformationDetail.getVesselId())
        .ifPresent(loadingInformation::setVesselXId);
    loadingInformation.setIsActive(true);
    return loadingInformationRepository.save(loadingInformation);
  }

  private void deleteLoadingInformationOfVessel(Long vesselId, Long loadablePatternId) {

    List<LoadingInformation> loadingInformations =
        loadingInformationRepository.findByVesselXIdAndLoadablePatternXIdNotAndIsActive(
            vesselId, loadablePatternId, true);
    loadingInformations.forEach(
        loadingInformation -> {
          deleteLoadablePlanDetails(loadingInformation);
          loadingInformationRepository.deleteByLoadingInformationId(loadingInformation.getId());
        });
  }

  @Override
  public void deleteLoadablePlanDetails(LoadingInformation loadingInformation) {
    cargoToppingOffSequenceRepository.deleteByLoadingInformation(loadingInformation);
    loadablePlanBallastDetailsRepository.deleteByLoadingInformation(loadingInformation);
    loadablePlanCommingleDetailsRepository.deleteByLoadingInformation(loadingInformation);
    loadablePlanQuantityRepository.deleteByLoadingInformation(loadingInformation);
    loadablePlanStowageDetailsRepository.deleteByLoadingInformation(loadingInformation);
  }

  @Override
  public Optional<LoadingInformation> getLoadingInformation(
      Long id, Long vesselId, Long patternId) {
    Optional<LoadingInformation> information = Optional.empty();

    Optional<LoadingInformation> val1 = this.loadingInformationRepository.findById(id);

    if ((id != null && id > 0) && (vesselId != null && vesselId > 0)) {
      information =
          this.loadingInformationRepository.findByIdAndVesselXIdAndIsActiveTrue(id, vesselId);
      log.info("Loading Information found for Id {}, Vessel Id {}", id, vesselId);
    } else if ((patternId != null && patternId > 0) && (vesselId != null && vesselId > 0)) {
      information =
          this.loadingInformationRepository.findByVesselXIdAndLoadablePatternXIdAndIsActiveTrue(
              vesselId, patternId);
      log.info("Loading Information found for Vessel {}, Pattern Id {}", vesselId, patternId);
    }
    return information;
  }

  @Override
  public LoadingPlanModels.LoadingInformation getLoadingInformation(
      LoadingPlanModels.LoadingInformationRequest request,
      LoadingPlanModels.LoadingInformation.Builder builder)
      throws GenericServiceException {

    Optional<LoadingInformation> var1 =
        this.getLoadingInformation(
            request.getLoadingPlanId(), request.getVesselId(), request.getLoadingPatternId());
    if (!var1.isPresent()) {
      throw new GenericServiceException(null, null, null);
    }

    // Loading Details
    LoadingPlanModels.LoadingDetails details =
        this.informationBuilderService.buildLoadingDetailsMessage(var1.get());

    // Loading Rates
    LoadingPlanModels.LoadingRates rates =
        this.informationBuilderService.buildLoadingRateMessage(var1.get());

    // Loading Berths
    List<LoadingBerthDetail> list1 =
        this.berthDetailsRepository.findAllByLoadingInformationAndIsActiveTrue(var1.get());
    List<LoadingPlanModels.LoadingBerths> berths =
        this.informationBuilderService.buildLoadingBerthsMessage(list1);

    // Machines in use
    List<LoadingMachineryInUse> list2 =
        this.loadingMachineryInUserRepository.findAllByLoadingInformationAndIsActiveTrue(
            var1.get());
    List<LoadingPlanModels.LoadingMachinesInUse> machines =
        this.informationBuilderService.buildLoadingMachineryInUseMessage(list2);

    builder.setLoadingDetail(details);
    builder.setLoadingRate(rates);
    builder.addAllLoadingBerths(berths);
    builder.addAllLoadingMachines(machines);
    return builder.build();
  }

  @Override
  public void saveLoadingInformation(
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformation
          request)
      throws Exception {
    Optional<LoadingInformation> loadingInformationOpt =
        loadingInformationRepository.findByIdAndIsActiveTrue(
            request.getLoadingDetail().getId());
    if (loadingInformationOpt.isPresent()) {
      buildLoadingInformation(request, loadingInformationOpt.get());
      // TODO loading information details
      loadingBerthService.saveLoadingBerthList(request.getLoadingBerthsList());
      loadingDelayService.saveLoadingDelayList(request.getLoadingDelays());
      // loadingInformation.getLoadingDetail();
      // loadingInformation.getLoadingInformation();
      loadingMachineryInUseService.saveLoadingMachineryList(
          request.getLoadingMachinesList());
      // request.getLoadingRate();
      loadingStageService.saveLoadingStages(request.getLoadingStage());
      toppingOffSequenceService.saveCargoToppingOffSequences(
          request.getToppingOffSequenceList());
    } else {
    	throw new Exception("Cannot find loading information for the active voyage");
    }
  }

private void buildLoadingInformation(
		com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformation request,
		LoadingInformation loadingInformation) {
	//loadingInformation.setStartTime(request.getLoadingDetail().getStartTime());
	loadingInformation.setFinalTrim(StringUtils.isEmpty(request.getLoadingDetail().getTrimAllowed().getFinalTrim()) ? null : new BigDecimal(request.getLoadingDetail().getTrimAllowed().getFinalTrim()));
	loadingInformation.setInitialTrim(StringUtils.isEmpty(request.getLoadingDetail().getTrimAllowed().getInitialTrim()) ? null : new BigDecimal(request.getLoadingDetail().getTrimAllowed().getInitialTrim()));
	loadingInformation.setMaximumTrim(StringUtils.isEmpty(request.getLoadingDetail().getTrimAllowed().getMaximumTrim()) ? null : new BigDecimal(request.getLoadingDetail().getTrimAllowed().getMaximumTrim()));
	loadingInformation.setLineContentRemaining(StringUtils.isEmpty(request.getLoadingRate().getLineContentRemaining()) ? null : new BigDecimal(request.getLoadingRate().getLineContentRemaining()));
	loadingInformation.setMaxDeBallastRate(StringUtils.isEmpty(request.getLoadingRate().getMaxDeBallastingRate()) ? null : new BigDecimal(request.getLoadingRate().getMaxDeBallastingRate()));
	loadingInformation.setMaxLoadingRate(StringUtils.isEmpty(request.getLoadingRate().getMaxLoadingRate()) ? null : new BigDecimal(request.getLoadingRate().getMaxLoadingRate()));
	loadingInformation.setMinDeBallastRate(StringUtils.isEmpty(request.getLoadingRate().getMinDeBallastingRate()) ? null : new BigDecimal(request.getLoadingRate().getMinDeBallastingRate()));
	loadingInformation.setReducedLoadingRate(StringUtils.isEmpty(request.getLoadingRate().getReducedLoadingRate()) ? null : new BigDecimal(request.getLoadingRate().getReducedLoadingRate()));
	// minLoadingRate missing
	loadingInformation.setNoticeTimeForRateReduction(StringUtils.isEmpty(request.getLoadingRate().getNoticeTimeRateReduction()) ? null : Integer.valueOf(request.getLoadingRate().getNoticeTimeRateReduction()));
	loadingInformation.setNoticeTimeForStopLoading(StringUtils.isEmpty(request.getLoadingRate().getNoticeTimeStopLoading()) ? null : Integer.valueOf(request.getLoadingRate().getNoticeTimeStopLoading()));
}
}
