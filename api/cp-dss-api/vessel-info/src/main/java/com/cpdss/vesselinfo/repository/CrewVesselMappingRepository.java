/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.CrewVesselMapping;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface CrewVesselMappingRepository
    extends CommonCrudRepository<CrewVesselMapping, Long>,
        JpaSpecificationExecutor<CrewVesselMapping> {

  @Query(
      value =
          "select cvm.* from crew_vessel_mapping cvm where cvm.crew_xid = ?1 "
              + " and cvm.vessel_xid = ?2 and cvm.is_active = ?3",
      nativeQuery = true)
  Optional<CrewVesselMapping> findByCargoXIdAndPortIdAndIsActive(
      Long crewId, Long vesselId, Boolean isActive);
}
