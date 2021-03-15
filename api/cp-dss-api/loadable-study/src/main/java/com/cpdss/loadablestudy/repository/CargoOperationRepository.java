/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.CargoOperation;
import java.util.List;

public interface CargoOperationRepository extends CommonCrudRepository<CargoOperation, Long> {

  public List<CargoOperation> findByIsActiveOrderById(boolean isActive);
}
