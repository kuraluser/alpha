/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.algo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.loadingplan.entity.*;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(
    classes = {
      LoadingPlanBuilderService.class,
    })
public class LoadingPlanBuilderServiceTest {
  @Autowired LoadingPlanBuilderService loadingPlanBuilderService;

  @Test
  void testBuildLoadingSequence() {
    LoadingSequence loadingSequence = new LoadingSequence();
    LoadingInformation loadingInformation = new LoadingInformation();
    loadingPlanBuilderService.buildLoadingSequence(
        loadingSequence, getLoadingSequence(), loadingInformation);
    assertEquals(1l, loadingSequence.getCargoNominationXId());
  }

  @Test
  void testBuildBallastValve() {
    BallastValve ballastValve = new BallastValve();
    loadingPlanBuilderService.buildBallastValve(
        new LoadingSequence(), ballastValve, getValveModels());
    assertEquals(1, ballastValve.getTime());
  }

  @Test
  void testBuildCargoValve() {
    CargoValve cargoValve = new CargoValve();
    loadingPlanBuilderService.buildCargoValve(new LoadingSequence(), cargoValve, getValveModels());
    assertEquals(1, cargoValve.getTime());
  }

  @Test
  void testBuildDeBallastingRate() {
    DeballastingRate deballastingRate = new DeballastingRate();
    loadingPlanBuilderService.buildDeBallastingRate(
        new LoadingSequence(), deballastingRate, getRate());
    assertEquals(1, deballastingRate.getTime());
  }

  @Test
  void buildLoadingPlanPortWiseDetails() {
    com.cpdss.loadingplan.entity.LoadingPlanPortWiseDetails portWiseDetails =
        new LoadingPlanPortWiseDetails();
    loadingPlanBuilderService.buildLoadingPlanPortWiseDetails(
        new LoadingSequence(), portWiseDetails, getPortWiseDetails());
    assertEquals(1, portWiseDetails.getTime());
  }

  @Test
  void testBuildDeBallastingRatePortWise() {
    DeballastingRate deballastingRate = new DeballastingRate();
    loadingPlanBuilderService.buildDeBallastingRate(
        new LoadingPlanPortWiseDetails(), deballastingRate, getRate());
    assertEquals(1, deballastingRate.getTime());
  }

  @Test
  void testBuildLoadingPlanBallastDetails() {
    LoadingPlanBallastDetails ballastDetails = new LoadingPlanBallastDetails();
    loadingPlanBuilderService.buildLoadingPlanBallastDetails(
        new LoadingPlanPortWiseDetails(), ballastDetails, getTankDetails());
    assertEquals(1l, ballastDetails.getTankXId());
  }

  @Test
  void buildLoadingPlanRobDetails() {
    LoadingPlanRobDetails robDetails = new LoadingPlanRobDetails();
    loadingPlanBuilderService.buildLoadingPlanRobDetails(
        new LoadingPlanPortWiseDetails(), robDetails, getTankDetails());
    assertEquals(1l, robDetails.getTankXId());
  }

  @Test
  void buildLoadingPlanStowageDetails() {
    LoadingPlanStowageDetails stowageDetails = new LoadingPlanStowageDetails();
    loadingPlanBuilderService.buildLoadingPlanStowageDetails(
        new LoadingPlanPortWiseDetails(), stowageDetails, getTankDetails());
    assertEquals(1l, stowageDetails.getTankXId());
  }

  @Test
  void buildStabilityParameters() {
    LoadingPlanStabilityParameters parameters = new LoadingPlanStabilityParameters();
    loadingPlanBuilderService.buildStabilityParameters(
        new LoadingPlanPortWiseDetails(), parameters, getStabilityParameters());
    assertEquals(new BigDecimal(1), parameters.getDraft());
  }

  @Test
  void buildCargoLoadingRate() {
    CargoLoadingRate loadingRate = new CargoLoadingRate();
    loadingPlanBuilderService.buildCargoLoadingRate(
        new LoadingSequence(), loadingRate, getLoadingRate());
    assertEquals(1l, loadingRate.getTankXId());
  }

  @Test
  void buildPortBallast() {
    PortLoadingPlanBallastDetails parameters = new PortLoadingPlanBallastDetails();
    loadingPlanBuilderService.buildPortBallast(
        new LoadingInformation(), parameters, getTankDetails());
    assertEquals(new BigDecimal(1), parameters.getQuantity());
  }

  @Test
  void buildPortRob() {
    PortLoadingPlanRobDetails parameters = new PortLoadingPlanRobDetails();
    loadingPlanBuilderService.buildPortRob(new LoadingInformation(), parameters, getTankDetails());
    assertEquals(new BigDecimal(1), parameters.getQuantity());
  }

  @Test
  void buildPortStabilityParams() {
    PortLoadingPlanStabilityParameters parameters = new PortLoadingPlanStabilityParameters();
    loadingPlanBuilderService.buildPortStabilityParams(
        new LoadingInformation(), parameters, getStabilityParameters());
    assertEquals(new BigDecimal(1), parameters.getDraft());
  }

  @Test
  void buildPortStowage() {
    PortLoadingPlanStowageDetails parameters = new PortLoadingPlanStowageDetails();
    loadingPlanBuilderService.buildPortStowage(
        new LoadingInformation(), parameters, getTankDetails());
    assertEquals(new BigDecimal(1), parameters.getApi());
  }

  @Test
  void buildBallastOperation() {
    BallastOperation parameters = new BallastOperation();
    loadingPlanBuilderService.buildBallastOperation(
        new LoadingSequence(), parameters, getPumpOperation());
    assertEquals(new BigDecimal(1), parameters.getQuantityM3());
  }

  private LoadingPlanModels.PumpOperation getPumpOperation() {
    LoadingPlanModels.PumpOperation pumpOperation =
        LoadingPlanModels.PumpOperation.newBuilder()
            .setEndTime(1)
            .setPumpName("1")
            .setPumpXId(1l)
            .setQuantityM3("1")
            .setRate("1")
            .setStartTime(1)
            .build();
    return pumpOperation;
  }

  @Test
  void buildLoadingSequenceStabilityParams() {
    LoadingSequenceStabilityParameters parameters = new LoadingSequenceStabilityParameters();
    loadingPlanBuilderService.buildLoadingSequenceStabilityParams(
        new LoadingInformation(), getStabilityParameters(), parameters);
    assertEquals(new BigDecimal(1), parameters.getDraft());
  }

  @Test
  void buildLoadingPlanCommingleDetails() {
    LoadingPlanCommingleDetails parameters = new LoadingPlanCommingleDetails();
    loadingPlanBuilderService.buildLoadingPlanCommingleDetails(
        new LoadingPlanPortWiseDetails(), parameters, getCommingleDetails());
    assertEquals(new BigDecimal(1), parameters.getApi());
  }

  private LoadingPlanModels.LoadingPlanCommingleDetails getCommingleDetails() {
    LoadingPlanModels.LoadingPlanCommingleDetails commingleDetails =
        LoadingPlanModels.LoadingPlanCommingleDetails.newBuilder()
            .setAbbreviation("1")
            .setApi("1")
            .setCargo1Id(1l)
            .setCargo2Id(1l)
            .setCargoNomination1Id(1l)
            .setCargoNomination2Id(1l)
            .setColorCode("1")
            .setQuantityMT("1")
            .setQuantityM3("1")
            .setTankId(1l)
            .setTemperature("1")
            .setUllage("1")
            .setUllage1("1")
            .setUllage2("1")
            .setQuantity1MT("1")
            .setQuantity2MT("1")
            .setQuantity1M3("1")
            .setQuantity2M3("1")
            .build();
    return commingleDetails;
  }

  private LoadingPlanModels.LoadingPlanStabilityParameters getStabilityParameters() {
    LoadingPlanModels.LoadingPlanStabilityParameters parameters =
        LoadingPlanModels.LoadingPlanStabilityParameters.newBuilder()
            .setBm("1")
            .setSf("1")
            .setDraft("1")
            .setAftDraft("1")
            .setForeDraft("1")
            .setMeanDraft("1")
            .setTrim("1")
            .setList("1")
            .build();
    return parameters;
  }

  @Test
  void buildPortCommingle() {
    PortLoadingPlanCommingleDetails loadingRate = new PortLoadingPlanCommingleDetails();
    loadingPlanBuilderService.buildPortCommingle(
        new LoadingInformation(), loadingRate, new LinkedHashMap<>(), getCommingleDetails());
    assertEquals("1", loadingRate.getApi());
  }

  private LoadingPlanModels.LoadingRate getLoadingRate() {
    LoadingPlanModels.LoadingRate loadingRate =
        LoadingPlanModels.LoadingRate.newBuilder().setLoadingRate("1").setTankId(1l).build();
    return loadingRate;
  }

  private LoadingPlanModels.LoadingPlanTankDetails getTankDetails() {
    LoadingPlanModels.LoadingPlanTankDetails details =
        LoadingPlanModels.LoadingPlanTankDetails.newBuilder()
            .setQuantity("1")
            .setTankId(1l)
            .setSounding("1")
            .setQuantityM3("1")
            .setColorCode("1")
            .setSg("1")
            .setApi("1")
            .build();
    return details;
  }

  private LoadingPlanModels.LoadingPlanPortWiseDetails getPortWiseDetails() {
    LoadingPlanModels.LoadingPlanPortWiseDetails portWiseDetails =
        LoadingPlanModels.LoadingPlanPortWiseDetails.newBuilder().setTime(1).build();
    return portWiseDetails;
  }

  private LoadingPlanModels.DeBallastingRate getRate() {
    LoadingPlanModels.DeBallastingRate rate =
        LoadingPlanModels.DeBallastingRate.newBuilder()
            .setTankId(1l)
            .setDeBallastingRate("1")
            .setTime(1)
            .build();
    return rate;
  }

  private LoadingPlanModels.Valve getValveModels() {
    LoadingPlanModels.Valve valve =
        LoadingPlanModels.Valve.newBuilder()
            .setOperation("1")
            .setTime(1)
            .setValveCode("1")
            .setValveType("1")
            .setValveId(1l)
            .build();
    return valve;
  }

  private LoadingPlanModels.LoadingSequence getLoadingSequence() {
    LoadingPlanModels.LoadingSequence sequence =
        LoadingPlanModels.LoadingSequence.newBuilder()
            .setCargoNominationId(1l)
            .setEndTime(1)
            .setSequenceNumber(1)
            .setStartTime(1)
            .setToLoadicator(true)
            .setCargoLoadingRate1("1")
            .setCargoLoadingRate2("1")
            .build();
    return sequence;
  }
}
