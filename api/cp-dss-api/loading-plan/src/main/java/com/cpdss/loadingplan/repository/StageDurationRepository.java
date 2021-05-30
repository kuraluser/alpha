/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.StageDuration;
import java.util.List;

public interface StageDurationRepository extends CommonCrudRepository<StageDuration, Long> {

  List<StageDuration> findAllByIsActiveTrue();
}
