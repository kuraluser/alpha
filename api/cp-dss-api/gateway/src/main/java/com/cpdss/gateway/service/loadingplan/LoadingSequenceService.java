/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.loadingplan;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.LoadableStudy.CargoNominationDetail;
import com.cpdss.common.generated.LoadableStudy.CargoNominationDetailReply;
import com.cpdss.common.generated.LoadableStudy.CargoNominationRequest;
import com.cpdss.common.generated.LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub;
import com.cpdss.common.generated.VesselInfo.PumpType;
import com.cpdss.common.generated.VesselInfo.VesselIdRequest;
import com.cpdss.common.generated.VesselInfo.VesselPumpsResponse;
import com.cpdss.common.generated.VesselInfo.VesselReply;
import com.cpdss.common.generated.VesselInfo.VesselRequest;
import com.cpdss.common.generated.VesselInfo.VesselTankDetail;
import com.cpdss.common.generated.VesselInfoServiceGrpc.VesselInfoServiceBlockingStub;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanStabilityParameters;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanTankDetails;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingRate;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequence;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequenceReply;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.PumpOperation;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.common.GatewayConstants;
import com.cpdss.gateway.domain.loadingplan.sequence.Ballast;
import com.cpdss.gateway.domain.loadingplan.sequence.BallastPump;
import com.cpdss.gateway.domain.loadingplan.sequence.Cargo;
import com.cpdss.gateway.domain.loadingplan.sequence.CargoLoadingRate;
import com.cpdss.gateway.domain.loadingplan.sequence.FlowRate;
import com.cpdss.gateway.domain.loadingplan.sequence.LoadingSequenceResponse;
import com.cpdss.gateway.domain.loadingplan.sequence.PumpCategory;
import com.cpdss.gateway.domain.loadingplan.sequence.StabilityParam;
import com.cpdss.gateway.domain.loadingplan.sequence.TankCategory;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
public class LoadingSequenceService {

  @GrpcClient("vesselInfoService")
  VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @GrpcClient("loadableStudyService")
  LoadableStudyServiceBlockingStub loadableStudyGrpcService;

  public void buildLoadingSequence(
      Long vesselId, LoadingSequenceReply reply, LoadingSequenceResponse response)
      throws GenericServiceException {
    List<VesselTankDetail> vesselTanks = this.getVesselTanks(vesselId);
    Set<Long> cargoNominationIds =
        reply.getLoadingSequencesList().stream()
            .map(sequence -> sequence.getCargoNominationId())
            .collect(Collectors.toSet());
    Map<Long, CargoNominationDetail> cargoNomDetails =
        this.getCargoNominationDetails(cargoNominationIds);
    List<Cargo> cargos = new ArrayList<Cargo>();
    List<Ballast> ballasts = new ArrayList<Ballast>();
    List<BallastPump> ballastPumps = new ArrayList<BallastPump>();
    List<BallastPump> gravity = new ArrayList<BallastPump>();
    List<CargoLoadingRate> cargoLoadingRates = new ArrayList<>();
    List<LoadingRate> loadingRates = new ArrayList<>();
    List<Long> stageTickPositions = new ArrayList<>();
    List<StabilityParam> stabilityParams = new ArrayList<>();

    StabilityParam foreDraft = new StabilityParam();
    foreDraft.setName("fore_draft");
    foreDraft.setData(new ArrayList<>());
    StabilityParam aftDraft = new StabilityParam();
    aftDraft.setName("aft_draft");
    aftDraft.setData(new ArrayList<>());
    StabilityParam trim = new StabilityParam();
    trim.setName("trim");
    trim.setData(new ArrayList<>());
    StabilityParam ukc = new StabilityParam();
    ukc.setName("ukc");
    ukc.setData(new ArrayList<>());
    StabilityParam gm = new StabilityParam();
    gm.setName("gm");
    gm.setData(new ArrayList<>());
    StabilityParam bm = new StabilityParam();
    bm.setName("bm");
    bm.setData(new ArrayList<>());
    StabilityParam sf = new StabilityParam();
    sf.setName("sf");
    sf.setData(new ArrayList<>());
    stabilityParams.addAll(Arrays.asList(foreDraft, aftDraft, trim, ukc, gm, sf, bm));
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd'T'HH:mm");
    try {
      response.setMinXAxisValue(
          StringUtils.isEmpty(reply.getStartDate()) ? new Date() : sdf.parse(reply.getStartDate()));
    } catch (ParseException e) {
      e.printStackTrace();
      response.setMinXAxisValue(new Date());
    }

    Long portEta = response.getMinXAxisValue().toInstant().toEpochMilli();
    Integer start = 0;
    Integer temp = 0;
        
    log.info("Populating Loading Sequences");
    for (LoadingSequence loadingSequence : reply.getLoadingSequencesList()) {
      for (LoadingPlanPortWiseDetails portWiseDetails :
          loadingSequence.getLoadingPlanPortWiseDetailsList()) {
    	  start = loadingSequence.getStartTime();
        for (LoadingPlanTankDetails stowage : portWiseDetails.getLoadingPlanStowageDetailsList()) {
          // Adding cargos
          temp = this.buildCargoSequence(stowage, vesselTanks, cargoNomDetails, portEta, start, portWiseDetails, cargos);
        }
        for (LoadingPlanTankDetails ballast : portWiseDetails.getLoadingPlanBallastDetailsList()) {
          // Adding ballasts
          this.buildBallastSequence(ballast, vesselTanks, portEta, start, portWiseDetails, ballasts);
        }
       this.buildStabilityParamSequence(portWiseDetails, portEta, start, stabilityParams);
        
        start = temp;
        stageTickPositions.add(portEta + (temp * 60 * 1000));
      }
      
   // Adding cargo loading rates
      this.buildCargoLoadingRates(loadingSequence, portEta, cargoLoadingRates);

      // Adding ballast pumps
      loadingSequence
          .getBallastOperationsList()
          .forEach(
              operation -> {
                BallastPump ballastPump = new BallastPump();
                buildBallastPump(operation, portEta, ballastPump);
                if (ballastPump.getPumpId() == 0L) {
                	gravity.add(ballastPump);
                } else {
                	ballastPumps.add(ballastPump);
                }
              });

      loadingRates.addAll(loadingSequence.getLoadingRatesList());
    }

    response.setCargos(cargos);
    response.setBallasts(ballasts);
    response.setBallastPumps(ballastPumps);
    response.setGravity(gravity);
    response.setCargoLoadingRates(cargoLoadingRates);
    response.setStageTickPositions(stageTickPositions);
    response.setStabilityParams(stabilityParams);

    this.buildFlowRates(loadingRates, vesselTanks, portEta, response);

    this.buildCargoTankCategories(reply, vesselTanks, response);

    this.buildBallastTankCategories(reply, vesselTanks, response);
    
    this.buildBallastPumpCategories(vesselId, response);
  }

  
  private void buildStabilityParamSequence(LoadingPlanPortWiseDetails portWiseDetails, Long portEta, Integer start,
		List<StabilityParam> stabilityParams) {
	LoadingPlanStabilityParameters params = portWiseDetails.getLoadingPlanStabilityParameters();
	stabilityParams.stream().filter(param -> param.getName().equals("fore_draft"))
		.forEach(foreDraft -> foreDraft.getData().add(Arrays.asList(portEta + (start * 60 * 1000), params.getDraft())));
	stabilityParams.stream().filter(param -> param.getName().equals("bm"))
		.forEach(bm -> bm.getData().add(Arrays.asList(portEta + (start * 60 * 1000), params.getBm())));
	stabilityParams.stream().filter(param -> param.getName().equals("sf"))
		.forEach(sf -> sf.getData().add(Arrays.asList(portEta + (start * 60 * 1000), params.getSf())));
}


private void buildBallastSequence(LoadingPlanTankDetails ballast, List<VesselTankDetail> vesselTanks, Long portEta,
		Integer start, LoadingPlanPortWiseDetails portWiseDetails, List<Ballast> ballasts) {
	  Ballast ballastDto = new Ballast();
      Optional<VesselTankDetail> tankDetailOpt =
          vesselTanks.stream()
              .filter(tank -> tank.getTankId() == ballast.getTankId())
              .findAny();
      buildBallast(ballast, ballastDto, portEta, start, portWiseDetails.getTime());
      tankDetailOpt.ifPresent(tank -> ballastDto.setTankName(tank.getShortName()));
      ballasts.add(ballastDto);
}


private Integer buildCargoSequence(LoadingPlanTankDetails stowage, List<VesselTankDetail> vesselTanks,
		Map<Long, CargoNominationDetail> cargoNomDetails, Long portEta, Integer start, LoadingPlanPortWiseDetails portWiseDetails, List<Cargo> cargos) {
	  Cargo cargo = new Cargo();
      Optional<VesselTankDetail> tankDetailOpt =
          vesselTanks.stream()
              .filter(tank -> tank.getTankId() == stowage.getTankId())
              .findAny();
      CargoNominationDetail cargoNomination =
          cargoNomDetails.get(stowage.getCargoNominationId());
      Integer end =
          buildCargo(
              stowage, cargo, cargoNomination, portEta, start, portWiseDetails.getTime());
      tankDetailOpt.ifPresent(tank -> cargo.setTankName(tank.getShortName()));
      cargos.add(cargo);
      return end;
}


private void buildCargoLoadingRates(LoadingSequence loadingSequence, Long portEta,
		List<CargoLoadingRate> cargoLoadingRates) {
	  log.info("Adding cargo loading rate");
	  CargoLoadingRate cargoLoadingRate = new CargoLoadingRate();
      cargoLoadingRate.setStartTime(portEta + (loadingSequence.getStartTime() * 60 * 1000));
      cargoLoadingRate.setEndTime(portEta + (loadingSequence.getEndTime() * 60 * 1000));
      List<BigDecimal> rateList = new ArrayList<BigDecimal>();
      if (!StringUtils.isEmpty(loadingSequence.getCargoLoadingRate1())) {
    	  rateList.add(new BigDecimal(loadingSequence.getCargoLoadingRate1()));
      }
      if (!StringUtils.isEmpty(loadingSequence.getCargoLoadingRate2())) {
    	  rateList.add(new BigDecimal(loadingSequence.getCargoLoadingRate2()));
      }
      cargoLoadingRate.setLoadingRates(rateList);
      cargoLoadingRates.add(cargoLoadingRate);
}


private void buildBallastPumpCategories(Long vesselId, LoadingSequenceResponse response) {
	  List<PumpCategory> ballastPumpCategories = new ArrayList<>();
	  log.info("Populating ballast pump categories");
	  VesselIdRequest.Builder builder = VesselIdRequest.newBuilder();
	  builder.setVesselId(vesselId);
	  VesselPumpsResponse pumpsResponse = vesselInfoGrpcService.getVesselPumpsByVesselId(builder.build());
	  if (pumpsResponse.getResponseStatus().getStatus().equals(GatewayConstants.SUCCESS)) {
		  pumpsResponse.getVesselPumpList().forEach(vesselPump -> {
			  PumpCategory pumpCategory = new PumpCategory();
			  pumpCategory.setId(vesselPump.getId());
			  pumpCategory.setPumpNo(vesselPump.getPumpCode());
			  Optional<PumpType> pumpTypeOpt = pumpsResponse.getPumpTypeList().stream().filter(pumpType -> pumpType.getId() == vesselPump.getId()).findAny();
			  pumpTypeOpt.ifPresent(pumpType -> pumpCategory.setPumpType(pumpType.getName()));
			  ballastPumpCategories.add(pumpCategory);
		  });  
	  }
	  response.setBallastPumpCategories(ballastPumpCategories);
}


private void buildBallastTankCategories(
      LoadingSequenceReply reply,
      List<VesselTankDetail> vesselTanks,
      LoadingSequenceResponse response) {
    log.info("Populating ballast tank categories");
    List<TankCategory> ballastTankCategories = new ArrayList<>();
    if (reply.getLoadingSequencesCount() > 0) {
      LoadingSequence initialSequence = reply.getLoadingSequences(0);
      if (initialSequence.getLoadingPlanPortWiseDetailsCount() > 0) {
        LoadingPlanPortWiseDetails portWiseDetails =
            initialSequence.getLoadingPlanPortWiseDetails(0);
        portWiseDetails
            .getLoadingPlanBallastDetailsList()
            .forEach(
                ballast -> {
                  TankCategory tankCategory = new TankCategory();
                  tankCategory.setId(ballast.getTankId());
                  Optional<VesselTankDetail> tankDetailOpt =
                      vesselTanks.stream()
                          .filter(tank -> tank.getTankId() == ballast.getTankId())
                          .findAny();
                  tankDetailOpt.ifPresent(tank -> tankCategory.setTankNo(tank.getShortName()));
                  ballastTankCategories.add(tankCategory);
                });
      }
    }
    response.setBallastTankCategories(ballastTankCategories);
  }

  private void buildCargoTankCategories(
      LoadingSequenceReply reply,
      List<VesselTankDetail> vesselTanks,
      LoadingSequenceResponse response) {
    log.info("Populating cargo tank categories");
    List<TankCategory> cargoTankCategories = new ArrayList<>();
    if (reply.getLoadingSequencesCount() > 0) {
      LoadingSequence finalSequence =
          reply.getLoadingSequences(reply.getLoadingSequencesCount() - 1);
      if (finalSequence.getLoadingPlanPortWiseDetailsCount() > 0) {
        LoadingPlanPortWiseDetails portWiseDetails =
            finalSequence.getLoadingPlanPortWiseDetails(
                finalSequence.getLoadingPlanPortWiseDetailsCount() - 1);
        portWiseDetails
            .getLoadingPlanStowageDetailsList()
            .forEach(
                stowage -> {
                  TankCategory tankCategory = new TankCategory();
                  Optional<VesselTankDetail> tankDetailOpt =
                      vesselTanks.stream()
                          .filter(tank -> tank.getTankId() == stowage.getTankId())
                          .findAny();
                  tankDetailOpt.ifPresent(tank -> tankCategory.setTankNo(tank.getShortName()));
                  tankCategory.setId(stowage.getTankId());
                  tankCategory.setQuantity(
                      StringUtils.isEmpty(stowage.getQuantity())
                          ? null
                          : new BigDecimal(stowage.getQuantity()));
                  tankCategory.setUllage(
                      StringUtils.isEmpty(stowage.getUllage())
                          ? null
                          : new BigDecimal(stowage.getUllage()));
                  cargoTankCategories.add(tankCategory);
                });
      }
    }
    response.setCargoTankCategories(cargoTankCategories);
  }

  private void buildFlowRates(
      List<LoadingRate> loadingRates,
      List<VesselTankDetail> vesselTanks,
      Long portEta,
      LoadingSequenceResponse response) {
    log.info("Populating flow rates");
    List<FlowRate> flowRates = new ArrayList<FlowRate>();
    Set<Long> tankIdList =
        loadingRates.stream().map(rate -> rate.getTankId()).collect(Collectors.toSet());
    tankIdList.forEach(
        tankId -> {
          FlowRate flowRate = new FlowRate();
          Optional<VesselTankDetail> tankDetailOpt =
              vesselTanks.stream().filter(tank -> tank.getTankId() == tankId).findAny();
          tankDetailOpt.ifPresent(tank -> flowRate.setTankNo(tank.getShortName()));
          flowRate.setData(
              loadingRates.stream()
                  .filter(loadingRate -> loadingRate.getTankId() == tankId)
                  .map(
                      loadingRate ->
                          Arrays.asList(
                              portEta + (loadingRate.getStartTime() * 60 * 1000),
                              StringUtils.isEmpty(loadingRate.getLoadingRate())
                                  ? null
                                  : new BigDecimal(loadingRate.getLoadingRate())))
                  .collect(Collectors.toList()));
          flowRates.add(flowRate);
        });
    response.setFlowRates(flowRates);
  }

  private Map<Long, CargoNominationDetail> getCargoNominationDetails(Set<Long> cargoNominationIds)
      throws GenericServiceException {
    Map<Long, CargoNominationDetail> details = new HashMap<Long, CargoNominationDetail>();
    cargoNominationIds.forEach(
        id -> {
          CargoNominationRequest.Builder builder = CargoNominationRequest.newBuilder();
          builder.setCargoNominationId(id);
          CargoNominationDetailReply reply =
              loadableStudyGrpcService.getCargoNominationByCargoNominationId(builder.build());
          if (reply.getResponseStatus().getStatus().equals(GatewayConstants.SUCCESS)) {
            log.info("Fetched details of cargo nomination with id {}", id);
            details.put(id, reply.getCargoNominationdetail());
          }
        });

    return details;
  }

  private List<VesselTankDetail> getVesselTanks(Long vesselId) throws GenericServiceException {
    VesselRequest.Builder builder = VesselRequest.newBuilder();
    builder.setVesselId(vesselId);
    builder.addTankCategories(1L);
    builder.addTankCategories(2L);
    VesselReply reply = vesselInfoGrpcService.getVesselTanks(builder.build());
    if (!reply.getResponseStatus().getStatus().equals(GatewayConstants.SUCCESS)) {
      throw new GenericServiceException(
          "Failed to get vessel tanks",
          CommonErrorCodes.E_HTTP_BAD_REQUEST,
          HttpStatusCode.BAD_REQUEST);
    }
    log.info("Fetched vessel tank details of vessel {}", vesselId);
    return reply.getVesselTanksList();
  }

  private void buildBallastPump(PumpOperation operation, Long portEta, BallastPump ballastPump) {
    ballastPump.setPumpId(operation.getPumpXId());
    ballastPump.setRate(
        StringUtils.isEmpty(operation.getRate()) ? null : new BigDecimal(operation.getRate()));
    ballastPump.setEnd(portEta + (operation.getEndTime() * 60 * 1000));
    ballastPump.setStart(portEta + (operation.getStartTime() * 60 * 1000));
    ballastPump.setQuantityM3(
        StringUtils.isEmpty(operation.getQuantityM3())
            ? null
            : new BigDecimal(operation.getQuantityM3()));
  }

  private void buildBallast(
      LoadingPlanTankDetails ballast,
      Ballast ballastDto,
      Long portEta,
      Integer start,
      Integer end) {
    ballastDto.setQuantity(
        StringUtils.isEmpty(ballast.getQuantity()) ? null : new BigDecimal(ballast.getQuantity()));
    ballastDto.setSounding(
        StringUtils.isEmpty(ballast.getSounding()) ? null : new BigDecimal(ballast.getSounding()));
    ballastDto.setStart(portEta + (start * 60 * 1000));
    ballastDto.setEnd(portEta + (end * 60 * 1000));
    ballastDto.setTankId(ballast.getTankId());
  }

  private Integer buildCargo(
      LoadingPlanTankDetails stowage,
      Cargo cargo,
      CargoNominationDetail cargoNomination,
      Long portEta,
      Integer start,
      Integer end) {
    cargo.setCargoNominationId(stowage.getCargoNominationId());
    cargo.setQuantity(
        StringUtils.isEmpty(stowage.getQuantity()) ? null : new BigDecimal(stowage.getQuantity()));
    cargo.setTankId(stowage.getTankId());
    cargo.setUllage(
        StringUtils.isEmpty(stowage.getUllage()) ? null : new BigDecimal(stowage.getUllage()));
    cargo.setColor(cargoNomination.getColor());
    cargo.setName(cargoNomination.getCargoName());
    cargo.setAbbreviation(cargoNomination.getAbbreviation());
    cargo.setStart(portEta + (start * 60 * 1000));
    cargo.setEnd(portEta + (end * 60 * 1000));
    return end;
  }
}
