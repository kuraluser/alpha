/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service;

import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.LoadableStudy.LoadableQuantityCargoDetails;
import com.cpdss.common.generated.LoadableStudy.LoadableQuantityCommingleCargoDetails;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.CargoToppingOffSequence;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInformationDetail;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncDetails;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanSyncReply;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.loadingplan.common.LoadingPlanConstants;
import com.cpdss.loadingplan.entity.LoadablePlanBallastDetails;
import com.cpdss.loadingplan.entity.LoadablePlanCommingleDetails;
import com.cpdss.loadingplan.entity.LoadablePlanStowageDetails;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.repository.CargoToppingOffSequenceRepository;
import com.cpdss.loadingplan.repository.LoadablePlanBallastDetailsRepository;
import com.cpdss.loadingplan.repository.LoadablePlanCommingleDetailsRepository;
import com.cpdss.loadingplan.repository.LoadablePlanQuantityRepository;
import com.cpdss.loadingplan.repository.LoadablePlanStowageDetailsRepository;
import com.cpdss.loadingplan.repository.LoadingInformationRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Slf4j
@Transactional
@Service
public class LoadingPlanService {

  @Autowired LoadingInformationRepository loadingInformationRepository;
  @Autowired CargoToppingOffSequenceRepository cargoToppingOffSequenceRepository;
  @Autowired LoadablePlanBallastDetailsRepository loadablePlanBallastDetailsRepository;
  @Autowired LoadablePlanCommingleDetailsRepository loadablePlanCommingleDetailsRepository;
  @Autowired LoadablePlanQuantityRepository loadablePlanQuantityRepository;
  @Autowired LoadablePlanStowageDetailsRepository loadablePlanStowageDetailsRepository;

  /**
   * @param request
   * @param builder
   */
  public void loadingPlanSynchronization(
      LoadingPlanSyncDetails request, LoadingPlanSyncReply.Builder builder) {
    try {
      LoadingInformation loadingInformation = new LoadingInformation();
      LoadingInformation savedLoadingInformation =
          saveLoadingInformation(request.getLoadingInformationDetail(), loadingInformation);
      saveCargoToppingOffSequenceList(
          request.getCargoToppingOffSequencesList(), savedLoadingInformation);
      saveLoadablePlanBallastDetailsList(
          request.getLoadablePlanDetailsReply().getLoadablePlanBallastDetailsList(),
          savedLoadingInformation);
      saveLoadablePlanCommingleDetailsList(
          request.getLoadablePlanDetailsReply().getLoadableQuantityCommingleCargoDetailsList(),
          savedLoadingInformation);
      saveLoadablePlanQuantyList(
          request.getLoadablePlanDetailsReply().getLoadableQuantityCargoDetailsList(),
          savedLoadingInformation);
      saveLoadablePlanStowageDetailsList(
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

  /**
   * Saves LoadablePlanStowageDetails from list
   *
   * @param stowageDetailsList
   * @param loadingInformation
   */
  private void saveLoadablePlanStowageDetailsList(
      List<LoadableStudy.LoadablePlanStowageDetails> stowageDetailsList,
      LoadingInformation loadingInformation) {
    stowageDetailsList.stream()
        .forEach(
            loadablePlanStowageDetail -> {
              LoadablePlanStowageDetails loadablePlanStowageDetails =
                  new LoadablePlanStowageDetails();
              Optional.ofNullable(loadablePlanStowageDetail.getApi())
                  .ifPresent(loadablePlanStowageDetails::setApi);
              Optional.ofNullable(loadablePlanStowageDetail.getCargoAbbreviation())
                  .ifPresent(loadablePlanStowageDetails::setAbbreviation);
              Optional.ofNullable(loadablePlanStowageDetail.getCargoNominationId())
                  .ifPresent(loadablePlanStowageDetails::setCargoNominationId);
              Optional.ofNullable(loadablePlanStowageDetail.getColorCode())
                  .ifPresent(loadablePlanStowageDetails::setColorCode);
              Optional.ofNullable(loadablePlanStowageDetail.getCorrectedUllage())
                  .ifPresent(loadablePlanStowageDetails::setCorrectedUllage);
              Optional.ofNullable(loadablePlanStowageDetail.getCorrectionFactor())
                  .ifPresent(loadablePlanStowageDetails::setCorrectionFactor);
              Optional.ofNullable(loadablePlanStowageDetail.getFillingRatio())
                  .ifPresent(loadablePlanStowageDetails::setFillingPercentage);
              Optional.ofNullable(loadablePlanStowageDetail.getId())
                  .ifPresent(loadablePlanStowageDetails::setId);
              Optional.ofNullable(loadablePlanStowageDetail.getObservedBarrels())
                  .ifPresent(loadablePlanStowageDetails::setObservedBarrels);
              Optional.ofNullable(loadablePlanStowageDetail.getObservedBarrelsAt60())
                  .ifPresent(loadablePlanStowageDetails::setObservedBarrelsAt60);
              Optional.ofNullable(loadablePlanStowageDetail.getObservedM3())
                  .ifPresent(loadablePlanStowageDetails::setObservedM3);
              Optional.ofNullable(loadablePlanStowageDetail.getRdgUllage())
                  .ifPresent(loadablePlanStowageDetails::setRdgUllage);
              Optional.ofNullable(loadablePlanStowageDetail.getTankId())
                  .ifPresent(loadablePlanStowageDetails::setTankId);
              Optional.ofNullable(loadablePlanStowageDetail.getTankName())
                  .ifPresent(loadablePlanStowageDetails::setTankname);
              Optional.ofNullable(loadablePlanStowageDetail.getTemperature())
                  .ifPresent(loadablePlanStowageDetails::setTemperature);
              Optional.ofNullable(loadablePlanStowageDetail.getWeight())
                  .ifPresent(loadablePlanStowageDetails::setWeight);
              loadablePlanStowageDetails.setLoadablePatternXId(
                  loadingInformation.getLoadablePatternXId());
              loadablePlanStowageDetails.setLoadingInformation(loadingInformation);
              loadablePlanStowageDetails.setIsActive(true);
              loadablePlanStowageDetailsRepository.save(loadablePlanStowageDetails);
            });
  }

  /**
   * Saves LoadablePlanQuantity from list
   *
   * @param cargoDetailsList
   * @param loadingInformation
   */
  private void saveLoadablePlanQuantyList(
      List<LoadableQuantityCargoDetails> cargoDetailsList, LoadingInformation loadingInformation) {
    cargoDetailsList.stream()
        .forEach(
            planQuantity -> {
              com.cpdss.loadingplan.entity.LoadablePlanQuantity loadablePlanQuantity =
                  new com.cpdss.loadingplan.entity.LoadablePlanQuantity();
              Optional.ofNullable(planQuantity.getCargoAbbreviation())
                  .ifPresent(loadablePlanQuantity::setCargoAbbreviation);
              Optional.ofNullable(planQuantity.getCargoId())
                  .ifPresent(loadablePlanQuantity::setCargoXId);
              Optional.ofNullable(planQuantity.getCargoNominationId())
                  .ifPresent(loadablePlanQuantity::setCargoNominationId);
              Optional.ofNullable(planQuantity.getColorCode())
                  .ifPresent(loadablePlanQuantity::setCargoColor);
              Optional.ofNullable(planQuantity.getDifferenceColor())
                  .ifPresent(loadablePlanQuantity::setDifferenceColor);
              Optional.ofNullable(planQuantity.getDifferencePercentage())
                  .ifPresent(loadablePlanQuantity::setDifferencePercentage);
              loadablePlanQuantity.setEstimatedApi(
                  StringUtils.isEmpty(planQuantity.getEstimatedAPI())
                      ? null
                      : new BigDecimal(planQuantity.getEstimatedAPI()));
              loadablePlanQuantity.setEstimatedTemperature(
                  StringUtils.isEmpty(planQuantity.getEstimatedTemp())
                      ? null
                      : new BigDecimal(planQuantity.getEstimatedTemp()));
              Optional.ofNullable(planQuantity.getGrade())
                  .ifPresent(loadablePlanQuantity::setGrade);
              Optional.ofNullable(planQuantity.getId()).ifPresent(loadablePlanQuantity::setId);
              Optional.ofNullable(planQuantity.getLoadableBbls60F())
                  .ifPresent(loadablePlanQuantity::setLoadableBbls60f);
              Optional.ofNullable(planQuantity.getLoadableBblsdbs())
                  .ifPresent(loadablePlanQuantity::setLoadableBblsDbs);
              Optional.ofNullable(planQuantity.getLoadableKL())
                  .ifPresent(loadablePlanQuantity::setLoadableKl);
              Optional.ofNullable(planQuantity.getLoadableLT())
                  .ifPresent(loadablePlanQuantity::setLoadableLt);
              Optional.ofNullable(planQuantity.getLoadableMT())
                  .ifPresent(loadablePlanQuantity::setLoadableMt);
              Optional.ofNullable(planQuantity.getLoadingOrder())
                  .ifPresent(loadablePlanQuantity::setLoadingOrder);
              Optional.ofNullable(planQuantity.getMaxTolerence())
                  .ifPresent(loadablePlanQuantity::setMaxTolerence);
              Optional.ofNullable(planQuantity.getMinTolerence())
                  .ifPresent(loadablePlanQuantity::setMinTolerence);
              Optional.ofNullable(planQuantity.getOrderBbls60F())
                  .ifPresent(loadablePlanQuantity::setOrderBbls60f);
              Optional.ofNullable(planQuantity.getOrderBblsdbs())
                  .ifPresent(loadablePlanQuantity::setOrderBblsDbs);
              loadablePlanQuantity.setOrderQuantity(
                  StringUtils.isEmpty(planQuantity.getOrderedMT())
                      ? null
                      : new BigDecimal(planQuantity.getOrderedMT()));
              Optional.ofNullable(planQuantity.getPriority())
                  .ifPresent(loadablePlanQuantity::setPriority);
              Optional.ofNullable(planQuantity.getSlopQuantity())
                  .ifPresent(loadablePlanQuantity::setSlopQuantity);
              Optional.ofNullable(planQuantity.getTimeRequiredForLoading())
                  .ifPresent(loadablePlanQuantity::setTimeRequiredForLoading);
              loadablePlanQuantity.setLoadablePattern(loadingInformation.getLoadablePatternXId());
              loadablePlanQuantity.setLoadingInformation(loadingInformation);
              loadablePlanQuantity.setIsActive(true);
              loadablePlanQuantityRepository.save(loadablePlanQuantity);
            });
  }

  /**
   * Saves LoadablePlanCommingleDetails from list
   *
   * @param commingleDetailsList
   * @param loadingInformation
   */
  private void saveLoadablePlanCommingleDetailsList(
      List<LoadableQuantityCommingleCargoDetails> commingleDetailsList,
      LoadingInformation loadingInformation) {
    commingleDetailsList.stream()
        .forEach(
            loadablePlanCommingleDetail -> {
              LoadablePlanCommingleDetails loadablePlanCommingleDetails =
                  new LoadablePlanCommingleDetails();
              Optional.ofNullable(loadablePlanCommingleDetail.getApi())
                  .ifPresent(loadablePlanCommingleDetails::setApi);
              Optional.ofNullable(loadablePlanCommingleDetail.getCargo1Abbreviation())
                  .ifPresent(loadablePlanCommingleDetails::setCargo1Abbreviation);
              Optional.ofNullable(loadablePlanCommingleDetail.getCargo1Bbls60F())
                  .ifPresent(loadablePlanCommingleDetails::setCargo1Bbls60f);
              Optional.ofNullable(loadablePlanCommingleDetail.getCargo1Bblsdbs())
                  .ifPresent(loadablePlanCommingleDetails::setCargo1BblsDbs);
              Optional.ofNullable(loadablePlanCommingleDetail.getCargo1KL())
                  .ifPresent(loadablePlanCommingleDetails::setCargo1Kl);
              Optional.ofNullable(loadablePlanCommingleDetail.getCargo1LT())
                  .ifPresent(loadablePlanCommingleDetails::setCargo1Lt);
              Optional.ofNullable(loadablePlanCommingleDetail.getCargo1MT())
                  .ifPresent(loadablePlanCommingleDetails::setCargo1Mt);
              Optional.ofNullable(loadablePlanCommingleDetail.getCargo1Percentage())
                  .ifPresent(loadablePlanCommingleDetails::setCargo1Percentage);
              Optional.ofNullable(loadablePlanCommingleDetail.getCargo2Abbreviation())
                  .ifPresent(loadablePlanCommingleDetails::setCargo2Abbreviation);
              Optional.ofNullable(loadablePlanCommingleDetail.getCargo2Bbls60F())
                  .ifPresent(loadablePlanCommingleDetails::setCargo2Bbls60f);
              Optional.ofNullable(loadablePlanCommingleDetail.getCargo2Bblsdbs())
                  .ifPresent(loadablePlanCommingleDetails::setCargo2BblsDbs);
              Optional.ofNullable(loadablePlanCommingleDetail.getCargo2KL())
                  .ifPresent(loadablePlanCommingleDetails::setCargo2Kl);
              Optional.ofNullable(loadablePlanCommingleDetail.getCargo2LT())
                  .ifPresent(loadablePlanCommingleDetails::setCargo2Lt);
              Optional.ofNullable(loadablePlanCommingleDetail.getCargo2MT())
                  .ifPresent(loadablePlanCommingleDetails::setCargo2Mt);
              Optional.ofNullable(loadablePlanCommingleDetail.getCargo2Percentage())
                  .ifPresent(loadablePlanCommingleDetails::setCargo2Percentage);
              Optional.ofNullable(loadablePlanCommingleDetail.getCorrectedUllage())
                  .ifPresent(loadablePlanCommingleDetails::setCorrectedUllage);
              Optional.ofNullable(loadablePlanCommingleDetail.getCorrectionFactor())
                  .ifPresent(loadablePlanCommingleDetails::setCorrectionFactor);
              Optional.ofNullable(loadablePlanCommingleDetail.getFillingRatio())
                  .ifPresent(loadablePlanCommingleDetails::setFillingRatio);
              Optional.ofNullable(loadablePlanCommingleDetail.getGrade())
                  .ifPresent(loadablePlanCommingleDetails::setGrade);
              Optional.ofNullable(loadablePlanCommingleDetail.getId())
                  .ifPresent(loadablePlanCommingleDetails::setId);
              Optional.ofNullable(loadablePlanCommingleDetail.getLoadingOrder())
                  .ifPresent(loadablePlanCommingleDetails::setLoadingOrder);
              Optional.ofNullable(loadablePlanCommingleDetail.getOrderedMT())
                  .ifPresent(loadablePlanCommingleDetails::setOrderQuantity);
              Optional.ofNullable(loadablePlanCommingleDetail.getPriority())
                  .ifPresent(loadablePlanCommingleDetails::setPriority);
              Optional.ofNullable(loadablePlanCommingleDetail.getQuantity())
                  .ifPresent(loadablePlanCommingleDetails::setQuantity);
              Optional.ofNullable(loadablePlanCommingleDetail.getRdgUllage())
                  .ifPresent(loadablePlanCommingleDetails::setRdgUllage);
              Optional.ofNullable(loadablePlanCommingleDetail.getSlopQuantity())
                  .ifPresent(loadablePlanCommingleDetails::setSlopQuantity);
              Optional.ofNullable(loadablePlanCommingleDetail.getTankId())
                  .ifPresent(loadablePlanCommingleDetails::setTankId);
              Optional.ofNullable(loadablePlanCommingleDetail.getTankName())
                  .ifPresent(loadablePlanCommingleDetails::setTankName);
              Optional.ofNullable(loadablePlanCommingleDetail.getTemp())
                  .ifPresent(loadablePlanCommingleDetails::setTemperature);
              Optional.ofNullable(loadablePlanCommingleDetail.getTimeRequiredForLoading())
                  .ifPresent(loadablePlanCommingleDetails::setTimeRequiredForLoading);
              loadablePlanCommingleDetails.setLoadablePatternXId(
                  loadingInformation.getLoadablePatternXId());
              loadablePlanCommingleDetails.setLoadingInformation(loadingInformation);
              loadablePlanCommingleDetails.setIsActive(true);
              loadablePlanCommingleDetailsRepository.save(loadablePlanCommingleDetails);
            });
    ;
  }

  /**
   * Saves LoadablePlanBallastDetails from list
   *
   * @param ballastDetailsList
   * @param loadingInformation
   */
  private void saveLoadablePlanBallastDetailsList(
      List<LoadableStudy.LoadablePlanBallastDetails> ballastDetailsList,
      LoadingInformation loadingInformation) {
    ballastDetailsList.stream()
        .forEach(
            loadablePlanBallastDetail -> {
              LoadablePlanBallastDetails loadablePlanBallastDetails =
                  new LoadablePlanBallastDetails();
              Optional.ofNullable(loadablePlanBallastDetail.getColorCode())
                  .ifPresent(loadablePlanBallastDetails::setColorCode);
              Optional.ofNullable(loadablePlanBallastDetail.getCorrectedLevel())
                  .ifPresent(loadablePlanBallastDetails::setCorrectedLevel);
              Optional.ofNullable(loadablePlanBallastDetail.getCorrectionFactor())
                  .ifPresent(loadablePlanBallastDetails::setCorrectionFactor);
              Optional.ofNullable(loadablePlanBallastDetail.getCubicMeter())
                  .ifPresent(loadablePlanBallastDetails::setCubicMeter);
              Optional.ofNullable(loadablePlanBallastDetail.getId())
                  .ifPresent(loadablePlanBallastDetails::setId);
              Optional.ofNullable(loadablePlanBallastDetail.getInertia())
                  .ifPresent(loadablePlanBallastDetails::setInertia);
              Optional.ofNullable(loadablePlanBallastDetail.getLcg())
                  .ifPresent(loadablePlanBallastDetails::setLcg);
              Optional.ofNullable(loadablePlanBallastDetail.getMetricTon())
                  .ifPresent(loadablePlanBallastDetails::setMetricTon);
              Optional.ofNullable(loadablePlanBallastDetail.getPercentage())
                  .ifPresent(loadablePlanBallastDetails::setPercentage);
              Optional.ofNullable(loadablePlanBallastDetail.getRdgLevel())
                  .ifPresent(loadablePlanBallastDetails::setRdgLevel);
              Optional.ofNullable(loadablePlanBallastDetail.getSg())
                  .ifPresent(loadablePlanBallastDetails::setSg);
              Optional.ofNullable(loadablePlanBallastDetail.getTankId())
                  .ifPresent(loadablePlanBallastDetails::setTankId);
              Optional.ofNullable(loadablePlanBallastDetail.getTankName())
                  .ifPresent(loadablePlanBallastDetails::setTankName);
              Optional.ofNullable(loadablePlanBallastDetail.getTcg())
                  .ifPresent(loadablePlanBallastDetails::setTcg);
              Optional.ofNullable(loadablePlanBallastDetail.getVcg())
                  .ifPresent(loadablePlanBallastDetails::setVcg);
              loadablePlanBallastDetails.setLoadingInformation(loadingInformation);
              loadablePlanBallastDetails.setIsActive(true);
              loadablePlanBallastDetailsRepository.save(loadablePlanBallastDetails);
            });
  }

  /**
   * Saves CargoToppingOffSequence from list
   *
   * @param cargoToppingOffSequencesList
   * @param loadingInformation
   */
  private void saveCargoToppingOffSequenceList(
      List<CargoToppingOffSequence> cargoToppingOffSequencesList,
      LoadingInformation loadingInformation) {
    cargoToppingOffSequencesList.stream()
        .forEach(
            cargoToppingOff -> {
              com.cpdss.loadingplan.entity.CargoToppingOffSequence cargoToppingOffSequence =
                  new com.cpdss.loadingplan.entity.CargoToppingOffSequence();
              Optional.ofNullable(cargoToppingOff.getCargoXId())
                  .ifPresent(cargoToppingOffSequence::setCargoXId);
              cargoToppingOffSequence.setFillingRatio(
                  StringUtils.isEmpty(cargoToppingOff.getFillingRatio())
                      ? null
                      : new BigDecimal(cargoToppingOff.getFillingRatio()));
              Optional.ofNullable(cargoToppingOff.getOrderNumber())
                  .ifPresent(cargoToppingOffSequence::setOrderNumber);
              Optional.ofNullable(cargoToppingOff.getRemarks())
                  .ifPresent(cargoToppingOffSequence::setRemarks);
              Optional.ofNullable(cargoToppingOff.getTankXId())
                  .ifPresent(cargoToppingOffSequence::setTankXId);
              cargoToppingOffSequence.setUllage(
                  StringUtils.isEmpty(cargoToppingOff.getUllage())
                      ? null
                      : new BigDecimal(cargoToppingOff.getUllage()));
              cargoToppingOffSequence.setVolume(
                  StringUtils.isEmpty(cargoToppingOff.getVolume())
                      ? null
                      : new BigDecimal(cargoToppingOff.getVolume()));
              cargoToppingOffSequence.setWeight(
                  StringUtils.isEmpty(cargoToppingOff.getWeight())
                      ? null
                      : new BigDecimal(cargoToppingOff.getWeight()));
              cargoToppingOffSequence.setLoadingInformation(loadingInformation);
              cargoToppingOffSequence.setIsActive(true);
              cargoToppingOffSequenceRepository.save(cargoToppingOffSequence);
            });
  }

  /**
   * Saves Loading Information
   *
   * @param loadingInformationDetail
   * @param loadingInformation
   * @return
   */
  private LoadingInformation saveLoadingInformation(
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

  /**
   * Deletes Loading Information
   *
   * @param vesselId
   * @param loadablePatternId
   */
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

  /**
   * Deletes Loadable Plan Details
   *
   * @param loadingInformation
   */
  private void deleteLoadablePlanDetails(LoadingInformation loadingInformation) {

    cargoToppingOffSequenceRepository.deleteByLoadingInformation(loadingInformation);
    loadablePlanBallastDetailsRepository.deleteByLoadingInformation(loadingInformation);
    loadablePlanCommingleDetailsRepository.deleteByLoadingInformation(loadingInformation);
    loadablePlanQuantityRepository.deleteByLoadingInformation(loadingInformation);
    loadablePlanStowageDetailsRepository.deleteByLoadingInformation(loadingInformation);
  }
}
