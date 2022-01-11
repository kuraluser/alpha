/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service;

import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import com.cpdss.dischargeplan.entity.DischargingCargoApiTemperature;
import com.cpdss.dischargeplan.repository.DischargingCargoToBeDischargedRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class to implement methods related to cargo api, temperature and max discharging rate
 *
 * @author sreemanikandan.k
 * @since 10/01/2022
 */
@Slf4j
@Service
@Transactional
public class DischargingCargoToBeDischargedService {

  @Autowired
  private DischargingCargoToBeDischargedRepository dischargingCargoToBeDischargedRepository;

  /**
   * Method to save cargo to be discharged details
   *
   * @param dischargeQuantityCargoDetailsList List of cargo to be discharged details
   * @param dischargeInformation Discharge Information object
   */
  public void saveCargoToBeDischarged(
      List<LoadableStudy.DischargeQuantityCargoDetails> dischargeQuantityCargoDetailsList,
      DischargeInformation dischargeInformation) {

    log.info("Inside saveCargoToBeDischarged method!");
    List<DischargingCargoApiTemperature> dischargingCargoApiTemperatures = new ArrayList<>();

    dischargeQuantityCargoDetailsList.forEach(
        dischargeQuantityCargoDetails -> {
          Optional<DischargingCargoApiTemperature> dischargingCargoApiTemperatureWrapper =
              dischargingCargoToBeDischargedRepository
                  .findByDischargeInformationAndCargoNominationIdAndIsActiveTrue(
                      dischargeInformation, dischargeQuantityCargoDetails.getCargoNominationId());
          DischargingCargoApiTemperature dischargingCargoApiTemperature;

          // Create new entry of entity in table if not present
          if (dischargingCargoApiTemperatureWrapper.isEmpty()) {
            dischargingCargoApiTemperature = new DischargingCargoApiTemperature();
          } else { // Fetch existing
            dischargingCargoApiTemperature = dischargingCargoApiTemperatureWrapper.get();
          }
          buildDischargingCargoApiTemperature(
              dischargingCargoApiTemperature, dischargeQuantityCargoDetails, dischargeInformation);
          dischargingCargoApiTemperatures.add(dischargingCargoApiTemperature);
        });
    dischargingCargoToBeDischargedRepository.saveAll(dischargingCargoApiTemperatures);
  }

  /**
   * Method to fetch cargo to be discharged details
   *
   * @param dischargeInformation DischargeInformation input
   * @param builder DischargeInformation.Builder input to be set
   */
  public void getCargoToBeDischarged(
      DischargeInformation dischargeInformation,
      com.cpdss.common.generated.discharge_plan.DischargeInformation.Builder builder) {

    log.info("Inside getCargoToBeDischarged method!");

    // Fetch from repo
    List<DischargingCargoApiTemperature> dischargingCargoApiTemperatures =
        dischargingCargoToBeDischargedRepository.findByDischargeInformationAndIsActiveTrue(
            dischargeInformation);
    buildDischargeInformationResponse(dischargingCargoApiTemperatures, builder);
  }

  /**
   * Method to build cargo to be discharged details
   *
   * @param dischargingCargoApiTemperatures List of DischargingCargoApiTemperature input
   * @param builder DischargeInformation.Builder input to be set
   */
  public void buildDischargeInformationResponse(
      List<DischargingCargoApiTemperature> dischargingCargoApiTemperatures,
      com.cpdss.common.generated.discharge_plan.DischargeInformation.Builder builder) {

    log.info("Inside buildDischargeInformationResponse method!");

    // Set fields
    dischargingCargoApiTemperatures.forEach(
        dischargingCargoApiTemperature -> {
          LoadableStudy.DischargeQuantityCargoDetails.Builder apiBuilder =
              LoadableStudy.DischargeQuantityCargoDetails.newBuilder();

          Optional.ofNullable(dischargingCargoApiTemperature.getApi())
              .ifPresent(api -> apiBuilder.setEstimatedApi(String.valueOf(api)));
          Optional.ofNullable(dischargingCargoApiTemperature.getTemperature())
              .ifPresent(temp -> apiBuilder.setEstimatedTemp(String.valueOf(temp)));
          Optional.ofNullable(dischargingCargoApiTemperature.getMaxDischargingRate())
              .ifPresent(rate -> apiBuilder.setMaxDischargingRate(String.valueOf(rate)));
          Optional.ofNullable(dischargingCargoApiTemperature.getCargoNominationId())
              .ifPresent(apiBuilder::setCargoNominationId);

          builder.addDischargeQuantityCargoDetails(apiBuilder.build());
        });
  }

  /**
   * Method to build Cargo api, temp and max discharging rate details
   *
   * @param dischargingCargoApiTemperature DischargingCargoApiTemperature object to be set and saved
   * @param dischargeQuantityCargoDetails Object containing cargo api details
   * @param dischargeInformation DischargeInformation object
   */
  public void buildDischargingCargoApiTemperature(
      DischargingCargoApiTemperature dischargingCargoApiTemperature,
      LoadableStudy.DischargeQuantityCargoDetails dischargeQuantityCargoDetails,
      DischargeInformation dischargeInformation) {

    log.info("Inside buildDischargingCargoApiTemperature method!");

    // Set fields
    Optional.of(dischargeQuantityCargoDetails.getEstimatedApi())
        .ifPresent(api -> dischargingCargoApiTemperature.setApi(new BigDecimal(api)));
    Optional.of(dischargeQuantityCargoDetails.getEstimatedTemp())
        .ifPresent(
            temperature ->
                dischargingCargoApiTemperature.setTemperature(new BigDecimal(temperature)));
    Optional.of(dischargeQuantityCargoDetails.getMaxDischargingRate())
        .ifPresent(
            maxDischargingRate ->
                dischargingCargoApiTemperature.setMaxDischargingRate(
                    new BigDecimal(maxDischargingRate)));
    Optional.ofNullable(dischargeInformation)
        .ifPresent(dischargingCargoApiTemperature::setDischargeInformation);
    Optional.of(dischargeQuantityCargoDetails.getCargoNominationId())
        .ifPresent(dischargingCargoApiTemperature::setCargoNominationId);
    dischargingCargoApiTemperature.setIsActive(true);
  }
}
