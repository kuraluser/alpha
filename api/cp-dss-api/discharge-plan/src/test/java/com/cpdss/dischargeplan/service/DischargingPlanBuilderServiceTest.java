/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cpdss.common.generated.discharge_plan.DischargingRate;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.dischargeplan.entity.*;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(
    classes = {
      DischargingPlanBuilderService.class,
    })
public class DischargingPlanBuilderServiceTest {

  @Autowired DischargingPlanBuilderService dischargingPlanBuilderService;

  @Test
  void testBuildDischargingSequence() {
    DischargingSequence loadingSequence = new DischargingSequence();
    com.cpdss.common.generated.discharge_plan.DischargingSequence sequence =
        com.cpdss.common.generated.discharge_plan.DischargingSequence.newBuilder()
            .setCargoNominationId(1L)
            .setSequenceNumber(1)
            .setStageName("1")
            .setStartTime(1)
            .setToLoadicator(true)
            .setCargoDischargingRate1("1")
            .setCargoDischargingRate2("1")
            .setEndTime(1)
            .build();
    DischargeInformation dischargingInformation = new DischargeInformation();
    dischargingInformation.setPortXid(1L);
    this.dischargingPlanBuilderService.buildDischargingSequence(
        loadingSequence, sequence, dischargingInformation);
    assertEquals(1L, loadingSequence.getCargoNominationXId());
  }

  @Test
  void testBuildBallastValve() {
    DischargingSequence loadingSequence = new DischargingSequence();
    BallastValve ballastValve = new BallastValve();
    LoadingPlanModels.Valve valve =
        LoadingPlanModels.Valve.newBuilder()
            .setOperation("1")
            .setTime(1)
            .setValveCode("1")
            .setValveType("1")
            .setValveId(1L)
            .build();
    this.dischargingPlanBuilderService.buildBallastValve(loadingSequence, ballastValve, valve);
    assertEquals("1", ballastValve.getOperation());
  }

  @Test
  void testBuildCargoValve() {
    DischargingSequence loadingSequence = new DischargingSequence();
    CargoValve cargoValve = new CargoValve();
    LoadingPlanModels.Valve valve =
        LoadingPlanModels.Valve.newBuilder()
            .setOperation("1")
            .setTime(1)
            .setValveCode("1")
            .setValveType("1")
            .setValveId(1L)
            .build();
    this.dischargingPlanBuilderService.buildCargoValve(loadingSequence, cargoValve, valve);
    assertEquals(1L, cargoValve.getValveXId());
  }

  @Test
  void testBuildDeBallastingRate() {
    DischargingSequence loadingSequence = new DischargingSequence();
    DeballastingRate deballastingRate = new DeballastingRate();
    LoadingPlanModels.DeBallastingRate rate =
        LoadingPlanModels.DeBallastingRate.newBuilder()
            .setTankId(1L)
            .setDeBallastingRate("1")
            .setTime(1)
            .build();
    this.dischargingPlanBuilderService.buildDeBallastingRate(
        loadingSequence, deballastingRate, rate);
    assertEquals(1L, deballastingRate.getTankXId());
  }

  @Test
  void testBuildDischargingPlanPortWiseDetails() {
    DischargingSequence loadingSequence = new DischargingSequence();
    DischargingPlanPortWiseDetails portWiseDetails = new DischargingPlanPortWiseDetails();
    LoadingPlanModels.LoadingPlanPortWiseDetails details =
        LoadingPlanModels.LoadingPlanPortWiseDetails.newBuilder().setTime(1).build();
    this.dischargingPlanBuilderService.buildDischargingPlanPortWiseDetails(
        loadingSequence, portWiseDetails, details);
    assertEquals(1, portWiseDetails.getTime());
  }

  @Test
  void testbuildDeBallastingRate3diffargs() {
    DischargingPlanPortWiseDetails dischargingPlanPortWiseDetails =
        new DischargingPlanPortWiseDetails();
    DeballastingRate deballastingRate = new DeballastingRate();
    LoadingPlanModels.DeBallastingRate rate =
        LoadingPlanModels.DeBallastingRate.newBuilder()
            .setTankId(1L)
            .setDeBallastingRate("1")
            .setTime(1)
            .build();
    this.dischargingPlanBuilderService.buildDeBallastingRate(
        dischargingPlanPortWiseDetails, deballastingRate, rate);
    assertEquals(1L, deballastingRate.getTankXId());
  }

  @Test
  void testBuildDischargingPlanBallastDetails() {
    DischargingPlanPortWiseDetails loadingPlanPortWiseDetails =
        new DischargingPlanPortWiseDetails();
    DischargingPlanBallastDetails ballastDetails = new DischargingPlanBallastDetails();
    LoadingPlanModels.LoadingPlanTankDetails ballast =
        LoadingPlanModels.LoadingPlanTankDetails.newBuilder()
            .setQuantity("1")
            .setTankId(1L)
            .setSounding("1")
            .setQuantityM3("1")
            .setColorCode("1")
            .setSg("1")
            .build();
    this.dischargingPlanBuilderService.buildDischargingPlanBallastDetails(
        loadingPlanPortWiseDetails, ballastDetails, ballast);
    assertEquals("1", ballastDetails.getColorCode());
  }

  @Test
  void testbuildDischargingPlanRobDetails() {
    DischargingPlanPortWiseDetails loadingPlanPortWiseDetails =
        new DischargingPlanPortWiseDetails();
    DischargingPlanRobDetails robDetails = new DischargingPlanRobDetails();
    LoadingPlanModels.LoadingPlanTankDetails rob =
        LoadingPlanModels.LoadingPlanTankDetails.newBuilder()
            .setQuantityM3("1")
            .setTankId(1L)
            .setQuantityM3("1")
            .setColorCode("1")
            .build();
    this.dischargingPlanBuilderService.buildDischargingPlanRobDetails(
        loadingPlanPortWiseDetails, robDetails, rob);
    assertEquals(1L, robDetails.getTankXId());
  }

  @Test
  void testBuildDischargingPlanStowageDetails() {
    DischargingPlanPortWiseDetails loadingPlanPortWiseDetails =
        new DischargingPlanPortWiseDetails();
    DischargingPlanStowageDetails stowageDetails = new DischargingPlanStowageDetails();
    com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanTankDetails stowage =
        com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanTankDetails
            .newBuilder()
            .setApi("1")
            .setCargoNominationId(1L)
            .setQuantity("1")
            .setTankId(1L)
            .setTemperature("1")
            .setUllage("1")
            .setQuantityM3("1")
            .setAbbreviation("1")
            .setColorCode("1")
            .build();
    this.dischargingPlanBuilderService.buildDischargingPlanStowageDetails(
        loadingPlanPortWiseDetails, stowageDetails, stowage);
    assertEquals(1L, stowageDetails.getTankXId());
  }

  @Test
  void testBuildStabilityParameters() {
    DischargingPlanPortWiseDetails loadingPlanPortWiseDetails =
        new DischargingPlanPortWiseDetails();
    DischargingPlanStabilityParameters parameters = new DischargingPlanStabilityParameters();
    com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanStabilityParameters
        loadingPlanStabilityParameters =
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanStabilityParameters
                .newBuilder()
                .setBm("1")
                .setSf("1")
                .setDraft("1")
                .setForeDraft("1")
                .setAftDraft("1")
                .setMeanDraft("1")
                .setTrim("1")
                .setList("1")
                .build();
    this.dischargingPlanBuilderService.buildStabilityParameters(
        loadingPlanPortWiseDetails, parameters, loadingPlanStabilityParameters);
    assertEquals(new BigDecimal(1), parameters.getAftDraft());
  }

  @Test
  void testBuildCargoDischargingRate() {
    DischargingSequence loadingSequence = new DischargingSequence();
    CargoDischargingRate cargoLoadingRate = new CargoDischargingRate();
    DischargingRate dischargingRate =
        DischargingRate.newBuilder().setDischargingRate("1").setTankId(1L).build();
    this.dischargingPlanBuilderService.buildCargoDischargingRate(
        loadingSequence, cargoLoadingRate, dischargingRate);
    assertEquals(1L, cargoLoadingRate.getTankXId());
  }

  @Test
  void testBuildPortBallast() {
    DischargeInformation dischargingInformation = new DischargeInformation();
    dischargingInformation.setPortXid(1L);
    dischargingInformation.setPortRotationXid(1L);
    PortDischargingPlanBallastDetails ballastDetails = new PortDischargingPlanBallastDetails();
    LoadingPlanModels.LoadingPlanTankDetails ballast =
        LoadingPlanModels.LoadingPlanTankDetails.newBuilder()
            .setConditionType(1)
            .setSounding("1")
            .setQuantityM3("1")
            .setQuantity("1")
            .setTankId(1L)
            .setTemperature("1")
            .setColorCode("1")
            .setSg("1")
            .build();
    this.dischargingPlanBuilderService.buildPortBallast(
        dischargingInformation, ballastDetails, ballast);
    assertEquals(1, ballastDetails.getConditionType());
  }

  @Test
  void testBuildPortRob() {
    DischargeInformation dischargingInformation = new DischargeInformation();
    dischargingInformation.setId(1L);
    dischargingInformation.setPortRotationXid(1L);
    dischargingInformation.setPortXid(1L);

    PortDischargingPlanRobDetails robDetails = new PortDischargingPlanRobDetails();
    LoadingPlanModels.LoadingPlanTankDetails rob =
        LoadingPlanModels.LoadingPlanTankDetails.newBuilder()
            .setQuantity("1")
            .setTankId(1L)
            .setQuantityM3("1")
            .setColorCode("1")
            .setDensity("1")
            .setConditionType(1)
            .build();
    this.dischargingPlanBuilderService.buildPortRob(dischargingInformation, robDetails, rob);
    assertEquals(new BigDecimal(1), robDetails.getDensity());
  }

  @Test
  void testBuildPortStabilityParams() {
    DischargeInformation dischargingInformation = new DischargeInformation();
    dischargingInformation.setPortXid(1L);
    dischargingInformation.setPortRotationXid(1L);
    PortDischargingPlanStabilityParameters stabilityParams =
        new PortDischargingPlanStabilityParameters();
    com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanStabilityParameters
        params =
            com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanStabilityParameters
                .newBuilder()
                .setBm("1")
                .setConditionType(1)
                .setSf("1")
                .setAftDraft("1")
                .setForeDraft("1")
                .setMeanDraft("1")
                .setTrim("1")
                .setList("1")
                .setDraft("1")
                .build();
    this.dischargingPlanBuilderService.buildPortStabilityParams(
        dischargingInformation, stabilityParams, params);
    assertEquals(new BigDecimal(1), stabilityParams.getBendingMoment());
  }

  @Test
  void testBuildPortStowage() {
    DischargeInformation dischargingInformation = new DischargeInformation();
    dischargingInformation.setPortXid(1L);
    dischargingInformation.setPortRotationXid(1L);
    PortDischargingPlanStowageDetails stowageDetails = new PortDischargingPlanStowageDetails();
    LoadingPlanModels.LoadingPlanTankDetails stowage =
        LoadingPlanModels.LoadingPlanTankDetails.newBuilder()
            .setApi("1")
            .setConditionType(1)
            .setCargoNominationId(1L)
            .setQuantityM3("1")
            .setQuantity("1")
            .setTankId(1L)
            .setTemperature("1")
            .setAbbreviation("1")
            .setColorCode("1")
            .setCargoId(1L)
            .setUllage("1")
            .build();
    this.dischargingPlanBuilderService.buildPortStowage(
        dischargingInformation, stowageDetails, stowage);
    assertEquals(1L, stowageDetails.getCargoNominationXId());
  }

  @Test
  void testBuildBallastOperation() {
    DischargingSequence loadingSequence = new DischargingSequence();
    BallastOperation ballastOperation = new BallastOperation();
    LoadingPlanModels.PumpOperation pumpOperation =
        LoadingPlanModels.PumpOperation.newBuilder()
            .setEndTime(1)
            .setPumpName("1")
            .setPumpXId(1L)
            .setQuantityM3("1")
            .setRate("1")
            .setStartTime(1)
            .build();
    this.dischargingPlanBuilderService.buildBallastOperation(
        loadingSequence, ballastOperation, pumpOperation);
    assertEquals(1L, ballastOperation.getPumpXId());
  }

  @Test
  void testBuildDischargingSequenceStabilityParams() {
    DischargeInformation dischargingInformation = new DischargeInformation();
    dischargingInformation.setPortXid(1L);
    com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanStabilityParameters param =
        LoadingPlanModels.LoadingPlanStabilityParameters.newBuilder()
            .setAftDraft("1")
            .setBm("1")
            .setDraft("1")
            .setSf("1")
            .setMeanDraft("1")
            .setTrim("1")
            .setList("1")
            .setTime(1)
            .setForeDraft("1")
            .build();
    DischargingSequenceStabilityParameters stabilityParameters =
        new DischargingSequenceStabilityParameters();
    this.dischargingPlanBuilderService.buildDischargingSequenceStabilityParams(
        dischargingInformation, param, stabilityParameters);
    assertEquals(1L, stabilityParameters.getPortXId());
  }

  @Test
  void testBuildDischargingPlanCommingleDetails() {
    DischargingPlanPortWiseDetails loadingPlanPortWiseDetails =
        new DischargingPlanPortWiseDetails();
    DischargingPlanCommingleDetails commingleDetails = new DischargingPlanCommingleDetails();
    com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanCommingleDetails
        commingle =
            LoadingPlanModels.LoadingPlanCommingleDetails.newBuilder()
                .setAbbreviation("1")
                .setApi("1")
                .setCargo1Id(1L)
                .setCargo2Id(1L)
                .setCargoNomination1Id(1L)
                .setCargoNomination2Id(1L)
                .setColorCode("1")
                .setQuantityMT("1")
                .setQuantityM3("1")
                .setTankId(1L)
                .setTemperature("1")
                .setUllage("1")
                .build();
    this.dischargingPlanBuilderService.buildDischargingPlanCommingleDetails(
        loadingPlanPortWiseDetails, commingleDetails, commingle);
    assertEquals(new BigDecimal(1), commingleDetails.getQuantity());
  }

  @Test
  void testBuildPortCommingle() {
    DischargeInformation dischargingInformation = new DischargeInformation();
    dischargingInformation.setDischargingPatternXid(1L);
    PortDischargingPlanCommingleDetails commingleDetails =
        new PortDischargingPlanCommingleDetails();
    com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingPlanCommingleDetails
        commingle =
            LoadingPlanModels.LoadingPlanCommingleDetails.newBuilder()
                .setApi("1")
                .setAbbreviation("1")
                .setQuantityMT("1")
                .setTankId(1L)
                .setTemperature("1")
                .setCargoNomination1Id(1L)
                .setCargoNomination2Id(1L)
                .setCargo1Id(1L)
                .setCargo2Id(1L)
                .setColorCode("1")
                .setQuantityM3("1")
                .setUllage("1")
                .setConditionType(1)
                .build();
    this.dischargingPlanBuilderService.buildPortCommingle(
        dischargingInformation, commingleDetails, commingle);
    assertEquals(1L, commingleDetails.getCargo1XId());
  }
}
