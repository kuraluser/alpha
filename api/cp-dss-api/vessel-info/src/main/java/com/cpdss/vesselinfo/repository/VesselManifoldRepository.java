/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import java.util.List;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.VesselManifold;
import com.cpdss.vesselinfo.entity.VesselPumps;

public interface VesselManifoldRepository extends CommonCrudRepository<VesselManifold, Long> {

	List<VesselManifold> findByVesselXidAndIsActiveTrue(Long id);
}
