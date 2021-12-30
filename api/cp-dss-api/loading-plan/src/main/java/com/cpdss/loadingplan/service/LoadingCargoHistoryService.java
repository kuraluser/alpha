/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service;

import static com.cpdss.loadingplan.common.LoadingPlanConstants.SUCCESS;

import com.cpdss.common.generated.Common;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.entity.PortLoadingPlanStowageDetails;
import com.cpdss.loadingplan.repository.PortLoadingPlanStowageDetailsRepository;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoadingCargoHistoryService {

  @Autowired LoadingInformationService loadingInformationService;

  @Autowired PortLoadingPlanStowageDetailsRepository stowageDetailsRepository;

  /** */
  public void buildCargoDetailsFromStowageData(
      Common.CargoHistoryOpsRequest request, Common.CargoHistoryResponse.Builder builder) {
    List<LoadingInformation> infoList =
        loadingInformationService.getAllLoadingInfoByVoyageId(
            request.getVesselId(), request.getVoyageId());
    if (!infoList.isEmpty()) {
      for (LoadingInformation info : infoList) {
        // Only needed Stowage details for departure condition, actual value
        var stowageList = stowageDetailsRepository.findCargoHistoryData(info.getId());
        for (PortLoadingPlanStowageDetails data : stowageList) {
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
            .setStatus(SUCCESS)
            .setCode(HttpStatusCode.OK.getReasonPhrase())
            .build());
  }
}
