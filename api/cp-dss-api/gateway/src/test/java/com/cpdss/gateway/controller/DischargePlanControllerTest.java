/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.gateway.GatewayTestConfiguration;
import com.cpdss.gateway.domain.dischargeplan.DischargeInformation;
import com.cpdss.gateway.domain.dischargeplan.DischargingInstructionResponse;
import com.cpdss.gateway.domain.dischargeplan.DischargingInstructionsSaveRequest;
import com.cpdss.gateway.domain.dischargeplan.DischargingInstructionsSaveResponse;
import com.cpdss.gateway.domain.dischargeplan.DischargingInstructionsStatus;
import com.cpdss.gateway.domain.dischargeplan.DischargingInstructionsUpdateRequest;
import com.cpdss.gateway.service.dischargeplan.DischargeInformationService;
import com.cpdss.gateway.service.dischargeplan.DischargingInstructionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@MockitoSettings
@WebMvcTest(controllers = DischargePlanController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {GatewayTestConfiguration.class, DischargeInformationService.class, DischargingInstructionService.class})
@TestPropertySource(properties = {"cpdss.build.env=none"})
public class DischargePlanControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean DischargeInformationService dischargeInformationService;
  
  @MockBean DischargingInstructionService dischargingInstructionService;

  // Common for discharge plan controller
  private static final String CORRELATION_ID_HEADER = "correlationId";
  private static final String CORRELATION_ID_HEADER_VALUE = "1234";
  private static final Long TEST_VESSEL_ID = 1L;
  private static final Long TEST_DISCHARGE_INFO_ID = 1L;
  private static final Long TEST_PORT_ROTATION_ID = 1L;
  private static final Long TEST_VOYAGE_ID = 1L;
  private static final String SUCCESS = "SUCCESS";

  // API URLS
  private static final String CLOUD_API_URL_PREFIX = "/api/cloud";
  private static final String SHIP_API_URL_PREFIX = "/api/ship";

  private static final String DIS_INFO_GET_URL =
      "/vessels/{vesselId}/voyages/{voyageId}/loading-info/{infoId}/port-rotation/{portRotationId}";
  private static final String DIS_RULE_GET_URL =
      "/vessels/{vesselId}/discharge-info-rule/{dischargingInfoId}";
  private static final String DIS_ADD_INSTRUCTION = 
		  "/new-instruction/vessels/{vesselId}/discharging-info/{infoId}/port-rotation/{portRotationId}";
  private static final String DIS_UPDATE_INSTRUCTION = 
		  "/update-instruction/vessels/{vesselId}/discharging-info/{infoId}/port-rotation/{portRotationId}";
  private static final String DIS_EDIT_INSTRUCTION = 
		  "/edit-instruction/vessels/{vesselId}/discharging-info/{infoId}/port-rotation/{portRotationId}";
  private static final String DIS_GET_INSTRUCTION = 
		  "/vessels/{vesselId}/discharging-info/{infoId}/port-rotation/{portRotationId}";
  private static final String DIS_DELETE_INSTRUCTION = 
		  "/delete-instruction/vessels/{vesselId}/discharging-info/{infoId}/port-rotation/{portRotationId}";

  @Test
  public void contextLoads() throws Exception {
    assertThat(dischargeInformationService).isNotNull();
    assertThat(dischargingInstructionService).isNotNull();
  }

  @ValueSource(
      strings = {CLOUD_API_URL_PREFIX + DIS_INFO_GET_URL, SHIP_API_URL_PREFIX + DIS_INFO_GET_URL})
  @ParameterizedTest
  public void getDischargeInformationByPortRIdTestCase1(String url) throws Exception {
    when(this.dischargeInformationService.getDischargeInformation(anyLong(), anyLong(), anyLong()))
        .thenReturn(new DischargeInformation());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get(
                    url,
                    TEST_VESSEL_ID,
                    TEST_VOYAGE_ID,
                    TEST_DISCHARGE_INFO_ID,
                    TEST_PORT_ROTATION_ID)
                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE))
        .andExpect(status().isOk());
  }
  
  	  @ValueSource(
	      strings = {CLOUD_API_URL_PREFIX + DIS_ADD_INSTRUCTION, SHIP_API_URL_PREFIX + DIS_ADD_INSTRUCTION})
	  @ParameterizedTest
	  public void addDischargeInstructionsTestCase1(String url) throws Exception {
	  
		  DischargingInstructionsSaveRequest request = new DischargingInstructionsSaveRequest();
		  request.setInstruction("test");
		  request.setInstructionHeaderId(1L);
		  request.setInstructionTypeId(1L);
		  request.setIsChecked(false);
		  request.setIsSingleHeader(false);
		  request.setIsSubHeader(false);
		  request.setSubHeaderId(1L);
		  String inputJson = this.mapToJson(request);
		  when(this.dischargingInstructionService.
				  addDischargingInstruction(anyLong(), anyLong(), anyLong(), any(DischargingInstructionsSaveRequest.class)))
		  			.thenReturn(new DischargingInstructionsSaveResponse(new CommonSuccessResponse(SUCCESS, "")));
		    this.mockMvc
		        .perform(
		            MockMvcRequestBuilders.post(
		                    url,
		                    TEST_VESSEL_ID,
		                    TEST_DISCHARGE_INFO_ID,
		                    TEST_PORT_ROTATION_ID)
		            	.accept(MediaType.APPLICATION_JSON)
		            	.content(inputJson)
		            	.contentType(MediaType.APPLICATION_JSON)
		                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE))
		        .andExpect(status().isOk());
	  }
  
  	  @ValueSource(
  		     strings = {CLOUD_API_URL_PREFIX + DIS_UPDATE_INSTRUCTION, SHIP_API_URL_PREFIX + DIS_UPDATE_INSTRUCTION})
  	  @ParameterizedTest
  	  public void updateDischargeInstructionsTestCase1(String url) throws Exception {
  		  
  		  	DischargingInstructionsUpdateRequest request = new DischargingInstructionsUpdateRequest();
  		  	DischargingInstructionsStatus insideRequest = new DischargingInstructionsStatus();
  		  	insideRequest.setInstruction("test");
  		  	insideRequest.setInstructionId(1L);
  		  	List<DischargingInstructionsStatus> list = new ArrayList<>();
  		  	list.add(insideRequest);
  		  	insideRequest.setIsChecked(false);
  		  	request.setInstructionList(list);
  			  String inputJson = this.mapToJson(request);
  			  when(this.dischargingInstructionService.
  					  updateDischargingInstructions(anyLong(), anyLong(), anyLong(), any(DischargingInstructionsUpdateRequest.class)))
  			  			.thenReturn(new DischargingInstructionsSaveResponse(new CommonSuccessResponse(SUCCESS, "")));
  			    this.mockMvc
  			        .perform(
  			            MockMvcRequestBuilders.post(
  			                    url,
  			                    TEST_VESSEL_ID,
  			                    TEST_DISCHARGE_INFO_ID,
  			                    TEST_PORT_ROTATION_ID)
  			            	.accept(MediaType.APPLICATION_JSON)
  			            	.content(inputJson)
  			            	.contentType(MediaType.APPLICATION_JSON)
  			                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE))
  			        .andExpect(status().isOk());
  	  }
  	  
  	  @ValueSource(
   		     strings = {CLOUD_API_URL_PREFIX + DIS_EDIT_INSTRUCTION, SHIP_API_URL_PREFIX + DIS_EDIT_INSTRUCTION})
   	  @ParameterizedTest
   	  public void editDischargeInstructionsTestCase1(String url) throws Exception {
   		  
   		  	DischargingInstructionsStatus insideRequest = new DischargingInstructionsStatus();
   		  	insideRequest.setInstruction("test");
   		  	insideRequest.setInstructionId(1L);
   			  String inputJson = this.mapToJson(insideRequest);
   			  when(this.dischargingInstructionService.
   					  editDischargingInstructions(anyLong(), anyLong(), anyLong(), any(DischargingInstructionsStatus.class)))
   			  			.thenReturn(new DischargingInstructionsSaveResponse(new CommonSuccessResponse(SUCCESS, "")));
   			    this.mockMvc
   			        .perform(
   			            MockMvcRequestBuilders.post(
   			                    url,
   			                    TEST_VESSEL_ID,
   			                    TEST_DISCHARGE_INFO_ID,
   			                    TEST_PORT_ROTATION_ID)
   			            	.accept(MediaType.APPLICATION_JSON)
   			            	.content(inputJson)
   			            	.contentType(MediaType.APPLICATION_JSON)
   			                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE))
   			        .andExpect(status().isOk());
   	  }
  	  
  	  @ValueSource(
    		     strings = {CLOUD_API_URL_PREFIX + DIS_GET_INSTRUCTION, SHIP_API_URL_PREFIX + DIS_GET_INSTRUCTION})
  	  @ParameterizedTest
  	  public void getDischargeInstructionsTestCase1(String url) throws Exception {
    			  when(this.dischargingInstructionService.
    					  getDischargingInstructions(anyLong(), anyLong(), anyLong()))
    			  			.thenReturn(new DischargingInstructionResponse());
    			  this.mockMvc
    		        .perform(
    		            MockMvcRequestBuilders.get(
    		                    url,
    		                    TEST_VESSEL_ID,
    		                    TEST_DISCHARGE_INFO_ID,
    		                    TEST_PORT_ROTATION_ID)
    		                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE))
    		        .andExpect(status().isOk());
  	  }
  	  
  	  @ValueSource(
    		     strings = {CLOUD_API_URL_PREFIX + DIS_DELETE_INSTRUCTION, SHIP_API_URL_PREFIX + DIS_DELETE_INSTRUCTION})
    	  @ParameterizedTest
    	  public void deleteDischargeInstructionsTestCase1(String url) throws Exception {
    		  
    		  	DischargingInstructionsStatus insideRequest = new DischargingInstructionsStatus();
    		  	insideRequest.setInstruction("test");
    		  	insideRequest.setInstructionId(1L);
    			  String inputJson = this.mapToJson(insideRequest);
    			  when(this.dischargingInstructionService.
    					  deleteDischargingInstructions(anyLong(), anyLong(), anyLong(), any(DischargingInstructionsStatus.class)))
    			  			.thenReturn(new DischargingInstructionsSaveResponse(new CommonSuccessResponse(SUCCESS, "")));
    			    this.mockMvc
    			        .perform(
    			            MockMvcRequestBuilders.post(
    			                    url,
    			                    TEST_VESSEL_ID,
    			                    TEST_DISCHARGE_INFO_ID,
    			                    TEST_PORT_ROTATION_ID)
    			            	.accept(MediaType.APPLICATION_JSON)
    			            	.content(inputJson)
    			            	.contentType(MediaType.APPLICATION_JSON)
    			                .header(CORRELATION_ID_HEADER, CORRELATION_ID_HEADER_VALUE))
    			        .andExpect(status().isOk());
    	  }
  	  
  	  
  	private String mapToJson(Object object) throws JsonProcessingException {
  		ObjectMapper objectMapper = new ObjectMapper();
  		return objectMapper.writeValueAsString(object);
  	}
}
