/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.JsonData;

import java.util.Optional;

public interface JsonDataRepository extends CommonCrudRepository<JsonData, Long> {

    Optional<JsonData> findByJsonTypeXIdAndReferenceXId(Long typeId, Long referId);
}
