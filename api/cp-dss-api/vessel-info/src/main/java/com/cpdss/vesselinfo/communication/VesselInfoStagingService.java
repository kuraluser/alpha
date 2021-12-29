/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.communication;

import com.cpdss.common.communication.StagingService;
import com.cpdss.vesselinfo.repository.VesselInfoStagingRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class VesselInfoStagingService extends StagingService {
  public VesselInfoStagingService(@Autowired VesselInfoStagingRepository stagingRepository) {
    super(stagingRepository);
  }
}
