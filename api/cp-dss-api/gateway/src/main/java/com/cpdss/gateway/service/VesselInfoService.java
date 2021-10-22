/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service;

import static org.springframework.util.StringUtils.isEmpty;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common;
import com.cpdss.common.generated.VesselInfo;
import com.cpdss.common.generated.VesselInfo.LoadLineDetail;
import com.cpdss.common.generated.VesselInfo.LoadingInfoRulesRequest;
import com.cpdss.common.generated.VesselInfo.ParameterValue;
import com.cpdss.common.generated.VesselInfo.VesselAlgoReply;
import com.cpdss.common.generated.VesselInfo.VesselAlgoRequest;
import com.cpdss.common.generated.VesselInfo.VesselDetail;
import com.cpdss.common.generated.VesselInfo.VesselDetaildInfoReply;
import com.cpdss.common.generated.VesselInfo.VesselIdRequest;
import com.cpdss.common.generated.VesselInfo.VesselParticulars;
import com.cpdss.common.generated.VesselInfo.VesselReply;
import com.cpdss.common.generated.VesselInfo.VesselRequest;
import com.cpdss.common.generated.VesselInfo.VesselRuleReply;
import com.cpdss.common.generated.VesselInfo.VesselRuleRequest;
import com.cpdss.common.generated.VesselInfo.VesselTankDetail;
import com.cpdss.common.generated.VesselInfo.VesselTankInfo;
import com.cpdss.common.generated.VesselInfo.VesselsInfoRequest;
import com.cpdss.common.generated.VesselInfo.VesselsInfoRequest.Builder;
import com.cpdss.common.generated.VesselInfo.VesselsInformation;
import com.cpdss.common.generated.VesselInfo.VesselsInformationReply;
import com.cpdss.common.generated.VesselInfoServiceGrpc.VesselInfoServiceBlockingStub;
import com.cpdss.common.redis.CommonKeyValueStore;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.rest.CommonSuccessResponse;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.gateway.domain.*;
import com.cpdss.gateway.domain.keycloak.KeycloakUser;
import com.cpdss.gateway.domain.loadingplan.VesselParticularsForExcel;
import com.cpdss.gateway.domain.user.UserStatusValue;
import com.cpdss.gateway.domain.user.UserType;
import com.cpdss.gateway.domain.vessel.*;
import com.cpdss.gateway.entity.RoleUserMapping;
import com.cpdss.gateway.entity.UserStatus;
import com.cpdss.gateway.entity.Users;
import com.cpdss.gateway.repository.RoleUserMappingRepository;
import com.cpdss.gateway.repository.UserStatusRepository;
import com.cpdss.gateway.repository.UsersRepository;
import com.cpdss.gateway.service.vesselinfo.VesselValveService;
import com.cpdss.gateway.utility.RuleUtility;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Service class for vessel related operations
 *
 * @author suhail.k
 */
@Service
@Log4j2
public class VesselInfoService extends CommonKeyValueStore<KeycloakUser> {

  @GrpcClient("vesselInfoService")
  private VesselInfoServiceBlockingStub vesselInfoGrpcService;

  @Autowired private UsersRepository usersRepository;
  @Autowired VesselValveService vesselValveService;
  @Autowired private RoleUserMappingRepository roleUserMappingRepository;
  @Autowired private UserCachingService userCachingService;
  @Autowired private KeycloakService keycloakService;
  @Autowired private UserStatusRepository userStatusRepository;

  private static final String SUCCESS = "SUCCESS";
  private static final String SHIP_URL_PREFIX = "/api/ship";

  private static final Long CARGO_TANK_CATEGORY_ID = 1L;

  /**
   * Get vessels by company
   *
   * @param companyId
   * @param correlationId
   * @return
   * @throws GenericServiceException
   */
  public VesselResponse getVesselsByCompany(Long companyId, String correlationId)
      throws GenericServiceException {
    log.info("Inside getVesselsByCompany, correlationId:{}", correlationId);
    VesselReply reply =
        this.getVesselsByCompany(VesselRequest.newBuilder().setCompanyId(companyId).build());

    if (!SUCCESS.equals(reply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "failed to fetch vessels",
          reply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(reply.getResponseStatus().getCode())));
    }
    VesselResponse response = this.createVesselResponse(reply);
    response.setResponseStatus(
        new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
    return response;
  }
  /**
   * Get vessel tanks
   *
   * @param vesselId
   * @param correlationId
   * @return
   * @throws GenericServiceException
   */
  public VesselTankResponse getCargoVesselTanks(Long vesselId, String correlationId)
      throws GenericServiceException {
    log.info("Inside getVessel tanks, correlationId:{}", correlationId);

    VesselInfo.VesselRequest.Builder vesselGrpcRequest = VesselInfo.VesselRequest.newBuilder();
    vesselGrpcRequest.setVesselId(vesselId);
    vesselGrpcRequest.addAllTankCategories(Arrays.asList(CARGO_TANK_CATEGORY_ID));
    VesselReply reply = this.vesselInfoGrpcService.getVesselCargoTanks(vesselGrpcRequest.build());

    if (!SUCCESS.equals(reply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "failed to fetch vessels",
          reply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(reply.getResponseStatus().getCode())));
    }
    List<VesselTankDetail> vesselsList = reply.getVesselTanksList();
    List<VesselTank> vesselTankList = new ArrayList<>();
    vesselsList
        .parallelStream()
        .filter(tank -> tank.getTankCategoryId() == CARGO_TANK_CATEGORY_ID)
        .forEach(
            tankDetail -> {
              VesselTank vesselTank = new VesselTank();
              vesselTank.setId(tankDetail.getTankId());
              vesselTank.setName(tankDetail.getTankName());
              vesselTank.setShortName(tankDetail.getShortName());
              vesselTank.setGroup(tankDetail.getTankGroup());
              vesselTank.setOrder(tankDetail.getTankOrder());
              vesselTank.setDisplayOrder(tankDetail.getTankDisplayOrder());
              vesselTankList.add(vesselTank);
            });

    VesselTankResponse response = new VesselTankResponse();
    response.setCargoVesselTanks(vesselTankList);
    response.setResponseStatus(
        new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
    return response;
  }

  /**
   * Create vessel response from grpc reply
   *
   * @param reply
   * @return
   * @throws GenericServiceException
   */
  private VesselResponse createVesselResponse(VesselReply reply) throws GenericServiceException {
    VesselResponse response = new VesselResponse();
    response.setVessels(new ArrayList<>());
    Set<Long> userIdList =
        reply.getVesselsList().stream()
            .map(VesselDetail::getCheifOfficerId)
            .collect(Collectors.toSet());
    userIdList.addAll(
        reply.getVesselsList().stream()
            .map(VesselDetail::getCaptainId)
            .collect(Collectors.toSet()));
    List<Users> userList = new ArrayList<Users>();
    userIdList.forEach(
        userId -> {
          Users user = new Users();
          KeycloakUser kuser = this.getData(userId.toString());
          log.debug("User data from cache: {}", kuser);

          if (null == kuser) {
            //    Get user data from repository
            user = this.usersRepository.findByIdAndIsActive(userId, true);
          } else {
            user.setId(kuser.getUserId());
            user.setFirstName(kuser.getFirstName());
            user.setLastName(kuser.getLastName());
            user.setUsername(kuser.getUsername());
            user.setEmail(kuser.getEmail());
            user.setActive(true);
          }
          if (user != null) {
            userList.add(user);
          }
        });
    //    List<Users> userList = this.usersRepository.findByIdIn(new ArrayList<>(userIdList));
    for (VesselDetail grpcReply : reply.getVesselsList()) {
      Vessel vessel = new Vessel();
      vessel.setId(grpcReply.getId());
      vessel.setName(grpcReply.getName());
      vessel.setImoNumber(grpcReply.getImoNumber());
      vessel.setFlagPath(grpcReply.getFlag());
      vessel.setCaptainId(grpcReply.getCaptainId());
      vessel.setChiefOfficerId(grpcReply.getCheifOfficerId());
      vessel.setCharterer(grpcReply.getCharterer());
      vessel.setHasLoadicator(grpcReply.getHasLoadicator());
      vessel.setKeelToMastHeight(grpcReply.getKeelToMastHeight());

      if (this.isShip()) {
        Optional<Users> userOpt =
            userList.stream().filter(item -> item.getId().equals(vessel.getCaptainId())).findAny();
        if (!userOpt.isPresent()) {
          throw new GenericServiceException(
              "Captain info not found in database",
              CommonErrorCodes.E_GEN_INTERNAL_ERR,
              HttpStatusCode.INTERNAL_SERVER_ERROR);
        }
        vessel.setCaptainName(userOpt.get().getFirstName() + " " + userOpt.get().getLastName());
        userOpt =
            userList.stream()
                .filter(item -> item.getId().equals(vessel.getChiefOfficerId()))
                .findAny();
        if (!userOpt.isPresent()) {
          throw new GenericServiceException(
              "Chief officer info not found in database",
              CommonErrorCodes.E_GEN_INTERNAL_ERR,
              HttpStatusCode.INTERNAL_SERVER_ERROR);
        }
        vessel.setChiefOfficerName(
            userOpt.get().getFirstName() + " " + userOpt.get().getLastName());
      } else {
        List<User> shoreUsers = this.findUsers(UserType.CLOUD);
        Optional<User> userOptCaptain =
            shoreUsers.stream()
                .filter(item -> item.getId().equals(vessel.getCaptainId()))
                .findAny();
        if (!userOptCaptain.isPresent()) {
          throw new GenericServiceException(
              "Captain info not found in database",
              CommonErrorCodes.E_GEN_INTERNAL_ERR,
              HttpStatusCode.INTERNAL_SERVER_ERROR);
        }
        vessel.setCaptainName(
            userOptCaptain.get().getFirstName() + " " + userOptCaptain.get().getLastName());
        Optional<Users> userOptChiefOfficer =
            userList.stream()
                .filter(item -> item.getId().equals(vessel.getChiefOfficerId()))
                .findAny();
        if (!userOptChiefOfficer.isPresent()) {
          throw new GenericServiceException(
              "Chief officer info not found in database",
              CommonErrorCodes.E_GEN_INTERNAL_ERR,
              HttpStatusCode.INTERNAL_SERVER_ERROR);
        }
        vessel.setChiefOfficerName(
            userOptChiefOfficer.get().getFirstName()
                + " "
                + userOptChiefOfficer.get().getLastName());
      }

      vessel.setLoadlines(new ArrayList<>());
      for (LoadLineDetail detail : grpcReply.getLoadLinesList()) {
        LoadLine loadLine = new LoadLine();
        loadLine.setId(detail.getId());
        loadLine.setName(detail.getName());
        loadLine.setDraftMarks(
            detail.getDraftMarksList().stream().map(BigDecimal::new).collect(Collectors.toList()));
        vessel.getLoadlines().add(loadLine);
      }
      response.getVessels().add(vessel);
    }
    return response;
  }

  /**
   * Call grpc service for vessel info
   *
   * @param request
   * @return
   */
  public VesselReply getVesselsByCompany(VesselRequest request) {
    return this.vesselInfoGrpcService.getAllVesselsByCompany(request);
  }

  /**
   * Method to find users
   *
   * @param userType UserType Ship/Cloud
   * @return List of users
   * @throws GenericServiceException Exception object
   */
  private List<User> findUsers(UserType userType) throws GenericServiceException {
    List<User> userList = new ArrayList<>();
    List<Users> users = new ArrayList<>();
    UserStatus userStatus = userStatusRepository.getOne(UserStatusValue.APPROVED.getId());
    switch (userType) {
      case SHIP:
        users = this.usersRepository.findByIsActiveAndStatusOrderById(true, userStatus);
        if (users != null && !users.isEmpty()) {
          users.forEach(
              userEntity -> {
                User user = new User();
                user.setId(userEntity.getId());
                user.setFirstName(userEntity.getFirstName());
                user.setLastName(userEntity.getLastName());
                user.setUsername(userEntity.getUsername());
                user.setDesignation(userEntity.getDesignation());
                List<RoleUserMapping> mapping =
                    this.roleUserMappingRepository.findByUsersAndIsActive(userEntity, true);
                if (!mapping.isEmpty()) {
                  user.setRole(mapping.get(0).getRoles().getName());
                }
                user.setDefaultUser(userEntity.getIsShipUser());
                userList.add(user);
              });
        }
        break;
      case CLOUD:
        // Get all keycloak users
        KeycloakUser[] keycloakUsersList = keycloakService.getUsers();
        List<String> keyCloakIds =
            Arrays.stream(keycloakUsersList).map(KeycloakUser::getId).collect(Collectors.toList());

        users =
            this.usersRepository.findByKeycloakIdInAndStatusAndIsActiveOrderById(
                keyCloakIds, userStatus, true);
        users.forEach(
            userEntity -> {
              KeycloakUser keycloakUser = null;
              try {
                keycloakUser = userCachingService.getUser(userEntity.getKeycloakId());
              } catch (GenericServiceException e) {
                e.printStackTrace();
              }
              User user = new User();
              user.setId(userEntity.getId());
              user.setKeycloakId(keycloakUser.getId());
              user.setFirstName(keycloakUser.getFirstName());
              user.setLastName(keycloakUser.getLastName());
              user.setUsername(keycloakUser.getUsername());
              user.setDesignation(userEntity.getDesignation());
              List<RoleUserMapping> mapping =
                  this.roleUserMappingRepository.findByUsersAndIsActive(userEntity, true);
              if (!mapping.isEmpty()) {
                user.setRole(mapping.get(0).getRoles().getName());
              }
              user.setDefaultUser(userEntity.getIsShipUser());
              userList.add(user);
            });
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + userType);
    }
    return userList;
  }

  /**
   * Identify ship or shore based on accessed url
   *
   * @return
   */
  public boolean isShip() {
    if (null != RequestContextHolder.getRequestAttributes()) {
      HttpServletRequest request =
          ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
      if (null != request.getRequestURI()) {
        return request.getRequestURI().indexOf(SHIP_URL_PREFIX) != -1;
      }
    }
    return false;
  }

  /**
   * @param vesselId
   * @param correlationId
   * @return VesselDetailsResponse
   */
  @Cacheable(value = "vesselDetails", key = "{#vesselId, #enableValveSeq}")
  public VesselDetailsResponse getVesselsDetails(
      Long vesselId, String correlationId, boolean enableValveSeq) throws GenericServiceException {
    VesselDetailsResponse vesselDetailsResponse = new VesselDetailsResponse();
    VesselAlgoRequest vesselAlgoRequest =
        VesselAlgoRequest.newBuilder().setVesselId(vesselId).build();
    VesselAlgoReply vesselAlgoReply = this.getVesselsDetails(vesselAlgoRequest);
    if (!SUCCESS.equals(vesselAlgoReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "failed to get Vessel Details ",
          vesselAlgoReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(vesselAlgoReply.getResponseStatus().getCode())));
    }
    vesselDetailsResponse.setVessel(
        this.buildVesselDetailsResponse(vesselAlgoReply, correlationId));
    vesselDetailsResponse.setVesselDraftCondition(
        this.buildVesselDraftconditionResponse(vesselAlgoReply, correlationId));
    vesselDetailsResponse.setVesselTanks(
        this.buildVesselTankListResponse(vesselAlgoReply, correlationId));
    vesselDetailsResponse.setHydrostaticDatas(
        this.buildHydrostaticResponse(vesselAlgoReply, correlationId));
    vesselDetailsResponse.setVesselTankTCGs(
        this.buildVesselTankTcgResponse(vesselAlgoReply, correlationId));

    BMAndSF bmAndSF = new BMAndSF();

    if (vesselAlgoReply.getVesselDetail().getBmSfModelType() != null
        && vesselAlgoReply.getVesselDetail().getBmSfModelType().equals("2")) {
      bmAndSF.setBendingMomentType2(
          this.createBendingMomentResponseType2(vesselAlgoReply, correlationId));
      bmAndSF.setShearingForceType2(
          this.createShearingForceResponseType2(vesselAlgoReply, correlationId));
    }
    if (vesselAlgoReply.getVesselDetail().getBmSfModelType() != null
        && vesselAlgoReply.getVesselDetail().getBmSfModelType().equals("4")) {
      bmAndSF.setBendingMomentType4(
          this.createBendingMomentResponseType4(vesselAlgoReply, correlationId));
      bmAndSF.setShearingForceType4(
          this.createShearingForceResponseType4(vesselAlgoReply, correlationId));
    }
    if (vesselAlgoReply.getVesselDetail().getBmSfModelType() != null
        && vesselAlgoReply.getVesselDetail().getBmSfModelType().equals("3")) {
      bmAndSF.setBendingMomentShearingForceType3(
          this.createBendingMomentShearingForceType3(vesselAlgoReply, correlationId));
    } else {
      bmAndSF.setBendingMomentType1(
          this.createBendingMomentResponse(vesselAlgoReply, correlationId));
      bmAndSF.setShearingForceType1(
          this.createShearingForceResponse(vesselAlgoReply, correlationId));
    }

    bmAndSF.setCalculationSheet(
        this.createCalculationSheetResponse(vesselAlgoReply, correlationId));
    bmAndSF.setCalculationSheetTankGroup(
        this.createCalculationSheetTankGroupResponse(vesselAlgoReply, correlationId));
    bmAndSF.setMinMaxValuesForBMAndSfs(
        this.createMinMaxValuesForBMAndSfResponse(vesselAlgoReply, correlationId));
    bmAndSF.setStationValues(this.createStationValues(vesselAlgoReply, correlationId));
    bmAndSF.setInnerBulkHeadValues(this.createInnerBulkHeadValues(vesselAlgoReply, correlationId));
    vesselDetailsResponse.setBMAndSF(bmAndSF);
    vesselDetailsResponse.setUllageDetails(createVesselUllageDetails(vesselAlgoReply));
    vesselDetailsResponse.setResponseStatus(
        new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
    vesselDetailsResponse.setUllageTrimCorrections(
        this.buildUllageTrimCorrections(vesselAlgoReply));
    vesselDetailsResponse.setSelectableParameter(this.buildSelectableParameters(vesselAlgoReply));
    if (enableValveSeq)
      vesselDetailsResponse.setVesselValveSequence(this.getVesselValveSequenceData(vesselId));
    vesselDetailsResponse.setPumpTypes(this.buildPumpTypes(vesselAlgoReply));
    vesselDetailsResponse.setVesselPumps(this.buildVesselPumps(vesselAlgoReply));
    vesselDetailsResponse.setTankTypes(this.buildTankTypes(vesselAlgoReply));
    vesselDetailsResponse.setVesselManifold(this.buildVesselManifold(vesselAlgoReply));
    vesselDetailsResponse.setVesselBottomLine(this.buildVesselBottomLine(vesselAlgoReply));

    vesselDetailsResponse.getVesselManifold().stream()
        .forEach(
            manifold -> {
              Optional<TankType> type =
                  vesselDetailsResponse.getTankTypes().stream()
                      .filter(tankType -> tankType.getId().equals(manifold.getComponentType()))
                      .findFirst();
              if (type.isPresent()) {
                manifold.setComponentTypeName(type.get().getTypeName());
              }
            });

    vesselDetailsResponse.getVesselBottomLine().stream()
        .forEach(
            bottomLine -> {
              Optional<TankType> type =
                  vesselDetailsResponse.getTankTypes().stream()
                      .filter(tankType -> tankType.getId().equals(bottomLine.getComponentType()))
                      .findFirst();
              if (type.isPresent()) {
                bottomLine.setComponentTypeName(type.get().getTypeName());
              }
            });
    return vesselDetailsResponse;
  }

  private Map<Object, Object> getVesselValveSequenceData(Long vesselId) {
    VesselInfo.VesselRequest.Builder builder = VesselInfo.VesselRequest.newBuilder();
    builder.setVesselId(vesselId);
    VesselInfo.VesselValveSequenceReply reply =
        this.vesselInfoGrpcService.getVesselValveSequence(builder.build());
    if (reply.getResponseStatus().getStatus().equals(SUCCESS)) {
      Map<Object, Object> response = new HashMap<>();
      // Vessel Valve Sequence
      response.putAll(
          this.vesselValveService.buildVesselValveResponse(reply.getVvSequenceEntitiesList()));
      // Vessel Eductor Process
      response.put(
          "eductionProcess",
          this.vesselValveService.buildVesselValveEductorResponse(
              reply.getVvEducationEntitiesList()));
      // Air Purge
      response.put(
          "airPurge",
          this.vesselValveService.buildVesselValveAirPurge(reply.getVvAirPurgeSequenceList()));

      // Stripping Sequence
      response.put(
          "strippingSequence",
          this.vesselValveService.buildVesselValveStrippingSeq(reply.getVvStrippingSequenceList()));

      log.info("Vessel Valve Sequence data size {}", response.size());
      return response;
    }
    log.info("Vessel Valve Sequence data not found");
    return null;
  }

  private List<SelectableParameter> buildSelectableParameters(VesselAlgoReply vesselAlgoReply) {
    List<SelectableParameter> selectableParameters = new ArrayList<>();
    for (com.cpdss.common.generated.VesselInfo.SelectableParameter dbValue :
        vesselAlgoReply.getSelectableParameterList()) {
      SelectableParameter parameter = new SelectableParameter();
      parameter.setName(dbValue.getParamterName());
      List<Parameter> parameterValues = new ArrayList<>();
      if (dbValue.getValuesList() == null || dbValue.getValuesList().isEmpty()) {
        parameterValues.add(createFlowRateParameter(1));
        parameterValues.add(createFlowRateParameter(6));
        parameterValues.add(createFlowRateParameter(7));
        parameterValues.add(createFlowRateParameter(12));

      } else {
        for (ParameterValue value : dbValue.getValuesList()) {
          Parameter flowrateValue = new Parameter();
          flowrateValue.setType(value.getType());
          flowrateValue.setValue(value.getValue());
          parameterValues.add(flowrateValue);
        }
      }
      parameter.setValues(parameterValues);
      selectableParameters.add(parameter);
    }
    return selectableParameters;
  }

  /**
   * @param vesselAlgoReply
   * @param correlationId
   * @return List<BendingMoment>
   */
  private List<BendingMomentType2> createBendingMomentResponseType2(
      VesselAlgoReply vesselAlgoReply, String correlationId) {
    List<BendingMomentType2> bendingMoments = new ArrayList<BendingMomentType2>();
    vesselAlgoReply
        .getBMAndSF()
        .getBendingMomentType2List()
        .forEach(
            bendingMoment -> {
              BendingMomentType2 bm = new BendingMomentType2();
              bm.setId(bendingMoment.getId());
              bm.setFrameNumber(bendingMoment.getFrameNumber());
              bm.setId(bendingMoment.getId());
              bm.setBuay(bendingMoment.getBuay());
              bm.setCorrt(bendingMoment.getCorrt());
              bm.setDisplacement(bendingMoment.getDisplacement());
              bm.setIsActive(bendingMoment.getIsActive());
              bendingMoments.add(bm);
            });
    return bendingMoments;
  }

  /**
   * @param vesselAlgoReply
   * @param correlationId
   * @return List<BendingMoment>
   */
  private List<BendingMomentType4> createBendingMomentResponseType4(
      VesselAlgoReply vesselAlgoReply, String correlationId) {
    List<BendingMomentType4> bendingMoments = new ArrayList<BendingMomentType4>();
    vesselAlgoReply
        .getBMAndSF()
        .getBendingMomentType4List()
        .forEach(
            bendingMoment -> {
              BendingMomentType4 bm = new BendingMomentType4();
              bm.setId(bendingMoment.getId());
              bm.setFrameNumber(bendingMoment.getFrameNumber());
              bm.setId(bendingMoment.getId());
              bm.setTrim_m1(bendingMoment.getTrimM1());
              bm.setTrim_0(bendingMoment.getTrim0());
              bm.setTrim_1(bendingMoment.getTrim1());
              bm.setTrim_2(bendingMoment.getTrim2());
              bm.setTrim_3(bendingMoment.getTrim3());
              bm.setTrim_4(bendingMoment.getTrim4());
              bm.setTrim_5(bendingMoment.getTrim5());
              bm.setIsActive(bendingMoment.getIsActive());
              bendingMoments.add(bm);
            });
    return bendingMoments;
  }

  /**
   * @param vesselAlgoReply
   * @param correlationId
   * @return List<BendingMoment>
   */
  private List<BendingMomentShearingForceType3> createBendingMomentShearingForceType3(
      VesselAlgoReply vesselAlgoReply, String correlationId) {
    List<BendingMomentShearingForceType3> bendingMoments =
        new ArrayList<BendingMomentShearingForceType3>();
    vesselAlgoReply
        .getBMAndSF()
        .getBendingMomentShearingForce3List()
        .forEach(
            bendingMoment -> {
              BendingMomentShearingForceType3 bm = new BendingMomentShearingForceType3();
              bm.setId(bendingMoment.getId());
              bm.setFrameNumber(bendingMoment.getFrameNumber());
              bm.setLoadCondition(bendingMoment.getLoadCondition());
              bm.setDraftAp(bendingMoment.getDraftAp());
              bm.setDraftFp(bendingMoment.getDraftFp());
              bm.setBendingMoment(bendingMoment.getBendingMoment());
              bm.setShearingForce(bendingMoment.getShearingForce());
              bm.setIsActive(bendingMoment.getIsActive());
              bendingMoments.add(bm);
            });
    return bendingMoments;
  }

  private Parameter createFlowRateParameter(int type) {
    Parameter flowrateValue = new Parameter();
    flowrateValue.setType(type);
    flowrateValue.setValue("0");
    return flowrateValue;
  }

  /**
   * Build ullage trim correction list
   *
   * @param vesselAlgoReply
   * @return
   */
  private List<UllageTrimCorrection> buildUllageTrimCorrections(VesselAlgoReply vesselAlgoReply) {
    List<UllageTrimCorrection> list = new ArrayList<>();
    for (com.cpdss.common.generated.VesselInfo.UllageTrimCorrection dto :
        vesselAlgoReply.getUllageTrimCorrectionList()) {
      UllageTrimCorrection ullageTrimCorrection = new UllageTrimCorrection();
      ullageTrimCorrection.setId(dto.getId());
      ullageTrimCorrection.setTankId(dto.getTankId());
      ullageTrimCorrection.setUllageDepth(
          isEmpty(dto.getUllageDepth()) ? null : dto.getUllageDepth());
      ullageTrimCorrection.setTrimM1(isEmpty(dto.getTrimM1()) ? null : dto.getTrimM1());
      ullageTrimCorrection.setTrimM2(isEmpty(dto.getTrimM1()) ? null : dto.getTrimM2());
      ullageTrimCorrection.setTrimM3(isEmpty(dto.getTrimM1()) ? null : dto.getTrimM3());
      ullageTrimCorrection.setTrimM4(isEmpty(dto.getTrimM1()) ? null : dto.getTrimM4());
      ullageTrimCorrection.setTrimM5(isEmpty(dto.getTrimM1()) ? null : dto.getTrimM5());
      ullageTrimCorrection.setTrim0(isEmpty(dto.getTrim0()) ? null : dto.getTrim0());
      ullageTrimCorrection.setTrim1(isEmpty(dto.getTrim1()) ? null : dto.getTrim1());
      ullageTrimCorrection.setTrim2(isEmpty(dto.getTrim2()) ? null : dto.getTrim2());
      ullageTrimCorrection.setTrim3(isEmpty(dto.getTrim3()) ? null : dto.getTrim3());
      ullageTrimCorrection.setTrim4(isEmpty(dto.getTrim4()) ? null : dto.getTrim4());
      ullageTrimCorrection.setTrim5(isEmpty(dto.getTrim5()) ? null : dto.getTrim5());
      ullageTrimCorrection.setTrim6(isEmpty(dto.getTrim6()) ? null : dto.getTrim6());
      list.add(ullageTrimCorrection);
    }
    return list;
  }

  /**
   * @param vesselAlgoReply
   * @return List<UllageDetails>
   */
  private List<UllageDetails> createVesselUllageDetails(VesselAlgoReply vesselAlgoReply) {
    List<UllageDetails> ullageDetails = new ArrayList<UllageDetails>();
    vesselAlgoReply
        .getUllageDetailsList()
        .forEach(
            ullage -> {
              UllageDetails ullageDetail = new UllageDetails();
              ullageDetail.setId(ullage.getId());
              ullageDetail.setTankId(ullage.getTankId());
              ullageDetail.setUllageDepth(ullage.getUllageDepth());
              ullageDetail.setEvenKeelCapacityCubm(ullage.getEvenKeelCapacityCubm());
              ullageDetail.setSoundDepth(ullage.getSoundDepth());
              ullageDetails.add(ullageDetail);
            });
    return ullageDetails;
  }

  /**
   * @param vesselAlgoReply
   * @param correlationId
   * @return Object
   */
  private List<InnerBulkHeadValues> createInnerBulkHeadValues(
      VesselAlgoReply vesselAlgoReply, String correlationId) {
    List<InnerBulkHeadValues> innerBulkHeadSFValues = new ArrayList<InnerBulkHeadValues>();
    vesselAlgoReply
        .getBMAndSF()
        .getInnerBulkHeadSFList()
        .forEach(
            innerBulkHeadSFValue -> {
              InnerBulkHeadValues values = new InnerBulkHeadValues();
              values.setId(innerBulkHeadSFValue.getId());
              values.setFrameNumber(innerBulkHeadSFValue.getFrameNumber());
              values.setForeAlpha(innerBulkHeadSFValue.getForeAlpha());
              values.setForeCenterCargoTankId(innerBulkHeadSFValue.getForeCenterCargoTankId());
              values.setForeC1(innerBulkHeadSFValue.getForeC1());
              values.setForeWingTankIds(innerBulkHeadSFValue.getForeWingTankIds());
              values.setForeC2(innerBulkHeadSFValue.getForeC2());
              values.setForeBallstTanks(innerBulkHeadSFValue.getForeBallastTanks());
              values.setForeC3(innerBulkHeadSFValue.getForeC3());
              values.setForeBWCorrection(innerBulkHeadSFValue.getForeBWCorrection());
              values.setForeC4(innerBulkHeadSFValue.getForeC4());
              values.setForeMaxAllowence(innerBulkHeadSFValue.getForeMaxAllowence());
              values.setForeMinAllowence(innerBulkHeadSFValue.getForeMinAllowence());
              values.setAftAlpha(innerBulkHeadSFValue.getAftAlpha());
              values.setAftCenterCargoTankId(innerBulkHeadSFValue.getAftCenterCargoTankId());
              values.setAftC1(innerBulkHeadSFValue.getAftC1());
              values.setAftWingTankIds(innerBulkHeadSFValue.getAftWingTankIds());
              values.setAftC2(innerBulkHeadSFValue.getAftC2());
              values.setAftBallstTanks(innerBulkHeadSFValue.getAftBallastTanks());
              values.setAftC3(innerBulkHeadSFValue.getAftC3());
              values.setAftBWCorrection(innerBulkHeadSFValue.getAftBWCorrection());
              values.setAftC4(innerBulkHeadSFValue.getAftC4());
              values.setAftMaxFlAllowence(innerBulkHeadSFValue.getAftMaxFlAllowence());
              values.setAftMinFlAllowence(innerBulkHeadSFValue.getAftMinFlAllowence());
              innerBulkHeadSFValues.add(values);
            });
    return innerBulkHeadSFValues;
  }

  /**
   * @param vesselAlgoReply
   * @param correlationId
   * @return Object
   */
  private List<StationValues> createStationValues(
      VesselAlgoReply vesselAlgoReply, String correlationId) {
    List<StationValues> stationValues = new ArrayList<StationValues>();
    vesselAlgoReply
        .getBMAndSF()
        .getStationValuesList()
        .forEach(
            stationValue -> {
              StationValues values = new StationValues();
              values.setId(stationValue.getId());
              values.setFrameNumberFrom(stationValue.getFrameNumberFrom());
              values.setFrameNumberTo(stationValue.getFrameNumberTo());
              values.setStationFrom(stationValue.getStationFrom());
              values.setStationTo(stationValue.getStationTo());
              values.setDistance(stationValue.getDistance());
              stationValues.add(values);
            });
    return stationValues;
  }

  /**
   * @param vesselAlgoReply
   * @param correlationId
   * @return List<MinMaxValuesForBMAndSf>
   */
  private List<MinMaxValuesForBMAndSf> createMinMaxValuesForBMAndSfResponse(
      VesselAlgoReply vesselAlgoReply, String correlationId) {
    List<MinMaxValuesForBMAndSf> minMaxValuesForBMAndSfs = new ArrayList<MinMaxValuesForBMAndSf>();
    vesselAlgoReply
        .getBMAndSF()
        .getMinMaxValuesForBMAndSfList()
        .forEach(
            minMaxValuesForBMAndSf -> {
              MinMaxValuesForBMAndSf minMaxBmSf = new MinMaxValuesForBMAndSf();
              minMaxBmSf.setId(minMaxValuesForBMAndSf.getId());
              minMaxBmSf.setFrameNumber(minMaxValuesForBMAndSf.getFrameNumber());
              minMaxBmSf.setMinBm(minMaxValuesForBMAndSf.getMinBm());
              minMaxBmSf.setMinSf(minMaxValuesForBMAndSf.getMinSf());
              minMaxBmSf.setMaxBm(minMaxValuesForBMAndSf.getMaxBm());
              minMaxBmSf.setMaxSf(minMaxValuesForBMAndSf.getMaxSf());
              minMaxValuesForBMAndSfs.add(minMaxBmSf);
            });
    return minMaxValuesForBMAndSfs;
  }

  /**
   * @param vesselAlgoReply
   * @param correlationId
   * @return List<CalculationSheetTankGroup>
   */
  private List<CalculationSheetTankGroup> createCalculationSheetTankGroupResponse(
      VesselAlgoReply vesselAlgoReply, String correlationId) {
    List<CalculationSheetTankGroup> calculationSheetTankGroups =
        new ArrayList<CalculationSheetTankGroup>();
    vesselAlgoReply
        .getBMAndSF()
        .getCalculationSheetTankGroupList()
        .forEach(
            calculationSheetTankGroup -> {
              CalculationSheetTankGroup cstg = new CalculationSheetTankGroup();
              cstg.setId(calculationSheetTankGroup.getId());
              cstg.setTankGroup(calculationSheetTankGroup.getTankGroup());
              cstg.setLcg(calculationSheetTankGroup.getLcg());
              cstg.setFrameNumber(calculationSheetTankGroup.getFrameNumber());
              calculationSheetTankGroups.add(cstg);
            });
    return calculationSheetTankGroups;
  }

  /**
   * @param vesselAlgoReply
   * @param correlationId
   * @return List<CalculationSheet>
   */
  private List<CalculationSheet> createCalculationSheetResponse(
      VesselAlgoReply vesselAlgoReply, String correlationId) {
    List<CalculationSheet> calculationSheets = new ArrayList<CalculationSheet>();
    vesselAlgoReply
        .getBMAndSF()
        .getCalculationSheetList()
        .forEach(
            calculationSheet -> {
              CalculationSheet cs = new CalculationSheet();
              cs.setId(calculationSheet.getId());
              cs.setTankGroup(calculationSheet.getTankGroup());
              cs.setTankId(calculationSheet.getTankId());
              cs.setWeightRatio(calculationSheet.getWeightRatio());
              cs.setLcg(calculationSheet.getLcg());

              calculationSheets.add(cs);
            });
    return calculationSheets;
  }

  /**
   * @param vesselAlgoReply
   * @param correlationId
   * @return List<ShearingForce>
   */
  private List<ShearingForceType1> createShearingForceResponse(
      VesselAlgoReply vesselAlgoReply, String correlationId) {
    List<ShearingForceType1> shearingForceType1s = new ArrayList<ShearingForceType1>();
    vesselAlgoReply
        .getBMAndSF()
        .getShearingForceType1List()
        .forEach(
            shearingForce -> {
              ShearingForceType1 sf = new ShearingForceType1();
              sf.setId(shearingForce.getId());
              sf.setFrameNumber(shearingForce.getFrameNumber());
              sf.setId(shearingForce.getId());
              sf.setBaseValue(shearingForce.getBaseValue());
              sf.setBaseDraft(shearingForce.getBaseDraft());
              sf.setDraftCorrection(shearingForce.getDraftCorrection());
              sf.setTrimCorrection(shearingForce.getTrimCorrection());
              shearingForceType1s.add(sf);
            });
    return shearingForceType1s;
  }

  /**
   * @param vesselAlgoReply
   * @param correlationId
   * @return List<ShearingForce>
   */
  private List<ShearingForceType2> createShearingForceResponseType2(
      VesselAlgoReply vesselAlgoReply, String correlationId) {
    List<ShearingForceType2> shearingForces = new ArrayList<ShearingForceType2>();
    vesselAlgoReply
        .getBMAndSF()
        .getShearingForceType2List()
        .forEach(
            shearingForce -> {
              ShearingForceType2 sf = new ShearingForceType2();
              sf.setId(shearingForce.getId());
              sf.setFrameNumber(shearingForce.getFrameNumber());
              sf.setDisplacement(shearingForce.getDisplacement());
              sf.setBuay(shearingForce.getBuay());
              sf.setDifft(shearingForce.getDifft());
              sf.setCorrt(shearingForce.getCorrt());
              sf.setIsActive(shearingForce.getIsActive());
              shearingForces.add(sf);
            });
    return shearingForces;
  }

  /**
   * @param vesselAlgoReply
   * @param correlationId
   * @return List<ShearingForce>
   */
  private List<ShearingForceType4> createShearingForceResponseType4(
      VesselAlgoReply vesselAlgoReply, String correlationId) {
    List<ShearingForceType4> shearingForces = new ArrayList<ShearingForceType4>();
    vesselAlgoReply
        .getBMAndSF()
        .getShearingForceType4List()
        .forEach(
            shearingForce -> {
              ShearingForceType4 sf = new ShearingForceType4();
              sf.setId(shearingForce.getId());
              sf.setFrameNumber(shearingForce.getFrameNumber());
              sf.setTrim_m1(shearingForce.getTrimM1());
              sf.setTrim_0(shearingForce.getTrim0());
              sf.setTrim_1(shearingForce.getTrim1());
              sf.setTrim_2(shearingForce.getTrim2());
              sf.setTrim_3(shearingForce.getTrim3());
              sf.setTrim_4(shearingForce.getTrim4());
              sf.setTrim_5(shearingForce.getTrim5());
              sf.setIsActive(shearingForce.getIsActive());
              shearingForces.add(sf);
            });
    return shearingForces;
  }

  /**
   * @param vesselAlgoReply
   * @param correlationId
   * @return List<BendingMoment>
   */
  private List<BendingMomentType1> createBendingMomentResponse(
      VesselAlgoReply vesselAlgoReply, String correlationId) {
    List<BendingMomentType1> bendingMoments = new ArrayList<BendingMomentType1>();
    vesselAlgoReply
        .getBMAndSF()
        .getBendingMomentType1List()
        .forEach(
            bendingMoment -> {
              BendingMomentType1 bm = new BendingMomentType1();
              bm.setId(bendingMoment.getId());
              bm.setFrameNumber(bendingMoment.getFrameNumber());
              bm.setId(bendingMoment.getId());
              bm.setBaseValue(bendingMoment.getBaseValue());
              bm.setBaseDraft(bendingMoment.getBaseDraft());
              bm.setDraftCorrection(bendingMoment.getDraftCorrection());
              bm.setTrimCorrection(bendingMoment.getTrimCorrection());
              bendingMoments.add(bm);
            });
    return bendingMoments;
  }

  /**
   * @param vesselAlgoReply
   * @param correlationId
   * @return List<VesselTankTCG>
   */
  private List<VesselTankTCG> buildVesselTankTcgResponse(
      VesselAlgoReply vesselAlgoReply, String correlationId) {
    List<VesselTankTCG> vesselTankTCGs = new ArrayList<VesselTankTCG>();
    vesselAlgoReply
        .getVesselTankTCGList()
        .forEach(
            vesselTankTCG -> {
              VesselTankTCG tankTCG = new VesselTankTCG();
              tankTCG.setCapacity(vesselTankTCG.getCapacity());
              tankTCG.setId(vesselTankTCG.getId());
              tankTCG.setTankId(vesselTankTCG.getTankId());
              tankTCG.setTcg(vesselTankTCG.getTcg());
              tankTCG.setLcg(vesselTankTCG.getLcg());
              tankTCG.setVcg(vesselTankTCG.getVcg());
              tankTCG.setInertia(vesselTankTCG.getInertia());
              vesselTankTCGs.add(tankTCG);
            });
    return vesselTankTCGs;
  }

  /**
   * @param vesselAlgoReply
   * @param correlationId
   * @return List<HydrostaticData>
   */
  private List<HydrostaticData> buildHydrostaticResponse(
      VesselAlgoReply vesselAlgoReply, String correlationId) {

    List<HydrostaticData> hydrostaticDatas = new ArrayList<HydrostaticData>();
    vesselAlgoReply
        .getHydrostaticDataList()
        .forEach(
            data -> {
              HydrostaticData hydrostaticData = new HydrostaticData();
              hydrostaticData.setDisplacement(data.getDisplacement());
              hydrostaticData.setDraft(data.getDraft());
              hydrostaticData.setId(data.getId());
              hydrostaticData.setLcb(data.getLcb());
              hydrostaticData.setLcf(data.getLcf());
              hydrostaticData.setLkm(data.getLkm());
              hydrostaticData.setTkm(data.getTkm());
              hydrostaticData.setTrim(data.getTrim());
              hydrostaticData.setVcb(data.getVcb());
              hydrostaticData.setMtc(data.getMtc());
              hydrostaticDatas.add(hydrostaticData);
            });

    return hydrostaticDatas;
  }

  /**
   * @param vesselAlgoReply
   * @param correlationId
   * @return List<VesselTank>
   */
  private List<VesselTank> buildVesselTankListResponse(
      VesselAlgoReply vesselAlgoReply, String correlationId) {
    List<VesselTank> vesselTanks = new ArrayList<VesselTank>();
    vesselAlgoReply
        .getVesselTankDetailList()
        .forEach(
            vesselTank -> {
              VesselTank tank = new VesselTank();
              tank.setCategoryId(vesselTank.getTankCategoryId());
              tank.setCategoryName(vesselTank.getTankCategoryName());
              tank.setCoatingTypeId(vesselTank.getCoatingTypeId());
              tank.setDensity(
                  vesselTank.getDensity().equals("")
                      ? null
                      : new BigDecimal(vesselTank.getDensity()));
              tank.setFillCapcityCubm(
                  vesselTank.getFillCapacityCubm().equals("")
                      ? new BigDecimal(0)
                      : new BigDecimal(vesselTank.getFillCapacityCubm()));
              tank.setFullCapcityCubm(
                  vesselTank.getFullCapacityCubm().equals("")
                      ? null
                      : new BigDecimal(vesselTank.getFullCapacityCubm()));
              tank.setFrameNumberFrom(vesselTank.getFrameNumberFrom());
              tank.setFrameNumberTo(vesselTank.getFrameNumberTo());
              tank.setId(vesselTank.getTankId());
              tank.setLcg(vesselTank.getLcg());
              tank.setName(vesselTank.getTankName());
              tank.setShortName(vesselTank.getShortName());
              tank.setTcg(vesselTank.getTcg());
              tank.setVcg(vesselTank.getVcg());
              tank.setIsLoadicatorUsing(vesselTank.getIsLoadicatorUsing());
              vesselTanks.add(tank);
            });
    return vesselTanks;
  }

  /**
   * @param vesselAlgoReply
   * @param correlationId
   * @return List<VesselDraftCondition>
   */
  private List<VesselDraftCondition> buildVesselDraftconditionResponse(
      VesselAlgoReply vesselAlgoReply, String correlationId) {
    List<VesselDraftCondition> vesselDraftConditions = new ArrayList<VesselDraftCondition>();
    vesselAlgoReply
        .getVesselDraftConditionList()
        .forEach(
            vesselDraftCondition -> {
              VesselDraftCondition draftCondition = new VesselDraftCondition();
              draftCondition.setDeadWeight(vesselDraftCondition.getDeadWeight());
              draftCondition.setDepth(vesselDraftCondition.getDepth());
              draftCondition.setDisplacement(vesselDraftCondition.getDisplacement());
              draftCondition.setDraftConditionId(vesselDraftCondition.getDraftConditionId());
              draftCondition.setDraftExtreme(vesselDraftCondition.getDraftExtreme());
              draftCondition.setFreeboard(vesselDraftCondition.getFreeboard());
              draftCondition.setId(vesselDraftCondition.getId());
              vesselDraftConditions.add(draftCondition);
            });
    return vesselDraftConditions;
  }

  /**
   * @param vesselAlgoReply
   * @param correlationId
   * @return VesselDetailsResponse
   */
  private Vessel buildVesselDetailsResponse(VesselAlgoReply vesselAlgoReply, String correlationId) {
    Vessel vessel = new Vessel();
    vessel.setName(vesselAlgoReply.getVesselDetail().getName());
    vessel.setImoNumber(vesselAlgoReply.getVesselDetail().getImoNumber());
    vessel.setCode(vesselAlgoReply.getVesselDetail().getCode());
    vessel.setPortOfRegistry(vesselAlgoReply.getVesselDetail().getPortOfRegistry());
    vessel.setBuilder(vesselAlgoReply.getVesselDetail().getBuilder());
    vessel.setOfficialNumber(vesselAlgoReply.getVesselDetail().getOfficialNumber());
    vessel.setSignalLetter(vesselAlgoReply.getVesselDetail().getSignalLetter());
    vessel.setNavigationAreaId(vesselAlgoReply.getVesselDetail().getNavigationAreaId());
    vessel.setTypeOfShip(vesselAlgoReply.getVesselDetail().getTypeOfShip());
    vessel.setRegisterLength(vesselAlgoReply.getVesselDetail().getRegisterLength());
    vessel.setLengthOverall(vesselAlgoReply.getVesselDetail().getLengthOverall());
    vessel.setLengthBetweenPerpendiculars(
        vesselAlgoReply.getVesselDetail().getLengthBetweenPerpendiculars());
    vessel.setBreadthMolded(vesselAlgoReply.getVesselDetail().getBreadthMolded());
    vessel.setDepthMolded(vesselAlgoReply.getVesselDetail().getDepthMolded());
    vessel.setDesignedLoaddraft(vesselAlgoReply.getVesselDetail().getDesignedLoaddraft());
    vessel.setDraftFullLoadSummer(vesselAlgoReply.getVesselDetail().getDraftFullLoadSummer());
    vessel.setThicknessOfUpperDeckStringerPlate(
        vesselAlgoReply.getVesselDetail().getThicknessOfUpperDeckStringerPlate());
    vessel.setThicknessOfKeelplate(vesselAlgoReply.getVesselDetail().getThicknessOfKeelplate());
    vessel.setDeadweight(vesselAlgoReply.getVesselDetail().getDeadweight());
    vessel.setLightweight(vesselAlgoReply.getVesselDetail().getLightweight());
    vessel.setLcg(vesselAlgoReply.getVesselDetail().getLcg());
    vessel.setKeelToMastHeight(vesselAlgoReply.getVesselDetail().getKeelToMastHeight());
    vessel.setDeadweightConstant(vesselAlgoReply.getVesselDetail().getDeadweightConstant());
    vessel.setProvisionalConstant(vesselAlgoReply.getVesselDetail().getProvisionalConstant());
    vessel.setDeadweightConstantLcg(vesselAlgoReply.getVesselDetail().getDeadweightConstantLcg());
    vessel.setProvisionalConstantLcg(vesselAlgoReply.getVesselDetail().getProvisionalConstantLcg());
    vessel.setGrossTonnage(vesselAlgoReply.getVesselDetail().getGrossTonnage());
    vessel.setNetTonnage(vesselAlgoReply.getVesselDetail().getNetTonnage());
    vessel.setDeadweightConstantTcg(vesselAlgoReply.getVesselDetail().getDeadweightConstantTcg());
    vessel.setFrameSpace3l(vesselAlgoReply.getVesselDetail().getFrameSpace3L());
    vessel.setFrameSpace7l(vesselAlgoReply.getVesselDetail().getFrameSpace7L());
    vessel.setHasLoadicator(vesselAlgoReply.getVesselDetail().getHasLoadicator());
    vessel.setBmSfModelType(vesselAlgoReply.getVesselDetail().getBmSfModelType());
    vessel.setMaxLoadRate(vesselAlgoReply.getVesselDetail().getMaxLoadRate());
    vessel.setMastRiser(vesselAlgoReply.getVesselDetail().getMastRiser());
    vessel.setHeightOfManifoldAboveDeck(
        vesselAlgoReply.getVesselDetail().getHeightOfManifoldAboveDeck());
    return vessel;
  }

  /**
   * @param vesselAlgoRequest
   * @return VesselAlgoReply
   */
  public VesselAlgoReply getVesselsDetails(VesselAlgoRequest vesselAlgoRequest) {
    return vesselInfoGrpcService.getVesselDetailsForAlgo(vesselAlgoRequest);
  }

  public VesselInfo.VesselPumpsResponse getVesselPumpsFromVesselInfo(Long vesselId) {
    VesselInfo.VesselPumpsResponse response =
        this.vesselInfoGrpcService.getVesselPumpsByVesselId(
            VesselInfo.VesselIdRequest.newBuilder().setVesselId(vesselId).build());
    log.info("Vessel Info RPC call for Pump list, Vessel Id {}", vesselId);
    if (response.getResponseStatus().getStatus().equalsIgnoreCase(SUCCESS)) {
      return response;
    } else {
      log.error("Vessel Info RPC call Failed, {}", response.getResponseStatus().getMessage());
      return null;
    }
  }

  /**
   * To retrieve vessel rule OR To save rule for vessel
   *
   * @param vesselId
   * @param sectionId
   * @param vesselRuleRequest
   * @param correlationId
   * @return
   * @throws GenericServiceException
   */
  public RuleResponse getRulesByVesselIdAndSectionId(
      Long vesselId,
      Long sectionId,
      com.cpdss.gateway.domain.RuleRequest vesselRuleRequest,
      String correlationId)
      throws GenericServiceException {
    VesselRuleRequest.Builder vesselRuleBuilder = VesselRuleRequest.newBuilder();
    vesselRuleBuilder.setSectionId(sectionId);
    vesselRuleBuilder.setVesselId(vesselId);
    vesselRuleBuilder.setIsNoDefaultRule(false);
    vesselRuleBuilder.setIsFetchEnabledRules(false);
    RuleUtility.buildRuleListForSave(vesselRuleRequest, vesselRuleBuilder, null, null, true, false);
    VesselRuleReply vesselRuleReply =
        this.vesselInfoGrpcService.getRulesByVesselIdAndSectionId(vesselRuleBuilder.build());
    RuleResponse ruleResponse = new RuleResponse();
    if (!SUCCESS.equals(vesselRuleReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "failed to get or save Vessel rules ",
          vesselRuleReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(vesselRuleReply.getResponseStatus().getCode())));
    }
    ruleResponse.setPlan(RuleUtility.buildAdminRulePlan(vesselRuleReply));
    ruleResponse.setResponseStatus(
        new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
    return ruleResponse;
  }

  public VesselDetail getVesselInfoByVesselId(Long vesselId) {
    VesselInfo.VesselIdRequest builder =
        VesselInfo.VesselIdRequest.newBuilder().setVesselId(vesselId).build();
    VesselInfo.VesselIdResponse response = vesselInfoGrpcService.getVesselInfoByVesselId(builder);
    if (response.getResponseStatus().getStatus().equals(SUCCESS)) {
      log.info("Fetched vessel details by vessel Id {}", vesselId);
      return response.getVesselDetail();
    }
    log.info("No data found for vessel Id {}", vesselId);
    return null;
  }

  private List<PumpType> buildPumpTypes(VesselAlgoReply reply) {
    List<PumpType> pumpTypeList = new ArrayList<>();
    reply.getPumpTypeList().stream()
        .forEach(
            pump -> {
              PumpType pumpType = new PumpType();
              pumpType.setId(pump.getId());
              pumpType.setName(pump.getName());
              pumpTypeList.add(pumpType);
            });
    return pumpTypeList;
  }

  private List<VesselPumpResponse> buildVesselPumps(VesselAlgoReply reply) {
    List<VesselPumpResponse> pumpList = new ArrayList<>();
    reply.getVesselPumpList().stream()
        .forEach(
            vesselPump -> {
              VesselPumpResponse pump = new VesselPumpResponse();
              pump.setPumpId(vesselPump.getId());
              pump.setVesselId(vesselPump.getVesselId());
              pump.setPumpCapacity(
                  !vesselPump.getPumpCapacity().isEmpty()
                      ? new BigDecimal(vesselPump.getPumpCapacity())
                      : null);
              pump.setPumpCode(vesselPump.getPumpCode());
              pump.setPumpName(vesselPump.getPumpName());
              pump.setPumpTypeId(vesselPump.getPumpTypeId());
              pump.setMachineType(Common.MachineType.MANIFOLD_VALUE);
              pumpList.add(pump);
            });
    return pumpList;
  }

  private List<TankType> buildTankTypes(VesselAlgoReply vesselAlgoReply) {
    List<TankType> tankTypeList = new ArrayList<>();
    vesselAlgoReply.getTankTypeList().stream()
        .forEach(
            tankType -> {
              TankType type = new TankType();
              type.setId(tankType.getId());
              type.setTypeName(tankType.getTypeName());
              tankTypeList.add(type);
            });
    return tankTypeList;
  }

  private List<VesselManiFold> buildVesselManifold(VesselAlgoReply vesselAlgoReply) {
    List<VesselManiFold> maniFoldList = new ArrayList<>();
    vesselAlgoReply.getVesselManifoldList().stream()
        .forEach(
            vesselManifold -> {
              VesselManiFold maniFold = new VesselManiFold();
              maniFold.setManiFoldId(vesselManifold.getId());
              maniFold.setVesselId(vesselManifold.getVesselId());
              maniFold.setComponentCode(vesselManifold.getComponentCode());
              maniFold.setComponentName(vesselManifold.getComponentName());
              maniFold.setComponentType(vesselManifold.getComponentType());
              maniFold.setMachineTypeId(Common.MachineType.MANIFOLD_VALUE);
              maniFoldList.add(maniFold);
            });
    return maniFoldList;
  }

  private List<VesselBottomLine> buildVesselBottomLine(VesselAlgoReply vesselAlgoReply) {
    List<VesselBottomLine> bottomLineList = new ArrayList<>();
    vesselAlgoReply.getVesselBottomLineList().stream()
        .forEach(
            bottomLineReply -> {
              VesselBottomLine bottomLine = new VesselBottomLine();
              bottomLine.setBottomLineId(bottomLineReply.getId());
              bottomLine.setVesselId(bottomLineReply.getVesselId());
              bottomLine.setComponentCode(bottomLineReply.getComponentCode());
              bottomLine.setComponentName(bottomLineReply.getComponentName());
              bottomLine.setComponentType(bottomLineReply.getComponentType());
              bottomLine.setMachineTypeId(Common.MachineType.MANIFOLD_VALUE);
              bottomLineList.add(bottomLine);
            });
    return bottomLineList;
  }

  public VesselsInfoResponse getAllVesselsInormation(
      int pageSize,
      int pageNo,
      String sortBy,
      String orderBy,
      String vesselName,
      String vesselType,
      String builder,
      String dateOfLaunch,
      String correlationId)
      throws GenericServiceException {
    Builder newBuilder = VesselsInfoRequest.newBuilder();
    if (dateOfLaunch != null) newBuilder.setDateOfLaunch(dateOfLaunch);
    if (orderBy != null) newBuilder.setOrderBy(orderBy);
    if (sortBy != null) newBuilder.setSortBy(sortBy);
    if (vesselName != null) newBuilder.setVesselName(vesselName);
    if (vesselType != null) newBuilder.setVesselType(vesselType);
    if (builder != null) newBuilder.setBuilder(builder);

    newBuilder.setPageNo(pageNo);
    newBuilder.setPageSize(pageSize);
    VesselsInformationReply vesselsInformationReply =
        vesselInfoGrpcService.getVesselsInformation(newBuilder.build());
    if (!SUCCESS.equals(vesselsInformationReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "failed to get all vessel information ",
          vesselsInformationReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(
              Integer.valueOf(vesselsInformationReply.getResponseStatus().getCode())));
    }
    List<VesselInformation> vesselInfoList = new ArrayList<>();
    VesselsInfoResponse response = new VesselsInfoResponse();
    response.setResponseStatus(
        new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
    List<VesselsInformation> vesselsInformationList =
        vesselsInformationReply.getVesselsInformationList();
    vesselsInformationList.forEach(
        item -> {
          VesselInformation info = new VesselInformation();
          info.setBuilder(item.getBuilder());
          info.setDateOfLaunching(item.getDateOfLaunch());
          info.setOfficialNumber(item.getOfficialNumber());
          info.setSignalLetter(item.getSignalLetter());
          info.setVesselId(item.getVesselId());
          info.setName(item.getVesselName());
          info.setTypeOfShip(item.getVesselType());
          vesselInfoList.add(info);
        });
    response.setVesselsInfo(vesselInfoList);
    response.setTotalElements(vesselsInformationReply.getTotalElement());
    return response;
  }

  public VesselParticularsForExcel getVesselParticulars(Long vesselId)
      throws NumberFormatException, GenericServiceException {
    log.info("Getting vessel particulars for vessel id : {}", vesselId);
    VesselParticularsForExcel vesselParticulars = new VesselParticularsForExcel();
    LoadingInfoRulesRequest.Builder request = LoadingInfoRulesRequest.newBuilder();
    request.setVesselId(vesselId);
    VesselParticulars reply = vesselInfoGrpcService.getVesselParticulars(request.build());
    if (!SUCCESS.equals(reply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "GRPC call failed to fetch vessels particulars",
          reply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(Integer.valueOf(reply.getResponseStatus().getCode())));
    }
    vesselParticulars.setVesselId(vesselId);
    Optional.ofNullable(reply.getVesselTypeId()).ifPresent(vesselParticulars::setVesselTypeId);
    Optional.ofNullable(reply.getShipMaxLoadingRate())
        .ifPresent(vesselParticulars::setShipMaxLoadingRate);
    Optional.ofNullable(reply.getShipMaxFlowRate())
        .ifPresent(vesselParticulars::setShipMaxFlowRate);
    Optional.ofNullable(reply.getShipMaxFlowRatePerTank())
        .ifPresent(vesselParticulars::setShipMaxFlowRatePerTank);
    Optional.ofNullable(reply.getMaxLoadingRateSlopP())
        .ifPresent(vesselParticulars::setMaxLoadingRateSlopP);
    Optional.ofNullable(reply.getMaxLoadingRateSlopS())
        .ifPresent(vesselParticulars::setMaxLoadingRateSlopS);
    Optional.ofNullable(reply.getBallastPumpCount())
        .ifPresent(vesselParticulars::setBallastPumpCount);
    Optional.ofNullable(reply.getCapacityPerPump())
        .ifPresent(vesselParticulars::setCapacityPerPump);
    Optional.ofNullable(reply.getShipManifold()).ifPresent(vesselParticulars::setShipManifold);
    Optional.ofNullable(reply.getSummerDraft()).ifPresent(vesselParticulars::setSummerDraft);
    Optional.ofNullable(reply.getFreshMLD()).ifPresent(vesselParticulars::setFreshWaterDraft);
    Optional.ofNullable(reply.getTropicalDraft()).ifPresent(vesselParticulars::setTropicalDraft);
    Optional.ofNullable(reply.getSummerDeadweight())
        .ifPresent(vesselParticulars::setSummerDeadweight);
    Optional.ofNullable(reply.getSummerDisplacement())
        .ifPresent(vesselParticulars::setSummerDisplacement);
    Optional.ofNullable(reply.getCargoTankCapacity())
        .ifPresent(vesselParticulars::setCargoTankCapacity);
    Optional.ofNullable(reply.getHighVelocityVentingPressure())
        .ifPresent(vesselParticulars::setHighVelocityVentingPressure);
    Optional.ofNullable(reply.getHighVelocityVentingVaccum())
        .ifPresent(vesselParticulars::setHighVelocityVentingVaccum);
    Optional.ofNullable(reply.getPvBreakerVentingPressure())
        .ifPresent(vesselParticulars::setPvBreakerVentingPressure);
    Optional.ofNullable(reply.getPvBreakerVentingVaccum())
        .ifPresent(vesselParticulars::setPvBreakerVentingVaccum);
    log.info("vessel particulars fetched succesfully");
    return vesselParticulars;
  }

  /**
   * Get vessel detailed information
   *
   * @param vesselId
   * @param correlationId
   * @return VesselDetailedInfoResponse
   * @throws GenericServiceException
   */
  public VesselDetailedInfoResponse getVesselDetaildInformation(Long vesselId, String correlationId)
      throws GenericServiceException {

    com.cpdss.common.generated.VesselInfo.VesselIdRequest.Builder builder =
        VesselIdRequest.newBuilder();
    builder.setVesselId(vesselId);
    VesselDetaildInfoReply vesselDetaildInfoReply =
        vesselInfoGrpcService.getVesselDetaildInformation(builder.build());
    if (!SUCCESS.equals(vesselDetaildInfoReply.getResponseStatus().getStatus())) {
      throw new GenericServiceException(
          "failed to get vessel detailed information ",
          vesselDetaildInfoReply.getResponseStatus().getCode(),
          HttpStatusCode.valueOf(
              Integer.valueOf(vesselDetaildInfoReply.getResponseStatus().getCode())));
    }

    VesselDetailedInfoResponse response = new VesselDetailedInfoResponse();
    response.setResponseStatus(
        new CommonSuccessResponse(String.valueOf(HttpStatus.OK.value()), correlationId));
    String vesselName = vesselDetaildInfoReply.getVesselName();
    response.setVesselId(vesselDetaildInfoReply.getVesselId());
    response.setVesselName(vesselDetaildInfoReply.getVesselName());
    response.setImoNumber(vesselDetaildInfoReply.getImoNumber());
    response.setCountryFlagUrl("assets/images/flags/japan.png");
    response.setVesselImageUrl("assets/images/vessels/" + vesselName.toLowerCase() + "/" + vesselName.toLowerCase() + ".jpg");
    setGeneralVesselInformation(vesselDetaildInfoReply, response);
    setVesselDimensionInfo(vesselDetaildInfoReply, response);
    VesselDraftDisplacementResponse draftResponse = new VesselDraftDisplacementResponse();
    draftResponse.setThicknessOfKeelPlate(vesselDetaildInfoReply.getThicknessOfKeelPlate());
    draftResponse.setThicknessOfUpperDeck(vesselDetaildInfoReply.getThicknessOfUpperDeck());
    draftResponse.setTotalDepth(vesselDetaildInfoReply.getTotalDepth());
    draftResponse.setDepthMoulded(vesselDetaildInfoReply.getDepthMoulded());
    response.setDraftDisplacementDeadweight(draftResponse);
    response.setBunkerRearTanks(
        setVesselTankLayoutDetails(vesselDetaildInfoReply.getBunkerRearTanksList(), response));
    response.setBunkerTanks(
        setVesselTankLayoutDetails(vesselDetaildInfoReply.getBunkerTanksList(), response));
    response.setBallastFrontTanks(
        setVesselTankLayoutDetails(vesselDetaildInfoReply.getBallastFrontTanksList(), response));
    response.setBallastCenterTanks(
        setVesselTankLayoutDetails(vesselDetaildInfoReply.getBallastCenterTanksList(), response));
    response.setBallastRearTanks(
        setVesselTankLayoutDetails(vesselDetaildInfoReply.getBallastRearTanksList(), response));
    response.setCargoTanks(
        setVesselTankLayoutDetails(vesselDetaildInfoReply.getCargoTanksList(), response));
    return response;
  }

  /**
   * Set general vessel information category
   *
   * @param vesselDetaildInfoReply
   * @param response
   */
  private void setGeneralVesselInformation(
      VesselDetaildInfoReply vesselDetaildInfoReply, VesselDetailedInfoResponse response) {
    VesselGeneralInfoResponse generalResponse = new VesselGeneralInfoResponse();
    generalResponse.setBuilder(vesselDetaildInfoReply.getBuilder());
    generalResponse.setDateOfDelivery(vesselDetaildInfoReply.getDateOfDelivery());
    generalResponse.setDateOfKeelLaid(vesselDetaildInfoReply.getDateOfKeelLaid());
    generalResponse.setDateOfLaunch(vesselDetaildInfoReply.getDateOfLaunch());
    generalResponse.setNavigationArea(vesselDetaildInfoReply.getNavigationArea());
    generalResponse.setOfficialNumber(vesselDetaildInfoReply.getOfficialNumber());
    generalResponse.setSignalLetter(vesselDetaildInfoReply.getSignalLetter());
    generalResponse.setVesselClass(vesselDetaildInfoReply.getClass_());
    generalResponse.setVesselType(vesselDetaildInfoReply.getVesselType());
    response.setGeneralInfo(generalResponse);
  }

  /**
   * Set vessel dimension information category
   *
   * @param vesselDetaildInfoReply
   * @param response
   */
  private void setVesselDimensionInfo(
      VesselDetaildInfoReply vesselDetaildInfoReply, VesselDetailedInfoResponse response) {
    VesselDimensionResponse dimensionResponse = new VesselDimensionResponse();
    dimensionResponse.setBreadthMoulded(vesselDetaildInfoReply.getBreadthMoulded());
    dimensionResponse.setDesignedLoadDraft(vesselDetaildInfoReply.getDesignedLoadDraft());
    dimensionResponse.setDraftFullLoad(vesselDetaildInfoReply.getDraftFullLoad());
    dimensionResponse.setLengthBetweenPerpendiculars(
        vesselDetaildInfoReply.getLengthBetweenPerpendiculars());
    dimensionResponse.setLengthOverall(vesselDetaildInfoReply.getLengthOverall());
    dimensionResponse.setRegisterLength(vesselDetaildInfoReply.getRegisterLength());
    response.setVesselDimesnsions(dimensionResponse);
  }

  /**
   * Set vessel bunker, ballast, cargo tank layout datas
   * @param tanksList
   * @param response
   * @return
   */
  private List<List<VesselTankInformation>> setVesselTankLayoutDetails(List<VesselTankInfo> tanksList, VesselDetailedInfoResponse response) {
	  
	  List<List<VesselTankInformation>> tanks = new ArrayList<>();
	  List<VesselTankInformation> tankGroup = null;
	  Map<Integer, List<VesselTankInformation>> tankInfoMap = new HashMap<>();
	  for(VesselTankInfo tankDetail:tanksList) {
		  if(tankInfoMap.get(tankDetail.getTankGroup())==null) {
			  tankGroup = new ArrayList<>();
		  }else {
			  tankGroup = tankInfoMap.get(tankDetail.getTankGroup());
		  }
		  VesselTankInformation tank = new VesselTankInformation();
		  tank.setCategoryId(tankDetail.getTankCategoryId());
		  tank.setCategoryName(tankDetail.getTankCategoryName());
		  tank.setDensity(tankDetail.getDensity());
		  tank.setFrameNumberFrom(tankDetail.getFrameNumberFrom());
		  tank.setFrameNumberTo(tankDetail.getFrameNumberTo());
		  tank.setFullCapacityCubm(tankDetail.getFullCapacityCubm());
		  tank.setGroup(tankDetail.getTankGroup());
		  tank.setId(tankDetail.getTankId());
		  tank.setName(tankDetail.getTankName());
		  tank.setOrder(tankDetail.getTankOrder());
		  tank.setShortName(tankDetail.getShortName());
		  tank.setSlopTank(tankDetail.getIsSlopTank());
		  tankGroup.add(tank);
		  tankInfoMap.put(tankDetail.getTankGroup(), tankGroup);
	  }
	  for (Map.Entry<Integer, List<VesselTankInformation>> entry : tankInfoMap.entrySet()) {
		  
		  List<VesselTankInformation> list = entry.getValue();
		  Collections.sort(list, Comparator.comparing(VesselTankInformation::getOrder));
		  tanks.add(list);
	  }
	  return tanks;
	  
  }
}
