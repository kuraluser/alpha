/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.VesselCowParameters;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface VesselCowParameterRepository
    extends CommonCrudRepository<VesselCowParameters, Long> {

  List<VesselCowParameters> findAllByVesselIdAndIsActiveTrue(Long var1);
}
