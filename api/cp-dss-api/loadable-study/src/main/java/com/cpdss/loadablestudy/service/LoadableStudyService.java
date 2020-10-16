/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.service;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.cpdss.common.exception.GenericServiceException;
import com.cpdss.common.generated.LoadableStudy.LoadableQuantityReply;
import com.cpdss.common.generated.LoadableStudy.LoadableQuantityRequest;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyAttachment;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyReply;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyReply.Builder;
import com.cpdss.common.generated.LoadableStudy.LoadableStudyRequest;
import com.cpdss.common.generated.LoadableStudy.StatusReply;
import com.cpdss.common.generated.LoadableStudy.VoyageReply;
import com.cpdss.common.generated.LoadableStudy.VoyageRequest;
import com.cpdss.common.generated.LoadableStudyServiceGrpc.LoadableStudyServiceImplBase;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.loadablestudy.entity.LoadableQuantity;
import com.cpdss.loadablestudy.entity.LoadableStudy;
import com.cpdss.loadablestudy.entity.LoadableStudyAttachments;
import com.cpdss.loadablestudy.entity.Voyage;
import com.cpdss.loadablestudy.repository.LoadableQuantityRepository;
import com.cpdss.loadablestudy.repository.LoadableStudyRepository;
import com.cpdss.loadablestudy.repository.VoyageRepository;

import io.grpc.internal.GrpcUtil.Http2Error;
import io.grpc.stub.StreamObserver;
import java.math.BigDecimal;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.server.service.GrpcService;

/** @Author jerin.g */
@Log4j2
@GrpcService
@Service
@Transactional
public class LoadableStudyService extends LoadableStudyServiceImplBase {

  @Value("${loadablestudy.attachement.path}")
  private String uploadFolderPath;

  @Autowired private VoyageRepository voyageRepository;

  @Autowired private LoadableStudyRepository loadableStudyRepository;

  @Autowired private LoadableQuantityRepository loadableQuantityRepository;

  private static final String SUCCESS = "SUCCESS";
  private static final String FAILED = "FAILED";
  private static final String VOYAGEEXISTS = "VOYAGEEXISTS";
  private static final String CREATED_DATE_FORMAT = "dd-MM-yyyy";

  /**
   * method for save voyage
   *
   * @param request - voyage request details
   * @param responseObserver - grpc class
   * @return
   */
  @Override
  public void saveVoyage(VoyageRequest request, StreamObserver<VoyageReply> responseObserver) {
    VoyageReply reply = null;
    try {

      // validation for duplicate voyages
      if (!voyageRepository
          .findByCompanyXIdAndVesselXIdAndVoyageNo(
              request.getCompanyId(), request.getVesselId(), request.getVoyageNo())
          .isEmpty()) {
        reply = VoyageReply.newBuilder().setMessage(VOYAGEEXISTS).setStatus(SUCCESS).build();
      } else {

        Voyage voyage = new Voyage();
        voyage.setIsActive(true);
        voyage.setCompanyXId(request.getCompanyId());
        voyage.setVesselXId(request.getVesselId());
        voyage.setVoyageNo(request.getVoyageNo());
        voyage.setCaptainXId(request.getCaptainId());
        voyage.setChiefOfficerXId(request.getChiefOfficerId());
        voyage = voyageRepository.save(voyage);
        // when Db save is complete we return to client a success message
        reply =
            VoyageReply.newBuilder()
                .setMessage(SUCCESS)
                .setStatus(SUCCESS)
                .setVoyageId(voyage.getId())
                .build();
      }
    } catch (Exception e) {

      log.error("Error in saving Voyage ", e);
      reply = VoyageReply.newBuilder().setMessage("FAIL").setStatus("FAIL").build();

    } finally {
      responseObserver.onNext(reply);
      responseObserver.onCompleted();
    }
  }

	  /**
	   * method to save loadable quantity
	   *
	   * @param loadableQuantityRequest
	   * @param responseObserver
	   * @throws Exception
	   * @return void
	   */
	  @Override
	  public void saveLoadableQuantity(
	      LoadableQuantityRequest loadableQuantityRequest,
	      StreamObserver<LoadableQuantityReply> responseObserver) {
	    LoadableQuantityReply loadableQuantityReply = null;
	    try {
	      Optional<LoadableStudy> loadableStudy =
	          loadableStudyRepository.findById((Long) loadableQuantityRequest.getLoadableStudyId());
	      if (loadableStudy.isPresent()) {
	        LoadableQuantity loadableQuantity = new LoadableQuantity();
	        loadableQuantity.setConstant(new BigDecimal(loadableQuantityRequest.getConstant()));
	        loadableQuantity.setDeadWeight(new BigDecimal(loadableQuantityRequest.getDwt()));
	        loadableQuantity.setDisplacementAtDraftRestriction(
	            new BigDecimal(loadableQuantityRequest.getDisplacmentDraftRestriction()));
	        loadableQuantity.setDistanceFromLastPort(
	        		new BigDecimal(loadableQuantityRequest.getDistanceFromLastPort()));
	        loadableQuantity.setEstimatedDOOnBoard(
	            new BigDecimal(loadableQuantityRequest.getEstDOOnBoard()));
	        loadableQuantity.setEstimatedFOOnBoard(
	            new BigDecimal(loadableQuantityRequest.getEstFOOnBoard()));
	        loadableQuantity.setEstimatedFWOnBoard(
	            new BigDecimal(loadableQuantityRequest.getEstFreshWaterOnBoard()));
	        loadableQuantity.setEstimatedSagging(
	            new BigDecimal(loadableQuantityRequest.getEstSagging()));

	        loadableQuantity.setEstimatedSeaDensity(
	            new BigDecimal(loadableQuantityRequest.getEstSeaDensity()));
	        loadableQuantity.setFoConsumptionPerDay(
	            new BigDecimal(loadableQuantityRequest.getFoConsumptionPerDay()));
	        loadableQuantity.setLightWeight(
	            new BigDecimal(loadableQuantityRequest.getVesselLightWeight()));
	        loadableQuantity.setLoadableStudyXId(loadableStudy.get());
	        loadableQuantity.setOtherIfAny(new BigDecimal(loadableQuantityRequest.getOtherIfAny()));
	        loadableQuantity.setSaggingDeduction(
	            new BigDecimal(loadableQuantityRequest.getSaggingDeduction()));
	        loadableQuantity.setSgCorrection(new BigDecimal(loadableQuantityRequest.getSgCorrection()));

	        loadableQuantity.setTotalFoConsumption(
	            new BigDecimal(loadableQuantityRequest.getEstTotalFOConsumption()));
	        loadableQuantity.setTotalQuantity(
	            new BigDecimal(loadableQuantityRequest.getTotalQuantity()));
	        loadableQuantity.setTpcatDraft(new BigDecimal(loadableQuantityRequest.getTpc()));
	        loadableQuantity.setVesselAverageSpeed(
	            new BigDecimal(loadableQuantityRequest.getVesselAverageSpeed()));
	        loadableQuantity =  loadableQuantityRepository.save(loadableQuantity);

	        // when Db save is complete we return to client a success message
	        loadableQuantityReply =
	            LoadableQuantityReply.newBuilder().setMessage(SUCCESS).setStatus(SUCCESS).setLoadableQuantityId(loadableQuantity.getId()).build();
	      } else {
	        log.info("INVALID_LOADABLE_STUDY ", "");
	        loadableQuantityReply =
	            LoadableQuantityReply.newBuilder()
	                .setMessage("INVALID_LOADABLE_STUDY")
	                .setStatus(SUCCESS)
	                .build();
	      }
	    } catch (Exception e) {
	      log.error("Error in saving loadable quantity ", e);
	      loadableQuantityReply =
	              LoadableQuantityReply.newBuilder()
	                  .setMessage("INVALID_LOADABLE_STUDY")
	                  .setStatus(FAILED)
	                  .build();
	    } finally {
	      responseObserver.onNext(loadableQuantityReply);
	      responseObserver.onCompleted();
	    }
	  }

  /**
   * Method to find list of loadable studies based on vessel and voyage
   *
   * @param {@link LoadableStudyRequest} - The grpc generated message
   * @param {@link StreamObserver}
   */
  @Override
  public void findLoadableStudiesByVesselAndVoyage(
      LoadableStudyRequest request, StreamObserver<LoadableStudyReply> responseObserver) {
    Builder replyBuilder = null;
    try {
      log.info("inside loadable study service - findLoadableStudiesByVesselAndVoyage");
      Optional<Voyage> voyageOpt = this.voyageRepository.findById(request.getVoyageId());
      if (!voyageOpt.isPresent()) {
        throw new GenericServiceException(
            "Voyage does not exist", CommonErrorCodes.E_HTTP_BAD_REQUEST, HttpStatus.BAD_REQUEST);
      }
      List<LoadableStudy> loadableStudyEntityList =
          this.loadableStudyRepository.findByVesselXIdAndVoyage(
              request.getVesselId(), voyageOpt.get());
      replyBuilder =
          LoadableStudyReply.newBuilder()
              .setResponseStatus(StatusReply.newBuilder().setStatus(SUCCESS).build());
      DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(CREATED_DATE_FORMAT);
      for (LoadableStudy entity : loadableStudyEntityList) {
        com.cpdss.common.generated.LoadableStudy.LoadableStudyDetail.Builder builder =
            LoadableStudyDetail.newBuilder();
        builder.setId(entity.getId());
        builder.setName(entity.getName());
        builder.setCreatedDate(dateTimeFormatter.format(entity.getCreatedDate()));
        Optional.ofNullable(entity.getLoadableStudyStatus()).ifPresent(builder::setStatus);
        Optional.ofNullable(entity.getDetails()).ifPresent(builder::setDetail);
        Optional.ofNullable(entity.getCharterer()).ifPresent(builder::setCharterer);
        Optional.ofNullable(entity.getSubCharterer()).ifPresent(builder::setSubCharterer);
        Optional.ofNullable(entity.getLoadLineXId()).ifPresent(builder::setLoadLineXId);
        Optional.ofNullable(entity.getDraftMark())
            .ifPresent(dratMark -> builder.setDraftMark(String.valueOf(dratMark)));
        Optional.ofNullable(entity.getDraftRestriction())
            .ifPresent(
                draftRestriction -> builder.setDraftRestriction(String.valueOf(draftRestriction)));
        Optional.ofNullable(entity.getMaxTempExpected())
            .ifPresent(maxTemp -> builder.setMaxTempExpected(String.valueOf(maxTemp)));
        replyBuilder.addLoadableStudies(builder.build());
      }

    } catch (GenericServiceException e) {
      log.error("GenericServiceException when fetching loadable study", e);
      replyBuilder =
          LoadableStudyReply.newBuilder()
              .setResponseStatus(
                  StatusReply.newBuilder()
                      .setCode(e.getCode())
                      .setMessage(e.getMessage())
                      .setStatus(FAILED)
                      .build());
    } catch (Exception e) {
      log.error("Error fetching loadable studies", e);
      replyBuilder =
          LoadableStudyReply.newBuilder()
              .setResponseStatus(
                  StatusReply.newBuilder()
                      .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
                      .setMessage("Error fetching loadable studies")
                      .setStatus(FAILED)
                      .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }

  @Override
  public void saveLoadableStudy(
      LoadableStudyDetail request, StreamObserver<LoadableStudyReply> responseObserver) {
    Builder replyBuilder = null;
    try {
      Optional<Voyage> voyageOpt = this.voyageRepository.findById(request.getVoyageId());
      if (!voyageOpt.isPresent()) {
        throw new GenericServiceException(
            "Voyage does not exist", CommonErrorCodes.E_HTTP_BAD_REQUEST, HttpStatus.BAD_REQUEST);
      }
      LoadableStudy entity = new LoadableStudy();
      if (0 != request.getDuplicatedFromId()) {
        Optional<LoadableStudy> createdFromOpt =
            this.loadableStudyRepository.findById(request.getDuplicatedFromId());
        if (!createdFromOpt.isPresent()) {
          throw new GenericServiceException(
              "Created from loadable study does not exist",
              CommonErrorCodes.E_HTTP_BAD_REQUEST,
              HttpStatus.BAD_REQUEST);
        }
        entity.setDuplicatedFrom(createdFromOpt.get());
      }
      entity.setName(request.getName());
      entity.setDetails(StringUtils.isEmpty(request.getDetail()) ? null : request.getDetail());
      entity.setCharterer(
          StringUtils.isEmpty(request.getCharterer()) ? null : request.getCharterer());
      entity.setSubCharterer(
          StringUtils.isEmpty(request.getSubCharterer()) ? null : request.getSubCharterer());
      entity.setVesselXId(request.getVesselId());
      entity.setDraftMark(
          StringUtils.isEmpty(request.getDraftMark())
              ? null
              : new BigDecimal(request.getDraftMark()));
      entity.setLoadLineXId(request.getLoadLineXId());
      entity.setDraftRestriction(
          StringUtils.isEmpty(request.getDraftRestriction())
              ? null
              : new BigDecimal(request.getDraftRestriction()));
      entity.setMaxTempExpected(
          StringUtils.isEmpty(request.getMaxTempExpected())
              ? null
              : new BigDecimal(request.getMaxTempExpected()));
      entity.setVoyage(voyageOpt.get());
      if (!request.getAttachmentsList().isEmpty()) {
        Set<LoadableStudyAttachments> attachmentCollection = new HashSet<>();
        for (LoadableStudyAttachment attachment : request.getAttachmentsList()) {
          Path path = Paths.get(this.uploadFolderPath + attachment.getFileName());
          Files.write(path, attachment.getByteString().toByteArray());
          LoadableStudyAttachments attachmentEntity = new LoadableStudyAttachments();
          attachmentEntity.setUploadedFileName(attachment.getFileName());
          attachmentEntity.setFilePath(this.uploadFolderPath);
          attachmentEntity.setLoadableStudy(entity);
          attachmentCollection.add(attachmentEntity);
        }
        entity.setAttachments(attachmentCollection);
      }
      entity = this.loadableStudyRepository.save(entity);
      replyBuilder =
          LoadableStudyReply.newBuilder()
              .setResponseStatus(StatusReply.newBuilder().setStatus(SUCCESS).build())
              .setId(entity.getId());
    } catch (GenericServiceException e) {
      log.error("GenericServiceException when saving loadable study", e);
      replyBuilder =
          LoadableStudyReply.newBuilder()
              .setResponseStatus(
                  StatusReply.newBuilder()
                      .setCode(e.getCode())
                      .setMessage(e.getMessage())
                      .setStatus(FAILED)
                      .build());
    } catch (Exception e) {
      log.error("Error saving loadable study", e);
      replyBuilder =
          LoadableStudyReply.newBuilder()
              .setResponseStatus(
                  StatusReply.newBuilder()
                      .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
                      .setMessage("Error saving loadable study")
                      .setStatus(FAILED)
                      .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }
}
