/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.BendingMomentShearingForceType3;
import com.cpdss.vesselinfo.entity.Vessel;
import java.util.List;

/** @Author ravi.r */
public interface BendingMomentShearingForceRepositoryType3
    extends CommonCrudRepository<BendingMomentShearingForceType3, Long> {
  public List<BendingMomentShearingForceType3> findByVessel(Vessel vessel);
}
