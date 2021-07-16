/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.algo;

import com.cpdss.common.generated.loading_plan.LoadingPlanModels.DeBallastingRate;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanPortWiseDetails;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanTankDetails;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.Valve;
import com.cpdss.loadingplan.entity.BallastValve;
import com.cpdss.loadingplan.entity.CargoValve;
import com.cpdss.loadingplan.entity.DeballastingRate;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.entity.LoadingPlanBallastDetails;
import com.cpdss.loadingplan.entity.LoadingPlanRobDetails;
import com.cpdss.loadingplan.entity.LoadingPlanStowageDetails;
import com.cpdss.loadingplan.entity.LoadingSequence;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class LoadingPlanBuilderService {

  public void buildLoadingSequence(
      LoadingSequence loadingSequence,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingSequence sequence,
      LoadingInformation loadingInformation) {
    loadingSequence.setCargoNominationId(sequence.getCargoNominationId());
    loadingSequence.setEndTime(sequence.getEndTime());
    loadingSequence.setIsActive(true);
    loadingSequence.setLoadingInformation(loadingInformation);
    loadingSequence.setPortXId(sequence.getPortId());
    loadingSequence.setSequenceNumber(sequence.getSequenceNumber());
    loadingSequence.setStageName(sequence.getStageName());
    loadingSequence.setStartTime(sequence.getStartTime());
    loadingSequence.setToLoadicator(sequence.getToLoadicator());
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
    deballastingRate.setLoadingRate(
        StringUtils.isEmpty(rate.getLoadingRate()) ? null : new BigDecimal(rate.getLoadingRate()));
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
    ballastDetails.setQuantity(ballast.getQuantity());
    ballastDetails.setTankXId(ballast.getTankId());
  }

  public void buildLoadingPlanRobDetails(
      com.cpdss.loadingplan.entity.LoadingPlanPortWiseDetails loadingPlanPortWiseDetails,
      LoadingPlanRobDetails robDetails,
      LoadingPlanTankDetails rob) {
    robDetails.setIsActive(true);
    robDetails.setLoadingPlanPortWiseDetails(loadingPlanPortWiseDetails);
    robDetails.setQuantity(rob.getQuantity());
    robDetails.setTankXId(rob.getTankId());
  }

  public void buildLoadingPlanStowageDetails(
      com.cpdss.loadingplan.entity.LoadingPlanPortWiseDetails loadingPlanPortWiseDetails,
      LoadingPlanStowageDetails stowageDetails,
      com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanStowageDetails stowage) {
    stowageDetails.setApi(stowage.getApi());
    stowageDetails.setCargoNominationId(stowage.getCargoNominationId());
    stowageDetails.setIsActive(true);
    stowageDetails.setLoadingPlanPortWiseDetails(loadingPlanPortWiseDetails);
    stowageDetails.setQuantity(stowage.getQuantity());
    stowageDetails.setTankXId(stowage.getTankId());
    stowageDetails.setTemperature(stowage.getTemperature());
  }
}
