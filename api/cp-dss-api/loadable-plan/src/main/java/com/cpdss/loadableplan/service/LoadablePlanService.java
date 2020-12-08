/* Licensed under Apache-2.0 */
package com.cpdss.loadableplan.service;

import com.cpdss.common.generated.Common.ResponseStatus;
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

  
}
