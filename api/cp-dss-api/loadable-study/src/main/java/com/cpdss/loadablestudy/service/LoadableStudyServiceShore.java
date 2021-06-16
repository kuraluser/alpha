/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static java.lang.String.valueOf;
import static org.springframework.util.StringUtils.isEmpty;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.EnvoyReaderServiceGrpc;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.loadablestudy.domain.CargoNominationOperationDetails;
import com.cpdss.loadablestudy.entity.*;
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

  @Autowired private CommingleCargoRepository commingleCargoRepository;
  @Autowired private LoadableQuantityRepository loadableQuantityRepository;
  @Autowired private LoadableStudyPortRotationRepository loadableStudyPortRotationRepository;
  @Autowired private CargoNominationRepository cargoNominationRepository;
  @Autowired private VoyageRepository voyageRepository;
  @Autowired private LoadableStudyRepository loadableStudyRepository;
  @Autowired private OnHandQuantityRepository onHandQuantityRepository;
  @Autowired private OnBoardQuantityRepository onBoardQuantityRepository;
  @Autowired private CargoOperationRepository cargoOperationRepository;

  public LoadableStudy setLoadablestudyShore(String jsonResult, String messageId)
      throws GenericServiceException {
    LoadableStudy loadableStudyEntity = null;
    com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy =
        new Gson().fromJson(jsonResult, com.cpdss.loadablestudy.domain.LoadableStudy.class);

    Voyage voyage = saveVoyageShore(loadableStudy.getVesselId(), loadableStudy.getVoyageNo());
    loadableStudyEntity = checkIfLoadableStudyExist(loadableStudy.getName(), voyage);
    if (null == loadableStudyEntity) {

      try {
        ModelMapper modelMapper = new ModelMapper();
        loadableStudyEntity = saveLoadableStudyShore(loadableStudy, voyage, messageId);
        saveLoadableStudyDataShore(loadableStudyEntity, loadableStudy, modelMapper);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return loadableStudyEntity;
  }

  private void saveLoadableStudyDataShore(
      LoadableStudy loadableStudyEntity,
      com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy,
      ModelMapper modelMapper) {

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

    List<CargoNomination> cargoNominationEntities = new ArrayList<>();
    loadableStudy.getCargoNomination().stream()
        .forEach(
            cargoNom -> {
              CargoNomination cargoNomination = new CargoNomination();
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
              ? null
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
    entity.setFuelTypeXId(null != request.getFuelTypeXId() ? request.getFuelTypeXId() : null);
    entity.setTankXId(null != request.getTankXId() ? request.getTankXId() : null);
    entity.setPortXId(null != request.getPortXId() ? request.getPortXId() : null);
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

    // entity.setDensity(isEmpty(request.getDensity()) ? null : new
    // BigDecimal(request.getDensity()));
    if (request.getPortXId() != null) {
      LoadableStudyPortRotation lsPortRot =
          loadableStudyPortRotationRepository.findByLoadableStudyAndPortXIdAndIsActive(
              entity.getLoadableStudy(), request.getPortXId(), true);
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

  private LoadableStudy checkIfLoadableStudyExist(String name, Voyage voyage) {
    LoadableStudy duplicate =
        this.loadableStudyRepository.findByVoyageAndNameIgnoreCaseAndIsActive(voyage, name, true);
    return duplicate;
  }

  private Voyage saveVoyageShore(Long vesselId, String voyageNo) {
    List<Voyage> voyageList =
        voyageRepository.findByCompanyXIdAndVesselXIdAndVoyageNoIgnoreCase(1L, vesselId, voyageNo);
    if (voyageList != null && voyageList.get(0) != null) {
      return voyageList.get(0);
    } else {
      Voyage voyage = new Voyage();
      voyage.setVoyageNo(voyageNo);
      voyage.setVesselXId(vesselId);
      voyage = voyageRepository.save(voyage);
      return voyage;
    }
  }

  private LoadableStudy saveLoadableStudyShore(
      com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy, Voyage voyage, String messageId)
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
    entity.setMessageUUID(messageId);
    /*entity.setIsCargoNominationComplete(loadableStudy.getIsCargoNominationComplete());
    entity.setIsDischargePortsComplete(loadableStudy.getIsDischargePortsComplete());
    entity.setIsObqComplete(loadableStudy.getIsObqComplete());
    entity.setIsOhqComplete(loadableStudy.getIsOhqComplete());
    entity.setIsPortsComplete(loadableStudy.getIsPortsComplete());*/
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
}
