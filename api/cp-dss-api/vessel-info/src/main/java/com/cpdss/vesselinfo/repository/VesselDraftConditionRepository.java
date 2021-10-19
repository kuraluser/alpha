/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.Vessel;
import com.cpdss.vesselinfo.entity.VesselDraftCondition;
import java.util.List;

/** @Author jerin.g */
public interface VesselDraftConditionRepository
    extends CommonCrudRepository<VesselDraftCondition, Long> {

  public List<VesselDraftCondition> findByVesselAndIsActive(Vessel vessel, Boolean isActive);
}
