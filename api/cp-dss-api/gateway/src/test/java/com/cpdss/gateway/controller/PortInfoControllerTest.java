/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.controller;

import com.cpdss.gateway.GatewayTestConfiguration;
import com.cpdss.gateway.domain.*;
import com.cpdss.gateway.service.*;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@MockitoSettings
@WebMvcTest(PortInfoController.class)
@ContextConfiguration(classes = {GatewayTestConfiguration.class})
@AutoConfigureMockMvc(addFilters = false)
public class PortInfoControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean InstructionService instructionService;

  @MockBean PortInfoService portInfoService;

  private static final String CORRELATION_ID_HEADER = "correlationId";
  private static final String CORRELATION_ID_HEADER_VALUE = "1234";

  private static final String CLOUD_API_URL_PREFIX = "/api/cloud";
  private static final String SHIP_API_URL_PREFIX = "/api/ship";
  private static final String PORT_INSTRUCTION = "/port-instructions";
  private static final String CLOUD_PORT_INSTRUCTION = CLOUD_API_URL_PREFIX + PORT_INSTRUCTION;
  private static final String SHIP_PORT_INSTRUCTION = SHIP_API_URL_PREFIX + PORT_INSTRUCTION;
  private static final String PORT_DETAILS = "/portInfo/{portId}";
  private static final String CLOUD_PORT_DETAILS = CLOUD_API_URL_PREFIX + PORT_DETAILS;
  private static final String SHIP_PORT_DETAILS = SHIP_API_URL_PREFIX + PORT_DETAILS;
  private static final String GET_COUNTRIES = "/countries";
  private static final String CLOUD_GET_COUNTRIES = CLOUD_API_URL_PREFIX + GET_COUNTRIES;
  private static final String SHIP_GET_COUNTRIES = SHIP_API_URL_PREFIX + GET_COUNTRIES;
  private static final String SAVE_PORT_INFO = "/portInfo/{portId}";
  private static final String CLOUD_SAVE_PORT_INFO = CLOUD_API_URL_PREFIX + SAVE_PORT_INFO;
  private static final String SHIP_SAVE_PORT_INFO = SHIP_API_URL_PREFIX + SAVE_PORT_INFO;

  //  @ValueSource(strings = {CLOUD_PORT_INSTRUCTION, SHIP_PORT_INSTRUCTION})
  //  @ParameterizedTest
  //  void testGetDischargeStudyByVoyage(String url) throws Exception {
  //    Mockito.when(this.instructionService.getInstructions(Mockito.anyString()))
  //        .thenReturn(new InstructionResponse());
  //    this.mockMvc
  //        .perform(
  //            MockMvcRequestBuilders.get(url)
  //                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
  //                .contentType(MediaType.APPLICATION_JSON_VALUE)
  //                .accept(MediaType.APPLICATION_JSON_VALUE))
  //        .andExpect(status().isOk());
  //  }
  //
  //  @ValueSource(strings = {CLOUD_PORT_INSTRUCTION, SHIP_PORT_INSTRUCTION})
  //  @ParameterizedTest
  //  void testGetDischargeStudyByVoyageServiceException(String url) throws Exception {
  //    Mockito.when(this.instructionService.getInstructions(Mockito.anyString()))
  //        .thenThrow(
  //            new GenericServiceException(
  //                "service exception",
  //                CommonErrorCodes.E_GEN_INTERNAL_ERR,
  //                HttpStatusCode.INTERNAL_SERVER_ERROR));
  //    this.mockMvc
  //        .perform(
  //            MockMvcRequestBuilders.get(url)
  //                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
  //                .contentType(MediaType.APPLICATION_JSON_VALUE)
  //                .accept(MediaType.APPLICATION_JSON_VALUE))
  //        .andExpect(status().isInternalServerError());
  //  }
  //
  //  @ValueSource(strings = {CLOUD_PORT_INSTRUCTION, SHIP_PORT_INSTRUCTION})
  //  @ParameterizedTest
  //  void testGetDischargeStudyByVoyageRuntimeException(String url) throws Exception {
  //    Mockito.when(this.instructionService.getInstructions(Mockito.anyString()))
  //        .thenThrow(RuntimeException.class);
  //    this.mockMvc
  //        .perform(
  //            MockMvcRequestBuilders.get(url)
  //                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
  //                .contentType(MediaType.APPLICATION_JSON_VALUE)
  //                .accept(MediaType.APPLICATION_JSON_VALUE))
  //        .andExpect(status().isInternalServerError());
  //  }
  //
  //  @ValueSource(strings = {CLOUD_PORT_DETAILS, SHIP_PORT_DETAILS})
  //  @ParameterizedTest
  //  void testGetPortDetailsByPortId(String url) throws Exception {
  //    Mockito.when(
  //            this.portInfoService.getPortInformationByPortId(Mockito.anyLong(),
  // Mockito.anyString()))
  //        .thenReturn(new PortDetailResponse());
  //    this.mockMvc
  //        .perform(
  //            MockMvcRequestBuilders.get(url, 1L)
  //                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
  //                .contentType(MediaType.APPLICATION_JSON_VALUE)
  //                .accept(MediaType.APPLICATION_JSON_VALUE))
  //        .andExpect(status().isOk());
  //  }
  //
  //  @ValueSource(strings = {CLOUD_PORT_DETAILS, SHIP_PORT_DETAILS})
  //  @ParameterizedTest
  //  void testGetPortDetailsByPortIdServiceException(String url) throws Exception {
  //    Mockito.when(
  //            this.portInfoService.getPortInformationByPortId(Mockito.anyLong(),
  // Mockito.anyString()))
  //        .thenThrow(
  //            new GenericServiceException(
  //                "service exception",
  //                CommonErrorCodes.E_GEN_INTERNAL_ERR,
  //                HttpStatusCode.INTERNAL_SERVER_ERROR));
  //    this.mockMvc
  //        .perform(
  //            MockMvcRequestBuilders.get(url, 1L)
  //                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
  //                .contentType(MediaType.APPLICATION_JSON_VALUE)
  //                .accept(MediaType.APPLICATION_JSON_VALUE))
  //        .andExpect(status().isInternalServerError());
  //  }
  //
  //  @ValueSource(strings = {CLOUD_PORT_DETAILS, SHIP_PORT_DETAILS})
  //  @ParameterizedTest
  //  void testGetPortDetailsByPortIdRuntimeException(String url) throws Exception {
  //    Mockito.when(
  //            this.portInfoService.getPortInformationByPortId(Mockito.anyLong(),
  // Mockito.anyString()))
  //        .thenThrow(RuntimeException.class);
  //    this.mockMvc
  //        .perform(
  //            MockMvcRequestBuilders.get(url, 1L)
  //                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
  //                .contentType(MediaType.APPLICATION_JSON_VALUE)
  //                .accept(MediaType.APPLICATION_JSON_VALUE))
  //        .andExpect(status().isInternalServerError());
  //  }
  //
  //  @ValueSource(strings = {CLOUD_GET_COUNTRIES, SHIP_GET_COUNTRIES})
  //  @ParameterizedTest
  //  void testGetAllCountrys(String url) throws Exception {
  //    Mockito.when(this.portInfoService.getAllCountrys(Mockito.anyString()))
  //        .thenReturn(new CountrysResponse());
  //    this.mockMvc
  //        .perform(
  //            MockMvcRequestBuilders.get(url)
  //                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
  //                .contentType(MediaType.APPLICATION_JSON_VALUE)
  //                .accept(MediaType.APPLICATION_JSON_VALUE))
  //        .andExpect(status().isOk());
  //  }
  //
  //  @ValueSource(strings = {CLOUD_GET_COUNTRIES, SHIP_GET_COUNTRIES})
  //  @ParameterizedTest
  //  void testGetAllCountrysServiceException(String url) throws Exception {
  //    Mockito.when(this.portInfoService.getAllCountrys(Mockito.anyString()))
  //        .thenThrow(
  //            new GenericServiceException(
  //                "service exception",
  //                CommonErrorCodes.E_GEN_INTERNAL_ERR,
  //                HttpStatusCode.INTERNAL_SERVER_ERROR));
  //    this.mockMvc
  //        .perform(
  //            MockMvcRequestBuilders.get(url)
  //                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
  //                .contentType(MediaType.APPLICATION_JSON_VALUE)
  //                .accept(MediaType.APPLICATION_JSON_VALUE))
  //        .andExpect(status().isInternalServerError());
  //  }
  //
  //  @ValueSource(strings = {CLOUD_GET_COUNTRIES, SHIP_GET_COUNTRIES})
  //  @ParameterizedTest
  //  void testGetAllCountrysRuntimeException(String url) throws Exception {
  //    Mockito.when(this.portInfoService.getAllCountrys(Mockito.anyString()))
  //        .thenThrow(RuntimeException.class);
  //    this.mockMvc
  //        .perform(
  //            MockMvcRequestBuilders.get(url)
  //                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
  //                .contentType(MediaType.APPLICATION_JSON_VALUE)
  //                .accept(MediaType.APPLICATION_JSON_VALUE))
  //        .andExpect(status().isInternalServerError());
  //  }
  //
  //  @ValueSource(strings = {CLOUD_SAVE_PORT_INFO, SHIP_SAVE_PORT_INFO})
  //  @ParameterizedTest
  //  void testSavePortInfo(String url) throws Exception {
  //    Mockito.when(
  //            this.portInfoService.savePortInfo(
  //                Mockito.anyLong(), Mockito.anyString(), Mockito.any()))
  //        .thenReturn(new PortDetailResponse());
  //    this.mockMvc
  //        .perform(
  //            MockMvcRequestBuilders.post(url, 1L)
  //                .content(this.createPortRotationRequest())
  //                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
  //                .contentType(MediaType.APPLICATION_JSON_VALUE)
  //                .accept(MediaType.APPLICATION_JSON_VALUE))
  //        .andExpect(status().isOk());
  //  }
  //
  //  private String createPortRotationRequest() throws JsonProcessingException {
  //    PortDetails request = new PortDetails();
  //    request.setAmbientTemperature(new BigDecimal(1));
  //    ObjectMapper mapper = new ObjectMapper();
  //    return mapper.writeValueAsString(request);
  //  }
  //
  //  @ValueSource(strings = {CLOUD_SAVE_PORT_INFO, SHIP_SAVE_PORT_INFO})
  //  @ParameterizedTest
  //  void testSavePortInfoServiceException(String url) throws Exception {
  //    Mockito.when(
  //            this.portInfoService.savePortInfo(
  //                Mockito.anyLong(), Mockito.anyString(), Mockito.any()))
  //        .thenThrow(
  //            new GenericServiceException(
  //                "service exception",
  //                CommonErrorCodes.E_GEN_INTERNAL_ERR,
  //                HttpStatusCode.INTERNAL_SERVER_ERROR));
  //    this.mockMvc
  //        .perform(
  //            MockMvcRequestBuilders.post(url, 1L)
  //                .content(this.createPortRotationRequest())
  //                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
  //                .contentType(MediaType.APPLICATION_JSON_VALUE)
  //                .accept(MediaType.APPLICATION_JSON_VALUE))
  //        .andExpect(status().isInternalServerError());
  //  }
  //
  //  @ValueSource(strings = {CLOUD_SAVE_PORT_INFO, SHIP_SAVE_PORT_INFO})
  //  @ParameterizedTest
  //  void testSavePortInfoRuntimeException(String url) throws Exception {
  //    Mockito.when(
  //            this.portInfoService.savePortInfo(
  //                Mockito.anyLong(), Mockito.anyString(), Mockito.any()))
  //        .thenThrow(RuntimeException.class);
  //    this.mockMvc
  //        .perform(
  //            MockMvcRequestBuilders.post(url, 1L)
  //                .content(this.createPortRotationRequest())
  //                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE)
  //                .contentType(MediaType.APPLICATION_JSON_VALUE)
  //                .accept(MediaType.APPLICATION_JSON_VALUE))
  //        .andExpect(status().isInternalServerError());
  //  }
}
