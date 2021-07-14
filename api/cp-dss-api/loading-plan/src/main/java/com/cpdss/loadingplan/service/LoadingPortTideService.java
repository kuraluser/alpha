/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.service;

import com.cpdss.loadingplan.repository.projections.PortTideAlgo;
import java.util.List;

public interface LoadingPortTideService {

  List<PortTideAlgo> findRecentTideDetailsByPortId(Long id);
}
