/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.CowHistory;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CowHistoryRepository extends CommonCrudRepository<CowHistory, Long> {

  List<CowHistory> findAllByVesselIdAndIsActiveTrue(Long vessel, Pageable pageable);
}
