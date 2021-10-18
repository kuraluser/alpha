/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.VesselVentingCapacity;

/** @author sanalkumar.k */
public interface VesselVendingCapacityRepository
    extends CommonCrudRepository<VesselVentingCapacity, Long> {

  public VesselVentingCapacity findByVesselIdAndIsActiveTrue(Long vessel);
}
