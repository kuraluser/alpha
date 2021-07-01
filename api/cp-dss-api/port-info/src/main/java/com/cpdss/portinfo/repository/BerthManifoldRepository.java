/* Licensed at AlphaOri Technologies */
package com.cpdss.portinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.portinfo.entity.BerthInfo;
import com.cpdss.portinfo.entity.BerthManifold;
import java.util.List;

public interface BerthManifoldRepository extends CommonCrudRepository<BerthManifold, Long> {

  List<BerthManifold> findByBerthInfoAndIsActiveTrue(BerthInfo berthInfo);
}
