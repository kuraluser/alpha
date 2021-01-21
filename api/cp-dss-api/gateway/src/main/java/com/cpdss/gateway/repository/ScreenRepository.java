/* Licensed under Apache-2.0 */
package com.cpdss.gateway.repository;

import com.cpdss.gateway.entity.Screen;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ScreenRepository extends CrudRepository<Screen, Long> {

  @Query("FROM Screen SC WHERE SC.isActive = ?1")
  List<Screen> getByIsActive(boolean isActive);

  List<Screen> findByCompanyXIdAndIsActive(Long companyId, boolean isActive);
}
