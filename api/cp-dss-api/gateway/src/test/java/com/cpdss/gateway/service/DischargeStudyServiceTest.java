/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.LoadableStudy.AlgoReply;
import com.cpdss.common.generated.LoadableStudy.AlgoRequest;
import com.cpdss.common.generated.loading_plan.LoadingInstructionServiceGrpc.LoadingInstructionServiceBlockingStub;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionStatus;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionsSave;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionsUpdate;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.AlgoPatternResponse;
import com.cpdss.gateway.domain.loadingplan.LoadingInstructionsSaveRequest;
import com.cpdss.gateway.domain.loadingplan.LoadingInstructionsSaveResponse;
import com.cpdss.gateway.domain.loadingplan.LoadingInstructionsStatus;
import com.cpdss.gateway.domain.loadingplan.LoadingInstructionsUpdateRequest;
import com.cpdss.gateway.repository.UsersRepository;
import com.cpdss.gateway.service.loadingplan.impl.LoadingInstructionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * Test class for {@link DischargeStudyService}
 *
 * @author Sanal
 */
@SpringJUnitConfig(classes = { DischargeStudyService.class })
class DischargeStudyServiceTest {

	private DischargeStudyService dischargeStudyService;

	private static final Long TEST_VESSEL_ID = 1L;

	private static final String SUCCESS = "SUCCESS";
	private static final String FAILED = "FAILED";

	private static final Long TEST_LOADING_INFO_ID = 1L;
	private static final Long TEST_PORT_ROTATION_ID = 1L;

	@MockBean
	private UsersRepository usersRepository;
	@MockBean
	private LoadingInstructionServiceBlockingStub loadingInstructionServiceBlockingStub;

	@BeforeEach
	public void init() {
		this.dischargeStudyService = Mockito.mock(DischargeStudyService.class);
	}

	/**
	 * Generate Discharge pattern
	 *
	 * @throws GenericServiceException
	 */
	@Test
	void testGenerateDischargePatterns() throws GenericServiceException {
		Mockito.when(this.dischargeStudyService.generateDischargePatterns(anyLong(), anyLong(), anyLong(),
				any(String.class))).thenCallRealMethod();
		AlgoReply replyBuilder = AlgoReply.newBuilder()
				.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build()).build();
		Mockito.when(this.dischargeStudyService.generateDischargePatterns(ArgumentMatchers.any(AlgoRequest.class)))
				.thenReturn(replyBuilder);
		AlgoPatternResponse response = this.dischargeStudyService.generateDischargePatterns(TEST_VESSEL_ID,
				TEST_LOADING_INFO_ID, TEST_PORT_ROTATION_ID, "");

		assertEquals(SUCCESS, response.getResponseStatus().getStatus(), String.valueOf(HttpStatus.OK.value()));
	}

	

	/**
	 * Generate Discharge pattern - Exception
	 *
	 * @throws GenericServiceException
	 */
	@Test
	void testGenerateDischargePatternsException() throws GenericServiceException {
		Mockito.when(this.dischargeStudyService.generateDischargePatterns(anyLong(), anyLong(), anyLong(),
				any(String.class))).thenCallRealMethod();
		AlgoReply replyBuilder = AlgoReply.newBuilder()
				.setResponseStatus(ResponseStatus.newBuilder().setStatus(FAILED).build()).build();
		Mockito.when(this.dischargeStudyService.generateDischargePatterns(ArgumentMatchers.any(AlgoRequest.class)))
				.thenReturn(replyBuilder);
		assertThrows(GenericServiceException.class,
				() -> this.dischargeStudyService.generateDischargePatterns(TEST_VESSEL_ID,
				TEST_LOADING_INFO_ID, TEST_PORT_ROTATION_ID, ""));

	}
}
