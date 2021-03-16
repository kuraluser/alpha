/* Licensed at AlphaOri Technologies */
package com.cpdss.loadableplan.service;

import com.cpdss.common.generated.LoadablePlanServiceGrpc.LoadablePlanServiceImplBase;
import com.cpdss.loadableplan.repository.CargoNominationRepository;
import com.cpdss.loadableplan.repository.LoadableQuantityRepository;
import com.cpdss.loadableplan.repository.LoadableStudyPortRotationRepository;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.server.service.GrpcService;
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
