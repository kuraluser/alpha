/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.*;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadablestudy.entity.SynopticalTable;
import com.cpdss.loadablestudy.repository.SynopticalTableRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Master Service For Synoptic Related Operations
 *
 * @author Johnsooraj.X
 * @since 27-05-2021
 */
@Slf4j
@Service
public class SynopticService {

  @Autowired SynopticalTableRepository synopticalTableRepository;

  public void fetchLoadingInformationSynopticDetails(
      LoadableStudy.LoadingPlanIdRequest request,
      LoadableStudy.LoadingPlanCommonResponse.Builder builder,
      Common.ResponseStatus.Builder repBuilder)
      throws GenericServiceException {
    Long id = request.getId();
    switch (request.getIdType()) {
      case "PORT_ROTATION":
        this.buildPortRotationResponse(id, builder, repBuilder);
        break;
      default:
        log.info("Synoptic Data for Loading Plan Default Case");
        break;
    }
  }

  // Single Entry with the Operation Type - ARR
  private void buildPortRotationResponse(
      Long id,
      LoadableStudy.LoadingPlanCommonResponse.Builder builder,
      Common.ResponseStatus.Builder repBuilder)
      throws GenericServiceException {
    List<SynopticalTable> list = synopticalTableRepository.findAllByPortRotationId(id);
    if (list.isEmpty()) {
      log.error(
          "Synoptic Data Not Found for Port Rotation Id {}, Operation Type {}",
          id,
          OPERATION_TYPE_ARR);
      throw new GenericServiceException(
          "Synoptic Data Not Found for Port Rotation Id " + id + ", Type ARR",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    log.info(
        "Synoptic Record Found for Port Rotation Id {}, Ids {}",
        id,
        list.stream().map(SynopticalTable::getId).collect(Collectors.toList()));
    try {
      for (SynopticalTable data : list) {
        LoadableStudy.LoadingSynopticResponse.Builder builder1 =
            LoadableStudy.LoadingSynopticResponse.newBuilder();
        Optional.ofNullable(data.getId()).ifPresent(builder1::setSynopticTableId);
        Optional.ofNullable(data.getLoadableStudyXId()).ifPresent(builder1::setLoadableStudyId);
        if (data.getLoadableStudyPortRotation() != null) {
          Optional.ofNullable(data.getLoadableStudyPortRotation().getId())
              .ifPresent(builder1::setPortRotationId);
          Optional.ofNullable(data.getPortXid()).ifPresent(builder1::setPortId);
        }
        Optional.ofNullable(data.getOperationType()).ifPresent(builder1::setOperationType);
        Optional.ofNullable(data.getTimeOfSunrise())
            .ifPresent(v -> builder1.setTimeOfSunrise(TIME_FORMATTER.format(v)));
        Optional.ofNullable(data.getTimeOfSunSet())
            .ifPresent(v -> builder1.setTimeOfSunset(TIME_FORMATTER.format(v)));
        builder.addSynopticData(builder1.build());
        builder.setResponseStatus(repBuilder.setStatus(SUCCESS).build());
      }
    } catch (Exception e) {
      builder.setResponseStatus(repBuilder.setStatus(FAILED).setMessage(e.getMessage()).build());
      e.printStackTrace();
      log.info("Failed to build synoptic data, {}", e.getMessage());
      throw new GenericServiceException(
          "Synoptic Data Builder Exception",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
  }
}
