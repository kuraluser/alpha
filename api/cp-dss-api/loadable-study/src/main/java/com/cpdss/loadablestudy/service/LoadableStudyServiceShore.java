/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static com.cpdss.loadablestudy.utility.LoadableStudiesConstants.*;
import static java.lang.String.valueOf;
import static org.apache.commons.collections4.ListUtils.emptyIfNull;
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
  @Autowired private SynopticalTableRepository synopticalTableRepository;

  @Autowired
  private CargoNominationOperationDetailsRepository cargoNominationOperationDetailsRepository;

  @Autowired
  private LoadableStudyCommunicationStatusRepository loadableStudyCommunicationStatusRepository;

  public LoadableStudy setLoadableStudyShore(String jsonResult, String messageId)
      throws GenericServiceException {
    log.info("inside setLoadablestudyShore ");
    LoadableStudy loadableStudyEntity;
    com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy =
        new Gson().fromJson(jsonResult, com.cpdss.loadablestudy.domain.LoadableStudy.class);

    Voyage voyage = saveVoyageShore(loadableStudy.getVesselId(), loadableStudy.getVoyage());
    if (!checkIfLoadableStudyExist(loadableStudy.getId(), voyage)) {

      try {

        loadableStudyEntity = saveLoadableStudyShore(loadableStudy, voyage);
        saveLoadableStudyCommunicaionStatus(messageId, loadableStudyEntity);
        saveLoadableStudyDataShore(loadableStudyEntity, loadableStudy);
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
      saveLoadableStudyDataShore(loadableStudyEntity, loadableStudy);
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

  /**
   * Method to save loadable study data at shore side
   *
   * @param loadableStudyEntity loadableStudy entity object
   * @param loadableStudyReqObj loadableStudy request object from ship
   */
  private void saveLoadableStudyDataShore(
      final LoadableStudy loadableStudyEntity,
      final com.cpdss.loadablestudy.domain.LoadableStudy loadableStudyReqObj) {

    // Save commingle cargoes
    final List<CommingleCargo> commingleEntities = new ArrayList<>();
    emptyIfNull(loadableStudyReqObj.getCommingleCargos())
        .forEach(
            commingleCargoReqObj -> {
              CommingleCargo commingleCargoEntity = buildCommingleCargoEntity(commingleCargoReqObj);
              commingleEntities.add(commingleCargoEntity);
            });
    this.commingleCargoRepository.saveAll(commingleEntities);
    // TODO check if flush is required
    this.commingleCargoRepository.flush();

    // Save cargo nomination
    List<CargoNomination> cargoNominationEntities = new ArrayList<>();
    emptyIfNull(loadableStudyReqObj.getCargoNomination())
        .forEach(
            cargoNominationReqObj -> {
              CargoNomination cargoNominationEntity =
                  buildCargoNominationEntity(
                      cargoNominationReqObj,
                      loadableStudyReqObj.getCargoNominationOperationDetails());
              cargoNominationEntities.add(cargoNominationEntity);
            });
    this.cargoNominationRepository.saveAll(cargoNominationEntities);
    this.cargoNominationRepository.flush();

    // Save loadable study port rotations
    List<LoadableStudyPortRotation> loadableStudyPortRotationEntities = new ArrayList<>();
    emptyIfNull(loadableStudyReqObj.getLoadableStudyPortRotation())
        .forEach(
            portRotationReqObj -> {
              LoadableStudyPortRotation loadableStudyPortRotationEntity =
                  buildLoadableStudyPortRotationEntity(
                      portRotationReqObj,
                      loadableStudyReqObj.getSynopticalTableDetails(),
                      loadableStudyEntity);
              loadableStudyPortRotationEntities.add(loadableStudyPortRotationEntity);
            });
    this.loadableStudyPortRotationRepository.saveAll(loadableStudyPortRotationEntities);
    this.loadableStudyPortRotationRepository.flush();

    // Save on hand quantities
    List<OnHandQuantity> onHandQuantityEntities = new ArrayList<>();
    emptyIfNull(loadableStudyReqObj.getOnHandQuantity())
        .forEach(
            onHandQuantityReqObj -> {
              OnHandQuantity onHandQuantityEntity =
                  buildOnHandQuantity(loadableStudyEntity, onHandQuantityReqObj);
              onHandQuantityEntities.add(onHandQuantityEntity);
            });
    this.onHandQuantityRepository.saveAll(onHandQuantityEntities);
    this.onHandQuantityRepository.flush();

    // Save on board quantities
    List<OnBoardQuantity> onBoardQuantityEntities = new ArrayList<>();
    emptyIfNull(loadableStudyReqObj.getOnBoardQuantity())
        .forEach(
            onBoardQuantityReqObj -> {
              OnBoardQuantity onBoardQuantityEntity =
                  buildOnBoardQuantityEntity(loadableStudyEntity, onBoardQuantityReqObj);
              onBoardQuantityEntities.add(onBoardQuantityEntity);
            });
    this.onBoardQuantityRepository.saveAll(onBoardQuantityEntities);
    this.onBoardQuantityRepository.flush();

    // Save loadable quantity
    LoadableQuantity loadableQuantity =
        buildLoadableQuantityEntity(loadableStudyReqObj.getLoadableQuantity(), loadableStudyEntity);
    if (null != loadableQuantity) {
      this.loadableQuantityRepository.save(loadableQuantity);
      this.loadableQuantityRepository.flush();
    }
  }

  // TODO remove function on refactoring if usages not found
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

  /**
   * Method to build loadable study port rotation entity
   *
   * @param portRotationReqObj port rotation request object
   * @param synopticalTableDetailsReqObj synoptical table details in request object
   * @param loadableStudyEntity loadable study entity
   * @return LoadableStudyPortRotation entity object
   */
  private LoadableStudyPortRotation buildLoadableStudyPortRotationEntity(
      com.cpdss.loadablestudy.domain.LoadableStudyPortRotation portRotationReqObj,
      List<com.cpdss.loadablestudy.domain.SynopticalTable> synopticalTableDetailsReqObj,
      LoadableStudy loadableStudyEntity) {

    // Get latest port rotation entity
    LoadableStudyPortRotation loadableStudyPortRotationEntity =
        Optional.ofNullable(
                loadableStudyPortRotationRepository.findByIdAndIsActive(
                    portRotationReqObj.getId(), true))
            .orElse(new LoadableStudyPortRotation());

    // Update id and activate
    loadableStudyPortRotationEntity.setId(portRotationReqObj.getId());
    loadableStudyPortRotationEntity.setActive(true);

    // Set loadable study data
    loadableStudyPortRotationEntity.setLoadableStudy(loadableStudyEntity);

    // Set other details
    loadableStudyPortRotationEntity.setPortXId(
        portRotationReqObj.getPortId() != null ? portRotationReqObj.getPortId() : null);
    loadableStudyPortRotationEntity.setPortOrder(
        isEmpty(portRotationReqObj.getPortOrder()) ? null : portRotationReqObj.getPortOrder());
    loadableStudyPortRotationEntity.setDistanceBetweenPorts(
        portRotationReqObj.getDistanceBetweenPorts() != null
            ? portRotationReqObj.getDistanceBetweenPorts()
            : null);

    loadableStudyPortRotationEntity.setBerthXId(
        portRotationReqObj.getBerthId() != null ? portRotationReqObj.getBerthId() : null);
    loadableStudyPortRotationEntity.setSeaWaterDensity(
        portRotationReqObj.getSeaWaterDensity() != null
            ? portRotationReqObj.getSeaWaterDensity()
            : null);

    loadableStudyPortRotationEntity.setTimeOfStay(
        isEmpty(portRotationReqObj.getTimeOfStay()) ? null : portRotationReqObj.getTimeOfStay());

    loadableStudyPortRotationEntity.setMaxDraft(
        isEmpty(portRotationReqObj.getMaxDraft()) ? null : portRotationReqObj.getMaxDraft());
    loadableStudyPortRotationEntity.setAirDraftRestriction(
        isEmpty(portRotationReqObj.getMaxAirDraft()) ? null : portRotationReqObj.getMaxAirDraft());

    loadableStudyPortRotationEntity.setEta(
        isEmpty(portRotationReqObj.getEta())
            ? null
            : LocalDateTime.parse(portRotationReqObj.getEta()));
    loadableStudyPortRotationEntity.setEtd(
        isEmpty(portRotationReqObj.getEtd())
            ? null
            : LocalDateTime.parse(portRotationReqObj.getEtd()));

    loadableStudyPortRotationEntity.setLayCanFrom(
        isEmpty(portRotationReqObj.getLayCanFrom())
            ? null
            : LocalDate.parse(portRotationReqObj.getLayCanFrom()));
    loadableStudyPortRotationEntity.setLayCanTo(
        isEmpty(portRotationReqObj.getLayCanTo())
            ? null
            : LocalDate.parse(portRotationReqObj.getLayCanTo()));

    // Get cargo operation data
    CargoOperation operation =
        cargoOperationRepository.findById(portRotationReqObj.getOperationId()).orElse(null);
    loadableStudyPortRotationEntity.setOperation(operation);
    loadableStudyPortRotationEntity.setIsPortRotationOhqComplete(true);

    // Set synoptical table details
    if (!synopticalTableDetailsReqObj.isEmpty()) {
      List<SynopticalTable> synopticalTableList =
          synopticalTableDetailsReqObj.stream()
              .filter(
                  data -> data.getLoadableStudyPortRotationId().equals(portRotationReqObj.getId()))
              .map(
                  synopticalTable -> {
                    SynopticalTable entitySynoptical =
                        synopticalTableRepository
                            .findById(synopticalTable.getId())
                            .orElse(new SynopticalTable());

                    // Set id and activate
                    entitySynoptical.setId(synopticalTable.getId());
                    entitySynoptical.setIsActive(true);

                    entitySynoptical.setLoadableStudyPortRotation(loadableStudyPortRotationEntity);
                    entitySynoptical.setPortXid(synopticalTable.getPortId());
                    entitySynoptical.setOperationType(synopticalTable.getOperationType());
                    entitySynoptical.setLoadableStudyXId(loadableStudyEntity.getId());
                    return entitySynoptical;
                  })
              .collect(Collectors.toList());

      // Update existing record and relations
      if (null != loadableStudyPortRotationEntity.getSynopticalTable()) {
        loadableStudyPortRotationEntity.getSynopticalTable().clear();
        loadableStudyPortRotationEntity.getSynopticalTable().addAll(synopticalTableList);
      } else {
        loadableStudyPortRotationEntity.setSynopticalTable(synopticalTableList);
      }
    }
    return loadableStudyPortRotationEntity;
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

  /**
   * Method to build loadable quantity entity object
   *
   * @param loadableQuantityReqObj loadable quantity request object
   * @param loadableStudyEntity loadable study entity object
   * @return LoadableQuantity loadable quantity entity object
   */
  private LoadableQuantity buildLoadableQuantityEntity(
      com.cpdss.loadablestudy.domain.LoadableQuantity loadableQuantityReqObj,
      LoadableStudy loadableStudyEntity) {

    if (null != loadableQuantityReqObj) {
      // Get latest loadable quantity entity
      LoadableQuantity loadableQuantity =
          Optional.ofNullable(
                  loadableQuantityRepository.findByIdAndIsActive(
                      loadableQuantityReqObj.getId(), true))
              .orElse(new LoadableQuantity());

      // Update id and activate
      loadableQuantity.setId(loadableQuantityReqObj.getId());
      loadableQuantity.setIsActive(true);

      // Set loadable study data
      loadableQuantity.setLoadableStudyXId(loadableStudyEntity);

      // Set other details
      loadableQuantity.setConstant(
          StringUtils.isEmpty(loadableQuantityReqObj.getConstant())
              ? null
              : new BigDecimal(loadableQuantityReqObj.getConstant()));
      loadableQuantity.setDeadWeight(
          StringUtils.isEmpty(loadableQuantityReqObj.getDeadWeight())
              ? null
              : new BigDecimal(loadableQuantityReqObj.getDeadWeight()));
      loadableQuantity.setDistanceFromLastPort(
          StringUtils.isEmpty(loadableQuantityReqObj.getDistanceFromLastPort())
              ? null
              : new BigDecimal(loadableQuantityReqObj.getDistanceFromLastPort()));

      loadableQuantity.setEstimatedDOOnBoard(
          StringUtils.isEmpty(loadableQuantityReqObj.getEstDOOnBoard())
              ? null
              : new BigDecimal(loadableQuantityReqObj.getEstDOOnBoard()));
      loadableQuantity.setEstimatedFOOnBoard(
          StringUtils.isEmpty(loadableQuantityReqObj.getEstFOOnBoard())
              ? null
              : new BigDecimal(loadableQuantityReqObj.getEstFOOnBoard()));
      loadableQuantity.setEstimatedFWOnBoard(
          StringUtils.isEmpty(loadableQuantityReqObj.getEstFreshWaterOnBoard())
              ? null
              : new BigDecimal(loadableQuantityReqObj.getEstFreshWaterOnBoard()));

      loadableQuantity.setEstimatedSagging(
          StringUtils.isEmpty(loadableQuantityReqObj.getEstSagging())
              ? null
              : new BigDecimal(loadableQuantityReqObj.getEstSagging()));

      loadableQuantity.setOtherIfAny(
          StringUtils.isEmpty(loadableQuantityReqObj.getOtherIfAny())
              ? null
              : new BigDecimal(loadableQuantityReqObj.getOtherIfAny()));
      loadableQuantity.setSaggingDeduction(
          StringUtils.isEmpty(loadableQuantityReqObj.getSaggingDeduction())
              ? null
              : new BigDecimal(loadableQuantityReqObj.getSaggingDeduction()));

      loadableQuantity.setSgCorrection(
          StringUtils.isEmpty(loadableQuantityReqObj.getSgCorrection())
              ? new BigDecimal("0.0000")
              : new BigDecimal(loadableQuantityReqObj.getSgCorrection()));

      loadableQuantity.setTotalQuantity(
          StringUtils.isEmpty(loadableQuantityReqObj.getTotalQuantity())
              ? null
              : new BigDecimal(loadableQuantityReqObj.getTotalQuantity()));
      loadableQuantity.setTpcatDraft(
          StringUtils.isEmpty(loadableQuantityReqObj.getTpc())
              ? null
              : new BigDecimal(loadableQuantityReqObj.getTpc()));

      loadableQuantity.setVesselAverageSpeed(
          StringUtils.isEmpty(loadableQuantityReqObj.getVesselAverageSpeed())
              ? null
              : new BigDecimal(loadableQuantityReqObj.getVesselAverageSpeed()));

      loadableQuantity.setPortId(
          StringUtils.isEmpty(loadableQuantityReqObj.getPortId())
              ? null
              : new BigDecimal(loadableQuantityReqObj.getPortId()));
      loadableQuantity.setBoilerWaterOnBoard(
          StringUtils.isEmpty(loadableQuantityReqObj.getBoilerWaterOnBoard())
              ? null
              : new BigDecimal(loadableQuantityReqObj.getBoilerWaterOnBoard()));
      loadableQuantity.setBallast(
          StringUtils.isEmpty(loadableQuantityReqObj.getBallast())
              ? null
              : new BigDecimal(loadableQuantityReqObj.getBallast()));
      loadableQuantity.setRunningHours(
          StringUtils.isEmpty(loadableQuantityReqObj.getRunningHours())
              ? null
              : new BigDecimal(loadableQuantityReqObj.getRunningHours()));
      loadableQuantity.setRunningDays(
          StringUtils.isEmpty(loadableQuantityReqObj.getRunningDays())
              ? null
              : new BigDecimal(loadableQuantityReqObj.getRunningDays()));
      loadableQuantity.setFoConsumptionInSZ(
          StringUtils.isEmpty(loadableQuantityReqObj.getFoConInSZ())
              ? null
              : new BigDecimal(loadableQuantityReqObj.getFoConInSZ()));
      loadableQuantity.setDraftRestriction(
          StringUtils.isEmpty(loadableQuantityReqObj.getDraftRestriction())
              ? null
              : new BigDecimal(loadableQuantityReqObj.getDraftRestriction()));

      loadableQuantity.setFoConsumptionPerDay(
          StringUtils.isEmpty(loadableQuantityReqObj.getFoConsumptionPerDay())
              ? null
              : new BigDecimal(loadableQuantityReqObj.getFoConsumptionPerDay()));

      if (loadableQuantityReqObj.getPortId() != null) {
        LoadableStudyPortRotation lsPortRot =
            loadableStudyPortRotationRepository.findByLoadableStudyAndPortXIdAndIsActive(
                loadableStudyEntity, loadableQuantityReqObj.getPortId(), true);
        loadableQuantity.setLoadableStudyPortRotation(lsPortRot);
      }
      return loadableQuantity;
    }
    return null;
  }

  /**
   * Method to build on board quantity entity object
   *
   * @param loadableStudyEntity loadable study entity object
   * @param onBoardQuantityReqObj onboard quantity request object
   * @return OnBoardQuantity entity object
   */
  private OnBoardQuantity buildOnBoardQuantityEntity(
      LoadableStudy loadableStudyEntity,
      com.cpdss.loadablestudy.domain.OnBoardQuantity onBoardQuantityReqObj) {

    // Get latest onboard quantity entity object
    OnBoardQuantity onBoardQuantityEntity =
        onBoardQuantityRepository
            .findById(onBoardQuantityReqObj.getId())
            .orElse(new OnBoardQuantity());

    // Set id and activate
    onBoardQuantityEntity.setId(onBoardQuantityReqObj.getId());
    onBoardQuantityEntity.setIsActive(true);

    // Set loadable study object
    onBoardQuantityEntity.setLoadableStudy(loadableStudyEntity);

    // Set other details
    onBoardQuantityEntity.setCargoId(
        0 == onBoardQuantityReqObj.getCargoId() ? null : onBoardQuantityReqObj.getCargoId());
    onBoardQuantityEntity.setTankId(onBoardQuantityReqObj.getTankId());
    onBoardQuantityEntity.setPortId(onBoardQuantityReqObj.getPortId());
    onBoardQuantityEntity.setVolumeInM3(onBoardQuantityReqObj.getVolume());
    onBoardQuantityEntity.setColorCode(
        isEmpty(onBoardQuantityReqObj.getColorCode())
            ? null
            : onBoardQuantityReqObj.getColorCode());

    return onBoardQuantityEntity;
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

  /**
   * Method to build on hand quantity entity object
   *
   * @param loadableStudyEntity loadable study entity object
   * @param onHandQuantityReqObj onhand quantity request object
   * @return OnHandQuantity entity object
   */
  private OnHandQuantity buildOnHandQuantity(
      final LoadableStudy loadableStudyEntity,
      final com.cpdss.loadablestudy.domain.OnHandQuantity onHandQuantityReqObj) {

    // Get latest onhand quantity entity
    OnHandQuantity onHandQuantityEntity =
        Optional.ofNullable(
                onHandQuantityRepository.findByIdAndIsActive(onHandQuantityReqObj.getId(), true))
            .orElse(new OnHandQuantity());

    // Set id and activate
    onHandQuantityEntity.setId(onHandQuantityReqObj.getId());
    onHandQuantityEntity.setIsActive(true);

    // Set loadable study data
    onHandQuantityEntity.setLoadableStudy(loadableStudyEntity);

    // Set other details
    onHandQuantityEntity.setFuelTypeXId(
        null != onHandQuantityReqObj.getFueltypeId() ? onHandQuantityReqObj.getFueltypeId() : null);
    onHandQuantityEntity.setTankXId(
        null != onHandQuantityReqObj.getTankId() ? onHandQuantityReqObj.getTankId() : null);
    onHandQuantityEntity.setPortXId(
        null != onHandQuantityReqObj.getPortId() ? onHandQuantityReqObj.getPortId() : null);

    onHandQuantityEntity.setArrivalQuantity(
        isEmpty(onHandQuantityReqObj.getArrivalQuantity())
            ? null
            : new BigDecimal(onHandQuantityReqObj.getArrivalQuantity()));
    onHandQuantityEntity.setArrivalVolume(
        isEmpty(onHandQuantityReqObj.getArrivalVolume())
            ? null
            : new BigDecimal(onHandQuantityReqObj.getArrivalVolume()));

    onHandQuantityEntity.setDepartureQuantity(
        isEmpty(onHandQuantityReqObj.getDepartureQuantity())
            ? null
            : new BigDecimal(onHandQuantityReqObj.getDepartureQuantity()));
    onHandQuantityEntity.setDepartureVolume(
        isEmpty(onHandQuantityReqObj.getDepartureVolume())
            ? null
            : new BigDecimal(onHandQuantityReqObj.getDepartureVolume()));

    onHandQuantityEntity.setDensity(
        isEmpty(onHandQuantityReqObj.getDensity())
            ? null
            : new BigDecimal(onHandQuantityReqObj.getDensity()));

    if (onHandQuantityReqObj.getPortId() != null) {
      // Set port rotation details
      LoadableStudyPortRotation lsPortRotationEntity =
          loadableStudyPortRotationRepository.findByLoadableStudyAndPortXIdAndIsActive(
              onHandQuantityEntity.getLoadableStudy(), onHandQuantityReqObj.getPortId(), true);
      onHandQuantityEntity.setPortRotation(lsPortRotationEntity);
    }
    return onHandQuantityEntity;
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

  /**
   * Method to build cargo nomination entity
   *
   * @param cargoNominationRequestObj cargo nomination request object
   * @param cargoNominationOperationDetails cargo nomination operation details
   * @return CargoNomination entity object
   */
  private CargoNomination buildCargoNominationEntity(
      com.cpdss.loadablestudy.domain.CargoNomination cargoNominationRequestObj,
      List<CargoNominationOperationDetails> cargoNominationOperationDetails) {

    // Get cargonomination details
    com.cpdss.loadablestudy.entity.CargoNomination cargoNominationEntity =
        cargoNominationRepository
            .findByIdAndIsActive(cargoNominationRequestObj.getId(), true)
            .orElse(new com.cpdss.loadablestudy.entity.CargoNomination());

    // Set id and activate
    cargoNominationEntity.setId(cargoNominationRequestObj.getId());
    cargoNominationEntity.setIsActive(true);

    cargoNominationEntity.setLoadableStudyXId(cargoNominationRequestObj.getLoadableStudyId());
    cargoNominationEntity.setPriority(cargoNominationRequestObj.getPriority());
    cargoNominationEntity.setCargoXId(cargoNominationRequestObj.getCargoId());
    cargoNominationEntity.setAbbreviation(cargoNominationRequestObj.getAbbreviation());
    cargoNominationEntity.setColor(cargoNominationRequestObj.getColor());
    cargoNominationEntity.setApi(cargoNominationRequestObj.getApi());
    cargoNominationEntity.setTemperature(cargoNominationRequestObj.getTemperature());
    cargoNominationEntity.setQuantity(
        !StringUtils.isEmpty(cargoNominationRequestObj.getQuantity())
            ? new BigDecimal(String.valueOf(cargoNominationRequestObj.getQuantity()))
            : null);
    cargoNominationEntity.setMaxTolerance(
        !StringUtils.isEmpty(cargoNominationRequestObj.getMaxTolerance())
            ? new BigDecimal(String.valueOf(cargoNominationRequestObj.getMaxTolerance()))
            : null);
    cargoNominationEntity.setMinTolerance(
        !StringUtils.isEmpty(cargoNominationRequestObj.getMinTolerance())
            ? new BigDecimal(String.valueOf(cargoNominationRequestObj.getMinTolerance()))
            : null);
    cargoNominationEntity.setSegregationXId(cargoNominationRequestObj.getSegregationId());

    // Set cargo nomination port details
    if (!cargoNominationOperationDetails.isEmpty()) {
      Set<CargoNominationPortDetails> cargoNominationPortDetailsList =
          cargoNominationOperationDetails.stream()
              .filter(
                  cargoNominationOperationDetail ->
                      cargoNominationOperationDetail
                          .getCargoNominationId()
                          .equals(cargoNominationRequestObj.getId()))
              .map(
                  cargo -> {
                    CargoNominationPortDetails cargoNominationPortDetails =
                        cargoNominationOperationDetailsRepository
                            .findById(cargo.getId())
                            .orElse(new CargoNominationPortDetails());
                    cargoNominationPortDetails.setId(cargo.getId());
                    cargoNominationPortDetails.setCargoNomination(cargoNominationEntity);
                    cargoNominationPortDetails.setPortId(cargo.getPortId());
                    cargoNominationPortDetails.setQuantity(new BigDecimal(cargo.getQuantity()));
                    cargoNominationPortDetails.setIsActive(true);
                    return cargoNominationPortDetails;
                  })
              .collect(Collectors.toSet());

      // Update references
      if (null != cargoNominationEntity.getCargoNominationPortDetails()) {
        cargoNominationEntity.getCargoNominationPortDetails().clear();
        cargoNominationEntity
            .getCargoNominationPortDetails()
            .addAll(cargoNominationPortDetailsList);
      } else {
        cargoNominationEntity.setCargoNominationPortDetails(cargoNominationPortDetailsList);
      }
    }

    return cargoNominationEntity;
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
                    CargoNominationPortDetails cargoNominationPortDetails =
                        new CargoNominationPortDetails();
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

  /**
   * Method to build commingle cargo entity
   *
   * @param commingleCargoRequestObj commingle cargo request object
   * @return commingle cargo entity object
   */
  private com.cpdss.loadablestudy.entity.CommingleCargo buildCommingleCargoEntity(
      com.cpdss.loadablestudy.domain.CommingleCargo commingleCargoRequestObj) {

    // Get commingle cargo entity
    com.cpdss.loadablestudy.entity.CommingleCargo commingleCargoEntity =
        commingleCargoRepository
            .findByIdAndIsActive(commingleCargoRequestObj.getId(), true)
            .orElse(new com.cpdss.loadablestudy.entity.CommingleCargo());

    // Set id and activate
    commingleCargoEntity.setId(commingleCargoRequestObj.getId());
    commingleCargoEntity.setIsActive(true);

    // Set loadable study data
    commingleCargoEntity.setLoadableStudyXId(commingleCargoRequestObj.getLoadableStudyXId());

    // Set other details
    commingleCargoEntity.setPurposeXid(commingleCargoRequestObj.getPurposeXid());
    commingleCargoEntity.setTankIds(commingleCargoRequestObj.getTankIds());
    commingleCargoEntity.setCargo1Xid(commingleCargoRequestObj.getCargo1Id());
    commingleCargoEntity.setCargo2Xid(commingleCargoRequestObj.getCargo2Id());
    commingleCargoEntity.setQuantity(
        !StringUtils.isEmpty(commingleCargoRequestObj.getQuantity())
            ? new BigDecimal(commingleCargoRequestObj.getQuantity())
            : null);
    commingleCargoEntity.setIsSlopOnly(commingleCargoRequestObj.getIsSlopOnly());

    // Fetch the max priority for the cargoNomination ids and set as priority for commingle
    Long maxPriority =
        cargoNominationRepository.getMaxPriorityCargoNominationIn(
            Arrays.asList(
                commingleCargoRequestObj.getCargoNomination1Id(),
                commingleCargoRequestObj.getCargoNomination2Id()));
    commingleCargoEntity.setPriority(maxPriority != null ? maxPriority.intValue() : null);

    commingleCargoEntity.setCargoNomination1Id(commingleCargoRequestObj.getCargoNomination1Id());
    commingleCargoEntity.setCargoNomination2Id(commingleCargoRequestObj.getCargoNomination2Id());

    return commingleCargoEntity;
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
    entity.setDischargeCargoNominationId(loadableStudy.getCargoToBeDischargeFirstId());
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

  public LoadableStudy saveOrUpdateLSInShore(String jsonResult, String messageId)
      throws GenericServiceException {
    LoadableStudy loadableStudyEntity = null;
    LoadabalePatternValidateRequest loadabalePatternValidateRequest =
        new Gson()
            .fromJson(
                jsonResult, com.cpdss.loadablestudy.domain.LoadabalePatternValidateRequest.class);
    com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy =
        loadabalePatternValidateRequest.getLoadableStudy();
    Voyage voyage = saveVoyageShore(loadableStudy.getVesselId(), loadableStudy.getVoyage());
    if (!checkIfLoadableStudyExist(loadableStudy.getId(), voyage)) {
      try {
        loadableStudyEntity = saveLoadableStudyShore(loadableStudy, voyage);
        saveLoadableStudyCommunicaionStatus(messageId, loadableStudyEntity);
        ModelMapper modelMapper = new ModelMapper();
        saveLoadableStudyDataShore(loadableStudyEntity, loadableStudy, modelMapper);
        return loadableStudyEntity;
      } catch (IOException e) {
        log.error("Saving loadable study attachment failed: {}", loadableStudy, e);
        throw new GenericServiceException(
            "Saving loadable study attachment failed: " + loadableStudy,
            CommonErrorCodes.E_CPDSS_FILE_WRITE_ERROR,
            HttpStatusCode.INTERNAL_SERVER_ERROR,
            e);
      }
    } else {
      log.error("Loadable study does not exist: {}", loadableStudy);
      throw new GenericServiceException(
          "Loadable study does not exist: " + loadableStudy,
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
  }

  // private void saveLoadablePlanStowageTempDetails(LoadabalePatternValidateRequest
  // loadabalePatternValidateRequest) {
  //    if(loadabalePatternValidateRequest.getLoadablePlanStowageTempDetails() != null){
  //      loadabalePatternValidateRequest.getLoadablePlanStowageTempDetails().forEach(stowageTemp->{
  //                LoadablePlanStowageDetailsTemp loadablePlanStowageDetailsTemp = new
  // LoadablePlanStowageDetailsTemp();
  //                Optional<Long> isBallastExist =
  // Optional.ofNullable(stowageTemp.getBallastDetailsId());
  //                if(isBallastExist.isPresent()){
  //                  LoadablePlanBallastDetails ballastDetails =
  //
  // this.loadablePlanBallastDetailsRepository.getOne(stowageTemp.getBallastDetailsId());
  //                  loadablePlanStowageDetailsTemp.setLoadablePlanBallastDetails(ballastDetails);
  //                }
  //                Optional<Boolean> isCommingleExist =
  // Optional.ofNullable(stowageTemp.getIsCommingle());
  //                if(isCommingleExist.isPresent()){
  //                  LoadablePlanCommingleDetails commingleDetails =
  //
  // this.loadablePlanCommingleDetailsRepository.getOne(stowageTemp.getCommingleDetailId());
  //
  // loadablePlanStowageDetailsTemp.setLoadablePlanCommingleDetails(commingleDetails);
  //                }
  //                Optional<Long> isStowageExist =
  // Optional.ofNullable(stowageTemp.getStowageDetailsId());
  //                if(isStowageExist.isPresent()){
  //                  LoadablePlanStowageDetails stowageDetails =
  //
  // this.loadablePlanStowageDetailsRespository.getOne(stowageTemp.getStowageDetailsId());
  //                  loadablePlanStowageDetailsTemp.setLoadablePlanStowageDetails(stowageDetails);
  //                }
  //                Optional<LoadablePattern> loadablePatternOpt =
  //
  // this.loadablePatternRepository.findByIdAndIsActive(stowageTemp.getLoadablePatternId(), true);
  //                if(loadablePatternOpt.isPresent()){
  //                  loadablePlanStowageDetailsTemp.setLoadablePattern(loadablePatternOpt.get());
  //                }
  //                loadablePlanStowageDetailsTemp.setIsActive(true);
  //                loadablePlanStowageDetailsTemp.setCorrectedUllage(
  //                        isEmpty(stowageTemp.getCorrectedUllage())
  //                                ? null
  //                                : stowageTemp.getCorrectedUllage());
  //                loadablePlanStowageDetailsTemp.setCorrectionFactor(
  //                        isEmpty(stowageTemp.getCorrectionFactor())
  //                                ? null
  //                                : stowageTemp.getCorrectionFactor());
  //
  //                loadablePlanStowageDetailsTemp.setQuantity(
  //                        isEmpty(stowageTemp.getQuantity())
  //                                ? null
  //                                : stowageTemp.getQuantity());
  //                loadablePlanStowageDetailsTemp.setRdgUllage(
  //                        isEmpty(stowageTemp.getRdgUllage())
  //                                ? null
  //                                : stowageTemp.getRdgUllage());
  //                loadablePlanStowageDetailsTemp.setIsBallast(stowageTemp.getIsBallast());
  //                loadablePlanStowageDetailsTemp.setIsCommingle(stowageTemp.getIsCommingle());
  //                loadablePlanStowageDetailsTemp.setFillingRatio(
  //                        isEmpty(stowageTemp.getFillingRatio())
  //                                ? null
  //                                : stowageTemp.getFillingRatio());
  //                stowageDetailsTempRepository.save(loadablePlanStowageDetailsTemp);
  //              }
  //      );
  //    }
  //  }
}
