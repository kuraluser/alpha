/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.*;
import static java.lang.String.valueOf;
import static org.springframework.util.StringUtils.isEmpty;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.EnvoyReaderServiceGrpc;
import com.cpdss.common.generated.VesselInfo;
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
  @Autowired private CargoNominationOperationDetailsRepository cargoNominationOperationDetailsRepository;
  @Autowired private LoadableStudyRuleInputRepository loadableStudyRuleInputRepository;


  public LoadableStudy setLoadablestudyShore(String jsonResult, String messageId)
      throws GenericServiceException {
    log.info("inside setLoadablestudyShore ");
    LoadableStudy loadableStudyEntity = null;
    com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy =
        new Gson().fromJson(jsonResult, com.cpdss.loadablestudy.domain.LoadableStudy.class);

    Voyage voyage = saveVoyageShore(loadableStudy.getVesselId(), loadableStudy.getVoyage());
    ModelMapper modelMapper = new ModelMapper();
    if (!checkIfLoadableStudyExist(loadableStudy.getName(), voyage)) {

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
        e.printStackTrace();
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
    if (!synopticalTableDetails.isEmpty()) {
      List<SynopticalTable> synopticalTableList =
          synopticalTableDetails.stream()
              .filter(data -> data.getLoadableStudyPortRotationId().equals(portRotation.getId()))
              .map(
                  synopticalTable -> {
                    SynopticalTable entitySynoptical = new SynopticalTable();
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
    entity.setCargoId(0 == request.getCargoId() ? null : request.getCargoId());
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
                    if(cargo.getId() != null){
                     Optional<CargoNominationPortDetails> existingCargoNominationPortDetails =
                             cargoNominationOperationDetailsRepository.findById(cargo.getId());
                      if(existingCargoNominationPortDetails.isPresent()){
                        cargoNominationPortDetails = existingCargoNominationPortDetails.get();
                      }else{
                        cargoNominationPortDetails =
                                new CargoNominationPortDetails();
                      }
                    }else{
                      cargoNominationPortDetails =
                              new CargoNominationPortDetails();
                    }
                    cargoNominationPortDetails.setCargoNomination(cargoNomination);
                    cargoNominationPortDetails.setPortId(cargo.getPortId());
                    cargoNominationPortDetails.setQuantity(new BigDecimal(cargo.getQuantity()));
                    cargoNominationPortDetails.setIsActive(true);
                    return cargoNominationPortDetails;
                  })
              .collect(Collectors.toSet());
      cargoNomination.setCargoNominationPortDetails(cargoNominationPortDetailsList);
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

  private boolean checkIfLoadableStudyExist(String name, Voyage voyage) {
    boolean duplicate =
        this.loadableStudyRepository.existsByNameIgnoreCaseAndPlanningTypeXIdAndVoyageAndIsActive(
            name, Common.PLANNING_TYPE.LOADABLE_STUDY_VALUE, voyage, true);
    return duplicate;
  }

  private Voyage saveVoyageShore(Long vesselId, VoyageDto voyageDto) {
    List<Voyage> voyageList =
        voyageRepository.findByCompanyXIdAndVesselXIdAndVoyageNoIgnoreCase(
            1L, vesselId, voyageDto.getVoyageNo());
    if (voyageList != null && voyageList.size() != 0) {
      return voyageList.get(0);
    } else {
      Voyage voyage = new Voyage();
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
      return voyage;
    }
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
  public LoadableStudy persistShipPayloadInShoreSide(String jsonResult, String messageId)
          throws GenericServiceException, IOException {
    LoadableStudy loadableStudyEntity = null;
    LoadabalePatternValidateRequest loadabalePatternValidateRequest =
        new Gson()
            .fromJson(
                jsonResult, com.cpdss.loadablestudy.domain.LoadabalePatternValidateRequest.class);
    com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy =
        loadabalePatternValidateRequest.getLoadableStudy();
    Voyage voyage = saveOrUpdateVoyageInShoreSide(loadableStudy.getVesselId(), loadableStudy.getVoyage());
    Optional<LoadableStudy> loadableStudyEntityOpt = loadableStudyRepository.findByIdAndIsActive(loadableStudy.getId(), true);
    ModelMapper modelMapper = new ModelMapper();
    if(loadableStudyEntityOpt.isPresent()){
      log.debug("Stowage Edit update LS --- id : "+loadableStudy.getId());
      loadableStudyEntity = saveOrUpdateLoadableStudyInShore(loadableStudy, voyage, loadableStudyEntityOpt.get());
      updateCommunicationStatus(messageId, loadableStudyEntity);
      saveOrUpdateLoadableStudyDataInShore(loadableStudyEntity, loadableStudy, modelMapper);
      saveLoadablePlanStowageTempDetailsInShore(loadabalePatternValidateRequest);
      // savePattern(loadabalePatternValidateRequest, loadableStudyEntity);
    }else{
      log.debug("Stowage Edit insert LS --- id : "+loadableStudy.getId());
      LoadableStudy entity = new LoadableStudy();
      entity.setId(loadableStudy.getId());
      loadableStudyEntity = saveOrUpdateLoadableStudyInShore(loadableStudy, voyage, entity);
      updateCommunicationStatus(messageId, loadableStudyEntity);
      saveOrUpdateLoadableStudyDataInShore(loadableStudyEntity, loadableStudy, modelMapper);
      saveLoadablePlanStowageTempDetailsInShore(loadabalePatternValidateRequest);
    }
    return loadableStudyEntity;
  }

  private Voyage saveOrUpdateVoyageInShoreSide(Long vesselId, VoyageDto voyageDto) {
   Voyage voyageEntity = voyageRepository.findByIdAndIsActive(voyageDto.getId(), true);
   if(Objects.isNull(voyageEntity)){
     voyageEntity = new Voyage();
     voyageEntity.setId(voyageDto.getId());
     log.debug("Stowage Edit insert new voyage --- id : "+voyageDto.getId()+" vesselId : "+vesselId);
   }else{
     log.debug("Stowage Edit update existing voyage-- id : "+voyageDto.getId()+ "vesselId : "+vesselId);
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
                      DateTimeFormatter.ofPattern(VOYAGE_DATE_FORMAT)
                              .parse(voyageDto.getVoyageEndDate()))
                      : null);
    voyageEntity = voyageRepository.save(voyageEntity);
    return voyageEntity;
  }

  private LoadableStudy saveOrUpdateLoadableStudyInShore(
          com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy, Voyage voyage, LoadableStudy entity)
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
//                attachment.getFileName().substring(0, attachment.getFileName().lastIndexOf("."));
//        String extension =
//                attachment
//                        .getFileName()
//                        .substring(attachment.getFileName().lastIndexOf("."))
//                        .toLowerCase();
//        String filePath = folderLocation + fileName + "_" + System.currentTimeMillis() + extension;
//        Path path = Paths.get(this.rootFolder + filePath);
//        Files.createFile(path);
//        Files.write(path, attachment.getContent());
        Optional<LoadableStudyAttachments> loadableStudyAttachment =
                loadableStudyAttachmentsRepository.findByIdAndIsActive(attachment.getId(), true);
        LoadableStudyAttachments attachmentEntity;
        if(loadableStudyAttachment.isPresent()){
          log.debug("Stowage Edit Update Attachment File --- id : "+attachment.getId());
          attachmentEntity = loadableStudyAttachment.get();
        }else{
           log.debug("Stowage Edit INSERT Attachment File --- id : "+attachment.getId());
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

  private void updateCommunicationStatus(
          String messageId, LoadableStudy loadableStudyEntity) {
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
            .forEach(commingleCargo -> {
                      try {
                        CommingleCargo commingleCargoEntity = null;
                        if(commingleCargo.getId() != null){
                          Optional<CommingleCargo> existingCommingleCargo =
                                  this.commingleCargoRepository.findByIdAndIsActive(
                                          commingleCargo.getId(), true);
                          if (existingCommingleCargo.isPresent()) {
                            log.debug("Stowage Edit Update CommingleCargo --- id : "+commingleCargo.getId());
                            commingleCargoEntity = existingCommingleCargo.get();
                          }else {
                            log.debug("Stowage Edit INSERT CommingleCargo --- id : "+commingleCargo.getId());
                            commingleCargoEntity = new CommingleCargo();
                            commingleCargoEntity.setId(commingleCargo.getId());
                          }
                        }else {
                          log.debug("Stowage Edit INSERT CommingleCargo --- id : ");
                          commingleCargoEntity = new CommingleCargo();
                          commingleCargoEntity.setId(commingleCargo.getId());
                        }
                        buildCommingleCargoShore(
                                commingleCargoEntity, commingleCargo, loadableStudyEntity.getId());
                        commingleEntities.add(commingleCargoEntity);
                      } catch (Exception e) {
                        log.error("Exception in creating entities for save commingle cargo", e);
                        throw new RuntimeException(e);
                      }
                    });
    List<CommingleCargo> updatedCommingleEntities = this.commingleCargoRepository.saveAll(commingleEntities);
    log.debug("Stowage Edit no of rows updated for commingle : "+updatedCommingleEntities.size());
    List<CargoNomination> cargoNominationEntities = new ArrayList<>();
    loadableStudy.getCargoNomination().stream()
            .forEach(
                    cargoNom -> {
                      Optional<CargoNomination> existingCargoNomination = null;
                      CargoNomination cargoNomination = null;
                      if(cargoNom.getId() != null){
                        existingCargoNomination = cargoNominationRepository.findByIdAndIsActive(cargoNom.getId(), true);
                        if (existingCargoNomination.isPresent()) {
                          cargoNomination = existingCargoNomination.get();
                          log.debug("Stowage Edit Update CargoNomination --- id : "+cargoNom.getId());
                        } else{
                          cargoNomination = new CargoNomination();
                          log.debug("Stowage Edit INSERT CargoNomination --- id : "+cargoNom.getId());
                        }
                      }else{
                        cargoNomination = new CargoNomination();
                        log.debug("Stowage Edit INSERT CargoNomination --- id : ");
                      }
                      buildCargoNomination(
                              cargoNomination,
                              cargoNom,
                              loadableStudy.getCargoNominationOperationDetails());
                      cargoNomination.setLoadableStudyXId(loadableStudyEntity.getId());
                      cargoNominationEntities.add(cargoNomination);
                    });
    List<CargoNomination> updatedCargoNominationEntities = this.cargoNominationRepository.saveAll(cargoNominationEntities);
    log.debug("Stowage Edit no of rows updated for CargoNominationEntities : "+updatedCargoNominationEntities.size());
    List<LoadableStudyPortRotation> loadableStudyPortRotations = new ArrayList<>();
    loadableStudy.getLoadableStudyPortRotation().stream()
            .forEach(
                    portRotation -> {
                      LoadableStudyPortRotation loadableStudyPortRotation;
                      if(portRotation.getId() != null){
                        LoadableStudyPortRotation existingLoadableStudyPortRotation = loadableStudyPortRotationRepository.findByIdAndIsActive(portRotation.getId(), true);
                        if(existingLoadableStudyPortRotation != null){
                          loadableStudyPortRotation = existingLoadableStudyPortRotation;
                          log.debug("Stowage Edit UPDATE LoadableStudyPortRotation --- id : "+portRotation.getId());
                        }else{
                          loadableStudyPortRotation = new LoadableStudyPortRotation();
                          loadableStudyPortRotation.setId(portRotation.getId());
                          log.debug("Stowage Edit INSERT LoadableStudyPortRotation --- id : "+portRotation.getId());
                        }
                      }else{
                        loadableStudyPortRotation = new LoadableStudyPortRotation();
                        log.debug("Stowage Edit INSERT LoadableStudyPortRotation --- id : ");
                      }
                      loadableStudyPortRotation.setLoadableStudy(loadableStudyEntity);
                      loadableStudyPortRotation =
                              buildLoadableStudyPortRotation(
                                      loadableStudyPortRotation,
                                      portRotation,
                                      loadableStudy.getSynopticalTableDetails());
                      loadableStudyPortRotations.add(loadableStudyPortRotation);
                    });
    List<LoadableStudyPortRotation> updatedLoadableStudyPortRotations = this.loadableStudyPortRotationRepository.saveAll(loadableStudyPortRotations);
    log.debug("Stowage Edit no of rows updated for LoadableStudyPortRotation : "+updatedLoadableStudyPortRotations.size());
    List<OnHandQuantity> onHandQuantityEntities = new ArrayList<>();
    loadableStudy.getOnHandQuantity().stream()
            .forEach(
                    onHandQuantity -> {
                      OnHandQuantity onHandQuantityEntity;
                      if(onHandQuantity.getId() != null){
                        OnHandQuantity existingOnHandQuantity =  onHandQuantityRepository.findByIdAndIsActive(onHandQuantity.getId(), true);
                        if(existingOnHandQuantity != null ){
                          log.debug("Stowage Edit UPDATE OnHandQuantity --- id : "+onHandQuantity.getId());
                          onHandQuantityEntity = existingOnHandQuantity;
                        }else{
                          onHandQuantityEntity = new OnHandQuantity();
                          onHandQuantityEntity.setId(onHandQuantity.getId());
                          log.debug("Stowage Edit INSERT OnHandQuantity --- id : "+onHandQuantity.getId());
                        }
                      }else{
                        onHandQuantityEntity = new OnHandQuantity();
                        log.debug("Stowage Edit INSERT OnHandQuantity --- id : ");
                      }
                      onHandQuantityEntity.setLoadableStudy(loadableStudyEntity);
                      buildOnHandQuantity(onHandQuantityEntity, onHandQuantity);
                      onHandQuantityEntities.add(onHandQuantityEntity);
                    });
    List<OnHandQuantity> updatedOnHandQuantityEntities = this.onHandQuantityRepository.saveAll(onHandQuantityEntities);
    log.debug("Stowage Edit no of rows updated for OnHandQuantity : "+updatedOnHandQuantityEntities.size());
    List<OnBoardQuantity> onBoardQuantityEntities = new ArrayList<>();
    loadableStudy.getOnBoardQuantity().stream()
            .forEach(
                    onBoardQuantity -> {
                      OnBoardQuantity onBoardQuantityEntity;
                      if(onBoardQuantity.getId() != null){
                        OnBoardQuantity existingOnBoardQuantityEntity = onBoardQuantityRepository.findByIdAndIsActive(onBoardQuantity.getId(), true);
                        if(existingOnBoardQuantityEntity != null){
                          onBoardQuantityEntity = existingOnBoardQuantityEntity;
                          log.debug("Stowage Edit UPDATE OnBoardQuantity --- id : "+onBoardQuantity.getId());
                        }else{
                          onBoardQuantityEntity = new OnBoardQuantity();
                          onBoardQuantityEntity.setId(onBoardQuantity.getId());
                          log.debug("Stowage Edit INSERT OnBoardQuantity --- id : "+onBoardQuantity.getId());
                        }
                      }else{
                        onBoardQuantityEntity = new OnBoardQuantity();
                        log.debug("Stowage Edit INSERT OnBoardQuantity --- id : ");
                      }
                      onBoardQuantityEntity.setLoadableStudy(loadableStudyEntity);
                      buildOnBoardQuantityEntity(onBoardQuantityEntity, onBoardQuantity);
                      onBoardQuantityEntities.add(onBoardQuantityEntity);
                    });
    List<OnBoardQuantity> updatedOnBoardQuantityEntities = this.onBoardQuantityRepository.saveAll(onBoardQuantityEntities);
    log.debug("Stowage Edit no of rows updated for OnBoardQuantity : "+updatedOnBoardQuantityEntities.size());
    com.cpdss.loadablestudy.domain.LoadableQuantity loadableQuantityDomain =
            loadableStudy.getLoadableQuantity();
    if (null != loadableQuantityDomain) {
      LoadableQuantity loadableQuantity;
      if(loadableQuantityDomain.getId() != null){
        LoadableQuantity  existingLoadableQuantity = loadableQuantityRepository.findByIdAndIsActive(loadableQuantityDomain.getId(), true);
        if(existingLoadableQuantity != null){
          loadableQuantity = existingLoadableQuantity;
          log.debug("Stowage Edit UPDATE LoadableQuantity --- id : "+loadableQuantityDomain.getId());
        }else{
          loadableQuantity = new LoadableQuantity();
          loadableQuantity.setId(loadableQuantityDomain.getId());
          log.debug("Stowage Edit INSERT LoadableQuantity --- id : "+loadableQuantityDomain.getId());
        }
      }else{
        loadableQuantity = new LoadableQuantity();
        log.debug("Stowage Edit INSERT LoadableQuantity --- id : ");
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
      this.loadableQuantityRepository.save(loadableQuantity);
    }
    if (!loadableStudy.getLoadableStudyRuleList().isEmpty()) {
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
                          LoadableStudyRules loadableStudyRules;
                          if(rule.getId() != null){
                          Optional<LoadableStudyRules> existingLoadableStudyRules = loadableStudyRuleRepository.findById(Long.parseLong(rule.getId()));
                            if(existingLoadableStudyRules.isPresent()){
                              loadableStudyRules = existingLoadableStudyRules.get();
                            }else{
                              loadableStudyRules = new LoadableStudyRules();
                              loadableStudyRules.setId(Long.parseLong(rule.getId()));
                            }
                          }else{
                            loadableStudyRules = new LoadableStudyRules();
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
                            LoadableStudyRuleInput ruleTemplateInput;
                            if(input.getId() != null){
                              Optional<LoadableStudyRuleInput> loadableStudyRuleInput =
                                      loadableStudyRuleInputRepository.findById(Long.valueOf(input.getId()));
                              if(loadableStudyRuleInput.isPresent()){
                                ruleTemplateInput = loadableStudyRuleInput.get();
                              }else{
                                ruleTemplateInput = new LoadableStudyRuleInput();
                                ruleTemplateInput.setId(Long.valueOf(input.getId()));
                              }
                            }else{
                              ruleTemplateInput = new LoadableStudyRuleInput();
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
                          loadableStudyRules.setLoadableStudyRuleInputs(ruleVesselMappingInputList);
                          loadableStudyRulesList.add(loadableStudyRules);
                        });
              });
      List<LoadableStudyRules> updatedLoadableStudyRulesList = this.loadableStudyRuleRepository.saveAll(loadableStudyRulesList);
      log.debug("Stowage Edit no of rows updated for LoadableStudyRules  : "+updatedLoadableStudyRulesList.size());
    }
  }

   private void saveLoadablePlanStowageTempDetailsInShore(LoadabalePatternValidateRequest
   loadabalePatternValidateRequest) {
      if(!loadabalePatternValidateRequest.getLoadablePlanStowageTempDetails().isEmpty()){
        loadabalePatternValidateRequest.getLoadablePlanStowageTempDetails().forEach(stowageTemp->{
          LoadablePlanStowageDetailsTemp loadablePlanStowageDetailsTemp = new LoadablePlanStowageDetailsTemp();
          Optional<Long> isBallastExist = Optional.ofNullable(stowageTemp.getBallastDetailsId());
          if(isBallastExist.isPresent()){
            LoadablePlanBallastDetails ballastDetails =
                    this.loadablePlanBallastDetailsRepository.getOne(stowageTemp.getBallastDetailsId());
            loadablePlanStowageDetailsTemp.setLoadablePlanBallastDetails(ballastDetails);
          }
          Optional<Boolean> isCommingleExist = Optional.ofNullable(stowageTemp.getIsCommingle());
          if(isCommingleExist.isPresent()){
            LoadablePlanCommingleDetails commingleDetails =
                    this.loadablePlanCommingleDetailsRepository.getOne(stowageTemp.getCommingleDetailId());
            loadablePlanStowageDetailsTemp.setLoadablePlanCommingleDetails(commingleDetails);
          }
          Optional<Long> isStowageExist = Optional.ofNullable(stowageTemp.getStowageDetailsId());
          if(isStowageExist.isPresent()){
            LoadablePlanStowageDetails stowageDetails = this.loadablePlanStowageDetailsRespository.getOne(stowageTemp.getStowageDetailsId());
                    loadablePlanStowageDetailsTemp.setLoadablePlanStowageDetails(stowageDetails);
          }
          Optional<LoadablePattern> loadablePatternOpt = this.loadablePatternRepository.findByIdAndIsActive(stowageTemp.getLoadablePatternId(), true);
          if(loadablePatternOpt.isPresent()){
                    loadablePlanStowageDetailsTemp.setLoadablePattern(loadablePatternOpt.get());
           }
          loadablePlanStowageDetailsTemp.setIsActive(true);
          loadablePlanStowageDetailsTemp.setCorrectedUllage(
                  isEmpty(stowageTemp.getCorrectedUllage())
                          ? null
                          : stowageTemp.getCorrectedUllage());
          loadablePlanStowageDetailsTemp.setCorrectionFactor(
                  isEmpty(stowageTemp.getCorrectionFactor())
                          ? null
                          : stowageTemp.getCorrectionFactor());

          loadablePlanStowageDetailsTemp.setQuantity(
                  isEmpty(stowageTemp.getQuantity())
                          ? null
                          : stowageTemp.getQuantity());
          loadablePlanStowageDetailsTemp.setRdgUllage(
                  isEmpty(stowageTemp.getRdgUllage())
                          ? null
                          : stowageTemp.getRdgUllage());
          loadablePlanStowageDetailsTemp.setIsBallast(stowageTemp.getIsBallast());
          loadablePlanStowageDetailsTemp.setIsCommingle(stowageTemp.getIsCommingle());
          loadablePlanStowageDetailsTemp.setFillingRatio(
                  isEmpty(stowageTemp.getFillingRatio())
                          ? null
                          : stowageTemp.getFillingRatio());
          stowageDetailsTempRepository.save(loadablePlanStowageDetailsTemp);
        }
        );
      }
    }
}
