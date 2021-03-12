/* Licensed at AlphaOri Technologies */
package com.cpdss.vesselinfo.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.vesselinfo.entity.CalculationSheet;
import com.cpdss.vesselinfo.entity.Vessel;
import java.util.List;

/** @Author jerin.g */
public interface CalculationSheetRepository extends CommonCrudRepository<CalculationSheet, Long> {
  public List<CalculationSheet> findByVessel(Vessel vessel);
}
