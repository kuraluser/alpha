/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.CrewVesselMapping;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CrewVesselMappingRepository
    extends CommonCrudRepository<CrewVesselMapping, Long>,
        JpaSpecificationExecutor<CrewVesselMapping> {}
