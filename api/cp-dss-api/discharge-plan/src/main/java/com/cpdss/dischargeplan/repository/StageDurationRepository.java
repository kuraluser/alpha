/* Licensed at AlphaOri Technologies */
package com.cpdss.dischargeplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.dischargeplan.entity.StageDuration;
import java.util.List;
import java.util.Optional;

public interface StageDurationRepository extends CommonCrudRepository<StageDuration, Long> {

  List<StageDuration> findAllByIsActiveTrue();

  public Optional<StageDuration> findByIdAndIsActiveTrue(Long id);

  Optional<StageDuration> findByDurationAndIsActiveTrue(Integer duration);
}
