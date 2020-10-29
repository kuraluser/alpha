/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.CargoNomination;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/** Repository interface for cargoNomination entity */
public interface CargoNominationRepository extends CommonCrudRepository<CargoNomination, Long> {

  public Optional<CargoNomination> findByIdAndIsActive(Long id, Boolean isActive);

  public List<CargoNomination> findByLoadableStudyXIdAndIsActive(
      Long loadableStudyXId, Boolean isActive);

  @Transactional
  @Modifying
  @Query("Update CargoNomination set isActive = false where id = ?1 ")
  public void deleteCargoNomination(Long cargoNominationId);
}
