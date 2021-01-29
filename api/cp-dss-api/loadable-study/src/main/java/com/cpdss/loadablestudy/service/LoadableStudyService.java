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
import com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest;
import com.cpdss.common.generated.LoadableStudy.LoadablePatternCargoDetails;
import com.cpdss.common.generated.LoadableStudy.LoadablePatternCommingleDetailsReply;
import com.cpdss.common.generated.LoadableStudy.LoadablePatternCommingleDetailsRequest;
import com.cpdss.common.generated.LoadableStudy.LoadablePatternReply;
import com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest;
import com.cpdss.common.generated.LoadableStudy.LoadablePlanDetails;
import com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply;
import com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest;
import com.cpdss.common.generated.LoadableStudy.LoadablePlanPortWiseDetails;
import com.cpdss.common.generated.LoadableStudy.LoadableQuantityCargoDetails;
import com.cpdss.common.generated.LoadableStudy.LoadableQuantityCommingleCargoDetails;
import com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply;
import com.cpdss.common.generated.LoadableStudy.LoadableQuantityRequest;
import com.cpdss.common.generated.LoadableStudy.LoadableQuantityResponse;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyAttachment;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyAttachmentReply;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyAttachmentRequest;
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
import com.cpdss.common.generated.LoadableStudy.SaveCommentReply;
import com.cpdss.common.generated.LoadableStudy.SaveCommentRequest;
import com.cpdss.common.generated.LoadableStudy.StatusReply;
import com.cpdss.common.generated.LoadableStudy.SynopticalBallastRecord;
import com.cpdss.common.generated.LoadableStudy.SynopticalCargoRecord;
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
import com.cpdss.loadablestudy.domain.AlgoResponse;
import com.cpdss.loadablestudy.domain.CargoHistory;
import com.cpdss.loadablestudy.domain.PortDetails;
import com.cpdss.loadablestudy.entity.CargoNomination;
import com.cpdss.loadablestudy.entity.CargoNominationPortDetails;
import com.cpdss.loadablestudy.entity.CargoNominationValveSegregation;
import com.cpdss.loadablestudy.entity.CargoOperation;
import com.cpdss.loadablestudy.entity.LoadablePattern;
import com.cpdss.loadablestudy.entity.LoadablePlanBallastDetails;
import com.cpdss.loadablestudy.entity.LoadablePlanComments;
import com.cpdss.loadablestudy.entity.LoadablePlanCommingleDetails;
import com.cpdss.loadablestudy.entity.LoadablePlanConstraints;
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
import com.cpdss.loadablestudy.repository.LoadablePatternCargoDetailsRepository;
import com.cpdss.loadablestudy.repository.LoadablePatternDetailsRepository;
import com.cpdss.loadablestudy.repository.LoadablePatternRepository;
import com.cpdss.loadablestudy.repository.LoadablePlanBallastDetailsRepository;
import com.cpdss.loadablestudy.repository.LoadablePlanCommentsRepository;
import com.cpdss.loadablestudy.repository.LoadablePlanCommingleDetailsRepository;
import com.cpdss.loadablestudy.repository.LoadablePlanConstraintsRespository;
import com.cpdss.loadablestudy.repository.LoadablePlanQuantityRepository;
import com.cpdss.loadablestudy.repository.LoadablePlanStowageBallastDetailsRepository;
import com.cpdss.loadablestudy.repository.LoadablePlanStowageDetailsRespository;
import com.cpdss.loadablestudy.repository.LoadableQuantityRepository;
import com.cpdss.loadablestudy.repository.LoadableStudyAlgoStatusRepository;
import com.cpdss.loadablestudy.repository.LoadableStudyAttachmentsRepository;
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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
import javax.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.server.service.GrpcService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

/** @Author jerin.g */
@Log4j2
@GrpcService
@Transactional
public class LoadableStudyService extends LoadableStudyServiceImplBase {

  @Value("${loadablestudy.attachement.rooFolder}")
  private String rootFolder;

  @Value("${algo.loadablestudy.api.url}")
  private String loadableStudyUrl;

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
  @Autowired private LoadablePlanCommentsRepository loadablePlanCommentsRepository;
  @Autowired private LoadablePlanCommingleDetailsRepository loadablePlanCommingleDetailsRepository;
  @Autowired private LoadablePlanStowageDetailsRespository loadablePlanStowageDetailsRespository;
  @Autowired private EntityManager entityManager;
  @Autowired private RestTemplate restTemplate;
  @Autowired private LoadablePlanBallastDetailsRepository loadablePlanBallastDetailsRepository;
  @Autowired private LoadableStudyAttachmentsRepository loadableStudyAttachmentsRepository;

  @Autowired
  private LoadablePlanStowageBallastDetailsRepository loadablePlanStowageBallastDetailsRepository;

  @Autowired private LoadablePlanConstraintsRespository loadablePlanConstraintsRespository;

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

  @Autowired private LoadablePatternCargoDetailsRepository loadablePatternCargoDetailsRepository;

  private static final String SUCCESS = "SUCCESS";
  private static final String FAILED = "FAILED";
  private static final String VOYAGEEXISTS = "VOYAGE_EXISTS";
  private static final String CREATED_DATE_FORMAT = "dd-MM-yyyy";
  private static final String INVALID_LOADABLE_QUANTITY = "INVALID_LOADABLE_QUANTITY";
  private static final String COMMINGLE = "COM";
  private static final String ETA_ETD_FORMAT = "dd-MM-yyyy HH:mm";
  private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm";
  private static final String LAY_CAN_FORMAT = "dd-MM-yyyy";
  private static final Long LOADING_OPERATION_ID = 1L;
  private static final Long DISCHARGING_OPERATION_ID = 2L;
  private static final Long BUNKERING_OPERATION_ID = 3L;
  private static final Long TRANSIT_OPERATION_ID = 4L;
  private static final Long LOADABLE_STUDY_INITIAL_STATUS_ID = 1L;
  private static final Long LOADABLE_STUDY_PROCESSING_STARTED_ID = 4L;
  private static final Long LOADABLE_STUDY_STATUS_PLAN_GENERATED_ID = 3L;
  private static final Long CONFIRMED_STATUS_ID = 2L;
  private static final String INVALID_LOADABLE_STUDY_ID = "INVALID_LOADABLE_STUDY_ID";
  private static final String ERRO_CALLING_ALGO = "ERROR_CALLING_ALGO";
  private static final int CASE_1 = 1;
  private static final int CASE_2 = 2;
  private static final int CASE_3 = 3;
  private static final String INVALID_LOADABLE_PATTERN_COMMINGLE_DETAIL_ID =
      "INVALID_LOADABLE_PATTERN_COMMINGLE_DETAIL_ID";
  private static final String INVALID_LOADABLE_PATTERN_ID = "INVALID_LOADABLE_PATTERN_ID";
  private static final Long LOAD_LINE_TROPICAL_TO_SUMMER_ID = 7L;
  private static final Long LOAD_LINE_TROPICAL_TO_WINTER_ID = 8L;
  private static final Long LOAD_LINE_SUMMER_TO_WINTER_ID = 9L;

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
  private static final Long BALLAST_VOID_TANK_CATEGORY_ID = 16L;
  private static final Long BALLAST_TANK_CATEGORY_ID = 2L;

  private static final String BALLAST_FRONT_TANK = "FRONT";
  private static final String BALLAST_CENTER_TANK = "CENTER";
  private static final String BALLAST_REAR_TANK = "REAR";

  private static final List<Long> BALLAST_TANK_CATEGORIES =
      Arrays.asList(BALLAST_TANK_CATEGORY_ID, BALLAST_VOID_TANK_CATEGORY_ID);

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

  private static final List<Long> CARGO_BALLAST_TANK_CATEGORIES =
      Arrays.asList(
          CARGO_TANK_CATEGORY_ID,
          CARGO_SLOP_TANK_CATEGORY_ID,
          CARGO_VOID_TANK_CATEGORY_ID,
          BALLAST_TANK_CATEGORY_ID,
          BALLAST_VOID_TANK_CATEGORY_ID);

  private static final List<Long> CARGO_TANK_CATEGORIES =
      Arrays.asList(
          CARGO_TANK_CATEGORY_ID, CARGO_SLOP_TANK_CATEGORY_ID, CARGO_VOID_TANK_CATEGORY_ID);

  private static final List<Long> CARGO_OPERATION_ARR_DEP_SYNOPTICAL =
      Arrays.asList(
          LOADING_OPERATION_ID,
          DISCHARGING_OPERATION_ID,
          BUNKERING_OPERATION_ID,
          TRANSIT_OPERATION_ID);

  private static final List<Long> SYNOPTICAL_TABLE_TANK_CATEGORIES =
      Arrays.asList(
          CARGO_TANK_CATEGORY_ID,
          CARGO_SLOP_TANK_CATEGORY_ID,
          FRESH_WATER_TANK_CATEGORY_ID,
          FUEL_OIL_TANK_CATEGORY_ID,
          DIESEL_OIL_TANK_CATEGORY_ID,
          LUBRICATING_OIL_TANK_CATEGORY_ID,
          LUBRICANT_OIL_TANK_CATEGORY_ID,
          BALLAST_TANK_CATEGORY_ID);

  private static final String SYNOPTICAL_TABLE_OP_TYPE_ARRIVAL = "ARR";
  private static final String SYNOPTICAL_TABLE_OP_TYPE_DEPARTURE = "DEP";

  private static final String STATUS_ACTIVE = "ACTIVE";
  private static final String STATUS_CONFIRMED = "CONFIRMED";

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
    LoadableQuantity loadableQuantity = null;
    try {
      Optional<LoadableStudy> loadableStudy =
          loadableStudyRepository.findById((Long) loadableQuantityRequest.getLoadableStudyId());
      if (loadableStudy.isPresent()) {
        if (0 == loadableQuantityRequest.getId()) {
          loadableQuantity = new LoadableQuantity();
        } else {
          loadableQuantity =
              this.loadableQuantityRepository.findByIdAndIsActive(
                  loadableQuantityRequest.getId(), true);
          if (null == loadableQuantity) {
            throw new GenericServiceException(
                "Loadable quantity does not exist",
                CommonErrorCodes.E_HTTP_BAD_REQUEST,
                HttpStatusCode.BAD_REQUEST);
          }
        }

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
          this.loadableStudyRepository
              .findByVesselXIdAndVoyageAndIsActiveOrderByLastModifiedDateTimeDesc(
                  request.getVesselId(), voyageOpt.get(), true);
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
      DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(CREATED_DATE_FORMAT);
      for (LoadableStudy entity : loadableStudyEntityList) {
        com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail.Builder builder =
            LoadableStudyDetail.newBuilder();
        builder.setId(entity.getId());
        builder.setName(entity.getName());
        Optional.ofNullable(entity.getDischargeCargoId()).ifPresent(builder::setDischargingCargoId);
        builder.setCreatedDate(dateTimeFormatter.format(entity.getCreatedDate()));
        Optional.ofNullable(entity.getDuplicatedFrom())
            .ifPresent(
                duplicatedFrom -> {
                  builder.setCreatedFromId(duplicatedFrom.getId());
                });
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

        List<LoadableStudyAttachments> loadableStudyAttachments =
            this.loadableStudyAttachmentsRepository.findByLoadableStudyXIdAndIsActive(
                entity.getId(), true);
        com.cpdss.common.generated.LoadableStudy.LoadableStudyAttachment.Builder
            loadableStudyAttachmentBuilder = LoadableStudyAttachment.newBuilder();
        if (null != loadableStudyAttachments && !loadableStudyAttachments.isEmpty()) {
          loadableStudyAttachments.forEach(
              loadableStudyAttachment -> {
                loadableStudyAttachmentBuilder.setFileName(
                    loadableStudyAttachment.getUploadedFileName());
                loadableStudyAttachmentBuilder.setId(loadableStudyAttachment.getId());

                builder.addAttachments(loadableStudyAttachmentBuilder.build());
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

      if (request.getId() != 0) {
        Optional<LoadableStudy> loadableStudy =
            this.loadableStudyRepository.findByIdAndIsActive(request.getId(), true);
        if (!loadableStudy.isPresent()) {
          throw new GenericServiceException(
              "Loadable study with given id does not exist",
              CommonErrorCodes.E_HTTP_BAD_REQUEST,
              HttpStatusCode.BAD_REQUEST);
        }

        entity = loadableStudy.get();

      } else {
        entity = new LoadableStudy();
      }

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
      entity.setDischargeCargoId(request.getDischargingCargoId());
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
          attachmentEntity.setIsActive(true);
          attachmentCollection.add(attachmentEntity);
        }

        if (request.getId() != 0) {
          Set<LoadableStudyAttachments> deletedAttachmentsList =
              this.loadableStudyAttachmentsRepository.findByIdInAndIsActive(
                  request.getDeletedAttachmentsList(), true);

          if (deletedAttachmentsList != null && deletedAttachmentsList.size() != 0) {
            deletedAttachmentsList.forEach(
                attachment -> {
                  attachment.setIsActive(false);
                });
          }
          attachmentCollection.addAll(deletedAttachmentsList);
        }

        entity.setAttachments(attachmentCollection);
      }

      this.setCaseNo(entity);
      entity.setLoadableStudyStatus(
          this.loadableStudyStatusRepository.getOne(LOADABLE_STUDY_INITIAL_STATUS_ID));
      entity = this.loadableStudyRepository.save(entity);
      this.checkDuplicatedFromAndCloneEntity(request, entity);
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

      List<LoadableStudyPortRotation> entityList =
          this.loadableStudyPortRotationRepository.findByLoadableStudyAndIsActiveOrderByPortOrder(
              loadableStudyOpt.get(), true);
      for (LoadableStudyPortRotation entity : entityList) {
        replyBuilder.addPorts(
            this.createPortDetail(
                entity,
                DateTimeFormatter.ofPattern(ETA_ETD_FORMAT),
                DateTimeFormatter.ofPattern(LAY_CAN_FORMAT)));
      }
      List<CargoOperation> operationEntityList =
          this.cargoOperationRepository.findByIsActiveOrderById(true);
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
          this.cargoNominationRepository.findByLoadableStudyXIdAndIsActiveOrderByCreatedDateTime(
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
      List<Long> portList = this.getPortRoationPortIds(loadableStudy.get());
      BigDecimal foOnboard = BigDecimal.ZERO;
      BigDecimal doOnboard = BigDecimal.ZERO;
      BigDecimal freshWaterOnBoard = BigDecimal.ZERO;
      BigDecimal boileWaterOnBoard = BigDecimal.ZERO;
      if (!portList.isEmpty()) {
        long firstPort = portList.iterator().next();
        List<OnHandQuantity> onHandQuantityList =
            this.onHandQuantityRepository.findByLoadableStudyAndIsActive(loadableStudy.get(), true);

        foOnboard =
            BigDecimal.valueOf(
                onHandQuantityList.stream()
                    .filter(
                        ohq ->
                            ohq.getFuelTypeXId().equals(FUEL_OIL_TANK_CATEGORY_ID)
                                && ohq.getPortXId().equals(firstPort)
                                && ohq.getIsActive())
                    .mapToLong(
                        foOnboardQuantity -> foOnboardQuantity.getArrivalQuantity().longValue())
                    .sum());
        doOnboard =
            BigDecimal.valueOf(
                onHandQuantityList.stream()
                    .filter(
                        ohq ->
                            ohq.getFuelTypeXId().equals(DIESEL_OIL_TANK_CATEGORY_ID)
                                && ohq.getPortXId().equals(firstPort)
                                && ohq.getIsActive())
                    .mapToLong(
                        doOnboardQuantity -> doOnboardQuantity.getArrivalQuantity().longValue())
                    .sum());
        freshWaterOnBoard =
            BigDecimal.valueOf(
                onHandQuantityList.stream()
                    .filter(
                        ohq ->
                            ohq.getFuelTypeXId() == FRESH_WATER_TANK_CATEGORY_ID
                                && ohq.getPortXId() == firstPort
                                && ohq.getIsActive())
                    .mapToLong(
                        fwOnboardQuantity -> fwOnboardQuantity.getArrivalQuantity().longValue())
                    .sum());
        boileWaterOnBoard =
            BigDecimal.valueOf(
                onHandQuantityList.stream()
                    .filter(
                        ohq ->
                            ohq.getFuelTypeXId().equals(FRESH_WATER_TANK_CATEGORY_ID)
                                && ohq.getPortXId().equals(firstPort)
                                && ohq.getIsActive())
                    .mapToLong(
                        bwOnboardQuantity -> bwOnboardQuantity.getArrivalQuantity().longValue())
                    .sum());
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
        loadableQuantityRequest.setId(loadableQuantity.get(0).getId());
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

        loadableQuantityRequest.setEstFOOnBoard(String.valueOf(foOnboard));
        loadableQuantityRequest.setEstDOOnBoard(String.valueOf(doOnboard));
        loadableQuantityRequest.setEstFreshWaterOnBoard(String.valueOf(freshWaterOnBoard));
        loadableQuantityRequest.setBoilerWaterOnBoard(String.valueOf(boileWaterOnBoard));

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
                        DateTimeFormatter.ofPattern(DATE_FORMAT).format(updateDateAndTime)));
        Optional.ofNullable(loadableQuantity.get(0).getPortId())
            .ifPresent(portId -> loadableQuantityRequest.setPortId(portId.longValue()));

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
        detailbuilder.setStatus(
            entity.getVoyageStatus() != null ? entity.getVoyageStatus().getName() : "");
        // fetch the confirmed loadable study for active voyages
        if (entity.getVoyageStatus() != null
            && STATUS_ACTIVE.equalsIgnoreCase(entity.getVoyageStatus().getName())) {
          Stream<LoadableStudy> loadableStudyStream =
              Optional.ofNullable(entity.getLoadableStudies())
                  .map(Collection::stream)
                  .orElseGet(Stream::empty);
          Optional<LoadableStudy> loadableStudy =
              loadableStudyStream
                  .filter(
                      loadableStudyElement ->
                          (loadableStudyElement.getLoadableStudyStatus() != null
                              && STATUS_CONFIRMED.equalsIgnoreCase(
                                  loadableStudyElement.getLoadableStudyStatus().getName())))
                  .findFirst();
          loadableStudy.ifPresent(
              record -> detailbuilder.setConfirmedLoadableStudyId(record.getId()));
        }
        builder.addVoyages(detailbuilder.build());
      }
      builder.setResponseStatus(StatusReply.newBuilder().setStatus(SUCCESS).build());
    } catch (Exception e) {
      log.error("Error in getVoyagesByVessel method ", e);
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

      LoadableStudy loadableStudy = loadableStudyOpt.get();
      loadableStudy.setDischargeCargoId(request.getDischargingCargoId());
      this.loadableStudyRepository.save(loadableStudy);

      CargoOperation discharging = this.cargoOperationRepository.getOne(DISCHARGING_OPERATION_ID);
      List<LoadableStudyPortRotation> dischargingPorts =
          this.loadableStudyPortRotationRepository.findByLoadableStudyAndOperationAndIsActive(
              loadableStudyOpt.get(), discharging, true);
      List<Long> portIds = new ArrayList<>(request.getDischargingPortIdsList());
      for (LoadableStudyPortRotation portRotation : dischargingPorts) {
        if (!request.getDischargingPortIdsList().contains(portRotation.getPortXId())) {
          portRotation.setActive(false);
          List<SynopticalTable> synopticalEntities = portRotation.getSynopticalTable();
          if (null != synopticalEntities && !synopticalEntities.isEmpty()) {
            synopticalEntities.forEach(entity -> entity.setIsActive(false));
          }
          portIds.remove(portRotation.getPortXId());
        } else {
          portIds.remove(portRotation.getPortXId());
        }
      }
      if (!CollectionUtils.isEmpty(portIds)) {
        GetPortInfoByPortIdsRequest.Builder reqBuilder = GetPortInfoByPortIdsRequest.newBuilder();
        portIds.forEach(
            port -> {
              reqBuilder.addId(port);
            });
        PortReply portReply = portInfoGrpcService.getPortInfoByPortIds(reqBuilder.build());
        if (!SUCCESS.equalsIgnoreCase(portReply.getResponseStatus().getStatus())) {
          throw new GenericServiceException(
              "Error in calling port service",
              CommonErrorCodes.E_GEN_INTERNAL_ERR,
              HttpStatusCode.INTERNAL_SERVER_ERROR);
        }

        if (!CollectionUtils.isEmpty(portIds)
            && !CollectionUtils.isEmpty(portReply.getPortsList())) {
          dischargingPorts =
              this.buildDischargingPorts(portReply, loadableStudy, dischargingPorts, portIds);
          this.loadableStudyPortRotationRepository.saveAll(dischargingPorts);
        }
      }
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
   * Build discharging ports
   *
   * @param portReply
   * @param loadableStudy
   * @param dischargingPorts
   * @param portIds
   * @return
   */
  private List<LoadableStudyPortRotation> buildDischargingPorts(
      PortReply portReply,
      LoadableStudy loadableStudy,
      List<LoadableStudyPortRotation> dischargingPorts,
      List<Long> portIds) {
    Long maxPorOrder = 0L;
    LoadableStudyPortRotation maxPortOrderEntity =
        this.loadableStudyPortRotationRepository
            .findFirstByLoadableStudyAndIsActiveOrderByPortOrderDesc(loadableStudy, true);
    if (maxPortOrderEntity != null) {
      maxPorOrder = maxPortOrderEntity.getPortOrder();
    }
    for (Long requestedPortId : portIds) {
      Optional<PortDetail> portOpt =
          portReply.getPortsList().stream()
              .filter(portDetail -> Objects.equals(requestedPortId, portDetail.getId()))
              .findAny();

      if (portOpt.isPresent()) {
        PortDetail port = portOpt.get();
        LoadableStudyPortRotation portRotationEntity = new LoadableStudyPortRotation();
        portRotationEntity.setLoadableStudy(loadableStudy);
        portRotationEntity.setPortXId(port.getId());
        portRotationEntity.setOperation(
            this.cargoOperationRepository.getOne(DISCHARGING_OPERATION_ID));
        portRotationEntity.setSeaWaterDensity(
            !StringUtils.isEmpty(port.getWaterDensity())
                ? new BigDecimal(port.getWaterDensity())
                : null);
        portRotationEntity.setMaxDraft(
            !StringUtils.isEmpty(port.getMaxDraft()) ? new BigDecimal(port.getMaxDraft()) : null);

        portRotationEntity.setAirDraftRestriction(
            !StringUtils.isEmpty(port.getMaxAirDraft())
                ? new BigDecimal(port.getMaxAirDraft())
                : null);
        portRotationEntity.setPortOrder(++maxPorOrder);

        // add ports to synoptical table by reusing the function called by
        // port-rotation flow
        buildPortsInfoSynopticalTable(portRotationEntity, DISCHARGING_OPERATION_ID, port.getId());
        dischargingPorts.add(portRotationEntity);
      }
    }
    return dischargingPorts;
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
    buildLoadableStudyPortRotationEntity(entity, request);
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
   * @param request
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

      this.commingleCargoRepository.deleteCommingleCargoByLodableStudyXId(
          existingCargoNomination.get().getLoadableStudyXId());
      Optional<LoadableStudy> loadableStudyOpt =
          this.loadableStudyRepository.findById(
              existingCargoNomination.get().getLoadableStudyXId());
      if (loadableStudyOpt.isPresent()) {
        LoadableStudy loadableStudy = loadableStudyOpt.get();
        loadableStudy.setDischargeCargoId(null);
        this.loadableStudyRepository.save(loadableStudy);
      }
      this.cargoNominationOperationDetailsRepository.deleteCargoNominationPortDetails(
          request.getCargoNominationId());
      /*
       * delete respective loading ports from port rotation table if ports not
       * associated with any other cargo nomination belonging to the same loadable
       * study
       */
      if (!existingCargoNomination.get().getCargoNominationPortDetails().isEmpty()) {
        List<Long> requestedPortIds =
            existingCargoNomination.get().getCargoNominationPortDetails().stream()
                .map(CargoNominationPortDetails::getPortId)
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(requestedPortIds)) {
          requestedPortIds.forEach(
              requestPortId -> {
                Long otherCargoRefExistCount =
                    this.cargoNominationRepository.getCountCargoNominationWithPortIds(
                        existingCargoNomination.get().getLoadableStudyXId(),
                        existingCargoNomination.get(),
                        requestPortId);
                if (Objects.equals(otherCargoRefExistCount, Long.valueOf("0"))
                    && loadableStudyOpt.isPresent()) {
                  loadableStudyPortRotationRepository.deleteLoadingPortRotation(
                      loadableStudyOpt.get(), requestPortId);
                }
              });
        }
      }
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

  private List<Long> getPortRoationPortIds(LoadableStudy loadableStudy) {
    List<Long> portIds =
        this.loadableStudyPortRotationRepository.findByLoadableStudyAndIsActive(
            loadableStudy, true);
    return portIds.stream().distinct().collect(Collectors.toList());
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
        List<Long> portIds = this.getPortRoationPortIds(loadableStudy.get());
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

      Voyage previousVoyage =
          this.voyageRepository
              .findFirstByVoyageEndDateLessThanAndVesselXIdAndIsActiveOrderByVoyageEndDateDesc(
                  loadableStudyOpt.get().getVoyage().getVoyageStartDate(),
                  loadableStudyOpt.get().getVoyage().getVesselXId(),
                  true);

      VesselReply vesselReply = this.getOhqTanks(request);
      List<OnHandQuantity> onHandQuantities =
          this.onHandQuantityRepository.findByLoadableStudyAndPortXIdAndIsActive(
              loadableStudyOpt.get(), request.getPortId(), true);
      Optional<LoadableStudy> confirmedLoadableStudyOpt =
          this.loadableStudyRepository.findByVoyageAndLoadableStudyStatusAndIsActive(
              previousVoyage, CONFIRMED_STATUS_ID, true);
      List<OnHandQuantity> onHandQuantityList = null;
      if (confirmedLoadableStudyOpt.isPresent()) {
        onHandQuantityList =
            this.onHandQuantityRepository.findByLoadableStudyAndPortXIdAndIsActive(
                confirmedLoadableStudyOpt.get(), request.getPortId(), true);
      }
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
          Optional.ofNullable(qty.getActualArrivalQuantity())
              .ifPresent(item -> detailBuilder.setActualArrivalQuantity(valueOf(item)));
          Optional.ofNullable(qty.getArrivalVolume())
              .ifPresent(item -> detailBuilder.setArrivalVolume(valueOf(item)));
          Optional.ofNullable(qty.getDepartureQuantity())
              .ifPresent(item -> detailBuilder.setDepartureQuantity(valueOf(item)));
          Optional.ofNullable(qty.getActualDepartureQuantity())
              .ifPresent(item -> detailBuilder.setActualDepartureQuantity(valueOf(item)));
          Optional.ofNullable(qty.getDepartureVolume())
              .ifPresent(item -> detailBuilder.setDepartureVolume(valueOf(item)));
          Optional.ofNullable(qty.getDensity())
              .ifPresent(item -> detailBuilder.setDensity(valueOf(item)));
        } else {
          if (onHandQuantityList != null && !onHandQuantityList.isEmpty()) {
            Optional<OnHandQuantity> ohqQtyOpt =
                onHandQuantityList.stream()
                    .filter(
                        entity ->
                            entity.getFuelTypeXId().equals(tankDetail.getTankCategoryId())
                                && entity.getTankXId().equals(tankDetail.getTankId()))
                    .findAny();
            if (ohqQtyOpt.isPresent()) {
              OnHandQuantity ohqQty = ohqQtyOpt.get();
              detailBuilder.setId(ohqQty.getId());
              Optional.ofNullable(ohqQty.getArrivalQuantity())
                  .ifPresent(item -> detailBuilder.setArrivalQuantity(valueOf(item)));
              Optional.ofNullable(ohqQty.getArrivalVolume())
                  .ifPresent(item -> detailBuilder.setArrivalVolume(valueOf(item)));
              Optional.ofNullable(ohqQty.getDepartureQuantity())
                  .ifPresent(item -> detailBuilder.setDepartureQuantity(valueOf(item)));
              Optional.ofNullable(ohqQty.getDepartureVolume())
                  .ifPresent(item -> detailBuilder.setDepartureVolume(valueOf(item)));
              Optional.ofNullable(ohqQty.getDensity())
                  .ifPresent(item -> detailBuilder.setDensity(valueOf(item)));
            }
          }
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
    builder.setTankDisplayOrder(detail.getTankDisplayOrder());
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
    entity.setDensity(isEmpty(request.getDensity()) ? null : new BigDecimal(request.getDensity()));
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

  /** Get list of patterns for a loadable study */
  @Override
  public void saveLoadablePatterns(
      LoadablePatternAlgoRequest request, StreamObserver<AlgoReply> responseObserver) {
    AlgoReply.Builder builder = AlgoReply.newBuilder();
    try {
      log.info("saveLoadablePatternDetails - loadable study micro service");
      Optional<LoadableStudy> loadableStudyOpt =
          this.loadableStudyRepository.findByIdAndIsActive(request.getLoadableStudyId(), true);
      if (!loadableStudyOpt.isPresent()) {
        throw new GenericServiceException(
            "Loadable study does not exist",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      Long lastLoadingPort =
          getLastPort(
              loadableStudyOpt.get(), this.cargoOperationRepository.getOne(LOADING_OPERATION_ID));
      request
          .getLoadablePlanDetailsList()
          .forEach(
              lpd -> {
                LoadablePattern loadablePattern = saveloadablePattern(lpd, loadableStudyOpt.get());
                Optional<LoadablePlanPortWiseDetails> lppwdOptional =
                    lpd.getLoadablePlanPortWiseDetailsList().stream()
                        .filter(lppwd -> lppwd.getPortId() == lastLoadingPort)
                        .findAny();
                if (lppwdOptional.isPresent()) {
                  saveLoadableQuantity(lppwdOptional.get(), loadablePattern);
                  saveLoadablePlanCommingleCargo(
                      lppwdOptional
                          .get()
                          .getDepartureCondition()
                          .getLoadableQuantityCommingleCargoDetailsList(),
                      loadablePattern);
                  saveLoadablePlanStowageDetails(
                      lppwdOptional
                          .get()
                          .getDepartureCondition()
                          .getLoadablePlanStowageDetailsList(),
                      loadablePattern);
                  saveLoadablePlanBallastDetails(
                      lppwdOptional
                          .get()
                          .getDepartureCondition()
                          .getLoadablePlanBallastDetailsList(),
                      loadablePattern);
                }
              });

    } catch (GenericServiceException e) {
      log.error("GenericServiceException in loadable pattern list", e);
      builder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(e.getCode())
              .setMessage(e.getMessage())
              .setStatus(FAILED)
              .build());
    } catch (Exception e) {
      log.error("Exception in loadable pattern list", e);
      builder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Exception in getLoadablePatternList")
              .setStatus(FAILED)
              .build());
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * @param loadablePlanBallastDetailsList
   * @param loadablePattern void
   */
  private void saveLoadablePlanBallastDetails(
      List<com.cpdss.common.generated.LoadableStudy.LoadablePlanBallastDetails>
          loadablePlanBallastDetailsList,
      LoadablePattern loadablePattern) {
    loadablePlanBallastDetailsList.forEach(
        lpbd -> {
          LoadablePlanBallastDetails loadablePlanBallastDetails = new LoadablePlanBallastDetails();
          loadablePlanBallastDetails.setMetricTon(lpbd.getMetricTon());
          loadablePlanBallastDetails.setPercentage(lpbd.getPercentage());
          loadablePlanBallastDetails.setSg(lpbd.getSg());
          loadablePlanBallastDetails.setTankName(lpbd.getTankName());
          loadablePlanBallastDetails.setTankId(lpbd.getTankId());
          loadablePlanBallastDetails.setRdgLevel(lpbd.getRdgLevel());
          loadablePlanBallastDetails.setIsActive(true);
          loadablePlanBallastDetails.setLoadablePattern(loadablePattern);
          loadablePlanBallastDetailsRepository.save(loadablePlanBallastDetails);
        });
  }

  /**
   * @param loadablePlanStowageDetailsList
   * @param loadablePattern void
   */
  private void saveLoadablePlanStowageDetails(
      List<com.cpdss.common.generated.LoadableStudy.LoadablePlanStowageDetails>
          loadablePlanStowageDetailsList,
      LoadablePattern loadablePattern) {
    loadablePlanStowageDetailsList.forEach(
        lpsd -> {
          LoadablePlanStowageDetails loadablePlanStowageDetails = new LoadablePlanStowageDetails();
          loadablePlanStowageDetails.setApi(lpsd.getApi());
          loadablePlanStowageDetails.setAbbreviation(lpsd.getCargoAbbreviation());
          loadablePlanStowageDetails.setColorCode(lpsd.getColorCode());
          loadablePlanStowageDetails.setFillingPercentage(lpsd.getFillingRatio());
          loadablePlanStowageDetails.setRdgUllage(lpsd.getRdgUllage());
          loadablePlanStowageDetails.setTankId(lpsd.getTankId());
          loadablePlanStowageDetails.setTankname(lpsd.getTankName());
          loadablePlanStowageDetails.setWeight(lpsd.getWeight());
          loadablePlanStowageDetails.setTemperature(lpsd.getTemperature());
          loadablePlanStowageDetails.setIsActive(true);
          loadablePlanStowageDetails.setLoadablePattern(loadablePattern);
          loadablePlanStowageDetailsRespository.save(loadablePlanStowageDetails);
        });
  }

  /**
   * @param loadableQuantityCommingleCargoDetailsList
   * @param loadablePattern void
   */
  private void saveLoadablePlanCommingleCargo(
      List<LoadableQuantityCommingleCargoDetails> loadableQuantityCommingleCargoDetailsList,
      LoadablePattern loadablePattern) {
    for (int i = 0; i < loadableQuantityCommingleCargoDetailsList.size(); i++) {
      LoadablePlanCommingleDetails loadablePlanCommingleDetails =
          new LoadablePlanCommingleDetails();
      loadablePlanCommingleDetails.setApi(
          loadableQuantityCommingleCargoDetailsList.get(i).getApi());
      loadablePlanCommingleDetails.setCargo1Abbreviation(
          loadableQuantityCommingleCargoDetailsList.get(i).getCargo1Abbreviation());
      loadablePlanCommingleDetails.setCargo1Mt(
          loadableQuantityCommingleCargoDetailsList.get(i).getCargo1MT());
      loadablePlanCommingleDetails.setCargo1Percentage(
          loadableQuantityCommingleCargoDetailsList.get(i).getCargo1Percentage());
      loadablePlanCommingleDetails.setCargo2Abbreviation(
          loadableQuantityCommingleCargoDetailsList.get(i).getCargo2Abbreviation());
      loadablePlanCommingleDetails.setCargo2Mt(
          loadableQuantityCommingleCargoDetailsList.get(i).getCargo2MT());
      loadablePlanCommingleDetails.setCargo2Percentage(
          loadableQuantityCommingleCargoDetailsList.get(i).getCargo2Percentage());
      loadablePlanCommingleDetails.setGrade(COMMINGLE + (i + 1));
      loadablePlanCommingleDetails.setIsActive(true);
      loadablePlanCommingleDetails.setLoadablePattern(loadablePattern);
      loadablePlanCommingleDetails.setQuantity(
          loadableQuantityCommingleCargoDetailsList.get(i).getQuantity());
      loadablePlanCommingleDetails.setTankName(
          loadableQuantityCommingleCargoDetailsList.get(i).getTankName());
      loadablePlanCommingleDetails.setTemperature(
          loadableQuantityCommingleCargoDetailsList.get(i).getTemp());
      loadablePlanCommingleDetails.setOrderQuantity(
          loadableQuantityCommingleCargoDetailsList.get(i).getOrderedMT());
      loadablePlanCommingleDetails.setPriority(
          loadableQuantityCommingleCargoDetailsList.get(i).getPriority());
      loadablePlanCommingleDetails.setLoadingOrder(
          loadableQuantityCommingleCargoDetailsList.get(i).getLoadingOrder());
      loadablePlanCommingleDetailsRepository.save(loadablePlanCommingleDetails);
    }
  }

  /**
   * @param loadablePlanPortWiseDetails
   * @return Consumer<? super LoadablePlanPortWiseDetails>
   */
  private void saveLoadableQuantity(
      LoadablePlanPortWiseDetails loadablePlanPortWiseDetails, LoadablePattern loadablePattern) {
    loadablePlanPortWiseDetails
        .getDepartureCondition()
        .getLoadableQuantityCargoDetailsList()
        .forEach(
            lqcd -> {
              LoadablePlanQuantity loadablePlanQuantity = new LoadablePlanQuantity();
              loadablePlanQuantity.setDifferencePercentage(lqcd.getDifferencePercentage());
              loadablePlanQuantity.setEstimatedApi(new BigDecimal(lqcd.getEstimatedAPI()));
              loadablePlanQuantity.setEstimatedTemperature(new BigDecimal(lqcd.getEstimatedTemp()));
              loadablePlanQuantity.setCargoXId(lqcd.getCargoId());
              loadablePlanQuantity.setIsActive(true);
              loadablePlanQuantity.setLoadableMt(lqcd.getLoadableMT());
              loadablePlanQuantity.setOrderQuantity(new BigDecimal(lqcd.getOrderedMT()));
              loadablePlanQuantity.setLoadablePattern(loadablePattern);
              loadablePlanQuantity.setCargoAbbreviation(lqcd.getCargoAbbreviation());
              loadablePlanQuantity.setCargoColor(lqcd.getColorCode());
              loadablePlanQuantity.setPriority(lqcd.getPriority());
              loadablePlanQuantity.setLoadingOrder(lqcd.getLoadingOrder());
              loadablePlanQuantityRepository.save(loadablePlanQuantity);
            });
  }

  /**
   * @param lpd void
   * @param loadableStudy
   */
  private LoadablePattern saveloadablePattern(
      LoadablePlanDetails lpd, LoadableStudy loadableStudy) {
    LoadablePattern loadablePattern = new LoadablePattern();
    loadablePattern.setCaseNumber(lpd.getCaseNumber());
    loadablePattern.setIsActive(true);
    loadablePattern.setLoadableStudy(loadableStudy);
    loadablePattern.setLoadableStudyStatus(LOADABLE_STUDY_STATUS_PLAN_GENERATED_ID);
    loadablePatternRepository.save(loadablePattern);
    return loadablePattern;
  }

  /**
   * @param loadableStudy
   * @param one
   * @return Long
   */
  private Long getLastPort(LoadableStudy loadableStudy, CargoOperation loading) {
    return loadableStudyPortRotationRepository.findLastPort(loadableStudy, loading, true);
  }

  @Override
  public void getLoadablePatternList(
      LoadablePatternRequest request, StreamObserver<LoadablePatternReply> responseObserver) {
    LoadablePatternReply.Builder replyBuilder = LoadablePatternReply.newBuilder();
    try {
      log.info("getLoadablePatternList - loadable study micro service");
      Optional<LoadableStudy> loadableStudyOpt =
          this.loadableStudyRepository.findByIdAndIsActive(request.getLoadableStudyId(), true);
      if (!loadableStudyOpt.isPresent()) {
        throw new GenericServiceException(
            "Loadable study does not exist",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      List<LoadablePattern> patterns =
          this.loadablePatternRepository.findByLoadableStudyAndIsActive(
              loadableStudyOpt.get(), true);
      if (null != patterns && !patterns.isEmpty()) {
        this.buildPatternDetails(patterns, replyBuilder);
      }
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    } catch (GenericServiceException e) {
      log.error("GenericServiceException in loadable pattern list", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(e.getCode())
              .setMessage(e.getMessage())
              .setStatus(FAILED)
              .build());
    } catch (Exception e) {
      log.error("Exception in loadable pattern list", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Exception in getLoadablePatternList")
              .setStatus(FAILED)
              .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * Build pattern reply
   *
   * @param patterns
   * @param replyBuilder
   */
  private void buildPatternDetails(
      List<LoadablePattern> patterns, LoadablePatternReply.Builder replyBuilder) {
    for (LoadablePattern pattern : patterns) {
      com.cpdss.common.generated.LoadableStudy.LoadablePattern.Builder builder =
          com.cpdss.common.generated.LoadableStudy.LoadablePattern.newBuilder();
      builder.setLoadablePatternId(pattern.getId());
      Optional.ofNullable(pattern.getCaseNumber()).ifPresent(item -> builder.setCaseNumber(item));
      replyBuilder.addLoadablePattern(builder.build());
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

              Optional.ofNullable(loadablePattern.getCaseNumber())
                  .ifPresent(loadablePatternBuilder::setCaseNumber);

              List<LoadablePlanConstraints> loadablePlanConstraints =
                  loadablePlanConstraintsRespository.findByLoadablePatternAndIsActive(
                      loadablePattern, true);

              buildLoadablePatternConstraints(loadablePlanConstraints, loadablePatternBuilder);

              loadablePatternBuilder.clearLoadablePatternCargoDetails();
              buildLoadablePatternCargoAndCommingleDetails(loadablePattern, loadablePatternBuilder);
              List<LoadablePlanStowageDetails> loadablePlanStowageDetails =
                  loadablePlanStowageDetailsRespository.findByLoadablePatternAndIsActive(
                      loadablePattern, true);
              com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply.Builder
                  replyBuilder =
                      com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply
                          .newBuilder();
              buildLoadablePlanStowageCargoDetails(loadablePlanStowageDetails, replyBuilder);
              loadablePatternBuilder.addAllLoadablePlanStowageDetails(
                  replyBuilder.getLoadablePlanStowageDetailsList());

              builder.addLoadablePattern(loadablePatternBuilder);
              loadablePatternBuilder.clearLoadablePlanStowageDetails();
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
   * @param loadablePlanConstraints
   * @param loadablePatternBuilder void
   */
  private void buildLoadablePatternConstraints(
      List<LoadablePlanConstraints> loadablePlanConstraints,
      com.cpdss.common.generated.LoadableStudy.LoadablePattern.Builder loadablePatternBuilder) {
    loadablePlanConstraints.forEach(
        lpc -> {
          loadablePatternBuilder.addConstraints(lpc.getConstraintsData());
        });
  }

  /**
   * @param loadablePattern
   * @param loadablePatternBuilder void
   */
  private void buildLoadablePatternCargoAndCommingleDetails(
      LoadablePattern loadablePattern,
      com.cpdss.common.generated.LoadableStudy.LoadablePattern.Builder loadablePatternBuilder) {
    List<LoadablePlanQuantity> loadablePlanQuantities =
        loadablePlanQuantityRepository.findByLoadablePatternAndIsActive(loadablePattern, true);
    loadablePlanQuantities.forEach(
        lpq -> {
          LoadablePatternCargoDetails.Builder loadablePatternCargoDetailsBuilder =
              LoadablePatternCargoDetails.newBuilder();
          Optional.ofNullable(lpq.getPriority())
              .ifPresent(priority -> loadablePatternCargoDetailsBuilder.setPriority(priority));
          Optional.ofNullable(lpq.getLoadableMt())
              .ifPresent(
                  quantity ->
                      loadablePatternCargoDetailsBuilder.setQuantity(String.valueOf(quantity)));
          Optional.ofNullable(lpq.getOrderQuantity())
              .ifPresent(
                  orderedQuantity ->
                      loadablePatternCargoDetailsBuilder.setOrderedQuantity(
                          String.valueOf(orderedQuantity)));

          Optional.ofNullable(lpq.getCargoAbbreviation())
              .ifPresent(
                  cargoAbbreviation ->
                      loadablePatternCargoDetailsBuilder.setCargoAbbreviation(cargoAbbreviation));
          Optional.ofNullable(lpq.getCargoColor())
              .ifPresent(
                  cargoColor -> loadablePatternCargoDetailsBuilder.setCargoColor(cargoColor));
          loadablePatternCargoDetailsBuilder.setIsCommingle(false);
          loadablePatternBuilder.addLoadablePatternCargoDetails(loadablePatternCargoDetailsBuilder);
        });

    List<LoadablePlanCommingleDetails> loadablePlanCommingleDetails =
        loadablePlanCommingleDetailsRepository.findByLoadablePatternAndIsActive(
            loadablePattern, true);
    loadablePlanCommingleDetails.forEach(
        lpcd -> {
          LoadablePatternCargoDetails.Builder loadablePatternCargoDetailsBuilder =
              LoadablePatternCargoDetails.newBuilder();
          Optional.ofNullable(lpcd.getPriority())
              .ifPresent(priority -> loadablePatternCargoDetailsBuilder.setPriority(priority));
          Optional.ofNullable(lpcd.getQuantity())
              .ifPresent(
                  quantity ->
                      loadablePatternCargoDetailsBuilder.setQuantity(String.valueOf(quantity)));
          Optional.ofNullable(lpcd.getOrderQuantity())
              .ifPresent(
                  orderedQuantity ->
                      loadablePatternCargoDetailsBuilder.setOrderedQuantity(
                          String.valueOf(orderedQuantity)));

          Optional.ofNullable(lpcd.getGrade())
              .ifPresent(
                  cargoAbbreviation ->
                      loadablePatternCargoDetailsBuilder.setCargoAbbreviation(cargoAbbreviation));

          loadablePatternCargoDetailsBuilder.setIsCommingle(true);
          Optional.ofNullable(lpcd.getId())
              .ifPresent(
                  id ->
                      loadablePatternCargoDetailsBuilder.setLoadablePatternCommingleDetailsId(id));
          loadablePatternBuilder.addLoadablePatternCargoDetails(loadablePatternCargoDetailsBuilder);
        });
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
      // get preferred tanks
      VesselRequest.Builder vesselGrpcRequest = VesselRequest.newBuilder();
      vesselGrpcRequest.setVesselId(request.getVesselId());
      vesselGrpcRequest.addAllTankCategories(CARGO_TANK_CATEGORIES);
      VesselReply vesselReply = this.getVesselTanks(vesselGrpcRequest.build());
      if (!SUCCESS.equals(vesselReply.getResponseStatus().getStatus())) {
        throw new GenericServiceException(
            "Failed to fetch vessel particualrs",
            vesselReply.getResponseStatus().getCode(),
            HttpStatusCode.valueOf(Integer.valueOf(vesselReply.getResponseStatus().getCode())));
      }
      buildCommingleCargoReply(commingleCargoList, replyBuilder, vesselReply);
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
      CommingleCargoReply.Builder replyBuilder,
      VesselReply vesselReply) {
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
    // build preferred tanks
    replyBuilder.addAllTanks(groupTanks(vesselReply.getVesselTanksList()));
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
      Optional<LoadablePlanCommingleDetails> loadablePlanComingleDetails =
          loadablePlanCommingleDetailsRepository.findByIdAndIsActive(
              request.getLoadablePatternCommingleDetailsId(), true);
      if (!loadablePlanComingleDetails.isPresent()) {
        log.info(
            INVALID_LOADABLE_PATTERN_COMMINGLE_DETAIL_ID,
            request.getLoadablePatternCommingleDetailsId());
        builder.setResponseStatus(
            ResponseStatus.newBuilder()
                .setStatus(FAILED)
                .setMessage(INVALID_LOADABLE_PATTERN_COMMINGLE_DETAIL_ID)
                .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST));
      } else {

        buildLoadablePatternComingleDetails(loadablePlanComingleDetails.get(), builder);
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
      LoadablePlanCommingleDetails loadablePlanCommingleDetails,
      com.cpdss.common.generated.LoadableStudy.LoadablePatternCommingleDetailsReply.Builder
          builder) {
    Optional.ofNullable(loadablePlanCommingleDetails.getApi())
        .ifPresent(api -> builder.setApi(String.valueOf(api)));
    Optional.ofNullable(loadablePlanCommingleDetails.getCargo1Abbreviation())
        .ifPresent(cargo1Abbrivation -> builder.setCargo1Abbrivation(cargo1Abbrivation));

    Optional.ofNullable(loadablePlanCommingleDetails.getCargo2Abbreviation())
        .ifPresent(cargo2Abbrivation -> builder.setCargo2Abbrivation(cargo2Abbrivation));

    Optional.ofNullable(loadablePlanCommingleDetails.getCargo1Percentage())
        .ifPresent(
            cargo1Percentage -> builder.setCargo1Percentage(String.valueOf(cargo1Percentage)));

    Optional.ofNullable(loadablePlanCommingleDetails.getCargo2Percentage())
        .ifPresent(
            cargo2Percentage -> builder.setCargo2Percentage(String.valueOf(cargo2Percentage)));

    Optional.ofNullable(loadablePlanCommingleDetails.getCargo1Mt())
        .ifPresent(cargo1Quantity -> builder.setCargo1Quantity(String.valueOf(cargo1Quantity)));

    Optional.ofNullable(loadablePlanCommingleDetails.getCargo2Mt())
        .ifPresent(cargo2Quantity -> builder.setCargo2Quantity(String.valueOf(cargo2Quantity)));

    Optional.ofNullable(loadablePlanCommingleDetails.getGrade())
        .ifPresent(grade -> builder.setGrade(grade));

    Optional.ofNullable(loadablePlanCommingleDetails.getQuantity())
        .ifPresent(quantity -> builder.setQuantity(String.valueOf(quantity)));

    Optional.ofNullable(loadablePlanCommingleDetails.getTankName())
        .ifPresent(tankShortName -> builder.setTankShortName(tankShortName));

    Optional.ofNullable(loadablePlanCommingleDetails.getTemperature())
        .ifPresent(temperature -> builder.setTemperature(String.valueOf(temperature)));

    Optional.ofNullable(loadablePlanCommingleDetails.getId()).ifPresent(id -> builder.setId(id));
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

        buildLoadableStuydDetails(loadableStudyOpt, loadableStudy);
        buildCargoNominationDetails(request.getLoadableStudyId(), loadableStudy, modelMapper);
        buildCommingleCargoDetails(loadableStudyOpt.get().getId(), loadableStudy, modelMapper);
        buildLoadableQuantityDetails(request.getLoadableStudyId(), loadableStudy);
        buildLoadableStudyPortRotationDetails(
            request.getLoadableStudyId(), loadableStudy, modelMapper);
        buildCargoNominationPortDetails(request.getLoadableStudyId(), loadableStudy);
        buildOnHandQuantityDetails(loadableStudyOpt.get(), loadableStudy, modelMapper);
        buildOnBoardQuantityDetails(loadableStudyOpt.get(), loadableStudy, modelMapper);
        buildportRotationDetails(loadableStudyOpt.get(), loadableStudy);

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.writeValue(new File("loadableStudy.json"), loadableStudy);

        AlgoResponse algoResponse =
            restTemplate.postForObject(loadableStudyUrl, loadableStudy, AlgoResponse.class);
        updateProcessId(algoResponse, loadableStudyOpt.get());

        replyBuilder =
            AlgoReply.newBuilder()
                .setProcesssId(algoResponse.getProcessId())
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
    } catch (ResourceAccessException e) {
      log.info("Error calling ALGO ", request.getLoadableStudyId());
      replyBuilder =
          AlgoReply.newBuilder()
              .setResponseStatus(
                  ResponseStatus.newBuilder()
                      .setStatus(FAILED)
                      .setMessage(ERRO_CALLING_ALGO)
                      .setCode(CommonErrorCodes.E_CPDSS_ALGO_ISSUE)
                      .build());
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

  /**
   * @param algoResponse
   * @param loadableStudy void
   */
  public void updateProcessId(AlgoResponse algoResponse, LoadableStudy loadableStudy) {
    LoadableStudyAlgoStatus status = new LoadableStudyAlgoStatus();
    status.setLoadableStudy(loadableStudy);
    status.setIsActive(true);
    status.setLoadableStudyStatus(
        loadableStudyStatusRepository.getOne(LOADABLE_STUDY_PROCESSING_STARTED_ID));
    status.setProcessId(algoResponse.getProcessId());
    status.setVesselxid(loadableStudy.getVesselXId());
    loadableStudyAlgoStatusRepository.save(status);
  }

  /**
   * @param loadableStudy
   * @param loadableStudyEntity void
   */
  private void buildportRotationDetails(
      LoadableStudy loadableStudyEntity,
      com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy) {
    GetPortInfoByPortIdsRequest.Builder portsBuilder = GetPortInfoByPortIdsRequest.newBuilder();
    List<Long> portIds = this.getPortRoationPortIds(loadableStudyEntity);
    portIds.forEach(
        portId -> {
          portsBuilder.addId(portId);
        });

    loadableStudy.setPortDetails(new ArrayList<PortDetails>());
    PortReply portReply = getPortInfo(portsBuilder.build());
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
  }

  /**
   * @param build
   * @return PortReply
   */
  public PortReply getPortInfo(GetPortInfoByPortIdsRequest build) {
    return portInfoGrpcService.getPortInfoByPortIds(build);
  }

  /**
   * @param loadableStudy
   * @param loadableStudy2
   * @param modelMapper void
   */
  private void buildOnBoardQuantityDetails(
      LoadableStudy loadableStudyEntity,
      com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy,
      ModelMapper modelMapper) {
    loadableStudy.setOnBoardQuantity(new ArrayList<>());
    List<OnBoardQuantity> onBoardQuantities =
        onBoardQuantityRepository.findByLoadableStudyAndIsActive(loadableStudyEntity, true);
    onBoardQuantities.forEach(
        onBoardQuantity -> {
          com.cpdss.loadablestudy.domain.OnBoardQuantity onBoardQuantityDto =
              new com.cpdss.loadablestudy.domain.OnBoardQuantity();
          onBoardQuantityDto =
              modelMapper.map(
                  onBoardQuantity, com.cpdss.loadablestudy.domain.OnBoardQuantity.class);
          loadableStudy.getOnBoardQuantity().add(onBoardQuantityDto);
        });
  }

  /**
   * @param loadableStudy
   * @param loadableStudy2
   * @param modelMapper void
   */
  private void buildOnHandQuantityDetails(
      LoadableStudy loadableStudyEntity,
      com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy,
      ModelMapper modelMapper) {
    loadableStudy.setOnHandQuantity(new ArrayList<>());
    List<OnHandQuantity> onHandQuantities =
        onHandQuantityRepository.findByLoadableStudyAndIsActive(loadableStudyEntity, true);
    onHandQuantities.forEach(
        onHandQuantity -> {
          com.cpdss.loadablestudy.domain.OnHandQuantity onHandQuantityDto =
              new com.cpdss.loadablestudy.domain.OnHandQuantity();
          onHandQuantityDto =
              modelMapper.map(onHandQuantity, com.cpdss.loadablestudy.domain.OnHandQuantity.class);
          loadableStudy.getOnHandQuantity().add(onHandQuantityDto);
        });
  }

  /**
   * @param loadableStudyId
   * @param loadableStudy void
   */
  private void buildCargoNominationPortDetails(
      long loadableStudyId, com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy) {
    List<CargoNomination> cargoNominations =
        cargoNominationRepository.findByLoadableStudyXIdAndIsActive(loadableStudyId, true);
    loadableStudy.setCargoNominationOperationDetails(new ArrayList<>());
    List<CargoNominationPortDetails> cargoNominationOperationDetails =
        cargoNominationOperationDetailsRepository.findByCargoNominationAndIsActive(
            cargoNominations, true);
    cargoNominationOperationDetails.forEach(
        cargoNominationOperationDetail -> {
          com.cpdss.loadablestudy.domain.CargoNominationOperationDetails
              cargoNominationOperationDetailDto =
                  new com.cpdss.loadablestudy.domain.CargoNominationOperationDetails();
          cargoNominationOperationDetailDto.setCargoNominationId(
              cargoNominationOperationDetail.getCargoNomination().getId());
          cargoNominationOperationDetailDto.setId(cargoNominationOperationDetail.getId());
          cargoNominationOperationDetailDto.setPortId(cargoNominationOperationDetail.getPortId());
          cargoNominationOperationDetailDto.setQuantity(
              String.valueOf(cargoNominationOperationDetail.getQuantity()));
          loadableStudy.getCargoNominationOperationDetails().add(cargoNominationOperationDetailDto);
        });
  }

  /**
   * @param loadableStudyId
   * @param loadableStudy
   * @param modelMapper void
   */
  private void buildLoadableStudyPortRotationDetails(
      long loadableStudyId,
      com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy,
      ModelMapper modelMapper) {
    List<LoadableStudyPortRotation> loadableStudyPortRotations =
        loadableStudyPortRotationRepository.findByLoadableStudyAndIsActive(loadableStudyId, true);

    loadableStudy.setLoadableStudyPortRotation(new ArrayList<>());
    if (!loadableStudyPortRotations.isEmpty()) {
      loadableStudyPortRotations.forEach(
          loadableStudyPortRotation -> {
            com.cpdss.loadablestudy.domain.LoadableStudyPortRotation loadableStudyPortRotationDto =
                new com.cpdss.loadablestudy.domain.LoadableStudyPortRotation();
            loadableStudyPortRotationDto =
                modelMapper.map(
                    loadableStudyPortRotation,
                    com.cpdss.loadablestudy.domain.LoadableStudyPortRotation.class);
            loadableStudy.getLoadableStudyPortRotation().add(loadableStudyPortRotationDto);
          });
    }
  }

  /**
   * @param loadableStudyId
   * @param loadableStudy void
   */
  private void buildLoadableQuantityDetails(
      long loadableStudyId, com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy) {
    List<LoadableQuantity> loadableQuantity =
        loadableQuantityRepository.findByLoadableStudyXIdAndIsActive(loadableStudyId, true);
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
                .ifPresent(constant -> loadableQuantityDto.setConstant(String.valueOf(constant)));
            Optional.ofNullable(loadableQunty.getDeadWeight())
                .ifPresent(
                    deadWeight -> loadableQuantityDto.setDeadWeight(String.valueOf(deadWeight)));
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
                        loadableQuantityDto.setDraftRestriction(String.valueOf(draftRestriction)));
            Optional.ofNullable(loadableQunty.getEstimatedDOOnBoard())
                .ifPresent(
                    estimatedDOOnBoard ->
                        loadableQuantityDto.setEstDOOnBoard(String.valueOf(estimatedDOOnBoard)));
            Optional.ofNullable(loadableQunty.getEstimatedFOOnBoard())
                .ifPresent(
                    estimatedFOOnBoard ->
                        loadableQuantityDto.setEstDOOnBoard(String.valueOf(estimatedFOOnBoard)));
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
                        loadableQuantityDto.setEstSeaDensity(String.valueOf(estimatedSeaDensity)));
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
                    otherIfAny -> loadableQuantityDto.setOtherIfAny(String.valueOf(otherIfAny)));
            Optional.ofNullable(loadableQunty.getPortId())
                .ifPresent(
                    portId -> loadableQuantityDto.setPortId(Long.valueOf(portId.toString())));
            Optional.ofNullable(loadableQunty.getRunningDays())
                .ifPresent(
                    runningDays -> loadableQuantityDto.setRunningDays(String.valueOf(runningDays)));
            Optional.ofNullable(loadableQunty.getRunningHours())
                .ifPresent(
                    runningHours ->
                        loadableQuantityDto.setRunningHours(String.valueOf(runningHours)));
            Optional.ofNullable(loadableQunty.getSaggingDeduction())
                .ifPresent(
                    saggingDeduction ->
                        loadableQuantityDto.setSaggingDeduction(String.valueOf(saggingDeduction)));
            Optional.ofNullable(loadableQunty.getSgCorrection())
                .ifPresent(
                    sgCorrection ->
                        loadableQuantityDto.setSgCorrection(String.valueOf(sgCorrection)));
            Optional.ofNullable(loadableQunty.getSubTotal())
                .ifPresent(subTotal -> loadableQuantityDto.setSubTotal(String.valueOf(subTotal)));
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
                .ifPresent(tpcatDraft -> loadableQuantityDto.setTpc(String.valueOf(tpcatDraft)));
            Optional.ofNullable(loadableQunty.getVesselAverageSpeed())
                .ifPresent(
                    VesselAverageSpeed ->
                        loadableQuantityDto.setVesselAverageSpeed(
                            String.valueOf(VesselAverageSpeed)));

            loadableStudy.setLoadableQuantity(loadableQuantityDto);
          });
    }
  }

  /**
   * @param id
   * @param loadableStudy void
   * @param modelMapper
   */
  private void buildCommingleCargoDetails(
      Long loadableStudyId,
      com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy,
      ModelMapper modelMapper) {

    List<com.cpdss.loadablestudy.entity.CommingleCargo> commingleCargos =
        commingleCargoRepository.findByLoadableStudyXIdAndIsActive(loadableStudyId, true);

    loadableStudy.setCommingleCargos(new ArrayList<>());

    commingleCargos.forEach(
        commingleCargo -> {
          com.cpdss.loadablestudy.domain.CommingleCargo commingleCargoDto =
              new com.cpdss.loadablestudy.domain.CommingleCargo();
          commingleCargoDto =
              modelMapper.map(commingleCargo, com.cpdss.loadablestudy.domain.CommingleCargo.class);
          commingleCargoDto.setCargo1Id(commingleCargo.getCargo1Xid());
          commingleCargoDto.setCargo2Id(commingleCargo.getCargo2Xid());
          commingleCargoDto.setCargo1Percentage(commingleCargo.getCargo1Pct().toString());
          commingleCargoDto.setCargo2Percentage(commingleCargo.getCargo2Pct().toString());
          loadableStudy.getCommingleCargos().add(commingleCargoDto);
        });
  }

  /**
   * @param loadableStudyId
   * @param loadableStudy void
   * @param modelMapper
   */
  private void buildCargoNominationDetails(
      long loadableStudyId,
      com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy,
      ModelMapper modelMapper) {
    List<CargoNomination> cargoNominations =
        cargoNominationRepository.findByLoadableStudyXIdAndIsActive(loadableStudyId, true);

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
  }

  /**
   * @param loadableStudyOpt
   * @param loadableStudy void
   */
  private void buildLoadableStuydDetails(
      Optional<LoadableStudy> loadableStudyOpt,
      com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy) {
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
            estimatedMaxSag -> loadableStudy.setEstimatedMaxSG(String.valueOf(estimatedMaxSag)));
    Optional.ofNullable(loadableStudyOpt.get().getMaxAirTemperature())
        .ifPresent(
            maxAirTemperature -> loadableStudy.setMaxAirTemp(String.valueOf(maxAirTemperature)));
    Optional.ofNullable(loadableStudyOpt.get().getMaxWaterTemperature())
        .ifPresent(
            maxWaterTemperature ->
                loadableStudy.setMaxWaterTemp(String.valueOf(maxWaterTemperature)));
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
        Optional.ofNullable(entity.getActualArrivalWeight())
            .ifPresent(item -> builder.setActualWeight(item.toString()));
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
          Optional.ofNullable(dto.getQuantity())
              .ifPresent(item -> builder.setWeight(valueOf(item)));
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
      log.info("Inside generateLoadablePatterns service");
      Optional<LoadableStudyAlgoStatus> loadableStudyAlgoStatusOpt =
          loadableStudyAlgoStatusRepository.findByProcessIdAndIsActive(
              request.getProcesssId(), true);
      if (!loadableStudyAlgoStatusOpt.isPresent()) {
        log.info("Invalid process id for updateing loadable study sttus");
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
  @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
  public void saveSynopticalTable(
      SynopticalTableRequest request, StreamObserver<SynopticalTableReply> responseObserver) {
    SynopticalTableReply.Builder replyBuilder = SynopticalTableReply.newBuilder();
    try {
      Optional<LoadableStudy> loadableStudyOpt =
          this.loadableStudyRepository.findByIdAndIsActive(request.getLoadableStudyId(), true);
      if (!loadableStudyOpt.isPresent()) {
        throw new GenericServiceException(
            "Loadable study does not exist with given id",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      Optional<LoadablePattern> patternOpt = Optional.empty();
      if (request.getLoadablePatternId() > 0) {
        patternOpt =
            this.loadablePatternRepository.findByIdAndIsActive(
                request.getLoadablePatternId(), true);
        if (!patternOpt.isPresent()) {
          throw new GenericServiceException(
              "Loadable pattern does not exist with given id",
              CommonErrorCodes.E_HTTP_BAD_REQUEST,
              HttpStatusCode.BAD_REQUEST);
        }
      }
      for (SynopticalRecord record : request.getSynopticalRecordList()) {
        Optional<SynopticalTable> entityOpt =
            this.synopticalTableRepository.findByIdAndIsActive(record.getId(), true);
        if (!entityOpt.isPresent()) {
          throw new GenericServiceException(
              "Synoptical record does not exist with given id",
              CommonErrorCodes.E_HTTP_BAD_REQUEST,
              HttpStatusCode.BAD_REQUEST);
        }
        SynopticalTable entity = entityOpt.get();
        entity = this.buildSynopticalTableEntity(entity, record);
        entity = this.synopticalTableRepository.save(entity);
        this.saveSynopticalEtaEtdEstimates(entity, record);
        if (request.getLoadablePatternId() > 0) {
          this.saveSynopticalLoadicatorData(request.getLoadablePatternId(), record);
          this.saveSynopticalBallastData(request.getLoadablePatternId(), record, entity);
        }
        this.saveSynopticalCargoData(request, loadableStudyOpt.get(), entity, record);
        this.saveSynopticalOhqData(loadableStudyOpt.get(), entity, record);
      }
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when saving synoptical table", e);
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(e.getCode())
              .setMessage("GenericServiceException when saving synoptical table")
              .setStatus(FAILED)
              .build());
    } catch (Exception e) {
      log.error("Exception when saving saving synoptical table", e);
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Exception when saving synoptical table")
              .setStatus(FAILED)
              .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * Save synoptical ballast data
   *
   * @param loadablePatternId
   * @param record
   * @param entity
   */
  private void saveSynopticalBallastData(
      long loadablePatternId, SynopticalRecord record, SynopticalTable entity) {
    List<LoadablePlanStowageBallastDetails> ballastEntities =
        this.loadablePlanStowageBallastDetailsRepository
            .findByLoadablePatternIdAndPortXIdAndOperationTypeAndIsActive(
                loadablePatternId, entity.getPortXid(), entity.getOperationType(), true);
    List<LoadablePlanStowageBallastDetails> toBeSaved = new ArrayList<>();
    if (!record.getBallastList().isEmpty()) {
      for (SynopticalBallastRecord rec : record.getBallastList()) {
        Optional<LoadablePlanStowageBallastDetails> entityOpt =
            ballastEntities.stream()
                .filter(b -> b.getTankXId().longValue() == rec.getTankId())
                .findAny();
        if (entityOpt.isPresent()) {
          LoadablePlanStowageBallastDetails ent = entityOpt.get();
          ent.setActualQuantity(
              isEmpty(rec.getActualWeight()) ? null : new BigDecimal(rec.getActualWeight()));
          toBeSaved.add(ent);
        }
      }
      this.loadablePlanStowageBallastDetailsRepository.saveAll(toBeSaved);
    }
  }

  private void saveSynopticalOhqData(
      LoadableStudy loadableStudy, SynopticalTable entity, SynopticalRecord record) {
    List<OnHandQuantity> ohqEntities =
        this.onHandQuantityRepository.findByLoadableStudyAndPortXIdAndIsActive(
            loadableStudy, record.getPortId(), true);
    List<OnHandQuantity> toBeSavedList = new ArrayList<>();
    for (SynopticalOhqRecord ohqRecord : record.getOhqList()) {
      OnHandQuantity ohqEntity = null;
      Optional<OnHandQuantity> ohqEntityOpt =
          ohqEntities.stream()
              .filter(ohq -> ohq.getTankXId().equals(ohqRecord.getTankId()))
              .findAny();
      if (ohqEntityOpt.isPresent()) {
        ohqEntity = ohqEntityOpt.get();
      } else {
        ohqEntity = new OnHandQuantity();
        ohqEntity.setTankXId(ohqRecord.getTankId());
        ohqEntity.setPortXId(record.getPortId());
        ohqEntity.setLoadableStudy(loadableStudy);
        ohqEntity.setIsActive(true);
      }

      if (SYNOPTICAL_TABLE_OP_TYPE_ARRIVAL.equals(entity.getOperationType())) {
        ohqEntity.setArrivalQuantity(
            isEmpty(ohqRecord.getPlannedWeight())
                ? null
                : new BigDecimal(ohqRecord.getPlannedWeight()));
        ohqEntity.setActualArrivalQuantity(
            isEmpty(ohqRecord.getActualWeight())
                ? null
                : new BigDecimal(ohqRecord.getActualWeight()));
      } else {
        ohqEntity.setDepartureQuantity(
            isEmpty(ohqRecord.getPlannedWeight())
                ? null
                : new BigDecimal(ohqRecord.getPlannedWeight()));
        ohqEntity.setActualDepartureQuantity(
            isEmpty(ohqRecord.getActualWeight())
                ? null
                : new BigDecimal(ohqRecord.getActualWeight()));
      }
      toBeSavedList.add(ohqEntity);
    }
    if (!toBeSavedList.isEmpty()) {
      this.onHandQuantityRepository.saveAll(toBeSavedList);
    }
  }

  /**
   * Save cargo data
   *
   * @param request
   * @param loadableStudy
   * @param entity
   * @param request
   * @throws GenericServiceException
   */
  private void saveSynopticalCargoData(
      SynopticalTableRequest request,
      LoadableStudy loadableStudy,
      SynopticalTable entity,
      SynopticalRecord record)
      throws GenericServiceException {
    List<Long> portIds = this.getPortRoationPortIds(loadableStudy);
    if (null == portIds || portIds.isEmpty()) {
      throw new GenericServiceException(
          "No ports added for loadable study with id: " + loadableStudy.getId(),
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    Long firstPortId = portIds.get(0);
    if (entity.getPortXid().equals(firstPortId)
        && SYNOPTICAL_TABLE_OP_TYPE_ARRIVAL.equals(entity.getOperationType())) {
      this.saveSynopticalObqData(loadableStudy, record);
    } else if (request.getLoadablePatternId() > 0) {
      this.saveSynopticalCargoByLoadablePattern(request, entity, record);
    }
  }

  /**
   * Save synoptical cargo data by loadable pattern id
   *
   * @param request
   * @param entity
   * @param record
   */
  private void saveSynopticalCargoByLoadablePattern(
      SynopticalTableRequest request, SynopticalTable entity, SynopticalRecord record) {
    List<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails> cargoEntities =
        this.loadablePatternCargoDetailsRepository
            .findByLoadablePatternIdAndPortIdAndOperationTypeAndIsActive(
                request.getLoadablePatternId(),
                entity.getPortXid(),
                entity.getOperationType(),
                true);
    List<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails> toBeSavedCargoList =
        new ArrayList<>();
    for (SynopticalCargoRecord cargoRecord : record.getCargoList()) {
      Optional<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails> entityOpt =
          cargoEntities.stream()
              .filter(cargo -> cargo.getTankId().equals(cargoRecord.getTankId()))
              .findAny();
      if (entityOpt.isPresent()) {
        com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails cargoEntity = entityOpt.get();
        cargoEntity.setActualQuantity(
            isEmpty(cargoRecord.getActualWeight())
                ? null
                : new BigDecimal(cargoRecord.getActualWeight()));
        toBeSavedCargoList.add(cargoEntity);
      }
    }
    if (!toBeSavedCargoList.isEmpty()) {
      this.loadablePatternCargoDetailsRepository.saveAll(toBeSavedCargoList);
    }
  }

  /**
   * Save obq data from synoptical table
   *
   * @param loadableStudy
   * @param record
   */
  private void saveSynopticalObqData(LoadableStudy loadableStudy, SynopticalRecord record) {
    List<OnBoardQuantity> obqEntities =
        this.onBoardQuantityRepository.findByLoadableStudyAndPortIdAndIsActive(
            loadableStudy, record.getPortId(), true);
    List<OnBoardQuantity> toBeSavedList = new ArrayList<>();
    for (SynopticalCargoRecord cargoRecord : record.getCargoList()) {
      OnBoardQuantity obqEntity = null;
      Optional<OnBoardQuantity> obqEntityOpt =
          obqEntities.stream()
              .filter(obq -> obq.getTankId().equals(cargoRecord.getTankId()))
              .findAny();
      if (obqEntityOpt.isPresent()) {
        obqEntity = obqEntityOpt.get();
      } else {
        obqEntity = new OnBoardQuantity();
        obqEntity.setTankId(cargoRecord.getTankId());
        obqEntity.setPortId(record.getPortId());
        obqEntity.setLoadableStudy(loadableStudy);
        obqEntity.setIsActive(true);
      }
      obqEntity.setPlannedArrivalWeight(
          isEmpty(cargoRecord.getPlannedWeight())
              ? null
              : new BigDecimal(cargoRecord.getPlannedWeight()));
      obqEntity.setActualArrivalWeight(
          isEmpty(cargoRecord.getActualWeight())
              ? null
              : new BigDecimal(cargoRecord.getActualWeight()));
      toBeSavedList.add(obqEntity);
    }
    if (!toBeSavedList.isEmpty()) {
      this.onBoardQuantityRepository.saveAll(toBeSavedList);
    }
  }

  /**
   * Save synoptical table loadicator data
   *
   * @param entity
   * @param entity
   * @param request
   * @throws GenericServiceException
   */
  public void saveSynopticalLoadicatorData(Long loadablepatternId, SynopticalRecord record)
      throws GenericServiceException {
    com.cpdss.common.generated.LoadableStudy.SynopticalTableLoadicatorData data =
        record.getLoadicatorData();
    SynopticalTableLoadicatorData ldEntity =
        this.synopticalTableLoadicatorDataRepository.findByLoadablePatternIdAndIsActive(
            loadablepatternId, true);
    if (null == ldEntity) {
      log.info(
          "Loadicator data does not exist for given synoptical record with id {}", record.getId());
    } else {
      ldEntity.setHog(isEmpty(data.getHogSag()) ? null : new BigDecimal(data.getHogSag()));
      ldEntity.setCalculatedDraftFwdActual(
          isEmpty(data.getCalculatedDraftFwdActual())
              ? null
              : new BigDecimal(data.getCalculatedDraftFwdActual()));
      ldEntity.setCalculatedDraftAftActual(
          isEmpty(data.getCalculatedDraftAftActual())
              ? null
              : new BigDecimal(data.getCalculatedDraftAftActual()));
      ldEntity.setCalculatedDraftMidActual(
          isEmpty(data.getCalculatedDraftMidActual())
              ? null
              : new BigDecimal(data.getCalculatedDraftMidActual()));
      ldEntity.setCalculatedTrimActual(
          isEmpty(data.getCalculatedTrimActual())
              ? null
              : new BigDecimal(data.getCalculatedTrimActual()));
      ldEntity.setBlindSector(
          isEmpty(data.getBlindSector()) ? null : new BigDecimal(data.getBlindSector()));
      ldEntity.setBallastActual(
          isEmpty(record.getBallastActual()) ? null : new BigDecimal(record.getBallastActual()));
      this.synopticalTableLoadicatorDataRepository.save(ldEntity);
    }
  }

  /**
   * Update estimated values to port rotation table
   *
   * @param entity
   * @param request
   * @throws GenericServiceException
   */
  public void saveSynopticalEtaEtdEstimates(SynopticalTable entity, SynopticalRecord record)
      throws GenericServiceException {
    LoadableStudyPortRotation prEntity = entity.getLoadableStudyPortRotation();
    if (null == prEntity) {
      throw new GenericServiceException(
          "Port rotation does not exist for given synoptical record",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    LocalDateTime etaEtdEstimated =
        isEmpty(record.getEtaEtdEstimated())
            ? null
            : LocalDateTime.from(
                DateTimeFormatter.ofPattern(ETA_ETD_FORMAT).parse(record.getEtaEtdEstimated()));
    if (SYNOPTICAL_TABLE_OP_TYPE_ARRIVAL.equals(entity.getOperationType())) {
      prEntity.setEta(etaEtdEstimated);
    } else {
      prEntity.setEtd(etaEtdEstimated);
    }
    this.loadableStudyPortRotationRepository.save(prEntity);
  }

  /**
   * Populate synoptical entity fields
   *
   * @param entity
   * @param request
   * @return
   */
  public SynopticalTable buildSynopticalTableEntity(
      SynopticalTable entity, SynopticalRecord record) {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
    entity.setDistance(isEmpty(record.getDistance()) ? null : new BigDecimal(record.getDistance()));
    entity.setSpeed(isEmpty(record.getSpeed()) ? null : new BigDecimal(record.getSpeed()));
    entity.setRunningHours(
        isEmpty(record.getRunningHours()) ? null : new BigDecimal(record.getRunningHours()));
    entity.setInPortHours(
        isEmpty(record.getInPortHours()) ? null : new BigDecimal(record.getInPortHours()));
    entity.setTimeOfSunrise(
        isEmpty(record.getTimeOfSunrise())
            ? null
            : LocalTime.from(dtf.parse(record.getTimeOfSunrise())));
    entity.setTimeOfSunSet(
        isEmpty(record.getTimeOfSunset())
            ? null
            : LocalTime.from(dtf.parse(record.getTimeOfSunset())));

    entity.setSpecificGravity(
        isEmpty(record.getSpecificGravity()) ? null : new BigDecimal(record.getSpecificGravity()));
    entity.setHwTideFrom(
        isEmpty(record.getHwTideFrom()) ? null : new BigDecimal(record.getHwTideFrom()));
    entity.setHwTideTo(isEmpty(record.getHwTideTo()) ? null : new BigDecimal(record.getHwTideTo()));
    entity.setLwTideFrom(
        isEmpty(record.getLwTideFrom()) ? null : new BigDecimal(record.getLwTideFrom()));
    entity.setLwTideTo(isEmpty(record.getLwTideTo()) ? null : new BigDecimal(record.getLwTideTo()));
    entity.setHwTideTimeFrom(
        isEmpty(record.getHwTideTimeFrom())
            ? null
            : LocalTime.from(dtf.parse(record.getHwTideTimeFrom())));
    entity.setHwTideTimeTo(
        isEmpty(record.getHwTideTimeTo())
            ? null
            : LocalTime.from(dtf.parse(record.getHwTideTimeTo())));
    entity.setLwTideTimeFrom(
        isEmpty(record.getLwTideTimeFrom())
            ? null
            : LocalTime.from(dtf.parse(record.getLwTideTimeFrom())));
    entity.setLwTideTimeTo(
        isEmpty(record.getLwTideTimeTo())
            ? null
            : LocalTime.from(dtf.parse(record.getLwTideTimeTo())));
    this.buidlSynopticalTableVesselData(entity, record);
    this.buildSynopticalTableEtaEtdActuals(entity, record);
    return entity;
  }

  /**
   * Set vessel particular data in synoptical table
   *
   * @param entity
   * @param record
   */
  private void buidlSynopticalTableVesselData(SynopticalTable entity, SynopticalRecord record) {
    entity.setOthersPlanned(
        isEmpty(record.getOthersPlanned()) ? null : new BigDecimal(record.getOthersPlanned()));
    entity.setOthersActual(
        isEmpty(record.getOthersActual()) ? null : new BigDecimal(record.getOthersActual()));
    entity.setConstantPlanned(
        isEmpty(record.getConstantPlanned()) ? null : new BigDecimal(record.getConstantPlanned()));
    entity.setConstantActual(
        isEmpty(record.getConstantActual()) ? null : new BigDecimal(record.getConstantActual()));
    entity.setDeadWeightPlanned(
        isEmpty(record.getTotalDwtPlanned()) ? null : new BigDecimal(record.getTotalDwtPlanned()));
    entity.setDeadWeightActual(
        isEmpty(record.getTotalDwtActual()) ? null : new BigDecimal(record.getTotalDwtActual()));
    entity.setDisplacementPlanned(
        isEmpty(record.getDisplacementPlanned())
            ? null
            : new BigDecimal(record.getDisplacementPlanned()));
    entity.setDisplacementActual(
        isEmpty(record.getDisplacementActual())
            ? null
            : new BigDecimal(record.getDisplacementActual()));
  }

  /**
   * build eta etd actuals
   *
   * @param entity
   * @param record
   */
  public void buildSynopticalTableEtaEtdActuals(SynopticalTable entity, SynopticalRecord record) {
    LocalDateTime etaEtdActual =
        isEmpty(record.getEtaEtdActual())
            ? null
            : LocalDateTime.from(
                DateTimeFormatter.ofPattern(ETA_ETD_FORMAT).parse(record.getEtaEtdActual()));
    if (SYNOPTICAL_TABLE_OP_TYPE_ARRIVAL.equals(entity.getOperationType())) {
      entity.setEtaActual(etaEtdActual);
    } else {
      entity.setEtdActual(etaEtdActual);
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
            request,
            synopticalTableList,
            this.getSynopticalTablePortDetails(synopticalTableList),
            this.getSynopticalTablePortRotations(loadableStudyOpt.get()),
            loadableStudyOpt.get(),
            sortedTankList,
            vesselReply.getVesselLoadableQuantityDetails(),
            replyBuilder);
      }
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS));
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when fetching synoptical table", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(e.getCode())
              .setMessage(e.getMessage())
              .setStatus(FAILED)
              .build());
    } catch (Exception e) {
      log.error("Exception when fetching synoptical table", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Exception when fetching synoptical table")
              .setStatus(FAILED)
              .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
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
    PortReply portReply = this.getPortInfo(portReqBuilder.build());
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
  private List<LoadableStudyPortRotation> getSynopticalTablePortRotations(
      LoadableStudy loadableStudy) {
    return this.loadableStudyPortRotationRepository.findByLoadableStudyAndIsActiveOrderByPortOrder(
        loadableStudy, true);
  }

  /**
   * Build Synoptical records for synoptical table
   *
   * @param request
   * @param synopticalTableList
   * @param portReply
   * @param vesselLoadableQuantityDetails
   * @param vesselReply
   * @param vesselReply
   * @param replyBuilder
   */
  private void buildSynopticalTableReply(
      SynopticalTableRequest request,
      List<SynopticalTable> synopticalTableList,
      PortReply portReply,
      List<LoadableStudyPortRotation> portRotations,
      LoadableStudy loadableStudy,
      List<VesselTankDetail> sortedTankList,
      VesselLoadableQuantityDetails vesselLoadableQuantityDetails,
      SynopticalTableReply.Builder replyBuilder) {
    if (!CollectionUtils.isEmpty(synopticalTableList)) {
      Long firstPortId = portRotations.get(0).getPortXId();
      // first port arrival condition data will be same as the data in obq
      List<OnBoardQuantity> obqEntities =
          this.onBoardQuantityRepository.findByLoadableStudyAndPortIdAndIsActive(
              loadableStudy, firstPortId, true);
      // fething entire ohq entities based on loadable study
      List<OnHandQuantity> ohqEntities =
          this.onHandQuantityRepository.findByLoadableStudyAndIsActive(loadableStudy, true);
      List<SynopticalRecord> records = new ArrayList<>();
      List<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails> cargoDetails = null;
      List<LoadablePlanStowageBallastDetails> ballastDetails = new ArrayList<>();
      // if there is loadable pattern selected, fetch corresponding loadicator and
      // ballast data of
      // all ports
      if (request.getLoadablePatternId() > 0) {
        cargoDetails =
            this.loadablePatternCargoDetailsRepository.findByLoadablePatternIdAndIsActive(
                request.getLoadablePatternId(), true);
        ballastDetails.addAll(
            this.loadablePlanStowageBallastDetailsRepository.findByLoadablePatternIdAndIsActive(
                request.getLoadablePatternId(), true));
      }
      for (SynopticalTable synopticalEntity : synopticalTableList) {
        SynopticalRecord.Builder builder = SynopticalRecord.newBuilder();
        this.buildSynopticalRecord(synopticalEntity, builder, portReply);
        // set eta/etd estamted values from port rotation table
        this.setSynopticalEtaEtdEstimated(synopticalEntity, builder, portRotations);
        this.setSynopticalCargoDetails(
            request,
            cargoDetails,
            obqEntities,
            synopticalEntity,
            builder,
            sortedTankList,
            firstPortId,
            loadableStudy.getVoyage());
        this.setSynopticalOhqData(ohqEntities, synopticalEntity, builder, sortedTankList);
        this.setSynopticalTableVesselParticulars(
            synopticalEntity, builder, vesselLoadableQuantityDetails);
        if (request.getLoadablePatternId() > 0) {
          this.setSynopticalTableLoadicatorData(request.getLoadablePatternId(), builder);
          this.setBallastDetails(
              synopticalEntity,
              builder,
              ballastDetails,
              sortedTankList,
              request.getLoadablePatternId());
        }
        records.add(builder.build());
      }
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
   * @param sortedTankList
   * @param paatternId
   */
  private void setBallastDetails(
      SynopticalTable synopticalEntity,
      com.cpdss.common.generated.LoadableStudy.SynopticalRecord.Builder builder,
      List<LoadablePlanStowageBallastDetails> ballastDetails,
      List<VesselTankDetail> sortedTankList,
      Long paatternId) {
    List<LoadablePlanStowageBallastDetails> portBallastList =
        ballastDetails.stream()
            .filter(
                bd ->
                    synopticalEntity.getPortXid().equals(bd.getPortXId())
                        && synopticalEntity.getOperationType().equals(bd.getOperationType()))
            .collect(Collectors.toList());
    if (null != portBallastList && !portBallastList.isEmpty()) {
      for (VesselTankDetail tank : sortedTankList) {
        if (!BALLAST_TANK_CATEGORY_ID.equals(tank.getTankCategoryId())) {
          continue;
        }
        SynopticalBallastRecord.Builder ballastBuilder = SynopticalBallastRecord.newBuilder();
        ballastBuilder.setTankId(tank.getTankId());
        ballastBuilder.setTankName(tank.getShortName());
        ballastBuilder.setCapacity(tank.getFullCapacityCubm());
        Optional<LoadablePlanStowageBallastDetails> tankBallastDetail =
            portBallastList.stream()
                .filter(b -> b.getTankXId().longValue() == tank.getTankId())
                .findAny();
        if (tankBallastDetail.isPresent()) {
          LoadablePlanStowageBallastDetails ballast = tankBallastDetail.get();
          Optional.ofNullable(ballast.getQuantity())
              .ifPresent(item -> ballastBuilder.setPlannedWeight(valueOf(item)));
          Optional.ofNullable(ballast.getActualQuantity())
              .ifPresent(item -> ballastBuilder.setActualWeight(valueOf(item)));
        } else {
          log.info(
              "Ballast details not available for the tank: {}, pattern: {}",
              tank.getTankId(),
              paatternId);
        }
        builder.addBallast(ballastBuilder.build());
      }
    }
  }

  /**
   * Set loadicator data in synoptical table record
   *
   * @param synopticalEntity
   * @param builder
   */
  private void setSynopticalTableLoadicatorData(
      Long loadablePatternId,
      com.cpdss.common.generated.LoadableStudy.SynopticalRecord.Builder builder) {
    SynopticalTableLoadicatorData loadicatorData =
        this.synopticalTableLoadicatorDataRepository.findByLoadablePatternIdAndIsActive(
            loadablePatternId, true);
    if (null != loadicatorData) {
      com.cpdss.common.generated.LoadableStudy.SynopticalTableLoadicatorData.Builder dataBuilder =
          com.cpdss.common.generated.LoadableStudy.SynopticalTableLoadicatorData.newBuilder();
      Optional.ofNullable(loadicatorData.getBlindSector())
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
      Optional.ofNullable(loadicatorData.getBallastActual())
          .ifPresent(item -> builder.setBallastActual(valueOf(item)));
    } else {
      log.info("loadicator data does not exist for loadable patter with id {}", loadablePatternId);
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
      hog = loadicatorData.getHog();
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
      }
      builder.addOhq(ohqBuilder.build());
    }
  }

  /**
   * Set cargo details on synoptical record
   *
   * @param request
   * @param obqEntities
   * @param synopticalEntity
   * @param builder
   * @param firstPortId
   * @param voyage
   * @param vesselReply
   * @param loadableStudy
   */
  private void setSynopticalCargoDetails(
      SynopticalTableRequest request,
      List<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails> cargoDetails,
      List<OnBoardQuantity> obqEntities,
      SynopticalTable synopticalEntity,
      SynopticalRecord.Builder builder,
      List<VesselTankDetail> sortedTankList,
      Long firstPortId,
      Voyage voyage) {
    BigDecimal cargoActualTotal = BigDecimal.ZERO;
    BigDecimal cargoPlannedTotal = BigDecimal.ZERO;
    List<CargoHistory> cargoHistories = null;
    List<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails> portSpecificCargoDetails =
        new ArrayList<>();
    if (null != cargoDetails) {
      portSpecificCargoDetails.addAll(
          cargoDetails.stream()
              .filter(
                  det ->
                      det.getPortId().equals(synopticalEntity.getPortXid())
                          && det.getOperationType().equals(synopticalEntity.getOperationType()))
              .collect(Collectors.toList()));
    }
    for (VesselTankDetail tank : sortedTankList) {
      if (!CARGO_TANK_CATEGORIES.contains(tank.getTankCategoryId())) {
        continue;
      }
      SynopticalCargoRecord.Builder cargoBuilder = SynopticalCargoRecord.newBuilder();
      cargoBuilder.setTankId(tank.getTankId());
      cargoBuilder.setTankName(tank.getShortName());
      cargoBuilder.setCapacity(tank.getFullCapacityCubm());
      // first port arrival data will be same as obq data
      // if no obq data is found, previos voyage data has to be fetched
      if (synopticalEntity.getPortXid().equals(firstPortId)
          && synopticalEntity.getOperationType().equals(SYNOPTICAL_TABLE_OP_TYPE_ARRIVAL)) {
        this.buildObqDataForSynopticalTable(
            tank, cargoHistories, obqEntities, cargoBuilder, voyage);
        if (!isEmpty(cargoBuilder.getActualWeight())) {
          cargoActualTotal = cargoActualTotal.add(new BigDecimal(cargoBuilder.getActualWeight()));
        }
        if (!isEmpty(cargoBuilder.getPlannedWeight())) {
          cargoPlannedTotal =
              cargoPlannedTotal.add(new BigDecimal(cargoBuilder.getPlannedWeight()));
        }
      } else if (request.getLoadablePatternId() > 0) {
        Optional<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails> tankDataOpt =
            portSpecificCargoDetails.stream()
                .filter(cargo -> cargo.getTankId().equals(tank.getTankId()))
                .findAny();
        if (tankDataOpt.isPresent()) {
          Optional.ofNullable(tankDataOpt.get().getPlannedQuantity())
              .ifPresent(item -> cargoBuilder.setPlannedWeight(valueOf(item)));
          Optional.ofNullable(tankDataOpt.get().getActualQuantity())
              .ifPresent(item -> cargoBuilder.setActualWeight(valueOf(item)));
          // attributes for landing page
          Optional.ofNullable(tankDataOpt.get().getCargoId()).ifPresent(cargoBuilder::setCargoId);
          Optional.ofNullable(tankDataOpt.get().getAbbreviation())
              .ifPresent(cargoBuilder::setCargoAbbreviation);
          Optional.ofNullable(tankDataOpt.get().getColorCode())
              .ifPresent(cargoBuilder::setColorCode);
          Optional.ofNullable(tankDataOpt.get().getCorrectedUllage())
              .ifPresent(ullage -> cargoBuilder.setCorrectedUllage(valueOf(ullage)));
        }
      }
      builder.addCargo(cargoBuilder.build());
    }
    builder.setCargoActualTotal(valueOf(cargoActualTotal));
    builder.setCargoPlannedTotal(valueOf(cargoPlannedTotal));
  }

  private void buildObqDataForSynopticalTable(
      VesselTankDetail tank,
      List<CargoHistory> cargoHistories,
      List<OnBoardQuantity> obqEntities,
      SynopticalCargoRecord.Builder cargoBuilder,
      Voyage voyage) {

    Optional<OnBoardQuantity> obqOpt =
        obqEntities.stream().filter(obq -> obq.getTankId().equals(tank.getTankId())).findAny();
    if (obqOpt.isPresent()) {
      OnBoardQuantity obqEntity = obqOpt.get();
      if (null != obqEntity.getActualArrivalWeight()) {
        cargoBuilder.setActualWeight(valueOf(obqEntity.getActualArrivalWeight()));
      }
      if (null != obqEntity.getPlannedArrivalWeight()) {
        cargoBuilder.setPlannedWeight(valueOf(obqEntity.getPlannedArrivalWeight()));
      }
    } else {
      // data has to be populated from previous voyage - cargo history table
      // lazy loading the cargo history
      if (null == cargoHistories) {
        cargoHistories = this.findCargoHistoryForPrvsVoyage(voyage);
      }
      Optional<CargoHistory> cargoHistoryOpt =
          cargoHistories.stream().filter(e -> e.getTankId().equals(tank.getTankId())).findAny();
      if (cargoHistoryOpt.isPresent()) {
        CargoHistory dto = cargoHistoryOpt.get();
        Optional.ofNullable(dto.getQuantity())
            .ifPresent(item -> cargoBuilder.setPlannedWeight(valueOf(item)));
      }
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
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    if (portReply != null) {
      portReply.getPortsList().stream()
          .filter(p -> synopticalEntity.getPortXid().equals(p.getId()))
          .findAny()
          .ifPresent(
              port -> {
                this.setSynopticalPortValues(port, builder);
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
    Optional.ofNullable(synopticalEntity.getTimeOfSunrise())
        .ifPresent(time -> builder.setTimeOfSunrise(timeFormatter.format(time)));
    Optional.ofNullable(synopticalEntity.getTimeOfSunSet())
        .ifPresent(time -> builder.setTimeOfSunset(timeFormatter.format(time)));
    // If specific port related data is available in synoptical table then replace the port master
    // value
    Optional.ofNullable(synopticalEntity.getSpecificGravity())
        .ifPresent(sg -> builder.setSpecificGravity(valueOf(sg)));
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
    Optional.ofNullable(synopticalEntity.getHwTideFrom())
        .ifPresent(hwTideFrom -> builder.setHwTideFrom(String.valueOf(hwTideFrom)));
    Optional.ofNullable(synopticalEntity.getHwTideTimeFrom())
        .ifPresent(
            hwTideTimeFrom -> builder.setHwTideTimeFrom(timeFormatter.format(hwTideTimeFrom)));
    Optional.ofNullable(synopticalEntity.getHwTideTo())
        .ifPresent(hwTideTo -> builder.setHwTideTo(String.valueOf(hwTideTo)));
    Optional.ofNullable(synopticalEntity.getHwTideTimeTo())
        .ifPresent(hwTideTimeTo -> builder.setHwTideTimeTo(timeFormatter.format(hwTideTimeTo)));
    Optional.ofNullable(synopticalEntity.getLwTideFrom())
        .ifPresent(lwTideFrom -> builder.setLwTideFrom(String.valueOf(lwTideFrom)));
    Optional.ofNullable(synopticalEntity.getLwTideTimeFrom())
        .ifPresent(
            lwTideTimeFrom -> builder.setLwTideTimeFrom(timeFormatter.format(lwTideTimeFrom)));
    Optional.ofNullable(synopticalEntity.getLwTideTo())
        .ifPresent(lwTideTo -> builder.setLwTideTo(String.valueOf(lwTideTo)));
    Optional.ofNullable(synopticalEntity.getLwTideTimeTo())
        .ifPresent(lwTideTimeTo -> builder.setLwTideTimeTo(timeFormatter.format(lwTideTimeTo)));
    if (null != synopticalEntity.getEtaActual()) {
      builder.setEtaEtdActual(formatter.format(synopticalEntity.getEtaActual()));
    } else if (null != synopticalEntity.getEtdActual()) {
      builder.setEtaEtdActual(formatter.format(synopticalEntity.getEtdActual()));
    }
  }

  /**
   * * Set port values to synoptical response
   *
   * @param port
   * @param builder
   */
  private void setSynopticalPortValues(
      PortDetail port, com.cpdss.common.generated.LoadableStudy.SynopticalRecord.Builder builder) {
    builder.setPortName(port.getName());
    builder.setSpecificGravity(port.getWaterDensity());
    builder.setHwTideFrom(port.getHwTideFrom());
    builder.setHwTideTo(port.getHwTideTo());
    builder.setLwTideFrom(port.getLwTideFrom());
    builder.setLwTideTo(port.getLwTideTo());
    if (!isEmpty(port.getHwTideTimeFrom())) {
      builder.setHwTideTimeFrom(port.getHwTideTimeFrom());
    }
    if (!isEmpty(port.getHwTideTimeTo())) {
      builder.setHwTideTimeTo(port.getHwTideTimeTo());
    }
    if (!isEmpty(port.getLwTideTimeFrom())) {
      builder.setLwTideTimeFrom(port.getLwTideTimeFrom());
    }
    if (!isEmpty(port.getLwTideTimeTo())) {
      builder.setLwTideTimeTo(port.getLwTideTimeTo());
    }
    if (!isEmpty(port.getSunriseTime())) {
      builder.setTimeOfSunrise(port.getSunriseTime());
    }
    if (!isEmpty(port.getSunsetTime())) {
      builder.setTimeOfSunset(port.getSunsetTime());
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
      if (SYNOPTICAL_TABLE_OP_TYPE_ARRIVAL.equals(synopticalEntity.getOperationType())) {
        builder.setEtaEtdEstimated(formatter.format(portRotation.get().getEta()));
      } else {
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

  private VesselReply getTanks(Long vesselId, List<Long> tankCategory)
      throws GenericServiceException {
    VesselRequest.Builder vesselGrpcRequest = VesselRequest.newBuilder();
    vesselGrpcRequest.setVesselId(vesselId);
    vesselGrpcRequest.addAllTankCategories(tankCategory);
    return this.getVesselTanks(vesselGrpcRequest.build());
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

        buildLoadablePlanDetails(loadablePatternOpt, replyBuilder);
      }
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when fetching loadable study - port data", e);
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(FAILED));
    } catch (Exception e) {
      log.error("Exception when getLoadablePlanDetails ", e);
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(FAILED));
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * void
   *
   * @param replyBuilder
   * @param loadablePatternOpt
   */
  private void buildLoadablePlanDetails(
      Optional<LoadablePattern> loadablePatternOpt,
      com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply.Builder replyBuilder)
      throws GenericServiceException {

    VesselReply vesselReplyCargoBallastTanks =
        this.getTanks(
            loadablePatternOpt.get().getLoadableStudy().getVesselXId(),
            CARGO_BALLAST_TANK_CATEGORIES);
    if (!SUCCESS.equals(vesselReplyCargoBallastTanks.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Failed to fetch vessel particualrs for cargo tanks",
          vesselReplyCargoBallastTanks.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(
              Integer.valueOf(vesselReplyCargoBallastTanks.getResponseStatus().getCode())));
    }

    List<LoadablePlanQuantity> loadablePlanQuantities =
        loadablePlanQuantityRepository.findByLoadablePatternAndIsActive(
            loadablePatternOpt.get(), true);
    buildLoadablePlanQuantity(loadablePlanQuantities, replyBuilder);

    List<LoadablePlanCommingleDetails> loadablePlanCommingleDetails =
        loadablePlanCommingleDetailsRepository.findByLoadablePatternAndIsActive(
            loadablePatternOpt.get(), true);
    buildLoadablePlanCommingleDetails(loadablePlanCommingleDetails, replyBuilder);

    List<LoadablePlanStowageDetails> loadablePlanStowageDetails =
        loadablePlanStowageDetailsRespository.findByLoadablePatternAndIsActive(
            loadablePatternOpt.get(), true);
    buildLoadablePlanStowageCargoDetails(loadablePlanStowageDetails, replyBuilder);

    replyBuilder.addAllTanks(
        this.groupTanks(
            vesselReplyCargoBallastTanks.getVesselTanksList().stream()
                .filter(tankList -> CARGO_TANK_CATEGORIES.contains(tankList.getTankCategoryId()))
                .collect(Collectors.toList())));

    buildBallastTankLayout(
        vesselReplyCargoBallastTanks.getVesselTanksList().stream()
            .filter(tankList -> BALLAST_TANK_CATEGORIES.contains(tankList.getTankCategoryId()))
            .collect(Collectors.toList()),
        replyBuilder);
    List<LoadablePlanBallastDetails> loadablePlanBallastDetails =
        loadablePlanBallastDetailsRepository.findByLoadablePatternAndIsActive(
            loadablePatternOpt.get(), true);
    buildBallastGridDetails(loadablePlanBallastDetails, replyBuilder);

    List<LoadablePlanComments> loadablePlanComments =
        loadablePlanCommentsRepository.findByLoadablePatternAndIsActiveOrderByIdDesc(
            loadablePatternOpt.get(), true);

    buildCommentDetails(loadablePlanComments, replyBuilder);

    buildLoadablePlanDetails(replyBuilder, loadablePatternOpt.get());

    replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
  }

  /**
   * @param replyBuilder
   * @param loadablePattern void
   */
  private void buildLoadablePlanDetails(
      com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply.Builder replyBuilder,
      LoadablePattern loadablePattern) {
    replyBuilder.setId(loadablePattern.getId());
    replyBuilder.setCaseNumber(loadablePattern.getCaseNumber().toString());
    replyBuilder.setDate(
        DateTimeFormatter.ofPattern(CREATED_DATE_FORMAT).format(loadablePattern.getCreatedDate()));
    replyBuilder.setVoyageNumber(loadablePattern.getLoadableStudy().getVoyage().getVoyageNo());
  }

  /**
   * @param loadablePlanComments
   * @param replyBuilder void
   */
  private void buildCommentDetails(
      List<LoadablePlanComments> loadablePlanComments,
      com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply.Builder replyBuilder) {

    loadablePlanComments.forEach(
        lpc -> {
          com.cpdss.common.generated.LoadableStudy.LoadablePlanComments.Builder builder =
              com.cpdss.common.generated.LoadableStudy.LoadablePlanComments.newBuilder();
          Optional.ofNullable(lpc.getId()).ifPresent(builder::setId);
          Optional.ofNullable(lpc.getComments()).ifPresent(builder::setComment);
          Optional.ofNullable(
                  DateTimeFormatter.ofPattern(DATE_FORMAT).format(lpc.getCreatedDateTime()))
              .ifPresent(builder::setDataAndTime);
          builder.setUserName("Uttam Kumar"); // ToDo - replace it with the value taken from cache
          replyBuilder.addLoadablePlanComments(builder);
        });
  }

  /**
   * @param loadablePlanBallastDetails
   * @param replyBuilder void
   */
  private void buildBallastGridDetails(
      List<LoadablePlanBallastDetails> loadablePlanBallastDetails,
      com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply.Builder replyBuilder) {
    loadablePlanBallastDetails.forEach(
        lpbd -> {
          com.cpdss.common.generated.LoadableStudy.LoadablePlanBallastDetails.Builder builder =
              com.cpdss.common.generated.LoadableStudy.LoadablePlanBallastDetails.newBuilder();
          Optional.ofNullable(lpbd.getId()).ifPresent(builder::setId);
          Optional.ofNullable(lpbd.getCorrectedLevel()).ifPresent(builder::setCorrectedLevel);
          Optional.ofNullable(lpbd.getCorrectionFactor()).ifPresent(builder::setCorrectionFactor);
          Optional.ofNullable(lpbd.getCubicMeter()).ifPresent(builder::setCubicMeter);
          Optional.ofNullable(lpbd.getInertia()).ifPresent(builder::setInertia);
          Optional.ofNullable(lpbd.getLcg()).ifPresent(builder::setLcg);
          Optional.ofNullable(lpbd.getMetricTon()).ifPresent(builder::setMetricTon);
          Optional.ofNullable(lpbd.getPercentage()).ifPresent(builder::setPercentage);
          Optional.ofNullable(lpbd.getRdgLevel()).ifPresent(builder::setRdgLevel);
          Optional.ofNullable(lpbd.getSg()).ifPresent(builder::setSg);
          Optional.ofNullable(lpbd.getTankId()).ifPresent(builder::setTankId);
          Optional.ofNullable(lpbd.getTcg()).ifPresent(builder::setTcg);
          Optional.ofNullable(lpbd.getVcg()).ifPresent(builder::setVcg);
          Optional.ofNullable(lpbd.getTankName()).ifPresent(builder::setTankName);
          Optional.ofNullable(lpbd.getColorCode()).ifPresent(builder::setColorCode);
          replyBuilder.addLoadablePlanBallastDetails(builder);
        });
  }

  /**
   * @param vesselReplyBallastTanks
   * @param replyBuilder void
   */
  private void buildBallastTankLayout(
      List<VesselTankDetail> vesselTankDetails,
      com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply.Builder replyBuilder) {

    List<VesselTankDetail> frontBallastTanks = new ArrayList<>();
    List<VesselTankDetail> centerBallestTanks = new ArrayList<>();
    List<VesselTankDetail> rearBallastTanks = new ArrayList<>();
    frontBallastTanks.addAll(
        vesselTankDetails.stream()
            .filter(tank -> BALLAST_FRONT_TANK.equals(tank.getTankPositionCategory()))
            .collect(Collectors.toList()));
    centerBallestTanks.addAll(
        vesselTankDetails.stream()
            .filter(tank -> BALLAST_CENTER_TANK.equals(tank.getTankPositionCategory()))
            .collect(Collectors.toList()));

    rearBallastTanks.addAll(
        vesselTankDetails.stream()
            .filter(tank -> BALLAST_REAR_TANK.equals(tank.getTankPositionCategory()))
            .collect(Collectors.toList()));

    replyBuilder.addAllBallastFrontTanks(this.groupTanks(frontBallastTanks));
    replyBuilder.addAllBallastCenterTanks(this.groupTanks(centerBallestTanks));
    replyBuilder.addAllBallastRearTanks(this.groupTanks(rearBallastTanks));
  }

  /**
   * @param loadablePlanCommingleDetails
   * @param replyBuilder void
   */
  private void buildLoadablePlanCommingleDetails(
      List<LoadablePlanCommingleDetails> loadablePlanCommingleDetails,
      com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply.Builder replyBuilder) {
    loadablePlanCommingleDetails.forEach(
        lpcd -> {
          LoadableQuantityCommingleCargoDetails.Builder builder =
              LoadableQuantityCommingleCargoDetails.newBuilder();
          Optional.ofNullable(lpcd.getId()).ifPresent(builder::setId);
          Optional.ofNullable(lpcd.getApi()).ifPresent(builder::setApi);
          Optional.ofNullable(lpcd.getCargo1Abbreviation())
              .ifPresent(builder::setCargo1Abbreviation);
          Optional.ofNullable(lpcd.getCargo1Bbls60f()).ifPresent(builder::setCargo1Bbls60F);
          Optional.ofNullable(lpcd.getCargo1BblsDbs()).ifPresent(builder::setCargo1Bblsdbs);
          Optional.ofNullable(lpcd.getCargo1Kl()).ifPresent(builder::setCargo1KL);
          Optional.ofNullable(lpcd.getCargo1Lt()).ifPresent(builder::setCargo1LT);
          Optional.ofNullable(lpcd.getCargo1Mt()).ifPresent(builder::setCargo1MT);
          Optional.ofNullable(lpcd.getCargo1Percentage()).ifPresent(builder::setCargo1Percentage);
          Optional.ofNullable(lpcd.getCargo2Abbreviation())
              .ifPresent(builder::setCargo2Abbreviation);
          Optional.ofNullable(lpcd.getCargo2Bbls60f()).ifPresent(builder::setCargo2Bbls60F);
          Optional.ofNullable(lpcd.getCargo2BblsDbs()).ifPresent(builder::setCargo2Bblsdbs);
          Optional.ofNullable(lpcd.getCargo2Kl()).ifPresent(builder::setCargo2KL);
          Optional.ofNullable(lpcd.getCargo2Lt()).ifPresent(builder::setCargo2LT);
          Optional.ofNullable(lpcd.getCargo2Mt()).ifPresent(builder::setCargo2MT);
          Optional.ofNullable(lpcd.getCargo2Percentage()).ifPresent(builder::setCargo2Percentage);
          Optional.ofNullable(lpcd.getGrade()).ifPresent(builder::setGrade);
          Optional.ofNullable(lpcd.getQuantity()).ifPresent(builder::setQuantity);
          Optional.ofNullable(lpcd.getTankName()).ifPresent(builder::setTankName);
          Optional.ofNullable(lpcd.getTemperature()).ifPresent(builder::setTemp);
          replyBuilder.addLoadableQuantityCommingleCargoDetails(builder);
        });
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
          Optional.ofNullable(lpsd.getColorCode()).ifPresent(builder::setColorCode);
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
      SynopticalTableRequest request, StreamObserver<SynopticalTableReply> responseObserver) {
    SynopticalTableReply.Builder replyBuilder = SynopticalTableReply.newBuilder();
    try {
      Optional<LoadableStudy> loadableStudyOpt =
          this.loadableStudyRepository.findById(request.getLoadableStudyId());
      if (!loadableStudyOpt.isPresent()) {
        throw new GenericServiceException(
            "Loadable study does not exist", CommonErrorCodes.E_HTTP_BAD_REQUEST, null);
      }
      /**
       * updated the request object with confirmed loadable pattern id which is needed only for
       * voyage status initiated flow. In case this method is reused for any other flow then the
       * loadable pattern id should be updated accordingly using probably a flag to track initiated
       * flow
       */
      List<LoadablePattern> confirmedLoadablePatternList =
          loadablePatternRepository.findByVoyageAndLoadableStudyStatusAndIsActive(
              request.getVoyageId(), CONFIRMED_STATUS_ID, true);
      Optional<LoadablePattern> confirmedLoadablePattern =
          confirmedLoadablePatternList.stream().findFirst();
      if (!confirmedLoadablePattern.isPresent()) {
        throw new GenericServiceException(
            "Confirmed loadable pattern does not exist", CommonErrorCodes.E_HTTP_BAD_REQUEST, null);
      }
      Long confirmedLoadablePatternId = confirmedLoadablePattern.get().getId();
      SynopticalTableRequest.Builder requestBuilder = request.toBuilder();
      requestBuilder.setLoadablePatternId(confirmedLoadablePatternId);
      // fetching synoptical entity list
      List<SynopticalTable> synopticalTableList =
          this.synopticalTableRepository.findByLoadableStudyXIdAndIsActiveAndPortXid(
              request.getLoadableStudyId(), true, request.getPortId());
      if (!synopticalTableList.isEmpty()) {
        // fetching vessel tanks including ohq and cargo tanks
        VesselReply vesselReply =
            this.getSynopticalTableVesselData(request, loadableStudyOpt.get());
        // sorting the tanks based on tank display order from vessel tank table
        List<VesselTankDetail> sortedTankList = new ArrayList<>(vesselReply.getVesselTanksList());
        Collections.sort(
            sortedTankList, Comparator.comparing(VesselTankDetail::getTankDisplayOrder));
        buildSynopticalTableReply(
            requestBuilder.build(),
            synopticalTableList,
            this.getSynopticalTablePortDetails(synopticalTableList),
            this.getSynopticalTablePortRotations(loadableStudyOpt.get()),
            loadableStudyOpt.get(),
            sortedTankList,
            vesselReply.getVesselLoadableQuantityDetails(),
            replyBuilder);
        // build ballast tank details not available in synoptical
        List<VesselTankDetail> ballastTankList =
            sortedTankList.stream()
                .filter(tankList -> BALLAST_TANK_CATEGORIES.contains(tankList.getTankCategoryId()))
                .collect(Collectors.toList());
        List<VesselTankDetail> frontBallastTanks = new ArrayList<>();
        List<VesselTankDetail> centerBallastTanks = new ArrayList<>();
        List<VesselTankDetail> rearBallastTanks = new ArrayList<>();
        frontBallastTanks.addAll(
            ballastTankList.stream()
                .filter(tank -> BALLAST_FRONT_TANK.equals(tank.getTankPositionCategory()))
                .collect(Collectors.toList()));
        centerBallastTanks.addAll(
            ballastTankList.stream()
                .filter(tank -> BALLAST_CENTER_TANK.equals(tank.getTankPositionCategory()))
                .collect(Collectors.toList()));

        rearBallastTanks.addAll(
            ballastTankList.stream()
                .filter(tank -> BALLAST_REAR_TANK.equals(tank.getTankPositionCategory()))
                .collect(Collectors.toList()));

        replyBuilder.addAllBallastFrontTanks(this.groupTanks(frontBallastTanks));
        replyBuilder.addAllBallastCenterTanks(this.groupTanks(centerBallastTanks));
        replyBuilder.addAllBallastRearTanks(this.groupTanks(rearBallastTanks));
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

  /** Retrieves the synoptical port data for a portId */
  @Override
  public void getSynopticalPortDataByPortId(
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
          this.synopticalTableRepository.findByLoadableStudyXIdAndIsActiveAndPortXid(
              request.getLoadableStudyId(), true, request.getPortId());
      if (!synopticalTableList.isEmpty()) {
        buildSynopticalPortDataReplyByPortId(synopticalTableList, replyBuilder);
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

  private void buildSynopticalPortDataReplyByPortId(
      List<SynopticalTable> synopticalTableList,
      com.cpdss.common.generated.LoadableStudy.SynopticalTableReply.Builder replyBuilder) {
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

  @Transactional
  public void checkDuplicatedFromAndCloneEntity(LoadableStudyDetail request, LoadableStudy entity)
      throws GenericServiceException {
    if (0 != request.getDuplicatedFromId()) {
      try {

        List<CargoNomination> cargoNominationList =
            this.cargoNominationRepository.findByLoadableStudyXIdAndIsActive(
                request.getDuplicatedFromId(), true);

        if (!cargoNominationList.isEmpty()) {
          List<CargoNomination> crgoNominationList = new ArrayList<CargoNomination>();
          cargoNominationList.forEach(
              cargoNomination -> {
                CargoNomination crgoNomination = new CargoNomination();
                List<CargoNominationPortDetails> oldCargoNominationPortDetails =
                    this.cargoNominationOperationDetailsRepository
                        .findByCargoNominationnAndIsActive(cargoNomination, true);

                BeanUtils.copyProperties(cargoNomination, crgoNomination);
                crgoNomination.setLoadableStudyXId(entity.getId());
                crgoNomination.setId(null);
                crgoNominationList.add(crgoNomination);
                crgoNomination.setCargoNominationPortDetails(
                    new HashSet<CargoNominationPortDetails>());
                oldCargoNominationPortDetails.forEach(
                    oldCargo -> {
                      CargoNominationPortDetails cargoNominationPortDetails =
                          new CargoNominationPortDetails();
                      BeanUtils.copyProperties(oldCargo, cargoNominationPortDetails);
                      cargoNominationPortDetails.setId(null);
                      cargoNominationPortDetails.setCargoNomination(crgoNomination);
                      crgoNomination
                          .getCargoNominationPortDetails()
                          .add(cargoNominationPortDetails);
                    });
              });
          this.cargoNominationRepository.saveAll(crgoNominationList);
        }

        List<LoadableStudyPortRotation> loadableStudyPortRotationList =
            this.loadableStudyPortRotationRepository.findByLoadableStudyAndIsActive(
                request.getDuplicatedFromId(), true);
        if (!loadableStudyPortRotationList.isEmpty()) {
          List<LoadableStudyPortRotation> LoadableStudyPorts =
              new ArrayList<LoadableStudyPortRotation>();

          loadableStudyPortRotationList.forEach(
              loadableStudyPortRotation -> {
                entityManager.detach(loadableStudyPortRotation);
                loadableStudyPortRotation.setId(null);
                loadableStudyPortRotation.setLoadableStudy(entity);
                LoadableStudyPorts.add(loadableStudyPortRotation);
              });
          this.loadableStudyPortRotationRepository.saveAll(LoadableStudyPorts);
        }

        Optional<LoadableStudy> loadableStudyOpt =
            this.loadableStudyRepository.findByIdAndIsActive(request.getDuplicatedFromId(), true);
        if (!loadableStudyOpt.isPresent()) {
          throw new GenericServiceException(
              "Loadable study does not exist",
              CommonErrorCodes.E_HTTP_BAD_REQUEST,
              HttpStatusCode.BAD_REQUEST);
        }

        List<OnHandQuantity> onHandQuantityList =
            this.onHandQuantityRepository.findByLoadableStudyAndIsActive(
                loadableStudyOpt.get(), true);
        if (!onHandQuantityList.isEmpty()) {
          List<OnHandQuantity> OnHandQuantities = new ArrayList<OnHandQuantity>();

          onHandQuantityList.forEach(
              onHandQuantity -> {
                entityManager.detach(onHandQuantity);
                onHandQuantity.setId(null);
                onHandQuantity.setLoadableStudy(entity);
                OnHandQuantities.add(onHandQuantity);
              });
          this.onHandQuantityRepository.saveAll(OnHandQuantities);
        }
        List<OnBoardQuantity> onBoardQuantityList =
            this.onBoardQuantityRepository.findByLoadableStudyAndIsActive(
                loadableStudyOpt.get(), true);
        if (!onBoardQuantityList.isEmpty()) {
          List<OnBoardQuantity> OnBoardQuantities = new ArrayList<OnBoardQuantity>();

          onBoardQuantityList.forEach(
              onBoardQuantity -> {
                entityManager.detach(onBoardQuantity);
                onBoardQuantity.setId(null);
                onBoardQuantity.setLoadableStudy(entity);
                OnBoardQuantities.add(onBoardQuantity);
              });
          this.onBoardQuantityRepository.saveAll(OnBoardQuantities);
        }

        List<LoadableQuantity> loadableQuantityList =
            this.loadableQuantityRepository.findByLoadableStudyXIdAndIsActive(
                request.getDuplicatedFromId(), true);
        if (!loadableQuantityList.isEmpty()) {
          List<LoadableQuantity> loadableQuantities = new ArrayList<LoadableQuantity>();

          loadableQuantityList.forEach(
              loadableQuantity -> {
                System.out.println(loadableQuantity.getId());
                entityManager.detach(loadableQuantity);
                loadableQuantity.setId(null);
                loadableQuantity.setLoadableStudyXId(entity);
                loadableQuantities.add(loadableQuantity);
              });
          this.loadableQuantityRepository.saveAll(loadableQuantities);
        }

        List<com.cpdss.loadablestudy.entity.CommingleCargo> CommingleCargoList =
            this.commingleCargoRepository.findByLoadableStudyXIdAndIsActive(
                request.getDuplicatedFromId(), true);
        if (!CommingleCargoList.isEmpty()) {
          List<com.cpdss.loadablestudy.entity.CommingleCargo> CommingleCargos =
              new ArrayList<com.cpdss.loadablestudy.entity.CommingleCargo>();

          CommingleCargoList.forEach(
              CommingleCargo -> {
                entityManager.detach(CommingleCargo);
                CommingleCargo.setId(null);
                CommingleCargo.setLoadableStudyXId(entity.getId());
                CommingleCargos.add(CommingleCargo);
              });
          this.commingleCargoRepository.saveAll(CommingleCargos);
        }

        List<SynopticalTable> synopticalTableList =
            this.synopticalTableRepository.findByLoadableStudyXIdAndIsActive(
                request.getDuplicatedFromId(), true);

        if (!synopticalTableList.isEmpty()) {
          List<SynopticalTable> SynopticalTables = new ArrayList<SynopticalTable>();

          synopticalTableList.forEach(
              synopticalTable -> {
                entityManager.detach(synopticalTable);
                synopticalTable.setId(null);
                synopticalTable.setLoadableStudyXId(entity.getId());
                SynopticalTables.add(synopticalTable);
              });
          this.synopticalTableRepository.saveAll(SynopticalTables);
        }

        List<LoadableStudyAttachments> loadableStudyAttachmentsList =
            this.loadableStudyAttachmentsRepository.findByLoadableStudyXIdAndIsActive(
                request.getDuplicatedFromId(), true);

        if (!loadableStudyAttachmentsList.isEmpty()) {
          List<LoadableStudyAttachments> loadableStudyAttachments =
              new ArrayList<LoadableStudyAttachments>();

          loadableStudyAttachmentsList.forEach(
              loadableStudyAttachment -> {
                entityManager.detach(loadableStudyAttachment);
                loadableStudyAttachment.setId(null);
                loadableStudyAttachment.setLoadableStudy(entity);
                loadableStudyAttachments.add(loadableStudyAttachment);
              });
          this.loadableStudyAttachmentsRepository.saveAll(loadableStudyAttachments);
        }

      } catch (Exception e) {
        log.error("GenericServiceException in checkDuplicatedFromAndCloneEntity", e);
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        throw new GenericServiceException(
            "Failed to save duplicate entries", CommonErrorCodes.E_GEN_INTERNAL_ERR, null);
      }
    }
  }

  @Override
  public void downloadLoadableStudyAttachment(
      LoadableStudyAttachmentRequest request,
      StreamObserver<LoadableStudyAttachmentReply> responseObserver) {
    LoadableStudyAttachmentReply.Builder builder = LoadableStudyAttachmentReply.newBuilder();
    try {

      LoadableStudyAttachments attachment =
          loadableStudyAttachmentsRepository.findByIdAndLoadableStudyXIdAndIsActive(
              request.getFileId(), request.getLoadableStudyId(), true);
      if (null == attachment) {
        throw new GenericServiceException(
            "Attachment does not exist",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }

      String FILE_PATH =
          this.rootFolder + attachment.getFilePath() + attachment.getUploadedFileName();

      builder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
      builder.setFilePath(FILE_PATH);

    } catch (GenericServiceException e) {
      log.error("GenericServiceException in downloadLoadableStudyAttachment", e);
      builder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setStatus(FAILED)
              .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST)
              .build());
    } catch (Exception e) {
      log.error("Exception in downloadLoadableStudyAttachment", e);
      builder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setStatus(FAILED)
              .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST)
              .build());
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  /*
   * To save all port rotation details
   */
  @Override
  public void saveLoadableStudyPortRotationList(
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
      // validates the input port rotation list for valid ids
      List<LoadableStudyPortRotation> existingPortRotationList = new ArrayList<>();
      if (!CollectionUtils.isEmpty(request.getPortRotationDetailsList())) {
        for (PortRotationDetail requestPortRotation : request.getPortRotationDetailsList()) {
          Optional<LoadableStudyPortRotation> portRotation =
              this.loadableStudyPortRotationRepository.findById(requestPortRotation.getId());
          if (!portRotation.isPresent()) {
            throw new GenericServiceException(
                "Port rotation does not exist",
                CommonErrorCodes.E_HTTP_BAD_REQUEST,
                HttpStatusCode.BAD_REQUEST);
          }
          existingPortRotationList.add(portRotation.get());
        }
      }
      createPortRotationEntityList(existingPortRotationList, request);
      if (!CollectionUtils.isEmpty(existingPortRotationList)) {
        this.loadableStudyPortRotationRepository.saveAll(existingPortRotationList);
      }
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when saveLoadableStudyPortRotationList", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(e.getCode())
              .setMessage(e.getMessage())
              .setStatus(FAILED)
              .build());
    } catch (Exception e) {
      log.error("Exception when saveLoadableStudyPortRotationList", e);
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

  /*
   * builds list of port rotation entities for bulk save
   */
  private List<LoadableStudyPortRotation> createPortRotationEntityList(
      List<LoadableStudyPortRotation> existingPortRotationList,
      PortRotationRequest portRotationRequest) {
    if (!CollectionUtils.isEmpty(existingPortRotationList)
        && !CollectionUtils.isEmpty(portRotationRequest.getPortRotationDetailsList())) {
      existingPortRotationList.forEach(
          entity ->
              portRotationRequest.getPortRotationDetailsList().stream()
                  .filter(requestPort -> requestPort.getId() == entity.getId().longValue())
                  .forEach(request -> buildLoadableStudyPortRotationEntity(entity, request)));
    }
    return existingPortRotationList;
  }

  /*
   * builds single entity from the request
   */
  private void buildLoadableStudyPortRotationEntity(
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
    // update distance, etaActual, etdActual values in synoptical
    if (!CollectionUtils.isEmpty(entity.getSynopticalTable())) {
      entity
          .getSynopticalTable()
          .forEach(
              record -> {
                record.setDistance(
                    !StringUtils.isEmpty(request.getDistanceBetweenPorts())
                        ? new BigDecimal(request.getDistanceBetweenPorts())
                        : null);
                if (SYNOPTICAL_TABLE_OP_TYPE_ARRIVAL.equalsIgnoreCase(record.getOperationType())) {
                  record.setEtaActual(
                      isEmpty(request.getEtaActual())
                          ? null
                          : LocalDateTime.from(
                              DateTimeFormatter.ofPattern(ETA_ETD_FORMAT)
                                  .parse(request.getEtaActual())));
                } else {
                  record.setEtdActual(
                      isEmpty(request.getEtdActual())
                          ? null
                          : LocalDateTime.from(
                              DateTimeFormatter.ofPattern(ETA_ETD_FORMAT)
                                  .parse(request.getEtdActual())));
                }
              });
    }
  }

  @Override
  public void saveComment(
      SaveCommentRequest request, StreamObserver<SaveCommentReply> responseObserver) {

    SaveCommentReply.Builder replyBuilder = SaveCommentReply.newBuilder();
    try {
      LoadablePlanComments entity = new LoadablePlanComments();
      entity.setComments(request.getComment());
      Optional<LoadablePattern> loadablePatternOpt =
          this.loadablePatternRepository.findByIdAndIsActive(request.getLoadablePatternId(), true);
      if (loadablePatternOpt.isPresent()) {
        entity.setLoadablePattern(loadablePatternOpt.get());
      }
      entity.setCreatedBy(Long.toString(request.getUser()));
      entity.setIsActive(true);
      entity = this.loadablePlanCommentsRepository.save(entity);

      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    } catch (Exception e) {
      log.error("Error saving comment", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Error saving comment")
              .setStatus(FAILED)
              .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }
}
