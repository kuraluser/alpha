/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.common.generated.VesselInfoServiceGrpc;
import com.cpdss.common.generated.discharge_plan.CowTankDetails;
import com.cpdss.common.generated.discharge_plan.DSCowDetails;
import com.cpdss.common.generated.discharge_plan.DischargeStudyDataTransferRequest;
import com.cpdss.common.generated.discharge_plan.PortData;
import com.cpdss.dischargeplan.entity.DischargeInformation;
import com.cpdss.dischargeplan.repository.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;

@SpringJUnitConfig(
    classes = {
      DischargePlanSynchronizeService.class,
    })
public class DischargePlanSynchronizeServiceTest {

  @Autowired DischargePlanSynchronizeService dischargePlanSynchronizeService;

  @MockBean DischargeInformationRepository dischargeInformationRepository;

  @MockBean DischargeInformationService dischargeInformationService;

  @MockBean CowPlanDetailRepository cowPlanDetailRepository;

  @MockBean DischargeRulesRepository dischargeStudyRulesRepository;

  @MockBean private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @MockBean private DischargePlanAlgoService dischargePlanAlgoService;

  @MockBean private DischargeRuleService dischargeRuleService;

  @MockBean DischargingStagesMinAmountRepository dischargingStagesMinAmountRepository;

  @MockBean DischargingStagesDurationRepository dischargingStagesDurationRepository;

  @MockBean DischargingMachineryInUseService dischargingMachineryInUseService;

  @Test
  void testsaveDischargeInformation() {
    List<PortData> list = new ArrayList<>();
    PortData portData =
        PortData.newBuilder().setPortRotationId(1L).setSynopticTableId(1L).setPortId(1L).build();
    list.add(portData);
    DischargeStudyDataTransferRequest request =
        DischargeStudyDataTransferRequest.newBuilder()
            .setVoyageId(1L)
            .setVesselId(1L)
            .setDischargePatternId(1L)
            .addAllPortData(list)
            .build();
    Mockito.when(dischargeInformationRepository.saveAll(Mockito.anyList())).thenReturn(getLDI());
    Mockito.when(this.vesselInfoGrpcService.getRulesByVesselIdAndSectionId(Mockito.any()))
        .thenReturn(getVRR());
    ReflectionTestUtils.setField(
        dischargePlanSynchronizeService,
        "dischargeInformationRepository",
        this.dischargeInformationRepository);
    ReflectionTestUtils.setField(
        dischargePlanSynchronizeService, "vesselInfoGrpcService", this.vesselInfoGrpcService);
    try {
      this.dischargePlanSynchronizeService.saveDischargeInformation(request);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private List<DischargeInformation> getLDI() {
    List<DischargeInformation> list = new ArrayList<>();
    DischargeInformation dischargeInformation = new DischargeInformation();
    dischargeInformation.setId(1L);
    dischargeInformation.setVesselXid(1L);
    list.add(dischargeInformation);
    return list;
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
    List<Common.Rules> list1 = new ArrayList<>();
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
    list1.add(rules);
    List<Common.RulePlans> list = new ArrayList<>();
    Common.RulePlans rulePlans = Common.RulePlans.newBuilder().addAllRules(list1).build();
    list.add(rulePlans);
    List<VesselInfo.RuleTypeMaster> list2 = new ArrayList<>();
    VesselInfo.RuleTypeMaster ruleTypeMaster =
        VesselInfo.RuleTypeMaster.newBuilder().setRuleType("1").setId(1L).build();
    list2.add(ruleTypeMaster);
    VesselInfo.VesselRuleReply vesselRuleReply =
        VesselInfo.VesselRuleReply.newBuilder()
            .addAllRuleTypeMaster(list2)
            .addAllRulePlan(list)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus("SUCCESS").build())
            .build();
    return vesselRuleReply;
  }

  @Test
  void testSaveCowDetailsForDischargeStudy() {
    List<PortData> list = new ArrayList<>();
    PortData portData = PortData.newBuilder().setPortRotationId(1L).setCowDetails(getCD()).build();
    list.add(portData);
    DischargeStudyDataTransferRequest request =
        DischargeStudyDataTransferRequest.newBuilder()
            .setVesselId(1L)
            .setVoyageId(1L)
            .addAllPortData(list)
            .build();
    Mockito.when(
            this.dischargeInformationRepository
                .findByVesselXidAndVoyageXidAndPortRotationXidAndIsActiveTrue(
                    Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong()))
        .thenReturn(getODI());
    Mockito.when(
            this.dischargeInformationService.getDischargeInformation(
                Mockito.anyLong(), Mockito.any(), Mockito.anyLong()))
        .thenCallRealMethod();
    ReflectionTestUtils.setField(
        dischargeInformationService,
        "dischargeInformationRepository",
        this.dischargeInformationRepository);
    this.dischargePlanSynchronizeService.saveCowDetailsForDischargeStudy(request);
  }

  private Optional<DischargeInformation> getODI() {
    DischargeInformation dischargeInformation = new DischargeInformation();
    dischargeInformation.setId(1L);
    return Optional.of(dischargeInformation);
  }

  private DSCowDetails getCD() {
    List<Long> list = new ArrayList<>();
    list.add(1L);
    DSCowDetails cowDetails =
        DSCowDetails.newBuilder()
            .setCowOptionTypeValue(1)
            .setPercent(1.1F)
            .setCowTankDetails(
                CowTankDetails.newBuilder().setCowType(getCT()).addAllTankIds(list).build())
            .build();
    return cowDetails;
  }

  private Common.COW_TYPE getCT() {
    Common.COW_TYPE cow_type = Common.COW_TYPE.EMPTY_COW_TYPE;
    return cow_type;
  }
}
