/* Licensed under Apache-2.0 */
package com.cpdss.gateway.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.VesselInfo.BMAndSF;
import com.cpdss.common.generated.VesselInfo.BendingMoment;
import com.cpdss.common.generated.VesselInfo.CalculationSheet;
import com.cpdss.common.generated.VesselInfo.CalculationSheetTankGroup;
import com.cpdss.common.generated.VesselInfo.HydrostaticData;
import com.cpdss.common.generated.VesselInfo.InnerBulkHeadSF;
import com.cpdss.common.generated.VesselInfo.MinMaxValuesForBMAndSf;
import com.cpdss.common.generated.VesselInfo.ShearingForce;
import com.cpdss.common.generated.VesselInfo.StationValues;
import com.cpdss.common.generated.VesselInfo.VesselAlgoReply;
import com.cpdss.common.generated.VesselInfo.VesselAlgoRequest;
import com.cpdss.common.generated.VesselInfo.VesselDraftCondition;
import com.cpdss.common.generated.VesselInfo.VesselTankDetail;
import com.cpdss.common.generated.VesselInfo.VesselTankTCG;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.VesselDetailsResponse;
import com.cpdss.gateway.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/** @Author jerin.g */
@SpringJUnitConfig(classes = {VesselInfoService.class})
public class VesselInfoServiceTest {

  private VesselInfoService vesselInfoService;
  @MockBean private UsersRepository usersRepository;

  private static final String SUCCESS = "SUCCESS";
  private static final String FAILED = "FAILED";
  private static final String CORRELATION_ID_HEADER_VALUE = "1234";

  @BeforeEach
  public void init() {
    this.vesselInfoService = Mockito.mock(VesselInfoService.class);
  }

  @Test
  void testGetVesselsDetails() throws GenericServiceException {
    Mockito.when(this.vesselInfoService.getVesselsDetails(anyLong(), anyString()))
        .thenCallRealMethod();
    VesselDraftCondition.Builder vesselDraftConditionBuilder = VesselDraftCondition.newBuilder();
    VesselTankDetail.Builder vesselTankBuilder = VesselTankDetail.newBuilder();
    HydrostaticData.Builder hydrostaticDataBuilder = HydrostaticData.newBuilder();
    VesselTankTCG.Builder vesselTankTcgBuilder = VesselTankTCG.newBuilder();
    BMAndSF.Builder bMAndSFBuilder = BMAndSF.newBuilder();
    BendingMoment.Builder bendingMomentBuilder = BendingMoment.newBuilder();
    ShearingForce.Builder shearingForceBuilder = ShearingForce.newBuilder();
    CalculationSheet.Builder calculationSheetBuilder = CalculationSheet.newBuilder();
    MinMaxValuesForBMAndSf.Builder minMaxValuesForBMAndSfBuilder =
        MinMaxValuesForBMAndSf.newBuilder();
    CalculationSheetTankGroup.Builder calculationSheetTankGroupBuilder =
        CalculationSheetTankGroup.newBuilder();
    StationValues.Builder stationValueBuilder = StationValues.newBuilder();
    InnerBulkHeadSF.Builder innerBulkHeadSFBuilder = InnerBulkHeadSF.newBuilder();
    bMAndSFBuilder.addBendingMoment(bendingMomentBuilder);
    bMAndSFBuilder.addShearingForce(shearingForceBuilder.build());
    bMAndSFBuilder.addCalculationSheet(calculationSheetBuilder.build());
    bMAndSFBuilder.addCalculationSheetTankGroup(calculationSheetTankGroupBuilder.build());
    bMAndSFBuilder.addMinMaxValuesForBMAndSf(minMaxValuesForBMAndSfBuilder.build());
    bMAndSFBuilder.addStationValues(stationValueBuilder.build());
    bMAndSFBuilder.addInnerBulkHeadSF(innerBulkHeadSFBuilder.build());
    Mockito.when(this.vesselInfoService.getVesselsDetails(any(VesselAlgoRequest.class)))
        .thenReturn(
            VesselAlgoReply.newBuilder()
                .addVesselDraftCondition(vesselDraftConditionBuilder.build())
                .addVesselTankDetail(vesselTankBuilder.build())
                .addHydrostaticData(hydrostaticDataBuilder.build())
                .addVesselTankTCG(vesselTankTcgBuilder.build())
                .setBMAndSF(bMAndSFBuilder)
                .setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build())
                .build());

    VesselDetailsResponse response =
        this.vesselInfoService.getVesselsDetails(1L, CORRELATION_ID_HEADER_VALUE);
    assertAll(
        () ->
            assertEquals(
                String.valueOf(HttpStatusCode.OK.value()),
                response.getResponseStatus().getStatus(),
                "response valid"));
  }

  @Test
  void testGetVesselsDetailsGrpcFailure() throws GenericServiceException {
    Mockito.when(this.vesselInfoService.getVesselsDetails(anyLong(), anyString()))
        .thenCallRealMethod();
    Mockito.when(this.vesselInfoService.getVesselsDetails(any(VesselAlgoRequest.class)))
        .thenReturn(
            VesselAlgoReply.newBuilder()
                .setResponseStatus(
                    ResponseStatus.newBuilder()
                        .setStatus(FAILED)
                        .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST)
                        .build())
                .build());
    final GenericServiceException ex =
        assertThrows(
            GenericServiceException.class,
            () -> this.vesselInfoService.getVesselsDetails(1L, CORRELATION_ID_HEADER_VALUE));
    assertAll(
        () -> assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), "Invalid error code"),
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
  }
}