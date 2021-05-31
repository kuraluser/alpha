/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.impl;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail;
import com.cpdss.loadingplan.entity.*;
import com.cpdss.loadingplan.repository.*;
import com.cpdss.loadingplan.service.LoadingInformationBuilderService;
import com.cpdss.loadingplan.service.LoadingInformationService;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

  @Autowired StageOffsetRepository stageOffsetRepository;

  @Autowired StageDurationRepository stageDurationRepository;

  @Autowired ReasonForDelayRepository reasonForDelayRepository;

  @Autowired LoadingDelayRepository loadingDelayRepository;

  @Override
  public LoadingInformation saveLoadingInformation(
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

    // Stage Min Amount Master
    List<StageOffset> list3 = this.stageOffsetRepository.findAll();

    // Stage Duration Master
    List<StageDuration> list4 = this.stageDurationRepository.findAll();

    // Staging User data and Master data
    LoadingPlanModels.LoadingStages loadingStages =
        this.informationBuilderService.buildLoadingStageMessage(var1.get(), list3, list4);

    // Loading Delay
    List<ReasonForDelay> list5 = this.reasonForDelayRepository.findAll();
    List<LoadingDelay> list6 =
        this.loadingDelayRepository.findAllByLoadingInformationAndIsActiveTrue(var1.get());
    LoadingPlanModels.LoadingDelay loadingDelay =
        this.informationBuilderService.buildLoadingDelayMessage(list5, list6);

    List<CargoToppingOffSequence> list8 =
        this.cargoToppingOffSequenceRepository.findAllByLoadingInformationAndIsActiveTrue(
            var1.get());
    List<LoadingPlanModels.LoadingToppingOff> toppingOff =
        this.informationBuilderService.buildToppingOffMessage(list8);

    builder.setLoadingDetail(details);
    builder.setLoadingRate(rates);
    builder.addAllLoadingBerths(berths);
    builder.addAllLoadingMachines(machines);
    builder.setLoadingStage(loadingStages);
    builder.setLoadingDelays(loadingDelay);
    builder.addAllToppingOffSequence(toppingOff);
    return builder.build();
  }
}
