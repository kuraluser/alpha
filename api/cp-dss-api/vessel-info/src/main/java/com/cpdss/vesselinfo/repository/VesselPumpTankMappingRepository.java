/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.VesselPumpTankMapping;
import java.util.List;

public interface VesselPumpTankMappingRepository
    extends CommonCrudRepository<VesselPumpTankMapping, Long> {

  public List<VesselPumpTankMapping> findByVesselXidAndIsActive(
      Integer vesselXid, Boolean isActive);
}
