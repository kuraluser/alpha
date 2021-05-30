/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.StageOffset;
import java.util.List;

public interface StageOffsetRepository extends CommonCrudRepository<StageOffset, Long> {

  List<StageOffset> findAllByIsActiveTrue();
}
