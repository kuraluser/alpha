/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.VesselTankTcg;
import java.util.List;

/** @Author jerin.g */
public interface VesselTankTcgRepository extends CommonCrudRepository<VesselTankTcg, Long> {

  public List<VesselTankTcg> findByVesselIdAndIsActive(Long vesselId, Boolean isActive);
}
