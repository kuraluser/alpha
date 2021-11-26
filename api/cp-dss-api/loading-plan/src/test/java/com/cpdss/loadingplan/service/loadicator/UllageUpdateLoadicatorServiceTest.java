/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.loadicator;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.loadingplan.communication.LoadingPlanStagingService;
import com.cpdss.loadingplan.domain.algo.*;
import com.cpdss.loadingplan.entity.*;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.repository.*;
import com.cpdss.loadingplan.service.LoadingPlanCommunicationService;
import com.cpdss.loadingplan.service.LoadingPlanService;
import com.cpdss.loadingplan.service.algo.LoadingPlanAlgoService;
import com.google.gson.JsonArray;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

@TestPropertySource(properties = "cpdss.communication.enable = false")
@SpringJUnitConfig(
    classes = {
      UllageUpdateLoadicatorService.class,
    })
public class UllageUpdateLoadicatorServiceTest {
  @Autowired UllageUpdateLoadicatorService ullageUpdateLoadicatorService;

  @MockBean LoadingInformationRepository loadingInformationRepository;
  @MockBean PortLoadingPlanStowageDetailsRepository portLoadingPlanStowageDetailsRepository;
  @MockBean PortLoadingPlanBallastDetailsRepository portLoadingPlanBallastDetailsRepository;
  @MockBean PortLoadingPlanRobDetailsRepository portLoadingPlanRobDetailsRepository;
  @MockBean PortLoadingPlanStowageTempDetailsRepository portLoadingPlanStowageDetailsTempRepository;
  @MockBean PortLoadingPlanBallastTempDetailsRepository portLoadingPlanBallastDetailsTempRepository;

  @MockBean
  PortLoadingPlanStabilityParametersRepository portLoadingPlanStabilityParametersRepository;

  @MockBean AlgoErrorHeadingRepository algoErrorHeadingRepository;
  @MockBean AlgoErrorsRepository algoErrorsRepository;

  @MockBean
  PortLoadingPlanCommingleTempDetailsRepository portLoadingPlanCommingleDetailsTempRepository;

  @MockBean LoadingPlanAlgoService loadingPlanAlgoService;
  @MockBean LoadicatorService loadicatorService;
  @MockBean LoadingPlanService loadingPlanService;
  @MockBean LoadingPlanStagingService loadingPlanStagingService;
  @MockBean LoadingPlanCommunicationStatusRepository loadingPlanCommunicationStatusRepository;
  @MockBean private LoadingPlanCommunicationService communicationService;
  @MockBean RestTemplate restTemplate;
  public static final String SUCCESS = "SUCCESS";
  public static final String FAILED = "FAILED";

  @Value("${cpdss.communication.enable}")
  private boolean enableCommunication;

  @Test
  void testSaveLoadicatorInfoForUllageUpdate()
      throws GenericServiceException, InvocationTargetException, IllegalAccessException {
    LoadingPlanModels.UllageBillRequest request =
        LoadingPlanModels.UllageBillRequest.newBuilder()
            .addUpdateUllage(
                LoadingPlanModels.UpdateUllage.newBuilder().setLoadingInformationId(1l).build())
            .build();
    when(loadingInformationRepository.findByIdAndIsActiveTrue(anyLong()))
        .thenReturn(Optional.of(getLoadingInfoEntity()));
    when(loadingPlanStagingService.getCommunicationData(
            anyList(), anyString(), anyString(), anyLong(), any()))
        .thenReturn(new JsonArray());
    when(communicationService.passRequestPayloadToEnvoyWriter(anyString(), anyLong(), anyString()))
        .thenReturn(getEwReply());
    when(this.loadingPlanCommunicationStatusRepository.save(
            any(LoadingPlanCommunicationStatus.class)))
        .thenReturn(new LoadingPlanCommunicationStatus());
    when(loadingPlanAlgoService.getLoadingInformationStatus(anyLong()))
        .thenReturn(Optional.of(getCommunicationStatus()));
    doNothing()
        .when(loadingPlanService)
        .updateLoadingPlanStatus(
            any(LoadingInformation.class), any(LoadingInformationStatus.class), anyInt());
    doNothing()
        .when(loadingPlanAlgoService)
        .createLoadingInformationAlgoStatus(
            any(LoadingInformation.class),
            anyString(),
            any(LoadingInformationStatus.class),
            anyInt());

    ReflectionTestUtils.setField(ullageUpdateLoadicatorService, "enableCommunication", true);
    ReflectionTestUtils.setField(ullageUpdateLoadicatorService, "env", "ship");

    var result = ullageUpdateLoadicatorService.saveLoadicatorInfoForUllageUpdate(request);
    assertTrue(result != null);
  }

  private LoadicatorAlgoResponse getAlgoResponse() {
    List<LoadicatorResult> resultList = new ArrayList<>();
    LoadicatorResult loadicatorResult = new LoadicatorResult();
    loadicatorResult.setJudgement(Arrays.asList("1"));
    loadicatorResult.setErrorDetails(Arrays.asList("1"));
    resultList.add(loadicatorResult);
    LoadicatorAlgoResponse algoResponse = new LoadicatorAlgoResponse();
    algoResponse.setLoadicatorResults(resultList);
    return algoResponse;
  }

  @Test
  void testSaveLoadicatorInfoForUllageUpdateElseWithLoadicator()
      throws GenericServiceException, InvocationTargetException, IllegalAccessException {
    LoadingPlanModels.UllageBillRequest request =
        LoadingPlanModels.UllageBillRequest.newBuilder()
            .addUpdateUllage(
                LoadingPlanModels.UpdateUllage.newBuilder()
                    .setArrivalDepartutre(1)
                    .setLoadingInformationId(1l)
                    .build())
            .build();
    List<VesselInfo.VesselDetail> vesselDetailList = new ArrayList<>();
    VesselInfo.VesselDetail vesselDetail =
        VesselInfo.VesselDetail.newBuilder().setHasLoadicator(true).build();
    vesselDetailList.add(vesselDetail);
    List<VesselInfo.VesselTankDetail> tankDetailList = new ArrayList<>();
    VesselInfo.VesselTankDetail tankDetail =
        VesselInfo.VesselTankDetail.newBuilder()
            .setTankName("1")
            .setShortName("1")
            .setTankId(1l)
            .build();
    tankDetailList.add(tankDetail);
    VesselInfo.VesselReply vesselReply =
        VesselInfo.VesselReply.newBuilder()
            .addAllVessels(vesselDetailList)
            .addAllVesselTanks(tankDetailList)
            .build();

    when(loadingInformationRepository.findByIdAndIsActiveTrue(anyLong()))
        .thenReturn(Optional.of(getLoadingInfoEntity()));
    when(loadingPlanAlgoService.getLoadingInformationStatus(anyLong()))
        .thenReturn(Optional.of(getCommunicationStatus()));
    doNothing()
        .when(loadingPlanService)
        .updateLoadingPlanStatus(
            any(LoadingInformation.class), any(LoadingInformationStatus.class), anyInt());
    doNothing()
        .when(loadingPlanAlgoService)
        .createLoadingInformationAlgoStatus(
            any(LoadingInformation.class),
            anyString(),
            any(LoadingInformationStatus.class),
            anyInt());
    when(loadicatorService.getVesselDetailsForLoadicator(any(LoadingInformation.class)))
        .thenReturn(vesselReply);
    when(portLoadingPlanStowageDetailsTempRepository
            .findByLoadingInformationAndConditionTypeAndIsActive(anyLong(), anyInt(), anyBoolean()))
        .thenReturn(getStowageTempDetails());
    when(portLoadingPlanBallastDetailsTempRepository
            .findByLoadingInformationAndConditionTypeAndIsActive(anyLong(), anyInt(), anyBoolean()))
        .thenReturn(getBallastTempDetails());
    when(portLoadingPlanRobDetailsRepository
            .findByLoadingInformationAndConditionTypeAndValueTypeAndIsActive(
                anyLong(), anyInt(), anyInt(), anyBoolean()))
        .thenReturn(getRobDetails());
    when(portLoadingPlanCommingleDetailsTempRepository
            .findByLoadingInformationAndConditionTypeAndIsActive(anyLong(), anyInt(), anyBoolean()))
        .thenReturn(getCommingleTempDetails());
    when(loadicatorService.getCargoNominationDetails(anySet())).thenReturn(getDetailsMap());
    when(loadicatorService.getCargoInfoForLoadicator(any(LoadingInformation.class)))
        .thenReturn(getCargoReply());
    when(loadicatorService.getPortInfoForLoadicator(any(LoadingInformation.class)))
        .thenReturn(getPortReply());
    doNothing()
        .when(loadicatorService)
        .buildStowagePlan(
            any(LoadingInformation.class),
            anyInt(),
            anyString(),
            any(CargoInfo.CargoReply.class),
            any(VesselInfo.VesselReply.class),
            any(PortInfo.PortReply.class),
            any(Loadicator.StowagePlan.Builder.class),
            any(BigDecimal.class));
    when(loadicatorService.saveLoadicatorInfo(any(Loadicator.LoadicatorRequest.class)))
        .thenReturn(getLoadicatorReply());
    doNothing()
        .when(loadingPlanAlgoService)
        .updateLoadingInfoAlgoStatus(
            any(LoadingInformation.class), anyString(), any(LoadingInformationStatus.class));

    ReflectionTestUtils.setField(ullageUpdateLoadicatorService, "loadicatorUrl", "url");
    ReflectionTestUtils.setField(ullageUpdateLoadicatorService, "rootFolder", "D:\\");
    ReflectionTestUtils.setField(ullageUpdateLoadicatorService, "enableCommunication", false);
    ReflectionTestUtils.setField(ullageUpdateLoadicatorService, "env", "ship");

    var result = ullageUpdateLoadicatorService.saveLoadicatorInfoForUllageUpdate(request);
    assertTrue(result != null);
  }

  private Loadicator.LoadicatorReply getLoadicatorReply() {
    Loadicator.LoadicatorReply reply =
        Loadicator.LoadicatorReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  private Map<Long, LoadableStudy.CargoNominationDetail> getDetailsMap() {
    Map<Long, LoadableStudy.CargoNominationDetail> details =
        new HashMap<Long, LoadableStudy.CargoNominationDetail>();
    LoadableStudy.CargoNominationDetail cargoNominationDetail =
        LoadableStudy.CargoNominationDetail.newBuilder()
            .setCargoId(1l)
            .setAbbreviation("1")
            .build();
    details.put(1l, cargoNominationDetail);
    return details;
  }

  private List<PortLoadingPlanStowageTempDetails> getStowageTempDetails() {
    List<PortLoadingPlanStowageTempDetails> tempStowageDetails = new ArrayList<>();
    PortLoadingPlanStowageTempDetails stowageTempDetails = new PortLoadingPlanStowageTempDetails();
    stowageTempDetails.setCargoNominationXId(1l);
    stowageTempDetails.setCargoXId(1l);
    stowageTempDetails.setApi(new BigDecimal(1));
    stowageTempDetails.setTemperature(new BigDecimal(1));
    tempStowageDetails.add(stowageTempDetails);
    return tempStowageDetails;
  }

  private List<PortLoadingPlanBallastTempDetails> getBallastTempDetails() {
    List<PortLoadingPlanBallastTempDetails> tempBallastDetails = new ArrayList<>();
    PortLoadingPlanBallastTempDetails ballastTempDetails = new PortLoadingPlanBallastTempDetails();
    ballastTempDetails.setSg(new BigDecimal(1));
    ballastTempDetails.setQuantity(new BigDecimal(1));
    ballastTempDetails.setTankXId(1l);
    tempBallastDetails.add(ballastTempDetails);
    return tempBallastDetails;
  }

  private List<PortLoadingPlanRobDetails> getRobDetails() {
    List<PortLoadingPlanRobDetails> robDetails = new ArrayList<>();
    PortLoadingPlanRobDetails details = new PortLoadingPlanRobDetails();
    details.setTankXId(1l);
    details.setQuantity(new BigDecimal(1));
    robDetails.add(details);
    return robDetails;
  }

  private List<PortLoadingPlanCommingleTempDetails> getCommingleTempDetails() {
    List<PortLoadingPlanCommingleTempDetails> tempCommingleDetails = new ArrayList<>();
    PortLoadingPlanCommingleTempDetails commingleTempDetails =
        new PortLoadingPlanCommingleTempDetails();
    commingleTempDetails.setGrade("1");
    commingleTempDetails.setTankId(1l);
    commingleTempDetails.setQuantity("1");
    commingleTempDetails.setCargoNomination1XId(1l);
    commingleTempDetails.setCargoNomination2XId(1l);
    commingleTempDetails.setApi("1");
    commingleTempDetails.setTemperature("1");
    commingleTempDetails.setCargo1XId(1l);
    commingleTempDetails.setCargo2XId(1l);
    commingleTempDetails.setId(1l);
    commingleTempDetails.setQuantity1M3("1");
    commingleTempDetails.setQuantity1MT("1");
    commingleTempDetails.setQuantity2MT("1");
    commingleTempDetails.setQuantity2M3("1");
    commingleTempDetails.setUllage1("1");
    commingleTempDetails.setUllage2("1");
    commingleTempDetails.setConditionType(1);
    commingleTempDetails.setValueType(1);
    commingleTempDetails.setQuantity("1");
    commingleTempDetails.setQuantity("1");

    tempCommingleDetails.add(commingleTempDetails);
    return tempCommingleDetails;
  }

  private PortInfo.PortReply getPortReply() {
    List<PortInfo.PortDetail> portDetailList = new ArrayList<>();
    PortInfo.PortDetail portDetail =
        PortInfo.PortDetail.newBuilder().setCode("1").setWaterDensity("1").build();
    portDetailList.add(portDetail);
    PortInfo.PortReply portReply =
        PortInfo.PortReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .addAllPorts(portDetailList)
            .build();
    return portReply;
  }

  private CargoInfo.CargoReply getCargoReply() {
    CargoInfo.CargoReply cargoReply =
        CargoInfo.CargoReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return cargoReply;
  }

  private VesselInfo.VesselReply getVesselReply() {
    List<VesselInfo.VesselDetail> vesselDetailList = new ArrayList<>();
    VesselInfo.VesselDetail vesselDetail =
        VesselInfo.VesselDetail.newBuilder()
            .setId(1l)
            .setImoNumber("1")
            .setTypeOfShip("1")
            .setCode("1")
            .setProvisionalConstant("1")
            .setDeadweightConstant("1")
            .setHasLoadicator(false)
            .build();
    vesselDetailList.add(vesselDetail);
    VesselInfo.VesselReply vesselReply =
        VesselInfo.VesselReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .addAllVessels(vesselDetailList)
            .build();
    return vesselReply;
  }

  private LoadingInformationStatus getCommunicationStatus() {
    LoadingInformationStatus status = new LoadingInformationStatus();
    return status;
  }

  private EnvoyWriter.WriterReply getEwReply() {
    EnvoyWriter.WriterReply writerReply =
        EnvoyWriter.WriterReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .setMessageId("1")
            .build();
    return writerReply;
  }

  private LoadingInformation getLoadingInfoEntity() {
    LoadingInformation loadingInfoOpt = new LoadingInformation();
    loadingInfoOpt.setVoyageId(1l);
    loadingInfoOpt.setVesselXId(1l);
    loadingInfoOpt.setPortXId(1l);
    loadingInfoOpt.setSynopticalTableXId(1l);
    loadingInfoOpt.setLoadablePatternXId(1l);
    loadingInfoOpt.setPortRotationXId(1l);
    loadingInfoOpt.setId(1l);
    return loadingInfoOpt;
  }
}
