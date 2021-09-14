package com.cpdss.gateway.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.discharge_plan.DischargingInstructionServiceGrpc.DischargingInstructionServiceBlockingStub;
import com.cpdss.common.generated.discharge_plan.DischargingInstructionStatus;
import com.cpdss.common.generated.discharge_plan.DischargingInstructionsSave;
import com.cpdss.common.generated.discharge_plan.DischargingInstructionsUpdate;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.dischargeplan.DischargingInstructionsSaveRequest;
import com.cpdss.gateway.domain.dischargeplan.DischargingInstructionsSaveResponse;
import com.cpdss.gateway.domain.dischargeplan.DischargingInstructionsStatus;
import com.cpdss.gateway.domain.dischargeplan.DischargingInstructionsUpdateRequest;
import com.cpdss.gateway.repository.UsersRepository;
import com.cpdss.gateway.service.dischargeplan.DischargingInstructionService;

@SpringJUnitConfig(classes = {DischargingInstructionService.class})
public class DischargingInstructionServiceTest {
	
	  private DischargingInstructionService dischargingInstructionService;

	  private static final Long TEST_VESSEL_ID = 1L;

	  private static final String SUCCESS = "SUCCESS";
	  private static final String FAILED = "FAILED";

	  private static final Long TEST_DISCHARGING_INFO_ID = 1L;
	  private static final Long TEST_PORT_ROTATION_ID = 1L;

	  @MockBean private UsersRepository usersRepository;
	  @MockBean private DischargingInstructionServiceBlockingStub dischargingInstructionServiceBlockingStub;

	  @BeforeEach
	  public void init() {
	    this.dischargingInstructionService = Mockito.mock(DischargingInstructionService.class);
	  }

	  /**
	   * Discharging instruction- add new instruction
	   *
	   * @throws GenericServiceException
	   */
	  @Test
	  void testAddnewInstruction() throws GenericServiceException {
	    Mockito.when(
	            this.dischargingInstructionService.addDischargingInstruction(
	                anyLong(), anyLong(), anyLong(), any(DischargingInstructionsSaveRequest.class)))
	        .thenCallRealMethod();
	    ResponseStatus replyBuilder = ResponseStatus.newBuilder().setStatus(SUCCESS).build();
	    Mockito.when(
	            this.dischargingInstructionServiceBlockingStub.addDischargingInstruction(
	                ArgumentMatchers.any(DischargingInstructionsSave.class)))
	        .thenReturn(replyBuilder);
	    DischargingInstructionsSaveResponse response =
	        this.dischargingInstructionService.addDischargingInstruction(
	            TEST_VESSEL_ID,
	            TEST_DISCHARGING_INFO_ID,
	            TEST_PORT_ROTATION_ID,
	            new DischargingInstructionsSaveRequest());

	    assertEquals(SUCCESS, response.getResponseStatus().getStatus(), "SUCCESS");
	  }

	  /**
	   * Discharging instruction- add new instruction - Exception
	   *
	   * @throws GenericServiceException
	   */
	  @Test
	  void testAddnewInstructionException() throws GenericServiceException {
	    Mockito.when(
	            this.dischargingInstructionService.addDischargingInstruction(
	                anyLong(), anyLong(), anyLong(), any(DischargingInstructionsSaveRequest.class)))
	        .thenCallRealMethod();
	    ResponseStatus replyBuilder = ResponseStatus.newBuilder().setStatus(FAILED).build();
	    Mockito.when(
	            this.dischargingInstructionServiceBlockingStub.addDischargingInstruction(
	                ArgumentMatchers.any(DischargingInstructionsSave.class)))
	        .thenReturn(replyBuilder);

	    final GenericServiceException ex =
	        assertThrows(
	            GenericServiceException.class,
	            () ->
	                this.dischargingInstructionService.addDischargingInstruction(
	                    TEST_VESSEL_ID,
	                    TEST_DISCHARGING_INFO_ID,
	                    TEST_PORT_ROTATION_ID,
	                    new DischargingInstructionsSaveRequest()));

	    assertAll(
	        () -> assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), "Invalid error code"),
	        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
	  }

	  /**
	   * Discharging instruction- Update instruction
	   *
	   * @throws GenericServiceException
	   */
	  @Test
	  void testUpdateInstructions() throws GenericServiceException {
	    Mockito.when(
	            this.dischargingInstructionService.updateDischargingInstructions(
	                anyLong(), anyLong(), anyLong(), any(DischargingInstructionsUpdateRequest.class)))
	        .thenCallRealMethod();
	    ResponseStatus replyBuilder = ResponseStatus.newBuilder().setStatus(SUCCESS).build();
	    Mockito.when(
	            this.dischargingInstructionServiceBlockingStub.updateDischargingInstructions(
	                ArgumentMatchers.any(DischargingInstructionsUpdate.class)))
	        .thenReturn(replyBuilder);
	    DischargingInstructionsSaveResponse response =
	        this.dischargingInstructionService.updateDischargingInstructions(
	            TEST_VESSEL_ID,
	            TEST_DISCHARGING_INFO_ID,
	            TEST_PORT_ROTATION_ID,
	            new DischargingInstructionsUpdateRequest());

	    assertEquals(SUCCESS, response.getResponseStatus().getStatus(), "SUCCESS");
	  }

	  /**
	   * Discharging instruction- Update instruction - Exception
	   *
	   * @throws GenericServiceException
	   */
	  @Test
	  void testUpdateInstructionsException() throws GenericServiceException {
	    Mockito.when(
	            this.dischargingInstructionService.updateDischargingInstructions(
	                anyLong(), anyLong(), anyLong(), any(DischargingInstructionsUpdateRequest.class)))
	        .thenCallRealMethod();
	    ResponseStatus replyBuilder = ResponseStatus.newBuilder().setStatus(FAILED).build();
	    Mockito.when(
	            this.dischargingInstructionServiceBlockingStub.updateDischargingInstructions(
	                ArgumentMatchers.any(DischargingInstructionsUpdate.class)))
	        .thenReturn(replyBuilder);

	    final GenericServiceException ex =
	        assertThrows(
	            GenericServiceException.class,
	            () ->
	                this.dischargingInstructionService.updateDischargingInstructions(
	                    TEST_VESSEL_ID,
	                    TEST_DISCHARGING_INFO_ID,
	                    TEST_PORT_ROTATION_ID,
	                    new DischargingInstructionsUpdateRequest()));

	    assertAll(
	        () -> assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), "Invalid error code"),
	        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
	  }

	  /**
	   * Discharging instruction- Edit instruction
	   *
	   * @throws GenericServiceException
	   */
	  @Test
	  void testEditInstructions() throws GenericServiceException {
	    Mockito.when(
	            this.dischargingInstructionService.editDischargingInstructions(
	                anyLong(), anyLong(), anyLong(), any(DischargingInstructionsStatus.class)))
	        .thenCallRealMethod();
	    ResponseStatus replyBuilder = ResponseStatus.newBuilder().setStatus(SUCCESS).build();
	    Mockito.when(
	            this.dischargingInstructionServiceBlockingStub.editInstructions(
	                ArgumentMatchers.any(DischargingInstructionStatus.class)))
	        .thenReturn(replyBuilder);
	    DischargingInstructionsSaveResponse response =
	        this.dischargingInstructionService.editDischargingInstructions(
	            TEST_VESSEL_ID,
	            TEST_DISCHARGING_INFO_ID,
	            TEST_PORT_ROTATION_ID,
	            new DischargingInstructionsStatus());

	    assertEquals(SUCCESS, response.getResponseStatus().getStatus(), "SUCCESS");
	  }

	  /**
	   * Discharging instruction- Edit instruction - Exception
	   *
	   * @throws GenericServiceException
	   */
	  @Test
	  void testEditInstructionsException() throws GenericServiceException {
	    Mockito.when(
	            this.dischargingInstructionService.editDischargingInstructions(
	                anyLong(), anyLong(), anyLong(), any(DischargingInstructionsStatus.class)))
	        .thenCallRealMethod();
	    ResponseStatus replyBuilder = ResponseStatus.newBuilder().setStatus(FAILED).build();
	    Mockito.when(
	            this.dischargingInstructionServiceBlockingStub.editInstructions(
	                ArgumentMatchers.any(DischargingInstructionStatus.class)))
	        .thenReturn(replyBuilder);

	    final GenericServiceException ex =
	        assertThrows(
	            GenericServiceException.class,
	            () ->
	                this.dischargingInstructionService.editDischargingInstructions(
	                    TEST_VESSEL_ID,
	                    TEST_DISCHARGING_INFO_ID,
	                    TEST_PORT_ROTATION_ID,
	                    new DischargingInstructionsStatus()));

	    assertAll(
	        () -> assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), "Invalid error code"),
	        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
	  }

	  /**
	   * Discharging instruction- Delete instruction
	   *
	   * @throws GenericServiceException
	   */
	  @Test
	  void testDeleteInstructions() throws GenericServiceException {
	    Mockito.when(
	            this.dischargingInstructionService.deleteDischargingInstructions(
	                anyLong(), anyLong(), anyLong(), any(DischargingInstructionsStatus.class)))
	        .thenCallRealMethod();
	    ResponseStatus replyBuilder = ResponseStatus.newBuilder().setStatus(SUCCESS).build();
	    Mockito.when(
	            this.dischargingInstructionServiceBlockingStub.deleteDischargingInstructions(
	                ArgumentMatchers.any(DischargingInstructionStatus.class)))
	        .thenReturn(replyBuilder);
	    DischargingInstructionsSaveResponse response =
	        this.dischargingInstructionService.deleteDischargingInstructions(
	            TEST_VESSEL_ID,
	            TEST_DISCHARGING_INFO_ID,
	            TEST_PORT_ROTATION_ID,
	            new DischargingInstructionsStatus());

	    assertEquals(SUCCESS, response.getResponseStatus().getStatus(), "SUCCESS");
	  }

	  /**
	   * Discharging instruction- Delete instruction - Exception
	   *
	   * @throws GenericServiceException
	   */
	  @Test
	  void testDeleteInstructionsException() throws GenericServiceException {
	    Mockito.when(
	            this.dischargingInstructionService.deleteDischargingInstructions(
	                anyLong(), anyLong(), anyLong(), any(DischargingInstructionsStatus.class)))
	        .thenCallRealMethod();
	    ResponseStatus replyBuilder = ResponseStatus.newBuilder().setStatus(FAILED).build();
	    Mockito.when(
	            this.dischargingInstructionServiceBlockingStub.deleteDischargingInstructions(
	                ArgumentMatchers.any(DischargingInstructionStatus.class)))
	        .thenReturn(replyBuilder);

	    final GenericServiceException ex =
	        assertThrows(
	            GenericServiceException.class,
	            () ->
	                this.dischargingInstructionService.deleteDischargingInstructions(
	                    TEST_VESSEL_ID,
	                    TEST_DISCHARGING_INFO_ID,
	                    TEST_PORT_ROTATION_ID,
	                    new DischargingInstructionsStatus()));

	    assertAll(
	        () -> assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), "Invalid error code"),
	        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
	  }

}
