/* Licensed under Apache-2.0 */
package com.cpdss.loadableplan.service;

import com.cpdss.common.generated.Common.ResponseStatus;
import com.cpdss.common.generated.LoadablePlan.AlgoReply;
import com.cpdss.common.generated.LoadablePlan.AlgoReply.Builder;
import com.cpdss.common.generated.LoadablePlanServiceGrpc.LoadablePlanServiceImplBase;
import com.cpdss.common.rest.CommonErrorCodes;
import com.cpdss.loadableplan.domain.LoadableStudy;
import com.cpdss.loadableplan.entity.CargoNomination;
import com.cpdss.loadableplan.entity.LoadableQuantity;
import com.cpdss.loadableplan.entity.LoadableStudyPortRotation;
import com.cpdss.loadableplan.repository.CargoNominationRepository;
import com.cpdss.loadableplan.repository.LoadableQuantityRepository;
import com.cpdss.loadableplan.repository.LoadableStudyPortRotationRepository;
import io.grpc.stub.StreamObserver;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.server.service.GrpcService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** @Author jerin.g */
@Log4j2
@GrpcService
@Service
@Transactional
public class LoadablePlanService extends LoadablePlanServiceImplBase {

  @Autowired private CargoNominationRepository cargoNominationRepository;

  @Autowired private LoadableQuantityRepository loadableQuantityRepository;

  @Autowired private LoadableStudyPortRotationRepository loadableStudyPortRotationRepository;

  private static final String SUCCESS = "SUCCESS";
  private static final String FAILED = "FAILED";

  @Override
  public void callAlgo(
      com.cpdss.common.generated.LoadablePlan.AlgoRequest request,
      StreamObserver<com.cpdss.common.generated.LoadablePlan.AlgoReply> responseObserver) {
    log.info("Inside callAlgo service");
    Builder replyBuilder = AlgoReply.newBuilder();
    try {
      ModelMapper modelMapper = new ModelMapper();
      LoadableStudy loadableStudy = new LoadableStudy();
      List<CargoNomination> cargoNominations =
          cargoNominationRepository.findByLoadableStudyXIdAndIsActive(
              request.getLoadableStudyId(), true);

      loadableStudy.setCargoNomination(new ArrayList<>());
      if (!cargoNominations.isEmpty()) {
        cargoNominations.forEach(
            cargoNomination -> {
              com.cpdss.loadableplan.domain.CargoNomination cargoNominationDto =
                  new com.cpdss.loadableplan.domain.CargoNomination();
              cargoNominationDto =
                  modelMapper.map(
                      cargoNomination, com.cpdss.loadableplan.domain.CargoNomination.class);
              loadableStudy.getCargoNomination().add(cargoNominationDto);
            });
      }
      Optional<LoadableQuantity> loadableQuantity =
          loadableQuantityRepository.findByLoadableStudyXId(request.getLoadableStudyId());
      if (loadableQuantity.isPresent()) {
        com.cpdss.loadableplan.domain.LoadableQuantity loadableQuantityDto =
            new com.cpdss.loadableplan.domain.LoadableQuantity();
        loadableQuantityDto =
            modelMapper.map(
                loadableQuantity.get(), com.cpdss.loadableplan.domain.LoadableQuantity.class);
        loadableStudy.setLoadableQuantity(loadableQuantityDto);
      }

      List<LoadableStudyPortRotation> loadableStudyPortRotations =
          loadableStudyPortRotationRepository.findByLoadableStudyAndIsActive(
              request.getLoadableStudyId(), true);

      loadableStudy.setLoadableStudyPortRotation(new ArrayList<>());
      if (!loadableStudyPortRotations.isEmpty()) {
        loadableStudyPortRotations.forEach(
            loadableStudyPortRotation -> {
              com.cpdss.loadableplan.domain.LoadableStudyPortRotation loadableStudyPortRotationDto =
                  new com.cpdss.loadableplan.domain.LoadableStudyPortRotation();
              loadableStudyPortRotationDto =
                  modelMapper.map(
                      loadableStudyPortRotation,
                      com.cpdss.loadableplan.domain.LoadableStudyPortRotation.class);
              loadableStudy.getLoadableStudyPortRotation().add(loadableStudyPortRotationDto);
            });
      }

      replyBuilder =
          AlgoReply.newBuilder()
              .setResponseStatus(
                  ResponseStatus.newBuilder().setMessage(SUCCESS).setStatus(SUCCESS).build());
    } catch (Exception e) {
      log.error("Exception when when calling algo  ", e);
      replyBuilder =
          AlgoReply.newBuilder()
              .setResponseStatus(
                  ResponseStatus.newBuilder()
                      .setCode(CommonErrorCodes.E_GEN_INTERNAL_ERR)
                      .setMessage("Error when calling algo ")
                      .setStatus(FAILED)
                      .build());
    } finally {
      responseObserver.onNext(replyBuilder.build());
      responseObserver.onCompleted();
    }
  }
}
