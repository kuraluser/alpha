/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.entity.LoadingMachineryInUse;
import java.util.List;

public interface LoadingMachineryInUseRepository
    extends CommonCrudRepository<LoadingMachineryInUse, Long> {

  List<LoadingMachineryInUse> findAllByLoadingInformationAndIsActiveTrue(LoadingInformation var1);
}
