/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service;

import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.BallastUpdate;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.BillOfLanding;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.BillOfLandingRemove;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.UllageBillRequest;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.UpdateUllage;
import com.cpdss.dischargeplan.common.DischargePlanConstants;
import com.cpdss.dischargeplan.entity.*;
import com.cpdss.dischargeplan.repository.BillOfLaddingRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.util.StringUtils;

public class DischargeUllageServiceUtils {

  private DischargeUllageServiceUtils() {}

  public static List<BillOfLadding> updateBillOfLadding(
      UllageBillRequest request,
      BillOfLaddingRepository billOfLaddingRepo,
      DischargeInformationService dischargeInformationService) {
    List<Long> ids =
        request.getBillOfLandingList().stream()
            .filter(BillOfLanding::getIsUpdate)
            .map(com.cpdss.common.generated.loading_plan.LoadingPlanModels.BillOfLanding::getId)
            .collect(Collectors.toList());
    List<BillOfLadding> entitiesToUpdate = billOfLaddingRepo.findByIdIn(ids);
    List<BillOfLadding> updatedEntities = new ArrayList<>();

    request
        .getBillOfLandingList()
        .forEach(
            billOfLadding -> {
              BigDecimal bbls =
                  billOfLadding.getBblAt60F() != null
                      ? new BigDecimal(billOfLadding.getBblAt60F())
                      : null;
              BigDecimal quantityLt =
                  billOfLadding.getQuantityLt() != null
                      ? new BigDecimal(billOfLadding.getQuantityLt())
                      : null;
              BigDecimal quantityMt =
                  billOfLadding.getQuantityMt() != null
                      ? new BigDecimal(billOfLadding.getQuantityMt())
                      : null;
              BigDecimal quantityKl =
                  billOfLadding.getKlAt15C() != null
                      ? new BigDecimal(billOfLadding.getKlAt15C())
                      : null;
              BigDecimal api =
                  billOfLadding.getApi() != null ? new BigDecimal(billOfLadding.getApi()) : null;
              BigDecimal temp =
                  billOfLadding.getTemperature() != null
                      ? new BigDecimal(billOfLadding.getTemperature())
                      : null;

              if (billOfLadding.getIsUpdate()) {
                BillOfLadding entity =
                    entitiesToUpdate.stream()
                        .filter(ladding -> ladding.getId().equals(billOfLadding.getId()))
                        .findFirst()
                        .orElse(null);

                Optional.ofNullable(billOfLadding.getBlRefNumber()).ifPresent(entity::setBlRefNo);
                entity.setQuantityBbls(bbls);
                entity.setQuantityLT(quantityLt);
                entity.setQuantityMt(quantityMt);
                entity.setQuantityKl(quantityKl);
                entity.setApi(api);
                entity.setTemperature(temp);
                updatedEntities.add(entity);
              } else {
                BillOfLadding ladding = new BillOfLadding();
                ladding.setDischargeInformation(
                    dischargeInformationService.getDischargeInformation(
                        billOfLadding.getDischargingId()));
                ladding.setPortId(billOfLadding.getPortId());
                ladding.setCargoNominationId(billOfLadding.getCargoId());
                ladding.setBlRefNo(billOfLadding.getBlRefNumber());
                ladding.setQuantityBbls(bbls);
                ladding.setQuantityLT(quantityLt);
                ladding.setQuantityMt(quantityMt);
                ladding.setQuantityKl(quantityKl);
                ladding.setApi(api);
                ladding.setTemperature(temp);
                ladding.setIsActive(true);
                ladding.setVersion(billOfLadding.getVersion());
                updatedEntities.add(ladding);
              }
            });
    List<Long> idsToDisable =
        request.getBillOfLandingRemoveList().stream()
            .map(BillOfLandingRemove::getId)
            .collect(Collectors.toList());
    List<BillOfLadding> bLsToDisable = billOfLaddingRepo.findByIdIn(idsToDisable);
    request
        .getBillOfLandingRemoveList()
        .forEach(
            billOfLadding -> {
              BillOfLadding bLToDisable =
                  bLsToDisable.stream()
                      .filter(bL -> bL.getId().equals(billOfLadding.getId()))
                      .findFirst()
                      .orElse(null);
              bLToDisable.setIsActive(false);
              updatedEntities.add(bLToDisable);
            });
    return updatedEntities;
  }

  public static List<PortDischargingPlanBallastTempDetails> updateBallast(
      UllageBillRequest request, List<PortDischargingPlanBallastTempDetails> tempBallast) {
    List<PortDischargingPlanBallastTempDetails> udpatedBallast = new ArrayList<>();
    request
        .getBallastUpdateList()
        .forEach(
            ballastRequest -> {
              if (ballastRequest.getIsUpdate() && !tempBallast.isEmpty()) {
                PortDischargingPlanBallastTempDetails dbData =
                    tempBallast.stream()
                        .filter(
                            ballast ->
                                ballast.getTankXId().equals(ballastRequest.getTankId())
                                    && ballast
                                        .getDischargingInformation()
                                        .equals(ballastRequest.getDischargingInformationId())
                                    && ballast
                                        .getConditionType()
                                        .equals(ballastRequest.getArrivalDepartutre()))
                        .findFirst()
                        .orElse(null);

                dbData.setSg(
                    StringUtils.isEmpty(ballastRequest.getSg())
                        ? null
                        : new BigDecimal(ballastRequest.getSg()));
                dbData.setCorrectedUllage(
                    StringUtils.isEmpty(ballastRequest.getCorrectedUllage())
                        ? null
                        : new BigDecimal(ballastRequest.getCorrectedUllage()));
                dbData.setColorCode(ballastRequest.getColorCode());
                dbData.setQuantity(new BigDecimal(ballastRequest.getQuantity()));
                dbData.setSounding(new BigDecimal(ballastRequest.getSounding()));
                dbData.setQuantityM3(new BigDecimal(ballastRequest.getQuantity()));
                udpatedBallast.add(dbData);
              } else {
                udpatedBallast.add(createBallast(ballastRequest));
              }
            });
    return udpatedBallast;
  }

  private static PortDischargingPlanBallastTempDetails createBallast(BallastUpdate ballastRequest) {
    PortDischargingPlanBallastTempDetails details = new PortDischargingPlanBallastTempDetails();
    details.setDischargingInformation(ballastRequest.getDischargingInformationId());
    details.setPortRotationXId(ballastRequest.getPortRotationXid());
    details.setPortXId(ballastRequest.getPortXid());
    details.setTankXId(ballastRequest.getTankId());
    details.setTemperature(
        StringUtils.isEmpty(ballastRequest.getTemperature())
            ? null
            : new BigDecimal(ballastRequest.getTemperature()));
    details.setCorrectedUllage(
        StringUtils.isEmpty(ballastRequest.getCorrectedUllage())
            ? null
            : new BigDecimal(ballastRequest.getCorrectedUllage()));
    details.setQuantity(
        StringUtils.isEmpty(ballastRequest.getQuantity())
            ? null
            : new BigDecimal(ballastRequest.getQuantity()));
    details.setObservedM3(
        StringUtils.isEmpty(ballastRequest.getObservedM3())
            ? null
            : new BigDecimal(ballastRequest.getObservedM3()));
    details.setFillingPercentage(
        StringUtils.isEmpty(ballastRequest.getFillingPercentage())
            ? null
            : new BigDecimal(ballastRequest.getFillingPercentage()));
    details.setSounding(
        StringUtils.isEmpty(ballastRequest.getSounding())
            ? null
            : new BigDecimal(ballastRequest.getSounding()));
    details.setValueType(ballastRequest.getActualPlanned());
    details.setConditionType(ballastRequest.getArrivalDepartutre());
    details.setIsActive(true);
    details.setColorCode(ballastRequest.getColorCode());
    details.setSg(
        StringUtils.isEmpty(ballastRequest.getSg())
            ? null
            : new BigDecimal(ballastRequest.getSg()));
    return details;
  }

  public static List<PortDischargingPlanStowageTempDetails> updateStowage(
      UllageBillRequest request, List<PortDischargingPlanStowageTempDetails> tempStowage) {
    List<PortDischargingPlanStowageTempDetails> stowageToSave = new ArrayList<>();
    request
        .getUpdateUllageList()
        .forEach(
            stowageRequest -> {
              if (stowageRequest.getIsUpdate() && !tempStowage.isEmpty()) {
                PortDischargingPlanStowageTempDetails dbData =
                    tempStowage.stream()
                        .filter(
                            ballast ->
                                ballast.getTankXId().equals(stowageRequest.getTankId())
                                    && ballast
                                        .getDischargingInformation()
                                        .equals(stowageRequest.getDischargingInfoId())
                                    && ballast
                                        .getConditionType()
                                        .equals(stowageRequest.getArrivalDepartutre()))
                        .findFirst()
                        .orElse(null);
                dbData.setQuantity(new BigDecimal(stowageRequest.getQuantity()));
                dbData.setUllage(new BigDecimal(stowageRequest.getUllage()));
                // DSS 5450 saving volume of cargo for next voyage OBQ
                dbData.setQuantityM3(new BigDecimal(stowageRequest.getObservedM3()));
                dbData.setApi(new BigDecimal(stowageRequest.getApi()));
                dbData.setTemperature(new BigDecimal(stowageRequest.getTemperature()));
                stowageToSave.add(dbData);
              } else {
                stowageToSave.add(createStowage(stowageRequest));
              }
            });
    return stowageToSave;
  }

  public static PortDischargingPlanStowageTempDetails createStowage(UpdateUllage stowageRequest) {
    PortDischargingPlanStowageTempDetails tempData = new PortDischargingPlanStowageTempDetails();
    tempData.setDischargingInformation(stowageRequest.getDischargingInfoId());
    tempData.setTankXId(stowageRequest.getTankId());
    tempData.setTemperature(
        StringUtils.isEmpty(stowageRequest.getTemperature())
            ? null
            : new BigDecimal(stowageRequest.getTemperature()));
    tempData.setCorrectedUllage(
        StringUtils.isEmpty(stowageRequest.getCorrectedUllage())
            ? null
            : new BigDecimal(stowageRequest.getCorrectedUllage()));
    tempData.setQuantity(new BigDecimal(stowageRequest.getQuantity()));
    // DSS 5450 saving volume of cargo for next voyage OBQ
    tempData.setQuantityM3(new BigDecimal(stowageRequest.getObservedM3()));
    tempData.setFillingPercentage(
        StringUtils.isEmpty(stowageRequest.getFillingPercentage())
            ? null
            : new BigDecimal(stowageRequest.getFillingPercentage()));
    tempData.setApi(
        StringUtils.isEmpty(stowageRequest.getApi())
            ? null
            : new BigDecimal(stowageRequest.getApi()));
    tempData.setCargoNominationXId(stowageRequest.getCargoNominationXid());
    tempData.setPortXId(stowageRequest.getPortXid());
    tempData.setPortRotationXId(stowageRequest.getPortRotationXid());
    tempData.setValueType(stowageRequest.getActualPlanned());
    tempData.setConditionType(stowageRequest.getArrivalDepartutre());
    tempData.setCorrectionFactor(
        StringUtils.isEmpty(stowageRequest.getCorrectionFactor())
            ? null
            : new BigDecimal(stowageRequest.getCorrectionFactor()));
    tempData.setIsActive(true);
    tempData.setUllage(
        StringUtils.isEmpty(stowageRequest.getUllage())
            ? null
            : new BigDecimal(stowageRequest.getUllage()));
    tempData.setColorCode(stowageRequest.getColorCode());
    tempData.setAbbreviation(stowageRequest.getAbbreviation());
    tempData.setCargoXId(stowageRequest.getCargoId());
    return tempData;
  }

  public static List<PortDischargingPlanRobDetails> updateRob(
      UllageBillRequest request, List<PortDischargingPlanRobDetails> tempRob) {

    List<PortDischargingPlanRobDetails> robToSave = new ArrayList<>();
    request
        .getRobUpdateList()
        .forEach(
            robRequest -> {
              BigDecimal quantity =
                  StringUtils.isEmpty(robRequest.getQuantity())
                      ? null
                      : new BigDecimal(robRequest.getQuantity());
              if (robRequest.getIsUpdate() && !tempRob.isEmpty()) {
                PortDischargingPlanRobDetails dbData =
                    tempRob.stream()
                        .filter(
                            ballast ->
                                ballast.getTankXId().equals(robRequest.getTankId())
                                    && ballast
                                        .getDischargingInformation()
                                        .equals(robRequest.getDischargingInformationId())
                                    && ballast
                                        .getConditionType()
                                        .equals(robRequest.getArrivalDepartutre())
                                    && ballast.getValueType().equals(robRequest.getActualPlanned()))
                        .findFirst()
                        .orElse(null);
                dbData.setQuantity(quantity);
                dbData.setQuantityM3(quantity);
                robToSave.add(dbData);
              } else {
                PortDischargingPlanRobDetails robDet = new PortDischargingPlanRobDetails();
                robDet.setDischargingInformation(robRequest.getDischargingInformationId());
                robDet.setTankXId(Long.valueOf(robRequest.getTankId()));
                robDet.setQuantity(
                    StringUtils.isEmpty(robRequest.getQuantity())
                        ? null
                        : new BigDecimal(robRequest.getQuantity()));
                robDet.setPortXId(Long.valueOf(robRequest.getPortXid()));
                robDet.setPortRotationXId(Long.valueOf(robRequest.getPortRotationXid()));
                robDet.setConditionType(robRequest.getArrivalDepartutre());
                robDet.setValueType(DischargePlanConstants.ACTUAL_TYPE_VALUE);
                robDet.setIsActive(true);
                robDet.setColorCode(robRequest.getColourCode());
                robDet.setDensity(
                    StringUtils.isEmpty(robRequest.getDensity())
                        ? null
                        : new BigDecimal(robRequest.getDensity()));
                robToSave.add(robDet);
              }
            });

    return robToSave;
  }

  public static List<PortDischargingPlanCommingleTempDetails> updateCommingle(
      UllageBillRequest request, List<PortDischargingPlanCommingleTempDetails> tempCommingle) {
    List<PortDischargingPlanCommingleTempDetails> commingleToSave = new ArrayList<>();
    request
        .getCommingleUpdateList()
        .forEach(
            commingleRequest -> {
              if (commingleRequest.getIsUpdate() && !tempCommingle.isEmpty()) {
                PortDischargingPlanCommingleTempDetails dbData =
                    tempCommingle.stream()
                        .filter(
                            commingle ->
                                commingle.getTankId().equals(commingleRequest.getTankId())
                                    && commingle
                                        .getDischargingInformation()
                                        .equals(commingleRequest.getDischargingInformationId())
                                    && commingle
                                        .getConditionType()
                                        .equals(commingleRequest.getArrivalDeparture()))
                        .findFirst()
                        .orElse(null);
                dbData.setQuantityM3(commingleRequest.getQuantityM3());
                dbData.setApi(commingleRequest.getApi());
                dbData.setFillingRatio(commingleRequest.getFillingPercentage());
                dbData.setQuantity(commingleRequest.getQuantityMT());
                dbData.setQuantityM3(commingleRequest.getQuantityM3());
                dbData.setTemperature(commingleRequest.getTemperature());
                dbData.setCargo1Mt(commingleRequest.getQuantity1MT());
                dbData.setCargo2Mt(commingleRequest.getQuantity2MT());
                dbData.setCargo1Lt(commingleRequest.getQuantity1M3());
                dbData.setCargo2Lt(commingleRequest.getQuantity2M3());
                dbData.setUllage(commingleRequest.getUllage());
                dbData.setColorCode(commingleRequest.getColorCode());
                dbData.setTankId(commingleRequest.getTankId());
                dbData.setDischargingInformation(commingleRequest.getDischargingInformationId());
                dbData.setConditionType(commingleRequest.getArrivalDeparture());

                commingleToSave.add(dbData);
              } else {
                commingleToSave.add(createCommingle(commingleRequest));
              }
            });
    return commingleToSave;
  }

  private static PortDischargingPlanCommingleTempDetails createCommingle(
      LoadingPlanModels.CommingleUpdate commingleRequest) {
    PortDischargingPlanCommingleTempDetails tempData =
        new PortDischargingPlanCommingleTempDetails();
    tempData.setDischargingInformation(commingleRequest.getDischargingInformationId());
    tempData.setTankId(commingleRequest.getTankId());
    tempData.setTemperature(
        StringUtils.isEmpty(commingleRequest.getTemperature())
            ? null
            : commingleRequest.getTemperature());
    tempData.setCorrectedUllage(
        StringUtils.isEmpty(commingleRequest.getCorrectedUllage())
            ? null
            : Long.valueOf(commingleRequest.getCorrectedUllage()));
    tempData.setQuantity(commingleRequest.getQuantityMT());
    tempData.setFillingRatio(
        StringUtils.isEmpty(commingleRequest.getFillingPercentage())
            ? null
            : commingleRequest.getFillingPercentage());
    tempData.setApi(
        StringUtils.isEmpty(commingleRequest.getApi()) ? null : commingleRequest.getApi());
    tempData.setValueType(commingleRequest.getActualPlanned());
    tempData.setConditionType(commingleRequest.getArrivalDeparture());
    tempData.setCorrectionFactor(
        StringUtils.isEmpty(commingleRequest.getCorrectionFactor())
            ? null
            : commingleRequest.getCorrectionFactor());
    tempData.setIsActive(true);
    tempData.setUllage(
        StringUtils.isEmpty(commingleRequest.getUllage()) ? null : commingleRequest.getUllage());
    tempData.setColorCode(commingleRequest.getColorCode());
    tempData.setCargoNomination1XId(commingleRequest.getCargoNomination1Id());
    tempData.setCargoNomination2XId(commingleRequest.getCargoNomination2Id());
    tempData.setCargo1XId(commingleRequest.getCargo1Id());
    tempData.setCargo2XId(commingleRequest.getCargo2Id());
    tempData.setCargo1Mt(commingleRequest.getQuantity1MT());
    tempData.setCargo2Mt(commingleRequest.getQuantity2MT());
    tempData.setCargo1Lt(commingleRequest.getQuantity1M3());
    tempData.setCargo2Lt(commingleRequest.getQuantity2M3());
    tempData.setGrade(commingleRequest.getAbbreviation());
    return tempData;
  }
}
