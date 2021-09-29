/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service;

import com.cpdss.common.generated.loading_plan.LoadingPlanModels.BallastUpdate;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.BillOfLanding;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.BillOfLandingRemove;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.UllageBillRequest;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.UpdateUllage;
import com.cpdss.dischargeplan.entity.BillOfLadding;
import com.cpdss.dischargeplan.entity.PortDischargingPlanBallastTempDetails;
import com.cpdss.dischargeplan.entity.PortDischargingPlanStowageTempDetails;
import com.cpdss.dischargeplan.repository.BillOfLaddingRepository;
import com.cpdss.dischargeplan.repository.PortDischargingPlanBallastTempDetailsRepository;
import com.cpdss.dischargeplan.repository.PortDischargingPlanStowageTempDetailsRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@Transactional
public class DischargeUllageService {

  @Autowired DischargeInformationService dischargeInformationService;
  @Autowired BillOfLaddingRepository billOfLaddingRepo;

  @Autowired
  PortDischargingPlanBallastTempDetailsRepository portDischargingPlanBallastTempDetailsRepository;

  @Autowired
  PortDischargingPlanStowageTempDetailsRepository portDischargingPlanStowageTempDetailsRepository;

  public List<BillOfLadding> updateBillOfLadding(UllageBillRequest request) {
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
                        billOfLadding.getLoadingId()));
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
    return billOfLaddingRepo.saveAll(updatedEntities);
  }

  public List<PortDischargingPlanBallastTempDetails> updateBallast(UllageBillRequest request) {
    List<PortDischargingPlanBallastTempDetails> tempBallast =
        portDischargingPlanBallastTempDetailsRepository
            .findByDischargingInformationAndConditionTypeAndIsActive(
                request.getUpdateUllage(0).getLoadingInformationId(),
                request.getUpdateUllage(0).getArrivalDepartutre(),
                true);
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
                                        .equals(ballastRequest.getLoadingInformationId())
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
    return portDischargingPlanBallastTempDetailsRepository.saveAll(udpatedBallast);
  }

  private PortDischargingPlanBallastTempDetails createBallast(BallastUpdate ballastRequest) {
    PortDischargingPlanBallastTempDetails details = new PortDischargingPlanBallastTempDetails();
    details.setDischargingInformation(ballastRequest.getLoadingInformationId());
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

  public List<PortDischargingPlanStowageTempDetails> updateStowage(UllageBillRequest request) {
    List<PortDischargingPlanStowageTempDetails> tempStowage =
        portDischargingPlanStowageTempDetailsRepository
            .findByDischargingInformationAndConditionTypeAndIsActive(
                request.getUpdateUllage(0).getLoadingInformationId(),
                request.getUpdateUllage(0).getArrivalDepartutre(),
                true);
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
                                        .equals(stowageRequest.getLoadingInformationId())
                                    && ballast
                                        .getConditionType()
                                        .equals(stowageRequest.getArrivalDepartutre()))
                        .findFirst()
                        .orElse(null);
                dbData.setQuantity(new BigDecimal(stowageRequest.getQuantity()));
                dbData.setUllage(new BigDecimal(stowageRequest.getUllage()));
                dbData.setQuantityM3(new BigDecimal(stowageRequest.getQuantity()));
                dbData.setApi(new BigDecimal(stowageRequest.getApi()));
                dbData.setTemperature(new BigDecimal(stowageRequest.getTemperature()));
                stowageToSave.add(dbData);
              } else {
                stowageToSave.add(createStowage(stowageRequest));
              }
            });
    return portDischargingPlanStowageTempDetailsRepository.saveAll(stowageToSave);
  }

  private PortDischargingPlanStowageTempDetails createStowage(UpdateUllage stowageRequest) {
    PortDischargingPlanStowageTempDetails tempData = new PortDischargingPlanStowageTempDetails();
    tempData.setDischargingInformation(stowageRequest.getLoadingInformationId());
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
}
