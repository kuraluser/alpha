/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.StageOffset;
import java.util.List;
import java.util.Optional;

public interface StageOffsetRepository extends CommonCrudRepository<StageOffset, Long> {

  List<StageOffset> findAllByIsActiveTrue();

  public Optional<StageOffset> findByIdAndIsActiveTrue(Long id);

  Optional<StageOffset> findByStageOffsetValAndIsActiveTrue(Integer offsetVal);
}
