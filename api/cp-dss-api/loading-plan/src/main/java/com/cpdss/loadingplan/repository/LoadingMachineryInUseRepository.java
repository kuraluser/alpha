/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.LoadingInformation;
import com.cpdss.loadingplan.entity.LoadingMachineryInUse;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface LoadingMachineryInUseRepository
    extends CommonCrudRepository<LoadingMachineryInUse, Long> {

  List<LoadingMachineryInUse> findAllByLoadingInformationAndIsActiveTrue(LoadingInformation var1);

  Optional<LoadingMachineryInUse> findByIdAndIsActiveTrue(Long id);

  Optional<LoadingMachineryInUse>
      findByLoadingInformationAndMachineXIdAndMachineTypeXidAndIsActiveTrue(
          LoadingInformation var1, Long var2, Integer var3);

  @Transactional
  @Modifying
  @Query("UPDATE LoadingMachineryInUse SET isActive = false WHERE loadingInformation.id = ?1")
  public void deleteByLoadingInformationId(Long loadingInformationId);

  List<LoadingMachineryInUse> findByLoadingInformationIdAndIsActive(
      Long loadingInformationId, Boolean isActive);

  @Transactional
  @Modifying
  @Query("UPDATE LoadingMachineryInUse SET isActive = false WHERE id = ?1")
  public void deleteById(Long id);
}
