/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import com.cpdss.vesselinfo.entity.VesselValveAirPurgeSequence;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VesselValveAirPurgeSequenceRepository
    extends JpaRepository<VesselValveAirPurgeSequence, Long> {
  List<VesselValveAirPurgeSequence> findAllByVesselId(Long id);
}
