/* Licensed at AlphaOri Technologies */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.LoadablePlanStowageDetails;
import com.cpdss.loadablestudy.entity.LoadablePlanStowageDetailsTemp;

/** @author suhail.k */
public interface LoadablePlanStowageDetailsTempRepository
    extends CommonCrudRepository<LoadablePlanStowageDetailsTemp, Long> {

  public LoadablePlanStowageDetailsTemp findByLoadablePlanStowageDetailsAndIsActive(
      LoadablePlanStowageDetails stowageDetail, boolean isActive);
}
