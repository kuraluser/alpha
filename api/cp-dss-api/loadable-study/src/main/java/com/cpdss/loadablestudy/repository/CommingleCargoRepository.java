/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.CommingleCargo;
import java.util.List;

public interface CommingleCargoRepository extends CommonCrudRepository<CommingleCargo, Long> {

  public List<CommingleCargo> findByLoadableStudyXIdAndIsActive(
      Long loadableStudyXId, Boolean isActive);
}
