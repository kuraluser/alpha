/* Licensed under Apache-2.0 */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.Vessel;
import com.cpdss.vesselinfo.entity.VesselChartererMapping;

public interface VesselChartererMappingRepository
    extends CommonCrudRepository<VesselChartererMapping, Long> {

  public VesselChartererMapping findByVesselAndIsActive(
      final Vessel vessel, final boolean isActive);
}
