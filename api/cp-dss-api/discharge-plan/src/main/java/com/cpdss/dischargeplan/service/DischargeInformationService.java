/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service;

import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.discharge_plan.DischargeInformationRequest;
import com.cpdss.dischargeplan.common.DischargePlanConstants;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import com.cpdss.dischargeplan.repository.DischargeInformationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DischargeInformationService {

  @Autowired DischargeInformationRepository dischargeInformationRepository;

  @Autowired DischargeInformationBuilderService informationBuilderService;

  public DischargeInformation getDischargeInformation(Long primaryKey) {
    return this.dischargeInformationRepository.findByIdAndIsActiveTrue(primaryKey).orElse(null);
  }

  public DischargeInformation getDischargeInformation(
      Long vesselId, Long voyage, Long portRotationId) {
    return this.dischargeInformationRepository
        .findByVesselXidAndVoyageXidAndPortRotationXidAndIsActiveTrue(
            vesselId, voyage, portRotationId)
        .orElse(null);
  }

  public void getDischargeInformation(
      DischargeInformationRequest request,
      com.cpdss.common.generated.discharge_plan.DischargeInformation.Builder builder) {
    DischargeInformation disEntity =
        this.getDischargeInformation(
            request.getVesselId(), request.getVoyageId(), request.getPortRotationId());
    if (disEntity == null) {
      log.error(
          "Discharge information not found for Vessel Id {}, Voyage Id {}, PortR Id {}",
          request.getVesselId(),
          request.getVoyageId(),
          request.getPortRotationId());
    }

    try {
      builder.setDischargeInfoId(disEntity.getId());
      builder.setSynopticTableId(disEntity.getSynopticTableXid());
      log.info("Setting Discharge PK and Synoptic Id");
    } catch (Exception e) {
      log.error("Failed to set PK, Synoptic Id in response - {}", e.getMessage());
    }

    // Set Discharge Details
    this.informationBuilderService.buildDischargeDetailsMessageFromEntity(disEntity, builder);

    // Set Discharge Rates
    this.informationBuilderService.buildDischargeRateMessageFromEntity(disEntity, builder);

    builder.setResponseStatus(
        Common.ResponseStatus.newBuilder()
            .setHttpStatusCode(HttpStatus.OK.value())
            .setStatus(DischargePlanConstants.SUCCESS)
            .build());
  }
}
