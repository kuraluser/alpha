/* Licensed under Apache-2.0 */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.MinMaxValuesForBmsf;
import com.cpdss.vesselinfo.entity.Vessel;
import java.util.List;

/** @Author jerin.g */
public interface MinMaxValuesForBmsfRepository
    extends CommonCrudRepository<MinMaxValuesForBmsf, Long> {
  public List<MinMaxValuesForBmsf> findByVessel(Vessel vessel);
}
