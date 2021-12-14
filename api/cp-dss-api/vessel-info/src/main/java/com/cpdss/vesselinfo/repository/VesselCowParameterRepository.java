package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.VesselCowParameters;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VesselCowParameterRepository extends CommonCrudRepository<VesselCowParameters, Long> {

    List<VesselCowParameters> findAllByVesselIdAndIsActiveTrue(Long var1);
}
