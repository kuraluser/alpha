/* Licensed at AlphaOri Technologies */
package com.cpdss.gateway.service.communication;

import com.cpdss.common.communication.StagingService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class FileSharingService extends StagingService {

  public FileSharingService(@Autowired FileSharingStagingRepository fileSharingStagingRepository) {
    super(fileSharingStagingRepository);
  }
}
