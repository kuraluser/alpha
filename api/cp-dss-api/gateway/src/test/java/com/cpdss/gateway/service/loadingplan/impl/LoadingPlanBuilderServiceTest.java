/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.loadingplan.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.gateway.service.loadingplan.LoadingPlanBuilderService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = {LoadingPlanBuilderService.class})
public class LoadingPlanBuilderServiceTest {

  @Autowired LoadingPlanBuilderService loadingPlanBuilderService;

  @Test
  void testBuildLoadingPlanStowageFromRpc() {
    List<LoadingPlanModels.LoadingPlanTankDetails> list = new ArrayList<>();
    LoadingPlanModels.LoadingPlanTankDetails details =
        LoadingPlanModels.LoadingPlanTankDetails.newBuilder()
            .setApi("1")
            .setTemperature("1")
            .setQuantityM3("1")
            .setCargoNominationId(1L)
            .setTankId(1L)
            .setQuantity("1")
            .setConditionType(1)
            .setValueType(1)
            .setUllage("1")
            .setAbbreviation("1")
            .setCargoId(1L)
            .setColorCode("1")
            .build();
    list.add(details);
    var response = this.loadingPlanBuilderService.buildLoadingPlanStowageFromRpc(list);
    assertEquals(1L, response.get(0).getCargoNominationId());
  }

  @Test
  void testBuildLoadingPlanBallastFromRpc() {
    List<LoadingPlanModels.LoadingPlanTankDetails> list = new ArrayList<>();
    LoadingPlanModels.LoadingPlanTankDetails details =
        LoadingPlanModels.LoadingPlanTankDetails.newBuilder()
            .setApi("1")
            .setTemperature("1")
            .setQuantityM3("1")
            .setCargoNominationId(1L)
            .setSounding("1")
            .setTankId(1L)
            .setQuantity("1")
            .setSg("1")
            .setConditionType(1)
            .setValueType(1)
            .setUllage("1")
            .setAbbreviation("1")
            .setCargoId(1L)
            .setColorCode("1")
            .build();
    list.add(details);
    var response = this.loadingPlanBuilderService.buildLoadingPlanBallastFromRpc(list);
    assertEquals(1, response.get(0).getConditionType());
  }

  @Test
  void testBuildLoadingPlanRobFromRpc() {
    List<LoadingPlanModels.LoadingPlanTankDetails> list = new ArrayList<>();
    LoadingPlanModels.LoadingPlanTankDetails details =
        LoadingPlanModels.LoadingPlanTankDetails.newBuilder()
            .setApi("1")
            .setTemperature("1")
            .setQuantityM3("1")
            .setCargoNominationId(1L)
            .setSounding("1")
            .setDensity("1")
            .setTankId(1L)
            .setQuantity("1")
            .setSg("1")
            .setConditionType(1)
            .setValueType(1)
            .setUllage("1")
            .setAbbreviation("1")
            .setCargoId(1L)
            .setColorCode("1")
            .build();
    list.add(details);
    var response = this.loadingPlanBuilderService.buildLoadingPlanRobFromRpc(list);
    assertEquals(new BigDecimal(1), response.get(0).getDensity());
  }

  @Test
  void testBuildLoadingPlanStabilityParamFromRpc() {
    List<LoadingPlanModels.LoadingPlanStabilityParameters> list = new ArrayList<>();
    LoadingPlanModels.LoadingPlanStabilityParameters planStabilityParameters =
        LoadingPlanModels.LoadingPlanStabilityParameters.newBuilder()
            .setBm("1")
            .setSf("1")
            .setMeanDraft("1")
            .setForeDraft("1")
            .setAftDraft("1")
            .setTrim("1")
            .setFreeboard("1")
            .setManifoldHeight("1")
            .setConditionType(1)
            .setValueType(1)
            .build();
    list.add(planStabilityParameters);
    var response = this.loadingPlanBuilderService.buildLoadingPlanStabilityParamFromRpc(list);
    assertEquals(1, response.get(0).getConditionType());
  }

  @Test
  void testBuildLoadingPlanCommingleFromRpc() {
    List<LoadingPlanModels.LoadingPlanCommingleDetails> portLoadingPlanCommingleDetailsList =
        new ArrayList<>();
    LoadingPlanModels.LoadingPlanCommingleDetails details =
        LoadingPlanModels.LoadingPlanCommingleDetails.newBuilder()
            .setAbbreviation("1")
            .setApi("1")
            .setCargo1Id(1L)
            .setCargo2Id(1L)
            .setCargoNomination2Id(1L)
            .setCargoNomination1Id(1L)
            .setId(1L)
            .setQuantityM3("1")
            .setQuantityMT("1")
            .setTankId(1L)
            .setTemperature("1")
            .setUllage("1")
            .setQuantity1MT("1")
            .setQuantity2MT("1")
            .setQuantity1M3("1")
            .setQuantity2M3("1")
            .setUllage1("1")
            .setUllage2("1")
            .setConditionType(1)
            .setValueType(1)
            .setColorCode("1")
            .build();
    portLoadingPlanCommingleDetailsList.add(details);
    var response =
        this.loadingPlanBuilderService.buildLoadingPlanCommingleFromRpc(
            portLoadingPlanCommingleDetailsList);
    assertEquals(1, response.get(0).getConditionType());
  }
}
