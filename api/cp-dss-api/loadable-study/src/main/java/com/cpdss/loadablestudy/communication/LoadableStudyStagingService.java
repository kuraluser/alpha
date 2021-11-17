/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.communication;

import com.cpdss.common.communication.StagingService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** @Author Selvy Thomas */
@Log4j2
@Service
public class LoadableStudyStagingService extends StagingService {

  @Autowired private LoadableStudyStagingRepository loadableStudyStagingRepository;

  public LoadableStudyStagingService(
      @Autowired LoadableStudyStagingRepository loadableStudyStagingRepository) {
    super(loadableStudyStagingRepository);
  }
}
