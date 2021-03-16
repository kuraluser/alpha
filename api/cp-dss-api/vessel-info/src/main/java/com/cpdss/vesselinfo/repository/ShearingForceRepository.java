/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.ShearingForce;
import com.cpdss.vesselinfo.entity.Vessel;
import java.util.List;

/** @Author jerin.g */
public interface ShearingForceRepository extends CommonCrudRepository<ShearingForce, Long> {
  public List<ShearingForce> findByVessel(Vessel vessel);
}
