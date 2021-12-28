/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static org.mockito.ArgumentMatchers.*;

import com.cpdss.loadablestudy.domain.*;
import com.cpdss.loadablestudy.domain.CommingleCargo;
import com.cpdss.loadablestudy.domain.LoadableQuantity;
import com.cpdss.loadablestudy.domain.LoadableStudy;
import com.cpdss.loadablestudy.domain.LoadableStudyPortRotation;
import com.cpdss.loadablestudy.domain.OnHandQuantity;
import com.cpdss.loadablestudy.domain.SynopticalTable;
import com.cpdss.loadablestudy.entity.*;
import com.cpdss.loadablestudy.repository.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(
    classes = {
      LoadableStudyServiceShore.class,
    })
public class LoadableStudyServiceShoreTest {
  @Autowired LoadableStudyServiceShore loadableStudyServiceShore;

  @MockBean VoyageRepository voyageRepository;
  @MockBean LoadableStudyRepository loadableStudyRepository;
  @MockBean LoadableStudyCommunicationStatusRepository loadableStudyCommunicationStatusRepository;
  @MockBean CommingleCargoRepository commingleCargoRepository;
  @MockBean CargoNominationRepository cargoNominationRepository;
  @MockBean LoadableStudyPortRotationRepository loadableStudyPortRotationRepository;
  @MockBean OnHandQuantityRepository onHandQuantityRepository;
  @MockBean OnBoardQuantityRepository onBoardQuantityRepository;
  @MockBean LoadableQuantityRepository loadableQuantityRepository;
  @MockBean VoyageStatusRepository voyageStatusRepository;
  @MockBean CargoOperationRepository cargoOperationRepository;
  @MockBean LoadableStudyRuleService loadableStudyRuleService;
  @MockBean LoadableStudyRuleRepository loadableStudyRuleRepository;
  @MockBean SynopticalTableRepository synopticalTableRepository;
  @MockBean CargoNominationOperationDetailsRepository cargoNominationOperationDetailsRepository;
  @MockBean LoadablePlanStowageDetailsTempRepository stowageDetailsTempRepository;
  @MockBean LoadablePlanBallastDetailsRepository loadablePlanBallastDetailsRepository;
  @MockBean LoadablePlanCommingleDetailsRepository loadablePlanCommingleDetailsRepository;
  @MockBean LoadablePlanStowageDetailsRespository loadablePlanStowageDetailsRespository;
  @MockBean LoadablePatternRepository loadablePatternRepository;
  @MockBean LoadableStudyPortRotationService loadableStudyPortRotationService;
  @MockBean LoadableQuantityService loadableQuantityService;
  @MockBean LoadableStudyAttachmentsRepository loadableStudyAttachmentsRepository;
  @MockBean LoadableStudyRuleInputRepository loadableStudyRuleInputRepository;
  @MockBean LoadablePatternService loadablePatternService;
  @MockBean LoadablePlanQuantityRepository loadablePlanQuantityRepository;
  @MockBean LoadablePatternCargoToppingOffSequenceRepository toppingOffSequenceRepository;

  @MockBean
  LoadablePlanCommingleDetailsPortwiseRepository loadablePlanCommingleDetailsPortwiseRepository;

  @MockBean LoadablePatternCargoDetailsRepository loadablePatternCargoDetailsRepository;
  @MockBean LoadablePlanStowageBallastDetailsRepository loadablePlanStowageBallastDetailsRepository;
  @MockBean SynopticalTableLoadicatorDataRepository synopticalTableLoadicatorDataRepository;

  private PatternDetails getPatternDetails() {
    PatternDetails patternDetails = new PatternDetails();
    List<LoadablePlanQuantityDto> list = new ArrayList<>();
    LoadablePlanQuantityDto quantity = new LoadablePlanQuantityDto();
    quantity.setId(1l);
    quantity.setDifferencePercentage("1");
    quantity.setEstimatedApi(new BigDecimal(1));
    quantity.setEstimatedTemperature(new BigDecimal(1));
    quantity.setCargoXId(1l);
    quantity.setLoadableMt("1l");
    quantity.setOrderQuantity(new BigDecimal(1));
    quantity.setCargoAbbreviation("1l");
    quantity.setCargoColor("1");
    quantity.setPriority(1);
    quantity.setLoadingOrder(1);
    quantity.setMinTolerence("1");
    quantity.setMaxTolerence("1");
    quantity.setSlopQuantity("1");
    quantity.setCargoNominationId(1l);
    quantity.setCargoNominationTemperature(new BigDecimal(1));
    quantity.setTimeRequiredForLoading("1");
    list.add(quantity);
    patternDetails.setLoadablePlanQuantityList(list);

    List<LoadablePatternCargoToppingOffSequenceDto> dtoList = new ArrayList<>();
    LoadablePatternCargoToppingOffSequenceDto sequenceDto =
        new LoadablePatternCargoToppingOffSequenceDto();
    sequenceDto.setId(1l);
    sequenceDto.setCargoXId(1l);
    sequenceDto.setTankXId(1l);
    sequenceDto.setOrderNumber(1);
    patternDetails.setToppingOffSequenceList(dtoList);

    List<LoadablePlanCommingleDetailsDto> detailsDtoList = new ArrayList<>();
    LoadablePlanCommingleDetailsDto detailsDto = new LoadablePlanCommingleDetailsDto();
    detailsDto.setId(1l);
    detailsDto.setApi("1");
    detailsDto.setCargo1Abbreviation("1");
    detailsDto.setCargo1Mt("1");
    detailsDto.setCargo1Percentage("1");
    detailsDto.setCargo2Abbreviation("1");
    detailsDto.setCargo2Mt("1");
    detailsDto.setCargo2Percentage("1");
    detailsDto.setGrade("1");
    detailsDto.setQuantity("1");
    detailsDto.setTankName("1");
    detailsDto.setTemperature("1");
    detailsDto.setOrderQuantity("1");
    detailsDto.setPriority(1);
    detailsDto.setLoadingOrder(1);
    detailsDto.setTankId(1l);
    detailsDto.setFillingRatio("1");
    detailsDto.setCorrectionFactor("1");
    detailsDto.setCorrectedUllage("1");
    detailsDto.setSlopQuantity("1");
    detailsDto.setTimeRequiredForLoading("1");
    detailsDto.setCargo2NominationId(1l);
    detailsDto.setTankShortName("1");
    detailsDtoList.add(detailsDto);
    patternDetails.setLoadablePlanCommingleDetails(detailsDtoList);

    List<LoadablePlanStowageDetailsDto> stowageDetailsDtoList = new ArrayList<>();
    LoadablePlanStowageDetailsDto stowageDetailsDto = new LoadablePlanStowageDetailsDto();
    stowageDetailsDto.setId(1l);
    stowageDetailsDto.setApi("1");
    stowageDetailsDto.setAbbreviation("1");
    stowageDetailsDto.setColorCode("1");
    stowageDetailsDto.setFillingPercentage("1");
    stowageDetailsDto.setWeight("1");
    stowageDetailsDto.setCargoNominationId(1l);
    stowageDetailsDto.setCargoNominationTemperature(new BigDecimal(1));
    stowageDetailsDto.setTemperature("1");
    stowageDetailsDto.setTankId(1l);
    stowageDetailsDto.setCorrectionFactor("1");
    stowageDetailsDto.setCorrectedUllage("1");
    stowageDetailsDtoList.add(stowageDetailsDto);
    patternDetails.setLoadablePlanStowageDetailsList(stowageDetailsDtoList);

    List<LoadablePlanBallastDetailsDto> ballastDetailsDtoList = new ArrayList<>();
    LoadablePlanBallastDetailsDto ballastDetailsDto = new LoadablePlanBallastDetailsDto();
    ballastDetailsDto.setId(1l);
    ballastDetailsDto.setColorCode("1");
    ballastDetailsDto.setTankId(1l);
    ballastDetailsDto.setMetricTon("1");
    ballastDetailsDto.setPercentage("1");
    ballastDetailsDto.setSg("1");
    ballastDetailsDto.setTankName("1");
    ballastDetailsDto.setRdgLevel("1");
    ballastDetailsDto.setCorrectedLevel("1");
    ballastDetailsDto.setCorrectionFactor("1");
    ballastDetailsDtoList.add(ballastDetailsDto);
    patternDetails.setLoadablePlanBallastDetailsList(ballastDetailsDtoList);

    List<LoadablePlanComminglePortwiseDetailsDto> portwiseDetailsDtoList = new ArrayList<>();
    LoadablePlanComminglePortwiseDetailsDto portwiseDetailsDto =
        new LoadablePlanComminglePortwiseDetailsDto();
    portwiseDetailsDto.setId(1l);
    portwiseDetailsDto.setPortId(1l);
    portwiseDetailsDto.setTankId(1l);
    portwiseDetailsDto.setPortRotationXid(1l);
    portwiseDetailsDto.setOperationType("1");
    portwiseDetailsDto.setApi("1");
    portwiseDetailsDto.setCargo1Abbreviation("1");
    portwiseDetailsDto.setCargo1Mt("1");
    portwiseDetailsDto.setCargo1Percentage("1");
    portwiseDetailsDto.setCargo2Abbreviation("1");
    portwiseDetailsDto.setCargo2Mt("1");
    portwiseDetailsDto.setCargo2Percentage("1");
    portwiseDetailsDto.setGrade("1");
    portwiseDetailsDto.setQuantity("1");
    portwiseDetailsDto.setTankName("1");
    portwiseDetailsDto.setTemperature("1");
    portwiseDetailsDto.setOrderQuantity("1");
    portwiseDetailsDto.setPriority(1);
    portwiseDetailsDto.setLoadingOrder(1);
    portwiseDetailsDto.setTankId(1l);
    portwiseDetailsDto.setFillingRatio("1");
    portwiseDetailsDto.setCorrectionFactor("1");
    portwiseDetailsDto.setCorrectedUllage("1");
    portwiseDetailsDto.setCargo2NominationId(1l);
    portwiseDetailsDto.setTankShortName("1");
    portwiseDetailsDto.setCorrectionFactor("1");
    portwiseDetailsDtoList.add(portwiseDetailsDto);
    patternDetails.setLoadablePlanComminglePortwiseDetailsList(portwiseDetailsDtoList);

    List<LoadablePatternCargoDetailsDto> cargoDetailsDtoList = new ArrayList<>();
    LoadablePatternCargoDetailsDto cargoDetailsDto = new LoadablePatternCargoDetailsDto();
    cargoDetailsDto.setId(1l);
    cargoDetailsDto.setPortId(1l);
    cargoDetailsDto.setColorCode("1");
    cargoDetailsDto.setOperationType("1");
    cargoDetailsDto.setLoadablePatternId(1l);
    cargoDetailsDto.setPlannedQuantity(new BigDecimal(1));
    cargoDetailsDto.setPortRotationId(1l);
    cargoDetailsDto.setTankId(1l);
    cargoDetailsDto.setAbbreviation("1");
    cargoDetailsDto.setApi(new BigDecimal(1));
    cargoDetailsDto.setTemperature(new BigDecimal(1));
    cargoDetailsDto.setCorrectedUllage(new BigDecimal(1));
    cargoDetailsDto.setCargoNominationId(1l);
    cargoDetailsDto.setCargoNominationTemperature(new BigDecimal(1));
    cargoDetailsDto.setFillingRatio("1");
    cargoDetailsDto.setOnBoard(new BigDecimal(1));
    cargoDetailsDto.setMaxTankVolume(new BigDecimal(1));
    cargoDetailsDto.setCorrectionFactor("1");
    cargoDetailsDtoList.add(cargoDetailsDto);
    patternDetails.setLoadablePatternCargoDetailsList(cargoDetailsDtoList);

    List<LoadablePlanStowageBallastDetailsDto> list1 = new ArrayList<>();
    LoadablePlanStowageBallastDetailsDto stowageBallastDetailsDto =
        new LoadablePlanStowageBallastDetailsDto();
    stowageBallastDetailsDto.setId(1l);
    stowageBallastDetailsDto.setLoadablePatternId(1l);
    stowageBallastDetailsDto.setOperationType("1");
    stowageBallastDetailsDto.setPortRotationId(1l);
    stowageBallastDetailsDto.setPortXId(1l);
    stowageBallastDetailsDto.setQuantity(new BigDecimal(1));
    stowageBallastDetailsDto.setTankXId(1l);
    stowageBallastDetailsDto.setColorCode("1");
    stowageBallastDetailsDto.setSg("1");
    stowageBallastDetailsDto.setCorrectedUllage("1");
    stowageBallastDetailsDto.setCorrectionFactor("1");
    stowageBallastDetailsDto.setRdgUllage("1");
    stowageBallastDetailsDto.setFillingPercentage("1");
    stowageBallastDetailsDto.setVolume(new BigDecimal(1));
    stowageBallastDetailsDto.setMaxTankVolume(new BigDecimal(1));
    list1.add(stowageBallastDetailsDto);
    patternDetails.setLoadablePlanStowageBallastDetailsList(list1);

    List<SynopticalTableLoadicatorDataDto> dataDtoList = new ArrayList<>();
    SynopticalTableLoadicatorDataDto loadicatorDataDto = new SynopticalTableLoadicatorDataDto();
    loadicatorDataDto.setId(1l);
    loadicatorDataDto.setLoadablePatternId(1l);
    loadicatorDataDto.setCalculatedDraftFwdPlanned(new BigDecimal(1));
    loadicatorDataDto.setCalculatedDraftMidPlanned(new BigDecimal(1));
    loadicatorDataDto.setCalculatedDraftAftPlanned(new BigDecimal(1));
    loadicatorDataDto.setCalculatedTrimPlanned(new BigDecimal(1));
    loadicatorDataDto.setBendingMoment(new BigDecimal(1));
    loadicatorDataDto.setShearingForce(new BigDecimal(1));
    loadicatorDataDto.setSynopticalTable(new SynopticalTableDto());

    list1.add(stowageBallastDetailsDto);
    patternDetails.setLoadablePlanStowageBallastDetailsList(list1);

    return patternDetails;
  }

  private com.cpdss.loadablestudy.entity.CargoOperation getCargoOperation() {
    com.cpdss.loadablestudy.entity.CargoOperation cargoOperation =
        new com.cpdss.loadablestudy.entity.CargoOperation();
    cargoOperation.setName("1");
    return cargoOperation;
  }

  private VoyageDto getVoyageDto() {
    VoyageDto voyageDto = new VoyageDto();
    voyageDto.setCaptainXId(1l);
    voyageDto.setId(1l);
    voyageDto.setVoyageNo("1");
    voyageDto.setChiefOfficerXId(1l);
    voyageDto.setStartTimezoneId(1l);
    voyageDto.setEndTimezoneId(1l);
    voyageDto.setVoyageStartDate("1986-04-08T12:30");
    voyageDto.setVoyageEndDate("1986-04-08T12:30");
    return voyageDto;
  }

  private List<com.cpdss.loadablestudy.domain.CargoNomination> getCargoNominationList() {
    List<com.cpdss.loadablestudy.domain.CargoNomination> cargoNominationList = new ArrayList<>();
    com.cpdss.loadablestudy.domain.CargoNomination cargoNomination =
        new com.cpdss.loadablestudy.domain.CargoNomination();
    cargoNomination.setAbbreviation("1");
    cargoNomination.setColor("1");
    cargoNominationList.add(cargoNomination);
    return cargoNominationList;
  }

  private List<com.cpdss.loadablestudy.domain.CommingleCargo> getCommingleCargoList() {
    List<com.cpdss.loadablestudy.domain.CommingleCargo> commingleCargoList = new ArrayList<>();
    com.cpdss.loadablestudy.domain.CommingleCargo commingleCargo = new CommingleCargo();
    commingleCargo.setId(1l);
    commingleCargo.setCargoNomination1Id(1l);
    commingleCargo.setCargoNomination2Id(1l);
    commingleCargo.setCargo1Id(1l);
    commingleCargo.setCargo2Id(1l);
    commingleCargo.setQuantity("1");
    commingleCargoList.add(commingleCargo);
    return commingleCargoList;
  }

  private com.cpdss.loadablestudy.entity.LoadableStudy getLoadableStudyEntity() {
    com.cpdss.loadablestudy.entity.LoadableStudy loadableStudyEntity =
        new com.cpdss.loadablestudy.entity.LoadableStudy();
    Voyage voyage = new Voyage();
    voyage.setCaptainXId(1l);
    voyage.setVoyageNo("1 1");
    voyage.setId(1l);
    loadableStudyEntity.setVoyage(voyage);
    loadableStudyEntity.setId(1l);
    loadableStudyEntity.setVesselXId(1l);
    loadableStudyEntity.setDetails("1");
    loadableStudyEntity.setName("test");
    return loadableStudyEntity;
  }

  private com.cpdss.loadablestudy.domain.LoadableStudy getLoadableStudyDomain() {
    com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy = new LoadableStudy();
    loadableStudy.setId(1l);
    loadableStudy.setVesselId(1l);
    loadableStudy.setName("1");
    loadableStudy.setDetails("1");
    loadableStudy.setCharterer("1");
    loadableStudy.setSubCharterer("1");
    loadableStudy.setDraftMark("1");
    loadableStudy.setLoadlineId(1l);
    loadableStudy.setDraftRestriction("1");
    loadableStudy.setEstimatedMaxSG("1");
    loadableStudy.setMaxAirTemp("1");
    loadableStudy.setMaxWaterTemp("1");
    loadableStudy.setLoadOnTopForSlopTank(true);

    List<LoadableStudyAttachment> loadableStudyAttachmentList = new ArrayList<>();
    LoadableStudyAttachment loadableStudyAttachment = new LoadableStudyAttachment();
    loadableStudyAttachment.setFileName("1");
    loadableStudyAttachment.setFilePath("1");
    loadableStudyAttachment.setContent(new byte[1]);
    loadableStudyAttachmentList.add(loadableStudyAttachment);

    loadableStudy.setLoadableStudyAttachment(loadableStudyAttachmentList);
    loadableStudy.setVoyage(getVoyageDto());
    loadableStudy.setCargoNomination(getCargoNominationList());
    loadableStudy.setCargoNominationOperationDetails(getCargoNominationOperationDetailsList());
    loadableStudy.setLoadableStudyPortRotation(getPortRotationList());
    loadableStudy.setSynopticalTableDetails(getSynopticalTableList());
    loadableStudy.setOnHandQuantity(getOnHandQuantityList());
    loadableStudy.setOnBoardQuantity(getOnBoardQuantityEntities());
    loadableStudy.setLoadableQuantity(getLoadableQuantityDomain());
    loadableStudy.setCommingleCargos(getCommingleCargoList());
    return loadableStudy;
  }

  private Optional<com.cpdss.loadablestudy.entity.CommingleCargo> getCommingleCargoEntityOpt() {
    com.cpdss.loadablestudy.entity.CommingleCargo commingleCargoEntity =
        new com.cpdss.loadablestudy.entity.CommingleCargo();
    commingleCargoEntity.setId(1l);
    return Optional.of(commingleCargoEntity);
  }

  private List<LoadableStudyPortRotation> getPortRotationList() {
    List<LoadableStudyPortRotation> loadableStudyPortRotationList = new ArrayList<>();
    com.cpdss.loadablestudy.domain.LoadableStudyPortRotation loadableStudyPortRotation =
        new com.cpdss.loadablestudy.domain.LoadableStudyPortRotation();
    loadableStudyPortRotation.setPortId(1l);
    loadableStudyPortRotation.setBerthId(1l);
    loadableStudyPortRotation.setSeaWaterDensity(new BigDecimal(1));
    loadableStudyPortRotation.setDistanceBetweenPorts(new BigDecimal(1));
    loadableStudyPortRotation.setTimeOfStay(new BigDecimal(1));
    loadableStudyPortRotation.setMaxDraft(new BigDecimal(1));
    loadableStudyPortRotation.setMaxAirDraft(new BigDecimal(1));
    loadableStudyPortRotation.setEta("2011-12-03T10:15:30");
    loadableStudyPortRotation.setEtd("2011-12-03T10:15:30");
    loadableStudyPortRotation.setLayCanFrom("2007-12-03");
    loadableStudyPortRotation.setLayCanTo("2007-12-03");
    loadableStudyPortRotation.setPortOrder(1l);
    loadableStudyPortRotation.setOperationId(1l);
    loadableStudyPortRotation.setId(1l);
    loadableStudyPortRotationList.add(loadableStudyPortRotation);
    return loadableStudyPortRotationList;
  }

  private List<SynopticalTable> getSynopticalTableList() {
    List<SynopticalTable> synopticalTableList = new ArrayList<>();
    SynopticalTable synopticalTable = new SynopticalTable();
    synopticalTable.setPortId(1l);
    synopticalTable.setId(1l);
    synopticalTable.setLoadableStudyPortRotationId(1l);
    synopticalTable.setOperationType("1");
    synopticalTableList.add(synopticalTable);
    return synopticalTableList;
  }

  private List<CargoNominationOperationDetails> getCargoNominationOperationDetailsList() {
    List<CargoNominationOperationDetails> cargoNominationOperationDetailsList = new ArrayList<>();
    CargoNominationOperationDetails cargoNominationOperationDetails =
        new CargoNominationOperationDetails();
    cargoNominationOperationDetails.setCargoNominationId(1l);
    cargoNominationOperationDetailsList.add(cargoNominationOperationDetails);
    return cargoNominationOperationDetailsList;
  }

  private List<OnHandQuantity> getOnHandQuantityList() {
    List<OnHandQuantity> onHandQuantityList = new ArrayList<>();
    OnHandQuantity onHandQuantity = new OnHandQuantity();
    onHandQuantity.setFueltypeId(1l);
    onHandQuantity.setTankId(1l);
    onHandQuantity.setPortId(1l);
    onHandQuantity.setArrivalQuantity("1");
    onHandQuantity.setArrivalVolume("1");
    onHandQuantity.setDepartureQuantity("1");
    onHandQuantity.setDepartureVolume("1");
    onHandQuantity.setDensity("1");
    onHandQuantityList.add(onHandQuantity);
    return onHandQuantityList;
  }

  private List<com.cpdss.loadablestudy.domain.OnBoardQuantity> getOnBoardQuantityEntities() {
    List<com.cpdss.loadablestudy.domain.OnBoardQuantity> onBoardQuantityEntities =
        new ArrayList<>();
    com.cpdss.loadablestudy.domain.OnBoardQuantity onBoardQuantity =
        new com.cpdss.loadablestudy.domain.OnBoardQuantity();
    onBoardQuantity.setPortId(1l);
    onBoardQuantity.setCargoId(1l);
    onBoardQuantity.setTankId(1l);
    onBoardQuantity.setVolume("1");
    onBoardQuantity.setColorCode("1");
    onBoardQuantityEntities.add(onBoardQuantity);
    return onBoardQuantityEntities;
  }

  private com.cpdss.loadablestudy.domain.LoadableQuantity getLoadableQuantityDomain() {
    com.cpdss.loadablestudy.domain.LoadableQuantity loadableQuantityDomain = new LoadableQuantity();
    loadableQuantityDomain.setConstant("1");
    loadableQuantityDomain.setDeadWeight("1");
    loadableQuantityDomain.setDistanceFromLastPort("1");
    loadableQuantityDomain.setEstDOOnBoard("1");
    loadableQuantityDomain.setEstFOOnBoard("1");
    loadableQuantityDomain.setEstFreshWaterOnBoard("1");
    loadableQuantityDomain.setEstSagging("1");
    loadableQuantityDomain.setOtherIfAny("1");
    loadableQuantityDomain.setSaggingDeduction("1");
    loadableQuantityDomain.setSgCorrection("1");
    loadableQuantityDomain.setTotalQuantity("1");
    loadableQuantityDomain.setTpc("1");
    loadableQuantityDomain.setVesselAverageSpeed("1");
    loadableQuantityDomain.setPortId(1l);
    loadableQuantityDomain.setBoilerWaterOnBoard("1");
    loadableQuantityDomain.setBallast("1");
    loadableQuantityDomain.setRunningDays("1");
    loadableQuantityDomain.setRunningHours("1");
    loadableQuantityDomain.setFoConInSZ("1");
    loadableQuantityDomain.setDraftRestriction("1");
    loadableQuantityDomain.setFoConsumptionPerDay("1");
    return loadableQuantityDomain;
  }
}
