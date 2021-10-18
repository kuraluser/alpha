/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service.impl;

import com.cpdss.loadingplan.repository.PortTideDetailsRepository;
import com.cpdss.loadingplan.repository.projections.PortTideAlgo;
import com.cpdss.loadingplan.service.LoadingPortTideService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoadingPortTideServiceImpl implements LoadingPortTideService {

  @Autowired PortTideDetailsRepository portTideDetailsRepository;

  PageRequest defaultPage = PageRequest.of(0, 1);

  public List<PortTideAlgo> findAllByPortIdAndPageable(Long portId) {
    return this.portTideDetailsRepository
        .findAllByPortXidAndIsActiveTrueOrderByTideDateDescTideTimeDesc(portId, defaultPage);
  }

  public List<PortTideAlgo> findAllByPortIdLoadingInfoId(Long portId, Long infoId) {
    return this.portTideDetailsRepository
        .findAllByPortXidAndIsActiveTrueOrderByTideDateDescTideTimeDesc(portId, defaultPage);
  }

  @Override
  public List<PortTideAlgo> findRecentTideDetailsByPortId(Long id) {
    List<PortTideAlgo> portTideAlgo = this.findAllByPortIdAndPageable(id);
    log.info("Fetch Tide Details for port {}, Size {}", id, portTideAlgo.size());
    return portTideAlgo;
  }

  @Override
  public List<PortTideAlgo> findRecentTideDetailsByPortIdAndLoadingInfoId(
      Long portId, Long infoId) {
    List<PortTideAlgo> data =
        this.portTideDetailsRepository.findByLoadingXidAndPortXidAndIsActiveTrue(infoId, portId);
    log.info("Fetch Tide Details for port {}, Info Id {}, Size {}", portId, infoId, data.size());
    return data;
  }
}
