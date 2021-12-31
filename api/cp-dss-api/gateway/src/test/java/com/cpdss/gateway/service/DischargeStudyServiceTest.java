/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.*;
import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.LoadableStudy;
import com.cpdss.common.generated.LoadableStudy.AlgoReply;
import com.cpdss.common.generated.LoadableStudy.AlgoRequest;
import com.cpdss.common.generated.loadableStudy.LoadableStudyModels;
import com.cpdss.common.generated.loading_plan.LoadingInformationServiceGrpc;
import com.cpdss.common.generated.loading_plan.LoadingInstructionServiceGrpc.LoadingInstructionServiceBlockingStub;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels;
import com.cpdss.common.generated.loading_plan.LoadingPlanServiceGrpc;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.gateway.domain.*;
import com.cpdss.gateway.domain.DischargeStudy.DischargeStudyCargoResponse;
import com.cpdss.gateway.domain.DischargeStudy.DischargeStudyRequest;
import com.cpdss.gateway.repository.UsersRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Test class for {@link DischargeStudyService}
 *
 * @author Sanal
 */
@SpringJUnitConfig(classes = {DischargeStudyService.class})
class DischargeStudyServiceTest {

  @Autowired private DischargeStudyService dischargeStudyService;

  private static final Long TEST_VESSEL_ID = 1L;

  private static final String SUCCESS = "SUCCESS";
  private static final String FAILED = "FAILED";

  private static final Long TEST_LOADING_INFO_ID = 1L;
  private static final Long TEST_PORT_ROTATION_ID = 1L;

  @MockBean private UsersRepository usersRepository;
  @MockBean private LoadingInstructionServiceBlockingStub loadingInstructionServiceBlockingStub;
  @MockBean private LoadableStudyService loadableStudyService;

  @MockBean
  private LoadingInformationServiceGrpc.LoadingInformationServiceBlockingStub
      loadingInfoServiceBlockingStub;

  @MockBean
  private LoadingPlanServiceGrpc.LoadingPlanServiceBlockingStub loadingPlanServiceBlockingStub;

  @MockBean
  private LoadableStudyServiceGrpc.LoadableStudyServiceBlockingStub
      loadableStudyServiceBlockingStub;

  @MockBean
  private DischargeStudyOperationServiceGrpc.DischargeStudyOperationServiceBlockingStub
      dischargeStudyOperationServiceBlockingStub;

  @MockBean private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  /**
   * Generate Discharge pattern
   *
   * @throws GenericServiceException
   */
  @Test
  void testGenerateDischargePatterns() throws GenericServiceException {
    AlgoReply replyBuilder =
        AlgoReply.newBuilder()
            .setProcesssId("1")
            .setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    Mockito.when(
            this.dischargeStudyOperationServiceBlockingStub.generateDischargePatterns(
                Mockito.any()))
        .thenReturn(replyBuilder);
    ReflectionTestUtils.setField(
        dischargeStudyService,
        "dischargeStudyOperationServiceBlockingStub",
        this.dischargeStudyOperationServiceBlockingStub);
    AlgoPatternResponse response =
        this.dischargeStudyService.generateDischargePatterns(
            TEST_VESSEL_ID, TEST_LOADING_INFO_ID, TEST_PORT_ROTATION_ID, "");

    assertEquals(
        "200", response.getResponseStatus().getStatus(), String.valueOf(HttpStatus.OK.value()));
  }

  /**
   * Generate Discharge pattern - Exception
   *
   * @throws GenericServiceException
   */
  @Test
  void testGenerateDischargePatternsException() throws GenericServiceException {
    AlgoReply replyBuilder =
        AlgoReply.newBuilder()
            .setResponseStatus(
                ResponseStatus.newBuilder()
                    .setStatus(FAILED)
                    .setCode(CommonErrorCodes.E_CPDSS_ALGO_ISSUE)
                    .build())
            .build();
    Mockito.when(
            this.dischargeStudyOperationServiceBlockingStub.generateDischargePatterns(
                Mockito.any()))
        .thenReturn(replyBuilder);
    ReflectionTestUtils.setField(
        dischargeStudyService,
        "dischargeStudyOperationServiceBlockingStub",
        this.dischargeStudyOperationServiceBlockingStub);
    assertThrows(
        GenericServiceException.class,
        () ->
            this.dischargeStudyService.generateDischargePatterns(
                TEST_VESSEL_ID, TEST_LOADING_INFO_ID, TEST_PORT_ROTATION_ID, ""));
  }

  @Test
  void testGetDischargeStudyByVoyage() {
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long dischargeStudyId = 1L;
    String correlationId = "1";
    Mockito.when(
            loadableStudyServiceBlockingStub.getLoadablePatternByVoyageAndStatus(Mockito.any()))
        .thenReturn(getLPCR());
    ReflectionTestUtils.setField(
        dischargeStudyService,
        "loadableStudyServiceBlockingStub",
        this.loadableStudyServiceBlockingStub);
    Mockito.when(loadingInfoServiceBlockingStub.getLoadigInformationByVoyage(Mockito.any()))
        .thenReturn(getLISR());
    ReflectionTestUtils.setField(
        dischargeStudyService,
        "loadingInfoServiceBlockingStub",
        this.loadingInfoServiceBlockingStub);
    Mockito.when(loadingPlanServiceBlockingStub.getLoadingPlanCommingleDetails(Mockito.any()))
        .thenReturn(getLPCCDR());
    ReflectionTestUtils.setField(
        dischargeStudyService,
        "loadingPlanServiceBlockingStub",
        this.loadingPlanServiceBlockingStub);
    try {
      var response =
          this.dischargeStudyService.getDischargeStudyByVoyage(
              vesselId, voyageId, dischargeStudyId, correlationId);
      assertEquals("200", response.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadableStudy.LoadablePatternConfirmedReply getLPCR() {
    LoadableStudy.LoadablePatternConfirmedReply reply =
        LoadableStudy.LoadablePatternConfirmedReply.newBuilder()
            .setPattern(LoadableStudy.LoadablePattern.newBuilder().setLoadablePatternId(1L).build())
            .setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  private LoadingPlanModels.LoadingInformationSynopticalReply getLISR() {
    List<Common.BillOfLadding> list = new ArrayList<>();
    Common.BillOfLadding bill = Common.BillOfLadding.newBuilder().setCargoAbbrevation("1").build();
    list.add(bill);
    LoadingPlanModels.LoadingInformationSynopticalReply reply =
        LoadingPlanModels.LoadingInformationSynopticalReply.newBuilder()
            .addAllBillOfLadding(list)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  private LoadingPlanModels.LoadablePlanCommingleCargoDetailsReply getLPCCDR() {
    List<LoadingPlanModels.LoadablePlanCommingleCargoDetails> list = new ArrayList<>();
    LoadingPlanModels.LoadablePlanCommingleCargoDetails details =
        LoadingPlanModels.LoadablePlanCommingleCargoDetails.newBuilder()
            .setId(1L)
            .setApi("1")
            .setCargo1Abbreviation("1")
            .setCargo1Bbls60F("1")
            .setCargo1Bblsdbs("1")
            .setCargo1KL("1")
            .setCargo1LT("1")
            .setQuantity1MT("1")
            .setCargo1Percentage("1")
            .setCargo2Abbreviation("1")
            .setCargo2Bbls60F("1")
            .setCargo2Bblsdbs("1")
            .setCargo2KL("1")
            .setCargo2Percentage("1")
            .setCargo2MT("1")
            .setQuantity2MT("1")
            .setGrade("1")
            .setQuantity("1")
            .setTankName("1")
            .setTemp("1")
            .setTankShortName("1")
            .setCargo1Id(1L)
            .setCargo2Id(1L)
            .setQuantity1MT("1")
            .setQuantity2MT("1")
            .setQuantity1M3("1")
            .setQuantity2M3("1")
            .setCargo1NominationId(1L)
            .setCargo2NominationId(1L)
            .build();
    list.add(details);
    LoadingPlanModels.LoadablePlanCommingleCargoDetailsReply reply =
        LoadingPlanModels.LoadablePlanCommingleCargoDetailsReply.newBuilder()
            .addAllLoadablePlanCommingleCargoList(list)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  @Test
  void testGetDischargeStudyByVoyageException1() {
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long dischargeStudyId = 1L;
    String correlationId = "1";
    Mockito.when(
            loadableStudyServiceBlockingStub.getLoadablePatternByVoyageAndStatus(Mockito.any()))
        .thenReturn(getLPCRNS());
    ReflectionTestUtils.setField(
        dischargeStudyService,
        "loadableStudyServiceBlockingStub",
        this.loadableStudyServiceBlockingStub);
    try {
      var response =
          this.dischargeStudyService.getDischargeStudyByVoyage(
              vesselId, voyageId, dischargeStudyId, correlationId);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadableStudy.LoadablePatternConfirmedReply getLPCRNS() {
    LoadableStudy.LoadablePatternConfirmedReply reply =
        LoadableStudy.LoadablePatternConfirmedReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setCode("400").build())
            .build();
    return reply;
  }

  @Test
  void testGetDischargeStudyByVoyageException2() {
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long dischargeStudyId = 1L;
    String correlationId = "1";
    Mockito.when(
            loadableStudyServiceBlockingStub.getLoadablePatternByVoyageAndStatus(Mockito.any()))
        .thenReturn(getLPCR());
    ReflectionTestUtils.setField(
        dischargeStudyService,
        "loadableStudyServiceBlockingStub",
        this.loadableStudyServiceBlockingStub);
    Mockito.when(loadingInfoServiceBlockingStub.getLoadigInformationByVoyage(Mockito.any()))
        .thenReturn(getLISRNS());
    ReflectionTestUtils.setField(
        dischargeStudyService,
        "loadingInfoServiceBlockingStub",
        this.loadingInfoServiceBlockingStub);
    try {
      var response =
          this.dischargeStudyService.getDischargeStudyByVoyage(
              vesselId, voyageId, dischargeStudyId, correlationId);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadingPlanModels.LoadingInformationSynopticalReply getLISRNS() {
    LoadingPlanModels.LoadingInformationSynopticalReply reply =
        LoadingPlanModels.LoadingInformationSynopticalReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setCode("400").build())
            .build();
    return reply;
  }

  @Test
  void testGetDischargeStudyByVoyageException3() {
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long dischargeStudyId = 1L;
    String correlationId = "1";
    Mockito.when(
            loadableStudyServiceBlockingStub.getLoadablePatternByVoyageAndStatus(Mockito.any()))
        .thenReturn(getLPCR());
    ReflectionTestUtils.setField(
        dischargeStudyService,
        "loadableStudyServiceBlockingStub",
        this.loadableStudyServiceBlockingStub);
    Mockito.when(loadingInfoServiceBlockingStub.getLoadigInformationByVoyage(Mockito.any()))
        .thenReturn(getLISR());
    ReflectionTestUtils.setField(
        dischargeStudyService,
        "loadingInfoServiceBlockingStub",
        this.loadingInfoServiceBlockingStub);
    Mockito.when(loadingPlanServiceBlockingStub.getLoadingPlanCommingleDetails(Mockito.any()))
        .thenReturn(getLPCCDRNS());
    ReflectionTestUtils.setField(
        dischargeStudyService,
        "loadingPlanServiceBlockingStub",
        this.loadingPlanServiceBlockingStub);
    try {
      var response =
          this.dischargeStudyService.getDischargeStudyByVoyage(
              vesselId, voyageId, dischargeStudyId, correlationId);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadingPlanModels.LoadablePlanCommingleCargoDetailsReply getLPCCDRNS() {
    LoadingPlanModels.LoadablePlanCommingleCargoDetailsReply reply =
        LoadingPlanModels.LoadablePlanCommingleCargoDetailsReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setCode("400").build())
            .build();
    return reply;
  }

  @Test
  void testGetDischargeStudyPortDataByVoyage() throws GenericServiceException {
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long dischargeStudyId = 1L;
    String correlationId = "1";
    HttpHeaders headers = new HttpHeaders();
    Mockito.when(
            loadableStudyService.getLoadableStudyPortRotationList(
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.anyLong(),
                Mockito.any(),
                Mockito.any(),
                Mockito.any()))
        .thenReturn(getPRR());
    var response =
        this.dischargeStudyService.getDischargeStudyPortDataByVoyage(
            vesselId, voyageId, dischargeStudyId, correlationId, headers);
    assertEquals(1L, response.getId());
  }

  private PortRotationResponse getPRR() {
    PortRotationResponse response = new PortRotationResponse();
    response.setId(1L);
    return response;
  }

  @Test
  void testSavePortRotation() {
    PortRotation request = new PortRotation();
    String correlationId = "1";
    HttpHeaders headers = new HttpHeaders();
    Mockito.when(loadableStudyService.createPortRotationDetail(Mockito.any(), Mockito.any()))
        .thenReturn(getPRD());
    Mockito.when(loadableStudyService.savePortRotation(Mockito.any())).thenReturn(getPRRS());
    try {
      var response = this.dischargeStudyService.savePortRotation(request, correlationId, headers);
      assertEquals("200", response.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadableStudy.PortRotationDetail getPRD() {
    LoadableStudy.PortRotationDetail detail = LoadableStudy.PortRotationDetail.newBuilder().build();
    return detail;
  }

  private LoadableStudy.PortRotationReply getPRRS() {
    List<Long> list3 = new ArrayList<>();
    List<Long> list2 = new ArrayList<>();
    List<LoadableStudyModels.BackLoading> list1 = new ArrayList<>();
    List<LoadableStudy.PortRotationDetail> list = new ArrayList<>();
    LoadableStudy.PortRotationDetail detail =
        LoadableStudy.PortRotationDetail.newBuilder()
            .setOperationId(2L)
            .setPortId(1L)
            .setId(1L)
            .setMaxDraft("null")
            .addAllInstructionId(list3)
            .setIsBackLoadingEnabled(true)
            .addAllBackLoading(list1)
            //   .setCowId(1L)
            //  .setPercentage(1L)
            //  .addAllTanks(list2)
            .build();
    list.add(detail);
    LoadableStudy.PortRotationReply reply =
        LoadableStudy.PortRotationReply.newBuilder()
            .addAllPorts(list)
            .setLoadableQuantity("1")
            .setPortRotationId(1L)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  @Test
  void testSavePortRotationException() {
    PortRotation request = new PortRotation();
    String correlationId = "1";
    HttpHeaders headers = new HttpHeaders();
    Mockito.when(loadableStudyService.createPortRotationDetail(Mockito.any(), Mockito.any()))
        .thenReturn(getPRD());
    Mockito.when(loadableStudyService.savePortRotation(Mockito.any())).thenReturn(getPRRNS());
    try {
      var response = this.dischargeStudyService.savePortRotation(request, correlationId, headers);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadableStudy.PortRotationReply getPRRNS() {
    LoadableStudy.PortRotationReply reply =
        LoadableStudy.PortRotationReply.newBuilder()
            .setResponseStatus(
                Common.ResponseStatus.newBuilder().setCode("400").setHttpStatusCode(400).build())
            .build();
    return reply;
  }

  @Test
  void testDeletePortRotation() throws GenericServiceException {
    Long loadableStudyId = 1L;
    Long id = 1L;
    String correlationId = "1";
    Mockito.when(loadableStudyService.deletePortRotation(Mockito.any())).thenReturn(getPRRR());
    var response =
        this.dischargeStudyService.deletePortRotation(loadableStudyId, id, correlationId);
    assertEquals("200", response.getResponseStatus().getStatus());
  }

  private LoadableStudy.PortRotationReply getPRRR() {
    LoadableStudy.PortRotationReply reply =
        LoadableStudy.PortRotationReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  @Test
  void testDeletePortRotationException() {
    Long loadableStudyId = 1L;
    Long id = 1L;
    String correlationId = "1";
    Mockito.when(loadableStudyService.deletePortRotation(Mockito.any())).thenReturn(getPRRRNS());
    try {
      var response =
          this.dischargeStudyService.deletePortRotation(loadableStudyId, id, correlationId);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadableStudy.PortRotationReply getPRRRNS() {
    LoadableStudy.PortRotationReply reply =
        LoadableStudy.PortRotationReply.newBuilder()
            .setResponseStatus(
                Common.ResponseStatus.newBuilder().setCode("400").setHttpStatusCode(400).build())
            .build();
    return reply;
  }

  @Test
  void testSaveDischargeStudy() {
    DischargeStudyRequest request = new DischargeStudyRequest();
    request.setName("1");
    request.setEnquiryDetails("1");
    request.setVesselId(1L);
    request.setVoyageId(1L);
    String correlationId = "1";
    Mockito.when(this.dischargeStudyOperationServiceBlockingStub.saveDischargeStudy(Mockito.any()))
        .thenReturn(getDSR());
    ReflectionTestUtils.setField(
        dischargeStudyService,
        "dischargeStudyOperationServiceBlockingStub",
        this.dischargeStudyOperationServiceBlockingStub);
    try {
      var response = this.dischargeStudyService.saveDischargeStudy(request, correlationId);
      assertEquals(1L, response.getDischargeStudyId());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadableStudyModels.DischargeStudyReply getDSR() {
    LoadableStudyModels.DischargeStudyReply reply =
        LoadableStudyModels.DischargeStudyReply.newBuilder()
            .setId(1L)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  @Test
  void testSaveDischargeStudyException() {
    DischargeStudyRequest request = new DischargeStudyRequest();
    request.setName("1");
    request.setEnquiryDetails("1");
    request.setVesselId(1L);
    request.setVoyageId(1L);
    String correlationId = "1";
    Mockito.when(this.dischargeStudyOperationServiceBlockingStub.saveDischargeStudy(Mockito.any()))
        .thenReturn(getDSRNS());
    ReflectionTestUtils.setField(
        dischargeStudyService,
        "dischargeStudyOperationServiceBlockingStub",
        this.dischargeStudyOperationServiceBlockingStub);
    try {
      var response = this.dischargeStudyService.saveDischargeStudy(request, correlationId);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadableStudyModels.DischargeStudyReply getDSRNS() {
    LoadableStudyModels.DischargeStudyReply reply =
        LoadableStudyModels.DischargeStudyReply.newBuilder()
            .setResponseStatus(
                Common.ResponseStatus.newBuilder().setCode("400").setHttpStatusCode(400).build())
            .build();
    return reply;
  }

  @Test
  void testUpdateDischargeStudy() {
    DischargeStudyRequest request = new DischargeStudyRequest();
    request.setName("1");
    request.setEnquiryDetails("1");
    String correlationId = "1";
    Long dischargeStudyId = 1L;
    Mockito.when(
            this.dischargeStudyOperationServiceBlockingStub.updateDischargeStudy(Mockito.any()))
        .thenReturn(getUDSR());
    ReflectionTestUtils.setField(
        dischargeStudyService,
        "dischargeStudyOperationServiceBlockingStub",
        this.dischargeStudyOperationServiceBlockingStub);
    try {
      var response =
          this.dischargeStudyService.updateDischargeStudy(request, correlationId, dischargeStudyId);
      assertEquals("1", response.getDischargeStudy().getName());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadableStudyModels.UpdateDischargeStudyReply getUDSR() {
    LoadableStudyModels.UpdateDischargeStudyReply reply =
        LoadableStudyModels.UpdateDischargeStudyReply.newBuilder()
            .setDischargeStudy(
                LoadableStudyModels.UpdateDischargeStudyDetail.newBuilder()
                    .setId(1L)
                    .setName("1")
                    .setEnquiryDetails("1")
                    .build())
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  @Test
  void testUpdateDischargeStudyException() {
    DischargeStudyRequest request = new DischargeStudyRequest();
    request.setName("1");
    request.setEnquiryDetails("1");
    String correlationId = "1";
    Long dischargeStudyId = 1L;
    Mockito.when(
            this.dischargeStudyOperationServiceBlockingStub.updateDischargeStudy(Mockito.any()))
        .thenReturn(getUDSRNS());
    ReflectionTestUtils.setField(
        dischargeStudyService,
        "dischargeStudyOperationServiceBlockingStub",
        this.dischargeStudyOperationServiceBlockingStub);
    try {
      var response =
          this.dischargeStudyService.updateDischargeStudy(request, correlationId, dischargeStudyId);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadableStudyModels.UpdateDischargeStudyReply getUDSRNS() {
    LoadableStudyModels.UpdateDischargeStudyReply reply =
        LoadableStudyModels.UpdateDischargeStudyReply.newBuilder()
            .setResponseStatus(
                Common.ResponseStatus.newBuilder().setCode("400").setHttpStatusCode(400).build())
            .build();
    return reply;
  }

  @Test
  void testGetOnHandQuantity() {
    Long vesselId = 1L;
    Long dischargeStudyId = 1L;
    Long portRotationId = 1L;
    String correlationId = "1";
    Mockito.when(loadableStudyService.getOnHandQuantity(Mockito.any())).thenReturn(getOHQR());
    Mockito.when(loadableStudyService.buildOnHandQuantityResponse(Mockito.any(), Mockito.any()))
        .thenReturn(getOHQRE());
    try {
      var response =
          this.dischargeStudyService.getOnHandQuantity(
              vesselId, dischargeStudyId, portRotationId, correlationId);
      assertEquals(1L, response.getId());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadableStudy.OnHandQuantityReply getOHQR() {
    LoadableStudy.OnHandQuantityReply reply =
        LoadableStudy.OnHandQuantityReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  private OnHandQuantityResponse getOHQRE() {
    OnHandQuantityResponse response = new OnHandQuantityResponse();
    response.setId(1L);
    return response;
  }

  @Test
  void testGetOnHandQuantityException() {
    Long vesselId = 1L;
    Long dischargeStudyId = 1L;
    Long portRotationId = 1L;
    String correlationId = "1";
    Mockito.when(loadableStudyService.getOnHandQuantity(Mockito.any())).thenReturn(getOHQRNS());
    try {
      var response =
          this.dischargeStudyService.getOnHandQuantity(
              vesselId, dischargeStudyId, portRotationId, correlationId);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadableStudy.OnHandQuantityReply getOHQRNS() {
    LoadableStudy.OnHandQuantityReply reply =
        LoadableStudy.OnHandQuantityReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  @Test
  void testDeleteDischargeStudy() {
    Long dischargeStudyId = 1L;
    String correlationId = "1";
    Mockito.when(dischargeStudyOperationServiceBlockingStub.deleteDischargeStudy(Mockito.any()))
        .thenReturn(getDSR());
    ReflectionTestUtils.setField(
        dischargeStudyService,
        "dischargeStudyOperationServiceBlockingStub",
        this.dischargeStudyOperationServiceBlockingStub);
    try {
      var response =
          this.dischargeStudyService.deleteDischargeStudy(dischargeStudyId, correlationId);
      assertEquals("200", response.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testDeleteDischargeStudyException() {
    Long dischargeStudyId = 1L;
    String correlationId = "1";
    Mockito.when(dischargeStudyOperationServiceBlockingStub.deleteDischargeStudy(Mockito.any()))
        .thenReturn(getDSRNS());
    ReflectionTestUtils.setField(
        dischargeStudyService,
        "dischargeStudyOperationServiceBlockingStub",
        this.dischargeStudyOperationServiceBlockingStub);
    try {
      var response =
          this.dischargeStudyService.deleteDischargeStudy(dischargeStudyId, correlationId);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testSaveOnHandQuantity() throws GenericServiceException {
    OnHandQuantity request = new OnHandQuantity();
    String correlationId = "1";
    Mockito.when(this.loadableStudyService.saveOnHandQuantity(Mockito.any(), Mockito.any()))
        .thenReturn(getOHQRE());
    var response = this.dischargeStudyService.saveOnHandQuantity(request, correlationId);
    assertEquals(1L, response.getId());
  }

  @Test
  void testGetDischargeStudyCargoByVoyage() {
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long dischargeStudyId = 1L;
    String correlationId = "1";
    Mockito.when(dischargeStudyOperationServiceBlockingStub.getDischargeCowDetails(Mockito.any()))
        .thenReturn(getDCR());
    Mockito.when(loadableStudyService.getPortRotation(Mockito.any())).thenReturn(getPRRS());
    Mockito.when(loadableStudyServiceBlockingStub.getCargoNominationById(Mockito.any()))
        .thenReturn(getCNR());
    ReflectionTestUtils.setField(
        dischargeStudyService,
        "loadableStudyServiceBlockingStub",
        this.loadableStudyServiceBlockingStub);
    ReflectionTestUtils.setField(
        dischargeStudyService,
        "dischargeStudyOperationServiceBlockingStub",
        this.dischargeStudyOperationServiceBlockingStub);
    try {
      var response =
          this.dischargeStudyService.getDischargeStudyCargoByVoyage(
              vesselId, voyageId, dischargeStudyId, correlationId);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testGetDischargeStudyCargoByVoyageException2() {
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long dischargeStudyId = 1L;
    String correlationId = "1";
    Mockito.when(dischargeStudyOperationServiceBlockingStub.getDischargeCowDetails(Mockito.any()))
        .thenReturn(getDCRNS());
    Mockito.when(loadableStudyService.getPortRotation(Mockito.any())).thenReturn(getPRRS());
    Mockito.when(loadableStudyServiceBlockingStub.getCargoNominationById(Mockito.any()))
        .thenReturn(getCNR());
    ReflectionTestUtils.setField(
        dischargeStudyService,
        "loadableStudyServiceBlockingStub",
        this.loadableStudyServiceBlockingStub);
    ReflectionTestUtils.setField(
        dischargeStudyService,
        "dischargeStudyOperationServiceBlockingStub",
        this.dischargeStudyOperationServiceBlockingStub);
    try {
      var response =
          this.dischargeStudyService.getDischargeStudyCargoByVoyage(
              vesselId, voyageId, dischargeStudyId, correlationId);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadableStudy.DischargeCowResponse getDCRNS() {
    LoadableStudy.DischargeCowResponse response =
        LoadableStudy.DischargeCowResponse.newBuilder()
            .setResponseStatus(
                Common.ResponseStatus.newBuilder().setCode("400").setStatus(FAILED).build())
            .build();
    return response;
  }

  private LoadableStudy.CargoNominationReply getCNR() {
    List<LoadableStudy.LoadingPortDetail> list1 = new ArrayList<>();
    LoadableStudy.LoadingPortDetail portDetail =
        LoadableStudy.LoadingPortDetail.newBuilder()
            .setPortId(1L)
            .setQuantity("1")
            .setMode(1L)
            .build();
    list1.add(portDetail);
    List<LoadableStudy.CargoNominationDetail> list = new ArrayList<>();
    LoadableStudy.CargoNominationDetail detail =
        LoadableStudy.CargoNominationDetail.newBuilder()
            .addAllLoadingPortDetails(list1)
            .setDischargingTime("1")
            .setQuantity("1")
            .setApi("1")
            .setTemperature("1")
            .setMaxQuantity("1")
            .setAbbreviation("1")
            .build();
    list.add(detail);
    LoadableStudy.CargoNominationReply reply =
        LoadableStudy.CargoNominationReply.newBuilder()
            .setPatternId(1L)
            .addAllCargoNominations(list)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  @Test
  void testGetDischargeStudyCargoByVoyageException1() {
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long dischargeStudyId = 1L;
    String correlationId = "1";
    Mockito.when(dischargeStudyOperationServiceBlockingStub.getDischargeCowDetails(Mockito.any()))
        .thenReturn(getDCR());
    Mockito.when(loadableStudyService.getPortRotation(Mockito.any())).thenReturn(getPRRS());
    Mockito.when(loadableStudyServiceBlockingStub.getCargoNominationById(Mockito.any()))
        .thenReturn(getCNRNS());
    ReflectionTestUtils.setField(
        dischargeStudyService,
        "loadableStudyServiceBlockingStub",
        this.loadableStudyServiceBlockingStub);
    ReflectionTestUtils.setField(
        dischargeStudyService,
        "dischargeStudyOperationServiceBlockingStub",
        this.dischargeStudyOperationServiceBlockingStub);
    try {
      var response =
          this.dischargeStudyService.getDischargeStudyCargoByVoyage(
              vesselId, voyageId, dischargeStudyId, correlationId);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadableStudy.DischargeCowResponse getDCR() {
    LoadableStudy.DischargeCowResponse response =
        LoadableStudy.DischargeCowResponse.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return response;
  }

  private LoadableStudy.CargoNominationReply getCNRNS() {
    LoadableStudy.CargoNominationReply reply =
        LoadableStudy.CargoNominationReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setCode("400").build())
            .build();
    return reply;
  }

  @Test
  void testGetCargosByPorts() {
    Long dischargeStudyId = 1L;
    HttpHeaders headers = new HttpHeaders();
    Mockito.when(
            dischargeStudyOperationServiceBlockingStub.getDischargeStudyPortWiseCargos(
                Mockito.any()))
        .thenReturn(getDSCR());
    ReflectionTestUtils.setField(
        dischargeStudyService,
        "dischargeStudyOperationServiceBlockingStub",
        this.dischargeStudyOperationServiceBlockingStub);
    try {
      var response = this.dischargeStudyService.getCargosByPorts(dischargeStudyId, headers);
      assertEquals("200", response.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadableStudyModels.DishargeStudyCargoReply getDSCR() {
    List<LoadableStudyModels.DishargeStudyCargoDetail> list1 = new ArrayList<>();
    LoadableStudyModels.DishargeStudyCargoDetail detail =
        LoadableStudyModels.DishargeStudyCargoDetail.newBuilder()
            .setAbbreviation("1")
            .setId(1L)
            .setCrudeType("1")
            .build();
    list1.add(detail);
    List<LoadableStudyModels.DishargeStudyPortCargoMapping> list = new ArrayList<>();
    LoadableStudyModels.DishargeStudyPortCargoMapping mapping =
        LoadableStudyModels.DishargeStudyPortCargoMapping.newBuilder()
            .setPortId(1L)
            .addAllCargos(list1)
            .build();
    list.add(mapping);
    LoadableStudyModels.DishargeStudyCargoReply reply =
        LoadableStudyModels.DishargeStudyCargoReply.newBuilder()
            .addAllPortCargos(list)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  @Test
  void testGetCargosByPortsException() {
    Long dischargeStudyId = 1L;
    HttpHeaders headers = new HttpHeaders();
    Mockito.when(
            dischargeStudyOperationServiceBlockingStub.getDischargeStudyPortWiseCargos(
                Mockito.any()))
        .thenReturn(getDSCR());
    ReflectionTestUtils.setField(
        dischargeStudyService,
        "dischargeStudyOperationServiceBlockingStub",
        this.dischargeStudyOperationServiceBlockingStub);
    try {
      var response = this.dischargeStudyService.getCargosByPorts(dischargeStudyId, headers);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadableStudyModels.DishargeStudyCargoReply getDSCRNS() {
    LoadableStudyModels.DishargeStudyCargoReply reply =
        LoadableStudyModels.DishargeStudyCargoReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  @Test
  void testSaveDischargeStudyWithBackloaing() {
    DischargeStudyCargoResponse request = new DischargeStudyCargoResponse();
    request.setDischargeStudyId(1L);
    request.setPortList(getLPR());
    request.setCowId(1L);
    String correlationId = "1";
    Mockito.when(
            this.dischargeStudyOperationServiceBlockingStub.saveDischargeStudyBackLoading(
                Mockito.any()))
        .thenReturn(getDSR());
    ReflectionTestUtils.setField(
        dischargeStudyService,
        "dischargeStudyOperationServiceBlockingStub",
        this.dischargeStudyOperationServiceBlockingStub);
    try {
      var response =
          this.dischargeStudyService.saveDischargeStudyWithBackloaing(request, correlationId);
      assertEquals("200", response.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private List<PortRotation> getLPR() {
    List<PortRotation> list = new ArrayList<>();
    PortRotation portRotation = new PortRotation();
    portRotation.setId(1L);
    portRotation.setMaxDraft(new BigDecimal(1));
    portRotation.setIsBackLoadingEnabled(true);
    portRotation.setBackLoading(getLBL());
    //    portRotation.setCowId(2L);
    //    portRotation.setPercentage(1L);
    //    portRotation.setTanks(getL());
    portRotation.setCow(true);
    portRotation.setFreshCrudeOilTime(new BigDecimal(1));
    portRotation.setFreshCrudeOil(true);
    portRotation.setInstructionId(getL());
    portRotation.setCargoNominationList(getLCN());
    list.add(portRotation);
    return list;
  }

  private List<CargoNomination> getLCN() {
    List<CargoNomination> list = new ArrayList<>();
    CargoNomination cargoNomination = new CargoNomination();
    cargoNomination.setId(1L);
    cargoNomination.setCargoId(1L);
    cargoNomination.setSequenceNo(1L);
    cargoNomination.setQuantity(new BigDecimal(1));
    cargoNomination.setMode(1L);
    cargoNomination.setAbbreviation("1");
    cargoNomination.setColor("1");
    cargoNomination.setApi(new BigDecimal(1));
    cargoNomination.setTemperature(new BigDecimal(1));
    cargoNomination.setEmptyMaxNoOfTanks(true);
    list.add(cargoNomination);
    return list;
  }

  private List<Long> getL() {
    List<Long> list = new ArrayList<>();
    list.add(1L);
    return list;
  }

  private List<BackLoading> getLBL() {
    List<BackLoading> list = new ArrayList<>();
    BackLoading backLoading = new BackLoading();
    backLoading.setId(1L);
    backLoading.setApi(new BigDecimal(1));
    backLoading.setCargoId(1L);
    backLoading.setQuantity(new BigDecimal(1));
    backLoading.setColor("1");
    backLoading.setAbbreviation("1");
    backLoading.setTemperature(new BigDecimal(1));
    list.add(backLoading);
    return list;
  }

  @Test
  void testSaveDischargeStudyWithBackLoaingException() {
    DischargeStudyCargoResponse request = new DischargeStudyCargoResponse();
    request.setDischargeStudyId(1L);
    request.setPortList(getLPR());
    request.setCowId(1L);
    String correlationId = "1";
    Mockito.when(
            this.dischargeStudyOperationServiceBlockingStub.saveDischargeStudyBackLoading(
                Mockito.any()))
        .thenReturn(getDSRNS());
    ReflectionTestUtils.setField(
        dischargeStudyService,
        "dischargeStudyOperationServiceBlockingStub",
        this.dischargeStudyOperationServiceBlockingStub);
    try {
      var response =
          this.dischargeStudyService.saveDischargeStudyWithBackloaing(request, correlationId);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testGenerateDischargePatternsWith1Args() {
    AlgoRequest request = AlgoRequest.newBuilder().build();
    Mockito.when(
            this.dischargeStudyOperationServiceBlockingStub.generateDischargePatterns(
                Mockito.any()))
        .thenReturn(getAR());
    ReflectionTestUtils.setField(
        dischargeStudyService,
        "dischargeStudyOperationServiceBlockingStub",
        this.dischargeStudyOperationServiceBlockingStub);
    var response = this.dischargeStudyService.generateDischargePatterns(request);
    assertEquals("1", response.getProcesssId());
  }

  private AlgoReply getAR() {
    AlgoReply reply = AlgoReply.newBuilder().setProcesssId("1").build();
    return reply;
  }

  @Test
  void testGetDischargePatternDetails() {
    Long loadableStudyId = 1L;
    Long vesselId = 1L;
    String correlationId = "1";
    Mockito.when(dischargeStudyOperationServiceBlockingStub.getDischargePlanDetails(Mockito.any()))
        .thenReturn(getCNR());
    ReflectionTestUtils.setField(
        dischargeStudyService,
        "dischargeStudyOperationServiceBlockingStub",
        this.dischargeStudyOperationServiceBlockingStub);
    Mockito.when(loadableStudyService.getPortRotation(Mockito.any())).thenReturn(getPRRS());
    try {
      var response =
          this.dischargeStudyService.getDischargePatternDetails(
              loadableStudyId, vesselId, correlationId);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testGetDischargePatternDetailsException() {
    Long loadableStudyId = 1L;
    Long vesselId = 1L;
    String correlationId = "1";
    Mockito.when(dischargeStudyOperationServiceBlockingStub.getDischargePlanDetails(Mockito.any()))
        .thenReturn(getCNR());
    ReflectionTestUtils.setField(
        dischargeStudyService,
        "dischargeStudyOperationServiceBlockingStub",
        this.dischargeStudyOperationServiceBlockingStub);
    Mockito.when(loadableStudyService.getPortRotation(Mockito.any())).thenReturn(getPRRNS());
    try {
      var response =
          this.dischargeStudyService.getDischargePatternDetails(
              loadableStudyId, vesselId, correlationId);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testConfirmPlan() {
    Long dischargeStudyId = 1L;
    Long dischargePatternId = 1L;
    String correlationId = "1";
    Mockito.when(this.dischargeStudyOperationServiceBlockingStub.confirmPlan(Mockito.any()))
        .thenReturn(getCPR());
    ReflectionTestUtils.setField(
        dischargeStudyService,
        "dischargeStudyOperationServiceBlockingStub",
        this.dischargeStudyOperationServiceBlockingStub);
    try {
      var response =
          this.dischargeStudyService.confirmPlan(
              dischargeStudyId, dischargePatternId, correlationId);
      assertEquals("200", response.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadableStudy.ConfirmPlanReply getCPR() {
    LoadableStudy.ConfirmPlanReply reply =
        LoadableStudy.ConfirmPlanReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  @Test
  void testConfirmPlanException() {
    Long dischargeStudyId = 1L;
    Long dischargePatternId = 1L;
    String correlationId = "1";
    Mockito.when(this.dischargeStudyOperationServiceBlockingStub.confirmPlan(Mockito.any()))
        .thenReturn(getCPRNS());
    ReflectionTestUtils.setField(
        dischargeStudyService,
        "dischargeStudyOperationServiceBlockingStub",
        this.dischargeStudyOperationServiceBlockingStub);
    try {
      var response =
          this.dischargeStudyService.confirmPlan(
              dischargeStudyId, dischargePatternId, correlationId);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private LoadableStudy.ConfirmPlanReply getCPRNS() {
    LoadableStudy.ConfirmPlanReply reply =
        LoadableStudy.ConfirmPlanReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setCode("400").build())
            .build();
    return reply;
  }
}
