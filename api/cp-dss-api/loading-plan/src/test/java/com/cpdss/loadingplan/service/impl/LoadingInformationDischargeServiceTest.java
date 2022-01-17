/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.impl;

import static com.cpdss.loadingplan.common.LoadingPlanConstants.SUCCESS;
import static org.junit.Assert.assertEquals;

import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.LoadableStudy.LoadingInformationSynopticalReply;
import com.cpdss.common.generated.LoadableStudyServiceGrpc;
import com.cpdss.common.generated.PortInfoServiceGrpc;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.loadingplan.entity.BillOfLadding;
import com.cpdss.loadingplan.repository.BillOfLaddingRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;

@SpringJUnitConfig(classes = {LoadingInformationDischargeService.class})
public class LoadingInformationDischargeServiceTest {

  @Autowired LoadingInformationDischargeService loadingInformationDischargeService;

  @MockBean BillOfLaddingRepository billOfLaddingRepository;

  @MockBean private PortInfoServiceGrpc.PortInfoServiceBlockingStub portInfoGrpcService;

  @MockBean
  private LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub loadableStudyGrpcService;

  @Test
  void testGetLoadigInformationByVoyage() {
    List<Common.BillOfLadding> list = new ArrayList<>();
    Common.BillOfLadding bill =
        Common.BillOfLadding.newBuilder()
            .setCargoColor("1")
            .setCargoAbbrevation("1")
            .setCargoId(1L)
            .setCargoName("1")
            .setPortId(1L)
            .setQuantityBbls("1")
            .setQuantityKl("1")
            .setQuantityMt("1")
            .setApi("1")
            .setTemperature("1")
            .build();
    list.add(bill);
    LoadingPlanModels.LoadingInformationSynopticalRequest request =
        LoadingPlanModels.LoadingInformationSynopticalRequest.newBuilder()
            .setLoadablePatternId(1L)
            .build();
    LoadingInformationSynopticalReply.Builder builder =
        LoadingInformationSynopticalReply.newBuilder().addAllBillOfLadding(list);
    Mockito.when(
            billOfLaddingRepository.findByLoadablePatternXIdAndIsActive(
                Mockito.anyLong(), Mockito.anyBoolean()))
        .thenReturn(getLBL());
    Mockito.when(this.loadableStudyGrpcService.getCargoNominationByCargoNominationId(Mockito.any()))
        .thenReturn(getCNDR());
    ReflectionTestUtils.setField(
        loadingInformationDischargeService,
        "loadableStudyGrpcService",
        this.loadableStudyGrpcService);
    try {
      var reply =
          this.loadingInformationDischargeService.getLoadigInformationByVoyage(request, builder);
      assertEquals(SUCCESS, reply.getResponseStatus().getStatus());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private List<BillOfLadding> getLBL() {
    List<BillOfLadding> list = new ArrayList<>();
    BillOfLadding billOfLadding = new BillOfLadding();
    billOfLadding.setId(1L);
    billOfLadding.setApi(new BigDecimal(1));
    billOfLadding.setTemperature(new BigDecimal(1));
    billOfLadding.setQuantityBbls(new BigDecimal(1));
    billOfLadding.setQuantityKl(new BigDecimal(1));
    billOfLadding.setQuantityMt(new BigDecimal(1));
    billOfLadding.setCargoNominationId(1L);
    billOfLadding.setPortId(1L);

    list.add(billOfLadding);
    return list;
  }

  private LoadableStudy.CargoNominationDetailReply getCNDR() {
    LoadableStudy.CargoNominationDetailReply reply =
        LoadableStudy.CargoNominationDetailReply.newBuilder()
            .setCargoNominationdetail(
                LoadableStudy.CargoNominationDetail.newBuilder()
                    .setAbbreviation("1")
                    .setColor("1")
                    .setCargoId(1L)
                    .setCargoName("1")
                    .setId(1L)
                    .build())
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  @Test
  void testBuildCargoDetails() {
    Long cargoNominationId = 1L;
    Common.BillOfLadding.Builder bolBuilder = Common.BillOfLadding.newBuilder();
    Mockito.when(this.loadableStudyGrpcService.getCargoNominationByCargoNominationId(Mockito.any()))
        .thenReturn(getCNDR());
    ReflectionTestUtils.setField(
        loadingInformationDischargeService,
        "loadableStudyGrpcService",
        this.loadableStudyGrpcService);
    this.loadingInformationDischargeService.buildCargoDetails(cargoNominationId, bolBuilder);
    assertEquals(1L, bolBuilder.getCargoId());
  }
}
