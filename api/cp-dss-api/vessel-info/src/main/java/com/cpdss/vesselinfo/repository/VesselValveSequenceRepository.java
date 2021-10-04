/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import com.cpdss.vesselinfo.entity.VesselValveSequence;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VesselValveSequenceRepository extends JpaRepository<VesselValveSequence, Long> {
  List<VesselValveSequence> findAllByVesselXid(Long id);
}
