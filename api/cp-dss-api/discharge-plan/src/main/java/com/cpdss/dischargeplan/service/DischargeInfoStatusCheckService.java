/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.discharge_plan.DischargeInfoStatusReply;
import com.cpdss.common.generated.discharge_plan.DischargeInfoStatusRequest;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.dischargeplan.entity.DischargingInformationAlgoStatus;
import com.cpdss.dischargeplan.repository.DischargeInformationStatusRepository;
import com.cpdss.dischargeplan.repository.DischargingInformationAlgoStatusRepository;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DischargeInfoStatusCheckService {

  @Autowired DischargeInformationStatusRepository dischargeInformationStatusRepository;

  @Autowired DischargingInformationAlgoStatusRepository algoStatusRepository;

  public void checkStatus(
      DischargeInfoStatusRequest request, DischargeInfoStatusReply.Builder builder)
      throws GenericServiceException {
    Optional<DischargingInformationAlgoStatus> data = Optional.empty();
    log.info(
        "Get Status for Discharge info - {}, processId - {} and condition type - {}",
        request.getDischargeInfoId(),
        request.getProcessId(),
        request.getConditionType());
    if (request.getConditionType() == 0) {
      data =
          this.algoStatusRepository.findByProcessIdAndDischargeInformationIdAndIsActiveTrue(
              request.getProcessId(), request.getDischargeInfoId());
    } else {
      data =
          this.algoStatusRepository
              .findByProcessIdAndDischargeInformationIdAndConditionTypeAndIsActiveTrue(
                  request.getProcessId(), request.getDischargeInfoId(), request.getConditionType());
    }
    if (data.isEmpty()) {
      throw new GenericServiceException(
          "Could not find discharge info status for discharge information "
              + request.getDischargeInfoId(),
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    builder.setDischargeInfoId(request.getDischargeInfoId());
    builder.setDischargeInfoStatusId(data.get().getDischargingInformationStatus().getId());
    builder.setDischargeInfoStatusLastModifiedTime(data.get().getLastModifiedDateTime().toString());
    log.info(
        "Get Status for Discharge info - {}, status - {}",
        request.getDischargeInfoId(),
        builder.getDischargeInfoStatusId());
  }
}
