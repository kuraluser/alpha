/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.BendingMomentType2;
import com.cpdss.vesselinfo.entity.BendingMomentType4;
import com.cpdss.vesselinfo.entity.Vessel;

import java.util.List;

/** @Author ravi.r */
public interface BendingMomentRepositoryType4
    extends CommonCrudRepository<BendingMomentType4, Long> {
  public List<BendingMomentType4> findByVessel(Vessel vessel);
}
