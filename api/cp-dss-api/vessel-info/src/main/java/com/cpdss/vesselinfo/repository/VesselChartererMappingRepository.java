/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.Charterer;
import com.cpdss.vesselinfo.entity.Vessel;
import com.cpdss.vesselinfo.entity.VesselChartererMapping;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface VesselChartererMappingRepository
    extends CommonCrudRepository<VesselChartererMapping, Long> {

  public VesselChartererMapping findByVesselAndIsActive(
      final Vessel vessel, final boolean isActive);

  public List<VesselChartererMapping> findByChartererAndIsActive(
      final Charterer charterer, final boolean isActive);

  public List<VesselChartererMapping> findBycharterer_idInAndIsActive(
      final Set<Long> charterer, final boolean isActive);

  Optional<VesselChartererMapping> findByCharterer_idAndVessel_idAndIsActiveTrue(
      Long charterId, Long vesselId);
}
