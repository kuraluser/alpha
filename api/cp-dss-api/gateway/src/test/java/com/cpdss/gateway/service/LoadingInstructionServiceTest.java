/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.loading_plan.LoadingInstructionServiceGrpc.LoadingInstructionServiceBlockingStub;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionStatus;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionsSave;
import com.cpdss.common.generated.loading_plan.LoadingPlanModels.LoadingInstructionsUpdate;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
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
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * Test class for {@link LoadingInstructionService}
 *
 * @author suhail.k
 */
@SpringJUnitConfig(classes = {LoadingInstructionService.class})
class LoadingInstructionServiceTest {

  private LoadingInstructionService loadingInstructionService;

  private static final Long TEST_VESSEL_ID = 1L;

  private static final String SUCCESS = "SUCCESS";
  private static final String FAILED = "FAILED";

  private static final Long TEST_LOADING_INFO_ID = 1L;
  private static final Long TEST_PORT_ROTATION_ID = 1L;

  @MockBean private UsersRepository usersRepository;
  @MockBean private LoadingInstructionServiceBlockingStub loadingInstructionServiceBlockingStub;

  @BeforeEach
  public void init() {
    this.loadingInstructionService = Mockito.mock(LoadingInstructionService.class);
  }

  /**
   * Loading instruction- add new instruction
   *
   * @throws GenericServiceException
   */
  @Test
  void testAddnewInstruction() throws GenericServiceException {
    Mockito.when(
            this.loadingInstructionService.addLoadingInstruction(
                anyLong(), anyLong(), anyLong(), any(LoadingInstructionsSaveRequest.class)))
        .thenCallRealMethod();
    ResponseStatus replyBuilder = ResponseStatus.newBuilder().setStatus(SUCCESS).build();
    Mockito.when(
            this.loadingInstructionServiceBlockingStub.addLoadingInstruction(
                ArgumentMatchers.any(LoadingInstructionsSave.class)))
        .thenReturn(replyBuilder);
    LoadingInstructionsSaveResponse response =
        this.loadingInstructionService.addLoadingInstruction(
            TEST_VESSEL_ID,
            TEST_LOADING_INFO_ID,
            TEST_PORT_ROTATION_ID,
            new LoadingInstructionsSaveRequest());

    assertEquals(SUCCESS, response.getResponseStatus().getStatus(), "Invalid response status");
  }

  /**
   * Loading instruction- add new instruction - Exception
   *
   * @throws GenericServiceException
   */
  @Test
  void testAddnewInstructionException() throws GenericServiceException {
    Mockito.when(
            this.loadingInstructionService.addLoadingInstruction(
                anyLong(), anyLong(), anyLong(), any(LoadingInstructionsSaveRequest.class)))
        .thenCallRealMethod();
    ResponseStatus replyBuilder = ResponseStatus.newBuilder().setStatus(FAILED).build();
    Mockito.when(
            this.loadingInstructionServiceBlockingStub.addLoadingInstruction(
                ArgumentMatchers.any(LoadingInstructionsSave.class)))
        .thenReturn(replyBuilder);

    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () ->
                this.loadingInstructionService.addLoadingInstruction(
                    TEST_VESSEL_ID,
                    TEST_LOADING_INFO_ID,
                    TEST_PORT_ROTATION_ID,
                    new LoadingInstructionsSaveRequest()));

    assertAll(
        () -> assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), "Invalid error code"),
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
  }

  /**
   * Loading instruction- Update instruction
   *
   * @throws GenericServiceException
   */
  @Test
  void testUpdateInstructions() throws GenericServiceException {
    Mockito.when(
            this.loadingInstructionService.updateLoadingInstructions(
                anyLong(), anyLong(), anyLong(), any(LoadingInstructionsUpdateRequest.class)))
        .thenCallRealMethod();
    ResponseStatus replyBuilder = ResponseStatus.newBuilder().setStatus(SUCCESS).build();
    Mockito.when(
            this.loadingInstructionServiceBlockingStub.updateLoadingInstructions(
                ArgumentMatchers.any(LoadingInstructionsUpdate.class)))
        .thenReturn(replyBuilder);
    LoadingInstructionsSaveResponse response =
        this.loadingInstructionService.updateLoadingInstructions(
            TEST_VESSEL_ID,
            TEST_LOADING_INFO_ID,
            TEST_PORT_ROTATION_ID,
            new LoadingInstructionsUpdateRequest());

    assertEquals(SUCCESS, response.getResponseStatus().getStatus(), "Invalid response status");
  }

  /**
   * Loading instruction- Update instruction - Exception
   *
   * @throws GenericServiceException
   */
  @Test
  void testUpdateInstructionsException() throws GenericServiceException {
    Mockito.when(
            this.loadingInstructionService.updateLoadingInstructions(
                anyLong(), anyLong(), anyLong(), any(LoadingInstructionsUpdateRequest.class)))
        .thenCallRealMethod();
    ResponseStatus replyBuilder = ResponseStatus.newBuilder().setStatus(FAILED).build();
    Mockito.when(
            this.loadingInstructionServiceBlockingStub.updateLoadingInstructions(
                ArgumentMatchers.any(LoadingInstructionsUpdate.class)))
        .thenReturn(replyBuilder);

    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () ->
                this.loadingInstructionService.updateLoadingInstructions(
                    TEST_VESSEL_ID,
                    TEST_LOADING_INFO_ID,
                    TEST_PORT_ROTATION_ID,
                    new LoadingInstructionsUpdateRequest()));

    assertAll(
        () -> assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), "Invalid error code"),
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
  }

  /**
   * Loading instruction- Edit instruction
   *
   * @throws GenericServiceException
   */
  @Test
  void testEditInstructions() throws GenericServiceException {
    Mockito.when(
            this.loadingInstructionService.editLoadingInstructions(
                anyLong(), anyLong(), anyLong(), any(LoadingInstructionsStatus.class)))
        .thenCallRealMethod();
    ResponseStatus replyBuilder = ResponseStatus.newBuilder().setStatus(SUCCESS).build();
    Mockito.when(
            this.loadingInstructionServiceBlockingStub.editInstructions(
                ArgumentMatchers.any(LoadingInstructionStatus.class)))
        .thenReturn(replyBuilder);
    LoadingInstructionsSaveResponse response =
        this.loadingInstructionService.editLoadingInstructions(
            TEST_VESSEL_ID,
            TEST_LOADING_INFO_ID,
            TEST_PORT_ROTATION_ID,
            new LoadingInstructionsStatus());

    assertEquals(SUCCESS, response.getResponseStatus().getStatus(), "Invalid response status");
  }

  /**
   * Loading instruction- Edit instruction - Exception
   *
   * @throws GenericServiceException
   */
  @Test
  void testEditInstructionsException() throws GenericServiceException {
    Mockito.when(
            this.loadingInstructionService.editLoadingInstructions(
                anyLong(), anyLong(), anyLong(), any(LoadingInstructionsStatus.class)))
        .thenCallRealMethod();
    ResponseStatus replyBuilder = ResponseStatus.newBuilder().setStatus(FAILED).build();
    Mockito.when(
            this.loadingInstructionServiceBlockingStub.editInstructions(
                ArgumentMatchers.any(LoadingInstructionStatus.class)))
        .thenReturn(replyBuilder);

    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () ->
                this.loadingInstructionService.editLoadingInstructions(
                    TEST_VESSEL_ID,
                    TEST_LOADING_INFO_ID,
                    TEST_PORT_ROTATION_ID,
                    new LoadingInstructionsStatus()));

    assertAll(
        () -> assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), "Invalid error code"),
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
  }

  /**
   * Loading instruction- Delete instruction
   *
   * @throws GenericServiceException
   */
  @Test
  void testDeleteInstructions() throws GenericServiceException {
    Mockito.when(
            this.loadingInstructionService.deleteLoadingInstructions(
                anyLong(), anyLong(), anyLong(), any(LoadingInstructionsStatus.class)))
        .thenCallRealMethod();
    ResponseStatus replyBuilder = ResponseStatus.newBuilder().setStatus(SUCCESS).build();
    Mockito.when(
            this.loadingInstructionServiceBlockingStub.deleteLoadingInstructions(
                ArgumentMatchers.any(LoadingInstructionStatus.class)))
        .thenReturn(replyBuilder);
    LoadingInstructionsSaveResponse response =
        this.loadingInstructionService.deleteLoadingInstructions(
            TEST_VESSEL_ID,
            TEST_LOADING_INFO_ID,
            TEST_PORT_ROTATION_ID,
            new LoadingInstructionsStatus());

    assertEquals(SUCCESS, response.getResponseStatus().getStatus(), "Invalid response status");
  }

  /**
   * Loading instruction- Delete instruction - Exception
   *
   * @throws GenericServiceException
   */
  @Test
  void testDeleteInstructionsException() throws GenericServiceException {
    Mockito.when(
            this.loadingInstructionService.deleteLoadingInstructions(
                anyLong(), anyLong(), anyLong(), any(LoadingInstructionsStatus.class)))
        .thenCallRealMethod();
    ResponseStatus replyBuilder = ResponseStatus.newBuilder().setStatus(FAILED).build();
    Mockito.when(
            this.loadingInstructionServiceBlockingStub.deleteLoadingInstructions(
                ArgumentMatchers.any(LoadingInstructionStatus.class)))
        .thenReturn(replyBuilder);

    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () ->
                this.loadingInstructionService.deleteLoadingInstructions(
                    TEST_VESSEL_ID,
                    TEST_LOADING_INFO_ID,
                    TEST_PORT_ROTATION_ID,
                    new LoadingInstructionsStatus()));

    assertAll(
        () -> assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), "Invalid error code"),
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
  }
}
