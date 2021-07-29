/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.BendingMomentType1;
import com.cpdss.vesselinfo.entity.Vessel;
import java.util.List;

/** @Author jerin.g */
public interface BendingMomentRepository extends CommonCrudRepository<BendingMomentType1, Long> {
  public List<BendingMomentType1> findByVessel(Vessel vessel);
}
