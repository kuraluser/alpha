/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import com.cpdss.vesselinfo.entity.VesselValveStrippingSequenceCargoValve;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VVStrippingSequenceCargoValveRepository
    extends JpaRepository<VesselValveStrippingSequenceCargoValve, Long> {
  List<VesselValveStrippingSequenceCargoValve> findAllByVesselId(Long id);
}
