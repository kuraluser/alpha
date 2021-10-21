/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.VesselBottomLine;
import java.util.List;

public interface VesselBottomLineRepository extends CommonCrudRepository<VesselBottomLine, Long> {
  List<VesselBottomLine> findAllByVesselXidAndIsActiveTrue(Long id);
}
