/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.JsonType;
import java.util.Optional;

public interface JsonTypeRepository extends CommonCrudRepository<JsonType, Long> {

  Optional<JsonType> findByIdAndIsActive(Long id, Boolean isActive);
}
