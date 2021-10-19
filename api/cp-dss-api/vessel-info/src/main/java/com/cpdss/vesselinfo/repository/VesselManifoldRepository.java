/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.VesselManifold;
import java.util.List;

public interface VesselManifoldRepository extends CommonCrudRepository<VesselManifold, Long> {

  List<VesselManifold> findByVesselXidAndIsActiveTrue(Long id);
}
