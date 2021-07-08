/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static com.cpdss.loadablestudy.service.LoadableStudyService.SYNOPTICAL_TABLE_OP_TYPE_ARRIVAL;
import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.*;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadablestudy.entity.CargoNomination;
import com.cpdss.loadablestudy.entity.LoadablePlanQuantity;
import com.cpdss.loadablestudy.entity.LoadableStudyPortRotation;
import com.cpdss.loadablestudy.entity.SynopticalTable;
import com.cpdss.loadablestudy.repository.CargoNominationRepository;
import com.cpdss.loadablestudy.repository.LoadablePlanQuantityRepository;
import com.cpdss.loadablestudy.repository.SynopticalTableRepository;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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

  @Autowired LoadablePlanQuantityRepository loadablePlanQuantityRepository;

  @Autowired CargoNominationRepository cargoNominationRepository;

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

    // Cargo details based on port, and operation type
    this.buildCargoToBeLoadedForPort(request, builder, repBuilder);
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

  private void buildCargoToBeLoadedForPort(
      LoadableStudy.LoadingPlanIdRequest request,
      LoadableStudy.LoadingPlanCommonResponse.Builder builder,
      Common.ResponseStatus.Builder repBuilder) {

    List<LoadablePlanQuantity> list =
        this.loadablePlanQuantityRepository.PORT_WISE_CARGO_DETAILS(
            request.getPatternId(),
            request.getOperationType(),
            request.getPortRotationId(),
            request.getPortId());

    if (!list.isEmpty()) {
      for (LoadablePlanQuantity var1 : list) {
        LoadableStudy.LoadableQuantityCargoDetails.Builder builder1 =
            LoadableStudy.LoadableQuantityCargoDetails.newBuilder();
        Optional.ofNullable(var1.getId()).ifPresent(builder1::setId);
        Optional.ofNullable(var1.getGrade()).ifPresent(builder1::setGrade);
        Optional.ofNullable(var1.getEstimatedApi())
            .ifPresent(v -> builder1.setEstimatedAPI(v.toString()));
        Optional.ofNullable(var1.getEstimatedTemperature())
            .ifPresent(v -> builder1.setEstimatedTemp(v.toString()));
        Optional.ofNullable(var1.getCargoNominationTemperature())
            .ifPresent(v -> builder1.setCargoNominationTemperature(v.toString()));

        Optional.ofNullable(var1.getOrderBblsDbs()).ifPresent(builder1::setOrderBblsdbs);
        Optional.ofNullable(var1.getOrderBbls60f()).ifPresent(builder1::setOrderBbls60F);
        Optional.ofNullable(var1.getMinTolerence()).ifPresent(builder1::setMinTolerence);
        Optional.ofNullable(var1.getMaxTolerence()).ifPresent(builder1::setMaxTolerence);
        Optional.ofNullable(var1.getLoadableBblsDbs()).ifPresent(builder1::setLoadableBblsdbs);
        Optional.ofNullable(var1.getLoadableBbls60f()).ifPresent(builder1::setLoadableBbls60F);

        Optional.ofNullable(var1.getLoadableLt()).ifPresent(builder1::setLoadableLT);
        Optional.ofNullable(var1.getLoadableMt()).ifPresent(builder1::setLoadableMT);
        Optional.ofNullable(var1.getLoadableKl()).ifPresent(builder1::setLoadableKL);
        Optional.ofNullable(var1.getDifferencePercentage())
            .ifPresent(builder1::setDifferencePercentage);
        Optional.ofNullable(var1.getDifferenceColor()).ifPresent(builder1::setDifferenceColor);

        Optional.ofNullable(var1.getCargoXId()).ifPresent(builder1::setCargoId);
        Optional.ofNullable(var1.getCargoAbbreviation()).ifPresent(builder1::setCargoAbbreviation);
        Optional.ofNullable(var1.getCargoColor()).ifPresent(builder1::setColorCode);
        Optional.ofNullable(var1.getPriority()).ifPresent(builder1::setPriority);
        Optional.ofNullable(var1.getLoadingOrder()).ifPresent(builder1::setLoadingOrder);
        Optional.ofNullable(var1.getSlopQuantity()).ifPresent(builder1::setSlopQuantity);

        Optional.ofNullable(var1.getOrderQuantity())
            .ifPresent(
                v -> {
                  builder1.setOrderQuantity(v.toString());
                });
        Optional.ofNullable(this.getCargoNominationQuantity(var1.getCargoNominationId()))
            .ifPresent(
                v -> {
                  builder1.setCargoNominationQuantity(v.toString());
                });

        Optional.ofNullable(var1.getCargoNominationId()).ifPresent(builder1::setCargoNominationId);
        Optional.ofNullable(var1.getTimeRequiredForLoading())
            .ifPresent(builder1::setTimeRequiredForLoading);
        // loading port details no need
        // cargo topping off no need
        builder.addLoadableQuantityCargoDetails(builder1.build());
      }
    }
    builder.setResponseStatus(repBuilder.setStatus(SUCCESS).build());
  }

  private BigDecimal getCargoNominationQuantity(Long cargoNominationId) {
    try {
      Optional<CargoNomination> var1 =
          cargoNominationRepository.findByIdAndIsActive(cargoNominationId, true);
      if (var1.isPresent()) {
        if (var1.get().getQuantity() != null) {
          return var1.get().getQuantity();
        }
      }
    } catch (Exception e) {
      log.error("Failed to get cargo nomination quantity");
    }
    return BigDecimal.ZERO;
  }

  /**
   * Save data from loading information like timeOfSunrise, timeOfSunset etc to SynopticalTable
   *
   * @param request
   * @throws Exception
   */
  public void saveLoadingInformationToSynopticalTable(
      LoadableStudy.LoadingInfoSynopticalUpdateRequest request) throws Exception {
    ResponseStatus.Builder builder = ResponseStatus.newBuilder();
    Optional<SynopticalTable> synopticalOpt =
        this.synopticalTableRepository.findByIdAndIsActive(request.getSynopticalTableId(), true);
    if (synopticalOpt.isPresent()) {
      SynopticalTable table = synopticalOpt.get();
      if (!StringUtils.isEmpty(request.getTimeOfSunrise())) {
        table.setTimeOfSunrise(
            LocalTime.from(DateTimeFormatter.ofPattern("HH:mm").parse(request.getTimeOfSunrise())));
      }
      if (!StringUtils.isEmpty(request.getTimeOfSunset())) {
        table.setTimeOfSunSet(
            LocalTime.from(DateTimeFormatter.ofPattern("HH:mm").parse(request.getTimeOfSunset())));
      }
      this.synopticalTableRepository.save(table);
    } else
      throw new Exception("Cannot find synoptical table with id " + request.getSynopticalTableId());
  }

  /**
   * Builds the port info in synoptical table
   * @param entity
   * @param requestedOperationId
   * @param requestedPortId
   */
  public void buildPortsInfoSynopticalTable(
          LoadableStudyPortRotation entity, Long requestedOperationId, Long requestedPortId) {
    // build ports information to update synoptical table
    if (requestedOperationId != 0
            && !StringUtils.isEmpty(
            com.cpdss.loadablestudy.domain.CargoOperation.getOperation(requestedOperationId))) {
      List<SynopticalTable> synopticalTableEntityList = new ArrayList<>();
      if (CARGO_OPERATION_ARR_DEP_SYNOPTICAL.contains(requestedOperationId)) {
        buildSynopticalTableRecord(
                requestedPortId, entity, synopticalTableEntityList, SYNOPTICAL_TABLE_OP_TYPE_ARRIVAL);
        buildSynopticalTableRecord(
                requestedPortId, entity, synopticalTableEntityList, SYNOPTICAL_TABLE_OP_TYPE_DEPARTURE);
      }
      if (!CollectionUtils.isEmpty(entity.getSynopticalTable())) {
        entity.getSynopticalTable().addAll(synopticalTableEntityList);
      } else {
        entity.setSynopticalTable(synopticalTableEntityList);
      }
    }
  }

  /**
   * Builds the synoptical table records
   *
   * @param portId
   * @param entity
   * @param synopticalTableList
   * @param portStage
   */
  private void buildSynopticalTableRecord(
          Long portId,
          LoadableStudyPortRotation entity,
          List<SynopticalTable> synopticalTableList,
          String portStage) {
    SynopticalTable synopticalTable = new SynopticalTable();
    synopticalTable.setLoadableStudyPortRotation(entity);
    synopticalTable.setLoadableStudyXId(entity.getLoadableStudy().getId());
    synopticalTable.setOperationType(portStage);
    synopticalTable.setPortXid(0 == portId ? null : portId);
    synopticalTable.setIsActive(true);
    synopticalTableList.add(synopticalTable);
  }
}
