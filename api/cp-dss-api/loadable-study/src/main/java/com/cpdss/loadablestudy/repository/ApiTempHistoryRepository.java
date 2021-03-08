/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.ApiTempHistory;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

/** Api-Temp history repository */
public interface ApiTempHistoryRepository extends CommonCrudRepository<ApiTempHistory, Long> {

  @Query(
      "FROM ApiTempHistory ath WHERE "
          //  		+ "ath.isActive = true AND "
          + "ath.cargoId = ?1 AND ath.loadedYear >= ?2 order by ath.loadedDate desc")
  public List<com.cpdss.loadablestudy.entity.ApiTempHistory> findApiTempHistoryWithLoadedYearAfter(
      Long cargoId, Integer year);
}
