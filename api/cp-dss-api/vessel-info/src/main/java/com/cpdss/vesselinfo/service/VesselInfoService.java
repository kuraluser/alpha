/* Licensed under Apache-2.0 */
package com.cpdss.vesselinfo.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.VesselInfo.BMAndSF;
import com.cpdss.common.generated.VesselInfo.BendingMoment;
import com.cpdss.common.generated.VesselInfo.CalculationSheet;
import com.cpdss.common.generated.VesselInfo.CalculationSheetTankGroup;
import com.cpdss.common.generated.VesselInfo.HydrostaticData;
import com.cpdss.common.generated.VesselInfo.InnerBulkHeadSF;
import com.cpdss.common.generated.VesselInfo.LoadLineDetail;
import com.cpdss.common.generated.VesselInfo.MinMaxValuesForBMAndSf;
import com.cpdss.common.generated.VesselInfo.ShearingForce;
import com.cpdss.common.generated.VesselInfo.StationValues;
import com.cpdss.common.generated.VesselInfo.VesselAlgoReply;
import com.cpdss.common.generated.VesselInfo.VesselAlgoRequest;
import com.cpdss.common.generated.VesselInfo.VesselDetail;
import com.cpdss.common.generated.VesselInfo.VesselDetail.Builder;
import com.cpdss.common.generated.VesselInfo.VesselLoadableQuantityDetails;
import com.cpdss.common.generated.VesselInfo.VesselReply;
import com.cpdss.common.generated.VesselInfo.VesselRequest;
import com.cpdss.common.generated.VesselInfo.VesselTankDetail;
import com.cpdss.common.generated.VesselInfo.VesselTankTCG;
import com.cpdss.common.generated.VesselInfoServiceGrpc.VesselInfoServiceImplBase;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.vesselinfo.domain.VesselDetails;
import com.cpdss.vesselinfo.entity.CalculationSheetTankgroup;
import com.cpdss.vesselinfo.entity.DraftCondition;
import com.cpdss.vesselinfo.entity.HydrostaticTable;
import com.cpdss.vesselinfo.entity.InnerBulkHeadValues;
import com.cpdss.vesselinfo.entity.MinMaxValuesForBmsf;
import com.cpdss.vesselinfo.entity.TankCategory;
import com.cpdss.vesselinfo.entity.Vessel;
import com.cpdss.vesselinfo.entity.VesselChartererMapping;
import com.cpdss.vesselinfo.entity.VesselDraftCondition;
import com.cpdss.vesselinfo.entity.VesselTank;
import com.cpdss.vesselinfo.entity.VesselTankTcg;
import com.cpdss.vesselinfo.repository.BendingMomentRepository;
import com.cpdss.vesselinfo.repository.CalculationSheetRepository;
import com.cpdss.vesselinfo.repository.CalculationSheetTankgroupRepository;
import com.cpdss.vesselinfo.repository.HydrostaticTableRepository;
import com.cpdss.vesselinfo.repository.InnerBulkHeadValuesRepository;
import com.cpdss.vesselinfo.repository.MinMaxValuesForBmsfRepository;
import com.cpdss.vesselinfo.repository.ShearingForceRepository;
import com.cpdss.vesselinfo.repository.StationValuesRepository;
import com.cpdss.vesselinfo.repository.TankCategoryRepository;
import com.cpdss.vesselinfo.repository.VesselChartererMappingRepository;
import com.cpdss.vesselinfo.repository.VesselDraftConditionRepository;
import com.cpdss.vesselinfo.repository.VesselRepository;
import com.cpdss.vesselinfo.repository.VesselTankRepository;
import com.cpdss.vesselinfo.repository.VesselTankTcgRepository;
import io.grpc.stub.StreamObserver;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Vessel info grpc service class
 *
 * @author suhail.k
 */
@GrpcService
@Log4j2
@Transactional
public class VesselInfoService extends VesselInfoServiceImplBase {

  @Autowired private VesselRepository vesselRepository;
  @Autowired private VesselChartererMappingRepository chartererMappingRepository;
  @Autowired private TankCategoryRepository tankCategoryRepository;
  @Autowired private VesselTankRepository vesselTankRepository;
  @Autowired private HydrostaticTableRepository hydrostaticTableRepository;
  @Autowired private VesselDraftConditionRepository vesselDraftConditionRepository;
  @Autowired private VesselTankTcgRepository vesselTankTcgRepository;
  @Autowired private BendingMomentRepository bendingMomentRepository;
  @Autowired private ShearingForceRepository shearingForceRepository;
  @Autowired private CalculationSheetRepository calculationSheetRepository;
  @Autowired private CalculationSheetTankgroupRepository calculationSheetTankgroupRepository;
  @Autowired private MinMaxValuesForBmsfRepository minMaxValuesForBmsfRepository;
  @Autowired private StationValuesRepository stationValuesRepository;
  @Autowired private InnerBulkHeadValuesRepository innerBulkHeadValuesRepository;

  private static final String SUCCESS = "SUCCESS";
  private static final String FAILED = "FAILED";

  private static final Long CARGO_TANK_CATEGORY_ID = 1L;
  private static final Long CARGO_SLOP_TANK_CATEGORY_ID = 9L;
  private static final String INVALID_VESSEL_ID = "INVALID_VESSEL_ID";

  private static final List<Long> CARGO_TANK_CATEGORIES =
      Arrays.asList(CARGO_TANK_CATEGORY_ID, CARGO_SLOP_TANK_CATEGORY_ID);

  /** Get vessel for a company */
  @Override
  public void getAllVesselsByCompany(
      VesselRequest request, StreamObserver<VesselReply> responseObserver) {
    log.info("inside grpc service: getAllVesselsByCompany");
    VesselReply.Builder replyBuilder = VesselReply.newBuilder();
    try {
      List<Vessel> vesselEntities = this.vesselRepository.findByIsActive(true);
      for (Vessel entity : vesselEntities) {
        VesselDetail.Builder builder = VesselDetail.newBuilder();
        builder.setId(entity.getId());
        Optional.ofNullable(entity.getChiefofficerId()).ifPresent(builder::setCheifOfficerId);
        Optional.ofNullable(entity.getImoNumber()).ifPresent(builder::setImoNumber);
        Optional.ofNullable(entity.getMasterId()).ifPresent(builder::setCaptainId);
        Optional.ofNullable(entity.getName()).ifPresent(builder::setName);
        Optional.ofNullable(entity.getVesselFlag())
            .ifPresent(flag -> builder.setFlag(flag.getFlagImagePath()));
        Set<VesselDraftCondition> draftConditions = entity.getVesselDraftConditionCollection();

        TreeMap<Long, TreeSet<BigDecimal>> map = new TreeMap<>();
        Comparator<BigDecimal> draftMarkComparator =
            (BigDecimal b1, BigDecimal b2) -> b2.compareTo(b1);
        draftConditions.forEach(
            condition -> {
              if (condition.getDraftCondition().getIsActive()) {
                if (null == map.get(condition.getDraftCondition().getId())) {
                  TreeSet<BigDecimal> set = new TreeSet<>(draftMarkComparator);
                  set.add(condition.getDraftExtreme());
                  map.put(condition.getDraftCondition().getId(), set);
                } else {
                  map.get(condition.getDraftCondition().getId()).add(condition.getDraftExtreme());
                }
              }
            });

        List<LoadLineDetail.Builder> builderList = new ArrayList<>();
        for (Map.Entry<Long, TreeSet<BigDecimal>> entry : map.entrySet()) {
          Optional<VesselDraftCondition> condition =
              draftConditions.stream()
                  .filter(d -> d.getDraftCondition().getId().equals(entry.getKey()))
                  .findFirst();
          if (condition.isPresent()) {
            DraftCondition draftCondition = condition.get().getDraftCondition();
            LoadLineDetail.Builder loadLineBuilder = LoadLineDetail.newBuilder();
            loadLineBuilder.setId(draftCondition.getId());
            loadLineBuilder.setName(draftCondition.getName());
            map.get(draftCondition.getId())
                .forEach(
                    draftExtreme -> {
                      loadLineBuilder.addDraftMarks(String.valueOf(draftExtreme));
                    });
            builderList.add(loadLineBuilder);
          }
        }

        builder.addAllLoadLines(
            builderList.stream().map(LoadLineDetail.Builder::build).collect(Collectors.toList()));
        VesselChartererMapping vesselChartererMapping =
            this.chartererMappingRepository.findByVesselAndIsActive(entity, true);
        if (null != vesselChartererMapping && null != vesselChartererMapping.getCharterer()) {
          builder.setCharterer(vesselChartererMapping.getCharterer().getName());
        }
        replyBuilder.addVessels(builder.build());
        replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
      }

    } catch (Exception e) {
      log.error("Exception when fetching vessel info", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage(null != e.getMessage() ? e.getMessage() : "")
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
  public void getVesselDetailsById(
      VesselRequest request, StreamObserver<VesselReply> responseObserver) {
    VesselReply.Builder replyBuilder = VesselReply.newBuilder();
    try {
      log.info("inside grpc service: getVesselDetailsById");
      VesselDetails vesselDetails =
          vesselRepository.findVesselDetailsById(
              request.getVesselId(),
              request.getVesselDraftConditionId(),
              new BigDecimal(request.getDraftExtreme()));

      VesselLoadableQuantityDetails.Builder builder = VesselLoadableQuantityDetails.newBuilder();
      List<BigDecimal> tpc =
          hydrostaticTableRepository.getTPCFromDraf(
              request.getVesselId(), new BigDecimal(request.getDraftExtreme()), true);
      if (null != vesselDetails) {
        Optional.ofNullable(vesselDetails.getDisplacmentDraftRestriction())
            .ifPresent(item -> builder.setDisplacmentDraftRestriction(item.toString()));
        Optional.ofNullable(vesselDetails.getVesselLightWeight())
            .ifPresent(item -> builder.setVesselLightWeight(item.toString()));
        Optional.ofNullable(vesselDetails.getDeadWeight())
            .ifPresent(dwt -> builder.setDwt(dwt.toString()));
        Optional.ofNullable(vesselDetails.getDraftConditionName())
            .ifPresent(builder::setDraftConditionName);
        Optional.ofNullable(vesselDetails.getConstant())
            .ifPresent(constant -> builder.setConstant(constant.toString()));
      }
      if (!tpc.isEmpty()) {
        Optional.ofNullable(tpc.get(0))
            .ifPresent(tpcValue -> builder.setTpc(String.valueOf(tpcValue)));
      }
      replyBuilder.setVesselLoadableQuantityDetails(builder.build());
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder().setStatus(SUCCESS).setMessage(SUCCESS).build());
    } catch (Exception e) {
      log.error("Exception when fetching vessel details by id ", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage(FAILED)
              .setStatus(FAILED)
              .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /** Get fuel tanks for a vessel by its id */
  @Override
  public void getVesselTanks(VesselRequest request, StreamObserver<VesselReply> responseObserver) {
    VesselReply.Builder replyBuilder = VesselReply.newBuilder();
    try {
      List<VesselTankDetail> tankList =
          this.findVesselTanksByCategory(request.getVesselId(), request.getTankCategoriesList());
      if (null != tankList && !tankList.isEmpty()) {
        replyBuilder.addAllVesselTanks(tankList);
      }
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when fetching fuel tanks", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(e.getCode())
              .setMessage("GenericServiceException when fetching fuel tanks")
              .setStatus(FAILED)
              .build());
    } catch (Exception e) {
      log.error("Exception when fetching fuel tanks", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Exception when fetching fuel tanks")
              .setStatus(FAILED)
              .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * find tanks based on vessel and category
   *
   * @param vesselId
   * @param tankCategoryIds
   * @return
   * @throws GenericServiceException
   */
  public List<VesselTankDetail> findVesselTanksByCategory(Long vesselId, List<Long> tankCategoryIds)
      throws GenericServiceException {
    Vessel vessel = this.vesselRepository.findByIdAndIsActive(vesselId, true);
    if (null == vessel) {
      throw new GenericServiceException(
          "Vessel with given id does not exist",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    List<TankCategory> tankCategoryEntities = new ArrayList<>();
    tankCategoryIds.forEach(
        tankCategoryId ->
            tankCategoryEntities.add(this.tankCategoryRepository.getOne(tankCategoryId)));
    List<VesselTank> vesselTanks =
        this.vesselTankRepository.findByVesselAndTankCategoryInAndIsActive(
            vessel, tankCategoryEntities, true);
    List<VesselTankDetail> tankDetailsList = new ArrayList<>();
    for (VesselTank tank : vesselTanks) {
      VesselTankDetail.Builder builder = VesselTankDetail.newBuilder();
      builder.setTankId(tank.getId());
      builder.setTankCategoryId(tank.getTankCategory().getId());
      builder.setTankCategoryName(tank.getTankCategory().getName());
      builder.setShortName(tank.getShortName());
      builder.setFrameNumberFrom(tank.getFrameNumberFrom());
      builder.setFrameNumberTo(tank.getFrameNumberTo());
      builder.setTankName(tank.getTankName());
      Optional.ofNullable(tank.getHeightFrom()).ifPresent(builder::setHeightFrom);
      Optional.ofNullable(tank.getHeightTo()).ifPresent(builder::setHeightTo);
      Optional.ofNullable(tank.getFillCapacityCubm())
          .ifPresent(
              capacity -> builder.setFillCapacityCubm(String.valueOf(tank.getFillCapacityCubm())));
      Optional.ofNullable(tank.getDensity())
          .ifPresent(density -> builder.setDensity(String.valueOf(tank.getDensity())));
      Optional.ofNullable(tank.getTankGroup()).ifPresent(builder::setTankGroup);
      Optional.ofNullable(tank.getTankOrder()).ifPresent(builder::setTankOrder);
      Optional.ofNullable(tank.getIsSlopTank()).ifPresent(builder::setIsSlopTank);
      Optional.ofNullable(tank.getTankCategory().getColorCode()).ifPresent(builder::setColourCode);
      Optional.ofNullable(tank.getFullCapacityCubm())
          .ifPresent(
              capacity -> builder.setFullCapacityCubm(String.valueOf(tank.getFullCapacityCubm())));
      tankDetailsList.add(builder.build());
    }
    return tankDetailsList;
  }

  /** Retrieve vessel cargo tanks for a vessel-id */
  @Override
  public void getVesselCargoTanks(
      VesselRequest request, StreamObserver<VesselReply> responseObserver) {
    VesselReply.Builder replyBuilder = VesselReply.newBuilder();
    try {
      Vessel vesselEntity = this.vesselRepository.findByIdAndIsActive(request.getVesselId(), true);
      if (vesselEntity == null) {
        throw new GenericServiceException(
            "Vessel with given id does not exist",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      List<TankCategory> tankCategoryEntities = new ArrayList<>();
      CARGO_TANK_CATEGORIES.forEach(
          tankCategoryId ->
              tankCategoryEntities.add(this.tankCategoryRepository.getOne(tankCategoryId)));
      List<VesselTank> vesselTanks =
          this.vesselTankRepository.findByVesselAndTankCategoryInAndIsActive(
              vesselEntity, tankCategoryEntities, true);
      for (VesselTank tank : vesselTanks) {
        VesselTankDetail.Builder builder = VesselTankDetail.newBuilder();
        builder.setTankId(tank.getId());
        builder.setTankName(tank.getTankName());
        builder.setShortName(tank.getShortName());
        //	        builder.setTankCategoryId(tank.getTankCategory().getId());
        //	        builder.setTankCategoryName(tank.getTankCategory().getName());
        //	        builder.setFrameNumberFrom(tank.getFrameNumberFrom());
        //	        builder.setFrameNumberTo(tank.getFrameNumberTo());
        replyBuilder.addVesselTanks(builder.build());
      }
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when fetching cargo tanks", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(e.getCode())
              .setMessage("GenericServiceException when fetching cargo tanks")
              .setStatus(FAILED)
              .build());
    } catch (Exception e) {
      log.error("Exception when fetching cargo tanks", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Exception when fetching cargo tanks")
              .setStatus(FAILED)
              .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void getVesselDetailsForAlgo(
      VesselAlgoRequest request, StreamObserver<VesselAlgoReply> responseObserver) {
    VesselAlgoReply.Builder replyBuilder = VesselAlgoReply.newBuilder();
    try {
      Vessel vessel = this.vesselRepository.findByIdAndIsActive(request.getVesselId(), true);
      if (vessel == null) {
        log.info(INVALID_VESSEL_ID, request.getVesselId());
        replyBuilder.setResponseStatus(
            ResponseStatus.newBuilder()
                .setStatus(FAILED)
                .setMessage(INVALID_VESSEL_ID)
                .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST));
      } else {
        VesselDetail.Builder vesselDetailBuilder = VesselDetail.newBuilder();
        replyBuilder.setVesselDetail(createVesseDetails(vessel, vesselDetailBuilder));

        com.cpdss.common.generated.VesselInfo.VesselDraftCondition.Builder
            vesselDraftConditionBuilder =
                com.cpdss.common.generated.VesselInfo.VesselDraftCondition.newBuilder();
        vesselDraftConditionRepository
            .findByVesselAndIsActive(vessel, true)
            .forEach(
                vesselDraftCondition -> {
                  replyBuilder.addVesselDraftCondition(
                      createVesselHydrostaticData(
                          vesselDraftCondition, vesselDraftConditionBuilder));
                });

        VesselTankDetail.Builder vesselTankBuilder = VesselTankDetail.newBuilder();
        vesselTankRepository
            .findByVesselAndIsActive(vessel, true)
            .forEach(
                vesselTank -> {
                  replyBuilder.addVesselTankDetail(
                      createVesselTankData(vesselTank, vesselTankBuilder));
                });

        HydrostaticData.Builder hydrostaticDataBuilder = HydrostaticData.newBuilder();
        hydrostaticTableRepository
            .findByVesselAndIsActive(vessel, true)
            .forEach(
                hydrostaticTable -> {
                  replyBuilder.addHydrostaticData(
                      createhydrostaticTableData(hydrostaticTable, hydrostaticDataBuilder));
                });

        VesselTankTCG.Builder vesselTankTcgBuilder = VesselTankTCG.newBuilder();
        vesselTankTcgRepository
            .findByVesselIdAndIsActive(vessel.getId(), true)
            .forEach(
                vesselTankTcg -> {
                  replyBuilder.addVesselTankTCG(
                      createVesselTankTcg(vesselTankTcg, vesselTankTcgBuilder));
                });

        BMAndSF.Builder bMAndSFBuilder = BMAndSF.newBuilder();
        bendingMomentRepository
            .findByVessel(vessel)
            .forEach(
                bendingMoment -> {
                  BendingMoment.Builder bendingMomentBuilder = BendingMoment.newBuilder();
                  bMAndSFBuilder.addBendingMoment(
                      createBendingMoment(bendingMoment, bendingMomentBuilder));
                });

        shearingForceRepository
            .findByVessel(vessel)
            .forEach(
                shearingForce -> {
                  ShearingForce.Builder shearingForceBuilder = ShearingForce.newBuilder();
                  bMAndSFBuilder.addShearingForce(
                      createShearingForce(shearingForce, shearingForceBuilder));
                });

        calculationSheetRepository
            .findByVessel(vessel)
            .forEach(
                calculationSheet -> {
                  CalculationSheet.Builder calculationSheetBuilder = CalculationSheet.newBuilder();
                  bMAndSFBuilder.addCalculationSheet(
                      createCalculationSheet(calculationSheet, calculationSheetBuilder));
                });

        calculationSheetTankgroupRepository
            .findByVessel(vessel)
            .forEach(
                calculationSheetTankgroup -> {
                  CalculationSheetTankGroup.Builder calculationSheetTankGroupBuilder =
                      CalculationSheetTankGroup.newBuilder();
                  bMAndSFBuilder.addCalculationSheetTankGroup(
                      createCalculationSheetTankGroup(
                          calculationSheetTankgroup, calculationSheetTankGroupBuilder));
                });

        minMaxValuesForBmsfRepository
            .findByVessel(vessel)
            .forEach(
                minMaxValuesForBmsf -> {
                  MinMaxValuesForBMAndSf.Builder minMaxValuesForBMAndSfBuilder =
                      MinMaxValuesForBMAndSf.newBuilder();
                  bMAndSFBuilder.addMinMaxValuesForBMAndSf(
                      createMinMaxValuesForBMAndSfBuilder(
                          minMaxValuesForBmsf, minMaxValuesForBMAndSfBuilder));
                });

        stationValuesRepository
            .findByVesselId(vessel.getId())
            .forEach(
                stationValue -> {
                  StationValues.Builder stationValueBuilder = StationValues.newBuilder();
                  bMAndSFBuilder.addStationValues(
                      createStationValueBuilder(stationValue, stationValueBuilder));
                });

        innerBulkHeadValuesRepository
            .findByVesselId(vessel.getId())
            .forEach(
                innerBulkHeadSF -> {
                  InnerBulkHeadSF.Builder innerBulkHeadSFBuilder = InnerBulkHeadSF.newBuilder();
                  bMAndSFBuilder.addInnerBulkHeadSF(
                      createInnerBulkHeadSFBuilder(innerBulkHeadSF, innerBulkHeadSFBuilder));
                });

        replyBuilder.setBMAndSF(bMAndSFBuilder);
        replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
      }
    } catch (Exception e) {
      log.error("Exception when fetching vessel details", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Exception when fetching vessel details")
              .setStatus(FAILED)
              .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * @param innerBulkHeadSF
   * @param innerBulkHeadSFBuilder
   * @return InnerBulkHeadSF
   */
  private InnerBulkHeadSF createInnerBulkHeadSFBuilder(
      InnerBulkHeadValues innerBulkHeadSF,
      com.cpdss.common.generated.VesselInfo.InnerBulkHeadSF.Builder innerBulkHeadSFBuilder) {
    innerBulkHeadSFBuilder.setId(innerBulkHeadSF.getId());
    Optional.ofNullable(innerBulkHeadSF.getFrameNumber())
        .ifPresent(
            frameNumber -> innerBulkHeadSFBuilder.setFrameNumber(String.valueOf(frameNumber)));
    Optional.ofNullable(innerBulkHeadSF.getForeAlpha())
        .ifPresent(foreAlpha -> innerBulkHeadSFBuilder.setForeAlpha(String.valueOf(foreAlpha)));
    Optional.ofNullable(innerBulkHeadSF.getForeCenterCargotankId())
        .ifPresent(
            foreCenterCargoTankId ->
                innerBulkHeadSFBuilder.setForeCenterCargoTankId(
                    Long.valueOf(foreCenterCargoTankId.toString())));
    Optional.ofNullable(innerBulkHeadSF.getForeC1())
        .ifPresent(foreC1 -> innerBulkHeadSFBuilder.setForeC1(String.valueOf(foreC1)));

    Optional.ofNullable(innerBulkHeadSF.getForeWingTankId())
        .ifPresent(
            foreWingTankIds ->
                innerBulkHeadSFBuilder.setForeWingTankIds(String.valueOf(foreWingTankIds)));

    Optional.ofNullable(innerBulkHeadSF.getForeC2())
        .ifPresent(foreC2 -> innerBulkHeadSFBuilder.setForeC2(String.valueOf(foreC2)));

    Optional.ofNullable(innerBulkHeadSF.getForeBallastTank())
        .ifPresent(
            foreBallastTanks ->
                innerBulkHeadSFBuilder.setForeBallastTanks(String.valueOf(foreBallastTanks)));

    Optional.ofNullable(innerBulkHeadSF.getForeC3())
        .ifPresent(foreC3 -> innerBulkHeadSFBuilder.setForeC3(String.valueOf(foreC3)));

    Optional.ofNullable(innerBulkHeadSF.getForeBwCorrection())
        .ifPresent(
            foreBWCorrection ->
                innerBulkHeadSFBuilder.setForeBWCorrection(String.valueOf(foreBWCorrection)));

    Optional.ofNullable(innerBulkHeadSF.getForeC4())
        .ifPresent(foreC4 -> innerBulkHeadSFBuilder.setForeC4(String.valueOf(foreC4)));

    Optional.ofNullable(innerBulkHeadSF.getForeMaxAllowence())
        .ifPresent(
            foreMaxAllowence ->
                innerBulkHeadSFBuilder.setForeMaxAllowence(String.valueOf(foreMaxAllowence)));

    Optional.ofNullable(innerBulkHeadSF.getForeMinAllowence())
        .ifPresent(
            foreMinAllowence ->
                innerBulkHeadSFBuilder.setForeMinAllowence(String.valueOf(foreMinAllowence)));

    Optional.ofNullable(innerBulkHeadSF.getAftAlpha())
        .ifPresent(aftAlpha -> innerBulkHeadSFBuilder.setAftAlpha(String.valueOf(aftAlpha)));
    Optional.ofNullable(innerBulkHeadSF.getAftCenterCargotankId())
        .ifPresent(
            aftCenterCargoTankId ->
                innerBulkHeadSFBuilder.setAftCenterCargoTankId(
                    Long.valueOf(aftCenterCargoTankId.toString())));
    Optional.ofNullable(innerBulkHeadSF.getAftC1())
        .ifPresent(aftC1 -> innerBulkHeadSFBuilder.setAftC1(String.valueOf(aftC1)));

    Optional.ofNullable(innerBulkHeadSF.getAftWingTankId())
        .ifPresent(
            aftWingTankIds ->
                innerBulkHeadSFBuilder.setAftWingTankIds(String.valueOf(aftWingTankIds)));

    Optional.ofNullable(innerBulkHeadSF.getAftC2())
        .ifPresent(aftC2 -> innerBulkHeadSFBuilder.setAftC2(String.valueOf(aftC2)));

    Optional.ofNullable(innerBulkHeadSF.getAftBallastTank())
        .ifPresent(
            aftBallastTanks ->
                innerBulkHeadSFBuilder.setAftBallastTanks(String.valueOf(aftBallastTanks)));

    Optional.ofNullable(innerBulkHeadSF.getAftC3())
        .ifPresent(aftC3 -> innerBulkHeadSFBuilder.setAftC3(String.valueOf(aftC3)));

    Optional.ofNullable(innerBulkHeadSF.getAftBwCorrection())
        .ifPresent(
            aftBWCorrection ->
                innerBulkHeadSFBuilder.setAftBWCorrection(String.valueOf(aftBWCorrection)));

    Optional.ofNullable(innerBulkHeadSF.getAftC4())
        .ifPresent(aftC4 -> innerBulkHeadSFBuilder.setAftC4(String.valueOf(aftC4)));

    Optional.ofNullable(innerBulkHeadSF.getAftMaxFlAllowence())
        .ifPresent(
            aftMaxFlAllowence ->
                innerBulkHeadSFBuilder.setAftMaxFlAllowence(String.valueOf(aftMaxFlAllowence)));

    Optional.ofNullable(innerBulkHeadSF.getAftMinFlAllowence())
        .ifPresent(
            aftMinFlAllowence ->
                innerBulkHeadSFBuilder.setAftMinFlAllowence(String.valueOf(aftMinFlAllowence)));

    return innerBulkHeadSFBuilder.build();
  }

  /**
   * @param stationValue
   * @param stationValueBuilder
   * @return StationValues
   */
  private StationValues createStationValueBuilder(
      com.cpdss.vesselinfo.entity.StationValues stationValue,
      com.cpdss.common.generated.VesselInfo.StationValues.Builder stationValueBuilder) {
    stationValueBuilder.setId(stationValue.getId());
    Optional.ofNullable(stationValue.getFrameNumberFrom())
        .ifPresent(
            frameNumberFrom ->
                stationValueBuilder.setFrameNumberFrom(String.valueOf(frameNumberFrom)));
    Optional.ofNullable(stationValue.getFrameNumberTo())
        .ifPresent(
            frameNumberTo -> stationValueBuilder.setFrameNumberTo(String.valueOf(frameNumberTo)));
    Optional.ofNullable(stationValue.getStattionFrom())
        .ifPresent(stationFrom -> stationValueBuilder.setStationFrom(String.valueOf(stationFrom)));
    Optional.ofNullable(stationValue.getStationTo())
        .ifPresent(stationTo -> stationValueBuilder.setStationTo(String.valueOf(stationTo)));
    Optional.ofNullable(stationValue.getDistance())
        .ifPresent(distance -> stationValueBuilder.setDistance(String.valueOf(distance)));
    return stationValueBuilder.build();
  }

  /**
   * @param minMaxValuesForBmsf
   * @param minMaxValuesForBMAndSfBuilder
   * @return MinMaxValuesForBMAndSf
   */
  private MinMaxValuesForBMAndSf createMinMaxValuesForBMAndSfBuilder(
      MinMaxValuesForBmsf minMaxValuesForBmsf,
      com.cpdss.common.generated.VesselInfo.MinMaxValuesForBMAndSf.Builder
          minMaxValuesForBMAndSfBuilder) {
    minMaxValuesForBMAndSfBuilder.setId(minMaxValuesForBmsf.getId());
    Optional.ofNullable(minMaxValuesForBmsf.getFrameNumber())
        .ifPresent(
            frameNumber ->
                minMaxValuesForBMAndSfBuilder.setFrameNumber(String.valueOf(frameNumber)));
    Optional.ofNullable(minMaxValuesForBmsf.getMinBm())
        .ifPresent(minBm -> minMaxValuesForBMAndSfBuilder.setMinBm(String.valueOf(minBm)));

    Optional.ofNullable(minMaxValuesForBmsf.getMaxBm())
        .ifPresent(maxBm -> minMaxValuesForBMAndSfBuilder.setMaxBm(String.valueOf(maxBm)));

    Optional.ofNullable(minMaxValuesForBmsf.getMinSf())
        .ifPresent(minSf -> minMaxValuesForBMAndSfBuilder.setMinSf(String.valueOf(minSf)));

    Optional.ofNullable(minMaxValuesForBmsf.getMaxSf())
        .ifPresent(maxSf -> minMaxValuesForBMAndSfBuilder.setMaxSf(String.valueOf(maxSf)));

    return minMaxValuesForBMAndSfBuilder.build();
  }

  /**
   * @param calculationSheetTankgroup
   * @param calculationSheetTankGroupBuilder
   * @return CalculationSheetTankGroup
   */
  private CalculationSheetTankGroup createCalculationSheetTankGroup(
      CalculationSheetTankgroup calculationSheetTankgroup,
      com.cpdss.common.generated.VesselInfo.CalculationSheetTankGroup.Builder
          calculationSheetTankGroupBuilder) {
    calculationSheetTankGroupBuilder.setId(calculationSheetTankgroup.getId());
    Optional.ofNullable(calculationSheetTankgroup.getTankGroup())
        .ifPresent(tankGroup -> calculationSheetTankGroupBuilder.setTankGroup(tankGroup));
    Optional.ofNullable(calculationSheetTankgroup.getLcg())
        .ifPresent(lcg -> calculationSheetTankGroupBuilder.setLcg(String.valueOf(lcg)));
    Optional.ofNullable(calculationSheetTankgroup.getFrameNumber())
        .ifPresent(
            frameNumber ->
                calculationSheetTankGroupBuilder.setFrameNumber(String.valueOf(frameNumber)));

    return calculationSheetTankGroupBuilder.build();
  }

  /**
   * @param calculationSheet
   * @param calculationSheetBuilder
   * @return CalculationSheet
   */
  private CalculationSheet createCalculationSheet(
      com.cpdss.vesselinfo.entity.CalculationSheet calculationSheet,
      com.cpdss.common.generated.VesselInfo.CalculationSheet.Builder calculationSheetBuilder) {
    calculationSheetBuilder.setId(calculationSheet.getId());
    Optional.ofNullable(calculationSheet.getTankGroup())
        .ifPresent(tankGroup -> calculationSheetBuilder.setTankGroup(tankGroup));
    Optional.ofNullable(calculationSheet.getTankId())
        .ifPresent(tankId -> calculationSheetBuilder.setTankId(tankId));
    Optional.ofNullable(calculationSheet.getWeightRatio())
        .ifPresent(
            weightRatio -> calculationSheetBuilder.setWeightRatio(String.valueOf(weightRatio)));
    Optional.ofNullable(calculationSheet.getLcg())
        .ifPresent(lcg -> calculationSheetBuilder.setLcg(String.valueOf(lcg)));
    return calculationSheetBuilder.build();
  }

  /**
   * @param shearingForce
   * @param shearingForceBuilder
   * @return ShearingForce
   */
  private ShearingForce createShearingForce(
      com.cpdss.vesselinfo.entity.ShearingForce shearingForce,
      com.cpdss.common.generated.VesselInfo.ShearingForce.Builder shearingForceBuilder) {
    Optional.ofNullable(shearingForce.getId()).ifPresent(id -> shearingForceBuilder.setId(id));
    Optional.ofNullable(shearingForce.getFrameNumber())
        .ifPresent(frameNumber -> shearingForceBuilder.setFrameNumber(String.valueOf(frameNumber)));
    Optional.ofNullable(shearingForce.getBaseDraft())
        .ifPresent(baseDraft -> shearingForceBuilder.setBaseDraft(String.valueOf(baseDraft)));
    Optional.ofNullable(shearingForce.getBaseValue())
        .ifPresent(baseValue -> shearingForceBuilder.setBaseValue(String.valueOf(baseValue)));
    Optional.ofNullable(shearingForce.getDraftCorrection())
        .ifPresent(
            draftCorrection ->
                shearingForceBuilder.setDraftCorrection(String.valueOf(draftCorrection)));
    Optional.ofNullable(shearingForce.getTrimCorrection())
        .ifPresent(
            trimCorrection ->
                shearingForceBuilder.setTrimCorrection(String.valueOf(trimCorrection)));
    return shearingForceBuilder.build();
  }

  /**
   * @param bendingMoment
   * @param bendingMomentBuilder
   * @return BendingMoment
   */
  private BendingMoment createBendingMoment(
      com.cpdss.vesselinfo.entity.BendingMoment bendingMoment,
      com.cpdss.common.generated.VesselInfo.BendingMoment.Builder bendingMomentBuilder) {
    Optional.ofNullable(bendingMoment.getId()).ifPresent(id -> bendingMomentBuilder.setId(id));
    Optional.ofNullable(bendingMoment.getFrameNumber())
        .ifPresent(frameNumber -> bendingMomentBuilder.setFrameNumber(String.valueOf(frameNumber)));
    Optional.ofNullable(bendingMoment.getBaseDraft())
        .ifPresent(baseDraft -> bendingMomentBuilder.setBaseDraft(String.valueOf(baseDraft)));
    Optional.ofNullable(bendingMoment.getBaseValue())
        .ifPresent(baseValue -> bendingMomentBuilder.setBaseValue(String.valueOf(baseValue)));
    Optional.ofNullable(bendingMoment.getDraftCorrection())
        .ifPresent(
            draftCorrection ->
                bendingMomentBuilder.setDraftCorrection(String.valueOf(draftCorrection)));
    Optional.ofNullable(bendingMoment.getTrimCorrection())
        .ifPresent(
            trimCorrection ->
                bendingMomentBuilder.setTrimCorrection(String.valueOf(trimCorrection)));
    return bendingMomentBuilder.build();
  }

  /**
   * @param vesselTankTcg
   * @param vesselTankTcgBuilder
   * @return VesselTankTCG
   */
  private VesselTankTCG createVesselTankTcg(
      VesselTankTcg vesselTankTcg,
      com.cpdss.common.generated.VesselInfo.VesselTankTCG.Builder vesselTankTcgBuilder) {
    Optional.ofNullable(vesselTankTcg.getId()).ifPresent(id -> vesselTankTcgBuilder.setId(id));
    Optional.ofNullable(vesselTankTcg.getCapacity())
        .ifPresent(capacity -> vesselTankTcgBuilder.setCapacity(String.valueOf(capacity)));
    Optional.ofNullable(vesselTankTcg.getTankId())
        .ifPresent(tankId -> vesselTankTcgBuilder.setTankId(tankId));
    Optional.ofNullable(vesselTankTcg.getTcg())
        .ifPresent(tcg -> vesselTankTcgBuilder.setTcg(String.valueOf(tcg)));
    return vesselTankTcgBuilder.build();
  }

  /**
   * @param hydrostaticTable
   * @param hydrostaticDataBuilder
   * @return HydrostaticData
   */
  private HydrostaticData createhydrostaticTableData(
      HydrostaticTable hydrostaticTable,
      com.cpdss.common.generated.VesselInfo.HydrostaticData.Builder hydrostaticDataBuilder) {
    Optional.ofNullable(hydrostaticTable.getId()).ifPresent(id -> hydrostaticDataBuilder.setId(id));
    Optional.ofNullable(hydrostaticTable.getTrim().toString())
        .ifPresent(trim -> hydrostaticDataBuilder.setTrim(trim));
    Optional.ofNullable(hydrostaticTable.getDraft().toString())
        .ifPresent(draft -> hydrostaticDataBuilder.setDraft(draft));
    Optional.ofNullable(hydrostaticTable.getDisplacement().toString())
        .ifPresent(displacement -> hydrostaticDataBuilder.setDisplacement(displacement));
    Optional.ofNullable(hydrostaticTable.getLcb().toString())
        .ifPresent(lcb -> hydrostaticDataBuilder.setLcb(lcb));
    Optional.ofNullable(hydrostaticTable.getLcf().toString())
        .ifPresent(lcf -> hydrostaticDataBuilder.setLcf(lcf));
    Optional.ofNullable(hydrostaticTable.getMtc().toString())
        .ifPresent(mtc -> hydrostaticDataBuilder.setMtc(mtc));
    Optional.ofNullable(hydrostaticTable.getTpc().toString())
        .ifPresent(tpc -> hydrostaticDataBuilder.setTpc(tpc));
    Optional.ofNullable(hydrostaticTable.getVcb())
        .ifPresent(vcb -> hydrostaticDataBuilder.setVcb(String.valueOf(vcb)));
    Optional.ofNullable(hydrostaticTable.getTkm().toString())
        .ifPresent(tkm -> hydrostaticDataBuilder.setTkm(tkm));
    Optional.ofNullable(hydrostaticTable.getLkm())
        .ifPresent(lkm -> hydrostaticDataBuilder.setLkm(String.valueOf(lkm)));
    return hydrostaticDataBuilder.build();
  }

  /**
   * @param vesselTank
   * @param vesselTankBuilder
   * @return VesselTankDetail
   */
  private VesselTankDetail createVesselTankData(
      VesselTank vesselTank,
      com.cpdss.common.generated.VesselInfo.VesselTankDetail.Builder vesselTankBuilder) {
    Optional.ofNullable(vesselTank.getId()).ifPresent(id -> vesselTankBuilder.setTankId(id));
    Optional.ofNullable(vesselTank.getTankCategory())
        .ifPresent(tankCategory -> vesselTankBuilder.setTankCategoryId(tankCategory.getId()));
    Optional.ofNullable(vesselTank.getTankType())
        .ifPresent(tankType -> vesselTankBuilder.setTankTypeId(tankType.getId()));
    Optional.ofNullable(vesselTank.getCoatingTypeXid())
        .ifPresent(coatingTypeId -> vesselTankBuilder.setCoatingTypeId(coatingTypeId));
    Optional.ofNullable(vesselTank.getTankName())
        .ifPresent(tankName -> vesselTankBuilder.setTankName(tankName));
    Optional.ofNullable(vesselTank.getFrameNumberFrom())
        .ifPresent(frameNumberFrom -> vesselTankBuilder.setFrameNumberFrom(frameNumberFrom));
    Optional.ofNullable(vesselTank.getFrameNumberTo())
        .ifPresent(frameNumberTo -> vesselTankBuilder.setFrameNumberTo(frameNumberTo));
    Optional.ofNullable(vesselTank.getFullCapacityCubm().toString())
        .ifPresent(fullCapacityCubm -> vesselTankBuilder.setFullCapacityCubm(fullCapacityCubm));
    Optional.ofNullable(vesselTank.getLcg().toString())
        .ifPresent(lcg -> vesselTankBuilder.setLcg(lcg));
    Optional.ofNullable(vesselTank.getVcg().toString())
        .ifPresent(vcg -> vesselTankBuilder.setVcg(vcg));
    Optional.ofNullable(vesselTank.getTcg().toString())
        .ifPresent(tcg -> vesselTankBuilder.setTcg(tcg));
    Optional.ofNullable(vesselTank.getFillCapacityCubm().toString())
        .ifPresent(fillCapacityCubm -> vesselTankBuilder.setFillCapacityCubm(fillCapacityCubm));
    Optional.ofNullable(vesselTank.getShortName())
        .ifPresent(shortName -> vesselTankBuilder.setShortName(shortName));
    return vesselTankBuilder.build();
  }

  /**
   * @param vesselDraftCondition
   * @param vesselDraftConditionBuilder
   * @return HydrostaticData
   */
  private com.cpdss.common.generated.VesselInfo.VesselDraftCondition createVesselHydrostaticData(
      VesselDraftCondition vesselDraftCondition,
      com.cpdss.common.generated.VesselInfo.VesselDraftCondition.Builder
          vesselDraftConditionBuilder) {
    Optional.ofNullable(vesselDraftCondition.getId())
        .ifPresent(id -> vesselDraftConditionBuilder.setId(id));
    Optional.ofNullable(vesselDraftCondition.getDraftCondition().getId())
        .ifPresent(
            draftConditionId -> vesselDraftConditionBuilder.setDraftConditionId(draftConditionId));

    Optional.ofNullable(vesselDraftCondition.getDepth().toString())
        .ifPresent(depth -> vesselDraftConditionBuilder.setDepth(depth));
    Optional.ofNullable(vesselDraftCondition.getFreeboard().toString())
        .ifPresent(freeBoard -> vesselDraftConditionBuilder.setFreeboard(freeBoard));
    Optional.ofNullable(vesselDraftCondition.getDraftExtreme().toString())
        .ifPresent(draftExtreme -> vesselDraftConditionBuilder.setDraftExtreme(draftExtreme));
    Optional.ofNullable(vesselDraftCondition.getDisplacement())
        .ifPresent(
            displacement ->
                vesselDraftConditionBuilder.setDisplacement(String.valueOf(displacement)));
    Optional.ofNullable(vesselDraftCondition.getDeadweight().toString())
        .ifPresent(deadweight -> vesselDraftConditionBuilder.setDeadWeight(deadweight));
    return vesselDraftConditionBuilder.build();
  }

  /**
   * @param vessel
   * @param vesselDetailBuilder
   * @return VesselDetail
   */
  private VesselDetail createVesseDetails(Vessel vessel, Builder vesselDetailBuilder) {
    Optional.ofNullable(vessel.getName()).ifPresent(name -> vesselDetailBuilder.setName(name));
    Optional.ofNullable(vessel.getImoNumber())
        .ifPresent(imoNumber -> vesselDetailBuilder.setImoNumber(imoNumber));

    Optional.ofNullable(vessel.getPortOfRegistry())
        .ifPresent(portOfRegistory -> vesselDetailBuilder.setPortOfRegistry(portOfRegistory));

    Optional.ofNullable(vessel.getOfficialNumber())
        .ifPresent(officialNumber -> vesselDetailBuilder.setOfficialNumber(officialNumber));

    Optional.ofNullable(vessel.getSignalLetter())
        .ifPresent(signalLetter -> vesselDetailBuilder.setImoNumber(signalLetter));

    Optional.ofNullable(vessel.getNavigationAreaId())
        .ifPresent(navigationAreaId -> vesselDetailBuilder.setNavigationAreaId(navigationAreaId));

    Optional.ofNullable(vessel.getTypeOfShip())
        .ifPresent(typeOfShip -> vesselDetailBuilder.setTypeOfShip(typeOfShip));

    Optional.ofNullable(vessel.getRegisterLength().toString())
        .ifPresent(registerLength -> vesselDetailBuilder.setRegisterLength(registerLength));

    Optional.ofNullable(vessel.getLengthOverall().toString())
        .ifPresent(lenthOverall -> vesselDetailBuilder.setLengthOverall(lenthOverall));

    Optional.ofNullable(vessel.getLengthBetweenPerpendiculars().toString())
        .ifPresent(
            lengthBetweenPerpendiculars ->
                vesselDetailBuilder.setLengthBetweenPerpendiculars(lengthBetweenPerpendiculars));

    Optional.ofNullable(vessel.getDepthMolded().toString())
        .ifPresent(depthMolded -> vesselDetailBuilder.setDepthMolded(depthMolded));

    Optional.ofNullable(vessel.getDesignedLoaddraft())
        .ifPresent(
            designedLoaddraft ->
                vesselDetailBuilder.setDesignedLoaddraft(String.valueOf(designedLoaddraft)));

    Optional.ofNullable(vessel.getDraftFullLoadSummer().toString())
        .ifPresent(
            draftFullLoadSummer -> vesselDetailBuilder.setDraftFullLoadSummer(draftFullLoadSummer));

    Optional.ofNullable(vessel.getThicknessOfUpperDeckStringerPlate().toString())
        .ifPresent(
            thicknessOfUpperDeckStringerPlate ->
                vesselDetailBuilder.setThicknessOfUpperDeckStringerPlate(
                    thicknessOfUpperDeckStringerPlate));

    Optional.ofNullable(vessel.getThicknessOfKeelplate().toString())
        .ifPresent(
            thicknessOfKeelplate ->
                vesselDetailBuilder.setThicknessOfKeelplate(thicknessOfKeelplate));

    Optional.ofNullable(vessel.getDeadweight())
        .ifPresent(deadweight -> vesselDetailBuilder.setDeadweight(String.valueOf(deadweight)));

    Optional.ofNullable(vessel.getLightweight().toString())
        .ifPresent(lightweight -> vesselDetailBuilder.setLightweight(lightweight));

    Optional.ofNullable(vessel.getLcg().toString())
        .ifPresent(lcg -> vesselDetailBuilder.setLcg(lcg));

    Optional.ofNullable(vessel.getKeelToMastHeight().toString())
        .ifPresent(keelToMastHeight -> vesselDetailBuilder.setKeelToMastHeight(keelToMastHeight));

    Optional.ofNullable(vessel.getDeadweightConstant().toString())
        .ifPresent(
            deadweightConstant -> vesselDetailBuilder.setDeadweightConstant(deadweightConstant));

    Optional.ofNullable(vessel.getProvisionalConstant().toString())
        .ifPresent(
            provisionalConstant -> vesselDetailBuilder.setProvisionalConstant(provisionalConstant));

    Optional.ofNullable(vessel.getDeadweightConstantLcg().toString())
        .ifPresent(
            deadweightConstantLcg ->
                vesselDetailBuilder.setDeadweightConstantLcg(deadweightConstantLcg));

    Optional.ofNullable(vessel.getProvisionalConstantLcg().toString())
        .ifPresent(
            provisionalConstantLcg ->
                vesselDetailBuilder.setProvisionalConstantLcg(provisionalConstantLcg));

    Optional.ofNullable(vessel.getGrossTonnage())
        .ifPresent(
            grossTonnage -> vesselDetailBuilder.setGrossTonnage(String.valueOf(grossTonnage)));

    Optional.ofNullable(vessel.getNetTonnage())
        .ifPresent(netTonnage -> vesselDetailBuilder.setNetTonnage(String.valueOf(netTonnage)));

    Optional.ofNullable(vessel.getDeadweightConstantTcg().toString())
        .ifPresent(
            deadweightConstantTcg ->
                vesselDetailBuilder.setDeadweightConstantTcg(deadweightConstantTcg));

    return vesselDetailBuilder.build();
  }
}
