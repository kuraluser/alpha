/* Licensed at AlphaOri Technologies */
package com.cpdss.portinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.portinfo.entity.BerthManifold;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface BerthManifoldRepository extends CommonCrudRepository<BerthManifold, Long> {

  @Query("FROM BerthManifold bm WHERE bm.berthInfo.id = ?1 AND bm.isActive = true")
  List<BerthManifold> findByBerthInfoAndIsActiveTrue(Long berthInfoId);
}
