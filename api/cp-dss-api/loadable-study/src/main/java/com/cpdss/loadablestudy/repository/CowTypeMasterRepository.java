/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.CargoOperation;
import com.cpdss.loadablestudy.entity.CowTypeMaster;

import java.util.List;
import java.util.Optional;

public interface CowTypeMasterRepository extends CommonCrudRepository<CowTypeMaster, Long> {

	Optional<CowTypeMaster> findByCowTypeAndIsActiveTrue(String washType);

}
