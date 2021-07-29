/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.entity.PortLoadingPlanRobDetails;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PortLoadingPlanRobDetailsRepository
    extends CommonCrudRepository<PortLoadingPlanRobDetails, Long> {

  public List<PortLoadingPlanRobDetails> findByLoadingInformationAndIsActive(
      LoadingInformation loadingInformation, Boolean isActive);

  @Modifying
  @Transactional
  @Query("UPDATE PortLoadingPlanRobDetails SET isActive = false WHERE id = ?1")
  public void deleteById(Long id);
}
