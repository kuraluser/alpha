/* Licensed under Apache-2.0 */
package com.cpdss.loadableplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadableplan.entity.CargoNomination;
import java.util.List;

/** Repository interface for cargoNomination entity */
public interface CargoNominationRepository extends CommonCrudRepository<CargoNomination, Long> {

  public List<CargoNomination> findByLoadableStudyXIdAndIsActive(
      Long loadableStudyXId, Boolean isActive);
}
