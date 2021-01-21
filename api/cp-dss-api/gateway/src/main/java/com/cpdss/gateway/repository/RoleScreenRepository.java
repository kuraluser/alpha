/* Licensed under Apache-2.0 */
package com.cpdss.gateway.repository;

import com.cpdss.gateway.domain.ScreenInfo;
import com.cpdss.gateway.entity.RoleScreen;
import com.cpdss.gateway.entity.Roles;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RoleScreenRepository extends CrudRepository<RoleScreen, Long> {

  @Query(
      "Select new com.cpdss.gateway.domain.ScreenInfo(r.screen.id, r.screen.name, r.screen.languageKey, r.screen.isAvailableAdd, r.screen.isAvailableEdit, r.screen.isAvailableDelete, r.screen.isAvailableView) from RoleScreen r where r.roles = ?1 and r.isActive = ?2")
  List<ScreenInfo> findByRolesAndIsActive(Roles roles, Boolean isActive);

  @Query("FROM RoleScreen RS WHERE RS.roles.id = ?1 and RS.screen.id = ?2 and RS.companyXId = ?3")
  Optional<RoleScreen> findByRolesAndScreenAndCompanyXId(
      long roleId, long screenId, long companyId);
}
