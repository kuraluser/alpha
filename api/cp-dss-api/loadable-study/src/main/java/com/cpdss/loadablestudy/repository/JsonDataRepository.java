/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.JsonData;
import com.cpdss.loadablestudy.entity.JsonType;
import java.util.Optional;

public interface JsonDataRepository extends CommonCrudRepository<JsonData, Long> {

  Optional<JsonData> findByJsonTypeXIdAndReferenceXId(JsonType type, Long referId);

  JsonData findTopByReferenceXIdAndJsonTypeXIdOrderByIdDesc(long id, JsonType type);
}
