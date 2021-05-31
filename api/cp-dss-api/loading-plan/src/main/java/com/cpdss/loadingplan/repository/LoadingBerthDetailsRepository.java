/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.LoadingBerthDetail;
import com.cpdss.loadingplan.entity.LoadingInformation;
import java.util.List;

public interface LoadingBerthDetailsRepository
    extends CommonCrudRepository<LoadingBerthDetail, Long> {

  List<LoadingBerthDetail> findAllByLoadingInformationAndIsActiveTrue(LoadingInformation var1);
}
