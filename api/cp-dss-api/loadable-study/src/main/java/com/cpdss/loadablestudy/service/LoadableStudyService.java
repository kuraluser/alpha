/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.service;

import static java.lang.String.valueOf;
import static org.springframework.util.StringUtils.isEmpty;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.LoadableStudy.AlgoReply;
import com.cpdss.common.generated.LoadableStudy.AlgoRequest;
import com.cpdss.common.generated.LoadableStudy.AlgoStatusReply;
import com.cpdss.common.generated.LoadableStudy.AlgoStatusRequest;
import com.cpdss.common.generated.LoadableStudy.CargoNominationDetail;
import com.cpdss.common.generated.LoadableStudy.CargoNominationReply;
import com.cpdss.common.generated.LoadableStudy.CargoNominationRequest;
import com.cpdss.common.generated.LoadableStudy.CommingleCargo;
import com.cpdss.common.generated.LoadableStudy.CommingleCargoReply;
import com.cpdss.common.generated.LoadableStudy.CommingleCargoRequest;
import com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply;
import com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest;
import com.cpdss.common.generated.LoadableStudy.LoadablePatternCargoDetails;
import com.cpdss.common.generated.LoadableStudy.LoadablePatternCommingleDetailsReply;
import com.cpdss.common.generated.LoadableStudy.LoadablePatternCommingleDetailsRequest;
import com.cpdss.common.generated.LoadableStudy.LoadablePatternReply;
import com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest;
import com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply;
import com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest;
import com.cpdss.common.generated.LoadableStudy.LoadableQuantityCargoDetails;
import com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply;
import com.cpdss.common.generated.LoadableStudy.LoadableQuantityRequest;
import com.cpdss.common.generated.LoadableStudy.LoadableQuantityResponse;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyAttachment;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyReply;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyReply.Builder;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyStatusReply;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyStatusRequest;
import com.cpdss.common.generated.LoadableStudy.LoadingPortDetail;
import com.cpdss.common.generated.LoadableStudy.OnBoardQuantityDetail;
import com.cpdss.common.generated.LoadableStudy.OnBoardQuantityReply;
import com.cpdss.common.generated.LoadableStudy.OnBoardQuantityRequest;
import com.cpdss.common.generated.LoadableStudy.OnHandQuantityDetail;
import com.cpdss.common.generated.LoadableStudy.OnHandQuantityReply;
import com.cpdss.common.generated.LoadableStudy.OnHandQuantityRequest;
import com.cpdss.common.generated.LoadableStudy.Operation;
import com.cpdss.common.generated.LoadableStudy.PortRotationDetail;
import com.cpdss.common.generated.LoadableStudy.PortRotationReply;
import com.cpdss.common.generated.LoadableStudy.PortRotationRequest;
import com.cpdss.common.generated.LoadableStudy.PurposeOfCommingleReply;
import com.cpdss.common.generated.LoadableStudy.PurposeOfCommingleRequest;
import com.cpdss.common.generated.LoadableStudy.StatusReply;
import com.cpdss.common.generated.LoadableStudy.SynopticalCargoRecord;
import com.cpdss.common.generated.LoadableStudy.SynopticalDataReply;
import com.cpdss.common.generated.LoadableStudy.SynopticalDataRequest;
import com.cpdss.common.generated.LoadableStudy.SynopticalOhqRecord;
import com.cpdss.common.generated.LoadableStudy.SynopticalRecord;
import com.cpdss.common.generated.LoadableStudy.SynopticalTableReply;
import com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest;
import com.cpdss.common.generated.LoadableStudy.TankDetail;
import com.cpdss.common.generated.LoadableStudy.TankList;
import com.cpdss.common.generated.LoadableStudy.ValveSegregation;
import com.cpdss.common.generated.LoadableStudy.ValveSegregationReply;
import com.cpdss.common.generated.LoadableStudy.ValveSegregationRequest;
import com.cpdss.common.generated.LoadableStudy.VoyageDetail;
import com.cpdss.common.generated.LoadableStudy.VoyageListReply;
import com.cpdss.common.generated.LoadableStudy.VoyageReply;
import com.cpdss.common.generated.LoadableStudy.VoyageRequest;
import com.cpdss.common.generated.LoadableStudyServiceGrpc.LoadableStudyServiceImplBase;
import com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest;
import com.cpdss.common.generated.PortInfo.PortDetail;
import com.cpdss.common.generated.PortInfo.PortReply;
import com.cpdss.common.generated.PortInfoServiceGrpc.PortInfoServiceBlockingStub;
import com.cpdss.common.generated.VesselInfo.VesselLoadableQuantityDetails;
import com.cpdss.common.generated.VesselInfo.VesselReply;
import com.cpdss.common.generated.VesselInfo.VesselRequest;
import com.cpdss.common.generated.VesselInfo.VesselTankDetail;
import com.cpdss.common.generated.VesselInfoServiceGrpc.VesselInfoServiceBlockingStub;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadablestudy.domain.CargoHistory;
import com.cpdss.loadablestudy.domain.PortDetails;
import com.cpdss.loadablestudy.entity.CargoNomination;
import com.cpdss.loadablestudy.entity.CargoNominationPortDetails;
import com.cpdss.loadablestudy.entity.CargoNominationValveSegregation;
import com.cpdss.loadablestudy.entity.CargoOperation;
import com.cpdss.loadablestudy.entity.LoadablePattern;
import com.cpdss.loadablestudy.entity.LoadablePatternComingleDetails;
import com.cpdss.loadablestudy.entity.LoadablePatternDetails;
import com.cpdss.loadablestudy.entity.LoadablePlanQuantity;
import com.cpdss.loadablestudy.entity.LoadablePlanStowageBallastDetails;
import com.cpdss.loadablestudy.entity.LoadablePlanStowageDetails;
import com.cpdss.loadablestudy.entity.LoadableQuantity;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.entity.LoadableStudyAlgoStatus;
import com.cpdss.loadablestudy.entity.LoadableStudyAttachments;
import com.cpdss.loadablestudy.entity.LoadableStudyPortRotation;
import com.cpdss.loadablestudy.entity.OnBoardQuantity;
import com.cpdss.loadablestudy.entity.OnHandQuantity;
import com.cpdss.loadablestudy.entity.PurposeOfCommingle;
import com.cpdss.loadablestudy.entity.SynopticalTable;
import com.cpdss.loadablestudy.entity.SynopticalTableLoadicatorData;
import com.cpdss.loadablestudy.entity.Voyage;
import com.cpdss.loadablestudy.entity.VoyageHistory;
import com.cpdss.loadablestudy.repository.CargoHistoryRepository;
import com.cpdss.loadablestudy.repository.CargoNominationOperationDetailsRepository;
import com.cpdss.loadablestudy.repository.CargoNominationRepository;
import com.cpdss.loadablestudy.repository.CargoNominationValveSegregationRepository;
import com.cpdss.loadablestudy.repository.CargoOperationRepository;
import com.cpdss.loadablestudy.repository.CommingleCargoRepository;
import com.cpdss.loadablestudy.repository.LoadablePatternComingleDetailsRepository;
import com.cpdss.loadablestudy.repository.LoadablePatternDetailsRepository;
import com.cpdss.loadablestudy.repository.LoadablePatternRepository;
import com.cpdss.loadablestudy.repository.LoadablePlanCommingleDetailsRepository;
import com.cpdss.loadablestudy.repository.LoadablePlanQuantityRepository;
import com.cpdss.loadablestudy.repository.LoadablePlanStowageBallastDetailsRepository;
import com.cpdss.loadablestudy.repository.LoadablePlanStowageDetailsRespository;
import com.cpdss.loadablestudy.repository.LoadableQuantityRepository;
import com.cpdss.loadablestudy.repository.LoadableStudyAlgoStatusRepository;
import com.cpdss.loadablestudy.repository.LoadableStudyPortRotationRepository;
import com.cpdss.loadablestudy.repository.LoadableStudyRepository;
import com.cpdss.loadablestudy.repository.LoadableStudyStatusRepository;
import com.cpdss.loadablestudy.repository.OnBoardQuantityRepository;
import com.cpdss.loadablestudy.repository.OnHandQuantityRepository;
import com.cpdss.loadablestudy.repository.PurposeOfCommingleRepository;
import com.cpdss.loadablestudy.repository.SynopticalTableLoadicatorDataRepository;
import com.cpdss.loadablestudy.repository.SynopticalTableRepository;
import com.cpdss.loadablestudy.repository.VoyageHistoryRepository;
import com.cpdss.loadablestudy.repository.VoyageRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.stub.StreamObserver;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.server.service.GrpcService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/** @Author jerin.g */
@Log4j2
@GrpcService
@Service
@Transactional
public class LoadableStudyService extends LoadableStudyServiceImplBase {

  @Value("${loadablestudy.attachement.rooFolder}")
  private String rootFolder;

  @Autowired private VoyageRepository voyageRepository;
  @Autowired private LoadableStudyPortRotationRepository loadableStudyPortRotationRepository;
  @Autowired private CargoOperationRepository cargoOperationRepository;
  @Autowired private LoadableStudyRepository loadableStudyRepository;
  @Autowired private LoadableQuantityRepository loadableQuantityRepository;
  @Autowired private CargoNominationRepository cargoNominationRepository;
  @Autowired private CargoNominationValveSegregationRepository valveSegregationRepository;
  @Autowired private LoadableStudyStatusRepository loadableStudyStatusRepository;
  @Autowired private PurposeOfCommingleRepository purposeOfCommingleRepository;
  @Autowired private LoadablePatternDetailsRepository loadablePatternDetailsRepository;
  @Autowired private LoadablePatternRepository loadablePatternRepository;
  @Autowired private LoadablePlanQuantityRepository loadablePlanQuantityRepository;
  @Autowired private LoadablePlanCommingleDetailsRepository loadablePlanCommingleDetailsRepository;
  @Autowired private LoadablePlanStowageDetailsRespository loadablePlanStowageDetailsRespository;

  @Autowired
  private LoadablePlanStowageBallastDetailsRepository loadablePlanStowageBallastDetailsRepository;

  @Autowired
  private LoadablePatternComingleDetailsRepository loadablePatternComingleDetailsRepository;

  @Autowired
  private CargoNominationOperationDetailsRepository cargoNominationOperationDetailsRepository;

  @Autowired private OnHandQuantityRepository onHandQuantityRepository;
  @Autowired private CommingleCargoRepository commingleCargoRepository;

  @Autowired private OnBoardQuantityRepository onBoardQuantityRepository;
  @Autowired private CargoHistoryRepository cargoHistoryRepository;
  @Autowired private VoyageHistoryRepository voyageHistoryRepository;
  @Autowired private LoadableStudyAlgoStatusRepository loadableStudyAlgoStatusRepository;
  @Autowired private SynopticalTableRepository synopticalTableRepository;

  @Autowired
  private SynopticalTableLoadicatorDataRepository synopticalTableLoadicatorDataRepository;

  private static final String SUCCESS = "SUCCESS";
  private static final String FAILED = "FAILED";
  private static final String VOYAGEEXISTS = "VOYAGE_EXISTS";
  private static final String CREATED_DATE_FORMAT = "dd-MM-yyyy";
  private static final String INVALID_LOADABLE_QUANTITY = "INVALID_LOADABLE_QUANTITY";
  private static final String ETA_ETD_FORMAT = "dd-MM-yyyy HH:mm";
  private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm";
  private static final String LAY_CAN_FORMAT = "dd-MM-yyyy";
  private static final Long LOADING_OPERATION_ID = 1L;
  private static final Long DISCHARGING_OPERATION_ID = 2L;
  private static final Long BUNKERING_OPERATION_ID = 3L;
  private static final Long TRANSIT_OPERATION_ID = 4L;
  private static final Long LOADABLE_STUDY_INITIAL_STATUS_ID = 1L;
  private static final Long LOADABLE_STUDY_STATUS_PLAN_GENERATED_ID = 3L;
  private static final Long CONFIRMED_STATUS_ID = 2L;
  private static final String INVALID_LOADABLE_STUDY_ID = "INVALID_LOADABLE_STUDY_ID";
  private static final int CASE_1 = 1;
  private static final int CASE_2 = 2;
  private static final int CASE_3 = 3;
  private static final String INVALID_LOADABLE_PATTERN_COMMINGLE_DETAIL_ID =
      "INVALID_LOADABLE_PATTERN_COMMINGLE_DETAIL_ID";
  private static final String INVALID_LOADABLE_PATTERN_ID = "INVALID_LOADABLE_PATTERN_ID";
  private static final Long LOAD_LINE_TROPICAL_TO_SUMMER_ID = 7L;
  private static final Long LOAD_LINE_TROPICAL_TO_WINTER_ID = 8L;
  private static final Long LOAD_LINE_SUMMER_TO_WINTER_ID = 9L;

  private static final String INVALID_PROCESS_ID = "INVALID_PROCESS_ID";

  private static final List<Long> CASE_1_LOAD_LINES =
      Arrays.asList(
          LOAD_LINE_TROPICAL_TO_SUMMER_ID,
          LOAD_LINE_TROPICAL_TO_WINTER_ID,
          LOAD_LINE_SUMMER_TO_WINTER_ID);

  private static final Long FRESH_WATER_TANK_CATEGORY_ID = 3L;
  private static final Long FUEL_OIL_TANK_CATEGORY_ID = 5L;
  private static final Long DIESEL_OIL_TANK_CATEGORY_ID = 6L;
  private static final Long LUBRICATING_OIL_TANK_CATEGORY_ID = 14L;
  private static final Long LUBRICANT_OIL_TANK_CATEGORY_ID = 19L;
  private static final Long FUEL_VOID_TANK_CATEGORY_ID = 22L;
  private static final Long FRESH_WATER_VOID_TANK_CATEGORY_ID = 23L;

  private static final List<Long> OHQ_TANK_CATEGORIES =
      Arrays.asList(
          FRESH_WATER_TANK_CATEGORY_ID,
          FUEL_OIL_TANK_CATEGORY_ID,
          DIESEL_OIL_TANK_CATEGORY_ID,
          LUBRICATING_OIL_TANK_CATEGORY_ID,
          LUBRICANT_OIL_TANK_CATEGORY_ID,
          FUEL_VOID_TANK_CATEGORY_ID,
          FRESH_WATER_VOID_TANK_CATEGORY_ID);

  private static final List<Long> OHQ_CENTER_TANK_CATEGORIES =
      Arrays.asList(
          FUEL_OIL_TANK_CATEGORY_ID, DIESEL_OIL_TANK_CATEGORY_ID, FUEL_VOID_TANK_CATEGORY_ID);

  private static final List<Long> OHQ_REAR_TANK_CATEGORIES =
      Arrays.asList(FRESH_WATER_TANK_CATEGORY_ID, FRESH_WATER_VOID_TANK_CATEGORY_ID);

  private static final List<Long> OHQ_VOID_TANK_CATEGORIES =
      Arrays.asList(FUEL_VOID_TANK_CATEGORY_ID, FRESH_WATER_VOID_TANK_CATEGORY_ID);

  private static final Long CARGO_TANK_CATEGORY_ID = 1L;
  private static final Long CARGO_SLOP_TANK_CATEGORY_ID = 9L;
  private static final Long CARGO_VOID_TANK_CATEGORY_ID = 15L;

  private static final List<Long> CARGO_TANK_CATEGORIES =
      Arrays.asList(
          CARGO_TANK_CATEGORY_ID, CARGO_SLOP_TANK_CATEGORY_ID, CARGO_VOID_TANK_CATEGORY_ID);

  private static final List<Long> CARGO_OPERATION_ARR_DEP_SYNOPTICAL =
      Arrays.asList(LOADING_OPERATION_ID, DISCHARGING_OPERATION_ID, BUNKERING_OPERATION_ID);

  private static final List<Long> SYNOPTICAL_TABLE_TANK_CATEGORIES =
      Arrays.asList(
          CARGO_TANK_CATEGORY_ID,
          CARGO_SLOP_TANK_CATEGORY_ID,
          FRESH_WATER_TANK_CATEGORY_ID,
          FUEL_OIL_TANK_CATEGORY_ID,
          DIESEL_OIL_TANK_CATEGORY_ID,
          LUBRICATING_OIL_TANK_CATEGORY_ID,
          LUBRICANT_OIL_TANK_CATEGORY_ID);

  private static final String SYNOPTICAL_TABLE_OP_TYPE_ARRIVAL = "ARR";
  private static final String SYNOPTICAL_TABLE_OP_TYPE_DEPARTURE = "DEP";

  private static final String ACTUAL_SUM_MAP_SUFFIX = "_actual";
  private static final String PLANNED_SUM_MAP_SUFFIX = "_planned";

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @GrpcClient("portInfoService")
  private PortInfoServiceBlockingStub portInfoGrpcService;

  /**
   * method for save voyage
   *
   * @param request - voyage request details
   * @param responseObserver - grpc class
   * @return
   */
  @Override
  public void saveVoyage(VoyageRequest request, StreamObserver<VoyageReply> responseObserver) {
    VoyageReply reply = null;
    try {
      // validation for duplicate voyages
      if (!voyageRepository
          .findByCompanyXIdAndVesselXIdAndVoyageNoIgnoreCase(
              request.getCompanyId(), request.getVesselId(), request.getVoyageNo())
          .isEmpty()) {
        reply =
            VoyageReply.newBuilder()
                .setResponseStatus(
                    StatusReply.newBuilder()
                        .setStatus(FAILED)
                        .setMessage(VOYAGEEXISTS)
                        .setCode(CommonErrorCodes.E_CPDSS_VOYAGE_EXISTS))
                .build();
      } else {

        Voyage voyage = new Voyage();
        voyage.setIsActive(true);
        voyage.setCompanyXId(request.getCompanyId());
        voyage.setVesselXId(request.getVesselId());
        voyage.setVoyageNo(request.getVoyageNo());
        voyage.setCaptainXId(request.getCaptainId());
        voyage.setChiefOfficerXId(request.getChiefOfficerId());
        voyage.setVoyageStartDate(
            !StringUtils.isEmpty(request.getStartDate())
                ? LocalDateTime.from(
                    DateTimeFormatter.ofPattern(DATE_FORMAT).parse(request.getStartDate()))
                : null);
        voyage.setVoyageEndDate(
            !StringUtils.isEmpty(request.getEndDate())
                ? LocalDateTime.from(
                    DateTimeFormatter.ofPattern(DATE_FORMAT).parse(request.getEndDate()))
                : null);
        voyage = voyageRepository.save(voyage);
        // when Db save is complete we return to client a success message
        reply =
            VoyageReply.newBuilder()
                .setResponseStatus(StatusReply.newBuilder().setStatus(SUCCESS).setMessage(SUCCESS))
                .setVoyageId(voyage.getId())
                .build();
      }
    } catch (Exception e) {

      log.error("Error in saving Voyage ", e);
      reply =
          VoyageReply.newBuilder()
              .setResponseStatus(
                  StatusReply.newBuilder()
                      .setStatus(FAILED)
                      .setMessage(FAILED)
                      .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR))
              .build();

    } finally {
      responseObserver.onNext(reply);
      responseObserver.onCompleted();
    }
  }

  /**
   * method to save loadable quantity
   *
   * @param loadableQuantityRequest
   * @param responseObserver
   * @throws Exception
   * @return void
   */
  @Override
  public void saveLoadableQuantity(
      LoadableQuantityRequest loadableQuantityRequest,
      StreamObserver<LoadableQuantityReply> responseObserver) {
    LoadableQuantityReply loadableQuantityReply = null;
    try {
      Optional<LoadableStudy> loadableStudy =
          loadableStudyRepository.findById((Long) loadableQuantityRequest.getLoadableStudyId());
      if (loadableStudy.isPresent()) {
        LoadableQuantity loadableQuantity = new LoadableQuantity();
        loadableQuantity.setConstant(new BigDecimal(loadableQuantityRequest.getConstant()));
        loadableQuantity.setDeadWeight(new BigDecimal(loadableQuantityRequest.getDwt()));
        loadableQuantity.setDisplacementAtDraftRestriction(
            StringUtils.isEmpty(loadableQuantityRequest.getDisplacmentDraftRestriction())
                ? null
                : new BigDecimal(loadableQuantityRequest.getDisplacmentDraftRestriction()));
        loadableQuantity.setDistanceFromLastPort(
            StringUtils.isEmpty(loadableQuantityRequest.getDistanceFromLastPort())
                ? null
                : new BigDecimal(loadableQuantityRequest.getDistanceFromLastPort()));
        loadableQuantity.setEstimatedDOOnBoard(
            new BigDecimal(loadableQuantityRequest.getEstDOOnBoard()));
        loadableQuantity.setEstimatedFOOnBoard(
            new BigDecimal(loadableQuantityRequest.getEstFOOnBoard()));
        loadableQuantity.setEstimatedFWOnBoard(
            new BigDecimal(loadableQuantityRequest.getEstFreshWaterOnBoard()));
        loadableQuantity.setEstimatedSagging(
            new BigDecimal(loadableQuantityRequest.getEstSagging()));

        loadableQuantity.setEstimatedSeaDensity(
            StringUtils.isEmpty(loadableQuantityRequest.getEstSeaDensity())
                ? null
                : new BigDecimal(loadableQuantityRequest.getEstSeaDensity()));

        loadableQuantity.setLightWeight(
            StringUtils.isEmpty(loadableQuantityRequest.getVesselLightWeight())
                ? null
                : new BigDecimal(loadableQuantityRequest.getVesselLightWeight()));

        loadableQuantity.setLoadableStudyXId(loadableStudy.get());
        loadableQuantity.setOtherIfAny(new BigDecimal(loadableQuantityRequest.getOtherIfAny()));
        loadableQuantity.setSaggingDeduction(
            new BigDecimal(loadableQuantityRequest.getSaggingDeduction()));

        loadableQuantity.setSgCorrection(
            StringUtils.isEmpty(loadableQuantityRequest.getSgCorrection())
                ? null
                : new BigDecimal(loadableQuantityRequest.getSgCorrection()));

        loadableQuantity.setTotalQuantity(
            new BigDecimal(loadableQuantityRequest.getTotalQuantity()));
        loadableQuantity.setTpcatDraft(new BigDecimal(loadableQuantityRequest.getTpc()));
        loadableQuantity.setVesselAverageSpeed(
            StringUtils.isEmpty(loadableQuantityRequest.getVesselAverageSpeed())
                ? null
                : new BigDecimal(loadableQuantityRequest.getVesselAverageSpeed()));

        loadableQuantity.setPortId(
            StringUtils.isEmpty(loadableQuantityRequest.getPortId())
                ? null
                : new BigDecimal(loadableQuantityRequest.getPortId()));
        loadableQuantity.setBoilerWaterOnBoard(
            StringUtils.isEmpty(loadableQuantityRequest.getBoilerWaterOnBoard())
                ? null
                : new BigDecimal(loadableQuantityRequest.getBoilerWaterOnBoard()));
        loadableQuantity.setBallast(
            StringUtils.isEmpty(loadableQuantityRequest.getBallast())
                ? null
                : new BigDecimal(loadableQuantityRequest.getBallast()));
        loadableQuantity.setRunningHours(
            StringUtils.isEmpty(loadableQuantityRequest.getRunningHours())
                ? null
                : new BigDecimal(loadableQuantityRequest.getRunningHours()));
        loadableQuantity.setRunningDays(
            StringUtils.isEmpty(loadableQuantityRequest.getRunningDays())
                ? null
                : new BigDecimal(loadableQuantityRequest.getRunningDays()));
        loadableQuantity.setFoConsumptionInSZ(
            StringUtils.isEmpty(loadableQuantityRequest.getFoConInSZ())
                ? null
                : new BigDecimal(loadableQuantityRequest.getFoConInSZ()));
        loadableQuantity.setDraftRestriction(
            StringUtils.isEmpty(loadableQuantityRequest.getDraftRestriction())
                ? null
                : new BigDecimal(loadableQuantityRequest.getDraftRestriction()));

        loadableQuantity.setSubTotal(
            StringUtils.isEmpty(loadableQuantityRequest.getSubTotal())
                ? null
                : new BigDecimal(loadableQuantityRequest.getSubTotal()));
        loadableQuantity.setFoConsumptionPerDay(
            StringUtils.isEmpty(loadableQuantityRequest.getFoConsumptionPerDay())
                ? null
                : new BigDecimal(loadableQuantityRequest.getFoConsumptionPerDay()));
        loadableQuantity.setIsActive(true);
        loadableQuantity = loadableQuantityRepository.save(loadableQuantity);

        // when Db save is complete we return to client a success message
        loadableQuantityReply =
            LoadableQuantityReply.newBuilder()
                .setResponseStatus(StatusReply.newBuilder().setStatus(SUCCESS).setMessage(SUCCESS))
                .setLoadableQuantityId(loadableQuantity.getId())
                .build();
      } else {
        log.info("INVALID_LOADABLE_STUDY {} - ", loadableQuantityRequest.getLoadableStudyId());
        loadableQuantityReply =
            LoadableQuantityReply.newBuilder()
                .setResponseStatus(
                    StatusReply.newBuilder()
                        .setStatus(FAILED)
                        .setMessage(INVALID_LOADABLE_QUANTITY)
                        .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST))
                .build();
      }
    } catch (Exception e) {
      log.error("Error in saving loadable quantity ", e);
      loadableQuantityReply =
          LoadableQuantityReply.newBuilder()
              .setResponseStatus(
                  StatusReply.newBuilder()
                      .setStatus(FAILED)
                      .setMessage(FAILED)
                      .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR))
              .build();
    } finally {
      responseObserver.onNext(loadableQuantityReply);
      responseObserver.onCompleted();
    }
  }

  /**
   * Method to find list of loadable studies based on vessel and voyage
   *
   * @param {@link LoadableStudyRequest} - The grpc generated message
   * @param {@link StreamObserver}
   */
  @Override
  @Transactional
  public void findLoadableStudiesByVesselAndVoyage(
      LoadableStudyRequest request, StreamObserver<LoadableStudyReply> responseObserver) {
    Builder replyBuilder = LoadableStudyReply.newBuilder();
    try {
      log.info("inside loadable study service - findLoadableStudiesByVesselAndVoyage");
      Optional<Voyage> voyageOpt = this.voyageRepository.findById(request.getVoyageId());
      if (!voyageOpt.isPresent()) {
        throw new GenericServiceException(
            "Voyage does not exist", CommonErrorCodes.E_HTTP_BAD_REQUEST, null);
      }
      List<LoadableStudy> loadableStudyEntityList =
          this.loadableStudyRepository.findByVesselXIdAndVoyageAndIsActive(
              request.getVesselId(), voyageOpt.get(), true);
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
      DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(CREATED_DATE_FORMAT);
      for (LoadableStudy entity : loadableStudyEntityList) {
        com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail.Builder builder =
            LoadableStudyDetail.newBuilder();
        builder.setId(entity.getId());
        builder.setName(entity.getName());
        builder.setCreatedDate(dateTimeFormatter.format(entity.getCreatedDate()));
        Optional.ofNullable(entity.getLoadableStudyStatus())
            .ifPresent(
                status -> {
                  builder.setStatusId(status.getId());
                  builder.setStatus(status.getName());
                });
        Optional.ofNullable(entity.getDetails()).ifPresent(builder::setDetail);
        Optional.ofNullable(entity.getCharterer()).ifPresent(builder::setCharterer);
        Optional.ofNullable(entity.getSubCharterer()).ifPresent(builder::setSubCharterer);
        Optional.ofNullable(entity.getLoadLineXId()).ifPresent(builder::setLoadLineXId);
        Optional.ofNullable(entity.getDraftMark())
            .ifPresent(dratMark -> builder.setDraftMark(valueOf(dratMark)));
        Optional.ofNullable(entity.getDraftRestriction())
            .ifPresent(draftRestriction -> builder.setDraftRestriction(valueOf(draftRestriction)));
        Optional.ofNullable(entity.getMaxAirTemperature())
            .ifPresent(maxTemp -> builder.setMaxAirTemperature(valueOf(maxTemp)));
        Optional.ofNullable(entity.getMaxWaterTemperature())
            .ifPresent(maxTemp -> builder.setMaxWaterTemperature(valueOf(maxTemp)));
        Set<LoadableStudyPortRotation> portRotations = entity.getPortRotations();
        if (null != portRotations && !portRotations.isEmpty()) {
          portRotations.forEach(
              port -> {
                if (port.isActive()
                    && null != port.getOperation()
                    && DISCHARGING_OPERATION_ID.equals(port.getOperation().getId())) {
                  builder.addDischargingPortIds(port.getPortXId());
                }
              });
        }
        replyBuilder.addLoadableStudies(builder.build());
      }

    } catch (GenericServiceException e) {
      log.error("GenericServiceException when fetching loadable study", e);
      replyBuilder =
          LoadableStudyReply.newBuilder()
              .setResponseStatus(
                  ResponseStatus.newBuilder()
                      .setCode(e.getCode())
                      .setMessage(e.getMessage())
                      .setStatus(FAILED)
                      .build());
    } catch (Exception e) {
      log.error("Error fetching loadable studies", e);
      replyBuilder =
          LoadableStudyReply.newBuilder()
              .setResponseStatus(
                  ResponseStatus.newBuilder()
                      .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
                      .setMessage("Error fetching loadable studies")
                      .setStatus(FAILED)
                      .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * Save loadable study
   *
   * @param {@link LoadableStudyDetail}
   * @param {@link StreamObserver}
   */
  @Override
  public void saveLoadableStudy(
      LoadableStudyDetail request, StreamObserver<LoadableStudyReply> responseObserver) {
    Builder replyBuilder = LoadableStudyReply.newBuilder();
    LoadableStudy entity = null;
    try {
      entity = new LoadableStudy();
      this.checkVoyageAndCreatedFrom(request, entity);
      entity.setName(request.getName());
      entity.setDetails(StringUtils.isEmpty(request.getDetail()) ? null : request.getDetail());
      entity.setCharterer(
          StringUtils.isEmpty(request.getCharterer()) ? null : request.getCharterer());
      entity.setSubCharterer(
          StringUtils.isEmpty(request.getSubCharterer()) ? null : request.getSubCharterer());
      entity.setVesselXId(request.getVesselId());
      entity.setDraftMark(
          StringUtils.isEmpty(request.getDraftMark())
              ? null
              : new BigDecimal(request.getDraftMark()));
      entity.setLoadLineXId(request.getLoadLineXId());
      entity.setDraftRestriction(
          StringUtils.isEmpty(request.getDraftRestriction())
              ? null
              : new BigDecimal(request.getDraftRestriction()));
      entity.setMaxAirTemperature(
          StringUtils.isEmpty(request.getMaxAirTemperature())
              ? null
              : new BigDecimal(request.getMaxAirTemperature()));
      entity.setMaxWaterTemperature(
          StringUtils.isEmpty(request.getMaxWaterTemperature())
              ? null
              : new BigDecimal(request.getMaxWaterTemperature()));
      if (!request.getAttachmentsList().isEmpty()) {
        String folderLocation = this.constructFolderPath(entity);
        Files.createDirectories(Paths.get(this.rootFolder + folderLocation));
        Set<LoadableStudyAttachments> attachmentCollection = new HashSet<>();
        for (LoadableStudyAttachment attachment : request.getAttachmentsList()) {
          Path path = Paths.get(this.rootFolder + folderLocation + attachment.getFileName());
          Files.createFile(path);
          Files.write(path, attachment.getByteString().toByteArray());
          LoadableStudyAttachments attachmentEntity = new LoadableStudyAttachments();
          attachmentEntity.setUploadedFileName(attachment.getFileName());
          attachmentEntity.setFilePath(folderLocation);
          attachmentEntity.setLoadableStudy(entity);
          attachmentCollection.add(attachmentEntity);
        }
        entity.setAttachments(attachmentCollection);
      }
      this.setCaseNo(entity);
      entity.setLoadableStudyStatus(
          this.loadableStudyStatusRepository.getOne(LOADABLE_STUDY_INITIAL_STATUS_ID));
      entity = this.loadableStudyRepository.save(entity);
      replyBuilder
          .setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build())
          .setId(entity.getId());
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when saving loadable study", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(e.getCode())
              .setMessage(e.getMessage())
              .setStatus(FAILED)
              .build());
      this.deleteFiles(entity);
    } catch (Exception e) {
      log.error("Error saving loadable study", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Error saving loadable study")
              .setStatus(FAILED)
              .build());
      this.deleteFiles(entity);
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * * Set case number in loadable study entity
   *
   * @param request
   * @param entity
   */
  private void setCaseNo(LoadableStudy entity) {
    if (null != entity.getDraftRestriction()) {
      entity.setCaseNo(CASE_3);
    } else if (CASE_1_LOAD_LINES.contains(entity.getLoadLineXId())) {
      entity.setCaseNo(CASE_1);
    } else {
      entity.setCaseNo(CASE_2);
    }
  }

  private void checkVoyageAndCreatedFrom(LoadableStudyDetail request, LoadableStudy entity)
      throws GenericServiceException {
    Optional<Voyage> voyageOpt = this.voyageRepository.findById(request.getVoyageId());
    if (!voyageOpt.isPresent()) {
      throw new GenericServiceException(
          "Voyage does not exist", CommonErrorCodes.E_HTTP_BAD_REQUEST, null);
    }
    entity.setVoyage(voyageOpt.get());
    if (0 != request.getDuplicatedFromId()) {
      Optional<LoadableStudy> createdFromOpt =
          this.loadableStudyRepository.findById(request.getDuplicatedFromId());
      if (!createdFromOpt.isPresent()) {
        throw new GenericServiceException(
            "Created from loadable study does not exist",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            null);
      }
      entity.setDuplicatedFrom(createdFromOpt.get());
    }
  }

  /**
   * Construct folder path for loadable study attachments
   *
   * @param loadableStudy - loadable study entity
   * @param voyage - voyage entity
   * @return - the folder path
   */
  private String constructFolderPath(LoadableStudy loadableStudy) {
    String separator = File.separator;
    StringBuilder pathBuilder = new StringBuilder(separator);
    pathBuilder
        .append("company_")
        .append(loadableStudy.getVoyage().getCompanyXId())
        .append(separator)
        .append("vessel_")
        .append(loadableStudy.getVesselXId())
        .append(separator)
        .append(loadableStudy.getVoyage().getVoyageNo().replace(" ", "_"))
        .append("_")
        .append(loadableStudy.getVoyage().getId())
        .append(separator)
        .append(loadableStudy.getName().replace(" ", "_"))
        .append(separator);
    return valueOf(pathBuilder);
  }

  /**
   * Method to delete file if there is any exception when saving loadable study
   *
   * @param entity - the set of entities for attachments
   */
  private void deleteFiles(LoadableStudy entity) {
    if (null == entity || null == entity.getAttachments()) {
      return;
    }
    for (LoadableStudyAttachments attachment : entity.getAttachments()) {
      Path path =
          Paths.get(this.rootFolder + attachment.getFilePath() + attachment.getUploadedFileName());
      try {
        Files.deleteIfExists(path);
      } catch (IOException e) {
        log.error(
            "unable to delete file : {}",
            this.rootFolder + attachment.getFilePath() + attachment.getUploadedFileName(),
            e);
      }
    }
  }

  @Override
  public void saveCargoNomination(
      CargoNominationRequest request, StreamObserver<CargoNominationReply> responseObserver) {
    CargoNominationReply.Builder cargoNominationReplyBuilder = CargoNominationReply.newBuilder();
    try {
      Optional<LoadableStudy> loadableStudy =
          this.loadableStudyRepository.findByIdAndIsActive(request.getLoadableStudyId(), true);
      if (!loadableStudy.isPresent()) {
        throw new GenericServiceException(
            "Loadable Study does not exist",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      CargoNomination cargoNomination = null;
      if (request.getCargoNominationDetail() != null
          && request.getCargoNominationDetail().getId() != 0) {
        Optional<CargoNomination> existingCargoNomination =
            this.cargoNominationRepository.findByIdAndIsActive(
                request.getCargoNominationDetail().getId(), true);
        if (!existingCargoNomination.isPresent()) {
          throw new GenericServiceException(
              "Cargo Nomination does not exist",
              CommonErrorCodes.E_HTTP_BAD_REQUEST,
              HttpStatusCode.BAD_REQUEST);
        }
        cargoNomination = existingCargoNomination.get();
        cargoNomination = buildCargoNomination(cargoNomination, request);
      } else if (request.getCargoNominationDetail() != null
          && request.getCargoNominationDetail().getId() == 0) {
        cargoNomination = new CargoNomination();
        cargoNomination = buildCargoNomination(cargoNomination, request);
      }
      this.cargoNominationRepository.save(cargoNomination);
      // update port rotation table with loading ports from cargo nomination
      LoadableStudy loadableStudyRecord = loadableStudy.get();
      this.updatePortRotationWithLoadingPorts(loadableStudyRecord, cargoNomination);
      cargoNominationReplyBuilder
          .setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS))
          .setCargoNominationId((cargoNomination.getId() != null) ? cargoNomination.getId() : 0);
    } catch (Exception e) {
      log.error("Error saving cargo nomination", e);
      cargoNominationReplyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(FAILED));
    } finally {
      responseObserver.onNext(cargoNominationReplyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * Fetch ports for the specific loadableStudy already available in port rotation and update to
   * port rotation along with port master fields water density, maxDraft, maxAirDraft only those
   * ports that are not already available
   *
   * @param cargoNomination
   * @throws GenericServiceException
   */
  private void updatePortRotationWithLoadingPorts(
      LoadableStudy loadableStudy, CargoNomination cargoNomination) throws GenericServiceException {
    List<LoadableStudyPortRotation> loadableStudyPortRotations =
        this.loadableStudyPortRotationRepository.findByLoadableStudyAndOperationAndIsActive(
            loadableStudy, cargoOperationRepository.getOne(LOADING_OPERATION_ID), true);
    List<Long> requestedPortIds = null;
    List<Long> existingPortIds = null;
    if (!cargoNomination.getCargoNominationPortDetails().isEmpty()) {
      requestedPortIds =
          cargoNomination.getCargoNominationPortDetails().stream()
              .map(CargoNominationPortDetails::getPortId)
              .collect(Collectors.toList());
    }
    if (!loadableStudyPortRotations.isEmpty()) {
      existingPortIds =
          loadableStudyPortRotations.stream()
              .map(LoadableStudyPortRotation::getPortXId)
              .collect(Collectors.toList());
    }
    int existingPortsCount = 0;
    // remove loading portIds from request which are already available in port
    // rotation for the
    // specific loadable study
    if (!CollectionUtils.isEmpty(requestedPortIds) && !CollectionUtils.isEmpty(existingPortIds)) {
      requestedPortIds.removeAll(existingPortIds);
      existingPortsCount = existingPortIds.size();
    }
    // fetch the specific ports attributes like waterDensity and draft values from
    // port master
    if (!CollectionUtils.isEmpty(requestedPortIds)) {
      GetPortInfoByPortIdsRequest.Builder reqBuilder = GetPortInfoByPortIdsRequest.newBuilder();
      buildGetPortInfoByPortIdsRequest(reqBuilder, cargoNomination);
      PortReply portReply = portInfoGrpcService.getPortInfoByPortIds(reqBuilder.build());
      if (portReply != null
          && portReply.getResponseStatus() != null
          && !SUCCESS.equalsIgnoreCase(portReply.getResponseStatus().getStatus())) {
        throw new GenericServiceException(
            "Error in calling port service",
            CommonErrorCodes.E_GEN_INTERNAL_ERR,
            HttpStatusCode.INTERNAL_SERVER_ERROR);
      }
      // update loadable-study-port-rotation with ports from cargoNomination and port
      // attributes
      buildAndSaveLoadableStudyPortRotationEntities(
          loadableStudy, requestedPortIds, portReply, existingPortsCount);
    }
  }

  /**
   * Create Port rotation entities for each loading port from cargoNomination with pre-populate port
   * master attributes
   *
   * @param request
   * @return
   */
  private void buildAndSaveLoadableStudyPortRotationEntities(
      LoadableStudy loadableStudy,
      List<Long> requestedPortIds,
      PortReply portReply,
      int existingPortsCount) {
    if (!CollectionUtils.isEmpty(requestedPortIds)
        && portReply != null
        && !CollectionUtils.isEmpty(portReply.getPortsList())) {
      AtomicLong atomLong = new AtomicLong(existingPortsCount);
      List<LoadableStudyPortRotation> portRotationList = new ArrayList<>();
      requestedPortIds.stream()
          .forEach(
              requestedPortId ->
                  portReply.getPortsList().stream()
                      .filter(port -> Objects.equals(requestedPortId, port.getId()))
                      .forEach(
                          port -> {
                            LoadableStudyPortRotation portRotationEntity =
                                new LoadableStudyPortRotation();
                            portRotationEntity.setLoadableStudy(loadableStudy);
                            portRotationEntity.setPortXId(port.getId());
                            portRotationEntity.setOperation(
                                this.cargoOperationRepository.getOne(LOADING_OPERATION_ID));
                            portRotationEntity.setSeaWaterDensity(
                                !StringUtils.isEmpty(port.getWaterDensity())
                                    ? new BigDecimal(port.getWaterDensity())
                                    : null);
                            portRotationEntity.setMaxDraft(
                                !StringUtils.isEmpty(port.getMaxDraft())
                                    ? new BigDecimal(port.getMaxDraft())
                                    : null);
                            portRotationEntity.setAirDraftRestriction(
                                !StringUtils.isEmpty(port.getMaxAirDraft())
                                    ? new BigDecimal(port.getMaxAirDraft())
                                    : null);
                            portRotationEntity.setPortOrder(atomLong.incrementAndGet());
                            // add ports to synoptical table by reusing the function called by
                            // port-rotation flow
                            buildPortsInfoSynopticalTable(
                                portRotationEntity, LOADING_OPERATION_ID, port.getId());
                            portRotationList.add(portRotationEntity);
                          }));
      loadableStudyPortRotationRepository.saveAll(portRotationList);
    }
  }

  /**
   * Builds the request for fetching the port attributes from port master
   *
   * @param cargoNomination
   */
  private void buildGetPortInfoByPortIdsRequest(
      GetPortInfoByPortIdsRequest.Builder reqBuilder, CargoNomination cargoNomination) {
    // build fetch port details request object
    if (cargoNomination != null
        && !CollectionUtils.isEmpty(cargoNomination.getCargoNominationPortDetails())) {
      cargoNomination
          .getCargoNominationPortDetails()
          .forEach(
              loadingPort -> {
                Optional.ofNullable(loadingPort.getPortId()).ifPresent(reqBuilder::addId);
              });
    }
  }

  private CargoNomination buildCargoNomination(
      CargoNomination cargoNomination, CargoNominationRequest request) {
    cargoNomination.setLoadableStudyXId(request.getCargoNominationDetail().getLoadableStudyId());
    cargoNomination.setPriority(request.getCargoNominationDetail().getPriority());
    cargoNomination.setCargoXId(request.getCargoNominationDetail().getCargoId());
    cargoNomination.setAbbreviation(request.getCargoNominationDetail().getAbbreviation());
    cargoNomination.setColor(request.getCargoNominationDetail().getColor());
    cargoNomination.setQuantity(
        !StringUtils.isEmpty(request.getCargoNominationDetail().getQuantity())
            ? new BigDecimal(request.getCargoNominationDetail().getQuantity())
            : null);
    cargoNomination.setMaxTolerance(
        !StringUtils.isEmpty(request.getCargoNominationDetail().getMaxTolerance())
            ? new BigDecimal(request.getCargoNominationDetail().getMaxTolerance())
            : null);
    cargoNomination.setMinTolerance(
        !StringUtils.isEmpty(request.getCargoNominationDetail().getMinTolerance())
            ? new BigDecimal(request.getCargoNominationDetail().getMinTolerance())
            : null);
    cargoNomination.setApi(
        !StringUtils.isEmpty(request.getCargoNominationDetail().getApiEst())
            ? new BigDecimal(request.getCargoNominationDetail().getApiEst())
            : null);
    cargoNomination.setTemperature(
        !StringUtils.isEmpty(request.getCargoNominationDetail().getTempEst())
            ? new BigDecimal(request.getCargoNominationDetail().getTempEst())
            : null);
    cargoNomination.setSegregationXId(request.getCargoNominationDetail().getSegregationId());
    // activate the records to be saved
    cargoNomination.setIsActive(true);
    if (!request.getCargoNominationDetail().getLoadingPortDetailsList().isEmpty()) {
      // clear any existing CargoNominationPortDetails otherwise create new
      if (cargoNomination.getCargoNominationPortDetails() != null) {
        cargoNomination.getCargoNominationPortDetails().clear();
      }
      Set<CargoNominationPortDetails> cargoNominationPortDetailsList =
          request.getCargoNominationDetail().getLoadingPortDetailsList().stream()
              .map(
                  loadingPortDetail -> {
                    CargoNominationPortDetails cargoNominationPortDetails =
                        new CargoNominationPortDetails();
                    cargoNominationPortDetails.setCargoNomination(cargoNomination);
                    cargoNominationPortDetails.setPortId(loadingPortDetail.getPortId());
                    cargoNominationPortDetails.setQuantity(
                        !loadingPortDetail.getQuantity().isEmpty()
                            ? new BigDecimal(loadingPortDetail.getQuantity())
                            : null);
                    cargoNominationPortDetails.setIsActive(true);
                    return cargoNominationPortDetails;
                  })
              .collect(Collectors.toSet());
      // clear any existing CargoNominationPortDetails otherwise create new
      if (cargoNomination.getCargoNominationPortDetails() != null) {
        cargoNomination.getCargoNominationPortDetails().addAll(cargoNominationPortDetailsList);
      } else {
        cargoNomination.setCargoNominationPortDetails(cargoNominationPortDetailsList);
      }
    }
    return cargoNomination;
  }

  @Override
  public void getLoadableStudyPortRotation(
      PortRotationRequest request, StreamObserver<PortRotationReply> responseObserver) {
    PortRotationReply.Builder replyBuilder = PortRotationReply.newBuilder();
    try {
      Optional<LoadableStudy> loadableStudyOpt =
          this.loadableStudyRepository.findById(request.getLoadableStudyId());
      if (!loadableStudyOpt.isPresent()) {
        throw new GenericServiceException(
            "Loadable study does not exist in database", CommonErrorCodes.E_HTTP_BAD_REQUEST, null);
      }
      CargoOperation dischargingOperation =
          this.cargoOperationRepository.getOne(DISCHARGING_OPERATION_ID);
      List<LoadableStudyPortRotation> entityList =
          this.loadableStudyPortRotationRepository
              .findByLoadableStudyAndOperationNotAndIsActiveOrderByPortOrder(
                  loadableStudyOpt.get(), dischargingOperation, true);
      for (LoadableStudyPortRotation entity : entityList) {
        replyBuilder.addPorts(
            this.createPortDetail(
                entity,
                DateTimeFormatter.ofPattern(ETA_ETD_FORMAT),
                DateTimeFormatter.ofPattern(LAY_CAN_FORMAT)));
      }
      List<CargoOperation> operationEntityList =
          this.cargoOperationRepository.findByIdNotAndIsActiveOrderById(
              DISCHARGING_OPERATION_ID, true);
      for (CargoOperation entity : operationEntityList) {
        replyBuilder.addOperations(this.createOperationDetail(entity));
      }
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when fetching loadable study - port data", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(e.getCode())
              .setMessage(e.getMessage())
              .setStatus(FAILED)
              .build());
    } catch (Exception e) {
      log.error("Exception when fetching port rotation data", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Exception when fetching loadable study - port data")
              .setStatus(FAILED)
              .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * Create {@link Operation} from {@link CargoOperation}
   *
   * @param entity - {@link CargoOperation}
   * @return
   */
  private Operation createOperationDetail(CargoOperation entity) {
    return Operation.newBuilder().setId(entity.getId()).setOperationName(entity.getName()).build();
  }

  /**
   * Create {@link PortDetail} from {@link LoadableStudyPortRotation}
   *
   * @param entity {@link LoadableStudyPortRotation}
   * @return {@link PortDetail}
   */
  private PortRotationDetail createPortDetail(
      LoadableStudyPortRotation entity,
      DateTimeFormatter etaEtdFormatter,
      DateTimeFormatter layCanFormatter) {
    PortRotationDetail.Builder builder = PortRotationDetail.newBuilder();
    builder.setId(entity.getId());
    builder.setLoadableStudyId(entity.getLoadableStudy().getId());
    Optional.ofNullable(entity.getPortXId()).ifPresent(builder::setPortId);
    Optional.ofNullable(entity.getOperation()).ifPresent(op -> builder.setOperationId(op.getId()));
    Optional.ofNullable(entity.getBerthXId()).ifPresent(builder::setBerthId);
    Optional.ofNullable(entity.getSeaWaterDensity())
        .ifPresent(density -> builder.setSeaWaterDensity(valueOf(density)));
    Optional.ofNullable(entity.getDistanceBetweenPorts())
        .ifPresent(distance -> builder.setDistanceBetweenPorts(valueOf(distance)));
    Optional.ofNullable(entity.getTimeOfStay())
        .ifPresent(timeOfStay -> builder.setTimeOfStay(valueOf(timeOfStay)));
    Optional.ofNullable(entity.getMaxDraft())
        .ifPresent(maxDraft -> builder.setMaxDraft(valueOf(maxDraft)));
    Optional.ofNullable(entity.getAirDraftRestriction())
        .ifPresent(airDraft -> builder.setMaxAirDraft(valueOf(airDraft)));
    Optional.ofNullable(entity.getEta())
        .ifPresent(eta -> builder.setEta(etaEtdFormatter.format(eta)));
    Optional.ofNullable(entity.getEtd())
        .ifPresent(etd -> builder.setEtd(etaEtdFormatter.format(etd)));
    Optional.ofNullable(entity.getLayCanFrom())
        .ifPresent(layCanFrom -> builder.setLayCanFrom(layCanFormatter.format(layCanFrom)));
    Optional.ofNullable(entity.getLayCanTo())
        .ifPresent(layCanTo -> builder.setLayCanTo(layCanFormatter.format(layCanTo)));
    Optional.ofNullable(entity.getPortOrder()).ifPresent(builder::setPortOrder);
    return builder.build();
  }

  /**
   * Retrieves list of cargoNominations by LoadableStudyId can be extended to other Ids like
   * vesselId or voyageId
   */
  @Override
  public void getCargoNominationById(
      CargoNominationRequest request, StreamObserver<CargoNominationReply> responseObserver) {
    com.cpdss.common.generated.LoadableStudy.CargoNominationReply.Builder replyBuilder =
        CargoNominationReply.newBuilder();
    try {
      Optional<LoadableStudy> loadableStudyOpt =
          this.loadableStudyRepository.findById(request.getLoadableStudyId());
      if (!loadableStudyOpt.isPresent()) {
        throw new GenericServiceException(
            "Loadable study does not exist", CommonErrorCodes.E_HTTP_BAD_REQUEST, null);
      }
      List<CargoNomination> cargoNominationList =
          this.cargoNominationRepository.findByLoadableStudyXIdAndIsActive(
              request.getLoadableStudyId(), true);
      buildCargoNominationReply(cargoNominationList, replyBuilder);
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS));
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when fetching loadable study - port data", e);
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(FAILED));
    } catch (Exception e) {
      log.error("Exception when fetching loadable study - port data", e);
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(FAILED));
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * method for getting loadable quantity
   *
   * @param request - has the loadable quantity id
   * @param responseObserver
   */
  @Override
  public void getLoadableQuantity(
      LoadableQuantityReply request, StreamObserver<LoadableQuantityResponse> responseObserver) {
    LoadableQuantityResponse.Builder builder = LoadableQuantityResponse.newBuilder();
    try {
      List<LoadableQuantity> loadableQuantity =
          loadableQuantityRepository.findByLoadableStudyXIdAndIsActive(
              request.getLoadableStudyId(), true);
      Optional<LoadableStudy> loadableStudy =
          this.loadableStudyRepository.findById(request.getLoadableStudyId());
      if (!loadableStudy.isPresent()) {
        log.info(INVALID_LOADABLE_STUDY_ID, request.getLoadableStudyId());
        throw new GenericServiceException(
            INVALID_LOADABLE_QUANTITY,
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      VesselRequest replyBuilder =
          VesselRequest.newBuilder()
              .setVesselId(loadableStudy.get().getVesselXId())
              .setVesselDraftConditionId(loadableStudy.get().getLoadLineXId())
              .setDraftExtreme(loadableStudy.get().getDraftMark().toString())
              .build();
      VesselReply vesselReply = this.getVesselDetailsById(replyBuilder);
      String selectedZone = "";
      if (vesselReply.getVesselLoadableQuantityDetails().getDraftConditionName() != null) {
        String[] array =
            vesselReply.getVesselLoadableQuantityDetails().getDraftConditionName().split(" ");
        selectedZone = array[array.length - 1];
      }
      builder.setCaseNo(loadableStudy.get().getCaseNo());
      builder.setSelectedZone(selectedZone);
      if (loadableQuantity.isEmpty()) {
        String draftRestictoin = "";
        if (Optional.ofNullable(loadableStudy.get().getDraftRestriction()).isPresent()) {
          draftRestictoin = loadableStudy.get().getDraftRestriction().toString();
        } else if (Optional.ofNullable(loadableStudy.get().getDraftMark()).isPresent()) {
          draftRestictoin = loadableStudy.get().getDraftMark().toString();
        }
        LoadableQuantityRequest loadableQuantityRequest =
            LoadableQuantityRequest.newBuilder()
                .setDisplacmentDraftRestriction(
                    vesselReply.getVesselLoadableQuantityDetails().getDisplacmentDraftRestriction())
                .setVesselLightWeight(
                    vesselReply.getVesselLoadableQuantityDetails().getVesselLightWeight())
                .setConstant(vesselReply.getVesselLoadableQuantityDetails().getConstant())
                .setTpc(vesselReply.getVesselLoadableQuantityDetails().getTpc())
                .setDraftRestriction(draftRestictoin)
                .setDwt(vesselReply.getVesselLoadableQuantityDetails().getDwt())
                .build();
        builder.setLoadableQuantityRequest(loadableQuantityRequest);
        builder.setResponseStatus(StatusReply.newBuilder().setStatus(SUCCESS).setMessage(SUCCESS));
      } else {

        LoadableQuantityRequest.Builder loadableQuantityRequest =
            LoadableQuantityRequest.newBuilder();
        Optional.ofNullable(loadableQuantity.get(0).getDisplacementAtDraftRestriction())
            .ifPresent(
                disp -> loadableQuantityRequest.setDisplacmentDraftRestriction(disp.toString()));
        Optional.ofNullable(loadableQuantity.get(0).getConstant())
            .ifPresent(cons -> loadableQuantityRequest.setConstant(cons.toString()));
        Optional.ofNullable(loadableQuantity.get(0).getDraftRestriction())
            .ifPresent(
                draftRestriction ->
                    loadableQuantityRequest.setDraftRestriction(draftRestriction.toString()));
        Optional.ofNullable(loadableQuantity.get(0).getDistanceFromLastPort())
            .ifPresent(dist -> loadableQuantityRequest.setDistanceFromLastPort(dist.toString()));
        Optional.ofNullable(loadableQuantity.get(0).getDeadWeight())
            .ifPresent(deadWeight -> loadableQuantityRequest.setDwt(deadWeight.toString()));
        Optional.ofNullable(loadableQuantity.get(0).getEstimatedDOOnBoard())
            .ifPresent(
                estDOOnBoard -> loadableQuantityRequest.setEstDOOnBoard(estDOOnBoard.toString()));
        Optional.ofNullable(loadableQuantity.get(0).getEstimatedFOOnBoard())
            .ifPresent(
                estFOOnBoard -> loadableQuantityRequest.setEstFOOnBoard(estFOOnBoard.toString()));
        Optional.ofNullable(loadableQuantity.get(0).getEstimatedFWOnBoard())
            .ifPresent(
                estFreshWaterOnBoard ->
                    loadableQuantityRequest.setEstFreshWaterOnBoard(
                        estFreshWaterOnBoard.toString()));
        Optional.ofNullable(loadableQuantity.get(0).getEstimatedSagging())
            .ifPresent(estSagging -> loadableQuantityRequest.setEstSagging(estSagging.toString()));
        Optional.ofNullable(loadableQuantity.get(0).getEstimatedSeaDensity())
            .ifPresent(
                estSeaDensity ->
                    loadableQuantityRequest.setEstSeaDensity(estSeaDensity.toString()));
        Optional.ofNullable(loadableQuantity.get(0).getOtherIfAny())
            .ifPresent(otherIfAny -> loadableQuantityRequest.setOtherIfAny(otherIfAny.toString()));
        Optional.ofNullable(loadableQuantity.get(0).getSaggingDeduction())
            .ifPresent(
                saggingDeduction ->
                    loadableQuantityRequest.setSaggingDeduction(saggingDeduction.toString()));
        Optional.ofNullable(loadableQuantity.get(0).getSgCorrection())
            .ifPresent(
                sgCorrection -> loadableQuantityRequest.setSgCorrection(sgCorrection.toString()));
        Optional.ofNullable(loadableQuantity.get(0).getTotalQuantity())
            .ifPresent(
                totalQuantity ->
                    loadableQuantityRequest.setTotalQuantity(totalQuantity.toString()));
        Optional.ofNullable(loadableQuantity.get(0).getTpcatDraft())
            .ifPresent(tpc -> loadableQuantityRequest.setTpc(tpc.toString()));
        Optional.ofNullable(loadableQuantity.get(0).getVesselAverageSpeed())
            .ifPresent(
                vesselAverageSpeed ->
                    loadableQuantityRequest.setVesselAverageSpeed(vesselAverageSpeed.toString()));
        Optional.ofNullable(loadableQuantity.get(0).getLightWeight())
            .ifPresent(
                vesselLightWeight ->
                    loadableQuantityRequest.setVesselLightWeight(vesselLightWeight.toString()));
        Optional.ofNullable(loadableQuantity.get(0).getLastModifiedDateTime())
            .ifPresent(
                updateDateAndTime ->
                    loadableQuantityRequest.setUpdateDateAndTime(
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                            .format(updateDateAndTime)));
        Optional.ofNullable(loadableQuantity.get(0).getPortId())
            .ifPresent(portId -> loadableQuantityRequest.setPortId(portId.longValue()));
        Optional.ofNullable(loadableQuantity.get(0).getBoilerWaterOnBoard())
            .ifPresent(
                boilerWaterOnBoard ->
                    loadableQuantityRequest.setBoilerWaterOnBoard(boilerWaterOnBoard.toString()));
        Optional.ofNullable(loadableQuantity.get(0).getBallast())
            .ifPresent(ballast -> loadableQuantityRequest.setBallast(ballast.toString()));
        Optional.ofNullable(loadableQuantity.get(0).getRunningHours())
            .ifPresent(
                runningHours -> loadableQuantityRequest.setRunningHours(runningHours.toString()));
        Optional.ofNullable(loadableQuantity.get(0).getRunningDays())
            .ifPresent(
                runningDays -> loadableQuantityRequest.setRunningDays(runningDays.toString()));
        Optional.ofNullable(loadableQuantity.get(0).getFoConsumptionInSZ())
            .ifPresent(
                foConsumptionInSZ ->
                    loadableQuantityRequest.setFoConInSZ(foConsumptionInSZ.toString()));
        Optional.ofNullable(loadableQuantity.get(0).getDraftRestriction())
            .ifPresent(
                draftRestriction ->
                    loadableQuantityRequest.setDraftRestriction(draftRestriction.toString()));
        Optional.ofNullable(loadableQuantity.get(0).getSubTotal())
            .ifPresent(subTotal -> loadableQuantityRequest.setSubTotal(subTotal.toString()));
        Optional.ofNullable(loadableQuantity.get(0).getFoConsumptionPerDay())
            .ifPresent(
                foConsumptionPerDay ->
                    loadableQuantityRequest.setFoConsumptionPerDay(foConsumptionPerDay.toString()));

        builder.setLoadableQuantityRequest(loadableQuantityRequest);
        builder.setResponseStatus(StatusReply.newBuilder().setStatus(SUCCESS).setMessage(SUCCESS));
      }
    } catch (GenericServiceException e) {
      log.error("Error getting loadable quantity ", e);
      builder.setResponseStatus(
          StatusReply.newBuilder()
              .setStatus(FAILED)
              .setMessage(e.getMessage())
              .setCode(e.getCode()));
    } catch (Exception e) {
      log.error("Error getting loadable quantity ", e);
      builder.setResponseStatus(
          StatusReply.newBuilder()
              .setStatus(FAILED)
              .setMessage(FAILED)
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR));
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  public VesselReply getVesselDetailsById(VesselRequest replyBuilder) {
    return this.vesselInfoGrpcService.getVesselDetailsById(replyBuilder);
  }

  private void buildCargoNominationReply(
      List<CargoNomination> cargoNominationList,
      com.cpdss.common.generated.LoadableStudy.CargoNominationReply.Builder
          cargoNominationReplyBuilder) {
    if (!CollectionUtils.isEmpty(cargoNominationList)) {
      cargoNominationList.forEach(
          cargoNomination -> {
            CargoNominationDetail.Builder builder = CargoNominationDetail.newBuilder();
            Optional.ofNullable(cargoNomination.getId()).ifPresent(builder::setId);
            Optional.ofNullable(cargoNomination.getLoadableStudyXId())
                .ifPresent(builder::setLoadableStudyId);
            Optional.ofNullable(cargoNomination.getPriority()).ifPresent(builder::setPriority);
            Optional.ofNullable(cargoNomination.getColor()).ifPresent(builder::setColor);
            Optional.ofNullable(cargoNomination.getCargoXId()).ifPresent(builder::setCargoId);
            Optional.ofNullable(cargoNomination.getAbbreviation())
                .ifPresent(builder::setAbbreviation);
            // build inner loadingPort details object
            if (!CollectionUtils.isEmpty(cargoNomination.getCargoNominationPortDetails())) {
              cargoNomination
                  .getCargoNominationPortDetails()
                  .forEach(
                      loadingPort -> {
                        LoadingPortDetail.Builder loadingPortDetailBuilder =
                            LoadingPortDetail.newBuilder();
                        Optional.ofNullable(loadingPort.getPortId())
                            .ifPresent(loadingPortDetailBuilder::setPortId);
                        Optional.ofNullable(loadingPort.getQuantity())
                            .ifPresent(
                                quantity ->
                                    loadingPortDetailBuilder.setQuantity(String.valueOf(quantity)));
                        builder.addLoadingPortDetails(loadingPortDetailBuilder);
                      });
            }
            Optional.ofNullable(cargoNomination.getMaxTolerance())
                .ifPresent(maxTolerance -> builder.setMaxTolerance(String.valueOf(maxTolerance)));
            Optional.ofNullable(cargoNomination.getMinTolerance())
                .ifPresent(minTolerance -> builder.setMinTolerance(String.valueOf(minTolerance)));
            Optional.ofNullable(cargoNomination.getApi())
                .ifPresent(api -> builder.setApiEst(String.valueOf(api)));
            Optional.ofNullable(cargoNomination.getTemperature())
                .ifPresent(temperature -> builder.setTempEst(String.valueOf(temperature)));
            Optional.ofNullable(cargoNomination.getSegregationXId())
                .ifPresent(builder::setSegregationId);
            cargoNominationReplyBuilder.addCargoNominations(builder);
          });
    }
  }

  /** Retrieves all valve segregation available */
  @Override
  public void getValveSegregation(
      ValveSegregationRequest request, StreamObserver<ValveSegregationReply> responseObserver) {
    ValveSegregationReply.Builder reply = ValveSegregationReply.newBuilder();
    try {
      Iterable<CargoNominationValveSegregation> segregationsList =
          valveSegregationRepository.findAll();
      segregationsList.forEach(
          segregation -> {
            ValveSegregation.Builder segregationDetail = ValveSegregation.newBuilder();
            if (segregation.getId() != null) {
              segregationDetail.setId(segregation.getId());
            }
            if (!StringUtils.isEmpty(segregation.getName())) {
              segregationDetail.setName(segregation.getName());
            }
            reply.addValveSegregation(segregationDetail);
          });
      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus(SUCCESS);
      reply.setResponseStatus(responseStatus);
    } catch (Exception e) {
      log.error("Error in getValveSegregation method ", e);
      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus(FAILED);
      reply.setResponseStatus(responseStatus);
    } finally {
      responseObserver.onNext(reply.build());
      responseObserver.onCompleted();
    }
  }

  /** Get voyages by vessel */
  @Override
  public void getVoyagesByVessel(
      VoyageRequest request, StreamObserver<VoyageListReply> responseObserver) {
    VoyageListReply.Builder builder = VoyageListReply.newBuilder();
    try {
      List<Voyage> entityList =
          this.voyageRepository.findByVesselXIdAndIsActiveOrderByLastModifiedDateTimeDesc(
              request.getVesselId(), true);
      for (Voyage entity : entityList) {
        VoyageDetail.Builder detailbuilder = VoyageDetail.newBuilder();
        detailbuilder.setId(entity.getId());
        detailbuilder.setVoyageNumber(entity.getVoyageNo());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        Optional.ofNullable(entity.getVoyageStartDate())
            .ifPresent(startDate -> detailbuilder.setStartDate(formatter.format(startDate)));
        Optional.ofNullable(entity.getVoyageEndDate())
            .ifPresent(endDate -> detailbuilder.setEndDate(formatter.format(endDate)));
        builder.addVoyages(detailbuilder.build());
      }
      builder.setResponseStatus(StatusReply.newBuilder().setStatus(SUCCESS).build());
    } catch (Exception e) {
      builder.setResponseStatus(
          StatusReply.newBuilder()
              .setStatus(FAILED)
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .build());
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  /** Save port rotation info for loadable study */
  @Override
  public void saveLoadableStudyPortRotation(
      PortRotationDetail request, StreamObserver<PortRotationReply> responseObserver) {
    PortRotationReply.Builder replyBuilder = PortRotationReply.newBuilder();
    try {
      Optional<LoadableStudy> loadableStudyOpt =
          this.loadableStudyRepository.findById(request.getLoadableStudyId());
      if (!loadableStudyOpt.isPresent()) {
        throw new GenericServiceException(
            "Loadable study does not exist",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      LoadableStudyPortRotation entity = null;
      if (request.getId() == 0) {
        entity = new LoadableStudyPortRotation();
        entity.setLoadableStudy(loadableStudyOpt.get());
        // Add ports to synoptical table
        buildPortsInfoSynopticalTable(entity, request.getOperationId(), request.getPortId());
      } else {
        Optional<LoadableStudyPortRotation> portRoationOpt =
            this.loadableStudyPortRotationRepository.findById(request.getId());
        if (!portRoationOpt.isPresent()) {
          throw new GenericServiceException(
              "Port rotation does not exist",
              CommonErrorCodes.E_HTTP_BAD_REQUEST,
              HttpStatusCode.BAD_REQUEST);
        }
        entity = portRoationOpt.get();
      }
      entity =
          this.loadableStudyPortRotationRepository.save(
              this.createPortRotationEntity(entity, request));
      replyBuilder.setPortRotationId(entity.getId());
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when saving loadable study - port data", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(e.getCode())
              .setMessage(e.getMessage())
              .setStatus(FAILED)
              .build());
    } catch (Exception e) {
      log.error("Exception when saving loadable study port data", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Exception when saving port data")
              .setStatus(FAILED)
              .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  @Transactional
  public void saveDischargingPorts(
      PortRotationRequest request, StreamObserver<PortRotationReply> responseObserver) {
    PortRotationReply.Builder replyBuilder = PortRotationReply.newBuilder();
    try {
      Optional<LoadableStudy> loadableStudyOpt =
          this.loadableStudyRepository.findById(request.getLoadableStudyId());
      if (!loadableStudyOpt.isPresent()) {
        throw new GenericServiceException(
            "Loadable study does not exist",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      CargoOperation discharging = this.cargoOperationRepository.getOne(DISCHARGING_OPERATION_ID);
      List<LoadableStudyPortRotation> dischargingPorts =
          this.loadableStudyPortRotationRepository.findByLoadableStudyAndOperationAndIsActive(
              loadableStudyOpt.get(), discharging, true);
      List<Long> portIds = new ArrayList<>(request.getDischargingPortIdsList());
      for (LoadableStudyPortRotation portRoation : dischargingPorts) {
        if (!request.getDischargingPortIdsList().contains(portRoation.getPortXId())) {
          portRoation.setActive(false);
        } else {
          portIds.remove(portRoation.getPortXId());
        }
      }
      if (!portIds.isEmpty()) {
        portIds.forEach(
            id -> {
              LoadableStudyPortRotation port = new LoadableStudyPortRotation();
              port.setPortXId(id);
              port.setLoadableStudy(loadableStudyOpt.get());
              port.setOperation(discharging);
              dischargingPorts.add(port);
            });
      }
      this.loadableStudyPortRotationRepository.saveAll(dischargingPorts);
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when saving discharging ports", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(e.getCode())
              .setMessage(e.getMessage())
              .setStatus(FAILED)
              .build());
    } catch (Exception e) {
      log.error("Exception when saving discharging ports data", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Exception when saving discharging ports")
              .setStatus(FAILED)
              .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * Create entity class from request
   *
   * @param entity
   * @param request
   * @return
   */
  private LoadableStudyPortRotation createPortRotationEntity(
      LoadableStudyPortRotation entity, PortRotationDetail request) {
    entity.setAirDraftRestriction(
        isEmpty(request.getMaxAirDraft()) ? null : new BigDecimal(request.getMaxAirDraft()));
    entity.setBerthXId(0 == request.getBerthId() ? null : request.getBerthId());
    entity.setPortXId(0 == request.getPortId() ? null : request.getPortId());
    entity.setDistanceBetweenPorts(
        isEmpty(request.getDistanceBetweenPorts())
            ? null
            : new BigDecimal(request.getDistanceBetweenPorts()));
    entity.setMaxDraft(
        isEmpty(request.getMaxDraft()) ? null : new BigDecimal(request.getMaxDraft()));
    entity.setSeaWaterDensity(
        isEmpty(request.getSeaWaterDensity())
            ? null
            : new BigDecimal(request.getSeaWaterDensity()));
    entity.setTimeOfStay(
        isEmpty(request.getTimeOfStay()) ? null : new BigDecimal(request.getTimeOfStay()));
    entity.setEta(
        isEmpty(request.getEta())
            ? null
            : LocalDateTime.from(
                DateTimeFormatter.ofPattern(ETA_ETD_FORMAT).parse(request.getEta())));
    entity.setEtd(
        isEmpty(request.getEtd())
            ? null
            : LocalDateTime.from(
                DateTimeFormatter.ofPattern(ETA_ETD_FORMAT).parse(request.getEtd())));
    entity.setLayCanFrom(
        isEmpty(request.getLayCanFrom())
            ? null
            : LocalDate.from(
                DateTimeFormatter.ofPattern(LAY_CAN_FORMAT).parse(request.getLayCanFrom())));
    entity.setLayCanTo(
        isEmpty(request.getLayCanTo())
            ? null
            : LocalDate.from(
                DateTimeFormatter.ofPattern(LAY_CAN_FORMAT).parse(request.getLayCanTo())));
    entity.setOperation(this.cargoOperationRepository.getOne(request.getOperationId()));
    entity.setPortOrder(0 == request.getPortOrder() ? null : request.getPortOrder());
    return entity;
  }

  /**
   * Builds the port info in synoptical table
   *
   * @param entity
   * @param request
   */
  private void buildPortsInfoSynopticalTable(
      LoadableStudyPortRotation entity, Long requestedOperationId, Long requestedPortId) {
    // build ports information to update synoptical table
    if (requestedOperationId != 0
        && !StringUtils.isEmpty(
            com.cpdss.loadablestudy.domain.CargoOperation.getOperation(requestedOperationId))) {
      Set<SynopticalTable> synopticalTableEntityList = new HashSet<>();
      if (CARGO_OPERATION_ARR_DEP_SYNOPTICAL.contains(requestedOperationId)) {
        buildSynopticalTableRecord(requestedPortId, entity, synopticalTableEntityList, "ARR");
        buildSynopticalTableRecord(requestedPortId, entity, synopticalTableEntityList, "DEP");
      } else if (TRANSIT_OPERATION_ID.equals(requestedOperationId)) {
        buildSynopticalTableRecord(requestedPortId, entity, synopticalTableEntityList, "TRANSIT");
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
   * @param request
   * @param entity
   * @param synopticalTableList
   * @param portStage
   */
  private void buildSynopticalTableRecord(
      Long portId,
      LoadableStudyPortRotation entity,
      Set<SynopticalTable> synopticalTableList,
      String portStage) {
    SynopticalTable synopticalTable = new SynopticalTable();
    synopticalTable.setLoadableStudyPortRotation(entity);
    synopticalTable.setLoadableStudyXId(entity.getLoadableStudy().getId());
    synopticalTable.setOperationType(portStage);
    synopticalTable.setPortXid(0 == portId ? null : portId);
    synopticalTable.setIsActive(true);
    synopticalTableList.add(synopticalTable);
  }

  /** Delete specific cargo nomination */
  @Override
  public void deleteCargoNomination(
      CargoNominationRequest request, StreamObserver<CargoNominationReply> responseObserver) {
    CargoNominationReply.Builder cargoNominationReplyBuilder = CargoNominationReply.newBuilder();
    try {
      Optional<CargoNomination> existingCargoNomination =
          this.cargoNominationRepository.findById(request.getCargoNominationId());
      if (!existingCargoNomination.isPresent()) {
        throw new GenericServiceException(
            "Cargo Nomination does not exist",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      this.cargoNominationOperationDetailsRepository.deleteCargoNominationPortDetails(
          request.getCargoNominationId());
      this.cargoNominationRepository.deleteCargoNomination(request.getCargoNominationId());
      cargoNominationReplyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS));
    } catch (Exception e) {
      log.error("Error deleting cargo nomination", e);
      cargoNominationReplyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(FAILED));
    } finally {
      responseObserver.onNext(cargoNominationReplyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void deleteLoadableStudy(
      LoadableStudyRequest request, StreamObserver<LoadableStudyReply> responseObserver) {
    LoadableStudyReply.Builder replyBuilder = LoadableStudyReply.newBuilder();
    try {
      Optional<LoadableStudy> entityOpt =
          this.loadableStudyRepository.findById(request.getLoadableStudyId());
      if (!entityOpt.isPresent()) {
        throw new GenericServiceException(
            "Loadable study does not exist",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      LoadableStudy entity = entityOpt.get();
      if (null != entity.getLoadableStudyStatus()
          && LOADABLE_STUDY_STATUS_PLAN_GENERATED_ID.equals(
              entity.getLoadableStudyStatus().getId())) {
        throw new GenericServiceException(
            "Loadable study with status plan generated cannot be deleted",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      entity.setActive(false);
      this.loadableStudyRepository.save(entity);
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when saving loadable study - port data", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(e.getCode())
              .setMessage(e.getMessage())
              .setStatus(FAILED)
              .build());
    } catch (Exception e) {
      log.error("Exception when saving loadable study - port data", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Exception when saving loadable study - port data")
              .setStatus(FAILED)
              .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * @param request
   * @param responseObserver
   */
  @Override
  public void getPortRotationByLoadableStudyId(
      PortRotationRequest request, StreamObserver<PortRotationReply> responseObserver) {
    log.info("Inside getPortRotation loadable study micro service");
    PortRotationReply.Builder portRotationReplyBuilder = PortRotationReply.newBuilder();
    try {
      Optional<LoadableStudy> loadableStudy =
          this.loadableStudyRepository.findById(request.getLoadableStudyId());

      if (!loadableStudy.isPresent()) {
        log.info(INVALID_LOADABLE_STUDY_ID, request.getLoadableStudyId());
        portRotationReplyBuilder.setResponseStatus(
            ResponseStatus.newBuilder()
                .setStatus(FAILED)
                .setMessage(INVALID_LOADABLE_STUDY_ID)
                .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST));
      } else {

        List<Long> portIds =
            this.loadableStudyPortRotationRepository.findByLoadableStudyAndIsActive(
                loadableStudy.get(), true);
        if (portIds.isEmpty()) {
          log.info(INVALID_LOADABLE_STUDY_ID, request.getLoadableStudyId());
          portRotationReplyBuilder.setResponseStatus(
              ResponseStatus.newBuilder()
                  .setStatus(FAILED)
                  .setMessage(INVALID_LOADABLE_STUDY_ID)
                  .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST));
        } else {

          portIds.forEach(
              portId -> {
                PortRotationDetail.Builder builder = PortRotationDetail.newBuilder();
                builder.setPortId(portId);
                portRotationReplyBuilder.addPorts(builder);
              });

          portRotationReplyBuilder
              .setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).setMessage(SUCCESS))
              .build();
        }
      }
    } catch (Exception e) {
      log.error("Error deleting cargo nomination", e);
      portRotationReplyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setStatus(FAILED)
              .setMessage(FAILED)
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR));
    } finally {
      responseObserver.onNext(portRotationReplyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /** Delete port rotation by id */
  @Override
  public void deletePortRotation(
      PortRotationRequest request, StreamObserver<PortRotationReply> responseObserver) {
    PortRotationReply.Builder replyBuilder = PortRotationReply.newBuilder();
    try {
      Optional<LoadableStudy> loadableStudyOpt =
          this.loadableStudyRepository.findById(request.getLoadableStudyId());
      if (!loadableStudyOpt.isPresent() || !loadableStudyOpt.get().isActive()) {
        throw new GenericServiceException(
            "Loadable study does not exist",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      LoadableStudy loadableStudy = loadableStudyOpt.get();
      if (null != loadableStudy.getLoadableStudyStatus()
          && LOADABLE_STUDY_STATUS_PLAN_GENERATED_ID.equals(
              loadableStudy.getLoadableStudyStatus().getId())) {
        throw new GenericServiceException(
            "Cannot delete ports for loadable study with status - plan generated",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      Optional<LoadableStudyPortRotation> entityOpt =
          this.loadableStudyPortRotationRepository.findById(request.getId());
      if (!entityOpt.isPresent()) {
        throw new GenericServiceException(
            "port rotation does not exist",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      LoadableStudyPortRotation entity = entityOpt.get();
      if (null != entity.getOperation()
          && (LOADING_OPERATION_ID.equals(entity.getOperation().getId())
              || DISCHARGING_OPERATION_ID.equals(entity.getOperation().getId()))) {
        throw new GenericServiceException(
            "Cannot delete loading/discharging ports",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      entity.setActive(false);
      // delete ports from synoptical table
      if (!CollectionUtils.isEmpty(entity.getSynopticalTable())) {
        entity.getSynopticalTable().forEach(portRecord -> portRecord.setIsActive(false));
      }
      this.loadableStudyPortRotationRepository.save(entity);
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when deleting port rotation", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(e.getCode())
              .setMessage(e.getMessage())
              .setStatus(FAILED)
              .build());
    } catch (Exception e) {
      log.error("Exception when deleting port rotation", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Exception when deleting port rotation")
              .setStatus(FAILED)
              .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /** Get on hand quantity */
  @Override
  public void getOnHandQuantity(
      OnHandQuantityRequest request, StreamObserver<OnHandQuantityReply> responseObserver) {
    OnHandQuantityReply.Builder replyBuilder = OnHandQuantityReply.newBuilder();
    try {
      Optional<LoadableStudy> loadableStudyOpt =
          this.loadableStudyRepository.findByIdAndIsActive(request.getLoadableStudyId(), true);
      if (!loadableStudyOpt.isPresent()) {
        throw new GenericServiceException(
            "Loadable study does not exist",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      VesselReply vesselReply = this.getOhqTanks(request);
      List<OnHandQuantity> onHandQuantities =
          this.onHandQuantityRepository.findByLoadableStudyAndPortXIdAndIsActive(
              loadableStudyOpt.get(), request.getPortId(), true);
      for (VesselTankDetail tankDetail : vesselReply.getVesselTanksList()) {
        if (!tankDetail.getShowInOhqObq()
            || OHQ_VOID_TANK_CATEGORIES.contains(tankDetail.getTankCategoryId())) {
          continue;
        }
        OnHandQuantityDetail.Builder detailBuilder = OnHandQuantityDetail.newBuilder();
        detailBuilder.setFuelType(tankDetail.getTankCategoryName());
        detailBuilder.setFuelTypeShortName(tankDetail.getTankCategoryShortName());
        detailBuilder.setFuelTypeId(tankDetail.getTankCategoryId());
        detailBuilder.setTankId(tankDetail.getTankId());
        detailBuilder.setTankName(tankDetail.getShortName());
        detailBuilder.setColorCode(tankDetail.getColourCode());
        Optional<OnHandQuantity> qtyOpt =
            onHandQuantities.stream()
                .filter(
                    entity ->
                        entity.getFuelTypeXId().equals(tankDetail.getTankCategoryId())
                            && entity.getTankXId().equals(tankDetail.getTankId()))
                .findAny();
        if (qtyOpt.isPresent()) {
          OnHandQuantity qty = qtyOpt.get();
          detailBuilder.setId(qty.getId());
          Optional.ofNullable(qty.getArrivalQuantity())
              .ifPresent(item -> detailBuilder.setArrivalQuantity(valueOf(item)));
          Optional.ofNullable(qty.getArrivalVolume())
              .ifPresent(item -> detailBuilder.setArrivalVolume(valueOf(item)));
          Optional.ofNullable(qty.getDepartureQuantity())
              .ifPresent(item -> detailBuilder.setDepartureQuantity(valueOf(item)));
          Optional.ofNullable(qty.getDepartureVolume())
              .ifPresent(item -> detailBuilder.setDepartureVolume(valueOf(item)));
        }
        replyBuilder.addOnHandQuantity(detailBuilder.build());
      }
      this.createOhqVesselTankLayoutArray(vesselReply, replyBuilder);
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when fetching on hand quantities", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(e.getCode())
              .setMessage(e.getMessage())
              .setStatus(FAILED)
              .build());
    } catch (Exception e) {
      log.error("Exception when fetching on hand quantities", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Exception when fetching on hand quantities")
              .setStatus(FAILED)
              .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * Get On hand quantity tanks
   *
   * @param request
   * @return
   * @throws NumberFormatException
   * @throws GenericServiceException
   */
  private VesselReply getOhqTanks(OnHandQuantityRequest request) throws GenericServiceException {
    VesselRequest.Builder vesselGrpcRequest = VesselRequest.newBuilder();
    vesselGrpcRequest.setCompanyId(request.getCompanyId());
    vesselGrpcRequest.setVesselId(request.getVesselId());
    vesselGrpcRequest.addAllTankCategories(OHQ_TANK_CATEGORIES);
    VesselReply vesselReply = this.getVesselTanks(vesselGrpcRequest.build());
    if (!SUCCESS.equals(vesselReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Failed to fetch vessel particualrs",
          vesselReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(vesselReply.getResponseStatus().getCode())));
    }
    return vesselReply;
  }

  /**
   * Group tanks by tank group
   *
   * @param vesselReply
   * @return
   */
  private void createOhqVesselTankLayoutArray(
      VesselReply vesselReply, OnHandQuantityReply.Builder replyBuilder) {
    List<VesselTankDetail> rearTanks = new ArrayList<>();
    List<VesselTankDetail> centerTanks = new ArrayList<>();
    rearTanks.addAll(
        vesselReply.getVesselTanksList().stream()
            .filter(
                tank ->
                    OHQ_REAR_TANK_CATEGORIES.contains(tank.getTankCategoryId())
                        && tank.getShowInOhqObq())
            .collect(Collectors.toList()));
    centerTanks.addAll(
        vesselReply.getVesselTanksList().stream()
            .filter(
                tank ->
                    OHQ_CENTER_TANK_CATEGORIES.contains(tank.getTankCategoryId())
                        && tank.getShowInOhqObq())
            .collect(Collectors.toList()));
    replyBuilder.addAllTanks(this.groupTanks(centerTanks));
    replyBuilder.addAllRearTanks(this.groupTanks(rearTanks));
  }

  /**
   * Group tanks based on tank group
   *
   * @param tankDetailList
   * @return
   */
  private List<TankList> groupTanks(List<VesselTankDetail> tankDetailList) {
    Map<Integer, List<VesselTankDetail>> vesselTankMap = new HashMap<>();
    for (VesselTankDetail tank : tankDetailList) {
      Integer tankGroup = tank.getTankGroup();
      List<VesselTankDetail> list = null;
      if (null == vesselTankMap.get(tankGroup)) {
        list = new ArrayList<>();
      } else {
        list = vesselTankMap.get(tankGroup);
      }
      list.add(tank);
      vesselTankMap.put(tankGroup, list);
    }
    List<TankList> tankList = new ArrayList<>();
    List<TankDetail> tankGroup = null;
    for (Map.Entry<Integer, List<VesselTankDetail>> entry : vesselTankMap.entrySet()) {
      tankGroup = entry.getValue().stream().map(this::buildTankDetail).collect(Collectors.toList());
      Collections.sort(tankGroup, Comparator.comparing(TankDetail::getTankOrder));
      tankList.add(TankList.newBuilder().addAllVesselTank(tankGroup).build());
    }
    return tankList;
  }

  /**
   * create tank detail
   *
   * @param detail
   * @return
   */
  public TankDetail buildTankDetail(VesselTankDetail detail) {
    TankDetail.Builder builder = TankDetail.newBuilder();
    builder.setFrameNumberFrom(detail.getFrameNumberFrom());
    builder.setFrameNumberTo(detail.getFrameNumberTo());
    builder.setShortName(detail.getShortName());
    builder.setTankCategoryId(detail.getTankCategoryId());
    builder.setTankCategoryName(detail.getTankCategoryName());
    builder.setTankId(detail.getTankId());
    builder.setTankName(detail.getTankName());
    builder.setIsSlopTank(detail.getIsSlopTank());
    builder.setDensity(detail.getDensity());
    builder.setFillCapacityCubm(detail.getFillCapacityCubm());
    builder.setHeightFrom(detail.getHeightFrom());
    builder.setHeightTo(detail.getHeightTo());
    builder.setTankOrder(detail.getTankOrder());
    builder.setTankGroup(detail.getTankGroup());
    builder.setFullCapacityCubm(detail.getFullCapacityCubm());
    return builder.build();
  }

  /**
   * Get vessel fuel tanks from vessel micro service
   *
   * @param request
   * @return
   */
  public VesselReply getVesselTanks(VesselRequest request) {
    return this.vesselInfoGrpcService.getVesselTanks(request);
  }

  /** Save on hand quantity */
  @Override
  public void saveOnHandQuantity(
      OnHandQuantityDetail request, StreamObserver<OnHandQuantityReply> responseObserver) {
    OnHandQuantityReply.Builder replyBuilder = OnHandQuantityReply.newBuilder();
    try {
      OnHandQuantity entity = null;
      if (request.getId() != 0) {
        entity = this.onHandQuantityRepository.findByIdAndIsActive(request.getId(), true);
        if (null == entity) {
          throw new GenericServiceException(
              "On hand quantity with given id does not exist",
              CommonErrorCodes.E_HTTP_BAD_REQUEST,
              HttpStatusCode.BAD_REQUEST);
        }
      } else {
        entity = new OnHandQuantity();
        Optional<LoadableStudy> loadableStudyOpt =
            this.loadableStudyRepository.findByIdAndIsActive(request.getLoadableStudyId(), true);
        if (!loadableStudyOpt.isPresent()) {
          throw new GenericServiceException(
              "Loadable study does not exist",
              CommonErrorCodes.E_HTTP_BAD_REQUEST,
              HttpStatusCode.BAD_REQUEST);
        }
        entity.setLoadableStudy(loadableStudyOpt.get());
        entity.setPortXId(request.getPortId());
      }
      entity = this.buildOnHandQuantityEntity(entity, request);
      entity = this.onHandQuantityRepository.save(entity);
      replyBuilder
          .setId(entity.getId())
          .setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when saving on hand quantities", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(e.getCode())
              .setMessage(e.getMessage())
              .setStatus(FAILED)
              .build());
    } catch (Exception e) {
      log.error("Exception when saving on hand quantities", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Exception when saving on hand quantities")
              .setStatus(FAILED)
              .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * Build on hand quantity entity from request
   *
   * @param entity
   * @param request
   * @return
   */
  private OnHandQuantity buildOnHandQuantityEntity(
      OnHandQuantity entity, OnHandQuantityDetail request) {
    entity.setIsActive(true);
    entity.setFuelTypeXId(0 == request.getFuelTypeId() ? null : request.getFuelTypeId());
    entity.setTankXId(0 == request.getTankId() ? null : request.getTankId());
    entity.setArrivalQuantity(
        isEmpty(request.getArrivalQuantity())
            ? null
            : new BigDecimal(request.getArrivalQuantity()));
    entity.setArrivalVolume(
        isEmpty(request.getArrivalVolume()) ? null : new BigDecimal(request.getArrivalVolume()));
    entity.setDepartureQuantity(
        isEmpty(request.getDepartureQuantity())
            ? null
            : new BigDecimal(request.getDepartureQuantity()));
    entity.setDepartureVolume(
        isEmpty(request.getDepartureVolume())
            ? null
            : new BigDecimal(request.getDepartureVolume()));
    return entity;
  }

  /** get purpose of commingle look up */
  @Override
  public void getPurposeOfCommingle(
      PurposeOfCommingleRequest request, StreamObserver<PurposeOfCommingleReply> responseObserver) {
    PurposeOfCommingleReply.Builder reply = PurposeOfCommingleReply.newBuilder();
    try {
      Iterable<PurposeOfCommingle> purposeList = purposeOfCommingleRepository.findAll();
      purposeList.forEach(
          purposeEntity -> {
            com.cpdss.common.generated.LoadableStudy.PurposeOfCommingle.Builder purpose =
                com.cpdss.common.generated.LoadableStudy.PurposeOfCommingle.newBuilder();

            if (purposeEntity.getId() != null) {
              purpose.setId(purposeEntity.getId());
            }
            if (!StringUtils.isEmpty(purposeEntity.getPurpose())) {
              purpose.setName(purposeEntity.getPurpose());
            }
            reply.addPurposeOfCommingle(purpose);
          });
      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus(SUCCESS);
      reply.setResponseStatus(responseStatus);
    } catch (Exception e) {
      log.error("Error in getPurposeOfCommingle method ", e);
      ResponseStatus.Builder responseStatus = ResponseStatus.newBuilder();
      responseStatus.setStatus(FAILED);
      reply.setResponseStatus(responseStatus);
    } finally {
      responseObserver.onNext(reply.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void getLoadablePatternDetails(
      LoadablePatternRequest request, StreamObserver<LoadablePatternReply> responseObserver) {
    log.info("Inside get Loadable Pattern Details in loadable study micro service");
    LoadablePatternReply.Builder builder = LoadablePatternReply.newBuilder();
    try {
      Optional<LoadableStudy> loadableStudy =
          this.loadableStudyRepository.findByIdAndIsActive(request.getLoadableStudyId(), true);
      if (!loadableStudy.isPresent()) {
        log.info(INVALID_LOADABLE_STUDY_ID, request.getLoadableStudyId());
        builder.setResponseStatus(
            ResponseStatus.newBuilder()
                .setStatus(FAILED)
                .setMessage(INVALID_LOADABLE_STUDY_ID)
                .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST));
      } else {
        com.cpdss.common.generated.LoadableStudy.LoadablePattern.Builder loadablePatternBuilder =
            com.cpdss.common.generated.LoadableStudy.LoadablePattern.newBuilder();
        List<LoadablePattern> loadablePatterns =
            loadablePatternRepository.findByLoadableStudyAndIsActiveOrderByCaseNumberAsc(
                loadableStudy.get(), true);
        loadablePatterns.forEach(
            loadablePattern -> {
              loadablePatternBuilder.setLoadablePatternId(loadablePattern.getId());
              Optional.ofNullable(loadableStudy.get().getName())
                  .ifPresent(builder::setLoadableStudyName);
              DateTimeFormatter dateTimeFormatter =
                  DateTimeFormatter.ofPattern(CREATED_DATE_FORMAT);

              Optional.ofNullable(dateTimeFormatter.format(loadablePattern.getCreatedDate()))
                  .ifPresent(builder::setLoadablePatternCreatedDate);
              Optional.ofNullable(loadablePattern.getLoadableStudyStatus())
                  .ifPresent(loadablePatternBuilder::setLoadableStudyStatusId);

              Optional.ofNullable(loadablePattern.getConstraints())
                  .ifPresent(loadablePatternBuilder::setConstraints);
              Optional.ofNullable(loadablePattern.getDifferenceColor())
                  .ifPresent(loadablePatternBuilder::setTotalDifferenceColor);
              List<LoadablePatternDetails> loadablePatternDetails =
                  loadablePatternDetailsRepository.findByLoadablePatternAndIsActive(
                      loadablePattern, true);
              loadablePatternBuilder.clearLoadablePatternCargoDetails();
              loadablePatternDetails.forEach(
                  loadablePatternDetail -> {
                    LoadablePatternCargoDetails.Builder loadablePatternCargoDetailsBuilder =
                        LoadablePatternCargoDetails.newBuilder();
                    Optional.ofNullable(loadablePatternDetail.getPriority())
                        .ifPresent(
                            priority -> loadablePatternCargoDetailsBuilder.setPriority(priority));
                    Optional.ofNullable(loadablePatternDetail.getQuantity())
                        .ifPresent(
                            quantity ->
                                loadablePatternCargoDetailsBuilder.setQuantity(
                                    String.valueOf(quantity)));
                    Optional.ofNullable(loadablePatternDetail.getTankId())
                        .ifPresent(tankId -> loadablePatternCargoDetailsBuilder.setTankId(tankId));
                    Optional.ofNullable(loadablePatternDetail.getCargoAbbreviation())
                        .ifPresent(
                            cargoAbbreviation ->
                                loadablePatternCargoDetailsBuilder.setCargoAbbreviation(
                                    cargoAbbreviation));
                    Optional.ofNullable(loadablePatternDetail.getCargoColor())
                        .ifPresent(
                            cargoColor ->
                                loadablePatternCargoDetailsBuilder.setCargoColor(cargoColor));
                    Optional.ofNullable(loadablePatternDetail.getDifference())
                        .ifPresent(
                            difference ->
                                loadablePatternCargoDetailsBuilder.setDifference(
                                    String.valueOf(difference)));

                    Optional.ofNullable(loadablePatternDetail.getDifferenceColor())
                        .ifPresent(
                            differenceColor ->
                                loadablePatternCargoDetailsBuilder.setDifferenceColor(
                                    differenceColor));
                    Optional.ofNullable(loadablePatternDetail.getId())
                        .ifPresent(
                            loadablePatternDetailsId ->
                                loadablePatternCargoDetailsBuilder.setLoadablePatternDetailsId(
                                    loadablePatternDetailsId));
                    Optional.ofNullable(loadablePatternDetail.getIsCommingle())
                        .ifPresent(
                            isCommingle ->
                                loadablePatternCargoDetailsBuilder.setIsCommingle(isCommingle));
                    if (loadablePatternCargoDetailsBuilder.getIsCommingle()) {
                      Optional<LoadablePatternComingleDetails> loadablePatternComingleDetails =
                          loadablePatternComingleDetailsRepository
                              .findByLoadablePatternDetailsIdAndIsActive(
                                  loadablePatternDetail.getId(), true);
                      if (loadablePatternComingleDetails.isPresent()) {
                        Optional.ofNullable(loadablePatternComingleDetails.get().getId())
                            .ifPresent(
                                id ->
                                    loadablePatternCargoDetailsBuilder
                                        .setLoadablePatternCommingleDetailsId(id));
                      }
                    }
                    loadablePatternBuilder.addLoadablePatternCargoDetails(
                        loadablePatternCargoDetailsBuilder);
                  });
              builder.addLoadablePattern(loadablePatternBuilder);
            });
        VesselReply vesselReply = this.getTankListForPattern(loadableStudy.get().getVesselXId());
        if (!SUCCESS.equals(vesselReply.getResponseStatus().getStatus())) {
          builder.setResponseStatus(ResponseStatus.newBuilder().setStatus(FAILED).build());
        } else {
          builder.addAllTanks(this.groupTanks(vesselReply.getVesselTanksList()));
          builder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
        }
      }
    } catch (Exception e) {
      log.error("Exception when fetching get Loadable Pattern Details", e);
      builder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Exception when fetching on Loadable Pattern Details")
              .setStatus(FAILED)
              .build());
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * @param vesselXId
   * @return VesselReply
   */
  private VesselReply getTankListForPattern(Long vesselId) {
    VesselRequest.Builder vesselGrpcRequest = VesselRequest.newBuilder();
    vesselGrpcRequest.setVesselId(vesselId);
    vesselGrpcRequest.addAllTankCategories(CARGO_TANK_CATEGORIES);
    VesselReply vesselReply = this.getVesselTanks(vesselGrpcRequest.build());
    return vesselReply;
  }

  /** Get commingle cargo for a loadable study */
  @Override
  public void getCommingleCargo(
      CommingleCargoRequest request, StreamObserver<CommingleCargoReply> responseObserver) {
    com.cpdss.common.generated.LoadableStudy.CommingleCargoReply.Builder replyBuilder =
        CommingleCargoReply.newBuilder();
    try {
      Optional<LoadableStudy> loadableStudyOpt =
          this.loadableStudyRepository.findByIdAndIsActive(request.getLoadableStudyId(), true);
      if (!loadableStudyOpt.isPresent()) {
        throw new GenericServiceException(
            "Loadable study does not exist", CommonErrorCodes.E_HTTP_BAD_REQUEST, null);
      }
      List<com.cpdss.loadablestudy.entity.CommingleCargo> commingleCargoList =
          this.commingleCargoRepository.findByLoadableStudyXIdAndIsActive(
              request.getLoadableStudyId(), true);
      buildCommingleCargoReply(commingleCargoList, replyBuilder);
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS));
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when fetching loadable study - getCommingleCargo", e);
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(FAILED));
    } catch (Exception e) {
      log.error("Exception when fetching loadable study - getCommingleCargo", e);
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(FAILED));
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * build commingleCargo reply with commingle values from db
   *
   * @param commingleCargoList
   * @param replyBuilder
   */
  private void buildCommingleCargoReply(
      List<com.cpdss.loadablestudy.entity.CommingleCargo> commingleCargoList,
      CommingleCargoReply.Builder replyBuilder) {
    if (!CollectionUtils.isEmpty(commingleCargoList)) {
      commingleCargoList.forEach(
          commingleCargo -> {
            CommingleCargo.Builder builder = CommingleCargo.newBuilder();
            Optional.ofNullable(commingleCargo.getId()).ifPresent(builder::setId);
            Optional.ofNullable(commingleCargo.getPurposeXid()).ifPresent(builder::setPurposeId);
            Optional.ofNullable(commingleCargo.getIsSlopOnly()).ifPresent(builder::setSlopOnly);
            // Convert comma separated tank list to arrays
            if (commingleCargo.getTankIds() != null && !commingleCargo.getTankIds().isEmpty()) {
              List<Long> tankIdList =
                  Stream.of(commingleCargo.getTankIds().split(","))
                      .map(String::trim)
                      .map(Long::parseLong)
                      .collect(Collectors.toList());
              builder.addAllPreferredTanks(tankIdList);
            }
            Optional.ofNullable(commingleCargo.getCargo1Xid()).ifPresent(builder::setCargo1Id);
            Optional.ofNullable(commingleCargo.getCargo1Pct())
                .ifPresent(cargo1Pct -> builder.setCargo1Pct(String.valueOf(cargo1Pct)));
            Optional.ofNullable(commingleCargo.getCargo2Xid()).ifPresent(builder::setCargo2Id);
            Optional.ofNullable(commingleCargo.getCargo2Pct())
                .ifPresent(cargo2Pct -> builder.setCargo2Pct(String.valueOf(cargo2Pct)));
            Optional.ofNullable(commingleCargo.getQuantity())
                .ifPresent(quantity -> builder.setQuantity(String.valueOf(quantity)));
            replyBuilder.addCommingleCargo(builder);
          });
    }
  }

  /** Save commingle cargo for the specific loadable study */
  @Override
  public void saveCommingleCargo(
      CommingleCargoRequest request, StreamObserver<CommingleCargoReply> responseObserver) {
    com.cpdss.common.generated.LoadableStudy.CommingleCargoReply.Builder replyBuilder =
        CommingleCargoReply.newBuilder();
    try {
      Optional<LoadableStudy> loadableStudyOpt =
          this.loadableStudyRepository.findByIdAndIsActive(request.getLoadableStudyId(), true);
      if (!loadableStudyOpt.isPresent()) {
        throw new GenericServiceException(
            "Loadable study does not exist", CommonErrorCodes.E_HTTP_BAD_REQUEST, null);
      }
      if (!CollectionUtils.isEmpty(request.getCommingleCargoList())) {
        // for existing commingle cargo find missing ids in request and delete them
        deleteCommingleCargo(request);
        Long loadableStudyId = request.getLoadableStudyId();
        List<com.cpdss.loadablestudy.entity.CommingleCargo> commingleEntities = new ArrayList<>();
        // for id = 0 save as new commingle cargo
        request
            .getCommingleCargoList()
            .forEach(
                commingleCargo -> {
                  try {
                    com.cpdss.loadablestudy.entity.CommingleCargo commingleCargoEntity = null;
                    if (commingleCargo != null && commingleCargo.getId() != 0) {
                      Optional<com.cpdss.loadablestudy.entity.CommingleCargo>
                          existingCommingleCargo =
                              this.commingleCargoRepository.findByIdAndIsActive(
                                  commingleCargo.getId(), true);
                      if (!existingCommingleCargo.isPresent()) {
                        throw new GenericServiceException(
                            "commingle cargo does not exist",
                            CommonErrorCodes.E_HTTP_BAD_REQUEST,
                            HttpStatusCode.BAD_REQUEST);
                      }
                      commingleCargoEntity = existingCommingleCargo.get();
                      commingleCargoEntity =
                          buildCommingleCargo(
                              commingleCargoEntity, commingleCargo, loadableStudyId);
                    } else if (commingleCargo != null && commingleCargo.getId() == 0) {
                      commingleCargoEntity = new com.cpdss.loadablestudy.entity.CommingleCargo();
                      commingleCargoEntity =
                          buildCommingleCargo(
                              commingleCargoEntity, commingleCargo, loadableStudyId);
                    }
                    commingleEntities.add(commingleCargoEntity);
                  } catch (Exception e) {
                    log.error("Exception in creating entities for save commingle cargo", e);
                    throw new RuntimeException(e);
                  }
                });
        // save all entities
        this.commingleCargoRepository.saveAll(commingleEntities);
      }
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS));
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when saving CommingleCargo", e);
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(FAILED));
    } catch (Exception e) {
      log.error("Exception when saving CommingleCargo", e);
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(FAILED));
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * build entity to save in commingle cargo
   *
   * @param commingleCargoEntity
   * @param commingleCargoRequestRecord
   * @return
   */
  private com.cpdss.loadablestudy.entity.CommingleCargo buildCommingleCargo(
      com.cpdss.loadablestudy.entity.CommingleCargo commingleCargoEntity,
      CommingleCargo requestRecord,
      Long loadableStudyId) {
    commingleCargoEntity.setLoadableStudyXId(loadableStudyId);
    commingleCargoEntity.setPurposeXid(requestRecord.getPurposeId());
    commingleCargoEntity.setTankIds(
        StringUtils.collectionToCommaDelimitedString(requestRecord.getPreferredTanksList()));
    commingleCargoEntity.setCargo1Xid(requestRecord.getCargo1Id());
    commingleCargoEntity.setCargo1Pct(
        !StringUtils.isEmpty(requestRecord.getCargo1Pct())
            ? new BigDecimal(requestRecord.getCargo1Pct())
            : null);
    commingleCargoEntity.setCargo2Xid(requestRecord.getCargo2Id());
    commingleCargoEntity.setCargo2Pct(
        !StringUtils.isEmpty(requestRecord.getCargo2Pct())
            ? new BigDecimal(requestRecord.getCargo2Pct())
            : null);
    commingleCargoEntity.setQuantity(
        !StringUtils.isEmpty(requestRecord.getQuantity())
            ? new BigDecimal(requestRecord.getQuantity())
            : null);
    commingleCargoEntity.setIsActive(true);
    commingleCargoEntity.setIsSlopOnly(requestRecord.getSlopOnly());
    return commingleCargoEntity;
  }

  /**
   * delete commingle cargo not available in the save request
   *
   * @param request
   */
  private void deleteCommingleCargo(CommingleCargoRequest request) {
    List<com.cpdss.loadablestudy.entity.CommingleCargo> commingleCargoEntityList =
        this.commingleCargoRepository.findByLoadableStudyXIdAndIsActive(
            request.getLoadableStudyId(), true);
    List<Long> requestedCommingleCargoIds = null;
    List<Long> existingCommingleCargoIds = null;
    if (!request.getCommingleCargoList().isEmpty()) {
      requestedCommingleCargoIds =
          request.getCommingleCargoList().stream()
              .map(CommingleCargo::getId)
              .collect(Collectors.toList());
    }
    if (!commingleCargoEntityList.isEmpty()) {
      existingCommingleCargoIds =
          commingleCargoEntityList.stream()
              .map(com.cpdss.loadablestudy.entity.CommingleCargo::getId)
              .collect(Collectors.toList());
    }
    /**
     * find commingle ids available for the loadable study that are not available in save request so
     * that they can be deleted
     */
    if (!CollectionUtils.isEmpty(requestedCommingleCargoIds)
        && !CollectionUtils.isEmpty(existingCommingleCargoIds)) {
      existingCommingleCargoIds.removeAll(requestedCommingleCargoIds);
      commingleCargoRepository.deleteCommingleCargo(existingCommingleCargoIds);
    }
  }

  @Override
  public void getLoadablePatternCommingleDetails(
      LoadablePatternCommingleDetailsRequest request,
      StreamObserver<LoadablePatternCommingleDetailsReply> responseObserver) {
    log.info("Inside get Loadable Pattern Commingle Details in loadable study micro service");
    LoadablePatternCommingleDetailsReply.Builder builder =
        LoadablePatternCommingleDetailsReply.newBuilder();
    try {
      Optional<LoadablePatternComingleDetails> loadablePatternComingleDetails =
          loadablePatternComingleDetailsRepository.findByIdAndIsActive(
              request.getLoadablePatternCommingleDetailsId(), true);
      if (!loadablePatternComingleDetails.isPresent()) {
        log.info(
            INVALID_LOADABLE_PATTERN_COMMINGLE_DETAIL_ID,
            request.getLoadablePatternCommingleDetailsId());
        builder.setResponseStatus(
            ResponseStatus.newBuilder()
                .setStatus(FAILED)
                .setMessage(INVALID_LOADABLE_PATTERN_COMMINGLE_DETAIL_ID)
                .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST));
      } else {

        buildLoadablePatternComingleDetails(loadablePatternComingleDetails.get(), builder);
        builder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
      }

    } catch (Exception e) {
      log.error("Exception when fetching get Loadable Pattern Details", e);
      builder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Exception when fetching Loadable Pattern Commingle Details")
              .setStatus(FAILED)
              .build());
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * @param loadablePatternComingleDetails void
   * @param builder
   */
  private void buildLoadablePatternComingleDetails(
      LoadablePatternComingleDetails loadablePatternComingleDetails,
      com.cpdss.common.generated.LoadableStudy.LoadablePatternCommingleDetailsReply.Builder
          builder) {
    Optional.ofNullable(loadablePatternComingleDetails.getApi())
        .ifPresent(api -> builder.setApi(String.valueOf(api)));
    Optional.ofNullable(loadablePatternComingleDetails.getCargo1Abbrivation())
        .ifPresent(cargo1Abbrivation -> builder.setCargo1Abbrivation(cargo1Abbrivation));

    Optional.ofNullable(loadablePatternComingleDetails.getCargo2Abbrivation())
        .ifPresent(cargo2Abbrivation -> builder.setCargo2Abbrivation(cargo2Abbrivation));

    Optional.ofNullable(loadablePatternComingleDetails.getCargo1Percentage())
        .ifPresent(
            cargo1Percentage -> builder.setCargo1Percentage(String.valueOf(cargo1Percentage)));

    Optional.ofNullable(loadablePatternComingleDetails.getCargo2Percentage())
        .ifPresent(
            cargo2Percentage -> builder.setCargo2Percentage(String.valueOf(cargo2Percentage)));

    Optional.ofNullable(loadablePatternComingleDetails.getCargo1Quantity())
        .ifPresent(cargo1Quantity -> builder.setCargo1Quantity(String.valueOf(cargo1Quantity)));

    Optional.ofNullable(loadablePatternComingleDetails.getCargo2Quantity())
        .ifPresent(cargo2Quantity -> builder.setCargo2Quantity(String.valueOf(cargo2Quantity)));

    Optional.ofNullable(loadablePatternComingleDetails.getGrade())
        .ifPresent(grade -> builder.setGrade(grade));

    Optional.ofNullable(loadablePatternComingleDetails.getQuantity())
        .ifPresent(quantity -> builder.setQuantity(String.valueOf(quantity)));

    Optional.ofNullable(loadablePatternComingleDetails.getTankShortName())
        .ifPresent(tankShortName -> builder.setTankShortName(tankShortName));

    Optional.ofNullable(loadablePatternComingleDetails.getTemperature())
        .ifPresent(temperature -> builder.setTemperature(String.valueOf(temperature)));

    Optional.ofNullable(loadablePatternComingleDetails.getId()).ifPresent(id -> builder.setId(id));
  }

  @Override
  public void generateLoadablePatterns(
      AlgoRequest request, StreamObserver<AlgoReply> responseObserver) {
    log.info("Inside generateLoadablePatterns service");
    com.cpdss.common.generated.LoadableStudy.AlgoReply.Builder replyBuilder =
        AlgoReply.newBuilder();
    try {

      Optional<LoadableStudy> loadableStudyOpt =
          loadableStudyRepository.findByIdAndIsActive(request.getLoadableStudyId(), true);
      if (loadableStudyOpt.isPresent()) {

        ModelMapper modelMapper = new ModelMapper();
        com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy =
            new com.cpdss.loadablestudy.domain.LoadableStudy();

        loadableStudy.setId(loadableStudyOpt.get().getId());
        loadableStudy.setVesselId(loadableStudyOpt.get().getVesselXId());
        Optional.ofNullable(loadableStudyOpt.get().getDetails())
            .ifPresent(details -> loadableStudy.setDetails(details));

        Optional.ofNullable(loadableStudyOpt.get().getVoyage())
            .ifPresent(voyage -> loadableStudy.setVoyageNo(voyage.getVoyageNo()));

        Optional.ofNullable(loadableStudyOpt.get().getName())
            .ifPresent(name -> loadableStudy.setName(name));
        Optional.ofNullable(loadableStudyOpt.get().getCharterer())
            .ifPresent(charterer -> loadableStudy.setCharterer(charterer));
        Optional.ofNullable(loadableStudyOpt.get().getSubCharterer())
            .ifPresent(subCharterer -> loadableStudy.setSubCharterer(subCharterer));

        Optional.ofNullable(loadableStudyOpt.get().getDraftMark())
            .ifPresent(draftMark -> loadableStudy.setDraftMark(String.valueOf(draftMark)));

        Optional.ofNullable(loadableStudyOpt.get().getDraftRestriction())
            .ifPresent(
                draftRestriction ->
                    loadableStudy.setDraftRestriction(String.valueOf(draftRestriction)));

        Optional.ofNullable(loadableStudyOpt.get().getLoadLineXId())
            .ifPresent(loadLineId -> loadableStudy.setLoadlineId(loadLineId));
        Optional.ofNullable(loadableStudyOpt.get().getEstimatedMaxSag())
            .ifPresent(
                estimatedMaxSag ->
                    loadableStudy.setEstimatedMaxSG(String.valueOf(estimatedMaxSag)));
        Optional.ofNullable(loadableStudyOpt.get().getMaxAirTemperature())
            .ifPresent(
                maxAirTemperature ->
                    loadableStudy.setMaxAirTemp(String.valueOf(maxAirTemperature)));
        Optional.ofNullable(loadableStudyOpt.get().getMaxWaterTemperature())
            .ifPresent(
                maxWaterTemperature ->
                    loadableStudy.setMaxWaterTemp(String.valueOf(maxWaterTemperature)));

        List<CargoNomination> cargoNominations =
            cargoNominationRepository.findByLoadableStudyXIdAndIsActive(
                request.getLoadableStudyId(), true);

        loadableStudy.setCargoNomination(new ArrayList<>());
        cargoNominations.forEach(
            cargoNomination -> {
              com.cpdss.loadablestudy.domain.CargoNomination cargoNominationDto =
                  new com.cpdss.loadablestudy.domain.CargoNomination();
              cargoNominationDto =
                  modelMapper.map(
                      cargoNomination, com.cpdss.loadablestudy.domain.CargoNomination.class);
              loadableStudy.getCargoNomination().add(cargoNominationDto);
            });

        loadableStudy.setCommingleCargos(new ArrayList<>());
        List<com.cpdss.loadablestudy.entity.CommingleCargo> commingleCargos =
            commingleCargoRepository.findByLoadableStudyXIdAndIsActive(
                loadableStudyOpt.get().getId(), true);
        commingleCargos.forEach(
            commingleCargo -> {
              com.cpdss.loadablestudy.domain.CommingleCargo commingleCargoDto =
                  new com.cpdss.loadablestudy.domain.CommingleCargo();
              commingleCargoDto =
                  modelMapper.map(
                      commingleCargo, com.cpdss.loadablestudy.domain.CommingleCargo.class);
              commingleCargoDto.setCargo1Id(commingleCargo.getCargo1Xid());
              commingleCargoDto.setCargo2Id(commingleCargo.getCargo2Xid());
              commingleCargoDto.setCargo1Percentage(commingleCargo.getCargo1Pct().toString());
              commingleCargoDto.setCargo2Percentage(commingleCargo.getCargo2Pct().toString());
              loadableStudy.getCommingleCargos().add(commingleCargoDto);
            });

        List<LoadableQuantity> loadableQuantity =
            loadableQuantityRepository.findByLoadableStudyXIdAndIsActive(
                request.getLoadableStudyId(), true);
        if (!loadableQuantity.isEmpty()) {

          loadableQuantity.forEach(
              loadableQunty -> {
                com.cpdss.loadablestudy.domain.LoadableQuantity loadableQuantityDto =
                    new com.cpdss.loadablestudy.domain.LoadableQuantity();

                Optional.ofNullable(loadableQunty.getBallast())
                    .ifPresent(ballast -> loadableQuantityDto.setBallast(String.valueOf(ballast)));
                Optional.ofNullable(loadableQunty.getBoilerWaterOnBoard())
                    .ifPresent(
                        boilerWaterOnBoard ->
                            loadableQuantityDto.setBoilerWaterOnBoard(
                                String.valueOf(boilerWaterOnBoard)));
                Optional.ofNullable(loadableQunty.getConstant())
                    .ifPresent(
                        constant -> loadableQuantityDto.setConstant(String.valueOf(constant)));
                Optional.ofNullable(loadableQunty.getDeadWeight())
                    .ifPresent(
                        deadWeight ->
                            loadableQuantityDto.setDeadWeight(String.valueOf(deadWeight)));
                Optional.ofNullable(loadableQunty.getDisplacementAtDraftRestriction())
                    .ifPresent(
                        displacementAtDraftRestriction ->
                            loadableQuantityDto.setDisplacmentDraftRestriction(
                                String.valueOf(displacementAtDraftRestriction)));
                Optional.ofNullable(loadableQunty.getDistanceFromLastPort())
                    .ifPresent(
                        distanceFromLastPort ->
                            loadableQuantityDto.setDistanceFromLastPort(
                                String.valueOf(distanceFromLastPort)));
                Optional.ofNullable(loadableQunty.getDraftRestriction())
                    .ifPresent(
                        draftRestriction ->
                            loadableQuantityDto.setDraftRestriction(
                                String.valueOf(draftRestriction)));
                Optional.ofNullable(loadableQunty.getEstimatedDOOnBoard())
                    .ifPresent(
                        estimatedDOOnBoard ->
                            loadableQuantityDto.setEstDOOnBoard(
                                String.valueOf(estimatedDOOnBoard)));
                Optional.ofNullable(loadableQunty.getEstimatedFOOnBoard())
                    .ifPresent(
                        estimatedFOOnBoard ->
                            loadableQuantityDto.setEstDOOnBoard(
                                String.valueOf(estimatedFOOnBoard)));
                Optional.ofNullable(loadableQunty.getEstimatedFWOnBoard())
                    .ifPresent(
                        estimatedFWOnBoard ->
                            loadableQuantityDto.setEstFreshWaterOnBoard(
                                String.valueOf(estimatedFWOnBoard)));
                Optional.ofNullable(loadableQunty.getEstimatedSagging())
                    .ifPresent(
                        estimatedSagging ->
                            loadableQuantityDto.setEstSagging(String.valueOf(estimatedSagging)));
                Optional.ofNullable(loadableQunty.getEstimatedSeaDensity())
                    .ifPresent(
                        estimatedSeaDensity ->
                            loadableQuantityDto.setEstSeaDensity(
                                String.valueOf(estimatedSeaDensity)));
                Optional.ofNullable(loadableQunty.getFoConsumptionInSZ())
                    .ifPresent(
                        foConsumptionInSZ ->
                            loadableQuantityDto.setFoConInSZ(String.valueOf(foConsumptionInSZ)));
                Optional.ofNullable(loadableQunty.getId())
                    .ifPresent(id -> loadableQuantityDto.setId(id));
                Optional.ofNullable(loadableQunty.getLightWeight())
                    .ifPresent(
                        vesselLightWeight ->
                            loadableQuantityDto.setVesselLightWeight(
                                String.valueOf(vesselLightWeight)));
                Optional.ofNullable(loadableQunty.getOtherIfAny())
                    .ifPresent(
                        otherIfAny ->
                            loadableQuantityDto.setOtherIfAny(String.valueOf(otherIfAny)));
                Optional.ofNullable(loadableQunty.getPortId())
                    .ifPresent(
                        portId -> loadableQuantityDto.setPortId(Long.valueOf(portId.toString())));
                Optional.ofNullable(loadableQunty.getRunningDays())
                    .ifPresent(
                        runningDays ->
                            loadableQuantityDto.setRunningDays(String.valueOf(runningDays)));
                Optional.ofNullable(loadableQunty.getRunningHours())
                    .ifPresent(
                        runningHours ->
                            loadableQuantityDto.setRunningHours(String.valueOf(runningHours)));
                Optional.ofNullable(loadableQunty.getSaggingDeduction())
                    .ifPresent(
                        saggingDeduction ->
                            loadableQuantityDto.setSaggingDeduction(
                                String.valueOf(saggingDeduction)));
                Optional.ofNullable(loadableQunty.getSgCorrection())
                    .ifPresent(
                        sgCorrection ->
                            loadableQuantityDto.setSgCorrection(String.valueOf(sgCorrection)));
                Optional.ofNullable(loadableQunty.getSubTotal())
                    .ifPresent(
                        subTotal -> loadableQuantityDto.setSubTotal(String.valueOf(subTotal)));
                Optional.ofNullable(loadableQunty.getTotalFoConsumption())
                    .ifPresent(
                        totalFoConsumption ->
                            loadableQuantityDto.setTotalFoConsumption(
                                String.valueOf(totalFoConsumption)));
                Optional.ofNullable(loadableQunty.getTotalQuantity())
                    .ifPresent(
                        totalQuantity ->
                            loadableQuantityDto.setTotalQuantity(String.valueOf(totalQuantity)));
                Optional.ofNullable(loadableQunty.getTpcatDraft())
                    .ifPresent(
                        tpcatDraft -> loadableQuantityDto.setTpc(String.valueOf(tpcatDraft)));
                Optional.ofNullable(loadableQunty.getVesselAverageSpeed())
                    .ifPresent(
                        VesselAverageSpeed ->
                            loadableQuantityDto.setVesselAverageSpeed(
                                String.valueOf(VesselAverageSpeed)));

                loadableStudy.setLoadableQuantity(loadableQuantityDto);
              });
        }

        List<LoadableStudyPortRotation> loadableStudyPortRotations =
            loadableStudyPortRotationRepository.findByLoadableStudyAndIsActive(
                request.getLoadableStudyId(), true);

        loadableStudy.setLoadableStudyPortRotation(new ArrayList<>());
        if (!loadableStudyPortRotations.isEmpty()) {
          loadableStudyPortRotations.forEach(
              loadableStudyPortRotation -> {
                com.cpdss.loadablestudy.domain.LoadableStudyPortRotation
                    loadableStudyPortRotationDto =
                        new com.cpdss.loadablestudy.domain.LoadableStudyPortRotation();
                loadableStudyPortRotationDto =
                    modelMapper.map(
                        loadableStudyPortRotation,
                        com.cpdss.loadablestudy.domain.LoadableStudyPortRotation.class);
                loadableStudy.getLoadableStudyPortRotation().add(loadableStudyPortRotationDto);
              });
        }

        loadableStudy.setCargoNominationOperationDetails(new ArrayList<>());
        List<CargoNominationPortDetails> cargoNominationOperationDetails =
            cargoNominationOperationDetailsRepository.findByCargoNominationAndIsActive(
                cargoNominations, true);
        cargoNominationOperationDetails.forEach(
            cargoNominationOperationDetail -> {
              com.cpdss.loadablestudy.domain.CargoNominationOperationDetails
                  cargoNominationOperationDetailDto =
                      new com.cpdss.loadablestudy.domain.CargoNominationOperationDetails();
              cargoNominationOperationDetailDto.setCargoNominationXId(
                  cargoNominationOperationDetail.getCargoNomination().getId());
              cargoNominationOperationDetailDto.setId(cargoNominationOperationDetail.getId());
              cargoNominationOperationDetailDto.setPortId(
                  cargoNominationOperationDetail.getPortId());
              cargoNominationOperationDetailDto.setQuantity(
                  String.valueOf(cargoNominationOperationDetail.getQuantity()));
              loadableStudy
                  .getCargoNominationOperationDetails()
                  .add(cargoNominationOperationDetailDto);
            });

        loadableStudy.setOnHandQuantity(new ArrayList<>());
        List<OnHandQuantity> onHandQuantities =
            onHandQuantityRepository.findByLoadableStudyAndIsActive(loadableStudyOpt.get(), true);
        onHandQuantities.forEach(
            onHandQuantity -> {
              com.cpdss.loadablestudy.domain.OnHandQuantity onHandQuantityDto =
                  new com.cpdss.loadablestudy.domain.OnHandQuantity();
              onHandQuantityDto =
                  modelMapper.map(
                      onHandQuantity, com.cpdss.loadablestudy.domain.OnHandQuantity.class);
              loadableStudy.getOnHandQuantity().add(onHandQuantityDto);
            });

        loadableStudy.setOnBoardQuantity(new ArrayList<>());
        List<OnBoardQuantity> onBoardQuantities =
            onBoardQuantityRepository.findByLoadableStudyAndIsActive(loadableStudyOpt.get(), true);
        onBoardQuantities.forEach(
            onBoardQuantity -> {
              com.cpdss.loadablestudy.domain.OnBoardQuantity onBoardQuantityDto =
                  new com.cpdss.loadablestudy.domain.OnBoardQuantity();
              onBoardQuantityDto =
                  modelMapper.map(
                      onBoardQuantity, com.cpdss.loadablestudy.domain.OnBoardQuantity.class);
              loadableStudy.getOnBoardQuantity().add(onBoardQuantityDto);
            });

        GetPortInfoByPortIdsRequest.Builder portsBuilder = GetPortInfoByPortIdsRequest.newBuilder();
        List<Long> portIds =
            loadableStudyPortRotationRepository.findByLoadableStudyAndIsActive(
                loadableStudyOpt.get(), true);
        portIds.forEach(
            portId -> {
              portsBuilder.addId(portId);
            });

        loadableStudy.setPortDetails(new ArrayList<PortDetails>());
        PortReply portReply = portInfoGrpcService.getPortInfoByPortIds(portsBuilder.build());
        portReply
            .getPortsList()
            .forEach(
                portList -> {
                  PortDetails portDetails = new PortDetails();
                  portDetails.setAverageTideHeight(portList.getAverageTideHeight());
                  portDetails.setCode(portList.getCode());
                  portDetails.setDensitySeaWater(portList.getWaterDensity());
                  portDetails.setId(portList.getId());
                  portDetails.setName(portList.getName());
                  portDetails.setTideHeight(portList.getTideHeight());
                  loadableStudy.getPortDetails().add(portDetails);
                });

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.writeValue(new File("loadableStudy.json"), loadableStudy);
        replyBuilder =
            AlgoReply.newBuilder()
                .setResponseStatus(
                    ResponseStatus.newBuilder().setMessage(SUCCESS).setStatus(SUCCESS).build());
      } else {
        log.info("INVALID_LOADABLE_STUDY {} - ", request.getLoadableStudyId());
        replyBuilder =
            AlgoReply.newBuilder()
                .setResponseStatus(
                    ResponseStatus.newBuilder()
                        .setStatus(FAILED)
                        .setMessage(INVALID_LOADABLE_STUDY_ID)
                        .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST)
                        .build());
      }
    } catch (Exception e) {
      log.error("Exception when when calling algo  ", e);
      replyBuilder =
          AlgoReply.newBuilder()
              .setResponseStatus(
                  ResponseStatus.newBuilder()
                      .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
                      .setMessage("Error when calling algo ")
                      .setStatus(FAILED)
                      .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /** Get on board quantity details corresponding to a loadable study */
  @Override
  public void getOnBoardQuantity(
      OnBoardQuantityRequest request, StreamObserver<OnBoardQuantityReply> responseObserver) {
    OnBoardQuantityReply.Builder replyBuilder = OnBoardQuantityReply.newBuilder();
    try {
      Voyage voyage = this.voyageRepository.findByIdAndIsActive(request.getVoyageId(), true);
      if (null == voyage) {
        throw new GenericServiceException(
            "Voyage does not exist",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      Optional<LoadableStudy> loadableStudyOpt =
          this.loadableStudyRepository.findByIdAndIsActive(request.getLoadableStudyId(), true);
      if (!loadableStudyOpt.isPresent()) {
        throw new GenericServiceException(
            "Loadable study does not exist",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      VesselReply vesselReply = this.getObqTanks(request);
      replyBuilder.addAllOnBoardQuantity(
          this.buildOnBoardQuantity(
              request, loadableStudyOpt.get(), voyage, vesselReply.getVesselTanksList()));
      replyBuilder.addAllTanks(this.groupTanks(vesselReply.getVesselTanksList()));
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    } catch (Exception e) {
      log.error("Exception when fetching on board quantities", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Exception when fetching on board quantities")
              .setStatus(FAILED)
              .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * Build obq detail objects
   *
   * @param request
   * @param loadableStudy
   * @param vesselTanksList
   * @param replyBuilder
   */
  private List<OnBoardQuantityDetail> buildOnBoardQuantity(
      OnBoardQuantityRequest request,
      LoadableStudy loadableStudy,
      Voyage voyage,
      List<VesselTankDetail> vesselTanksList) {
    List<OnBoardQuantity> obqEntities =
        this.onBoardQuantityRepository.findByLoadableStudyAndPortIdAndIsActive(
            loadableStudy, request.getPortId(), true);
    List<CargoHistory> cargoHistories = null;
    List<OnBoardQuantityDetail> obqDetailList = new ArrayList<>();
    List<VesselTankDetail> modifieableList = new ArrayList<>(vesselTanksList);
    Collections.sort(modifieableList, Comparator.comparing(VesselTankDetail::getTankDisplayOrder));
    for (VesselTankDetail tank : modifieableList) {
      OnBoardQuantityDetail.Builder builder = OnBoardQuantityDetail.newBuilder();
      builder.setTankId(tank.getTankId());
      builder.setTankName(tank.getShortName());
      Optional<OnBoardQuantity> entityOpt =
          obqEntities.stream().filter(e -> e.getTankId().equals(tank.getTankId())).findAny();
      if (entityOpt.isPresent()) {
        OnBoardQuantity entity = entityOpt.get();
        builder.setId(entity.getId());
        Optional.ofNullable(entity.getCargoId()).ifPresent(builder::setCargoId);
        Optional.ofNullable(entity.getSounding())
            .ifPresent(item -> builder.setSounding(item.toString()));
        Optional.ofNullable(entity.getPlannedArrivalWeight())
            .ifPresent(item -> builder.setWeight(item.toString()));
        Optional.ofNullable(entity.getVolume())
            .ifPresent(item -> builder.setVolume(item.toString()));
        Optional.ofNullable(entity.getColorCode()).ifPresent(builder::setColorCode);
        Optional.ofNullable(entity.getAbbreviation()).ifPresent(builder::setAbbreviation);
      } else {
        // lazy loading the cargo history
        if (null == cargoHistories) {
          cargoHistories = this.findCargoHistoryForPrvsVoyage(voyage);
        }
        Optional<CargoHistory> cargoHistoryOpt =
            cargoHistories.stream().filter(e -> e.getTankId().equals(tank.getTankId())).findAny();
        if (cargoHistoryOpt.isPresent()) {
          CargoHistory dto = cargoHistoryOpt.get();
          Optional.ofNullable(dto.getCargoId()).ifPresent(builder::setCargoId);
          Optional.ofNullable(dto.getCargoColor()).ifPresent(builder::setColorCode);
          Optional.ofNullable(dto.getAbbreviation()).ifPresent(builder::setAbbreviation);
        }
      }
      obqDetailList.add(builder.build());
    }
    return obqDetailList;
  }

  /**
   * find voyage history for previous voyage
   *
   * @param voyage
   * @return
   */
  private List<CargoHistory> findCargoHistoryForPrvsVoyage(Voyage voyage) {
    if (voyage.getVoyageStartDate() != null && voyage.getVoyageEndDate() != null) {
      Voyage previousVoyage =
          this.voyageRepository
              .findFirstByVoyageEndDateLessThanAndVesselXIdAndIsActiveOrderByVoyageEndDateDesc(
                  voyage.getVoyageStartDate(), voyage.getVesselXId(), true);
      if (null == previousVoyage) {
        log.error("Could not find previous voyage of {}", voyage.getVoyageNo());
      } else {
        VoyageHistory voyageHistory =
            this.voyageHistoryRepository.findFirstByVoyageOrderByPortOrderDesc(previousVoyage);
        if (null == voyageHistory) {
          log.error("Could not find voyage history for voyage: {}", previousVoyage.getVoyageNo());
        } else {
          return this.cargoHistoryRepository.findCargoHistory(
              previousVoyage.getId(), voyageHistory.getLoadingPortId());
        }
      }
    } else {
      log.error(
          "Voyage start/end date for voyage {} not set and hence, cargo history cannot be fetched",
          voyage.getVoyageNo());
    }
    return new ArrayList<>();
  }

  /**
   * Fetch vessel fuel and fresh water tanks
   *
   * @param request
   * @return
   * @throws GenericServiceException
   */
  private VesselReply getObqTanks(OnBoardQuantityRequest request) throws GenericServiceException {
    VesselRequest.Builder vesselGrpcRequest = VesselRequest.newBuilder();
    vesselGrpcRequest.setCompanyId(request.getCompanyId());
    vesselGrpcRequest.setVesselId(request.getVesselId());
    vesselGrpcRequest.addAllTankCategories(CARGO_TANK_CATEGORIES);
    VesselReply vesselReply = this.getVesselTanks(vesselGrpcRequest.build());
    if (!SUCCESS.equals(vesselReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Failed to fetch vessel particualrs",
          vesselReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(vesselReply.getResponseStatus().getCode())));
    }
    return vesselReply;
  }

  /** Save On board quantity details */
  @Override
  public void saveOnBoardQuantity(
      OnBoardQuantityDetail request, StreamObserver<OnBoardQuantityReply> responseObserver) {
    OnBoardQuantityReply.Builder replyBuilder = OnBoardQuantityReply.newBuilder();
    try {
      Optional<LoadableStudy> loadableStudyOpt =
          this.loadableStudyRepository.findByIdAndIsActive(request.getLoadableStudyId(), true);
      if (!loadableStudyOpt.isPresent()) {
        throw new GenericServiceException(
            "Loadable study does not exist",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      OnBoardQuantity entity = null;
      if (request.getId() == 0) {
        entity = new OnBoardQuantity();
        entity.setLoadableStudy(loadableStudyOpt.get());
      } else {
        entity = this.onBoardQuantityRepository.findByIdAndIsActive(request.getId(), true);
        if (null == entity) {
          throw new GenericServiceException(
              "On hand quantity does not exist",
              CommonErrorCodes.E_HTTP_BAD_REQUEST,
              HttpStatusCode.BAD_REQUEST);
        }
      }
      this.buildOnBoardQuantityEntity(entity, request);
      entity = this.onBoardQuantityRepository.save(entity);
      replyBuilder.setId(entity.getId());
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when saving on board quantities", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(e.getCode())
              .setMessage("GenericServiceException when saving on board quantities")
              .setStatus(FAILED)
              .build());
    } catch (Exception e) {
      log.error("Exception when saving on board quantities", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Exception when saving on board quantities")
              .setStatus(FAILED)
              .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * Build on board quantity entity
   *
   * @param entity
   * @param request
   */
  private void buildOnBoardQuantityEntity(OnBoardQuantity entity, OnBoardQuantityDetail request) {
    entity.setCargoId(0 == request.getCargoId() ? null : request.getCargoId());
    entity.setTankId(request.getTankId());
    entity.setPortId(request.getPortId());
    entity.setSounding(
        isEmpty(request.getSounding()) ? null : new BigDecimal(request.getSounding()));
    entity.setPlannedArrivalWeight(
        isEmpty(request.getWeight()) ? null : new BigDecimal(request.getWeight()));
    entity.setVolume(isEmpty(request.getVolume()) ? null : new BigDecimal(request.getVolume()));
    entity.setColorCode(isEmpty(request.getColorCode()) ? null : request.getColorCode());
    entity.setAbbreviation(isEmpty(request.getAbbreviation()) ? null : request.getAbbreviation());
    entity.setIsActive(true);
  }

  /**
   * @param request
   * @param responseObserver
   */
  @Override
  public void saveAlgoLoadableStudyStatus(
      AlgoStatusRequest request, StreamObserver<AlgoStatusReply> responseObserver) {
    AlgoStatusReply.Builder replyBuilder = AlgoStatusReply.newBuilder();
    try {
      Optional<LoadableStudyAlgoStatus> loadableStudyAlgoStatusOpt =
          loadableStudyAlgoStatusRepository.findByProcessIdAndIsActive(
              request.getProcesssId(), true);
      if (!loadableStudyAlgoStatusOpt.isPresent()) {
        replyBuilder.setResponseStatus(
            ResponseStatus.newBuilder()
                .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST)
                .setMessage("Invalid process Id")
                .build());
      } else {

        loadableStudyAlgoStatusRepository.updateLoadableStudyAlgoStatus(
            request.getLoadableStudystatusId(), request.getProcesssId(), true);
        replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
      }
    } catch (Exception e) {
      log.error("Exception when saving Algo Loadable Study Status ", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Exception when saving Algo Loadable Study Status")
              .setStatus(FAILED)
              .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void getSynopticalTable(
      SynopticalTableRequest request, StreamObserver<SynopticalTableReply> responseObserver) {
    SynopticalTableReply.Builder replyBuilder = SynopticalTableReply.newBuilder();
    try {
      Optional<LoadableStudy> loadableStudyOpt =
          this.loadableStudyRepository.findById(request.getLoadableStudyId());
      if (!loadableStudyOpt.isPresent()) {
        throw new GenericServiceException(
            "Loadable study does not exist", CommonErrorCodes.E_HTTP_BAD_REQUEST, null);
      }
      List<SynopticalTable> synopticalTableList =
          this.synopticalTableRepository.findByLoadableStudyXIdAndIsActive(
              request.getLoadableStudyId(), true);
      if (!synopticalTableList.isEmpty()) {
        VesselReply vesselReply =
            this.getSynopticalTableVesselData(request, loadableStudyOpt.get());
        List<VesselTankDetail> sortedTankList = new ArrayList<>(vesselReply.getVesselTanksList());
        Collections.sort(
            sortedTankList, Comparator.comparing(VesselTankDetail::getTankDisplayOrder));
        buildSynopticalTableReply(
            synopticalTableList,
            this.getSynopticalTablePortDetails(synopticalTableList),
            this.getSynopticalTablePortRotations(request.getLoadableStudyId()),
            loadableStudyOpt.get(),
            sortedTankList,
            vesselReply.getVesselLoadableQuantityDetails(),
            replyBuilder);
        this.setSynopticalTableCargoTanks(sortedTankList, replyBuilder);
      }
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS));
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when fetching loadable study - port data", e);
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(FAILED));
    } catch (Exception e) {
      log.error("Exception when fetching loadable study - port data", e);
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(FAILED));
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * Create tank detail for synoptical table
   *
   * @param vesselReply
   * @param replyBuilder
   */
  private void setSynopticalTableCargoTanks(
      List<VesselTankDetail> sortedTankList, SynopticalTableReply.Builder replyBuilder) {
    for (VesselTankDetail tank : sortedTankList) {
      if (!CARGO_TANK_CATEGORIES.contains(tank.getTankCategoryId())) {
        continue;
      }
      replyBuilder.addVesselTank(
          TankDetail.newBuilder()
              .setTankId(tank.getTankId())
              .setShortName(tank.getShortName())
              .build());
    }
  }

  /**
   * Fetch port details for synoptical table
   *
   * @param synopticalTableList
   * @return
   * @throws GenericServiceException
   */
  private PortReply getSynopticalTablePortDetails(List<SynopticalTable> synopticalTableList)
      throws GenericServiceException {
    GetPortInfoByPortIdsRequest.Builder portReqBuilder = GetPortInfoByPortIdsRequest.newBuilder();
    buildPortIdsRequestSynoptical(portReqBuilder, synopticalTableList);
    PortReply portReply = portInfoGrpcService.getPortInfoByPortIds(portReqBuilder.build());
    if (portReply != null
        && portReply.getResponseStatus() != null
        && !SUCCESS.equalsIgnoreCase(portReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Error in calling port service within getSynopticalTable",
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    return portReply;
  }

  /**
   * Build port request to fetch port related fields from port master
   *
   * @param portReqBuilder
   * @param synopticalTableList
   */
  private void buildPortIdsRequestSynoptical(
      com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest.Builder portReqBuilder,
      List<SynopticalTable> synopticalTableList) {
    // build fetch port details request object
    if (!CollectionUtils.isEmpty(synopticalTableList)) {
      synopticalTableList.forEach(
          synopticalRecord ->
              Optional.ofNullable(synopticalRecord.getPortXid()).ifPresent(portReqBuilder::addId));
    }
  }

  /**
   * Get port rotation details for synoptical table
   *
   * @param loadableStudyId
   * @return
   */
  private List<LoadableStudyPortRotation> getSynopticalTablePortRotations(Long loadableStudyId) {
    return this.loadableStudyPortRotationRepository.findByLoadableStudyAndIsActive(
        loadableStudyId, true);
  }

  /**
   * Build Synoptical records for synoptical table
   *
   * @param synopticalTableList
   * @param portReply
   * @param vesselLoadableQuantityDetails
   * @param vesselReply
   * @param vesselReply
   * @param replyBuilder
   */
  private void buildSynopticalTableReply(
      List<SynopticalTable> synopticalTableList,
      PortReply portReply,
      List<LoadableStudyPortRotation> portRotations,
      LoadableStudy loadableStudy,
      List<VesselTankDetail> sortedTankList,
      VesselLoadableQuantityDetails vesselLoadableQuantityDetails,
      SynopticalTableReply.Builder replyBuilder) {
    if (!CollectionUtils.isEmpty(synopticalTableList)) {
      List<OnBoardQuantity> obqEntities =
          this.onBoardQuantityRepository.findByLoadableStudyAndIsActive(loadableStudy, true);
      List<OnHandQuantity> ohqEntities =
          this.onHandQuantityRepository.findByLoadableStudyAndIsActive(loadableStudy, true);
      List<LoadablePlanStowageBallastDetails> ballastDetails =
          this.loadablePlanStowageBallastDetailsRepository.findBallastDetailsForLoadableStudy(
              loadableStudy.getId(), CONFIRMED_STATUS_ID);
      List<SynopticalRecord> records = new ArrayList<>();
      synopticalTableList.forEach(
          synopticalEntity -> {
            SynopticalRecord.Builder builder = SynopticalRecord.newBuilder();
            this.buildSynopticalRecord(synopticalEntity, builder, portReply);
            this.setSynopticalEtaEtdEstimated(synopticalEntity, builder, portRotations);
            this.setSynopticalCargoDetails(obqEntities, synopticalEntity, builder, sortedTankList);
            this.setSynopticalOhqData(ohqEntities, synopticalEntity, builder, sortedTankList);
            this.setSynopticalTableVesselParticulars(
                synopticalEntity, builder, vesselLoadableQuantityDetails);
            this.setSynopticalTableLoadicatorData(synopticalEntity, builder);
            this.setBallastDetails(synopticalEntity, builder, ballastDetails);
            records.add(builder.build());
          });
      Collections.sort(
          records,
          Comparator.comparing(SynopticalRecord::getPortOrder)
              .thenComparing(Comparator.comparing(SynopticalRecord::getOperationType)));
      replyBuilder.addAllSynopticalRecords(records);
    }
  }

  /**
   * Set ballast details
   *
   * @param synopticalEntity
   * @param builder
   * @param ballastDetails
   */
  private void setBallastDetails(
      SynopticalTable synopticalEntity,
      com.cpdss.common.generated.LoadableStudy.SynopticalRecord.Builder builder,
      List<LoadablePlanStowageBallastDetails> ballastDetails) {
    List<LoadablePlanStowageBallastDetails> portBallastList =
        ballastDetails.stream()
            .filter(
                bd ->
                    synopticalEntity.getPortXid().equals(bd.getPortXId())
                        && synopticalEntity.getOperationType().equals(bd.getOperationType()))
            .collect(Collectors.toList());
    if (null != portBallastList && !portBallastList.isEmpty()) {
      BigDecimal plannedSum =
          portBallastList.stream()
              .map(LoadablePlanStowageBallastDetails::getQuantity)
              .reduce(BigDecimal.ZERO, BigDecimal::add);
      builder.setBallastPlanned(valueOf(plannedSum));
    }
    Optional.ofNullable(synopticalEntity.getBallastActual())
        .ifPresent(item -> builder.setBallastActual(valueOf(item)));
  }

  /**
   * Set loadicator data in synoptical table record
   *
   * @param synopticalEntity
   * @param builder
   */
  private void setSynopticalTableLoadicatorData(
      SynopticalTable synopticalEntity,
      com.cpdss.common.generated.LoadableStudy.SynopticalRecord.Builder builder) {
    SynopticalTableLoadicatorData loadicatorData =
        this.synopticalTableLoadicatorDataRepository.findBySynopticalTableIdAndIsActive(
            synopticalEntity.getId(), true);
    if (null != loadicatorData) {
      com.cpdss.common.generated.LoadableStudy.SynopticalTableLoadicatorData.Builder dataBuilder =
          com.cpdss.common.generated.LoadableStudy.SynopticalTableLoadicatorData.newBuilder();
      Optional.ofNullable(loadicatorData.getBlindSetor())
          .ifPresent(item -> dataBuilder.setBlindSector(valueOf(item)));
      Optional.ofNullable(loadicatorData.getHog())
          .ifPresent(item -> dataBuilder.setHogSag(valueOf(item)));
      Optional.ofNullable(loadicatorData.getCalculatedDraftAftActual())
          .ifPresent(item -> dataBuilder.setCalculatedDraftAftActual(valueOf(item)));
      Optional.ofNullable(loadicatorData.getCalculatedDraftAftPlanned())
          .ifPresent(item -> dataBuilder.setCalculatedDraftAftPlanned(valueOf(item)));
      Optional.ofNullable(loadicatorData.getCalculatedDraftFwdActual())
          .ifPresent(item -> dataBuilder.setCalculatedDraftFwdActual(valueOf(item)));
      Optional.ofNullable(loadicatorData.getCalculatedDraftFwdPlanned())
          .ifPresent(item -> dataBuilder.setCalculatedDraftFwdPlanned(valueOf(item)));
      Optional.ofNullable(loadicatorData.getCalculatedDraftMidActual())
          .ifPresent(item -> dataBuilder.setCalculatedDraftMidActual(valueOf(item)));
      Optional.ofNullable(loadicatorData.getCalculatedDraftMidPlanned())
          .ifPresent(item -> dataBuilder.setCalculatedDraftMidPlanned(valueOf(item)));
      Optional.ofNullable(loadicatorData.getCalculatedTrimActual())
          .ifPresent(item -> dataBuilder.setCalculatedTrimActual(valueOf(item)));
      Optional.ofNullable(loadicatorData.getCalculatedTrimPlanned())
          .ifPresent(item -> dataBuilder.setCalculatedTrimPlanned(valueOf(item)));
      this.setFinalDraftValues(dataBuilder, loadicatorData);
      builder.setLoadicatorData(dataBuilder.build());
    }
  }

  /**
   * Set final draft values
   *
   * @param dataBuilder
   * @param loadicatorData
   */
  private void setFinalDraftValues(
      com.cpdss.common.generated.LoadableStudy.SynopticalTableLoadicatorData.Builder dataBuilder,
      SynopticalTableLoadicatorData loadicatorData) {
    BigDecimal hog = BigDecimal.ZERO;
    if (null != loadicatorData.getHog()) {
      hog = BigDecimal.ZERO;
    }
    BigDecimal calculatedDraftFwd = BigDecimal.ZERO;
    if (null != loadicatorData.getCalculatedDraftFwdActual()) {
      calculatedDraftFwd = loadicatorData.getCalculatedDraftFwdActual();
    } else if (null != loadicatorData.getCalculatedDraftFwdPlanned()) {
      calculatedDraftFwd = loadicatorData.getCalculatedDraftFwdPlanned();
    }
    BigDecimal calculatedDraftAft = BigDecimal.ZERO;
    if (null != loadicatorData.getCalculatedDraftAftActual()) {
      calculatedDraftAft = loadicatorData.getCalculatedDraftAftActual();
    } else if (null != loadicatorData.getCalculatedDraftAftPlanned()) {
      calculatedDraftAft = loadicatorData.getCalculatedDraftAftPlanned();
    }
    BigDecimal calculatedDraftMid = BigDecimal.ZERO;
    if (null != loadicatorData.getCalculatedDraftMidActual()) {
      calculatedDraftMid = loadicatorData.getCalculatedDraftMidActual();
    } else if (null != loadicatorData.getCalculatedDraftMidPlanned()) {
      calculatedDraftMid = loadicatorData.getCalculatedDraftMidPlanned();
    }
    dataBuilder.setFinalDraftAft(valueOf(hog.add(calculatedDraftAft)));
    dataBuilder.setFinalDraftFwd(valueOf(hog.add(calculatedDraftFwd)));
    dataBuilder.setFinalDraftMid(valueOf(hog.add(calculatedDraftMid)));
  }

  /**
   * Set vessel particular data
   *
   * @param synopticalEntity
   * @param builder
   * @param vesselLoadableQuantityDetails
   */
  private void setSynopticalTableVesselParticulars(
      SynopticalTable synopticalEntity,
      SynopticalRecord.Builder builder,
      VesselLoadableQuantityDetails vesselLoadableQuantityDetails) {
    Optional.ofNullable(synopticalEntity.getOthersPlanned())
        .ifPresent(item -> builder.setOthersPlanned(valueOf(item)));
    Optional.ofNullable(synopticalEntity.getOthersActual())
        .ifPresent(item -> builder.setOthersActual(valueOf(item)));
    builder.setConstantPlanned(vesselLoadableQuantityDetails.getConstant());
    Optional.ofNullable(synopticalEntity.getConstantActual())
        .ifPresent(item -> builder.setConstantActual(valueOf(item)));
    builder.setTotalDwtPlanned(vesselLoadableQuantityDetails.getDwt());
    Optional.ofNullable(synopticalEntity.getDeadWeightActual())
        .ifPresent(item -> builder.setTotalDwtActual(valueOf(item)));
    builder.setDisplacementPlanned(vesselLoadableQuantityDetails.getDisplacmentDraftRestriction());
    Optional.ofNullable(synopticalEntity.getDisplacementActual())
        .ifPresent(item -> builder.setDisplacementActual(valueOf(item)));
  }

  /**
   * Set ohq data
   *
   * @param ohqEntities
   * @param synopticalEntity
   * @param builder
   * @param sortedTankList
   */
  private void setSynopticalOhqData(
      List<OnHandQuantity> ohqEntities,
      SynopticalTable synopticalEntity,
      SynopticalRecord.Builder builder,
      List<VesselTankDetail> sortedTankList) {
    List<OnHandQuantity> portSpecificEntities =
        ohqEntities.stream()
            .filter(entity -> entity.getPortXId().equals(synopticalEntity.getPortXid()))
            .collect(Collectors.toList());
    Map<String, BigDecimal> sumMap = new HashMap<>();
    for (VesselTankDetail tank : sortedTankList) {
      if (!OHQ_TANK_CATEGORIES.contains(tank.getTankCategoryId()) || !tank.getShowInOhqObq()) {
        continue;
      }
      SynopticalOhqRecord.Builder ohqBuilder = SynopticalOhqRecord.newBuilder();
      ohqBuilder.setTankId(tank.getTankId());
      ohqBuilder.setTankName(tank.getShortName());
      ohqBuilder.setFuelTypeId(tank.getTankCategoryId());
      ohqBuilder.setFuelType(tank.getTankCategoryShortName());
      Optional<OnHandQuantity> ohqOpt =
          portSpecificEntities.stream()
              .filter(ohq -> ohq.getTankXId().equals(tank.getTankId()))
              .findAny();
      if (ohqOpt.isPresent()) {
        OnHandQuantity ohq = ohqOpt.get();
        if (synopticalEntity.getOperationType().equals(SYNOPTICAL_TABLE_OP_TYPE_ARRIVAL)) {
          if (null != ohq.getArrivalQuantity()) {
            ohqBuilder.setPlannedWeight(valueOf(ohq.getArrivalQuantity()));
          }
          if (null != ohq.getActualArrivalQuantity()) {
            ohqBuilder.setActualWeight(valueOf(ohq.getActualArrivalQuantity()));
          }
        } else if (synopticalEntity.getOperationType().equals(SYNOPTICAL_TABLE_OP_TYPE_DEPARTURE)) {
          if (null != ohq.getDepartureQuantity()) {
            ohqBuilder.setPlannedWeight(valueOf(ohq.getDepartureQuantity()));
          }
          if (null != ohq.getActualDepartureQuantity()) {
            ohqBuilder.setActualWeight(valueOf(ohq.getActualDepartureQuantity()));
          }
        }
        this.calculateOhqSum(tank, ohq, sumMap, synopticalEntity.getOperationType());
      }
      builder.addOhq(ohqBuilder.build());
    }
    this.setOhqTotalValues(builder, sumMap);
  }

  /**
   * Set ohq total values
   *
   * @param builder
   * @param sumMap
   * @param portSpecificEntities
   */
  private void setOhqTotalValues(SynopticalRecord.Builder builder, Map<String, BigDecimal> sumMap) {
    if (null != sumMap.get(FUEL_OIL_TANK_CATEGORY_ID + ACTUAL_SUM_MAP_SUFFIX)) {
      builder.setFoActualTotal(
          valueOf(sumMap.get(FUEL_OIL_TANK_CATEGORY_ID + ACTUAL_SUM_MAP_SUFFIX)));
    }
    if (null != sumMap.get(FUEL_OIL_TANK_CATEGORY_ID + PLANNED_SUM_MAP_SUFFIX)) {
      builder.setFoPlannedTotal(
          valueOf(sumMap.get(FUEL_OIL_TANK_CATEGORY_ID + PLANNED_SUM_MAP_SUFFIX)));
    }

    if (null != sumMap.get(DIESEL_OIL_TANK_CATEGORY_ID + ACTUAL_SUM_MAP_SUFFIX)) {
      builder.setDoActualTotal(
          valueOf(sumMap.get(DIESEL_OIL_TANK_CATEGORY_ID + ACTUAL_SUM_MAP_SUFFIX)));
    }
    if (null != sumMap.get(DIESEL_OIL_TANK_CATEGORY_ID + PLANNED_SUM_MAP_SUFFIX)) {
      builder.setDoPlannedTotal(
          valueOf(sumMap.get(DIESEL_OIL_TANK_CATEGORY_ID + PLANNED_SUM_MAP_SUFFIX)));
    }

    if (null == sumMap.get(LUBRICANT_OIL_TANK_CATEGORY_ID + ACTUAL_SUM_MAP_SUFFIX)) {
      sumMap.put(LUBRICANT_OIL_TANK_CATEGORY_ID + ACTUAL_SUM_MAP_SUFFIX, BigDecimal.ZERO);
    }

    if (null == sumMap.get(LUBRICANT_OIL_TANK_CATEGORY_ID + PLANNED_SUM_MAP_SUFFIX)) {
      sumMap.put(LUBRICANT_OIL_TANK_CATEGORY_ID + PLANNED_SUM_MAP_SUFFIX, BigDecimal.ZERO);
    }

    if (null == sumMap.get(LUBRICATING_OIL_TANK_CATEGORY_ID + ACTUAL_SUM_MAP_SUFFIX)) {
      sumMap.put(LUBRICATING_OIL_TANK_CATEGORY_ID + ACTUAL_SUM_MAP_SUFFIX, BigDecimal.ZERO);
    }

    if (null == sumMap.get(LUBRICATING_OIL_TANK_CATEGORY_ID + PLANNED_SUM_MAP_SUFFIX)) {
      sumMap.put(LUBRICATING_OIL_TANK_CATEGORY_ID + PLANNED_SUM_MAP_SUFFIX, BigDecimal.ZERO);
    }

    builder.setLubeActualTotal(
        valueOf(
            sumMap
                .get(LUBRICATING_OIL_TANK_CATEGORY_ID + ACTUAL_SUM_MAP_SUFFIX)
                .add(sumMap.get(LUBRICANT_OIL_TANK_CATEGORY_ID + ACTUAL_SUM_MAP_SUFFIX))));
    builder.setLubePlannedTotal(
        valueOf(
            sumMap
                .get(LUBRICATING_OIL_TANK_CATEGORY_ID + PLANNED_SUM_MAP_SUFFIX)
                .add(sumMap.get(LUBRICANT_OIL_TANK_CATEGORY_ID + PLANNED_SUM_MAP_SUFFIX))));
  }

  private void calculateOhqSum(
      VesselTankDetail tank,
      OnHandQuantity ohq,
      Map<String, BigDecimal> sumMap,
      String operationType) {
    String keyActual = tank.getTankCategoryId() + ACTUAL_SUM_MAP_SUFFIX;
    String keyPlanned = tank.getTankCategoryId() + PLANNED_SUM_MAP_SUFFIX;
    BigDecimal actualQty = null;
    BigDecimal plannedQty = null;
    if (operationType.equals(SYNOPTICAL_TABLE_OP_TYPE_ARRIVAL)) {
      actualQty = ohq.getActualArrivalQuantity();
      plannedQty = ohq.getArrivalQuantity();
    } else if (operationType.equals(SYNOPTICAL_TABLE_OP_TYPE_DEPARTURE)) {
      actualQty = ohq.getActualDepartureQuantity();
      plannedQty = ohq.getDepartureQuantity();
    }
    if (null != actualQty) {
      if (null == sumMap.get(keyActual)) {
        sumMap.put(keyActual, actualQty);
      } else {
        sumMap.put(keyActual, sumMap.get(keyActual).add(actualQty));
      }
    }
    if (null != plannedQty) {
      if (null == sumMap.get(keyPlanned)) {
        sumMap.put(keyPlanned, plannedQty);
      } else {
        sumMap.put(keyPlanned, sumMap.get(keyPlanned).add(plannedQty));
      }
    }
  }

  /**
   * Set cargo details on synoptical record
   *
   * @param obqEntities
   * @param synopticalEntity
   * @param builder
   * @param vesselReply
   * @param loadableStudy
   */
  private void setSynopticalCargoDetails(
      List<OnBoardQuantity> obqEntities,
      SynopticalTable synopticalEntity,
      SynopticalRecord.Builder builder,
      List<VesselTankDetail> sortedTankList) {
    List<OnBoardQuantity> portSpecificEntities =
        obqEntities.stream()
            .filter(entity -> entity.getPortId().equals(synopticalEntity.getPortXid()))
            .collect(Collectors.toList());
    BigDecimal cargoActualTotal = BigDecimal.ZERO;
    BigDecimal cargoPlannedTotal = BigDecimal.ZERO;
    for (VesselTankDetail tank : sortedTankList) {
      if (!CARGO_TANK_CATEGORIES.contains(tank.getTankCategoryId())) {
        continue;
      }
      SynopticalCargoRecord.Builder cargoBuilder = SynopticalCargoRecord.newBuilder();
      cargoBuilder.setTankId(tank.getTankId());
      cargoBuilder.setTankName(tank.getShortName());
      Optional<OnBoardQuantity> obqOpt =
          portSpecificEntities.stream()
              .filter(obq -> obq.getTankId().equals(tank.getTankId()))
              .findAny();
      if (obqOpt.isPresent()) {
        OnBoardQuantity obqEntity = obqOpt.get();
        if (synopticalEntity.getOperationType().equals(SYNOPTICAL_TABLE_OP_TYPE_ARRIVAL)) {
          if (null != obqEntity.getActualArrivalWeight()) {
            cargoBuilder.setActualWeight(valueOf(obqEntity.getActualArrivalWeight()));
            cargoActualTotal = cargoActualTotal.add(obqEntity.getActualArrivalWeight());
          }
          if (null != obqEntity.getPlannedArrivalWeight()) {
            cargoBuilder.setPlannedWeight(valueOf(obqEntity.getPlannedArrivalWeight()));
            cargoPlannedTotal = cargoPlannedTotal.add(obqEntity.getPlannedArrivalWeight());
          }
        } else if (synopticalEntity.getOperationType().equals(SYNOPTICAL_TABLE_OP_TYPE_DEPARTURE)) {
          if (null != obqEntity.getActualDepartureWeight()) {
            cargoBuilder.setActualWeight(valueOf(obqEntity.getActualDepartureWeight()));
            cargoActualTotal = cargoActualTotal.add(obqEntity.getActualDepartureWeight());
          }
          if (null != obqEntity.getPlannedDepartureWeight()) {
            cargoBuilder.setPlannedWeight(valueOf(obqEntity.getPlannedDepartureWeight()));
            cargoPlannedTotal = cargoPlannedTotal.add(obqEntity.getPlannedDepartureWeight());
          }
        }
      }
      builder.setCargoActualTotal(valueOf(cargoActualTotal));
      builder.setCargoPlannedTotal(valueOf(cargoPlannedTotal));
      builder.addCargo(cargoBuilder.build());
    }
  }

  /**
   * Build synoptical table record
   *
   * @param synopticalEntity
   * @param builder
   * @param portReply
   */
  private void buildSynopticalRecord(
      SynopticalTable synopticalEntity, SynopticalRecord.Builder builder, PortReply portReply) {
    Optional.ofNullable(synopticalEntity.getId()).ifPresent(builder::setId);
    Optional.ofNullable(synopticalEntity.getPortXid()).ifPresent(builder::setPortId);
    if (portReply != null) {
      portReply.getPortsList().stream()
          .forEach(
              port -> {
                Optional.ofNullable(port.getName()).ifPresent(builder::setPortName);
                Optional.ofNullable(port.getWaterDensity()).ifPresent(builder::setSpecificGravity);
              });
    }
    Optional.ofNullable(synopticalEntity.getOperationType()).ifPresent(builder::setOperationType);
    Optional.ofNullable(synopticalEntity.getDistance())
        .ifPresent(distance -> builder.setDistance(String.valueOf(distance)));
    Optional.ofNullable(synopticalEntity.getSpeed())
        .ifPresent(speed -> builder.setSpeed(String.valueOf(speed)));
    Optional.ofNullable(synopticalEntity.getRunningHours())
        .ifPresent(runningHours -> builder.setRunningHours(String.valueOf(runningHours)));
    Optional.ofNullable(synopticalEntity.getInPortHours())
        .ifPresent(inPortHours -> builder.setInPortHours(String.valueOf(inPortHours)));
    Optional.ofNullable(synopticalEntity.getTimeOfSunrise()).ifPresent(builder::setTimeOfSunrise);
    Optional.ofNullable(synopticalEntity.getTimeOfSunset()).ifPresent(builder::setTimeOfSunset);
    // If specific gravity is available in database then replace the port master
    // value
    Optional.ofNullable(synopticalEntity.getSpecificGravity())
        .ifPresent(sg -> builder.setSpecificGravity(valueOf(sg)));
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
    Optional.ofNullable(synopticalEntity.getHwTideFrom())
        .ifPresent(hwTideFrom -> builder.setHwTideFrom(String.valueOf(hwTideFrom)));
    Optional.ofNullable(synopticalEntity.getHwTideTimeFrom())
        .ifPresent(hwTideTimeFrom -> builder.setHwTideTimeFrom(formatter.format(hwTideTimeFrom)));
    Optional.ofNullable(synopticalEntity.getHwTideTo())
        .ifPresent(hwTideTo -> builder.setHwTideTo(String.valueOf(hwTideTo)));
    Optional.ofNullable(synopticalEntity.getHwTideTimeTo())
        .ifPresent(hwTideTimeTo -> builder.setHwTideTimeTo(formatter.format(hwTideTimeTo)));
    Optional.ofNullable(synopticalEntity.getLwTideFrom())
        .ifPresent(lwTideFrom -> builder.setLwTideFrom(String.valueOf(lwTideFrom)));
    Optional.ofNullable(synopticalEntity.getLwTideTimeFrom())
        .ifPresent(lwTideTimeFrom -> builder.setLwTideTimeFrom(formatter.format(lwTideTimeFrom)));
    Optional.ofNullable(synopticalEntity.getLwTideTo())
        .ifPresent(lwTideTo -> builder.setLwTideTo(String.valueOf(lwTideTo)));
    Optional.ofNullable(synopticalEntity.getLwTideTimeTo())
        .ifPresent(lwTideTimeTo -> builder.setLwTideTimeTo(String.valueOf(lwTideTimeTo)));
    if (null != synopticalEntity.getEtaActual()) {
      builder.setEtaEtdActual(formatter.format(synopticalEntity.getEtaActual()));
    } else if (null != synopticalEntity.getEtdActual()) {
      builder.setEtaEtdActual(formatter.format(synopticalEntity.getEtdActual()));
    }
  }

  /**
   * Set eta and etd estimated values
   *
   * @param synopticalEntity
   * @param builder
   * @param portRotations
   */
  private void setSynopticalEtaEtdEstimated(
      SynopticalTable synopticalEntity,
      SynopticalRecord.Builder builder,
      List<LoadableStudyPortRotation> portRotations) {
    Optional<LoadableStudyPortRotation> portRotation =
        portRotations.stream()
            .filter(
                pr -> pr.getId().equals(synopticalEntity.getLoadableStudyPortRotation().getId()))
            .findFirst();
    if (portRotation.isPresent()) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
      if (null != portRotation.get().getEta()) {
        builder.setEtaEtdEstimated(formatter.format(portRotation.get().getEta()));
      } else if (null != portRotation.get().getEtd()) {
        builder.setEtaEtdEstimated(formatter.format(portRotation.get().getEtd()));
      }
      if (null != portRotation.get().getPortOrder()) {
        builder.setPortOrder(portRotation.get().getPortOrder());
      }
    }
  }

  /**
   * * Get vessel data for synoptical table
   *
   * @param request
   * @param loadableStudy
   * @return
   * @throws GenericServiceException
   */
  private VesselReply getSynopticalTableVesselData(
      SynopticalTableRequest request, LoadableStudy loadableStudy) throws GenericServiceException {
    VesselRequest.Builder vesselGrpcRequest = VesselRequest.newBuilder();
    vesselGrpcRequest.setVesselId(request.getVesselId());
    Optional.ofNullable(loadableStudy.getLoadLineXId())
        .ifPresent(vesselGrpcRequest::setVesselDraftConditionId);
    Optional.ofNullable(loadableStudy.getDraftMark())
        .ifPresent(item -> vesselGrpcRequest.setDraftExtreme(valueOf(item)));
    vesselGrpcRequest.addAllTankCategories(SYNOPTICAL_TABLE_TANK_CATEGORIES);
    VesselReply vesselReply = this.getVesselDetailForSynopticalTable(vesselGrpcRequest.build());
    if (!SUCCESS.equals(vesselReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Failed to fetch vessel particualrs",
          vesselReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(vesselReply.getResponseStatus().getCode())));
    }
    return vesselReply;
  }

  /**
   * Call vessel info grpc service for synoptical table data
   *
   * @param request
   * @return
   */
  public VesselReply getVesselDetailForSynopticalTable(VesselRequest request) {
    return this.vesselInfoGrpcService.getVesselDetailForSynopticalTable(request);
  }

  @Override
  public void getLoadablePlanDetails(
      LoadablePlanDetailsRequest request,
      StreamObserver<LoadablePlanDetailsReply> responseObserver) {
    log.info("inside getLoadablePlanDetails loadable study service");
    LoadablePlanDetailsReply.Builder replyBuilder = LoadablePlanDetailsReply.newBuilder();
    try {
      Optional<LoadablePattern> loadablePatternOpt =
          this.loadablePatternRepository.findByIdAndIsActive(request.getLoadablePatternId(), true);
      if (!loadablePatternOpt.isPresent()) {
        log.info(INVALID_LOADABLE_PATTERN_ID, request.getLoadablePatternId());
        replyBuilder.setResponseStatus(
            ResponseStatus.newBuilder()
                .setStatus(FAILED)
                .setMessage(INVALID_LOADABLE_PATTERN_ID)
                .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST));
      } else {
        List<LoadablePlanQuantity> loadablePlanQuantities =
            loadablePlanQuantityRepository.findByLoadablePatternAndIsActive(
                loadablePatternOpt.get(), true);
        buildLoadablePlanQuantity(loadablePlanQuantities, replyBuilder);
        List<LoadablePlanStowageDetails> loadablePlanStowageDetails =
            loadablePlanStowageDetailsRespository.findByLoadablePatternAndIsActive(
                loadablePatternOpt.get(), true);
        buildLoadablePlanStowageCargoDetails(loadablePlanStowageDetails, replyBuilder);
        replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
      }
    } catch (Exception e) {
      log.error("Exception when getLoadablePlanDetails ", e);
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(FAILED));
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * @param loadablePlanStowageDetails
   * @param replyBuilder void
   */
  private void buildLoadablePlanStowageCargoDetails(
      List<LoadablePlanStowageDetails> loadablePlanStowageDetails,
      com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply.Builder replyBuilder) {
    loadablePlanStowageDetails.forEach(
        lpsd -> {
          com.cpdss.common.generated.LoadableStudy.LoadablePlanStowageDetails.Builder builder =
              com.cpdss.common.generated.LoadableStudy.LoadablePlanStowageDetails.newBuilder();
          Optional.ofNullable(lpsd.getId()).ifPresent(builder::setId);
          Optional.ofNullable(lpsd.getAbbreviation()).ifPresent(builder::setCargoAbbreviation);
          Optional.ofNullable(lpsd.getApi()).ifPresent(builder::setApi);
          Optional.ofNullable(lpsd.getCorrectedUllage()).ifPresent(builder::setCorrectedUllage);
          Optional.ofNullable(lpsd.getCorrectionFactor()).ifPresent(builder::setCorrectionFactor);
          Optional.ofNullable(lpsd.getFillingPercentage()).ifPresent(builder::setFillingRatio);
          Optional.ofNullable(lpsd.getObservedBarrels()).ifPresent(builder::setObservedBarrels);
          Optional.ofNullable(lpsd.getObservedBarrelsAt60())
              .ifPresent(builder::setObservedBarrelsAt60);
          Optional.ofNullable(lpsd.getObservedM3()).ifPresent(builder::setObservedM3);
          Optional.ofNullable(lpsd.getRdgUllage()).ifPresent(builder::setRdgUllage);
          Optional.ofNullable(lpsd.getTankname()).ifPresent(builder::setTankName);
          Optional.ofNullable(lpsd.getTankId()).ifPresent(builder::setTankId);
          Optional.ofNullable(lpsd.getTemperature()).ifPresent(builder::setTemperature);
          Optional.ofNullable(lpsd.getWeight()).ifPresent(builder::setWeight);
          replyBuilder.addLoadablePlanStowageDetails(builder);
        });
  }

  /**
   * @param loadablePlanQuantities
   * @param replyBuilder void
   */
  private void buildLoadablePlanQuantity(
      List<LoadablePlanQuantity> loadablePlanQuantities,
      com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply.Builder replyBuilder) {
    loadablePlanQuantities.forEach(
        lpq -> {
          LoadableQuantityCargoDetails.Builder builder = LoadableQuantityCargoDetails.newBuilder();
          Optional.ofNullable(lpq.getId()).ifPresent(builder::setId);
          Optional.ofNullable(lpq.getDifferenceColor()).ifPresent(builder::setDifferenceColor);
          Optional.ofNullable(lpq.getDifferencePercentage())
              .ifPresent(
                  diffPercentage ->
                      builder.setDifferencePercentage(String.valueOf(diffPercentage)));
          Optional.ofNullable(lpq.getEstimatedApi())
              .ifPresent(estimatedApi -> builder.setEstimatedAPI(String.valueOf(estimatedApi)));
          Optional.ofNullable(lpq.getEstimatedTemperature())
              .ifPresent(
                  estimatedTemperature ->
                      builder.setEstimatedTemp(String.valueOf(estimatedTemperature)));
          Optional.ofNullable(lpq.getGrade()).ifPresent(builder::setGrade);
          Optional.ofNullable(lpq.getLoadableBbls60f()).ifPresent(builder::setLoadableBbls60F);
          Optional.ofNullable(lpq.getLoadableBblsDbs()).ifPresent(builder::setLoadableBblsdbs);
          Optional.ofNullable(lpq.getLoadableKl()).ifPresent(builder::setLoadableKL);
          Optional.ofNullable(lpq.getLoadableLt()).ifPresent(builder::setLoadableLT);
          Optional.ofNullable(lpq.getLoadableMt()).ifPresent(builder::setLoadableMT);
          Optional.ofNullable(lpq.getMaxTolerence()).ifPresent(builder::setMaxTolerence);
          Optional.ofNullable(lpq.getMinTolerence()).ifPresent(builder::setMinTolerence);
          Optional.ofNullable(lpq.getOrderBbls60f()).ifPresent(builder::setOrderBbls60F);
          Optional.ofNullable(lpq.getOrderBblsDbs()).ifPresent(builder::setOrderBblsdbs);
          replyBuilder.addLoadableQuantityCargoDetails(builder);
        });
  }

  @Override
  public void confirmPlan(
      ConfirmPlanRequest request, StreamObserver<ConfirmPlanReply> responseObserver) {
    log.info("inside confirmPlan loadable study service");
    ConfirmPlanReply.Builder replyBuilder = ConfirmPlanReply.newBuilder();
    try {
      Optional<LoadablePattern> loadablePatternOpt =
          this.loadablePatternRepository.findByIdAndIsActive(request.getLoadablePatternId(), true);
      if (!loadablePatternOpt.isPresent()) {
        log.info(INVALID_LOADABLE_PATTERN_ID, request.getLoadablePatternId());
        replyBuilder.setResponseStatus(
            ResponseStatus.newBuilder()
                .setStatus(FAILED)
                .setMessage(INVALID_LOADABLE_PATTERN_ID)
                .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST));
      } else {
        List<LoadablePattern> loadablePatternConfirmedOpt =
            loadablePatternRepository.findByVoyageAndLoadableStudyStatusAndIsActive(
                loadablePatternOpt.get().getLoadableStudy().getVoyage().getId(),
                CONFIRMED_STATUS_ID,
                true);
        if (!loadablePatternConfirmedOpt.isEmpty()) {
          loadablePatternRepository.updateLoadablePatternStatus(
              LOADABLE_STUDY_STATUS_PLAN_GENERATED_ID, loadablePatternConfirmedOpt.get(0).getId());
          loadablePatternRepository.updateLoadableStudyStatus(
              LOADABLE_STUDY_STATUS_PLAN_GENERATED_ID,
              loadablePatternConfirmedOpt.get(0).getLoadableStudy().getId());
        }
        loadablePatternRepository.updateLoadablePatternStatus(
            CONFIRMED_STATUS_ID, loadablePatternOpt.get().getId());
        loadableStudyRepository.updateLoadableStudyStatus(
            CONFIRMED_STATUS_ID, loadablePatternOpt.get().getLoadableStudy().getId());
        replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
      }
    } catch (Exception e) {
      log.error("Exception when confirmPlan ", e);
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(FAILED));
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * @param request
   * @param responseObserver
   */
  @Override
  public void getLoadableStudyStatus(
      LoadableStudyStatusRequest request,
      StreamObserver<LoadableStudyStatusReply> responseObserver) {
    LoadableStudyStatusReply.Builder replyBuilder = LoadableStudyStatusReply.newBuilder();
    try {
      Optional<LoadableStudyAlgoStatus> loadableStudyAlgoStatusOpt =
          loadableStudyAlgoStatusRepository.findByLoadableStudyIdAndIsActive(
              request.getLoadableStudyId(), true);
      if (!loadableStudyAlgoStatusOpt.isPresent()) {
        replyBuilder.setResponseStatus(
            ResponseStatus.newBuilder()
                .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST)
                .setMessage("Invalid loadable study Id")
                .build());
      } else {
        replyBuilder.setLoadableStudystatusId(
            loadableStudyAlgoStatusOpt.get().getLoadableStudyStatus().getId());
        replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
      }
    } catch (Exception e) {
      log.error("Exception when getLoadableStudyStatus ", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Exception when saving Algo Loadable Study Status")
              .setStatus(FAILED)
              .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /** Retrieves the synoptical data for a portId */
  @Override
  public void getSynopticalDataByPortId(
      SynopticalDataRequest request, StreamObserver<SynopticalDataReply> responseObserver) {
    SynopticalDataReply.Builder replyBuilder = SynopticalDataReply.newBuilder();
    try {
      Optional<LoadableStudy> loadableStudyOpt =
          this.loadableStudyRepository.findById(request.getLoadableStudyId());
      if (!loadableStudyOpt.isPresent()) {
        throw new GenericServiceException(
            "Loadable study does not exist", CommonErrorCodes.E_HTTP_BAD_REQUEST, null);
      }
      List<SynopticalTable> synopticalTableList =
          this.synopticalTableRepository.findByLoadableStudyXIdAndIsActiveAndPortXid(
              request.getLoadableStudyId(), true, request.getPortId());
      if (!synopticalTableList.isEmpty()) {
        buildSynopticalDataReply(synopticalTableList, replyBuilder);
      }
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS));
    } catch (GenericServiceException e) {
      log.error("GenericServiceException in getSynopticalDataByPortId", e);
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(FAILED));
    } catch (Exception e) {
      log.error("Exception in getSynopticalDataByPortId", e);
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(FAILED));
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  private void buildSynopticalDataReply(
      List<SynopticalTable> synopticalTableList,
      com.cpdss.common.generated.LoadableStudy.SynopticalDataReply.Builder replyBuilder) {
    if (!CollectionUtils.isEmpty(synopticalTableList)) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
      synopticalTableList.forEach(
          synopticalRecord -> {
            SynopticalRecord.Builder recordBuilder = SynopticalRecord.newBuilder();
            Optional.ofNullable(synopticalRecord.getOperationType())
                .ifPresent(recordBuilder::setOperationType);
            Optional.ofNullable(synopticalRecord.getDistance())
                .ifPresent(distance -> recordBuilder.setDistance(String.valueOf(distance)));
            Optional.ofNullable(synopticalRecord.getEtaActual())
                .ifPresent(
                    etaActual ->
                        recordBuilder.setEtaEtdActual(
                            formatter.format(synopticalRecord.getEtaActual())));
            Optional.ofNullable(synopticalRecord.getEtdActual())
                .ifPresent(
                    etdActual ->
                        recordBuilder.setEtaEtdActual(
                            formatter.format(synopticalRecord.getEtdActual())));
            replyBuilder.addSynopticalRecords(recordBuilder);
          });
    }
  }
}
