/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.impl;

import static com.cpdss.loadingplan.common.LoadingPlanConstants.SUCCESS;
import static org.junit.Assert.assertEquals;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.common.generated.VesselInfoServiceGrpc;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.entity.LoadingRule;
import com.cpdss.loadingplan.entity.LoadingRuleInput;
import com.cpdss.loadingplan.repository.LoadingRuleInputRepository;
import com.cpdss.loadingplan.repository.LoadingRuleRepository;
import com.cpdss.loadingplan.service.LoadingInformationService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;

@SpringJUnitConfig(classes = {LoadingPlanRuleServiceImpl.class})
public class LoadingPlanRuleServiceImpltest {

  @Autowired LoadingPlanRuleServiceImpl loadingPlanRuleService;

  @MockBean LoadingInformationService loadingInformationService;

  @MockBean private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @MockBean LoadingRuleRepository loadingRuleRepository;

  @MockBean LoadingRuleInputRepository loadingRuleInputRepository;

  @Test
  void testGetOrSaveRulesForLoadingPlan() {
    List<Common.RulesInputs> list2 = new ArrayList<>();
    Common.RulesInputs rulesInputs =
        Common.RulesInputs.newBuilder()
            .setId("1")
            .setDefaultValue(",")
            .setMax("1")
            .setMin("1")
            .setSuffix("tank can be filled with commingled cargo")
            .setPrefix("only")
            .setType("2")
            .setIsMandatory(false)
            .build();
    list2.add(rulesInputs);
    List<Common.Rules> list1 = new ArrayList<>();
    Common.Rules rules =
        Common.Rules.newBuilder()
            .addAllInputs(list2)
            .setVesselRuleXId("1")
            .setRuleTemplateId("1")
            .setId("1")
            .setRuleType("1")
            .setDisplayInSettings(false)
            .setEnable(false)
            .setNumericPrecision(1L)
            .setNumericScale(1L)
            .setIsHardRule(false)
            .build();
    list1.add(rules);
    List<Common.RulePlans> list = new ArrayList<>();
    Common.RulePlans rulePlans =
        Common.RulePlans.newBuilder().addAllRules(list1).setHeader("1").build();
    list.add(rulePlans);
    LoadingPlanModels.LoadingPlanRuleRequest request =
        LoadingPlanModels.LoadingPlanRuleRequest.newBuilder()
            .addAllRulePlan(list)
            .setSectionId(2L)
            .setVesselId(1L)
            .setLoadingInfoId(1L)
            .build();
    LoadingPlanModels.LoadingPlanRuleReply.Builder builder =
        LoadingPlanModels.LoadingPlanRuleReply.newBuilder();
    Mockito.when(this.loadingInformationService.getLoadingInformation(Mockito.anyLong()))
        .thenReturn(getOLI());
    Mockito.when(this.vesselInfoGrpcService.getRulesByVesselIdAndSectionId(Mockito.any()))
        .thenReturn(getVRR());
    Mockito.when(loadingRuleRepository.findById(Mockito.anyLong())).thenReturn(getOLR());
    Mockito.when(loadingRuleInputRepository.findById(Mockito.anyLong())).thenReturn(getOLRI());
    Mockito.when(
            loadingRuleRepository
                .findByLoadingXidAndVesselXidAndIsActiveTrueAndVesselRuleXidInOrderById(
                    Mockito.anyLong(), Mockito.anyLong(), Mockito.anyList()))
        .thenReturn(getLLR());
    ReflectionTestUtils.setField(
        loadingPlanRuleService, "vesselInfoGrpcService", this.vesselInfoGrpcService);
    try {
      this.loadingPlanRuleService.getOrSaveRulesForLoadingPlan(request, builder);
      assertEquals(SUCCESS, builder.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private Optional<LoadingInformation> getOLI() {
    LoadingInformation loadingInformation = new LoadingInformation();
    loadingInformation.setId(1L);
    return Optional.of(loadingInformation);
  }

  private VesselInfo.VesselRuleReply getVRR() {
    List<Common.RulesInputs> list5 = new ArrayList<>();
    Common.RulesInputs rulesInputs =
        Common.RulesInputs.newBuilder()
            .setPrefix("1")
            .setDefaultValue("1")
            .setType("1")
            .setMax("1")
            .setMin("1")
            .setSuffix("1")
            .setIsMandatory(false)
            .build();
    list5.add(rulesInputs);
    List<Common.Rules> list4 = new ArrayList<>();
    Common.Rules rules =
        Common.Rules.newBuilder()
            .addAllInputs(list5)
            .setVesselRuleXId("1")
            .setRuleType("1")
            .setDisplayInSettings(false)
            .setEnable(false)
            .setIsHardRule(false)
            .setNumericScale(1L)
            .setNumericPrecision(1L)
            .setRuleTemplateId("1")
            .build();
    list4.add(rules);
    List<Common.RulePlans> list3 = new ArrayList<>();
    Common.RulePlans rulePlans =
        Common.RulePlans.newBuilder().addAllRules(list4).setHeader("1").build();
    list3.add(rulePlans);
    List<VesselInfo.CargoTankMaster> list2 = new ArrayList<>();
    VesselInfo.CargoTankMaster cargoTankMaster = VesselInfo.CargoTankMaster.newBuilder().build();
    list2.add(cargoTankMaster);
    List<VesselInfo.RuleDropDownValueMaster> list1 = new ArrayList<>();
    VesselInfo.RuleDropDownValueMaster master =
        VesselInfo.RuleDropDownValueMaster.newBuilder().setId(1L).setRuleTemplateId(1L).build();
    list1.add(master);
    List<VesselInfo.RuleTypeMaster> list = new ArrayList<>();
    VesselInfo.RuleTypeMaster ruleTypeMaster =
        VesselInfo.RuleTypeMaster.newBuilder().setRuleType("1").setId(1L).build();
    list.add(ruleTypeMaster);
    VesselInfo.VesselRuleReply ruleReply =
        VesselInfo.VesselRuleReply.newBuilder()
            .addAllRulePlan(list3)
            .addAllRuleDropDownValueMaster(list1)
            .addAllCargoTankMaster(list2)
            .addAllRuleTypeMaster(list)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return ruleReply;
  }

  private Optional<LoadingRule> getOLR() {
    LoadingRule loadingRule = new LoadingRule();
    loadingRule.setId(1L);
    return Optional.of(loadingRule);
  }

  private Optional<LoadingRuleInput> getOLRI() {
    LoadingRuleInput loadingRuleInput = new LoadingRuleInput();
    loadingRuleInput.setId(1L);
    return Optional.of(loadingRuleInput);
  }

  private List<LoadingRule> getLLR() {
    List<LoadingRule> list = new ArrayList<>();
    LoadingRule loadingRule = new LoadingRule();
    loadingRule.setId(1L);
    loadingRule.setVesselRuleXid(1L);
    loadingRule.setIsEnable(false);
    loadingRule.setDisplayInSettings(false);
    loadingRule.setRuleTypeXid(1L);
    loadingRule.setParentRuleXid(1L);
    loadingRule.setNumericPrecision(1L);
    loadingRule.setNumericScale(1L);
    loadingRule.setLoadingRuleInputs(getLLRI());
    list.add(loadingRule);
    return list;
  }

  private List<LoadingRuleInput> getLLRI() {
    List<LoadingRuleInput> loadingRuleInputs = new ArrayList<>();
    LoadingRuleInput loadingRuleInput = new LoadingRuleInput();
    loadingRuleInput.setId(1L);
    loadingRuleInput.setDefaultValue("1");
    loadingRuleInput.setPrefix("1");
    loadingRuleInput.setMinValue("1");
    loadingRuleInput.setMaxValue("1");
    loadingRuleInput.setTypeValue("Boolean");
    loadingRuleInput.setSuffix("1");
    loadingRuleInput.setIsMandatory(false);

    loadingRuleInputs.add(loadingRuleInput);
    return loadingRuleInputs;
  }

  @Test
  void testGetLoadingPlanRuleForAlgo() {
    Long vesselId = 1L;
    Long loadingInfoId = 1L;
    Mockito.when(this.loadingInformationService.getLoadingInformation(Mockito.anyLong()))
        .thenReturn(getOLI());
    Mockito.when(this.vesselInfoGrpcService.getRulesByVesselIdAndSectionId(Mockito.any()))
        .thenReturn(getVRR());
    Mockito.when(
            loadingRuleRepository
                .findByLoadingXidAndVesselXidAndIsActiveTrueAndVesselRuleXidInOrderById(
                    Mockito.anyLong(), Mockito.anyLong(), Mockito.anyList()))
        .thenReturn(getLLR());
    ReflectionTestUtils.setField(
        loadingPlanRuleService, "vesselInfoGrpcService", this.vesselInfoGrpcService);
    var builder = this.loadingPlanRuleService.getLoadingPlanRuleForAlgo(vesselId, loadingInfoId);
    assertEquals(SUCCESS, builder.getResponseStatus().getStatus());
  }

  @Test
  void testSaveRulesAgainstLoadingInformation() {
    LoadingInformation loadingInformation = new LoadingInformation();
    loadingInformation.setVesselXId(1L);
    loadingInformation.setId(1L);
    Mockito.when(this.vesselInfoGrpcService.getRulesByVesselIdAndSectionId(Mockito.any()))
        .thenReturn(getVRR());
    ReflectionTestUtils.setField(
        loadingPlanRuleService, "vesselInfoGrpcService", this.vesselInfoGrpcService);
    try {
      this.loadingPlanRuleService.saveRulesAgainstLoadingInformation(loadingInformation);
      Mockito.verify(loadingRuleRepository).saveAll(Mockito.anyList());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }
}
