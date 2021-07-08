/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.BendingMomentType2;
import com.cpdss.vesselinfo.entity.Vessel;
import java.util.List;

/** @Author jerin.g */
public interface BendingMomentRepositoryType2
    extends CommonCrudRepository<BendingMomentType2, Long> {
  public List<BendingMomentType2> findByVessel(Vessel vessel);
}
