/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.PortLoadingPlanCommingleDetails;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PortLoadingPlanCommingleDetailsRepository
    extends CommonCrudRepository<PortLoadingPlanCommingleDetails, Long> {

  List<PortLoadingPlanCommingleDetails> findByLoadablePatternIdAndIsActiveTrue(
      Long loadablePatternId);

  @Modifying
  @Transactional
  @Query(
      "UPDATE PortLoadingPlanCommingleDetails SET isActive = false WHERE loadingInformation.id = ?1")
  public void deleteByLoadingInformationId(Long loadingInfoId);
}
