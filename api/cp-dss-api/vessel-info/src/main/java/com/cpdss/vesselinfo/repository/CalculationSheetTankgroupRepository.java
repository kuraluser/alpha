/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.CalculationSheetTankgroup;
import com.cpdss.vesselinfo.entity.Vessel;
import java.util.List;

/** @Author jerin.g */
public interface CalculationSheetTankgroupRepository
    extends CommonCrudRepository<CalculationSheetTankgroup, Long> {
  public List<CalculationSheetTankgroup> findByVessel(Vessel vessel);
}
