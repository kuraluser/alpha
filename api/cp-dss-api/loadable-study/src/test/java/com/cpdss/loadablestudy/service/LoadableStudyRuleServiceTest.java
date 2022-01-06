/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.common.generated.VesselInfoServiceGrpc;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.entity.LoadableStudyRuleInput;
import com.cpdss.loadablestudy.entity.LoadableStudyRules;
import com.cpdss.loadablestudy.repository.LoadableStudyRepository;
import com.cpdss.loadablestudy.repository.LoadableStudyRuleInputRepository;
import com.cpdss.loadablestudy.repository.LoadableStudyRuleRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;

@SpringJUnitConfig(classes = {LoadableStudyRuleService.class})
public class LoadableStudyRuleServiceTest {

  @Autowired private LoadableStudyRuleService loadableStudyRuleService;
  @MockBean private LoadableStudyRepository loadableStudyRepository;
  @MockBean private LoadableStudyRuleRepository loadableStudyRuleRepository;
  @MockBean private LoadableStudyRuleInputRepository loadableStudyRuleInputRepository;
  @MockBean private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;
  public static final String SUCCESS = "SUCCESS";

  @Test
  void testGetLoadableStudyRules() {
    com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail request =
        com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail.newBuilder()
            .setVesselId(1L)
            .setDuplicatedFromId(1L)
            .build();
    when(this.loadableStudyRepository.findByIdAndIsActive(Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLoadablestudy());
    when(loadableStudyRuleRepository.findByLoadableStudyAndVesselXIdAndIsActive(
            Mockito.any(), Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLSR());
    try {
      var loadableStudyRules = this.loadableStudyRuleService.getLoadableStudyRules(request);
      assertEquals(Optional.of(1L), Optional.of(loadableStudyRules.get(0).getRuleTypeXId()));
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private Optional<LoadableStudy> getLoadablestudy() {
    LoadableStudy loadableStudy = new LoadableStudy();
    loadableStudy.setId(1L);
    loadableStudy.setVesselXId(1l);
    return Optional.of(loadableStudy);
  }

  private List<LoadableStudyRules> getLSR() {
    List<LoadableStudyRules> loadableStudyRules = new ArrayList<>();
    LoadableStudyRules loadableStudyRules1 = new LoadableStudyRules();
    loadableStudyRules1.setIsActive(true);
    loadableStudyRules1.setRuleTypeXId(1L);
    loadableStudyRules.add(loadableStudyRules1);
    return loadableStudyRules;
  }

  @Test
  void testSaveDuplicateLoadableStudyRules() {
    List<LoadableStudyRules> listOfExistingLSRules = new ArrayList<>();
    LoadableStudyRules loadableStudyRules = new LoadableStudyRules();
    loadableStudyRules.setId(1L);
    loadableStudyRules.setRuleTypeXId(1L);
    loadableStudyRules.setDisplayInSettings(false);
    loadableStudyRules.setIsEnable(false);
    loadableStudyRules.setIsHardRule(false);
    loadableStudyRules.setIsActive(false);
    loadableStudyRules.setNumericPrecision(1L);
    loadableStudyRules.setNumericScale(1L);
    loadableStudyRules.setParentRuleXId(1L);
    loadableStudyRules.setLoadableStudyRuleInputs(getLLSRI());
    loadableStudyRules.setVesselRuleXId(1L);
    listOfExistingLSRules.add(loadableStudyRules);
    LoadableStudy currentLoableStudy = new LoadableStudy();
    com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail request =
        com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail.newBuilder()
            .setVesselId(1L)
            .build();
    this.loadableStudyRuleService.saveDuplicateLoadableStudyRules(
        listOfExistingLSRules, currentLoableStudy, request);
    Mockito.verify(loadableStudyRuleRepository).saveAll(Mockito.anyList());
  }

  private List<LoadableStudyRuleInput> getLLSRI() {
    List<LoadableStudyRuleInput> loadableStudyRuleInputs = new ArrayList<>();
    LoadableStudyRuleInput loadableStudyRuleInput = new LoadableStudyRuleInput();
    loadableStudyRuleInput.setId(1L);
    loadableStudyRuleInput.setPrefix("1");
    loadableStudyRuleInput.setDefaultValue("1");
    loadableStudyRuleInput.setTypeValue("1");
    loadableStudyRuleInput.setMaxValue("1");
    loadableStudyRuleInput.setMinValue("1");
    loadableStudyRuleInput.setSuffix("1");
    loadableStudyRuleInput.setIsActive(true);
    loadableStudyRuleInput.setIsMandatory(false);
    loadableStudyRuleInputs.add(loadableStudyRuleInput);
    return loadableStudyRuleInputs;
  }

  @Test
  void testGetOrSaveRulesForLoadableStudy() throws GenericServiceException {
    LoadableStudyRuleService spyService = spy(LoadableStudyRuleService.class);
    com.cpdss.common.generated.LoadableStudy.LoadableRuleRequest request =
        com.cpdss.common.generated.LoadableStudy.LoadableRuleRequest.newBuilder()
            .setSectionId(1l)
            .setVesselId(1l)
            .setLoadableStudyId(1l)
            .addAllRulePlan(getVesselRuleReply().getRulePlanList())
            .build();
    com.cpdss.common.generated.LoadableStudy.LoadableRuleReply.Builder builder =
        com.cpdss.common.generated.LoadableStudy.LoadableRuleReply.newBuilder();
    when(loadableStudyRepository.findByIdAndIsActiveAndVesselXId(
            anyLong(), anyBoolean(), anyLong()))
        .thenReturn(getLoadablestudy());
    LoadableStudyRules rVesselMapping = new LoadableStudyRules();
    when(loadableStudyRuleInputRepository.findById(anyLong()))
        .thenReturn(Optional.of(getLLSRI().get(0)));
    when(loadableStudyRuleRepository.findById(anyLong())).thenReturn(Optional.of(rVesselMapping));
    when(this.vesselInfoGrpcService.getRulesByVesselIdAndSectionId(
            any(VesselInfo.VesselRuleRequest.class)))
        .thenReturn(getVesselRuleReply());
    when(loadableStudyRuleRepository
            .findByLoadableStudyAndVesselXIdAndIsActiveAndVesselRuleXIdInOrderById(
                any(LoadableStudy.class), anyLong(), anyBoolean(), anyList()))
        .thenReturn(getLoadableStudyRulesList());
    ReflectionTestUtils.setField(spyService, "vesselInfoGrpcService", vesselInfoGrpcService);
    ReflectionTestUtils.setField(spyService, "loadableStudyRepository", loadableStudyRepository);
    ReflectionTestUtils.setField(
        spyService, "loadableStudyRuleInputRepository", loadableStudyRuleInputRepository);
    ReflectionTestUtils.setField(
        spyService, "loadableStudyRuleRepository", loadableStudyRuleRepository);

    spyService.getOrSaveRulesForLoadableStudy(request, builder);
    assertEquals(SUCCESS, builder.getResponseStatus().getStatus());
  }

  @Test
  void testGetOrSaveRulesForLoadableStudyIdNull() throws GenericServiceException {
    LoadableStudyRuleService spyService = spy(LoadableStudyRuleService.class);
    List<Common.Rules> rulesList = new ArrayList<>();
    Common.Rules rules =
        Common.Rules.newBuilder()
            .setVesselRuleXId("1")
            .setRuleTemplateId("1")
            .setDisplayInSettings(true)
            .setEnable(true)
            .setIsHardRule(true)
            .setNumericPrecision(1l)
            .setNumericScale(1l)
            .setRuleType("1")
            .addAllInputs(
                Arrays.asList(
                    Common.RulesInputs.newBuilder()
                        .setDefaultValue("1,1")
                        .setMin("1")
                        .setMax("1")
                        .setSuffix("**")
                        .setPrefix("**")
                        .setType("Dropdown")
                        .setIsMandatory(true)
                        .setId("1")
                        .build()))
            .build();
    rulesList.add(rules);
    List<Common.RulePlans> plansList = new ArrayList<>();
    Common.RulePlans rulePlans =
        Common.RulePlans.newBuilder().setHeader("1").addAllRules(rulesList).build();
    plansList.add(rulePlans);
    com.cpdss.common.generated.LoadableStudy.LoadableRuleRequest request =
        com.cpdss.common.generated.LoadableStudy.LoadableRuleRequest.newBuilder()
            .setSectionId(1l)
            .setVesselId(1l)
            .setLoadableStudyId(1l)
            .addAllRulePlan(plansList)
            .build();
    com.cpdss.common.generated.LoadableStudy.LoadableRuleReply.Builder builder =
        com.cpdss.common.generated.LoadableStudy.LoadableRuleReply.newBuilder();
    when(loadableStudyRepository.findByIdAndIsActiveAndVesselXId(
            anyLong(), anyBoolean(), anyLong()))
        .thenReturn(getLoadablestudy());
    LoadableStudyRules rVesselMapping = new LoadableStudyRules();
    when(loadableStudyRuleInputRepository.findById(anyLong()))
        .thenReturn(Optional.of(getLLSRI().get(0)));
    when(loadableStudyRuleRepository.findById(anyLong())).thenReturn(Optional.of(rVesselMapping));
    when(this.vesselInfoGrpcService.getRulesByVesselIdAndSectionId(
            any(VesselInfo.VesselRuleRequest.class)))
        .thenReturn(getVesselRuleReply());
    when(loadableStudyRuleRepository
            .findByLoadableStudyAndVesselXIdAndIsActiveAndVesselRuleXIdInOrderById(
                any(LoadableStudy.class), anyLong(), anyBoolean(), anyList()))
        .thenReturn(getLoadableStudyRulesList());
    ReflectionTestUtils.setField(spyService, "vesselInfoGrpcService", vesselInfoGrpcService);
    ReflectionTestUtils.setField(spyService, "loadableStudyRepository", loadableStudyRepository);
    ReflectionTestUtils.setField(
        spyService, "loadableStudyRuleInputRepository", loadableStudyRuleInputRepository);
    ReflectionTestUtils.setField(
        spyService, "loadableStudyRuleRepository", loadableStudyRuleRepository);

    spyService.getOrSaveRulesForLoadableStudy(request, builder);
    assertEquals(SUCCESS, builder.getResponseStatus().getStatus());
  }

  @Test
  void testGetOrSaveRulesForLoadableStudyElse() throws GenericServiceException {
    LoadableStudyRuleService spyService = spy(LoadableStudyRuleService.class);
    com.cpdss.common.generated.LoadableStudy.LoadableRuleRequest request =
        com.cpdss.common.generated.LoadableStudy.LoadableRuleRequest.newBuilder()
            .setSectionId(1l)
            .setVesselId(1l)
            .setLoadableStudyId(1l)
            .addAllRulePlan(getVesselRuleReply().getRulePlanList())
            .build();
    com.cpdss.common.generated.LoadableStudy.LoadableRuleReply.Builder builder =
        com.cpdss.common.generated.LoadableStudy.LoadableRuleReply.newBuilder();
    List<LoadableStudyRules> studyRulesList = new ArrayList<>();
    when(loadableStudyRepository.findByIdAndIsActiveAndVesselXId(
            anyLong(), anyBoolean(), anyLong()))
        .thenReturn(getLoadablestudy());
    LoadableStudyRules rVesselMapping = new LoadableStudyRules();
    when(loadableStudyRuleInputRepository.findById(anyLong()))
        .thenReturn(Optional.of(getLLSRI().get(0)));
    when(loadableStudyRuleRepository.findById(anyLong())).thenReturn(Optional.of(rVesselMapping));
    when(this.vesselInfoGrpcService.getRulesByVesselIdAndSectionId(
            any(VesselInfo.VesselRuleRequest.class)))
        .thenReturn(getVesselRuleReply());
    when(loadableStudyRuleRepository
            .findByLoadableStudyAndVesselXIdAndIsActiveAndVesselRuleXIdInOrderById(
                any(LoadableStudy.class), anyLong(), anyBoolean(), anyList()))
        .thenReturn(studyRulesList);
    ReflectionTestUtils.setField(spyService, "vesselInfoGrpcService", vesselInfoGrpcService);
    ReflectionTestUtils.setField(spyService, "loadableStudyRepository", loadableStudyRepository);
    ReflectionTestUtils.setField(
        spyService, "loadableStudyRuleInputRepository", loadableStudyRuleInputRepository);
    ReflectionTestUtils.setField(
        spyService, "loadableStudyRuleRepository", loadableStudyRuleRepository);

    spyService.getOrSaveRulesForLoadableStudy(request, builder);
    assertEquals(SUCCESS, builder.getResponseStatus().getStatus());
  }

  private List<LoadableStudyRules> getLoadableStudyRulesList() {
    List<LoadableStudyRules> loadableStudyRulesList = new ArrayList<>();
    LoadableStudyRules studyRules = new LoadableStudyRules();
    studyRules.setVesselRuleXId(1l);
    studyRules.setId(1l);
    studyRules.setIsEnable(true);
    studyRules.setDisplayInSettings(true);
    studyRules.setRuleTypeXId(1l);
    studyRules.setIsHardRule(true);
    studyRules.setVesselRuleXId(1l);
    studyRules.setParentRuleXId(1l);
    studyRules.setNumericPrecision(1l);
    studyRules.setNumericScale(1l);
    List<LoadableStudyRuleInput> inputList = new ArrayList<>();
    inputList.add(getRuleInput());
    studyRules.setLoadableStudyRuleInputs(inputList);
    loadableStudyRulesList.add(studyRules);
    return loadableStudyRulesList;
  }

  private LoadableStudyRuleInput getRuleInput() {
    LoadableStudyRuleInput input = new LoadableStudyRuleInput();
    input.setId(1l);
    input.setDefaultValue("1");
    input.setPrefix("**");
    input.setMinValue("1");
    input.setMaxValue("1");
    input.setTypeValue("MultiSelect");
    input.setSuffix("**");
    input.setIsMandatory(true);
    return input;
  }

  private VesselInfo.VesselRuleReply getVesselRuleReply() {
    List<Common.Rules> rulesList = new ArrayList<>();
    Common.Rules rules =
        Common.Rules.newBuilder()
            .setVesselRuleXId("1")
            .setRuleTemplateId("1")
            .setId("1")
            .addAllInputs(
                Arrays.asList(
                    Common.RulesInputs.newBuilder()
                        .setDefaultValue("1,1")
                        .setMin("1")
                        .setMax("1")
                        .setSuffix("**")
                        .setPrefix("**")
                        .setType("Dropdown")
                        .setIsMandatory(true)
                        .setId("1")
                        .build()))
            .setDisplayInSettings(true)
            .setEnable(true)
            .setIsHardRule(true)
            .setNumericPrecision(1l)
            .setNumericScale(1l)
            .setRuleType("1")
            .build();
    rulesList.add(rules);
    List<Common.RulePlans> plansList = new ArrayList<>();
    Common.RulePlans rulePlans =
        Common.RulePlans.newBuilder().setHeader("1").addAllRules(rulesList).build();
    plansList.add(rulePlans);
    List<VesselInfo.CargoTankMaster> masterList = new ArrayList<>();
    VesselInfo.CargoTankMaster tankMaster =
        VesselInfo.CargoTankMaster.newBuilder().setId(1l).setShortName("1").build();
    masterList.add(tankMaster);
    List<VesselInfo.RuleDropDownValueMaster> valueMasterList = new ArrayList<>();
    VesselInfo.RuleDropDownValueMaster valueMaster =
        VesselInfo.RuleDropDownValueMaster.newBuilder()
            .setRuleTemplateId(1l)
            .setId(1l)
            .setValue("1")
            .build();
    valueMasterList.add(valueMaster);
    List<VesselInfo.RuleTypeMaster> typeMasterList = new ArrayList<>();
    VesselInfo.RuleTypeMaster typeMaster =
        VesselInfo.RuleTypeMaster.newBuilder().setRuleType("1").build();
    typeMasterList.add(typeMaster);

    VesselInfo.VesselRuleReply vesselRuleReply =
        VesselInfo.VesselRuleReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .addAllRulePlan(plansList)
            .addAllCargoTankMaster(masterList)
            .addAllRuleTypeMaster(typeMasterList)
            .addAllRuleDropDownValueMaster(valueMasterList)
            .build();
    return vesselRuleReply;
  }

  @Test
  void testBuildLoadableStudyRuleDetails() throws GenericServiceException {
    LoadableStudyRuleService spyService = spy(LoadableStudyRuleService.class);
    com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.domain.LoadableStudy();
    when(this.vesselInfoGrpcService.getRulesByVesselIdAndSectionId(
            any(VesselInfo.VesselRuleRequest.class)))
        .thenReturn(getVesselRuleReply());
    when(this.loadableStudyRuleInputRepository.findAllByLoadableStudyRuleXId(
            any(LoadableStudyRules.class)))
        .thenReturn(getLoadableStudyRulesList().get(0).getLoadableStudyRuleInputs());
    when(loadableStudyRuleRepository.findByLoadableStudyAndVesselXIdAndIsActive(
            any(LoadableStudy.class), anyLong(), anyBoolean()))
        .thenReturn(getLoadableStudyRulesList());

    ReflectionTestUtils.setField(spyService, "vesselInfoGrpcService", vesselInfoGrpcService);
    ReflectionTestUtils.setField(
        spyService, "loadableStudyRuleRepository", loadableStudyRuleRepository);
    ReflectionTestUtils.setField(
        spyService, "loadableStudyRuleInputRepository", loadableStudyRuleInputRepository);

    spyService.buildLoadableStudyRuleDetails(getLoadablestudy().get(), loadableStudy);
    var id =
        loadableStudy
            .getLoadableStudyRuleList()
            .get(0)
            .getRules()
            .get(0)
            .getInputs()
            .get(0)
            .getRuleDropDownMaster()
            .get(0)
            .getId();
    assertEquals(1l, id);
    assertEquals(1, loadableStudy.getLoadableStudyRuleList().size());
  }

  @Test
  void testBuildLoadableStudyRuleDetailsElse() throws GenericServiceException {
    LoadableStudyRuleService spyService = spy(LoadableStudyRuleService.class);
    com.cpdss.loadablestudy.domain.LoadableStudy loadableStudy =
        new com.cpdss.loadablestudy.domain.LoadableStudy();
    List<LoadableStudyRules> loadableStudyRulesList = new ArrayList<>();
    LoadableStudyRules studyRules = new LoadableStudyRules();
    studyRules.setVesselRuleXId(1l);
    studyRules.setId(1l);
    studyRules.setIsEnable(true);
    studyRules.setDisplayInSettings(true);
    studyRules.setRuleTypeXId(1l);
    studyRules.setIsHardRule(true);
    studyRules.setVesselRuleXId(1l);
    studyRules.setParentRuleXId(1l);
    studyRules.setNumericPrecision(1l);
    studyRules.setNumericScale(1l);
    LoadableStudyRuleInput ruleInput = getRuleInput();
    ruleInput.setSuffix("");
    ruleInput.setPrefix("");
    studyRules.setLoadableStudyRuleInputs(Arrays.asList(ruleInput));
    loadableStudyRulesList.add(studyRules);

    when(this.vesselInfoGrpcService.getRulesByVesselIdAndSectionId(
            any(VesselInfo.VesselRuleRequest.class)))
        .thenReturn(getVesselRuleReply());
    when(loadableStudyRuleInputRepository.findAllByLoadableStudyRuleXId(
            any(LoadableStudyRules.class)))
        .thenReturn(Arrays.asList(ruleInput));
    when(loadableStudyRuleRepository.findByLoadableStudyAndVesselXIdAndIsActive(
            any(LoadableStudy.class), anyLong(), anyBoolean()))
        .thenReturn(loadableStudyRulesList);
    ReflectionTestUtils.setField(
        spyService, "loadableStudyRuleInputRepository", loadableStudyRuleInputRepository);

    ReflectionTestUtils.setField(spyService, "vesselInfoGrpcService", vesselInfoGrpcService);
    ReflectionTestUtils.setField(
        spyService, "loadableStudyRuleRepository", loadableStudyRuleRepository);

    spyService.buildLoadableStudyRuleDetails(getLoadablestudy().get(), loadableStudy);
    var id =
        loadableStudy
            .getLoadableStudyRuleList()
            .get(0)
            .getRules()
            .get(0)
            .getInputs()
            .get(0)
            .getRuleDropDownMaster()
            .get(0)
            .getId();
    assertEquals(1l, id);
    assertEquals(1, loadableStudy.getLoadableStudyRuleList().size());
  }

  @Test
  void testSaveRulesAgainstLoadableStudy() throws GenericServiceException {
    com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail request =
        com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail.newBuilder()
            .setVesselId(1L)
            .setDuplicatedFromId(1L)
            .build();
    when(this.vesselInfoGrpcService.getRulesByVesselIdAndSectionId(
            any(VesselInfo.VesselRuleRequest.class)))
        .thenReturn(getVesselRuleReply());

    ReflectionTestUtils.setField(
        loadableStudyRuleService, "vesselInfoGrpcService", vesselInfoGrpcService);

    loadableStudyRuleService.saveRulesAgainstLoadableStudy(request, getLoadablestudy().get());
    verify(loadableStudyRuleRepository).saveAll(anyList());
  }
}
