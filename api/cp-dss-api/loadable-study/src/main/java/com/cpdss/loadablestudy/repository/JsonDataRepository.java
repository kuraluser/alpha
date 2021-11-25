/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.JsonData;
import com.cpdss.loadablestudy.entity.JsonType;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

public interface JsonDataRepository extends CommonCrudRepository<JsonData, Long> {

  Optional<JsonData> findByJsonTypeXIdAndReferenceXId(JsonType type, Long referId);

  JsonData findTopByReferenceXIdAndJsonTypeXIdOrderByIdDesc(long id, JsonType type);

  @Query(
      value =
          "SELECT  CAST(json_agg(u) as VARCHAR) json_out FROM json_data u where reference_xid=?1 "
              + "and json_type_xid in (24, 25)",
      nativeQuery = true)
  String getJsonDataWithLoadingInfoId(long id);
}
