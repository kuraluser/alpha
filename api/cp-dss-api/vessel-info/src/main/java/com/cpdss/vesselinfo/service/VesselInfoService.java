/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.service;

import static java.lang.String.valueOf;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.Common.RuleDropDownMaster;
import com.cpdss.common.generated.Common.RulePlans;
import com.cpdss.common.generated.Common.Rules;
import com.cpdss.common.generated.Common.RulesInputs;
import com.cpdss.common.generated.VesselInfo.BMAndSF;
import com.cpdss.common.generated.VesselInfo.BendingMomentShearingForceType3;
import com.cpdss.common.generated.VesselInfo.BendingMomentType1;
import com.cpdss.common.generated.VesselInfo.BendingMomentType2;
import com.cpdss.common.generated.VesselInfo.BendingMomentType4;
import com.cpdss.common.generated.VesselInfo.CalculationSheet;
import com.cpdss.common.generated.VesselInfo.CalculationSheetTankGroup;
import com.cpdss.common.generated.VesselInfo.HydrostaticData;
import com.cpdss.common.generated.VesselInfo.InnerBulkHeadSF;
import com.cpdss.common.generated.VesselInfo.LoadLineDetail;
import com.cpdss.common.generated.VesselInfo.LoadingInfoRulesReply;
import com.cpdss.common.generated.VesselInfo.LoadingInfoRulesRequest;
import com.cpdss.common.generated.VesselInfo.MinMaxValuesForBMAndSf;
import com.cpdss.common.generated.VesselInfo.ParameterValue;
import com.cpdss.common.generated.VesselInfo.SelectableParameter;
import com.cpdss.common.generated.VesselInfo.ShearingForceType1;
import com.cpdss.common.generated.VesselInfo.ShearingForceType2;
import com.cpdss.common.generated.VesselInfo.ShearingForceType4;
import com.cpdss.common.generated.VesselInfo.StationValues;
import com.cpdss.common.generated.VesselInfo.UllageDetails;
import com.cpdss.common.generated.VesselInfo.VesselAlgoReply;
import com.cpdss.common.generated.VesselInfo.VesselAlgoRequest;
import com.cpdss.common.generated.VesselInfo.VesselDetail;
import com.cpdss.common.generated.VesselInfo.VesselDetail.Builder;
import com.cpdss.common.generated.VesselInfo.VesselIdResponse;
import com.cpdss.common.generated.VesselInfo.VesselLoadableQuantityDetails;
import com.cpdss.common.generated.VesselInfo.VesselReply;
import com.cpdss.common.generated.VesselInfo.VesselRequest;
import com.cpdss.common.generated.VesselInfo.VesselRuleReply;
import com.cpdss.common.generated.VesselInfo.VesselRuleRequest;
import com.cpdss.common.generated.VesselInfo.VesselTankDetail;
import com.cpdss.common.generated.VesselInfo.VesselTankOrder;
import com.cpdss.common.generated.VesselInfo.VesselTankRequest;
import com.cpdss.common.generated.VesselInfo.VesselTankResponse;
import com.cpdss.common.generated.VesselInfo.VesselTankTCG;
import com.cpdss.common.generated.VesselInfoServiceGrpc.VesselInfoServiceImplBase;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.vesselinfo.domain.CargoTankMaster;
import com.cpdss.vesselinfo.domain.RuleMasterData;
import com.cpdss.vesselinfo.domain.TypeValue;
import com.cpdss.vesselinfo.domain.VesselDetails;
import com.cpdss.vesselinfo.domain.VesselInfo;
import com.cpdss.vesselinfo.domain.VesselRule;
import com.cpdss.vesselinfo.domain.VesselTankDetails;
import com.cpdss.vesselinfo.entity.CalculationSheetTankgroup;
import com.cpdss.vesselinfo.entity.DraftCondition;
import com.cpdss.vesselinfo.entity.HydrostaticTable;
import com.cpdss.vesselinfo.entity.InnerBulkHeadValues;
import com.cpdss.vesselinfo.entity.MinMaxValuesForBmsf;
import com.cpdss.vesselinfo.entity.RuleTemplate;
import com.cpdss.vesselinfo.entity.RuleTemplateInput;
import com.cpdss.vesselinfo.entity.RuleType;
import com.cpdss.vesselinfo.entity.RuleVesselDropDownValues;
import com.cpdss.vesselinfo.entity.RuleVesselMapping;
import com.cpdss.vesselinfo.entity.RuleVesselMappingInput;
import com.cpdss.vesselinfo.entity.TankCategory;
import com.cpdss.vesselinfo.entity.UllageTableData;
import com.cpdss.vesselinfo.entity.UllageTrimCorrection;
import com.cpdss.vesselinfo.entity.Vessel;
import com.cpdss.vesselinfo.entity.VesselChartererMapping;
import com.cpdss.vesselinfo.entity.VesselDraftCondition;
import com.cpdss.vesselinfo.entity.VesselFlowRate;
import com.cpdss.vesselinfo.entity.VesselTank;
import com.cpdss.vesselinfo.entity.VesselTankTcg;
import com.cpdss.vesselinfo.repository.*;
import io.grpc.stub.StreamObserver;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
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
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

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
  @Autowired private BendingMomentRepositoryType2 bendingMomentRepositoryType2;
  @Autowired private ShearingForceRepositoryType2 shearingForceRepositoryType2;
  @Autowired private BendingMomentRepositoryType4 bendingMomentRepositoryType4;
  @Autowired private ShearingForceRepositoryType4 shearingForceRepositoryType4;

  @Autowired
  private BendingMomentShearingForceRepositoryType3 bendingMomentShearingForceRepositoryType3;

  @Autowired private CalculationSheetRepository calculationSheetRepository;
  @Autowired private CalculationSheetTankgroupRepository calculationSheetTankgroupRepository;
  @Autowired private MinMaxValuesForBmsfRepository minMaxValuesForBmsfRepository;
  @Autowired private StationValuesRepository stationValuesRepository;
  @Autowired private InnerBulkHeadValuesRepository innerBulkHeadValuesRepository;
  @Autowired private UllageTableDataRepository ullageTableDataRepository;
  @Autowired private VesselFlowRateRepository vesselFlowRateRepository;
  @Autowired HydrostaticService hydrostaticService;
  @Autowired VesselPumpService vesselPumpService;
  @Autowired RuleTypeRepository ruleTypeRepository;
  @Autowired RuleTemplateRepository ruleTemplateRepository;
  @Autowired RuleVesselMappingRepository ruleVesselMappingRepository;
  @Autowired RuleVesselMappingInputRespository ruleVesselMappingInputRespository;
  @Autowired VesselValveSequenceRepository vesselValveSequenceRepository;
  @Autowired RuleTemplateInputRepository ruleTemplateInputRepository;
  @Autowired RuleVesselDropDownValuesRepository ruleVesselDropDownValuesRepository;

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
        Optional.ofNullable(entity.getHasLoadicator()).ifPresent(builder::setHasLoadicator);
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

      Vessel vessel = vesselRepository.findByIdAndIsActive(request.getVesselId(), true);
      Optional<HydrostaticTable> hydrostaticTable =
          hydrostaticService
              .fetchAllDataByDraftAndVessel(vessel, new BigDecimal(request.getDraftExtreme()))
              .stream()
              .findFirst();
      List<BigDecimal> tpc =
          hydrostaticTableRepository.getTPCFromDraf(
              request.getVesselId(), new BigDecimal(request.getDraftExtreme()), true);
      if (null != vesselDetails) {
        Optional.ofNullable(hydrostaticTable.get().getDisplacement())
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
      log.info("inside getVesselTanks");
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
    log.info("inside findVesselTanksByCategory");
    Vessel vessel = this.vesselRepository.findByIdAndIsActive(vesselId, true);
    if (null == vessel) {
      log.error("Vessel does not exist");
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
      Optional.ofNullable(tank.getTankCategory().getShortName())
          .ifPresent(builder::setTankCategoryShortName);
      Optional.ofNullable(tank.getShortName()).ifPresent(builder::setShortName);
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
      Optional.ofNullable(tank.getTankDisplayOrder()).ifPresent(builder::setTankDisplayOrder);
      Optional.ofNullable(tank.getShowInOhqObq()).ifPresent(builder::setShowInOhqObq);
      Optional.ofNullable(tank.getTankPositionCategory())
          .ifPresent(builder::setTankPositionCategory);
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
        builder.setTankCategoryId(tank.getTankCategory().getId());
        // builder.setTankCategoryName(tank.getTankCategory().getName());
        // builder.setFrameNumberFrom(tank.getFrameNumberFrom());
        // builder.setFrameNumberTo(tank.getFrameNumberTo());
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
        if (vessel != null
            && vessel.getBm_sf_model_type() != null
            && vessel.getBm_sf_model_type() != 0) {

          if (vessel.getBm_sf_model_type() == 1) {
            bendingMomentRepository
                .findByVessel(vessel)
                .forEach(
                    bendingMomentType1 -> {
                      BendingMomentType1.Builder bendingMomentBuilder =
                          BendingMomentType1.newBuilder();
                      bMAndSFBuilder.addBendingMomentType1(
                          createBendingMomentType1(bendingMomentType1, bendingMomentBuilder));
                    });

            shearingForceRepository
                .findByVessel(vessel)
                .forEach(
                    shearingForceType1 -> {
                      ShearingForceType1.Builder shearingForceBuilder =
                          ShearingForceType1.newBuilder();
                      bMAndSFBuilder.addShearingForceType1(
                          createShearingForce(shearingForceType1, shearingForceBuilder));
                    });
          }
          if (vessel.getBm_sf_model_type() == 2) {
            bendingMomentRepositoryType2
                .findByVessel(vessel)
                .forEach(
                    bendingMomentType2 -> {
                      BendingMomentType2.Builder bendingMomentBuilder =
                          BendingMomentType2.newBuilder();
                      bMAndSFBuilder.addBendingMomentType2(
                          createBendingMomentType2(bendingMomentType2, bendingMomentBuilder));
                    });

            shearingForceRepositoryType2
                .findByVessel(vessel)
                .forEach(
                    shearingForceType2 -> {
                      ShearingForceType2.Builder shearingForceBuilder =
                          ShearingForceType2.newBuilder();
                      bMAndSFBuilder.addShearingForceType2(
                          createShearingForce2(shearingForceType2, shearingForceBuilder));
                    });
          }
          if (vessel.getBm_sf_model_type() == 4) {
            bendingMomentRepositoryType4
                .findByVessel(vessel)
                .forEach(
                    bendingMomentType4 -> {
                      BendingMomentType4.Builder bendingMomentBuilder =
                          BendingMomentType4.newBuilder();
                      bMAndSFBuilder.addBendingMomentType4(
                          createBendingMomentType4(bendingMomentType4, bendingMomentBuilder));
                    });

            shearingForceRepositoryType4
                .findByVessel(vessel)
                .forEach(
                    shearingForceType4 -> {
                      ShearingForceType4.Builder shearingForceBuilder =
                          ShearingForceType4.newBuilder();
                      bMAndSFBuilder.addShearingForceType4(
                          createShearingForceType4(shearingForceType4, shearingForceBuilder));
                    });
          }
          if (vessel.getBm_sf_model_type() == 4) {
            bendingMomentShearingForceRepositoryType3
                .findByVessel(vessel)
                .forEach(
                    bendingMomentShearingForceType3 -> {
                      BendingMomentShearingForceType3.Builder bendingMomentBuilder =
                          BendingMomentShearingForceType3.newBuilder();
                      bMAndSFBuilder.addBendingMomentShearingForce3(
                          createBendingMomentShearingForce3(
                              bendingMomentShearingForceType3, bendingMomentBuilder));
                    });
          }
        }

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

        ullageTableDataRepository
            .findByVesselOrderByVesselTankAscUllageDepthAsc(vessel)
            .forEach(
                ullageTableData -> {
                  UllageDetails.Builder ullageDetailsBuilder = UllageDetails.newBuilder();
                  replyBuilder.addUllageDetails(
                      ullageDetailsBuilder(ullageTableData, ullageDetailsBuilder));
                });

        Set<UllageTrimCorrection> ullageTrimCorrections = vessel.getUllageTrimCorrections();
        if (null != ullageTrimCorrections && !ullageTrimCorrections.isEmpty()) {
          for (UllageTrimCorrection entity : ullageTrimCorrections) {
            if (null != entity.getIsActive() && entity.getIsActive()) {
              replyBuilder.addUllageTrimCorrection(this.buildUllageTrimCorrection(entity));
            }
          }
        }

        vesselFlowRateRepository
            .findByVessel(vessel)
            .forEach(
                flowRate -> {
                  SelectableParameter.Builder selectableBuilder = SelectableParameter.newBuilder();
                  replyBuilder.addSelectableParameter(
                      selectableParameterBuilder(flowRate, selectableBuilder));
                });

        com.cpdss.common.generated.VesselInfo.VesselPumpsResponse.Builder pumpDetailsBuilder =
            com.cpdss.common.generated.VesselInfo.VesselPumpsResponse.newBuilder();
        this.vesselPumpService.getVesselPumpsAndTypes(pumpDetailsBuilder, vessel.getId());
        replyBuilder.addAllPumpType(pumpDetailsBuilder.getPumpTypeList());
        replyBuilder.addAllVesselPump(pumpDetailsBuilder.getVesselPumpList());
        replyBuilder.addAllTankType(pumpDetailsBuilder.getTankTypeList());
        replyBuilder.addAllVesselManifold(pumpDetailsBuilder.getVesselManifoldList());
        replyBuilder.addAllVesselBottomLine(pumpDetailsBuilder.getVesselBottomLineList());

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

  private SelectableParameter selectableParameterBuilder(
      VesselFlowRate flowRate,
      com.cpdss.common.generated.VesselInfo.SelectableParameter.Builder selectableBuilder) {
    Optional.ofNullable(flowRate.getFlowRateParameter())
        .ifPresent(
            flowRateName -> selectableBuilder.setParamterName(flowRateName.getFlowRateParameter()));
    Optional.ofNullable(flowRate.getFlowRateOne())
        .ifPresent(
            flowRateOne -> {
              ParameterValue.Builder parameterBuilder = ParameterValue.newBuilder();
              parameterBuilder.setValue(String.valueOf(flowRateOne));
              parameterBuilder.setType(1);
              selectableBuilder.addValues(parameterBuilder.build());
            });
    Optional.ofNullable(flowRate.getFlowRateSix())
        .ifPresent(
            flowRateSix -> {
              ParameterValue.Builder parameterBuilder = ParameterValue.newBuilder();
              parameterBuilder.setValue(String.valueOf(flowRateSix));
              parameterBuilder.setType(6);
              selectableBuilder.addValues(parameterBuilder.build());
            });
    Optional.ofNullable(flowRate.getFlowRateSeven())
        .ifPresent(
            flowRateSeven -> {
              ParameterValue.Builder parameterBuilder = ParameterValue.newBuilder();
              parameterBuilder.setValue(String.valueOf(flowRateSeven));
              parameterBuilder.setType(7);
              selectableBuilder.addValues(parameterBuilder.build());
            });
    Optional.ofNullable(flowRate.getFlowRateTwelve())
        .ifPresent(
            flowRateTwelve -> {
              ParameterValue.Builder parameterBuilder = ParameterValue.newBuilder();
              parameterBuilder.setValue(String.valueOf(flowRateTwelve));
              parameterBuilder.setType(12);
              selectableBuilder.addValues(parameterBuilder.build());
            });

    return selectableBuilder.build();
  }

  /**
   * Build ullage trim correction value
   *
   * @param entity
   * @return
   */
  private com.cpdss.common.generated.VesselInfo.UllageTrimCorrection buildUllageTrimCorrection(
      UllageTrimCorrection entity) {
    com.cpdss.common.generated.VesselInfo.UllageTrimCorrection.Builder builder =
        com.cpdss.common.generated.VesselInfo.UllageTrimCorrection.newBuilder();
    builder.setId(entity.getId());
    Optional.ofNullable(entity.getTankId()).ifPresent(builder::setTankId);
    Optional.ofNullable(entity.getUllageDepth())
        .ifPresent(item -> builder.setUllageDepth(valueOf(item)));
    Optional.ofNullable(entity.getTrimM1()).ifPresent(item -> builder.setTrimM1(valueOf(item)));
    Optional.ofNullable(entity.getTrimM2()).ifPresent(item -> builder.setTrimM2(valueOf(item)));
    Optional.ofNullable(entity.getTrimM3()).ifPresent(item -> builder.setTrimM3(valueOf(item)));
    Optional.ofNullable(entity.getTrimM4()).ifPresent(item -> builder.setTrimM4(valueOf(item)));
    Optional.ofNullable(entity.getTrimM5()).ifPresent(item -> builder.setTrimM5(valueOf(item)));
    Optional.ofNullable(entity.getTrim0()).ifPresent(item -> builder.setTrim0(valueOf(item)));
    Optional.ofNullable(entity.getTrim1()).ifPresent(item -> builder.setTrim1(valueOf(item)));
    Optional.ofNullable(entity.getTrim2()).ifPresent(item -> builder.setTrim2(valueOf(item)));
    Optional.ofNullable(entity.getTrim3()).ifPresent(item -> builder.setTrim3(valueOf(item)));
    Optional.ofNullable(entity.getTrim4()).ifPresent(item -> builder.setTrim4(valueOf(item)));
    Optional.ofNullable(entity.getTrim5()).ifPresent(item -> builder.setTrim5(valueOf(item)));
    Optional.ofNullable(entity.getTrim6()).ifPresent(item -> builder.setTrim6(valueOf(item)));
    return builder.build();
  }

  /**
   * @param ullageTableData
   * @param ullageDetailsBuilder
   * @return UllageDetails
   */
  private UllageDetails ullageDetailsBuilder(
      UllageTableData ullageTableData,
      com.cpdss.common.generated.VesselInfo.UllageDetails.Builder ullageDetailsBuilder) {
    Optional.ofNullable(ullageTableData.getId()).ifPresent(id -> ullageDetailsBuilder.setId(id));
    Optional.ofNullable(ullageTableData.getVesselTank())
        .ifPresent(vesselTank -> ullageDetailsBuilder.setTankId(vesselTank.getId()));
    Optional.ofNullable(ullageTableData.getUllageDepth())
        .ifPresent(ullageDepth -> ullageDetailsBuilder.setUllageDepth(String.valueOf(ullageDepth)));
    Optional.ofNullable(ullageTableData.getEvenKeelCapacityCubm())
        .ifPresent(
            evenKeelCapacityCubm ->
                ullageDetailsBuilder.setEvenKeelCapacityCubm(String.valueOf(evenKeelCapacityCubm)));
    Optional.ofNullable(ullageTableData.getSoundDepth())
        .ifPresent(soundDepth -> ullageDetailsBuilder.setSoundDepth(String.valueOf(soundDepth)));
    return ullageDetailsBuilder.build();
  }

  /**
   * @param innerBulkHeadSF
   * @param innerBulkHeadSFBuilder
   * @return InnerBulkHeadSF
   */
  private InnerBulkHeadSF createInnerBulkHeadSFBuilder(
      InnerBulkHeadValues innerBulkHeadSF,
      com.cpdss.common.generated.VesselInfo.InnerBulkHeadSF.Builder innerBulkHeadSFBuilder) {

    Optional.ofNullable(innerBulkHeadSF.getId()).ifPresent(id -> innerBulkHeadSFBuilder.setId(id));
    Optional.ofNullable(String.valueOf(innerBulkHeadSF.getFrameNumber()))
        .ifPresent(innerBulkHeadSFBuilder::setFrameNumber);

    Optional.ofNullable(innerBulkHeadSF.getForeAlpha())
        .ifPresent(foreAlpha -> innerBulkHeadSFBuilder.setForeAlpha(String.valueOf(foreAlpha)));
    Optional.ofNullable(innerBulkHeadSF.getForeCenterCargotankId())
        .ifPresent(
            foreCenterCargoTankId ->
                innerBulkHeadSFBuilder.setForeCenterCargoTankId(foreCenterCargoTankId.longValue()));
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
                innerBulkHeadSFBuilder.setAftCenterCargoTankId(aftCenterCargoTankId.longValue()));
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
    Optional.ofNullable(stationValue.getId()).ifPresent(id -> stationValueBuilder.setId(id));
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
    Optional.ofNullable(minMaxValuesForBmsf.getId())
        .ifPresent(id -> minMaxValuesForBMAndSfBuilder.setId(id));
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
    Optional.ofNullable(calculationSheetTankgroup.getId())
        .ifPresent(id -> calculationSheetTankGroupBuilder.setId(id));
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
    Optional.ofNullable(calculationSheet.getId())
        .ifPresent(id -> calculationSheetBuilder.setId(id));
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
   * @param shearingForceType1
   * @param shearingForceBuilder
   * @return ShearingForce
   */
  private ShearingForceType1 createShearingForce(
      com.cpdss.vesselinfo.entity.ShearingForceType1 shearingForceType1,
      com.cpdss.common.generated.VesselInfo.ShearingForceType1.Builder shearingForceBuilder) {
    Optional.ofNullable(shearingForceType1.getId()).ifPresent(id -> shearingForceBuilder.setId(id));
    Optional.ofNullable(shearingForceType1.getFrameNumber())
        .ifPresent(frameNumber -> shearingForceBuilder.setFrameNumber(String.valueOf(frameNumber)));
    Optional.ofNullable(shearingForceType1.getBaseDraft())
        .ifPresent(baseDraft -> shearingForceBuilder.setBaseDraft(String.valueOf(baseDraft)));
    Optional.ofNullable(shearingForceType1.getBaseValue())
        .ifPresent(baseValue -> shearingForceBuilder.setBaseValue(String.valueOf(baseValue)));
    Optional.ofNullable(shearingForceType1.getDraftCorrection())
        .ifPresent(
            draftCorrection ->
                shearingForceBuilder.setDraftCorrection(String.valueOf(draftCorrection)));
    Optional.ofNullable(shearingForceType1.getTrimCorrection())
        .ifPresent(
            trimCorrection ->
                shearingForceBuilder.setTrimCorrection(String.valueOf(trimCorrection)));
    return shearingForceBuilder.build();
  }

  /**
   * @param shearingForce
   * @param shearingForceBuilder
   * @return ShearingForce
   */
  private ShearingForceType2 createShearingForce2(
      com.cpdss.vesselinfo.entity.ShearingForceType2 shearingForce,
      com.cpdss.common.generated.VesselInfo.ShearingForceType2.Builder shearingForceBuilder) {

    Optional.ofNullable(shearingForce.getDisplacement())
        .ifPresent(
            displacement -> shearingForceBuilder.setDisplacement(String.valueOf(displacement)));

    Optional.ofNullable(shearingForce.getFrameNumber())
        .ifPresent(frameNumber -> shearingForceBuilder.setFrameNumber(String.valueOf(frameNumber)));

    Optional.ofNullable(shearingForce.getBuay())
        .ifPresent(buay -> shearingForceBuilder.setBuay(String.valueOf(buay)));

    Optional.ofNullable(shearingForce.getDifft())
        .ifPresent(baseValue -> shearingForceBuilder.setDifft(String.valueOf(baseValue)));

    Optional.ofNullable(shearingForce.getCorrt())
        .ifPresent(
            draftCorrection -> shearingForceBuilder.setCorrt(String.valueOf(draftCorrection)));

    Optional.ofNullable(shearingForce.getIsActive())
        .ifPresent(isActive -> shearingForceBuilder.setIsActive(String.valueOf(isActive)));

    return shearingForceBuilder.build();
  }

  /**
   * @param shearingForce
   * @param shearingForceBuilder
   * @return ShearingForce
   */
  private ShearingForceType4 createShearingForceType4(
      com.cpdss.vesselinfo.entity.ShearingForceType4 shearingForce,
      com.cpdss.common.generated.VesselInfo.ShearingForceType4.Builder shearingForceBuilder) {

    Optional.ofNullable(shearingForce.getFrameNumber())
        .ifPresent(frameNumber -> shearingForceBuilder.setFrameNumber(String.valueOf(frameNumber)));

    Optional.ofNullable(shearingForce.getTrim_m1())
        .ifPresent(trim_m1 -> shearingForceBuilder.setTrimM1(String.valueOf(trim_m1)));

    Optional.ofNullable(shearingForce.getTrim_1())
        .ifPresent(trim1 -> shearingForceBuilder.setTrim1(String.valueOf(trim1)));
    Optional.ofNullable(shearingForce.getTrim_3())
        .ifPresent(trim2 -> shearingForceBuilder.setTrim1(String.valueOf(trim2)));
    Optional.ofNullable(shearingForce.getTrim_3())
        .ifPresent(trim3 -> shearingForceBuilder.setTrim2(String.valueOf(trim3)));
    Optional.ofNullable(shearingForce.getTrim_4())
        .ifPresent(trim4 -> shearingForceBuilder.setTrim3(String.valueOf(trim4)));
    Optional.ofNullable(shearingForce.getTrim_5())
        .ifPresent(trim5 -> shearingForceBuilder.setTrim4(String.valueOf(trim5)));

    return shearingForceBuilder.build();
  }

  /**
   * @param type3
   * @param shearingForceBuilder
   * @return ShearingForce
   */
  private BendingMomentShearingForceType3 createBendingMomentShearingForce3(
      com.cpdss.vesselinfo.entity.BendingMomentShearingForceType3 type3,
      com.cpdss.common.generated.VesselInfo.BendingMomentShearingForceType3.Builder
          shearingForceBuilder) {

    Optional.ofNullable(type3.getFrameNumber())
        .ifPresent(frameNumber -> shearingForceBuilder.setFrameNumber(String.valueOf(frameNumber)));

    return shearingForceBuilder.build();
  }

  /**
   * @param bendingMomentType1
   * @param bendingMomentBuilder
   * @return BendingMoment
   */
  private BendingMomentType1 createBendingMomentType1(
      com.cpdss.vesselinfo.entity.BendingMomentType1 bendingMomentType1,
      BendingMomentType1.Builder bendingMomentBuilder) {
    Optional.ofNullable(bendingMomentType1.getId()).ifPresent(id -> bendingMomentBuilder.setId(id));
    Optional.ofNullable(bendingMomentType1.getFrameNumber())
        .ifPresent(frameNumber -> bendingMomentBuilder.setFrameNumber(String.valueOf(frameNumber)));
    Optional.ofNullable(bendingMomentType1.getBaseDraft())
        .ifPresent(baseDraft -> bendingMomentBuilder.setBaseDraft(String.valueOf(baseDraft)));
    Optional.ofNullable(bendingMomentType1.getBaseValue())
        .ifPresent(baseValue -> bendingMomentBuilder.setBaseValue(String.valueOf(baseValue)));
    Optional.ofNullable(bendingMomentType1.getDraftCorrection())
        .ifPresent(
            draftCorrection ->
                bendingMomentBuilder.setDraftCorrection(String.valueOf(draftCorrection)));
    Optional.ofNullable(bendingMomentType1.getTrimCorrection())
        .ifPresent(
            trimCorrection ->
                bendingMomentBuilder.setTrimCorrection(String.valueOf(trimCorrection)));
    return bendingMomentBuilder.build();
  }

  /**
   * @param bendingMomentBuildertype2
   * @param bendingMoment
   * @return BendingMoment
   */
  private com.cpdss.common.generated.VesselInfo.BendingMomentType2 createBendingMomentType2(
      com.cpdss.vesselinfo.entity.BendingMomentType2 bendingMoment,
      com.cpdss.common.generated.VesselInfo.BendingMomentType2.Builder bendingMomentBuildertype2) {

    Optional.ofNullable(bendingMoment.getDisplacement())
        .ifPresent(
            displacement ->
                bendingMomentBuildertype2.setDisplacement(String.valueOf(displacement)));

    Optional.ofNullable(bendingMoment.getFrameNumber())
        .ifPresent(
            frameNumber -> bendingMomentBuildertype2.setFrameNumber(String.valueOf(frameNumber)));

    Optional.ofNullable(bendingMoment.getBuay())
        .ifPresent(buay -> bendingMomentBuildertype2.setBuay(String.valueOf(buay)));

    Optional.ofNullable(bendingMoment.getDifft())
        .ifPresent(baseValue -> bendingMomentBuildertype2.setDifft(String.valueOf(baseValue)));

    Optional.ofNullable(bendingMoment.getCorrt())
        .ifPresent(
            draftCorrection -> bendingMomentBuildertype2.setCorrt(String.valueOf(draftCorrection)));

    Optional.ofNullable(bendingMoment.getIsActive())
        .ifPresent(isActive -> bendingMomentBuildertype2.setIsActive(String.valueOf(isActive)));

    return bendingMomentBuildertype2.build();
  }

  /**
   * @param bendingMoment
   * @return BendingMoment
   */
  private com.cpdss.common.generated.VesselInfo.BendingMomentType4 createBendingMomentType4(
      com.cpdss.vesselinfo.entity.BendingMomentType4 bendingMoment,
      com.cpdss.common.generated.VesselInfo.BendingMomentType4.Builder bendingMomentBuildertype4) {

    Optional.ofNullable(bendingMoment.getFrameNumber())
        .ifPresent(
            frameNumber -> bendingMomentBuildertype4.setFrameNumber(String.valueOf(frameNumber)));

    Optional.ofNullable(bendingMoment.getTrim_m1())
        .ifPresent(trim_m1 -> bendingMomentBuildertype4.setTrimM1(String.valueOf(trim_m1)));

    Optional.ofNullable(bendingMoment.getTrim_1())
        .ifPresent(trim1 -> bendingMomentBuildertype4.setTrim1(String.valueOf(trim1)));
    Optional.ofNullable(bendingMoment.getTrim_3())
        .ifPresent(trim2 -> bendingMomentBuildertype4.setTrim1(String.valueOf(trim2)));
    Optional.ofNullable(bendingMoment.getTrim_3())
        .ifPresent(trim3 -> bendingMomentBuildertype4.setTrim2(String.valueOf(trim3)));
    Optional.ofNullable(bendingMoment.getTrim_4())
        .ifPresent(trim4 -> bendingMomentBuildertype4.setTrim3(String.valueOf(trim4)));
    Optional.ofNullable(bendingMoment.getTrim_5())
        .ifPresent(trim5 -> bendingMomentBuildertype4.setTrim4(String.valueOf(trim5)));

    Optional.ofNullable(bendingMomentBuildertype4.getIsActive())
        .ifPresent(isActive -> bendingMomentBuildertype4.setIsActive(String.valueOf(isActive)));

    return bendingMomentBuildertype4.build();
  }

  /*
   * int64 id = 1;
  string frameNumber = 2;
  string trim_m1 = 3;
  string trim_0 = 4;
  string trim_1 = 5;
  string trim_2 = 6;
  string trim_3 = 7;
  string trim_4 = 8;
  string trim_5 = 9;
  string isActive = 10;
   * */

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
    Optional.ofNullable(vesselTankTcg.getLcg())
        .ifPresent(lcg -> vesselTankTcgBuilder.setLcg(String.valueOf(lcg)));
    Optional.ofNullable(vesselTankTcg.getVcg())
        .ifPresent(vcg -> vesselTankTcgBuilder.setVcg(String.valueOf(vcg)));
    Optional.ofNullable(vesselTankTcg.getInertia())
        .ifPresent(inertia -> vesselTankTcgBuilder.setInertia(String.valueOf(inertia)));
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
    Optional.ofNullable(hydrostaticTable.getTrim())
        .ifPresent(trim -> hydrostaticDataBuilder.setTrim(String.valueOf(trim)));
    Optional.ofNullable(hydrostaticTable.getDraft())
        .ifPresent(draft -> hydrostaticDataBuilder.setDraft(String.valueOf(draft)));
    Optional.ofNullable(hydrostaticTable.getDisplacement())
        .ifPresent(
            displacement -> hydrostaticDataBuilder.setDisplacement(String.valueOf(displacement)));
    Optional.ofNullable(hydrostaticTable.getLcb())
        .ifPresent(lcb -> hydrostaticDataBuilder.setLcb(String.valueOf(lcb)));
    Optional.ofNullable(hydrostaticTable.getLcf())
        .ifPresent(lcf -> hydrostaticDataBuilder.setLcf(String.valueOf(lcf)));
    Optional.ofNullable(hydrostaticTable.getMtc())
        .ifPresent(mtc -> hydrostaticDataBuilder.setMtc(String.valueOf(mtc)));
    Optional.ofNullable(hydrostaticTable.getTpc())
        .ifPresent(tpc -> hydrostaticDataBuilder.setTpc(String.valueOf(tpc)));
    Optional.ofNullable(hydrostaticTable.getVcb())
        .ifPresent(vcb -> hydrostaticDataBuilder.setVcb(String.valueOf(vcb)));
    Optional.ofNullable(hydrostaticTable.getTkm())
        .ifPresent(tkm -> hydrostaticDataBuilder.setTkm(String.valueOf(tkm)));
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
    Optional.ofNullable(vesselTank.getFullCapacityCubm())
        .ifPresent(
            fullCapacityCubm ->
                vesselTankBuilder.setFullCapacityCubm(String.valueOf(fullCapacityCubm)));
    Optional.ofNullable(vesselTank.getLcg())
        .ifPresent(lcg -> vesselTankBuilder.setLcg(String.valueOf(lcg)));
    Optional.ofNullable(vesselTank.getVcg())
        .ifPresent(vcg -> vesselTankBuilder.setVcg(String.valueOf(vcg)));
    Optional.ofNullable(vesselTank.getTcg())
        .ifPresent(tcg -> vesselTankBuilder.setTcg(String.valueOf(tcg)));
    Optional.ofNullable(vesselTank.getFillCapacityCubm())
        .ifPresent(
            fillCapacityCubm ->
                vesselTankBuilder.setFillCapacityCubm(String.valueOf(fillCapacityCubm)));
    Optional.ofNullable(vesselTank.getShortName())
        .ifPresent(shortName -> vesselTankBuilder.setShortName(shortName));
    Optional.ofNullable(vesselTank.getIsLoadicatorUsing())
        .ifPresent(isLoadicatorUsing -> vesselTankBuilder.setIsLoadicatorUsing(isLoadicatorUsing));
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
    Optional.ofNullable(vesselDraftCondition.getDraftCondition())
        .ifPresent(
            draftCondition ->
                vesselDraftConditionBuilder.setDraftConditionId(draftCondition.getId()));

    Optional.ofNullable(vesselDraftCondition.getDepth())
        .ifPresent(depth -> vesselDraftConditionBuilder.setDepth(String.valueOf(depth)));
    Optional.ofNullable(vesselDraftCondition.getFreeboard())
        .ifPresent(
            freeBoard -> vesselDraftConditionBuilder.setFreeboard(String.valueOf(freeBoard)));
    Optional.ofNullable(vesselDraftCondition.getDraftExtreme())
        .ifPresent(
            draftExtreme ->
                vesselDraftConditionBuilder.setDraftExtreme(String.valueOf(draftExtreme)));
    Optional.ofNullable(vesselDraftCondition.getDisplacement())
        .ifPresent(
            displacement ->
                vesselDraftConditionBuilder.setDisplacement(String.valueOf(displacement)));
    Optional.ofNullable(vesselDraftCondition.getDeadweight())
        .ifPresent(
            deadweight -> vesselDraftConditionBuilder.setDeadWeight(String.valueOf(deadweight)));
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

    Optional.ofNullable(vessel.getBm_sf_model_type())
        .ifPresent(bmModelType -> vesselDetailBuilder.setBmSfModelType(bmModelType.toString()));

    Optional.ofNullable(vessel.getRegisterLength())
        .ifPresent(
            registerLength ->
                vesselDetailBuilder.setRegisterLength(String.valueOf(registerLength)));

    Optional.ofNullable(vessel.getLengthOverall())
        .ifPresent(
            lenthOverall -> vesselDetailBuilder.setLengthOverall(String.valueOf(lenthOverall)));

    Optional.ofNullable(vessel.getLengthBetweenPerpendiculars())
        .ifPresent(
            lengthBetweenPerpendiculars ->
                vesselDetailBuilder.setLengthBetweenPerpendiculars(
                    String.valueOf(lengthBetweenPerpendiculars)));

    Optional.ofNullable(vessel.getDepthMolded())
        .ifPresent(depthMolded -> vesselDetailBuilder.setDepthMolded(String.valueOf(depthMolded)));

    Optional.ofNullable(vessel.getDesignedLoaddraft())
        .ifPresent(
            designedLoaddraft ->
                vesselDetailBuilder.setDesignedLoaddraft(String.valueOf(designedLoaddraft)));

    Optional.ofNullable(vessel.getDraftFullLoadSummer())
        .ifPresent(
            draftFullLoadSummer ->
                vesselDetailBuilder.setDraftFullLoadSummer(String.valueOf(draftFullLoadSummer)));

    Optional.ofNullable(vessel.getThicknessOfUpperDeckStringerPlate())
        .ifPresent(
            thicknessOfUpperDeckStringerPlate ->
                vesselDetailBuilder.setThicknessOfUpperDeckStringerPlate(
                    String.valueOf(thicknessOfUpperDeckStringerPlate)));

    Optional.ofNullable(vessel.getThicknessOfKeelplate())
        .ifPresent(
            thicknessOfKeelplate ->
                vesselDetailBuilder.setThicknessOfKeelplate(String.valueOf(thicknessOfKeelplate)));

    Optional.ofNullable(vessel.getDeadweight())
        .ifPresent(deadweight -> vesselDetailBuilder.setDeadweight(String.valueOf(deadweight)));

    Optional.ofNullable(vessel.getLightweight())
        .ifPresent(lightweight -> vesselDetailBuilder.setLightweight(String.valueOf(lightweight)));

    Optional.ofNullable(vessel.getLcg())
        .ifPresent(lcg -> vesselDetailBuilder.setLcg(String.valueOf(lcg)));

    Optional.ofNullable(vessel.getKeelToMastHeight())
        .ifPresent(
            keelToMastHeight ->
                vesselDetailBuilder.setKeelToMastHeight(String.valueOf(keelToMastHeight)));

    Optional.ofNullable(vessel.getDeadweightConstant())
        .ifPresent(
            deadweightConstant ->
                vesselDetailBuilder.setDeadweightConstant(String.valueOf(deadweightConstant)));

    Optional.ofNullable(vessel.getProvisionalConstant())
        .ifPresent(
            provisionalConstant ->
                vesselDetailBuilder.setProvisionalConstant(String.valueOf(provisionalConstant)));

    Optional.ofNullable(vessel.getDeadweightConstantLcg())
        .ifPresent(
            deadweightConstantLcg ->
                vesselDetailBuilder.setDeadweightConstantLcg(
                    String.valueOf(deadweightConstantLcg)));

    Optional.ofNullable(vessel.getProvisionalConstantLcg())
        .ifPresent(
            provisionalConstantLcg ->
                vesselDetailBuilder.setProvisionalConstantLcg(
                    String.valueOf(provisionalConstantLcg)));

    Optional.ofNullable(vessel.getGrossTonnage())
        .ifPresent(
            grossTonnage -> vesselDetailBuilder.setGrossTonnage(String.valueOf(grossTonnage)));

    Optional.ofNullable(vessel.getNetTonnage())
        .ifPresent(netTonnage -> vesselDetailBuilder.setNetTonnage(String.valueOf(netTonnage)));

    Optional.ofNullable(vessel.getDeadweightConstantTcg())
        .ifPresent(
            deadweightConstantTcg ->
                vesselDetailBuilder.setDeadweightConstantTcg(
                    String.valueOf(deadweightConstantTcg)));

    Optional.ofNullable(vessel.getHasLoadicator())
        .ifPresent(hasLoadicator -> vesselDetailBuilder.setHasLoadicator(hasLoadicator));

    return vesselDetailBuilder.build();
  }

  @Override
  public void getVesselDetailForSynopticalTable(
      VesselRequest request, StreamObserver<VesselReply> responseObserver) {
    VesselReply.Builder replyBuilder = VesselReply.newBuilder();
    try {
      List<VesselTankDetail> tankList =
          this.findVesselTanksByCategory(request.getVesselId(), request.getTankCategoriesList());
      if (null != tankList && !tankList.isEmpty()) {
        replyBuilder.addAllVesselTanks(tankList);
      }
      VesselDetails vesselDetails =
          vesselRepository.findVesselDetailsById(
              request.getVesselId(),
              request.getVesselDraftConditionId(),
              new BigDecimal(request.getDraftExtreme()));
      VesselLoadableQuantityDetails.Builder builder = VesselLoadableQuantityDetails.newBuilder();
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
        Optional.ofNullable(vesselDetails.getHasLoadicator())
            .ifPresent(hasLoadicator -> builder.setHasLoadicator(hasLoadicator));
      }
      replyBuilder.setVesselLoadableQuantityDetails(builder.build());
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    } catch (GenericServiceException e) {
      log.error("GenericServiceException in getVesselDetailForSynopticalTable", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(e.getCode())
              .setMessage("GenericServiceException in getVesselDetailForSynopticalTable")
              .setStatus(FAILED)
              .build());
    } catch (Exception e) {
      log.error("Exception in getVesselDetailForSynopticalTable", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Exception in getVesselDetailForSynopticalTable")
              .setStatus(FAILED)
              .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void getVesselDetailByVesselId(
      VesselRequest request, StreamObserver<VesselReply> responseObserver) {
    VesselReply.Builder replyBuilder = VesselReply.newBuilder();

    VesselInfo vesselDetails =
        vesselRepository.findVesselDetailsByVesselId(request.getVesselId(), true);
    try {
      if (null != vesselDetails) {
        VesselDetail.Builder builder = VesselDetail.newBuilder();

        Optional.ofNullable(vesselDetails.getId()).ifPresent(item -> builder.setId(item));
        Optional.ofNullable(vesselDetails.getName()).ifPresent(item -> builder.setName(item));
        Optional.ofNullable(vesselDetails.getImoNumber())
            .ifPresent(item -> builder.setImoNumber(item));
        Optional.ofNullable(vesselDetails.getTypeOfShip())
            .ifPresent(item -> builder.setTypeOfShip(item));
        Optional.ofNullable(vesselDetails.getCode()).ifPresent(item -> builder.setCode(item));
        Optional.ofNullable(vesselDetails.getDeadweightConstant())
            .ifPresent(item -> builder.setDeadweightConstant(item.toString()));
        Optional.ofNullable(vesselDetails.getProvisionalConstant())
            .ifPresent(item -> builder.setProvisionalConstant(item.toString()));

        // Set vessel loadable quantity details
        VesselLoadableQuantityDetails.Builder vesselLoadableQuantityBuilder =
            VesselLoadableQuantityDetails.newBuilder();
        Optional.ofNullable(vesselDetails.getLightWeight())
            .ifPresent(item -> vesselLoadableQuantityBuilder.setVesselLightWeight(item.toString()));
        Optional.ofNullable(vesselDetails.getDeadweightConstant())
            .ifPresent(
                item -> vesselLoadableQuantityBuilder.setDeadWeightConstant(item.doubleValue()));

        replyBuilder.addVessels(builder.build());
        replyBuilder.setVesselLoadableQuantityDetails(vesselLoadableQuantityBuilder);

        Vessel vessel = this.vesselRepository.findByIdAndIsActive(request.getVesselId(), true);
        if (null == vessel) {
          log.error("Vessel does not exist");
          throw new GenericServiceException(
              "Vessel with given id does not exist",
              CommonErrorCodes.E_HTTP_BAD_REQUEST,
              HttpStatusCode.BAD_REQUEST);
        }
        VesselTankDetail.Builder vesselTankBuilder = VesselTankDetail.newBuilder();
        vesselTankRepository
            .findByVesselAndIsActive(vessel, true)
            .forEach(
                vesselTank -> {
                  replyBuilder.addVesselTanks(createVesselTankData(vesselTank, vesselTankBuilder));
                });
        replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
      }

    } catch (Exception e) {
      log.error("Exception in getVesselDetailForSynopticalTable", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Exception in getVesselDetailForSynopticalTable")
              .setStatus(FAILED)
              .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void getVesselInfoByPaging(
      com.cpdss.common.generated.VesselInfo.VesselRequestWithPaging request,
      StreamObserver<VesselReply> responseObserver) {
    VesselReply.Builder replyBuilder = VesselReply.newBuilder();
    try {
      List<Object[]> vesselResp = vesselRepository.findVesselIdAndNames();
      if (!vesselResp.isEmpty()) {
        log.info("Vessels list size {}", vesselResp.size());
        for (Object[] var1 : vesselResp) {
          VesselDetail.Builder builder = VesselDetail.newBuilder();
          if (var1[0] != null) { // First param Id
            BigInteger val = (BigInteger) var1[0];
            builder.setId(val.longValue());
          }
          if (var1[1] != null) { // Second param Name
            builder.setName((String) var1[1]);
          }
          replyBuilder.addVessels(builder);
        }
      }
    } catch (Exception e) {
      log.error("Exception in get all vessel Id and Name Only", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Exception in getVesselInfoByPaging")
              .setStatus(FAILED)
              .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void getVesselInfoBytankIds(
      VesselTankRequest request, StreamObserver<VesselTankResponse> responseObserver) {
    VesselTankResponse.Builder replyBuilder = VesselTankResponse.newBuilder();
    try {
      List<Long> tankIds = new ArrayList<>(request.getTankIdsList());
      List<VesselTankDetails> tankResp = vesselTankRepository.findTankDetailsByTankIds(tankIds);
      if (!tankResp.isEmpty()) {
        log.info("Tank list size {}", tankResp.size());
        for (VesselTankDetails var1 : tankResp) {
          VesselTankOrder.Builder builder = VesselTankOrder.newBuilder();
          builder.setTankId(var1.getTankId());
          builder.setShortName(var1.getShortName() != null ? var1.getShortName() : null);
          builder.setTankDisplayOrder(
              var1.getTankDisplayOrder() != null ? var1.getTankDisplayOrder() : 0);
          builder.setTankName(var1.getTankName());
          replyBuilder.addVesselTankOrder(builder);
        }
      }
    } catch (Exception e) {
      log.error("Exception in get VesselInfo By tank Ids", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Exception in getVesselInfoBytankIds")
              .setStatus(FAILED)
              .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void getDWTFromVesselByVesselId(
      com.cpdss.common.generated.VesselInfo.VesselDWTRequest request,
      StreamObserver<com.cpdss.common.generated.VesselInfo.VesselDWTResponse> responseObserver) {
    com.cpdss.common.generated.VesselInfo.VesselDWTResponse.Builder builder =
        com.cpdss.common.generated.VesselInfo.VesselDWTResponse.newBuilder();
    try {
      Long vesselId = request.getVesselId();
      BigDecimal draft =
          request.getDraftValue().length() > 0
              ? new BigDecimal(request.getDraftValue())
              : BigDecimal.ZERO;
      DecimalFormat df = new DecimalFormat("#.##");

      if (vesselId > 0) {
        Vessel vessel = vesselRepository.findByIdAndIsActive(vesselId, true);
        List<HydrostaticTable> tables =
            hydrostaticService.fetchAllDataByDraftAndVessel(
                vessel, new BigDecimal(df.format(draft)));
        Optional<HydrostaticTable> hydrostaticTable = tables.stream().findFirst();
        if (hydrostaticTable.isPresent()) {
          BigDecimal lightWeight =
              vessel.getLightweight() != null ? vessel.getLightweight() : BigDecimal.ZERO;
          BigDecimal displacement =
              hydrostaticTable.get().getDisplacement() != null
                  ? hydrostaticTable.get().getDisplacement()
                  : BigDecimal.ZERO;
          BigDecimal dwt = this.getDWTFromVesselAndDraft(displacement, lightWeight);
          builder.setDwtResult(String.valueOf(dwt));
        }
        builder.setVesselId(vessel.getId());
        builder.setCompanyId(vessel.getCompanyXId());
      }
      log.info(
          "Vessel Info, DWT for vessel - {}, Vessel Id {}, Draft {}",
          builder.getDwtResult(),
          vesselId,
          draft);
      builder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    } catch (Exception e) {
      log.error("Exception in Calculate DWT from Vessel", e);
      builder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Exception in Calculate DWT from Vessel")
              .setStatus(FAILED)
              .build());
    } finally {
      responseObserver.onNext(builder.build());
      responseObserver.onCompleted();
    }
  }

  // calculation for DWT
  // dwt = displacement - light weight
  private BigDecimal getDWTFromVesselAndDraft(BigDecimal var1, BigDecimal var2) {
    return var1.subtract(var2);
  }

  @Override
  public void getVesselInfoByVesselId(
      com.cpdss.common.generated.VesselInfo.VesselIdRequest request,
      StreamObserver<VesselIdResponse> responseObserver) {
    VesselIdResponse.Builder replyBuilder = VesselIdResponse.newBuilder();
    try {
      VesselInfo vesselDetails =
          vesselRepository.findVesselDetailsByVesselId(request.getVesselId(), true);
      if (null == vesselDetails) {
        log.error("Vessel does not exist");
        throw new GenericServiceException(
            "Vessel with given id does not exist",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      } else {
        if (null != vesselDetails) {
          VesselDetail.Builder builder = VesselDetail.newBuilder();

          Optional.ofNullable(vesselDetails.getId()).ifPresent(item -> builder.setId(item));
          Optional.ofNullable(vesselDetails.getName()).ifPresent(item -> builder.setName(item));
          Optional.ofNullable(vesselDetails.getImoNumber())
              .ifPresent(item -> builder.setImoNumber(item));
          Optional.ofNullable(vesselDetails.getTypeOfShip())
              .ifPresent(item -> builder.setTypeOfShip(item));
          Optional.ofNullable(vesselDetails.getCode()).ifPresent(item -> builder.setCode(item));
          Optional.ofNullable(vesselDetails.getDeadweightConstant())
              .ifPresent(item -> builder.setDeadweightConstant(item.toString()));
          Optional.ofNullable(vesselDetails.getProvisionalConstant())
              .ifPresent(item -> builder.setProvisionalConstant(item.toString()));
          Optional.ofNullable(vesselDetails.getMaxLoadRate())
              .ifPresent(v -> builder.setMaxLoadingRate(v.toString()));
          Optional.ofNullable(vesselDetails.getMinLoadRate())
              .ifPresent(v -> builder.setMinLoadingRate(v.toString()));
          replyBuilder.setVesselDetail(builder.build());
          replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
        }
      }
    } catch (GenericServiceException e) {
      log.error("Exception in getVesselInfoByVesselId", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Exception in getVesselInfoByVesselId")
              .setStatus(FAILED)
              .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void getVesselPumpsByVesselId(
      com.cpdss.common.generated.VesselInfo.VesselIdRequest request,
      StreamObserver<com.cpdss.common.generated.VesselInfo.VesselPumpsResponse> responseObserver) {
    com.cpdss.common.generated.VesselInfo.VesselPumpsResponse.Builder builder =
        com.cpdss.common.generated.VesselInfo.VesselPumpsResponse.newBuilder();
    try {
      this.vesselPumpService.getVesselPumpsAndTypes(builder, request.getVesselId());
    } catch (Exception e) {
      log.error("Exception in Vessel Pump and Type Getter", e);
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

  /** To retrieve vessel rule based on vessel id OR To save rule for vessel */
  @Override
  @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
  public void getRulesByVesselIdAndSectionId(
      VesselRuleRequest request, StreamObserver<VesselRuleReply> responseObserver) {
    com.cpdss.common.generated.VesselInfo.VesselRuleReply.Builder builder =
        com.cpdss.common.generated.VesselInfo.VesselRuleReply.newBuilder();
    try {
      Vessel vessel = vesselRepository.findByIdAndIsActive(request.getVesselId(), true);
      if (null == vessel) {
        log.error("Vessel does not exist in saving rule");
        throw new GenericServiceException(
            "Vessel with given id does not exist",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      // Fetch master records
      List<RuleVesselDropDownValues> listOfDropDownValue =
          ruleVesselDropDownValuesRepository.findByIsActive(true);
      // fetch master for cargo tank dropdown
      List<CargoTankMaster> cargoTankMaster =
          vesselTankRepository.findCargoTankMaster(request.getVesselId(), true);
      // fetch master for rule type
      List<RuleType> ruleTypeList = ruleTypeRepository.findByIsActive(true);
      if (!CollectionUtils.isEmpty(request.getRulePlanList())) {
        log.info("To save rule against vessel");
        saveRulesAgainstVessel(request, vessel, listOfDropDownValue, cargoTankMaster, ruleTypeList);
      }
      // Fetch rules against rule
      List<VesselRule> vesselRuleList =
          vesselRepository.findRulesAgainstVessel(request.getVesselId(), request.getSectionId());
      if (vesselRuleList != null && vesselRuleList.size() > 0) {
        if (!request.getIsNoDefaultRule()) {
          log.info("Fetch vessel rule for admin");
          buildResponseForVesselRules(
              builder,
              vesselRuleList,
              true,
              false,
              listOfDropDownValue,
              cargoTankMaster,
              request.getIsFetchEnabledRules());
        } else {
          log.info("Fetch vessel rule for loadable study");
          // isDisplayId: false means primary key will be set null for all rules
          // isDisplayVesselRuleXId: true  means primary key value set to foriegn key column for
          // storing rules against loadable study
          buildResponseForVesselRules(
              builder,
              vesselRuleList,
              false,
              true,
              listOfDropDownValue,
              cargoTankMaster,
              request.getIsFetchEnabledRules());
        }
      } else {
        if (!request.getIsNoDefaultRule()) {
          log.info("Fetch default rule template");
          vesselRuleList = vesselRepository.findDefaultAdminRule(request.getSectionId());
          buildResponseForVesselRules(
              builder,
              vesselRuleList,
              false,
              false,
              listOfDropDownValue,
              cargoTankMaster,
              request.getIsFetchEnabledRules());
        }
      }
      buildCargoTankMaster(builder, cargoTankMaster);
      buildRuleDropDownMaster(builder, listOfDropDownValue);
      buildRuleTypeMaster(builder, ruleTypeList);
      builder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when save or get vessel rule", e);
      builder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage(e.getMessage())
              .setStatus(FAILED)
              .build());
    } catch (Exception e) {
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
      log.error("Exception in save or get vessel rule", e);
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

  /**
   * rule type master response
   *
   * @param builder
   * @param ruleTypeList
   */
  private void buildRuleTypeMaster(
      com.cpdss.common.generated.VesselInfo.VesselRuleReply.Builder builder,
      List<RuleType> ruleTypeList) {
    com.cpdss.common.generated.VesselInfo.RuleTypeMaster.Builder ruleTypeBuilder =
        com.cpdss.common.generated.VesselInfo.RuleTypeMaster.newBuilder();
    ruleTypeList.forEach(
        rule -> {
          Optional.ofNullable(rule.getId()).ifPresent(ruleTypeBuilder::setId);
          Optional.ofNullable(rule.getRuleType()).ifPresent(ruleTypeBuilder::setRuleType);
          builder.addRuleTypeMaster(ruleTypeBuilder.build());
        });
  }

  /**
   * rule master drop down response
   *
   * @param builder
   * @param listOfDropDownValue
   */
  private void buildRuleDropDownMaster(
      com.cpdss.common.generated.VesselInfo.VesselRuleReply.Builder builder,
      List<RuleVesselDropDownValues> listOfDropDownValue) {
    com.cpdss.common.generated.VesselInfo.RuleDropDownValueMaster.Builder rBuilder =
        com.cpdss.common.generated.VesselInfo.RuleDropDownValueMaster.newBuilder();
    listOfDropDownValue.forEach(
        dropDownValue -> {
          Optional.ofNullable(dropDownValue.getId()).ifPresent(rBuilder::setId);
          Optional.ofNullable(dropDownValue.getDropDownValue()).ifPresent(rBuilder::setValue);
          Optional.ofNullable(dropDownValue.getRuleTemplateXid())
              .ifPresent(rBuilder::setRuleTemplateId);
          builder.addRuleDropDownValueMaster(rBuilder.build());
        });
  }

  /**
   * cargo tank master response
   *
   * @param builder
   * @param cargoTankMaster
   */
  private void buildCargoTankMaster(
      com.cpdss.common.generated.VesselInfo.VesselRuleReply.Builder builder,
      List<CargoTankMaster> cargoTankMaster) {
    com.cpdss.common.generated.VesselInfo.CargoTankMaster.Builder cargoBuider =
        com.cpdss.common.generated.VesselInfo.CargoTankMaster.newBuilder();
    cargoTankMaster.forEach(
        cargoTank -> {
          Optional.ofNullable(cargoTank.getId()).ifPresent(cargoBuider::setId);
          Optional.ofNullable(cargoTank.getValue()).ifPresent(cargoBuider::setShortName);
          builder.addCargoTankMaster(cargoBuider.build());
        });
  }

  /**
   * To validate dropdown value while saving
   *
   * @param listOfDropDownValue
   * @param cargoTankMaster
   * @param isCargoTankMaster
   * @param input
   * @param ruleTemplateInput
   * @param rule
   * @throws GenericServiceException
   */
  private void ruleMasterDropDownValidation(
      List<RuleVesselDropDownValues> listOfDropDownValue,
      List<CargoTankMaster> cargoTankMaster,
      boolean isCargoTankMaster,
      RulesInputs input,
      RuleVesselMappingInput ruleTemplateInput,
      Rules rule)
      throws GenericServiceException {
    Optional<String> isDefaultValueExist =
        Optional.ofNullable(input.getDefaultValue()).filter(item -> item.trim().length() != 0);
    if (isDefaultValueExist.isPresent()) {
      String value = "";
      List<RuleVesselDropDownValues> filterMasterByRule =
          listOfDropDownValue.stream()
              .filter(
                  rDropDown ->
                      rDropDown.getRuleTemplateXid() != null
                          && rule.getRuleTemplateId() != null
                          && rDropDown
                              .getRuleTemplateXid()
                              .equals(Long.parseLong(rule.getRuleTemplateId().trim())))
              .collect(Collectors.toList());
      if (input.getDefaultValue().contains(",")) {
        String[] masterIds = input.getDefaultValue().split(",");
        for (int id = 0; id < masterIds.length; id++) {
          int finalId = id;
          if (isCargoTankMaster) {
            if (cargoTankMaster.stream()
                .map(CargoTankMaster::getId)
                .filter(item -> item == Long.parseLong(masterIds[finalId].trim()))
                .findFirst()
                .isPresent()) {
              if (masterIds.length - 1 != id) {
                value += masterIds[id] + ",";
              } else {
                value += masterIds[id];
              }
            } else {
              throw new GenericServiceException(
                  "Rule master master with given id does not exist",
                  CommonErrorCodes.E_HTTP_BAD_REQUEST,
                  HttpStatusCode.BAD_REQUEST);
            }
          } else {
            if (filterMasterByRule.stream()
                .map(RuleVesselDropDownValues::getId)
                .filter(item -> item == Long.parseLong(masterIds[finalId].trim()))
                .findFirst()
                .isPresent()) {
              if (masterIds.length - 1 != id) {
                value += masterIds[id] + ",";
              } else {
                value += masterIds[id];
              }
            } else {
              throw new GenericServiceException(
                  "Rule master master with given id does not exist",
                  CommonErrorCodes.E_HTTP_BAD_REQUEST,
                  HttpStatusCode.BAD_REQUEST);
            }
          }
        }
        ruleTemplateInput.setDefaultValue(value);
      } else {
        if (isCargoTankMaster) {
          if (input.getDefaultValue() != null
              && input.getDefaultValue().length() != 0
              && !cargoTankMaster.stream()
                  .map(CargoTankMaster::getId)
                  .filter(item -> item == Long.parseLong(input.getDefaultValue().trim()))
                  .findFirst()
                  .isPresent()) {
            throw new GenericServiceException(
                "Cargo master with given id does not exist",
                CommonErrorCodes.E_HTTP_BAD_REQUEST,
                HttpStatusCode.BAD_REQUEST);
          }
        } else {
          if (input.getDefaultValue() != null
              && input.getDefaultValue().length() != 0
              && !filterMasterByRule.stream()
                  .map(RuleVesselDropDownValues::getId)
                  .filter(item -> item == Long.parseLong(input.getDefaultValue().trim()))
                  .findFirst()
                  .isPresent()) {
            throw new GenericServiceException(
                "Rule master master with given id does not exist",
                CommonErrorCodes.E_HTTP_BAD_REQUEST,
                HttpStatusCode.BAD_REQUEST);
          }
        }
        ruleTemplateInput.setDefaultValue(input.getDefaultValue());
      }
    } else {
      ruleTemplateInput.setDefaultValue(null);
    }
  }

  /**
   * Generate rule list
   *
   * @param builder
   * @param vesselRuleList
   * @param isDisplayId
   * @param isDisplayVesselRuleXId
   * @param listOfDropDownValue
   * @param cargoTankMaster
   * @param isFetchEnabledRules
   */
  private void buildResponseForVesselRules(
      VesselRuleReply.Builder builder,
      List<VesselRule> vesselRuleList,
      boolean isDisplayId,
      boolean isDisplayVesselRuleXId,
      List<RuleVesselDropDownValues> listOfDropDownValue,
      List<CargoTankMaster> cargoTankMaster,
      boolean isFetchEnabledRules) {
    Map<String, List<VesselRule>> groupByHeader =
        vesselRuleList.stream()
            .collect(
                Collectors.groupingBy(
                    VesselRule::getHeader, LinkedHashMap::new, Collectors.toList()));
    groupByHeader.forEach(
        (header, v) -> {
          RulePlans.Builder rulePlanBuilder = RulePlans.newBuilder();
          Optional.ofNullable(header).ifPresent(rulePlanBuilder::setHeader);
          Map<Long, List<VesselRule>> groupByRuleTemplateId =
              v.stream()
                  .collect(
                      Collectors.groupingBy(
                          VesselRule::getTemplateId, LinkedHashMap::new, Collectors.toList()));
          groupByRuleTemplateId.forEach(
              (key, value) -> {
                RulesInputs.Builder ruleInput = RulesInputs.newBuilder();
                Rules.Builder rulesBuilder = Rules.newBuilder();
                for (int id = 0; id < value.size(); id++) {
                  Optional<Boolean> isRuleEnabled =
                      Optional.ofNullable(value.get(id).getTemplateIsEnable());
                  if (!isFetchEnabledRules) {
                    buildRuleListResponse(
                        value.get(id),
                        ruleInput,
                        rulesBuilder,
                        isDisplayId,
                        isDisplayVesselRuleXId,
                        listOfDropDownValue,
                        cargoTankMaster,
                        rulePlanBuilder,
                        value,
                        id);
                  } else {
                    if (isFetchEnabledRules && isRuleEnabled.isPresent() && isRuleEnabled.get()) {
                      buildRuleListResponse(
                          value.get(id),
                          ruleInput,
                          rulesBuilder,
                          isDisplayId,
                          isDisplayVesselRuleXId,
                          listOfDropDownValue,
                          cargoTankMaster,
                          rulePlanBuilder,
                          value,
                          id);
                    }
                  }
                }
              });
          builder.addRulePlan(rulePlanBuilder.build());
        });
  }

  /**
   * @param vesselRule
   * @param ruleInput
   * @param rulesBuilder
   * @param isDisplayId
   * @param isDisplayVesselRuleXId
   * @param listOfDropDownValue
   * @param cargoTankMaster
   * @param rulePlanBuilder
   * @param value
   * @param id
   */
  private void buildRuleListResponse(
      VesselRule vesselRule,
      RulesInputs.Builder ruleInput,
      Rules.Builder rulesBuilder,
      boolean isDisplayId,
      boolean isDisplayVesselRuleXId,
      List<RuleVesselDropDownValues> listOfDropDownValue,
      List<CargoTankMaster> cargoTankMaster,
      RulePlans.Builder rulePlanBuilder,
      List<VesselRule> value,
      int id) {
    Optional.ofNullable(vesselRule.getTemplateInputDefaultValue())
        .filter(item -> item.trim().length() != 0)
        .ifPresentOrElse(ruleInput::setDefaultValue, () -> ruleInput.setDefaultValue(""));
    Optional.ofNullable(vesselRule.getTemplateInputPrefix())
        .filter(item -> item.trim().length() != 0)
        .ifPresentOrElse(ruleInput::setPrefix, () -> ruleInput.setPrefix(""));
    Optional.ofNullable(vesselRule.getTemplateInputMinValue())
        .filter(item -> item.trim().length() != 0)
        .ifPresentOrElse(ruleInput::setMin, () -> ruleInput.setMin(""));
    Optional.ofNullable(vesselRule.getTemplateInputMaxValue())
        .filter(item -> item.trim().length() != 0)
        .ifPresentOrElse(ruleInput::setMax, () -> ruleInput.setMax(""));
    Optional.ofNullable(vesselRule.getTemplateInputTypeValue())
        .filter(item -> item.trim().length() != 0)
        .ifPresentOrElse(ruleInput::setType, () -> ruleInput.setType(""));
    Optional.ofNullable(vesselRule.getTemplateInputSuffix())
        .filter(item -> item.trim().length() != 0)
        .ifPresentOrElse(ruleInput::setSuffix, () -> ruleInput.setSuffix(""));
    Optional.ofNullable(vesselRule.getIsMandatory())
        .ifPresentOrElse(ruleInput::setIsMandatory, () -> ruleInput.setIsMandatory(false));
    Optional<String> isTypeBoolean =
        Optional.ofNullable(vesselRule.getTemplateInputTypeValue())
            .filter(
                item ->
                    item.trim().length() != 0
                        && item.trim().equalsIgnoreCase(TypeValue.BOOLEAN.getType()));
    if (isTypeBoolean.isPresent()) {
      Optional.ofNullable(vesselRule.getTemplateInputDefaultValue())
          .filter(item -> item.trim().length() != 0 && item.trim().equalsIgnoreCase("true"))
          .ifPresentOrElse(ruleInput::setDefaultValue, () -> ruleInput.setDefaultValue("false"));
    }
    Optional<String> isTypeDropDownOrMultiSelect =
        Optional.ofNullable(vesselRule.getTemplateInputTypeValue())
            .filter(
                item ->
                    item.trim().length() != 0
                            && item.trim().equalsIgnoreCase(TypeValue.DROPDOWN.getType())
                        || item.trim().equalsIgnoreCase(TypeValue.MULTISELECT.getType()));
    if (isTypeDropDownOrMultiSelect.isPresent()) {
      Optional<String> isPrefixExist =
          Optional.ofNullable(vesselRule.getTemplateInputPrefix())
              .filter(
                  item ->
                      item.trim().length() != 0
                          && item.trim().equalsIgnoreCase(RuleMasterData.CargoTank.getPrefix()));
      Optional<String> isSuffixExist =
          Optional.ofNullable(vesselRule.getTemplateInputSuffix())
              .filter(
                  item ->
                      item.trim().length() != 0
                          && item.trim().equalsIgnoreCase(RuleMasterData.CargoTank.getSuffix()));
      RuleDropDownMaster.Builder ruleDropDownMaster = RuleDropDownMaster.newBuilder();
      if (isPrefixExist.isPresent() && isSuffixExist.isPresent()) {
        cargoTankMaster.forEach(
            cargoTank -> {
              Optional.ofNullable(cargoTank.getId()).ifPresent(ruleDropDownMaster::setId);
              Optional.ofNullable(cargoTank.getValue())
                  .ifPresentOrElse(
                      ruleDropDownMaster::setValue, () -> ruleDropDownMaster.setValue(""));
              ruleInput.addRuleDropDownMaster(ruleDropDownMaster.build());
            });
      } else {
        Optional<Long> ruleTempId;
        if (isDisplayId || isDisplayVesselRuleXId) {
          ruleTempId = Optional.ofNullable(vesselRule.getTemplateId());
        } else {
          ruleTempId = Optional.ofNullable(vesselRule.getTemplateFId());
        }
        if (ruleTempId.isPresent()) {
          List<RuleVesselDropDownValues> filterMasterByRule =
              listOfDropDownValue.stream()
                  .filter(
                      rDropDown ->
                          rDropDown.getRuleTemplateXid() != null
                              && ruleTempId.get() != null
                              && rDropDown.getRuleTemplateXid().equals(ruleTempId.get()))
                  .collect(Collectors.toList());
          filterMasterByRule.forEach(
              masterDropDownRule -> {
                Optional.ofNullable(masterDropDownRule.getId())
                    .ifPresent(ruleDropDownMaster::setId);
                Optional.ofNullable(masterDropDownRule.getDropDownValue())
                    .ifPresentOrElse(
                        ruleDropDownMaster::setValue, () -> ruleDropDownMaster.setValue(""));
                ruleInput.addRuleDropDownMaster(ruleDropDownMaster.build());
              });
        }
      }
    }
    if (isDisplayId) {
      Optional.ofNullable(vesselRule.getTemplateInputId())
          .ifPresentOrElse(
              item -> ruleInput.setId(String.valueOf(item)), () -> ruleInput.setId(""));
    }
    rulesBuilder.addInputs(ruleInput.build());
    if (id == value.size() - 1) {
      Optional.ofNullable(vesselRule.getNumericPrecision())
          .ifPresentOrElse(
              rulesBuilder::setNumericPrecision, () -> rulesBuilder.setNumericPrecision(0));
      Optional.ofNullable(vesselRule.getNumericScale())
          .ifPresentOrElse(rulesBuilder::setNumericScale, () -> rulesBuilder.setNumericScale(0));
      Optional.ofNullable(vesselRule.getTemplateIsEnable())
          .ifPresentOrElse(rulesBuilder::setEnable, () -> rulesBuilder.setEnable(false));
      Optional.ofNullable(vesselRule.getTemplateDisplayInSettings())
          .ifPresentOrElse(
              rulesBuilder::setDisplayInSettings, () -> rulesBuilder.setDisplayInSettings(false));
      Optional.ofNullable(vesselRule.getTemplateId())
          .ifPresentOrElse(
              item -> rulesBuilder.setRuleTemplateId(String.valueOf(item)),
              () -> rulesBuilder.setRuleTemplateId(""));
      if (isDisplayId) {
        Optional.ofNullable(vesselRule.getId())
            .ifPresentOrElse(
                item -> rulesBuilder.setId(String.valueOf(item)), () -> rulesBuilder.setId(""));
      }
      Optional.ofNullable(vesselRule.getTemplateRuleType())
          .filter(item -> item.trim().length() != 0)
          .ifPresentOrElse(rulesBuilder::setRuleType, () -> rulesBuilder.setRuleType(""));
      Optional.ofNullable(vesselRule.getIsHardRule())
          .ifPresentOrElse(rulesBuilder::setIsHardRule, () -> rulesBuilder.setIsHardRule(false));
      if (isDisplayVesselRuleXId) {
        Optional.ofNullable(vesselRule.getId())
            .ifPresentOrElse(
                item -> rulesBuilder.setVesselRuleXId(String.valueOf(item)),
                () -> rulesBuilder.setVesselRuleXId(""));
      }
      rulePlanBuilder.addRules(rulesBuilder.build());
    }
  }

  @Autowired VesselValveEducationProcessRepository educationProcessRepository;

  @Override
  public void getVesselValveSequence(
      VesselRequest request,
      StreamObserver<com.cpdss.common.generated.VesselInfo.VesselValveSequenceReply>
          responseObserver) {
    com.cpdss.common.generated.VesselInfo.VesselValveSequenceReply.Builder builder =
        com.cpdss.common.generated.VesselInfo.VesselValveSequenceReply.newBuilder();
    try {
      builder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
      builder.addAllVvSequenceEntities(
          vesselPumpService.buildVesselValveSeqMessage(vesselValveSequenceRepository.findAll()));
      builder.addAllVvEducationEntities(
          vesselPumpService.buildVesselValveEducator(educationProcessRepository.findAll()));
    } catch (Exception e) {
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
  public void getLoadingInfoRules(
      LoadingInfoRulesRequest request, StreamObserver<LoadingInfoRulesReply> responseObserver) {
    LoadingInfoRulesReply.Builder builder = LoadingInfoRulesReply.newBuilder();
    try {
      List<RuleVesselMapping> vesselRules =
          this.ruleVesselMappingRepository.findLoadingInfoRulesByVesselId(request.getVesselId());
      if (!vesselRules.isEmpty()) {
        vesselRules.forEach(
            vesselRule -> {
              Rules.Builder ruleBuilder = Rules.newBuilder();
              Optional.ofNullable(vesselRule.getId())
                  .ifPresent(id -> ruleBuilder.setId(String.valueOf(id)));
              List<RuleVesselMappingInput> inputs =
                  this.ruleVesselMappingInputRespository.findByRuleVesselMappingAndIsActive(
                      vesselRule, true);
              inputs.forEach(
                  input -> {
                    RulesInputs.Builder inputBuilder = RulesInputs.newBuilder();
                    Optional.ofNullable(input.getPrefix()).ifPresent(inputBuilder::setPrefix);
                    Optional.ofNullable(input.getSuffix()).ifPresent(inputBuilder::setSuffix);
                    Optional.ofNullable(input.getDefaultValue())
                        .ifPresent(inputBuilder::setDefaultValue);
                    ruleBuilder.addInputs(inputBuilder.build());
                  });
              builder.addRules(ruleBuilder.build());
            });
      } else {
        List<RuleTemplate> templateRules = this.ruleTemplateRepository.findLoadingInfoRules();
        templateRules.forEach(
            templateRule -> {
              Rules.Builder ruleBuilder = Rules.newBuilder();
              Optional.ofNullable(templateRule.getId())
                  .ifPresent(id -> ruleBuilder.setId(String.valueOf(id)));
              List<RuleTemplateInput> inputs =
                  this.ruleTemplateInputRepository.findByRuleTemplateAndIsActive(
                      templateRule, true);
              inputs.forEach(
                  input -> {
                    RulesInputs.Builder inputBuilder = RulesInputs.newBuilder();
                    Optional.ofNullable(input.getPrefix()).ifPresent(inputBuilder::setPrefix);
                    Optional.ofNullable(input.getSuffix()).ifPresent(inputBuilder::setSuffix);
                    Optional.ofNullable(input.getDefaultValue())
                        .ifPresent(inputBuilder::setDefaultValue);
                    ruleBuilder.addInputs(inputBuilder.build());
                  });
              builder.addRules(ruleBuilder.build());
            });
      }
      builder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    } catch (Exception e) {
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

  /**
   * To save Rules against vessel
   *
   * @param request
   * @param vessel
   * @param listOfDropDownValue
   * @param cargoTankMaster
   * @param ruleTypeList
   */
  void saveRulesAgainstVessel(
      VesselRuleRequest request,
      Vessel vessel,
      List<RuleVesselDropDownValues> listOfDropDownValue,
      List<CargoTankMaster> cargoTankMaster,
      List<RuleType> ruleTypeList) {
    List<RuleTemplate> ruleTemplateList = ruleTemplateRepository.findByIsActive(true);
    List<RuleVesselMapping> ruleVesselMappingList = new ArrayList<>();
    request
        .getRulePlanList()
        .forEach(
            rulePlans -> {
              rulePlans
                  .getRulesList()
                  .forEach(
                      rule -> {
                        RuleVesselMapping ruleVesselMapping = new RuleVesselMapping();
                        Optional<String> isRuleTemplateIdExist =
                            Optional.ofNullable(rule.getRuleTemplateId())
                                .filter(item -> item.trim().length() != 0);
                        if (rule.getId() != null && rule.getId().trim().length() != 0) {
                          Optional<RuleVesselMapping> rVesselMapping =
                              ruleVesselMappingRepository.findById(Long.valueOf(rule.getId()));
                          if (rVesselMapping.isPresent()) {
                            ruleVesselMapping = rVesselMapping.get();
                          } else {
                            log.info("No record exist for this id in rule vessel mapping table");
                            throw new RuntimeException(
                                "No record exist for this id in rule vessel mapping table");
                          }
                        } else {
                          if (isRuleTemplateIdExist.isPresent()) {
                            Optional<RuleVesselMapping> ruleVesselMappingRecord =
                                ruleVesselMappingRepository.checkIsRuleTemplateMapped(
                                    vessel.getId(), true, Long.valueOf(rule.getRuleTemplateId()));
                            if (ruleVesselMappingRecord.isPresent()) {
                              log.info(
                                  "Duplicate row can't insert for given vessel id and rule template");
                              throw new RuntimeException(
                                  "Duplicate row can't insert for given vessel id and rule template");
                            }
                          }
                        }
                        ruleVesselMapping.setIsActive(true);
                        RuleVesselMapping finalRuleVesselMapping = ruleVesselMapping;
                        Optional.ofNullable(rule.getDisplayInSettings())
                            .ifPresentOrElse(
                                ruleVesselMapping::setDisplayInSettings,
                                () -> finalRuleVesselMapping.setDisplayInSettings(false));
                        Optional.ofNullable(rule.getNumericPrecision())
                            .ifPresent(ruleVesselMapping::setNumericPrecision);
                        Optional.ofNullable(rule.getNumericScale())
                            .ifPresent(ruleVesselMapping::setNumericScale);
                        Optional.ofNullable(rule.getEnable())
                            .ifPresentOrElse(
                                ruleVesselMapping::setIsEnable,
                                () -> finalRuleVesselMapping.setIsEnable(false));
                        Optional.ofNullable(rule.getIsHardRule())
                            .ifPresentOrElse(
                                ruleVesselMapping::setIsHardRule,
                                () -> finalRuleVesselMapping.setIsHardRule(false));
                        ruleVesselMapping.setVessel(vessel);
                        if (!CollectionUtils.isEmpty(ruleTypeList)
                            && rule.getRuleType() != null
                            && rule.getRuleType().trim() != "") {
                          Optional<RuleType> ruleType =
                              ruleTypeList.stream()
                                  .filter(
                                      rType ->
                                          rType.getRuleType().equalsIgnoreCase(rule.getRuleType()))
                                  .findAny();
                          ruleType.orElseThrow(RuntimeException::new);
                          ruleVesselMapping.setRuleType(ruleType.get());
                        } else {
                          log.info("Rule Type can't be null");
                          throw new RuntimeException("Rule type can't be null");
                        }
                        if (!CollectionUtils.isEmpty(ruleTemplateList)
                            && rule.getRuleTemplateId() != null
                            && rule.getRuleTemplateId().trim().length() != 0) {
                          Optional<RuleTemplate> ruleTemplate =
                              ruleTemplateList.stream()
                                  .filter(
                                      rTemplate ->
                                          rTemplate.getId()
                                              == Long.parseLong(rule.getRuleTemplateId()))
                                  .findAny();
                          ruleTemplate.orElseThrow(RuntimeException::new);
                          ruleVesselMapping.setRuleTemplate(ruleTemplate.get());
                        } else {
                          log.info("Template id can't be null");
                          throw new RuntimeException("Template id can't be null");
                        }
                        List<RuleVesselMappingInput> ruleVesselMappingInputList = new ArrayList<>();
                        for (RulesInputs input : rule.getInputsList()) {
                          RuleVesselMappingInput ruleTemplateInput = new RuleVesselMappingInput();
                          if (input.getId() != null && input.getId().trim().length() != 0) {
                            Optional<RuleVesselMappingInput> rTemplateInput =
                                ruleVesselMappingInputRespository.findById(
                                    Long.valueOf(input.getId()));
                            if (rTemplateInput.isPresent()) {
                              ruleTemplateInput = rTemplateInput.get();
                            } else {
                              log.info(
                                  "No record exist for this id in rule vessel mapping input table");
                              throw new RuntimeException(
                                  "No record exist for this id in rule vessel mapping input table");
                            }
                          }
                          RuleVesselMappingInput finalRuleTemplateInput1 = ruleTemplateInput;
                          Optional.ofNullable(input.getDefaultValue())
                              .filter(item -> item.trim().length() != 0)
                              .ifPresentOrElse(
                                  ruleTemplateInput::setDefaultValue,
                                  () -> finalRuleTemplateInput1.setDefaultValue(null));
                          Optional.ofNullable(input.getMax())
                              .filter(item -> item.trim().length() != 0)
                              .ifPresent(ruleTemplateInput::setMaxValue);
                          Optional.ofNullable(input.getMin())
                              .filter(item -> item.trim().length() != 0)
                              .ifPresent(ruleTemplateInput::setMinValue);
                          Optional.ofNullable(input.getSuffix())
                              .filter(item -> item.trim().length() != 0)
                              .ifPresent(ruleTemplateInput::setSuffix);
                          Optional.ofNullable(input.getPrefix())
                              .filter(item -> item.trim().length() != 0)
                              .ifPresent(ruleTemplateInput::setPrefix);
                          Optional.ofNullable(input.getType())
                              .filter(item -> item.trim().length() != 0)
                              .ifPresent(ruleTemplateInput::setTypeValue);
                          RuleVesselMappingInput finalRuleTemplateInput = ruleTemplateInput;
                          Optional.ofNullable(input.getIsMandatory())
                              .ifPresentOrElse(
                                  ruleTemplateInput::setIsMandatory,
                                  () -> finalRuleTemplateInput.setIsMandatory((false)));
                          Optional<String> isTypeDropDownOrMultiSelect =
                              Optional.ofNullable(input.getType())
                                  .filter(
                                      value ->
                                          value.trim().length() != 0
                                                  && value
                                                      .trim()
                                                      .equalsIgnoreCase(
                                                          TypeValue.DROPDOWN.getType())
                                              || value
                                                  .trim()
                                                  .equalsIgnoreCase(
                                                      TypeValue.MULTISELECT.getType()));

                          if (isTypeDropDownOrMultiSelect.isPresent()) {
                            Optional<String> isPrefixExist =
                                Optional.ofNullable(input.getPrefix())
                                    .filter(
                                        item ->
                                            item.trim().length() != 0
                                                && item.trim()
                                                    .equalsIgnoreCase(
                                                        RuleMasterData.CargoTank.getPrefix()));
                            Optional<String> isSuffixExist =
                                Optional.ofNullable(input.getSuffix())
                                    .filter(
                                        item ->
                                            item.trim().length() != 0
                                                && item.trim()
                                                    .equalsIgnoreCase(
                                                        RuleMasterData.CargoTank.getSuffix()));

                            try {
                              if (isPrefixExist.isPresent() && isSuffixExist.isPresent()) {
                                this.ruleMasterDropDownValidation(
                                    listOfDropDownValue,
                                    cargoTankMaster,
                                    true,
                                    input,
                                    ruleTemplateInput,
                                    rule);
                              } else {
                                this.ruleMasterDropDownValidation(
                                    listOfDropDownValue,
                                    cargoTankMaster,
                                    false,
                                    input,
                                    ruleTemplateInput,
                                    rule);
                              }
                            } catch (GenericServiceException e) {
                              throw new RuntimeException(
                                  "Master rule drop down value does not exist");
                            }
                          }
                          ruleTemplateInput.setIsActive(true);
                          ruleTemplateInput.setRuleVesselMapping(ruleVesselMapping);
                          ruleVesselMappingInputList.add(ruleTemplateInput);
                        }
                        ruleVesselMapping.setRuleVesselMappingInput(ruleVesselMappingInputList);
                        ruleVesselMappingList.add(ruleVesselMapping);
                      });
            });
    ruleVesselMappingRepository.saveAll(ruleVesselMappingList);
  }
}
