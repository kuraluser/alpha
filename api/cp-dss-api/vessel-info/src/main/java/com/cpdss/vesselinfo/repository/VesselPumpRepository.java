/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.Vessel;
import com.cpdss.vesselinfo.entity.VesselPumps;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VesselPumpRepository extends CommonCrudRepository<VesselPumps, Long> {

  Page<VesselPumps> findAllByVesselAndIsActiveTrue(Vessel vessel, Pageable pageable);

  Page<VesselPumps> findAllByVesselAndIsActiveTrueOrderById(Vessel vessel, Pageable pageable);
}
