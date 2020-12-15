/* Licensed under Apache-2.0 */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.StationValues;
import java.util.List;

/** @Author jerin.g */
public interface StationValuesRepository extends CommonCrudRepository<StationValues, Long> {
  public List<StationValues> findByVesselId(Long vesselId);
}
