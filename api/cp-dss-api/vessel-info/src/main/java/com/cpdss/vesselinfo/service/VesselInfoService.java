/* Licensed under Apache-2.0 */
package com.cpdss.vesselinfo.service;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.VesselInfo.LoadLineDetail;
import com.cpdss.common.generated.VesselInfo.VesselDetail;
import com.cpdss.common.generated.VesselInfo.VesselLoadableQuantityDetails;
import com.cpdss.common.generated.VesselInfo.VesselReply;
import com.cpdss.common.generated.VesselInfo.VesselRequest;
import com.cpdss.common.generated.VesselInfo.VesselTankDetail;
import com.cpdss.common.generated.VesselInfoServiceGrpc.VesselInfoServiceImplBase;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.common.utils.HttpStatusCode;
import com.cpdss.vesselinfo.domain.VesselDetails;
import com.cpdss.vesselinfo.entity.TankCategory;
import com.cpdss.vesselinfo.entity.Vessel;
import com.cpdss.vesselinfo.entity.VesselChartererMapping;
import com.cpdss.vesselinfo.entity.VesselDraftCondition;
import com.cpdss.vesselinfo.entity.VesselTank;
import com.cpdss.vesselinfo.repository.HydrostaticTableRepository;
import com.cpdss.vesselinfo.repository.TankCategoryRepository;
import com.cpdss.vesselinfo.repository.VesselChartererMappingRepository;
import com.cpdss.vesselinfo.repository.VesselRepository;
import com.cpdss.vesselinfo.repository.VesselTankRepository;
import io.grpc.stub.StreamObserver;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Vessel info grpc service class
 *
 * @author suhail.k
 */
@GrpcService
@Log4j2
@Transactional
public class VesselInfoService extends VesselInfoServiceImplBase {

  @Autowired private VesselRepository vesselRepository;
  @Autowired private VesselChartererMappingRepository chartererMappingRepository;
  @Autowired private TankCategoryRepository tankCategoryRepository;
  @Autowired private VesselTankRepository vesselTankRepository;
  @Autowired private HydrostaticTableRepository hydrostaticTableRepository;

  private static final String SUCCESS = "SUCCESS";
  private static final String FAILED = "FAILED";

  private static final Long FRESH_WATER_TANK_CATEGORY_ID = 3L;
  private static final Long FUEL_OIL_TANK_CATEGORY_ID = 5L;
  private static final Long DIESEL_OIL_TANK_CATEGORY_ID = 6L;
  private static final Long LUBRICATING_OIL_TANK_CATEGORY_ID = 14L;
  private static final Long LUBRICANT_OIL_TANK_CATEGORY_ID = 19L;
  private static final Long CARGO_TANK_CATEGORY_ID = 1L;
  private static final Long CARGO_SLOP_TANK_CATEGORY_ID = 9L;

  private static final List<Long> OHQ_TANK_CATEGORIES =
      Arrays.asList(
          FRESH_WATER_TANK_CATEGORY_ID,
          FUEL_OIL_TANK_CATEGORY_ID,
          DIESEL_OIL_TANK_CATEGORY_ID,
          LUBRICATING_OIL_TANK_CATEGORY_ID,
          LUBRICANT_OIL_TANK_CATEGORY_ID);

  private static final List<Long> CARGO_TANK_CATEGORIES =
	      Arrays.asList(
	    		  CARGO_TANK_CATEGORY_ID,
	          CARGO_SLOP_TANK_CATEGORY_ID);
  
  /** Get vessel for a company */
  @Override
  public void getAllVesselsByCompany(
      VesselRequest request, StreamObserver<VesselReply> responseObserver) {
    log.info("inside grpc service: getAllVesselsByCompany");
    VesselReply.Builder replyBuilder = VesselReply.newBuilder();
    try {
      List<Vessel> vesselEntities = this.vesselRepository.findByIsActive(true);
      for (Vessel entity : vesselEntities) {
        VesselDetail.Builder builder = VesselDetail.newBuilder();
        builder.setId(entity.getId());
        Optional.ofNullable(entity.getChiefofficerId()).ifPresent(builder::setCheifOfficerId);
        Optional.ofNullable(entity.getImoNumber()).ifPresent(builder::setImoNumber);
        Optional.ofNullable(entity.getMasterId()).ifPresent(builder::setCaptainId);
        Optional.ofNullable(entity.getName()).ifPresent(builder::setName);
        Optional.ofNullable(entity.getVesselFlag())
            .ifPresent(flag -> builder.setFlag(flag.getFlagImagePath()));
        Set<VesselDraftCondition> draftConditions = entity.getVesselDraftConditionCollection();
        List<LoadLineDetail.Builder> builderList = new ArrayList<>();
        Map<Long, Integer> indexMap = new HashMap<>();
        int index = 0;
        for (VesselDraftCondition condition : draftConditions) {
          if (null == indexMap.get(condition.getDraftCondition().getId())) {
            LoadLineDetail.Builder loadLineBuilder = LoadLineDetail.newBuilder();
            loadLineBuilder.setId(condition.getDraftCondition().getId());
            loadLineBuilder.setName(condition.getDraftCondition().getName());
            loadLineBuilder.addDraftMarks(String.valueOf(condition.getDraftExtreme()));
            builderList.add(loadLineBuilder);
            indexMap.put(condition.getDraftCondition().getId(), index);
            index++;
          } else {
            builderList
                .get(indexMap.get(condition.getDraftCondition().getId()))
                .addDraftMarks(String.valueOf(condition.getDraftExtreme()));
          }
        }
        builder.addAllLoadLines(
            builderList.stream().map(LoadLineDetail.Builder::build).collect(Collectors.toList()));
        VesselChartererMapping vesselChartererMapping =
            this.chartererMappingRepository.findByVesselAndIsActive(entity, true);
        if (null != vesselChartererMapping && null != vesselChartererMapping.getCharterer()) {
          builder.setCharterer(vesselChartererMapping.getCharterer().getName());
        }
        replyBuilder.addVessels(builder.build());
        replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
      }

    } catch (Exception e) {
      log.error("Exception when fetching vessel info", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage(null != e.getMessage() ? e.getMessage() : "")
              .setStatus(FAILED)
              .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /**
   * @param request
   * @param responseObserver
   */
  @Override
  public void getVesselDetailsById(
      VesselRequest request, StreamObserver<VesselReply> responseObserver) {
    VesselReply.Builder replyBuilder = VesselReply.newBuilder();
    try {
      log.info("inside grpc service: getVesselDetailsById");
      VesselDetails vesselDetails =
          vesselRepository.findVesselDetailsById(
              request.getVesselId(),
              request.getVesselDraftConditionId(),
              new BigDecimal(request.getDraftExtreme()));

      VesselLoadableQuantityDetails.Builder builder = VesselLoadableQuantityDetails.newBuilder();
      List<BigDecimal> tpc =
          hydrostaticTableRepository.getTPCFromDraf(
              request.getVesselId(), new BigDecimal(request.getDraftExtreme()), true);
      if (null != vesselDetails) {
        Optional.ofNullable(vesselDetails.getDisplacmentDraftRestriction().toString())
            .ifPresent(builder::setDisplacmentDraftRestriction);
        Optional.ofNullable(vesselDetails.getVesselLightWeight())
            .ifPresent(builder::setVesselLightWeight);
        Optional.ofNullable(vesselDetails.getConstant()).ifPresent(builder::setConstant);
      }
      if (!tpc.isEmpty()) {
        Optional.ofNullable(tpc.get(0))
            .ifPresent(tpcValue -> builder.setTpc(String.valueOf(tpcValue)));
      }
      replyBuilder.setVesselLoadableQuantityDetails(builder.build());
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder().setStatus(SUCCESS).setMessage(SUCCESS).build());
    } catch (Exception e) {
      log.error("Exception when fetching vessel details by id ", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage(FAILED)
              .setStatus(FAILED)
              .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  /** Get fuel tanks for a vessel by its id */
  @Override
  public void getVesselFuelTanks(
      VesselRequest request, StreamObserver<VesselReply> responseObserver) {
    VesselReply.Builder replyBuilder = VesselReply.newBuilder();
    try {
      Vessel vessel = this.vesselRepository.findByIdAndIsActive(request.getVesselId(), true);
      if (null == vessel) {
        throw new GenericServiceException(
            "Vessel with given id does not exist",
            CommonErrorCodes.E_HTTP_BAD_REQUEST,
            HttpStatusCode.BAD_REQUEST);
      }
      List<TankCategory> tankCaetgoryEntities = new ArrayList<>();
      OHQ_TANK_CATEGORIES.forEach(
          tankCategoryId ->
              tankCaetgoryEntities.add(this.tankCategoryRepository.getOne(tankCategoryId)));
      List<VesselTank> vesselTanks =
          this.vesselTankRepository.findByVesselAndTankCategoryInAndIsActive(
              vessel, tankCaetgoryEntities, true);
      for (VesselTank tank : vesselTanks) {
        VesselTankDetail.Builder builder = VesselTankDetail.newBuilder();
        builder.setTankId(tank.getId());
        builder.setTankCategoryId(tank.getTankCategory().getId());
        builder.setTankCategoryName(tank.getTankCategory().getName());
        builder.setShortName(tank.getShortName());
        builder.setFrameNumberFrom(tank.getFrameNumberFrom());
        builder.setFrameNumberTo(tank.getFrameNumberTo());
        builder.setTankName(tank.getTankName());
        Optional.ofNullable(tank.getHeightFrom()).ifPresent(builder::setHeightFrom);
        Optional.ofNullable(tank.getHeightTo()).ifPresent(builder::setHeightTo);
        Optional.ofNullable(tank.getFillCapacityCubm())
            .ifPresent(
                capacity ->
                    builder.setFillCapacityCubm(String.valueOf(tank.getFillCapacityCubm())));
        Optional.ofNullable(tank.getDensity())
            .ifPresent(density -> builder.setDensity(String.valueOf(tank.getDensity())));
        Optional.ofNullable(tank.getTankGroup()).ifPresent(builder::setTankGroup);
        Optional.ofNullable(tank.getTankOrder()).ifPresent(builder::setTankOrder);
        Optional.ofNullable(tank.getIsSlopTank()).ifPresent(builder::setIsSlopTank);
        replyBuilder.addVesselTanks(builder.build());
      }
      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when fetching fuel tanks", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(e.getCode())
              .setMessage("GenericServiceException when fetching fuel tanks")
              .setStatus(FAILED)
              .build());
    } catch (Exception e) {
      log.error("Exception when fetching fuel tanks", e);
      replyBuilder.setResponseStatus(
          ResponseStatus.newBuilder()
              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
              .setMessage("Exception when fetching fuel tanks")
              .setStatus(FAILED)
              .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }
  
  /** 
   * Retrieve vessel cargo tanks for a vessel-id
   */
  @Override
  public void getVesselCargoTanks(
      VesselRequest request, StreamObserver<VesselReply> responseObserver) {
	  VesselReply.Builder replyBuilder = VesselReply.newBuilder();
	    try {
	      Vessel vesselEntity =
	          this.vesselRepository.findByIdAndIsActive(
	              request.getVesselId(), true);
	      if (vesselEntity == null) {
	        throw new GenericServiceException(
	            "Vessel with given id does not exist",
	            CommonErrorCodes.E_HTTP_BAD_REQUEST,
	            HttpStatusCode.BAD_REQUEST);
	      }
	      List<TankCategory> tankCategoryEntities = new ArrayList<>();
	      CARGO_TANK_CATEGORIES.forEach(
	          tankCategoryId ->
	          tankCategoryEntities.add(this.tankCategoryRepository.getOne(tankCategoryId)));
	      List<VesselTank> vesselTanks =
	          this.vesselTankRepository.findByVesselAndTankCategoryInAndIsActive(
	        		  vesselEntity, tankCategoryEntities, true);
	      for (VesselTank tank : vesselTanks) {
	        VesselTankDetail.Builder builder = VesselTankDetail.newBuilder();
	        builder.setTankId(tank.getId());
	        builder.setTankName(tank.getTankName());
	        builder.setShortName(tank.getShortName());
//	        builder.setTankCategoryId(tank.getTankCategory().getId());
//	        builder.setTankCategoryName(tank.getTankCategory().getName());
//	        builder.setFrameNumberFrom(tank.getFrameNumberFrom());
//	        builder.setFrameNumberTo(tank.getFrameNumberTo());
	        replyBuilder.addVesselTanks(builder.build());
	      }
	      replyBuilder.setResponseStatus(ResponseStatus.newBuilder().setStatus(SUCCESS).build());
	    } catch (GenericServiceException e) {
	      log.error("GenericServiceException when fetching cargo tanks", e);
	      replyBuilder.setResponseStatus(
	          ResponseStatus.newBuilder()
	              .setCode(e.getCode())
	              .setMessage("GenericServiceException when fetching cargo tanks")
	              .setStatus(FAILED)
	              .build());
	    } catch (Exception e) {
	      log.error("Exception when fetching cargo tanks", e);
	      replyBuilder.setResponseStatus(
	          ResponseStatus.newBuilder()
	              .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
	              .setMessage("Exception when fetching cargo tanks")
	              .setStatus(FAILED)
	              .build());
	    } finally {
	      responseObserver.onNext(replyBuilder.build());
	      responseObserver.onCompleted();
	    }
  }
}
