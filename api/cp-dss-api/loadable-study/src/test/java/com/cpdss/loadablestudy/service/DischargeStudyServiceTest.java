/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.cpdss.common.generated.LoadableStudy.DischargeStudyDetail;
import com.cpdss.common.generated.LoadableStudy.DischargeStudyReply;
import com.cpdss.common.generated.LoadableStudy.UpdateDischargeStudyReply;
import com.cpdss.loadablestudy.entity.CargoNomination;
import com.cpdss.loadablestudy.entity.CargoNominationPortDetails;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.entity.LoadableStudyPortRotation;
import com.cpdss.loadablestudy.entity.LoadableStudyStatus;
import com.cpdss.loadablestudy.entity.OnHandQuantity;
import com.cpdss.loadablestudy.entity.SynopticalTable;
import com.cpdss.loadablestudy.entity.Voyage;
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
import com.cpdss.loadablestudy.repository.LoadablePatternComingleDetailsRepository;
import com.cpdss.loadablestudy.repository.LoadablePatternDetailsRepository;
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
import io.grpc.internal.testing.StreamRecorder;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.client.RestTemplate;

/**
 * @Author jerin.g
 *
 * <p>Class for writing test cases for discharge study
 */
@SpringJUnitConfig(classes = {LoadableStudyService.class})
class DischargeStudyServiceTest {

  @Autowired private LoadableStudyService loadableStudyService;
  @MockBean LoadableStudyRuleInputRepository loadableStudyRuleInputRepository;
  @MockBean LoadableStudyRuleRepository loadableStudyRuleRepository;
  @MockBean SynopticService synopticService;
  @MockBean private VoyageService voyageService;
  @MockBean private VoyageRepository voyageRepository;
  @MockBean private LoadableStudyRepository loadableStudyRepository;
  @MockBean private LoadableQuantityRepository loadableQuantityRepository;

  @MockBean private CargoNominationRepository cargoNominationRepository;
  @MockBean private LoadableStudyPortRotationRepository loadableStudyPortRotationRepository;
  @MockBean private CargoOperationRepository cargoOperationRepository;
  @MockBean private LoadableStudyStatusRepository loadableStudyStatusRepository;

  @MockBean private LoadablePlanStowageDetailsRespository loadablePlanStowageDetailsRespository;
  @MockBean private LoadableStudyAttachmentsRepository loadableStudyAttachmentsRepository;
  @MockBean private LoadablePlanStowageDetailsTempRepository stowageDetailsTempRepository;

  @MockBean
  private LoadablePlanStowageBallastDetailsRepository loadablePlanStowageBallastDetailsRepository;

  @MockBean private SynopticalTableLoadicatorDataRepository synopticalTableLoadicatorDataRepository;
  @MockBean private LoadablePlanCommentsRepository loadablePlanCommentsRepository;
  @MockBean private LoadablePatternDetailsRepository loadablePatternDetailsRepository;
  @MockBean private LoadablePatternRepository loadablePatternRepository;
  @MockBean private LoadablePlanConstraintsRespository loadablePlanConstraintsRespository;
  @MockBean private PurposeOfCommingleRepository purposeOfCommingleRepository;

  @MockBean
  private LoadablePlanCommingleDetailsPortwiseRepository
      loadablePlanCommingleDetailsPortwiseRepository;

  @MockBean private CommingleCargoRepository commingleCargoRepository;
  @MockBean private AlgoErrorService algoErrorService;

  @MockBean
  private LoadablePatternComingleDetailsRepository loadablePatternComingleDetailsRepository;

  @MockBean
  private CargoNominationValveSegregationRepository cargoNominationValveSegregationRepository;

  @MockBean
  private CargoNominationOperationDetailsRepository cargoNominationOperationDetailsRepository;

  @MockBean private OnHandQuantityRepository onHandQuantityRepository;

  @MockBean private OnBoardQuantityRepository onBoardQuantityRepository;
  @MockBean private CargoHistoryRepository cargoHistoryRepository;
  @MockBean private LoadablePlanQuantityRepository loadablePlanQuantityRepository;
  @MockBean private LoadablePlanCommingleDetailsRepository loadablePlanCommingleDetailsRepository;
  @MockBean private VoyageHistoryRepository voyageHistoryRepository;
  @MockBean private LoadableStudyAlgoStatusRepository loadableStudyAlgoStatusRepository;
  @MockBean private SynopticalTableRepository synopticalTableRepository;
  @Mock private CargoNomination cargoNomination;
  @Mock private CargoNominationPortDetails cargoNominationPortDetails;
  @MockBean private RestTemplate restTemplate;
  @MockBean private EntityManager entityManager;
  @MockBean private AlgoErrorHeadingRepository algoErrorHeadingRepository;
  @MockBean private AlgoErrorsRepository algoErrorsRepository;
  @MockBean private LoadablePatternAlgoStatusRepository loadablePatternAlgoStatusRepository;
  @MockBean private EntityManagerFactory entityManagerFactory;

  @MockBean private LoadablePlanBallastDetailsRepository loadablePlanBallastDetailsRepository;
  @MockBean private LoadablePatternCargoDetailsRepository loadablePatternCargoDetailsRepository;
  @MockBean private VoyageStatusRepository voyageStatusRepository;
  @MockBean private ApiTempHistoryRepository apiTempHistoryRepository;

  @MockBean private StabilityParameterRepository stabilityParameterRepository;

  @MockBean private JsonDataRepository jsonDataRepository;
  @MockBean private JsonTypeRepository jsonTypeRepository;
  @MockBean private LoadablePlanService loadablePlanService;
  @MockBean private LoadablePatternCargoToppingOffSequenceRepository toppingOffSequenceRepository;
  @MockBean private LoadableQuantityService loadableQuantityService;

  private static final String SUCCESS = "SUCCESS";

  @BeforeAll
  public static void beforeAll() {
    MockedStatic<Files> mockedStatic = Mockito.mockStatic(Files.class);
    Path pathMock = Mockito.mock(Path.class);
    mockedStatic.when(() -> Files.createDirectories(any(Path.class))).thenReturn(pathMock);
    mockedStatic.when(() -> Files.createFile(any(Path.class))).thenReturn(pathMock);
    mockedStatic.when(() -> Files.write(any(Path.class), any(byte[].class))).thenReturn(pathMock);
    mockedStatic
        .when(() -> Files.deleteIfExists(any(Path.class)))
        .thenReturn(true)
        .thenThrow(IOException.class);

    TransactionStatus status = Mockito.mock(TransactionStatus.class);
    MockedStatic<TransactionAspectSupport> mockedTransactionStatic =
        Mockito.mockStatic(TransactionAspectSupport.class);
    mockedTransactionStatic
        .when(() -> TransactionAspectSupport.currentTransactionStatus())
        .thenReturn(status);

    MockitoAnnotations.openMocks(LoadableStudyServiceTest.class);
  }

  @Test
  void testSaveDischargeStudy() {
    DischargeStudyDetail request =
        DischargeStudyDetail.newBuilder().setName("DS").setVesselId(1L).setVoyageId(1L).build();
    LoadableStudy entity = new LoadableStudy();
    entity.setId(2L);
    when(this.voyageRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(new com.cpdss.loadablestudy.entity.Voyage());
    when(this.loadableStudyRepository.findByVesselXIdAndVoyageAndIsActiveAndLoadableStudyStatus_id(
            anyLong(), any(Voyage.class), anyBoolean(), anyLong()))
        .thenReturn(createLoadableList());

    when(this.cargoNominationRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(new com.cpdss.loadablestudy.entity.CargoNomination()));
    when(loadableStudyPortRotationRepository.findByLoadableStudyAndOperation_idAndIsActive(
            any(LoadableStudy.class), anyLong(), anyBoolean()))
        .thenReturn(createLoadableStudyPortRotationList());
    when(this.synopticalTableRepository
            .findByLoadableStudyXIdAndLoadableStudyPortRotation_idAndIsActive(
                anyLong(), anyLong(), anyBoolean()))
        .thenReturn(Arrays.asList(new SynopticalTable()));

    when(this.onHandQuantityRepository.findByLoadableStudyAndPortRotationAndIsActive(
            any(LoadableStudy.class), any(LoadableStudyPortRotation.class), anyBoolean()))
        .thenReturn(createOnHandQuantityList());

    when(this.loadableStudyStatusRepository.getOne(anyLong()))
        .thenReturn(new LoadableStudyStatus());

    when(this.loadableStudyRepository.save(any(LoadableStudy.class))).thenReturn(entity);
    StreamRecorder<DischargeStudyReply> responseObserver = StreamRecorder.create();
    this.loadableStudyService.saveDischargeStudy(request, responseObserver);
    List<DischargeStudyReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
    assertEquals(2L, replies.get(0).getId());
  }

  @Test
  void testUpdateDischargeStudy() {
    DischargeStudyDetail request =
        DischargeStudyDetail.newBuilder().setName("update DS").setEnquiryDetails("details").build();
    LoadableStudy entity = new LoadableStudy();
    entity.setId(2L);
    entity.setName("update DS");
    entity.setDetails("details");
    when(this.loadableStudyRepository.findById(anyLong()))
        .thenReturn(Optional.of(new LoadableStudy()));

    when(this.loadableStudyRepository.save(any(LoadableStudy.class))).thenReturn(entity);
    StreamRecorder<UpdateDischargeStudyReply> responseObserver = StreamRecorder.create();
    this.loadableStudyService.updateDischargeStudy(request, responseObserver);
    List<UpdateDischargeStudyReply> replies = responseObserver.getValues();
    assertEquals(1, replies.size());
    assertNull(responseObserver.getError());
    assertEquals(SUCCESS, replies.get(0).getResponseStatus().getStatus());
    assertEquals(2L, replies.get(0).getDischargeStudy().getId());
  }

  private List<OnHandQuantity> createOnHandQuantityList() {
    OnHandQuantity onHandQuantity = new OnHandQuantity();
    return Arrays.asList(onHandQuantity);
  }

  private List<SynopticalTable> createLoadableSynopticalTableList() {
    return Arrays.asList(new SynopticalTable());
  }

  private List<LoadableStudyPortRotation> createLoadableStudyPortRotationList() {
    LoadableStudyPortRotation loadableStudyPortRotation = new LoadableStudyPortRotation();
    loadableStudyPortRotation.setPortOrder(1L);
    return Arrays.asList(loadableStudyPortRotation);
  }

  private List<LoadableStudy> createLoadableList() {

    return Arrays.asList(new LoadableStudy());
  }
}
