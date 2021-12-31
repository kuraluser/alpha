/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service;

import static org.mockito.ArgumentMatchers.*;

import com.cpdss.common.generated.VesselInfoServiceGrpc;
import com.cpdss.common.redis.CommonKeyValueStore;
import com.cpdss.gateway.domain.keycloak.KeycloakUser;
import com.cpdss.gateway.repository.RoleUserMappingRepository;
import com.cpdss.gateway.repository.UserStatusRepository;
import com.cpdss.gateway.repository.UsersRepository;
import com.cpdss.gateway.service.vesselinfo.VesselValveService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/** @Author jerin.g */
@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(classes = {VesselInfoService.class})
public class VesselInfoServiceTest extends CommonKeyValueStore<KeycloakUser> {

  @Autowired private VesselInfoService vesselInfoService;
  @MockBean private UsersRepository usersRepository;
  @MockBean private RedisTemplate redisTemplate;
  // @MockBean redisTemplate redisTemplate;
  @MockBean VesselValveService vesselValveService;
  @MockBean private RoleUserMappingRepository roleUserMappingRepository;
  @MockBean private UserCachingService userCachingService;
  @MockBean private KeycloakService keycloakService;
  @MockBean private UserStatusRepository userStatusRepository;

  private static final String SUCCESS = "SUCCESS";
  private static final String FAILED = "FAILED";
  private static final String CORRELATION_ID_HEADER_VALUE = "1234";
  private static final Long TEST_ID = 1L;
  private static final String NUMERICAL_TEST_VALUE = "123";

  @BeforeEach
  public void init() {
    this.vesselInfoService = Mockito.mock(VesselInfoService.class);
  }

  @MockBean private VesselInfoServiceGrpc.VesselInfoServiceBlockingStub vesselInfoGrpcService;

  //  @ParameterizedTest
  //  @ValueSource(booleans = {true, false})
  //  void testGetVesselsDetails(boolean empty) throws GenericServiceException {
  //    Mockito.when(this.vesselInfoService.getVesselsDetails(anyLong(), anyString(), eq(false)))
  //        .thenCallRealMethod();
  //    VesselDraftCondition.Builder vesselDraftConditionBuilder =
  // VesselDraftCondition.newBuilder();
  //    UllageDetails.Builder ullageBuilder = UllageDetails.newBuilder();
  //    VesselTankDetail.Builder vesselTankBuilder = VesselTankDetail.newBuilder();
  //    HydrostaticData.Builder hydrostaticDataBuilder = HydrostaticData.newBuilder();
  //    VesselTankTCG.Builder vesselTankTcgBuilder = VesselTankTCG.newBuilder();
  //    BMAndSF.Builder bMAndSFBuilder = BMAndSF.newBuilder();
  //    BendingMomentType1.Builder bendingMomentBuilder = BendingMomentType1.newBuilder();
  //    ShearingForceType1.Builder shearingForceBuilder = ShearingForceType1.newBuilder();
  //    BendingMomentType2.Builder bendingMomentBuilder2 = BendingMomentType2.newBuilder();
  //    ShearingForceType2.Builder shearingForceBuilder2 = ShearingForceType2.newBuilder();
  //    BendingMomentType4.Builder bendingMomentBuilder4 = BendingMomentType4.newBuilder();
  //    ShearingForceType4.Builder shearingForceBuilder4 = ShearingForceType4.newBuilder();
  //    CalculationSheet.Builder calculationSheetBuilder = CalculationSheet.newBuilder();
  //    MinMaxValuesForBMAndSf.Builder minMaxValuesForBMAndSfBuilder =
  //        MinMaxValuesForBMAndSf.newBuilder();
  //    CalculationSheetTankGroup.Builder calculationSheetTankGroupBuilder =
  //        CalculationSheetTankGroup.newBuilder();
  //    StationValues.Builder stationValueBuilder = StationValues.newBuilder();
  //    InnerBulkHeadSF.Builder innerBulkHeadSFBuilder = InnerBulkHeadSF.newBuilder();
  //    bMAndSFBuilder.addBendingMomentType1(bendingMomentBuilder);
  //    bMAndSFBuilder.addShearingForceType1(shearingForceBuilder.build());
  //    bMAndSFBuilder.addBendingMomentType2(bendingMomentBuilder2);
  //    bMAndSFBuilder.addBendingMomentType4(bendingMomentBuilder4);
  //    bMAndSFBuilder.addShearingForceType1(shearingForceBuilder.build());
  //    bMAndSFBuilder.addShearingForceType2(shearingForceBuilder2.build());
  //    bMAndSFBuilder.addShearingForceType4(shearingForceBuilder4.build());
  //
  //    bMAndSFBuilder.addCalculationSheet(calculationSheetBuilder.build());
  //    bMAndSFBuilder.addCalculationSheetTankGroup(calculationSheetTankGroupBuilder.build());
  //    bMAndSFBuilder.addMinMaxValuesForBMAndSf(minMaxValuesForBMAndSfBuilder.build());
  //    bMAndSFBuilder.addStationValues(stationValueBuilder.build());
  //    bMAndSFBuilder.addInnerBulkHeadSF(innerBulkHeadSFBuilder.build());
  //    Mockito.when(this.vesselInfoService.getVesselsDetails(any(VesselAlgoRequest.class)))
  //        .thenReturn(
  //            VesselAlgoReply.newBuilder()
  //                .addVesselDraftCondition(vesselDraftConditionBuilder.build())
  //                .addVesselTankDetail(vesselTankBuilder.build())
  //                .addHydrostaticData(hydrostaticDataBuilder.build())
  //                .addVesselTankTCG(vesselTankTcgBuilder.build())
  //                .setBMAndSF(bMAndSFBuilder)
  //                .addUllageDetails(ullageBuilder.build())
  //                .addUllageTrimCorrection(this.createUllageTrimCorrection(empty))
  //                .setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build())
  //                .build());
  //
  //    VesselDetailsResponse response =
  //        this.vesselInfoService.getVesselsDetails(1L, CORRELATION_ID_HEADER_VALUE, false);
  //    assertAll(
  //        () ->
  //            assertEquals(
  //                String.valueOf(HttpStatusCode.OK.value()),
  //                response.getResponseStatus().getStatus(),
  //                "response valid"));
  //  }
  //
  //  private UllageTrimCorrection createUllageTrimCorrection(boolean empty) {
  //    UllageTrimCorrection.Builder builder = UllageTrimCorrection.newBuilder();
  //    if (!empty) {
  //      builder.setTrim0(NUMERICAL_TEST_VALUE);
  //      builder.setTrim1(NUMERICAL_TEST_VALUE);
  //      builder.setTrim2(NUMERICAL_TEST_VALUE);
  //      builder.setTrim3(NUMERICAL_TEST_VALUE);
  //      builder.setTrim4(NUMERICAL_TEST_VALUE);
  //      builder.setTrim5(NUMERICAL_TEST_VALUE);
  //      builder.setTrim6(NUMERICAL_TEST_VALUE);
  //      builder.setTrimM1(NUMERICAL_TEST_VALUE);
  //      builder.setTrimM2(NUMERICAL_TEST_VALUE);
  //      builder.setTrimM3(NUMERICAL_TEST_VALUE);
  //      builder.setTrimM4(NUMERICAL_TEST_VALUE);
  //      builder.setTrimM5(NUMERICAL_TEST_VALUE);
  //      builder.setUllageDepth(NUMERICAL_TEST_VALUE);
  //    }
  //    builder.setId(TEST_ID);
  //    builder.setTankId(TEST_ID);
  //    return builder.build();
  //  }
  //
  //  @Test
  //  void testGetVesselsDetailsGrpcFailure() throws GenericServiceException {
  //    Mockito.when(this.vesselInfoService.getVesselsDetails(anyLong(), anyString(), eq(false)))
  //        .thenCallRealMethod();
  //    Mockito.when(this.vesselInfoService.getVesselsDetails(any(VesselAlgoRequest.class)))
  //        .thenReturn(
  //            VesselAlgoReply.newBuilder()
  //                .setResponseStatus(
  //                    ResponseStatus.newBuilder()
  //                        .setStatus(FAILED)
  //                        .setCode(CommonErrorCodes.E_HTTP_BAD_REQUEST)
  //                        .build())
  //                .build());
  //    final GenericServiceException ex =
  //        assertThrows(
  //            GenericServiceException.class,
  //            () -> this.vesselInfoService.getVesselsDetails(1L, CORRELATION_ID_HEADER_VALUE,
  // false));
  //    assertAll(
  //        () -> assertEquals(CommonErrorCodes.E_HTTP_BAD_REQUEST, ex.getCode(), "Invalid error
  // code"),
  //        () -> assertEquals(HttpStatusCode.BAD_REQUEST, ex.getStatus(), "Invalid http status"));
  //  }
  //
  //  @Test
  //  void testGetVesselsByCompany() {
  //    Long companyId = 1L;
  //    String correlationId = "1";
  //    Mockito.when(this.vesselInfoGrpcService.getAllVesselsByCompany(Mockito.any()))
  //        .thenReturn(getVR());
  //    ReflectionTestUtils.setField(
  //        vesselInfoService, "vesselInfoGrpcService", this.vesselInfoGrpcService);
  //  }
  //
  //  private VesselInfo.VesselReply getVR() {
  //    VesselInfo.VesselReply reply =
  //        VesselInfo.VesselReply.newBuilder()
  //            .setResponseStatus(Common.ResponseStatus.newBuilder().setStatus(SUCCESS).build())
  //            .build();
  //    return reply;
  //  }
  //
  //  @Test
  //  void testGetVesselsByCompanyException() {
  //    Long companyId = 1L;
  //    String correlationId = "1";
  //    Mockito.when(this.vesselInfoGrpcService.getAllVesselsByCompany(Mockito.any()))
  //        .thenReturn(getVRNS());
  //    ReflectionTestUtils.setField(
  //        vesselInfoService, "vesselInfoGrpcService", this.vesselInfoGrpcService);
  //    try {
  //      var response = this.vesselInfoService.getVesselsByCompany(companyId, correlationId);
  //    } catch (GenericServiceException e) {
  //      e.printStackTrace();
  //    }
  //  }
  //
  //  private VesselInfo.VesselReply getVRNS() {
  //    VesselInfo.VesselReply reply =
  //        VesselInfo.VesselReply.newBuilder()
  //            .setResponseStatus(Common.ResponseStatus.newBuilder().setCode("400").build())
  //            .build();
  //    return reply;
  //  }
}
