/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.service;

import static org.junit.Assert.assertEquals;

import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.dischargeplan.entity.BillOfLadding;
import com.cpdss.dischargeplan.entity.PortDischargingPlanBallastTempDetails;
import com.cpdss.dischargeplan.entity.PortDischargingPlanRobDetails;
import com.cpdss.dischargeplan.entity.PortDischargingPlanStowageTempDetails;
import com.cpdss.dischargeplan.repository.BillOfLaddingRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = {DischargeUllageServiceUtils.class})
public class DischargeUllageServiceUtilsTest {

  @Autowired DischargeUllageServiceUtils dischargeUllageServiceUtils;

  @MockBean BillOfLaddingRepository billOfLaddingRepo;

  @Test
  void testUpdateBillOfLadding() {
    List<LoadingPlanModels.BillOfLandingRemove> list2 = new ArrayList<>();
    LoadingPlanModels.BillOfLandingRemove billOfLandingRemove =
        LoadingPlanModels.BillOfLandingRemove.newBuilder().setId(1L).build();
    list2.add(billOfLandingRemove);
    List<LoadingPlanModels.BillOfLanding> list = new ArrayList<>();
    LoadingPlanModels.BillOfLanding landing =
        LoadingPlanModels.BillOfLanding.newBuilder()
            .setBlRefNumber("1")
            .setBblAt60F("1")
            .setQuantityLt("1")
            .setQuantityMt("1")
            .setKlAt15C("1")
            .setApi("1")
            .setTemperature("1")
            .setIsUpdate(true)
            .setId(1L)
            .build();
    list.add(landing);
    LoadingPlanModels.UllageBillRequest request =
        LoadingPlanModels.UllageBillRequest.newBuilder()
            .addAllBillOfLandingRemove(list2)
            .addAllBillOfLanding(list)
            .build();
    DischargeInformationService dischargeInformationService = new DischargeInformationService();
    Mockito.when(this.billOfLaddingRepo.findByIdIn(Mockito.any())).thenReturn(getLBL());
    Mockito.when(this.billOfLaddingRepo.findByIdIn(Mockito.any())).thenReturn(getLBL());
    var updatedEntities =
        DischargeUllageServiceUtils.updateBillOfLadding(
            request, billOfLaddingRepo, dischargeInformationService);
    assertEquals("1", updatedEntities.get(0).getBlRefNo());
  }

  private List<BillOfLadding> getLBL() {
    List<BillOfLadding> list = new ArrayList<>();
    BillOfLadding billOfLadding = new BillOfLadding();
    billOfLadding.setId(1L);
    list.add(billOfLadding);
    return list;
  }

  @Test
  void testUpdateBallast1() {
    List<LoadingPlanModels.BallastUpdate> list = new ArrayList<>();
    LoadingPlanModels.BallastUpdate ballastUpdate =
        LoadingPlanModels.BallastUpdate.newBuilder()
            .setArrivalDepartutre(1)
            .setSg("1")
            .setCorrectedUllage("1")
            .setColorCode("1")
            .setQuantity("1")
            .setSounding("1")
            .setDischargingInformationId(1L)
            .setIsUpdate(true)
            .setTankId(1L)
            .build();
    list.add(ballastUpdate);
    LoadingPlanModels.UllageBillRequest request =
        LoadingPlanModels.UllageBillRequest.newBuilder().addAllBallastUpdate(list).build();
    List<PortDischargingPlanBallastTempDetails> tempBallast = new ArrayList<>();
    PortDischargingPlanBallastTempDetails planBallastTempDetails =
        new PortDischargingPlanBallastTempDetails();
    planBallastTempDetails.setId(1L);
    planBallastTempDetails.setTankXId(1L);
    planBallastTempDetails.setConditionType(1);
    planBallastTempDetails.setDischargingInformation(1L);
    tempBallast.add(planBallastTempDetails);
    var udpatedBallast = DischargeUllageServiceUtils.updateBallast(request, tempBallast);
    assertEquals(new BigDecimal(1), udpatedBallast.get(0).getQuantity());
  }

  @Test
  void testUpdateBallast2() {
    List<LoadingPlanModels.BallastUpdate> list = new ArrayList<>();
    LoadingPlanModels.BallastUpdate ballastUpdate =
        LoadingPlanModels.BallastUpdate.newBuilder()
            .setDischargingInformationId(1L)
            .setPortRotationXid(1L)
            .setPortXid(1L)
            .setTankId(1L)
            .setTemperature("1")
            .setCorrectedUllage("1")
            .setQuantity("1")
            .setObservedM3("1")
            .setFillingPercentage("1")
            .setSounding("1")
            .setActualPlanned(1)
            .setArrivalDepartutre(1)
            .setColorCode("1")
            .setSg("1")
            .build();
    list.add(ballastUpdate);
    LoadingPlanModels.UllageBillRequest request =
        LoadingPlanModels.UllageBillRequest.newBuilder().addAllBallastUpdate(list).build();
    List<PortDischargingPlanBallastTempDetails> tempBallast = new ArrayList<>();
    var udpatedBallast = DischargeUllageServiceUtils.updateBallast(request, tempBallast);
    assertEquals(new BigDecimal(1), udpatedBallast.get(0).getQuantity());
  }

  @Test
  void testUpdateStowage1() {
    List<LoadingPlanModels.UpdateUllage> list = new ArrayList<>();
    LoadingPlanModels.UpdateUllage updateUllage =
        LoadingPlanModels.UpdateUllage.newBuilder()
            .setQuantity("1")
            .setUllage("1")
            .setApi("1")
            .setTemperature("1")
            .setArrivalDepartutre(1)
            .setIsUpdate(true)
            .setDischargingInfoId(1L)
            .setTankId(1L)
            .build();
    list.add(updateUllage);
    LoadingPlanModels.UllageBillRequest request =
        LoadingPlanModels.UllageBillRequest.newBuilder().addAllUpdateUllage(list).build();
    List<PortDischargingPlanStowageTempDetails> tempStowage = new ArrayList<>();
    PortDischargingPlanStowageTempDetails planStowageTempDetails =
        new PortDischargingPlanStowageTempDetails();
    planStowageTempDetails.setId(1L);
    planStowageTempDetails.setTankXId(1L);
    planStowageTempDetails.setDischargingInformation(1L);
    planStowageTempDetails.setConditionType(1);
    tempStowage.add(planStowageTempDetails);
    var stowageToSave = DischargeUllageServiceUtils.updateStowage(request, tempStowage);
    assertEquals(
        Optional.of(1L), Optional.ofNullable(stowageToSave.get(0).getDischargingInformation()));
  }

  @Test
  void testUpdateStowage2() {
    List<LoadingPlanModels.UpdateUllage> list = new ArrayList<>();
    LoadingPlanModels.UpdateUllage updateUllage =
        LoadingPlanModels.UpdateUllage.newBuilder()
            .setDischargingInfoId(1L)
            .setTankId(1L)
            .setTemperature("1")
            .setCorrectedUllage("1")
            .setQuantity("1")
            .setFillingPercentage("1")
            .setApi("1")
            .setCargoNominationXid(1L)
            .setPortXid(1L)
            .setPortRotationXid(1L)
            .setActualPlanned(1)
            .setArrivalDepartutre(1)
            .setCorrectionFactor("1")
            .setUllage("1")
            .setColorCode("1")
            .setAbbreviation("1")
            .setCargoId(1L)
            .build();
    list.add(updateUllage);
    LoadingPlanModels.UllageBillRequest request =
        LoadingPlanModels.UllageBillRequest.newBuilder().addAllUpdateUllage(list).build();
    List<PortDischargingPlanStowageTempDetails> tempStowage = new ArrayList<>();
    var stowageToSave = DischargeUllageServiceUtils.updateStowage(request, tempStowage);
    assertEquals(
        Optional.of(1L), Optional.ofNullable(stowageToSave.get(0).getDischargingInformation()));
  }

  @Test
  void testUpdateRob1() {
    List<LoadingPlanModels.RobUpdate> list = new ArrayList<>();
    LoadingPlanModels.RobUpdate robUpdate =
        LoadingPlanModels.RobUpdate.newBuilder()
            .setActualPlanned(1)
            .setArrivalDepartutre(1)
            .setDischargingInformationId(1L)
            .setTankId(1L)
            .setQuantity("1")
            .setIsUpdate(true)
            .build();
    list.add(robUpdate);
    LoadingPlanModels.UllageBillRequest request =
        LoadingPlanModels.UllageBillRequest.newBuilder().addAllRobUpdate(list).build();
    List<PortDischargingPlanRobDetails> tempRob = new ArrayList<>();
    PortDischargingPlanRobDetails planRobDetails = new PortDischargingPlanRobDetails();
    planRobDetails.setId(1L);
    planRobDetails.setTankXId(1L);
    planRobDetails.setConditionType(1);
    planRobDetails.setValueType(1);
    planRobDetails.setDischargingInformation(1L);
    tempRob.add(planRobDetails);
    var robToSave = DischargeUllageServiceUtils.updateRob(request, tempRob);
    assertEquals(
        Optional.of(1L), Optional.ofNullable(robToSave.get(0).getDischargingInformation()));
  }

  @Test
  void testUpdateRob2() {
    List<LoadingPlanModels.RobUpdate> list = new ArrayList<>();
    LoadingPlanModels.RobUpdate robUpdate =
        LoadingPlanModels.RobUpdate.newBuilder()
            .setDischargingInformationId(1L)
            .setTankId(1L)
            .setQuantity("1")
            .setPortXid(1L)
            .setPortRotationXid(1L)
            .setArrivalDepartutre(1)
            .setColourCode("1")
            .setDensity("1")
            .setQuantity("1")
            .build();
    list.add(robUpdate);
    LoadingPlanModels.UllageBillRequest request =
        LoadingPlanModels.UllageBillRequest.newBuilder().addAllRobUpdate(list).build();
    List<PortDischargingPlanRobDetails> tempRob = new ArrayList<>();
    var robToSave = DischargeUllageServiceUtils.updateRob(request, tempRob);
    assertEquals(
        Optional.of(1L), Optional.ofNullable(robToSave.get(0).getDischargingInformation()));
  }
}
