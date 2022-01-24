/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service;

import static com.cpdss.loadingplan.common.LoadingPlanConstants.TIME_FORMATTER;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.*;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingDelay;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanCommingleDetails;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadingplan.entity.*;
import com.cpdss.loadingplan.entity.CargoToppingOffSequence;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.entity.PortLoadingPlanBallastDetails;
import com.cpdss.loadingplan.entity.PortLoadingPlanRobDetails;
import com.cpdss.loadingplan.repository.LoadingDelayReasonRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoadingInformationBuilderService {

  @Autowired LoadingDelayReasonRepository loadingDelayReasonRepository;

  public LoadingDetails buildLoadingDetailsMessage(LoadingInformation var1) {
    LoadingDetails.Builder builder = LoadingDetails.newBuilder();
    if (var1 != null) {
      Optional.of(var1.getId()).ifPresent(builder::setId);
      Optional.ofNullable(var1.getSunriseTime())
          .ifPresent(v -> builder.setTimeOfSunrise(TIME_FORMATTER.format(v)));
      Optional.ofNullable(var1.getSunsetTime())
          .ifPresent(v -> builder.setTimeOfSunset(TIME_FORMATTER.format(v)));
      Optional.ofNullable(var1.getStartTime())
          .ifPresent(v -> builder.setStartTime(TIME_FORMATTER.format(v)));

      TrimAllowed.Builder builder1 = TrimAllowed.newBuilder();
      Optional.ofNullable(var1.getInitialTrim())
          .ifPresent(v -> builder1.setInitialTrim(v.toString()));
      Optional.ofNullable(var1.getMaximumTrim())
          .ifPresent(v -> builder1.setMaximumTrim(v.toString()));
      Optional.ofNullable(var1.getFinalTrim()).ifPresent(v -> builder1.setFinalTrim(v.toString()));

      builder.setTrimAllowed(builder1.build());

      Optional.ofNullable(var1.getCommonDate())
          .ifPresent(commonDate -> builder.setCommonDate(String.valueOf(var1.getCommonDate())));
    }
    return builder.build();
  }

  public LoadingRates buildLoadingRateMessage(LoadingInformation var1) {
    LoadingRates.Builder builder = LoadingRates.newBuilder();
    if (var1 != null) {
      Optional.ofNullable(var1.getId()).ifPresent(builder::setId);
      Optional.ofNullable(var1.getInitialLoadingRate())
          .ifPresent(v -> builder.setInitialLoadingRate(v.toString()));
      Optional.ofNullable(var1.getMaxLoadingRate())
          .ifPresent(v -> builder.setMaxLoadingRate(v.toString()));
      Optional.ofNullable(var1.getReducedLoadingRate())
          .ifPresent(v -> builder.setReducedLoadingRate(v.toString()));
      Optional.ofNullable(var1.getMinDeBallastRate())
          .ifPresent(v -> builder.setMinDeBallastingRate(v.toString()));
      Optional.ofNullable(var1.getMaxDeBallastRate())
          .ifPresent(v -> builder.setMaxDeBallastingRate(v.toString()));
      Optional.ofNullable(var1.getNoticeTimeForRateReduction())
          .ifPresent(v -> builder.setNoticeTimeRateReduction(v.toString()));
      Optional.ofNullable(var1.getNoticeTimeForStopLoading())
          .ifPresent(v -> builder.setNoticeTimeStopLoading(v.toString()));
      Optional.ofNullable(var1.getLineContentRemaining())
          .ifPresent(v -> builder.setLineContentRemaining(v.toString()));
      Optional.ofNullable(var1.getMinLoadingRate())
          .ifPresent(v -> builder.setMinLoadingRate(v.toString()));
      Optional.ofNullable(var1.getShoreLoadingRate())
          .ifPresent(v -> builder.setShoreLoadingRate(v.toString()));
    }
    return builder.build();
  }

  public List<LoadingBerths> buildLoadingBerthsMessage(List<LoadingBerthDetail> list) {
    List<LoadingBerths> berths = new ArrayList<>();
    for (LoadingBerthDetail var1 : list) {
      LoadingBerths.Builder builder = LoadingBerths.newBuilder();
      Optional.ofNullable(var1.getId()).ifPresent(builder::setId);
      Optional.ofNullable(var1.getLoadingInformation().getId())
          .ifPresent(builder::setLoadingInfoId);
      Optional.ofNullable(var1.getBerthXId()).ifPresent(builder::setBerthId);
      Optional.ofNullable(var1.getDepth()).ifPresent(v -> builder.setDepth(v.toString()));
      Optional.ofNullable(var1.getSeaDraftLimitation())
          .ifPresent(v -> builder.setSeaDraftLimitation(v.toString()));
      Optional.ofNullable(var1.getAirDraftLimitation())
          .ifPresent(v -> builder.setAirDraftLimitation(v.toString()));
      Optional.ofNullable(var1.getMaxManifoldHeight())
          .ifPresent(v -> builder.setMaxManifoldHeight(v.toString()));
      Optional.ofNullable(var1.getSpecialRegulationRestriction())
          .ifPresent(v -> builder.setSpecialRegulationRestriction(v.toString()));
      Optional.ofNullable(var1.getItemToBeAgreedWith())
          .ifPresent(v -> builder.setItemsToBeAgreedWith(v));
      Optional.ofNullable(var1.getHoseConnections()).ifPresent(v -> builder.setHoseConnections(v));
      Optional.ofNullable(var1.getLineDisplacement())
          .ifPresent(v -> builder.setLineDisplacement(v.toString()));
      Optional.ofNullable(var1.getDisplacement())
          .ifPresent(v -> builder.setDisplacement(v.toString()));
      berths.add(builder.build());
    }
    return berths;
  }

  public List<LoadingMachinesInUse> buildLoadingMachineryInUseMessage(
      List<LoadingMachineryInUse> list) {
    List<LoadingMachinesInUse> machinery = new ArrayList<>();
    for (LoadingMachineryInUse var1 : list) {
      LoadingMachinesInUse.Builder builder = LoadingMachinesInUse.newBuilder();
      Optional.ofNullable(var1.getId()).ifPresent(builder::setId);
      Optional.ofNullable(var1.getLoadingInformation().getId())
          .ifPresent(builder::setLoadingInfoId);
      Optional.ofNullable(var1.getMachineXId()).ifPresent(builder::setMachineId);
      Optional.ofNullable(var1.getMachineTypeXid())
          .ifPresent(
              v -> {
                builder.setMachineTypeValue(v);
              });
      Optional.ofNullable(var1.getCapacity())
          .ifPresent(value -> builder.setCapacity(value.toString()));
      Optional.ofNullable(var1.getIsUsing()).ifPresent(builder::setIsUsing);
      machinery.add(builder.build());
    }
    return machinery;
  }

  /**
   * Method to build Loading Stage
   *
   * @param var1 LoadingInformation object
   * @param list3 List<StageOffset>
   * @param list4 List<com.cpdss.loadingplan.entity.StageDuration>
   * @return builder.build()
   */
  public LoadingStages buildLoadingStageMessage(
      LoadingInformation var1,
      List<StageOffset> list3,
      List<com.cpdss.loadingplan.entity.StageDuration> list4) {

    log.info("Inside buildLoadingStageMessage method!");
    LoadingStages.Builder builder = LoadingStages.newBuilder();
    if (var1 != null) {

      // Set fields
      Optional.ofNullable(var1.getId()).ifPresent(builder::setId);
      Optional.ofNullable(var1.getStageOffset())
          .ifPresent(value -> builder.setStageOffset(value.getId().intValue()));
      Optional.ofNullable(var1.getStageDuration())
          .ifPresent(value -> builder.setStageDuration(value.getId().intValue()));
      Optional.ofNullable(var1.getTrackStartEndStage()).ifPresent(builder::setTrackStartEndStage);
      Optional.ofNullable(var1.getTrackGradeSwitch()).ifPresent(builder::setTrackGradeSwitch);

      // Set flags for check boxes
      Optional.ofNullable(var1.getIsStageDurationUsed()).ifPresent(builder::setIsStageDurationUsed);
      Optional.ofNullable(var1.getIsStageOffsetUsed()).ifPresent(builder::setIsStageOffsetUsed);
    }

    // Set Offset Master
    builder.addAllStageOffsets(this.buildStageOffsetMasterMessage(list3));
    builder.addAllStageDurations(this.buildStageDurationMasterMessage(list4));
    return builder.build();
  }

  public List<LoadingPlanModels.StageDuration> buildStageDurationMasterMessage(
      List<com.cpdss.loadingplan.entity.StageDuration> list) {
    List<LoadingPlanModels.StageDuration> durations = new ArrayList<>();
    for (com.cpdss.loadingplan.entity.StageDuration dr : list) {
      LoadingPlanModels.StageDuration.Builder builder =
          LoadingPlanModels.StageDuration.newBuilder();
      builder.setId(dr.getId());
      builder.setDuration(dr.getDuration());
      durations.add(builder.build());
    }
    return durations;
  }

  public List<StageOffsets> buildStageOffsetMasterMessage(List<StageOffset> list) {
    List<StageOffsets> offsets = new ArrayList<>();
    for (StageOffset offset : list) {
      StageOffsets.Builder builder = StageOffsets.newBuilder();
      builder.setId(offset.getId());
      builder.setStageOffsetVal(offset.getStageOffsetVal());
      offsets.add(builder.build());
    }
    return offsets;
  }

  public LoadingDelay buildLoadingDelayMessage(
      List<ReasonForDelay> list, List<com.cpdss.loadingplan.entity.LoadingDelay> list6) {
    LoadingDelay.Builder builder = LoadingDelay.newBuilder();
    for (ReasonForDelay var : list) {
      DelayReasons.Builder builder1 = DelayReasons.newBuilder();
      builder1.setId(var.getId());
      builder1.setReason(var.getReason());
      builder.addReasons(builder1);
    }
    for (com.cpdss.loadingplan.entity.LoadingDelay var : list6) {
      List<LoadingDelayReason> activeReasons =
          loadingDelayReasonRepository.findAllByLoadingDelayAndIsActiveTrue(var);
      var.setLoadingDelayReasons(
          new ArrayList<>()); // always set empty array, as the Lazy fetch not works :(
      if (!activeReasons.isEmpty()) {
        var.setLoadingDelayReasons(activeReasons);
      }
      LoadingDelays.Builder builder1 = LoadingDelays.newBuilder();
      builder1.setId(var.getId());
      Optional.ofNullable(var.getLoadingInformation().getId())
          .ifPresent(builder1::setLoadingInfoId);
      Optional.ofNullable(var.getLoadingDelayReasons())
          .ifPresent(
              v -> v.forEach(s -> builder1.addReasonForDelayIds(s.getReasonForDelay().getId())));
      Optional.ofNullable(var.getDuration())
          .ifPresent(value -> builder1.setDuration(value.toString()));
      Optional.ofNullable(var.getCargoXId()).ifPresent(builder1::setCargoId);
      Optional.ofNullable(var.getQuantity())
          .ifPresent(value -> builder1.setQuantity(value.toString()));
      Optional.ofNullable(var.getCargoNominationId()).ifPresent(builder1::setCargoNominationId);
      builder.addDelays(builder1);
    }
    // Cargo List for drop down, at gate way
    return builder.build();
  }

  public List<LoadingToppingOff> buildToppingOffMessage(List<CargoToppingOffSequence> list) {
    List<LoadingToppingOff> toppingOffs = new ArrayList<>();
    for (CargoToppingOffSequence var1 : list) {
      LoadingToppingOff.Builder builder = LoadingToppingOff.newBuilder();
      Optional.ofNullable(var1.getId()).ifPresent(builder::setId);
      Optional.ofNullable(var1.getLoadingInformation().getId())
          .ifPresent(builder::setLoadingInfoId);
      Optional.ofNullable(var1.getOrderNumber()).ifPresent(builder::setOrderNumber);
      Optional.ofNullable(var1.getTankXId()).ifPresent(builder::setTankId);
      Optional.ofNullable(var1.getCargoXId()).ifPresent(builder::setCargoId);
      Optional.ofNullable(var1.getUllage()).ifPresent(v -> builder.setUllage(v.toString()));
      Optional.ofNullable(var1.getQuantity())
          .ifPresent(value -> builder.setQuantity(value.toString()));
      Optional.ofNullable(var1.getFillingRatio())
          .ifPresent(value -> builder.setFillingRatio(value.toString()));
      Optional.ofNullable(var1.getRemarks()).ifPresent(builder::setRemark);
      Optional.ofNullable(var1.getApi()).ifPresent(v -> builder.setApi(v.toString()));
      Optional.ofNullable(var1.getTemperature())
          .ifPresent(v -> builder.setTemperature(v.toString()));
      Optional.ofNullable(var1.getDisplayOrder()).ifPresent(builder::setDisplayOrder);
      // cargo name, short name, colour need to add
      Optional.ofNullable(var1.getAbbreviation()).ifPresent(builder::setAbbreviation);
      Optional.ofNullable(var1.getCargoNominationXId()).ifPresent(builder::setCargoNominationId);
      toppingOffs.add(builder.build());
    }
    return toppingOffs;
  }

  public LoadingInformation buildLoadingInfoFromRpcMessage(
      LoadingPlanModels.LoadingInformation source, LoadingInformation target) {
    // Set Loading Details
    if (source.getLoadingDetail() != null) {
      log.info("Save Loading info, Set Loading Details");
      if (!source.getLoadingDetail().getStartTime().isEmpty())
        target.setStartTime(
            LocalTime.from(TIME_FORMATTER.parse(source.getLoadingDetail().getStartTime())));

      if (!source.getLoadingDetail().getTrimAllowed().getFinalTrim().isEmpty())
        target.setFinalTrim(
            new BigDecimal(source.getLoadingDetail().getTrimAllowed().getFinalTrim()));

      if (!source.getLoadingDetail().getTrimAllowed().getInitialTrim().isEmpty())
        target.setInitialTrim(
            new BigDecimal(source.getLoadingDetail().getTrimAllowed().getInitialTrim()));

      if (!source.getLoadingDetail().getTrimAllowed().getMaximumTrim().isEmpty())
        target.setMaximumTrim(
            new BigDecimal(source.getLoadingDetail().getTrimAllowed().getMaximumTrim()));

      if (!source.getLoadingDetail().getCommonDate().isEmpty()) {
        target.setCommonDate(LocalDate.parse(source.getLoadingDetail().getCommonDate()));
      }
    }
    return target;
  }

  public List<LoadingPlanTankDetails> buildLoadingPlanTankBallastMessage(
      List<PortLoadingPlanBallastDetails> list) throws GenericServiceException {
    log.info("Loading Plan, Ballast Builder");
    List<LoadingPlanTankDetails> response = new ArrayList<>();
    for (PortLoadingPlanBallastDetails var1 : list) {
      response.add(
          this.buildLoadingPlanTankBuilder(
              var1.getId(),
              null,
              null,
              null,
              var1.getQuantity(),
              var1.getTankXId(),
              null,
              var1.getQuantityM3(),
              var1.getSounding(),
              var1.getConditionType(),
              var1.getValueType(),
              var1.getColorCode(),
              var1.getSg(),
              null,
              null,
              null));
    }
    return response;
  }

  public List<LoadingPlanTankDetails> buildLoadingPlanTankStowageMessage(
      List<PortLoadingPlanStowageDetails> list) throws GenericServiceException {
    log.info("Loading Plan, Stowage Builder");
    List<LoadingPlanTankDetails> response = new ArrayList<>();
    for (PortLoadingPlanStowageDetails var1 : list) {
      response.add(
          this.buildLoadingPlanTankBuilder(
              var1.getId(),
              var1.getApi(),
              var1.getTemperature(),
              var1.getCargoNominationXId(),
              var1.getQuantity(),
              var1.getTankXId(),
              var1.getUllage(),
              var1.getQuantityM3(),
              null,
              var1.getConditionType(),
              var1.getValueType(),
              var1.getColorCode(),
              null,
              var1.getAbbreviation(),
              null,
              var1.getCargoXId()));
    }
    return response;
  }

  public List<LoadingPlanTankDetails> buildLoadingPlanTankRobMessage(
      List<PortLoadingPlanRobDetails> list) throws GenericServiceException {
    log.info("Loading Plan, Rob Builder");
    List<LoadingPlanTankDetails> response = new ArrayList<>();
    for (PortLoadingPlanRobDetails var1 : list) {
      response.add(
          this.buildLoadingPlanTankBuilder(
              var1.getId(),
              null,
              null,
              null,
              var1.getQuantity(),
              var1.getTankXId(),
              null,
              var1.getQuantityM3(),
              null,
              var1.getConditionType(),
              var1.getValueType(),
              var1.getColorCode(),
              null,
              null,
              var1.getDensity(),
              null));
    }
    return response;
  }

  public List<LoadingPlanModels.LoadingPlanStabilityParameters>
      buildLoadingPlanTankStabilityMessage(List<PortLoadingPlanStabilityParameters> list)
          throws GenericServiceException {
    log.info("Loading Plan, Rob Builder");
    List<LoadingPlanModels.LoadingPlanStabilityParameters> response = new ArrayList<>();
    for (PortLoadingPlanStabilityParameters var1 : list) {
      response.add(
          this.buildLoadingPlanStabilityBuilder(
              var1.getForeDraft(),
              var1.getMeanDraft(),
              var1.getAftDraft(),
              var1.getTrim(),
              var1.getBendingMoment(),
              var1.getShearingForce(),
              var1.getFreeboard(),
              var1.getManifoldHeight(),
              var1.getConditionType(),
              var1.getValueType()));
    }
    return response;
  }

  private LoadingPlanModels.LoadingPlanStabilityParameters buildLoadingPlanStabilityBuilder(
      BigDecimal foreDraft,
      BigDecimal meanDraft,
      BigDecimal aftDraft,
      BigDecimal trim,
      BigDecimal bm,
      BigDecimal sf,
      BigDecimal freeboard,
      BigDecimal manifoldHeight,
      Integer conditionType,
      Integer valueType)
      throws GenericServiceException {
    try {
      LoadingPlanModels.LoadingPlanStabilityParameters builder =
          LoadingPlanModels.LoadingPlanStabilityParameters.newBuilder()
              .setForeDraft(foreDraft != null ? foreDraft.toString() : "")
              .setMeanDraft(meanDraft != null ? meanDraft.toString() : "")
              .setAftDraft(aftDraft != null ? aftDraft.toString() : "")
              .setTrim(trim != null ? trim.toString() : "")
              .setBm(bm != null ? bm.toString() : "")
              .setSf(sf != null ? sf.toString() : "")
              .setFreeboard(freeboard != null ? freeboard.toString() : "")
              .setManifoldHeight(manifoldHeight != null ? manifoldHeight.toString() : "")
              .setConditionType(conditionType != null ? conditionType : 0)
              .setValueType(valueType != null ? valueType : 0)
              .build();
      return builder;
    } catch (Exception e) {
      e.printStackTrace();
      throw new GenericServiceException(
          "LoadingPlanStabilityParameters Object Build failed",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
  }

  private LoadingPlanTankDetails buildLoadingPlanTankBuilder(
      Long id,
      BigDecimal api,
      BigDecimal temp,
      Long nominationId,
      BigDecimal quantity,
      Long tankId,
      BigDecimal ullage,
      BigDecimal quantityM3,
      BigDecimal sounding,
      Integer conditionType,
      Integer valueType,
      String colorCode,
      BigDecimal sg,
      String abbreviation,
      BigDecimal density,
      Long cargoId)
      throws GenericServiceException {

    try {
      LoadingPlanTankDetails builder =
          LoadingPlanTankDetails.newBuilder()
              .setId(id != null ? id : 0)
              .setApi(api != null ? api.toString() : "")
              .setTemperature(temp != null ? temp.toString() : "")
              .setCargoNominationId(nominationId != null ? nominationId : 0)
              .setQuantity(quantity != null ? quantity.toString() : "")
              .setTankId(tankId != null ? tankId : 0)
              .setUllage(ullage != null ? ullage.toString() : "")
              .setQuantityM3(quantityM3 != null ? quantityM3.toString() : "")
              .setSounding(sounding != null ? sounding.toString() : "")
              .setConditionType(conditionType != null ? conditionType : 0)
              .setValueType(valueType != null ? valueType : 0)
              .setColorCode(colorCode != null ? colorCode : "")
              .setSg(sg != null ? sg.toString() : "")
              .setAbbreviation(abbreviation != null ? abbreviation : "")
              .setDensity(density != null ? density.toString() : "")
              .setCargoId(cargoId != null ? cargoId : 0)
              .build();
      return builder;
    } catch (Exception e) {
      e.printStackTrace();
      throw new GenericServiceException(
          "LoadingPlanTankDetails Object Build failed",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
  }

  /**
   * @param plpCommingleList
   * @return
   * @throws GenericServiceException
   */
  public List<LoadingPlanCommingleDetails> buildLoadingPlanCommingleMessage(
      List<PortLoadingPlanCommingleDetails> plpCommingleList) throws GenericServiceException {
    List<LoadingPlanCommingleDetails> portLoadingPlanCommingleDetails =
        new ArrayList<LoadingPlanCommingleDetails>();
    try {
      plpCommingleList.forEach(
          commingle -> {
            LoadingPlanCommingleDetails.Builder builder = LoadingPlanCommingleDetails.newBuilder();
            Optional.ofNullable(commingle.getGrade()).ifPresent(builder::setAbbreviation);
            Optional.ofNullable(commingle.getApi()).ifPresent(builder::setApi);
            Optional.ofNullable(commingle.getCargo1XId()).ifPresent(builder::setCargo1Id);
            Optional.ofNullable(commingle.getCargo2XId()).ifPresent(builder::setCargo2Id);
            Optional.ofNullable(commingle.getCargoNomination1XId())
                .ifPresent(builder::setCargoNomination1Id);
            Optional.ofNullable(commingle.getCargoNomination2XId())
                .ifPresent(builder::setCargoNomination2Id);
            Optional.ofNullable(commingle.getId()).ifPresent(builder::setId);
            Optional.ofNullable(commingle.getQuantity()).ifPresent(builder::setQuantityMT);
            Optional.ofNullable(commingle.getQuantityM3()).ifPresent(builder::setQuantityM3);
            Optional.ofNullable(commingle.getTankId()).ifPresent(builder::setTankId);
            Optional.ofNullable(commingle.getTemperature()).ifPresent(builder::setTemperature);
            Optional.ofNullable(commingle.getQuantity1MT()).ifPresent(builder::setQuantity1MT);
            Optional.ofNullable(commingle.getQuantity2MT()).ifPresent(builder::setQuantity2MT);
            Optional.ofNullable(commingle.getQuantity1M3()).ifPresent(builder::setQuantity1M3);
            Optional.ofNullable(commingle.getQuantity2M3()).ifPresent(builder::setQuantity2M3);
            Optional.ofNullable(commingle.getUllage1()).ifPresent(builder::setUllage1);
            Optional.ofNullable(commingle.getUllage2()).ifPresent(builder::setUllage2);
            Optional.ofNullable(commingle.getUllage()).ifPresent(builder::setUllage);

            Optional.ofNullable(commingle.getConditionType()).ifPresent(builder::setConditionType);
            Optional.ofNullable(commingle.getValueType()).ifPresent(builder::setValueType);
            Optional.ofNullable(commingle.getColorCode()).ifPresent(builder::setColorCode);
            Optional.ofNullable(commingle.getCargo1Percentage())
                .ifPresent(builder::setCargo1Percentage);
            Optional.ofNullable(commingle.getCargo2Percentage())
                .ifPresent(builder::setCargo2Percentage);
            Optional.ofNullable(commingle.getCargo1Abbreviation())
                .ifPresent(builder::setCargo1Abbreviation);
            Optional.ofNullable(commingle.getCargo2Abbreviation())
                .ifPresent(builder::setCargo2Abbreviation);
            Optional.ofNullable(commingle.getTankName()).ifPresent(builder::setTankName);

            portLoadingPlanCommingleDetails.add(builder.build());
          });
    } catch (Exception e) {
      e.printStackTrace();
      throw new GenericServiceException(
          "LoadingPlanCommingleDetails Object Build failed",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    return portLoadingPlanCommingleDetails;
  }
}
