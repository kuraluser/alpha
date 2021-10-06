/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.Vessel;
import com.cpdss.vesselinfo.entity.VesselFlowRate;
import java.util.List;

/** @Author arun.j */
public interface VesselFlowRateRepository extends CommonCrudRepository<VesselFlowRate, Long> {
	public List<VesselFlowRate> findByVessel(Vessel vessel);
}
