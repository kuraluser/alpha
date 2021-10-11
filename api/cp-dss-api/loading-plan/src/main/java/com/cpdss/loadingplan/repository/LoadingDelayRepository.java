/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.LoadingDelay;
import com.cpdss.loadingplan.entity.LoadingInformation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface LoadingDelayRepository extends CommonCrudRepository<LoadingDelay, Long> {

  List<LoadingDelay> findAllByLoadingInformationAndIsActiveTrue(LoadingInformation var1);

  List<LoadingDelay> findAllByLoadingInformationAndIsActiveTrueOrderById(LoadingInformation var1);

  public Optional<LoadingDelay> findByIdAndIsActive(Long id, Boolean isActive);

  @Transactional
  @Modifying
  @Query("UPDATE LoadingDelay SET isActive = false WHERE loadingInformation.id = ?1")
  public void deleteByLoadingInformationId(Long loadingInformationId);

  public List<LoadingDelay> findByLoadingInformationIdAndIsActive(
      Long loadingInformationId, Boolean isActive);

  @Transactional
  @Modifying
  @Query("UPDATE LoadingDelay SET isActive = false WHERE id = ?1")
  public void deleteById(Long id);

  public List<LoadingDelay> findByLoadingInformationId(
          Long loadingInformationId);
}
