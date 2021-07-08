/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.ShearingForceType2;
import com.cpdss.vesselinfo.entity.ShearingForceType4;
import com.cpdss.vesselinfo.entity.Vessel;

import java.util.List;

/** @Author ravi.r */
public interface ShearingForceRepositoryType4 extends CommonCrudRepository<ShearingForceType4, Long> {
  public List<ShearingForceType4> findByVessel(Vessel vessel);
}
