/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service;

import com.cpdss.common.generated.Common;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.dischargeplan.common.DischargePlanConstants;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import com.cpdss.dischargeplan.entity.PortDischargingPlanStowageDetails;
import com.cpdss.dischargeplan.repository.PortDischargingPlanStowageDetailsRepository;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DischargeCargoHistoryService {

  @Autowired DischargeInformationService dischargeInformationService;

  @Autowired PortDischargingPlanStowageDetailsRepository stowageDetailsRepository;

  public void buildCargoDetailsFromStowageData(
      Common.CargoHistoryOpsRequest request, Common.CargoHistoryResponse.Builder builder) {
    List<DischargeInformation> infoList =
        dischargeInformationService.getAllDischargeInformation(
            request.getVesselId(), request.getVoyageId());
    if (!infoList.isEmpty()) {
      for (DischargeInformation info : infoList) {
        // only needed Stowage details for departure condition, actual value
        var stowageList = stowageDetailsRepository.findCargoHistoryData(info.getId());
        for (PortDischargingPlanStowageDetails data : stowageList) {
          Common.CargoHistoryOps.Builder builder1 = Common.CargoHistoryOps.newBuilder();
          Optional.ofNullable(data.getTankXId()).ifPresent(builder1::setTankId);
          Optional.ofNullable(data.getApi()).ifPresent(v -> builder1.setApi(v.toString()));
          Optional.ofNullable(data.getTemperature())
              .ifPresent(v -> builder1.setTemperature(v.toString()));
          Optional.ofNullable(data.getCargoNominationXId())
              .ifPresent(builder1::setCargoNominationId);
          Optional.ofNullable(data.getCargoXId()).ifPresent(builder1::setCargoId);
          Optional.ofNullable(data.getPortXId()).ifPresent(builder1::setPortId);
          Optional.ofNullable(data.getQuantity())
              .ifPresent(v -> builder1.setQuantity(v.toString()));
          Optional.ofNullable(data.getLastModifiedDateTime())
              .ifPresent(v -> builder1.setUpdateDate(v.toString()));
          builder.addCargoHistory(builder1.build());
        }
      }
    }
    builder.setResponseStatus(
        Common.ResponseStatus.newBuilder()
            .setStatus(DischargePlanConstants.SUCCESS)
            .setCode(HttpStatusCode.OK.getReasonPhrase())
            .build());
  }
}
