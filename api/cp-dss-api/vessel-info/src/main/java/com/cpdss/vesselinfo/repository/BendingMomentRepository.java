/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.BendingMoment;
import com.cpdss.vesselinfo.entity.Vessel;
import java.util.List;

/** @Author jerin.g */
public interface BendingMomentRepository extends CommonCrudRepository<BendingMoment, Long> {
  public List<BendingMoment> findByVessel(Vessel vessel);
}
