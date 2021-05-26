/* Licensed at AlphaOri Technologies */
package com.cpdss.portinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.portinfo.entity.BerthInfo;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface BerthInfoRepository extends CommonCrudRepository<BerthInfo, Long> {

  @Query("from BerthInfo bt where bt.portInfo.id = ?1 and bt.isActive = true")
  List<BerthInfo> findAllByPortInfoAndIsActiveTrue(Long portId);
}
