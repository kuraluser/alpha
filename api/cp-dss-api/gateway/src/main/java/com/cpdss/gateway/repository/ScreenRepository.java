/* Licensed under Apache-2.0 */
package com.cpdss.gateway.repository;

import com.cpdss.gateway.entity.Screen;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ScreenRepository extends CrudRepository<Screen, Long> {

  @Query("FROM Screen SC WHERE SC.isActive = ?1")
  List<Screen> getByIsActive(boolean isActive);

  List<Screen> findByCompanyXIdAndIsActive(Long companyId, boolean isActive);

  @Query("FROM Screen SC WHERE SC.id =?1 AND SC.isActive = ?2")
  Optional<Screen> findByIdIdAndIsActive(Long screenId, boolean isActive);

  public List<Screen> findByCompanyXIdAndIdInAndIsActive(
      Long companyId, List<Long> screenId, boolean isActive);
}
