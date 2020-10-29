/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.CargoOperation;
import java.util.List;

public interface CargoOperationRepository extends CommonCrudRepository<CargoOperation, Long> {

  public List<CargoOperation> findByIdNotIn(List<Long> idsExcluded);
}
