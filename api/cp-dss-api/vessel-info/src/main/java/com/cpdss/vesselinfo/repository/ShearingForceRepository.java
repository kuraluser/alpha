/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.ShearingForceType1;
import com.cpdss.vesselinfo.entity.Vessel;
import java.util.List;

/** @Author jerin.g */
public interface ShearingForceRepository extends CommonCrudRepository<ShearingForceType1, Long> {
  public List<ShearingForceType1> findByVessel(Vessel vessel);
}
