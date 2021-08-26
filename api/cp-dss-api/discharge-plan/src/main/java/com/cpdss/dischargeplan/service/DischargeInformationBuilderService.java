/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service;

import com.cpdss.common.generated.discharge_plan.DischargeDetails;
import com.cpdss.common.generated.discharge_plan.DischargeRates;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DischargeInformationBuilderService {

  public void buildDischargeDetailsMessageFromEntity(
      DischargeInformation disEntity,
      com.cpdss.common.generated.discharge_plan.DischargeInformation.Builder builder) {
    try {
      DischargeDetails.Builder builder1 = DischargeDetails.newBuilder();
      builder1.setId(disEntity.getId());
      Optional.ofNullable(disEntity.getSunriseTime())
          .ifPresent(v -> builder1.setTimeOfSunrise(v.toString()));
      Optional.ofNullable(disEntity.getSunsetTime())
          .ifPresent(v -> builder1.setTimeOfSunset(v.toString()));
      Optional.ofNullable(disEntity.getStartTime())
          .ifPresent(v -> builder1.setStartTime(v.toString()));
      builder1.setVoyageId(disEntity.getVoyageXid());

      // Set Trim Values
      LoadingPlanModels.TrimAllowed.Builder trimAllowed =
          LoadingPlanModels.TrimAllowed.newBuilder();
      Optional.ofNullable(disEntity.getInitialTrim())
          .ifPresent(v -> trimAllowed.setInitialTrim(v.toString()));
      Optional.ofNullable(disEntity.getMaximumTrim())
          .ifPresent(v -> trimAllowed.setMaximumTrim(v.toString()));
      Optional.ofNullable(disEntity.getFinalTrim())
          .ifPresent(v -> trimAllowed.setFinalTrim(v.toString()));

      builder1.setTrimAllowed(trimAllowed.build());
      builder.setDischargeDetails(builder1.build());
      log.info("Setting Discharge Details");
    } catch (Exception e) {
      log.error("Failed to set Discharge Details {}", e.getMessage());
      e.printStackTrace();
    }
  }

  public void buildDischargeRateMessageFromEntity(
      DischargeInformation disEntity,
      com.cpdss.common.generated.discharge_plan.DischargeInformation.Builder builder) {
    try {
      DischargeRates.Builder builder1 = DischargeRates.newBuilder();
      Optional.ofNullable(disEntity.getInitialDischargingRate())
          .ifPresent(v -> builder1.setInitialDischargeRate(v.toString()));
      Optional.ofNullable(disEntity.getMaxDischargingRate())
          .ifPresent(v -> builder1.setMaxDischargeRate(v.toString()));
      Optional.ofNullable(disEntity.getMinBallastRate())
          .ifPresent(v -> builder1.setMinBallastRate(v.toString()));
      Optional.ofNullable(disEntity.getMaxBallastRate())
          .ifPresent(v -> builder1.setMaxBallastRate(v.toString()));

      builder.setDischargeRate(builder1.build());
      log.info("Setting Discharge Rates");
    } catch (Exception e) {
      log.error("Failed to set Discharge Rates {}", e.getMessage());
      e.printStackTrace();
    }
  }
}
