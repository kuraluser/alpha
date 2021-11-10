/* Licensed at AlphaOri Technologies */
package com.cpdss.loadingplan.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadingplan.entity.CargoToppingOffSequence;
import com.cpdss.loadingplan.entity.LoadingInformation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CargoToppingOffSequenceRepository
    extends CommonCrudRepository<CargoToppingOffSequence, Long> {

  @Transactional
  @Modifying
  @Query("UPDATE CargoToppingOffSequence SET isActive = false WHERE loadingInformation = ?1")
  public void deleteByLoadingInformation(LoadingInformation loadingInformation);

  List<CargoToppingOffSequence> findAllByLoadingInformationAndIsActiveTrue(LoadingInformation var1);

  public Optional<CargoToppingOffSequence> findByIdAndIsActiveTrue(Long id);

  @Transactional
  @Modifying
  @Query("UPDATE CargoToppingOffSequence SET isActive = false WHERE loadingInformation.id = ?1")
  public void deleteByLoadingInformationId(Long loadingInformationId);

  List<CargoToppingOffSequence> findByLoadingInformationId(Long loadingInfoId);
}
