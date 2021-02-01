/* Licensed under Apache-2.0 */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.UllageTableData;
import com.cpdss.vesselinfo.entity.Vessel;
import java.util.List;

/** @Author jerin.g */
public interface UllageTableDataRepository extends CommonCrudRepository<UllageTableData, Long> {
  public List<UllageTableData> findByVesselOrderById(Vessel vessel);
}
