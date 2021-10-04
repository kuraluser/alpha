/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import com.cpdss.vesselinfo.entity.VesselValveStrippingSequence;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VesselValveStrippingSequenceRepository
    extends JpaRepository<VesselValveStrippingSequence, Long> {
  List<VesselValveStrippingSequence> findAllByVesselId(Long id);
}
