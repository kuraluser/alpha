/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.ShearingForce;
import com.cpdss.vesselinfo.entity.ShearingForceType2;
import com.cpdss.vesselinfo.entity.Vessel;

import java.util.List;

/** @Author ravi.r */
public interface ShearingForceRepositoryType2 extends CommonCrudRepository<ShearingForceType2, Long> {
  public List<ShearingForceType2> findByVessel(Vessel vessel);
}
