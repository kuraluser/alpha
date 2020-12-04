/* Licensed under Apache-2.0 */
package com.cpdss.loadablestudy.repository;

import com.cpdss.common.springdata.CommonCrudRepository;
import com.cpdss.loadablestudy.entity.CommingleCargo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CommingleCargoRepository extends CommonCrudRepository<CommingleCargo, Long> {

  public List<CommingleCargo> findByLoadableStudyXIdAndIsActive(
      Long loadableStudyXId, Boolean isActive);

  public Optional<CommingleCargo> findByIdAndIsActive(Long id, Boolean isActive);

  @Transactional
  @Modifying
  @Query("Update CommingleCargo set isActive = false where id in ?1 ")
  public void deleteCommingleCargo(List<Long> commingleCargoIds);
}
