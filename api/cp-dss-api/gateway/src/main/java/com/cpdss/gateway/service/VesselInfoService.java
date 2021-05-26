/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service;

import static org.springframework.util.StringUtils.isEmpty;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.common.generated.VesselInfo.LoadLineDetail;
import com.cpdss.common.generated.VesselInfo.VesselAlgoReply;
import com.cpdss.common.generated.VesselInfo.VesselAlgoRequest;
import com.cpdss.common.generated.VesselInfo.VesselDetail;
import com.cpdss.common.generated.VesselInfo.VesselReply;
import com.cpdss.common.generated.VesselInfo.VesselRequest;
import com.cpdss.common.generated.VesselInfoServiceGrpc.VesselInfoServiceBlockingStub;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.BMAndSF;
import com.cpdss.gateway.domain.BendingMoment;
import com.cpdss.gateway.domain.CalculationSheet;
import com.cpdss.gateway.domain.CalculationSheetTankGroup;
import com.cpdss.gateway.domain.HydrostaticData;
import com.cpdss.gateway.domain.InnerBulkHeadValues;
import com.cpdss.gateway.domain.LoadLine;
import com.cpdss.gateway.domain.MinMaxValuesForBMAndSf;
import com.cpdss.gateway.domain.ShearingForce;
import com.cpdss.gateway.domain.StationValues;
import com.cpdss.gateway.domain.UllageDetails;
import com.cpdss.gateway.domain.UllageTrimCorrection;
import com.cpdss.gateway.domain.Vessel;
import com.cpdss.gateway.domain.VesselDetailsResponse;
import com.cpdss.gateway.domain.VesselDraftCondition;
import com.cpdss.gateway.domain.VesselResponse;
import com.cpdss.gateway.domain.VesselTank;
import com.cpdss.gateway.domain.VesselTankTCG;
import com.cpdss.gateway.entity.Users;
import com.cpdss.gateway.repository.UsersRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * Service class for vessel related operations
 *
 * @author suhail.k
 */
@Service
@Log4j2
public class VesselInfoService {

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @Autowired private UsersRepository usersRepository;

  private static final String SUCCESS = "SUCCESS";

  /**
   * Get vessels by company
   *
   * @param companyId
   * @param correlationId
   * @return
   * @throws GenericServiceException
   */
  public VesselResponse getVesselsByCompany(Long companyId, String correlationId)
      throws GenericServiceException {
    log.info("Inside getVesselsByCompany, correlationId:{}", correlationId);
    VesselReply reply =
        this.getVesselsByCompany(VesselRequest.newBuilder().setCompanyId(companyId).build());

    if (!SUCCESS.equals(reply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "failed to fetch vessels",
          reply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(reply.getResponseStatus().getCode())));
    }
    VesselResponse response = this.createVesselResponse(reply);
    response.setResponseStatus(
        new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
    return response;
  }

  /**
   * Create vessel response from grpc reply
   *
   * @param reply
   * @return
   * @throws GenericServiceException
   */
  private VesselResponse createVesselResponse(VesselReply reply) throws GenericServiceException {
    VesselResponse response = new VesselResponse();
    response.setVessels(new ArrayList<>());
    Set<Long> userIdList =
        reply.getVesselsList().stream()
            .map(VesselDetail::getCheifOfficerId)
            .collect(Collectors.toSet());
    userIdList.addAll(
        reply.getVesselsList().stream()
            .map(VesselDetail::getCaptainId)
            .collect(Collectors.toSet()));
    List<Users> userList = this.usersRepository.findByIdIn(new ArrayList<>(userIdList));
    for (VesselDetail grpcReply : reply.getVesselsList()) {
      Vessel vessel = new Vessel();
      vessel.setId(grpcReply.getId());
      vessel.setName(grpcReply.getName());
      vessel.setImoNumber(grpcReply.getImoNumber());
      vessel.setFlagPath(grpcReply.getFlag());
      vessel.setCaptainId(grpcReply.getCaptainId());
      vessel.setChiefOfficerId(grpcReply.getCheifOfficerId());
      vessel.setCharterer(grpcReply.getCharterer());
      Optional<Users> userOpt =
          userList.stream().filter(item -> item.getId().equals(vessel.getCaptainId())).findAny();
      if (!userOpt.isPresent()) {
        throw new GenericServiceException(
            "Captain info not found in database",
            CommonErrorCodes.E_GEN_INTERNAL_ERR,
            HttpStatusCode.INTERNAL_SERVER_ERROR);
      }
      vessel.setCaptainName(userOpt.get().getFirstName() + " " + userOpt.get().getLastName());
      userOpt =
          userList.stream()
              .filter(item -> item.getId().equals(vessel.getChiefOfficerId()))
              .findAny();
      if (!userOpt.isPresent()) {
        throw new GenericServiceException(
            "Chief officer info not found in database",
            CommonErrorCodes.E_GEN_INTERNAL_ERR,
            HttpStatusCode.INTERNAL_SERVER_ERROR);
      }
      vessel.setChiefOfficerName(userOpt.get().getFirstName() + " " + userOpt.get().getLastName());
      vessel.setLoadlines(new ArrayList<>());
      for (LoadLineDetail detail : grpcReply.getLoadLinesList()) {
        LoadLine loadLine = new LoadLine();
        loadLine.setId(detail.getId());
        loadLine.setName(detail.getName());
        loadLine.setDraftMarks(
            detail.getDraftMarksList().stream().map(BigDecimal::new).collect(Collectors.toList()));
        vessel.getLoadlines().add(loadLine);
      }
      response.getVessels().add(vessel);
    }
    return response;
  }

  /**
   * Call grpc service for vessel info
   *
   * @param request
   * @return
   */
  public VesselReply getVesselsByCompany(VesselRequest request) {
    return this.vesselInfoGrpcService.getAllVesselsByCompany(request);
  }

  /**
   * @param vesselId
   * @param first
   * @return VesselDetailsResponse
   */
  public VesselDetailsResponse getVesselsDetails(Long vesselId, String correlationId)
      throws GenericServiceException {
    VesselDetailsResponse vesselDetailsResponse = new VesselDetailsResponse();
    VesselAlgoRequest vesselAlgoRequest =
        VesselAlgoRequest.newBuilder().setVesselId(vesselId).build();
    VesselAlgoReply vesselAlgoReply = this.getVesselsDetails(vesselAlgoRequest);
    if (!SUCCESS.equals(vesselAlgoReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "failed to get Vessel Details ",
          vesselAlgoReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(vesselAlgoReply.getResponseStatus().getCode())));
    }
    vesselDetailsResponse.setVessel(
        this.buildVesselDetailsResponse(vesselAlgoReply, correlationId));
    vesselDetailsResponse.setVesselDraftCondition(
        this.buildVesselDraftconditionResponse(vesselAlgoReply, correlationId));
    vesselDetailsResponse.setVesselTanks(
        this.buildVesselTankListResponse(vesselAlgoReply, correlationId));
    vesselDetailsResponse.setHydrostaticDatas(
        this.buildHydrostaticResponse(vesselAlgoReply, correlationId));
    vesselDetailsResponse.setVesselTankTCGs(
        this.buildVesselTankTcgResponse(vesselAlgoReply, correlationId));
    BMAndSF bmAndSF = new BMAndSF();
    bmAndSF.setBendingMoment(this.createBendingMomentResponse(vesselAlgoReply, correlationId));
    bmAndSF.setShearingForce(this.createShearingForceResponse(vesselAlgoReply, correlationId));
    bmAndSF.setCalculationSheet(
        this.createCalculationSheetResponse(vesselAlgoReply, correlationId));
    bmAndSF.setCalculationSheetTankGroup(
        this.createCalculationSheetTankGroupResponse(vesselAlgoReply, correlationId));
    bmAndSF.setMinMaxValuesForBMAndSfs(
        this.createMinMaxValuesForBMAndSfResponse(vesselAlgoReply, correlationId));
    bmAndSF.setStationValues(this.createStationValues(vesselAlgoReply, correlationId));
    bmAndSF.setInnerBulkHeadValues(this.createInnerBulkHeadValues(vesselAlgoReply, correlationId));
    vesselDetailsResponse.setBMAndSF(bmAndSF);
    vesselDetailsResponse.setUllageDetails(createVesselUllageDetails(vesselAlgoReply));
    vesselDetailsResponse.setResponseStatus(
        new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
    vesselDetailsResponse.setUllageTrimCorrections(
        this.buildUllageTrimCorrections(vesselAlgoReply));
    return vesselDetailsResponse;
  }

  /**
   * Build ullage trim correction list
   *
   * @param vesselAlgoReply
   * @return
   */
  private List<UllageTrimCorrection> buildUllageTrimCorrections(VesselAlgoReply vesselAlgoReply) {
    List<UllageTrimCorrection> list = new ArrayList<>();
    for (com.cpdss.common.generated.VesselInfo.UllageTrimCorrection dto :
        vesselAlgoReply.getUllageTrimCorrectionList()) {
      UllageTrimCorrection ullageTrimCorrection = new UllageTrimCorrection();
      ullageTrimCorrection.setId(dto.getId());
      ullageTrimCorrection.setTankId(dto.getTankId());
      ullageTrimCorrection.setUllageDepth(
          isEmpty(dto.getUllageDepth()) ? null : dto.getUllageDepth());
      ullageTrimCorrection.setTrimM1(isEmpty(dto.getTrimM1()) ? null : dto.getTrimM1());
      ullageTrimCorrection.setTrimM2(isEmpty(dto.getTrimM1()) ? null : dto.getTrimM2());
      ullageTrimCorrection.setTrimM3(isEmpty(dto.getTrimM1()) ? null : dto.getTrimM3());
      ullageTrimCorrection.setTrimM4(isEmpty(dto.getTrimM1()) ? null : dto.getTrimM4());
      ullageTrimCorrection.setTrimM5(isEmpty(dto.getTrimM1()) ? null : dto.getTrimM5());
      ullageTrimCorrection.setTrim0(isEmpty(dto.getTrim0()) ? null : dto.getTrim0());
      ullageTrimCorrection.setTrim1(isEmpty(dto.getTrim1()) ? null : dto.getTrim1());
      ullageTrimCorrection.setTrim2(isEmpty(dto.getTrim2()) ? null : dto.getTrim2());
      ullageTrimCorrection.setTrim3(isEmpty(dto.getTrim3()) ? null : dto.getTrim3());
      ullageTrimCorrection.setTrim4(isEmpty(dto.getTrim4()) ? null : dto.getTrim4());
      ullageTrimCorrection.setTrim5(isEmpty(dto.getTrim5()) ? null : dto.getTrim5());
      ullageTrimCorrection.setTrim6(isEmpty(dto.getTrim6()) ? null : dto.getTrim6());
      list.add(ullageTrimCorrection);
    }
    return list;
  }

  /**
   * @param vesselAlgoReply
   * @return List<UllageDetails>
   */
  private List<UllageDetails> createVesselUllageDetails(VesselAlgoReply vesselAlgoReply) {
    List<UllageDetails> ullageDetails = new ArrayList<UllageDetails>();
    vesselAlgoReply
        .getUllageDetailsList()
        .forEach(
            ullage -> {
              UllageDetails ullageDetail = new UllageDetails();
              ullageDetail.setId(ullage.getId());
              ullageDetail.setTankId(ullage.getTankId());
              ullageDetail.setUllageDepth(ullage.getUllageDepth());
              ullageDetail.setEvenKeelCapacityCubm(ullage.getEvenKeelCapacityCubm());
              ullageDetail.setSoundDepth(ullage.getSoundDepth());
              ullageDetails.add(ullageDetail);
            });
    return ullageDetails;
  }

  /**
   * @param vesselAlgoReply
   * @param correlationId
   * @return Object
   */
  private List<InnerBulkHeadValues> createInnerBulkHeadValues(
      VesselAlgoReply vesselAlgoReply, String correlationId) {
    List<InnerBulkHeadValues> innerBulkHeadSFValues = new ArrayList<InnerBulkHeadValues>();
    vesselAlgoReply
        .getBMAndSF()
        .getInnerBulkHeadSFList()
        .forEach(
            innerBulkHeadSFValue -> {
              InnerBulkHeadValues values = new InnerBulkHeadValues();
              values.setId(innerBulkHeadSFValue.getId());
              values.setFrameNumber(innerBulkHeadSFValue.getFrameNumber());
              values.setForeAlpha(innerBulkHeadSFValue.getForeAlpha());
              values.setForeCenterCargoTankId(innerBulkHeadSFValue.getForeCenterCargoTankId());
              values.setForeC1(innerBulkHeadSFValue.getForeC1());
              values.setForeWingTankIds(innerBulkHeadSFValue.getForeWingTankIds());
              values.setForeC2(innerBulkHeadSFValue.getForeC2());
              values.setForeBallstTanks(innerBulkHeadSFValue.getForeBallastTanks());
              values.setForeC3(innerBulkHeadSFValue.getForeC3());
              values.setForeBWCorrection(innerBulkHeadSFValue.getForeBWCorrection());
              values.setForeC4(innerBulkHeadSFValue.getForeC4());
              values.setForeMaxAllowence(innerBulkHeadSFValue.getForeMaxAllowence());
              values.setForeMinAllowence(innerBulkHeadSFValue.getForeMinAllowence());
              values.setAftAlpha(innerBulkHeadSFValue.getAftAlpha());
              values.setAftCenterCargoTankId(innerBulkHeadSFValue.getAftCenterCargoTankId());
              values.setAftC1(innerBulkHeadSFValue.getAftC1());
              values.setAftWingTankIds(innerBulkHeadSFValue.getAftWingTankIds());
              values.setAftC2(innerBulkHeadSFValue.getAftC2());
              values.setAftBallstTanks(innerBulkHeadSFValue.getAftBallastTanks());
              values.setAftC3(innerBulkHeadSFValue.getAftC3());
              values.setAftBWCorrection(innerBulkHeadSFValue.getAftBWCorrection());
              values.setAftC4(innerBulkHeadSFValue.getAftC4());
              values.setAftMaxFlAllowence(innerBulkHeadSFValue.getAftMaxFlAllowence());
              values.setAftMinFlAllowence(innerBulkHeadSFValue.getAftMinFlAllowence());
              innerBulkHeadSFValues.add(values);
            });
    return innerBulkHeadSFValues;
  }

  /**
   * @param vesselAlgoReply
   * @param correlationId
   * @return Object
   */
  private List<StationValues> createStationValues(
      VesselAlgoReply vesselAlgoReply, String correlationId) {
    List<StationValues> stationValues = new ArrayList<StationValues>();
    vesselAlgoReply
        .getBMAndSF()
        .getStationValuesList()
        .forEach(
            stationValue -> {
              StationValues values = new StationValues();
              values.setId(stationValue.getId());
              values.setFrameNumberFrom(stationValue.getFrameNumberFrom());
              values.setFrameNumberTo(stationValue.getFrameNumberTo());
              values.setStationFrom(stationValue.getStationFrom());
              values.setStationTo(stationValue.getStationTo());
              values.setDistance(stationValue.getDistance());
              stationValues.add(values);
            });
    return stationValues;
  }

  /**
   * @param vesselAlgoReply
   * @param correlationId
   * @return List<MinMaxValuesForBMAndSf>
   */
  private List<MinMaxValuesForBMAndSf> createMinMaxValuesForBMAndSfResponse(
      VesselAlgoReply vesselAlgoReply, String correlationId) {
    List<MinMaxValuesForBMAndSf> minMaxValuesForBMAndSfs = new ArrayList<MinMaxValuesForBMAndSf>();
    vesselAlgoReply
        .getBMAndSF()
        .getMinMaxValuesForBMAndSfList()
        .forEach(
            minMaxValuesForBMAndSf -> {
              MinMaxValuesForBMAndSf minMaxBmSf = new MinMaxValuesForBMAndSf();
              minMaxBmSf.setId(minMaxValuesForBMAndSf.getId());
              minMaxBmSf.setFrameNumber(minMaxValuesForBMAndSf.getFrameNumber());
              minMaxBmSf.setMinBm(minMaxValuesForBMAndSf.getMinBm());
              minMaxBmSf.setMinSf(minMaxValuesForBMAndSf.getMinSf());
              minMaxBmSf.setMaxBm(minMaxValuesForBMAndSf.getMaxBm());
              minMaxBmSf.setMaxSf(minMaxValuesForBMAndSf.getMaxSf());
              minMaxValuesForBMAndSfs.add(minMaxBmSf);
            });
    return minMaxValuesForBMAndSfs;
  }

  /**
   * @param vesselAlgoReply
   * @param correlationId
   * @return List<CalculationSheetTankGroup>
   */
  private List<CalculationSheetTankGroup> createCalculationSheetTankGroupResponse(
      VesselAlgoReply vesselAlgoReply, String correlationId) {
    List<CalculationSheetTankGroup> calculationSheetTankGroups =
        new ArrayList<CalculationSheetTankGroup>();
    vesselAlgoReply
        .getBMAndSF()
        .getCalculationSheetTankGroupList()
        .forEach(
            calculationSheetTankGroup -> {
              CalculationSheetTankGroup cstg = new CalculationSheetTankGroup();
              cstg.setId(calculationSheetTankGroup.getId());
              cstg.setTankGroup(calculationSheetTankGroup.getTankGroup());
              cstg.setLcg(calculationSheetTankGroup.getLcg());
              cstg.setFrameNumber(calculationSheetTankGroup.getFrameNumber());
              calculationSheetTankGroups.add(cstg);
            });
    return calculationSheetTankGroups;
  }

  /**
   * @param vesselAlgoReply
   * @param correlationId
   * @return List<CalculationSheet>
   */
  private List<CalculationSheet> createCalculationSheetResponse(
      VesselAlgoReply vesselAlgoReply, String correlationId) {
    List<CalculationSheet> calculationSheets = new ArrayList<CalculationSheet>();
    vesselAlgoReply
        .getBMAndSF()
        .getCalculationSheetList()
        .forEach(
            calculationSheet -> {
              CalculationSheet cs = new CalculationSheet();
              cs.setId(calculationSheet.getId());
              cs.setTankGroup(calculationSheet.getTankGroup());
              cs.setTankId(calculationSheet.getTankId());
              cs.setWeightRatio(calculationSheet.getWeightRatio());
              cs.setLcg(calculationSheet.getLcg());

              calculationSheets.add(cs);
            });
    return calculationSheets;
  }

  /**
   * @param vesselAlgoReply
   * @param correlationId
   * @return List<ShearingForce>
   */
  private List<ShearingForce> createShearingForceResponse(
      VesselAlgoReply vesselAlgoReply, String correlationId) {
    List<ShearingForce> shearingForces = new ArrayList<ShearingForce>();
    vesselAlgoReply
        .getBMAndSF()
        .getShearingForceList()
        .forEach(
            shearingForce -> {
              ShearingForce sf = new ShearingForce();
              sf.setId(shearingForce.getId());
              sf.setFrameNumber(shearingForce.getFrameNumber());
              sf.setId(shearingForce.getId());
              sf.setBaseValue(shearingForce.getBaseValue());
              sf.setBaseDraft(shearingForce.getBaseDraft());
              sf.setDraftCorrection(shearingForce.getDraftCorrection());
              sf.setTrimCorrection(shearingForce.getTrimCorrection());
              shearingForces.add(sf);
            });
    return shearingForces;
  }

  /**
   * @param vesselAlgoReply
   * @param correlationId
   * @return List<BendingMoment>
   */
  private List<BendingMoment> createBendingMomentResponse(
      VesselAlgoReply vesselAlgoReply, String correlationId) {
    List<BendingMoment> bendingMoments = new ArrayList<BendingMoment>();
    vesselAlgoReply
        .getBMAndSF()
        .getBendingMomentList()
        .forEach(
            bendingMoment -> {
              BendingMoment bm = new BendingMoment();
              bm.setId(bendingMoment.getId());
              bm.setFrameNumber(bendingMoment.getFrameNumber());
              bm.setId(bendingMoment.getId());
              bm.setBaseValue(bendingMoment.getBaseValue());
              bm.setBaseDraft(bendingMoment.getBaseDraft());
              bm.setDraftCorrection(bendingMoment.getDraftCorrection());
              bm.setTrimCorrection(bendingMoment.getTrimCorrection());
              bendingMoments.add(bm);
            });
    return bendingMoments;
  }

  /**
   * @param vesselAlgoReply
   * @param correlationId
   * @return List<VesselTankTCG>
   */
  private List<VesselTankTCG> buildVesselTankTcgResponse(
      VesselAlgoReply vesselAlgoReply, String correlationId) {
    List<VesselTankTCG> vesselTankTCGs = new ArrayList<VesselTankTCG>();
    vesselAlgoReply
        .getVesselTankTCGList()
        .forEach(
            vesselTankTCG -> {
              VesselTankTCG tankTCG = new VesselTankTCG();
              tankTCG.setCapacity(vesselTankTCG.getCapacity());
              tankTCG.setId(vesselTankTCG.getId());
              tankTCG.setTankId(vesselTankTCG.getTankId());
              tankTCG.setTcg(vesselTankTCG.getTcg());
              vesselTankTCGs.add(tankTCG);
            });
    return vesselTankTCGs;
  }

  /**
   * @param vesselAlgoReply
   * @param correlationId
   * @return List<HydrostaticData>
   */
  private List<HydrostaticData> buildHydrostaticResponse(
      VesselAlgoReply vesselAlgoReply, String correlationId) {

    List<HydrostaticData> hydrostaticDatas = new ArrayList<HydrostaticData>();
    vesselAlgoReply
        .getHydrostaticDataList()
        .forEach(
            data -> {
              HydrostaticData hydrostaticData = new HydrostaticData();
              hydrostaticData.setDisplacement(data.getDisplacement());
              hydrostaticData.setDraft(data.getDraft());
              hydrostaticData.setId(data.getId());
              hydrostaticData.setLcb(data.getLcb());
              hydrostaticData.setLcf(data.getLcf());
              hydrostaticData.setLkm(data.getLkm());
              hydrostaticData.setTkm(data.getTkm());
              hydrostaticData.setTrim(data.getTrim());
              hydrostaticData.setVcb(data.getVcb());
              hydrostaticData.setMtc(data.getMtc());
              hydrostaticDatas.add(hydrostaticData);
            });

    return hydrostaticDatas;
  }

  /**
   * @param vesselAlgoReply
   * @param correlationId
   * @return List<VesselTank>
   */
  private List<VesselTank> buildVesselTankListResponse(
      VesselAlgoReply vesselAlgoReply, String correlationId) {
    List<VesselTank> vesselTanks = new ArrayList<VesselTank>();
    vesselAlgoReply
        .getVesselTankDetailList()
        .forEach(
            vesselTank -> {
              VesselTank tank = new VesselTank();
              tank.setCategoryId(vesselTank.getTankCategoryId());
              tank.setCategoryName(vesselTank.getTankCategoryName());
              tank.setCoatingTypeId(vesselTank.getCoatingTypeId());
              tank.setDensity(
                  vesselTank.getDensity().equals("")
                      ? null
                      : new BigDecimal(vesselTank.getDensity()));
              tank.setFillCapcityCubm(
                  vesselTank.getFillCapacityCubm().equals("")
                      ? new BigDecimal(0)
                      : new BigDecimal(vesselTank.getFillCapacityCubm()));
              tank.setFullCapcityCubm(
                  vesselTank.getFullCapacityCubm().equals("")
                      ? null
                      : new BigDecimal(vesselTank.getFullCapacityCubm()));
              tank.setFrameNumberFrom(vesselTank.getFrameNumberFrom());
              tank.setFrameNumberTo(vesselTank.getFrameNumberTo());
              tank.setId(vesselTank.getTankId());
              tank.setLcg(vesselTank.getLcg());
              tank.setName(vesselTank.getTankName());
              tank.setShortName(vesselTank.getShortName());
              tank.setTcg(vesselTank.getTcg());
              tank.setVcg(vesselTank.getVcg());
              tank.setIsLoadicatorUsing(vesselTank.getIsLoadicatorUsing());
              vesselTanks.add(tank);
            });
    return vesselTanks;
  }

  /**
   * @param vesselAlgoReply
   * @param correlationId
   * @return List<VesselDraftCondition>
   */
  private List<VesselDraftCondition> buildVesselDraftconditionResponse(
      VesselAlgoReply vesselAlgoReply, String correlationId) {
    List<VesselDraftCondition> vesselDraftConditions = new ArrayList<VesselDraftCondition>();
    vesselAlgoReply
        .getVesselDraftConditionList()
        .forEach(
            vesselDraftCondition -> {
              VesselDraftCondition draftCondition = new VesselDraftCondition();
              draftCondition.setDeadWeight(vesselDraftCondition.getDeadWeight());
              draftCondition.setDepth(vesselDraftCondition.getDepth());
              draftCondition.setDisplacement(vesselDraftCondition.getDisplacement());
              draftCondition.setDraftConditionId(vesselDraftCondition.getDraftConditionId());
              draftCondition.setDraftExtreme(vesselDraftCondition.getDraftExtreme());
              draftCondition.setFreeboard(vesselDraftCondition.getFreeboard());
              draftCondition.setId(vesselDraftCondition.getId());
              vesselDraftConditions.add(draftCondition);
            });
    return vesselDraftConditions;
  }

  /**
   * @param vesselAlgoReply
   * @param correlationId
   * @return VesselDetailsResponse
   */
  private Vessel buildVesselDetailsResponse(VesselAlgoReply vesselAlgoReply, String correlationId) {
    Vessel vessel = new Vessel();
    vessel.setName(vesselAlgoReply.getVesselDetail().getName());
    vessel.setImoNumber(vesselAlgoReply.getVesselDetail().getImoNumber());
    vessel.setCode(vesselAlgoReply.getVesselDetail().getCode());
    vessel.setPortOfRegistry(vesselAlgoReply.getVesselDetail().getPortOfRegistry());
    vessel.setBuilder(vesselAlgoReply.getVesselDetail().getBuilder());
    vessel.setOfficialNumber(vesselAlgoReply.getVesselDetail().getOfficialNumber());
    vessel.setSignalLetter(vesselAlgoReply.getVesselDetail().getSignalLetter());
    vessel.setNavigationAreaId(vesselAlgoReply.getVesselDetail().getNavigationAreaId());
    vessel.setTypeOfShip(vesselAlgoReply.getVesselDetail().getTypeOfShip());
    vessel.setRegisterLength(vesselAlgoReply.getVesselDetail().getRegisterLength());
    vessel.setLengthOverall(vesselAlgoReply.getVesselDetail().getLengthOverall());
    vessel.setLengthBetweenPerpendiculars(
        vesselAlgoReply.getVesselDetail().getLengthBetweenPerpendiculars());
    vessel.setBreadthMolded(vesselAlgoReply.getVesselDetail().getBreadthMolded());
    vessel.setDepthMolded(vesselAlgoReply.getVesselDetail().getDepthMolded());
    vessel.setDesignedLoaddraft(vesselAlgoReply.getVesselDetail().getDesignedLoaddraft());
    vessel.setDraftFullLoadSummer(vesselAlgoReply.getVesselDetail().getDraftFullLoadSummer());
    vessel.setThicknessOfUpperDeckStringerPlate(
        vesselAlgoReply.getVesselDetail().getThicknessOfUpperDeckStringerPlate());
    vessel.setThicknessOfKeelplate(vesselAlgoReply.getVesselDetail().getThicknessOfKeelplate());
    vessel.setDeadweight(vesselAlgoReply.getVesselDetail().getDeadweight());
    vessel.setLightweight(vesselAlgoReply.getVesselDetail().getLightweight());
    vessel.setLcg(vesselAlgoReply.getVesselDetail().getLcg());
    vessel.setKeelToMastHeight(vesselAlgoReply.getVesselDetail().getKeelToMastHeight());
    vessel.setDeadweightConstant(vesselAlgoReply.getVesselDetail().getDeadweightConstant());
    vessel.setProvisionalConstant(vesselAlgoReply.getVesselDetail().getProvisionalConstant());
    vessel.setDeadweightConstantLcg(vesselAlgoReply.getVesselDetail().getDeadweightConstantLcg());
    vessel.setProvisionalConstantLcg(vesselAlgoReply.getVesselDetail().getProvisionalConstantLcg());
    vessel.setGrossTonnage(vesselAlgoReply.getVesselDetail().getGrossTonnage());
    vessel.setNetTonnage(vesselAlgoReply.getVesselDetail().getNetTonnage());
    vessel.setDeadweightConstantTcg(vesselAlgoReply.getVesselDetail().getDeadweightConstantTcg());
    vessel.setFrameSpace3l(vesselAlgoReply.getVesselDetail().getFrameSpace3L());
    vessel.setFrameSpace7l(vesselAlgoReply.getVesselDetail().getFrameSpace7L());
    vessel.setHasLoadicator(vesselAlgoReply.getVesselDetail().getHasLoadicator());
    return vessel;
  }

  /**
   * @param vesselAlgoRequest
   * @return VesselAlgoReply
   */
  public VesselAlgoReply getVesselsDetails(VesselAlgoRequest vesselAlgoRequest) {
    return vesselInfoGrpcService.getVesselDetailsForAlgo(vesselAlgoRequest);
  }

  public VesselInfo.VesselPumpsResponse getVesselPumpsFromVesselInfo(Long vesselId) {
    VesselInfo.VesselPumpsResponse response =
        this.vesselInfoGrpcService.getVesselPumpsByVesselId(
            VesselInfo.VesselIdRequest.newBuilder().setVesselId(vesselId).build());
    log.info("Vessel Info RPC call for Pump list, Vessel Id {}", vesselId);
    if (response.getResponseStatus().getStatus().equalsIgnoreCase(SUCCESS)) {
      return response;
    } else {
      log.error("Vessel Info RPC call Failed, {}", response.getResponseStatus().getMessage());
      return null;
    }
  }
}
