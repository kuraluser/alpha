/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.algo;

import com.cpdss.common.generated.loading_plan.LoadingPlanModels.DeBallastingRate;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanTankDetails;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingRate;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.PumpOperation;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve;
import com.cpdss.loadingplan.common.LoadingPlanConstants;
import com.cpdss.loadingplan.entity.BallastOperation;
import com.cpdss.loadingplan.entity.BallastValve;
import com.cpdss.loadingplan.entity.CargoLoadingRate;
import com.cpdss.loadingplan.entity.CargoValve;
import com.cpdss.loadingplan.entity.DeballastingRate;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.entity.LoadingPlanBallastDetails;
import com.cpdss.loadingplan.entity.LoadingPlanCommingleDetails;
import com.cpdss.loadingplan.entity.LoadingPlanRobDetails;
import com.cpdss.loadingplan.entity.LoadingPlanStabilityParameters;
import com.cpdss.loadingplan.entity.LoadingPlanStowageDetails;
import com.cpdss.loadingplan.entity.LoadingSequence;
import com.cpdss.loadingplan.entity.LoadingSequenceStabilityParameters;
import com.cpdss.loadingplan.entity.PortLoadingPlanBallastDetails;
import com.cpdss.loadingplan.entity.PortLoadingPlanCommingleDetails;
import com.cpdss.loadingplan.entity.PortLoadingPlanRobDetails;
import com.cpdss.loadingplan.entity.PortLoadingPlanStabilityParameters;
import com.cpdss.loadingplan.entity.PortLoadingPlanStowageDetails;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class LoadingPlanBuilderService {

  public void buildLoadingSequence(
      LoadingSequence loadingSequence,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequence sequence,
      LoadingInformation loadingInformation) {
    loadingSequence.setCargoNominationXId(sequence.getCargoNominationId());
    loadingSequence.setEndTime(sequence.getEndTime());
    loadingSequence.setIsActive(true);
    loadingSequence.setLoadingInformation(loadingInformation);
    loadingSequence.setPortXId(loadingInformation.getPortXId());
    loadingSequence.setSequenceNumber(sequence.getSequenceNumber());
    loadingSequence.setStageName(sequence.getStageName());
    loadingSequence.setStartTime(sequence.getStartTime());
    loadingSequence.setToLoadicator(sequence.getToLoadicator());
    loadingSequence.setCargoLoadingRate1(
        StringUtils.isEmpty(sequence.getCargoLoadingRate1())
            ? null
            : new BigDecimal(sequence.getCargoLoadingRate1()));
    loadingSequence.setCargoLoadingRate2(
        StringUtils.isEmpty(sequence.getCargoLoadingRate2())
            ? null
            : new BigDecimal(sequence.getCargoLoadingRate2()));
  }

  public void buildBallastValve(
      LoadingSequence loadingSequence, BallastValve ballastValve, Valve valve) {
    ballastValve.setIsActive(true);
    ballastValve.setLoadingSequence(loadingSequence);
    ballastValve.setOperation(valve.getOperation());
    ballastValve.setTime(valve.getTime());
    ballastValve.setValveCode(valve.getValveCode());
    ballastValve.setValveType(valve.getValveType());
    ballastValve.setValveXId(valve.getValveId());
  }

  public void buildCargoValve(LoadingSequence loadingSequence, CargoValve cargoValve, Valve valve) {
    cargoValve.setIsActive(true);
    cargoValve.setLoadingSequence(loadingSequence);
    cargoValve.setOperation(valve.getOperation());
    cargoValve.setTime(valve.getTime());
    cargoValve.setValveCode(valve.getValveCode());
    cargoValve.setValveType(valve.getValveType());
    cargoValve.setValveXId(valve.getValveId());
  }

  private void populateDeBallastingRate(DeballastingRate deballastingRate, DeBallastingRate rate) {
    deballastingRate.setIsActive(true);
    deballastingRate.setTankXId(rate.getTankId());
    deballastingRate.setDeBallastingRate(
        StringUtils.isEmpty(rate.getDeBallastingRate())
            ? null
            : new BigDecimal(rate.getDeBallastingRate()));
    deballastingRate.setTime(rate.getTime());
  }

  public void buildDeBallastingRate(
      LoadingSequence loadingSequence, DeballastingRate deballastingRate, DeBallastingRate rate) {
    populateDeBallastingRate(deballastingRate, rate);
    deballastingRate.setLoadingSequence(loadingSequence);
  }

  public void buildLoadingPlanPortWiseDetails(
      LoadingSequence loadingSequence,
      com.cpdss.loadingplan.entity.LoadingPlanPortWiseDetails portWiseDetails,
      LoadingPlanPortWiseDetails details) {
    portWiseDetails.setIsActive(true);
    portWiseDetails.setLoadingSequence(loadingSequence);
    portWiseDetails.setTime(details.getTime());
  }

  public void buildDeBallastingRate(
      com.cpdss.loadingplan.entity.LoadingPlanPortWiseDetails loadingPlanPortWiseDetails,
      DeballastingRate deballastingRate,
      DeBallastingRate rate) {
    populateDeBallastingRate(deballastingRate, rate);
    deballastingRate.setLoadingPlanPortWiseDetails(loadingPlanPortWiseDetails);
  }

  public void buildLoadingPlanBallastDetails(
      com.cpdss.loadingplan.entity.LoadingPlanPortWiseDetails loadingPlanPortWiseDetails,
      LoadingPlanBallastDetails ballastDetails,
      LoadingPlanTankDetails ballast) {
    ballastDetails.setIsActive(true);
    ballastDetails.setLoadingPlanPortWiseDetails(loadingPlanPortWiseDetails);
    ballastDetails.setQuantity(
        StringUtils.isEmpty(ballast.getQuantity()) ? null : new BigDecimal(ballast.getQuantity()));
    ballastDetails.setTankXId(ballast.getTankId());
    ballastDetails.setSounding(
        StringUtils.isEmpty(ballast.getSounding()) ? null : new BigDecimal(ballast.getSounding()));
    ballastDetails.setQuantityM3(
        StringUtils.isEmpty(ballast.getQuantityM3())
            ? null
            : new BigDecimal(ballast.getQuantityM3()));
    ballastDetails.setColorCode(
        StringUtils.isEmpty(ballast.getColorCode()) ? null : ballast.getColorCode());
    ballastDetails.setSg(
        StringUtils.isEmpty(ballast.getSg()) ? null : new BigDecimal(ballast.getSg()));
  }

  public void buildLoadingPlanRobDetails(
      com.cpdss.loadingplan.entity.LoadingPlanPortWiseDetails loadingPlanPortWiseDetails,
      LoadingPlanRobDetails robDetails,
      LoadingPlanTankDetails rob) {
    robDetails.setIsActive(true);
    robDetails.setLoadingPlanPortWiseDetails(loadingPlanPortWiseDetails);
    robDetails.setQuantity(
        StringUtils.isEmpty(rob.getQuantity()) ? null : new BigDecimal(rob.getQuantity()));
    robDetails.setTankXId(rob.getTankId());
    robDetails.setQuantityM3(
        StringUtils.isEmpty(rob.getQuantityM3()) ? null : new BigDecimal(rob.getQuantityM3()));
    robDetails.setColorCode(StringUtils.isEmpty(rob.getColorCode()) ? null : rob.getColorCode());
  }

  public void buildLoadingPlanStowageDetails(
      com.cpdss.loadingplan.entity.LoadingPlanPortWiseDetails loadingPlanPortWiseDetails,
      LoadingPlanStowageDetails stowageDetails,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanTankDetails stowage) {
    stowageDetails.setApi(
        StringUtils.isEmpty(stowage.getApi()) ? null : new BigDecimal(stowage.getApi()));
    stowageDetails.setCargoNominationId(stowage.getCargoNominationId());
    stowageDetails.setIsActive(true);
    stowageDetails.setLoadingPlanPortWiseDetails(loadingPlanPortWiseDetails);
    stowageDetails.setQuantity(
        StringUtils.isEmpty(stowage.getQuantity()) ? null : new BigDecimal(stowage.getQuantity()));
    stowageDetails.setTankXId(stowage.getTankId());
    stowageDetails.setTemperature(
        StringUtils.isEmpty(stowage.getTemperature())
            ? null
            : new BigDecimal(stowage.getTemperature()));
    stowageDetails.setUllage(
        StringUtils.isEmpty(stowage.getUllage()) ? null : new BigDecimal(stowage.getUllage()));
    stowageDetails.setQuantityM3(
        StringUtils.isEmpty(stowage.getQuantityM3())
            ? null
            : new BigDecimal(stowage.getQuantityM3()));
    stowageDetails.setAbbreviation(
        StringUtils.isEmpty(stowage.getAbbreviation()) ? null : stowage.getAbbreviation());
    stowageDetails.setColorCode(
        StringUtils.isEmpty(stowage.getColorCode()) ? null : stowage.getColorCode());
  }

  public void buildStabilityParameters(
      com.cpdss.loadingplan.entity.LoadingPlanPortWiseDetails loadingPlanPortWiseDetails,
      LoadingPlanStabilityParameters parameters,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanStabilityParameters
          loadingPlanStabilityParameters) {
    parameters.setBm(
        StringUtils.isEmpty(loadingPlanStabilityParameters.getBm())
            ? null
            : new BigDecimal(loadingPlanStabilityParameters.getBm()));
    parameters.setSf(
        StringUtils.isEmpty(loadingPlanStabilityParameters.getSf())
            ? null
            : new BigDecimal(loadingPlanStabilityParameters.getSf()));
    parameters.setDraft(
        StringUtils.isEmpty(loadingPlanStabilityParameters.getDraft())
            ? null
            : new BigDecimal(loadingPlanStabilityParameters.getDraft()));
    parameters.setForeDraft(
        StringUtils.isEmpty(loadingPlanStabilityParameters.getForeDraft())
            ? null
            : new BigDecimal(loadingPlanStabilityParameters.getForeDraft()));
    parameters.setAftDraft(
        StringUtils.isEmpty(loadingPlanStabilityParameters.getAftDraft())
            ? null
            : new BigDecimal(loadingPlanStabilityParameters.getAftDraft()));
    parameters.setMeanDraft(
        StringUtils.isEmpty(loadingPlanStabilityParameters.getMeanDraft())
            ? null
            : new BigDecimal(loadingPlanStabilityParameters.getMeanDraft()));
    parameters.setTrim(
        StringUtils.isEmpty(loadingPlanStabilityParameters.getTrim())
            ? null
            : new BigDecimal(loadingPlanStabilityParameters.getTrim()));
    parameters.setList(
        StringUtils.isEmpty(loadingPlanStabilityParameters.getList())
            ? null
            : new BigDecimal(loadingPlanStabilityParameters.getList()));
    parameters.setLoadingPlanPortWiseDetails(loadingPlanPortWiseDetails);
    parameters.setIsActive(true);
  }

  public void buildCargoLoadingRate(
      LoadingSequence loadingSequence, CargoLoadingRate cargoLoadingRate, LoadingRate loadingRate) {
    cargoLoadingRate.setIsActive(true);
    cargoLoadingRate.setLoadingRate(
        StringUtils.isEmpty(loadingRate.getLoadingRate())
            ? null
            : new BigDecimal(loadingRate.getLoadingRate()));
    cargoLoadingRate.setTankXId(loadingRate.getTankId());
    cargoLoadingRate.setLoadingSequence(loadingSequence);
  }

  public void buildPortBallast(
      LoadingInformation loadingInformation,
      PortLoadingPlanBallastDetails ballastDetails,
      LoadingPlanTankDetails ballast) {
    ballastDetails.setConditionType(ballast.getConditionType());
    ballastDetails.setSounding(
        StringUtils.isEmpty(ballast.getSounding()) ? null : new BigDecimal(ballast.getSounding()));
    ballastDetails.setIsActive(true);
    ballastDetails.setLoadingInformation(loadingInformation);
    ballastDetails.setValueType(LoadingPlanConstants.LOADING_PLAN_PLANNED_TYPE_VALUE);
    ballastDetails.setPortXId(loadingInformation.getPortXId());
    ballastDetails.setPortRotationXId(loadingInformation.getPortRotationXId());
    ballastDetails.setQuantity(
        StringUtils.isEmpty(ballast.getQuantity()) ? null : new BigDecimal(ballast.getQuantity()));
    ballastDetails.setQuantityM3(
        StringUtils.isEmpty(ballast.getQuantityM3())
            ? null
            : new BigDecimal(ballast.getQuantityM3()));
    ballastDetails.setTankXId(ballast.getTankId());
    ballastDetails.setTemperature(
        StringUtils.isEmpty(ballast.getTemperature())
            ? null
            : new BigDecimal(ballast.getTemperature()));
    ballastDetails.setColorCode(
        StringUtils.isEmpty(ballast.getColorCode()) ? null : ballast.getColorCode());
    ballastDetails.setSg(
        StringUtils.isEmpty(ballast.getSg()) ? null : new BigDecimal(ballast.getSg()));
  }

  public void buildPortRob(
      LoadingInformation loadingInformation,
      PortLoadingPlanRobDetails robDetails,
      LoadingPlanTankDetails rob) {
    robDetails.setConditionType(rob.getConditionType());
    robDetails.setIsActive(true);
    robDetails.setLoadingInformation(loadingInformation.getId());
    robDetails.setPortRotationXId(loadingInformation.getPortRotationXId());
    robDetails.setPortXId(loadingInformation.getPortXId());
    robDetails.setQuantity(
        StringUtils.isEmpty(rob.getQuantity()) ? null : new BigDecimal(rob.getQuantity()));
    robDetails.setTankXId(rob.getTankId());
    robDetails.setValueType(LoadingPlanConstants.LOADING_PLAN_PLANNED_TYPE_VALUE);
    robDetails.setQuantityM3(
        StringUtils.isEmpty(rob.getQuantityM3()) ? null : new BigDecimal(rob.getQuantityM3()));
    robDetails.setColorCode(StringUtils.isEmpty(rob.getColorCode()) ? null : rob.getColorCode());
    robDetails.setDensity(
        StringUtils.isEmpty(rob.getDensity()) ? null : new BigDecimal(rob.getDensity()));
  }

  public void buildPortStabilityParams(
      LoadingInformation loadingInformation,
      PortLoadingPlanStabilityParameters stabilityParams,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanStabilityParameters
          params) {
    stabilityParams.setBendingMoment(
        StringUtils.isEmpty(params.getBm()) ? null : new BigDecimal(params.getBm()));
    stabilityParams.setConditionType(params.getConditionType());
    stabilityParams.setDraft(
        StringUtils.isEmpty(params.getDraft()) ? null : new BigDecimal(params.getDraft()));
    stabilityParams.setIsActive(true);
    stabilityParams.setLoadingInformation(loadingInformation);
    stabilityParams.setPortRotationXId(loadingInformation.getPortRotationXId());
    stabilityParams.setPortXId(loadingInformation.getPortXId());
    stabilityParams.setShearingForce(
        StringUtils.isEmpty(params.getSf()) ? null : new BigDecimal(params.getSf()));
    stabilityParams.setForeDraft(
        StringUtils.isEmpty(params.getForeDraft()) ? null : new BigDecimal(params.getForeDraft()));
    stabilityParams.setMeanDraft(
        StringUtils.isEmpty(params.getMeanDraft()) ? null : new BigDecimal(params.getMeanDraft()));
    stabilityParams.setAftDraft(
        StringUtils.isEmpty(params.getAftDraft()) ? null : new BigDecimal(params.getAftDraft()));
    stabilityParams.setTrim(
        StringUtils.isEmpty(params.getTrim()) ? null : new BigDecimal(params.getTrim()));
    stabilityParams.setList(
        StringUtils.isEmpty(params.getList()) ? null : new BigDecimal(params.getList()));
    stabilityParams.setValueType(LoadingPlanConstants.LOADING_PLAN_PLANNED_TYPE_VALUE);
  }

  public void buildPortStowage(
      LoadingInformation loadingInformation,
      PortLoadingPlanStowageDetails stowageDetails,
      LoadingPlanTankDetails stowage) {
    stowageDetails.setApi(
        StringUtils.isEmpty(stowage.getApi()) ? null : new BigDecimal(stowage.getApi()));
    stowageDetails.setConditionType(stowage.getConditionType());
    stowageDetails.setCargoNominationXId(stowage.getCargoNominationId());
    stowageDetails.setUllage(
        StringUtils.isEmpty(stowage.getUllage()) ? null : new BigDecimal(stowage.getUllage()));
    stowageDetails.setIsActive(true);
    stowageDetails.setLoadingInformation(loadingInformation);
    stowageDetails.setPortRotationXId(loadingInformation.getPortRotationXId());
    stowageDetails.setPortXId(loadingInformation.getPortXId());
    stowageDetails.setQuantity(
        StringUtils.isEmpty(stowage.getQuantity()) ? null : new BigDecimal(stowage.getQuantity()));
    stowageDetails.setQuantityM3(
        StringUtils.isEmpty(stowage.getQuantityM3())
            ? null
            : new BigDecimal(stowage.getQuantityM3()));
    stowageDetails.setTankXId(stowage.getTankId());
    stowageDetails.setTemperature(
        StringUtils.isEmpty(stowage.getTemperature())
            ? null
            : new BigDecimal(stowage.getTemperature()));
    stowageDetails.setValueType(LoadingPlanConstants.LOADING_PLAN_PLANNED_TYPE_VALUE);
    stowageDetails.setAbbreviation(
        StringUtils.isEmpty(stowage.getAbbreviation()) ? null : stowage.getAbbreviation());
    stowageDetails.setColorCode(
        StringUtils.isEmpty(stowage.getColorCode()) ? null : stowage.getColorCode());
    stowageDetails.setCargoXId(stowage.getCargoId());
  }

  public void buildBallastOperation(
      LoadingSequence loadingSequence,
      BallastOperation ballastOperation,
      PumpOperation pumpOperation) {
    ballastOperation.setEndTime(pumpOperation.getEndTime());
    ballastOperation.setLoadingSequence(loadingSequence);
    ballastOperation.setPumpName(pumpOperation.getPumpName());
    ballastOperation.setPumpXId(pumpOperation.getPumpXId());
    ballastOperation.setQuantityM3(
        StringUtils.isEmpty(pumpOperation.getQuantityM3())
            ? null
            : new BigDecimal(pumpOperation.getQuantityM3()));
    ballastOperation.setRate(
        StringUtils.isEmpty(pumpOperation.getRate())
            ? null
            : new BigDecimal(pumpOperation.getRate()));
    ballastOperation.setStartTime(pumpOperation.getStartTime());
    ballastOperation.setIsActive(true);
  }

  public void buildLoadingSequenceStabilityParams(
      LoadingInformation loadingInformation,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanStabilityParameters
          param,
      LoadingSequenceStabilityParameters stabilityParameters) {
    stabilityParameters.setAftDraft(
        StringUtils.isEmpty(param.getAftDraft()) ? null : new BigDecimal(param.getAftDraft()));
    stabilityParameters.setBendingMoment(
        StringUtils.isEmpty(param.getBm()) ? null : new BigDecimal(param.getBm()));
    stabilityParameters.setDraft(
        StringUtils.isEmpty(param.getDraft()) ? null : new BigDecimal(param.getDraft()));
    stabilityParameters.setForeDraft(
        StringUtils.isEmpty(param.getForeDraft()) ? null : new BigDecimal(param.getForeDraft()));
    stabilityParameters.setIsActive(true);
    stabilityParameters.setLoadingInformation(loadingInformation);
    stabilityParameters.setPortXId(loadingInformation.getPortXId());
    stabilityParameters.setShearingForce(
        StringUtils.isEmpty(param.getSf()) ? null : new BigDecimal(param.getSf()));
    stabilityParameters.setMeanDraft(
        StringUtils.isEmpty(param.getMeanDraft()) ? null : new BigDecimal(param.getMeanDraft()));
    stabilityParameters.setTrim(
        StringUtils.isEmpty(param.getTrim()) ? null : new BigDecimal(param.getTrim()));
    stabilityParameters.setList(
        StringUtils.isEmpty(param.getList()) ? null : new BigDecimal(param.getList()));
    stabilityParameters.setTime(param.getTime());
  }

  /**
   * @param loadingPlanPortWiseDetails
   * @param commingleDetails
   * @param commingle
   */
  public void buildLoadingPlanCommingleDetails(
      com.cpdss.loadingplan.entity.LoadingPlanPortWiseDetails loadingPlanPortWiseDetails,
      LoadingPlanCommingleDetails commingleDetails,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanCommingleDetails
          commingle) {
    commingleDetails.setAbbreviation(commingle.getAbbreviation());
    commingleDetails.setApi(
        StringUtils.isEmpty(commingle.getApi()) ? null : new BigDecimal(commingle.getApi()));
    commingleDetails.setCargo1XId(commingle.getCargo1Id());
    commingleDetails.setCargo2XId(commingle.getCargo2Id());
    commingleDetails.setCargoNomination1XId(commingle.getCargoNomination1Id());
    commingleDetails.setCargoNomination2XId(commingle.getCargoNomination2Id());
    commingleDetails.setColorCode(commingle.getColorCode());
    commingleDetails.setIsActive(true);
    commingleDetails.setLoadingPlanPortWiseDetails(loadingPlanPortWiseDetails);
    commingleDetails.setQuantity(
        StringUtils.isEmpty(commingle.getQuantityMT())
            ? null
            : new BigDecimal(commingle.getQuantityMT()));
    commingleDetails.setQuantityM3(
        StringUtils.isEmpty(commingle.getQuantityM3())
            ? null
            : new BigDecimal(commingle.getQuantityM3()));
    commingleDetails.setTankXId(commingle.getTankId());
    commingleDetails.setTemperature(
        StringUtils.isEmpty(commingle.getTemperature())
            ? null
            : new BigDecimal(commingle.getTemperature()));
    commingleDetails.setUllage(
        StringUtils.isEmpty(commingle.getUllage()) ? null : new BigDecimal(commingle.getUllage()));
  }

  /**
   * @param loadingInformation
   * @param commingleDetails
   * @param commingle
   */
  public void buildPortCommingle(
      LoadingInformation loadingInformation,
      PortLoadingPlanCommingleDetails commingleDetails,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanCommingleDetails
          commingle) {
    commingleDetails.setApi(commingle.getApi());
    commingleDetails.setGrade(commingle.getAbbreviation());
    commingleDetails.setIsActive(true);
    commingleDetails.setLoadingInformation(loadingInformation);
    commingleDetails.setLoadablePatternId(loadingInformation.getLoadablePatternXId());
    commingleDetails.setQuantity(commingle.getQuantityMT());
    commingleDetails.setTankId(commingle.getTankId());
    commingleDetails.setTemperature(commingle.getTemperature());
    commingleDetails.setCargoNomination1XId(commingle.getCargoNomination1Id());
    commingleDetails.setCargoNomination2XId(commingle.getCargoNomination2Id());
    commingleDetails.setCargo1XId(commingle.getCargo1Id());
    commingleDetails.setCargo2XId(commingle.getCargo2Id());
    commingleDetails.setColorCode(commingle.getColorCode());
    commingleDetails.setQuantityM3(commingle.getQuantityM3());
    commingleDetails.setUllage(commingle.getUllage());
  }
}
