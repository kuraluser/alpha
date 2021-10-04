/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.DischargePatternQuantityCargoPortwiseDetails;
import java.util.List;

public interface DischargePatternQuantityCargoPortwiseRepository
    extends CommonCrudRepository<DischargePatternQuantityCargoPortwiseDetails, Long> {
  public List<DischargePatternQuantityCargoPortwiseDetails>
      findByCargoNominationIdInAndOperationType(List<Long> cargoIds, String operationType);

  public List<DischargePatternQuantityCargoPortwiseDetails>
      findByDischargeCargoNominationIdInAndOperationType(List<Long> cargoIds, String operationType);

  public List<DischargePatternQuantityCargoPortwiseDetails> findByCargoNominationIdIn(
      List<Long> cargoIds);

  List<DischargePatternQuantityCargoPortwiseDetails> findAllByLoadablePatternIdAndIsActiveTrue(
      Long patternId);
}
