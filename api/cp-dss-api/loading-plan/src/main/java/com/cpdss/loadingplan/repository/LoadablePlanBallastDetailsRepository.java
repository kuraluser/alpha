/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.LoadablePlanBallastDetails;
import com.cpdss.loadingplan.entity.LoadingInformation;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LoadablePlanBallastDetailsRepository
    extends CommonCrudRepository<LoadablePlanBallastDetails, Long> {

  @Transactional
  @Modifying
  @Query("UPDATE LoadablePlanBallastDetails SET isActive = false WHERE loadingInformation = ?1")
  public void deleteByLoadingInformation(LoadingInformation loadingInformation);
}
