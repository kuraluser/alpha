/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.StageOffset;
import java.util.List;
import java.util.Optional;

public interface StageOffsetRepository extends CommonCrudRepository<StageOffset, Long> {

  List<StageOffset> findAllByIsActiveTrue();

  public Optional<StageOffset> findByIdAndIsActiveTrue(Long id);

  Optional<StageOffset> findByStageOffsetValAndIsActiveTrue(Integer offsetVal);
}
