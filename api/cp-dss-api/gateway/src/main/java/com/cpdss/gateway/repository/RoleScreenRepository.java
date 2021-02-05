/* Licensed under Apache-2.0 */
package com.cpdss.gateway.repository;

import com.cpdss.gateway.domain.ScreenInfo;
import com.cpdss.gateway.entity.RoleScreen;
import com.cpdss.gateway.entity.Roles;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface RoleScreenRepository extends CrudRepository<RoleScreen, Long> {

  @Query(
      "Select new com.cpdss.gateway.domain.ScreenInfo(r.screen.id, r.screen.name, r.screen.languageKey, r.canAdd, r.canEdit, r.canDelete, r.canView) from RoleScreen r where r.roles = ?1 and r.isActive = ?2")
  List<ScreenInfo> findByRolesAndIsActive(Roles roles, Boolean isActive);

  @Query(
      "FROM RoleScreen RS WHERE RS.roles.id = ?1 and RS.screen.id = ?2 and RS.companyXId = ?3 and RS.isActive =?4")
  Optional<RoleScreen> findByRolesAndScreenAndCompanyXIdAndIsActive(
      Long roleId, Long screenId, Long companyId, Boolean isActive);

  @Query(
      "FROM RoleScreen RS WHERE RS.companyXId = ?1 and RS.screen.id = ?2 and RS.roles.id = ?3 and RS.isActive = ?4")
  Optional<RoleScreen> findByCompanyXIdAndScreenAndRolesAndIsActive(
      Long companyId, Long screenId, Long roleId, Boolean isActive);

  @Transactional
  @Modifying
  @Query("Update RoleScreen RS  set RS.isActive = false where  RS.roles.id = ?1 ")
  public void deleteRoles(Long id);
}
