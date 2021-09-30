/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service.loadicator;

import static java.lang.String.valueOf;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.CargoInfo;
import com.cpdss.common.generated.CargoInfo.CargoReply;
import com.cpdss.common.generated.CargoInfoServiceGrpc;
import com.cpdss.common.generated.LoadableStudy.CargoNominationDetail;
import com.cpdss.common.generated.LoadableStudy.CargoNominationDetailReply;
import com.cpdss.common.generated.LoadableStudy.CargoNominationRequest;
import com.cpdss.common.generated.LoadableStudy.LDtrim;
import com.cpdss.common.generated.LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub;
import com.cpdss.common.generated.Loadicator.StowagePlan.Builder;
import com.cpdss.common.generated.LoadicatorServiceGrpc;
import com.cpdss.common.generated.PortInfo;
import com.cpdss.common.generated.PortInfo.PortReply;
import com.cpdss.common.generated.PortInfoServiceGrpc;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.common.generated.VesselInfo.VesselReply;
import com.cpdss.common.generated.VesselInfoServiceGrpc;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.dischargeplan.common.DischargePlanConstants;
import com.cpdss.dischargeplan.domain.algo.LDIntactStability;
import com.cpdss.dischargeplan.domain.algo.LDStrength;
import com.cpdss.dischargeplan.domain.algo.LDTrim;
import com.cpdss.dischargeplan.domain.algo.LoadicatorStage;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import com.cpdss.dischargeplan.repository.DischargeInformationRepository;

import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;

@Slf4j
@Service
@Transactional
public class LoadicatorService {

  @Value(value = "${algo.loadicatorUrl}")
  private String loadicatorUrl;

  @Value(value = "${loadingplan.attachment.rootFolder}")
  private String rootFolder;

  @Autowired DischargeInformationRepository loadingInformationRepository;

  @Autowired RestTemplate restTemplate;

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @GrpcClient("loadableStudyService")
  private LoadableStudyServiceBlockingStub loadableStudyGrpcService;

  @GrpcClient("loadicatorService")
  private LoadicatorServiceGrpc.LoadicatorServiceBlockingStub loadicatorGrpcService;

  @GrpcClient("cargoService")
  private CargoInfoServiceGrpc.CargoInfoServiceBlockingStub cargoInfoGrpcService;

  @GrpcClient("portInfoService")
  private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoGrpcService;

  /**
   * get vessel detail for loadicator
   *
   * @param loadableStudyEntity
   * @return
   * @throws GenericServiceException
   */
  public VesselInfo.VesselReply getVesselDetailsForLoadicator(DischargeInformation dischargeInformation)
      throws GenericServiceException {
    VesselInfo.VesselRequest replyBuilder =
        VesselInfo.VesselRequest.newBuilder()
            .setVesselId(dischargeInformation.getVesselXid())
            .build();
    VesselInfo.VesselReply vesselReply = this.getVesselDetailByVesselId(replyBuilder);
    if (!DischargePlanConstants.SUCCESS.equalsIgnoreCase(
        vesselReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "Error in calling vessel service",
          CommonErrorCodes.E_GEN_INTERNAL_ERR,
          HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    return vesselReply;
  }
  public VesselInfo.VesselReply getVesselDetailByVesselId(VesselInfo.VesselRequest replyBuilder) {
	    return this.vesselInfoGrpcService.getVesselDetailByVesselId(replyBuilder);
	  }

  public void buildLdStrength(com.cpdss.common.generated.LoadableStudy.LDStrength ldStrength, LoadicatorStage loadicatorStage) {
	    LDStrength strength =
	        new LDStrength();
	    strength.setBendingMomentPersentFrameNumber(ldStrength.getBendingMomentPersentFrameNumber());
	    strength.setBendingMomentPersentJudgement(ldStrength.getBendingMomentPersentJudgement());
	    strength.setBendingMomentPersentValue(ldStrength.getBendingMomentPersentValue());
	    strength.setErrorDetails(ldStrength.getErrorDetails());
	    strength.setId(ldStrength.getId());
	    strength.setInnerLongiBhdFrameNumber(ldStrength.getInnerLongiBhdFrameNumber());
	    strength.setInnerLongiBhdJudgement(ldStrength.getInnerLongiBhdJudgement());
	    strength.setInnerLongiBhdValue(ldStrength.getInnerLongiBhdValue());
	    strength.setMessageText(ldStrength.getMessageText());
	    strength.setOuterLongiBhdFrameNumber(ldStrength.getOuterLongiBhdFrameNumber());
	    strength.setOuterLongiBhdJudgement(ldStrength.getOuterLongiBhdJudgement());
	    strength.setOuterLongiBhdValue(ldStrength.getOuterLongiBhdValue());
	    strength.setSfFrameNumber(ldStrength.getSfFrameNumber());
	    strength.setSfHopperFrameNumber(ldStrength.getSfHopperFrameNumber());
	    strength.setSfHopperJudgement(ldStrength.getSfHopperJudgement());
	    strength.setSfHopperValue(ldStrength.getSfHopperValue());
	    strength.setSfSideShellFrameNumber(ldStrength.getSfSideShellFrameNumber());
	    strength.setSfSideShellJudgement(ldStrength.getSfSideShellJudgement());
	    strength.setSfSideShellValue(ldStrength.getSfSideShellValue());
	    strength.setShearingForceJudgement(ldStrength.getShearingForceJudgement());
	    strength.setShearingForcePersentValue(ldStrength.getShearingForcePersentValue());
	    loadicatorStage.setLdStrength(strength);
	  }

	  public void buildLdIntactStability(
	      com.cpdss.common.generated.LoadableStudy.LDIntactStability lDIntactStability, LoadicatorStage loadicatorStage) {
	  LDIntactStability intactStability =
	        new LDIntactStability();
	    intactStability.setAngleatmaxrleverJudgement(lDIntactStability.getAngleatmaxrleverJudgement());
	    intactStability.setAngleatmaxrleverValue(lDIntactStability.getAngleatmaxrleverValue());
	    intactStability.setAreaofStability030Judgement(
	        lDIntactStability.getAreaofStability030Judgement());
	    intactStability.setAreaofStability030Value(lDIntactStability.getAreaofStability030Value());
	    intactStability.setAreaofStability040Judgement(
	        lDIntactStability.getAreaofStability040Judgement());
	    intactStability.setAreaofStability040Value(lDIntactStability.getAreaofStability040Value());
	    intactStability.setAreaofStability3040Judgement(
	        lDIntactStability.getAreaofStability3040Judgement());
	    intactStability.setAreaofStability3040Value(lDIntactStability.getAreaofStability3040Value());
	    intactStability.setBigIntialGomJudgement(lDIntactStability.getBigIntialGomJudgement());
	    intactStability.setBigintialGomValue(lDIntactStability.getBigintialGomValue());
	    intactStability.setErrorDetails(lDIntactStability.getErrorDetails());
	    intactStability.setErrorStatus(lDIntactStability.getErrorStatus());
	    intactStability.setGmAllowableCurveCheckJudgement(
	        lDIntactStability.getGmAllowableCurveCheckJudgement());
	    intactStability.setGmAllowableCurveCheckValue(
	        lDIntactStability.getGmAllowableCurveCheckValue());
	    intactStability.setHeelBySteadyWindJudgement(lDIntactStability.getHeelBySteadyWindJudgement());
	    intactStability.setHeelBySteadyWindValue(lDIntactStability.getHeelBySteadyWindValue());
	    intactStability.setId(lDIntactStability.getId());
	    intactStability.setMaximumRightingLeverJudgement(
	        lDIntactStability.getMaximumRightingLeverJudgement());
	    intactStability.setMaximumRightingLeverValue(lDIntactStability.getMaximumRightingLeverValue());
	    intactStability.setMessageText(lDIntactStability.getMessageText());
	    intactStability.setStabilityAreaBaJudgement(lDIntactStability.getStabilityAreaBaJudgement());
	    intactStability.setStabilityAreaBaValue(lDIntactStability.getStabilityAreaBaValue());
	    loadicatorStage.setLdIntactStability(intactStability);
	  }

	  public void buildLdTrim(LDtrim ldTrim, LoadicatorStage loadicatorStage) {
	    LDTrim trim = new LDTrim();
	    trim.setAftDraftValue(ldTrim.getAftDraftValue());
	    trim.setAirDraftJudgement(ldTrim.getAirDraftJudgement());
	    trim.setAirDraftValue(ldTrim.getAirDraftValue());
	    trim.setDisplacementJudgement(ldTrim.getDisplacementJudgement());
	    trim.setDisplacementValue(ldTrim.getDisplacementValue());
	    trim.setErrorDetails(ldTrim.getErrorDetails());
	    trim.setErrorStatus(ldTrim.getErrorStatus());
	    trim.setForeDraftValue(ldTrim.getForeDraftValue());
	    trim.setHeelValue(ldTrim.getHeelValue());
	    trim.setId(ldTrim.getId());
	    trim.setMaximumAllowableJudement(ldTrim.getMaximumAllowableJudement());
	    trim.setMaximumAllowableVisibility(ldTrim.getMaximumAllowableVisibility());
	    trim.setMaximumDraftJudgement(ldTrim.getMaximumDraftJudgement());
	    trim.setMeanDraftValue(ldTrim.getMaximumDraftValue());
	    trim.setMaximumDraftValue(ldTrim.getMaximumDraftValue());
	    trim.setMeanDraftJudgement(ldTrim.getMeanDraftJudgement());
	    trim.setMeanDraftValue(ldTrim.getMeanDraftValue());
	    trim.setMessageText(ldTrim.getMessageText());
	    trim.setMinimumForeDraftInRoughWeatherJudgement(
	        ldTrim.getMinimumForeDraftInRoughWeatherJudgement());
	    trim.setMinimumForeDraftInRoughWeatherValue(ldTrim.getMinimumForeDraftInRoughWeatherValue());
	    trim.setTrimValue(ldTrim.getTrimValue());
	    trim.setDeflection(ldTrim.getDeflection());
	    loadicatorStage.setLdTrim(trim);
	  }
	  public Map<Long, CargoNominationDetail> getCargoNominationDetails(Set<Long> cargoNominationIds) {
		    Map<Long, CargoNominationDetail> details = new HashMap<>();
		    cargoNominationIds.forEach(
		        id -> {
		          CargoNominationRequest.Builder builder = CargoNominationRequest.newBuilder();
		          builder.setCargoNominationId(id);
		          CargoNominationDetailReply reply =
		              loadableStudyGrpcService.getCargoNominationByCargoNominationId(builder.build());
		          if (reply.getResponseStatus().getStatus().equals(DischargePlanConstants.SUCCESS)) {
		            log.info("Fetched details of cargo nomination with id {}", id);
		            details.put(id, reply.getCargoNominationdetail());
		          }
		        });

		    return details;
		  }
	  /**
	   * Get cargo details
	   *
	   * @param loadableStudyEntity
	   * @return
	   * @throws GenericServiceException
	   */
	  public CargoInfo.CargoReply getCargoInfoForLoadicator(DischargeInformation dischargeInformation)
	      throws GenericServiceException {
	    CargoInfo.CargoRequest cargoRequest =
	        CargoInfo.CargoRequest.newBuilder()
	            .setVesselId(dischargeInformation.getVesselXid())
	            .setVoyageId(dischargeInformation.getVoyageXid())
	            .build();
	    CargoInfo.CargoReply cargoReply = this.getCargoInfo(cargoRequest);
	    if (!DischargePlanConstants.SUCCESS.equalsIgnoreCase(
	        cargoReply.getResponseStatus().getStatus())) {
	      throw new GenericServiceException(
	          "Error in calling cargo service",
	          CommonErrorCodes.E_GEN_INTERNAL_ERR,
	          HttpStatusCode.INTERNAL_SERVER_ERROR);
	    }
	    return cargoReply;
	  }
	  
	  public CargoInfo.CargoReply getCargoInfo(CargoInfo.CargoRequest build) {
		    return cargoInfoGrpcService.getCargoInfo(build);
	  }
	  
	  /**
	   * Get port info for loadicator
	   *
	   * @param loadableStudyEntity
	   * @return
	   * @throws GenericServiceException
	   */
	  public PortInfo.PortReply getPortInfoForLoadicator(DischargeInformation loadingInformation)
	      throws GenericServiceException {
	    PortInfo.PortRequest portRequest =
	        PortInfo.PortRequest.newBuilder()
	            .setVesselId(loadingInformation.getVesselXid())
	            .setVoyageId(loadingInformation.getVoyageXid())
	            .build();
	    PortInfo.PortReply portReply = this.getPortInfo(portRequest);
	    if (!DischargePlanConstants.SUCCESS.equalsIgnoreCase(portReply.getResponseStatus().getStatus())) {
	      throw new GenericServiceException(
	          "Error in calling cargo service",
	          CommonErrorCodes.E_GEN_INTERNAL_ERR,
	          HttpStatusCode.INTERNAL_SERVER_ERROR);
	    }
	    return portReply;
	  }
	  
	  public PortInfo.PortReply getPortInfo(PortInfo.PortRequest build) {
		    return portInfoGrpcService.getPortInfo(build);
		  }
	  
	  public void buildStowagePlan(
		      DischargeInformation dischargeInformation,
		      Integer time,
		      String processId,
		      CargoReply cargoReply,
		      VesselReply vesselReply,
		      PortReply portReply,
		      Builder stowagePlanBuilder) {
		    stowagePlanBuilder.setProcessId(processId);
		    VesselInfo.VesselDetail vessel = vesselReply.getVesselsList().get(0);
		    Optional.ofNullable(vessel.getId()).ifPresent(stowagePlanBuilder::setVesselId);
		    Optional.ofNullable(vessel.getImoNumber()).ifPresent(stowagePlanBuilder::setImoNumber);
		    Optional.ofNullable(vessel.getTypeOfShip()).ifPresent(stowagePlanBuilder::setShipType);
		    Optional.ofNullable(vessel.getCode()).ifPresent(stowagePlanBuilder::setVesselCode);
		    Optional.ofNullable(vessel.getProvisionalConstant())
		        .ifPresent(stowagePlanBuilder::setProvisionalConstant);
		    Optional.ofNullable(vessel.getDeadweightConstant())
		        .ifPresent(stowagePlanBuilder::setDeadweightConstant);
		    stowagePlanBuilder.setPortId(dischargeInformation.getPortXid());
		    stowagePlanBuilder.setStowageId(time);
		    stowagePlanBuilder.setStatus(1);
		    stowagePlanBuilder.setBookingListId(dischargeInformation.getId());
		    Optional<PortInfo.PortDetail> portDetail =
		        portReply.getPortsList().stream()
		            .filter(port -> Long.valueOf(port.getId()).equals(dischargeInformation.getPortXid()))
		            .findAny();
		    if (portDetail.isPresent()) {
		      Optional.ofNullable(portDetail.get().getCode()).ifPresent(stowagePlanBuilder::setPortCode);
		      Optional.ofNullable(portDetail.get().getWaterDensity())
		          .ifPresent(density -> stowagePlanBuilder.setSeaWaterDensity(valueOf(density)));
		    }
		    stowagePlanBuilder.setSynopticalId(dischargeInformation.getSynopticTableXid());
		  }

}
