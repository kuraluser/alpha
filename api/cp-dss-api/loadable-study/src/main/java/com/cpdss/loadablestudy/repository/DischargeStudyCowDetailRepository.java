/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.DischargeStudyCowDetail;
import java.util.List;

public interface DischargeStudyCowDetailRepository
    extends CommonCrudRepository<DischargeStudyCowDetail, Long> {
  public List<DischargeStudyCowDetail> findByDischargeStudyStudyIdAndPortIdIn(
      Long dischargestudyId, List<Long> portIds);
}
