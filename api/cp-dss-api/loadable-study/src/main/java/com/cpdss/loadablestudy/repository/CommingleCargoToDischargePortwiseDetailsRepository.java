/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.CommingleCargoToDischargePortwiseDetails;
import java.util.List;

public interface CommingleCargoToDischargePortwiseDetailsRepository
    extends CommonCrudRepository<CommingleCargoToDischargePortwiseDetails, Long> {

  List<CommingleCargoToDischargePortwiseDetails> findByDischargeStudyIdAndIsActiveTrue(
      long dischargeStudyId);

  List<CommingleCargoToDischargePortwiseDetails> findByDischargeStudyIdInAndIsActiveTrue(
      List<Long> dischargeStudyIds);
}
