/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service;

import com.cpdss.common.generated.discharge_plan.DischargingRate;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.DeBallastingRate;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanTankDetails;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.PumpOperation;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve;
import com.cpdss.dischargeplan.common.DischargePlanConstants;
import com.cpdss.dischargeplan.entity.BallastOperation;
import com.cpdss.dischargeplan.entity.BallastValve;
import com.cpdss.dischargeplan.entity.CargoDischargingRate;
import com.cpdss.dischargeplan.entity.CargoValve;
import com.cpdss.dischargeplan.entity.DeballastingRate;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import com.cpdss.dischargeplan.entity.DischargingPlanBallastDetails;
import com.cpdss.dischargeplan.entity.DischargingPlanCommingleDetails;
import com.cpdss.dischargeplan.entity.DischargingPlanPortWiseDetails;
import com.cpdss.dischargeplan.entity.DischargingPlanRobDetails;
import com.cpdss.dischargeplan.entity.DischargingPlanStabilityParameters;
import com.cpdss.dischargeplan.entity.DischargingPlanStowageDetails;
import com.cpdss.dischargeplan.entity.DischargingSequence;
import com.cpdss.dischargeplan.entity.DischargingSequenceStabilityParameters;
import com.cpdss.dischargeplan.entity.PortDischargingPlanBallastDetails;
import com.cpdss.dischargeplan.entity.PortDischargingPlanCommingleDetails;
import com.cpdss.dischargeplan.entity.PortDischargingPlanRobDetails;
import com.cpdss.dischargeplan.entity.PortDischargingPlanStabilityParameters;
import com.cpdss.dischargeplan.entity.PortDischargingPlanStowageDetails;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class DischargingPlanBuilderService {

  public void buildDischargingSequence(
      DischargingSequence loadingSequence,
      com.cpdss.common.generated.discharge_plan.DischargingSequence sequence,
      DischargeInformation dischargingInformation) {
    loadingSequence.setCargoNominationXId(sequence.getCargoNominationId());
    loadingSequence.setEndTime(sequence.getEndTime());
    loadingSequence.setIsActive(true);
    loadingSequence.setDischargeInformation(dischargingInformation);
    loadingSequence.setPortXId(dischargingInformation.getPortXid());
    loadingSequence.setSequenceNumber(sequence.getSequenceNumber());
    loadingSequence.setStageName(sequence.getStageName());
    loadingSequence.setStartTime(sequence.getStartTime());
    loadingSequence.setToLoadicator(sequence.getToLoadicator());
    loadingSequence.setCargoDischargingRate1(
        StringUtils.isEmpty(sequence.getCargoDischargingRate1())
            ? null
            : new BigDecimal(sequence.getCargoDischargingRate1()));
    loadingSequence.setCargoDischargingRate2(
        StringUtils.isEmpty(sequence.getCargoDischargingRate2())
            ? null
            : new BigDecimal(sequence.getCargoDischargingRate2()));
  }

  public void buildBallastValve(
      DischargingSequence loadingSequence, BallastValve ballastValve, Valve valve) {
    ballastValve.setIsActive(true);
    ballastValve.setDischargingSequence(loadingSequence);
    ballastValve.setOperation(valve.getOperation());
    ballastValve.setTime(valve.getTime());
    ballastValve.setValveCode(valve.getValveCode());
    ballastValve.setValveType(valve.getValveType());
    ballastValve.setValveXId(valve.getValveId());
  }

  public void buildCargoValve(
      DischargingSequence loadingSequence, CargoValve cargoValve, Valve valve) {
    cargoValve.setIsActive(true);
    cargoValve.setDischargingSequence(loadingSequence);
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
      DischargingSequence loadingSequence,
      DeballastingRate deballastingRate,
      DeBallastingRate rate) {
    populateDeBallastingRate(deballastingRate, rate);
    deballastingRate.setDischargingSequence(loadingSequence);
  }

  public void buildDischargingPlanPortWiseDetails(
      DischargingSequence loadingSequence,
      DischargingPlanPortWiseDetails portWiseDetails,
      LoadingPlanPortWiseDetails details) {
    portWiseDetails.setIsActive(true);
    portWiseDetails.setDischargingSequence(loadingSequence);
    portWiseDetails.setTime(details.getTime());
  }

  public void buildDeBallastingRate(
      DischargingPlanPortWiseDetails dischargingPlanPortWiseDetails,
      DeballastingRate deballastingRate,
      DeBallastingRate rate) {
    populateDeBallastingRate(deballastingRate, rate);
    deballastingRate.setDischargingPlanPortWiseDetails(dischargingPlanPortWiseDetails);
  }

  public void buildDischargingPlanBallastDetails(
      DischargingPlanPortWiseDetails loadingPlanPortWiseDetails,
      DischargingPlanBallastDetails ballastDetails,
      LoadingPlanTankDetails ballast) {
    ballastDetails.setIsActive(true);
    ballastDetails.setDischargingPlanPortWiseDetails(loadingPlanPortWiseDetails);
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

  public void buildDischargingPlanRobDetails(
      DischargingPlanPortWiseDetails loadingPlanPortWiseDetails,
      DischargingPlanRobDetails robDetails,
      LoadingPlanTankDetails rob) {
    robDetails.setIsActive(true);
    robDetails.setDischargingPlanPortWiseDetails(loadingPlanPortWiseDetails);
    robDetails.setQuantity(
        StringUtils.isEmpty(rob.getQuantity()) ? null : new BigDecimal(rob.getQuantity()));
    robDetails.setTankXId(rob.getTankId());
    robDetails.setQuantityM3(
        StringUtils.isEmpty(rob.getQuantityM3()) ? null : new BigDecimal(rob.getQuantityM3()));
    robDetails.setColorCode(StringUtils.isEmpty(rob.getColorCode()) ? null : rob.getColorCode());
  }

  public void buildDischargingPlanStowageDetails(
      DischargingPlanPortWiseDetails loadingPlanPortWiseDetails,
      DischargingPlanStowageDetails stowageDetails,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanTankDetails stowage) {
    stowageDetails.setApi(
        StringUtils.isEmpty(stowage.getApi()) ? null : new BigDecimal(stowage.getApi()));
    stowageDetails.setCargoNominationId(stowage.getCargoNominationId());
    stowageDetails.setIsActive(true);
    stowageDetails.setDischargingPlanPortWiseDetails(loadingPlanPortWiseDetails);
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
      DischargingPlanPortWiseDetails loadingPlanPortWiseDetails,
      DischargingPlanStabilityParameters parameters,
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
    parameters.setDischargingPlanPortWiseDetails(loadingPlanPortWiseDetails);
    parameters.setIsActive(true);
  }

  public void buildCargoDischargingRate(
      DischargingSequence loadingSequence,
      CargoDischargingRate cargoLoadingRate,
      DischargingRate dischargingRate) {
    cargoLoadingRate.setIsActive(true);
    cargoLoadingRate.setDischargingRate(
        StringUtils.isEmpty(dischargingRate.getDischargingRate())
            ? null
            : new BigDecimal(dischargingRate.getDischargingRate()));
    cargoLoadingRate.setTankXId(dischargingRate.getTankId());
    cargoLoadingRate.setDischargingSequence(loadingSequence);
  }

  public void buildPortBallast(
      DischargeInformation dischargingInformation,
      PortDischargingPlanBallastDetails ballastDetails,
      LoadingPlanTankDetails ballast) {
    ballastDetails.setConditionType(ballast.getConditionType());
    ballastDetails.setSounding(
        StringUtils.isEmpty(ballast.getSounding()) ? null : new BigDecimal(ballast.getSounding()));
    ballastDetails.setIsActive(true);
    ballastDetails.setDischargingInformation(dischargingInformation);
    ballastDetails.setValueType(DischargePlanConstants.DISCHARGING_PLAN_PLANNED_TYPE_VALUE);
    ballastDetails.setPortXId(dischargingInformation.getPortXid());
    ballastDetails.setPortRotationXId(dischargingInformation.getPortRotationXid());
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
      DischargeInformation dischargingInformation,
      PortDischargingPlanRobDetails robDetails,
      LoadingPlanTankDetails rob) {
    robDetails.setConditionType(rob.getConditionType());
    robDetails.setIsActive(true);
    robDetails.setDischargingInformation(dischargingInformation.getId());
    robDetails.setPortRotationXId(dischargingInformation.getPortRotationXid());
    robDetails.setPortXId(dischargingInformation.getPortXid());
    robDetails.setQuantity(
        StringUtils.isEmpty(rob.getQuantity()) ? null : new BigDecimal(rob.getQuantity()));
    robDetails.setTankXId(rob.getTankId());
    robDetails.setValueType(DischargePlanConstants.DISCHARGING_PLAN_PLANNED_TYPE_VALUE);
    robDetails.setQuantityM3(
        StringUtils.isEmpty(rob.getQuantityM3()) ? null : new BigDecimal(rob.getQuantityM3()));
    robDetails.setColorCode(StringUtils.isEmpty(rob.getColorCode()) ? null : rob.getColorCode());
    robDetails.setDensity(
        StringUtils.isEmpty(rob.getDensity()) ? null : new BigDecimal(rob.getDensity()));
  }

  public void buildPortStabilityParams(
      DischargeInformation dischargingInformation,
      PortDischargingPlanStabilityParameters stabilityParams,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanStabilityParameters
          params) {
    stabilityParams.setBendingMoment(
        StringUtils.isEmpty(params.getBm()) ? null : new BigDecimal(params.getBm()));
    stabilityParams.setConditionType(params.getConditionType());
    stabilityParams.setDraft(
        StringUtils.isEmpty(params.getDraft()) ? null : new BigDecimal(params.getDraft()));
    stabilityParams.setIsActive(true);
    stabilityParams.setDischargingInformation(dischargingInformation);
    stabilityParams.setPortRotationXId(dischargingInformation.getPortRotationXid());
    stabilityParams.setPortXId(dischargingInformation.getPortXid());
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
    stabilityParams.setValueType(DischargePlanConstants.DISCHARGING_PLAN_PLANNED_TYPE_VALUE);
  }

  public void buildPortStowage(
      DischargeInformation dischargingInformation,
      PortDischargingPlanStowageDetails stowageDetails,
      LoadingPlanTankDetails stowage) {
    stowageDetails.setApi(
        StringUtils.isEmpty(stowage.getApi()) ? null : new BigDecimal(stowage.getApi()));
    stowageDetails.setConditionType(stowage.getConditionType());
    stowageDetails.setCargoNominationXId(stowage.getCargoNominationId());
    stowageDetails.setUllage(
        StringUtils.isEmpty(stowage.getUllage()) ? null : new BigDecimal(stowage.getUllage()));
    stowageDetails.setIsActive(true);
    stowageDetails.setDischargingInformation(dischargingInformation);
    stowageDetails.setPortRotationXId(dischargingInformation.getPortRotationXid());
    stowageDetails.setPortXId(dischargingInformation.getPortXid());
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
    stowageDetails.setValueType(DischargePlanConstants.DISCHARGING_PLAN_PLANNED_TYPE_VALUE);
    stowageDetails.setAbbreviation(
        StringUtils.isEmpty(stowage.getAbbreviation()) ? null : stowage.getAbbreviation());
    stowageDetails.setColorCode(
        StringUtils.isEmpty(stowage.getColorCode()) ? null : stowage.getColorCode());
    stowageDetails.setCargoXId(stowage.getCargoId());
  }

  public void buildBallastOperation(
      DischargingSequence loadingSequence,
      BallastOperation ballastOperation,
      PumpOperation pumpOperation) {
    ballastOperation.setEndTime(pumpOperation.getEndTime());
    ballastOperation.setDischargingSequence(loadingSequence);
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

  public void buildDischargingSequenceStabilityParams(
      DischargeInformation dischargingInformation,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanStabilityParameters
          param,
      DischargingSequenceStabilityParameters stabilityParameters) {
    stabilityParameters.setAftDraft(
        StringUtils.isEmpty(param.getAftDraft()) ? null : new BigDecimal(param.getAftDraft()));
    stabilityParameters.setBendingMoment(
        StringUtils.isEmpty(param.getBm()) ? null : new BigDecimal(param.getBm()));
    stabilityParameters.setDraft(
        StringUtils.isEmpty(param.getDraft()) ? null : new BigDecimal(param.getDraft()));
    stabilityParameters.setForeDraft(
        StringUtils.isEmpty(param.getForeDraft()) ? null : new BigDecimal(param.getForeDraft()));
    stabilityParameters.setIsActive(true);
    stabilityParameters.setDischargingInformation(dischargingInformation);
    stabilityParameters.setPortXId(dischargingInformation.getPortXid());
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
  public void buildDischargingPlanCommingleDetails(
      DischargingPlanPortWiseDetails loadingPlanPortWiseDetails,
      DischargingPlanCommingleDetails commingleDetails,
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
    commingleDetails.setDischargingPlanPortWiseDetails(loadingPlanPortWiseDetails);
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
   * @param dischargingInformation
   * @param commingleDetails
   * @param commingle
   */
  public void buildPortCommingle(
      DischargeInformation dischargingInformation,
      PortDischargingPlanCommingleDetails commingleDetails,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanCommingleDetails
          commingle) {
    commingleDetails.setApi(commingle.getApi());
    commingleDetails.setGrade(commingle.getAbbreviation());
    commingleDetails.setIsActive(true);
    commingleDetails.setDischargingInformation(dischargingInformation);
    commingleDetails.setLoadablePatternId(dischargingInformation.getDischargingPatternXid());
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
    commingleDetails.setConditionType(commingle.getConditionType());
    commingleDetails.setValueType(DischargePlanConstants.DISCHARGING_PLAN_PLANNED_TYPE_VALUE);
  }
}
