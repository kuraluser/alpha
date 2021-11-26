/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.cpdss.common.generated.CargoInfoServiceGrpc;
import com.cpdss.common.generated.LoadableStudy.CargoNominationDetail;
import com.cpdss.common.generated.LoadableStudy.CargoNominationReply;
import com.cpdss.common.generated.LoadableStudy.CargoNominationRequest;
import com.cpdss.common.generated.LoadableStudy.LoadingPortDetail;
import com.cpdss.common.generated.LoadableStudy.SynopticalTableReply;
import com.cpdss.common.generated.LoadableStudy.SynopticalTableRequest;
import com.cpdss.common.generated.PortInfoServiceGrpc;
import com.cpdss.common.generated.VesselInfoServiceGrpc;
import com.cpdss.loadablestudy.entity.CargoOperation;
import com.cpdss.loadablestudy.entity.LoadablePattern;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.entity.LoadableStudyPortRotation;
import com.cpdss.loadablestudy.entity.SynopticalTable;
import com.cpdss.loadablestudy.repository.*;
import io.grpc.internal.testing.StreamRecorder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(
    properties = {
      "grpc.server.inProcessName=test", // Enable inProcess server
      "grpc.server.port=-1", // Disable external server
      "grpc.client.portInfoService.address=in-process:test",
      "grpc.client.vesselInfoService.address=in-process:test" // Configure the client to connect to
      // the inProcess server
    })
@SpringJUnitConfig(
    classes = {
      LoadableStudyServiceIntegrationTestConfiguration.class,
      VesselInfoImplForLoadableStudyServiceIntegration.class,
    })
@DirtiesContext
public class LoadableStudyServiceIntegrationTest {

  @Autowired private LoadableStudyService loadableStudyService;

  @MockBean private VoyageRepository voyageRepository;

  @MockBean private LoadableStudyPortRotationRepository loadableStudyPortRotationRepository;

  @MockBean private CargoOperationRepository cargoOperationRepository;

  @MockBean private LoadableStudyRepository loadableStudyRepository;

  @MockBean private LoadableQuantityRepository loadableQuantityRepository;

  @MockBean private CargoNominationRepository cargoNominationRepository;

  @MockBean private LoadablePlanQuantityRepository loadablePlanQuantityRepository;
  @MockBean private LoadablePlanStowageDetailsRespository loadablePlanStowageDetailsRespository;
  @MockBean private LoadablePlanCommingleDetailsRepository loadablePlanCommingleDetailsRepository;
  @MockBean private LoadablePlanCommentsRepository loadablePlanCommentsRepository;
  @MockBean private LoadableStudyRuleRepository loadableStudyRuleRepository;
  @MockBean private OnHandQuantityService onHandQuantityService;
  @MockBean private LoadablePatternAlgoStatusRepository loadablePatternAlgoStatusRepository;
  @MockBean private LoadablePlanService loadablePlanService;

  @MockBean
  private LoadablePlanCommingleDetailsPortwiseRepository
      loadablePlanCommingleDetailsPortwiseRepository;

  @MockBean private DischargePatternQuantityCargoPortwiseRepository disCargoQuantityRepository;
  @MockBean private SynopticServiceUtils synpoticServiceUtils;
  @MockBean private LoadablePatternCargoDetailsRepository loadablePatternCargoDetailsRepository;
  @MockBean private LoadableStudyPortRotationService loadableStudyPortRotationService;

  @MockBean
  private LoadablePlanStowageBallastDetailsRepository loadablePlanStowageBallastDetailsRepository;

  @MockBean private SynopticalTableLoadicatorDataRepository synopticalTableLoadicatorDataRepository;
  @MockBean private CargoHistoryRepository cargoHistoryRepository;
  @MockBean private VoyageHistoryRepository voyageHistoryRepository;
  @MockBean private LoadableStudyAlgoStatusRepository loadableStudyAlgoStatusRepository;
  @MockBean private SynopticalTableRepository synopticalTableRepository;
  @MockBean private LoadablePlanBallastDetailsRepository loadablePlanBallastDetailsRepository;
  @MockBean private CargoNominationValveSegregationRepository valveSegregationRepository;
  @MockBean private LoadableStudyStatusRepository loadableStudyStatusRepository;
  @MockBean private OnHandQuantityRepository onHandQuantityRepository;
  @MockBean private ApiTempHistoryRepository apiTempHistoryRepository;
  @MockBean private VoyageStatusRepository voyageStatusRepository;
  @MockBean private VoyageService voyageService;
  @MockBean private OnBoardQuantityService onBoardQuantityService;
  @MockBean private AlgoService algoService;
  @MockBean private JsonDataService jsonDataService;
  @MockBean private JsonDataRepository jsonDataRepository;
  @MockBean private JsonTypeRepository jsonTypeRepository;
  @MockBean private CargoNominationService cargoNominationService;
  @MockBean private CargoService cargoService;
  @MockBean private BillOfLandingRepository billOfLandingRepository;

  @MockBean
  private CargoNominationOperationDetailsRepository cargoNominationOperationDetailsRepository;

  @Mock private LoadableStudy loadableStudy;
  @Mock private CargoOperation cargoOperation;
  @Mock private LoadableStudyPortRotation loadableStudyPortRotation;
  @Mock private List<LoadableStudyPortRotation> loadableStudyPortRotationList;
  @Mock private LoadablePattern loadablePattern;
  @Mock private List<LoadablePattern> loadablePatternList;
  @MockBean private LoadablePatternDetailsRepository loadablePatternDetailsRepository;
  @MockBean private LoadablePatternRepository loadablePatternRepository;
  @MockBean private PurposeOfCommingleRepository purposeOfCommingleRepository;
  @MockBean private CommingleCargoRepository commingleCargoRepository;
  @MockBean private OnBoardQuantityRepository onBoardQuantityRepository;
  @MockBean private LoadablePatternService loadablePatternService;
  @MockBean private LoadableStudyRuleService loadableStudyRuleService;
  @MockBean private AlgoErrorService algoErrorService;
  @MockBean private LoadicatorService lsLoadicatorService;
  @MockBean private CommunicationService communicationService;
  @MockBean private LoadableQuantityService loadableQuantityService;

  @MockBean
  private LoadablePatternComingleDetailsRepository loadablePatternComingleDetailsRepository;

  @MockBean private LoadableStudyAttachmentsRepository loadableStudyAttachmentsRepository;
  @MockBean private EntityManager entityManager;
  @MockBean private RestTemplate restTemplate;
  @MockBean private LoadablePlanConstraintsRespository loadablePlanConstraintsRespository;
  @MockBean private LoadableStudyRuleInputRepository loadableStudyRuleInputRepository;

  @MockBean private CowDetailService cowDetailService;
  @MockBean private BackLoadingService backLoadingService;
  @MockBean private GenerateDischargeStudyJson generateDischargeStudyJson;
  @MockBean private DischargeStudyCowDetailRepository dischargeStudyCowDetailRepository;
  @MockBean private CowHistoryRepository cowHistoryRepository;

  @MockBean DischargeStudyPortInstructionRepository dischargeStudyPortInstructionRepository;
  @MockBean PortInstructionRepository portInstructionRepository;
  @MockBean private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;
  @MockBean private CargoInfoServiceGrpc.CargoInfoServiceBlockingStub cargoInfoGrpcService;
  @MockBean private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoGrpcService;
  @MockBean private LoadableStudyCommunicationData loadableStudyCommunicationData;

  private static final Long CARGO_TANK_CATEGORY_ID = 1L;
  private static final Long CARGO_SLOP_TANK_CATEGORY_ID = 9L;
  private static final Long CARGO_VOID_TANK_CATEGORY_ID = 15L;
  private static final Long FRESH_WATER_TANK_CATEGORY_ID = 3L;
  private static final Long FUEL_OIL_TANK_CATEGORY_ID = 5L;
  private static final Long DIESEL_OIL_TANK_CATEGORY_ID = 6L;
  private static final Long LUBRICATING_OIL_TANK_CATEGORY_ID = 14L;
  private static final Long LUBRICANT_OIL_TANK_CATEGORY_ID = 19L;
  private static final Long FUEL_VOID_TANK_CATEGORY_ID = 22L;
  private static final Long FRESH_WATER_VOID_TANK_CATEGORY_ID = 23L;
  private static final Long BALLAST_VOID_TANK_CATEGORY_ID = 16L;
  private static final Long BALLAST_TANK_CATEGORY_ID = 2L;

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

  @Test
  void testSaveCargoNomination() {
    CargoNominationRequest cargoNominationRequest = createSaveCargoNominationRequest(true);
    StreamRecorder<CargoNominationReply> responseObserver = StreamRecorder.create();
    when(this.loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(new com.cpdss.loadablestudy.entity.LoadableStudy()));
    when(this.cargoNominationRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(new com.cpdss.loadablestudy.entity.CargoNomination()));
    loadableStudyService.saveCargoNomination(cargoNominationRequest, responseObserver);
    // get results when no errors
    List<CargoNominationReply> results = responseObserver.getValues();
    assertEquals(true, results.size() > 0);
  }

  @Test
  void testSaveCargoNominationWithInvalidLoadableStudy() {
    CargoNominationRequest cargoNominationRequest = createSaveCargoNominationRequest(true);
    StreamRecorder<CargoNominationReply> responseObserver = StreamRecorder.create();
    when(this.loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.empty());
    when(this.cargoNominationRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(new com.cpdss.loadablestudy.entity.CargoNomination()));
    loadableStudyService.saveCargoNomination(cargoNominationRequest, responseObserver);
    // get results when no errors
    List<CargoNominationReply> results = responseObserver.getValues();
    assertEquals(true, results.size() > 0);
  }

  @Test
  void testSaveCargoNominationWithInvalidCargoNomination() {
    CargoNominationRequest cargoNominationRequest = createSaveCargoNominationRequest(true);
    StreamRecorder<CargoNominationReply> responseObserver = StreamRecorder.create();
    when(this.loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(new com.cpdss.loadablestudy.entity.LoadableStudy()));
    when(this.cargoNominationRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.empty());
    loadableStudyService.saveCargoNomination(cargoNominationRequest, responseObserver);
    // get results when no errors
    List<CargoNominationReply> results = responseObserver.getValues();
    assertEquals(true, results.size() > 0);
  }

  @Test
  void testSaveCargoNominationWithEmptyLoadingPorts() {
    CargoNominationRequest cargoNominationRequest =
        createSaveCargoNominationWithEmptyLoadingPortsRequest(true);
    StreamRecorder<CargoNominationReply> responseObserver = StreamRecorder.create();
    when(this.loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(new com.cpdss.loadablestudy.entity.LoadableStudy()));
    when(this.cargoNominationRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.empty());
    cargoNominationRequest
        .getCargoNominationDetail()
        .getLoadingPortDetailsList()
        .addAll(Collections.emptyList());
    loadableStudyService.saveCargoNomination(cargoNominationRequest, responseObserver);
    // get results when no errors
    List<CargoNominationReply> results = responseObserver.getValues();
    assertEquals(true, results.size() > 0);
  }

  @Test
  void testSaveCargoNominationWithExistingPortRotationRecords() {
    CargoNominationRequest cargoNominationRequest = createSaveCargoNominationRequest(true);
    StreamRecorder<CargoNominationReply> responseObserver = StreamRecorder.create();
    when(this.loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(new com.cpdss.loadablestudy.entity.LoadableStudy()));
    when(this.cargoNominationRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(new com.cpdss.loadablestudy.entity.CargoNomination()));
    LoadableStudyPortRotation loadableStudyPortRotation = new LoadableStudyPortRotation();
    loadableStudyPortRotation.setPortXId(2L);
    List<LoadableStudyPortRotation> loadableStudyPortRotationList = new ArrayList();
    loadableStudyPortRotationList.add(loadableStudyPortRotation);
    when(cargoOperationRepository.getOne(1L)).thenReturn(cargoOperation);
    when(this.loadableStudyPortRotationRepository.findByLoadableStudyAndOperationAndIsActive(
            ArgumentMatchers.any(LoadableStudy.class),
            ArgumentMatchers.any(CargoOperation.class),
            anyBoolean()))
        .thenReturn(loadableStudyPortRotationList);
    loadableStudyService.saveCargoNomination(cargoNominationRequest, responseObserver);
    // get results when no errors
    List<CargoNominationReply> results = responseObserver.getValues();
    assertEquals(true, results.size() > 0);
  }

  private CargoNominationRequest createSaveCargoNominationRequest(boolean existingRecord) {
    CargoNominationRequest request =
        CargoNominationRequest.newBuilder()
            .setLoadableStudyId(30)
            .setCargoNominationDetail(
                CargoNominationDetail.newBuilder()
                    .setId(existingRecord ? 15 : 0)
                    .setPriority(3L)
                    .setLoadableStudyId(30)
                    .setCargoId(1L)
                    .setAbbreviation("ABBREV")
                    .setColor("testColor")
                    .setMaxTolerance("10.0")
                    .setMinTolerance("20.0")
                    .setApiEst("5.0")
                    .setTempEst("6.0")
                    .setSegregationId(2L)
                    .addLoadingPortDetails(
                        LoadingPortDetail.newBuilder().setPortId(2L).setQuantity("100.00"))
                    .build())
            .build();
    return request;
  }

  private CargoNominationRequest createSaveCargoNominationWithEmptyLoadingPortsRequest(
      boolean existingRecord) {
    CargoNominationRequest request =
        CargoNominationRequest.newBuilder()
            .setLoadableStudyId(30)
            .setCargoNominationDetail(
                CargoNominationDetail.newBuilder()
                    .setId(existingRecord ? 15 : 0)
                    .setPriority(3L)
                    .setLoadableStudyId(30)
                    .setCargoId(1L)
                    .setAbbreviation("ABBREV")
                    .setColor("testColor")
                    .setMaxTolerance("10.0")
                    .setMinTolerance("20.0")
                    .setApiEst("5.0")
                    .setTempEst("6.0")
                    .setSegregationId(2L)
                    .build())
            .build();
    return request;
  }

  @Test
  void testGetSynopticalDataByPortId() {
    SynopticalTableRequest synopticalRequest = createSynopticalTableRequest();
    StreamRecorder<SynopticalTableReply> responseObserver = StreamRecorder.create();
    when(this.loadableStudyRepository.findByIdAndIsActive(anyLong(), anyBoolean()))
        .thenReturn(Optional.of(new com.cpdss.loadablestudy.entity.LoadableStudy()));
    List<LoadablePattern> loadablePatternList = new ArrayList();
    loadablePattern.setId(1L);
    loadablePatternList.add(loadablePattern);
    when(loadablePatternRepository.findByVoyageAndLoadableStudyStatusAndIsActive(
            anyLong(), anyLong(), anyBoolean()))
        .thenReturn(loadablePatternList);
    List<SynopticalTable> synopticalTableList = new ArrayList();
    SynopticalTable synopticalTable = new SynopticalTable();
    synopticalTable.setId(1L);
    synopticalTableList.add(synopticalTable);
    when(synopticalTableRepository.findByLoadableStudyXIdAndIsActiveAndPortXid(
            anyLong(), anyBoolean(), anyLong()))
        .thenReturn(synopticalTableList);
    List<LoadableStudyPortRotation> portRotationList = new ArrayList<LoadableStudyPortRotation>();
    LoadableStudyPortRotation portRotation = new LoadableStudyPortRotation();
    portRotation.setPortXId(1L);
    portRotationList.add(portRotation);
    when(loadableStudyPortRotationRepository.findByLoadableStudyAndIsActiveOrderByPortOrder(
            loadableStudy, true))
        .thenReturn(portRotationList);
    loadableStudyService.getSynopticalDataByPortId(synopticalRequest, responseObserver);
    // get results when no errors
    List<SynopticalTableReply> results = responseObserver.getValues();
    assertEquals(true, results.size() > 0);
  }

  private SynopticalTableRequest createSynopticalTableRequest() {
    SynopticalTableRequest request =
        SynopticalTableRequest.newBuilder().setLoadableStudyId(30).build();
    return request;
  }
}
