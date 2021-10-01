/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.*;
import static java.lang.String.valueOf;
import static org.springframework.util.StringUtils.isEmpty;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.EnvoyReaderServiceGrpc;
import com.cpdss.common.generated.VesselInfoServiceGrpc;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.common.utils.MessageTypes;
import com.cpdss.loadablestudy.domain.*;
import com.cpdss.loadablestudy.entity.*;
import com.cpdss.loadablestudy.entity.CargoNomination;
import com.cpdss.loadablestudy.entity.CargoOperation;
import com.cpdss.loadablestudy.entity.CommingleCargo;
import com.cpdss.loadablestudy.entity.LoadablePlanBallastDetails;
import com.cpdss.loadablestudy.entity.LoadablePlanQuantity;
import com.cpdss.loadablestudy.entity.LoadablePlanStowageDetails;
import com.cpdss.loadablestudy.entity.LoadableQuantity;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.entity.LoadableStudyPortRotation;
import com.cpdss.loadablestudy.entity.OnBoardQuantity;
import com.cpdss.loadablestudy.entity.OnHandQuantity;
import com.cpdss.loadablestudy.entity.SynopticalTable;
import com.cpdss.loadablestudy.repository.*;
import com.cpdss.loadablestudy.utility.LoadableStudiesConstants;
import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Log4j2
@Service
@Transactional
public class LoadableStudyServiceShore {
  @Value("${loadablestudy.attachement.rootFolder}")
  private String rootFolder;

  @GrpcClient("envoyReaderService")
  private EnvoyReaderServiceGrpc.EnvoyReaderServiceBlockingStub envoyReaderGrpcService;

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @Autowired private CommingleCargoRepository commingleCargoRepository;
  @Autowired private LoadableQuantityRepository loadableQuantityRepository;
  @Autowired private LoadableStudyPortRotationRepository loadableStudyPortRotationRepository;
  @Autowired private CargoNominationRepository cargoNominationRepository;
  @Autowired private VoyageRepository voyageRepository;
  @Autowired private VoyageStatusRepository voyageStatusRepository;
  @Autowired private LoadableStudyRepository loadableStudyRepository;
  @Autowired private OnHandQuantityRepository onHandQuantityRepository;
  @Autowired private OnBoardQuantityRepository onBoardQuantityRepository;
  @Autowired private CargoOperationRepository cargoOperationRepository;
  @Autowired private LoadableStudyRuleService loadableStudyRuleService;
  @Autowired private LoadableStudyRuleRepository loadableStudyRuleRepository;

  @Autowired
  private LoadableStudyCommunicationStatusRepository loadableStudyCommunicationStatusRepository;

  @Autowired private LoadablePlanStowageDetailsTempRepository stowageDetailsTempRepository;
  @Autowired private LoadablePlanBallastDetailsRepository loadablePlanBallastDetailsRepository;
  @Autowired private LoadablePlanCommingleDetailsRepository loadablePlanCommingleDetailsRepository;
  @Autowired LoadablePlanStowageDetailsRespository loadablePlanStowageDetailsRespository;
  @Autowired private LoadablePatternRepository loadablePatternRepository;
  @Autowired private LoadableStudyPortRotationService loadableStudyPortRotationService;
  @Autowired private LoadableQuantityService loadableQuantityService;
  @Autowired private LoadableStudyAttachmentsRepository loadableStudyAttachmentsRepository;

  @Autowired
  private CargoNominationOperationDetailsRepository cargoNominationOperationDetailsRepository;

  @Autowired private LoadableStudyRuleInputRepository loadableStudyRuleInputRepository;
  @Autowired private SynopticalTableRepository synopticalTableRepository;
  @Autowired private LoadablePatternService loadablePatternService;
  @Autowired private LoadablePlanQuantityRepository loadablePlanQuantityRepository;
  @Autowired private LoadablePatternCargoToppingOffSequenceRepository toppingOffSequenceRepository;

  @Autowired
  private LoadablePlanCommingleDetailsPortwiseRepository
      loadablePlanCommingleDetailsPortwiseRepository;

  @Autowired private LoadablePatternCargoDetailsRepository loadablePatternCargoDetailsRepository;

  @Autowired
  private LoadablePlanStowageBallastDetailsRepository loadablePlanStowageBallastDetailsRepository;

  @Autowired
  private SynopticalTableLoadicatorDataRepository synopticalTableLoadicatorDataRepository;

  public LoadableStudy setLoadableStudyShore(String jsonResult, String messageId)
      throws GenericServiceException {
    log.info("inside setLoadablestudyShore ");
    LoadableStudy loadableStudyEntity = null;
    com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy =
        new Gson().fromJson(jsonResult, com.cpdss.loadablestudy.domain.LoadableStudy.class);

    Voyage voyage = saveVoyageShore(loadableStudy.getVesselId(), loadableStudy.getVoyage());
    ModelMapper modelMapper = new ModelMapper();
    if (!checkIfLoadableStudyExist(loadableStudy.getId(), voyage)) {

      try {

        loadableStudyEntity = saveLoadableStudyShore(loadableStudy, voyage);
        saveLoadableStudyCommunicaionStatus(messageId, loadableStudyEntity);
        saveLoadableStudyDataShore(loadableStudyEntity, loadableStudy, modelMapper);
        if (loadableStudyEntity != null) {
          loadableStudyRepository.updateLoadableStudyStatus(
              LoadableStudiesConstants.LOADABLE_STUDY_INITIAL_STATUS_ID,
              loadableStudyEntity.getId());
        }
      } catch (IOException e) {
        log.error("Saving loadable study attachment failed: {}", loadableStudy, e);
        throw new GenericServiceException(
            "Saving loadable study attachment failed: " + loadableStudy,
            CommonErrorCodes.E_CPDSS_FILE_WRITE_ERROR,
            HttpStatusCode.INTERNAL_SERVER_ERROR,
            e);
      }
    } else {
      loadableStudyEntity =
          loadableStudyRepository.findByVoyageAndNameIgnoreCaseAndIsActiveAndPlanningTypeXId(
              voyage, loadableStudy.getName(), true, Common.PLANNING_TYPE.LOADABLE_STUDY_VALUE);
      saveLoadableStudyCommunicaionStatus(messageId, loadableStudyEntity);
      saveLoadableStudyDataShore(loadableStudyEntity, loadableStudy, modelMapper);
    }
    return loadableStudyEntity;
  }

  private void saveLoadableStudyCommunicaionStatus(
      String messageId, LoadableStudy loadableStudyEntity) {
    LoadableStudyCommunicationStatus lsCommunicationStatus = new LoadableStudyCommunicationStatus();
    lsCommunicationStatus.setMessageUUID(messageId);
    lsCommunicationStatus.setCommunicationStatus(
        CommunicationStatus.RECEIVED_WITH_HASH_VERIFIED.getId());
    lsCommunicationStatus.setReferenceId(loadableStudyEntity.getId());
    lsCommunicationStatus.setMessageType(MessageTypes.LOADABLESTUDY.getMessageType());
    lsCommunicationStatus.setCommunicationDateTime(LocalDateTime.now());
    this.loadableStudyCommunicationStatusRepository.save(lsCommunicationStatus);
  }

  private void saveLoadableStudyDataShore(
      LoadableStudy loadableStudyEntity,
      com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy,
      ModelMapper modelMapper)
      throws GenericServiceException {

    List<CommingleCargo> commingleEntities = new ArrayList<>();
    loadableStudy
        .getCommingleCargos()
        .forEach(
            commingleCargo -> {
              try {
                com.cpdss.loadablestudy.entity.CommingleCargo commingleCargoEntity = null;
                if (commingleCargo != null && commingleCargo.getId() != 0) {
                  Optional<CommingleCargo> existingCommingleCargo =
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
                      buildCommingleCargoShore(
                          commingleCargoEntity, commingleCargo, loadableStudyEntity.getId());
                } else if (commingleCargo != null && commingleCargo.getId() == 0) {
                  commingleCargoEntity = new com.cpdss.loadablestudy.entity.CommingleCargo();
                  commingleCargoEntity =
                      buildCommingleCargoShore(
                          commingleCargoEntity, commingleCargo, loadableStudyEntity.getId());
                }

                commingleEntities.add(commingleCargoEntity);
              } catch (Exception e) {
                log.error("Exception in creating entities for save commingle cargo", e);
                throw new RuntimeException(e);
              }
            });
    this.commingleCargoRepository.saveAll(commingleEntities);
    List<CargoNomination> existingCargoNominationList =
        this.cargoNominationRepository.findByLoadableStudyXIdAndIsActive(
            loadableStudyEntity.getId(), true);
    List<CargoNomination> cargoNominationEntities = new ArrayList<>();
    loadableStudy.getCargoNomination().stream()
        .forEach(
            cargoNom -> {
              Optional<CargoNomination> existingCargoNomination = null;
              CargoNomination cargoNomination = null;
              if (!existingCargoNominationList.isEmpty()) {
                existingCargoNomination =
                    existingCargoNominationList.stream()
                        .filter(
                            exCargo ->
                                (exCargo.getAbbreviation().equals(cargoNom.getAbbreviation())
                                    && exCargo.getColor().equals(cargoNom.getColor())))
                        .findFirst();
              }
              if (existingCargoNomination != null) {
                cargoNomination = existingCargoNomination.get();
              } else cargoNomination = new CargoNomination();
              cargoNomination.setLoadableStudyXId(loadableStudyEntity.getId());
              cargoNomination =
                  buildCargoNomination(
                      cargoNomination,
                      cargoNom,
                      loadableStudy.getCargoNominationOperationDetails());
              cargoNominationEntities.add(cargoNomination);
            });
    this.cargoNominationRepository.saveAll(cargoNominationEntities);
    List<LoadableStudyPortRotation> loadableStudyPortRotations = new ArrayList<>();
    loadableStudy.getLoadableStudyPortRotation().stream()
        .forEach(
            portRotation -> {
              LoadableStudyPortRotation loadableStudyPortRotation = new LoadableStudyPortRotation();
              loadableStudyPortRotation.setLoadableStudy(loadableStudyEntity);
              loadableStudyPortRotation =
                  buildLoadableStudyPortRotation(
                      loadableStudyPortRotation,
                      portRotation,
                      loadableStudy.getSynopticalTableDetails());
              loadableStudyPortRotations.add(loadableStudyPortRotation);
            });
    this.loadableStudyPortRotationRepository.saveAll(loadableStudyPortRotations);
    List<OnHandQuantity> onHandQuantityEntities = new ArrayList<>();
    loadableStudy.getOnHandQuantity().stream()
        .forEach(
            onHandQuantity -> {
              OnHandQuantity onHandQuantityEntity = new OnHandQuantity();
              onHandQuantityEntity.setLoadableStudy(loadableStudyEntity);
              onHandQuantityEntity = buildOnHandQuantity(onHandQuantityEntity, onHandQuantity);
              onHandQuantityEntities.add(onHandQuantityEntity);
            });
    this.onHandQuantityRepository.saveAll(onHandQuantityEntities);
    List<OnBoardQuantity> onBoardQuantityEntities = new ArrayList<>();
    loadableStudy.getOnBoardQuantity().stream()
        .forEach(
            onBoardQuantity -> {
              OnBoardQuantity onBoardQuantityEntity = new OnBoardQuantity();
              onBoardQuantityEntity.setLoadableStudy(loadableStudyEntity);
              onBoardQuantityEntity =
                  buildOnBoardQuantityEntity(onBoardQuantityEntity, onBoardQuantity);
              onBoardQuantityEntities.add(onBoardQuantityEntity);
            });
    this.onBoardQuantityRepository.saveAll(onBoardQuantityEntities);
    com.cpdss.loadablestudy.domain.LoadableQuantity loadableQuantityDomain =
        loadableStudy.getLoadableQuantity();
    if (null != loadableQuantityDomain) {
      LoadableQuantity loadableQuantity = new LoadableQuantity();
      loadableQuantity.setLoadableStudyXId(loadableStudyEntity);
      loadableQuantity.setConstant(
          StringUtils.isEmpty(loadableQuantityDomain.getConstant())
              ? null
              : new BigDecimal(loadableQuantityDomain.getConstant()));
      loadableQuantity.setDeadWeight(
          StringUtils.isEmpty(loadableQuantityDomain.getDeadWeight())
              ? null
              : new BigDecimal(loadableQuantityDomain.getDeadWeight()));

      loadableQuantity.setDistanceFromLastPort(
          StringUtils.isEmpty(loadableQuantityDomain.getDistanceFromLastPort())
              ? null
              : new BigDecimal(loadableQuantityDomain.getDistanceFromLastPort()));

      loadableQuantity.setEstimatedDOOnBoard(
          StringUtils.isEmpty(loadableQuantityDomain.getEstDOOnBoard())
              ? null
              : new BigDecimal(loadableQuantityDomain.getEstDOOnBoard()));

      loadableQuantity.setEstimatedFOOnBoard(
          StringUtils.isEmpty(loadableQuantityDomain.getEstFOOnBoard())
              ? null
              : new BigDecimal(loadableQuantityDomain.getEstFOOnBoard()));
      loadableQuantity.setEstimatedFWOnBoard(
          StringUtils.isEmpty(loadableQuantityDomain.getEstFreshWaterOnBoard())
              ? null
              : new BigDecimal(loadableQuantityDomain.getEstFreshWaterOnBoard()));
      loadableQuantity.setEstimatedSagging(
          StringUtils.isEmpty(loadableQuantityDomain.getEstSagging())
              ? null
              : new BigDecimal(loadableQuantityDomain.getEstSagging()));

      loadableQuantity.setOtherIfAny(
          StringUtils.isEmpty(loadableQuantityDomain.getOtherIfAny())
              ? null
              : new BigDecimal(loadableQuantityDomain.getOtherIfAny()));
      loadableQuantity.setSaggingDeduction(
          StringUtils.isEmpty(loadableQuantityDomain.getSaggingDeduction())
              ? null
              : new BigDecimal(loadableQuantityDomain.getSaggingDeduction()));

      loadableQuantity.setSgCorrection(
          StringUtils.isEmpty(loadableQuantityDomain.getSgCorrection())
              ? new BigDecimal("0.0000")
              : new BigDecimal(loadableQuantityDomain.getSgCorrection()));

      loadableQuantity.setTotalQuantity(
          StringUtils.isEmpty(loadableQuantityDomain.getTotalQuantity())
              ? null
              : new BigDecimal(loadableQuantityDomain.getTotalQuantity()));
      loadableQuantity.setTpcatDraft(
          StringUtils.isEmpty(loadableQuantityDomain.getTpc())
              ? null
              : new BigDecimal(loadableQuantityDomain.getTpc()));

      loadableQuantity.setVesselAverageSpeed(
          StringUtils.isEmpty(loadableQuantityDomain.getVesselAverageSpeed())
              ? null
              : new BigDecimal(loadableQuantityDomain.getVesselAverageSpeed()));

      loadableQuantity.setPortId(
          StringUtils.isEmpty(loadableQuantityDomain.getPortId())
              ? null
              : new BigDecimal(loadableQuantityDomain.getPortId()));
      loadableQuantity.setBoilerWaterOnBoard(
          StringUtils.isEmpty(loadableQuantityDomain.getBoilerWaterOnBoard())
              ? null
              : new BigDecimal(loadableQuantityDomain.getBoilerWaterOnBoard()));
      loadableQuantity.setBallast(
          StringUtils.isEmpty(loadableQuantityDomain.getBallast())
              ? null
              : new BigDecimal(loadableQuantityDomain.getBallast()));
      loadableQuantity.setRunningHours(
          StringUtils.isEmpty(loadableQuantityDomain.getRunningHours())
              ? null
              : new BigDecimal(loadableQuantityDomain.getRunningHours()));
      loadableQuantity.setRunningDays(
          StringUtils.isEmpty(loadableQuantityDomain.getRunningDays())
              ? null
              : new BigDecimal(loadableQuantityDomain.getRunningDays()));
      loadableQuantity.setFoConsumptionInSZ(
          StringUtils.isEmpty(loadableQuantityDomain.getFoConInSZ())
              ? null
              : new BigDecimal(loadableQuantityDomain.getFoConInSZ()));
      loadableQuantity.setDraftRestriction(
          StringUtils.isEmpty(loadableQuantityDomain.getDraftRestriction())
              ? null
              : new BigDecimal(loadableQuantityDomain.getDraftRestriction()));

      loadableQuantity.setFoConsumptionPerDay(
          StringUtils.isEmpty(loadableQuantityDomain.getFoConsumptionPerDay())
              ? null
              : new BigDecimal(loadableQuantityDomain.getFoConsumptionPerDay()));
      loadableQuantity.setIsActive(true);
      if (loadableQuantityDomain.getPortId() != null) {
        LoadableStudyPortRotation lsPortRot =
            loadableStudyPortRotationRepository.findByLoadableStudyAndPortXIdAndIsActive(
                loadableStudyEntity, loadableQuantityDomain.getPortId(), true);
        loadableQuantity.setLoadableStudyPortRotation(lsPortRot);
      }

      this.loadableQuantityRepository.save(loadableQuantity);
    }
    /* if (!loadableStudy.getLoadableStudyRuleList().isEmpty()) {
      VesselInfo.VesselRuleRequest.Builder vesselRuleBuilder =
          VesselInfo.VesselRuleRequest.newBuilder();
      vesselRuleBuilder.setSectionId(RuleMasterSection.Plan.getId());
      vesselRuleBuilder.setVesselId(loadableStudyEntity.getVesselXId());
      vesselRuleBuilder.setIsNoDefaultRule(true);
      VesselInfo.VesselRuleReply vesselRuleReply =
          this.vesselInfoGrpcService.getRulesByVesselIdAndSectionId(vesselRuleBuilder.build());
      if (!SUCCESS.equals(vesselRuleReply.getResponseStatus().getStatus())) {
        throw new GenericServiceException(
            "failed to get loadable study rule Details ",
            vesselRuleReply.getResponseStatus().getCode(),
            HttpStatusCode.valueOf(Integer.valueOf(vesselRuleReply.getResponseStatus().getCode())));
      }
      List<LoadableStudyRules> loadableStudyRulesList = new ArrayList<>();
      loadableStudy
          .getLoadableStudyRuleList()
          .forEach(
              rulePlans -> {
                rulePlans
                    .getRules()
                    .forEach(
                        rule -> {
                          LoadableStudyRules loadableStudyRules = new LoadableStudyRules();

                          loadableStudyRules.setLoadableStudy(loadableStudyEntity);
                          loadableStudyRules.setIsActive(true);
                          Optional.ofNullable(rule.getDisplayInSettings())
                              .ifPresent(loadableStudyRules::setDisplayInSettings);
                          Optional.ofNullable(rule.getEnable())
                              .ifPresent(loadableStudyRules::setIsEnable);
                          Optional.ofNullable(rule.getIsHardRule())
                              .ifPresent(loadableStudyRules::setIsHardRule);
                          Optional.ofNullable(rule.getNumericPrecision())
                              .ifPresent(loadableStudyRules::setNumericPrecision);
                          Optional.ofNullable(rule.getNumericScale())
                              .ifPresent(loadableStudyRules::setNumericScale);
                          LoadableStudyRules finalLoadableStudyRules = loadableStudyRules;
                          Optional.ofNullable(rule.getRuleTemplateId())
                              .ifPresent(
                                  item ->
                                      finalLoadableStudyRules.setParentRuleXId(
                                          Long.parseLong(item)));
                          loadableStudyRules.setVesselXId(loadableStudyEntity.getVesselXId());

                          if (rule.getRuleType() != null
                              && rule.getRuleType()
                                  .equalsIgnoreCase(RuleType.ABSOLUTE.getRuleType())) {
                            loadableStudyRules.setRuleTypeXId(RuleType.ABSOLUTE.getId());
                          }
                          if (rule.getRuleType() != null
                              && rule.getRuleType()
                                  .equalsIgnoreCase(RuleType.PREFERABLE.getRuleType())) {
                            loadableStudyRules.setRuleTypeXId(RuleType.PREFERABLE.getId());
                          }
                          List<Common.Rules> rulesList =
                              vesselRuleReply.getRulePlanList().stream()
                                  .filter(
                                      vesselRulePlan ->
                                          rulePlans.getHeader().equals(vesselRulePlan.getHeader()))
                                  .flatMap(rules -> rules.getRulesList().stream())
                                  .collect(Collectors.toList());
                          Optional<Common.Rules> vesselRule1 =
                              rulesList.stream()
                                  .filter(
                                      vesselRule ->
                                          vesselRule
                                              .getRuleTemplateId()
                                              .equals(loadableStudyRules.getParentRuleXId()))
                                  .findFirst();
                          loadableStudyRules.setVesselRuleXId(
                              Long.valueOf(vesselRule1.get().getVesselRuleXId()));
                          Optional.ofNullable(rule.getVesselRuleXId())
                              .ifPresent(
                                  vesselRuleXId ->
                                      finalLoadableStudyRules.setVesselRuleXId(
                                          Long.parseLong(vesselRuleXId)));
                          List<LoadableStudyRuleInput> ruleVesselMappingInputList =
                              new ArrayList<>();
                          for (RulesInputs input : rule.getInputs()) {
                            LoadableStudyRuleInput ruleTemplateInput = new LoadableStudyRuleInput();

                            Optional.ofNullable(input.getDefaultValue())
                                .ifPresent(ruleTemplateInput::setDefaultValue);
                            Optional.ofNullable(input.getMax())
                                .ifPresent(ruleTemplateInput::setMaxValue);
                            Optional.ofNullable(input.getMin())
                                .ifPresent(ruleTemplateInput::setMinValue);
                            Optional.ofNullable(input.getSuffix())
                                .ifPresent(ruleTemplateInput::setSuffix);
                            Optional.ofNullable(input.getPrefix())
                                .ifPresent(ruleTemplateInput::setPrefix);
                            Optional.ofNullable(input.getType())
                                .ifPresent(ruleTemplateInput::setTypeValue);
                            Optional.ofNullable(input.getIsMandatory())
                                .ifPresent(ruleTemplateInput::setIsMandatory);
                            ruleTemplateInput.setIsActive(true);
                            ruleTemplateInput.setLoadableStudyRuleXId(loadableStudyRules);
                            Optional.ofNullable(input.getType())
                                .ifPresent(ruleTemplateInput::setTypeValue);

                            ruleVesselMappingInputList.add(ruleTemplateInput);
                          }
                          loadableStudyRules.setLoadableStudyRuleInputs(ruleVesselMappingInputList);
                          loadableStudyRulesList.add(loadableStudyRules);
                        });
              });
      this.loadableStudyRuleRepository.saveAll(loadableStudyRulesList);
    }*/
  }

  private LoadableStudyPortRotation buildLoadableStudyPortRotation(
      LoadableStudyPortRotation loadableStudyPortRotation,
      com.cpdss.loadablestudy.domain.LoadableStudyPortRotation portRotation,
      List<com.cpdss.loadablestudy.domain.SynopticalTable> synopticalTableDetails) {
    loadableStudyPortRotation.setPortXId(
        portRotation.getPortId() != null ? portRotation.getPortId() : null);
    loadableStudyPortRotation.setBerthXId(
        portRotation.getBerthId() != null ? portRotation.getBerthId() : null);
    loadableStudyPortRotation.setSeaWaterDensity(
        portRotation.getSeaWaterDensity() != null ? portRotation.getSeaWaterDensity() : null);

    loadableStudyPortRotation.setDistanceBetweenPorts(
        portRotation.getDistanceBetweenPorts() != null
            ? portRotation.getDistanceBetweenPorts()
            : null);
    loadableStudyPortRotation.setTimeOfStay(
        isEmpty(portRotation.getTimeOfStay()) ? null : portRotation.getTimeOfStay());
    loadableStudyPortRotation.setMaxDraft(
        isEmpty(portRotation.getMaxDraft()) ? null : portRotation.getMaxDraft());
    loadableStudyPortRotation.setAirDraftRestriction(
        isEmpty(portRotation.getMaxAirDraft()) ? null : portRotation.getMaxAirDraft());
    loadableStudyPortRotation.setEta(
        isEmpty(portRotation.getEta()) ? null : LocalDateTime.parse(portRotation.getEta()));
    loadableStudyPortRotation.setEtd(
        isEmpty(portRotation.getEtd()) ? null : LocalDateTime.parse(portRotation.getEtd()));
    loadableStudyPortRotation.setLayCanFrom(
        isEmpty(portRotation.getLayCanFrom())
            ? null
            : LocalDate.parse(portRotation.getLayCanFrom()));
    loadableStudyPortRotation.setLayCanTo(
        isEmpty(portRotation.getLayCanTo()) ? null : LocalDate.parse(portRotation.getLayCanTo()));
    loadableStudyPortRotation.setPortOrder(
        isEmpty(portRotation.getPortOrder()) ? null : portRotation.getPortOrder());

    loadableStudyPortRotation.setActive(true);
    CargoOperation operation = this.cargoOperationRepository.getOne(portRotation.getOperationId());
    loadableStudyPortRotation.setOperation(operation);
    loadableStudyPortRotation.setIsPortRotationOhqComplete(true);
    if (Objects.nonNull(synopticalTableDetails)) {
      List<SynopticalTable> synopticalTableList =
          synopticalTableDetails.stream()
              .filter(data -> data.getLoadableStudyPortRotationId().equals(portRotation.getId()))
              .map(
                  synopticalTable -> {
                    SynopticalTable entitySynoptical = null;
                    if (synopticalTable.getId() != null) {
                      Optional<SynopticalTable> synopticalEnity =
                          synopticalTableRepository.findByIdAndIsActive(
                              synopticalTable.getId(), true);
                      if (synopticalEnity.isPresent()) {
                        entitySynoptical = synopticalEnity.get();
                      } else {
                        entitySynoptical = new SynopticalTable();
                        entitySynoptical.setId(synopticalTable.getId());
                      }
                    } else {
                      entitySynoptical = new SynopticalTable();
                    }
                    entitySynoptical.setLoadableStudyPortRotation(loadableStudyPortRotation);
                    entitySynoptical.setPortXid(synopticalTable.getPortId());
                    entitySynoptical.setOperationType(synopticalTable.getOperationType());
                    entitySynoptical.setIsActive(true);
                    entitySynoptical.setLoadableStudyXId(
                        loadableStudyPortRotation.getLoadableStudy().getId());
                    return entitySynoptical;
                  })
              .collect(Collectors.toList());
      loadableStudyPortRotation.setSynopticalTable(synopticalTableList);
    }
    return loadableStudyPortRotation;
  }

  private OnBoardQuantity buildOnBoardQuantityEntity(
      OnBoardQuantity entity, com.cpdss.loadablestudy.domain.OnBoardQuantity request) {
    if (request.getCargoId() != null && 0 != request.getCargoId()) {
      entity.setCargoId(request.getCargoId());
    } else {
      entity.setCargoId(null);
    }

    entity.setTankId(request.getTankId());
    entity.setPortId(request.getPortId());

    entity.setVolumeInM3(request.getVolume());
    entity.setColorCode(isEmpty(request.getColorCode()) ? null : request.getColorCode());

    entity.setIsActive(true);
    return entity;
  }

  private OnHandQuantity buildOnHandQuantity(
      OnHandQuantity entity, com.cpdss.loadablestudy.domain.OnHandQuantity request) {
    entity.setIsActive(true);
    entity.setFuelTypeXId(null != request.getFueltypeId() ? request.getFueltypeId() : null);
    entity.setTankXId(null != request.getTankId() ? request.getTankId() : null);
    entity.setPortXId(null != request.getPortId() ? request.getPortId() : null);
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
    if (request.getPortId() != null) {
      LoadableStudyPortRotation lsPortRot =
          loadableStudyPortRotationRepository.findByLoadableStudyAndPortXIdAndIsActive(
              entity.getLoadableStudy(), request.getPortId(), true);
      entity.setPortRotation(lsPortRot);
    }
    return entity;
  }

  private void setCaseNo(LoadableStudy entity) {
    if (null != entity.getDraftRestriction()) {
      entity.setCaseNo(LoadableStudiesConstants.CASE_3);
    } else if (LoadableStudiesConstants.CASE_1_LOAD_LINES.contains(entity.getLoadLineXId())) {
      entity.setCaseNo(LoadableStudiesConstants.CASE_1);
    } else {
      entity.setCaseNo(LoadableStudiesConstants.CASE_2);
    }
  }

  private CargoNomination buildCargoNomination(
      CargoNomination cargoNomination,
      com.cpdss.loadablestudy.domain.CargoNomination request,
      List<CargoNominationOperationDetails> cargoNominationOperationDetails) {
    cargoNomination.setPriority(request.getPriority());
    cargoNomination.setCargoXId(request.getCargoId());
    cargoNomination.setAbbreviation(request.getAbbreviation());
    cargoNomination.setColor(request.getColor());
    cargoNomination.setApi(request.getApi());
    cargoNomination.setTemperature(request.getTemperature());
    cargoNomination.setQuantity(
        !StringUtils.isEmpty(request.getQuantity())
            ? new BigDecimal(String.valueOf(request.getQuantity()))
            : null);
    cargoNomination.setMaxTolerance(
        !StringUtils.isEmpty(request.getMaxTolerance())
            ? new BigDecimal(String.valueOf(request.getMaxTolerance()))
            : null);
    cargoNomination.setMinTolerance(
        !StringUtils.isEmpty(request.getMinTolerance())
            ? new BigDecimal(String.valueOf(request.getMinTolerance()))
            : null);

    cargoNomination.setSegregationXId(request.getSegregationId());
    // activate the records to be saved
    cargoNomination.setIsActive(true);

    if (!cargoNominationOperationDetails.isEmpty()) {
      Set<CargoNominationPortDetails> cargoNominationPortDetailsList =
          cargoNominationOperationDetails.stream()
              .filter(var -> var.getCargoNominationId().equals(request.getId()))
              .map(
                  cargo -> {
                    CargoNominationPortDetails cargoNominationPortDetails = null;
                    if (cargo.getId() != null) {
                      Optional<CargoNominationPortDetails> existingCargoNominationPortDetails =
                          cargoNominationOperationDetailsRepository.findById(cargo.getId());
                      if (existingCargoNominationPortDetails.isPresent()) {
                        if (cargoNomination.getCargoNominationPortDetails() != null) {
                          cargoNomination.getCargoNominationPortDetails().clear();
                        }
                        cargoNominationPortDetails = existingCargoNominationPortDetails.get();
                        cargoNominationPortDetails.setId(
                            existingCargoNominationPortDetails.get().getId());
                        log.info("Stowage Edit UPDATE 1 Cargonomination");
                      } else {
                        cargoNominationPortDetails = new CargoNominationPortDetails();
                        cargoNominationPortDetails.setId(cargo.getId());
                        log.info("Stowage Edit INSERT 1 Cargonomination");
                      }
                    } else {
                      cargoNominationPortDetails = new CargoNominationPortDetails();
                      log.info("Stowage Edit INSERT 2 Cargonomination");
                    }
                    cargoNominationPortDetails.setCargoNomination(cargoNomination);
                    cargoNominationPortDetails.setPortId(cargo.getPortId());
                    cargoNominationPortDetails.setQuantity(new BigDecimal(cargo.getQuantity()));
                    cargoNominationPortDetails.setIsActive(true);
                    return cargoNominationPortDetails;
                  })
              .collect(Collectors.toSet());
      if (cargoNominationPortDetailsList != null && cargoNominationPortDetailsList.size() > 0) {
        cargoNomination.getCargoNominationPortDetails().addAll(cargoNominationPortDetailsList);
      }
    }
    return cargoNomination;
  }

  private com.cpdss.loadablestudy.entity.CommingleCargo buildCommingleCargoShore(
      com.cpdss.loadablestudy.entity.CommingleCargo commingleCargoEntity,
      com.cpdss.loadablestudy.domain.CommingleCargo commingleCargo,
      Long id) {
    List<Long> cargoNominationIds = new ArrayList<>();
    cargoNominationIds.add(commingleCargo.getCargoNomination1Id());
    cargoNominationIds.add(commingleCargo.getCargoNomination2Id());
    // fetch the max priority for the cargoNomination ids and set as priority for
    // commingle
    Long maxPriority =
        cargoNominationRepository.getMaxPriorityCargoNominationIn(cargoNominationIds);
    commingleCargoEntity.setPriority(maxPriority != null ? maxPriority.intValue() : null);
    commingleCargoEntity.setCargoNomination1Id(commingleCargo.getCargoNomination1Id());
    commingleCargoEntity.setCargoNomination2Id(commingleCargo.getCargoNomination2Id());
    commingleCargoEntity.setLoadableStudyXId(id);
    // commingleCargoEntity.setPurposeXid(commingleCargo.getPurposeId());
    /* commingleCargoEntity.setTankIds(
              StringUtils.collectionToCommaDelimitedString(commingleCargo.getPreferredTanksList()));
    */
    commingleCargoEntity.setCargo1Xid(commingleCargo.getCargo1Id());
    /* commingleCargoEntity.setCargo1Pct(
    !StringUtils.isEmpty(commingleCargo.getCargo1Pct())
            ? new BigDecimal(commingleCargo.getCargo1Pct())
            : null);*/
    commingleCargoEntity.setCargo2Xid(commingleCargo.getCargo2Id());
    /* commingleCargoEntity.setCargo2Pct(
    !StringUtils.isEmpty(commingleCargo.getCargo2Pct())
            ? new BigDecimal(commingleCargo.getCargo2Pct())
            : null);*/
    commingleCargoEntity.setQuantity(
        !StringUtils.isEmpty(commingleCargo.getQuantity())
            ? new BigDecimal(commingleCargo.getQuantity())
            : null);
    commingleCargoEntity.setIsActive(true);
    // commingleCargoEntity.setIsSlopOnly(commingleCargo.getSlopOnly());
    return commingleCargoEntity;
  }

  private boolean checkIfLoadableStudyExist(long loadableStudyId, Voyage voyage) {
    return this.loadableStudyRepository.existsByIdAndPlanningTypeXIdAndVoyageAndIsActive(
        loadableStudyId, Common.PLANNING_TYPE.LOADABLE_STUDY_VALUE, voyage, true);
  }

  /**
   * Method to save voyage details on shore side
   *
   * @param vesselId vesselId value
   * @param voyageDto voyage object from ship
   * @return Voyage Saved voyage object
   */
  private Voyage saveVoyageShore(Long vesselId, VoyageDto voyageDto) {

    Voyage voyage = voyageRepository.findByIdAndIsActive(voyageDto.getId(), true);

    // Create a new voyage record if not existing
    if (null == voyage) {
      voyage = new Voyage();
      voyage.setId(voyageDto.getId());
      voyage.setVoyageNo(voyageDto.getVoyageNo());
      voyage.setVesselXId(vesselId);
      voyage.setIsActive(true);
      voyage.setCompanyXId(1L);
      voyage.setCaptainXId(voyageDto.getCaptainXId());
      voyage.setChiefOfficerXId(voyageDto.getChiefOfficerXId());
      voyage.setStartTimezoneId(voyageDto.getStartTimezoneId());
      voyage.setEndTimezoneId(voyageDto.getEndTimezoneId());
      voyage.setVoyageStatus(this.voyageStatusRepository.getOne(OPEN_VOYAGE_STATUS));
      voyage.setVoyageStartDate(
          !StringUtils.isEmpty(voyageDto.getVoyageStartDate())
              ? LocalDateTime.from(
                  DateTimeFormatter.ofPattern(VOYAGE_DATE_FORMAT)
                      .parse(voyageDto.getVoyageStartDate()))
              : null);
      voyage.setVoyageEndDate(
          !StringUtils.isEmpty(voyageDto.getVoyageEndDate())
              ? LocalDateTime.from(
                  DateTimeFormatter.ofPattern(VOYAGE_DATE_FORMAT)
                      .parse(voyageDto.getVoyageEndDate()))
              : null);
      voyage = voyageRepository.save(voyage);
    }
    return voyage;
  }

  private LoadableStudy saveLoadableStudyShore(
      com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy, Voyage voyage)
      throws IOException {
    LoadableStudy entity = new LoadableStudy();
    entity.setVesselXId(loadableStudy.getVesselId());
    entity.setVoyage(voyage);
    entity.setName(loadableStudy.getName());
    entity.setDetails(loadableStudy.getDetails());
    entity.setCharterer(loadableStudy.getCharterer());
    entity.setSubCharterer(loadableStudy.getSubCharterer());
    entity.setDraftMark(new BigDecimal(loadableStudy.getDraftMark()));
    entity.setLoadLineXId(loadableStudy.getLoadlineId());
    entity.setDraftRestriction(
        loadableStudy.getDraftRestriction() != null
            ? new BigDecimal(loadableStudy.getDraftRestriction())
            : null);
    entity.setEstimatedMaxSag(
        loadableStudy.getEstimatedMaxSG() != null
            ? new BigDecimal(loadableStudy.getEstimatedMaxSG())
            : null);
    entity.setMaxAirTemperature(
        loadableStudy.getMaxAirTemp() != null
            ? new BigDecimal(loadableStudy.getMaxAirTemp())
            : null);
    entity.setMaxWaterTemperature(
        loadableStudy.getMaxWaterTemp() != null
            ? new BigDecimal(loadableStudy.getMaxWaterTemp())
            : null);
    entity.setActive(true);
    this.setCaseNo(entity);
    /*entity.setDischargeCargoId(loadableStudy.getD);*/
    entity.setLoadOnTop(loadableStudy.getLoadOnTop() != null ? loadableStudy.getLoadOnTop() : null);
    entity.setIsCargoNominationComplete(true);
    entity.setIsDischargePortsComplete(true);
    entity.setIsObqComplete(true);
    entity.setIsPortsComplete(true);
    Set<LoadableStudyAttachments> attachmentCollection = new HashSet<>();
    if (null != loadableStudy.getLoadableStudyAttachment()) {
      String folderLocation = constructFolderPath(entity);
      Files.createDirectories(Paths.get(this.rootFolder + folderLocation));
      for (com.cpdss.loadablestudy.domain.LoadableStudyAttachment attachment :
          loadableStudy.getLoadableStudyAttachment()) {
        String fileName =
            attachment.getFileName().substring(0, attachment.getFileName().lastIndexOf("."));
        String extension =
            attachment
                .getFileName()
                .substring(attachment.getFileName().lastIndexOf("."))
                .toLowerCase();
        String filePath = folderLocation + fileName + "_" + System.currentTimeMillis() + extension;
        Path path = Paths.get(this.rootFolder + filePath);
        Files.createFile(path);
        Files.write(path, attachment.getContent());
        LoadableStudyAttachments attachmentEntity = new LoadableStudyAttachments();
        attachmentEntity.setUploadedFileName(attachment.getFileName());
        attachmentEntity.setFilePath(filePath);
        attachmentEntity.setLoadableStudy(entity);
        attachmentEntity.setIsActive(true);
        attachmentCollection.add(attachmentEntity);
      }
      entity.setAttachments(attachmentCollection);
    }
    entity.setId(loadableStudy.getId());
    entity = this.loadableStudyRepository.save(entity);
    return entity;
  }

  public String constructFolderPath(LoadableStudy loadableStudy) {
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

  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = GenericServiceException.class)
  public LoadableStudy persistShipPayloadInShoreSide(
      String messageId, LoadabalePatternValidateRequest loadabalePatternValidateRequest)
      throws GenericServiceException, IOException {
    LoadableStudy loadableStudyEntity = null;

    com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy =
        loadabalePatternValidateRequest.getLoadableStudy();
    Voyage voyage =
        saveOrUpdateVoyageInShoreSide(loadableStudy.getVesselId(), loadableStudy.getVoyage());
    Optional<LoadableStudy> loadableStudyEntityOpt =
        loadableStudyRepository.findByIdAndIsActive(loadableStudy.getId(), true);
    ModelMapper modelMapper = new ModelMapper();
    if (loadableStudyEntityOpt.isPresent()) {
      log.info("Stowage Edit update LS --- id : " + loadableStudy.getId());
      loadableStudyEntity =
          saveOrUpdateLoadableStudyInShore(loadableStudy, voyage, loadableStudyEntityOpt.get());
      updateCommunicationStatus(messageId, loadableStudyEntity);
      saveOrUpdateLoadableStudyDataInShore(loadableStudyEntity, loadableStudy, modelMapper);
      // saveLoadablePlanStowageTempDetailsInShore(loadabalePatternValidateRequest);
      savePatternDetailsInShoreSide(loadabalePatternValidateRequest, loadableStudyEntity);
    } else {
      log.info("Stowage Edit insert LS --- id : " + loadableStudy.getId());
      LoadableStudy entity = new LoadableStudy();
      entity.setId(loadableStudy.getId());
      loadableStudyEntity = saveOrUpdateLoadableStudyInShore(loadableStudy, voyage, entity);
      updateCommunicationStatus(messageId, loadableStudyEntity);
      saveOrUpdateLoadableStudyDataInShore(loadableStudyEntity, loadableStudy, modelMapper);
      // saveLoadablePlanStowageTempDetailsInShore(loadabalePatternValidateRequest);
      savePatternDetailsInShoreSide(loadabalePatternValidateRequest, loadableStudyEntity);
    }
    log.info("Stowage EDIT LS insert or update : " + loadableStudyEntity.getId());
    return loadableStudyEntity;
  }

  private void savePatternDetailsInShoreSide(
      LoadabalePatternValidateRequest loadabalePatternValidateRequest,
      LoadableStudy loadableStudyEntity) {
    if (loadabalePatternValidateRequest.getLoadablePlanPortWiseDetails() != null) {}
  }

  private Voyage saveOrUpdateVoyageInShoreSide(Long vesselId, VoyageDto voyageDto) {
    Voyage voyageEntity = voyageRepository.findByIdAndIsActive(voyageDto.getId(), true);
    if (Objects.isNull(voyageEntity)) {
      voyageEntity = new Voyage();
      voyageEntity.setId(voyageDto.getId());
      log.info(
          "Stowage Edit insert new voyage --- id : "
              + voyageDto.getId()
              + " vesselId : "
              + vesselId);
    } else {
      log.info(
          "Stowage Edit update existing voyage-- id : "
              + voyageDto.getId()
              + "vesselId : "
              + vesselId);
    }
    voyageEntity.setVoyageNo(voyageDto.getVoyageNo());
    voyageEntity.setVesselXId(vesselId);
    voyageEntity.setIsActive(true);
    voyageEntity.setCompanyXId(1L);
    voyageEntity.setCaptainXId(voyageDto.getCaptainXId());
    voyageEntity.setChiefOfficerXId(voyageDto.getChiefOfficerXId());
    voyageEntity.setStartTimezoneId(voyageDto.getStartTimezoneId());
    voyageEntity.setEndTimezoneId(voyageDto.getEndTimezoneId());
    voyageEntity.setVoyageStatus(this.voyageStatusRepository.getOne(OPEN_VOYAGE_STATUS));
    voyageEntity.setVoyageStartDate(
        !StringUtils.isEmpty(voyageDto.getVoyageStartDate())
            ? LocalDateTime.from(
                DateTimeFormatter.ofPattern(VOYAGE_DATE_FORMAT)
                    .parse(voyageDto.getVoyageStartDate()))
            : null);
    voyageEntity.setVoyageEndDate(
        !StringUtils.isEmpty(voyageDto.getVoyageEndDate())
            ? LocalDateTime.from(
                DateTimeFormatter.ofPattern(VOYAGE_DATE_FORMAT).parse(voyageDto.getVoyageEndDate()))
            : null);
    voyageEntity = voyageRepository.save(voyageEntity);
    log.info("Stowage EDIT voyage insert or update : " + voyageEntity.getId());
    return voyageEntity;
  }

  private LoadableStudy saveOrUpdateLoadableStudyInShore(
      com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy,
      Voyage voyage,
      LoadableStudy entity)
      throws IOException {
    entity.setVesselXId(loadableStudy.getVesselId());
    entity.setVoyage(voyage);
    entity.setName(loadableStudy.getName());
    entity.setDetails(loadableStudy.getDetails());
    entity.setCharterer(loadableStudy.getCharterer());
    entity.setSubCharterer(loadableStudy.getSubCharterer());
    entity.setDraftMark(new BigDecimal(loadableStudy.getDraftMark()));
    entity.setLoadLineXId(loadableStudy.getLoadlineId());
    entity.setDraftRestriction(
        loadableStudy.getDraftRestriction() != null
            ? new BigDecimal(loadableStudy.getDraftRestriction())
            : null);
    entity.setEstimatedMaxSag(
        loadableStudy.getEstimatedMaxSG() != null
            ? new BigDecimal(loadableStudy.getEstimatedMaxSG())
            : null);
    entity.setMaxAirTemperature(
        loadableStudy.getMaxAirTemp() != null
            ? new BigDecimal(loadableStudy.getMaxAirTemp())
            : null);
    entity.setMaxWaterTemperature(
        loadableStudy.getMaxWaterTemp() != null
            ? new BigDecimal(loadableStudy.getMaxWaterTemp())
            : null);
    entity.setActive(true);
    this.setCaseNo(entity);
    /*entity.setDischargeCargoId(loadableStudy.getD);*/
    entity.setLoadOnTop(loadableStudy.getLoadOnTop() != null ? loadableStudy.getLoadOnTop() : null);
    entity.setIsCargoNominationComplete(true);
    entity.setIsDischargePortsComplete(true);
    entity.setIsObqComplete(true);
    entity.setIsPortsComplete(true);
    Set<LoadableStudyAttachments> attachmentCollection = new HashSet<>();
    if (null != loadableStudy.getLoadableStudyAttachment()) {
      String folderLocation = constructFolderPath(entity);
      Files.createDirectories(Paths.get(this.rootFolder + folderLocation));
      for (com.cpdss.loadablestudy.domain.LoadableStudyAttachment attachment :
          loadableStudy.getLoadableStudyAttachment()) {
        //        String fileName =
        //                attachment.getFileName().substring(0,
        // attachment.getFileName().lastIndexOf("."));
        //        String extension =
        //                attachment
        //                        .getFileName()
        //                        .substring(attachment.getFileName().lastIndexOf("."))
        //                        .toLowerCase();
        //        String filePath = folderLocation + fileName + "_" + System.currentTimeMillis() +
        // extension;
        //        Path path = Paths.get(this.rootFolder + filePath);
        //        Files.createFile(path);
        //        Files.write(path, attachment.getContent());
        Optional<LoadableStudyAttachments> loadableStudyAttachment =
            loadableStudyAttachmentsRepository.findByIdAndIsActive(attachment.getId(), true);
        LoadableStudyAttachments attachmentEntity;
        if (loadableStudyAttachment.isPresent()) {
          log.info("Stowage Edit Update Attachment File --- id : " + attachment.getId());
          attachmentEntity = loadableStudyAttachment.get();
        } else {
          log.info("Stowage Edit INSERT Attachment File --- id : " + attachment.getId());
          attachmentEntity = new LoadableStudyAttachments();
          attachmentEntity.setId(attachment.getId());
        }
        attachmentEntity.setUploadedFileName(attachment.getFileName());
        attachmentEntity.setFilePath(attachment.getFilePath());
        attachmentEntity.setLoadableStudy(entity);
        attachmentEntity.setIsActive(true);
        attachmentCollection.add(attachmentEntity);
      }
      entity.setAttachments(attachmentCollection);
    }
    entity = this.loadableStudyRepository.save(entity);
    return entity;
  }

  private void updateCommunicationStatus(String messageId, LoadableStudy loadableStudyEntity) {
    LoadableStudyCommunicationStatus lsCommunicationStatus = new LoadableStudyCommunicationStatus();
    lsCommunicationStatus.setMessageUUID(messageId);
    lsCommunicationStatus.setCommunicationStatus(
        CommunicationStatus.RECEIVED_WITH_HASH_VERIFIED.getId());
    lsCommunicationStatus.setReferenceId(loadableStudyEntity.getId());
    lsCommunicationStatus.setMessageType(MessageTypes.VALIDATEPLAN.getMessageType());
    lsCommunicationStatus.setCommunicationDateTime(LocalDateTime.now());
    this.loadableStudyCommunicationStatusRepository.save(lsCommunicationStatus);
  }

  private void saveOrUpdateLoadableStudyDataInShore(
      LoadableStudy loadableStudyEntity,
      com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy,
      ModelMapper modelMapper)
      throws GenericServiceException {

    List<CommingleCargo> commingleEntities = new ArrayList<>();
    loadableStudy
        .getCommingleCargos()
        .forEach(
            commingleCargo -> {
              try {
                CommingleCargo commingleCargoEntity = null;
                if (commingleCargo.getId() != null) {
                  Optional<CommingleCargo> existingCommingleCargo =
                      this.commingleCargoRepository.findByIdAndIsActive(
                          commingleCargo.getId(), true);
                  if (existingCommingleCargo.isPresent()) {
                    log.info(
                        "Stowage Edit Update CommingleCargo --- id : " + commingleCargo.getId());
                    commingleCargoEntity = existingCommingleCargo.get();
                  } else {
                    log.info(
                        "Stowage Edit INSERT CommingleCargo --- id : " + commingleCargo.getId());
                    commingleCargoEntity = new CommingleCargo();
                    commingleCargoEntity.setId(commingleCargo.getId());
                  }
                  buildCommingleCargoShore(
                      commingleCargoEntity, commingleCargo, loadableStudyEntity.getId());
                  commingleEntities.add(commingleCargoEntity);
                }
                //                else {
                //                  log.info("Stowage Edit INSERT CommingleCargo --- id : ");
                //                  commingleCargoEntity = new CommingleCargo();
                //                  commingleCargoEntity.setId(commingleCargo.getId());
                //                }

              } catch (Exception e) {
                log.error("Exception in creating entities for save commingle cargo", e);
                throw new RuntimeException(e);
              }
            });
    if (commingleEntities.size() > 0) {
      List<CommingleCargo> updatedCommingleEntities =
          this.commingleCargoRepository.saveAll(commingleEntities);
      log.info(
          "Stowage Edit no of rows updated for commingle : " + updatedCommingleEntities.size());
    }

    List<CargoNomination> cargoNominationEntities = new ArrayList<>();
    loadableStudy.getCargoNomination().stream()
        .forEach(
            cargoNom -> {
              Optional<CargoNomination> existingCargoNomination = null;
              CargoNomination cargoNomination = null;
              if (cargoNom.getId() != null) {
                existingCargoNomination =
                    cargoNominationRepository.findByIdAndIsActive(cargoNom.getId(), true);
                if (existingCargoNomination.isPresent()) {
                  cargoNomination = existingCargoNomination.get();
                  log.info("Stowage Edit Update CargoNomination --- id : " + cargoNom.getId());
                } else {
                  cargoNomination = new CargoNomination();
                  cargoNomination.setId(cargoNom.getId());
                  log.info("Stowage Edit INSERT CargoNomination --- id : " + cargoNom.getId());
                }
                buildCargoNomination(
                    cargoNomination, cargoNom, loadableStudy.getCargoNominationOperationDetails());
                cargoNomination.setLoadableStudyXId(loadableStudyEntity.getId());
                cargoNominationEntities.add(cargoNomination);
              }
              //              else {
              //                cargoNomination = new CargoNomination();
              //                log.info("Stowage Edit INSERT CargoNomination --- id : ");
              //              }

            });
    if (cargoNominationEntities.size() > 0) {
      List<CargoNomination> updatedCargoNominationEntities =
          this.cargoNominationRepository.saveAll(cargoNominationEntities);
      log.info(
          "Stowage Edit no of rows updated for CargoNominationEntities : "
              + updatedCargoNominationEntities.size());
    }
    List<LoadableStudyPortRotation> loadableStudyPortRotations = new ArrayList<>();
    loadableStudy.getLoadableStudyPortRotation().stream()
        .forEach(
            portRotation -> {
              LoadableStudyPortRotation loadableStudyPortRotation;
              if (portRotation.getId() != null) {

                LoadableStudyPortRotation existingLoadableStudyPortRotation =
                    loadableStudyPortRotationRepository.findByIdAndIsActive(
                        portRotation.getId(), true);
                if (existingLoadableStudyPortRotation != null) {
                  loadableStudyPortRotation = existingLoadableStudyPortRotation;
                  log.info(
                      "Stowage Edit UPDATE LoadableStudyPortRotation --- id : "
                          + portRotation.getId());
                } else {
                  loadableStudyPortRotation = new LoadableStudyPortRotation();
                  loadableStudyPortRotation.setId(portRotation.getId());
                  log.info(
                      "Stowage Edit INSERT LoadableStudyPortRotation --- id : "
                          + portRotation.getId());
                }
                loadableStudyPortRotation.setLoadableStudy(loadableStudyEntity);
                loadableStudyPortRotation =
                    buildLoadableStudyPortRotation(
                        loadableStudyPortRotation,
                        portRotation,
                        loadableStudy.getSynopticalTableDetails());
                loadableStudyPortRotations.add(loadableStudyPortRotation);
              }
              //              else {
              //                loadableStudyPortRotation = new LoadableStudyPortRotation();
              //                log.info("Stowage Edit INSERT LoadableStudyPortRotation --- id : ");
              //              }

            });
    if (loadableStudyPortRotations.size() > 0) {
      List<LoadableStudyPortRotation> updatedLoadableStudyPortRotations =
          this.loadableStudyPortRotationRepository.saveAll(loadableStudyPortRotations);
      log.info(
          "Stowage Edit no of rows updated for LoadableStudyPortRotation : "
              + updatedLoadableStudyPortRotations.size());
    }
    List<OnHandQuantity> onHandQuantityEntities = new ArrayList<>();
    loadableStudy.getOnHandQuantity().stream()
        .forEach(
            onHandQuantity -> {
              OnHandQuantity onHandQuantityEntity;
              if (onHandQuantity.getId() != null) {
                OnHandQuantity existingOnHandQuantity =
                    onHandQuantityRepository.findByIdAndIsActive(onHandQuantity.getId(), true);
                if (existingOnHandQuantity != null) {
                  log.info("Stowage Edit UPDATE OnHandQuantity --- id : " + onHandQuantity.getId());
                  onHandQuantityEntity = existingOnHandQuantity;
                } else {
                  onHandQuantityEntity = new OnHandQuantity();
                  onHandQuantityEntity.setId(onHandQuantity.getId());
                  log.info("Stowage Edit INSERT OnHandQuantity --- id : " + onHandQuantity.getId());
                }
                onHandQuantityEntity.setLoadableStudy(loadableStudyEntity);
                buildOnHandQuantity(onHandQuantityEntity, onHandQuantity);
                onHandQuantityEntities.add(onHandQuantityEntity);
              }
              //              else {
              //                onHandQuantityEntity = new OnHandQuantity();
              //                log.info("Stowage Edit INSERT OnHandQuantity --- id : ");
              //              }

            });
    if (onHandQuantityEntities.size() > 0) {
      List<OnHandQuantity> updatedOnHandQuantityEntities =
          this.onHandQuantityRepository.saveAll(onHandQuantityEntities);
      log.info(
          "Stowage Edit no of rows updated for OnHandQuantity : "
              + updatedOnHandQuantityEntities.size());
    }
    List<OnBoardQuantity> onBoardQuantityEntities = new ArrayList<>();
    loadableStudy.getOnBoardQuantity().stream()
        .forEach(
            onBoardQuantity -> {
              OnBoardQuantity onBoardQuantityEntity;
              if (onBoardQuantity.getId() != null) {
                OnBoardQuantity existingOnBoardQuantityEntity =
                    onBoardQuantityRepository.findByIdAndIsActive(onBoardQuantity.getId(), true);
                if (existingOnBoardQuantityEntity != null) {
                  onBoardQuantityEntity = existingOnBoardQuantityEntity;
                  log.info(
                      "Stowage Edit UPDATE OnBoardQuantity --- id : " + onBoardQuantity.getId());
                } else {
                  onBoardQuantityEntity = new OnBoardQuantity();
                  onBoardQuantityEntity.setId(onBoardQuantity.getId());
                  log.info(
                      "Stowage Edit INSERT OnBoardQuantity --- id : " + onBoardQuantity.getId());
                }
                onBoardQuantityEntity.setLoadableStudy(loadableStudyEntity);
                buildOnBoardQuantityEntity(onBoardQuantityEntity, onBoardQuantity);
                onBoardQuantityEntities.add(onBoardQuantityEntity);
              }
              //              else {
              //                onBoardQuantityEntity = new OnBoardQuantity();
              //                log.info("Stowage Edit INSERT OnBoardQuantity --- id : ");
              //              }

            });
    if (onBoardQuantityEntities.size() > 0) {
      List<OnBoardQuantity> updatedOnBoardQuantityEntities =
          this.onBoardQuantityRepository.saveAll(onBoardQuantityEntities);
      log.info(
          "Stowage Edit no of rows updated for OnBoardQuantity : "
              + updatedOnBoardQuantityEntities.size());
    }
    com.cpdss.loadablestudy.domain.LoadableQuantity loadableQuantityDomain =
        loadableStudy.getLoadableQuantity();
    if (null != loadableQuantityDomain) {
      LoadableQuantity loadableQuantity = null;
      if (loadableQuantityDomain.getId() != null) {
        LoadableQuantity existingLoadableQuantity =
            loadableQuantityRepository.findByIdAndIsActive(loadableQuantityDomain.getId(), true);
        if (existingLoadableQuantity != null) {
          loadableQuantity = existingLoadableQuantity;
          log.info(
              "Stowage Edit UPDATE LoadableQuantity --- id : " + loadableQuantityDomain.getId());
        } else {
          loadableQuantity = new LoadableQuantity();
          loadableQuantity.setId(loadableQuantityDomain.getId());
          log.info(
              "Stowage Edit INSERT LoadableQuantity --- id : " + loadableQuantityDomain.getId());
        }
        loadableQuantity.setLoadableStudyXId(loadableStudyEntity);
        loadableQuantity.setConstant(
            StringUtils.isEmpty(loadableQuantityDomain.getConstant())
                ? null
                : new BigDecimal(loadableQuantityDomain.getConstant()));
        loadableQuantity.setDeadWeight(
            StringUtils.isEmpty(loadableQuantityDomain.getDeadWeight())
                ? null
                : new BigDecimal(loadableQuantityDomain.getDeadWeight()));

        loadableQuantity.setDistanceFromLastPort(
            StringUtils.isEmpty(loadableQuantityDomain.getDistanceFromLastPort())
                ? null
                : new BigDecimal(loadableQuantityDomain.getDistanceFromLastPort()));

        loadableQuantity.setEstimatedDOOnBoard(
            StringUtils.isEmpty(loadableQuantityDomain.getEstDOOnBoard())
                ? null
                : new BigDecimal(loadableQuantityDomain.getEstDOOnBoard()));

        loadableQuantity.setEstimatedFOOnBoard(
            StringUtils.isEmpty(loadableQuantityDomain.getEstFOOnBoard())
                ? null
                : new BigDecimal(loadableQuantityDomain.getEstFOOnBoard()));
        loadableQuantity.setEstimatedFWOnBoard(
            StringUtils.isEmpty(loadableQuantityDomain.getEstFreshWaterOnBoard())
                ? null
                : new BigDecimal(loadableQuantityDomain.getEstFreshWaterOnBoard()));
        loadableQuantity.setEstimatedSagging(
            StringUtils.isEmpty(loadableQuantityDomain.getEstSagging())
                ? null
                : new BigDecimal(loadableQuantityDomain.getEstSagging()));

        loadableQuantity.setOtherIfAny(
            StringUtils.isEmpty(loadableQuantityDomain.getOtherIfAny())
                ? null
                : new BigDecimal(loadableQuantityDomain.getOtherIfAny()));
        loadableQuantity.setSaggingDeduction(
            StringUtils.isEmpty(loadableQuantityDomain.getSaggingDeduction())
                ? null
                : new BigDecimal(loadableQuantityDomain.getSaggingDeduction()));

        loadableQuantity.setSgCorrection(
            StringUtils.isEmpty(loadableQuantityDomain.getSgCorrection())
                ? new BigDecimal("0.0000")
                : new BigDecimal(loadableQuantityDomain.getSgCorrection()));

        loadableQuantity.setTotalQuantity(
            StringUtils.isEmpty(loadableQuantityDomain.getTotalQuantity())
                ? null
                : new BigDecimal(loadableQuantityDomain.getTotalQuantity()));
        loadableQuantity.setTpcatDraft(
            StringUtils.isEmpty(loadableQuantityDomain.getTpc())
                ? null
                : new BigDecimal(loadableQuantityDomain.getTpc()));

        loadableQuantity.setVesselAverageSpeed(
            StringUtils.isEmpty(loadableQuantityDomain.getVesselAverageSpeed())
                ? null
                : new BigDecimal(loadableQuantityDomain.getVesselAverageSpeed()));

        loadableQuantity.setPortId(
            StringUtils.isEmpty(loadableQuantityDomain.getPortId())
                ? null
                : new BigDecimal(loadableQuantityDomain.getPortId()));
        loadableQuantity.setBoilerWaterOnBoard(
            StringUtils.isEmpty(loadableQuantityDomain.getBoilerWaterOnBoard())
                ? null
                : new BigDecimal(loadableQuantityDomain.getBoilerWaterOnBoard()));
        loadableQuantity.setBallast(
            StringUtils.isEmpty(loadableQuantityDomain.getBallast())
                ? null
                : new BigDecimal(loadableQuantityDomain.getBallast()));
        loadableQuantity.setRunningHours(
            StringUtils.isEmpty(loadableQuantityDomain.getRunningHours())
                ? null
                : new BigDecimal(loadableQuantityDomain.getRunningHours()));
        loadableQuantity.setRunningDays(
            StringUtils.isEmpty(loadableQuantityDomain.getRunningDays())
                ? null
                : new BigDecimal(loadableQuantityDomain.getRunningDays()));
        loadableQuantity.setFoConsumptionInSZ(
            StringUtils.isEmpty(loadableQuantityDomain.getFoConInSZ())
                ? null
                : new BigDecimal(loadableQuantityDomain.getFoConInSZ()));
        loadableQuantity.setDraftRestriction(
            StringUtils.isEmpty(loadableQuantityDomain.getDraftRestriction())
                ? null
                : new BigDecimal(loadableQuantityDomain.getDraftRestriction()));

        loadableQuantity.setFoConsumptionPerDay(
            StringUtils.isEmpty(loadableQuantityDomain.getFoConsumptionPerDay())
                ? null
                : new BigDecimal(loadableQuantityDomain.getFoConsumptionPerDay()));
        loadableQuantity.setIsActive(true);
        if (loadableQuantityDomain.getPortId() != null) {
          LoadableStudyPortRotation lsPortRot =
              loadableStudyPortRotationRepository.findByLoadableStudyAndPortXIdAndIsActive(
                  loadableStudyEntity, loadableQuantityDomain.getPortId(), true);
          loadableQuantity.setLoadableStudyPortRotation(lsPortRot);
        }
      }
      //      else {
      //        loadableQuantity = new LoadableQuantity();
      //        log.info("Stowage Edit INSERT LoadableQuantity --- id : ");
      //      }

      if (loadableQuantity != null) {
        LoadableQuantity loadableQuantityEntity =
            this.loadableQuantityRepository.save(loadableQuantity);
        log.info("Stowage Edit Loadable Quantity has updated - " + loadableQuantityEntity.getId());
      }
    }
    /* if (!loadableStudy.getLoadableStudyRuleList().isEmpty()) {
      VesselInfo.VesselRuleRequest.Builder vesselRuleBuilder =
          VesselInfo.VesselRuleRequest.newBuilder();
      vesselRuleBuilder.setSectionId(RuleMasterSection.Plan.getId());
      vesselRuleBuilder.setVesselId(loadableStudyEntity.getVesselXId());
      vesselRuleBuilder.setIsNoDefaultRule(true);
      VesselInfo.VesselRuleReply vesselRuleReply =
          this.vesselInfoGrpcService.getRulesByVesselIdAndSectionId(vesselRuleBuilder.build());
      if (!SUCCESS.equals(vesselRuleReply.getResponseStatus().getStatus())) {
        throw new GenericServiceException(
            "failed to get loadable study rule Details When stowage edit update or insert ",
            vesselRuleReply.getResponseStatus().getCode(),
            HttpStatusCode.valueOf(Integer.valueOf(vesselRuleReply.getResponseStatus().getCode())));
      }
      List<LoadableStudyRules> loadableStudyRulesList = new ArrayList<>();
      loadableStudy
          .getLoadableStudyRuleList()
          .forEach(
              rulePlans -> {
                rulePlans
                    .getRules()
                    .forEach(
                        rule -> {
                          LoadableStudyRules loadableStudyRules = null;
                          if (rule.getId() != null) {
                            Optional<LoadableStudyRules> existingLoadableStudyRules =
                                loadableStudyRuleRepository.findById(Long.parseLong(rule.getId()));
                            if (existingLoadableStudyRules.isPresent()) {
                              loadableStudyRules = existingLoadableStudyRules.get();
                            } else {
                              loadableStudyRules = new LoadableStudyRules();
                              loadableStudyRules.setId(Long.parseLong(rule.getId()));
                            }
                            loadableStudyRules.setLoadableStudy(loadableStudyEntity);
                            loadableStudyRules.setIsActive(true);
                            Optional.ofNullable(rule.getDisplayInSettings())
                                .ifPresent(loadableStudyRules::setDisplayInSettings);
                            Optional.ofNullable(rule.getEnable())
                                .ifPresent(loadableStudyRules::setIsEnable);
                            Optional.ofNullable(rule.getIsHardRule())
                                .ifPresent(loadableStudyRules::setIsHardRule);
                            Optional.ofNullable(rule.getNumericPrecision())
                                .ifPresent(loadableStudyRules::setNumericPrecision);
                            Optional.ofNullable(rule.getNumericScale())
                                .ifPresent(loadableStudyRules::setNumericScale);
                            LoadableStudyRules finalLoadableStudyRules = loadableStudyRules;
                            Optional.ofNullable(rule.getRuleTemplateId())
                                .ifPresent(
                                    item ->
                                        finalLoadableStudyRules.setParentRuleXId(
                                            Long.parseLong(item)));
                            loadableStudyRules.setVesselXId(loadableStudyEntity.getVesselXId());

                            if (rule.getRuleType() != null
                                && rule.getRuleType()
                                    .equalsIgnoreCase(RuleType.ABSOLUTE.getRuleType())) {
                              loadableStudyRules.setRuleTypeXId(RuleType.ABSOLUTE.getId());
                            }
                            if (rule.getRuleType() != null
                                && rule.getRuleType()
                                    .equalsIgnoreCase(RuleType.PREFERABLE.getRuleType())) {
                              loadableStudyRules.setRuleTypeXId(RuleType.PREFERABLE.getId());
                            }
                            List<Common.Rules> rulesList =
                                vesselRuleReply.getRulePlanList().stream()
                                    .filter(
                                        vesselRulePlan ->
                                            rulePlans
                                                .getHeader()
                                                .equals(vesselRulePlan.getHeader()))
                                    .flatMap(rules -> rules.getRulesList().stream())
                                    .collect(Collectors.toList());
                            LoadableStudyRules finalLoadableStudyRules1 = loadableStudyRules;
                            Optional<Common.Rules> vesselRule1 =
                                rulesList.stream()
                                    .filter(
                                        vesselRule ->
                                            vesselRule
                                                .getRuleTemplateId()
                                                .equals(
                                                    finalLoadableStudyRules1.getParentRuleXId()))
                                    .findFirst();
                            loadableStudyRules.setVesselRuleXId(
                                Long.valueOf(vesselRule1.get().getVesselRuleXId()));
                            Optional.ofNullable(rule.getVesselRuleXId())
                                .ifPresent(
                                    vesselRuleXId ->
                                        finalLoadableStudyRules.setVesselRuleXId(
                                            Long.parseLong(vesselRuleXId)));
                            List<LoadableStudyRuleInput> ruleVesselMappingInputList =
                                new ArrayList<>();
                            for (RulesInputs input : rule.getInputs()) {
                              LoadableStudyRuleInput ruleTemplateInput;
                              if (input.getId() != null) {
                                Optional<LoadableStudyRuleInput> loadableStudyRuleInput =
                                    loadableStudyRuleInputRepository.findById(
                                        Long.valueOf(input.getId()));
                                if (loadableStudyRuleInput.isPresent()) {
                                  ruleTemplateInput = loadableStudyRuleInput.get();
                                } else {
                                  ruleTemplateInput = new LoadableStudyRuleInput();
                                  ruleTemplateInput.setId(Long.valueOf(input.getId()));
                                }
                                Optional.ofNullable(input.getDefaultValue())
                                    .ifPresent(ruleTemplateInput::setDefaultValue);
                                Optional.ofNullable(input.getMax())
                                    .ifPresent(ruleTemplateInput::setMaxValue);
                                Optional.ofNullable(input.getMin())
                                    .ifPresent(ruleTemplateInput::setMinValue);
                                Optional.ofNullable(input.getSuffix())
                                    .ifPresent(ruleTemplateInput::setSuffix);
                                Optional.ofNullable(input.getPrefix())
                                    .ifPresent(ruleTemplateInput::setPrefix);
                                Optional.ofNullable(input.getType())
                                    .ifPresent(ruleTemplateInput::setTypeValue);
                                Optional.ofNullable(input.getIsMandatory())
                                    .ifPresent(ruleTemplateInput::setIsMandatory);
                                ruleTemplateInput.setIsActive(true);
                                ruleTemplateInput.setLoadableStudyRuleXId(loadableStudyRules);
                                Optional.ofNullable(input.getType())
                                    .ifPresent(ruleTemplateInput::setTypeValue);
                                ruleVesselMappingInputList.add(ruleTemplateInput);
                              }
                              //                              else {
                              //                                ruleTemplateInput = new
                              // LoadableStudyRuleInput();
                              //                              }
                            }
                            loadableStudyRules.setLoadableStudyRuleInputs(
                                ruleVesselMappingInputList);
                            loadableStudyRulesList.add(loadableStudyRules);
                          }
                          //                          else {
                          //                            loadableStudyRules = new
                          // LoadableStudyRules();
                          //                          }

                        });
              });
      if (loadableStudyRulesList.size() > 0) {
        List<LoadableStudyRules> updatedLoadableStudyRulesList =
            this.loadableStudyRuleRepository.saveAll(loadableStudyRulesList);
        log.info(
            "Stowage Edit no of rows updated for LoadableStudyRules  : "
                + updatedLoadableStudyRulesList.size());
      }
    }*/
  }

  //  private void saveLoadablePlanStowageTempDetailsInShore(
  //      LoadabalePatternValidateRequest loadabalePatternValidateRequest) {
  //    if (loadabalePatternValidateRequest.getLoadablePlanStowageTempDetails() != null ) {
  //      log.info("********* getLoadablePlanStowageTempDetails");
  //      loadabalePatternValidateRequest
  //          .getLoadablePlanStowageTempDetails()
  //          .forEach(
  //              stowageTemp -> {
  //                LoadablePlanStowageDetailsTemp loadablePlanStowageDetailsTemp =
  //                    new LoadablePlanStowageDetailsTemp();
  //                Optional<Long> isBallastExist =
  //                    Optional.ofNullable(stowageTemp.getBallastDetailsId());
  //                if (isBallastExist.isPresent()) {
  //                  LoadablePlanBallastDetails ballastDetails =
  //                      this.loadablePlanBallastDetailsRepository.getOne(
  //                          stowageTemp.getBallastDetailsId());
  //                  loadablePlanStowageDetailsTemp.setLoadablePlanBallastDetails(ballastDetails);
  //                }
  //                Optional<Boolean> isCommingleExist =
  //                    Optional.ofNullable(stowageTemp.getIsCommingle());
  //                if (isCommingleExist.isPresent()) {
  //                  LoadablePlanCommingleDetails commingleDetails =
  //                      this.loadablePlanCommingleDetailsRepository.getOne(
  //                          stowageTemp.getCommingleDetailId());
  //
  // loadablePlanStowageDetailsTemp.setLoadablePlanCommingleDetails(commingleDetails);
  //                }
  //                Optional<Long> isStowageExist =
  //                    Optional.ofNullable(stowageTemp.getStowageDetailsId());
  //                if (isStowageExist.isPresent()) {
  //                  LoadablePlanStowageDetails stowageDetails =
  //                      this.loadablePlanStowageDetailsRespository.getOne(
  //                          stowageTemp.getStowageDetailsId());
  //                  loadablePlanStowageDetailsTemp.setLoadablePlanStowageDetails(stowageDetails);
  //                }
  //                Optional<LoadablePattern> loadablePatternOpt =
  //                    this.loadablePatternRepository.findByIdAndIsActive(
  //                        stowageTemp.getLoadablePatternId(), true);
  //                if (loadablePatternOpt.isPresent()) {
  //                  loadablePlanStowageDetailsTemp.setLoadablePattern(loadablePatternOpt.get());
  //                }
  //                loadablePlanStowageDetailsTemp.setIsActive(true);
  //                loadablePlanStowageDetailsTemp.setCorrectedUllage(
  //                    isEmpty(stowageTemp.getCorrectedUllage())
  //                        ? null
  //                        : stowageTemp.getCorrectedUllage());
  //                loadablePlanStowageDetailsTemp.setCorrectionFactor(
  //                    isEmpty(stowageTemp.getCorrectionFactor())
  //                        ? null
  //                        : stowageTemp.getCorrectionFactor());
  //
  //                loadablePlanStowageDetailsTemp.setQuantity(
  //                    isEmpty(stowageTemp.getQuantity()) ? null : stowageTemp.getQuantity());
  //                loadablePlanStowageDetailsTemp.setRdgUllage(
  //                    isEmpty(stowageTemp.getRdgUllage()) ? null : stowageTemp.getRdgUllage());
  //                loadablePlanStowageDetailsTemp.setIsBallast(stowageTemp.getIsBallast());
  //                loadablePlanStowageDetailsTemp.setIsCommingle(stowageTemp.getIsCommingle());
  //                loadablePlanStowageDetailsTemp.setFillingRatio(
  //                    isEmpty(stowageTemp.getFillingRatio()) ? null :
  // stowageTemp.getFillingRatio());
  //                LoadablePlanStowageDetailsTemp lPlanStowageDetailsTemp =
  //                    stowageDetailsTempRepository.save(loadablePlanStowageDetailsTemp);
  //                log.info(
  //                    "Stowage Edit LoadablePlanStowageDetailsTemp insert or update : "
  //                        + lPlanStowageDetailsTemp.getId());
  //              });
  //    }
  //  }
  @Transactional
  void savePatternInShipSide(PatternDetails patternDetails, LoadablePattern loadablePattern) {

    if (!Objects.isNull(patternDetails)) {
      if (patternDetails.getLoadablePlanQuantityList() != null) {
        List<LoadablePlanQuantity> loadablePlanQuantityList = new ArrayList<>();
        patternDetails
            .getLoadablePlanQuantityList()
            .forEach(
                lqcd -> {
                  LoadablePlanQuantity loadablePlanQuantity = null;
                  if (lqcd.getId() != null) {
                    Optional<LoadablePlanQuantity> loadablePlanQuantityExist =
                        loadablePlanQuantityRepository.findById(lqcd.getId());
                    if (loadablePlanQuantityExist.isPresent()) {
                      loadablePlanQuantity = loadablePlanQuantityExist.get();
                    } else {
                      loadablePlanQuantity = new LoadablePlanQuantity();
                      loadablePlanQuantity.setId(lqcd.getId());
                    }
                  } else {
                    loadablePlanQuantity = new LoadablePlanQuantity();
                  }
                  LoadablePlanQuantity finalLoadablePlanQuantity = loadablePlanQuantity;
                  Optional.ofNullable(lqcd.getDifferencePercentage())
                      .ifPresentOrElse(
                          loadablePlanQuantity::setDifferencePercentage,
                          () -> finalLoadablePlanQuantity.setDifferencePercentage(null));
                  Optional.ofNullable(lqcd.getEstimatedApi())
                      .ifPresentOrElse(
                          finalLoadablePlanQuantity::setEstimatedApi,
                          () -> finalLoadablePlanQuantity.setEstimatedApi(new BigDecimal(0)));
                  Optional.ofNullable(lqcd.getEstimatedTemperature())
                      .ifPresentOrElse(
                          finalLoadablePlanQuantity::setEstimatedTemperature,
                          () ->
                              finalLoadablePlanQuantity.setEstimatedTemperature(new BigDecimal(0)));
                  Optional.ofNullable(lqcd.getCargoXId())
                      .filter(cargoId -> cargoId != 0)
                      .ifPresentOrElse(
                          finalLoadablePlanQuantity::setCargoXId,
                          () -> finalLoadablePlanQuantity.setCargoXId(null));
                  finalLoadablePlanQuantity.setIsActive(true);
                  Optional.ofNullable(lqcd.getLoadableMt())
                      .ifPresentOrElse(
                          finalLoadablePlanQuantity::setLoadableMt,
                          () -> finalLoadablePlanQuantity.setLoadableMt(null));
                  finalLoadablePlanQuantity.setOrderQuantity(
                      (lqcd.getOrderQuantity() != null)
                          ? lqcd.getOrderQuantity()
                          : new BigDecimal(0));
                  finalLoadablePlanQuantity.setLoadablePattern(loadablePattern);
                  Optional.ofNullable(lqcd.getCargoAbbreviation())
                      .ifPresentOrElse(
                          finalLoadablePlanQuantity::setCargoAbbreviation,
                          () -> finalLoadablePlanQuantity.setCargoAbbreviation(null));
                  Optional.ofNullable(lqcd.getCargoColor())
                      .ifPresentOrElse(
                          finalLoadablePlanQuantity::setCargoColor,
                          () -> finalLoadablePlanQuantity.setCargoColor(null));
                  Optional.ofNullable(lqcd.getPriority())
                      .ifPresentOrElse(
                          finalLoadablePlanQuantity::setPriority,
                          () -> finalLoadablePlanQuantity.setPriority(null));
                  Optional.ofNullable(lqcd.getLoadingOrder())
                      .ifPresentOrElse(
                          finalLoadablePlanQuantity::setLoadingOrder,
                          () -> finalLoadablePlanQuantity.setLoadingOrder(null));
                  Optional.ofNullable(lqcd.getMinTolerence())
                      .ifPresentOrElse(
                          finalLoadablePlanQuantity::setMinTolerence,
                          () -> finalLoadablePlanQuantity.setMinTolerence(null));
                  Optional.ofNullable(lqcd.getMaxTolerence())
                      .ifPresentOrElse(
                          finalLoadablePlanQuantity::setMaxTolerence,
                          () -> finalLoadablePlanQuantity.setMaxTolerence(null));
                  Optional.ofNullable(lqcd.getSlopQuantity())
                      .ifPresentOrElse(
                          finalLoadablePlanQuantity::setSlopQuantity,
                          () -> finalLoadablePlanQuantity.setSlopQuantity(null));
                  Optional.ofNullable(lqcd.getCargoNominationId())
                      .ifPresentOrElse(
                          finalLoadablePlanQuantity::setCargoNominationId,
                          () -> finalLoadablePlanQuantity.setCargoNominationId(null));
                  loadablePlanQuantity.setCargoNominationTemperature(
                      (lqcd.getCargoNominationTemperature() != null)
                          ? lqcd.getCargoNominationTemperature()
                          : new BigDecimal(0));
                  Optional.ofNullable(lqcd.getTimeRequiredForLoading())
                      .ifPresentOrElse(
                          finalLoadablePlanQuantity::setTimeRequiredForLoading,
                          () -> finalLoadablePlanQuantity.setTimeRequiredForLoading(null));
                  loadablePlanQuantityList.add(finalLoadablePlanQuantity);
                  loadablePlanQuantityRepository.save(finalLoadablePlanQuantity);
                });
        if (loadablePlanQuantityList.size() > 0) {
          //                List<LoadablePlanQuantity>  loadablePlanQuantityEntityList =
          //
          // loadablePlanQuantityRepository.saveAll(loadablePlanQuantityList);
          log.info("===Pattern Update LoadablePlanQuantity " + loadablePlanQuantityList.size());
        }
      }
    }

    if (patternDetails.getToppingOffSequenceList() != null) {
      List<LoadablePatternCargoToppingOffSequence> loadablePatternCargoToppingOffSequencesList =
          new ArrayList<>();
      patternDetails
          .getToppingOffSequenceList()
          .forEach(
              lqcd -> {
                LoadablePatternCargoToppingOffSequence lpctos = null;
                if (lqcd.getId() != null) {
                  Optional<LoadablePatternCargoToppingOffSequence> lpctosIsExist =
                      toppingOffSequenceRepository.findById(lqcd.getId());
                  if (lpctosIsExist.isPresent()) {
                    lpctos = lpctosIsExist.get();
                  } else {
                    lpctos = new LoadablePatternCargoToppingOffSequence();
                    lpctos.setId(lpctos.getId());
                  }
                } else {
                  lpctos = new LoadablePatternCargoToppingOffSequence();
                }
                LoadablePatternCargoToppingOffSequence finalLpctos = lpctos;
                Optional.ofNullable(lpctos.getCargoXId())
                    .ifPresentOrElse(finalLpctos::setCargoXId, () -> finalLpctos.setCargoXId(null));
                Optional.ofNullable(lpctos.getTankXId())
                    .ifPresentOrElse(finalLpctos::setTankXId, () -> finalLpctos.setTankXId(null));
                Optional.ofNullable(lpctos.getOrderNumber())
                    .ifPresentOrElse(
                        finalLpctos::setOrderNumber, () -> finalLpctos.setOrderNumber(null));
                finalLpctos.setLoadablePattern(loadablePattern);
                finalLpctos.setIsActive(true);
                loadablePatternCargoToppingOffSequencesList.add(finalLpctos);
                toppingOffSequenceRepository.save(finalLpctos);
              });
      if (loadablePatternCargoToppingOffSequencesList.size() > 0) {
        //        List<LoadablePatternCargoToppingOffSequence>
        // loadablePatternCargoToppingOffSequencesEntity =
        //
        // toppingOffSequenceRepository.saveAll(loadablePatternCargoToppingOffSequencesList);
        log.info(
            "===Pattern Update LoadablePatternCargoToppingOffSequence "
                + loadablePatternCargoToppingOffSequencesList.size());
      }
    }

    if (patternDetails.getLoadablePlanCommingleDetails() != null) {
      List<LoadablePlanCommingleDetails> loadablePlanCommingleDetailsList = new ArrayList<>();
      patternDetails
          .getLoadablePlanCommingleDetails()
          .forEach(
              loadableQuantityCommingleCargoDetailsList -> {
                LoadablePlanCommingleDetails loadablePlanCommingleDetails = null;
                if (loadableQuantityCommingleCargoDetailsList.getId() != null) {
                  Optional<LoadablePlanCommingleDetails> loadablePlanCommingleExist =
                      loadablePlanCommingleDetailsRepository.findById(
                          loadableQuantityCommingleCargoDetailsList.getId());
                  if (loadablePlanCommingleExist.isPresent()) {
                    loadablePlanCommingleDetails = loadablePlanCommingleExist.get();
                  } else {
                    loadablePlanCommingleDetails = new LoadablePlanCommingleDetails();
                    loadablePlanCommingleDetails.setId(
                        loadableQuantityCommingleCargoDetailsList.getId());
                  }
                } else {
                  loadablePlanCommingleDetails = new LoadablePlanCommingleDetails();
                }
                LoadablePlanCommingleDetails finalLoadablePlanCommingleDetails =
                    loadablePlanCommingleDetails;
                Optional.ofNullable(loadableQuantityCommingleCargoDetailsList.getApi())
                    .ifPresentOrElse(
                        finalLoadablePlanCommingleDetails::setApi,
                        () -> finalLoadablePlanCommingleDetails.setApi(null));
                Optional.ofNullable(
                        loadableQuantityCommingleCargoDetailsList.getCargo1Abbreviation())
                    .ifPresentOrElse(
                        finalLoadablePlanCommingleDetails::setCargo1Abbreviation,
                        () -> finalLoadablePlanCommingleDetails.setCargo1Abbreviation(null));
                Optional.ofNullable(loadableQuantityCommingleCargoDetailsList.getCargo1Mt())
                    .ifPresentOrElse(
                        finalLoadablePlanCommingleDetails::setCargo1Mt,
                        () -> finalLoadablePlanCommingleDetails.setCargo1Mt(null));
                Optional.ofNullable(loadableQuantityCommingleCargoDetailsList.getCargo1Percentage())
                    .ifPresentOrElse(
                        finalLoadablePlanCommingleDetails::setCargo1Percentage,
                        () -> finalLoadablePlanCommingleDetails.setCargo1Percentage(null));
                Optional.ofNullable(
                        loadableQuantityCommingleCargoDetailsList.getCargo2Abbreviation())
                    .ifPresentOrElse(
                        finalLoadablePlanCommingleDetails::setCargo2Abbreviation,
                        () -> finalLoadablePlanCommingleDetails.setCargo2Abbreviation(null));
                Optional.ofNullable(loadableQuantityCommingleCargoDetailsList.getCargo2Mt())
                    .ifPresentOrElse(
                        finalLoadablePlanCommingleDetails::setCargo2Mt,
                        () -> finalLoadablePlanCommingleDetails.setCargo2Mt(null));
                Optional.ofNullable(loadableQuantityCommingleCargoDetailsList.getCargo2Percentage())
                    .ifPresentOrElse(
                        finalLoadablePlanCommingleDetails::setCargo2Percentage,
                        () -> finalLoadablePlanCommingleDetails.setCargo2Percentage(null));
                Optional.ofNullable(loadableQuantityCommingleCargoDetailsList.getGrade())
                    .ifPresentOrElse(
                        finalLoadablePlanCommingleDetails::setGrade,
                        () -> finalLoadablePlanCommingleDetails.setGrade(null));
                finalLoadablePlanCommingleDetails.setIsActive(true);
                finalLoadablePlanCommingleDetails.setLoadablePattern(loadablePattern);
                Optional.ofNullable(loadableQuantityCommingleCargoDetailsList.getQuantity())
                    .ifPresentOrElse(
                        finalLoadablePlanCommingleDetails::setQuantity,
                        () -> finalLoadablePlanCommingleDetails.setQuantity(null));
                Optional.ofNullable(loadableQuantityCommingleCargoDetailsList.getTankName())
                    .ifPresentOrElse(
                        finalLoadablePlanCommingleDetails::setTankName,
                        () -> finalLoadablePlanCommingleDetails.setTankName(null));
                Optional.ofNullable(loadableQuantityCommingleCargoDetailsList.getTemperature())
                    .ifPresentOrElse(
                        finalLoadablePlanCommingleDetails::setTemperature,
                        () -> finalLoadablePlanCommingleDetails.setTemperature(null));
                Optional.ofNullable(loadableQuantityCommingleCargoDetailsList.getOrderQuantity())
                    .ifPresentOrElse(
                        finalLoadablePlanCommingleDetails::setOrderQuantity,
                        () -> finalLoadablePlanCommingleDetails.setOrderQuantity(null));
                Optional.ofNullable(loadableQuantityCommingleCargoDetailsList.getPriority())
                    .ifPresentOrElse(
                        finalLoadablePlanCommingleDetails::setPriority,
                        () -> finalLoadablePlanCommingleDetails.setPriority(null));
                Optional.ofNullable(loadableQuantityCommingleCargoDetailsList.getLoadingOrder())
                    .ifPresentOrElse(
                        finalLoadablePlanCommingleDetails::setLoadingOrder,
                        () -> finalLoadablePlanCommingleDetails.setLoadingOrder(null));
                Optional.ofNullable(loadableQuantityCommingleCargoDetailsList.getTankId())
                    .ifPresentOrElse(
                        finalLoadablePlanCommingleDetails::setTankId,
                        () -> finalLoadablePlanCommingleDetails.setTankId(null));
                Optional.ofNullable(loadableQuantityCommingleCargoDetailsList.getFillingRatio())
                    .ifPresentOrElse(
                        finalLoadablePlanCommingleDetails::setFillingRatio,
                        () -> finalLoadablePlanCommingleDetails.setFillingRatio(null));
                Optional.ofNullable(loadableQuantityCommingleCargoDetailsList.getCorrectionFactor())
                    .ifPresentOrElse(
                        finalLoadablePlanCommingleDetails::setCorrectionFactor,
                        () -> finalLoadablePlanCommingleDetails.setCorrectionFactor(null));
                Optional.ofNullable(loadableQuantityCommingleCargoDetailsList.getCorrectedUllage())
                    .ifPresentOrElse(
                        finalLoadablePlanCommingleDetails::setCorrectedUllage,
                        () -> finalLoadablePlanCommingleDetails.setCorrectedUllage(null));
                Optional.ofNullable(loadableQuantityCommingleCargoDetailsList.getRdgUllage())
                    .ifPresentOrElse(
                        finalLoadablePlanCommingleDetails::setRdgUllage,
                        () -> finalLoadablePlanCommingleDetails.setRdgUllage(null));
                Optional.ofNullable(loadableQuantityCommingleCargoDetailsList.getSlopQuantity())
                    .ifPresentOrElse(
                        finalLoadablePlanCommingleDetails::setSlopQuantity,
                        () -> finalLoadablePlanCommingleDetails.setSlopQuantity(null));
                Optional.ofNullable(
                        loadableQuantityCommingleCargoDetailsList.getTimeRequiredForLoading())
                    .ifPresentOrElse(
                        finalLoadablePlanCommingleDetails::setTimeRequiredForLoading,
                        () -> finalLoadablePlanCommingleDetails.setTimeRequiredForLoading(null));
                Optional.ofNullable(
                        loadableQuantityCommingleCargoDetailsList.getCargo2NominationId())
                    .ifPresentOrElse(
                        finalLoadablePlanCommingleDetails::setCargo2NominationId,
                        () -> finalLoadablePlanCommingleDetails.setCargo2NominationId(null));
                Optional.ofNullable(loadableQuantityCommingleCargoDetailsList.getTankShortName())
                    .ifPresentOrElse(
                        finalLoadablePlanCommingleDetails::setTankShortName,
                        () -> finalLoadablePlanCommingleDetails.setTankShortName(null));
                loadablePlanCommingleDetailsList.add(finalLoadablePlanCommingleDetails);
                loadablePlanCommingleDetailsRepository.save(finalLoadablePlanCommingleDetails);
              });
      if (loadablePlanCommingleDetailsList.size() > 0) {
        // List<LoadablePlanCommingleDetails> loadablePlanCommingleDetailsEntity =
        // loadablePlanCommingleDetailsRepository.saveAll(loadablePlanCommingleDetailsList);
        log.info(
            "===Pattern Update LoadablePlanCommingleDetails "
                + loadablePlanCommingleDetailsList.size());
      }
    }

    if (patternDetails.getLoadablePlanStowageDetailsList() != null) {
      List<LoadablePlanStowageDetails> loadablePlanStowageDetailsList = new ArrayList<>();
      patternDetails
          .getLoadablePlanStowageDetailsList()
          .forEach(
              lpsd -> {
                LoadablePlanStowageDetails loadablePlanStowageDetails = null;
                if (lpsd.getId() != null) {
                  Optional<LoadablePlanStowageDetails> loadablePlanStowageDetailsExist =
                      loadablePlanStowageDetailsRespository.findById(lpsd.getId());
                  if (loadablePlanStowageDetailsExist.isPresent()) {
                    loadablePlanStowageDetails = new LoadablePlanStowageDetails();
                  } else {
                    loadablePlanStowageDetails = new LoadablePlanStowageDetails();
                    loadablePlanStowageDetails.setId(lpsd.getId());
                  }
                } else {
                  loadablePlanStowageDetails = new LoadablePlanStowageDetails();
                }
                LoadablePlanStowageDetails finalLoadablePlanStowageDetails =
                    loadablePlanStowageDetails;
                Optional.ofNullable(lpsd.getApi())
                    .ifPresentOrElse(
                        finalLoadablePlanStowageDetails::setApi,
                        () -> finalLoadablePlanStowageDetails.setApi(null));
                Optional.ofNullable(lpsd.getAbbreviation())
                    .ifPresentOrElse(
                        finalLoadablePlanStowageDetails::setAbbreviation,
                        () -> finalLoadablePlanStowageDetails.setAbbreviation(null));
                Optional.ofNullable(lpsd.getColorCode())
                    .ifPresentOrElse(
                        finalLoadablePlanStowageDetails::setColorCode,
                        () -> finalLoadablePlanStowageDetails.setColorCode(null));
                Optional.ofNullable(lpsd.getFillingPercentage())
                    .ifPresentOrElse(
                        finalLoadablePlanStowageDetails::setFillingPercentage,
                        () -> finalLoadablePlanStowageDetails.setFillingPercentage(null));
                Optional.ofNullable(lpsd.getRdgUllage())
                    .ifPresentOrElse(
                        finalLoadablePlanStowageDetails::setRdgUllage,
                        () -> finalLoadablePlanStowageDetails.setRdgUllage(null));
                Optional.ofNullable(lpsd.getTankId())
                    .ifPresentOrElse(
                        finalLoadablePlanStowageDetails::setTankId,
                        () -> finalLoadablePlanStowageDetails.setTankId(null));
                Optional.ofNullable(lpsd.getTankname())
                    .ifPresentOrElse(
                        finalLoadablePlanStowageDetails::setTankname,
                        () -> finalLoadablePlanStowageDetails.setTankname(null));
                finalLoadablePlanStowageDetails.setIsActive(true);
                finalLoadablePlanStowageDetails.setLoadablePattern(loadablePattern);
                Optional.ofNullable(lpsd.getWeight())
                    .ifPresentOrElse(
                        finalLoadablePlanStowageDetails::setWeight,
                        () -> finalLoadablePlanStowageDetails.setWeight(null));
                Optional.ofNullable(lpsd.getTemperature())
                    .ifPresentOrElse(
                        finalLoadablePlanStowageDetails::setTemperature,
                        () -> finalLoadablePlanStowageDetails.setTemperature(null));
                Optional.ofNullable(lpsd.getCorrectionFactor())
                    .ifPresentOrElse(
                        finalLoadablePlanStowageDetails::setCorrectionFactor,
                        () -> finalLoadablePlanStowageDetails.setCorrectionFactor(null));
                Optional.ofNullable(lpsd.getCorrectedUllage())
                    .ifPresentOrElse(
                        finalLoadablePlanStowageDetails::setCorrectedUllage,
                        () -> finalLoadablePlanStowageDetails.setCorrectedUllage(null));
                Optional.ofNullable(lpsd.getCargoNominationId())
                    .ifPresentOrElse(
                        finalLoadablePlanStowageDetails::setCargoNominationId,
                        () -> finalLoadablePlanStowageDetails.setCargoNominationId(null));
                Optional.ofNullable(lpsd.getCargoNominationTemperature())
                    .ifPresentOrElse(
                        finalLoadablePlanStowageDetails::setCargoNominationTemperature,
                        () ->
                            finalLoadablePlanStowageDetails.setCargoNominationTemperature(
                                new BigDecimal(0)));
                loadablePlanStowageDetailsList.add(finalLoadablePlanStowageDetails);
                loadablePlanStowageDetailsRespository.save(finalLoadablePlanStowageDetails);
              });
      if (loadablePlanStowageDetailsList.size() > 0) {
        //        List<LoadablePlanStowageDetails> loadablePlanStowageDetailsEntity  =
        //
        // loadablePlanStowageDetailsRespository.saveAll(loadablePlanStowageDetailsList);
        log.info(
            "===Pattern Update LoadablePlanStowageDetails "
                + loadablePlanStowageDetailsList.size());
      }
    }

    if (patternDetails.getLoadablePlanBallastDetailsList() != null) {
      List<LoadablePlanBallastDetails> loadablePlanBallastDetailsList = new ArrayList<>();
      patternDetails
          .getLoadablePlanBallastDetailsList()
          .forEach(
              lpbd -> {
                LoadablePlanBallastDetails loadablePlanBallastDetails = null;
                if (lpbd.getId() != null) {
                  Optional<LoadablePlanBallastDetails> loadablePlanBallastDetailsExist =
                      loadablePlanBallastDetailsRepository.findById(lpbd.getId());
                  if (loadablePlanBallastDetailsExist.isPresent()) {
                    loadablePlanBallastDetails = loadablePlanBallastDetailsExist.get();
                  } else {
                    loadablePlanBallastDetails = new LoadablePlanBallastDetails();
                    loadablePlanBallastDetails.setId(lpbd.getId());
                  }
                } else {
                  loadablePlanBallastDetails = new LoadablePlanBallastDetails();
                }
                LoadablePlanBallastDetails finalLoadablePlanBallastDetails =
                    loadablePlanBallastDetails;
                Optional.ofNullable(lpbd.getMetricTon())
                    .ifPresentOrElse(
                        finalLoadablePlanBallastDetails::setMetricTon,
                        () -> finalLoadablePlanBallastDetails.setMetricTon(null));
                Optional.ofNullable(lpbd.getPercentage())
                    .ifPresentOrElse(
                        finalLoadablePlanBallastDetails::setPercentage,
                        () -> finalLoadablePlanBallastDetails.setPercentage(null));
                Optional.ofNullable(lpbd.getSg())
                    .ifPresentOrElse(
                        finalLoadablePlanBallastDetails::setSg,
                        () -> finalLoadablePlanBallastDetails.setSg(null));
                Optional.ofNullable(lpbd.getTankName())
                    .ifPresentOrElse(
                        finalLoadablePlanBallastDetails::setTankName,
                        () -> finalLoadablePlanBallastDetails.setTankName(null));
                Optional.ofNullable(lpbd.getTankId())
                    .ifPresentOrElse(
                        finalLoadablePlanBallastDetails::setTankId,
                        () -> finalLoadablePlanBallastDetails.setTankId(null));
                Optional.ofNullable(lpbd.getRdgLevel())
                    .ifPresentOrElse(
                        finalLoadablePlanBallastDetails::setRdgLevel,
                        () -> finalLoadablePlanBallastDetails.setRdgLevel(null));
                finalLoadablePlanBallastDetails.setIsActive(true);
                finalLoadablePlanBallastDetails.setLoadablePattern(loadablePattern);
                Optional.ofNullable(lpbd.getColorCode())
                    .ifPresentOrElse(
                        finalLoadablePlanBallastDetails::setColorCode,
                        () -> finalLoadablePlanBallastDetails.setColorCode(null));
                Optional.ofNullable(lpbd.getCorrectedLevel())
                    .ifPresentOrElse(
                        finalLoadablePlanBallastDetails::setCorrectedLevel,
                        () -> finalLoadablePlanBallastDetails.setCorrectedLevel(null));
                Optional.ofNullable(lpbd.getCorrectionFactor())
                    .ifPresentOrElse(
                        finalLoadablePlanBallastDetails::setCorrectionFactor,
                        () -> finalLoadablePlanBallastDetails.setCorrectionFactor(null));
                loadablePlanBallastDetailsList.add(finalLoadablePlanBallastDetails);
                loadablePlanBallastDetailsRepository.save(finalLoadablePlanBallastDetails);
              });
      if (loadablePlanBallastDetailsList.size() > 0) {
        //            List<LoadablePlanBallastDetails> loadablePlanBallastDetailsEntity =
        //
        // loadablePlanBallastDetailsRepository.saveAll(loadablePlanBallastDetailsList);
        log.info(
            "===Pattern Update LoadablePlanBallastDetails "
                + loadablePlanBallastDetailsList.size());
      }
    }

    if (patternDetails.getLoadablePlanComminglePortwiseDetailsList() != null) {
      List<LoadablePlanComminglePortwiseDetails> loadablePlanComminglePortwiseDetailsList =
          new ArrayList<>();
      patternDetails
          .getLoadablePlanComminglePortwiseDetailsList()
          .forEach(
              it -> {
                LoadablePlanComminglePortwiseDetails loadablePlanComminglePortwiseDetails = null;
                if (it.getId() != null) {
                  Optional<LoadablePlanComminglePortwiseDetails>
                      loadablePlanComminglePortwiseDetailsExist =
                          loadablePlanCommingleDetailsPortwiseRepository.findById(it.getId());
                  if (loadablePlanComminglePortwiseDetailsExist.isPresent()) {
                    loadablePlanComminglePortwiseDetails =
                        loadablePlanComminglePortwiseDetailsExist.get();
                  } else {
                    loadablePlanComminglePortwiseDetails =
                        new LoadablePlanComminglePortwiseDetails();
                    loadablePlanComminglePortwiseDetails.setId(it.getId());
                  }
                } else {
                  loadablePlanComminglePortwiseDetails = new LoadablePlanComminglePortwiseDetails();
                }
                LoadablePlanComminglePortwiseDetails finalLoadablePlanComminglePortwiseDetails =
                    loadablePlanComminglePortwiseDetails;
                Optional.ofNullable(it.getPortId())
                    .ifPresentOrElse(
                        finalLoadablePlanComminglePortwiseDetails::setPortId,
                        () -> finalLoadablePlanComminglePortwiseDetails.setPortId(null));
                Optional.ofNullable(it.getOperationType())
                    .ifPresentOrElse(
                        finalLoadablePlanComminglePortwiseDetails::setOperationType,
                        () -> finalLoadablePlanComminglePortwiseDetails.setOperationType(null));
                Optional.ofNullable(it.getApi())
                    .ifPresentOrElse(
                        finalLoadablePlanComminglePortwiseDetails::setApi,
                        () -> finalLoadablePlanComminglePortwiseDetails.setApi(null));
                Optional.ofNullable(it.getCargo1Abbreviation())
                    .ifPresentOrElse(
                        finalLoadablePlanComminglePortwiseDetails::setCargo1Abbreviation,
                        () ->
                            finalLoadablePlanComminglePortwiseDetails.setCargo1Abbreviation(null));
                Optional.ofNullable(it.getCargo1Mt())
                    .ifPresentOrElse(
                        finalLoadablePlanComminglePortwiseDetails::setCargo1Mt,
                        () -> finalLoadablePlanComminglePortwiseDetails.setCargo1Mt(null));
                Optional.ofNullable(it.getCargo1Percentage())
                    .ifPresentOrElse(
                        finalLoadablePlanComminglePortwiseDetails::setCargo1Percentage,
                        () -> finalLoadablePlanComminglePortwiseDetails.setCargo1Percentage(null));
                Optional.ofNullable(it.getCargo2Abbreviation())
                    .ifPresentOrElse(
                        finalLoadablePlanComminglePortwiseDetails::setCargo2Abbreviation,
                        () ->
                            finalLoadablePlanComminglePortwiseDetails.setCargo2Abbreviation(null));
                Optional.ofNullable(it.getCargo2Mt())
                    .ifPresentOrElse(
                        finalLoadablePlanComminglePortwiseDetails::setCargo2Mt,
                        () -> finalLoadablePlanComminglePortwiseDetails.setCargo2Mt(null));
                Optional.ofNullable(it.getCargo2Percentage())
                    .ifPresentOrElse(
                        finalLoadablePlanComminglePortwiseDetails::setCargo2Percentage,
                        () -> finalLoadablePlanComminglePortwiseDetails.setCargo2Percentage(null));
                Optional.ofNullable(it.getGrade())
                    .ifPresentOrElse(
                        finalLoadablePlanComminglePortwiseDetails::setGrade,
                        () -> finalLoadablePlanComminglePortwiseDetails.setGrade(null));
                finalLoadablePlanComminglePortwiseDetails.setIsActive(true);
                finalLoadablePlanComminglePortwiseDetails.setLoadablePattern(loadablePattern);

                Optional.ofNullable(it.getQuantity())
                    .ifPresentOrElse(
                        finalLoadablePlanComminglePortwiseDetails::setQuantity,
                        () -> finalLoadablePlanComminglePortwiseDetails.setQuantity(null));
                Optional.ofNullable(it.getTankName())
                    .ifPresentOrElse(
                        finalLoadablePlanComminglePortwiseDetails::setTankName,
                        () -> finalLoadablePlanComminglePortwiseDetails.setTankName(null));
                Optional.ofNullable(it.getTemperature())
                    .ifPresentOrElse(
                        finalLoadablePlanComminglePortwiseDetails::setTemperature,
                        () -> finalLoadablePlanComminglePortwiseDetails.setTemperature(null));
                Optional.ofNullable(it.getOrderQuantity())
                    .ifPresentOrElse(
                        finalLoadablePlanComminglePortwiseDetails::setOrderQuantity,
                        () -> finalLoadablePlanComminglePortwiseDetails.setOrderQuantity(null));
                Optional.ofNullable(it.getPriority())
                    .ifPresentOrElse(
                        finalLoadablePlanComminglePortwiseDetails::setPriority,
                        () -> finalLoadablePlanComminglePortwiseDetails.setPriority(null));
                Optional.ofNullable(it.getLoadingOrder())
                    .ifPresentOrElse(
                        finalLoadablePlanComminglePortwiseDetails::setLoadingOrder,
                        () -> finalLoadablePlanComminglePortwiseDetails.setLoadingOrder(null));
                Optional.ofNullable(it.getTankId())
                    .ifPresentOrElse(
                        finalLoadablePlanComminglePortwiseDetails::setTankId,
                        () -> finalLoadablePlanComminglePortwiseDetails.setTankId(null));
                Optional.ofNullable(it.getFillingRatio())
                    .ifPresentOrElse(
                        finalLoadablePlanComminglePortwiseDetails::setFillingRatio,
                        () -> finalLoadablePlanComminglePortwiseDetails.setFillingRatio(null));
                Optional.ofNullable(it.getCorrectionFactor())
                    .ifPresentOrElse(
                        finalLoadablePlanComminglePortwiseDetails::setCorrectionFactor,
                        () -> finalLoadablePlanComminglePortwiseDetails.setCorrectionFactor(null));
                Optional.ofNullable(it.getCorrectedUllage())
                    .ifPresentOrElse(
                        finalLoadablePlanComminglePortwiseDetails::setCorrectedUllage,
                        () -> finalLoadablePlanComminglePortwiseDetails.setCorrectedUllage(null));
                Optional.ofNullable(it.getRdgUllage())
                    .ifPresentOrElse(
                        finalLoadablePlanComminglePortwiseDetails::setRdgUllage,
                        () -> finalLoadablePlanComminglePortwiseDetails.setRdgUllage(null));
                Optional.ofNullable(it.getCargo1NominationId())
                    .ifPresentOrElse(
                        finalLoadablePlanComminglePortwiseDetails::setCargo1NominationId,
                        () ->
                            finalLoadablePlanComminglePortwiseDetails.setCargo1NominationId(null));
                Optional.ofNullable(it.getCargo2NominationId())
                    .ifPresentOrElse(
                        finalLoadablePlanComminglePortwiseDetails::setCargo2NominationId,
                        () ->
                            finalLoadablePlanComminglePortwiseDetails.setCargo2NominationId(null));
                Optional.ofNullable(it.getPortRotationXid())
                    .ifPresentOrElse(
                        finalLoadablePlanComminglePortwiseDetails::setPortRotationXid,
                        () -> finalLoadablePlanComminglePortwiseDetails.setPortRotationXid(null));
                Optional.ofNullable(it.getTankShortName())
                    .ifPresentOrElse(
                        finalLoadablePlanComminglePortwiseDetails::setTankName,
                        () -> finalLoadablePlanComminglePortwiseDetails.setTankName(null));
                loadablePlanComminglePortwiseDetailsList.add(
                    finalLoadablePlanComminglePortwiseDetails);
                loadablePlanCommingleDetailsPortwiseRepository.save(
                    finalLoadablePlanComminglePortwiseDetails);
              });
      if (loadablePlanComminglePortwiseDetailsList.size() > 0) {
        // List<LoadablePlanComminglePortwiseDetails> loadablePlanComminglePortwiseDetailsEntity =
        // loadablePlanCommingleDetailsPortwiseRepository.saveAll(loadablePlanComminglePortwiseDetailsList);
        log.info(
            "===Pattern Update LoadablePlanComminglePortwiseDetails "
                + loadablePlanComminglePortwiseDetailsList.size());
      }
    }

    if (patternDetails.getLoadablePatternCargoDetailsList() != null) {
      List<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails>
          loadablePatternCargoDetailsList = new ArrayList<>();
      patternDetails
          .getLoadablePatternCargoDetailsList()
          .forEach(
              lpsd -> {
                com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails
                    loadablePatternCargoDetails = null;
                if (lpsd.getId() != null) {
                  Optional<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails>
                      loadablePatternCargoDetailsIsExist =
                          loadablePatternCargoDetailsRepository.findById(lpsd.getId());
                  if (loadablePatternCargoDetailsIsExist.isPresent()) {
                    loadablePatternCargoDetails = loadablePatternCargoDetailsIsExist.get();
                  } else {
                    loadablePatternCargoDetails =
                        new com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails();
                    loadablePatternCargoDetails.setId(lpsd.getId());
                  }
                } else {
                  loadablePatternCargoDetails =
                      new com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails();
                }
                LoadablePatternCargoDetails finalLoadablePatternCargoDetails =
                    loadablePatternCargoDetails;
                finalLoadablePatternCargoDetails.setIsActive(true);
                Optional.ofNullable(lpsd.getLoadablePatternId())
                    .ifPresentOrElse(
                        finalLoadablePatternCargoDetails::setLoadablePatternId,
                        () -> finalLoadablePatternCargoDetails.setLoadablePatternId(null));
                Optional.ofNullable(lpsd.getOperationType())
                    .ifPresentOrElse(
                        finalLoadablePatternCargoDetails::setOperationType,
                        () -> finalLoadablePatternCargoDetails.setOperationType(null));
                Optional.ofNullable(lpsd.getPlannedQuantity())
                    .ifPresentOrElse(
                        finalLoadablePatternCargoDetails::setPlannedQuantity,
                        () ->
                            finalLoadablePatternCargoDetails.setPlannedQuantity(new BigDecimal(0)));
                Optional.ofNullable(lpsd.getPortId())
                    .ifPresentOrElse(
                        finalLoadablePatternCargoDetails::setPortId,
                        () -> finalLoadablePatternCargoDetails.setPortId(null));
                Optional.ofNullable(lpsd.getPortRotationId())
                    .ifPresentOrElse(
                        finalLoadablePatternCargoDetails::setPortRotationId,
                        () -> finalLoadablePatternCargoDetails.setPortRotationId(null));
                Optional.ofNullable(lpsd.getTankId())
                    .ifPresentOrElse(
                        finalLoadablePatternCargoDetails::setTankId,
                        () -> finalLoadablePatternCargoDetails.setTankId(null));
                Optional.ofNullable(lpsd.getAbbreviation())
                    .ifPresentOrElse(
                        finalLoadablePatternCargoDetails::setAbbreviation,
                        () -> finalLoadablePatternCargoDetails.setAbbreviation(null));
                Optional.ofNullable(lpsd.getApi())
                    .ifPresentOrElse(
                        finalLoadablePatternCargoDetails::setApi,
                        () -> finalLoadablePatternCargoDetails.setApi(new BigDecimal(0)));
                Optional.ofNullable(lpsd.getTemperature())
                    .ifPresentOrElse(
                        finalLoadablePatternCargoDetails::setTemperature,
                        () -> finalLoadablePatternCargoDetails.setTemperature(new BigDecimal(0)));
                Optional.ofNullable(lpsd.getColorCode())
                    .ifPresentOrElse(
                        finalLoadablePatternCargoDetails::setColorCode,
                        () -> finalLoadablePatternCargoDetails.setColorCode(null));
                Optional.ofNullable(lpsd.getCorrectionFactor())
                    .ifPresentOrElse(
                        finalLoadablePatternCargoDetails::setCorrectionFactor,
                        () -> finalLoadablePatternCargoDetails.setCorrectionFactor(null));
                loadablePatternCargoDetails.setCorrectedUllage(
                    !StringUtils.isEmpty(lpsd.getCorrectedUllage())
                        ? lpsd.getCorrectedUllage()
                        : null);
                Optional.ofNullable(lpsd.getCargoNominationId())
                    .ifPresentOrElse(
                        finalLoadablePatternCargoDetails::setCargoNominationId,
                        () -> finalLoadablePatternCargoDetails.setCargoNominationId(null));
                Optional.ofNullable(lpsd.getCargoNominationTemperature())
                    .ifPresentOrElse(
                        finalLoadablePatternCargoDetails::setCargoNominationTemperature,
                        () ->
                            finalLoadablePatternCargoDetails.setCargoNominationTemperature(
                                new BigDecimal(0)));
                Optional.ofNullable(lpsd.getFillingRatio())
                    .ifPresentOrElse(
                        finalLoadablePatternCargoDetails::setFillingRatio,
                        () -> finalLoadablePatternCargoDetails.setFillingRatio(null));
                Optional.ofNullable(lpsd.getOnBoard())
                    .ifPresentOrElse(
                        finalLoadablePatternCargoDetails::setOnBoard,
                        () -> finalLoadablePatternCargoDetails.setOnBoard(new BigDecimal(0)));
                Optional.ofNullable(lpsd.getMaxTankVolume())
                    .ifPresentOrElse(
                        finalLoadablePatternCargoDetails::setMaxTankVolume,
                        () -> finalLoadablePatternCargoDetails.setMaxTankVolume(new BigDecimal(0)));
                loadablePatternCargoDetailsList.add(finalLoadablePatternCargoDetails);
                loadablePatternCargoDetailsRepository.save(finalLoadablePatternCargoDetails);
              });
      if (loadablePatternCargoDetailsList.size() > 0) {
        //            List<com.cpdss.loadablestudy.entity.LoadablePatternCargoDetails>
        // loadablePatternCargoDetailsEntity =
        //
        // loadablePatternCargoDetailsRepository.saveAll(loadablePatternCargoDetailsList);
        log.info(
            "===Pattern Update LoadablePatternCargoDetails "
                + loadablePatternCargoDetailsList.size());
      }
    }

    if (patternDetails.getLoadablePlanStowageBallastDetailsList() != null) {
      List<LoadablePlanStowageBallastDetails> loadablePlanStowageBallastDetailsList =
          new ArrayList<>();
      patternDetails
          .getLoadablePlanStowageBallastDetailsList()
          .forEach(
              lpbd -> {
                LoadablePlanStowageBallastDetails loadablePlanStowageBallastDetails = null;
                if (lpbd.getId() != null) {
                  Optional<LoadablePlanStowageBallastDetails>
                      loadablePlanStowageBallastDetailsIsExist =
                          loadablePlanStowageBallastDetailsRepository.findById(lpbd.getId());
                  if (loadablePlanStowageBallastDetailsIsExist.isPresent()) {
                    loadablePlanStowageBallastDetails =
                        loadablePlanStowageBallastDetailsIsExist.get();
                  } else {
                    loadablePlanStowageBallastDetails = new LoadablePlanStowageBallastDetails();
                    loadablePlanStowageBallastDetails.setId(lpbd.getId());
                  }
                } else {
                  loadablePlanStowageBallastDetails = new LoadablePlanStowageBallastDetails();
                }
                LoadablePlanStowageBallastDetails finalLoadablePlanStowageBallastDetails =
                    loadablePlanStowageBallastDetails;
                Optional.ofNullable(lpbd.getLoadablePatternId())
                    .ifPresentOrElse(
                        finalLoadablePlanStowageBallastDetails::setLoadablePatternId,
                        () -> finalLoadablePlanStowageBallastDetails.setLoadablePatternId(null));
                Optional.ofNullable(lpbd.getOperationType())
                    .ifPresentOrElse(
                        finalLoadablePlanStowageBallastDetails::setOperationType,
                        () -> finalLoadablePlanStowageBallastDetails.setOperationType(null));
                Optional.ofNullable(lpbd.getPortXId())
                    .ifPresentOrElse(
                        finalLoadablePlanStowageBallastDetails::setPortXId,
                        () -> finalLoadablePlanStowageBallastDetails.setPortXId(null));
                Optional.ofNullable(lpbd.getPortRotationId())
                    .ifPresentOrElse(
                        finalLoadablePlanStowageBallastDetails::setPortRotationId,
                        () -> finalLoadablePlanStowageBallastDetails.setPortRotationId(null));
                Optional.ofNullable(lpbd.getQuantity())
                    .ifPresentOrElse(
                        finalLoadablePlanStowageBallastDetails::setQuantity,
                        () ->
                            finalLoadablePlanStowageBallastDetails.setQuantity(new BigDecimal(0)));
                Optional.ofNullable(lpbd.getTankXId())
                    .ifPresentOrElse(
                        finalLoadablePlanStowageBallastDetails::setTankXId,
                        () -> finalLoadablePlanStowageBallastDetails.setTankXId(null));
                Optional.ofNullable(lpbd.getColorCode())
                    .ifPresentOrElse(
                        finalLoadablePlanStowageBallastDetails::setColorCode,
                        () -> finalLoadablePlanStowageBallastDetails.setColorCode(null));
                finalLoadablePlanStowageBallastDetails.setIsActive(true);
                Optional.ofNullable(lpbd.getSg())
                    .ifPresentOrElse(
                        finalLoadablePlanStowageBallastDetails::setSg,
                        () -> finalLoadablePlanStowageBallastDetails.setSg(null));
                Optional.ofNullable(lpbd.getCorrectedUllage())
                    .ifPresentOrElse(
                        finalLoadablePlanStowageBallastDetails::setCorrectedUllage,
                        () -> finalLoadablePlanStowageBallastDetails.setCorrectedUllage(null));
                Optional.ofNullable(lpbd.getCorrectionFactor())
                    .ifPresentOrElse(
                        finalLoadablePlanStowageBallastDetails::setCorrectionFactor,
                        () -> finalLoadablePlanStowageBallastDetails.setCorrectionFactor(null));
                Optional.ofNullable(lpbd.getRdgUllage())
                    .ifPresentOrElse(
                        finalLoadablePlanStowageBallastDetails::setRdgUllage,
                        () -> finalLoadablePlanStowageBallastDetails.setRdgUllage(null));
                Optional.ofNullable(lpbd.getFillingPercentage())
                    .ifPresentOrElse(
                        finalLoadablePlanStowageBallastDetails::setFillingPercentage,
                        () -> finalLoadablePlanStowageBallastDetails.setFillingPercentage(null));
                Optional.ofNullable(lpbd.getVolume())
                    .ifPresentOrElse(
                        finalLoadablePlanStowageBallastDetails::setVolume,
                        () -> finalLoadablePlanStowageBallastDetails.setVolume(new BigDecimal(0)));
                Optional.ofNullable(lpbd.getMaxTankVolume())
                    .ifPresentOrElse(
                        finalLoadablePlanStowageBallastDetails::setMaxTankVolume,
                        () ->
                            finalLoadablePlanStowageBallastDetails.setMaxTankVolume(
                                new BigDecimal(0)));
                loadablePlanStowageBallastDetailsList.add(finalLoadablePlanStowageBallastDetails);
                loadablePlanStowageBallastDetailsRepository.save(
                    finalLoadablePlanStowageBallastDetails);
              });
      if (loadablePlanStowageBallastDetailsList.size() > 0) {
        //           List<LoadablePlanStowageBallastDetails> loadablePlanStowageBallastDetailsEntity
        // =
        //
        // loadablePlanStowageBallastDetailsRepository.saveAll(loadablePlanStowageBallastDetailsList);
        log.info(
            "===Pattern Update LoadablePlanStowageBallastDetails "
                + loadablePlanStowageBallastDetailsList.size());
      }
    }

    if (patternDetails.getSynopticalTableLoadicatorData() != null) {
      List<SynopticalTableLoadicatorData> synopticalTableLoadicatorDataList = new ArrayList<>();
      patternDetails
          .getSynopticalTableLoadicatorData()
          .forEach(
              lsd -> {
                SynopticalTableLoadicatorData synopticalTableLoadicatorData = null;
                if (lsd.getId() != null) {
                  Optional<SynopticalTableLoadicatorData> synopticalTableLoadicatorDataExist =
                      synopticalTableLoadicatorDataRepository.findById(lsd.getId());
                  if (synopticalTableLoadicatorDataExist.isPresent()) {
                    synopticalTableLoadicatorData = synopticalTableLoadicatorDataExist.get();
                  } else {
                    synopticalTableLoadicatorData = new SynopticalTableLoadicatorData();
                    synopticalTableLoadicatorData.setId(lsd.getId());
                  }
                } else {
                  synopticalTableLoadicatorData = new SynopticalTableLoadicatorData();
                }
                com.cpdss.loadablestudy.entity.SynopticalTableLoadicatorData
                    finalSynopticalTableLoadicatorData = synopticalTableLoadicatorData;
                Optional.ofNullable(lsd.getLoadablePatternId())
                    .ifPresentOrElse(
                        finalSynopticalTableLoadicatorData::setLoadablePatternId,
                        () -> finalSynopticalTableLoadicatorData.setLoadablePatternId(null));
                Optional.ofNullable(lsd.getCalculatedDraftFwdPlanned())
                    .ifPresentOrElse(
                        finalSynopticalTableLoadicatorData::setCalculatedDraftFwdPlanned,
                        () ->
                            finalSynopticalTableLoadicatorData.setCalculatedDraftFwdPlanned(
                                new BigDecimal(0)));
                Optional.ofNullable(lsd.getCalculatedDraftMidPlanned())
                    .ifPresentOrElse(
                        finalSynopticalTableLoadicatorData::setCalculatedDraftMidPlanned,
                        () ->
                            finalSynopticalTableLoadicatorData.setCalculatedDraftMidPlanned(
                                new BigDecimal(0)));
                Optional.ofNullable(lsd.getCalculatedDraftAftPlanned())
                    .ifPresentOrElse(
                        finalSynopticalTableLoadicatorData::setCalculatedDraftAftPlanned,
                        () ->
                            finalSynopticalTableLoadicatorData.setCalculatedDraftAftPlanned(
                                new BigDecimal(0)));
                Optional.ofNullable(lsd.getCalculatedTrimPlanned())
                    .ifPresentOrElse(
                        finalSynopticalTableLoadicatorData::setCalculatedTrimPlanned,
                        () ->
                            finalSynopticalTableLoadicatorData.setCalculatedTrimPlanned(
                                new BigDecimal(0)));
                Optional.ofNullable(lsd.getBendingMoment())
                    .ifPresentOrElse(
                        finalSynopticalTableLoadicatorData::setBendingMoment,
                        () ->
                            finalSynopticalTableLoadicatorData.setBendingMoment(new BigDecimal(0)));
                Optional.ofNullable(lsd.getShearingForce())
                    .ifPresentOrElse(
                        finalSynopticalTableLoadicatorData::setShearingForce,
                        () ->
                            finalSynopticalTableLoadicatorData.setShearingForce(new BigDecimal(0)));

                finalSynopticalTableLoadicatorData.setActive(true);
                if (lsd.getSynopticalTable() != null && lsd.getSynopticalTable().getId() != null) {
                  Optional<SynopticalTable> optionalSynopticalTableLoadicatorData =
                      synopticalTableRepository.findById(lsd.getId());
                  if (optionalSynopticalTableLoadicatorData.isPresent()) {
                    synopticalTableLoadicatorData.setSynopticalTable(
                        optionalSynopticalTableLoadicatorData.get());
                  }
                }

                synopticalTableLoadicatorDataList.add(synopticalTableLoadicatorData);
                synopticalTableLoadicatorDataRepository.save(synopticalTableLoadicatorData);
              });
      if (synopticalTableLoadicatorDataList.size() > 0) {
        //                   List<SynopticalTableLoadicatorData> synopticalTableLoadicatorDataEntity
        // =
        //
        // synopticalTableLoadicatorDataRepository.saveAll(synopticalTableLoadicatorDataList);
        log.info(
            "===Pattern Update SynopticalTableLoadicatorData "
                + synopticalTableLoadicatorDataList.size());
      }
    }
  }
}
