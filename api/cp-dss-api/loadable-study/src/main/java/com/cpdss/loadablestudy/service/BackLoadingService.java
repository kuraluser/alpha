/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.service;

import com.cpdss.loadablestudy.entity.BackLoading;
import com.cpdss.loadablestudy.repository.BackLoadingRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BackLoadingService {

  @Autowired private BackLoadingRepository backLoadingRepository;

  public Map<Long, List<BackLoading>> getBackloadingDataByportIds(
      long loadableStudyId, List<Long> portIds) {

    List<BackLoading> backLoadings =
        backLoadingRepository.findByDischargeStudyIdAndPortIdIn(loadableStudyId, portIds);
    Map<Long, List<BackLoading>> BackLoadingByPort =
        backLoadings.stream()
            .collect(Collectors.groupingBy(backLoading -> backLoading.getPortId()));
    return BackLoadingByPort;
  }
}
