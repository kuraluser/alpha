/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.*;
import static java.lang.String.valueOf;
import static java.util.Optional.ofNullable;
import static org.springframework.util.StringUtils.isEmpty;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.generated.CargoInfo.CargoReply;
import com.cpdss.common.generated.CargoInfo.CargoRequest;
import com.cpdss.common.generated.CargoInfoServiceGrpc.CargoInfoServiceBlockingStub;
import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.LoadableStudy.AlgoErrorReply;
import com.cpdss.common.generated.LoadableStudy.AlgoErrorRequest;
import com.cpdss.common.generated.LoadableStudy.AlgoErrors;
import com.cpdss.common.generated.LoadableStudy.AlgoReply;
import com.cpdss.common.generated.LoadableStudy.AlgoRequest;
import com.cpdss.common.generated.LoadableStudy.AlgoStatusReply;
import com.cpdss.common.generated.LoadableStudy.AlgoStatusRequest;
import com.cpdss.common.generated.LoadableStudy.CargoHistoryDetail;
import com.cpdss.common.generated.LoadableStudy.CargoHistoryReply;
import com.cpdss.common.generated.LoadableStudy.CargoHistoryRequest;
import com.cpdss.common.generated.LoadableStudy.CargoNominationReply;
import com.cpdss.common.generated.LoadableStudy.CargoNominationRequest;
import com.cpdss.common.generated.LoadableStudy.CommingleCargo;
import com.cpdss.common.generated.LoadableStudy.CommingleCargoReply;
import com.cpdss.common.generated.LoadableStudy.CommingleCargoRequest;
import com.cpdss.common.generated.LoadableStudy.ConfirmPlanReply;
import com.cpdss.common.generated.LoadableStudy.ConfirmPlanRequest;
import com.cpdss.common.generated.LoadableStudy.JsonRequest;
import com.cpdss.common.generated.LoadableStudy.LDtrim;
import com.cpdss.common.generated.LoadableStudy.LatestCargoReply;
import com.cpdss.common.generated.LoadableStudy.LatestCargoRequest;
import com.cpdss.common.generated.LoadableStudy.LoadablePatternAlgoRequest;
import com.cpdss.common.generated.LoadableStudy.LoadablePatternCargoDetails;
import com.cpdss.common.generated.LoadableStudy.LoadablePatternCommingleDetailsReply;
import com.cpdss.common.generated.LoadableStudy.LoadablePatternCommingleDetailsRequest;
import com.cpdss.common.generated.LoadableStudy.LoadablePatternPortWiseDetailsJson;
import com.cpdss.common.generated.LoadableStudy.LoadablePatternReply;
import com.cpdss.common.generated.LoadableStudy.LoadablePatternRequest;
import com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsReply;
import com.cpdss.common.generated.LoadableStudy.LoadablePlanDetailsRequest;
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
import com.cpdss.common.generated.LoadableStudy.LoadicatorDataReply;
import com.cpdss.common.generated.LoadableStudy.LoadicatorDataRequest;
import com.cpdss.common.generated.LoadableStudy.LoadicatorResultsRequest;
import com.cpdss.common.generated.LoadableStudy.OhqPorts;
import com.cpdss.common.generated.LoadableStudy.OnBoardQuantityDetail;
import com.cpdss.common.generated.LoadableStudy.OnBoardQuantityReply;
import com.cpdss.common.generated.LoadableStudy.OnBoardQuantityRequest;
import com.cpdss.common.generated.LoadableStudy.OnHandQuantityDetail;
import com.cpdss.common.generated.LoadableStudy.OnHandQuantityReply;
import com.cpdss.common.generated.LoadableStudy.OnHandQuantityRequest;
import com.cpdss.common.generated.LoadableStudy.PortRotationDetail;
import com.cpdss.common.generated.LoadableStudy.PortRotationReply;
import com.cpdss.common.generated.LoadableStudy.PortRotationRequest;
import com.cpdss.common.generated.LoadableStudy.PurposeOfCommingleReply;
import com.cpdss.common.generated.LoadableStudy.PurposeOfCommingleRequest;
import com.cpdss.common.generated.LoadableStudy.SaveCommentReply;
import com.cpdss.common.generated.LoadableStudy.SaveCommentRequest;
import com.cpdss.common.generated.LoadableStudy.SaveVoyageStatusReply;
import com.cpdss.common.generated.LoadableStudy.SaveVoyageStatusRequest;
import com.cpdss.common.generated.LoadableStudy.StabilityParameter;
import com.cpdss.common.generated.LoadableStudy.StatusReply;
import com.cpdss.common.generated.LoadableStudy.SynopticalBallastRecord;
import com.cpdss.common.generated.LoadableStudy.SynopticalCargoRecord;
import com.cpdss.common.generated.LoadableStudy.SynopticalOhqRecord;
import com.cpdss.common.generated.LoadableStudy.SynopticalRecord;
import com.cpdss.common.generated.LoadableStudy.SynopticalTableReply;
import com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest;
import com.cpdss.common.generated.LoadableStudy.UpdateUllageReply;
import com.cpdss.common.generated.LoadableStudy.UpdateUllageRequest;
import com.cpdss.common.generated.LoadableStudy.ValveSegregationReply;
import com.cpdss.common.generated.LoadableStudy.ValveSegregationRequest;
import com.cpdss.common.generated.LoadableStudy.VoyageListReply;
import com.cpdss.common.generated.LoadableStudy.VoyageReply;
import com.cpdss.common.generated.LoadableStudy.VoyageRequest;
import com.cpdss.common.generated.LoadableStudyServiceGrpc.LoadableStudyServiceImplBase;
import com.cpdss.common.generated.PortInfo.GetPortInfoByPortIdsRequest;
import com.cpdss.common.generated.PortInfo.PortReply;
import com.cpdss.common.generated.VesselInfo.VesselDetail;
import com.cpdss.common.generated.VesselInfo.VesselReply;
import com.cpdss.common.generated.VesselInfo.VesselRequest;
import com.cpdss.common.generated.VesselInfo.VesselTankDetail;
import com.cpdss.common.generated.VesselInfoServiceGrpc.VesselInfoServiceBlockingStub;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadablestudy.domain.AlgoResponse;
import com.cpdss.loadablestudy.domain.CargoHistory;
import com.cpdss.loadablestudy.domain.LDIntactStability;
import com.cpdss.loadablestudy.domain.LDStrength;
import com.cpdss.loadablestudy.domain.LDTrim;
import com.cpdss.loadablestudy.domain.LoadabalePatternValidateRequest;
import com.cpdss.loadablestudy.domain.LoadicatorAlgoRequest;
import com.cpdss.loadablestudy.domain.LoadicatorAlgoResponse;
import com.cpdss.loadablestudy.domain.LoadicatorPatternDetails;
import com.cpdss.loadablestudy.domain.LoadicatorPatternDetailsResults;
import com.cpdss.loadablestudy.domain.LoadicatorResultDetails;
import com.cpdss.loadablestudy.domain.UllageUpdateRequest;
import com.cpdss.loadablestudy.domain.UllageUpdateResponse;
import com.cpdss.loadablestudy.entity.AlgoErrorHeading;
import com.cpdss.loadablestudy.entity.CargoNomination;
import com.cpdss.loadablestudy.entity.CargoNominationPortDetails;
import com.cpdss.loadablestudy.entity.JsonData;
import com.cpdss.loadablestudy.entity.JsonType;
import com.cpdss.loadablestudy.entity.LoadablePattern;
import com.cpdss.loadablestudy.entity.LoadablePatternAlgoStatus;
import com.cpdss.loadablestudy.entity.LoadablePlanBallastDetails;
import com.cpdss.loadablestudy.entity.LoadablePlanComments;
import com.cpdss.loadablestudy.entity.LoadablePlanCommingleDetails;
import com.cpdss.loadablestudy.entity.LoadablePlanConstraints;
import com.cpdss.loadablestudy.entity.LoadablePlanQuantity;
import com.cpdss.loadablestudy.entity.LoadablePlanStowageBallastDetails;
import com.cpdss.loadablestudy.entity.LoadablePlanStowageDetails;
import com.cpdss.loadablestudy.entity.LoadablePlanStowageDetailsTemp;
import com.cpdss.loadablestudy.entity.LoadableQuantity;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.entity.LoadableStudyAlgoStatus;
import com.cpdss.loadablestudy.entity.LoadableStudyAttachments;
import com.cpdss.loadablestudy.entity.LoadableStudyPortRotation;
import com.cpdss.loadablestudy.entity.LoadableStudyRuleInput;
import com.cpdss.loadablestudy.entity.LoadableStudyRules;
import com.cpdss.loadablestudy.entity.LoadableStudyStatus;
import com.cpdss.loadablestudy.entity.OnBoardQuantity;
import com.cpdss.loadablestudy.entity.OnHandQuantity;
import com.cpdss.loadablestudy.entity.SynopticalTable;
import com.cpdss.loadablestudy.entity.SynopticalTableLoadicatorData;
import com.cpdss.loadablestudy.entity.Voyage;
import com.cpdss.loadablestudy.entity.VoyageStatus;
import com.cpdss.loadablestudy.repository.AlgoErrorHeadingRepository;
import com.cpdss.loadablestudy.repository.AlgoErrorsRepository;
import com.cpdss.loadablestudy.repository.ApiTempHistoryRepository;
import com.cpdss.loadablestudy.repository.CargoHistoryRepository;
import com.cpdss.loadablestudy.repository.CargoNominationOperationDetailsRepository;
import com.cpdss.loadablestudy.repository.CargoNominationRepository;
import com.cpdss.loadablestudy.repository.CargoNominationValveSegregationRepository;
import com.cpdss.loadablestudy.repository.CargoOperationRepository;
import com.cpdss.loadablestudy.repository.CommingleCargoRepository;
import com.cpdss.loadablestudy.repository.JsonDataRepository;
import com.cpdss.loadablestudy.repository.JsonTypeRepository;
import com.cpdss.loadablestudy.repository.LoadablePatternAlgoStatusRepository;
import com.cpdss.loadablestudy.repository.LoadablePatternCargoDetailsRepository;
import com.cpdss.loadablestudy.repository.LoadablePatternCargoToppingOffSequenceRepository;
import com.cpdss.loadablestudy.repository.LoadablePatternRepository;
import com.cpdss.loadablestudy.repository.LoadablePlanBallastDetailsRepository;
import com.cpdss.loadablestudy.repository.LoadablePlanCommentsRepository;
import com.cpdss.loadablestudy.repository.LoadablePlanCommingleDetailsPortwiseRepository;
import com.cpdss.loadablestudy.repository.LoadablePlanCommingleDetailsRepository;
import com.cpdss.loadablestudy.repository.LoadablePlanConstraintsRespository;
import com.cpdss.loadablestudy.repository.LoadablePlanQuantityRepository;
import com.cpdss.loadablestudy.repository.LoadablePlanStowageBallastDetailsRepository;
import com.cpdss.loadablestudy.repository.LoadablePlanStowageDetailsRespository;
import com.cpdss.loadablestudy.repository.LoadablePlanStowageDetailsTempRepository;
import com.cpdss.loadablestudy.repository.LoadableQuantityRepository;
import com.cpdss.loadablestudy.repository.LoadableStudyAlgoStatusRepository;
import com.cpdss.loadablestudy.repository.LoadableStudyAttachmentsRepository;
import com.cpdss.loadablestudy.repository.LoadableStudyPortRotationRepository;
import com.cpdss.loadablestudy.repository.LoadableStudyRepository;
import com.cpdss.loadablestudy.repository.LoadableStudyRuleInputRepository;
import com.cpdss.loadablestudy.repository.LoadableStudyRuleRepository;
import com.cpdss.loadablestudy.repository.LoadableStudyStatusRepository;
import com.cpdss.loadablestudy.repository.OnBoardQuantityRepository;
import com.cpdss.loadablestudy.repository.OnHandQuantityRepository;
import com.cpdss.loadablestudy.repository.PurposeOfCommingleRepository;
import com.cpdss.loadablestudy.repository.StabilityParameterRepository;
import com.cpdss.loadablestudy.repository.SynopticalTableLoadicatorDataRepository;
import com.cpdss.loadablestudy.repository.SynopticalTableRepository;
import com.cpdss.loadablestudy.repository.VoyageHistoryRepository;
import com.cpdss.loadablestudy.repository.VoyageRepository;
import com.cpdss.loadablestudy.repository.VoyageStatusRepository;
import com.cpdss.loadablestudy.repository.projections.PortRotationIdAndPortId;
import com.cpdss.loadablestudy.utility.LoadableStudiesConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import io.grpc.stub.StreamObserver;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.server.service.GrpcService;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
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

  @Value("${loadablestudy.attachement.rootFolder}")
  private String rootFolder;

  @Value("${algo.loadablestudy.api.url}")
  private String loadableStudyUrl;

  @Value("${algo.loadicator.api.url}")
  private String loadicatorUrl;

  @Value("${algo.stowage.edit.api.url}")
  private String algoUpdateUllageUrl;

  @Autowired private VoyageRepository voyageRepository;
  @Autowired private LoadableStudyPortRotationRepository loadableStudyPortRotationRepository;
  @Autowired private CargoOperationRepository cargoOperationRepository;
  @Autowired private LoadableStudyRepository loadableStudyRepository;
  @Autowired private LoadableQuantityRepository loadableQuantityRepository;
  @Autowired private CargoNominationRepository cargoNominationRepository;
  @Autowired private CargoNominationValveSegregationRepository valveSegregationRepository;
  @Autowired private LoadableStudyStatusRepository loadableStudyStatusRepository;
  @Autowired private PurposeOfCommingleRepository purposeOfCommingleRepository;
  @Autowired private LoadablePatternRepository loadablePatternRepository;
  @Autowired private LoadablePlanQuantityRepository loadablePlanQuantityRepository;
  @Autowired private LoadablePlanCommentsRepository loadablePlanCommentsRepository;
  @Autowired private LoadablePlanCommingleDetailsRepository loadablePlanCommingleDetailsRepository;
  @Autowired private LoadablePlanStowageDetailsRespository loadablePlanStowageDetailsRespository;
  @Autowired private EntityManager entityManager;
  @Autowired private RestTemplate restTemplate;
  @Autowired private LoadablePlanBallastDetailsRepository loadablePlanBallastDetailsRepository;
  @Autowired private LoadableStudyAttachmentsRepository loadableStudyAttachmentsRepository;
  @Autowired private VoyageStatusRepository voyageStatusRepository;
  @Autowired private AlgoErrorHeadingRepository algoErrorHeadingRepository;
  @Autowired private AlgoErrorsRepository algoErrorsRepository;
  @Autowired private StabilityParameterRepository stabilityParameterRepository;
  @Autowired VoyageService voyageService;
  @Autowired SynopticService synopticService;
  @Autowired LoadableStudyRuleInputRepository loadableStudyRuleInputRepository;
  @Autowired LoadableStudyRuleRepository loadableStudyRuleRepository;
  @Autowired LoadablePatternService loadablePatternService;
  @Autowired private LoadableStudyPortRotationService loadableStudyPortRotationService;
  @Autowired private LoadableStudyRuleService loadableStudyRuleService;
  @Autowired private AlgoErrorService algoErrorService;
  @Autowired private DischargeStudyService dischargeStudyService;
  @Autowired private LoadicatorService lsLoadicatorService;
  @Autowired private OnBoardQuantityService onBoardQuantityService;

  @Autowired
  private LoadablePlanCommingleDetailsPortwiseRepository
      loadablePlanCommingleDetailsPortwiseRepository;

  @Autowired OnHandQuantityService onHandQuantityService;

  @Autowired private LoadablePlanStowageDetailsTempRepository stowageDetailsTempRepository;

  @Autowired
  private LoadablePlanStowageBallastDetailsRepository loadablePlanStowageBallastDetailsRepository;

  @Autowired private LoadablePlanConstraintsRespository loadablePlanConstraintsRespository;

  @Autowired
  private CargoNominationOperationDetailsRepository cargoNominationOperationDetailsRepository;

  @Autowired private OnHandQuantityRepository onHandQuantityRepository;
  @Autowired private CommingleCargoRepository commingleCargoRepository;
  @Autowired private OnBoardQuantityRepository onBoardQuantityRepository;
  @Autowired private LoadablePatternAlgoStatusRepository loadablePatternAlgoStatusRepository;
  @Autowired private CargoHistoryRepository cargoHistoryRepository;
  @Autowired private VoyageHistoryRepository voyageHistoryRepository;
  @Autowired private LoadableStudyAlgoStatusRepository loadableStudyAlgoStatusRepository;
  @Autowired private SynopticalTableRepository synopticalTableRepository;

  @Autowired
  private SynopticalTableLoadicatorDataRepository synopticalTableLoadicatorDataRepository;

  @Autowired private LoadablePatternCargoDetailsRepository loadablePatternCargoDetailsRepository;
  @Autowired private ApiTempHistoryRepository apiTempHistoryRepository;

  @Autowired private JsonDataRepository jsonDataRepository;
  @Autowired private JsonTypeRepository jsonTypeRepository;

  @Autowired private LoadablePlanService loadablePlanService;
  @Autowired private CargoNominationService cargoNominationService;
  @Autowired private CargoService cargoService;

  @Autowired private LoadablePatternCargoToppingOffSequenceRepository toppingOffSequenceRepository;

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @GrpcClient("cargoService")
  private CargoInfoServiceBlockingStub cargoInfoGrpcService;

  @GrpcClient("envoyWriterService")
  private EnvoyWriterServiceGrpc.EnvoyWriterServiceBlockingStub envoyWriterGrpcService;

  @GrpcClient("envoyReaderService")
  private EnvoyReaderServiceGrpc.EnvoyReaderServiceBlockingStub envoyReaderGrpcService;

  @GrpcClient("portInfoService")
  private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoGrpcService;

  /**
   * method for save voyage
   *
   * @param request - voyage request details
   * @param responseObserver - grpc class
   * @return
   */
  @Override
  public void saveVoyage(VoyageRequest request, StreamObserver<VoyageReply> responseObserver) {
    VoyageReply.Builder builder = VoyageReply.newBuilder();
    try {
      this.voyageService.saveVoyage(request, builder);
    } catch (Exception e) {
      log.error("Error in saving Voyage ", e);
      builder
          .setResponseStatus(
              StatusReply.newBuilder()
                  .setStatus(FAILED)
                  .setMessage(FAILED)
                  .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR))
          .build();
    } finally {
      responseObserver.onNext(builder.build());
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
    LoadableQuantityReply.Builder loadableQuantityReply = LoadableQuantityReply.newBuilder();
    try {
      loadableQuantityService.saveLoadableQuantity(loadableQuantityRequest, loadableQuantityReply);
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when saving loadable quantity", e);
      loadableQuantityReply
          .setResponseStatus(
              StatusReply.newBuilder()
                  .setCode(e.getCode())
                  .setMessage(e.getMessage())
                  .setStatus(FAILED)
                  .setStatusCode(CommonErrorCodes.E_HTTP_BAD_REQUEST))
          .build();
    } catch (Exception e) {
      log.error("Error in saving loadable quantity ", e);
      loadableQuantityReply
          .setResponseStatus(
              StatusReply.newBuilder()
                  .setStatus(FAILED)
                  .setMessage(FAILED)
                  .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
                  .setStatusCode(CommonErrorCodes.E_GEN_INTERNAL_ERR))
          .build();
    } finally {
      responseObserver.onNext(loadableQuantityReply.build());
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
          this.loadableStudyRepository.findAllLoadableStudy(
              request.getVesselId(), voyageOpt.get(), request.getPlanningTypeValue());
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
      DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
      for (LoadableStudy entity : loadableStudyEntityList) {
        com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail.Builder builder =
            LoadableStudyDetail.newBuilder();
        builder.setLastEdited(dateTimeFormatter.format(entity.getLastModifiedDateTime()));
        builder.setId(entity.getId());
        builder.setName(entity.getName());
        ofNullable(entity.getDischargeCargoNominationId())
            .ifPresent(builder::setDischargingCargoId);
        builder.setCreatedDate(dateTimeFormatter.format(entity.getCreatedDateTime()));
        ofNullable(entity.getDuplicatedFrom())
            .ifPresent(
                duplicatedFrom -> {
                  builder.setCreatedFromId(duplicatedFrom.getId());
                });
        ofNullable(entity.getLoadableStudyStatus())
            .ifPresent(
                status -> {
                  builder.setStatusId(status.getId());
                  builder.setStatus(status.getName());
                  List<LoadableStudyAlgoStatus> algoStatus =
                      loadableStudyAlgoStatusRepository.findByLoadableStudyIdAndIsActive(
                          entity.getId(), true);
                  if (!algoStatus.isEmpty()) {
                    DateTimeFormatter dateTimeFormatterLastModified =
                        DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_LAST_MODIFIED);

                    builder.setLoadableStudyStatusLastModifiedTime(
                        dateTimeFormatterLastModified.format(
                            algoStatus.stream()
                                .reduce((f, s) -> s)
                                .orElse(null)
                                .getLastModifiedDateTime())); // getting
                    // the
                    // last
                    // algo
                    // status
                  } else {
                    builder.setLoadableStudyStatusLastModifiedTime("0");
                  }
                });
        ofNullable(entity.getDetails()).ifPresent(builder::setDetail);
        ofNullable(entity.getCharterer()).ifPresent(builder::setCharterer);
        ofNullable(entity.getSubCharterer()).ifPresent(builder::setSubCharterer);
        ofNullable(entity.getLoadLineXId()).ifPresent(builder::setLoadLineXId);
        ofNullable(entity.getDraftMark())
            .ifPresent(dratMark -> builder.setDraftMark(valueOf(dratMark)));
        ofNullable(entity.getDraftRestriction())
            .ifPresent(draftRestriction -> builder.setDraftRestriction(valueOf(draftRestriction)));
        ofNullable(entity.getMaxAirTemperature())
            .ifPresent(maxTemp -> builder.setMaxAirTemperature(valueOf(maxTemp)));
        ofNullable(entity.getMaxWaterTemperature())
            .ifPresent(maxTemp -> builder.setMaxWaterTemperature(valueOf(maxTemp)));
        Optional.ofNullable(entity.getLoadOnTop())
            .ifPresent(loadOnTop -> builder.setLoadOnTop(loadOnTop));
        Optional.ofNullable(entity.getIsCargoNominationComplete())
            .ifPresent(builder::setIsCargoNominationComplete);
        Optional.ofNullable(entity.getIsPortsComplete()).ifPresent(builder::setIsPortsComplete);
        Optional.ofNullable(entity.getIsOhqComplete()).ifPresent(builder::setIsOhqComplete);
        Optional.ofNullable(entity.getIsObqComplete()).ifPresent(builder::setIsObqComplete);
        Optional.ofNullable(entity.getIsDischargePortsComplete())
            .ifPresent(builder::setIsDischargingPortComplete);

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
        if (null != portRotations && !portRotations.isEmpty()) {
          OhqPorts.Builder ohqPortsBuilder = OhqPorts.newBuilder();
          portRotations.forEach(
              port -> {
                if (port.isActive()) {
                  ohqPortsBuilder.setId(port.getId());
                  Optional.ofNullable(port.getIsPortRotationOhqComplete())
                      .ifPresent(ohqPortsBuilder::setIsPortRotationOhqComplete);
                  builder.addOhqPorts(ohqPortsBuilder.build());
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
  @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
  public void saveLoadableStudy(
      LoadableStudyDetail request, StreamObserver<LoadableStudyReply> responseObserver) {
    Builder replyBuilder = LoadableStudyReply.newBuilder();
    LoadableStudy entity = null;
    try {

      this.voyageService.checkIfVoyageClosed(request.getVoyageId());
      List<LoadableStudyRules> listOfExistingLSRules = null;
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

        loadablePatternService.isPatternGeneratedOrConfirmed(entity);
        loadableQuantityService.saveLQuantity(request, loadableStudy);
        // entity.setIsCargoNominationComplete(request.getIsCargoNominationComplete());
        // entity.setIsPortsComplete(request.getIsPortsComplete());
        // entity.setIsOhqComplete(request.getIsOhqComplete());
        // entity.setIsObqComplete(request.getIsObqComplete());
        // entity.setIsDischargePortsComplete(request.getIsDischargingPortComplete());
      } else {
        entity = new LoadableStudy();
        if (request.getDuplicatedFromId() == 0) {
          voyageService.checkIfVoyageActive(request.getVoyageId());
          entity.setIsCargoNominationComplete(false);
          entity.setIsPortsComplete(false);
          entity.setIsOhqComplete(false);
          entity.setIsObqComplete(true);
          entity.setIsDischargePortsComplete(false);
        } else {
          listOfExistingLSRules = loadableStudyRuleService.getLoadableStudyRules(request);
        }
      }

      this.validateLoadableStudySaveRequest(request, entity);
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

      Set<LoadableStudyAttachments> attachmentCollection = new HashSet<>();
      if (!request.getAttachmentsList().isEmpty()) {
        String folderLocation = this.constructFolderPath(entity);
        Files.createDirectories(Paths.get(this.rootFolder + folderLocation));
        for (LoadableStudyAttachment attachment : request.getAttachmentsList()) {
          String fileName =
              attachment.getFileName().substring(0, attachment.getFileName().lastIndexOf("."));
          String extension =
              attachment
                  .getFileName()
                  .substring(attachment.getFileName().lastIndexOf("."))
                  .toLowerCase();
          String filePath =
              folderLocation + fileName + "_" + System.currentTimeMillis() + extension;
          Path path = Paths.get(this.rootFolder + filePath);
          Files.createFile(path);
          Files.write(path, attachment.getByteString().toByteArray());
          LoadableStudyAttachments attachmentEntity = new LoadableStudyAttachments();
          attachmentEntity.setUploadedFileName(attachment.getFileName());
          attachmentEntity.setFilePath(filePath);
          attachmentEntity.setLoadableStudy(entity);
          attachmentEntity.setIsActive(true);
          attachmentCollection.add(attachmentEntity);
        }
        entity.setAttachments(attachmentCollection);
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

      this.setCaseNo(entity);
      entity.setLoadableStudyStatus(
          this.loadableStudyStatusRepository.getOne(LOADABLE_STUDY_INITIAL_STATUS_ID));
      entity = this.loadableStudyRepository.save(entity);
      this.checkDuplicatedFromAndCloneEntity(request, entity);
      final LoadableStudy currentLoableStudy = entity;
      // save rules against saved loadable study(If duplicated LS)
      if (request.getDuplicatedFromId() != 0) {
        loadableStudyRuleService.saveDuplicateLoadableStudyRules(
            listOfExistingLSRules, currentLoableStudy, request);
      }
      replyBuilder
          .setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build())
          .setId(entity.getId());

    } catch (GenericServiceException e) {
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
      log.error("GenericServiceException when saving loadable study", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(e.getCode())
              .setMessage(e.getMessage())
              .setHttpStatusCode(e.getStatus().value())
              .setStatus(FAILED)
              .build());
      this.deleteFiles(entity);
    } catch (Exception e) {
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
      log.error("Error saving loadable study", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setHttpStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR.value())
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

  private void validateLoadableStudySaveRequest(LoadableStudyDetail request, LoadableStudy entity)
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

    this.validateLoadableStudyName(voyageOpt.get(), request);
  }

  /**
   * Check for duplicate LS name
   *
   * @param voyage
   * @param request
   * @throws GenericServiceException
   */
  private void validateLoadableStudyName(Voyage voyage, LoadableStudyDetail request)
      throws GenericServiceException {
    LoadableStudy duplicate =
        this.loadableStudyRepository.findByVoyageAndNameIgnoreCaseAndIsActiveAndPlanningTypeXId(
            voyage, request.getName(), true, Common.PLANNING_TYPE.LOADABLE_STUDY_VALUE);
    if ((request.getId() == 0 && null != duplicate)
        || (null != duplicate && request.getId() != duplicate.getId().longValue())) {
      throw new GenericServiceException(
          "LS already exists with given name",
          CommonErrorCodes.E_CPDSS_LS_NAME_EXISTS,
          HttpStatusCode.BAD_REQUEST);
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
      Path path = Paths.get(this.rootFolder + attachment.getFilePath());
      try {
        Files.deleteIfExists(path);
      } catch (IOException e) {
        log.error("unable to delete file : {}", this.rootFolder + attachment.getFilePath(), e);
      }
    }
  }

  @Override
  public void saveCargoNomination(
      CargoNominationRequest request, StreamObserver<CargoNominationReply> responseObserver) {
    CargoNominationReply.Builder cargoNominationReplyBuilder = CargoNominationReply.newBuilder();
    try {
      cargoNominationService.saveCargoNomination(request, cargoNominationReplyBuilder);
    } catch (GenericServiceException e) {
      log.error("GenericServiceException saveCargoNomination", e);
      cargoNominationReplyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(e.getCode())
              .setMessage(e.getMessage())
              .setHttpStatusCode(e.getStatus().value())
              .setStatus(FAILED)
              .build());
    } catch (Exception e) {
      log.error("Error saving cargo nomination", e);
      cargoNominationReplyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(FAILED));
    } finally {
      responseObserver.onNext(cargoNominationReplyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void getLoadableStudyPortRotation(
      PortRotationRequest request, StreamObserver<PortRotationReply> responseObserver) {
    PortRotationReply.Builder replyBuilder = PortRotationReply.newBuilder();
    try {
      loadableStudyPortRotationService.getLoadableStudyPortRotation(request, replyBuilder);
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
   * Retrieves list of cargoNominations by LoadableStudyId can be extended to other Ids like
   * vesselId or voyageId
   */
  @Override
  public void getCargoNominationById(
      CargoNominationRequest request, StreamObserver<CargoNominationReply> responseObserver) {
    com.cpdss.common.generated.LoadableStudy.CargoNominationReply.Builder replyBuilder =
        CargoNominationReply.newBuilder();
    try {
      cargoNominationService.getCargoNominationById(request, replyBuilder);
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

  @Autowired LoadableQuantityService loadableQuantityService;

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
      loadableQuantityService.loadableQuantityByPortId(
          builder, request.getLoadableStudyId(), request.getPortRotationId());
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

  public VesselInfo.VesselTankResponse getVesselTankDetailsByTankIds(
      VesselInfo.VesselTankRequest replyBuilder) {
    return this.vesselInfoGrpcService.getVesselInfoBytankIds(replyBuilder);
  }

  /** Retrieves all valve segregation available */
  @Override
  public void getValveSegregation(
      ValveSegregationRequest request, StreamObserver<ValveSegregationReply> responseObserver) {
    ValveSegregationReply.Builder reply = ValveSegregationReply.newBuilder();
    try {
      cargoNominationService.getValveSegregation(request, reply);
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
      voyageService.getVoyagesByVessel(request, builder);
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
      loadableStudyPortRotationService.saveLoadableStudyPortRotation(request, replyBuilder);
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when saving loadable study - port data", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(e.getCode())
              .setMessage(e.getMessage())
              .setStatus(FAILED)
              .setHttpStatusCode(e.getStatus().value())
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
      dischargeStudyService.saveDischargingPorts(request, replyBuilder);
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when saving discharging ports", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(e.getCode())
              .setMessage(e.getMessage())
              .setHttpStatusCode(e.getStatus().value())
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

  /** Delete specific cargo nomination */
  @Override
  public void deleteCargoNomination(
      CargoNominationRequest request, StreamObserver<CargoNominationReply> responseObserver) {
    CargoNominationReply.Builder cargoNominationReplyBuilder = CargoNominationReply.newBuilder();
    try {
      cargoNominationService.deleteCargoNomination(request, cargoNominationReplyBuilder);
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when deleting cargo nomination", e);
      cargoNominationReplyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(e.getCode())
              .setMessage(e.getMessage())
              .setStatus(FAILED)
              .setHttpStatusCode(e.getStatus().value())
              .build());
    } catch (Exception e) {
      log.error("Error deleting cargo nomination", e);
      cargoNominationReplyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setStatus(FAILED)
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR));
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

      this.voyageService.checkIfVoyageClosed(entity.getVoyage().getId());

      loadablePatternService.isPatternGeneratedOrConfirmed(entity);

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
      List<LoadableStudyRules> listOfLSRules =
          loadableStudyRuleRepository.findByLoadableStudyAndIsActive(entity, true);
      if (listOfLSRules != null && listOfLSRules.size() > 0) {
        List<Long> rulesInputId =
            listOfLSRules.stream()
                .flatMap(lsRules -> lsRules.getLoadableStudyRuleInputs().stream())
                .map(LoadableStudyRuleInput::getId)
                .collect(Collectors.toList());
        loadableStudyRuleInputRepository.updateLoadbleStudyRulesInputStatus(rulesInputId);
        List<Long> rulesId =
            listOfLSRules.stream().map(LoadableStudyRules::getId).collect(Collectors.toList());
        loadableStudyRuleRepository.updateLoadbleStudyRulesStatus(rulesId);
      }
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when saving loadable study - port data", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(e.getCode())
              .setMessage(e.getMessage())
              .setStatus(FAILED)
              .setHttpStatusCode(e.getStatus().value())
              .build());
    } catch (Exception e) {
      log.error("Exception when saving loadable study - port data", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Exception when saving loadable study - port data")
              .setStatus(FAILED)
              .setHttpStatusCode(Integer.valueOf(CommonErrorCodes.E_GEN_INTERNAL_ERR))
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
      loadableStudyPortRotationService.getPortRotationByLoadableStudyId(
          request, portRotationReplyBuilder);
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
      loadableStudyPortRotationService.deletePortRotation(request, replyBuilder);
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when deleting port rotation", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(e.getCode())
              .setMessage(e.getMessage())
              .setStatus(FAILED)
              .setHttpStatusCode(e.getStatus().value())
              .build());
    } catch (Exception e) {
      log.error("Exception when deleting port rotation", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Exception when deleting port rotation")
              .setStatus(FAILED)
              .setHttpStatusCode(Integer.valueOf(CommonErrorCodes.E_GEN_INTERNAL_ERR))
              .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void getOnHandQuantity(
      OnHandQuantityRequest request, StreamObserver<OnHandQuantityReply> responseObserver) {
    log.info("getOnHandQuantity");
    OnHandQuantityReply.Builder replyBuilder = OnHandQuantityReply.newBuilder();
    try {
      onHandQuantityService.getOnHandQuantity(request, replyBuilder);
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
      onHandQuantityService.saveOnHandQuantity(request, replyBuilder);
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when saving on hand quantities", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(e.getCode())
              .setMessage(e.getMessage())
              .setStatus(FAILED)
              .setHttpStatusCode(e.getStatus().value())
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

  /** get purpose of commingle look up */
  @Override
  public void getPurposeOfCommingle(
      PurposeOfCommingleRequest request, StreamObserver<PurposeOfCommingleReply> responseObserver) {
    PurposeOfCommingleReply.Builder reply = PurposeOfCommingleReply.newBuilder();
    try {
      cargoService.getPurposeOfCommingle(request, reply);
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
      loadablePatternService.saveLoadablePatterns(request, builder);
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
  // uncomment with communication service implementation
  /*private void savePatternDtails(
           LoadablePatternAlgoRequest request, Optional<LoadableStudy> loadableStudyOpt) {
     Long lastLoadingPort =
             loadableStudyPortRotationService.getLastPort(
                     loadableStudyOpt.get(), this.cargoOperationRepository.getOne(LOADING_OPERATION_ID));
                      List<LoadablePattern> loadablePatterns = new ArrayList<LoadablePattern>();
     request
             .getLoadablePlanDetailsList()
             .forEach(
                     lpd -> {
                       LoadablePattern loadablePattern = saveloadablePattern(lpd, loadableStudyOpt.get());
  loadablePatterns.add(loadablePattern);
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
                                 lppwdOptional.get().getDepartureCondition().getLoadablePlanStowageDetailsList(),
                                 loadablePattern);
                         saveLoadablePlanBallastDetails(
                                 lppwdOptional.get().getDepartureCondition().getLoadablePlanBallastDetailsList(),
                                 loadablePattern);
                       }

                       saveLoadableQuantityCommingleCargoPortwiseDetails(
                               lpd.getLoadablePlanPortWiseDetailsList(), loadablePattern);
                       saveStabilityParameters(loadablePattern, lpd, lastLoadingPort);
                       saveLoadablePlanStowageDetails(loadablePattern, lpd);
                       saveLoadablePlanBallastDetails(loadablePattern, lpd);
                       saveStabilityParameterForNonLodicator(
                               request.getHasLodicator(), loadablePattern, lpd);
                     });
     if (request.getHasLodicator()) {
       this.saveLoadicatorInfo(loadableStudyOpt.get(), request.getProcesssId(), 0L, loadablePatterns);
     }
   }*/

  /**
   * @param request
   * @param responseObserver
   */
  @Override
  public void saveLoadicatorResults(
      LoadicatorResultsRequest request, StreamObserver<AlgoReply> responseObserver) {
    AlgoReply.Builder replyBuilder = AlgoReply.newBuilder();
    try {
      lsLoadicatorService.saveLoadicatorResults(request, replyBuilder);
    } catch (Exception e) {
      log.error("Exception in saveLoadicatorResults", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Exception in saveLoadicatorResults")
              .setStatus(FAILED)
              .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void getLoadablePatternList(
      LoadablePatternRequest request, StreamObserver<LoadablePatternReply> responseObserver) {
    LoadablePatternReply.Builder replyBuilder = LoadablePatternReply.newBuilder();
    try {
      loadablePatternService.getLoadablePatternList(request, replyBuilder);
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
   * This validation based on DSS-1860 If the ETA/ETD/Lay are empty for a LS, then this retunr value
   * must false.
   *
   * @return
   */
  private boolean validateLoadableStudyForConfimPlan(LoadableStudy ls) {
    boolean status = true;
    Map<Long, Boolean> validationStack = new HashMap<>();
    if (ls.getPortRotations() != null && !ls.getPortRotations().isEmpty()) {
      for (LoadableStudyPortRotation pr : ls.getPortRotations()) {
        if (pr.getId() > 0) {
          if (pr.getEta() == null || pr.getEtd() == null || !isLayCanValid(pr)) {
            validationStack.put(pr.getId(), false);
          }
        }
      }
    }
    log.info(
        "Loadable Study, Validate Plan, status - {}, LS Id - {}",
        validationStack.isEmpty(),
        ls.getId());
    if (!validationStack.isEmpty()) {
      log.info(
          "Loadable Study, Validate Plan, Invalid Port Rotaion Ids - {}", validationStack.keySet());
    }
    return validationStack.isEmpty();
  }

  private boolean isLayCanValid(LoadableStudyPortRotation lsPr) {
    List ids =
        Arrays.asList(LOADING_OPERATION_ID, STS_LOADING_OPERATION_ID, STS_DISCHARGING_OPERATION_ID);
    if (ids.contains(lsPr.getOperation().getId())) {
      if (lsPr.getLayCanTo() == null || lsPr.getLayCanFrom() == null) return false;
      else return true;
    }
    return true;
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
        boolean status = this.validateLoadableStudyForConfimPlan(loadableStudy.get());
        builder.setConfirmPlanEligibility(status);
        List<LoadablePattern> loadablePatterns =
            loadablePatternRepository.findByLoadableStudyAndIsActiveOrderByCaseNumberAsc(
                loadableStudy.get(), true);
        log.info(
            "Loadable Patterns, Found {} loadaple patterns for LS {}, Id {}",
            loadablePatterns.size(),
            loadableStudy.get().getName(),
            loadableStudy.get().getId());
        loadablePatterns.forEach(
            loadablePattern -> {
              com.cpdss.common.generated.LoadableStudy.LoadablePattern.Builder
                  loadablePatternBuilder =
                      com.cpdss.common.generated.LoadableStudy.LoadablePattern.newBuilder();
              loadablePatternBuilder.setLoadablePatternId(loadablePattern.getId());
              ofNullable(loadableStudy.get().getName()).ifPresent(builder::setLoadableStudyName);
              DateTimeFormatter dateTimeFormatter =
                  DateTimeFormatter.ofPattern(CREATED_DATE_FORMAT);

              ofNullable(dateTimeFormatter.format(loadablePattern.getCreatedDate()))
                  .ifPresent(builder::setLoadablePatternCreatedDate);
              ofNullable(loadablePattern.getLoadableStudyStatus())
                  .ifPresent(loadablePatternBuilder::setLoadableStudyStatusId);
              //              if (stowageDetailsTempRepository
              //                  .findByLoadablePatternAndIsActive(loadablePattern, true)
              //                  .isEmpty()) loadablePatternBuilder.setValidated(true);
              ofNullable(loadablePattern.getCaseNumber())
                  .ifPresent(loadablePatternBuilder::setCaseNumber);
              List<LoadablePatternAlgoStatus> patternStatus =
                  loadablePatternAlgoStatusRepository.findByLoadablePatternAndIsActive(
                      loadablePattern, true);
              if (!patternStatus.isEmpty()) {
                loadablePatternBuilder.setLoadablePatternStatusId(
                    patternStatus.get(patternStatus.size() - 1).getLoadableStudyStatus().getId());
              }

              if (!patternStatus.isEmpty()) {
                if (stowageDetailsTempRepository
                        .findByLoadablePatternAndIsActive(loadablePattern, true)
                        .isEmpty()
                    || VALIDATED_CONDITIONS.contains(
                        loadablePatternBuilder.getLoadablePatternStatusId())) {
                  loadablePatternBuilder.setValidated(true);
                }
              } else {
                if (stowageDetailsTempRepository
                    .findByLoadablePatternAndIsActive(loadablePattern, true)
                    .isEmpty()) {
                  loadablePatternBuilder.setValidated(true);
                }
              }

              loadablePatternBuilder.setStabilityParameters(
                  buildStabilityParamter(loadablePattern));

              List<LoadablePlanConstraints> loadablePlanConstraints =
                  loadablePlanConstraintsRespository.findByLoadablePatternAndIsActive(
                      loadablePattern, true);
              loadablePatternBuilder.clearConstraints();
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
              List<Long> tankIds =
                  loadablePlanStowageDetails.stream()
                      .map(LoadablePlanStowageDetails::getTankId)
                      .collect(Collectors.toList());
              VesselInfo.VesselTankRequest replyTankBuilder =
                  VesselInfo.VesselTankRequest.newBuilder().addAllTankIds(tankIds).build();
              VesselInfo.VesselTankResponse vesselReply =
                  this.getVesselTankDetailsByTankIds(replyTankBuilder);
              loadablePlanService.buildLoadablePlanStowageCargoDetails(
                  loadablePlanStowageDetails, replyBuilder, vesselReply);
              List<com.cpdss.common.generated.LoadableStudy.LoadablePlanStowageDetails>
                  modifieableList =
                      new ArrayList<>(replyBuilder.getLoadablePlanStowageDetailsList());
              Collections.sort(
                  modifieableList,
                  Comparator.comparing(
                      com.cpdss.common.generated.LoadableStudy.LoadablePlanStowageDetails
                          ::getTankDisplayOrder));
              loadablePatternBuilder.addAllLoadablePlanStowageDetails(modifieableList);

              // <--DSS-2016-->
              // loadableQuantityCargoDetails in json response
              List<LoadablePlanQuantity> loadablePlanQuantities =
                  loadablePlanQuantityRepository.findByLoadablePatternAndIsActive(
                      loadablePattern, true);
              log.info(
                  "Loadable Patters, Loadable Plan Quantity Size {}",
                  loadablePlanQuantities.size());
              loadablePlanService.buildLoadablePlanQuantity(
                  loadablePlanQuantities, loadablePatternBuilder);
              List<LoadablePlanCommingleDetails> loadablePlanCommingleDetails =
                  loadablePlanCommingleDetailsRepository.findByLoadablePatternAndIsActive(
                      loadablePattern, true);
              loadablePlanService.buildLoadablePlanCommingleDetails(
                  loadablePlanCommingleDetails, loadablePatternBuilder);
              List<LoadablePlanBallastDetails> loadablePlanBallastDetails =
                  loadablePlanBallastDetailsRepository.findByLoadablePatternAndIsActive(
                      loadablePattern, true);
              List<LoadablePlanStowageDetailsTemp> ballstTempList =
                  this.stowageDetailsTempRepository.findByLoadablePlanBallastDetailsInAndIsActive(
                      loadablePlanBallastDetails, true);
              loadablePlanService.buildBallastGridDetails(
                  loadablePlanBallastDetails, ballstTempList, loadablePatternBuilder);
              // <--DSS-2016!-->

              builder.addLoadablePattern(loadablePatternBuilder);
              loadablePatternBuilder.clearLoadablePlanStowageDetails();
              loadablePatternBuilder.clearLoadableQuantityCargoDetails();
            });

        VesselReply vesselReply = this.getTankListForPattern(loadableStudy.get().getVesselXId());
        VesselReply vesselReply2 =
            this.getTanks(loadableStudy.get().getVesselXId(), CARGO_BALLAST_TANK_CATEGORIES);
        if (!SUCCESS.equals(vesselReply.getResponseStatus().getStatus())) {
          builder.setResponseStatus(ResponseStatus.newBuilder().setStatus(FAILED).build());
        } else {
          builder.addAllTanks(this.groupTanks(vesselReply.getVesselTanksList()));
          builder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
          buildBallastTankLayout(
              vesselReply2.getVesselTanksList().stream()
                  .filter(
                      tankList -> BALLAST_TANK_CATEGORIES.contains(tankList.getTankCategoryId()))
                  .collect(Collectors.toList()),
              builder);
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
   * Ballast Tank category builder
   *
   * @param vesselTankDetails - List<VesselTankDetail>
   * @param replyBuilder - LoadablePatternReply.Builder
   */
  private void buildBallastTankLayout(
      List<VesselTankDetail> vesselTankDetails, LoadablePatternReply.Builder replyBuilder) {

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
   * @param loadablePattern
   * @return StabilityParameter
   */
  private StabilityParameter buildStabilityParamter(LoadablePattern loadablePattern) {
    StabilityParameter.Builder builder = StabilityParameter.newBuilder();
    stabilityParameterRepository
        .findByLoadablePatternAndIsActive(loadablePattern, true)
        .forEach(
            sp -> {
              builder.setAfterDraft(sp.getAftDraft());
              builder.setBendinMoment(sp.getBendingMoment());
              builder.setForwardDraft(sp.getFwdDraft());
              builder.setHeel(sp.getHeal());
              builder.setMeanDraft(sp.getMeanDraft());
              builder.setShearForce(sp.getShearingForce());
              builder.setTrim(sp.getTrimValue());
            });
    return builder.build();
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
          Optional.ofNullable(lpq.getMaxTolerence())
              .ifPresent(val -> loadablePatternCargoDetailsBuilder.setMaxTolerence(val));
          Optional.ofNullable(lpq.getMinTolerence())
              .ifPresent(val -> loadablePatternCargoDetailsBuilder.setMinTolerence(val));
          Optional.ofNullable(lpq.getPriority())
              .ifPresent(priority -> loadablePatternCargoDetailsBuilder.setPriority(priority));
          ofNullable(lpq.getLoadableMt())
              .ifPresent(
                  quantity ->
                      loadablePatternCargoDetailsBuilder.setQuantity(String.valueOf(quantity)));
          ofNullable(lpq.getOrderQuantity())
              .ifPresent(
                  orderedQuantity ->
                      loadablePatternCargoDetailsBuilder.setOrderedQuantity(
                          String.valueOf(orderedQuantity)));

          ofNullable(lpq.getCargoAbbreviation())
              .ifPresent(
                  cargoAbbreviation ->
                      loadablePatternCargoDetailsBuilder.setCargoAbbreviation(cargoAbbreviation));
          ofNullable(lpq.getCargoColor())
              .ifPresent(
                  cargoColor -> loadablePatternCargoDetailsBuilder.setCargoColor(cargoColor));
          ofNullable(lpq.getLoadingOrder())
              .ifPresent(
                  loadingOrder -> loadablePatternCargoDetailsBuilder.setLoadingOrder(loadingOrder));
          ofNullable(lpq.getEstimatedApi())
              .ifPresent(api -> loadablePatternCargoDetailsBuilder.setApi(String.valueOf(api)));
          Optional.ofNullable(lpq.getCargoNominationTemperature())
              .ifPresent(
                  temp -> loadablePatternCargoDetailsBuilder.setTemperature(String.valueOf(temp)));

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
          ofNullable(lpcd.getPriority())
              .ifPresent(priority -> loadablePatternCargoDetailsBuilder.setPriority(priority));
          ofNullable(lpcd.getQuantity())
              .ifPresent(
                  quantity ->
                      loadablePatternCargoDetailsBuilder.setQuantity(String.valueOf(quantity)));
          ofNullable(lpcd.getQuantity())
              .ifPresent(
                  orderedQuantity ->
                      loadablePatternCargoDetailsBuilder.setOrderedQuantity(
                          String.valueOf(orderedQuantity)));

          ofNullable(lpcd.getGrade())
              .ifPresent(
                  cargoAbbreviation ->
                      loadablePatternCargoDetailsBuilder.setCargoAbbreviation(cargoAbbreviation));

          loadablePatternCargoDetailsBuilder.setIsCommingle(true);
          ofNullable(lpcd.getId())
              .ifPresent(
                  id ->
                      loadablePatternCargoDetailsBuilder.setLoadablePatternCommingleDetailsId(id));
          ofNullable(lpcd.getLoadingOrder())
              .ifPresent(
                  loadingOrder -> loadablePatternCargoDetailsBuilder.setLoadingOrder(loadingOrder));
          ofNullable(lpcd.getApi())
              .ifPresent(api -> loadablePatternCargoDetailsBuilder.setApi(String.valueOf(api)));

          loadablePatternBuilder.addLoadablePatternCargoDetails(loadablePatternCargoDetailsBuilder);

          com.cpdss.common.generated.LoadableStudy.LoadablePlanStowageDetails.Builder builder =
              com.cpdss.common.generated.LoadableStudy.LoadablePlanStowageDetails.newBuilder();
          Optional.ofNullable(lpcd.getId()).ifPresent(builder::setId);
          Optional.ofNullable(lpcd.getGrade()).ifPresent(builder::setCargoAbbreviation);
          Optional.ofNullable(lpcd.getApi()).ifPresent(builder::setApi);
          Optional.ofNullable(lpcd.getCorrectedUllage()).ifPresent(builder::setCorrectedUllage);
          Optional.ofNullable(lpcd.getCorrectionFactor()).ifPresent(builder::setCorrectionFactor);
          Optional.ofNullable(lpcd.getFillingRatio()).ifPresent(builder::setFillingRatio);

          Optional.ofNullable(lpcd.getFillingRatio()).ifPresent(builder::setFillingRatioOrginal);
          Optional.ofNullable(lpcd.getCorrectedUllage())
              .ifPresent(builder::setCorrectedUllageOrginal);
          Optional.ofNullable(lpcd.getCorrectionFactor())
              .ifPresent(builder::setCorrectionFactorOrginal);
          Optional.ofNullable(lpcd.getRdgUllage()).ifPresent(builder::setRdgUllageOrginal);
          Optional.ofNullable(lpcd.getQuantity()).ifPresent(builder::setWeightOrginal);

          Optional.ofNullable(lpcd.getRdgUllage()).ifPresent(builder::setRdgUllage);
          Optional.ofNullable(lpcd.getTankName()).ifPresent(builder::setTankName);
          Optional.ofNullable(lpcd.getTankShortName()).ifPresent(builder::setTankShortName);
          Optional.ofNullable(lpcd.getTankId()).ifPresent(builder::setTankId);
          Optional.ofNullable(lpcd.getTemperature()).ifPresent(builder::setTemperature);
          Optional.ofNullable(lpcd.getQuantity()).ifPresent(builder::setWeight);
          builder.setIsCommingle(true);
          loadablePatternBuilder.addLoadablePlanStowageDetails(builder);
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
            ofNullable(commingleCargo.getId()).ifPresent(builder::setId);
            ofNullable(commingleCargo.getPurposeXid()).ifPresent(builder::setPurposeId);
            ofNullable(commingleCargo.getIsSlopOnly()).ifPresent(builder::setSlopOnly);
            // Convert comma separated tank list to arrays
            if (commingleCargo.getTankIds() != null && !commingleCargo.getTankIds().isEmpty()) {
              List<Long> tankIdList =
                  Stream.of(commingleCargo.getTankIds().split(","))
                      .map(String::trim)
                      .map(Long::parseLong)
                      .collect(Collectors.toList());
              builder.addAllPreferredTanks(tankIdList);
            }
            ofNullable(commingleCargo.getCargo1Xid()).ifPresent(builder::setCargo1Id);
            ofNullable(commingleCargo.getCargo1Pct())
                .ifPresent(cargo1Pct -> builder.setCargo1Pct(String.valueOf(cargo1Pct)));
            ofNullable(commingleCargo.getCargo2Xid()).ifPresent(builder::setCargo2Id);
            ofNullable(commingleCargo.getCargo2Pct())
                .ifPresent(cargo2Pct -> builder.setCargo2Pct(String.valueOf(cargo2Pct)));
            ofNullable(commingleCargo.getQuantity())
                .ifPresent(quantity -> builder.setQuantity(String.valueOf(quantity)));
            Optional.ofNullable(commingleCargo.getCargoNomination1Id())
                .ifPresent(builder::setCargoNomination1Id);
            Optional.ofNullable(commingleCargo.getCargoNomination2Id())
                .ifPresent(builder::setCargoNomination2Id);
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
      this.voyageService.checkIfVoyageClosed(loadableStudyOpt.get().getVoyage().getId());
      loadablePatternService.isPatternGeneratedOrConfirmed(loadableStudyOpt.get());

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
      } else {
        List<com.cpdss.loadablestudy.entity.CommingleCargo> commingleCargoEntityList =
            this.commingleCargoRepository.findByLoadableStudyXIdAndIsActive(
                request.getLoadableStudyId(), true);
        List<Long> existingCommingleCargoIds = null;
        if (!commingleCargoEntityList.isEmpty()) {
          existingCommingleCargoIds =
              commingleCargoEntityList.stream()
                  .map(com.cpdss.loadablestudy.entity.CommingleCargo::getId)
                  .collect(Collectors.toList());
        }
        if (!CollectionUtils.isEmpty(existingCommingleCargoIds)) {
          commingleCargoRepository.deleteCommingleCargo(existingCommingleCargoIds);
        }
      }
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS));
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when saving CommingleCargo", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(e.getCode())
              .setMessage(e.getMessage())
              .setStatus(FAILED)
              .setHttpStatusCode(e.getStatus().value()));
    } catch (Exception e) {
      log.error("Exception when saving CommingleCargo", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Exception when saving CommingleCargo")
              .setStatus(FAILED));
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
    List<Long> cargoNominationIds = new ArrayList<>();
    cargoNominationIds.add(requestRecord.getCargoNomination1Id());
    cargoNominationIds.add(requestRecord.getCargoNomination2Id());
    // fetch the max priority for the cargoNomination ids and set as priority for
    // commingle
    Long maxPriority =
        cargoNominationRepository.getMaxPriorityCargoNominationIn(cargoNominationIds);
    commingleCargoEntity.setPriority(maxPriority != null ? maxPriority.intValue() : null);
    commingleCargoEntity.setCargoNomination1Id(requestRecord.getCargoNomination1Id());
    commingleCargoEntity.setCargoNomination2Id(requestRecord.getCargoNomination2Id());
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
  public void updateUllage(
      UpdateUllageRequest request, StreamObserver<UpdateUllageReply> responseObserver) {
    log.info("Inside get updateUllage in loadable study micro service");
    UpdateUllageReply.Builder replyBuilder = UpdateUllageReply.newBuilder();
    try {
      Optional<LoadablePattern> loadablePatternOpt =
          this.loadablePatternRepository.findByIdAndIsActive(request.getLoadablePatternId(), true);
      if (!loadablePatternOpt.isPresent()) {
        throw new GenericServiceException(
            INVALID_LOADABLE_PATTERN_ID,
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      UllageUpdateResponse algoResponse =
          this.callAlgoUllageUpdateApi(
              this.prepareUllageUpdateRequest(request, loadablePatternOpt.get()));
      if (!request.getUpdateUllageForLoadingPlan() && !algoResponse.getFillingRatio().equals("")) {
        this.saveUllageUpdateResponse(algoResponse, request, loadablePatternOpt.get());
      }
      replyBuilder.setLoadablePlanStowageDetails(
          this.buildUpdateUllageReply(algoResponse, request));
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());

    } catch (GenericServiceException e) {
      log.error("GenericServiceException in update ullage", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(e.getCode())
              .setMessage(e.getMessage())
              .setStatus(FAILED)
              .build());
    } catch (Exception e) {
      log.error("Exception in update ullage", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage(e.getMessage())
              .setStatus(FAILED)
              .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * Build upadate ullage reply
   *
   * @param algoResponse
   * @param request
   * @return
   */
  private com.cpdss.common.generated.LoadableStudy.LoadablePlanStowageDetails
      buildUpdateUllageReply(UllageUpdateResponse algoResponse, UpdateUllageRequest request) {
    com.cpdss.common.generated.LoadableStudy.LoadablePlanStowageDetails.Builder builder =
        com.cpdss.common.generated.LoadableStudy.LoadablePlanStowageDetails.newBuilder();
    Optional.ofNullable(algoResponse.getCorrectedUllage()).ifPresent(builder::setCorrectedUllage);
    Optional.ofNullable(algoResponse.getCorrectionFactor()).ifPresent(builder::setCorrectionFactor);
    Optional.ofNullable(algoResponse.getQuantityMt()).ifPresent(builder::setWeight);
    Optional.ofNullable(request.getLoadablePlanStowageDetails().getIsBallast())
        .ifPresent(builder::setIsBallast);
    Optional.ofNullable(algoResponse.getFillingRatio()).ifPresent(builder::setFillingRatio);
    return builder.build();
  }

  /**
   * Save corrected ullage to the temp table
   *
   * @param algoResponse
   * @param loadablePattern
   */
  private void saveUllageUpdateResponse(
      UllageUpdateResponse algoResponse,
      UpdateUllageRequest request,
      LoadablePattern loadablePattern) {
    LoadablePlanStowageDetailsTemp stowageTemp = null;
    if (request.getLoadablePlanStowageDetails().getIsBallast()) {
      LoadablePlanBallastDetails ballastDetails =
          this.loadablePlanBallastDetailsRepository.getOne(algoResponse.getId());
      stowageTemp =
          this.stowageDetailsTempRepository.findByLoadablePlanBallastDetailsAndIsActive(
              ballastDetails, true);
      if (null == stowageTemp) {
        stowageTemp = new LoadablePlanStowageDetailsTemp();
        stowageTemp.setLoadablePlanBallastDetails(ballastDetails);
        stowageTemp.setIsActive(true);
      }
    } else if (request.getLoadablePlanStowageDetails().getIsCommingle()) {
      LoadablePlanCommingleDetails commingleDetails =
          this.loadablePlanCommingleDetailsRepository.getOne(algoResponse.getId());
      stowageTemp =
          this.stowageDetailsTempRepository.findByLoadablePlanCommingleDetailsAndIsActive(
              commingleDetails, true);
      if (null == stowageTemp) {
        stowageTemp = new LoadablePlanStowageDetailsTemp();
        stowageTemp.setLoadablePlanCommingleDetails(commingleDetails);
        stowageTemp.setIsActive(true);
      }
    } else {
      LoadablePlanStowageDetails stowageDetails =
          this.loadablePlanStowageDetailsRespository.getOne(algoResponse.getId());
      stowageTemp =
          this.stowageDetailsTempRepository.findByLoadablePlanStowageDetailsAndIsActive(
              stowageDetails, true);
      if (null == stowageTemp) {
        stowageTemp = new LoadablePlanStowageDetailsTemp();
        stowageTemp.setLoadablePlanStowageDetails(stowageDetails);
        stowageTemp.setIsActive(true);
      }
    }

    stowageTemp.setCorrectedUllage(
        isEmpty(algoResponse.getCorrectedUllage())
            ? null
            : new BigDecimal(algoResponse.getCorrectedUllage()));
    stowageTemp.setCorrectionFactor(
        isEmpty(algoResponse.getCorrectionFactor())
            ? null
            : new BigDecimal(algoResponse.getCorrectionFactor()));

    stowageTemp.setQuantity(
        isEmpty(algoResponse.getQuantityMt())
            ? null
            : new BigDecimal(algoResponse.getQuantityMt()));
    stowageTemp.setRdgUllage(
        isEmpty(request.getLoadablePlanStowageDetails().getCorrectedUllage())
            ? null
            : new BigDecimal(request.getLoadablePlanStowageDetails().getCorrectedUllage()));
    stowageTemp.setIsBallast(request.getLoadablePlanStowageDetails().getIsBallast());
    stowageTemp.setIsCommingle(request.getLoadablePlanStowageDetails().getIsCommingle());
    stowageTemp.setLoadablePattern(loadablePattern);
    stowageTemp.setFillingRatio(
        isEmpty(algoResponse.getFillingRatio())
            ? null
            : new BigDecimal(algoResponse.getFillingRatio()));
    this.stowageDetailsTempRepository.save(stowageTemp);
  }

  /**
   * Call algo - ullage update api and validate the resonse
   *
   * @param algoRequest
   * @return
   * @throws GenericServiceException
   */
  private UllageUpdateResponse callAlgoUllageUpdateApi(UllageUpdateRequest algoRequest)
      throws GenericServiceException {
    ResponseEntity<UllageUpdateResponse> responseEntity =
        this.restTemplate.postForEntity(
            this.algoUpdateUllageUrl, algoRequest, UllageUpdateResponse.class);
    if (HttpStatusCode.OK.value() != responseEntity.getStatusCodeValue()) {
      throw new GenericServiceException(
          "Error calling algo: invalid status received",
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    return responseEntity.getBody();
  }

  /**
   * Prepare ullage upate request for algo
   *
   * @param request
   * @param loadablePattern
   * @return
   */
  private UllageUpdateRequest prepareUllageUpdateRequest(
      UpdateUllageRequest request, LoadablePattern loadablePattern) {
    UllageUpdateRequest algoRequest = new UllageUpdateRequest();
    algoRequest.setRdgUllage(request.getLoadablePlanStowageDetails().getCorrectedUllage());
    algoRequest.setId(request.getLoadablePlanStowageDetails().getId());
    algoRequest.setTankId(request.getLoadablePlanStowageDetails().getTankId());
    algoRequest.setApi(request.getLoadablePlanStowageDetails().getApi());
    algoRequest.setTemp(request.getLoadablePlanStowageDetails().getTemperature());
    Optional<SynopticalTable> synopticalTableOpt =
        synopticalTableRepository.findByLoadableStudyPortRotationAndOperationTypeAndIsActive(
            loadableStudyPortRotationService.getLastPortRotationId(
                loadablePattern.getLoadableStudy(),
                this.cargoOperationRepository.getOne(LOADING_OPERATION_ID)),
            SYNOPTICAL_TABLE_OP_TYPE_DEPARTURE,
            true);
    if (synopticalTableOpt.isPresent()) {
      algoRequest.setTrim(
          String.valueOf(
              synopticalTableLoadicatorDataRepository
                  .findBySynopticalTableAndLoadablePatternIdAndIsActive(
                      synopticalTableOpt.get(), loadablePattern.getId(), true)
                  .getCalculatedTrimPlanned()));
    }
    algoRequest.setSg(request.getLoadablePlanStowageDetails().getSg());
    return algoRequest;
  }

  @Override
  public void validateLoadablePlan(
      LoadablePlanDetailsRequest request, StreamObserver<AlgoReply> responseObserver) {
    log.info("Inside get validateLoadablePlan in loadable study micro service");
    AlgoReply.Builder replyBuilder = AlgoReply.newBuilder();
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
        LoadabalePatternValidateRequest loadabalePatternValidateRequest =
            new LoadabalePatternValidateRequest();

        ModelMapper modelMapper = new ModelMapper();
        com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy =
            new com.cpdss.loadablestudy.domain.LoadableStudy();

        buildLoadableStudy(
            loadablePatternOpt.get().getLoadableStudy().getId(),
            loadablePatternOpt.get().getLoadableStudy(),
            loadableStudy,
            modelMapper);
        loadablePlanService.buildLoadablePlanPortWiseDetails(
            loadablePatternOpt.get(), loadabalePatternValidateRequest);

        loadabalePatternValidateRequest.setLoadableStudy(loadableStudy);

        loadabalePatternValidateRequest.setBallastEdited(
            stowageDetailsTempRepository.isBallastEdited(request.getLoadablePatternId(), true));
        loadabalePatternValidateRequest.setLoadablePatternId(request.getLoadablePatternId());
        loadabalePatternValidateRequest.setCaseNumber(loadablePatternOpt.get().getCaseNumber());
        ObjectMapper objectMapper = new ObjectMapper();
        this.saveJsonToDatabase(
            request.getLoadablePatternId(),
            LOADABLE_PATTERN_EDIT_REQUEST,
            objectMapper.writeValueAsString(loadabalePatternValidateRequest));
        objectMapper.writeValue(
            new File(
                this.rootFolder
                    + "/json/loadablePattern_request_"
                    + request.getLoadablePatternId()
                    + ".json"),
            loadabalePatternValidateRequest);
        AlgoResponse algoResponse =
            restTemplate.postForObject(
                loadableStudyUrl, loadabalePatternValidateRequest, AlgoResponse.class);

        updateProcessIdForLoadablePattern(
            algoResponse.getProcessId(),
            loadablePatternOpt.get(),
            LOADABLE_PATTERN_VALIDATION_STARTED_ID);

        replyBuilder =
            AlgoReply.newBuilder()
                .setProcesssId(algoResponse.getProcessId())
                .setResponseStatus(
                    ResponseStatus.newBuilder().setMessage(SUCCESS).setStatus(SUCCESS).build());
      }

    } catch (Exception e) {
      log.error("Exception while validateLoadablePlan", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Exception while validate Loadable Plan")
              .setStatus(FAILED)
              .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * @param processId
   * @param loadablePattern
   * @param loadableStudyProcessingStartedId void
   */
  private void updateProcessIdForLoadablePattern(
      String processId, LoadablePattern loadablePattern, Long loadablePatternProcessingStartedId) {
    LoadablePatternAlgoStatus status = new LoadablePatternAlgoStatus();
    status.setLoadablePattern(loadablePattern);
    status.setIsActive(true);
    status.setLoadableStudyStatus(
        loadableStudyStatusRepository.getOne(loadablePatternProcessingStartedId));
    status.setProcessId(processId);
    status.setVesselxid(loadablePattern.getLoadableStudy().getVesselXId());
    loadablePatternAlgoStatusRepository.save(status);
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
      log.error("Exception in update ullage", e);
      builder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Exception while recalculating volume")
              .setStatus(FAILED)
              .build());
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * Build upadate ullage reply
   *
   * @param algoResponse
   * @param request
   * @return
   */
  private void buildLoadablePatternComingleDetails(
      LoadablePlanCommingleDetails loadablePlanCommingleDetails,
      com.cpdss.common.generated.LoadableStudy.LoadablePatternCommingleDetailsReply.Builder
          builder) {
    ofNullable(loadablePlanCommingleDetails.getApi())
        .ifPresent(api -> builder.setApi(String.valueOf(api)));
    ofNullable(loadablePlanCommingleDetails.getCargo1Abbreviation())
        .ifPresent(cargo1Abbrivation -> builder.setCargo1Abbrivation(cargo1Abbrivation));

    ofNullable(loadablePlanCommingleDetails.getCargo2Abbreviation())
        .ifPresent(cargo2Abbrivation -> builder.setCargo2Abbrivation(cargo2Abbrivation));

    ofNullable(loadablePlanCommingleDetails.getCargo1Percentage())
        .ifPresent(
            cargo1Percentage -> builder.setCargo1Percentage(String.valueOf(cargo1Percentage)));

    ofNullable(loadablePlanCommingleDetails.getCargo2Percentage())
        .ifPresent(
            cargo2Percentage -> builder.setCargo2Percentage(String.valueOf(cargo2Percentage)));

    ofNullable(loadablePlanCommingleDetails.getCargo1Mt())
        .ifPresent(cargo1Quantity -> builder.setCargo1Quantity(String.valueOf(cargo1Quantity)));

    ofNullable(loadablePlanCommingleDetails.getCargo2Mt())
        .ifPresent(cargo2Quantity -> builder.setCargo2Quantity(String.valueOf(cargo2Quantity)));

    ofNullable(loadablePlanCommingleDetails.getGrade()).ifPresent(grade -> builder.setGrade(grade));

    ofNullable(loadablePlanCommingleDetails.getQuantity())
        .ifPresent(quantity -> builder.setQuantity(String.valueOf(quantity)));

    ofNullable(loadablePlanCommingleDetails.getTankName())
        .ifPresent(tankShortName -> builder.setTankShortName(tankShortName));

    ofNullable(loadablePlanCommingleDetails.getTemperature())
        .ifPresent(temperature -> builder.setTemperature(String.valueOf(temperature)));

    ofNullable(loadablePlanCommingleDetails.getId()).ifPresent(id -> builder.setId(id));
  }

  @Override
  public void savePatternValidateResult(
      LoadablePatternAlgoRequest request, StreamObserver<AlgoReply> responseObserver) {
    AlgoReply.Builder builder = AlgoReply.newBuilder();
    try {
      loadablePatternService.savePatternValidateResult(request, builder);
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
        this.voyageService.checkIfVoyageClosed(loadableStudyOpt.get().getVoyage().getId());
        this.validateLoadableStudyWithLQ(loadableStudyOpt.get());
        this.validateLoadableStudyWithCommingle(loadableStudyOpt.get());
        ModelMapper modelMapper = new ModelMapper();
        com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy =
            new com.cpdss.loadablestudy.domain.LoadableStudy();

        buildLoadableStudy(
            request.getLoadableStudyId(), loadableStudyOpt.get(), loadableStudy, modelMapper);

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.writeValue(
            new File(
                this.rootFolder + "/json/loadableStudy_" + request.getLoadableStudyId() + ".json"),
            loadableStudy);

        this.saveJsonToDatabase(
            request.getLoadableStudyId(),
            LOADABLE_STUDY_REQUEST,
            objectMapper.writeValueAsString(loadableStudy));
        /** **Calling EW for communication server */
        // uncomment with communication service implementation
        /* EnvoyWriter.WriterReply ewReply = passRequestPayloadToEnvoyWriter(loadableStudy);
                if (!SUCCESS.equals(ewReply.getResponseStatus().getStatus())) {
                  throw new GenericServiceException(
                          "Failed to pass toWriterReply",
                          ewReply.getResponseStatus().getCode(),
                          HttpStatusCode.valueOf(Integer.valueOf(ewReply.getResponseStatus().getCode())));
                }
                this.loadableStudyRepository.updateLoadableStudyUUIDAndSeqNo(
                        ewReply.getLsUUID(), ewReply.getSequenceNo(), request.getLoadableStudyId());
                EnvoyReader.EnvoyReaderResultReply erReply = getResultFromEnvoyReader(ewReply.getLsUUID());
                if (!SUCCESS.equals(erReply.getResponseStatus().getStatus())) {
                  throw new GenericServiceException(
                          "Failed to get Result from Communication Server",
                          erReply.getResponseStatus().getCode(),
                          HttpStatusCode.valueOf(Integer.valueOf(ewReply.getResponseStatus().getCode())));
                }
                LoadablePatternAlgoRequest.Builder load = LoadablePatternAlgoRequest.newBuilder();
                load.setLoadableStudyId(request.getLoadableStudyId());
                saveLoadablePatternDetails(erReply.getPatternResultJson(), load);
        */
        AlgoResponse algoResponse =
            restTemplate.postForObject(loadableStudyUrl, loadableStudy, AlgoResponse.class);
        updateProcessIdForLoadableStudy(
            algoResponse.getProcessId(),
            loadableStudyOpt.get(),
            LOADABLE_STUDY_PROCESSING_STARTED_ID);

        loadableStudyRepository.updateLoadableStudyStatus(
            LOADABLE_STUDY_PROCESSING_STARTED_ID, loadableStudyOpt.get().getId());

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
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when generating pattern", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(e.getCode())
              .setMessage(e.getMessage())
              .setStatus(FAILED)
              .setHttpStatusCode(e.getStatus().value())
              .build());
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
                      .setHttpStatusCode(Integer.valueOf(CommonErrorCodes.E_GEN_INTERNAL_ERR))
                      .setStatus(FAILED)
                      .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /** @param loadableStudy void */
  private void validateLoadableStudyWithCommingle(LoadableStudy loadableStudy)
      throws GenericServiceException {
    List<CargoNomination> cargoNominations =
        cargoNominationRepository.findByLoadableStudyXIdAndIsActive(loadableStudy.getId(), true);
    List<com.cpdss.loadablestudy.entity.CommingleCargo> commingleCargos =
        commingleCargoRepository.findByLoadableStudyXIdAndPurposeXidAndIsActive(
            loadableStudy.getId(), 2L, true);
    if (!cargoNominations.isEmpty() && !commingleCargos.isEmpty()) {
      BigDecimal cargoSum =
          cargoNominations.stream().map(CargoNomination::getQuantity).reduce(BigDecimal::add).get();
      BigDecimal commingleCargoSum =
          commingleCargos.stream()
              .map(com.cpdss.loadablestudy.entity.CommingleCargo::getQuantity)
              .reduce(BigDecimal::add)
              .get();
      if (commingleCargoSum.compareTo(cargoSum) == 1) {
        log.info("commingle quanity is gerater for LS - {}", loadableStudy.getId());
        throw new GenericServiceException(
            "Commingle quanity is gerater for LS - {}" + loadableStudy.getId(),
            CommonErrorCodes.E_CPDSS_LS_INVALID_COMMINGLE_QUANTITY,
            HttpStatusCode.INTERNAL_SERVER_ERROR);
      }
    }
  }

  private void validateLoadableStudyWithLQ(LoadableStudy ls) throws GenericServiceException {
    List<PortRotationIdAndPortId> ports =
        loadableStudyPortRotationRepository.findAllIdAndPortIdsByLSId(ls.getId(), true);
    boolean valid = false;
    for (PortRotationIdAndPortId port : ports) {
      Optional<LoadableQuantity> lQs =
          loadableQuantityRepository.findByLSIdAndPortRotationId(ls.getId(), port.getId(), true);
      if (lQs.isPresent()) {
        valid = true;
        break;
      }
    }
    if (!valid) {
      log.info("Loadable Study Validation, No Loadable Quantity Found for Ls Id - {}", ls.getId());
      throw new GenericServiceException(
          "No Loadable Quantity Found for Loadable Study, Id " + ls.getId(),
          CommonErrorCodes.E_CPDSS_LS_INVALID_LQ,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
  }

  private EnvoyReader.EnvoyReaderResultReply getResultFromEnvoyReader(String lsUUID) {
    EnvoyReader.EnvoyReaderResultRequest.Builder request =
        EnvoyReader.EnvoyReaderResultRequest.newBuilder();
    request.setLsUUID(lsUUID);
    return this.envoyReaderGrpcService.getResultFromCommServer(request.build());
  }

  private EnvoyWriter.WriterReply passRequestPayloadToEnvoyWriter(
      com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy) throws GenericServiceException {
    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    String loadableStudyJson = null;
    try {
      VesselDetail vesselReply = this.getVesselDetailsForEnvoy(loadableStudy.getVesselId());
      loadableStudyJson = ow.writeValueAsString(loadableStudy);
      EnvoyWriter.EnvoyWriterRequest.Builder writerRequest =
          EnvoyWriter.EnvoyWriterRequest.newBuilder();
      writerRequest.setJsonPayload(loadableStudyJson);
      writerRequest.setImoNumber(vesselReply.getImoNumber());
      writerRequest.setVesselId(vesselReply.getId());
      writerRequest.setRequestType(1);
      return this.envoyWriterGrpcService.getCommunicationServer(writerRequest.build());

    } catch (JsonProcessingException e) {
      log.error("Exception when when calling EnvoyWriter  ", e);
    }
    return null;
  }

  private EnvoyWriter.WriterReply passResultPayloadToEnvoyWriter(
      LoadablePatternAlgoRequest loadablePatternAlgoRequest) throws GenericServiceException {
    String jsonPayload = null;
    try {
      // VesselDetail vesselReply = this.getVesselDetailsForEnvoy(loadableStudy.getVesselId());
      jsonPayload = JsonFormat.printer().print(loadablePatternAlgoRequest);
      EnvoyWriter.EnvoyWriterRequest.Builder writerRequest =
          EnvoyWriter.EnvoyWriterRequest.newBuilder();
      writerRequest.setJsonPayload(jsonPayload);
      // loadableStudyValue.setImoNumber(vesselReply.getImoNumber());
      // loadableStudyValue.setVesselId(vesselReply.getId());
      writerRequest.setRequestType(2);
      return this.envoyWriterGrpcService.getCommunicationServer(writerRequest.build());

    } catch (InvalidProtocolBufferException e) {
      log.error("Exception when calling passResultPayloadToEnvoyWriter  ", e);
    }
    return null;
  }

  /**
   * @param loadableStudyOpt
   * @param loadableStudy
   * @param modelMapper void
   * @throws GenericServiceException
   */
  private void buildLoadableStudy(
      Long loadableStudyId,
      LoadableStudy loadableStudyOpt,
      com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy,
      ModelMapper modelMapper)
      throws GenericServiceException {
    buildLoadableStuydDetails(Optional.of(loadableStudyOpt), loadableStudy);
    buildCargoNominationDetails(loadableStudyId, loadableStudy, modelMapper);
    buildCommingleCargoDetails(loadableStudyOpt.getId(), loadableStudy, modelMapper);
    buildLoadableQuantityDetails(loadableStudyId, loadableStudy);
    loadableStudyPortRotationService.buildLoadableStudyPortRotationDetails(
        loadableStudyId, loadableStudy, modelMapper);
    buildCargoNominationPortDetails(loadableStudyId, loadableStudy);
    buildOnHandQuantityDetails(loadableStudyOpt, loadableStudy, modelMapper);
    buildOnBoardQuantityDetails(loadableStudyOpt, loadableStudy, modelMapper);
    loadableStudyPortRotationService.buildportRotationDetails(loadableStudyOpt, loadableStudy);
    loadableStudyRuleService.buildLoadableStudyRuleDetails(
        loadableStudyOpt, loadableStudy, modelMapper);
  }

  /**
   * @param algoResponse
   * @param loadableStudy void
   */
  public void updateProcessIdForLoadableStudy(
      String processId, LoadableStudy loadableStudy, Long loadableStudyStatus) {
    LoadableStudyAlgoStatus status = new LoadableStudyAlgoStatus();
    status.setLoadableStudy(loadableStudy);
    status.setIsActive(true);
    status.setLoadableStudyStatus(loadableStudyStatusRepository.getOne(loadableStudyStatus));
    status.setProcessId(processId);
    status.setVesselxid(loadableStudy.getVesselXId());
    loadableStudyAlgoStatusRepository.save(status);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void getLoadicatorData(
      LoadicatorDataRequest request, StreamObserver<LoadicatorDataReply> responseObserver) {
    log.info("Inside getLoadicatorData service");
    LoadicatorDataReply.Builder replyBuilder = LoadicatorDataReply.newBuilder();
    try {
      LoadicatorAlgoRequest loadicator = new LoadicatorAlgoRequest();
      this.buildLoadicatorUrlRequest(request, loadicator);
      ObjectMapper objectMapper = new ObjectMapper();
      this.saveLoadicatorAlgoRequest(request, loadicator, objectMapper);
      LoadicatorAlgoResponse algoResponse =
          restTemplate.postForObject(loadicatorUrl, loadicator, LoadicatorAlgoResponse.class);
      this.saveLoadicatorAlgoResponse(request, algoResponse, objectMapper);
      if (algoResponse.getFeedbackLoop() != null) {
        if (!request.getIsPattern()) {
          if (algoResponse.getFeedbackLoop()) {
            log.info(
                "I2R Algorithm has started feedback loop for loadable study "
                    + request.getLoadableStudyId());
            this.updateFeedbackLoopParameters(
                request.getLoadableStudyId(),
                false,
                true,
                algoResponse.getFeedbackLoopCount(),
                LOADABLE_STUDY_STATUS_FEEDBACK_LOOP_STARTED);
            Optional<LoadableStudy> loadableStudyOpt =
                this.loadableStudyRepository.findByIdAndIsActive(
                    request.getLoadableStudyId(), true);
            if (loadableStudyOpt.isPresent()) {
              log.info("Deleting existing patterns");
              this.loadablePatternRepository
                  .findByLoadableStudyAndIsActive(loadableStudyOpt.get(), true)
                  .forEach(
                      loadablePattern -> {
                        log.info("Deleting loadable pattern " + loadablePattern.getId());
                        this.loadablePatternRepository.deleteLoadablePattern(
                            loadablePattern.getId());
                        loadablePatternService.deleteExistingPlanDetails(loadablePattern);
                      });
            } else {
              log.error("Loadable Study not found in database");
              throw new Exception("Loadable Study not found in database");
            }
          } else {
            log.info("Feedback Loop ended for loadable study " + request.getLoadableStudyId());
            this.updateFeedbackLoopParameters(
                request.getLoadableStudyId(),
                false,
                false,
                algoResponse.getFeedbackLoopCount(),
                LOADABLE_STUDY_STATUS_PLAN_GENERATED_ID);
            this.saveloadicatorDataForSynopticalTable(algoResponse, request.getIsPattern());
            loadableStudyAlgoStatusRepository.updateLoadableStudyAlgoStatus(
                LOADABLE_STUDY_STATUS_PLAN_GENERATED_ID, algoResponse.getProcessId(), true);
          }
        } else {
          if (algoResponse.getFeedbackLoop()) {
            log.info(
                "I2R Algorithm has started feedback loop for loadable pattern "
                    + request.getLoadicatorPatternDetails(0).getLoadablePatternId());
            this.updateFeedbackLoopParameters(
                request.getLoadicatorPatternDetails(0).getLoadablePatternId(),
                true,
                true,
                algoResponse.getFeedbackLoopCount(),
                LOADABLE_PATTERN_VALIDATION_FEEDBACK_LOOP_STARTED);
          } else {
            log.info(
                "Feedback loop ended for loadable pattern "
                    + request.getLoadicatorPatternDetails(0).getLoadablePatternId());
            this.updateFeedbackLoopParameters(
                request.getLoadicatorPatternDetails(0).getLoadablePatternId(),
                true,
                false,
                algoResponse.getFeedbackLoopCount(),
                LOADABLE_STUDY_STATUS_PLAN_GENERATED_ID);
            this.saveloadicatorDataForSynopticalTable(algoResponse, request.getIsPattern());
            loadablePatternAlgoStatusRepository.updateLoadablePatternAlgoStatus(
                LOADABLE_PATTERN_VALIDATION_SUCCESS_ID, algoResponse.getProcessId(), true);
          }
        }
      } else {
        this.saveloadicatorDataForSynopticalTable(algoResponse, request.getIsPattern());
        loadablePatternAlgoStatusRepository.updateLoadablePatternAlgoStatus(
            LOADABLE_PATTERN_VALIDATION_SUCCESS_ID, algoResponse.getProcessId(), true);
      }
      replyBuilder =
          LoadicatorDataReply.newBuilder()
              .setResponseStatus(
                  ResponseStatus.newBuilder().setMessage(SUCCESS).setStatus(SUCCESS).build());

    } catch (Exception e) {
      log.error("Exception when when getLoadicatorData ", e);
      replyBuilder =
          LoadicatorDataReply.newBuilder()
              .setResponseStatus(
                  ResponseStatus.newBuilder()
                      .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
                      .setMessage("Error when getLoadicatorData ")
                      .setStatus(FAILED)
                      .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * Update Feedback Loop parameters for loadable study/ loadable pattern
   *
   * @param id
   * @param isPattern
   * @param feedbackLoop
   * @param feedbackLoopCount
   * @param status
   */
  private void updateFeedbackLoopParameters(
      Long id, Boolean isPattern, Boolean feedbackLoop, Integer feedbackLoopCount, Long status) {
    if (isPattern) {
      this.loadablePatternRepository.updateLoadablePatternStatus(status, id);
      this.loadablePatternRepository.updateLoadablePatternFeedbackLoopAndFeedbackLoopCount(
          feedbackLoop, feedbackLoopCount, id);
    } else {
      this.loadableStudyRepository.updateLoadableStudyStatus(status, id);
      this.loadableStudyRepository.updateLoadableStudyFeedbackLoopAndFeedbackLoopCount(
          feedbackLoop, feedbackLoopCount, id);
    }
  }

  /**
   * Save Loadicator ALGO request
   *
   * @param request
   * @param loadicator
   * @param objectMapper
   */
  private void saveLoadicatorAlgoRequest(
      LoadicatorDataRequest request, LoadicatorAlgoRequest loadicator, ObjectMapper objectMapper) {
    log.info("Saving Loadicator ALGO request to database");
    try {
      if (request.getIsPattern()) {
        objectMapper.writeValue(
            new File(
                this.rootFolder
                    + "/json/loadicator_pattern_"
                    + request.getLoadicatorPatternDetails(0).getLoadablePatternId()
                    + ".json"),
            loadicator);
        this.saveJsonToDatabase(
            request.getLoadicatorPatternDetails(0).getLoadablePatternId(),
            LOADABLE_PATTERN_EDIT_LOADICATOR_REQUEST,
            objectMapper.writeValueAsString(loadicator));
      } else {
        objectMapper.writeValue(
            new File(
                this.rootFolder + "/json/loadicator_" + request.getLoadableStudyId() + ".json"),
            loadicator);
        this.saveJsonToDatabase(
            request.getLoadableStudyId(),
            LOADABLE_STUDY_LOADICATOR_REQUEST,
            objectMapper.writeValueAsString(loadicator));
      }
    } catch (Exception e) {
      log.error("Encountered error while saving loadicator request json");
    }
  }

  /**
   * Save Loadicator ALGO reponse
   *
   * @param request
   * @param response
   * @param objectMapper
   */
  private void saveLoadicatorAlgoResponse(
      LoadicatorDataRequest request, LoadicatorAlgoResponse response, ObjectMapper objectMapper) {
    log.info("Saving Loadicator ALGO response to database");
    try {
      if (request.getIsPattern()) {
        objectMapper.writeValue(
            new File(
                this.rootFolder
                    + "/json/loadicator_algo_response_pattern_"
                    + request.getLoadicatorPatternDetails(0).getLoadablePatternId()
                    + ".json"),
            response);
        this.saveJsonToDatabase(
            request.getLoadicatorPatternDetails(0).getLoadablePatternId(),
            LOADABLE_PATTERN_EDIT_LOADICATOR_RESPONSE,
            objectMapper.writeValueAsString(response));
      } else {
        objectMapper.writeValue(
            new File(
                this.rootFolder
                    + "/json/loadicator_algo_response_"
                    + request.getLoadableStudyId()
                    + ".json"),
            response);
        this.saveJsonToDatabase(
            request.getLoadableStudyId(),
            LOADABLE_STUDY_LOADICATOR_RESPONSE,
            objectMapper.writeValueAsString(response));
      }
    } catch (Exception e) {
      log.error("Encountered error while saving loadicator response json");
    }
  }

  /**
   * Save data for synoptical table
   *
   * @param algoResponse
   */
  private void saveloadicatorDataForSynopticalTable(
      LoadicatorAlgoResponse algoResponse, Boolean isPattern) {
    List<SynopticalTableLoadicatorData> entities = new ArrayList<>();
    if (isPattern) {
      algoResponse
          .getLoadicatorResults()
          .getLoadicatorResultDetails()
          .forEach(
              result -> {
                this.synopticalTableLoadicatorDataRepository
                    .deleteBySynopticalTableAndLoadablePatternId(
                        this.synopticalTableRepository.getOne(result.getSynopticalId()),
                        algoResponse.getLoadicatorResults().getLoadablePatternId());
                entities.add(
                    this.createSynopticalTableLoadicatorDataEntity(
                        algoResponse.getLoadicatorResults(), result));
              });
    } else {
      for (LoadicatorPatternDetailsResults patternDetails :
          algoResponse.getLoadicatorResultsPatternWise()) {
        patternDetails
            .getLoadicatorResultDetails()
            .forEach(
                result -> {
                  entities.add(
                      this.createSynopticalTableLoadicatorDataEntity(patternDetails, result));
                  LoadablePattern loadablePattern =
                      loadablePatternRepository.getOne(patternDetails.getLoadablePatternId());
                  loadablePattern.setIsActive(true);
                  loadablePatternRepository.save(loadablePattern);
                });
      }
    }
    this.synopticalTableLoadicatorDataRepository.saveAll(entities);
  }

  /**
   * Create SynopticalTableLoadicatorData entities
   *
   * @param patternDetails
   * @param result
   * @return SynopticalTableLoadicatorData
   */
  private SynopticalTableLoadicatorData createSynopticalTableLoadicatorDataEntity(
      LoadicatorPatternDetailsResults patternDetails, LoadicatorResultDetails result) {
    SynopticalTableLoadicatorData entity = new SynopticalTableLoadicatorData();
    entity.setLoadablePatternId(patternDetails.getLoadablePatternId());
    entity.setSynopticalTable(this.synopticalTableRepository.getOne(result.getSynopticalId()));
    entity.setActive(true);
    entity.setBlindSector(
        isEmpty(result.getDeflection()) ? null : new BigDecimal(result.getDeflection()));
    entity.setCalculatedDraftAftPlanned(
        isEmpty(result.getCalculatedDraftAftPlanned())
            ? null
            : new BigDecimal(result.getCalculatedDraftAftPlanned()));
    entity.setCalculatedDraftFwdPlanned(
        isEmpty(result.getCalculatedDraftFwdPlanned())
            ? null
            : new BigDecimal(result.getCalculatedDraftFwdPlanned()));
    entity.setCalculatedDraftMidPlanned(
        isEmpty(result.getCalculatedDraftMidPlanned())
            ? null
            : new BigDecimal(result.getCalculatedDraftMidPlanned()));
    entity.setCalculatedTrimPlanned(
        isEmpty(result.getCalculatedTrimPlanned())
            ? null
            : new BigDecimal(result.getCalculatedTrimPlanned()));
    entity.setList(isEmpty(result.getList()) ? null : new BigDecimal(result.getList()));
    entity.setBendingMoment(isEmpty(result.getBm()) ? null : new BigDecimal(result.getBm()));
    entity.setShearingForce(isEmpty(result.getSf()) ? null : new BigDecimal(result.getSf()));
    entity.setDeflection(
        isEmpty(result.getDeflection()) ? null : new BigDecimal(result.getDeflection()));
    return entity;
  }

  /**
   * @param request
   * @param loadicator void
   * @throws GenericServiceException
   */
  private void buildLoadicatorUrlRequest(
      LoadicatorDataRequest request, LoadicatorAlgoRequest loadicator)
      throws GenericServiceException {
    loadicator.setProcessId(request.getProcessId());
    loadicator.setLoadicatorPatternDetails(new ArrayList<>());

    com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.domain.LoadableStudy();
    Optional<LoadableStudy> loadableStudyOpt =
        loadableStudyRepository.findByIdAndIsActive(request.getLoadableStudyId(), true);
    if (loadableStudyOpt.isPresent()) {
      ModelMapper modelMapper = new ModelMapper();
      buildLoadableStudy(
          request.getLoadableStudyId(), loadableStudyOpt.get(), loadableStudy, modelMapper);
    }

    if (request.getIsPattern()) {
      com.cpdss.common.generated.LoadableStudy.LoadicatorPatternDetails patternDetails =
          request.getLoadicatorPatternDetails(0);
      LoadicatorPatternDetails pattern = new LoadicatorPatternDetails();
      pattern.setLoadablePatternId(patternDetails.getLoadablePatternId());
      pattern.setLdTrim(this.createLdTrim(patternDetails.getLDtrimList()));
      pattern.setLdStrength(this.createLdStrength(patternDetails.getLDStrengthList()));
      pattern.setLdIntactStability(
          this.createLdIntactStability(patternDetails.getLDIntactStabilityList()));
      Optional<LoadablePattern> loadablePatternOpt =
          this.loadablePatternRepository.findByIdAndIsActive(
              patternDetails.getLoadablePatternId(), true);
      if (loadablePatternOpt.isPresent()) {
        loadableStudy.setFeedbackLoop(
            Optional.ofNullable(loadablePatternOpt.get().getFeedbackLoop()).isPresent()
                ? loadablePatternOpt.get().getFeedbackLoop()
                : false);
        loadableStudy.setFeedbackLoopCount(
            Optional.ofNullable(loadablePatternOpt.get().getFeedbackLoopCount()).isPresent()
                ? loadablePatternOpt.get().getFeedbackLoopCount()
                : 0);
      }
      loadicator.setLoadicatorPatternDetail(pattern);
    } else {
      request
          .getLoadicatorPatternDetailsList()
          .forEach(
              patternDetails -> {
                LoadicatorPatternDetails patterns = new LoadicatorPatternDetails();
                patterns.setLoadablePatternId(patternDetails.getLoadablePatternId());
                patterns.setLdTrim(this.createLdTrim(patternDetails.getLDtrimList()));
                patterns.setLdStrength(this.createLdStrength(patternDetails.getLDStrengthList()));
                patterns.setLdIntactStability(
                    this.createLdIntactStability(patternDetails.getLDIntactStabilityList()));
                loadicator.getLoadicatorPatternDetails().add(patterns);
              });
    }

    loadicator.setLoadableStudy(loadableStudy);
  }

  /**
   * @param list
   * @return LDIntactStability
   */
  private List<LDIntactStability> createLdIntactStability(
      List<com.cpdss.common.generated.LoadableStudy.LDIntactStability> list) {
    List<LDIntactStability> ldIntactStabilities = new ArrayList<LDIntactStability>();
    list.forEach(
        lDIntactStability -> {
          LDIntactStability intactStability = new LDIntactStability();
          intactStability.setAngleatmaxrleverJudgement(
              lDIntactStability.getAngleatmaxrleverJudgement());
          intactStability.setAngleatmaxrleverValue(lDIntactStability.getAngleatmaxrleverValue());
          intactStability.setAreaofStability030Judgement(
              lDIntactStability.getAreaofStability030Judgement());
          intactStability.setAreaofStability030Value(
              lDIntactStability.getAreaofStability030Value());
          intactStability.setAreaofStability040Judgement(
              lDIntactStability.getAreaofStability040Judgement());
          intactStability.setAreaofStability040Value(
              lDIntactStability.getAreaofStability040Value());
          intactStability.setAreaofStability3040Judgement(
              lDIntactStability.getAreaofStability3040Judgement());
          intactStability.setAreaofStability3040Value(
              lDIntactStability.getAreaofStability3040Value());
          intactStability.setBigIntialGomJudgement(lDIntactStability.getBigIntialGomJudgement());
          intactStability.setBigintialGomValue(lDIntactStability.getBigintialGomValue());
          intactStability.setErrorDetails(lDIntactStability.getErrorDetails());
          intactStability.setErrorStatus(lDIntactStability.getErrorStatus());
          intactStability.setGmAllowableCurveCheckJudgement(
              lDIntactStability.getGmAllowableCurveCheckJudgement());
          intactStability.setGmAllowableCurveCheckValue(
              lDIntactStability.getGmAllowableCurveCheckValue());
          intactStability.setHeelBySteadyWindJudgement(
              lDIntactStability.getHeelBySteadyWindJudgement());
          intactStability.setHeelBySteadyWindValue(lDIntactStability.getHeelBySteadyWindValue());
          intactStability.setId(lDIntactStability.getId());
          intactStability.setMaximumRightingLeverJudgement(
              lDIntactStability.getMaximumRightingLeverJudgement());
          intactStability.setMaximumRightingLeverValue(
              lDIntactStability.getMaximumRightingLeverValue());
          intactStability.setMessageText(lDIntactStability.getMessageText());
          intactStability.setStabilityAreaBaJudgement(
              lDIntactStability.getStabilityAreaBaJudgement());
          intactStability.setStabilityAreaBaValue(lDIntactStability.getStabilityAreaBaValue());
          intactStability.setPortId(lDIntactStability.getPortId());
          intactStability.setSynopticalId(lDIntactStability.getSynopticalId());
          ldIntactStabilities.add(intactStability);
        });

    return ldIntactStabilities;
  }

  /**
   * @param list
   * @return LDStrength
   */
  private List<LDStrength> createLdStrength(
      List<com.cpdss.common.generated.LoadableStudy.LDStrength> list) {
    List<LDStrength> ldStrengths = new ArrayList<LDStrength>();
    list.forEach(
        ldStrength -> {
          LDStrength strength = new LDStrength();
          strength.setBendingMomentPersentFrameNumber(
              ldStrength.getBendingMomentPersentFrameNumber());
          strength.setBendingMomentPersentJudgement(ldStrength.getBendingMomentPersentJudgement());
          strength.setBendingMomentPersentValue(ldStrength.getBendingMomentPersentValue());
          strength.setErrorDetails(ldStrength.getErrorDetails());
          strength.setId(ldStrength.getId());
          strength.setInnerLongiBhdFrameNumber(ldStrength.getInnerLongiBhdFrameNumber());
          strength.setInnerLongiBhdJudgement(ldStrength.getInnerLongiBhdJudgement());
          strength.setInnerLongiBhdValue(ldStrength.getInnerLongiBhdValue());
          strength.setMessageText(ldStrength.getMessageText());
          strength.setOuterLongiBhdFrameNumber(ldStrength.getOuterLongiBhdFrameNumber());
          strength.setOuterLongiBhdJudgement(ldStrength.getOuterLongiBhdJudgement());
          strength.setOuterLongiBhdValue(ldStrength.getOuterLongiBhdValue());
          strength.setSfFrameNumber(ldStrength.getSfFrameNumber());
          strength.setSfHopperFrameNumber(ldStrength.getSfHopperFrameNumber());
          strength.setSfHopperJudgement(ldStrength.getSfHopperJudgement());
          strength.setSfHopperValue(ldStrength.getSfHopperValue());
          strength.setSfSideShellFrameNumber(ldStrength.getSfSideShellFrameNumber());
          strength.setSfSideShellJudgement(ldStrength.getSfSideShellJudgement());
          strength.setSfSideShellValue(ldStrength.getSfSideShellValue());
          strength.setShearingForceJudgement(ldStrength.getShearingForceJudgement());
          strength.setShearingForcePersentValue(ldStrength.getShearingForcePersentValue());
          strength.setPortId(ldStrength.getPortId());
          strength.setSynopticalId(ldStrength.getSynopticalId());
          ldStrengths.add(strength);
        });

    return ldStrengths;
  }

  /**
   * @param list
   * @return LDTrim
   */
  private List<LDTrim> createLdTrim(List<LDtrim> list) {
    List<LDTrim> ldTrims = new ArrayList<LDTrim>();
    list.forEach(
        ldTrim -> {
          LDTrim trim = new LDTrim();
          trim.setAftDraftValue(ldTrim.getAftDraftValue());
          trim.setAirDraftJudgement(ldTrim.getAirDraftJudgement());
          trim.setAirDraftValue(ldTrim.getAirDraftValue());
          trim.setDisplacementJudgement(ldTrim.getDisplacementJudgement());
          trim.setDisplacementValue(ldTrim.getDisplacementValue());
          trim.setErrorDetails(ldTrim.getErrorDetails());
          trim.setErrorStatus(ldTrim.getErrorStatus());
          trim.setForeDraftValue(ldTrim.getForeDraftValue());
          trim.setHeelValue(ldTrim.getHeelValue());
          trim.setId(ldTrim.getId());
          trim.setMaximumAllowableJudement(ldTrim.getMaximumAllowableJudement());
          trim.setMaximumAllowableVisibility(ldTrim.getMaximumAllowableVisibility());
          trim.setMaximumDraftJudgement(ldTrim.getMaximumDraftJudgement());
          trim.setMeanDraftValue(ldTrim.getMaximumDraftValue());
          trim.setMaximumDraftValue(ldTrim.getMaximumDraftValue());
          trim.setMeanDraftJudgement(ldTrim.getMeanDraftJudgement());
          trim.setMeanDraftValue(ldTrim.getMeanDraftValue());
          trim.setMessageText(ldTrim.getMessageText());
          trim.setMinimumForeDraftInRoughWeatherJudgement(
              ldTrim.getMinimumForeDraftInRoughWeatherJudgement());
          trim.setMinimumForeDraftInRoughWeatherValue(
              ldTrim.getMinimumForeDraftInRoughWeatherValue());
          trim.setTrimValue(ldTrim.getTrimValue());
          trim.setPortId(ldTrim.getPortId());
          trim.setSynopticalId(ldTrim.getSynopticalId());
          trim.setDeflection(ldTrim.getDeflection());
          ldTrims.add(trim);
        });

    return ldTrims;
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
          onBoardQuantityDto.setApi(
              null != onBoardQuantity.getDensity()
                  ? String.valueOf(onBoardQuantity.getDensity())
                  : "");
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
          modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
          onHandQuantityDto =
              modelMapper.map(onHandQuantity, com.cpdss.loadablestudy.domain.OnHandQuantity.class);
          onHandQuantityDto.setFueltypeId(onHandQuantity.getFuelTypeXId());
          onHandQuantityDto.setPortId(onHandQuantity.getPortXId());
          onHandQuantityDto.setTankId(onHandQuantity.getTankXId());
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

            ofNullable(loadableQunty.getBallast())
                .ifPresent(ballast -> loadableQuantityDto.setBallast(String.valueOf(ballast)));
            ofNullable(loadableQunty.getBoilerWaterOnBoard())
                .ifPresent(
                    boilerWaterOnBoard ->
                        loadableQuantityDto.setBoilerWaterOnBoard(
                            String.valueOf(boilerWaterOnBoard)));
            ofNullable(loadableQunty.getConstant())
                .ifPresent(constant -> loadableQuantityDto.setConstant(String.valueOf(constant)));
            ofNullable(loadableQunty.getDeadWeight())
                .ifPresent(
                    deadWeight -> loadableQuantityDto.setDeadWeight(String.valueOf(deadWeight)));
            ofNullable(loadableQunty.getDistanceFromLastPort())
                .ifPresent(
                    distanceFromLastPort ->
                        loadableQuantityDto.setDistanceFromLastPort(
                            String.valueOf(distanceFromLastPort)));
            ofNullable(loadableQunty.getDraftRestriction())
                .ifPresent(
                    draftRestriction ->
                        loadableQuantityDto.setDraftRestriction(String.valueOf(draftRestriction)));
            ofNullable(loadableQunty.getEstimatedDOOnBoard())
                .ifPresent(
                    estimatedDOOnBoard ->
                        loadableQuantityDto.setEstDOOnBoard(String.valueOf(estimatedDOOnBoard)));
            ofNullable(loadableQunty.getEstimatedFOOnBoard())
                .ifPresent(
                    estimatedFOOnBoard ->
                        loadableQuantityDto.setEstFOOnBoard(String.valueOf(estimatedFOOnBoard)));
            ofNullable(loadableQunty.getEstimatedFWOnBoard())
                .ifPresent(
                    estimatedFWOnBoard ->
                        loadableQuantityDto.setEstFreshWaterOnBoard(
                            String.valueOf(estimatedFWOnBoard)));
            ofNullable(loadableQunty.getEstimatedSagging())
                .ifPresent(
                    estimatedSagging ->
                        loadableQuantityDto.setEstSagging(String.valueOf(estimatedSagging)));
            ofNullable(loadableQunty.getFoConsumptionInSZ())
                .ifPresent(
                    foConsumptionInSZ ->
                        loadableQuantityDto.setFoConInSZ(String.valueOf(foConsumptionInSZ)));
            ofNullable(loadableQunty.getId()).ifPresent(id -> loadableQuantityDto.setId(id));
            ofNullable(loadableQunty.getOtherIfAny())
                .ifPresent(
                    otherIfAny -> loadableQuantityDto.setOtherIfAny(String.valueOf(otherIfAny)));
            ofNullable(loadableQunty.getPortId())
                .ifPresent(
                    portId -> loadableQuantityDto.setPortId(Long.valueOf(portId.toString())));
            ofNullable(loadableQunty.getRunningDays())
                .ifPresent(
                    runningDays -> loadableQuantityDto.setRunningDays(String.valueOf(runningDays)));
            ofNullable(loadableQunty.getRunningHours())
                .ifPresent(
                    runningHours ->
                        loadableQuantityDto.setRunningHours(String.valueOf(runningHours)));
            ofNullable(loadableQunty.getSaggingDeduction())
                .ifPresent(
                    saggingDeduction ->
                        loadableQuantityDto.setSaggingDeduction(String.valueOf(saggingDeduction)));
            ofNullable(loadableQunty.getSgCorrection())
                .ifPresent(
                    sgCorrection ->
                        loadableQuantityDto.setSgCorrection(String.valueOf(sgCorrection)));
            ofNullable(loadableQunty.getTotalFoConsumption())
                .ifPresent(
                    totalFoConsumption ->
                        loadableQuantityDto.setTotalFoConsumption(
                            String.valueOf(totalFoConsumption)));
            ofNullable(loadableQunty.getTotalQuantity())
                .ifPresent(
                    totalQuantity ->
                        loadableQuantityDto.setTotalQuantity(String.valueOf(totalQuantity)));
            ofNullable(loadableQunty.getTpcatDraft())
                .ifPresent(tpcatDraft -> loadableQuantityDto.setTpc(String.valueOf(tpcatDraft)));
            ofNullable(loadableQunty.getVesselAverageSpeed())
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
          commingleCargoDto.setCargo1Percentage(
              null != commingleCargo.getCargo1Pct()
                  ? commingleCargo.getCargo1Pct().toString()
                  : null);
          commingleCargoDto.setCargo2Percentage(
              null != commingleCargo.getCargo2Pct()
                  ? commingleCargo.getCargo2Pct().toString()
                  : null);
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
          CargoRequest.Builder cargoReq = CargoRequest.newBuilder();
          cargoReq.setCargoId(cargoNomination.getCargoXId());
          com.cpdss.common.generated.CargoInfo.CargoDetailReply cargoDetailReply =
              this.getCargoInfoById(cargoReq.build());
          if (cargoDetailReply.getResponseStatus().getStatus().equals(SUCCESS)) {
            log.info("Fetched cargo info of cargo with id {}", cargoNomination.getCargoXId());
            cargoNominationDto.setIsCondensateCargo(
                cargoDetailReply.getCargoDetail().getIsCondensateCargo());
            cargoNominationDto.setIsHrvpCargo(cargoDetailReply.getCargoDetail().getIsHrvpCargo());
          } else {
            log.error(
                "Could not fetch cargo info of cargo with id {}", cargoNomination.getCargoXId());
          }
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
    ofNullable(loadableStudyOpt.get().getDetails())
        .ifPresent(details -> loadableStudy.setDetails(details));
    ofNullable(loadableStudyOpt.get().getVoyage())
        .ifPresent(voyage -> loadableStudy.setVoyageId(voyage.getId()));
    ofNullable(loadableStudyOpt.get().getVoyage())
        .ifPresent(voyage -> loadableStudy.setVoyageNo(voyage.getVoyageNo()));

    ofNullable(loadableStudyOpt.get().getName()).ifPresent(name -> loadableStudy.setName(name));
    ofNullable(loadableStudyOpt.get().getCharterer())
        .ifPresent(charterer -> loadableStudy.setCharterer(charterer));
    ofNullable(loadableStudyOpt.get().getSubCharterer())
        .ifPresent(subCharterer -> loadableStudy.setSubCharterer(subCharterer));

    ofNullable(loadableStudyOpt.get().getDraftMark())
        .ifPresent(draftMark -> loadableStudy.setDraftMark(String.valueOf(draftMark)));

    ofNullable(loadableStudyOpt.get().getDraftRestriction())
        .ifPresent(
            draftRestriction ->
                loadableStudy.setDraftRestriction(String.valueOf(draftRestriction)));

    ofNullable(loadableStudyOpt.get().getLoadLineXId())
        .ifPresent(loadLineId -> loadableStudy.setLoadlineId(loadLineId));
    ofNullable(loadableStudyOpt.get().getEstimatedMaxSag())
        .ifPresent(
            estimatedMaxSag -> loadableStudy.setEstimatedMaxSG(String.valueOf(estimatedMaxSag)));
    ofNullable(loadableStudyOpt.get().getMaxAirTemperature())
        .ifPresent(
            maxAirTemperature -> loadableStudy.setMaxAirTemp(String.valueOf(maxAirTemperature)));
    ofNullable(loadableStudyOpt.get().getMaxWaterTemperature())
        .ifPresent(
            maxWaterTemperature ->
                loadableStudy.setMaxWaterTemp(String.valueOf(maxWaterTemperature)));

    if (ofNullable(loadableStudyOpt.get().getLoadOnTop()).isPresent()) {
      loadableStudy.setLoadOnTop(loadableStudyOpt.get().getLoadOnTop());
    } else {
      loadableStudy.setLoadOnTop(false);
    }
    Optional<Long> dischargeCargoId =
        ofNullable(loadableStudyOpt.get().getDischargeCargoNominationId());
    if (dischargeCargoId.isPresent() && dischargeCargoId.get().equals(new Long(0))) {
      loadableStudy.setCargoToBeDischargeFirstId(null);
    } else if (!dischargeCargoId.isPresent()) {
      loadableStudy.setCargoToBeDischargeFirstId(null);
    } else {
      loadableStudy.setCargoToBeDischargeFirstId(dischargeCargoId.get());
    }

    if (Optional.ofNullable(loadableStudyOpt.get().getFeedbackLoop()).isPresent()) {
      loadableStudy.setFeedbackLoop(loadableStudyOpt.get().getFeedbackLoop());
    } else {
      loadableStudy.setFeedbackLoop(false);
    }

    if (Optional.ofNullable(loadableStudyOpt.get().getFeedbackLoopCount()).isPresent()) {
      loadableStudy.setFeedbackLoopCount(loadableStudyOpt.get().getFeedbackLoopCount());
    } else {
      loadableStudy.setFeedbackLoopCount(0);
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
    List<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails> cargoDetailsList = null;
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
        ofNullable(entity.getCargoId()).ifPresent(builder::setCargoId);
        ofNullable(entity.getSounding()).ifPresent(item -> builder.setSounding(item.toString()));
        ofNullable(entity.getPlannedArrivalWeight())
            .ifPresent(item -> builder.setWeight(item.toString()));
        ofNullable(entity.getActualArrivalWeight())
            .ifPresent(item -> builder.setActualWeight(item.toString()));
        ofNullable(entity.getVolume()).ifPresent(item -> builder.setVolume(item.toString()));
        ofNullable(entity.getColorCode()).ifPresent(builder::setColorCode);
        ofNullable(entity.getAbbreviation()).ifPresent(builder::setAbbreviation);
        ofNullable(entity.getDensity()).ifPresent(item -> builder.setDensity(item.toString()));
      } else {
        // lazy loading the cargo history
        if (null == cargoDetailsList) {
          // cargoHistories = this.findCargoHistoryForPrvsVoyage(voyage);
          cargoDetailsList = this.findCargoDetailsForPrevVoyage(voyage);
        }

        Optional<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails> lpCargo =
            cargoDetailsList.stream()
                .filter(var -> var.getTankId().equals(tank.getTankId()))
                .findAny();
        if (lpCargo.isPresent()) {
          Optional.ofNullable(lpCargo.get().getCargoId()).ifPresent(builder::setCargoId);
          Optional.ofNullable(lpCargo.get().getColorCode()).ifPresent(builder::setColorCode);
          Optional.ofNullable(lpCargo.get().getAbbreviation()).ifPresent(builder::setAbbreviation);
          Optional.ofNullable(lpCargo.get().getApi())
              .ifPresent(v -> builder.setDensity(valueOf(v)));
          Optional.ofNullable(lpCargo.get().getActualQuantity())
              .ifPresent(v -> builder.setWeight(valueOf(v)));
          Optional.ofNullable(lpCargo.get().getTemperature())
              .ifPresent(v -> builder.setTemperature(valueOf(v)));
        }
        /*
        Optional<CargoHistory> cargoHistoryOpt =
            cargoHistories.stream().filter(e -> e.getTankId().equals(tank.getTankId())).findAny();
        if (cargoHistoryOpt.isPresent()) {
          CargoHistory dto = cargoHistoryOpt.get();
          ofNullable(dto.getCargoId()).ifPresent(builder::setCargoId);
          ofNullable(dto.getCargoColor()).ifPresent(builder::setColorCode);
          ofNullable(dto.getAbbreviation()).ifPresent(builder::setAbbreviation);
          ofNullable(dto.getQuantity()).ifPresent(item -> builder.setWeight(valueOf(item)));
          ofNullable(dto.getApi()).ifPresent(item -> builder.setDensity(valueOf(item)));
        }*/
      }
      obqDetailList.add(builder.build());
    }
    return obqDetailList;
  }

  /**
   * Cargo Deatils of Previous closed voyage's confirmed loadable study's pattern's details,
   *
   * @return
   */
  private List<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails>
      findCargoDetailsForPrevVoyage(Voyage currentVoyage) {

    if (currentVoyage.getVoyageStartDate() != null && currentVoyage.getVoyageEndDate() != null) {
      VoyageStatus voyageStatus = this.voyageStatusRepository.getOne(CLOSE_VOYAGE_STATUS);
      Voyage previousVoyage =
          this.voyageRepository
              .findFirstByVesselXIdAndIsActiveAndVoyageStatusOrderByLastModifiedDateDesc(
                  currentVoyage.getVesselXId(), true, voyageStatus);
      if (previousVoyage != null) {
        Optional<LoadableStudyStatus> confimredLSStatus =
            loadableStudyStatusRepository.findById(LoadableStudiesConstants.LS_STATUS_CONFIRMED);
        Optional<LoadableStudy> confirmedLS =
            previousVoyage.getLoadableStudies().stream()
                .filter(
                    var ->
                        var.getLoadableStudyStatus()
                            .getId()
                            .equals(confimredLSStatus.get().getId()))
                .findFirst();
        if (confirmedLS.isPresent()) {
          log.info(
              "Get On-Board-Quantity, previous voyage, LS id - {}, name - {}",
              confirmedLS.get().getId(),
              confirmedLS.get().getName());
          Optional<LoadablePattern> confirmedLP =
              loadablePatternRepository.findByLoadableStudyAndLoadableStudyStatusAndIsActive(
                  confirmedLS.get(), confimredLSStatus.get().getId(), true);
          if (confirmedLP.isPresent()) {
            List<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails> lpCargos =
                loadablePatternCargoDetailsRepository.findByLoadablePatternIdAndIsActive(
                    confirmedLP.get().getId(), true);
            log.info(
                "Get On-Board-Quantity, Cargo Deatils for LP - {}, size - {}",
                confirmedLP.get().getId(),
                lpCargos.size());
            List dep =
                lpCargos.stream()
                    .filter(
                        var ->
                            var.getOperationType()
                                .equals(LoadableStudiesConstants.OPERATION_TYPE_DEP))
                    .collect(Collectors.toList());
            log.info(
                "Get On-Board-Quantity, Cargo Deatils DEP Condition for LP - {}, size - {}",
                confirmedLP.get().getId(),
                dep.size());
            return dep;
          }
        }
      }
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
      onBoardQuantityService.saveOnBoardQuantity(request, replyBuilder);
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when saving on board quantities", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(e.getCode())
              .setMessage("GenericServiceException when saving on board quantities")
              .setStatus(FAILED)
              .setHttpStatusCode(e.getStatus().value())
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
   * @param request
   * @param responseObserver
   */
  @Override
  public void saveAlgoLoadableStudyStatus(
      AlgoStatusRequest request, StreamObserver<AlgoStatusReply> responseObserver) {
    AlgoStatusReply.Builder replyBuilder = AlgoStatusReply.newBuilder();
    try {
      log.info("Inside saveAlgoLoadableStudyStatus service");
      Optional<LoadableStudyAlgoStatus> loadableStudyAlgoStatusOpt =
          loadableStudyAlgoStatusRepository.findByProcessIdAndIsActive(
              request.getProcesssId(), true);
      if (!loadableStudyAlgoStatusOpt.isPresent()) {
        log.info("Invalid process id for updating loadable study status");
        replyBuilder.setResponseStatus(
            ResponseStatus.newBuilder()
                .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST)
                .setMessage("Invalid process Id")
                .build());
      } else {
        log.info(
            "updated algo status with process-id "
                + request.getProcesssId()
                + " to "
                + request.getLoadableStudystatusId());
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

      this.voyageService.checkIfVoyageClosed(loadableStudyOpt.get().getVoyage().getId());

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
          this.saveSynopticalLoadicatorData(entity, request.getLoadablePatternId(), record);
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
              .setHttpStatusCode(e.getStatus().value())
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
            .findByLoadablePatternIdAndPortRotationIdAndOperationTypeAndIsActive(
                loadablePatternId,
                entity.getLoadableStudyPortRotation().getId(),
                entity.getOperationType(),
                true);
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
        } else {
          LoadablePlanStowageBallastDetails ent = new LoadablePlanStowageBallastDetails();
          ent.setTankXId(rec.getTankId());
          ent.setIsActive(true);
          ent.setLoadablePatternId(loadablePatternId);
          ent.setPortXId(entity.getPortXid());
          ent.setOperationType(entity.getOperationType());
          ent.setPortRotationId(entity.getLoadableStudyPortRotation().getId());
          ent.setActualQuantity(
              isEmpty(rec.getActualWeight()) ? null : new BigDecimal(rec.getActualWeight()));
          toBeSaved.add(ent);
        }
      }
      this.loadablePlanStowageBallastDetailsRepository.saveAll(toBeSaved);
    }
  }

  private void saveSynopticalOhqData(
      LoadableStudy loadableStudy, SynopticalTable entity, SynopticalRecord record)
      throws GenericServiceException {
    List<OnHandQuantity> ohqEntities =
        this.onHandQuantityRepository.findByLoadableStudyAndPortRotationAndIsActive(
            loadableStudy, entity.getLoadableStudyPortRotation(), true);
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
        ohqEntity.setFuelTypeXId(ohqRecord.getFuelTypeId());
        ohqEntity.setLoadableStudy(loadableStudy);
        ohqEntity.setPortRotation(entity.getLoadableStudyPortRotation());
        ohqEntity.setIsActive(true);
      }
      this.validateSaveSynopticalOhqData(ohqEntity, entity, ohqRecord, loadableStudy);

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
    List<Long> portIds = loadableStudyPortRotationService.getPortRoationPortIds(loadableStudy);
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
            .findByLoadablePatternIdAndPortRotationIdAndOperationTypeAndIsActive(
                request.getLoadablePatternId(),
                entity.getLoadableStudyPortRotation().getId(),
                entity.getOperationType(),
                true);

    List<com.cpdss.loadablestudy.entity.LoadablePlanComminglePortwiseDetails>
        commingleCargoEntities =
            this.loadablePlanCommingleDetailsPortwiseRepository
                .findByLoadablePatternIdAndPortRotationIdAndOperationTypeAndIsActive(
                    request.getLoadablePatternId(),
                    entity.getLoadableStudyPortRotation().getId(),
                    entity.getOperationType(),
                    true);
    List<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails> toBeSavedCargoList =
        new ArrayList<>();

    List<com.cpdss.loadablestudy.entity.LoadablePlanComminglePortwiseDetails>
        toBeSavedCommingleCargoList = new ArrayList<>();

    for (SynopticalCargoRecord cargoRecord : record.getCargoList()) {
      if (!cargoRecord.getIsCommingleCargo()) {
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
        } else {
          com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails cargoEntity =
              new com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails();
          cargoEntity.setLoadablePatternId(request.getLoadablePatternId());
          cargoEntity.setPortId(entity.getPortXid());
          cargoEntity.setTankId(cargoRecord.getTankId());
          cargoEntity.setIsActive(true);
          cargoEntity.setOperationType(entity.getOperationType());
          cargoEntity.setPortRotationId(entity.getLoadableStudyPortRotation().getId());
          cargoEntity.setActualQuantity(
              isEmpty(cargoRecord.getActualWeight())
                  ? null
                  : new BigDecimal(cargoRecord.getActualWeight()));
          toBeSavedCargoList.add(cargoEntity);
        }
      } else {
        Optional<com.cpdss.loadablestudy.entity.LoadablePlanComminglePortwiseDetails>
            comminleEntityOpt =
                commingleCargoEntities.stream()
                    .filter(
                        commingleCargo ->
                            commingleCargo.getTankId().equals(commingleCargo.getTankId()))
                    .findAny();
        if (comminleEntityOpt.isPresent()) {
          com.cpdss.loadablestudy.entity.LoadablePlanComminglePortwiseDetails commingleCargoEntity =
              comminleEntityOpt.get();

          commingleCargoEntity.setActualQuantity(
              isEmpty(cargoRecord.getActualWeight())
                  ? null
                  : new BigDecimal(cargoRecord.getActualWeight()));

          toBeSavedCommingleCargoList.add(commingleCargoEntity);
        } else {
          com.cpdss.loadablestudy.entity.LoadablePlanComminglePortwiseDetails commingleCargoEntity =
              new com.cpdss.loadablestudy.entity.LoadablePlanComminglePortwiseDetails();

          LoadablePattern loadablePattern =
              this.loadablePatternRepository.getOne(request.getLoadablePatternId());
          commingleCargoEntity.setLoadablePattern(loadablePattern);
          commingleCargoEntity.setPortId(entity.getPortXid());
          commingleCargoEntity.setTankId(cargoRecord.getTankId());
          commingleCargoEntity.setIsActive(true);
          commingleCargoEntity.setOperationType(entity.getOperationType());
          commingleCargoEntity.setPortRotationXid(entity.getLoadableStudyPortRotation().getId());
          commingleCargoEntity.setActualQuantity(
              isEmpty(cargoRecord.getActualWeight())
                  ? null
                  : new BigDecimal(cargoRecord.getActualWeight()));

          toBeSavedCommingleCargoList.add(commingleCargoEntity);
        }
      }
    }
    if (!toBeSavedCargoList.isEmpty()) {
      this.loadablePatternCargoDetailsRepository.saveAll(toBeSavedCargoList);
    }

    if (!toBeSavedCommingleCargoList.isEmpty()) {
      this.loadablePlanCommingleDetailsPortwiseRepository.saveAll(toBeSavedCommingleCargoList);
    }
  }

  /**
   * Save obq data from synoptical table
   *
   * @param loadableStudy
   * @param record
   * @throws GenericServiceException
   */
  private void saveSynopticalObqData(LoadableStudy loadableStudy, SynopticalRecord record)
      throws GenericServiceException {
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

      this.validateSaveSynopticalObqData(obqEntity, cargoRecord, loadableStudy);

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
  public void saveSynopticalLoadicatorData(
      SynopticalTable entity, Long loadablepatternId, SynopticalRecord record)
      throws GenericServiceException {
    com.cpdss.common.generated.LoadableStudy.SynopticalTableLoadicatorData data =
        record.getLoadicatorData();
    SynopticalTableLoadicatorData ldEntity =
        this.synopticalTableLoadicatorDataRepository
            .findBySynopticalTableAndLoadablePatternIdAndIsActive(entity, loadablepatternId, true);
    if (null == ldEntity) {
      log.info(
          "Loadicator data does not exist for given synoptical record with id {}", record.getId());
    } else {
      ldEntity.setDeflection(
          isEmpty(data.getDeflection()) ? null : new BigDecimal(data.getDeflection()));
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

    Optional<LoadableStudy> loadableStudy =
        this.loadableStudyRepository.findByIdAndIsActive(entity.getLoadableStudyXId(), true);
    if (!loadableStudy.isPresent()) {
      throw new GenericServiceException(
          "Loadable study with given id does not exist",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    this.validateSaveSynopticalEtaEtdEstimates(entity, record, loadableStudy.get(), prEntity);

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
    if (!isEmpty(record.getSpecificGravity())) {
      prEntity.setSeaWaterDensity(new BigDecimal(record.getSpecificGravity()));
    }
    this.loadableStudyPortRotationRepository.save(prEntity);
  }

  /**
   * Populate synoptical entity fields
   *
   * @param entity
   * @param request
   * @return
   * @throws GenericServiceException
   */
  public SynopticalTable buildSynopticalTableEntity(SynopticalTable entity, SynopticalRecord record)
      throws GenericServiceException {
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
   * @throws GenericServiceException
   */
  private void buidlSynopticalTableVesselData(SynopticalTable entity, SynopticalRecord record)
      throws GenericServiceException {

    Optional<LoadableStudy> loadableStudy =
        this.loadableStudyRepository.findByIdAndIsActive(entity.getLoadableStudyXId(), true);
    if (!loadableStudy.isPresent()) {
      throw new GenericServiceException(
          "Loadable study with given id does not exist",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }

    this.validateSynopticalVesselData(loadableStudy.get(), entity, record);

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
      synopticService.getSynopticalTable(request, replyBuilder);
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
   * Call vessel info grpc service for synoptical table data
   *
   * @param request
   * @return
   */
  public VesselReply getVesselDetailForSynopticalTable(VesselRequest request) {
    return this.vesselInfoGrpcService.getVesselDetailForSynopticalTable(request);
  }

  @Override
  public void getAlgoErrors(
      AlgoErrorRequest request, StreamObserver<AlgoErrorReply> responseObserver) {
    log.info("inside getAlgoErrors loadable study service");
    AlgoErrorReply.Builder replyBuilder = AlgoErrorReply.newBuilder();
    try {
      if (request.getLoadablePatternId() != 0) {
        Optional<LoadablePattern> loadablePatternOpt =
            this.loadablePatternRepository.findByIdAndIsActive(
                request.getLoadablePatternId(), true);
        if (!loadablePatternOpt.isPresent()) {
          log.info(INVALID_LOADABLE_PATTERN_ID, request.getLoadablePatternId());
          throw new GenericServiceException(
              INVALID_LOADABLE_PATTERN_ID,
              CommonErrorCodes.E_HTTP_BAD_REQUEST,
              HttpStatusCode.BAD_REQUEST);

        } else {
          log.info("inside getAlgoErrors loadable study service - getting error details");
          buildLoadablePatternErrorDetails(loadablePatternOpt.get(), replyBuilder);
        }
      } else {
        if (request.getLoadableStudyId() > 0) {
          Optional<LoadableStudy> ls =
              loadableStudyRepository.findByIdAndIsActive(request.getLoadableStudyId(), true);
          if (!ls.isPresent()) {
            log.info(INVALID_LOADABLE_STUDY_ID, request.getLoadablePatternId());
            throw new GenericServiceException(
                INVALID_LOADABLE_STUDY_ID,
                CommonErrorCodes.E_HTTP_BAD_REQUEST,
                HttpStatusCode.BAD_REQUEST);
          }
          log.info("Algo Error Fetch, Loadable Study, id - {}", ls.get().getId());
          algoErrorService.buildLoadableStudyErrorDetails(ls.get(), replyBuilder);
        }
      }
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when fetching loadable study - getAlgoErrors", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(e.getCode())
              .setMessage(e.getMessage())
              .setStatus(FAILED)
              .build());

    } catch (Exception e) {
      log.error("Exception when getAlgoErrors ", e);
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(FAILED));
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * @param loadablePattern
   * @param replyBuilder void
   */
  private void buildLoadablePatternErrorDetails(
      LoadablePattern loadablePattern,
      com.cpdss.common.generated.LoadableStudy.AlgoErrorReply.Builder replyBuilder) {

    Optional<List<AlgoErrorHeading>> alogError =
        algoErrorHeadingRepository.findByLoadablePatternAndIsActive(loadablePattern, true);
    if (alogError.isPresent()) {
      log.info("Adding ALGO error");
      for (AlgoErrorHeading errorHeading : alogError.get()) {
        AlgoErrors.Builder errorBuilder = AlgoErrors.newBuilder();

        Optional<List<com.cpdss.loadablestudy.entity.AlgoErrors>> algoError =
            algoErrorsRepository.findByAlgoErrorHeadingAndIsActive(errorHeading, true);
        if (algoError.isPresent()) {
          List<String> res = new ArrayList<>();
          res.addAll(
              algoError.get().stream()
                  .map(val -> val.getErrorMessage())
                  .collect(Collectors.toList()));
          errorBuilder.addAllErrorMessages(res);
        }

        errorBuilder.setErrorHeading(errorHeading.getErrorHeading());
        replyBuilder.addAlgoErrors(errorBuilder);
      }
    }
    replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
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
        Optional<LoadableStudy> ls =
            loadableStudyRepository.findByIdAndIsActive(
                loadablePatternOpt.get().getLoadableStudy().getId(), true);
        boolean status = this.validateLoadableStudyForConfimPlan(ls.get());
        replyBuilder.setConfirmPlanEligibility(status);
        loadablePlanService.buildLoadablePlanDetails(loadablePatternOpt, replyBuilder);
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
   * @param request
   * @param responseObserver
   */
  @Override
  public void confirmPlanStatus(
      ConfirmPlanRequest request, StreamObserver<ConfirmPlanReply> responseObserver) {
    log.info("inside confirmPlanStatus loadable study service");
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
        List<LoadablePatternAlgoStatus> patternStatus =
            loadablePatternAlgoStatusRepository.findByLoadablePatternAndIsActive(
                loadablePatternOpt.get(), true);
        if (!patternStatus.isEmpty()) {
          replyBuilder.setLoadablePatternStatusId(
              patternStatus.get(patternStatus.size() - 1).getLoadableStudyStatus().getId());
        }

        if (!patternStatus.isEmpty()) {
          if (stowageDetailsTempRepository
                  .findByLoadablePatternAndIsActive(loadablePatternOpt.get(), true)
                  .isEmpty()
              || VALIDATED_CONDITIONS.contains(replyBuilder.getLoadablePatternStatusId())) {
            replyBuilder.setValidated(true);
          }
        } else {
          if (stowageDetailsTempRepository
              .findByLoadablePatternAndIsActive(loadablePatternOpt.get(), true)
              .isEmpty()) {
            replyBuilder.setValidated(true);
          }
        }
        List<LoadablePattern> loadablePatternConfirmedOpt =
            loadablePatternRepository.findByVoyageAndLoadableStudyStatusAndIsActive(
                request.getVoyageId(), CONFIRMED_STATUS_ID, true);
        if (!loadablePatternConfirmedOpt.isEmpty()) {
          // set confirm status to false since some other plan is already confirmed
          log.info("other plan is in confirmed status or verification pending");
          replyBuilder.setConfirmed(false);
        } else {
          log.info("plan is okay to confirm");

          replyBuilder.setConfirmed(true);
        }
        replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
      }
    } catch (Exception e) {
      log.error("Exception when confirmPlanStatus ", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setStatus(FAILED)
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Exception when confirmPlanStatus Loadable Study Status"));
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
          log.info("changing status of other confirmed plan to plan generated");
          loadablePatternRepository.updateLoadablePatternStatusToPlanGenerated(
              LOADABLE_STUDY_STATUS_PLAN_GENERATED_ID, loadablePatternConfirmedOpt.get(0).getId());
          loadablePatternRepository.updateLoadableStudyStatusToPlanGenerated(
              LOADABLE_STUDY_STATUS_PLAN_GENERATED_ID,
              loadablePatternConfirmedOpt.get(0).getLoadableStudy().getId());
        }
        log.info("confirming selected plan");
        loadablePatternRepository.updateLoadablePatternStatus(
            CONFIRMED_STATUS_ID, loadablePatternOpt.get().getId());
        loadableStudyRepository.updateLoadableStudyStatus(
            CONFIRMED_STATUS_ID, loadablePatternOpt.get().getLoadableStudy().getId());
        replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
      }
    } catch (Exception e) {
      log.error("Exception when confirmPlan ", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setStatus(FAILED)
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Exception when confirmPlan Loadable Study Status"));

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
      if (0 == request.getLoadablePatternId()) {
        log.info("Inside getLoadableStudyStatus");
        Optional<LoadableStudyAlgoStatus> loadableStudyAlgoStatusOpt =
            loadableStudyAlgoStatusRepository.findByLoadableStudyIdAndProcessIdAndIsActive(
                request.getLoadableStudyId(), request.getProcessId(), true);
        if (!loadableStudyAlgoStatusOpt.isPresent()) {
          log.info("Invalid loadable study Id");
          replyBuilder.setResponseStatus(
              ResponseStatus.newBuilder()
                  .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST)
                  .setMessage("Invalid loadable study Id")
                  .build());
        } else {
          replyBuilder.setLoadableStudystatusId(
              loadableStudyAlgoStatusOpt.get().getLoadableStudyStatus().getId());
          replyBuilder.setLoadableStudyStatusLastModifiedTime(
              loadableStudyAlgoStatusOpt.get().getLastModifiedDateTime().toString());
          replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
        }
      } else {
        log.info("Inside getLoadablePatternStatus");
        Optional<LoadablePatternAlgoStatus> loadablePatternAlgoStatusOpt =
            loadablePatternAlgoStatusRepository.findByLoadablePatternIdAndProcessIdAndIsActive(
                request.getLoadablePatternId(), request.getProcessId(), true);
        if (!loadablePatternAlgoStatusOpt.isPresent()) {
          log.info("Invalid loadable pattern Id");
          replyBuilder.setResponseStatus(
              ResponseStatus.newBuilder()
                  .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST)
                  .setMessage("Invalid loadable pattern Id")
                  .build());
        } else {
          replyBuilder.setLoadableStudystatusId(
              loadablePatternAlgoStatusOpt.get().getLoadableStudyStatus().getId());
          replyBuilder.setLoadableStudyStatusLastModifiedTime(
              loadablePatternAlgoStatusOpt.get().getLastModifiedDateTime().toString());
          replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
        }
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
          this.loadableStudyRepository.findByIdAndIsActive(request.getLoadableStudyId(), true);
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
            synopticService.getSynopticalTableVesselData(request, loadableStudyOpt.get());
        // sorting the tanks based on tank display order from vessel tank table
        List<VesselTankDetail> sortedTankList = new ArrayList<>(vesselReply.getVesselTanksList());
        Collections.sort(
            sortedTankList, Comparator.comparing(VesselTankDetail::getTankDisplayOrder));
        synopticService.buildSynopticalTableReply(
            requestBuilder.build(),
            synopticalTableList,
            synopticService.getSynopticalTablePortDetails(synopticalTableList),
            synopticService.getSynopticalTablePortRotations(loadableStudyOpt.get()),
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
        // build cargo layout tanks not available in synoptical
        replyBuilder.addAllCargoTanks(
            this.groupTanks(
                sortedTankList.stream()
                    .filter(
                        tankList -> CARGO_TANK_CATEGORIES.contains(tankList.getTankCategoryId()))
                    .collect(Collectors.toList())));
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
      synopticService.getSynopticalPortDataByPortId(request, replyBuilder);
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

  @Transactional
  public void checkDuplicatedFromAndCloneEntity(LoadableStudyDetail request, LoadableStudy entity)
      throws GenericServiceException {

    if (0 != request.getDuplicatedFromId()) {
      try {

        List<CargoNomination> cargoNominationList =
            this.cargoNominationRepository.findByLoadableStudyXIdAndIsActiveOrderById(
                request.getDuplicatedFromId(), true);
        Map<Long, Long> cargoNominationIdMap = new HashMap<>();
        if (!cargoNominationList.isEmpty()) {
          cargoNominationList.forEach(
              cargoNomination -> {
                Long id = cargoNomination.getId();
                CargoNomination crgoNomination = new CargoNomination();
                List<CargoNominationPortDetails> oldCargoNominationPortDetails =
                    this.cargoNominationOperationDetailsRepository
                        .findByCargoNominationnAndIsActive(cargoNomination, true);

                BeanUtils.copyProperties(cargoNomination, crgoNomination);
                crgoNomination.setLoadableStudyXId(entity.getId());
                crgoNomination.setId(null);
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
                CargoNomination ent = this.cargoNominationRepository.save(crgoNomination);
                cargoNominationIdMap.put(id, ent.getId());
              });
        }

        List<LoadableStudyPortRotation> loadableStudyPortRotationParentList =
            this.loadableStudyPortRotationRepository.findByLoadableStudyAndIsActive(
                request.getDuplicatedFromId(), true);
        List<LoadableStudyPortRotation> loadableStudyDuplicatedPorts = new ArrayList<>();
        if (!loadableStudyPortRotationParentList.isEmpty()) {
          for (LoadableStudyPortRotation loadableStudyPortRotation :
              loadableStudyPortRotationParentList) {
            entityManager.detach(loadableStudyPortRotation);
            loadableStudyPortRotation.setId(null);
            loadableStudyPortRotation.setLoadableStudy(entity);
            loadableStudyDuplicatedPorts.add(loadableStudyPortRotation);
          }
          loadableStudyDuplicatedPorts =
              this.loadableStudyPortRotationRepository.saveAll(loadableStudyDuplicatedPorts);
        }

        Optional<LoadableStudy> loadableStudyOpt =
            this.loadableStudyRepository.findByIdAndIsActive(request.getDuplicatedFromId(), true);
        if (!loadableStudyOpt.isPresent()) {
          throw new GenericServiceException(
              "Loadable study does not exist",
              CommonErrorCodes.E_HTTP_BAD_REQUEST,
              HttpStatusCode.BAD_REQUEST);
        }
        entity.setDischargeCargoNominationId(
            loadableStudyOpt.get().getDischargeCargoNominationId());
        entity.setLoadOnTop(loadableStudyOpt.get().getLoadOnTop());
        entity.setIsCargoNominationComplete(loadableStudyOpt.get().getIsCargoNominationComplete());
        entity.setIsDischargePortsComplete(loadableStudyOpt.get().getIsDischargePortsComplete());
        entity.setIsObqComplete(loadableStudyOpt.get().getIsObqComplete());
        entity.setIsOhqComplete(loadableStudyOpt.get().getIsOhqComplete());
        entity.setIsPortsComplete(loadableStudyOpt.get().getIsPortsComplete());

        List<OnHandQuantity> onHandQuantityList =
            this.onHandQuantityRepository.findByLoadableStudyAndIsActive(
                loadableStudyOpt.get(), true);
        if (!onHandQuantityList.isEmpty()) {
          List<OnHandQuantity> OnHandQuantities = new ArrayList<OnHandQuantity>();

          for (OnHandQuantity onHandQuantity : onHandQuantityList) {

            Optional<LoadableStudyPortRotation> duplicated =
                loadableStudyDuplicatedPorts.stream()
                    .filter(
                        port ->
                            port.getPortXId().equals(onHandQuantity.getPortXId())
                                && onHandQuantity.getPortRotation() != null
                                && port.getOperation()
                                    .getId()
                                    .equals(onHandQuantity.getPortRotation().getOperation().getId())
                                && port.getPortOrder()
                                    .equals(onHandQuantity.getPortRotation().getPortOrder()))
                    .findAny();

            if (!duplicated.isPresent()) {
              throw new GenericServiceException(
                  "Could not find the duplicated port rotation entity",
                  CommonErrorCodes.E_GEN_INTERNAL_ERR,
                  HttpStatusCode.BAD_REQUEST);
            }

            entityManager.detach(onHandQuantity);
            onHandQuantity.setId(null);
            onHandQuantity.setLoadableStudy(entity);
            onHandQuantity.setActualArrivalQuantity(null);
            onHandQuantity.setActualDepartureQuantity(null);
            onHandQuantity.setPortRotation(duplicated.get());
            OnHandQuantities.add(onHandQuantity);
          }
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
                onBoardQuantity.setActualArrivalWeight(null);
                onBoardQuantity.setActualDepartureWeight(null);
                OnBoardQuantities.add(onBoardQuantity);
              });
          this.onBoardQuantityRepository.saveAll(OnBoardQuantities);
        }

        List<LoadableQuantity> loadableQuantityList =
            this.loadableQuantityRepository.findByLoadableStudyXIdAndIsActive(
                request.getDuplicatedFromId(), true);
        if (!loadableQuantityList.isEmpty()) {
          List<LoadableQuantity> loadableQuantities = new ArrayList<LoadableQuantity>();

          for (LoadableQuantity oldLQ : loadableQuantityList) {
            entityManager.detach(oldLQ);
            oldLQ.setId(null);
            oldLQ.setLoadableStudyXId(entity);
            log.info(
                "Duplicate loadable quantity From LS {}, LQ Port Rotation {}",
                request.getDuplicatedFromId(),
                oldLQ.getLoadableStudyPortRotation().getId());
            this.setNewPortRotationIdForNewLS(oldLQ, loadableStudyDuplicatedPorts);
            log.info(
                "Duplicate loadable quantity To LS {}, LQ Port Rotation {}",
                entity.getId(),
                oldLQ.getLoadableStudyPortRotation().getId());
            loadableQuantities.add(oldLQ);
          }
          this.loadableQuantityRepository.saveAll(loadableQuantities);
        }

        List<com.cpdss.loadablestudy.entity.CommingleCargo> CommingleCargoList =
            this.commingleCargoRepository.findByLoadableStudyXIdAndIsActive(
                request.getDuplicatedFromId(), true);
        if (!CommingleCargoList.isEmpty()) {
          List<com.cpdss.loadablestudy.entity.CommingleCargo> CommingleCargos = new ArrayList<>();

          CommingleCargoList.forEach(
              commingleCargo -> {
                entityManager.detach(commingleCargo);
                commingleCargo.setId(null);
                commingleCargo.setLoadableStudyXId(entity.getId());
                commingleCargo.setCargoNomination1Id(
                    cargoNominationIdMap.get(commingleCargo.getCargoNomination1Id()));
                commingleCargo.setCargoNomination2Id(
                    cargoNominationIdMap.get(commingleCargo.getCargoNomination2Id()));
                CommingleCargos.add(commingleCargo);
              });
          this.commingleCargoRepository.saveAll(CommingleCargos);
        }

        List<SynopticalTable> synopticalTableList =
            this.synopticalTableRepository.findByLoadableStudyXIdAndIsActive(
                request.getDuplicatedFromId(), true);

        if (!synopticalTableList.isEmpty()) {
          List<SynopticalTable> synopticalTables = new ArrayList<>();
          for (SynopticalTable synopticalTable : synopticalTableList) {
            LoadableStudyPortRotation parent = synopticalTable.getLoadableStudyPortRotation();
            Optional<LoadableStudyPortRotation> duplicated =
                loadableStudyDuplicatedPorts.stream()
                    .filter(
                        port ->
                            port.getPortXId().equals(parent.getPortXId())
                                && port.getOperation().getId().equals(parent.getOperation().getId())
                                && port.getPortOrder().equals(parent.getPortOrder()))
                    .findAny();
            if (!duplicated.isPresent()) {
              throw new GenericServiceException(
                  "Could not find the duplicated port rotation entity",
                  CommonErrorCodes.E_GEN_INTERNAL_ERR,
                  HttpStatusCode.BAD_REQUEST);
            }
            entityManager.detach(synopticalTable);
            synopticalTable.setId(null);
            synopticalTable.setLoadableStudyPortRotation(duplicated.get());
            synopticalTable.setLoadableStudyXId(entity.getId());
            synopticalTable.setEtaActual(null);
            synopticalTable.setEtdActual(null);
            synopticalTable.setConstantActual(null);
            synopticalTable.setDisplacementActual(null);
            synopticalTable.setConstantActual(null);
            synopticalTable.setDeadWeightActual(null);
            synopticalTable.setOthersActual(null);

            synopticalTables.add(synopticalTable);
          }
          this.synopticalTableRepository.saveAll(synopticalTables);
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

  private void setNewPortRotationIdForNewLS(
      LoadableQuantity lq, List<LoadableStudyPortRotation> newPrList) {
    if (lq != null && lq.getLoadableStudyPortRotation() != null) {
      LoadableStudyPortRotation pr = lq.getLoadableStudyPortRotation();
      if (pr.isActive()) {
        Optional<LoadableStudyPortRotation> portRotaionIdToAdd =
            newPrList.stream()
                .filter(
                    var ->
                        (pr.getPortXId().equals(var.getPortXId()))
                            && (pr.getOperation().getId().equals(var.getOperation().getId())))
                .findFirst();
        lq.setLoadableStudyPortRotation(portRotaionIdToAdd.get());
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
      builder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
      builder.setFilePath(this.rootFolder + attachment.getFilePath());
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
      loadableStudyPortRotationService.saveLoadableStudyPortRotationList(request, replyBuilder);
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when saveLoadableStudyPortRotationList", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(e.getCode())
              .setMessage(e.getMessage())
              .setStatus(FAILED)
              .setHttpStatusCode(e.getStatus().value())
              .build());
    } catch (Exception e) {
      log.error("Exception when saveLoadableStudyPortRotationList", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Exception when saving port data")
              .setStatus(FAILED)
              .setHttpStatusCode(Integer.valueOf(CommonErrorCodes.E_GEN_INTERNAL_ERR))
              .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
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
      this.loadablePlanCommentsRepository.save(entity);
      if (entity.getId() != null) {
        SaveCommentRequest.Builder comment = SaveCommentRequest.newBuilder();
        comment.setComment(entity.getComments());
        comment.setCommentId(entity.getId());
        comment.setCreateDate(
            DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).format(entity.getCreatedDateTime()));
        comment.setUpdateDate(
            DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).format(entity.getLastModifiedDateTime()));
        try {
          comment.setUser(Long.valueOf(entity.getCreatedBy()));
        } catch (Exception e) {
          log.error(
              "Failed to parse user id {}, error - {}", entity.getCreatedBy(), e.getMessage());
        }
        comment.build();
        replyBuilder.setComment(comment);
        log.info(
            "Save Comment, saved for Pattern id {}, Comment {}",
            request.getLoadablePatternId(),
            entity.getComments());
      }

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

  public void saveLoadOnTop(
      com.cpdss.common.generated.LoadableStudy.SaveLoadOnTopRequest request,
      io.grpc.stub.StreamObserver<SaveCommentReply> responseObserver) {
    SaveCommentReply.Builder replyBuilder = SaveCommentReply.newBuilder();
    try {
      Optional<LoadableStudy> loadableStudyOpt =
          this.loadableStudyRepository.findByIdAndIsActive(request.getLoadableStudyId(), true);

      if (!loadableStudyOpt.isPresent()) {
        throw new GenericServiceException(
            "LoadableStudy does not exist",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      loadablePatternService.isPatternGeneratedOrConfirmed(loadableStudyOpt.get());
      this.voyageService.checkIfVoyageClosed(loadableStudyOpt.get().getVoyage().getId());

      LoadableStudy entity = loadableStudyOpt.get();
      entity.setLoadOnTop(request.getLoadOnTop());
      entity = this.loadableStudyRepository.save(entity);

      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder().setStatus(SUCCESS).setMessage(SUCCESS).build());
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when saving load on top", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(e.getCode())
              .setMessage(e.getMessage())
              .setStatus(FAILED)
              .setHttpStatusCode(e.getStatus().value())
              .build());
    } catch (Exception e) {
      log.error("Error saving load on top", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Error saving load on top")
              .setStatus(FAILED)
              .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  private VesselDetail getVesselDetailsForEnvoy(Long vesselId) throws GenericServiceException {
    VesselInfo.VesselIdRequest replyBuilder =
        VesselInfo.VesselIdRequest.newBuilder().setVesselId(vesselId).build();
    VesselInfo.VesselIdResponse vesselResponse = this.getVesselInfoByVesselId(replyBuilder);
    if (!SUCCESS.equalsIgnoreCase(vesselResponse.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Error in calling vessel service",
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }

    return vesselResponse.getVesselDetail();
  }

  public CargoReply getCargoInfo(CargoRequest build) {
    return cargoInfoGrpcService.getCargoInfo(build);
  }

  public VesselInfo.VesselIdResponse getVesselInfoByVesselId(
      VesselInfo.VesselIdRequest replyBuilder) {
    return this.vesselInfoGrpcService.getVesselInfoByVesselId(replyBuilder);
  }

  @Override
  public void getVoyages(VoyageRequest request, StreamObserver<VoyageListReply> responseObserver) {
    VoyageListReply.Builder builder = VoyageListReply.newBuilder();
    try {
      voyageService.getVoyages(request, builder);
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

  /**
   * Method to generate loadable plan report
   *
   * @param request LoadablePlanReportRequest object
   * @param responseObserver StreamObserver<LoadablePlanReportReply> object
   */
  @Override
  public void getLoadablePlanReport(
      com.cpdss.common.generated.LoadableStudy.LoadablePlanReportRequest request,
      StreamObserver<com.cpdss.common.generated.LoadableStudy.LoadablePlanReportReply>
          responseObserver) {
    com.cpdss.common.generated.LoadableStudy.LoadablePlanReportReply.Builder dataChunkBuilder =
        com.cpdss.common.generated.LoadableStudy.LoadablePlanReportReply.newBuilder();
    //      Create workbook
    try (XSSFWorkbook workbook = new XSSFWorkbook()) {
      loadablePlanService.getLoadablePlanReport(workbook, request, dataChunkBuilder);
    } catch (Exception e) {
      log.error("Error in getLoadablePlanReport method ", e);
      dataChunkBuilder.setResponseStatus(
          StatusReply.newBuilder()
              .setStatus(FAILED)
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .build());
    } finally {
      responseObserver.onNext(dataChunkBuilder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void saveVoyageStatus(
      SaveVoyageStatusRequest request, StreamObserver<SaveVoyageStatusReply> responseObserver) {
    SaveVoyageStatusReply.Builder replyBuilder = SaveVoyageStatusReply.newBuilder();
    try {
      voyageService.saveVoyageStatus(request, replyBuilder);
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when saving voyage status", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(e.getCode())
              .setMessage(e.getMessage())
              .setStatus(FAILED)
              .build());
    } catch (Exception e) {
      log.error("Error saving voyage status", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Error saving voyage status")
              .setStatus(FAILED)
              .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /** Fetch the api and temp history for cargo and port ids if available */
  @Override
  public void getCargoApiTempHistory(
      CargoHistoryRequest request, StreamObserver<CargoHistoryReply> responseObserver) {
    com.cpdss.common.generated.LoadableStudy.CargoHistoryReply.Builder replyBuilder =
        CargoHistoryReply.newBuilder();
    try {
      // fetch the history for last 5 years
      Calendar now = Calendar.getInstance();
      int year = now.get(Calendar.YEAR) - 5;
      List<com.cpdss.loadablestudy.entity.ApiTempHistory> apiTempHistList =
          this.apiTempHistoryRepository.findApiTempHistoryWithYearAfter(request.getCargoId(), year);
      buildCargoHistoryReply(apiTempHistList, replyBuilder);
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS));
    } catch (Exception e) {
      log.error("Exception when fetching getCargoApiTempHistory", e);
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(FAILED));
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  private void buildCargoHistoryReply(
      List<com.cpdss.loadablestudy.entity.ApiTempHistory> apiTempHistList,
      com.cpdss.common.generated.LoadableStudy.CargoHistoryReply.Builder replyBuilder) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ETA_ETD_FORMAT);
    if (!CollectionUtils.isEmpty(apiTempHistList)) {
      apiTempHistList.forEach(
          apiTempRecord -> {
            CargoHistoryDetail.Builder builder = CargoHistoryDetail.newBuilder();
            Optional.ofNullable(apiTempRecord.getCargoId()).ifPresent(builder::setCargoId);
            Optional.ofNullable(apiTempRecord.getLoadingPortId())
                .ifPresent(builder::setLoadingPortId);
            Optional.ofNullable(apiTempRecord.getLoadedDate())
                .ifPresent(loadedDate -> builder.setLoadedDate(formatter.format(loadedDate)));
            Optional.ofNullable(apiTempRecord.getYear()).ifPresent(builder::setLoadedYear);
            Optional.ofNullable(apiTempRecord.getMonth()).ifPresent(builder::setLoadedMonth);
            Optional.ofNullable(apiTempRecord.getDate()).ifPresent(builder::setLoadedDay);
            Optional.ofNullable(apiTempRecord.getApi())
                .ifPresent(api -> builder.setApi(String.valueOf(api)));
            Optional.ofNullable(apiTempRecord.getTemp())
                .ifPresent(temperature -> builder.setTemperature(String.valueOf(temperature)));
            replyBuilder.addCargoHistory(builder);
          });
    }
  }

  @Override
  public void getAllCargoHistory(
      CargoHistoryRequest request, StreamObserver<CargoHistoryReply> responseObserver) {
    com.cpdss.common.generated.LoadableStudy.CargoHistoryReply.Builder replyBuilder =
        CargoHistoryReply.newBuilder();
    try {
      cargoService.getAllCargoHistory(request, replyBuilder);
    } catch (Exception e) {
      log.error("Exception when fetching getCargoApiTempHistory", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder().setMessage(e.getMessage()).setStatus(FAILED));
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  private void validateSaveSynopticalOhqData(
      OnHandQuantity ohqEntity,
      SynopticalTable entity,
      SynopticalOhqRecord ohqRecord,
      LoadableStudy loadableStudy)
      throws GenericServiceException {

    List<LoadablePattern> generatedPatterns =
        this.loadablePatternRepository.findLoadablePatterns(
            LOADABLE_STUDY_STATUS_PLAN_GENERATED_ID, loadableStudy, true);

    List<LoadablePattern> confirmedPatterns =
        this.loadablePatternRepository.findLoadablePatterns(
            CONFIRMED_STATUS_ID, loadableStudy, true);

    if ((!generatedPatterns.isEmpty() || !confirmedPatterns.isEmpty())
        && ((SYNOPTICAL_TABLE_OP_TYPE_ARRIVAL.equals(entity.getOperationType())
                && ohqEntity.getArrivalQuantity() != null
                && !Integer.toString(ohqEntity.getArrivalQuantity().intValue())
                    .equals(ohqRecord.getPlannedWeight()))
            || (SYNOPTICAL_TABLE_OP_TYPE_DEPARTURE.equals(entity.getOperationType())
                && ohqEntity.getDepartureQuantity() != null
                && !Integer.toString(ohqEntity.getDepartureQuantity().intValue())
                    .equals(ohqRecord.getPlannedWeight())))) {

      throw new GenericServiceException(
          "Cannot update planned values for plan generated loadable study",
          CommonErrorCodes.E_CPDSS_SAVE_NOT_ALLOWED,
          HttpStatusCode.BAD_REQUEST);
    }
  }

  private void validateSaveSynopticalObqData(
      OnBoardQuantity obqEntity, SynopticalCargoRecord cargoRecord, LoadableStudy loadableStudy)
      throws GenericServiceException {

    List<LoadablePattern> generatedPatterns =
        this.loadablePatternRepository.findLoadablePatterns(
            LOADABLE_STUDY_STATUS_PLAN_GENERATED_ID, loadableStudy, true);

    List<LoadablePattern> confirmedPatterns =
        this.loadablePatternRepository.findLoadablePatterns(
            CONFIRMED_STATUS_ID, loadableStudy, true);

    if ((!generatedPatterns.isEmpty() || !confirmedPatterns.isEmpty())
        && null != obqEntity.getPlannedArrivalWeight()
        && !cargoRecord
            .getPlannedWeight()
            .equals(Integer.toString(obqEntity.getPlannedArrivalWeight().intValue()))) {
      throw new GenericServiceException(
          "Cannot update planned values for plan generated loadable study",
          CommonErrorCodes.E_CPDSS_SAVE_NOT_ALLOWED,
          HttpStatusCode.BAD_REQUEST);
    }
  }

  private void validateSaveSynopticalEtaEtdEstimates(
      SynopticalTable entity,
      SynopticalRecord record,
      LoadableStudy loadableStudy,
      LoadableStudyPortRotation prEntity)
      throws GenericServiceException {

    List<LoadablePattern> generatedPatterns =
        this.loadablePatternRepository.findLoadablePatterns(
            LOADABLE_STUDY_STATUS_PLAN_GENERATED_ID, loadableStudy, true);

    List<LoadablePattern> confirmedPatterns =
        this.loadablePatternRepository.findLoadablePatterns(
            CONFIRMED_STATUS_ID, loadableStudy, true);

    if ((!generatedPatterns.isEmpty() || !confirmedPatterns.isEmpty())
        && ((SYNOPTICAL_TABLE_OP_TYPE_ARRIVAL.equals(entity.getOperationType())
                && prEntity.getEta() != null
                && prEntity.getEta().toString().equals(record.getEtaEtdEstimated()))
            || (SYNOPTICAL_TABLE_OP_TYPE_DEPARTURE.equals(entity.getOperationType())
                && prEntity.getEtd() != null
                && prEntity.getEtd().toString().equals(record.getEtaEtdEstimated())))) {

      throw new GenericServiceException(
          "Cannot update planned values for plan generated loadable study",
          CommonErrorCodes.E_CPDSS_SAVE_NOT_ALLOWED,
          HttpStatusCode.BAD_REQUEST);
    }
  }

  void validateSynopticalVesselData(
      LoadableStudy loadablestudy, SynopticalTable entity, SynopticalRecord record)
      throws GenericServiceException {
    List<LoadablePattern> generatedPatterns =
        this.loadablePatternRepository.findLoadablePatterns(
            LOADABLE_STUDY_STATUS_PLAN_GENERATED_ID, loadablestudy, true);

    List<LoadablePattern> confirmedPatterns =
        this.loadablePatternRepository.findLoadablePatterns(
            CONFIRMED_STATUS_ID, loadablestudy, true);
    if ((!generatedPatterns.isEmpty() || !confirmedPatterns.isEmpty())
        && ((null != entity.getOthersPlanned()
                && !Integer.toString(entity.getOthersPlanned().intValue())
                    .equals(record.getOthersPlanned()))
            || (null != entity.getConstantPlanned()
                && !Integer.toString(entity.getConstantPlanned().intValue())
                    .equals(record.getConstantPlanned()))
            || (null != entity.getDeadWeightPlanned()
                && !Integer.toString(entity.getDeadWeightPlanned().intValue())
                    .equals(record.getTotalDwtPlanned()))
            || (null != entity.getDisplacementPlanned()
                && !Integer.toString(entity.getDisplacementPlanned().intValue())
                    .equals(record.getDisplacementPlanned())))) {
      throw new GenericServiceException(
          "Cannot update planned values for plan generated loadable study",
          CommonErrorCodes.E_CPDSS_SAVE_NOT_ALLOWED,
          HttpStatusCode.BAD_REQUEST);
    }
  }

  /** Save JSON to database */
  @Override
  public void saveJson(JsonRequest request, StreamObserver<StatusReply> responseObserver) {
    StatusReply.Builder replyBuilder = StatusReply.newBuilder();
    try {
      this.saveJsonToDatabase(request.getReferenceId(), request.getJsonTypeId(), request.getJson());
      replyBuilder = StatusReply.newBuilder().setStatus(SUCCESS).setMessage(SUCCESS);
    } catch (Exception e) {
      log.error("Exception occured while trying to save JSON to database.", e);
      replyBuilder =
          StatusReply.newBuilder()
              .setStatus(FAILED)
              .setMessage(FAILED)
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR);
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * @param referenceId
   * @param jsonTypeId
   * @param json
   */
  public void saveJsonToDatabase(Long referenceId, Long jsonTypeId, String json) {
    Optional<JsonType> jsonTypeOpt = jsonTypeRepository.findByIdAndIsActive(jsonTypeId, true);

    if (jsonTypeOpt.isPresent()) {
      JsonData jsonData = new JsonData();
      jsonData.setReferenceXId(referenceId);
      jsonData.setJsonTypeXId(jsonTypeOpt.get());
      jsonData.setJsonData(json);
      jsonDataRepository.save(jsonData);
      log.info(String.format("Saved %s JSON in database.", jsonTypeOpt.get().getTypeName()));
    } else {
      log.error("Cannot find JSON type in database.");
    }
  }

  /**
   * Fetch Single Synoptic Data with DEP condition,
   *
   * @param request - LoadableStudy Id, LoadablePattern Id required
   * @param responseObserver - SynopticalTableReply with One SynopticalTable Data
   */
  @Override
  public void getSynopticDataByLoadableStudyId(
      com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest request,
      StreamObserver<com.cpdss.common.generated.LoadableStudy.SynopticalTableReply>
          responseObserver) {

    SynopticalTableReply.Builder replyBuilder = SynopticalTableReply.newBuilder();
    log.info(
        "Synoptic DEP data for loadable study {}, loadable pattern {}",
        request.getLoadableStudyId(),
        request.getLoadablePatternId());
    try {
      synopticService.getSynopticDataByLoadableStudyId(request, replyBuilder);
    } catch (Exception e) {
      log.error("Exception while fetch Synoptic data", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder().setMessage(e.getMessage()).setStatus(FAILED));
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void saveAlgoErrors(
      com.cpdss.common.generated.LoadableStudy.AlgoErrors request,
      StreamObserver<com.cpdss.common.generated.LoadableStudy.AlgoErrors> responseObserver) {
    algoErrorService.saveAlgoError(request, responseObserver);
  }

  @Override
  public void fetchAllAlgoErrors(
      com.cpdss.common.generated.LoadableStudy.AlgoErrors request,
      StreamObserver<com.cpdss.common.generated.LoadableStudy.AlgoErrors> responseObserver) {
    algoErrorService.fetchAllErrors(request, responseObserver);
  }

  @Override
  public void getCargoHistoryByCargo(
      LatestCargoRequest request, StreamObserver<LatestCargoReply> responseObserver) {

    LatestCargoReply.Builder replyBuilder = LatestCargoReply.newBuilder();
    try {
      cargoService.getCargoHistoryByCargo(request, replyBuilder);
    } catch (Exception e) {
      log.error("Exception when latest api temp against port data", e);
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(FAILED));
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void getActiveVoyagesByVessel(
      VoyageRequest request,
      StreamObserver<com.cpdss.common.generated.LoadableStudy.ActiveVoyage> responseObserver) {
    com.cpdss.common.generated.LoadableStudy.ActiveVoyage.Builder builder =
        com.cpdss.common.generated.LoadableStudy.ActiveVoyage.newBuilder();
    ResponseStatus.Builder repBuilder = ResponseStatus.newBuilder();
    try {
      if (request.getVesselId() > 0) {
        // Fetch and build Voayge Response Object
        this.voyageService.fetchActiveVoyageByVesselId(
            builder, request.getVesselId(), ACTIVE_VOYAGE_STATUS);
      }
      repBuilder.setStatus(SUCCESS);
      repBuilder.setHttpStatusCode(HttpStatusCode.OK.value());
    } catch (Exception e) {
      e.printStackTrace();
      log.error("Failed to fetch active voyage for, Vessel Id {}", request.getVesselId());
      repBuilder.setStatus(FAILED);
      repBuilder.setHttpStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR.value());
      repBuilder.setMessage(e.getMessage());
    } finally {
      builder.setResponseStatus(repBuilder.build());
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void getSynopticDataForLoadingPlan(
      com.cpdss.common.generated.LoadableStudy.LoadingPlanIdRequest request,
      StreamObserver<com.cpdss.common.generated.LoadableStudy.LoadingPlanCommonResponse>
          responseObserver) {
    com.cpdss.common.generated.LoadableStudy.LoadingPlanCommonResponse.Builder builder =
        com.cpdss.common.generated.LoadableStudy.LoadingPlanCommonResponse.newBuilder();
    ResponseStatus.Builder repBuilder = ResponseStatus.newBuilder();
    try {
      this.synopticService.fetchLoadingInformationSynopticDetails(request, builder, repBuilder);
    } catch (Exception e) {
      e.printStackTrace();
      log.error("Failed to Get Synoptic Record", request.getId());
      repBuilder.setStatus(FAILED);
      repBuilder.setHttpStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR.value());
      repBuilder.setMessage(e.getMessage());
    } finally {
      builder.setResponseStatus(repBuilder.build());
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void saveLoadingInfoToSynopticData(
      com.cpdss.common.generated.LoadableStudy.LoadingInfoSynopticalUpdateRequest request,
      StreamObserver<ResponseStatus> responseObserver) {
    ResponseStatus.Builder builder = ResponseStatus.newBuilder();
    try {
      this.synopticService.saveLoadingInformationToSynopticalTable(request, builder);
    } catch (Exception e) {
      e.printStackTrace();
      log.error("Failed to Get Synoptic Record ", request.getSynopticalTableId());
      builder.setStatus(FAILED);
      builder.setHttpStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR.value());
      builder.setMessage(e.getMessage());
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
  public void getOrSaveRulesForLoadableStudy(
      com.cpdss.common.generated.LoadableStudy.LoadableRuleRequest request,
      StreamObserver<com.cpdss.common.generated.LoadableStudy.LoadableRuleReply> responseObserver) {
    com.cpdss.common.generated.LoadableStudy.LoadableRuleReply.Builder builder =
        com.cpdss.common.generated.LoadableStudy.LoadableRuleReply.newBuilder();
    try {
      loadableStudyRuleService.getOrSaveRulesForLoadableStudy(request, builder);

    } catch (Exception e) {
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
      log.error("Exception in save or get loadable study rule", e);
      builder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage(e.getMessage())
              .setStatus(FAILED)
              .build());
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void getLoadablePatternDetailsJson(
      LoadablePlanDetailsRequest request,
      StreamObserver<LoadablePatternPortWiseDetailsJson> responseObserver) {
    LoadablePatternPortWiseDetailsJson.Builder builder =
        LoadablePatternPortWiseDetailsJson.newBuilder();
    try {
      loadablePatternService.getLoadablePatternDetailsJson(request, builder);
    } catch (Exception e) {
      e.printStackTrace();
      log.error("Failed to Get Pattern Details ", request.getLoadablePatternId());
      builder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setStatus(FAILED)
              .setHttpStatusCode(HttpStatusCode.INTERNAL_SERVER_ERROR.value())
              .setMessage(e.getMessage())
              .build());
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void getLoadablePatternByVoyageAndStatus(
      LoadableStudyRequest request,
      StreamObserver<com.cpdss.common.generated.LoadableStudy.LoadablePatternConfirmedReply>
          responseObserver) {
    com.cpdss.common.generated.LoadableStudy.LoadablePatternConfirmedReply.Builder builder =
        com.cpdss.common.generated.LoadableStudy.LoadablePatternConfirmedReply.newBuilder();
    try {
      loadablePatternService.getLoadablePatternByVoyageAndStatus(request, builder);
    } catch (GenericServiceException e) {
      builder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(e.getCode())
              .setMessage(e.getMessage())
              .setStatus(FAILED)
              .build());
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void getCargoNominationByCargoNominationId(
      CargoNominationRequest request,
      StreamObserver<com.cpdss.common.generated.LoadableStudy.CargoNominationDetailReply>
          responseObserver) {
    com.cpdss.common.generated.LoadableStudy.CargoNominationDetailReply.Builder builder =
        com.cpdss.common.generated.LoadableStudy.CargoNominationDetailReply.newBuilder();
    try {
      cargoNominationService.getCargoNominationByCargoNominationId(request, builder);
    } catch (Exception e) {
      e.printStackTrace();
      builder.setResponseStatus(ResponseStatus.newBuilder().setStatus(FAILED).build());
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  public com.cpdss.common.generated.CargoInfo.CargoDetailReply getCargoInfoById(
      CargoRequest build) {
    return cargoInfoGrpcService.getCargoInfoById(build);
  }

  @Override
  public void getLoadableCommingleByPatternId(
      LoadablePlanDetailsRequest request,
      StreamObserver<com.cpdss.common.generated.LoadableStudy.LoadableCommingleDetailsReply>
          responseObserver) {

    com.cpdss.common.generated.LoadableStudy.LoadableCommingleDetailsReply.Builder builder =
        com.cpdss.common.generated.LoadableStudy.LoadableCommingleDetailsReply.newBuilder();
    try {
      loadablePatternService.getLoadableCommingleByPatternId(request, builder);
    } catch (Exception e) {
      e.printStackTrace();
      builder.setResponseStatus(ResponseStatus.newBuilder().setStatus(FAILED).build());
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * Group tanks based on tank group
   *
   * @param tankDetailList
   * @return
   */
  private List<com.cpdss.common.generated.LoadableStudy.TankList> groupTanks(
      List<VesselInfo.VesselTankDetail> tankDetailList) {
    Map<Integer, List<VesselInfo.VesselTankDetail>> vesselTankMap = new HashMap<>();
    for (VesselInfo.VesselTankDetail tank : tankDetailList) {
      Integer tankGroup = tank.getTankGroup();
      List<VesselInfo.VesselTankDetail> list = null;
      if (null == vesselTankMap.get(tankGroup)) {
        list = new ArrayList<>();
      } else {
        list = vesselTankMap.get(tankGroup);
      }
      list.add(tank);
      vesselTankMap.put(tankGroup, list);
    }
    List<com.cpdss.common.generated.LoadableStudy.TankList> tankList = new ArrayList<>();
    List<com.cpdss.common.generated.LoadableStudy.TankDetail> tankGroup = null;
    for (Map.Entry<Integer, List<VesselInfo.VesselTankDetail>> entry : vesselTankMap.entrySet()) {
      tankGroup = entry.getValue().stream().map(this::buildTankDetail).collect(Collectors.toList());
      Collections.sort(
          tankGroup,
          Comparator.comparing(com.cpdss.common.generated.LoadableStudy.TankDetail::getTankOrder));
      tankList.add(
          com.cpdss.common.generated.LoadableStudy.TankList.newBuilder()
              .addAllVesselTank(tankGroup)
              .build());
    }
    return tankList;
  }

  /**
   * create tank detail
   *
   * @param detail
   * @return
   */
  public com.cpdss.common.generated.LoadableStudy.TankDetail buildTankDetail(
      VesselInfo.VesselTankDetail detail) {
    com.cpdss.common.generated.LoadableStudy.TankDetail.Builder builder =
        com.cpdss.common.generated.LoadableStudy.TankDetail.newBuilder();
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
}
