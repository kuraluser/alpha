/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import com.cpdss.vesselinfo.entity.VesselValveEducationProcess;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VesselValveEducationProcessRepository
    extends JpaRepository<VesselValveEducationProcess, Long> {
  List<VesselValveEducationProcess> findAllByVesselXid(Long id);
}
