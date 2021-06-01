/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.LoadingBerthDetail;
import com.cpdss.loadingplan.entity.LoadingInformation;
import java.util.List;
import java.util.Optional;

public interface LoadingBerthDetailsRepository
    extends CommonCrudRepository<LoadingBerthDetail, Long> {

  List<LoadingBerthDetail> findAllByLoadingInformationAndIsActiveTrue(LoadingInformation var1);

  Optional<LoadingBerthDetail> findByIdAndIsActiveTrue(Long id);
}
