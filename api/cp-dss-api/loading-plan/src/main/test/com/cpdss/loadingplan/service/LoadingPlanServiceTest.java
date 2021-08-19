package com.cpdss.loadingplan.service;

import com.cpdss.loadingplan.repository.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * @Author ravi.r
 *
 */

@SpringJUnitConfig(classes = {LoadingPlanService.class})
class LoadingPlanServiceTest {

    @MockBean BillOfLandingRepository billOfLandingRepository;
    @MockBean PortLoadingPlanStowageTempDetailsRepository loadingPlanStowageDetailsTempRepository;
    @MockBean PortLoadingPlanBallastTempDetailsRepository loadingPlanBallastDetailsTempRepository;
    @MockBean PortLoadingPlanStowageDetailsRepository loadingPlanStowageDetailsRepository;
    @MockBean PortLoadingPlanBallastDetailsRepository loadingPlanBallastDetailsRepository;
    @MockBean PortLoadingPlanRobDetailsRepository loadingPlanRobDetailsRepository;

    @Test
    public void testullageUpdate() throws GenericServiceException {
        Mockito.when(this.billOfLandingRepository.updateBillOfLandingRepository(
                        billOfLanding.getBlRefNumber(),
                        BigDecimal.valueOf(billOfLanding.getBblAt60F()),
                        BigDecimal.valueOf(billOfLanding.getQuantityLt()),
                        BigDecimal.valueOf(billOfLanding.getQuantityMt()),
                        BigDecimal.valueOf(billOfLanding.getKlAt15C()),
                        BigDecimal.valueOf(billOfLanding.getApi()),
                        BigDecimal.valueOf(billOfLanding.getTemperature()),
                        Integer.valueOf(billOfLanding.getCargoId() + ""),
                        Integer.valueOf(billOfLanding.getPortId() + "")))
                .thenReturn(new StatusReply());

        StreamRecorder<UllageUpdate> responseObserver = StreamRecorder.create();
        UllageUpdateReply update = new UllageUpdateReply();
        assertNull(responseObserver.getError());

        assertEquals(
                StatusReply.newBuilder().setStatus(SUCCESS).setMessage(SUCCESS).build(),
                response.getResponseStatus());

    }

    @Test
    public void testBillLanding() throws GenericServiceException {
        Mockito.when(billOfLandingRepository.deleteBillOfLandingRepository(
                Boolean.valueOf(billOfLanding.getIsActive() == 0 ? false : true),
                Integer.valueOf(billOfLanding.getCargoId() + ""),
                Integer.valueOf(billOfLanding.getPortId() + ""));)
                .thenReturn(new StatusReply());

        StreamRecorder<UllageUpdate> responseObserver = StreamRecorder.create();
        UllageUpdateReply update = new UllageUpdateReply();
        assertNull(responseObserver.getError());

        assertEquals(
                StatusReply.newBuilder().setStatus(SUCCESS).setMessage(SUCCESS).build(),
                response.getResponseStatus());

    }

    @Test
    public void testullageUpdateRob() throws GenericServiceException {
        Mockito.when(loadingPlanRobDetailsRepository.updatePortLoadingPlanRobDetailsRepository(
                BigDecimal.valueOf(ullageInsert.getQuantity()),
                BigDecimal.valueOf(ullageInsert.getQuantity()),
                Long.valueOf(ullageInsert.getTankId()),
                true))
                .thenReturn(new StatusReply());

        StreamRecorder<UllageUpdate> responseObserver = StreamRecorder.create();
        UllageUpdateReply update = new UllageUpdateReply();
        assertNull(responseObserver.getError());

        assertEquals(
                StatusReply.newBuilder().setStatus(SUCCESS).setMessage(SUCCESS).build(),
                response.getResponseStatus());
    }

    @Test
    public void testullageUpdateRobSave() throws GenericServiceException {
        Mockito.when(loadingPlanRobDetailsRepository.save(new PortLoadingPlanRobDetails()))
                .thenReturn(new StatusReply());

        StreamRecorder<UllageUpdate> responseObserver = StreamRecorder.create();
        UllageUpdateReply update = new UllageUpdateReply();
        assertNull(responseObserver.getError());

        assertEquals(
                StatusReply.newBuilder().setStatus(SUCCESS).setMessage(SUCCESS).build(),
                response.getResponseStatus());
    }

    @Test
    public void testullageUpdateBallast() throws GenericServiceException {
        Mockito.when(
                loadingPlanBallastDetailsTempRepository
                        .updateLoadingPlanBallastDetailsRepository(
                                BigDecimal.valueOf(ullageInsert.getQuantity()),
                                BigDecimal.valueOf(ullageInsert.getSounding()),
                                BigDecimal.valueOf(ullageInsert.getQuantity()),
                                Long.valueOf(ullageInsert.getTankId()),
                                true,
                                Long.valueOf(ullageInsert.getTankId())))
                .thenReturn(new StatusReply());

        StreamRecorder<UllageUpdate> responseObserver = StreamRecorder.create();
        UllageUpdateReply update = new UllageUpdateReply();
        assertNull(responseObserver.getError());

        assertEquals(
                StatusReply.newBuilder().setStatus(SUCCESS).setMessage(SUCCESS).build(),
                response.getResponseStatus());
    }

    @Test
    public void testullageUpdateBallastSave() throws GenericServiceException {
        Mockito.when(   loadingPlanBallastDetailsTempRepository.save(
                new PortLoadingPlanBallastTempDetails()))
                .thenReturn(new StatusReply());

        StreamRecorder<UllageUpdate> responseObserver = StreamRecorder.create();
        UllageUpdateReply update = new UllageUpdateReply();
        assertNull(responseObserver.getError());

        assertEquals(
                StatusReply.newBuilder().setStatus(SUCCESS).setMessage(SUCCESS).build(),
                response.getResponseStatus());
    }


    @Test
    public void testullageUpdateStowage() throws GenericServiceException {
        Mockito.when(loadingPlanStowageDetailsTempRepository
                        .updatePortLoadingPlanStowageDetailsRepository(
                                BigDecimal.valueOf(ullageInsert.getQuantity()),
                                BigDecimal.valueOf(ullageInsert.getCorrectedUllage()),
                                BigDecimal.valueOf(ullageInsert.getQuantity()),
                                BigDecimal.valueOf(Long.parseLong(ullageInsert.getApi() + "")),
                                BigDecimal.valueOf(Long.parseLong(ullageInsert.getTemperature() + "")),
                                Long.valueOf(ullageInsert.getTankId()),
                                true,
                                Long.valueOf(ullageInsert.getTankId())))
                .thenReturn(new StatusReply());

        StreamRecorder<UllageUpdate> responseObserver = StreamRecorder.create();
        UllageUpdateReply update = new UllageUpdateReply();
        assertNull(responseObserver.getError());

        assertEquals(
                StatusReply.newBuilder().setStatus(SUCCESS).setMessage(SUCCESS).build(),
                response.getResponseStatus());

    }

    @Test
    public void testullageUpdateStowageSave() throws GenericServiceException {
        Mockito.when( loadingPlanStowageDetailsTempRepository.save(
                        new PortLoadingPlanStowageTempDetails()))
                .thenReturn(new StatusReply());

        StreamRecorder<UllageUpdate> responseObserver = StreamRecorder.create();
        UllageUpdateReply update = new UllageUpdateReply();
        assertNull(responseObserver.getError());

        assertEquals(
                StatusReply.newBuilder().setStatus(SUCCESS).setMessage(SUCCESS).build(),
                response.getResponseStatus());

    }

    }
