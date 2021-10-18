/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.LoadingBerthDetail;
import com.cpdss.loadingplan.entity.LoadingInformation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface LoadingBerthDetailsRepository
    extends CommonCrudRepository<LoadingBerthDetail, Long> {

  List<LoadingBerthDetail> findAllByLoadingInformationAndIsActiveTrue(LoadingInformation var1);

  public Optional<LoadingBerthDetail> findByIdAndIsActiveTrue(Long id);

  @Transactional
  @Modifying
  @Query("UPDATE LoadingBerthDetail SET isActive = false WHERE loadingInformation.id = ?1")
  public void deleteByLoadingInformationId(Long loadingInformationId);

  public List<LoadingBerthDetail> findByLoadingInformationIdAndIsActive(
      Long loadingInformationId, Boolean isActive);

  @Transactional
  @Modifying
  @Query("UPDATE LoadingBerthDetail SET isActive = false WHERE id = ?1")
  public void deleteById(Long id);

  public List<LoadingBerthDetail> findByLoadingInformationId(Long loadingInformationId);
}
