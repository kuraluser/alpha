/* Licensed at AlphaOri Technologies */
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
import com.cpdss.common.generated.VesselInfo.BendingMomentType1;
import com.cpdss.common.generated.VesselInfo.BendingMomentType2;
import com.cpdss.common.generated.VesselInfo.BendingMomentType4;
import com.cpdss.common.generated.VesselInfo.CalculationSheet;
import com.cpdss.common.generated.VesselInfo.CalculationSheetTankGroup;
import com.cpdss.common.generated.VesselInfo.HydrostaticData;
import com.cpdss.common.generated.VesselInfo.InnerBulkHeadSF;
import com.cpdss.common.generated.VesselInfo.MinMaxValuesForBMAndSf;
import com.cpdss.common.generated.VesselInfo.ShearingForceType1;
import com.cpdss.common.generated.VesselInfo.ShearingForceType2;
import com.cpdss.common.generated.VesselInfo.ShearingForceType4;
import com.cpdss.common.generated.VesselInfo.StationValues;
import com.cpdss.common.generated.VesselInfo.UllageDetails;
import com.cpdss.common.generated.VesselInfo.UllageTrimCorrection;
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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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
  private static final Long TEST_ID = 1L;
  private static final String NUMERICAL_TEST_VALUE = "123";

  @BeforeEach
  public void init() {
    this.vesselInfoService = Mockito.mock(VesselInfoService.class);
  }

  @ParameterizedTest
  @ValueSource(booleans = {true, false})
  void testGetVesselsDetails(boolean empty) throws GenericServiceException {
    Mockito.when(this.vesselInfoService.getVesselsDetails(anyLong(), anyString(), false))
        .thenCallRealMethod();
    VesselDraftCondition.Builder vesselDraftConditionBuilder = VesselDraftCondition.newBuilder();
    UllageDetails.Builder ullageBuilder = UllageDetails.newBuilder();
    VesselTankDetail.Builder vesselTankBuilder = VesselTankDetail.newBuilder();
    HydrostaticData.Builder hydrostaticDataBuilder = HydrostaticData.newBuilder();
    VesselTankTCG.Builder vesselTankTcgBuilder = VesselTankTCG.newBuilder();
    BMAndSF.Builder bMAndSFBuilder = BMAndSF.newBuilder();
    BendingMomentType1.Builder bendingMomentBuilder = BendingMomentType1.newBuilder();
    ShearingForceType1.Builder shearingForceBuilder = ShearingForceType1.newBuilder();
    BendingMomentType2.Builder bendingMomentBuilder2 = BendingMomentType2.newBuilder();
    ShearingForceType2.Builder shearingForceBuilder2 = ShearingForceType2.newBuilder();
    BendingMomentType4.Builder bendingMomentBuilder4 = BendingMomentType4.newBuilder();
    ShearingForceType4.Builder shearingForceBuilder4 = ShearingForceType4.newBuilder();
    CalculationSheet.Builder calculationSheetBuilder = CalculationSheet.newBuilder();
    MinMaxValuesForBMAndSf.Builder minMaxValuesForBMAndSfBuilder =
        MinMaxValuesForBMAndSf.newBuilder();
    CalculationSheetTankGroup.Builder calculationSheetTankGroupBuilder =
        CalculationSheetTankGroup.newBuilder();
    StationValues.Builder stationValueBuilder = StationValues.newBuilder();
    InnerBulkHeadSF.Builder innerBulkHeadSFBuilder = InnerBulkHeadSF.newBuilder();
    bMAndSFBuilder.addBendingMomentType1(bendingMomentBuilder);
    bMAndSFBuilder.addShearingForceType1(shearingForceBuilder.build());
    bMAndSFBuilder.addBendingMomentType2(bendingMomentBuilder2);
    bMAndSFBuilder.addBendingMomentType4(bendingMomentBuilder4);
    bMAndSFBuilder.addShearingForceType1(shearingForceBuilder.build());
    bMAndSFBuilder.addShearingForceType2(shearingForceBuilder2.build());
    bMAndSFBuilder.addShearingForceType4(shearingForceBuilder4.build());

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
                .addUllageDetails(ullageBuilder.build())
                .addUllageTrimCorrection(this.createUllageTrimCorrection(empty))
                .setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build())
                .build());

    VesselDetailsResponse response =
        this.vesselInfoService.getVesselsDetails(1L, CORRELATION_ID_HEADER_VALUE, false);
    assertAll(
        () ->
            assertEquals(
                String.valueOf(HttpStatusCode.OK.value()),
                response.getResponseStatus().getStatus(),
                "response valid"));
  }

  private UllageTrimCorrection createUllageTrimCorrection(boolean empty) {
    UllageTrimCorrection.Builder builder = UllageTrimCorrection.newBuilder();
    if (!empty) {
      builder.setTrim0(NUMERICAL_TEST_VALUE);
      builder.setTrim1(NUMERICAL_TEST_VALUE);
      builder.setTrim2(NUMERICAL_TEST_VALUE);
      builder.setTrim3(NUMERICAL_TEST_VALUE);
      builder.setTrim4(NUMERICAL_TEST_VALUE);
      builder.setTrim5(NUMERICAL_TEST_VALUE);
      builder.setTrim6(NUMERICAL_TEST_VALUE);
      builder.setTrimM1(NUMERICAL_TEST_VALUE);
      builder.setTrimM2(NUMERICAL_TEST_VALUE);
      builder.setTrimM3(NUMERICAL_TEST_VALUE);
      builder.setTrimM4(NUMERICAL_TEST_VALUE);
      builder.setTrimM5(NUMERICAL_TEST_VALUE);
      builder.setUllageDepth(NUMERICAL_TEST_VALUE);
    }
    builder.setId(TEST_ID);
    builder.setTankId(TEST_ID);
    return builder.build();
  }

  @Test
  void testGetVesselsDetailsGrpcFailure() throws GenericServiceException {
    Mockito.when(this.vesselInfoService.getVesselsDetails(anyLong(), anyString(), false))
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
            () -> this.vesselInfoService.getVesselsDetails(1L, CORRELATION_ID_HEADER_VALUE, false));
    assertAll(
        () -> assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), "Invalid error code"),
        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
  }
}
