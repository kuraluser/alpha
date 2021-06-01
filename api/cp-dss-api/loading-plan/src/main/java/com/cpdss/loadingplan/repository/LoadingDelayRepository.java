/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.LoadingDelay;
import com.cpdss.loadingplan.entity.LoadingInformation;
import java.util.List;

public interface LoadingDelayRepository extends CommonCrudRepository<LoadingDelay, Long> {

  List<LoadingDelay> findAllByLoadingInformationAndIsActiveTrue(LoadingInformation var1);
}
