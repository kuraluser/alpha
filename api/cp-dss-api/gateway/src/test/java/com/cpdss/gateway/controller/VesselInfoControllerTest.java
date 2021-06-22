/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.GatewayTestConfiguration;
import com.cpdss.gateway.domain.RulePlans;
import com.cpdss.gateway.domain.RuleRequest;
import com.cpdss.gateway.domain.RuleResponse;
import com.cpdss.gateway.domain.Rules;
import com.cpdss.gateway.domain.VesselDetailsResponse;
import com.cpdss.gateway.service.VesselInfoService;
import com.cpdss.gateway.service.VesselInfoServiceTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/** @Author jerin.g */
@MockitoSettings
@WebMvcTest(controllers = VesselInfoController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {GatewayTestConfiguration.class})
public class VesselInfoControllerTest {
  @Autowired private MockMvc mockMvc;

  @MockBean private VesselInfoService vesselInfoService;

  @MockBean private VesselInfoServiceTest vesselInfoServiceTest;

  private static final Long TEST_VESSEL_ID = 1L;
  private static final String CORRELATION_ID_HEADER = "correlationId";
  private static final String CORRELATION_ID_HEADER_VALUE = "1234";

  private static final String CLOUD_API_URL_PREFIX = "/api/cloud";
  private static final String SHIP_API_URL_PREFIX = "/api/ship";
  private static final String GET_VESSEL_DETAILS_API_URL = "/vessel-details/{vesselId}";
  private static final String GET_VESSEL_DETAILS_API_URL_CLOUD_API_URL =
      CLOUD_API_URL_PREFIX + GET_VESSEL_DETAILS_API_URL;
  private static final String GET_VESSEL_DETAILS_SHIP_API_URL =
      SHIP_API_URL_PREFIX + GET_VESSEL_DETAILS_API_URL;
  private static final String GET_VESSEL_RULE_URL =
      "/vessel-rule/vessels/{vesselId}/ruleMasterSectionId/{sectionId}";
  private static final String GET_VESSEL_RULE_CLOUD_API_URL =
      CLOUD_API_URL_PREFIX + GET_VESSEL_RULE_URL;
  private static final String GET_VESSEL_RULE_SHIP_API_URL =
      SHIP_API_URL_PREFIX + GET_VESSEL_RULE_URL;
  private static final Long TEST_RULE_SECTION_ID = 1L;

  @ValueSource(
      strings = {GET_VESSEL_DETAILS_API_URL_CLOUD_API_URL, GET_VESSEL_DETAILS_SHIP_API_URL})
  @ParameterizedTest
  void testGetVesselsDetails(String url) throws Exception {
    when(this.vesselInfoService.getVesselsDetails(anyLong(), anyString()))
        .thenReturn(new VesselDetailsResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, TEST_VESSEL_ID)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(strings = {GET_VESSEL_RULE_CLOUD_API_URL, GET_VESSEL_RULE_SHIP_API_URL})
  @ParameterizedTest
  void testGetAllRulesForVessel(String url) throws Exception {
    when(this.vesselInfoService.getRulesByVesselIdAndSectionId(
            anyLong(), anyLong(), null, anyString()))
        .thenReturn(new RuleResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, TEST_VESSEL_ID, TEST_RULE_SECTION_ID)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE))
        .andExpect(status().isOk());
  }

  @ValueSource(classes = {GenericServiceException.class, RuntimeException.class})
  @ParameterizedTest
  void testGetVesselsDetailsRuntimeException(Class<? extends Exception> exceptionClass)
      throws Exception {
    Exception ex = new RuntimeException();
    if (exceptionClass == GenericServiceException.class) {
      ex =
          new GenericServiceException(
              "exception",
              CommonErrorCodes.E_GEN_INTERNAL_ERR,
              HttpStatusCode.INTERNAL_SERVER_ERROR);
    }
    when(this.vesselInfoService.getVesselsDetails(anyLong(), anyString())).thenThrow(ex);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(GET_VESSEL_DETAILS_API_URL_CLOUD_API_URL, TEST_VESSEL_ID)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE))
        .andExpect(status().isInternalServerError());
  }

  @ValueSource(strings = {GET_VESSEL_RULE_CLOUD_API_URL, GET_VESSEL_RULE_SHIP_API_URL})
  @ParameterizedTest
  void testSaveRulesForVessel(String url) throws Exception {
    when(this.vesselInfoService.getRulesByVesselIdAndSectionId(
            anyLong(), anyLong(), any(RuleRequest.class), anyString()))
        .thenReturn(new RuleResponse());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(url, TEST_VESSEL_ID, TEST_RULE_SECTION_ID)
                .content(createRuleRequest())
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE))
        .andExpect(status().isOk());
  }

  private String createRuleRequest() throws JsonProcessingException {
    RuleRequest request = new RuleRequest();
    List<RulePlans> rulePlanList = new ArrayList<RulePlans>();
    List<Rules> rules = new ArrayList<Rules>();
    List<com.cpdss.gateway.domain.RulesInputs> ruleInputList =
        new ArrayList<com.cpdss.gateway.domain.RulesInputs>();
    RulePlans rulePlan = new RulePlans();
    Rules rule = new Rules();
    rule.setDisableInSettigs(true);
    rule.setEnable(true);
    // rule.setId("1");
    rule.setRuleTemplateId("701");
    rule.setRuleType("Absolute");
    com.cpdss.gateway.domain.RulesInputs input = new com.cpdss.gateway.domain.RulesInputs();
    input.setPrefix("Condensate cargo can only be put in a tank for");
    input.setType("Number");
    input.setMax("10");
    input.setMin("1");
    // input.setId("1");
    input.setSuffix("voyages apart");
    ruleInputList.add(input);
    rule.setInputs(ruleInputList);
    rules.add(rule);
    rulePlan.setRules(rules);
    request.setPlan(rulePlanList);
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(request);
  }
}
