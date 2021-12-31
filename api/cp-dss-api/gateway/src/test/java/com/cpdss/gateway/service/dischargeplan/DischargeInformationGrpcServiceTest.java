/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.dischargeplan;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.discharge_plan.*;
import com.cpdss.gateway.domain.*;
import com.google.protobuf.ByteString;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

@SpringJUnitConfig(classes = {DischargeInformationGrpcService.class})
public class DischargeInformationGrpcServiceTest {

  @Autowired DischargeInformationGrpcService dischargeInformationGrpcService;

  @MockBean
  DischargeInformationServiceGrpc.DischargeInformationServiceBlockingStub dischargeInfoServiceStub;

  private static final String SUCCESS = "SUCCESS";

  @Test
  void testGetDischargeInfoRpc() {
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long portRotationId = 1L;
    Mockito.when(this.dischargeInfoServiceStub.getDischargeInformation(Mockito.any()))
        .thenReturn(getDI());
    ReflectionTestUtils.setField(
        dischargeInformationGrpcService, "dischargeInfoServiceStub", this.dischargeInfoServiceStub);
    try {
      var reply =
          this.dischargeInformationGrpcService.getDischargeInfoRpc(
              vesselId, voyageId, portRotationId);
      assertEquals(SUCCESS, reply.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private DischargeInformation getDI() {
    DischargeInformation dischargeInformation =
        DischargeInformation.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return dischargeInformation;
  }

  @Test
  void testGetDischargeInfoRpcException() {
    Long vesselId = 1L;
    Long voyageId = 1L;
    Long portRotationId = 1L;
    Mockito.when(this.dischargeInfoServiceStub.getDischargeInformation(Mockito.any()))
        .thenReturn(getDINS());
    ReflectionTestUtils.setField(
        dischargeInformationGrpcService, "dischargeInfoServiceStub", this.dischargeInfoServiceStub);
    try {
      var reply =
          this.dischargeInformationGrpcService.getDischargeInfoRpc(
              vesselId, voyageId, portRotationId);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private DischargeInformation getDINS() {
    DischargeInformation dischargeInformation = DischargeInformation.newBuilder().build();
    return dischargeInformation;
  }

  @Test
  void testGetOrSaveRulesForDischarge() {
    Long vesselId = 1L;
    Long dischargeStudyId = 1L;
    RuleRequest dischargeStudyRuleRequest = new RuleRequest();
    dischargeStudyRuleRequest.setPlan(getRP());
    String correlationId = "1";
    Mockito.when(dischargeInfoServiceStub.getOrSaveRulesForDischarging(Mockito.any()))
        .thenReturn(getDRR());
    ReflectionTestUtils.setField(
        dischargeInformationGrpcService, "dischargeInfoServiceStub", this.dischargeInfoServiceStub);
    try {
      var reply =
          this.dischargeInformationGrpcService.getOrSaveRulesForDischarge(
              vesselId, dischargeStudyId, dischargeStudyRuleRequest, correlationId);
      assertEquals("1", reply.getPlan().get(0).getHeader());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private DischargeRuleReply getDRR() {
    List<Common.RulesInputs> list2 = new ArrayList<>();
    Common.RulesInputs rulesInputs = Common.RulesInputs.newBuilder().setId("1").build();
    list2.add(rulesInputs);
    List<Common.Rules> list1 = new ArrayList<>();
    Common.Rules rules =
        Common.Rules.newBuilder()
            .addAllInputs(list2)
            .setEnable(true)
            .setDisplayInSettings(true)
            .setVesselRuleXId("1")
            .setId("1")
            .setIsHardRule(true)
            .setNumericPrecision(1L)
            .setNumericScale(1L)
            .setRuleTemplateId("1")
            .setRuleType("1")
            .build();
    list1.add(rules);
    List<Common.RulePlans> list = new ArrayList<>();
    Common.RulePlans rulePlans =
        Common.RulePlans.newBuilder().setHeader("1").addAllRules(list1).build();
    list.add(rulePlans);
    DischargeRuleReply dischargeRuleReply =
        DischargeRuleReply.newBuilder()
            .addAllRulePlan(list)
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return dischargeRuleReply;
  }

  private List<RulePlans> getRP() {
    List<RulePlans> list = new ArrayList<>();
    RulePlans rulePlans = new RulePlans();
    rulePlans.setRules(getLR());
    list.add(rulePlans);
    return list;
  }

  private List<Rules> getLR() {
    List<Rules> list = new ArrayList<>();
    Rules rules = new Rules();
    rules.setDisable(true);
    rules.setDisplayInSettings(true);
    rules.setEnable(true);
    rules.setId("1");
    rules.setRuleTemplateId("1");
    rules.setRuleType("1");
    rules.setVesselRuleXId("1");
    rules.setIsHardRule(true);
    rules.setNumericPrecision(1L);
    rules.setNumericScale(1L);
    rules.setInputs(getLI());
    list.add(rules);
    return list;
  }

  private List<RulesInputs> getLI() {
    List<RulesInputs> list = new ArrayList<>();
    RulesInputs rulesInputs = new RulesInputs();
    rulesInputs.setDefaultValue("1");
    rulesInputs.setMin("1");
    rulesInputs.setMax("1");
    rulesInputs.setId("1");
    rulesInputs.setSuffix("1");
    rulesInputs.setPrefix("1");
    rulesInputs.setType("1");
    rulesInputs.setValue("1");
    rulesInputs.setIsMandatory(true);
    list.add(rulesInputs);
    return list;
  }

  @Test
  void testGetOrSaveRulesForDischargeException() {
    Long vesselId = 1L;
    Long dischargeStudyId = 1L;
    RuleRequest dischargeStudyRuleRequest = new RuleRequest();
    dischargeStudyRuleRequest.setPlan(getRP());
    String correlationId = "1";
    Mockito.when(dischargeInfoServiceStub.getOrSaveRulesForDischarging(Mockito.any()))
        .thenReturn(getDRRNS());
    ReflectionTestUtils.setField(
        dischargeInformationGrpcService, "dischargeInfoServiceStub", this.dischargeInfoServiceStub);
    try {
      var reply =
          this.dischargeInformationGrpcService.getOrSaveRulesForDischarge(
              vesselId, dischargeStudyId, dischargeStudyRuleRequest, correlationId);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private DischargeRuleReply getDRRNS() {
    DischargeRuleReply dischargeRuleReply =
        DischargeRuleReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setCode("400").build())
            .build();
    return dischargeRuleReply;
  }

  @Test
  void testDischargeUploadLoadingTideDetails() {
    Long loadingId = 1L;
    MultipartFile file = new CommMultipartFile();
    String correlationId = "1";
    String portName = "1";
    Long portId = 1L;
    Mockito.when(dischargeInfoServiceStub.dischargingUploadPortTideDetails(Mockito.any()))
        .thenReturn(getDUTDS());
    ReflectionTestUtils.setField(
        dischargeInformationGrpcService, "dischargeInfoServiceStub", this.dischargeInfoServiceStub);
    try {
      var reply =
          this.dischargeInformationGrpcService.dischargeUploadLoadingTideDetails(
              loadingId, file, correlationId, portName, portId);
      assertEquals("200", reply.getResponseStatus().getStatus());
    } catch (IOException e) {
      e.printStackTrace();
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private DischargingUploadTideDetailStatusReply getDUTDS() {
    DischargingUploadTideDetailStatusReply reply =
        DischargingUploadTideDetailStatusReply.newBuilder()
            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
            .build();
    return reply;
  }

  private class CommMultipartFile implements MultipartFile {

    @Override
    public String getName() {
      return null;
    }

    @Override
    public String getOriginalFilename() {
      return "xlsx";
    }

    @Override
    public String getContentType() {
      return null;
    }

    @Override
    public boolean isEmpty() {
      return false;
    }

    @Override
    public long getSize() {
      return 0;
    }

    @Override
    public byte[] getBytes() throws IOException {
      return new byte[1];
    }

    @Override
    public InputStream getInputStream() throws IOException {
      return null;
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {}
  }

  @Test
  void testDischargeUploadLoadingTideDetailsException1() {
    Long loadingId = 1L;
    MultipartFile file = new ComMultipartFile();
    String correlationId = "1";
    String portName = "1";
    Long portId = 1L;
    Mockito.when(dischargeInfoServiceStub.dischargingUploadPortTideDetails(Mockito.any()))
        .thenReturn(getDUTDS());
    ReflectionTestUtils.setField(
        dischargeInformationGrpcService, "dischargeInfoServiceStub", this.dischargeInfoServiceStub);
    try {
      var reply =
          this.dischargeInformationGrpcService.dischargeUploadLoadingTideDetails(
              loadingId, file, correlationId, portName, portId);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private class ComMultipartFile implements MultipartFile {

    @Override
    public String getName() {
      return null;
    }

    @Override
    public String getOriginalFilename() {
      return "1";
    }

    @Override
    public String getContentType() {
      return null;
    }

    @Override
    public boolean isEmpty() {
      return false;
    }

    @Override
    public long getSize() {
      return 0;
    }

    @Override
    public byte[] getBytes() throws IOException {
      return new byte[1];
    }

    @Override
    public InputStream getInputStream() throws IOException {
      return null;
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {}
  }

  @Test
  void testDischargeUploadLoadingTideDetailsException2() {
    Long loadingId = 1L;
    MultipartFile file = new ComMultipartFile();
    String correlationId = "1";
    String portName = "1";
    Long portId = 1L;
    Mockito.when(dischargeInfoServiceStub.dischargingUploadPortTideDetails(Mockito.any()))
        .thenReturn(getDUTDSNS());
    ReflectionTestUtils.setField(
        dischargeInformationGrpcService, "dischargeInfoServiceStub", this.dischargeInfoServiceStub);
    try {
      var reply =
          this.dischargeInformationGrpcService.dischargeUploadLoadingTideDetails(
              loadingId, file, correlationId, portName, portId);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private DischargingUploadTideDetailStatusReply getDUTDSNS() {
    DischargingUploadTideDetailStatusReply reply =
        DischargingUploadTideDetailStatusReply.newBuilder()
            .setResponseStatus(
                Common.ResponseStatus.newBuilder()
                    .setMessage("1")
                    .setCode("400")
                    .setHttpStatusCode(400)
                    .build())
            .build();
    return reply;
  }

  @Test
  void testDownloadLoadingPortTideDetails() {
    Long dischargingId = 1L;

    Mockito.when(dischargeInfoServiceStub.dischargingDownloadPortTideDetails(Mockito.any()))
        .thenReturn(getDDTDS());

    ReflectionTestUtils.setField(
        dischargeInformationGrpcService, "dischargeInfoServiceStub", this.dischargeInfoServiceStub);
    try {
      var response =
          this.dischargeInformationGrpcService.downloadLoadingPortTideDetails(dischargingId);
      assertEquals(0, response.length);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private Iterator<DischargingDownloadTideDetailStatusReply> getDDTDS() {
    Iterator<DischargingDownloadTideDetailStatusReply> reply =
        new Iterator<DischargingDownloadTideDetailStatusReply>() {
          @Override
          public boolean hasNext() {
            return false;
          }

          @Override
          public DischargingDownloadTideDetailStatusReply next() {
            DischargingDownloadTideDetailStatusReply statusReply =
                DischargingDownloadTideDetailStatusReply.newBuilder()
                    .setData(ByteString.EMPTY)
                    .setResponseStatus(
                        Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
                    .build();
            return statusReply;
          }
        };
    return reply;
  }

  @Test
  void testDownloadLoadingPortTideDetailsException() {
    Long dischargingId = 1L;

    Mockito.when(dischargeInfoServiceStub.dischargingDownloadPortTideDetails(Mockito.any()))
        .thenReturn(getDDTDSNS());

    ReflectionTestUtils.setField(
        dischargeInformationGrpcService, "dischargeInfoServiceStub", this.dischargeInfoServiceStub);
    try {
      var response =
          this.dischargeInformationGrpcService.downloadLoadingPortTideDetails(dischargingId);
      assertEquals(0, response.length);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  private Iterator<DischargingDownloadTideDetailStatusReply> getDDTDSNS() {
    Iterator<DischargingDownloadTideDetailStatusReply> reply =
        new Iterator<DischargingDownloadTideDetailStatusReply>() {
          @Override
          public boolean hasNext() {
            return false;
          }

          @Override
          public DischargingDownloadTideDetailStatusReply next() {
            DischargingDownloadTideDetailStatusReply statusReply =
                DischargingDownloadTideDetailStatusReply.newBuilder()
                    .setData(ByteString.EMPTY)
                    .setResponseStatus(
                        Common.ResponseStatus.newBuilder()
                            .setMessage("failure")
                            .setCode("400")
                            .setHttpStatusCode(400)
                            .build())
                    .build();
            return statusReply;
          }
        };
    return reply;
  }

  @Test
  void testSaveOrGetDischargingPlanRules() {
    DischargeRuleRequest.Builder builder = DischargeRuleRequest.newBuilder();
    Mockito.when(this.dischargeInfoServiceStub.getOrSaveRulesForDischarging(Mockito.any()))
        .thenReturn(getDRR());
    ReflectionTestUtils.setField(
        dischargeInformationGrpcService, "dischargeInfoServiceStub", this.dischargeInfoServiceStub);
    try {
      var response = this.dischargeInformationGrpcService.saveOrGetDischargingPlanRules(builder);
      assertEquals("200", response.getResponseStatus().getStatus());
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testSaveOrGetDischargingPlanRulesException() {
    DischargeRuleRequest.Builder builder = DischargeRuleRequest.newBuilder();
    Mockito.when(this.dischargeInfoServiceStub.getOrSaveRulesForDischarging(Mockito.any()))
        .thenReturn(getDRRNS());
    ReflectionTestUtils.setField(
        dischargeInformationGrpcService, "dischargeInfoServiceStub", this.dischargeInfoServiceStub);
    try {
      var response = this.dischargeInformationGrpcService.saveOrGetDischargingPlanRules(builder);
    } catch (GenericServiceException e) {
      e.printStackTrace();
    }
  }
}
